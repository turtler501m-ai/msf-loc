package com.ktmmobile.mcp.content.dto;

public class ReShareDataResDto {

	private String contractNum;//서비스계약번호
	private String mySvcNo; //자회선 핸드폰번호
	private String mySocNm; //자회선 요금제명
	private String reqSvcNo; // 함께쓰기 모회선 핸드폰번호
	private String reqCustName; //함께쓰기 모회선 이름
	private String reqSocNm; //함께쓰기 모회선 요금제
	private String mskReqSvcNo; //마스킹된 모회선 핸드폰번호
	
	private String ncn; 		// 서비스계약번호

	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getMySvcNo() {
		return mySvcNo;
	}
	public void setMySvcNo(String mySvcNo) {
		this.mySvcNo = mySvcNo;
	}
	public String getMySocNm() {
		return mySocNm;
	}
	public void setMySocNm(String mySocNm) {
		this.mySocNm = mySocNm;
	}
	public String getReqSvcNo() {
		return reqSvcNo;
	}
	public void setReqSvcNo(String reqSvcNo) {
		this.reqSvcNo = reqSvcNo;
	}
	public String getReqCustName() {
		return reqCustName;
	}
	public void setReqCustName(String reqCustName) {
		this.reqCustName = reqCustName;
	}
	public String getReqSocNm() {
		return reqSocNm;
	}
	public void setReqSocNm(String reqSocNm) {
		this.reqSocNm = reqSocNm;
	}
	public String getMskReqSvcNo() {
		return mskReqSvcNo;
	}
	public void setMskReqSvcNo(String mskReqSvcNo) {
		this.mskReqSvcNo = mskReqSvcNo;
	}

	public String getNcn() {
		return ncn;
	}

	public void setNcn(String ncn) {
		this.ncn = ncn;
	}
}
