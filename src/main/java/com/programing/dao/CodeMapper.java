package com.programing.dao;

import com.programing.pojo.Code;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CodeMapper {

    int insert(Code record);

    int updateByPrimaryKey(Code record);

    Code selectByCompetitionIdAndUserId(@Param("competitionId")Integer competitionId, @Param("userId")Integer userId);

    List<Code>  selectListByCompetitionId(@Param("competitionId")Integer competitionId);
}
