package com.ktis.msp.org.bpamgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class BpaMgmtFileReqVO extends BaseVo {

    /** 업무자동화 파일 정보 */
    private String batchJobId;    // 배치작업ID
    private String bpaId;          // 업무ID
    private String etc1;            // 기타1
    private String etc2;            // 기타2
    private String etc3;            // 기타3
    private String fileId;         // 파일ID
    private String fileNm;         // 파일명
    private String regstDttm;      // 등록일자
    private String regstId;        // 등록자
    private String rmk;             // 비고
    private String rvisnDttm;      // 수정일자
    private String rvisnId;        // 수정자
    private String sortNo;         // 정렬순번
    private String useYn;          // 사용여부

    private String isAdminDev;

    // ================================================================

    public String getEtc2() {
        return etc2;
    }

    public void setEtc2(String etc2) {
        this.etc2 = etc2;
    }

    public String getBatchJobId() {
        return batchJobId;
    }

    public void setBatchJobId(String batchJobId) {
        this.batchJobId = batchJobId;
    }

    public String getBpaId() {
        return bpaId;
    }

    public void setBpaId(String bpaId) {
        this.bpaId = bpaId;
    }

    public String getEtc1() {
        return etc1;
    }

    public void setEtc1(String etc1) {
        this.etc1 = etc1;
    }

    public String getEtc3() {
        return etc3;
    }

    public void setEtc3(String etc3) {
        this.etc3 = etc3;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileNm() {
        return fileNm;
    }

    public void setFileNm(String fileNm) {
        this.fileNm = fileNm;
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

    public String getRmk() {
        return rmk;
    }

    public void setRmk(String rmk) {
        this.rmk = rmk;
    }

    public String getRvisnDttm() {
        return rvisnDttm;
    }

    public void setRvisnDttm(String rvisnDttm) {
        this.rvisnDttm = rvisnDttm;
    }

    public String getRvisnId() {
        return rvisnId;
    }

    public void setRvisnId(String rvisnId) {
        this.rvisnId = rvisnId;
    }

    public String getSortNo() {
        return sortNo;
    }

    public void setSortNo(String sortNo) {
        this.sortNo = sortNo;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getIsAdminDev() {
        return isAdminDev;
    }

    public void setIsAdminDev(String isAdminDev) {
        this.isAdminDev = isAdminDev;
    }
}
