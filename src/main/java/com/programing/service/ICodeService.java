package com.programing.service;

import com.github.pagehelper.PageInfo;
import com.programing.common.ServerResponse;
import com.programing.pojo.Code;

public interface ICodeService {

    ServerResponse saveOrUpdateCode(Code code);

    ServerResponse<Code> getCodeDetail(Integer competitionId, Integer userId);

    ServerResponse<PageInfo> getCodeListByCompetitionId(Integer competitionId,int pageNum,int pageSize);
}
