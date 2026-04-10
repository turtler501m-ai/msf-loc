package com.ktmmobile.mcp.mcash.dto;

import java.io.Serializable;

public class McashUserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userid;          // 포탈ID
    private String status;          // 회원상태(A:가입, C:탈퇴, S:대기)
    private String customerId;      // 고객ID
    private String contractNum;     // 계약번호
    private String svcCntrNo;       // 서비스 계약번호
    private String strtDttm;        // 시작일시
    private String endDttm;         // 종료일시

    private String mobileNo;        // 휴대폰 번호
    private String rateNm;          // 요금제명
    private String repNo;           // 대표번호
    private String birthDt;         // 생년월일
    private String denyRateYn;      // 이용불가 요금제 여부

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getSvcCntrNo() {
        return svcCntrNo;
    }

    public void setSvcCntrNo(String svcCntrNo) {
        this.svcCntrNo = svcCntrNo;
    }

    public String getStrtDttm() {
        return strtDttm;
    }

    public void setStrtDttm(String strtDttm) {
        this.strtDttm = strtDttm;
    }

    public String getEndDttm() {
        return endDttm;
    }

    public void setEndDttm(String endDttm) {
        this.endDttm = endDttm;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getRateNm() {
        return rateNm;
    }

    public void setRateNm(String rateNm) {
        this.rateNm = rateNm;
    }

    public String getRepNo() {
        return repNo;
    }

    public void setRepNo(String repNo) {
        this.repNo = repNo;
    }

    public String getBirthDt() {
        return birthDt;
    }

    public void setBirthDt(String birthDt) {
        this.birthDt = birthDt;
    }

    public String getDenyRateYn() {
        return denyRateYn;
    }

    public void setDenyRateYn(String denyRateYn) {
        this.denyRateYn = denyRateYn;
    }
}