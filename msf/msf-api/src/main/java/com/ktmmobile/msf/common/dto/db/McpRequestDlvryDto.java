package com.ktmmobile.msf.common.dto.db;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 프로젝트 : kt M mobile
 * 파일명   : McpRequestDlvryDto.java
 * 날짜     : 2016. 1. 18. 오후 1:43:10
 * 작성자   : papier
 * 설명     : 가입신청_배송정보 테이블(MCP_REQUSET_DLVRY)
 * </pre>
 */
public class McpRequestDlvryDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 가입신청_키 */
    private long requestKey;
    /** 배송정보_이름 */
    private String dlvryName;
    /** 배송정보_전화번호_앞자리 */
    private String dlvryTelFn;
    /** 배송정보_전화번호_중간자리 */
    private String dlvryTelMn;
    /** 배송정보_전화번호_뒷자리 */
    private String dlvryTelRn;
    /** 배송정보_휴대폰번호_앞자리 */
    private String dlvryMobileFn;
    /** 배송정보_휴대폰번호_중간자리 */
    private String dlvryMobileMn;
    /** 배송정보_휴대폰번호_뒷자리 */
    private String dlvryMobileRn;
    /** 배송정보_우편번호 */
    private String dlvryPost;
    /** 배송정보_주소 */
    private String dlvryAddr;
    /** 배송정보_상세주소 */
    private String dlvryAddrDtl;
    /** 배송정보_법정동주소 */
    private String dlvryAddrBjd;
    /** 배송정보_요청사항 */
    private String dlvryMemo;
    /** 등록일시 */
    private Date sysRdate;
    private String tbCd;
    public long getRequestKey() {
        return requestKey;
    }
    public void setRequestKey(long requestKey) {
        this.requestKey = requestKey;
    }
    public String getDlvryName() {
        return dlvryName;
    }
    public void setDlvryName(String dlvryName) {
        this.dlvryName = dlvryName;
    }
    public String getDlvryTelFn() {
        return dlvryTelFn;
    }
    public void setDlvryTelFn(String dlvryTelFn) {
        this.dlvryTelFn = dlvryTelFn;
    }
    public String getDlvryTelMn() {
        return dlvryTelMn;
    }
    public void setDlvryTelMn(String dlvryTelMn) {
        this.dlvryTelMn = dlvryTelMn;
    }
    public String getDlvryTelRn() {
        return dlvryTelRn;
    }
    public void setDlvryTelRn(String dlvryTelRn) {
        this.dlvryTelRn = dlvryTelRn;
    }
    public String getDlvryMobileFn() {
        return dlvryMobileFn;
    }
    public void setDlvryMobileFn(String dlvryMobileFn) {
        this.dlvryMobileFn = dlvryMobileFn;
    }
    public String getDlvryMobileMn() {
        return dlvryMobileMn;
    }
    public void setDlvryMobileMn(String dlvryMobileMn) {
        this.dlvryMobileMn = dlvryMobileMn;
    }
    public String getDlvryMobileRn() {
        return dlvryMobileRn;
    }
    public void setDlvryMobileRn(String dlvryMobileRn) {
        this.dlvryMobileRn = dlvryMobileRn;
    }
    public String getDlvryPost() {
        return dlvryPost;
    }
    public void setDlvryPost(String dlvryPost) {
        this.dlvryPost = dlvryPost;
    }
    public String getDlvryAddr() {
        return dlvryAddr;
    }
    public void setDlvryAddr(String dlvryAddr) {
        this.dlvryAddr = dlvryAddr;
    }
    public String getDlvryAddrDtl() {
        return dlvryAddrDtl;
    }
    public void setDlvryAddrDtl(String dlvryAddrDtl) {
        this.dlvryAddrDtl = dlvryAddrDtl;
    }
    public String getDlvryAddrBjd() {
        return dlvryAddrBjd;
    }
    public void setDlvryAddrBjd(String dlvryAddrBjd) {
        this.dlvryAddrBjd = dlvryAddrBjd;
    }
    public String getDlvryMemo() {
        return dlvryMemo;
    }
    public void setDlvryMemo(String dlvryMemo) {
        this.dlvryMemo = dlvryMemo;
    }
    public Date getSysRdate() {
        return sysRdate;
    }
    public void setSysRdate(Date sysRdate) {
        this.sysRdate = sysRdate;
    }
    public String getTbCd() {
        return tbCd;
    }
    public void setTbCd(String tbCd) {
        this.tbCd = tbCd;
    }



}
