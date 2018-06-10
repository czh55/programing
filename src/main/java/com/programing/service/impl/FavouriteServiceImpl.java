package com.programing.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.programing.common.Const;
import com.programing.common.ResponseCode;
import com.programing.common.ServerResponse;
import com.programing.dao.FavouriteMapper;
import com.programing.dao.CompetitionMapper;
import com.programing.pojo.Favourite;
import com.programing.pojo.Competition;
import com.programing.service.IFavouriteService;
import com.programing.service.ICompetitionService;
import com.programing.service.IUserService;
import com.programing.util.BigDecimalUtil;
import com.programing.util.PropertiesUtil;
import com.programing.vo.FavouriteCompetitionVo;
import com.programing.vo.FavouriteVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service("iFavouriteService")
public class FavouriteServiceImpl implements IFavouriteService {

    @Autowired
    private FavouriteMapper favouriteMapper;
    @Autowired
    private CompetitionMapper competitionMapper;

    @Autowired
    private ICompetitionService iCompetitionService;
    @Autowired
    private IUserService iUserService;

    //只有比赛没有被添加到收藏夹中，才可以添加
    public ServerResponse<FavouriteVo> add(Integer userId, Integer competitionId){
        if(competitionId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        Favourite favourite = favouriteMapper.selectFavouriteByUserIdCompetitionId(userId,competitionId);
        if(favourite == null){
            //这个比赛不在这个收藏夹里,需要新增一个这个比赛的记录
            Favourite favouriteItem = new Favourite();
            favouriteItem.setChecked(Const.Favourite.CHECKED);
            favouriteItem.setCompetitionId(competitionId);
            favouriteItem.setUserId(userId);
            favouriteMapper.insert(favouriteItem);
        }

        return this.list(userId);
    }


    public ServerResponse<FavouriteVo> deleteCompetition(Integer userId, String competitionIds){
        List<String> competitionList = Splitter.on(",").splitToList(competitionIds);
        if(CollectionUtils.isEmpty(competitionList)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        favouriteMapper.deleteByUserIdCompetitionIds(userId,competitionList);
        return this.list(userId);
    }


    public ServerResponse<FavouriteVo> list (Integer userId){
        FavouriteVo favouriteVo = this.getFavouriteVoLimit(userId);
        return ServerResponse.createBySuccess(favouriteVo);
    }



    public ServerResponse<FavouriteVo> selectOrUnSelect (Integer userId, Integer competitionId, Integer checked){
        favouriteMapper.checkedOrUncheckedCompetition(userId,competitionId,checked);
        return this.list(userId);
    }

    public ServerResponse<Integer> getFavouriteCompetitionCount(Integer userId){
        if(userId == null){
            return ServerResponse.createBySuccess(0);
        }
        return ServerResponse.createBySuccess(favouriteMapper.selectFavouriteCompetitionCount(userId));
    }


    public ServerResponse judgeSameSponsorId(Integer userId) {
        //从收藏夹中获取数据
        List<Favourite> favouriteList = favouriteMapper.selectCheckedFavouriteByUserId(userId);

        Favourite favourite = favouriteList.get(0);
        int sponsorIdTemp = iCompetitionService.getSponsorIdByCompetitionId(favourite.getCompetitionId()).getData();
        for(int i = 1; i < favouriteList.size(); i++){
            favourite = favouriteList.get(i);
            int sponsorId = iCompetitionService.getSponsorIdByCompetitionId(favourite.getCompetitionId()).getData();
            if(sponsorId != sponsorIdTemp){
                //code = 1表示失败
                return ServerResponse.createByErrorMessage("不是同一个sponsor，请重新选择");
            }
            sponsorIdTemp = sponsorId;
        }
        //code = 0表示成功
        return ServerResponse.createBySuccess();
    }


    private FavouriteVo getFavouriteVoLimit(Integer userId){
        FavouriteVo favouriteVo = new FavouriteVo();
        List<Favourite> favouriteList = favouriteMapper.selectFavouriteByUserId(userId);
        List<FavouriteCompetitionVo> favouriteCompetitionVoList = Lists.newArrayList();

        BigDecimal favouriteTotalPrice = new BigDecimal("0");

        if(CollectionUtils.isNotEmpty(favouriteList)){
            for(Favourite favouriteItem : favouriteList){

                Competition competition = competitionMapper.selectByPrimaryKey(favouriteItem.getCompetitionId());
                //这里要判断比赛存在，
                //名额这里我们不检查了，即使是0页显示，判断交给报名确认页
                //这里也不检查上架状态。只要是收藏的，我们就显示出来。过滤交给报名确认页。
                if(competition != null){

                    FavouriteCompetitionVo favouriteCompetitionVo = new FavouriteCompetitionVo();
                    favouriteCompetitionVo.setId(favouriteItem.getId());
                    favouriteCompetitionVo.setUserId(userId);
                    favouriteCompetitionVo.setCompetitionId(favouriteItem.getCompetitionId());

                    //用于前台顾客收藏夹显示比赛对应的sponsor
                    String sponsorName = iUserService.getInformation(competition.getSponsorId()).getData().getUsername();
                    favouriteCompetitionVo.setSponsorName(sponsorName);

                    favouriteCompetitionVo.setCompetitionMainImage(competition.getMainImage());
                    favouriteCompetitionVo.setCompetitionName(competition.getName());
                    favouriteCompetitionVo.setCompetitionSubtitle(competition.getSubtitle());
                    favouriteCompetitionVo.setCompetitionStatus(competition.getStatus());
                    favouriteCompetitionVo.setCompetitionPrice(competition.getPrice());
                    favouriteCompetitionVo.setCompetitionStock(competition.getStock());
                    //判断名额，名额不影响是否显示
                    if(competition.getStock() > 0){
                        //名额充足的时候
                        favouriteCompetitionVo.setLimitQuantity(Const.Favourite.LIMIT_NUM_SUCCESS);
                    }else{
                        //当名额为0的时候执行这里
                        favouriteCompetitionVo.setLimitQuantity(Const.Favourite.LIMIT_NUM_FAIL);
                    }

                    favouriteCompetitionVo.setCompetitionChecked(favouriteItem.getChecked());

                    if(favouriteItem.getChecked() == Const.Favourite.CHECKED){
                        //如果已经勾选,增加到整个的收藏夹总价中
                        favouriteTotalPrice = BigDecimalUtil.add(favouriteTotalPrice.doubleValue(), favouriteCompetitionVo.getCompetitionPrice().doubleValue());
                    }
                    favouriteCompetitionVoList.add(favouriteCompetitionVo);
                }
            }
        }
        favouriteVo.setFavouriteTotalPrice(favouriteTotalPrice);
        favouriteVo.setFavouriteCompetitionVoList(favouriteCompetitionVoList);
        favouriteVo.setAllChecked(this.getAllCheckedStatus(userId));
        favouriteVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));

        return favouriteVo;
    }

    private boolean getAllCheckedStatus(Integer userId){
        if(userId == null){
            return false;
        }
        return favouriteMapper.selectFavouriteCompetitionCheckedStatusByUserId(userId) == 0;

    }


























}
