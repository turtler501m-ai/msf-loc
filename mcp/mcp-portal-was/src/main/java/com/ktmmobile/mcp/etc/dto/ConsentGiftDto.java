package com.ktmmobile.mcp.etc.dto;

import java.io.Serializable;
import java.util.Date;

public class ConsentGiftDto implements Serializable{

    private static final long serialVersionUID = 1L;


    //MSP_TAX_SMS_LIST@DL_MSP.

    //MMS 식별 값
    private int taxNo;
    //발송 시간
    private String requestTime;
    //주소 사용여부
    private String addressYn;

    //MSP_TAX_SMS_SEND_LIST@DL_MSP

    //고객명
    private String userNm;
    //주민등록번호
    private String userSsn;
    //휴대폰번호
    private String telNO;
    //회신일자
    private String taxRecvTime;
    //우편번호
    private String taxAdrZip;
    //주소
    private String taxAdrPrimaryLn;
    //상세주소
    private String taxAdrSecondaryLn;
    //계약번호
    private String contractNum;

    public int getTaxNo() {
        return taxNo;
    }
    public void setTaxNo(int taxNo) {
        this.taxNo = taxNo;
    }
    public String getRequestTime() {
        return requestTime;
    }
    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }
    public String getAddressYn() {
        return addressYn;
    }
    public void setAddressYn(String addressYn) {
        this.addressYn = addressYn;
    }
    public String getUserNm() {
        return userNm;
    }
    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }
    public String getUserSsn() {
        return userSsn;
    }
    public void setUserSsn(String userSsn) {
        this.userSsn = userSsn;
    }
    public String getTelNO() {
        return telNO;
    }
    public void setTelNO(String telNO) {
        this.telNO = telNO;
    }
    public String getTaxRecvTime() {
        return taxRecvTime;
    }
    public void setTaxRecvTime(String taxRecvTime) {
        this.taxRecvTime = taxRecvTime;
    }
    public String getTaxAdrZip() {
        return taxAdrZip;
    }
    public void setTaxAdrZip(String taxAdrZip) {
        this.taxAdrZip = taxAdrZip;
    }
    public String getTaxAdrPrimaryLn() {
        return taxAdrPrimaryLn;
    }
    public void setTaxAdrPrimaryLn(String taxAdrPrimaryLn) {
        this.taxAdrPrimaryLn = taxAdrPrimaryLn;
    }
    public String getTaxAdrSecondaryLn() {
        return taxAdrSecondaryLn;
    }
    public void setTaxAdrSecondaryLn(String taxAdrSecondaryLn) {
        this.taxAdrSecondaryLn = taxAdrSecondaryLn;
    }
    public String getContractNum() {
        return contractNum;
    }
    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

}
