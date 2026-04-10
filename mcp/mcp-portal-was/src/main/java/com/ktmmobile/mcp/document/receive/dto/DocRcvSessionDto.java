package com.ktmmobile.mcp.document.receive.dto;

import java.io.Serializable;

public class DocRcvSessionDto implements Serializable {
    private static final long serialVersionUID = -6510970033252597452L;

    private String docRcvId;
    private String lastAuthDt;

    public DocRcvSessionDto(String docRcvId, String lastAuthDt) {
        this.docRcvId = docRcvId;
        this.lastAuthDt = lastAuthDt;
    }

    public String getDocRcvId() {
        return docRcvId;
    }

    public void setDocRcvId(String docRcvId) {
        this.docRcvId = docRcvId;
    }

    public String getLastAuthDt() {
        return lastAuthDt;
    }

    public void setLastAuthDt(String lastAuthDt) {
        this.lastAuthDt = lastAuthDt;
    }
}
