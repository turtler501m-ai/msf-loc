package com.ktmmobile.msf.formComm.dto;

/**
 * 서비스 변경 이력 DTO.
 * ASIS: McpServiceAlterTraceDto (mcp-api).
 * MSF_SERVICE_ALTER_TRACE 테이블 매핑.
 * (ASIS: MCP_SERVICE_ALTER_TRACE → MSF_SERVICE_ALTER_TRACE)
 */
public class McpServiceAlterTraceDto {

    private String ncn;           // 서비스계약번호(9자리)
    private String contractNum;   // 계약번호
    private long   seq;           // 일련번호
    private String globalNo;      // 글로벌번호
    private String subscriberNo;  // 전화번호(CTN)
    private String eventCode;     // 이벤트 코드 (FIN=최종)
    private String prcsMdlInd;    // 처리 모듈 구분
    private String trtmRsltSmst;  // 호출 내용 (SUCCESS)
    private String prcsSbst;      // 처리 결과 내용
    private String rsltCd;        // 처리결과코드
    private String aSocCode;      // 이전 SOC 코드
    private String tSocCode;      // 이후 SOC 코드
    private int    aSocAmnt;      // 변경전 기본료
    private int    tSocAmnt;      // 변경후 기본료
    private String parameter;     // 인자값
    private String accessIp;      // 접속 IP
    private String accessUrl;     // Referer URL
    private String userId;        // 사용자 ID
    private String chgType;       // 즉시:I, 예약:R
    private String succYn;        // 성공여부 Y/N
    private String procMemo;      // 처리 메모
    private String procYn;        // 처리여부
    private String procId;        // 처리자

    public String getNcn() { return ncn; }
    public void setNcn(String v) { this.ncn = v; }
    public String getContractNum() { return contractNum; }
    public void setContractNum(String v) { this.contractNum = v; }
    public long getSeq() { return seq; }
    public void setSeq(long v) { this.seq = v; }
    public String getGlobalNo() { return globalNo; }
    public void setGlobalNo(String v) { this.globalNo = v; }
    public String getSubscriberNo() { return subscriberNo; }
    public void setSubscriberNo(String v) { this.subscriberNo = v; }
    public String getEventCode() { return eventCode; }
    public void setEventCode(String v) { this.eventCode = v; }
    public String getPrcsMdlInd() { return prcsMdlInd; }
    public void setPrcsMdlInd(String v) { this.prcsMdlInd = v; }
    public String getTrtmRsltSmst() { return trtmRsltSmst; }
    public void setTrtmRsltSmst(String v) { this.trtmRsltSmst = v; }
    public String getPrcsSbst() { return prcsSbst; }
    public void setPrcsSbst(String v) { this.prcsSbst = v; }
    public String getRsltCd() { return rsltCd; }
    public void setRsltCd(String v) { this.rsltCd = v; }
    public String getASocCode() { return aSocCode; }
    public void setASocCode(String v) { this.aSocCode = v; }
    public String getTSocCode() { return tSocCode; }
    public void setTSocCode(String v) { this.tSocCode = v; }
    public int getASocAmnt() { return aSocAmnt; }
    public void setASocAmnt(int v) { this.aSocAmnt = v; }
    public int getTSocAmnt() { return tSocAmnt; }
    public void setTSocAmnt(int v) { this.tSocAmnt = v; }
    public String getParameter() { return parameter; }
    public void setParameter(String v) { this.parameter = v; }
    public String getAccessIp() { return accessIp; }
    public void setAccessIp(String v) { this.accessIp = v; }
    public String getAccessUrl() { return accessUrl; }
    public void setAccessUrl(String v) { this.accessUrl = v; }
    public String getUserId() { return userId; }
    public void setUserId(String v) { this.userId = v; }
    public String getChgType() { return chgType; }
    public void setChgType(String v) { this.chgType = v; }
    public String getSuccYn() { return succYn; }
    public void setSuccYn(String v) { this.succYn = v; }
    public String getProcMemo() { return procMemo; }
    public void setProcMemo(String v) { this.procMemo = v; }
    public String getProcYn() { return procYn; }
    public void setProcYn(String v) { this.procYn = v; }
    public String getProcId() { return procId; }
    public void setProcId(String v) { this.procId = v; }
}
