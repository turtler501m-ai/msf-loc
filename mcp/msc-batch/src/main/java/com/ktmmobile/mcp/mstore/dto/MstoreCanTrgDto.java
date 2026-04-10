package com.ktmmobile.mcp.mstore.dto;

import java.io.Serializable;

public class MstoreCanTrgDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String canTrgSeq;       //시퀀스
    private String contractNum;     //계약번호
    private String customerId;      //고객아이디
    private String chnCd;           //채널 코드 (공통코드 MSTORECHNCD)
    private String evntCd;          //이벤트 코드
    private String evntTrtmNo;      //이벤트 처리 번호
    private String procTrgYn;       //탈퇴 연동 대상 (활성회원 : Y , 그외 : N)
    private String ProcHistSeq;     //MSTORE 연동 응답 로그 순번
    private String procYn;          //처리 완료 여부 (Y/N)
    private String selfCstmrCi;     //회원 ci값
    private String userName;        //회원 이름

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

    public String getChnCd() {
        return chnCd;
    }

    public void setChnCd(String chnCd) {
        this.chnCd = chnCd;
    }

    public String getEvntCd() {
        return evntCd;
    }

    public void setEvntCd(String evntCd) {
        this.evntCd = evntCd;
    }

    public String getEvntTrtmNo() {
        return evntTrtmNo;
    }

    public void setEvntTrtmNo(String evntTrtmNo) {
        this.evntTrtmNo = evntTrtmNo;
    }

    public String getProcTrgYn() {
        return procTrgYn;
    }

    public void setProcTrgYn(String procTrgYn) {
        this.procTrgYn = procTrgYn;
    }

    public String getProcHistSeq() {
        return ProcHistSeq;
    }

    public void setProcHistSeq(String procHistSeq) {
        ProcHistSeq = procHistSeq;
    }

    public String getProcYn() {
        return procYn;
    }

    public void setProcYn(String procYn) {
        this.procYn = procYn;
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
}
