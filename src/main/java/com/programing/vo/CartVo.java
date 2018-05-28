package com.programing.vo;

import java.math.BigDecimal;
import java.util.List;

public class CartVo {

    private List<CartCompetitionVo> cartCompetitionVoList;
    private BigDecimal cartTotalPrice;
    private Boolean allChecked;//是否已经都勾选
    private String imageHost;

    public List<CartCompetitionVo> getCartCompetitionVoList() {
        return cartCompetitionVoList;
    }

    public void setCartCompetitionVoList(List<CartCompetitionVo> cartCompetitionVoList) {
        this.cartCompetitionVoList = cartCompetitionVoList;
    }

    public BigDecimal getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(BigDecimal cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
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
