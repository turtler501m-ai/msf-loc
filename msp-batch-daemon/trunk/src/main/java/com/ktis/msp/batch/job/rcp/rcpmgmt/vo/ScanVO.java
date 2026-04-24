package com.ktis.msp.batch.job.rcp.rcpmgmt.vo;

import com.ktis.msp.base.BaseVo;

public class ScanVO extends BaseVo {

	private String requestKey;
	private String userId;
	private String scanPath;
	private String scanUrl;
	private String resNo;
	
	public String getRequestKey() {
		return requestKey;
	}
	public void setRequestKey(String requestKey) {
		this.requestKey = requestKey;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getScanPath() {
		return scanPath;
	}
	public void setScanPath(String scanPath) {
		this.scanPath = scanPath;
	}
	public String getScanUrl() {
		return scanUrl;
	}
	public void setScanUrl(String scanUrl) {
		this.scanUrl = scanUrl;
	}
	public String getResNo() {
		return resNo;
	}
	public void setResNo(String resNo) {
		this.resNo = resNo;
	}
	
}
