package com.programing.service;

import com.programing.common.ServerResponse;
import com.programing.vo.CartVo;

/**
 * Created by geely
 */
public interface ICartService {
    ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count);
    ServerResponse<CartVo> update(Integer userId,Integer productId,Integer count);
    ServerResponse<CartVo> deleteProduct(Integer userId,String productIds);

    ServerResponse<CartVo> list (Integer userId);
    ServerResponse<CartVo> selectOrUnSelect (Integer userId,Integer productId,Integer checked);
    ServerResponse<Integer> getCartProductCount(Integer userId);

    //判断当前购物车中选中的商品是否是相同的sponsorid下的。
    ServerResponse judgeSameSponsorId(Integer productId);
}
