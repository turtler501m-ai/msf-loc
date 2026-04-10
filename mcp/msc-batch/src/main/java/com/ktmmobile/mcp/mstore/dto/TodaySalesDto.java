package com.ktmmobile.mcp.mstore.dto;

public class TodaySalesDto {
    private String prodNm;
    private String prodUrl;
    private String prodImgUrl;
    private Long prodCmprc;
    private Long prodSeprc;

    public String getProdNm() {
        return prodNm;
    }

    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
    }

    public String getProdUrl() {
        return prodUrl;
    }

    public void setProdUrl(String prodUrl) {
        this.prodUrl = prodUrl;
    }

    public String getProdImgUrl() {
        return prodImgUrl;
    }

    public void setProdImgUrl(String prodImgUrl) {
        this.prodImgUrl = prodImgUrl;
    }

    public Long getProdCmprc() {
        return prodCmprc;
    }

    public void setProdCmprc(Long prodCmprc) {
        this.prodCmprc = prodCmprc;
    }

    public Long getProdSeprc() {
        return prodSeprc;
    }

    public void setProdSeprc(Long prodSeprc) {
        this.prodSeprc = prodSeprc;
    }
}
