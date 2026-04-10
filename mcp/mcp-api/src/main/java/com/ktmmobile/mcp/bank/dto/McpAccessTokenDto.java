package com.ktmmobile.mcp.bank.dto;

import java.io.Serializable;

public class McpAccessTokenDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String issueDate;    /*발급일자 */
	private String clientId;       /*CLIENT ID */
	private String clientSecret; /*CLIENT SECRET */
	private String accessToken;  /*ACCESS TOKEN */
	private String strBankCode ;
	private String strAccountNo;
	private String strRequestNo;
	private String strResUniqId; 
	private String strNm       ;

	private Integer cnt;             /*Access Token Count */
	
	private Integer job;            /* 1:token발급, 2:token remove, 3:otp 생성, 4:otp 확인 */
	
	private String otp ;            /*  Otp (입금자) */
	
	private String removeYn;   /* 삭제 여부(Y:토큰 폐기) */
	
	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Integer getCnt() {
		return cnt;
	}

	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}

	public String getStrBankCode() {
		return strBankCode;
	}

	public void setStrBankCode(String strBankCode) {
		this.strBankCode = strBankCode;
	}

	public String getStrAccountNo() {
		return strAccountNo;
	}

	public void setStrAccountNo(String strAccountNo) {
		this.strAccountNo = strAccountNo;
	}


	public String getStrRequestNo() {
		return strRequestNo;
	}

	public void setStrRequestNo(String strRequestNo) {
		this.strRequestNo = strRequestNo;
	}

	public Integer getJob() {
		return job;
	}

	public void setJob(Integer job) {
		this.job = job;
	}

	public String getStrNm() {
		return strNm;
	}

	public void setStrNm(String strNm) {
		this.strNm = strNm;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getStrResUniqId() {
		return strResUniqId;
	}

	public void setStrResUniqId(String strResUniqId) {
		this.strResUniqId = strResUniqId;
	}

	public String getRemoveYn() {
		return removeYn;
	}

	public void setRemoveYn(String removeYn) {
		this.removeYn = removeYn;
	}

	@Override
	public String toString() {
		return "McpAccessTokenVO [issueDate=" + issueDate + ", clientId=" + clientId + ", clientSecret=" + clientSecret
				+ ", accessToken=" + accessToken + ", strBankCode=" + strBankCode + ", strAccountNo=" + strAccountNo
				+ ", strRequestNo=" + strRequestNo + ", strResUniqId=" + strResUniqId + ", strNm=" + strNm + ", cnt="
				+ cnt + ", job=" + job + ", otp=" + otp + ", removeYn=" + removeYn + "]";
	}

}
