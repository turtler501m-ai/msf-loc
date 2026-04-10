package com.ktmmobile.mcp.event.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class NmcpSurveyDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String resNo;     
	private Long requestKey;
	private String surveyCd;   
	private String surveyAns; 
	private String surveyEtc;
	private Date sysRdate;		
	private List<NmcpSurveyDto> nmcpSurveyDtoList; 	
			
	
	public Long getRequestKey() {
		return requestKey;
	}
	public void setRequestKey(Long requestKey) {
		this.requestKey = requestKey;
	}
	public String getResNo() {
		return resNo;
	}
	public void setResNo(String resNo) {
		this.resNo = resNo;
	}
	public String getSurveyCd() {
		return surveyCd;
	}
	public void setSurveyCd(String surveyCd) {
		this.surveyCd = surveyCd;
	}
	public String getSurveyAns() {
		return surveyAns;
	}
	public void setSurveyAns(String surveyAns) {
		this.surveyAns = surveyAns;
	}
	public String getSurveyEtc() {
		return surveyEtc;
	}
	public void setSurveyEtc(String surveyEtc) {
		this.surveyEtc = surveyEtc;
	}
	public Date getSysRdate() {
		return sysRdate;
	}
	public void setSysRdate(Date sysRdate) {
		this.sysRdate = sysRdate;
	}
	public List<NmcpSurveyDto> getNmcpSurveyDtoList() {
		return nmcpSurveyDtoList;
	}
	public void setNmcpSurveyDtoList(List<NmcpSurveyDto> nmcpSurveyDtoList) {
		this.nmcpSurveyDtoList = nmcpSurveyDtoList;
	}	
	
	

}
