package com.programing.dao;

import com.programing.pojo.Result;

public interface ResultMapper {

    int insert(Result record);

    int updateByPrimaryKey(Result record);

    Result selectByProductId(Integer id);
}
