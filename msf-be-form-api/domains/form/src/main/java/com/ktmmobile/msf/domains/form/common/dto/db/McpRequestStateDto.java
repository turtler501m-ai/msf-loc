package com.ktmmobile.msf.domains.form.common.dto.db;

import java.io.Serializable;
import java.util.Date;


/**
 * <pre>
 * 프로젝트 : kt M mobile
 * 파일명   : McpRequestStateDto.java
 * 날짜     : 2016. 1. 18. 오후 3:09:10
 * 작성자   : papier
 * 설명     : 진행상태 테이블
 * </pre>
 */
public class McpRequestStateDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 가입진행_키 */
    private long requestStateKey;
    /** 예약번호 */
    private String resNo;
    /** 가입신청_키 */
    private long requestKey;

    /** 가입진행_코드 */
    private String requestStateCode;
    /** 송장번호 */
    private String dlvryNo;
    /** 개통번호 */
    private String openNo;
    /** 메모 */
    private String memo;
    /** 등록일시 */
    private Date sysRdate;
    /** 등록자아이피 */
    private String rip;
    /** 등록자아이디 */
    private String rid;
    /** 화면표시_여부 */
    private String viewFlag;

    private String tbCd;

    public long getRequestStateKey() {
        return requestStateKey;
    }

    public void setRequestStateKey(long requestStateKey) {
        this.requestStateKey = requestStateKey;
    }

    public String getResNo() {
        return resNo;
    }

    public void setResNo(String resNo) {
        this.resNo = resNo;
    }

    public long getRequestKey() {
        return requestKey;
    }

    public void setRequestKey(long requestKey) {
        this.requestKey = requestKey;
    }

    public String getRequestStateCode() {
        return requestStateCode;
    }

    public void setRequestStateCode(String requestStateCode) {
        this.requestStateCode = requestStateCode;
    }

    public String getDlvryNo() {
        return dlvryNo;
    }

    public void setDlvryNo(String dlvryNo) {
        this.dlvryNo = dlvryNo;
    }

    public String getOpenNo() {
        return openNo;
    }

    public void setOpenNo(String openNo) {
        this.openNo = openNo;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getSysRdate() {
        return sysRdate;
    }

    public void setSysRdate(Date sysRdate) {
        this.sysRdate = sysRdate;
    }

    public String getRip() {
        return rip;
    }

    public void setRip(String rip) {
        this.rip = rip;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getViewFlag() {
        return viewFlag;
    }

    public void setViewFlag(String viewFlag) {
        this.viewFlag = viewFlag;
    }

    public String getTbCd() {
        return tbCd;
    }

    public void setTbCd(String tbCd) {
        this.tbCd = tbCd;
    }



}
