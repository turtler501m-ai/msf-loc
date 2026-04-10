package com.ktmmobile.mcp.retention.dto;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;


/**
 * @Class Name : retentionNotice
 * @Description : 약정만료 알림
 *
 * @author :
 * @Create Date :
 */
public class RetentionDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String customerNm;
    private String mobileNo;
    private String expiryDate;
    /** 사용자ID */
    private String userId = "";
    private String message;
    private String startDate;
    private String cretIp;
    private String amdIp;

    /** 본인인증한  CI 정보 */
    private String cstmrCi;
    /** 현재이용중인통신사(MB0015)  */
    private String mobileCarrierCd;
    /** 현재이용중인 휴대폰 브랜드(MB0016) */
    private String mobileCompanyCd;
    /** 사용중인 휴대폰 기종 */
    private String mobileModelNm;
    /** 인증정보 */
    private String authInfo;

    /** 고객이 입력한 생년월일*/
    private String birthDate;


    public String getCustomerNm() {
        return customerNm;
    }

    public void setCustomerNm(String customerNm) {
        this.customerNm = customerNm;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getUserId() {
        if (StringUtils.isBlank(userId)) {
            return "";
        } else {
            return userId;
        }
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getCretIp() {
        return cretIp;
    }

    public void setCretIp(String cretIp) {
        this.cretIp = cretIp;
    }

    public String getAmdIp() {
        return amdIp;
    }

    public void setAmdIp(String amdIp) {
        this.amdIp = amdIp;
    }

    public String getCstmrCi() {
        return cstmrCi;
    }

    public void setCstmrCi(String cstmrCi) {
        this.cstmrCi = cstmrCi;
    }

    public String getMobileCarrierCd() {
        return mobileCarrierCd;
    }

    public void setMobileCarrierCd(String mobileCarrierCd) {
        this.mobileCarrierCd = mobileCarrierCd;
    }

    public String getMobileCompanyCd() {
        return mobileCompanyCd;
    }

    public void setMobileCompanyCd(String mobileCompanyCd) {
        this.mobileCompanyCd = mobileCompanyCd;
    }

    public String getMobileModelNm() {
        return mobileModelNm;
    }

    public void setMobileModelNm(String mobileModelNm) {
        this.mobileModelNm = mobileModelNm;
    }

    public String getAuthInfo() {
        return authInfo;
    }

    public void setAuthInfo(String authInfo) {
        this.authInfo = authInfo;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }









}
