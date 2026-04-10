package com.ktis.msp.org.workmgmt.vo;

import java.io.Serializable;

public class DocRcvUrlVO implements Serializable {
    private static final long serialVersionUID = -6510970033252597452L;

    private String rcvUrlId;
    private String docRcvId;
    private String url;
    private String status;
    private String urlStatusNm;
    private String issueDt;
    private String expireDt;
    private String userId;

    public String getDocRcvId() {
        return docRcvId;
    }

    public void setDocRcvId(String docRcvId) {
        this.docRcvId = docRcvId;
    }

    public String getRcvUrlId() {
        return rcvUrlId;
    }

    public void setRcvUrlId(String rcvUrlId) {
        this.rcvUrlId = rcvUrlId;
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

    public String getUrlStatusNm() {
        return urlStatusNm;
    }

    public void setUrlStatusNm(String urlStatusNm) {
        this.urlStatusNm = urlStatusNm;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
