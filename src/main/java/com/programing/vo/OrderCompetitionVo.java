package com.programing.vo;

import java.math.BigDecimal;
import java.util.List;

public class OrderCompetitionVo {
    private List<OrderItemVo> orderItemVoList;
    private BigDecimal competitionTotalPrice;
    private String imageHost;

    public List<OrderItemVo> getOrderItemVoList() {
        return orderItemVoList;
    }

    public void setOrderItemVoList(List<OrderItemVo> orderItemVoList) {
        this.orderItemVoList = orderItemVoList;
    }

    public BigDecimal getCompetitionTotalPrice() {
        return competitionTotalPrice;
    }

    public void setCompetitionTotalPrice(BigDecimal competitionTotalPrice) {
        this.competitionTotalPrice = competitionTotalPrice;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }
}
