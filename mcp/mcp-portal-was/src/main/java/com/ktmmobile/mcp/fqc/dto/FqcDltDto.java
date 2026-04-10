package com.ktmmobile.mcp.fqc.dto;

import java.io.Serializable;

public class FqcDltDto implements Serializable {

    private static final long serialVersionUID = 4766947291811684671L;



    /** 참여 일련번호 */
    private long fqcSeq = -1 ;
    /** 프리퀀시 정책미션 코드 */
    private String fqcPlcyMsnCd;

    /** 프리퀀시 정책미션 변경 코드 */
    private String fqcPlcyMsnCdUp;

    /** 미션 종류 코드 */
    private String msnTpCd;
    /** 상태값 */
    private String stateCode;

    /** Y인 경우 검증 로직을 수행하지 않음 */
    private String bypassCheckYn;


    /** 계약번호 */
    private String contractNum;
    /** 이벤트 공유 키값 */
    private long uetSeq;

    /** 약관동의날짜  */
    private String mrkStrtDttm;
    /** 추천인 아이디 */
    private String commendId;
    /** 피추천인 계약번호 */
    private String reContractNum;
    /** 사용후기 일련번호 */
    private long reviewId;
    /** kt internet id */
    private String internetId;
    /** 설명 */
    private String dtlDesc;
    /** 등록일 YYYYMMDD */
    private String sysRdateDd;
    /** 시작일 */
    private String strtDttm;
    /** 종료일 */
    private String endDttm;

    /** 종료일 업데이트  */
    private String upEndDttm;
    /** 등록아이피 */
    private String rip;
    /** 등록자 ID */
    private String regstId;




    public long getFqcSeq() {
        return fqcSeq;
    }

    public void setFqcSeq(long fqcSeq) {
        this.fqcSeq = fqcSeq;
    }

    public String getFqcPlcyMsnCd() {
        return fqcPlcyMsnCd;
    }

    public void setFqcPlcyMsnCd(String fqcPlcyMsnCd) {
        this.fqcPlcyMsnCd = fqcPlcyMsnCd;
    }

    public String getFqcPlcyMsnCdUp() {
        return fqcPlcyMsnCdUp;
    }

    public void setFqcPlcyMsnCdUp(String fqcPlcyMsnCdUp) {
        this.fqcPlcyMsnCdUp = fqcPlcyMsnCdUp;
    }

    public String getMsnTpCd() {
        return msnTpCd;
    }

    public void setMsnTpCd(String msnTpCd) {
        this.msnTpCd = msnTpCd;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getBypassCheckYn() {
        return bypassCheckYn;
    }

    public void setBypassCheckYn(String bypassCheckYn) {
        this.bypassCheckYn = bypassCheckYn;
    }

    public String getContractNum() {
        if (contractNum == null) {
            return "";
        }
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public long getUetSeq() {
        return uetSeq;
    }

    public void setUetSeq(long uetSeq) {
        this.uetSeq = uetSeq;
    }

    public String getMrkStrtDttm() {
        return mrkStrtDttm;
    }

    public void setMrkStrtDttm(String mrkStrtDttm) {
        this.mrkStrtDttm = mrkStrtDttm;
    }

    public String getCommendId() {
        return commendId;
    }

    public void setCommendId(String commendId) {
        this.commendId = commendId;
    }

    public String getReContractNum() {
        if (reContractNum == null) {
            return "";
        }
        return reContractNum;
    }

    public void setReContractNum(String reContractNum) {
        this.reContractNum = reContractNum;
    }

    public long getReviewId() {
        return reviewId;
    }

    public void setReviewId(long reviewId) {
        this.reviewId = reviewId;
    }

    public String getInternetId() {
        return internetId;
    }

    public void setInternetId(String internetId) {
        this.internetId = internetId;
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

    public String getUpEndDttm() {
        return upEndDttm;
    }

    public void setUpEndDttm(String upEndDttm) {
        this.upEndDttm = upEndDttm;
    }
}
