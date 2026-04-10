package com.ktmmobile.mcp.document.receive.dto;

import java.io.Serializable;
import java.util.List;

public class DocRcvDetailDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String docRcvId;
    private String resNo;
    private int targetSeq;
    private String requestDay;
    private String requestDt;
    private String workTmplId;
    private int smsTemplateId;
    private String mobileNo;
    private String scanId;

    private String workId;
    private String workNm;
    private String workTmplNm;

    private DocRcvStatusDto docRcvStatusDto;
    private List<DocRcvItemDto> itemList;

    private DocRcvUrlDto docRcvUrlDto;
    private DocRcvUrlOtpDto docRcvUrlOtpDto;

    private String userId;
    private String menuId;

    public String getDocRcvId() {
        return docRcvId;
    }

    public void setDocRcvId(String docRcvId) {
        this.docRcvId = docRcvId;
    }

    public String getResNo() {
        return resNo;
    }

    public void setResNo(String resNo) {
        this.resNo = resNo;
    }

    public int getTargetSeq() {
        return targetSeq;
    }

    public void setTargetSeq(int targetSeq) {
        this.targetSeq = targetSeq;
    }

    public String getRequestDay() {
        return requestDay;
    }

    public void setRequestDay(String requestDay) {
        this.requestDay = requestDay;
    }

    public String getRequestDt() {
        return requestDt;
    }

    public void setRequestDt(String requestDt) {
        this.requestDt = requestDt;
    }

    public String getWorkTmplId() {
        return workTmplId;
    }

    public void setWorkTmplId(String workTmplId) {
        this.workTmplId = workTmplId;
    }

    public int getSmsTemplateId() {
        return smsTemplateId;
    }

    public void setSmsTemplateId(int smsTemplateId) {
        this.smsTemplateId = smsTemplateId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getScanId() {
        return scanId;
    }

    public void setScanId(String scanId) {
        this.scanId = scanId;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getWorkNm() {
        return workNm;
    }

    public void setWorkNm(String workNm) {
        this.workNm = workNm;
    }

    public String getWorkTmplNm() {
        return workTmplNm;
    }

    public void setWorkTmplNm(String workTmplNm) {
        this.workTmplNm = workTmplNm;
    }

    public DocRcvStatusDto getDocRcvStatusDto() {
        return docRcvStatusDto;
    }

    public void setDocRcvStatusDto(DocRcvStatusDto docRcvStatusDto) {
        this.docRcvStatusDto = docRcvStatusDto;
    }

    public List<DocRcvItemDto> getItemList() {
        return itemList;
    }

    public void setItemList(List<DocRcvItemDto> itemList) {
        this.itemList = itemList;
    }

    public DocRcvUrlDto getDocRcvUrlDto() {
        return docRcvUrlDto;
    }

    public void setDocRcvUrlDto(DocRcvUrlDto docRcvUrlDto) {
        this.docRcvUrlDto = docRcvUrlDto;
    }

    public DocRcvUrlOtpDto getDocRcvUrlOtpDto() {
        return docRcvUrlOtpDto;
    }

    public void setDocRcvUrlOtpDto(DocRcvUrlOtpDto docRcvUrlOtpDto) {
        this.docRcvUrlOtpDto = docRcvUrlOtpDto;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
}
