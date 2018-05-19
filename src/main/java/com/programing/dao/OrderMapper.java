package com.programing.dao;

import com.programing.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    Order selectByUserIdAndOrderNo(@Param("userId")Integer userId, @Param("orderNo")Long orderNo);

    //这个方法是用于alipay回调时使用
    Order selectByOrderNo(Long orderNo);

    //默认要使用sponsorId进行限制
    Order selectByOrderNoAndSponsorId(@Param("orderNo")Long orderNo, @Param("sponsorId")Integer sponsorId);

    List<Order> selectBySponsorId(@Param("sponsorId")Integer sponsorId);

    List<Order> selectByUserId(Integer userId);

    List<Order> selectAllOrder();

    //二期新增定时关单
    List<Order> selectOrderStatusByCreateTime(@Param("status") Integer status,@Param("date") String date);

    int closeOrderByOrderId(Integer id);

}