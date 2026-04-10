package com.ktmmobile.mcp.document.receive.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DocRcvItemDto implements Serializable {
    private static final long serialVersionUID = -6510970033252597452L;

    private long itemSeq;
    private String docRcvId;
    private String itemId;
    private String status;
    private String fileStatusNm;
    private String serviceId;
    private String fileRcvDt;

    private String itemNm;
    private String requestYn;

    private List<DocRcvItemFileDto> files;

    public long getItemSeq() {
        return itemSeq;
    }

    public void setItemSeq(long itemSeq) {
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

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
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

    public List<DocRcvItemFileDto> getFiles() {
        return files;
    }

    public void setFiles(List<DocRcvItemFileDto> files) {
        this.files = files;
    }

    public DocRcvItemDto deepCopy() {
        DocRcvItemDto copy = new DocRcvItemDto();
        copy.setItemSeq(itemSeq);
        copy.setDocRcvId(docRcvId);
        copy.setItemId(itemId);
        copy.setStatus(status);
        copy.setFileStatusNm(fileStatusNm);
        copy.setServiceId(serviceId);
        copy.setFileRcvDt(fileRcvDt);
        copy.setItemNm(itemNm);
        copy.setRequestYn(requestYn);
        if (files != null) {
            List<DocRcvItemFileDto> copyFileList = new ArrayList<>();
            for (DocRcvItemFileDto itemFile : files) {
                copyFileList.add(itemFile.deepCopy());
            }
            copy.setFiles(copyFileList);
        }
        return copy;
    }
}
