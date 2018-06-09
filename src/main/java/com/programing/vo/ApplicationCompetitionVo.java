package com.programing.vo;

import java.math.BigDecimal;
import java.util.List;

public class ApplicationCompetitionVo {
    private List<ApplicationItemVo> applicationItemVoList;
    private BigDecimal competitionTotalPrice;
    private String imageHost;

    public List<ApplicationItemVo> getApplicationItemVoList() {
        return applicationItemVoList;
    }

    public void setApplicationItemVoList(List<ApplicationItemVo> applicationItemVoList) {
        this.applicationItemVoList = applicationItemVoList;
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
