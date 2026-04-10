package com.ktmmobile.mcp.common.mplatform.dto;

public class PrdcTrtmVO {
    private String prdcCd;
    private String prdcSbscTrtmCd;
    private String prdcTypeCd;
    private String prdcSeqNo;
    private String ftrNewParam;

    public PrdcTrtmVO(String prdcCd, String prdcSbscTrtmCd, String prdcTypeCd) {
        this.prdcCd = prdcCd;
        this.prdcSbscTrtmCd = prdcSbscTrtmCd;
        this.prdcTypeCd = prdcTypeCd;
    }

    public String getPrdcCd() {
        return prdcCd;
    }

    public void setPrdcCd(String prdcCd) {
        this.prdcCd = prdcCd;
    }

    public String getPrdcSbscTrtmCd() {
        return prdcSbscTrtmCd;
    }

    public void setPrdcSbscTrtmCd(String prdcSbscTrtmCd) {
        this.prdcSbscTrtmCd = prdcSbscTrtmCd;
    }

    public String getPrdcTypeCd() {
        return prdcTypeCd;
    }

    public void setPrdcTypeCd(String prdcTypeCd) {
        this.prdcTypeCd = prdcTypeCd;
    }

    public String getPrdcSeqNo() {
        return prdcSeqNo;
    }

    public void setPrdcSeqNo(String prdcSeqNo) {
        this.prdcSeqNo = prdcSeqNo;
    }

    public String getFtrNewParam() {
        return ftrNewParam;
    }

    public void setFtrNewParam(String ftrNewParam) {
        this.ftrNewParam = ftrNewParam;
    }
}
