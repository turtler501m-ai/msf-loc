package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

public class JuoSubInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 가입계약번호 */
    private String contractNum;

    /** 주민등록번호 */
    private String customerSsn;

    /** 고객명 */
    private String customerLinkName;

    /** 전화번호_이전CTN */
    private String subscriberNo;

    /** 최초 개통일자 */
    private String lstComActvDate;

    /** USIM 카드 일련번호 */
    private String iccId;

    /** 기변 여부 */
    private String dvcChgYn;

    /** 성별 */
    private String gender;

    /** CUSTOMER_ID */
    private String customerId;

    private String customerType;
    /*2025-01-21 다이렉트몰 회원 가입 및 정보 변경시 본인인증 강화 */

    /** 법정대리인 Ci */
    private String legalCi;
    /* 법정대리인 주민번호 */
    private String lglAgntSsn;


    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getCustomerSsn() {
        return customerSsn;
    }

    public void setCustomerSsn(String customerSsn) {
        this.customerSsn = customerSsn;
    }

    public String getCustomerLinkName() {
        return customerLinkName;
    }

    public void setCustomerLinkName(String customerLinkName) {
        this.customerLinkName = customerLinkName;
    }

    public String getSubscriberNo() {
        return subscriberNo;
    }

    public void setSubscriberNo(String subscriberNo) {
        this.subscriberNo = subscriberNo;
    }

    public String getLstComActvDate() {
        return lstComActvDate;
    }

    public void setLstComActvDate(String lstComActvDate) {
        this.lstComActvDate = lstComActvDate;
    }

    public String getIccId() {
        return iccId;
    }

    public void setIccId(String iccId) {
        this.iccId = iccId;
    }

    public String getDvcChgYn() {
        return dvcChgYn;
    }

    public void setDvcChgYn(String dvcChgYn) {
        this.dvcChgYn = dvcChgYn;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getLegalCi() {
        return legalCi;
    }

    public void setLegalCi(String legalCi) {
        this.legalCi = legalCi;
    }

    public String getLglAgntSsn() {
        return lglAgntSsn;
    }

    public void setLglAgntSsn(String lglAgntSsn) {
        this.lglAgntSsn = lglAgntSsn;
    }
}
