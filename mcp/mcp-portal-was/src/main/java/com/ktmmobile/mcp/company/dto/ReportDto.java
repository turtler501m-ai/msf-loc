package com.ktmmobile.mcp.company.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ktmmobile.mcp.common.util.ParseHtmlTagUtil;


/**
 * @Class Name : reportDto
 * @Description : 윤리 위반신고 DTO
 *
 * @author : ant
 * @Create Date : 2018. 3. 29
 */
public class ReportDto implements Serializable{
    
    private static final long serialVersionUID = 1L;

    private int rnum;
    private int reportSeq;          
    private String reportCertNumber;  
    private String reportName;         
    private String reportMobileNo;    
    private String reportEmail;        
    private String reportTitle;        
    private String reportContent;      
    private String reportFileName;    
    private String reportOrgFileName;
    private String reportResultNotic; 
    private String reportStatus;       
    private String reportRegDt;       
    private String reportAnswerDt; 
    private String reportAdminId;
    private String reportAnswer;
    private String reportRcvCpltDt;
    private String smsSendYn;
    private String reportStatusText;
    private String searchType;
    private String searchText;
    
    public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public int getRnum() {
		return rnum;
	}
	public void setRnum(int rnum) {
		this.rnum = rnum;
	}
	public String getSmsSendYn() {
		return smsSendYn;
	}
	public void setSmsSendYn(String smsSendYn) {
		this.smsSendYn = smsSendYn;
	}
	public int getReportSeq() {
		return reportSeq;
	}
	public void setReportSeq(int reportSeq) {
		this.reportSeq = reportSeq;
	}
	public String getReportCertNumber() {
		return reportCertNumber;
	}
	public void setReportCertNumber(String reportCertNumber) {
		this.reportCertNumber = reportCertNumber;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = ParseHtmlTagUtil.parseTag(reportName);
	}
	public String getReportMobileNo() {
		return reportMobileNo;
	}
	public void setReportMobileNo(String reportMobileNo) {
		this.reportMobileNo = ParseHtmlTagUtil.parseTag(reportMobileNo);
	}
	public String getReportEmail() {
		return reportEmail;
	}
	public void setReportEmail(String reportEmail) {
		this.reportEmail = ParseHtmlTagUtil.parseTag(reportEmail);
	}
	public String getReportTitle() {
		return reportTitle;
	}
	public void setReportTitle(String reportTitle) {
		this.reportTitle = ParseHtmlTagUtil.parseTag(reportTitle);
	}
	public String getReportContent() {
		return reportContent;
	}
	public void setReportContent(String reportContent) {
		this.reportContent = ParseHtmlTagUtil.parseTag(reportContent);
	}
	public String getReportFileName() {
		return reportFileName;
	}
	public void setReportFileName(String reportFileName) {
		this.reportFileName = reportFileName;
	}
	public String getReportOrgFileName() {
		return reportOrgFileName;
	}
	public void setReportOrgFileName(String reportOrgFileName) {
		this.reportOrgFileName = reportOrgFileName;
	}
	public String getReportResultNotic() {
		return reportResultNotic;
	}
	public void setReportResultNotic(String reportResultNotic) {
		this.reportResultNotic = reportResultNotic;
	}
	public String getReportStatus() {
		return reportStatus;
	}
	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}
	public String getReportRegDt() {
		return reportRegDt;
	}
	public void setReportRegDt(String reportRegDt) {
		this.reportRegDt = reportRegDt;
	}
	public String getReportAnswerDt() {
		return reportAnswerDt;
	}
	public void setReportAnswerDt(String reportAnswerDt) {
		this.reportAnswerDt = reportAnswerDt;
	}
	public String getReportAdminId() {
		return reportAdminId;
	}
	public void setReportAdminId(String reportAdminId) {
		this.reportAdminId = reportAdminId;
	}
	public String getReportAnswer() {
		return reportAnswer;
	}
	public void setReportAnswer(String reportAnswer) {
		this.reportAnswer = reportAnswer;
	}
	public String getReportRcvCpltDt() {
		return reportRcvCpltDt;
	}
	public void setReportRcvCpltDt(String reportRcvCpltDt) {
		this.reportRcvCpltDt = reportRcvCpltDt;
	}
	public String getReportStatusText() {
		if (this.reportStatus.equals("B")) {
			reportStatusText = "접수완료";
		} else if (this.reportStatus.equals("C")) {
			reportStatusText = "답변완료";
		} else if (this.reportStatus.equals("A")) {
			reportStatusText = "접수중";
		}		
		return reportStatusText;
	}
	public void setReportStatusText(String reportStatusText) {
		
		this.reportStatusText = reportStatusText;
	}
	
	
}
