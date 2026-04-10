package com.ktis.msp.img.applicationForm.vo;

public class ScanFileVO {
    private String scanId;
    private String fileId;
    private int fileNumber;
    private String docId;
    private String originDir;

    private String originScanId;
    private String userId;

    public String getScanId() {
        return scanId;
    }

    public void setScanId(String scanId) {
        this.scanId = scanId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public int getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(int fileNumber) {
        this.fileNumber = fileNumber;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getOriginDir() {
        return originDir;
    }

    public void setOriginDir(String originDir) {
        this.originDir = originDir;
    }

    public String getOriginScanId() {
        return originScanId;
    }

    public void setOriginScanId(String originScanId) {
        this.originScanId = originScanId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
