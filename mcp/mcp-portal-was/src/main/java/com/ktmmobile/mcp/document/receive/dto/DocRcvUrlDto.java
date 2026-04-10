package com.ktmmobile.mcp.document.receive.dto;

import java.io.Serializable;

public class DocRcvUrlDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String rcvUrlId;
    private String docRcvId;
    private String url;
    private String status;
    private String issueDt;
    private String expireDt;

    public String getRcvUrlId() {
        return rcvUrlId;
    }

    public void setRcvUrlId(String rcvUrlId) {
        this.rcvUrlId = rcvUrlId;
    }

    public String getDocRcvId() {
        return docRcvId;
    }

    public void setDocRcvId(String docRcvId) {
        this.docRcvId = docRcvId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIssueDt() {
        return issueDt;
    }

    public void setIssueDt(String issueDt) {
        this.issueDt = issueDt;
    }

    public String getExpireDt() {
        return expireDt;
    }

    public void setExpireDt(String expireDt) {
        this.expireDt = expireDt;
    }
}
