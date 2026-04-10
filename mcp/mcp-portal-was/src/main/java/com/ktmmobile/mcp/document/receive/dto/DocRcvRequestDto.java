package com.ktmmobile.mcp.document.receive.dto;

import java.io.Serializable;

public class DocRcvRequestDto implements Serializable {
    private static final long serialVersionUID = -6510970033252597452L;

    private String docRcvId;
    private String rcvUrlId;
    private String otp;

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

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
