package com.ktmmobile.mcp.document.receive.dto;

import java.util.List;

public class DocRcvRequestDto {

    private String docRcvId;
    private String scanId;
    private List<DocRcvItemDto> items;
    private String successYn;

    private String serviceId;

    public String getDocRcvId() {
        return docRcvId;
    }

    public void setDocRcvId(String docRcvId) {
        this.docRcvId = docRcvId;
    }

    public String getScanId() {
        return scanId;
    }

    public void setScanId(String scanId) {
        this.scanId = scanId;
    }

    public List<DocRcvItemDto> getItems() {
        return items;
    }

    public void setItems(List<DocRcvItemDto> items) {
        this.items = items;
    }

    public String getSuccessYn() {
        return successYn;
    }

    public void setSuccessYn(String successYn) {
        this.successYn = successYn;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
