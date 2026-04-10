package com.ktmmobile.mcp.document.receive.dto;

import java.io.Serializable;

public class DocRcvItemFileDto implements Serializable {
    private static final long serialVersionUID = -6510970033252597452L;

    private long itemSeq;
    private String fileId;
    private String ext;
    private String serviceId;
    private String resultCode;
    private String resultMessage;

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

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public DocRcvItemFileDto deepCopy() {
        DocRcvItemFileDto copy = new DocRcvItemFileDto();
        copy.setItemSeq(itemSeq);
        copy.setFileId(fileId);
        copy.setExt(ext);
        copy.setServiceId(serviceId);
        copy.setResultCode(resultCode);
        copy.setResultMessage(resultMessage);
        return copy;
    }
}
