package com.ktis.msp.org.bpamgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class BpaMgmtReqVO extends BaseVo {

    /** 업무자동화 정보 */
    private String bpaId;       // 업무ID
    private String description; // 상세설명
    private String excelYn;     // 엑셀업로드 여부
    private String periodCd;    // 기간유형
    private String regstDttm;   // 등록일자
    private String regstId;     // 등록자
    private String rvisnDttm;   // 수정일자
    private String rvismId;     // 수정자

    private String usgYn;       // 사용여부 (공통코드)

    private String isAdminDev;  // 권한 체크

    // ================================================================

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBpaId() {
        return bpaId;
    }

    public void setBpaId(String bpaId) {
        this.bpaId = bpaId;
    }

    public String getExcelYn() {
        return excelYn;
    }

    public void setExcelYn(String excelYn) {
        this.excelYn = excelYn;
    }

    public String getPeriodCd() {
        return periodCd;
    }

    public void setPeriodCd(String periodCd) {
        this.periodCd = periodCd;
    }

    public String getRegstDttm() {
        return regstDttm;
    }

    public void setRegstDttm(String regstDttm) {
        this.regstDttm = regstDttm;
    }

    public String getRegstId() {
        return regstId;
    }

    public void setRegstId(String regstId) {
        this.regstId = regstId;
    }

    public String getRvisnDttm() {
        return rvisnDttm;
    }

    public void setRvisnDttm(String rvisnDttm) {
        this.rvisnDttm = rvisnDttm;
    }

    public String getRvismId() {
        return rvismId;
    }

    public void setRvismId(String rvismId) {
        this.rvismId = rvismId;
    }

    public String getUsgYn() {
        return usgYn;
    }

    public void setUsgYn(String usgYn) {
        this.usgYn = usgYn;
    }

    public String getIsAdminDev() {
        return isAdminDev;
    }

    public void setIsAdminDev(String isAdminDev) {
        this.isAdminDev = isAdminDev;
    }
}
