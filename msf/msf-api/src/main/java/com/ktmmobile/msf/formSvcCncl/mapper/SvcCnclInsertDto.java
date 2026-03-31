package com.ktmmobile.msf.formSvcCncl.mapper;

/**
 * 서비스해지 신청서 DB 저장 DTO (Mapper 내부용).
 * 대상 테이블: MSF_REQUEST_CANCEL (SQ_REQUEST_KEY 시퀀스, REQUEST_KEY PK)
 */
public class SvcCnclInsertDto {

    /** 가입신청키 (SQ_REQUEST_KEY) */
    private Long requestKey;
    /** 고객명 */
    private String cstmrNm;
    /** 고객유형코드 (NA/NM/FN/FNM/JP/PP) — NOT NULL */
    private String cstmrTypeCd;
    /** 해지 휴대폰번호 (숫자만) */
    private String cancelMobileNo;
    /** 계약번호 (NCN) */
    private String contractNum;
    /** 매니저코드 — NOT NULL, TOBE 미사용 시 '0' 고정 */
    private String managerCd;
    /** 대리점코드 — NOT NULL */
    private String agentCd;
    /** 업무구분유형코드 — NOT NULL, 'CC' (Cancel Consult) 고정 */
    private String operTypeCd;
    /** 해지 후 연락수단코드 — NOT NULL, 'E'(이메일)/'P'(우편) */
    private String receiveWayCd;
    /** 연락받을 번호 */
    private String receiveMobileNo;
    /** 해지 후 사용 통신사 코드 (CANCEL_USE_COMPANY_CD) — 사용여부 매핑 */
    private String cancelUseCompanyCd;
    /** 사용요금 (PAY_AMT) */
    private Long payAmt;
    /** 위약금 (PNLT_AMT) */
    private Long pnltAmt;
    /** 최종정산요금 (LAST_SUM_AMT) */
    private Long lastSumAmt;
    /** 잔여분할개월수 */
    private String instamtMnthCnt;
    /** 잔여분할금액 */
    private Long instamtMnthAmt;
    /** 혜택소멸동의여부 — 동의 후 저장하므로 'Y' 고정 */
    private String benefitAgreeYn;
    /** 메모 */
    private String memo;
    /** 등록자ID */
    private String regstId;
    /** 처리코드 — 'RC'(접수) 고정 */
    private String procCd;

    public Long getRequestKey() { return requestKey; }
    public void setRequestKey(Long requestKey) { this.requestKey = requestKey; }
    public String getCstmrNm() { return cstmrNm; }
    public void setCstmrNm(String cstmrNm) { this.cstmrNm = cstmrNm; }
    public String getCstmrTypeCd() { return cstmrTypeCd; }
    public void setCstmrTypeCd(String cstmrTypeCd) { this.cstmrTypeCd = cstmrTypeCd; }
    public String getCancelMobileNo() { return cancelMobileNo; }
    public void setCancelMobileNo(String cancelMobileNo) { this.cancelMobileNo = cancelMobileNo; }
    public String getContractNum() { return contractNum; }
    public void setContractNum(String contractNum) { this.contractNum = contractNum; }
    public String getManagerCd() { return managerCd; }
    public void setManagerCd(String managerCd) { this.managerCd = managerCd; }
    public String getAgentCd() { return agentCd; }
    public void setAgentCd(String agentCd) { this.agentCd = agentCd; }
    public String getOperTypeCd() { return operTypeCd; }
    public void setOperTypeCd(String operTypeCd) { this.operTypeCd = operTypeCd; }
    public String getReceiveWayCd() { return receiveWayCd; }
    public void setReceiveWayCd(String receiveWayCd) { this.receiveWayCd = receiveWayCd; }
    public String getReceiveMobileNo() { return receiveMobileNo; }
    public void setReceiveMobileNo(String receiveMobileNo) { this.receiveMobileNo = receiveMobileNo; }
    public String getCancelUseCompanyCd() { return cancelUseCompanyCd; }
    public void setCancelUseCompanyCd(String cancelUseCompanyCd) { this.cancelUseCompanyCd = cancelUseCompanyCd; }
    public Long getPayAmt() { return payAmt; }
    public void setPayAmt(Long payAmt) { this.payAmt = payAmt; }
    public Long getPnltAmt() { return pnltAmt; }
    public void setPnltAmt(Long pnltAmt) { this.pnltAmt = pnltAmt; }
    public Long getLastSumAmt() { return lastSumAmt; }
    public void setLastSumAmt(Long lastSumAmt) { this.lastSumAmt = lastSumAmt; }
    public String getInstamtMnthCnt() { return instamtMnthCnt; }
    public void setInstamtMnthCnt(String instamtMnthCnt) { this.instamtMnthCnt = instamtMnthCnt; }
    public Long getInstamtMnthAmt() { return instamtMnthAmt; }
    public void setInstamtMnthAmt(Long instamtMnthAmt) { this.instamtMnthAmt = instamtMnthAmt; }
    public String getBenefitAgreeYn() { return benefitAgreeYn; }
    public void setBenefitAgreeYn(String benefitAgreeYn) { this.benefitAgreeYn = benefitAgreeYn; }
    public String getMemo() { return memo; }
    public void setMemo(String memo) { this.memo = memo; }
    public String getRegstId() { return regstId; }
    public void setRegstId(String regstId) { this.regstId = regstId; }
    public String getProcCd() { return procCd; }
    public void setProcCd(String procCd) { this.procCd = procCd; }
}
