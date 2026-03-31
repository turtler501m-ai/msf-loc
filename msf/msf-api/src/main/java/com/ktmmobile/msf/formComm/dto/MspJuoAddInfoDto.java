package com.ktmmobile.msf.formComm.dto;

/**
 * M플랫폼 약정/채널 정보 DTO.
 * ASIS: MspJuoAddInfoDto (mcp-api).
 * getEnggInfo / getChannelInfo 결과 매핑.
 */
public class MspJuoAddInfoDto {

    private String contractNum;   // 계약번호
    private String subscriberNo;  // 전화번호
    private String appEndDate;    // 약정종료일자 (YYYYMMDD)
    private String enggYn;        // 약정여부 Y/N
    private int    enggMnthCnt;   // 약정개월수
    private String appStartDd;    // 적용시작일시

    // getChannelInfo 용
    private String agentCd;       // 대리점 코드
    private String agentNm;       // 대리점 명
    private String channelCd;     // 채널 코드
    private String channelNm;     // 채널 명

    public String getContractNum() { return contractNum; }
    public void setContractNum(String v) { this.contractNum = v; }
    public String getSubscriberNo() { return subscriberNo; }
    public void setSubscriberNo(String v) { this.subscriberNo = v; }
    public String getAppEndDate() { return appEndDate; }
    public void setAppEndDate(String v) { this.appEndDate = v; }
    public String getEnggYn() { return enggYn; }
    public void setEnggYn(String v) { this.enggYn = v; }
    public int getEnggMnthCnt() { return enggMnthCnt; }
    public void setEnggMnthCnt(int v) { this.enggMnthCnt = v; }
    public String getAppStartDd() { return appStartDd; }
    public void setAppStartDd(String v) { this.appStartDd = v; }
    public String getAgentCd() { return agentCd; }
    public void setAgentCd(String v) { this.agentCd = v; }
    public String getAgentNm() { return agentNm; }
    public void setAgentNm(String v) { this.agentNm = v; }
    public String getChannelCd() { return channelCd; }
    public void setChannelCd(String v) { this.channelCd = v; }
    public String getChannelNm() { return channelNm; }
    public void setChannelNm(String v) { this.channelNm = v; }
}
