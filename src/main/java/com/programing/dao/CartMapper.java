package com.programing.dao;

import com.programing.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    Cart selectCartByUserIdCompetitionId(@Param("userId") Integer userId, @Param("competitionId")Integer competitionId);

    List<Cart> selectCartByUserId(Integer userId);

    int selectCartCompetitionCheckedStatusByUserId(Integer userId);

    int deleteByUserIdCompetitionIds(@Param("userId") Integer userId,@Param("competitionIdList")List<String> competitionIdList);


    int checkedOrUncheckedCompetition(@Param("userId") Integer userId,@Param("competitionId")Integer competitionId,@Param("checked") Integer checked);

    int selectCartCompetitionCount(@Param("userId") Integer userId);


    List<Cart> selectCheckedCartByUserId(Integer userId);


}