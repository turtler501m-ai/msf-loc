package com.ktmmobile.mcp.mstore.dto;

import java.util.List;

public class TodaySalesMessage {
    private List<TodaySalesDto> todaySales;
    private List<PBannerDto> pBannerList;

    public List<TodaySalesDto> getTodaySales() {
        return todaySales;
    }

    public void setTodaySales(List<TodaySalesDto> todaySales) {
        this.todaySales = todaySales;
    }

    public List<PBannerDto> getPBannerList() {
        return pBannerList;
    }

    public void setPBannerList(List<PBannerDto> pBannerList) {
        this.pBannerList = pBannerList;
    }
}
