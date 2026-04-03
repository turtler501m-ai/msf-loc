package com.ktmmobile.msf.system.common.dto.db;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 프로젝트 : kt M mobile
 * 파일명   : McpRequestAgentDto.java
 * 날짜     : 2016. 1. 15. 오후 5:52:42
 * 작성자   : papier
 * 설명     : 가입신청_대리인 테이블(MCP_REQUEST_AGENT)
 * </pre>
 */
public class McpRequestAgentDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 가입신청_키 */
    private long requestKey;
    /** 미성년자_법정대리인_성명 */
    private String minorAgentName;
    /** 미성년자_법정대리인_주민등록번호 */
    private String minorAgentRrn;
    /** 미성년자_법정대리인_연락처_앞자리 */
    private String minorAgentTelFn;
    /** 미성년자_법정대리인_연락처_중간자리 */
    private String minorAgentTelMn;
    /** 미성년자_법정대리인_끝자리 */
    private String minorAgentTelRn;
    /** 미성년자_법정대리인_관계 */
    private String minorAgentRelation;
    /** 법인_대리인_성명 */
    private String jrdclAgentName;
    /** 법인_대리인_주민등록번호 */
    private String jrdclAgentRrn;
    /** 법인_대리인_연락처_앞자리 */
    private String jrdclAgentTelFn;
    /** 법인_대리인_연락처_중간자리 */
    private String jrdclAgentTelMn;
    /** 법인_대리인_연락처_끝자리 */
    private String jrdclAgentTelRn;
    /** 법정대리인_위임하는분 */
    private String entrustReqNm;
    /** 법정대리인_위임받는분 */
    private String entrustResNm;
    /** 법정대리인_위임관계 */
    private String entrustReqRelation;
    /** 법정대리인_위임_주민등록번호 */
    private String entrustResRrn;
    /** 법정대리인_위임_전화번호_앞자리 */
    private String entrustResTelFn;
    /** 법정대리인_위임_전화번호_중간자리 */
    private String entrustResTelMn;
    /** 법정대리인_위임_전화번호_뒷자리 */
    private String entrustResTelRn;
    /** 등록일시 */
    private Date sysRdate;
    /** 본인인증조회동의 */
    private String minorSelfInqryAgrmYn;
    /** 본인인증유형 */
    private String minorSelfCertType;
    /** 발급/만료일자 */
    private String minorSelfIssuExprDt;
    /** 발급번호 */
    private String minorSelfIssuNum;
    public long getRequestKey() {
        return requestKey;
    }
    public void setRequestKey(long requestKey) {
        this.requestKey = requestKey;
    }
    public String getMinorAgentName() {
        return minorAgentName;
    }
    public void setMinorAgentName(String minorAgentName) {
        this.minorAgentName = minorAgentName;
    }
    public String getMinorAgentRrn() {
        return minorAgentRrn;
    }
    public void setMinorAgentRrn(String minorAgentRrn) {
        this.minorAgentRrn = minorAgentRrn;
    }
    public String getMinorAgentTelFn() {
        return minorAgentTelFn;
    }
    public void setMinorAgentTelFn(String minorAgentTelFn) {
        this.minorAgentTelFn = minorAgentTelFn;
    }
    public String getMinorAgentTelMn() {
        return minorAgentTelMn;
    }
    public void setMinorAgentTelMn(String minorAgentTelMn) {
        this.minorAgentTelMn = minorAgentTelMn;
    }
    public String getMinorAgentTelRn() {
        return minorAgentTelRn;
    }
    public void setMinorAgentTelRn(String minorAgentTelRn) {
        this.minorAgentTelRn = minorAgentTelRn;
    }
    public String getMinorAgentRelation() {
        return minorAgentRelation;
    }
    public void setMinorAgentRelation(String minorAgentRelation) {
        this.minorAgentRelation = minorAgentRelation;
    }
    public String getJrdclAgentName() {
        return jrdclAgentName;
    }
    public void setJrdclAgentName(String jrdclAgentName) {
        this.jrdclAgentName = jrdclAgentName;
    }
    public String getJrdclAgentRrn() {
        return jrdclAgentRrn;
    }
    public void setJrdclAgentRrn(String jrdclAgentRrn) {
        this.jrdclAgentRrn = jrdclAgentRrn;
    }
    public String getJrdclAgentTelFn() {
        return jrdclAgentTelFn;
    }
    public void setJrdclAgentTelFn(String jrdclAgentTelFn) {
        this.jrdclAgentTelFn = jrdclAgentTelFn;
    }
    public String getJrdclAgentTelMn() {
        return jrdclAgentTelMn;
    }
    public void setJrdclAgentTelMn(String jrdclAgentTelMn) {
        this.jrdclAgentTelMn = jrdclAgentTelMn;
    }
    public String getJrdclAgentTelRn() {
        return jrdclAgentTelRn;
    }
    public void setJrdclAgentTelRn(String jrdclAgentTelRn) {
        this.jrdclAgentTelRn = jrdclAgentTelRn;
    }
    public String getEntrustReqNm() {
        return entrustReqNm;
    }
    public void setEntrustReqNm(String entrustReqNm) {
        this.entrustReqNm = entrustReqNm;
    }
    public String getEntrustResNm() {
        return entrustResNm;
    }
    public void setEntrustResNm(String entrustResNm) {
        this.entrustResNm = entrustResNm;
    }
    public String getEntrustReqRelation() {
        return entrustReqRelation;
    }
    public void setEntrustReqRelation(String entrustReqRelation) {
        this.entrustReqRelation = entrustReqRelation;
    }
    public String getEntrustResRrn() {
        return entrustResRrn;
    }
    public void setEntrustResRrn(String entrustResRrn) {
        this.entrustResRrn = entrustResRrn;
    }
    public String getEntrustResTelFn() {
        return entrustResTelFn;
    }
    public void setEntrustResTelFn(String entrustResTelFn) {
        this.entrustResTelFn = entrustResTelFn;
    }
    public String getEntrustResTelMn() {
        return entrustResTelMn;
    }
    public void setEntrustResTelMn(String entrustResTelMn) {
        this.entrustResTelMn = entrustResTelMn;
    }
    public String getEntrustResTelRn() {
        return entrustResTelRn;
    }
    public void setEntrustResTelRn(String entrustResTelRn) {
        this.entrustResTelRn = entrustResTelRn;
    }
    public Date getSysRdate() {
        return sysRdate;
    }
    public void setSysRdate(Date sysRdate) {
        this.sysRdate = sysRdate;
    }
    public String getMinorSelfInqryAgrmYn() {
        return minorSelfInqryAgrmYn;
    }
    public void setMinorSelfInqryAgrmYn(String minorSelfInqryAgrmYn) {
        this.minorSelfInqryAgrmYn = minorSelfInqryAgrmYn;
    }
    public String getMinorSelfCertType() {
        return minorSelfCertType;
    }
    public void setMinorSelfCertType(String minorSelfCertType) {
        this.minorSelfCertType = minorSelfCertType;
    }
    public String getMinorSelfIssuExprDt() {
        return minorSelfIssuExprDt;
    }
    public void setMinorSelfIssuExprDt(String minorSelfIssuExprDt) {
        this.minorSelfIssuExprDt = minorSelfIssuExprDt;
    }
    public String getMinorSelfIssuNum() {
        return minorSelfIssuNum;
    }
    public void setMinorSelfIssuNum(String minorSelfIssuNum) {
        this.minorSelfIssuNum = minorSelfIssuNum;
    }



}
