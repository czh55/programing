package com.programing.service;

import com.github.pagehelper.PageInfo;
import com.programing.common.ServerResponse;
import com.programing.vo.OrderVo;

import java.util.Map;

/**
 * Created by geely
 */
public interface IOrderService {
    ServerResponse pay(Long orderNo, Integer userId, String path);
    ServerResponse aliCallback(Map<String,String> params);
    ServerResponse queryOrderPayStatus(Integer userId,Long orderNo);
    ServerResponse createOrder(Integer userId,Integer shippingId);
    ServerResponse<String> cancel(Integer userId,Long orderNo);
    ServerResponse getOrderCartProduct(Integer userId);
    ServerResponse<OrderVo> getOrderDetail(Integer userId, Long orderNo);
    ServerResponse<PageInfo> getOrderList(Integer userId, int pageNum, int pageSize);



    //backend
    ServerResponse<PageInfo> manageList(int pageNum,int pageSize,int sponsorId);
    ServerResponse<OrderVo> manageDetail(Long orderNo,int sponsorId);
    ServerResponse<PageInfo> manageSearch(Long orderNo,int sponsorId,int pageNum,int pageSize);
    ServerResponse<String> manageSendGoods(Long orderNo,int sponsorId);

    //hour个小时以内未付款的订单，进行关闭
    void closeOrder(int hour);


}
