package com.ktmmobile.mcp.rate.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class McpServiceAlterTraceDto implements Serializable {


    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/** New 계약번호 */
    private String ncn;
    /** 계약번호 */
    private String contractNum;

    /** 일련번호 */
    private long seq;
    /** 전화번호 CTN */
    private String subscriberNo;
    /** 이벤트 코드 */
    private String eventCode;
    /** 처리 모듈 구분 */
    private String prcsMdlInd;
    /** 호출 내용 */
    private String trtmRsltSmst;
    /** 처리 결과 내용 */
    private String prcsSbst;
    /** 처리결과코드 */
    private String rsltCd;
    /** 이전 코드 값 */
    private String aSocCode;
    /** 이후 코드 값 */
    private String tSocCode;
    /** 인자값 설정값 */
    private String parameter;
    /** 접속 IP */
    private String accessIp;
    /** REFERER URL */
    private String accessUrl;
    /** 아이디 */
    private String userId;
    /** mplatform 연동 globalNo */
    private String globalNo;

    /** 변경전 요금제 할인 월정액 */
    private int aSocAmnt = 0 ;
    /** 변경후 요금제 할인 월정액 */
    private int tSocAmnt = 0 ;

    /**SUCC_YN  성공여부  ==> 성공시 : Y , 실패시 : N */
    private String succYn ;

    private String chgType  ; //--CHG_TYPE (즉시 : I , 예약 : R)

    /** 메모 */
    private String procMemo ;

    /** 처리여부 */
    private String procYn;

    /** 처리자 ID */
    private String procId ;

    /** 처리자 일시 */
    private Timestamp procDate ;

    public String getNcn() {
        return ncn;
    }
    public void setNcn(String ncn) {
        this.ncn = ncn;
    }
    public long getSeq() {
        return seq;
    }
    public void setSeq(long seq) {
        this.seq = seq;
    }
    public String getSubscriberNo() {
        return subscriberNo;
    }
    public void setSubscriberNo(String subscriberNo) {
        this.subscriberNo = subscriberNo;
    }
    public String getEventCode() {
        return eventCode;
    }
    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }
    public String getPrcsMdlInd() {
        return prcsMdlInd;
    }
    public void setPrcsMdlInd(String prcsMdlInd) {
        this.prcsMdlInd = prcsMdlInd;
    }
    public String getTrtmRsltSmst() {
        return trtmRsltSmst;
    }
    public void setTrtmRsltSmst(String trtmRsltSmst) {
        this.trtmRsltSmst = trtmRsltSmst;
    }
    public String getPrcsSbst() {
        return prcsSbst;
    }
    public void setPrcsSbst(String prcsSbst) {
        this.prcsSbst = prcsSbst;
    }
    public String getRsltCd() {
        return rsltCd;
    }
    public void setRsltCd(String rsltCd) {
        this.rsltCd = rsltCd;
    }
    public String getaSocCode() {
        return aSocCode;
    }
    public void setaSocCode(String aSocCode) {
        this.aSocCode = aSocCode;
    }
    public String gettSocCode() {
        return tSocCode;
    }
    public void settSocCode(String tSocCode) {
        this.tSocCode = tSocCode;
    }
    public String getParameter() {
        return parameter;
    }
    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
    public String getAccessIp() {
        return accessIp;
    }
    public void setAccessIp(String accessIp) {
        this.accessIp = accessIp;
    }
    public String getAccessUrl() {
        return accessUrl;
    }
    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getGlobalNo() {
        return globalNo;
    }
    public void setGlobalNo(String globalNo) {
        this.globalNo = globalNo;
    }
    public String getContractNum() {
        return contractNum;
    }
    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public int getaSocAmnt() {
        return aSocAmnt;
    }
    public void setaSocAmnt(int aSocAmnt) {
        this.aSocAmnt = aSocAmnt;
    }
    public int gettSocAmnt() {
        return tSocAmnt;
    }
    public void settSocAmnt(int tSocAmnt) {
        this.tSocAmnt = tSocAmnt;
    }
    public String getSuccYn() {
        return succYn;
    }
    public void setSuccYn(String succYn) {
        this.succYn = succYn;
    }
    public String getChgType() {
        return chgType;
    }
    public void setChgType(String chgType) {
        this.chgType = chgType;
    }

    public String getProcMemo() {
        return procMemo;
    }

    public void setProcMemo(String procMemo) {
        this.procMemo = procMemo;
    }

    public String getProcYn() {
        return procYn;
    }

    public void setProcYn(String procYn) {
        this.procYn = procYn;
    }

    public String getProcId() {
        return procId;
    }

    public void setProcId(String procId) {
        this.procId = procId;
    }

    public Timestamp getProcDate() {
        return procDate;
    }

    public void setProcDate(Timestamp procDate) {
        this.procDate = procDate;
    }
}
