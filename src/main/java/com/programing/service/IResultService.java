package com.programing.service;

import com.github.pagehelper.PageInfo;
import com.programing.common.ServerResponse;
import com.programing.pojo.Result;

public interface IResultService {

    ServerResponse saveOrUpdateResult(Result result);

    ServerResponse<Result> manageResultDetail(Integer productId);

}
