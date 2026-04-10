package com.ktmmobile.mcp.content.dto;

public class MyShareDataResDto {

	private String svcNo; //본인핸드폰번호
	private String socNm; //본인요금제
	private String opmdSvcSocNm; //데이터쉐어링 가입하는 요금제
	private String opmdSvcNo; //데이터쉐어링 가입한사람 핸드폰번호
	public String getSvcNo() {
		return svcNo;
	}
	public void setSvcNo(String svcNo) {
		this.svcNo = svcNo;
	}
	public String getSocNm() {
		return socNm;
	}
	public void setSocNm(String socNm) {
		this.socNm = socNm;
	}
	public String getOpmdSvcSocNm() {
		return opmdSvcSocNm;
	}
	public void setOpmdSvcSocNm(String opmdSvcSocNm) {
		this.opmdSvcSocNm = opmdSvcSocNm;
	}
	public String getOpmdSvcNo() {
		return opmdSvcNo;
	}
	public void setOpmdSvcNo(String opmdSvcNo) {
		this.opmdSvcNo = opmdSvcNo;
	}




}
