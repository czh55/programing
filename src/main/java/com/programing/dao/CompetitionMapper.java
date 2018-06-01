package com.programing.dao;

import com.programing.pojo.Competition;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface CompetitionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Competition record);

    int insertSelective(Competition record);

    Competition selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Competition record);

    int updateByPrimaryKey(Competition record);

    List<Competition> selectList();

    //sponsor 这里要特殊处理admin,他的sponsorId = 0
    List<Competition> selectListBySponsorId(@Param("sponsorId") Integer sponsorId);

    List<Competition> selectByNameAndCompetitionId(@Param("competitionName")String competitionName, @Param("competitionId") Integer competitionId);

    //sponsor 这里的sponsorId是必须条件，而name、competitionId、status是任选一个。
    //这里要特殊处理admin,他的sponsorId = 0
    List<Competition> selectByNameAndCompetitionIdAndStatusAndSponsorId(@Param("competitionName")String competitionName, @Param("competitionId") Integer competitionId, @Param("status") Integer status, @Param("sponsorId") Integer sponsorId);

    List<Competition> selectByNameAndCategoryIds(@Param("competitionName")String competitionName, @Param("categoryIdList")List<Integer> categoryIdList);

    List<Competition> selectWithResult();

    //这里一定要用Integer，因为int无法为NULL，考虑到很多商品已经删除的情况。
    Integer selectStockByCompetitionId(Integer id);


}