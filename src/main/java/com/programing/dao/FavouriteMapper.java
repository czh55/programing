package com.programing.dao;

import com.programing.pojo.Favourite;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FavouriteMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Favourite record);

    int insertSelective(Favourite record);

    Favourite selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Favourite record);

    Favourite selectFavouriteByUserIdCompetitionId(@Param("userId") Integer userId, @Param("competitionId")Integer competitionId);

    List<Favourite> selectFavouriteByUserId(Integer userId);

    int selectFavouriteCompetitionCheckedStatusByUserId(Integer userId);

    int deleteByUserIdCompetitionIds(@Param("userId") Integer userId,@Param("competitionIdList")List<String> competitionIdList);


    int checkedOrUncheckedCompetition(@Param("userId") Integer userId,@Param("competitionId")Integer competitionId,@Param("checked") Integer checked);

    int selectFavouriteCompetitionCount(@Param("userId") Integer userId);


    List<Favourite> selectCheckedFavouriteByUserId(Integer userId);


}