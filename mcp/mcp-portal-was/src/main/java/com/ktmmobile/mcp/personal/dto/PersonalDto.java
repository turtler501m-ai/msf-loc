package com.ktmmobile.mcp.personal.dto;

import java.io.Serializable;

public class PersonalDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String seq;
	private String pageType;
	private String landing;
	private String contractNum;
	private String svcCntrNo;
	private String cntrMobileNo;

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getPageType() {
		return pageType;
	}

	public void setPageType(String pageType) {
		this.pageType = pageType;
	}

	public String getLanding() {
		return landing;
	}

	public void setLanding(String landing) {
		this.landing = landing;
	}
	public String getContractNum() {
		return contractNum;
	}

	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}

	public String getSvcCntrNo() {
		return svcCntrNo;
	}

	public void setSvcCntrNo(String svcCntrNo) {
		this.svcCntrNo = svcCntrNo;
	}

	public String getCntrMobileNo() {
		return cntrMobileNo;
	}

	public void setCntrMobileNo(String cntrMobileNo) {
		this.cntrMobileNo = cntrMobileNo;
	}

}
