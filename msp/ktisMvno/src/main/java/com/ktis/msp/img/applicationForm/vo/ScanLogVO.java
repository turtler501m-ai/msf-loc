package com.ktis.msp.img.applicationForm.vo;

public class ScanLogVO {
    private long seqNum;
    private String docId;
    private String bizType;
    private String scanId;
    private String fileId;
    private String prcsSbst;
    private String procPrsnId;

    public long getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(long seqNum) {
        this.seqNum = seqNum;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

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

    public String getPrcsSbst() {
        return prcsSbst;
    }

    public void setPrcsSbst(String prcsSbst) {
        this.prcsSbst = prcsSbst;
    }

    public String getProcPrsnId() {
        return procPrsnId;
    }

    public void setProcPrsnId(String procPrsnId) {
        this.procPrsnId = procPrsnId;
    }
}
