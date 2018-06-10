package com.programing.service.impl;

import com.alipay.api.AlipayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.utils.ZxingUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.programing.common.Const;
import com.programing.common.ServerResponse;
import com.programing.dao.FavouriteMapper;
import com.programing.dao.ApplicationItemMapper;
import com.programing.dao.ApplicationMapper;
import com.programing.dao.PayInfoMapper;
import com.programing.dao.CompetitionMapper;
import com.programing.pojo.*;
import com.programing.pojo.Competition;
import com.programing.service.IApplicationService;
import com.programing.util.BigDecimalUtil;
import com.programing.util.DateTimeUtil;
import com.programing.util.FTPUtil;
import com.programing.util.PropertiesUtil;
import com.programing.vo.ApplicationItemVo;
import com.programing.vo.ApplicationCompetitionVo;
import com.programing.vo.ApplicationVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service("iApplicationService")
@Slf4j
public class ApplicationServiceImpl implements IApplicationService {


    private static  AlipayTradeService tradeService;
    static {

        /** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
         *  Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
         */
        Configs.init("zfbinfo.properties");

        /** 使用Configs提供的默认参数
         *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
         */
        tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
    }


    @Autowired
    private ApplicationMapper applicationMapper;
    @Autowired
    private ApplicationItemMapper applicationItemMapper;
    @Autowired
    private PayInfoMapper payInfoMapper;
    @Autowired
    private FavouriteMapper favouriteMapper;
    @Autowired
    private CompetitionMapper competitionMapper;


    public ServerResponse createApplication(Integer userId){

        //从收藏夹中获取数据
        List<Favourite> favouriteList = favouriteMapper.selectCheckedFavouriteByUserId(userId);

        //检查这些比赛是否已经参加过
        for(Favourite favourite : favouriteList){
            ServerResponse serverResponse = this.judgeIsJoin(favourite.getCompetitionId(), userId);

            if(!serverResponse.isSuccess()){
                return ServerResponse.createByErrorMessage(serverResponse.getMsg());
            }
        }

        //验证是否是同一个sponsor
        Competition competition = competitionMapper.selectByPrimaryKey(favouriteList.get(0).getCompetitionId());
        int sponsorId = competition.getSponsorId();

        for(Favourite favourite : favouriteList){
            Competition competition_temp = competitionMapper.selectByPrimaryKey(favourite.getCompetitionId());
            int sponsorId_temp = competition_temp.getSponsorId();

            if(sponsorId != sponsorId_temp){
                return ServerResponse.createByErrorMessage("比赛不同同一个sponsor，请重新选择");
            }
            sponsorId = sponsorId_temp;
        }

        //sponsorId还要application表的字段，正好上面就有

        //计算这次报名的总价,校验收藏夹的数据,包括比赛的状态和数量，返回封装好applicationItemList
        ServerResponse serverResponse = this.getApplicationItemByFavouriteList(userId, favouriteList,sponsorId);
        if(!serverResponse.isSuccess()){
            return serverResponse;
        }

        List<ApplicationItem> applicationItemList = (List<ApplicationItem>)serverResponse.getData();
        BigDecimal payment = this.getApplicationTotalPrice(applicationItemList);

        //生成报名信息:这时候操作的是application表，并把数据已经插入
        Application application = this.assembleApplication(userId,payment,sponsorId);
        if(application == null){
            return ServerResponse.createByErrorMessage("生成报名信息错误");
        }
        if(CollectionUtils.isEmpty(applicationItemList)){
            return ServerResponse.createByErrorMessage("收藏夹为空");
        }
        for(ApplicationItem applicationItem : applicationItemList){
            applicationItem.setApplicationNo(application.getApplicationNo());
        }
        //mybatis 批量插入,这时操作的是application_item表
        applicationItemMapper.batchInsert(applicationItemList);

        //生成成功,我们要减少我们比赛的名额
        this.reduceCompetitionStock(applicationItemList);
        //清空一下收藏夹
        this.cleanFavourite(favouriteList);

        //返回给前端数据

        ApplicationVo applicationVo = assembleApplicationVo(application, applicationItemList);
        return ServerResponse.createBySuccess(applicationVo);
    }



    private ApplicationVo assembleApplicationVo(Application application, List<ApplicationItem> applicationItemList){
        ApplicationVo applicationVo = new ApplicationVo();
        applicationVo.setApplicationNo(application.getApplicationNo());
        applicationVo.setPayment(application.getPayment());
        applicationVo.setPaymentType(application.getPaymentType());
        applicationVo.setPaymentTypeDesc(Const.PaymentTypeEnum.codeOf(application.getPaymentType()).getValue());

        applicationVo.setStatus(application.getStatus());
        applicationVo.setStatusDesc(Const.ApplicationStatusEnum.codeOf(application.getStatus()).getValue());

        applicationVo.setPaymentTime(DateTimeUtil.dateToStr(application.getPaymentTime()));
        applicationVo.setEndTime(DateTimeUtil.dateToStr(application.getEndTime()));
        applicationVo.setCreateTime(DateTimeUtil.dateToStr(application.getCreateTime()));
        applicationVo.setCloseTime(DateTimeUtil.dateToStr(application.getCloseTime()));


        applicationVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));


        List<ApplicationItemVo> applicationItemVoList = Lists.newArrayList();

        for(ApplicationItem applicationItem : applicationItemList){
            ApplicationItemVo applicationItemVo = assembleApplicationItemVo(applicationItem);
            applicationItemVoList.add(applicationItemVo);
        }
        applicationVo.setApplicationItemVoList(applicationItemVoList);
        return applicationVo;
    }


    private ApplicationItemVo assembleApplicationItemVo(ApplicationItem applicationItem){
        ApplicationItemVo applicationItemVo = new ApplicationItemVo();
        applicationItemVo.setApplicationNo(applicationItem.getApplicationNo());
        applicationItemVo.setCompetitionId(applicationItem.getCompetitionId());
        applicationItemVo.setCompetitionName(applicationItem.getCompetitionName());
        applicationItemVo.setCompetitionImage(applicationItem.getCompetitionImage());
        applicationItemVo.setCurrentUnitPrice(applicationItem.getCurrentUnitPrice());

        applicationItemVo.setCreateTime(DateTimeUtil.dateToStr(applicationItem.getCreateTime()));
        return applicationItemVo;
    }


    private void cleanFavourite(List<Favourite> favouriteList){
        for(Favourite favourite : favouriteList){
            favouriteMapper.deleteByPrimaryKey(favourite.getId());
        }
    }

    private void reduceCompetitionStock(List<ApplicationItem> applicationItemList){
        for(ApplicationItem applicationItem : applicationItemList){
            Competition competition = competitionMapper.selectByPrimaryKey(applicationItem.getCompetitionId());
            competition.setStock(competition.getStock()- 1);
            competitionMapper.updateByPrimaryKeySelective(competition);
        }
    }

    //生成报名信息:这时候操作的是application表，并把数据已经插入
    private Application assembleApplication(Integer userId, BigDecimal payment, Integer sponsorId){
        Application application = new Application();
        long applicationNo = this.generateApplicationNo();
        application.setApplicationNo(applicationNo);
        application.setStatus(Const.ApplicationStatusEnum.NO_PAY.getCode());
        application.setPaymentType(Const.PaymentTypeEnum.ONLINE_PAY.getCode());
        application.setPayment(payment);

        application.setUserId(userId);
        application.setSponsorId(sponsorId);
        //付款时间等等
        int rowCount = applicationMapper.insert(application);
        if(rowCount > 0){
            return application;
        }
        return null;
    }


    private long generateApplicationNo(){
        long currentTime =System.currentTimeMillis();
        return currentTime+new Random().nextInt(100);
    }

    private BigDecimal getApplicationTotalPrice(List<ApplicationItem> applicationItemList){
        BigDecimal payment = new BigDecimal("0");
        for(ApplicationItem applicationItem : applicationItemList){
            payment = BigDecimalUtil.add(payment.doubleValue(), applicationItem.getCurrentUnitPrice().doubleValue());
        }
        return payment;
    }

    //计算这次报名的总价,校验收藏夹的数据,包括比赛的状态和数量，返回封装好applicationItemList
    private ServerResponse getApplicationItemByFavouriteList(Integer userId, List<Favourite> favouriteList, Integer sponsorId){
        List<ApplicationItem> applicationItemList = Lists.newArrayList();
        if(CollectionUtils.isEmpty(favouriteList)){
            return ServerResponse.createByErrorMessage("收藏夹为空");
        }

        //校验收藏夹的数据,包括比赛的状态和数量
        for(Favourite favouriteItem : favouriteList){
            ApplicationItem applicationItem = new ApplicationItem();
            Competition competition = competitionMapper.selectByPrimaryKey(favouriteItem.getCompetitionId());
            if(Const.CompetitionStatusEnum.ON_SALE.getCode() != competition.getStatus()){
                return ServerResponse.createByErrorMessage("比赛"+ competition.getName()+"不是可报名状态");
            }

            //校验名额
            if(competition.getStock() < 1){
                return ServerResponse.createByErrorMessage("比赛"+ competition.getName()+"名额不足");
            }

            applicationItem.setSponsorId(sponsorId);
            applicationItem.setUserId(userId);
            applicationItem.setCompetitionId(competition.getId());
            applicationItem.setCompetitionName(competition.getName());
            applicationItem.setCompetitionImage(competition.getMainImage());
            applicationItem.setCurrentUnitPrice(competition.getPrice());
            applicationItemList.add(applicationItem);
        }
        return ServerResponse.createBySuccess(applicationItemList);
    }


    public ServerResponse<String> cancel(Integer userId,Long applicationNo){
        Application application = applicationMapper.selectByUserIdAndApplicationNo(userId,applicationNo);
        if(application == null){
            return ServerResponse.createByErrorMessage("该用户此报名不存在");
        }
        if(application.getStatus() != Const.ApplicationStatusEnum.NO_PAY.getCode()){
            return ServerResponse.createByErrorMessage("已付款,无法取消报名");
        }
        Application updateApplication = new Application();
        updateApplication.setId(application.getId());
        updateApplication.setStatus(Const.ApplicationStatusEnum.CANCELED.getCode());

        int row = applicationMapper.updateByPrimaryKeySelective(updateApplication);
        if(row > 0){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }




    public ServerResponse getApplicationCompetitionForConfirmPage(Integer userId){
        ApplicationCompetitionVo applicationCompetitionVo = new ApplicationCompetitionVo();
        //从收藏夹中获取数据

        List<Favourite> favouriteList = favouriteMapper.selectCheckedFavouriteByUserId(userId);

        if(favouriteList.size() == 0){
            return ServerResponse.createByErrorMessage("收藏夹中没有勾选比赛，请重新选择");
        }


        //查询比赛对应的sponsor
        Competition competition = competitionMapper.selectByPrimaryKey(favouriteList.get(0).getCompetitionId());
        int sponsorId = competition.getSponsorId();

        //这里的getFavouriteApplicationItem()还在create报名信息的时候使用，目的：根据favouriteList，检查并重组applicationItem
        //到达确认页以后，可能同时又很多用户同时操作。当我们真正的提交报名时，可能名额已经不够了，所有要调用进行第二次的检查
        ServerResponse serverResponse =  this.getApplicationItemByFavouriteList(userId, favouriteList,sponsorId);
        if(!serverResponse.isSuccess()){
            return serverResponse;
        }
        List<ApplicationItem> applicationItemList =( List<ApplicationItem> ) serverResponse.getData();

        List<ApplicationItemVo> applicationItemVoList = Lists.newArrayList();

        BigDecimal payment = new BigDecimal("0");
        for(ApplicationItem applicationItem : applicationItemList){
            payment = BigDecimalUtil.add(payment.doubleValue(), applicationItem.getCurrentUnitPrice().doubleValue());
            applicationItemVoList.add(assembleApplicationItemVo(applicationItem));
        }
        applicationCompetitionVo.setCompetitionTotalPrice(payment);
        applicationCompetitionVo.setApplicationItemVoList(applicationItemVoList);
        applicationCompetitionVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        return ServerResponse.createBySuccess(applicationCompetitionVo);
    }


    public ServerResponse<ApplicationVo> getApplicationDetail(Integer userId, Long applicationNo){
        Application application = applicationMapper.selectByUserIdAndApplicationNo(userId,applicationNo);
        if(application != null){
            List<ApplicationItem> applicationItemList = applicationItemMapper.getByApplicationNoUserId(applicationNo,userId);
            ApplicationVo applicationVo = assembleApplicationVo(application, applicationItemList);
            return ServerResponse.createBySuccess(applicationVo);
        }
        return  ServerResponse.createByErrorMessage("没有找到该报名");
    }


    public ServerResponse<PageInfo> getApplicationList(Integer userId,int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Application> applicationList = applicationMapper.selectByUserId(userId);
        List<ApplicationVo> applicationVoList = assembleApplicationVoList(applicationList,userId);
        PageInfo pageResult = new PageInfo(applicationList);
        pageResult.setList(applicationVoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    @Override
    public ServerResponse judgeIsJoin(Integer competitionId,Integer userId) {
        ApplicationItem applicationItem = applicationItemMapper.selectByCompetitionAndUserId(competitionId, userId);

        if(applicationItem != null){
            return ServerResponse.createByErrorMessage(applicationItem.getCompetitionName()+"已经参加过，不可重复");
        }
        else{
            return ServerResponse.createBySuccess();
        }

    }


    private List<ApplicationVo> assembleApplicationVoList(List<Application> applicationList, Integer userId){
        List<ApplicationVo> applicationVoList = Lists.newArrayList();
        for(Application application : applicationList){
            List<ApplicationItem> applicationItemList = Lists.newArrayList();
            if(userId == null){
                //todo 管理员查询的时候 不需要传userId
                applicationItemList = applicationItemMapper.getByApplicationNo(application.getApplicationNo());
            }else{
                applicationItemList = applicationItemMapper.getByApplicationNoUserId(application.getApplicationNo(),userId);
            }
            ApplicationVo applicationVo = assembleApplicationVo(application, applicationItemList);
            applicationVoList.add(applicationVo);
        }
        return applicationVoList;
    }






















    public ServerResponse pay(Long applicationNo,Integer userId,String path){
        Map<String ,String> resultMap = Maps.newHashMap();
        Application application = applicationMapper.selectByUserIdAndApplicationNo(userId,applicationNo);
        if(application == null){
            return ServerResponse.createByErrorMessage("用户没有该报名");
        }
        resultMap.put("applicationNo",String.valueOf(application.getApplicationNo()));



        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = application.getApplicationNo().toString();


        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = new StringBuilder().append("programing扫码支付,订单号:").append(outTradeNo).toString();


        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = application.getPayment().toString();


        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0";



        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = new StringBuilder().append("订单").append(outTradeNo).append("购买商品共").append(totalAmount).append("元").toString();


        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "test_operator_id";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "test_store_id";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088100200300400500");




        // 支付超时，定义为120分钟
        String timeoutExpress = "120m";

        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();

        List<ApplicationItem> applicationItemList = applicationItemMapper.getByApplicationNoUserId(applicationNo,userId);
        for(ApplicationItem applicationItem : applicationItemList){
            GoodsDetail goods = GoodsDetail.newInstance(applicationItem.getCompetitionId().toString(), applicationItem.getCompetitionName(),
                    BigDecimalUtil.mul(applicationItem.getCurrentUnitPrice().doubleValue(),new Double(100).doubleValue()).longValue(),
                    1);
            goodsDetailList.add(goods);
        }

        // 创建扫码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
                .setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
                .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
                .setTimeoutExpress(timeoutExpress)
                .setNotifyUrl(PropertiesUtil.getProperty("alipay.callback.url"))//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
                .setGoodsDetailList(goodsDetailList);


        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("支付宝预下单成功: )");

                AlipayTradePrecreateResponse response = result.getResponse();
                dumpResponse(response);

                File folder = new File(path);
                if(!folder.exists()){
                    folder.setWritable(true);
                    folder.mkdirs();
                }

                // 需要修改为运行机器上的路径
                //细节细节细节
                String qrPath = String.format(path+"/qr-%s.png",response.getOutTradeNo());
                String qrFileName = String.format("qr-%s.png",response.getOutTradeNo());
                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, qrPath);

                File targetFile = new File(path,qrFileName);
                try {
                    FTPUtil.uploadFile(Lists.newArrayList(targetFile));
                } catch (IOException e) {
                    log.error("上传二维码异常",e);
                }
                log.info("qrPath:" + qrPath);
                String qrUrl = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFile.getName();
                resultMap.put("qrUrl",qrUrl);
                return ServerResponse.createBySuccess(resultMap);
            case FAILED:
                log.error("支付宝预下单失败!!!");
                return ServerResponse.createByErrorMessage("支付宝预下单失败!!!");

            case UNKNOWN:
                log.error("系统异常，预下单状态未知!!!");
                return ServerResponse.createByErrorMessage("系统异常，预下单状态未知!!!");

            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                return ServerResponse.createByErrorMessage("不支持的交易状态，交易返回异常!!!");
        }

    }

    // 简单打印应答
    private void dumpResponse(AlipayResponse response) {
        if (response != null) {
            log.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (StringUtils.isNotEmpty(response.getSubCode())) {
                log.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
                        response.getSubMsg()));
            }
            log.info("body:" + response.getBody());
        }
    }


    public ServerResponse aliCallback(Map<String,String> params){
        Long applicationNo = Long.parseLong(params.get("out_trade_no"));
        String tradeNo = params.get("trade_no");
        String tradeStatus = params.get("trade_status");
        Application application = applicationMapper.selectByApplicationNo(applicationNo);
        if(application == null){
            return ServerResponse.createByErrorMessage("非programing的订单,回调忽略");
        }
        if(application.getStatus() >= Const.ApplicationStatusEnum.PAID.getCode()){
            return ServerResponse.createBySuccess("支付宝重复调用");
        }

        //交易成功，更改application表中数据的状态
        if(Const.AlipayCallback.TRADE_STATUS_TRADE_SUCCESS.equals(tradeStatus)){
            application.setPaymentTime(DateTimeUtil.strToDate(params.get("gmt_payment")));
            application.setStatus(Const.ApplicationStatusEnum.PAID.getCode());
            applicationMapper.updateByPrimaryKeySelective(application);
        }

        //交易成功，添加payinfo表的信息
        PayInfo payInfo = new PayInfo();
        payInfo.setUserId(application.getUserId());
        payInfo.setApplicationNo(application.getApplicationNo());
        payInfo.setPayPlatform(Const.PayPlatformEnum.ALIPAY.getCode());
        payInfo.setPlatformNumber(tradeNo);
        payInfo.setPlatformStatus(tradeStatus);

        payInfoMapper.insert(payInfo);

        return ServerResponse.createBySuccess();
    }





    public ServerResponse queryApplicationPayStatus(Integer userId,Long applicationNo){
        Application application = applicationMapper.selectByUserIdAndApplicationNo(userId,applicationNo);
        if(application == null){
            return ServerResponse.createByErrorMessage("用户没有该报名");
        }
        if(application.getStatus() >= Const.ApplicationStatusEnum.PAID.getCode()){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }














    //backend

    public ServerResponse<PageInfo> manageList(int pageNum,int pageSize, int sponsorId){
        PageHelper.startPage(pageNum,pageSize);
//        List<Application> applicationList = applicationMapper.selectAllApplication();

        List<Application> applicationList = applicationMapper.selectBySponsorId(sponsorId);
        List<ApplicationVo> applicationVoList = this.assembleApplicationVoList(applicationList,null);
        PageInfo pageResult = new PageInfo(applicationList);
        pageResult.setList(applicationVoList);
        return ServerResponse.createBySuccess(pageResult);
    }


    public ServerResponse<ApplicationVo> manageDetail(Long applicationNo, int sponsorId){
        Application application = applicationMapper.selectByApplicationNoAndSponsorId(applicationNo,sponsorId);
        if(application != null){
            List<ApplicationItem> applicationItemList = applicationItemMapper.getByApplicationNo(applicationNo);
            ApplicationVo applicationVo = assembleApplicationVo(application, applicationItemList);
            return ServerResponse.createBySuccess(applicationVo);
        }
        return ServerResponse.createByErrorMessage("报名不存在");
    }



    public ServerResponse<PageInfo> manageSearch(Long applicationNo,int sponsorId,int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        Application application = applicationMapper.selectByApplicationNoAndSponsorId(applicationNo,sponsorId);
        if(application != null){
            List<ApplicationItem> applicationItemList = applicationItemMapper.getByApplicationNo(applicationNo);
            ApplicationVo applicationVo = assembleApplicationVo(application, applicationItemList);

            PageInfo pageResult = new PageInfo(Lists.newArrayList(application));
            pageResult.setList(Lists.newArrayList(applicationVo));
            return ServerResponse.createBySuccess(pageResult);
        }
        return ServerResponse.createByErrorMessage("报名不存在");
    }


    @Override
    public void closeApplication(int hour) {
        Date closeDateTime = DateUtils.addHours(new Date(),-hour);
        List<Application> applicationList = applicationMapper.selectApplicationStatusByCreateTime(Const.ApplicationStatusEnum.NO_PAY.getCode(),DateTimeUtil.dateToStr(closeDateTime));

        for(Application application : applicationList){
            List<ApplicationItem> applicationItemList = applicationItemMapper.getByApplicationNo(application.getApplicationNo());
            for(ApplicationItem applicationItem : applicationItemList){

                //一定要用主键where条件，防止锁表。同时必须是支持MySQL的InnoDB。
                Integer stock = competitionMapper.selectStockByCompetitionId(applicationItem.getCompetitionId());

                //考虑到已生成的报名信息里的比赛，被删除的情况
                if(stock == null){
                    continue;
                }
                Competition competition = new Competition();
                competition.setId(applicationItem.getCompetitionId());
                competition.setStock(stock + 1);
                competitionMapper.updateByPrimaryKeySelective(competition);
            }
            applicationMapper.closeApplicationByApplicationId(application.getId());
            log.info("关闭订单ApplicationNo：{}", application.getApplicationNo());
        }
    }


}
