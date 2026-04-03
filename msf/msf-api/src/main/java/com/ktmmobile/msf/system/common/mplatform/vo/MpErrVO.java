package com.ktmmobile.msf.system.common.mplatform.vo;

import java.io.Serializable;

public class MpErrVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String resNo;             // 예약번호
    private String prgrStatCd;        // 진행상태코드
    private String prntsContractNo;   // 모회선
    private String customerId;        // 고객 ID
    private String errType;           // 오류타입
    private String errMsg;            // 오류 메시지

    public MpErrVO() {
    }

    public MpErrVO(String resNo, String prgrStatCd) {
        this.resNo = resNo;
        this.prgrStatCd = prgrStatCd;
    }

    public String getResNo() {
        return resNo;
    }

    public void setResNo(String resNo) {
        this.resNo = resNo;
    }

    public String getPrgrStatCd() {
        return prgrStatCd;
    }

    public void setPrgrStatCd(String prgrStatCd) {
        this.prgrStatCd = prgrStatCd;
    }

    public String getPrntsContractNo() {
        return prntsContractNo;
    }

    public void setPrntsContractNo(String prntsContractNo) {
        this.prntsContractNo = prntsContractNo;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getErrType() {
        return errType;
    }

    public void setErrType(String errType) {
        this.errType = errType;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public void setErrInfo(Exception exception){
        String errType= exception.getClass().getSimpleName();
        if(errType != null && 100 < errType.length()) {
            errType = errType.substring(0, 100);
        }
        this.errType = errType;

        String errMsg = exception.getMessage();
        if(errMsg != null && 1000 < errMsg.length()){
            errMsg = errMsg.substring(0, 1000);
        }
        this.errMsg= errMsg;
    }

}
