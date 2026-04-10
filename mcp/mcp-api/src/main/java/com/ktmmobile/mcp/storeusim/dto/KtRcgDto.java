package com.ktmmobile.mcp.storeusim.dto;

import java.io.Serializable;

public class KtRcgDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String iReqType;
    private String iSubscriberNo;
    private String iReqIp;
    private String oRcgSeq;
    private String oRetCode;
    private String oRetMsg;
    public String getiReqType() {
        return iReqType;
    }
    public void setiReqType(String iReqType) {
        this.iReqType = iReqType;
    }
    public String getiSubscriberNo() {
        return iSubscriberNo;
    }
    public void setiSubscriberNo(String iSubscriberNo) {
        this.iSubscriberNo = iSubscriberNo;
    }
    public String getiReqIp() {
        return iReqIp;
    }
    public void setiReqIp(String iReqIp) {
        this.iReqIp = iReqIp;
    }
    public String getoRcgSeq() {
        return oRcgSeq;
    }
    public void setoRcgSeq(String oRcgSeq) {
        this.oRcgSeq = oRcgSeq;
    }
    public String getoRetCode() {
        return oRetCode;
    }
    public void setoRetCode(String oRetCode) {
        this.oRetCode = oRetCode;
    }
    public String getoRetMsg() {
        return oRetMsg;
    }
    public void setoRetMsg(String oRetMsg) {
        this.oRetMsg = oRetMsg;
    }


}
