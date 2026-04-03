package com.ktmmobile.msf.system.common.legacy.point.dto;

import java.io.Serializable;

public class MstoreDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cmpyNo;          // 고객사코드
    private String empen;           // 사번 (회원 CI값)
    private String userName;        // 회원명
    private String mobileNo;        // 핸드폰번호
    private String contractNum;     // 계약번호
    private String userDivision;    // 회원구분
    private String userSsn;         // 주민번호
    private String customerId;      // customerId
    private String userId;          // M포탈 아이디
    private String termsAgreeYn;    // 약관 동의 여부
    private String customerType;    // 고객유형
    private String resNo;           // 예약번호
    private String requestKey;      // 신청정보 key
    private String lstComActvDate;  // 회선 개통일자
    private String mbrGrdCd;        // 회원등급 (정회원 100, 준회원 110, 비회원 120)

    public String getCmpyNo() {
        return cmpyNo;
    }

    public void setCmpyNo(String cmpyNo) {
        this.cmpyNo = cmpyNo;
    }

    public String getEmpen() {
        return empen;
    }

    public void setEmpen(String empen) {
        this.empen = empen;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getUserDivision() {
        return userDivision;
    }

    public void setUserDivision(String userDivision) {
        this.userDivision = userDivision;
    }

    public String getUserSsn() {
        return userSsn;
    }

    public void setUserSsn(String userSsn) {
        this.userSsn = userSsn;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTermsAgreeYn() {
        return termsAgreeYn;
    }

    public void setTermsAgreeYn(String termsAgreeYn) {
        this.termsAgreeYn = termsAgreeYn;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getResNo() {
        return resNo;
    }

    public void setResNo(String resNo) {
        this.resNo = resNo;
    }

    public String getRequestKey() {
        return requestKey;
    }

    public void setRequestKey(String requestKey) {
        this.requestKey = requestKey;
    }

    public String getLstComActvDate() {
        return lstComActvDate;
    }

    public void setLstComActvDate(String lstComActvDate) {
        this.lstComActvDate = lstComActvDate;
    }

    public String getMbrGrdCd() {
        return mbrGrdCd;
    }

    public void setMbrGrdCd(String mbrGrdCd) {
        this.mbrGrdCd = mbrGrdCd;
    }
}




