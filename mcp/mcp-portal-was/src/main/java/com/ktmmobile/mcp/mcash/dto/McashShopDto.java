package com.ktmmobile.mcp.mcash.dto;

import java.io.Serializable;

public class McashShopDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String shopId;
    private String shopName;
    private Double discountRate;

    public McashShopDto() {}

    public McashShopDto(String shopId, String shopName, Double discountRate) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.discountRate = discountRate;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(Double discountRate) {
        this.discountRate = discountRate;
    }
}
