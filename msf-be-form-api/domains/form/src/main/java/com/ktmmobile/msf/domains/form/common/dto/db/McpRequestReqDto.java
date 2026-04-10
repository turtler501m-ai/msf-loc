package com.ktmmobile.msf.domains.form.common.dto.db;

import java.io.Serializable;
import java.util.Date;


/**
 * <pre>
 * 프로젝트 : kt M mobile
 * 파일명   : McpRequestReqDto.java
 * 날짜     : 2016. 1. 18. 오후 1:57:39
 * 작성자   : papier
 * 설명     : 가입신청_청구정보 테이블(MCP_REQUEST_REQ)
 * </pre>
 */
public class McpRequestReqDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 가입신청_키 */
    private long requestKey;
    /** 신청정보_계좌이체_은행 */
    private String reqBank;
    /** 신청정보_계좌이체_예금주 */
    private String reqAccountName;
    /** 신청정보_계좌이체_예금주_주민번호 */
    private String reqAccountRrn;
    /** 신청정보_계좌이체_예금주와관계 */
    private String reqAccountRelation;
    /** 신청정보_계좌이체_계좌번호 */
    private String reqAccountNumber;
    /** 신청정보_신용카드_명의자 */
    private String reqCardName;
    /** 신청정보_신용카드_명의자_주민번호 */
    private String reqCardRrn;
    /** 신청정보_신용카드_카드사 */
    private String reqCardCompany;
    /** 신청정보_신용카드_번호 */
    private String reqCardNo;
    /** 신청정보_신용카드_유효년 */
    private String reqCardYy;
    /** 신청정보_신용카드_유효월 */
    private String reqCardMm;
    /** 신청정보_무선데이터_이용_타입 */
    private String reqWireType;
    /** 등록일시 */
    private Date sysRdate;
    /** 신청정보_타인납부_여부 */
    private String reqPayOtherFlag;
    /** 신청정보_타인납부_전화번호_앞자리 */
    private String reqPayOtherTelFn;
    /** 신청정보_타인납부_전화번호_중간자리 */
    private String reqPayOtherTelMn;
    /** 신청정보_타인납부_전화번호_끝자리 */
    private String reqPayOtherTelRn;

    public long getRequestKey() {
        return requestKey;
    }
    public void setRequestKey(long requestKey) {
        this.requestKey = requestKey;
    }
    public String getReqBank() {
        return reqBank;
    }
    public void setReqBank(String reqBank) {
        this.reqBank = reqBank;
    }
    public String getReqAccountName() {
        return reqAccountName;
    }
    public void setReqAccountName(String reqAccountName) {
        this.reqAccountName = reqAccountName;
    }
    public String getReqAccountRrn() {
        return reqAccountRrn;
    }
    public void setReqAccountRrn(String reqAccountRrn) {
        this.reqAccountRrn = reqAccountRrn;
    }
    public String getReqAccountRelation() {
        return reqAccountRelation;
    }
    public void setReqAccountRelation(String reqAccountRelation) {
        this.reqAccountRelation = reqAccountRelation;
    }
    public String getReqAccountNumber() {
        return reqAccountNumber;
    }
    public void setReqAccountNumber(String reqAccountNumber) {
        this.reqAccountNumber = reqAccountNumber;
    }
    public String getReqCardName() {
        return reqCardName;
    }
    public void setReqCardName(String reqCardName) {
        this.reqCardName = reqCardName;
    }
    public String getReqCardRrn() {
        return reqCardRrn;
    }
    public void setReqCardRrn(String reqCardRrn) {
        this.reqCardRrn = reqCardRrn;
    }
    public String getReqCardCompany() {
        return reqCardCompany;
    }
    public void setReqCardCompany(String reqCardCompany) {
        this.reqCardCompany = reqCardCompany;
    }
    public String getReqCardNo() {
        return reqCardNo;
    }
    public void setReqCardNo(String reqCardNo) {
        this.reqCardNo = reqCardNo;
    }
    public String getReqCardYy() {
        return reqCardYy;
    }
    public void setReqCardYy(String reqCardYy) {
        this.reqCardYy = reqCardYy;
    }
    public String getReqCardMm() {
        return reqCardMm;
    }
    public void setReqCardMm(String reqCardMm) {
        this.reqCardMm = reqCardMm;
    }
    public String getReqWireType() {
        return reqWireType;
    }
    public void setReqWireType(String reqWireType) {
        this.reqWireType = reqWireType;
    }
    public Date getSysRdate() {
        return sysRdate;
    }
    public void setSysRdate(Date sysRdate) {
        this.sysRdate = sysRdate;
    }
    public String getReqPayOtherFlag() {
        return reqPayOtherFlag;
    }
    public void setReqPayOtherFlag(String reqPayOtherFlag) {
        this.reqPayOtherFlag = reqPayOtherFlag;
    }
    public String getReqPayOtherTelFn() {
        return reqPayOtherTelFn;
    }
    public void setReqPayOtherTelFn(String reqPayOtherTelFn) {
        this.reqPayOtherTelFn = reqPayOtherTelFn;
    }
    public String getReqPayOtherTelMn() {
        return reqPayOtherTelMn;
    }
    public void setReqPayOtherTelMn(String reqPayOtherTelMn) {
        this.reqPayOtherTelMn = reqPayOtherTelMn;
    }
    public String getReqPayOtherTelRn() {
        return reqPayOtherTelRn;
    }
    public void setReqPayOtherTelRn(String reqPayOtherTelRn) {
        this.reqPayOtherTelRn = reqPayOtherTelRn;
    }



}
