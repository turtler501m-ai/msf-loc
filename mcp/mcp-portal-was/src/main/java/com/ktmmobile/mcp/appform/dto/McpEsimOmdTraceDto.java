package com.ktmmobile.mcp.appform.dto;

import java.io.Serializable;
import java.util.Date;

public class McpEsimOmdTraceDto implements Serializable{

	private static final long serialVersionUID = 1L;

	private int uploadPhoneSrlNo;
	private int seq;
	private String subscriberNo;
	private String globalNo;
	private String eventCode;
	private String trtmRsltSmst;
	private String prcsSbst;
	private String rsltCd;
	private String accessIp;
	private String accessUrl;
	private String sysDt;
	private Date sysRdate;
	private String eid;


	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public int getUploadPhoneSrlNo() {
		return uploadPhoneSrlNo;
	}
	public void setUploadPhoneSrlNo(int uploadPhoneSrlNo) {
		this.uploadPhoneSrlNo = uploadPhoneSrlNo;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getSubscriberNo() {
		return subscriberNo;
	}
	public void setSubscriberNo(String subscriberNo) {
		this.subscriberNo = subscriberNo;
	}
	public String getGlobalNo() {
		return globalNo;
	}
	public void setGlobalNo(String globalNo) {
		this.globalNo = globalNo;
	}
	public String getEventCode() {
		return eventCode;
	}
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}
	public String getTrtmRsltSmst() {
		return trtmRsltSmst;
	}
	public void setTrtmRsltSmst(String trtmRsltSmst) {
		this.trtmRsltSmst = trtmRsltSmst;
	}
	public String getPrcsSbst() {
		return prcsSbst;
	}
	public void setPrcsSbst(String prcsSbst) {
		this.prcsSbst = prcsSbst;
	}
	public String getRsltCd() {
		return rsltCd;
	}
	public void setRsltCd(String rsltCd) {
		this.rsltCd = rsltCd;
	}
	public String getAccessIp() {
		return accessIp;
	}
	public void setAccessIp(String accessIp) {
		this.accessIp = accessIp;
	}
	public String getAccessUrl() {
		return accessUrl;
	}
	public void setAccessUrl(String accessUrl) {
		this.accessUrl = accessUrl;
	}
	public String getSysDt() {
		return sysDt;
	}
	public void setSysDt(String sysDt) {
		this.sysDt = sysDt;
	}
	public Date getSysRdate() {
		return sysRdate;
	}
	public void setSysRdate(Date sysRdate) {
		this.sysRdate = sysRdate;
	}




}
