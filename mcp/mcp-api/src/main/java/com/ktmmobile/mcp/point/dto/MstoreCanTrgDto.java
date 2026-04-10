package com.ktmmobile.mcp.point.dto;

import java.io.Serializable;

public class MstoreCanTrgDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String canTrgSeq;       //시퀀스
    private String contractNum;     //계약번호
    private String customerId;      //고객아이디
    private String selfCstmrCi;     //회원 ci값
    private String userName;        //회원 이름
    private String chnCd;           //채널 코드 (공통코드 MSTORECHNCD)

    public String getCanTrgSeq() {
        return canTrgSeq;
    }

    public void setCanTrgSeq(String canTrgSeq) {
        this.canTrgSeq = canTrgSeq;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getSelfCstmrCi() {
        return selfCstmrCi;
    }

    public void setSelfCstmrCi(String selfCstmrCi) {
        this.selfCstmrCi = selfCstmrCi;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getChnCd() {
        return chnCd;
    }

    public void setChnCd(String chnCd) {
        this.chnCd = chnCd;
    }
}
