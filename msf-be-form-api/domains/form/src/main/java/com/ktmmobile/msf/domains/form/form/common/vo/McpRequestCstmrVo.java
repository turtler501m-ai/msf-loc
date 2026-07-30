package com.ktmmobile.msf.domains.form.form.common.vo;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class McpRequestCstmrVo {

    private Long requestKey;

    private LocalDateTime sysRdate = LocalDateTime.now();

    private LocalDateTime rvisnDttm;

    private String rvisnId;

    private String cstmrName;

    //@Encrypted(algorithm = FieldCryptoAlgorithm.LEGACY_AES256_CBC)
    private String cstmrNativeRrn;

    private String cstmrPrivateCname;

    private String cstmrPrivateNumber;

    private String cstmrForeignerRrn;

    private String cstmrForeignerBirth;

    private String cstmrForeignerPn;

    private String cstmrForeignerNation;

    private String visaCd;

    private String cstmrForeignerSdate;

    private String cstmrForeignerEdate;

    private String cstmrForeignerDod;

    private String cstmrJuridicalCname;

    private String cstmrJuridicalRrn;

    private String cstmrJuridicalNumber;

    private String upjnCd;

    private String bcuSbst;

    private String cstmrVisitType;

    private String fathMobileFn;

    private String fathMobileMn;

    private String fathMobileRn;

    private String selfInqryAgrmYn = "";

    private String selfCertType = "";

    private String selfCstmrCi = "";

    private String selfIssuExprDt;

    private String selfIssuNum;

    private String cstmrTelFn;

    private String cstmrTelMn;

    private String cstmrTelRn;

    private String cstmrMobileFn;

    private String cstmrMobileMn;

    private String cstmrMobileRn;

    private String cstmrPost;

    private String cstmrAddr;

    private String cstmrAddrDtl;

    private String cstmrAddrBjd;

    private String cstmrBillSendCode;

    private String cstmrMail;

    private String cstmrMailReceiveFlag;

    private String cstmrReceiveTelFn;

    private String cstmrReceiveTelNm;

    private String cstmrReceiveTelRn;

    private String othersPaymentAg;

    private String othersPaymentNm;

    private String othersPaymentRrn;

    private String othersPaymentRelation;

    private String othersPaymentRnm;

    private String prntsCtn;

    private String cstmrJejuId = "";

    private String recommendFlagCd = "";

    private String recommendInfo = "";

    // MyBatis 매핑 전용 가짜(Dummy) Setter
    public void setRequestKey(Long requestKey) { this.requestKey = requestKey; }

    public void setCretDt(LocalDateTime cretDt) { this.sysRdate = cretDt; }

    public void setAmdDt(LocalDateTime amdDt) { this.rvisnDttm = amdDt; }

    public void setAmdId(String amdId) { this.rvisnId = amdId; }

    public void setCstmrNm(String cstmrNm) { this.cstmrName = cstmrNm; }

    public void setCstmrNativeRrn(String cstmrNativeRrn) { this.cstmrNativeRrn = cstmrNativeRrn; }

    public void setCstmrPrivateCname(String cstmrPrivateCname) { this.cstmrPrivateCname = cstmrPrivateCname; }

    public void setCstmrPrivateBizNo(String cstmrPrivateBizNo) {
        this.cstmrPrivateNumber = cstmrPrivateBizNo;
    }

    public void setCstmrForeignerRrn(String cstmrForeignerRrn) { this.cstmrForeignerRrn = cstmrForeignerRrn; }

    public void setCstmrForeignerBirth(String cstmrForeignerBirth) { this.cstmrForeignerBirth = cstmrForeignerBirth; }

    public void setCstmrForeignerPn(String cstmrForeignerPn) { this.cstmrForeignerPn = cstmrForeignerPn; }

    public void setCstmrForeignerNation(String cstmrForeignerNation) { this.cstmrForeignerNation = cstmrForeignerNation; }

    public void setCstmrForeignerVisaNo(String cstmrForeignerVisaNo) { this.visaCd = cstmrForeignerVisaNo; }

    public void setCstmrForeignerVdateStartDate(String cstmrForeignerVdateStartDate) { this.cstmrForeignerSdate = cstmrForeignerVdateStartDate; }

    public void setCstmrForeignerVdateEndDate(String cstmrForeignerVdateEndDate) { this.cstmrForeignerEdate = cstmrForeignerVdateEndDate; }

    public void setCstmrForeignerDod(String cstmrForeignerDod) { this.cstmrForeignerDod = cstmrForeignerDod; }

    public void setCstmrJuridicalCname(String cstmrJuridicalCname) { this.cstmrJuridicalCname = cstmrJuridicalCname; }

    public void setCstmrJuridicalRrn(String cstmrJuridicalRrn) { this.cstmrJuridicalRrn = cstmrJuridicalRrn; }

    public void setCstmrJuridicalBizNo(String cstmrJuridicalBizNo) { this.cstmrJuridicalNumber = cstmrJuridicalBizNo; }

    public void setUpjnCd(String upjnCd) { this.upjnCd = upjnCd; }

    public void setBcuSbst(String bcuSbst) { this.bcuSbst = bcuSbst; }

    public void setCstmrVisitTypeCd(String cstmrVisitTypeCd) { this.cstmrVisitType = cstmrVisitTypeCd; }

    public void setFathMobileFnNo(String fathMobileFnNo) { this.fathMobileFn = fathMobileFnNo; }

    public void setFathMobileMnNo(String fathMobileMnNo) { this.fathMobileMn = fathMobileMnNo; }

    public void setFathMobileRnNo(String fathMobileRnNo) { this.fathMobileRn = fathMobileRnNo; }

    public void setIdentityIssuDate(String identityIssuDate) { this.selfIssuExprDt = identityIssuDate; }

    public void setSelfIssuNo(String selfIssuNo) { this.selfIssuNum = selfIssuNo; }

    public void setCstmrTelFnNo(String cstmrTelFnNo) { this.cstmrTelFn = cstmrTelFnNo; }

    public void setCstmrTelMnNo(String cstmrTelMnNo) { this.cstmrTelMn = cstmrTelMnNo; }

    public void setCstmrTelRnNo(String cstmrTelRnNo) { this.cstmrTelRn = cstmrTelRnNo; }

    public void setCstmrMobileFnNo(String cstmrMobileFnNo) { this.cstmrMobileFn = cstmrMobileFnNo; }

    public void setCstmrMobileMnNo(String cstmrMobileMnNo) { this.cstmrMobileMn = cstmrMobileMnNo; }

    public void setCstmrMobileRnNo(String cstmrMobileRnNo) { this.cstmrMobileRn = cstmrMobileRnNo; }

    public void setCstmrZipcd(String cstmrZipcd) { this.cstmrPost = cstmrZipcd; }

    public void setCstmrAdr(String cstmrAdr) { this.cstmrAddr = cstmrAdr; }

    public void setCstmrAdrDtl(String cstmrAdrDtl) { this.cstmrAddrDtl = cstmrAdrDtl; }

    public void setCstmrAdrBjd(String cstmrAdrBjd) { this.cstmrAddrBjd = cstmrAdrBjd; }

    public void setCstmrBillSendTypeCd(String cstmrBillSendTypeCd) { this.cstmrBillSendCode = cstmrBillSendTypeCd; }

    public void setCstmrEmailAdr(String cstmrEmailAdr) { this.cstmrMail = cstmrEmailAdr; }

    public void setCstmrEmailReceiveYn(String cstmrEmailReceiveYn) { this.cstmrMailReceiveFlag = cstmrEmailReceiveYn; }

    public void setCstmrReceiveTelFnNo(String cstmrReceiveTelFnNo) { this.cstmrReceiveTelFn = cstmrReceiveTelFnNo; }

    public void setCstmrReceiveTelNmNo(String cstmrReceiveTelNmNo) { this.cstmrReceiveTelNm = cstmrReceiveTelNmNo; }

    public void setCstmrReceiveTelRnNo(String cstmrReceiveTelRnNo) { this.cstmrReceiveTelRn = cstmrReceiveTelRnNo; }

    public void setOthersPaymentYn(String othersPaymentYn) { this.othersPaymentAg = othersPaymentYn; }

    public void setOthersPaymentNm(String othersPaymentNm) { this.othersPaymentNm = othersPaymentNm; }

    public void setOthersPaymentRrn(String othersPaymentRrn) { this.othersPaymentRrn = othersPaymentRrn; }

    public void setOthersPaymentRelTypeCd(String othersPaymentRelTypeCd) { this.othersPaymentRelation = othersPaymentRelTypeCd; }

    public void setOthersPaymentReqNm(String othersPaymentReqNm) { this.othersPaymentRnm = othersPaymentReqNm; }

    public void setPrntsMobileNo(String prntsMobileNo) { this.prntsCtn = prntsMobileNo; }
}
