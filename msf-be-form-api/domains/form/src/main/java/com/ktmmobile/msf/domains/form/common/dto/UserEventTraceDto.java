package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;
import java.util.Date;

public class UserEventTraceDto implements Serializable {

    private static final long serialVersionUID = -3675629223200392498L;


    /** 수행 일련번호 */
    private long uetSeq;
    /** 처리 모듈 대구분  */
    private String prcsMdlMain;
    /** 처리 모듈 중구분 */
    private String prcsMdlMid;
    /** 처리 모듈 소소구분 */
    private String prcsMdlSub;
    /** 처리 결과 내용 */
    private String trtmRsltSmst;
    /** 처리 내용 */
    private String prcsSbst;
    /** 설명 */
    private String dtlDesc;
    /** 등록일 YYYYMMDD */
    private String sysRdateDd;
    /** 등록아이피 */
    private String rip;
    /** 등록자 ID */
    private String regstId;
    /** 등록일시 */
    private Date regstDttm;
    /** 수정자 ID */
    private String rvisnId;
    /** 수정일시 */
    private Date rvisnDttm;


    /** 등록일시 String  */
    private String strRegstDttm;

    public long getUetSeq() {
        return uetSeq;
    }

    public void setUetSeq(long uetSeq) {
        this.uetSeq = uetSeq;
    }

    public String getPrcsMdlMain() {
        return prcsMdlMain;
    }

    public void setPrcsMdlMain(String prcsMdlMain) {
        this.prcsMdlMain = prcsMdlMain;
    }

    public String getPrcsMdlMid() {
        return prcsMdlMid;
    }

    public void setPrcsMdlMid(String prcsMdlMid) {
        this.prcsMdlMid = prcsMdlMid;
    }

    public String getPrcsMdlSub() {
        return prcsMdlSub;
    }

    public void setPrcsMdlSub(String prcsMdlSub) {
        this.prcsMdlSub = prcsMdlSub;
    }

    public String getTrtmRsltSmst() {
        return trtmRsltSmst;
    }

    public void setTrtmRsltSmst(String trtmRsltSmst) {
        this.trtmRsltSmst = trtmRsltSmst;
    }

    public String getPrcsSbst() {
        return prcsSbst;
    }

    public void setPrcsSbst(String prcsSbst) {
        this.prcsSbst = prcsSbst;
    }

    public String getDtlDesc() {
        return dtlDesc;
    }

    public void setDtlDesc(String dtlDesc) {
        this.dtlDesc = dtlDesc;
    }

    public String getSysRdateDd() {
        return sysRdateDd;
    }

    public void setSysRdateDd(String sysRdateDd) {
        this.sysRdateDd = sysRdateDd;
    }

    public String getRip() {
        return rip;
    }

    public void setRip(String rip) {
        this.rip = rip;
    }

    public String getRegstId() {
        return regstId;
    }

    public void setRegstId(String regstId) {
        this.regstId = regstId;
    }

    public Date getRegstDttm() {
        return regstDttm;
    }

    public void setRegstDttm(Date regstDttm) {
        this.regstDttm = regstDttm;
    }

    public String getRvisnId() {
        return rvisnId;
    }

    public void setRvisnId(String rvisnId) {
        this.rvisnId = rvisnId;
    }

    public Date getRvisnDttm() {
        return rvisnDttm;
    }

    public void setRvisnDttm(Date rvisnDttm) {
        this.rvisnDttm = rvisnDttm;
    }

    public String getStrRegstDttm() {
        return strRegstDttm;
    }

    public void setStrRegstDttm(String strRegstDttm) {
        this.strRegstDttm = strRegstDttm;
    }
}
