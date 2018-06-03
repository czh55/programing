package com.programing.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.programing.common.Const;
import com.programing.common.ResponseCode;
import com.programing.common.ServerResponse;
import com.programing.dao.CartMapper;
import com.programing.dao.CompetitionMapper;
import com.programing.pojo.Cart;
import com.programing.pojo.Competition;
import com.programing.service.ICartService;
import com.programing.service.ICompetitionService;
import com.programing.service.IUserService;
import com.programing.util.BigDecimalUtil;
import com.programing.util.PropertiesUtil;
import com.programing.vo.CartCompetitionVo;
import com.programing.vo.CartVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service("iCartService")
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private CompetitionMapper competitionMapper;

    @Autowired
    private ICompetitionService iCompetitionService;
    @Autowired
    private IUserService iUserService;

    //只有比赛没有被添加到收藏夹中，才可以添加
    public ServerResponse<CartVo> add(Integer userId, Integer competitionId, Integer count){
        if(competitionId == null || count == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }


        Cart cart = cartMapper.selectCartByUserIdCompetitionId(userId,competitionId);
        if(cart == null){
            //这个产品不在这个收藏夹里,需要新增一个这个产品的记录
            Cart cartItem = new Cart();
            cartItem.setQuantity(count);
            cartItem.setChecked(Const.Cart.CHECKED);
            cartItem.setCompetitionId(competitionId);
            cartItem.setUserId(userId);
            cartMapper.insert(cartItem);
        }

        return this.list(userId);
    }


    public ServerResponse<CartVo> deleteCompetition(Integer userId,String competitionIds){
        List<String> competitionList = Splitter.on(",").splitToList(competitionIds);
        if(CollectionUtils.isEmpty(competitionList)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        cartMapper.deleteByUserIdCompetitionIds(userId,competitionList);
        return this.list(userId);
    }


    public ServerResponse<CartVo> list (Integer userId){
        CartVo cartVo = this.getCartVoLimit(userId);
        return ServerResponse.createBySuccess(cartVo);
    }



    public ServerResponse<CartVo> selectOrUnSelect (Integer userId,Integer competitionId,Integer checked){
        cartMapper.checkedOrUncheckedCompetition(userId,competitionId,checked);
        return this.list(userId);
    }

    public ServerResponse<Integer> getCartCompetitionCount(Integer userId){
        if(userId == null){
            return ServerResponse.createBySuccess(0);
        }
        return ServerResponse.createBySuccess(cartMapper.selectCartCompetitionCount(userId));
    }


    public ServerResponse judgeSameSponsorId(Integer userId) {
        //从收藏夹中获取数据
        List<Cart> cartList = cartMapper.selectCheckedCartByUserId(userId);

        Cart cart = cartList.get(0);
        int sponsorIdTemp = iCompetitionService.getSponsorIdByCompetitionId(cart.getCompetitionId()).getData();
        for(int i = 1; i < cartList.size(); i++){
            cart = cartList.get(i);
            int sponsorId = iCompetitionService.getSponsorIdByCompetitionId(cart.getCompetitionId()).getData();
            if(sponsorId != sponsorIdTemp){
                //code = 1表示失败
                return ServerResponse.createByErrorMessage("不是同一个sponsor，请重新选择");
            }
            sponsorIdTemp = sponsorId;
        }
        //code = 0表示成功
        return ServerResponse.createBySuccess();
    }


    private CartVo getCartVoLimit(Integer userId){
        CartVo cartVo = new CartVo();
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
        List<CartCompetitionVo> cartCompetitionVoList = Lists.newArrayList();

        BigDecimal cartTotalPrice = new BigDecimal("0");

        if(CollectionUtils.isNotEmpty(cartList)){
            for(Cart cartItem : cartList){
                CartCompetitionVo cartCompetitionVo = new CartCompetitionVo();
                cartCompetitionVo.setId(cartItem.getId());
                cartCompetitionVo.setUserId(userId);
                cartCompetitionVo.setCompetitionId(cartItem.getCompetitionId());

                Competition competition = competitionMapper.selectByPrimaryKey(cartItem.getCompetitionId());
                //这里要判断商品存在，并且处于上架状态1,并且库存至少要有一个
                if(competition != null && competition.getStatus() == Const.CompetitionStatusEnum.ON_SALE.getCode() && competition.getStock() > 0){

                    //用于前台顾客收藏夹显示比赛对应的sponsor
                    String sponsorName = iUserService.getInformation(competition.getSponsorId()).getData().getUsername();
                    cartCompetitionVo.setSponsorName(sponsorName);

                    cartCompetitionVo.setCompetitionMainImage(competition.getMainImage());
                    cartCompetitionVo.setCompetitionName(competition.getName());
                    cartCompetitionVo.setCompetitionSubtitle(competition.getSubtitle());
                    cartCompetitionVo.setCompetitionStatus(competition.getStatus());
                    cartCompetitionVo.setCompetitionPrice(competition.getPrice());
                    cartCompetitionVo.setCompetitionStock(competition.getStock());
                    //判断名额
                    int buyLimitCount = 0;
                    if(competition.getStock() >= cartItem.getQuantity()){
                        //名额充足的时候
                        buyLimitCount = cartItem.getQuantity();
                        cartCompetitionVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
                    }else{
                        //这种情况在不会发生，因为我们默认的数量为1
                        buyLimitCount = competition.getStock();
                        cartCompetitionVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
                        //收藏夹中更新有效名额
                        Cart cartForQuantity = new Cart();
                        cartForQuantity.setId(cartItem.getId());
                        cartForQuantity.setQuantity(buyLimitCount);
                        cartMapper.updateByPrimaryKeySelective(cartForQuantity);
                    }
                    cartCompetitionVo.setQuantity(buyLimitCount);
                    //计算总价
                    cartCompetitionVo.setCompetitionTotalPrice(BigDecimalUtil.mul(competition.getPrice().doubleValue(),cartCompetitionVo.getQuantity()));
                    cartCompetitionVo.setCompetitionChecked(cartItem.getChecked());
                }

                if(cartItem.getChecked() == Const.Cart.CHECKED){
                    //如果已经勾选,增加到整个的收藏夹总价中
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(),cartCompetitionVo.getCompetitionTotalPrice().doubleValue());
                }
                cartCompetitionVoList.add(cartCompetitionVo);
            }
        }
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setCartCompetitionVoList(cartCompetitionVoList);
        cartVo.setAllChecked(this.getAllCheckedStatus(userId));
        cartVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));

        return cartVo;
    }

    private boolean getAllCheckedStatus(Integer userId){
        if(userId == null){
            return false;
        }
        return cartMapper.selectCartCompetitionCheckedStatusByUserId(userId) == 0;

    }


























}
