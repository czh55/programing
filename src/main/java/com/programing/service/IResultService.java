package com.programing.service;

import com.github.pagehelper.PageInfo;
import com.programing.common.ServerResponse;
import com.programing.pojo.Result;

public interface IResultService {

    ServerResponse saveOrUpdateResult(Result result);
    //这个方法可以前后端公用
    ServerResponse<Result> manageResultDetail(Integer competitionId);
}
