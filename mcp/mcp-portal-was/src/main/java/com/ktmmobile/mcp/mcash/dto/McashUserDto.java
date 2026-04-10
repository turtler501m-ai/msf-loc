package com.ktmmobile.mcp.mcash.dto;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import com.ktmmobile.mcp.common.util.MaskingUtil;
import com.ktmmobile.mcp.common.util.StringMakerUtil;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.mcash.util.McashAES256;

public class McashUserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userid;          // 포탈ID
    private String status;          // 회원상태(A:가입, C:탈퇴, S:대기)
    private String customerId;      // 고객번호
    private String contractNum;     // 계약번호
    private String svcCntrNo;       // 서비스 계약번호
    private String strtDttm;        // 시작일시
    private String endDttm;         // 종료일시
    private String evntType;        // 연동유형
    private String evntTypeDtl;     // 연동유형상세

    private String mkSvcCntrNo;
    private String mkMobileNo;
    private String mobileNo;        // 휴대폰 번호
    private String rateNm;          // 요금제명
    private String repNo;           // 대표번호
    private String birthDt;         // 생년월일
    private boolean isAdult;        // 성인여부
    private String denyRateYn;      // 이용불가 요금제 여부
    private String formatUnSvcNo;   // 마스킹해제 전화번호

    public String getUserid() {
        return userid;
    }

    public String getUseridEnc() throws GeneralSecurityException,UnsupportedEncodingException {
        return StringUtil.isNotNull(userid) ? McashAES256.encrypt(userid) : userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getSvcCntrNo() {
        return svcCntrNo;
    }

    public String getSvcCntrNoEnc() throws GeneralSecurityException,UnsupportedEncodingException {
        return StringUtil.isNotNull(svcCntrNo) ? McashAES256.encrypt(svcCntrNo) : svcCntrNo;
    }



    public void setSvcCntrNo(String svcCntrNo) {
        this.svcCntrNo = svcCntrNo;
        this.mkSvcCntrNo = MaskingUtil.getMaskedValue(svcCntrNo, 5,true,false); // 앞에서 5자리 이하 전체 마스킹
    }

    public String getStrtDttm() {
        return strtDttm;
    }

    public void setStrtDttm(String strtDttm) {
        this.strtDttm = strtDttm;
    }

    public String getEndDttm() {
        return endDttm;
    }

    public void setEndDttm(String endDttm) {
        this.endDttm = endDttm;
    }

    public String getEvntType() {
        return evntType;
    }

    public void setEvntType(String evntType) {
        this.evntType = evntType;
    }

    public String getEvntTypeDtl() {
        return evntTypeDtl;
    }

    public void setEvntTypeDtl(String evntTypeDtl) {
        this.evntTypeDtl = evntTypeDtl;
    }

    public String getMkSvcCntrNo() {
        return mkSvcCntrNo;
    }

    public void setMkSvcCntrNo(String mkSvcCntrNo) {
        this.mkSvcCntrNo = mkSvcCntrNo;
    }

    public String getMkMobileNo() {
        return mkMobileNo;
    }

    public void setMkMobileNo(String mkMobileNo) {
        this.mkMobileNo = mkMobileNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
        this.mkMobileNo = StringMakerUtil.getPhoneNum(mobileNo);
        this.formatUnSvcNo = StringUtil.getMobileFullNum(StringUtil.NVL(mobileNo,""));
    }

    public String getRateNm() {
        return rateNm;
    }

    public void setRateNm(String rateNm) {
        this.rateNm = rateNm;
    }

    public String getRepNo() {
        return repNo;
    }

    public void setRepNo(String repNo) {
        this.repNo = repNo;
    }

    public String getBirthDt() {
        return birthDt;
    }

    public void setBirthDt(String birthDt) {
        this.birthDt = birthDt;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public boolean getIsAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }

    public String getDenyRateYn() {
        return denyRateYn;
    }

    public void setDenyRateYn(String denyRateYn) {
        this.denyRateYn = denyRateYn;
    }

    public String getFormatUnSvcNo() {
        return formatUnSvcNo;
    }

    public void setFormatUnSvcNo(String formatUnSvcNo) {
        this.formatUnSvcNo = formatUnSvcNo;
    }
}