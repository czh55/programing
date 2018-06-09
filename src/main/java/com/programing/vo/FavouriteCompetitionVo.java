package com.programing.vo;

import java.math.BigDecimal;

public class FavouriteCompetitionVo {

//结合了比赛和收藏夹两张表的的一个抽象对象

    private Integer id;
    private Integer userId;
    private Integer competitionId;
    private String sponsorName;//用于前台顾客收藏夹显示比赛对应的sponsor
    private String competitionName;
    private String competitionSubtitle;
    private String competitionMainImage;
    private BigDecimal competitionPrice;
    private Integer competitionStatus;
    private Integer competitionStock;
    private Integer competitionChecked;//此比赛是否勾选

    private String limitQuantity;//限制名额的一个返回结果

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Integer competitionId) {
        this.competitionId = competitionId;
    }

    public String getCompetitionName() {
        return competitionName;
    }

    public void setCompetitionName(String competitionName) {
        this.competitionName = competitionName;
    }

    public String getCompetitionSubtitle() {
        return competitionSubtitle;
    }

    public void setCompetitionSubtitle(String competitionSubtitle) {
        this.competitionSubtitle = competitionSubtitle;
    }

    public String getCompetitionMainImage() {
        return competitionMainImage;
    }

    public void setCompetitionMainImage(String competitionMainImage) {
        this.competitionMainImage = competitionMainImage;
    }

    public BigDecimal getCompetitionPrice() {
        return competitionPrice;
    }

    public void setCompetitionPrice(BigDecimal competitionPrice) {
        this.competitionPrice = competitionPrice;
    }

    public Integer getCompetitionStatus() {
        return competitionStatus;
    }

    public void setCompetitionStatus(Integer competitionStatus) {
        this.competitionStatus = competitionStatus;
    }

    public Integer getCompetitionStock() {
        return competitionStock;
    }

    public void setCompetitionStock(Integer competitionStock) {
        this.competitionStock = competitionStock;
    }

    public Integer getCompetitionChecked() {
        return competitionChecked;
    }

    public void setCompetitionChecked(Integer competitionChecked) {
        this.competitionChecked = competitionChecked;
    }

    public String getLimitQuantity() {
        return limitQuantity;
    }

    public void setLimitQuantity(String limitQuantity) {
        this.limitQuantity = limitQuantity;
    }

    public String getSponsorName() {
        return sponsorName;
    }
    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }
}
