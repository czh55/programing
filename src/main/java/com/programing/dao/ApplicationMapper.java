package com.programing.dao;

import com.programing.pojo.Application;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApplicationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Application record);

    int insertSelective(Application record);

    Application selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Application record);

    int updateByPrimaryKey(Application record);

    Application selectByUserIdAndApplicationNo(@Param("userId")Integer userId, @Param("applicationNo")Long applicationNo);

    //这个方法是用于alipay回调时使用
    Application selectByApplicationNo(Long applicationNo);

    //默认要使用sponsorId进行限制
    Application selectByApplicationNoAndSponsorId(@Param("applicationNo")Long applicationNo, @Param("sponsorId")Integer sponsorId);

    List<Application> selectBySponsorId(@Param("sponsorId")Integer sponsorId);

    List<Application> selectByUserId(Integer userId);

    List<Application> selectAllApplication();

    //新增定时关单
    List<Application> selectApplicationStatusByCreateTime(@Param("status") Integer status, @Param("date") String date);

    int closeApplicationByApplicationId(Integer id);

}