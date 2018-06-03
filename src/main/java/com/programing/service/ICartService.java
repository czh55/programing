package com.programing.service;

import com.programing.common.ServerResponse;
import com.programing.vo.CartVo;


public interface ICartService {
    ServerResponse<CartVo> add(Integer userId, Integer competitionId);
    ServerResponse<CartVo> deleteCompetition(Integer userId,String competitionIds);

    ServerResponse<CartVo> list (Integer userId);
    ServerResponse<CartVo> selectOrUnSelect (Integer userId,Integer competitionId,Integer checked);
    ServerResponse<Integer> getCartCompetitionCount(Integer userId);

    //判断当前购物车中选中的商品是否是相同的sponsorid下的。
    ServerResponse judgeSameSponsorId(Integer competitionId);
}
