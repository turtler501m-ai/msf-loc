package com.ktmmobile.msf.formSvcCncl.dto;

/**
 * 서비스해지 신청서 상세 조회 응답 VO.
 * GET /api/v1/cancel/detail/{requestKey}
 * M전산이 SOURCE_CD='MSF' 건 처리 시 MSF 데이터로 계약정보 세팅에 활용.
 */
public class SvcCnclDetailResVO {

    private boolean success;
    private String message;

    private Long requestKey;
    private String cancelMobileNo;
    private String contractNum;
    private String cstmrNm;
    private String cstmrTypeCd;
    /** 사용요금 (PAY_AMT) */
    private Long payAmt;
    /** 위약금 (PNLT_AMT) */
    private Long pnltAmt;
    /** 최종정산금 (LAST_SUM_AMT) */
    private Long lastSumAmt;
    /** 해지 후 사용 통신사 코드 (CANCEL_USE_COMPANY_CD) */
    private String cancelUseCd;
    /** 처리 상태 코드 (RC/CP/BK) */
    private String procCd;
    /** 등록일시 */
    private String regDt;
    /** 대리점코드 */
    private String agentCd;
    /** SCAN 서버 스캔 ID */
    private String scanId;
    /** 서식지 생성 여부 */
    private String appFormYn;
    /** M포탈 고객요청 시퀀스 (적재 완료 시 RES_NO) */
    private String mcpCustReqSeq;

    public static SvcCnclDetailResVO fail(String message) {
        SvcCnclDetailResVO v = new SvcCnclDetailResVO();
        v.success = false;
        v.message = message;
        return v;
    }

    public boolean isSuccess()                              { return success; }
    public void setSuccess(boolean success)                 { this.success = success; }
    public String getMessage()                              { return message; }
    public void setMessage(String message)                  { this.message = message; }
    public Long getRequestKey()                             { return requestKey; }
    public void setRequestKey(Long requestKey)              { this.requestKey = requestKey; }
    public String getCancelMobileNo()                       { return cancelMobileNo; }
    public void setCancelMobileNo(String v)                 { this.cancelMobileNo = v; }
    public String getContractNum()                          { return contractNum; }
    public void setContractNum(String v)                    { this.contractNum = v; }
    public String getCstmrNm()                              { return cstmrNm; }
    public void setCstmrNm(String v)                        { this.cstmrNm = v; }
    public String getCstmrTypeCd()                          { return cstmrTypeCd; }
    public void setCstmrTypeCd(String v)                    { this.cstmrTypeCd = v; }
    public Long getPayAmt()                                 { return payAmt; }
    public void setPayAmt(Long v)                           { this.payAmt = v; }
    public Long getPnltAmt()                                { return pnltAmt; }
    public void setPnltAmt(Long v)                          { this.pnltAmt = v; }
    public Long getLastSumAmt()                             { return lastSumAmt; }
    public void setLastSumAmt(Long v)                       { this.lastSumAmt = v; }
    public String getCancelUseCd()                          { return cancelUseCd; }
    public void setCancelUseCd(String v)                    { this.cancelUseCd = v; }
    public String getProcCd()                               { return procCd; }
    public void setProcCd(String v)                         { this.procCd = v; }
    public String getRegDt()                                { return regDt; }
    public void setRegDt(String v)                          { this.regDt = v; }
    public String getAgentCd()                              { return agentCd; }
    public void setAgentCd(String v)                        { this.agentCd = v; }
    public String getScanId()                               { return scanId; }
    public void setScanId(String v)                         { this.scanId = v; }
    public String getAppFormYn()                            { return appFormYn; }
    public void setAppFormYn(String v)                      { this.appFormYn = v; }
    public String getMcpCustReqSeq()                        { return mcpCustReqSeq; }
    public void setMcpCustReqSeq(String v)                  { this.mcpCustReqSeq = v; }
}
