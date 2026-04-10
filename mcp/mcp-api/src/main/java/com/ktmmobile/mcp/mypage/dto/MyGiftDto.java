package com.ktmmobile.mcp.mypage.dto;

import java.io.Serializable;

public class MyGiftDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String  contractNum ;	// 계약번호
	private String  prmtNm ;		// 사은품 지급사유
	private String  prdtNm ;		// 사은품 명
	private String  regstDttm ;		// 등록일(신청일)

	private int apiParam1 = 0;
    private int apiParam2 = 0;

    public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getPrmtNm() {
		return prmtNm;
	}
	public void setPrmtNm(String prmtNm) {
		this.prmtNm = prmtNm;
	}
	public String getPrdtNm() {
		return prdtNm;
	}
	public void setPrdtNm(String prdtNm) {
		this.prdtNm = prdtNm;
	}
	public String getRegstDttm() {
		return regstDttm;
	}
	public void setRegstDttm(String regstDttm) {
		this.regstDttm = regstDttm;
	}
	public int getApiParam1() {
		return apiParam1;
	}
	public void setApiParam1(int apiParam1) {
		this.apiParam1 = apiParam1;
	}
	public int getApiParam2() {
		return apiParam2;
	}
	public void setApiParam2(int apiParam2) {
		this.apiParam2 = apiParam2;
	}


}