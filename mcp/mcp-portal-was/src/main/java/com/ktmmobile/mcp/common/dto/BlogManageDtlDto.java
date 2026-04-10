package com.ktmmobile.mcp.common.dto;

import java.util.List;
import java.io.Serializable;
import org.springframework.web.multipart.MultipartFile;

public class BlogManageDtlDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int downloadLimitCount;
    private int downloadCount;
    private String downloadExpireDate;
    private int attSeq;
    private int ctgSeq;
    private int ntcartSeq;
    private String filePathNm;
    private String fileType;
    private int fileCapa;
    private String cretId;
    private String amdId;
    private String cretDt;
    private String amdDt;
    private List<MultipartFile> multiFiles;
    public int getDownloadLimitCount() {
        return downloadLimitCount;
    }
    public void setDownloadLimitCount(int downloadLimitCount) {
        this.downloadLimitCount = downloadLimitCount;
    }
    public int getDownloadCount() {
        return downloadCount;
    }
    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }
    public String getDownloadExpireDate() {
        return downloadExpireDate;
    }
    public void setDownloadExpireDate(String downloadExpireDate) {
        this.downloadExpireDate = downloadExpireDate;
    }
    public int getAttSeq() {
        return attSeq;
    }
    public void setAttSeq(int attSeq) {
        this.attSeq = attSeq;
    }
    public int getCtgSeq() {
        return ctgSeq;
    }
    public void setCtgSeq(int ctgSeq) {
        this.ctgSeq = ctgSeq;
    }
    public int getNtcartSeq() {
        return ntcartSeq;
    }
    public void setNtcartSeq(int ntcartSeq) {
        this.ntcartSeq = ntcartSeq;
    }
    public String getFilePathNm() {
        return filePathNm;
    }
    public void setFilePathNm(String filePathNm) {
        this.filePathNm = filePathNm;
    }
    public String getFileType() {
        return fileType;
    }
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
    public int getFileCapa() {
        return fileCapa;
    }
    public void setFileCapa(int fileCapa) {
        this.fileCapa = fileCapa;
    }
    public String getCretId() {
        return cretId;
    }
    public void setCretId(String cretId) {
        this.cretId = cretId;
    }
    public String getAmdId() {
        return amdId;
    }
    public void setAmdId(String amdId) {
        this.amdId = amdId;
    }
    public String getCretDt() {
        return cretDt;
    }
    public void setCretDt(String cretDt) {
        this.cretDt = cretDt;
    }
    public String getAmdDt() {
        return amdDt;
    }
    public void setAmdDt(String amdDt) {
        this.amdDt = amdDt;
    }
    public List<MultipartFile> getMultiFiles() {
        return multiFiles;
    }
    public void setMultiFiles(List<MultipartFile> multiFiles) {
        this.multiFiles = multiFiles;
    }


}
