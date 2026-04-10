package com.ktis.msp.rcp.rcpMgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

import java.io.Serializable;
import java.util.Date;

public class NiceLogVO extends BaseVo implements Serializable {

    /** PK */
    private long niceHistSeq =-1;
    /** 일련번호 기본키 */
    private String reqSeq;
    /** 요청 일련번호 */
    private String resSeq;
    /** 요청 구분 */
    private String authType;
    /** 인증성명 */
    private String name;
    /** 인증생년월일 */
    private String birthDate;
    /** GENDER */
    private String gender;
    /** NATIONAL_INFO */
    private String nationalInfo;
    /** DUP_INFO */
    private String dupInfo;
    /** CONN_INFO */
    private String connInfo;
    /** 파라메터 1 */
    private String paramR1;
    /** 파라메터 2 */
    private String paramR2;
    /** 파라메터 3 */
    private String paramR3;
    /** 종료여부 */
    private String endYn;
    /** 등록아이피 */
    private String rip;
    /** 등록일 */
    private String sysRdateDt;
    /** 등록일시 */
    private Date sysRdate;

    private String nReferer;

    /** 수정일 */
    private Date rvisnDttm;

    private String cstmrRrn;

    private String sMobileNo;
    private String sMobileCo;

    public long getNiceHistSeq() {
        return niceHistSeq;
    }

    public void setNiceHistSeq(long niceHistSeq) {
        this.niceHistSeq = niceHistSeq;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationalInfo() {
        return nationalInfo;
    }

    public void setNationalInfo(String nationalInfo) {
        this.nationalInfo = nationalInfo;
    }

    public String getDupInfo() {
        return dupInfo;
    }

    public void setDupInfo(String dupInfo) {
        this.dupInfo = dupInfo;
    }

    public String getConnInfo() {
        return connInfo;
    }

    public void setConnInfo(String connInfo) {
        this.connInfo = connInfo;
    }

    public String getParamR1() {
        return paramR1;
    }

    public void setParamR1(String paramR1) {
        this.paramR1 = paramR1;
    }

    public String getParamR2() {
        return paramR2;
    }

    public void setParamR2(String paramR2) {
        this.paramR2 = paramR2;
    }

    public String getParamR3() {
        return paramR3;
    }

    public void setParamR3(String paramR3) {
        this.paramR3 = paramR3;
    }

    public String getEndYn() {
        return endYn;
    }

    public void setEndYn(String endYn) {
        this.endYn = endYn;
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

    public String getnReferer() {
        return nReferer;
    }

    public void setnReferer(String nReferer) {
        this.nReferer = nReferer;
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

    public String getsMobileNo() {
        return sMobileNo;
    }

    public void setsMobileNo(String sMobileNo) {
        this.sMobileNo = sMobileNo;
    }

    public String getsMobileCo() {
        return sMobileCo;
    }

    public void setsMobileCo(String sMobileCo) {
        this.sMobileCo = sMobileCo;
    }

    public String getCstmrRrn() {
        return cstmrRrn;
    }

    public void setCstmrRrn(String cstmrRrn) {
        this.cstmrRrn = cstmrRrn;
    }
}
