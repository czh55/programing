package com.programing.dao;

import com.programing.pojo.ApplicationItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApplicationItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ApplicationItem record);

    int insertSelective(ApplicationItem record);

    ApplicationItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ApplicationItem record);

    int updateByPrimaryKey(ApplicationItem record);

    List<ApplicationItem> getByApplicationNoUserId(@Param("applicationNo")Long applicationNo, @Param("userId")Integer userId);

    List<ApplicationItem> getByApplicationNo(@Param("applicationNo")Long applicationNo);



    void batchInsert(@Param("applicationItemList") List<ApplicationItem> applicationItemList);


    ApplicationItem selectByCompetitionAndUserId(@Param("competitionId")Integer competitionId, @Param("userId")Integer userId);
}