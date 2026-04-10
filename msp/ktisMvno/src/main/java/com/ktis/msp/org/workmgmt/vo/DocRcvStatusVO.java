package com.ktis.msp.org.workmgmt.vo;

import java.io.Serializable;

public class DocRcvStatusVO implements Serializable {
    private static final long serialVersionUID = -6510970033252597452L;

    private String docRcvId;
    private String rcvYn;
    private String rcvYnNm;
    private String rcvDt;
    private String procStatus;
    private String procStatusNm;
    private String procDt;

    private String userId;

    public String getDocRcvId() {
        return docRcvId;
    }

    public void setDocRcvId(String docRcvId) {
        this.docRcvId = docRcvId;
    }

    public String getRcvYn() {
        return rcvYn;
    }

    public void setRcvYn(String rcvYn) {
        this.rcvYn = rcvYn;
    }

    public String getRcvYnNm() {
        return rcvYnNm;
    }

    public void setRcvYnNm(String rcvYnNm) {
        this.rcvYnNm = rcvYnNm;
    }

    public String getRcvDt() {
        return rcvDt;
    }

    public void setRcvDt(String rcvDt) {
        this.rcvDt = rcvDt;
    }

    public String getProcStatus() {
        return procStatus;
    }

    public void setProcStatus(String procStatus) {
        this.procStatus = procStatus;
    }

    public String getProcStatusNm() {
        return procStatusNm;
    }

    public void setProcStatusNm(String procStatusNm) {
        this.procStatusNm = procStatusNm;
    }

    public String getProcDt() {
        return procDt;
    }

    public void setProcDt(String procDt) {
        this.procDt = procDt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
