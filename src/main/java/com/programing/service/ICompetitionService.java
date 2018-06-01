package com.programing.service;

import com.github.pagehelper.PageInfo;
import com.programing.common.ServerResponse;
import com.programing.pojo.Competition;
import com.programing.vo.CompetitionDetailVo;

public interface ICompetitionService {

    ServerResponse saveOrUpdateCompetition(Competition competition);

    ServerResponse<String> setSaleStatus(Integer competitionId,Integer status);

    ServerResponse<CompetitionDetailVo> manageCompetitionDetail(Integer competitionId);

    ServerResponse<PageInfo> getCompetitionList(int pageNum, int pageSize);

    //sponsor
    ServerResponse<PageInfo> getCompetitionListBySponsorId(Integer sponsorid, int pageNum, int pageSize);

    ServerResponse<PageInfo> searchCompetition(String competitionName,Integer competitionId,int pageNum,int pageSize);

    //sponsor
    ServerResponse<PageInfo> searchCompetition(String competitionName,Integer competitionId, Integer status, Integer sponsorId,int pageNum,int pageSize);

    ServerResponse<CompetitionDetailVo> getCompetitionDetail(Integer competitionId);

    ServerResponse<PageInfo> getCompetitionByKeywordCategory(String keyword,Integer categoryId,int pageNum,int pageSize,String orderBy);

    ServerResponse<PageInfo> getCompetitionWithResult(int pageNum,int pageSize);

    ServerResponse<Integer> getSponsorIdByCompetitionId(Integer competitionId);

}
