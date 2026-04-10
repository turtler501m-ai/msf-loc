package com.ktmmobile.mcp.document.receive.dto;

import java.io.Serializable;

public class DocRcvItemDto implements Serializable {
    private static final long serialVersionUID = -6510970033252597452L;

    private String itemSeq;
    private String docRcvId;
    private String itemId;
    private String status;
    private String fileStatusNm;
    private String userId;
    private String fileRcvDt;

    private String itemNm;
    private String requestYn;

    public String getItemSeq() {
        return itemSeq;
    }

    public void setItemSeq(String itemSeq) {
        this.itemSeq = itemSeq;
    }

    public String getDocRcvId() {
        return docRcvId;
    }

    public void setDocRcvId(String docRcvId) {
        this.docRcvId = docRcvId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getFileStatusNm() {
        return fileStatusNm;
    }

    public void setFileStatusNm(String fileStatusNm) {
        this.fileStatusNm = fileStatusNm;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFileRcvDt() {
        return fileRcvDt;
    }

    public void setFileRcvDt(String fileRcvDt) {
        this.fileRcvDt = fileRcvDt;
    }

    public String getItemNm() {
        return itemNm;
    }

    public void setItemNm(String itemNm) {
        this.itemNm = itemNm;
    }

    public String getRequestYn() {
        return requestYn;
    }

    public void setRequestYn(String requestYn) {
        this.requestYn = requestYn;
    }
}
