package com.ktmmobile.mcp.common.dto;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class NiceTryLogDto implements Serializable  {

    private static final long serialVersionUID = 1L;

    /** 순번(PK) */
    private long niceTryHistSeq =-1;

    /** 요청 일련번호 */
    private String reqSeq;

    /** 응답 일련번호 */
    private String resSeq;

    /** 본인인증 유형  N(네이버 인증서), A(PASS 인증서), T(Toss인증서) */
    private String authType;

    /** 인증성명 */
    private String name;

    /** 인증생년월일 */
    private String birthDate;

    /** CID */
    private String connInfo;

    /** REFERER */
    private String nReferer;

    /** 등록아이피 */
    private String rip;

    /** 등록일 */
    private String sysRdateDt;

    /** 등록일시 */
    private Date sysRdate;

    /** 수정일시 */
    private Date rvisnDttm;

    /** 본인인증 성공 여부  */
    private String succYn;

    /** 본인인증 최종 성공 여부 (본인인증에 사용한 정보와 고객정보 일치 여부)  */
    private String fnlSuccYn;

    /** 인증 시간 시간  */
    private long startTime ;

    public long getNiceTryHistSeq() {
        return niceTryHistSeq;
    }

    public void setNiceTryHistSeq(long niceTryHistSeq) {
        this.niceTryHistSeq = niceTryHistSeq;
    }

    public String getReqSeq() {
        return reqSeq;
    }

    public void setReqSeq(String reqSeq) {
        this.reqSeq = reqSeq;
    }

    public String getResSeq() {
        return resSeq;
    }

    public void setResSeq(String resSeq) {
        this.resSeq = resSeq;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getConnInfo() {
        return connInfo;
    }

    public void setConnInfo(String connInfo) {
        this.connInfo = connInfo;
    }

    public String getnReferer() {
        return nReferer;
    }

    public void setnReferer(String nReferer) {
        this.nReferer = nReferer;
    }

    public String getRip() {
        return rip;
    }

    public void setRip(String rip) {
        this.rip = rip;
    }

    public String getSysRdateDt() {
        return sysRdateDt;
    }

    public void setSysRdateDt(String sysRdateDt) {
        this.sysRdateDt = sysRdateDt;
    }

    public Date getSysRdate() {
        return sysRdate;
    }

    public void setSysRdate(Date sysRdate) {
        this.sysRdate = sysRdate;
    }

    public Date getRvisnDttm() {
        return rvisnDttm;
    }

    public void setRvisnDttm(Date rvisnDttm) {
        this.rvisnDttm = rvisnDttm;
    }

    public String getSuccYn() {
        return succYn;
    }

    public void setSuccYn(String succYn) {
        this.succYn = succYn;
    }

    public String getFnlSuccYn() {
        return fnlSuccYn;
    }

    public void setFnlSuccYn(String fnlSuccYn) {
        this.fnlSuccYn = fnlSuccYn;
    }

    public long getStartTime() { return startTime; }

    public void setStartTime(long startTime) { this.startTime = startTime; }

    public Date getStartTimeToDate() {

        Date renDate = null;
        if (startTime > 0) {
            renDate = new Date(startTime);
        } else {
            Calendar cal = Calendar.getInstance();
            renDate = new Date(cal.getTimeInMillis());
        }

        return  renDate ;

    }
}
