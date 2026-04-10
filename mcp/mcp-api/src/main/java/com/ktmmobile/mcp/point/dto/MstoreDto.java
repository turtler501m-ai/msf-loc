package com.ktmmobile.mcp.point.dto;

import java.io.Serializable;

public class MstoreDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private String empen;           //회원 CI값
    private String userName;        //회원명
    private String mobileNo;        //회원 폰번호
    private String contractNum;     //계약번호
    private String userSsn;         //회원 주민번호
    private String userDivision;    //회원구분
    private String customerId;      //고객ID

    private String customerType;    //고객유형
    private String resNo;           //예약번호
    private String lstComActvDate;  // 회선 개통일자
    private String userId;          // M포탈 아이디


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

    public String getUserSsn() {
        return userSsn;
    }

    public void setUserSsn(String userSsn) {
        this.userSsn = userSsn;
    }

    public String getUserDivision() {
        return userDivision;
    }

    public void setUserDivision(String userDivision) {
        this.userDivision = userDivision;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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

    public String getLstComActvDate() {
        return lstComActvDate;
    }

    public void setLstComActvDate(String lstComActvDate) {
        this.lstComActvDate = lstComActvDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
