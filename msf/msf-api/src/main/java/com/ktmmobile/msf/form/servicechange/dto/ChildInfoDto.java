package com.ktmmobile.msf.form.servicechange.dto;

import com.ktmmobile.msf.common.util.MaskingUtil;
import com.ktmmobile.msf.common.util.StringMakerUtil;

import java.io.Serializable;

public class ChildInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String svcCntrNo;           // 서비스계약번호
    private String minorAgentSvcCntrNo; // 법정대리인 서비스계약번호
    private String mobileNo;            // 휴대폰번호
    private String cntrMobileNo;
    private String customerId;          // 사용자ID
    private String contractNum;         // 계약번호
    private String name;                // 이름

    private String famSeq;              // 가족관계 시퀀스
    private int seq;                    // 구성원 시퀀스
    private String memType;             // 구성원 유형
    private String childType;           // 피부양자 유형
    private String strtDttm;            // 시작일시
    private String endDttm;             // 종료일시
    private String useYn;               // 사용여부
    private int moCtn;
    private String formatUnSvcNo;

    public String getSvcCntrNo() {
        return svcCntrNo;
    }

    public void setSvcCntrNo(String svcCntrNo) {
        this.svcCntrNo = svcCntrNo;
    }

    public String getMinorAgentSvcCntrNo() {
        return minorAgentSvcCntrNo;
    }

    public void setMinorAgentSvcCntrNo(String minorAgentSvcCntrNo) {
        this.minorAgentSvcCntrNo = minorAgentSvcCntrNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
        this.cntrMobileNo = StringMakerUtil.getPhoneNum(MaskingUtil.getMaskedTelNo(mobileNo));
    }

    public String getCntrMobileNo() {
        return cntrMobileNo;
    }

    public void setCntrMobileNo(String cntrMobileNo) {
        this.cntrMobileNo = cntrMobileNo;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamSeq() {
        return famSeq;
    }

    public void setFamSeq(String famSeq) {
        this.famSeq = famSeq;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getMemType() {
        return memType;
    }

    public void setMemType(String memType) {
        this.memType = memType;
    }

    public String getChildType() {
        return childType;
    }

    public void setChildType(String childType) {
        this.childType = childType;
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

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public int getMoCtn() {
        return moCtn;
    }

    public void setMoCtn(int moCtn) {
        this.moCtn = moCtn;
    }

    public String getFormatUnSvcNo() {
        return formatUnSvcNo;
    }

    public void setFormatUnSvcNo(String formatUnSvcNo) {
        this.formatUnSvcNo = formatUnSvcNo;
    }

}
