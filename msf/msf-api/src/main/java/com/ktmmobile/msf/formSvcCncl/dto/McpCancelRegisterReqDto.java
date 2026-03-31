package com.ktmmobile.msf.formSvcCncl.dto;

/**
 * M포탈 서비스해지 적재 API 요청 DTO.
 * POST {mcp.portal.url}/mcp/api/cancel/msf-register
 */
public class McpCancelRegisterReqDto {

    /** MSF REQUEST_KEY (M포탈 SOURCE_KEY로 저장됨) */
    private String sourceKey;
    /** 계약번호 */
    private String contractNum;
    /** 해지 대상 휴대폰번호 */
    private String cancelMobileNo;
    /** 고객명 */
    private String cstmrName;
    /** 고객유형코드 */
    private String cstmrType;
    /** 수령 번호 */
    private String receiveMobileNo;
    /** 수령 방법 코드 (E/P) */
    private String receiveWayCd;
    /** 사용요금 */
    private Long payAmt;
    /** 위약금 */
    private Long pnltAmt;
    /** 최종정산금 */
    private Long lastSumAmt;
    /** 등록자 ID */
    private String regstId;

    public String getSourceKey() { return sourceKey; }
    public void setSourceKey(String sourceKey) { this.sourceKey = sourceKey; }
    public String getContractNum() { return contractNum; }
    public void setContractNum(String contractNum) { this.contractNum = contractNum; }
    public String getCancelMobileNo() { return cancelMobileNo; }
    public void setCancelMobileNo(String cancelMobileNo) { this.cancelMobileNo = cancelMobileNo; }
    public String getCstmrName() { return cstmrName; }
    public void setCstmrName(String cstmrName) { this.cstmrName = cstmrName; }
    public String getCstmrType() { return cstmrType; }
    public void setCstmrType(String cstmrType) { this.cstmrType = cstmrType; }
    public String getReceiveMobileNo() { return receiveMobileNo; }
    public void setReceiveMobileNo(String receiveMobileNo) { this.receiveMobileNo = receiveMobileNo; }
    public String getReceiveWayCd() { return receiveWayCd; }
    public void setReceiveWayCd(String receiveWayCd) { this.receiveWayCd = receiveWayCd; }
    public Long getPayAmt() { return payAmt; }
    public void setPayAmt(Long payAmt) { this.payAmt = payAmt; }
    public Long getPnltAmt() { return pnltAmt; }
    public void setPnltAmt(Long pnltAmt) { this.pnltAmt = pnltAmt; }
    public Long getLastSumAmt() { return lastSumAmt; }
    public void setLastSumAmt(Long lastSumAmt) { this.lastSumAmt = lastSumAmt; }
    public String getRegstId() { return regstId; }
    public void setRegstId(String regstId) { this.regstId = regstId; }
}
