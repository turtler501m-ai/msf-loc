package com.ktmmobile.mcp.point.dto;

import java.io.Serializable;

public class MstoreCanTrgDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String canTrgSeq;       // 시퀀스
    private String contractNum;     // 계약번호
    private String customerId;      // 고객아이디
    private String chnCd;           // 탈퇴/해지채널 코드 (공통코드 MSTORECHNCD)
    private String procYn;          // 연동 완료 여부 (Y/N)
    private String userId;          // 회원아이디

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

    public String getProcYn() {
        return procYn;
    }

    public void setProcYn(String procYn) {
        this.procYn = procYn;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
