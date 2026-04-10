package com.ktmmobile.mcp.document.receive.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ForScanResponseDto {
    private String custNm;
    private String workId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String scanId;

    public String getCustNm() {
        return custNm;
    }

    public void setCustNm(String custNm) {
        this.custNm = custNm;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getScanId() {
        return scanId;
    }

    public void setScanId(String scanId) {
        this.scanId = scanId;
    }
}
