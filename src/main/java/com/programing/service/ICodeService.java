package com.programing.service;

import com.github.pagehelper.PageInfo;
import com.programing.common.ServerResponse;
import com.programing.pojo.Code;

public interface ICodeService {

    ServerResponse saveOrUpdateCode(Code code);

    ServerResponse<Code> getCodeDetail(Integer productId, Integer userId);

    ServerResponse<PageInfo> getCodeListByProductId(Integer productId,int pageNum,int pageSize);
}