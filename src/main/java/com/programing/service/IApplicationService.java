package com.programing.service;

import com.github.pagehelper.PageInfo;
import com.programing.common.ServerResponse;
import com.programing.vo.ApplicationVo;

import java.util.Map;

public interface IApplicationService {
    ServerResponse pay(Long applicationNo, Integer userId, String path);
    ServerResponse aliCallback(Map<String,String> params);
    ServerResponse queryApplicationPayStatus(Integer userId,Long applicationNo);
    ServerResponse createApplication(Integer userId);
    ServerResponse<String> cancel(Integer userId,Long applicationNo);
    ServerResponse getApplicationFavouriteCompetition(Integer userId);
    ServerResponse<ApplicationVo> getApplicationDetail(Integer userId, Long applicationNo);
    ServerResponse<PageInfo> getApplicationList(Integer userId, int pageNum, int pageSize);
    ServerResponse judgeIsJoin(Integer competitionId, Integer userId);


    //backend
    ServerResponse<PageInfo> manageList(int pageNum,int pageSize,int sponsorId);
    ServerResponse<ApplicationVo> manageDetail(Long applicationNo, int sponsorId);
    ServerResponse<PageInfo> manageSearch(Long applicationNo,int sponsorId,int pageNum,int pageSize);

    //hour个小时以内未付款的申请，进行关闭
    void closeApplication(int hour);



}
