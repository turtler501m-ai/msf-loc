package com.ktis.msp.rcp.rcpMgmt.vo;

public interface EventRequest {
    void setAppEventCd(String appEventCd);
    String getAppEventCd();
    void setNcn(String ncn);
    void setCtn(String ctn);
    void setCustId(String custId);
    void setUserid(String userid);
    void setIp(String ip);
    void setUrl(String url);
    void setMdlInd(String mdlInd);
}
