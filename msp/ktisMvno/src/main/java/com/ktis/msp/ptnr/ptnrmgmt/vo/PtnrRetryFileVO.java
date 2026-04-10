package com.ktis.msp.ptnr.ptnrmgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class PtnrRetryFileVO extends BaseVo {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7547188280448933351L;
	
	private String partnerId;
	private String fileNm;
	private String ifNo;
	private String upDir;
	private String downDir;
	private String localUpDir;
	private String localDownDir;
	private String hostUpDir;
	private String hostDownDir;
	
	private String sendFlag;
	private String sendResult;
	
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public String getFileNm() {
		return fileNm;
	}
	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}
	public String getIfNo() {
		return ifNo;
	}
	public void setIfNo(String ifNo) {
		this.ifNo = ifNo;
	}
	public String getUpDir() {
		return upDir;
	}
	public void setUpDir(String upDir) {
		this.upDir = upDir;
	}
	public String getDownDir() {
		return downDir;
	}
	public void setDownDir(String downDir) {
		this.downDir = downDir;
	}
	public String getLocalUpDir() {
		return localUpDir;
	}
	public void setLocalUpDir(String localUpDir) {
		this.localUpDir = localUpDir;
	}
	public String getLocalDownDir() {
		return localDownDir;
	}
	public void setLocalDownDir(String localDownDir) {
		this.localDownDir = localDownDir;
	}
	public String getHostUpDir() {
		return hostUpDir;
	}
	public void setHostUpDir(String hostUpDir) {
		this.hostUpDir = hostUpDir;
	}
	public String getHostDownDir() {
		return hostDownDir;
	}
	public void setHostDownDir(String hostDownDir) {
		this.hostDownDir = hostDownDir;
	}
	public String getSendFlag() {
		return sendFlag;
	}
	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
	}
	public String getSendResult() {
		return sendResult;
	}
	public void setSendResult(String sendResult) {
		this.sendResult = sendResult;
	}
	
}
