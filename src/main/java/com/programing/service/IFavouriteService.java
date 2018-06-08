package com.programing.service;

import com.programing.common.ServerResponse;
import com.programing.vo.FavouriteVo;


public interface IFavouriteService {
    ServerResponse<FavouriteVo> add(Integer userId, Integer competitionId);
    ServerResponse<FavouriteVo> deleteCompetition(Integer userId, String competitionIds);

    ServerResponse<FavouriteVo> list (Integer userId);
    ServerResponse<FavouriteVo> selectOrUnSelect (Integer userId, Integer competitionId, Integer checked);
    ServerResponse<Integer> getFavouriteCompetitionCount(Integer userId);

    //判断当前收藏夹中选中的比赛是否是相同的sponsorid下的。
    ServerResponse judgeSameSponsorId(Integer competitionId);
}
