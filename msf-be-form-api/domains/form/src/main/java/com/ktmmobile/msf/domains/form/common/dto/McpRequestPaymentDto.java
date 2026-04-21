package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 프로젝트 : kt M mobile
 * 파일명   : McpRequestDto.java
 * 날짜     : 2016. 1. 15. 오후 2:28:01
 * 작성자   : papier
 * 설명     : 번호이동정보 테이블(MCP_REQUEST_MOVE)
 * </pre>
 */
public class McpRequestPaymentDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 가입신청_키 */
    private long requestKey;
    private long reqAc02Amount;
    private Date sysRdate;
    private String reqAcType;
    private long reqAc01Balance;
    private String reqAc02Day;
    private long reqAc01Amount;
    public long getRequestKey() {
        return requestKey;
    }
    public void setRequestKey(long requestKey) {
        this.requestKey = requestKey;
    }
    public long getReqAc02Amount() {
        return reqAc02Amount;
    }
    public void setReqAc02Amount(long reqAc02Amount) {
        this.reqAc02Amount = reqAc02Amount;
    }
    public Date getSysRdate() {
        return sysRdate;
    }
    public void setSysRdate(Date sysRdate) {
        this.sysRdate = sysRdate;
    }
    public String getReqAcType() {
        return reqAcType;
    }
    public void setReqAcType(String reqAcType) {
        this.reqAcType = reqAcType;
    }
    public long getReqAc01Balance() {
        return reqAc01Balance;
    }
    public void setReqAc01Balance(long reqAc01Balance) {
        this.reqAc01Balance = reqAc01Balance;
    }
    public String getReqAc02Day() {
        return reqAc02Day;
    }
    public void setReqAc02Day(String reqAc02Day) {
        this.reqAc02Day = reqAc02Day;
    }
    public long getReqAc01Amount() {
        return reqAc01Amount;
    }
    public void setReqAc01Amount(long reqAc01Amount) {
        this.reqAc01Amount = reqAc01Amount;
    }



}
