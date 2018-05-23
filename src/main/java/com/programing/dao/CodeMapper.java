package com.programing.dao;

import com.programing.pojo.Code;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CodeMapper {

    int insert(Code record);

    int updateByPrimaryKey(Code record);

    Code selectByProductIdAndUserId(@Param("productId")Integer productId, @Param("userId")Integer userId);

    List<Code>  selectListByProductId(@Param("productId")Integer productId);
}
