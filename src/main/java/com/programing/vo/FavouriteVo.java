package com.programing.vo;

import java.math.BigDecimal;
import java.util.List;

public class FavouriteVo {

    private List<FavouriteCompetitionVo> favouriteCompetitionVoList;
    private BigDecimal favouriteTotalPrice;
    private Boolean allChecked;//是否已经都勾选
    private String imageHost;

    public List<FavouriteCompetitionVo> getFavouriteCompetitionVoList() {
        return favouriteCompetitionVoList;
    }

    public void setFavouriteCompetitionVoList(List<FavouriteCompetitionVo> favouriteCompetitionVoList) {
        this.favouriteCompetitionVoList = favouriteCompetitionVoList;
    }

    public BigDecimal getFavouriteTotalPrice() {
        return favouriteTotalPrice;
    }

    public void setFavouriteTotalPrice(BigDecimal favouriteTotalPrice) {
        this.favouriteTotalPrice = favouriteTotalPrice;
    }

    public Boolean getAllChecked() {
        return allChecked;
    }

    public void setAllChecked(Boolean allChecked) {
        this.allChecked = allChecked;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }
}
