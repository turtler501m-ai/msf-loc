package com.ktmmobile.mcp.common.mplatform.dto;

public abstract class BaseRequest implements EventRequest {
    private String appEventCd;

    private String ncn;
    private String ctn;
    private String custId;

    private String userid;
    private String ip;
    private String url;
    private String mdlInd;

    public String getAppEventCd() {
        return appEventCd;
    }

    public void setAppEventCd(String appEventCd) {
        this.appEventCd = appEventCd;
    }

    public String getNcn() {
        return ncn;
    }

    public void setNcn(String ncn) {
        this.ncn = ncn;
    }

    public String getCtn() {
        return ctn;
    }

    public void setCtn(String ctn) {
        this.ctn = ctn;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMdlInd() {
        return mdlInd;
    }

    public void setMdlInd(String mdlInd) {
        this.mdlInd = mdlInd;
    }
}
