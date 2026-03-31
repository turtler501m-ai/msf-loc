package com.ktmmobile.msf.formComm.dto;

/**
 * 비회원 계약 현행화 정보 DTO.
 * ASIS: McpUserCntrMngDto 중 selectCntrListNoLogin 매핑 필드.
 * MSP_JUO_SUB_INFO@DL_MSP + MSP_JUO_CUS_INFO@DL_MSP 조인 결과.
 */
public class McpSubInfoDto {

    private String svcCntrNo;      // 계약번호(9자리 NCN)
    private String contractNum;    // 계약번호
    private String custId;         // 고객ID (CUSTOMER_ID)
    private String modelName;      // 단말기 모델명
    private String modelId;        // 단말기 모델ID
    private String cntrMobileNo;   // 전화번호 (SUBSCRIBER_NO)
    private String subStatus;      // 계약상태 (A:활성, C:해지)
    private String dobyyyymmdd;    // 생년월일
    private String userName;       // 고객명 (SUB_LINK_NAME)
    private String unUserSSn;      // 주민번호(암호화)
    private String lstComActvDate; // 최근 개통일자
    private String customerId;     // 고객ID (중복 매핑)
    private String customerType;   // 고객구분 (MSP_JUO_CUS_INFO)
    private String pppo;           // 후불구분

    public String getSvcCntrNo() { return svcCntrNo; }
    public void setSvcCntrNo(String v) { this.svcCntrNo = v; }
    public String getContractNum() { return contractNum; }
    public void setContractNum(String v) { this.contractNum = v; }
    public String getCustId() { return custId; }
    public void setCustId(String v) { this.custId = v; }
    public String getModelName() { return modelName; }
    public void setModelName(String v) { this.modelName = v; }
    public String getModelId() { return modelId; }
    public void setModelId(String v) { this.modelId = v; }
    public String getCntrMobileNo() { return cntrMobileNo; }
    public void setCntrMobileNo(String v) { this.cntrMobileNo = v; }
    public String getSubStatus() { return subStatus; }
    public void setSubStatus(String v) { this.subStatus = v; }
    public String getDobyyyymmdd() { return dobyyyymmdd; }
    public void setDobyyyymmdd(String v) { this.dobyyyymmdd = v; }
    public String getUserName() { return userName; }
    public void setUserName(String v) { this.userName = v; }
    public String getUnUserSSn() { return unUserSSn; }
    public void setUnUserSSn(String v) { this.unUserSSn = v; }
    public String getLstComActvDate() { return lstComActvDate; }
    public void setLstComActvDate(String v) { this.lstComActvDate = v; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String v) { this.customerId = v; }
    public String getCustomerType() { return customerType; }
    public void setCustomerType(String v) { this.customerType = v; }
    public String getPppo() { return pppo; }
    public void setPppo(String v) { this.pppo = v; }
}
