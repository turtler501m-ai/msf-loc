package com.ktis.msp.org.workmgmt.vo;

import java.io.Serializable;
import java.util.List;

/**
 * NMCP_DOC_RCV@DL_MCP 상세 조회용 VO
 */
public class DocRcvDetailVO implements Serializable {
    private static final long serialVersionUID = -6510970033252597452L;

    private String docRcvId;
    private String resNo;
    private int targetSeq;
    private String requestDay;
    private String requestDt;
    private String workTmplId;
    private String smsTemplateId;
    private String cstmrName;
    private String mobileNo;
    private String scanId;

    private String workId;
    private String workNm;
    private String workTmplNm;

    private DocRcvStatusVO docRcvStatusVO;
    private List<DocRcvItemVO> itemList;

    private DocRcvUrlVO docRcvUrlVO;
    private DocRcvUrlOtpVO docRcvUrlOtpVO;

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

    public String getSmsTemplateId() {
        return smsTemplateId;
    }

    public void setSmsTemplateId(String smsTemplateId) {
        this.smsTemplateId = smsTemplateId;
    }

    public String getCstmrName() {
        return cstmrName;
    }

    public void setCstmrName(String cstmrName) {
        this.cstmrName = cstmrName;
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

    public DocRcvStatusVO getDocRcvStatusVO() {
        return docRcvStatusVO;
    }

    public void setDocRcvStatusVO(DocRcvStatusVO docRcvStatusVO) {
        this.docRcvStatusVO = docRcvStatusVO;
    }

    public List<DocRcvItemVO> getItemList() {
        return itemList;
    }

    public void setItemList(List<DocRcvItemVO> itemList) {
        this.itemList = itemList;
    }

    public DocRcvUrlVO getDocRcvUrlVO() {
        return docRcvUrlVO;
    }

    public void setDocRcvUrlVO(DocRcvUrlVO docRcvUrlVO) {
        this.docRcvUrlVO = docRcvUrlVO;
    }

    public DocRcvUrlOtpVO getDocRcvUrlOtpVO() {
        return docRcvUrlOtpVO;
    }

    public void setDocRcvUrlOtpVO(DocRcvUrlOtpVO docRcvUrlOtpVO) {
        this.docRcvUrlOtpVO = docRcvUrlOtpVO;
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
