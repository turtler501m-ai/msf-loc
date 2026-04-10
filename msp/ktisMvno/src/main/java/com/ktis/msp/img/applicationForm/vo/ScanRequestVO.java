package com.ktis.msp.img.applicationForm.vo;

import java.util.List;

public class ScanRequestVO {
    private String scanId;
    private String originScanId;
    private String prcsSbst;
    private String userId;
    private String fileId;

    public String getScanId() {
        return scanId;
    }

    public void setScanId(String scanId) {
        this.scanId = scanId;
    }

    public String getOriginScanId() {
        return originScanId;
    }

    public void setOriginScanId(String originScanId) {
        this.originScanId = originScanId;
    }

    public String getPrcsSbst() {
        return prcsSbst;
    }

    public void setPrcsSbst(String prcsSbst) {
        this.prcsSbst = prcsSbst;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
