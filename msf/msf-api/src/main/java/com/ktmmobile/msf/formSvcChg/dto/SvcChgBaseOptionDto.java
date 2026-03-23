package com.ktmmobile.msf.formSvcChg.dto;

/**
 * 서비스변경 업무 옵션 DTO. MSF_CD_DTL 행 매핑.
 */
public class SvcChgBaseOptionDto {

    private String dtlCd;
    private String dtlCdNm;
    private String concurrentAvailYn;
    private String imagingYn;
    private int sortSeq;

    public String getDtlCd() { return dtlCd; }
    public void setDtlCd(String dtlCd) { this.dtlCd = dtlCd; }

    public String getDtlCdNm() { return dtlCdNm; }
    public void setDtlCdNm(String dtlCdNm) { this.dtlCdNm = dtlCdNm; }

    public String getConcurrentAvailYn() { return concurrentAvailYn; }
    public void setConcurrentAvailYn(String concurrentAvailYn) { this.concurrentAvailYn = concurrentAvailYn; }

    public String getImagingYn() { return imagingYn; }
    public void setImagingYn(String imagingYn) { this.imagingYn = imagingYn; }

    public int getSortSeq() { return sortSeq; }
    public void setSortSeq(int sortSeq) { this.sortSeq = sortSeq; }
}
