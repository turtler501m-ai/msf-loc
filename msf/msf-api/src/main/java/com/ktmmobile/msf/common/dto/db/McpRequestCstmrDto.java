package com.ktmmobile.msf.common.dto.db;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 프로젝트 : kt M mobile
 * 파일명   : McpRequestCstmrDto.java
 * 날짜     : 2016. 1. 15. 오후 4:47:55
 * 작성자   : papier
 * 설명     : MCP_REQUEST_CSTMR DTO
 * </pre>
 */
public class McpRequestCstmrDto implements Serializable {
    private static final long serialVersionUID = 1L;

    //@Crypto(encryptName="DBMSEnc", fields = {"cstmrForeignerPn", "cstmrForeignerRrn", "cstmrNativeRrn", "othersPaymentRrn", "selfIssuNum"})
    /** 가입신청_키 */
    private long requestKey;
    /** 고객정보_외국인_국적 */
    private String cstmrForeignerNation;
    /** 고객정보_외국인_여권번호 */
    private String cstmrForeignerPn;
    /** 고객정보_외국인_외국인등록번호 */
    private String cstmrForeignerRrn;
    /** 고객정보_외국인_체류_시작날자 */
    private String cstmrForeignerSdate;
    /** 고객정보_외국인_체류_종료날자 */
    private String cstmrForeignerEdate;
    /** 고객정보_개인사업자_상호명 */
    private String cstmrPrivateCname;
    /** 고객정보_개인사업자_사업자등록번호 */
    private String cstmrPrivateNumber;
    /** 고객정보_법인사업자_법인명 */
    private String cstmrJuridicalCname;
    /** 고객정보_법인사업자_법인번호 */
    private String cstmrJuridicalRrn;
    /** 고객정보_법인사업자_사업자등록번호 */
    private String cstmrJuridicalNumber;
    /** 고객정보_전화번호_앞자리 */
    private String cstmrTelFn;
    /** 고객정보_전화번호_가운데자리 */
    private String cstmrTelMn;
    /** 고객정보_전화번호_끝자리 */
    private String cstmrTelRn;
    /** 고객정보_휴대폰번호_앞자리 */
    private String cstmrMobileFn;
    /** 고객정보_휴대폰번호_중간자리 */
    private String cstmrMobileMn;
    /** 고객정보_휴대폰번호_끝자리 */
    private String cstmrMobileRn;
    /** 고객정보_우편번호 */
    private String cstmrPost;
    /** 고객정보_주소 */
    private String cstmrAddr;
    /** 고객정보_상세주소 */
    private String cstmrAddrDtl;
    /** 고객정보_법정동주소 */
    private String cstmrAddrBjd;
    /** 고객정보_명세서종류 */
    private String cstmrBillSendCode;
    /** 고객정보_메일 */
    private String cstmrMail;
    /** 고객정보_메일수신_여부 */
    private String cstmrMailReceiveFlag;
    /** 고객정보_방문고객정보 */
    private String cstmrVisitType;
    /** 고객정보_연락받을번호_앞자 */
    private String cstmrReceiveTelFn;
    /** 고객정보_연락받을번호_중간자리 */
    private String cstmrReceiveTelNm;
    /** 고객정보_연락받을번호_끝자리 */
    private String cstmrReceiveTelRn;
    /** 고객정보_타인납부동의 */
    private String othersPaymentAg;
    /** 고객정보_타인납부_고객명 */
    private String othersPaymentNm;
    /** 고객정보_타인납부_주민번호 */
    private String othersPaymentRrn;
    /** 고객정보_타인납부_명의자와의관계 */
    private String othersPaymentRelation;
    /** 고객정보_타인납부_신청인 */
    private String othersPaymentRnm;
    /** 등록일시 */
    private Date sysRdate;
    /** 고객정보_고객명 */
    private String cstmrName;
    /** 고객정보_내국인_주민등록번호 */
    private String cstmrNativeRrn ;
    private String cstmrForeignerDod;
    private String cstmrForeignerBirth;
    /** 본인인증조회동의 */
    private String selfInqryAgrmYn;
    /** 본인인증유형 */
    private String selfCertType;
    /** 발급/만료일자 */
    private String selfIssuExprDt;
    /** 발급번호 */
    private String selfIssuNum;
    /** 제주항공ID */
    private String cstmrJejuId ;
    /** 본인인증한  CI 정보 */
    private String selfCstmrCi;
    /** 추천인 구분 코드 F001 01:추천 직원번호 02:제휴사 추천코드 */
    private String recommendFlagCd;
    /** 추천인정보 */
    private String recommendInfo;

    public long getRequestKey() {
        return requestKey;
    }
    public void setRequestKey(long requestKey) {
        this.requestKey = requestKey;
    }
    public String getCstmrForeignerNation() {
        return cstmrForeignerNation;
    }
    public void setCstmrForeignerNation(String cstmrForeignerNation) {
        this.cstmrForeignerNation = cstmrForeignerNation;
    }
    public String getCstmrForeignerPn() {
        return cstmrForeignerPn;
    }
    public void setCstmrForeignerPn(String cstmrForeignerPn) {
        this.cstmrForeignerPn = cstmrForeignerPn;
    }
    public String getCstmrForeignerRrn() {
        return cstmrForeignerRrn;
    }
    public void setCstmrForeignerRrn(String cstmrForeignerRrn) {
        this.cstmrForeignerRrn = cstmrForeignerRrn;
    }
    public String getCstmrForeignerSdate() {
        return cstmrForeignerSdate;
    }
    public void setCstmrForeignerSdate(String cstmrForeignerSdate) {
        this.cstmrForeignerSdate = cstmrForeignerSdate;
    }
    public String getCstmrForeignerEdate() {
        return cstmrForeignerEdate;
    }
    public void setCstmrForeignerEdate(String cstmrForeignerEdate) {
        this.cstmrForeignerEdate = cstmrForeignerEdate;
    }
    public String getCstmrPrivateCname() {
        return cstmrPrivateCname;
    }
    public void setCstmrPrivateCname(String cstmrPrivateCname) {
        this.cstmrPrivateCname = cstmrPrivateCname;
    }
    public String getCstmrPrivateNumber() {
        return cstmrPrivateNumber;
    }
    public void setCstmrPrivateNumber(String cstmrPrivateNumber) {
        this.cstmrPrivateNumber = cstmrPrivateNumber;
    }
    public String getCstmrJuridicalCname() {
        return cstmrJuridicalCname;
    }
    public void setCstmrJuridicalCname(String cstmrJuridicalCname) {
        this.cstmrJuridicalCname = cstmrJuridicalCname;
    }
    public String getCstmrJuridicalRrn() {
        return cstmrJuridicalRrn;
    }
    public void setCstmrJuridicalRrn(String cstmrJuridicalRrn) {
        this.cstmrJuridicalRrn = cstmrJuridicalRrn;
    }
    public String getCstmrJuridicalNumber() {
        return cstmrJuridicalNumber;
    }
    public void setCstmrJuridicalNumber(String cstmrJuridicalNumber) {
        this.cstmrJuridicalNumber = cstmrJuridicalNumber;
    }
    public String getCstmrTelFn() {
        return cstmrTelFn;
    }
    public void setCstmrTelFn(String cstmrTelFn) {
        this.cstmrTelFn = cstmrTelFn;
    }
    public String getCstmrTelMn() {
        return cstmrTelMn;
    }
    public void setCstmrTelMn(String cstmrTelMn) {
        this.cstmrTelMn = cstmrTelMn;
    }
    public String getCstmrTelRn() {
        return cstmrTelRn;
    }
    public void setCstmrTelRn(String cstmrTelRn) {
        this.cstmrTelRn = cstmrTelRn;
    }
    public String getCstmrMobileFn() {
        return cstmrMobileFn;
    }
    public void setCstmrMobileFn(String cstmrMobileFn) {
        this.cstmrMobileFn = cstmrMobileFn;
    }
    public String getCstmrMobileMn() {
        return cstmrMobileMn;
    }
    public void setCstmrMobileMn(String cstmrMobileMn) {
        this.cstmrMobileMn = cstmrMobileMn;
    }
    public String getCstmrMobileRn() {
        return cstmrMobileRn;
    }
    public void setCstmrMobileRn(String cstmrMobileRn) {
        this.cstmrMobileRn = cstmrMobileRn;
    }
    public String getCstmrPost() {
        return cstmrPost;
    }
    public void setCstmrPost(String cstmrPost) {
        this.cstmrPost = cstmrPost;
    }
    public String getCstmrAddr() {
        return cstmrAddr;
    }
    public void setCstmrAddr(String cstmrAddr) {
        this.cstmrAddr = cstmrAddr;
    }
    public String getCstmrAddrDtl() {
        return cstmrAddrDtl;
    }
    public void setCstmrAddrDtl(String cstmrAddrDtl) {
        this.cstmrAddrDtl = cstmrAddrDtl;
    }
    public String getCstmrAddrBjd() {
        return cstmrAddrBjd;
    }
    public void setCstmrAddrBjd(String cstmrAddrBjd) {
        this.cstmrAddrBjd = cstmrAddrBjd;
    }
    public String getCstmrBillSendCode() {
        return cstmrBillSendCode;
    }
    public void setCstmrBillSendCode(String cstmrBillSendCode) {
        this.cstmrBillSendCode = cstmrBillSendCode;
    }
    public String getCstmrMail() {
        return cstmrMail;
    }
    public void setCstmrMail(String cstmrMail) {
        this.cstmrMail = cstmrMail;
    }
    public String getCstmrMailReceiveFlag() {
        return cstmrMailReceiveFlag;
    }
    public void setCstmrMailReceiveFlag(String cstmrMailReceiveFlag) {
        this.cstmrMailReceiveFlag = cstmrMailReceiveFlag;
    }
    public String getCstmrVisitType() {
        return cstmrVisitType;
    }
    public void setCstmrVisitType(String cstmrVisitType) {
        this.cstmrVisitType = cstmrVisitType;
    }
    public String getCstmrReceiveTelFn() {
        return cstmrReceiveTelFn;
    }
    public void setCstmrReceiveTelFn(String cstmrReceiveTelFn) {
        this.cstmrReceiveTelFn = cstmrReceiveTelFn;
    }
    public String getCstmrReceiveTelNm() {
        return cstmrReceiveTelNm;
    }
    public void setCstmrReceiveTelNm(String cstmrReceiveTelNm) {
        this.cstmrReceiveTelNm = cstmrReceiveTelNm;
    }
    public String getCstmrReceiveTelRn() {
        return cstmrReceiveTelRn;
    }
    public void setCstmrReceiveTelRn(String cstmrReceiveTelRn) {
        this.cstmrReceiveTelRn = cstmrReceiveTelRn;
    }
    public String getOthersPaymentAg() {
        return othersPaymentAg;
    }
    public void setOthersPaymentAg(String othersPaymentAg) {
        this.othersPaymentAg = othersPaymentAg;
    }
    public String getOthersPaymentNm() {
        return othersPaymentNm;
    }
    public void setOthersPaymentNm(String othersPaymentNm) {
        this.othersPaymentNm = othersPaymentNm;
    }
    public String getOthersPaymentRrn() {
        return othersPaymentRrn;
    }
    public void setOthersPaymentRrn(String othersPaymentRrn) {
        this.othersPaymentRrn = othersPaymentRrn;
    }
    public String getOthersPaymentRelation() {
        return othersPaymentRelation;
    }
    public void setOthersPaymentRelation(String othersPaymentRelation) {
        this.othersPaymentRelation = othersPaymentRelation;
    }
    public String getOthersPaymentRnm() {
        return othersPaymentRnm;
    }
    public void setOthersPaymentRnm(String othersPaymentRnm) {
        this.othersPaymentRnm = othersPaymentRnm;
    }
    public Date getSysRdate() {
        return sysRdate;
    }
    public void setSysRdate(Date sysRdate) {
        this.sysRdate = sysRdate;
    }
    public String getCstmrName() {
        return cstmrName;
    }
    public void setCstmrName(String cstmrName) {
        this.cstmrName = cstmrName;
    }
    public String getCstmrNativeRrn() {
        return cstmrNativeRrn;
    }
    public void setCstmrNativeRrn(String cstmrNativeRrn) {
        this.cstmrNativeRrn = cstmrNativeRrn;
    }
    public String getCstmrForeignerDod() {
        return cstmrForeignerDod;
    }
    public void setCstmrForeignerDod(String cstmrForeignerDod) {
        this.cstmrForeignerDod = cstmrForeignerDod;
    }
    public String getCstmrForeignerBirth() {
        return cstmrForeignerBirth;
    }
    public void setCstmrForeignerBirth(String cstmrForeignerBirth) {
        this.cstmrForeignerBirth = cstmrForeignerBirth;
    }
    public String getSelfInqryAgrmYn() {
        return selfInqryAgrmYn;
    }
    public void setSelfInqryAgrmYn(String selfInqryAgrmYn) {
        this.selfInqryAgrmYn = selfInqryAgrmYn;
    }
    public String getSelfCertType() {
        return selfCertType;
    }
    public void setSelfCertType(String selfCertType) {
        this.selfCertType = selfCertType;
    }
    public String getSelfIssuExprDt() {
        return selfIssuExprDt;
    }
    public void setSelfIssuExprDt(String selfIssuExprDt) {
        this.selfIssuExprDt = selfIssuExprDt;
    }
    public String getSelfIssuNum() {
        return selfIssuNum;
    }
    public void setSelfIssuNum(String selfIssuNum) {
        this.selfIssuNum = selfIssuNum;
    }
    public String getCstmrJejuId() {
        return cstmrJejuId;
    }
    public void setCstmrJejuId(String cstmrJejuId) {
        this.cstmrJejuId = cstmrJejuId;
    }
    public String getSelfCstmrCi() {
        return selfCstmrCi;
    }
    public void setSelfCstmrCi(String selfCstmrCi) {
        this.selfCstmrCi = selfCstmrCi;
    }
    public String getRecommendFlagCd() {
        return recommendFlagCd;
    }
    public void setRecommendFlagCd(String recommendFlagCd) {
        this.recommendFlagCd = recommendFlagCd;
    }
    public String getRecommendInfo() {
        return recommendInfo;
    }
    public void setRecommendInfo(String recommendInfo) {
        this.recommendInfo = recommendInfo;
    }




}
