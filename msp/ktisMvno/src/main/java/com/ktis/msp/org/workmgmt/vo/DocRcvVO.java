package com.ktis.msp.org.workmgmt.vo;

import java.io.Serializable;
import java.util.List;

public class DocRcvVO implements Serializable {
    private static final long serialVersionUID = -6510970033252597452L;

    private String docRcvId;
    private String resNo;
    private int targetSeq;
    private String requestDay;
    private String requestDt;
    private String workTmplId;
    private String smsTemplateId;
    private String mobileNo;
    private String scanId;
    private String userId;
    private String menuId;
    private String expireDt;
    private List<Long> itemSeqList;

    public static DocRcvVO of(DocRcvRequestVO docRcvRequest) {
        DocRcvVO docRcvVO = new DocRcvVO();
        docRcvVO.setDocRcvId(docRcvRequest.getDocRcvId());
        docRcvVO.setResNo(docRcvRequest.getResNo());
        docRcvVO.setWorkTmplId(docRcvRequest.getWorkTmplId());
        docRcvVO.setSmsTemplateId(docRcvRequest.getSmsTemplateId());
        docRcvVO.setMobileNo(docRcvRequest.getMobileNo());
        docRcvVO.setScanId(docRcvRequest.getScanId());
        docRcvVO.setUserId(docRcvRequest.getSessionUserId());
        docRcvVO.setMenuId(docRcvRequest.getSessionMenuId());
        docRcvVO.setExpireDt(docRcvRequest.getExpireDt());
        docRcvVO.setItemSeqList(docRcvRequest.getItemSeqList());
        return docRcvVO;
    }

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

    public String getSmsTemplateId() {
        return smsTemplateId;
    }

    public void setSmsTemplateId(String smsTemplateId) {
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

    public String getExpireDt() {
        return expireDt;
    }

    public void setExpireDt(String expireDt) {
        this.expireDt = expireDt;
    }

    public List<Long> getItemSeqList() {
        return itemSeqList;
    }

    public void setItemSeqList(List<Long> itemSeqList) {
        this.itemSeqList = itemSeqList;
    }
}
