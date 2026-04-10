package com.ktis.msp.img.applicationForm.vo;


public class ScanInfoVo {
	
	//가입 예약 번호
	String resNo;
	//스캔 아이디
	String scanId;
	//사용자 아이디
	String userId;
	//사용자 이름
	String userNm;
	//조직 유형 - S:판매점, D:대리점, K:개통센터
	String orgType;
	//소속 조직 아이디
	String orgOid;
	//소속 조직명
	String orgNm;
	//MCP, MSP 구분
	String callType;
	String url;
	String port;
	String version;
	String downloadUrl;
	String regUserName;
	String scanType;
	
	String rgstDt;
	String requestStateCode;

	
	
	
	public String getScanType() {
		return scanType;
	}
	public void setScanType(String scanType) {
		this.scanType = scanType;
	}
	public String getRegUserName() {
		return regUserName;
	}
	public void setRegUserName(String regUserName) {
		this.regUserName = regUserName;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCallType() {
		return callType;
	}
	public void setCallType(String callType) {
		this.callType = callType;
	}
	public String getResNo() {
		return resNo;
	}
	public void setResNo(String resNo) {
		this.resNo = resNo;
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
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	public String getOrgType() {
		return orgType;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	public String getOrgOid() {
		return orgOid;
	}
	public void setOrgOid(String orgOid) {
		this.orgOid = orgOid;
	}
	public String getOrgNm() {
		return orgNm;
	}
	public void setOrgNm(String orgNm) {
		this.orgNm = orgNm;
	}
	public String getRgstDt() {
		return rgstDt;
	}
	public void setRgstDt(String rgstDt) {
		this.rgstDt = rgstDt;
	}
	public String getRequestStateCode() {
		return requestStateCode;
	}
	public void setRequestStateCode(String requestStateCode) {
		this.requestStateCode = requestStateCode;
	}
	
}
