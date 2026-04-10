package com.ktis.msp.org.bpamgmt.vo;


import com.ktis.msp.base.mvc.BaseVo;

public class BpaMgmtExcelReqVO extends BaseVo {

    private String bpaId;
    private String fileCd;
    private String fileNm;

    private String batchJobId;

    private String startDt;
    private String endDt;

    private Integer bpaTargetCnt;

    private String excelYn;
    private String periodCd;

    private String etc1;
    private String etc2;
    private String etc3;

    public String getEtc3() {
        return etc3;
    }

    public void setEtc3(String etc3) {
        this.etc3 = etc3;
    }

    public String getEtc2() {
        return etc2;
    }

    public void setEtc2(String etc2) {
        this.etc2 = etc2;
    }

    public String getEtc1() {
        return etc1;
    }

    public void setEtc1(String etc1) {
        this.etc1 = etc1;
    }

    public String getPeriodCd() {
        return periodCd;
    }

    public void setPeriodCd(String periodCd) {
        this.periodCd = periodCd;
    }

    public String getExcelYn() {
        return excelYn;
    }

    public void setExcelYn(String excelYn) {
        this.excelYn = excelYn;
    }

    public String getFileNm() {
        return fileNm;
    }

    public void setFileNm(String fileNm) {
        this.fileNm = fileNm;
    }

    public String getBpaId() {
        return bpaId;
    }

    public void setBpaId(String bpaId) {
        this.bpaId = bpaId;
    }

    public String getFileCd() {
        return fileCd;
    }

    public void setFileCd(String fileCd) {
        this.fileCd = fileCd;
    }

    public String getBatchJobId() {
        return batchJobId;
    }

    public void setBatchJobId(String batchJobId) {
        this.batchJobId = batchJobId;
    }

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    public Integer getBpaTargetCnt() {
        return bpaTargetCnt;
    }

    public void setBpaTargetCnt(Integer bpaTargetCnt) {
        this.bpaTargetCnt = bpaTargetCnt;
    }

}