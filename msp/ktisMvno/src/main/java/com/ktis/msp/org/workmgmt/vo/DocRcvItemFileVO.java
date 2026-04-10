package com.ktis.msp.org.workmgmt.vo;

public class DocRcvItemFileVO {

    private long itemSeq;
    private String fileId;
    private String mergeYn;
    private String userId;
    private String cretDt;
    private String ext;
    private String resultCode;
    private String resultMessage;

    private String status;

    public long getItemSeq() {
        return itemSeq;
    }

    public void setItemSeq(long itemSeq) {
        this.itemSeq = itemSeq;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getMergeYn() {
        return mergeYn;
    }

    public void setMergeYn(String mergeYn) {
        this.mergeYn = mergeYn;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCretDt() {
        return cretDt;
    }

    public void setCretDt(String cretDt) {
        this.cretDt = cretDt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
}
