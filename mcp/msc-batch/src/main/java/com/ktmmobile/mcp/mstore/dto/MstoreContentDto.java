package com.ktmmobile.mcp.mstore.dto;

public class MstoreContentDto {
    private long contentSeq;
    private String devcTpCd;
    private String status;
    private String failMessage;
    private String jsonHash;
    private String userId;

    public long getContentSeq() {
        return contentSeq;
    }

    public void setContentSeq(long contentSeq) {
        this.contentSeq = contentSeq;
    }

    public String getDevcTpCd() {
        return devcTpCd;
    }

    public void setDevcTpCd(String devcTpCd) {
        this.devcTpCd = devcTpCd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFailMessage() {
        return failMessage;
    }

    public void setFailMessage(String failMessage) {
        this.failMessage = failMessage;
    }

    public String getJsonHash() {
        return jsonHash;
    }

    public void setJsonHash(String jsonHash) {
        this.jsonHash = jsonHash;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
