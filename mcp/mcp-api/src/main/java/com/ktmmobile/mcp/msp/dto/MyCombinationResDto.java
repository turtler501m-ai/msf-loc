package com.ktmmobile.mcp.msp.dto;

public class MyCombinationResDto {

	private String pRateCd;
	private String rRateCd;
	private String rRateNm; // 결합 부가서비스 이름
	private String pRateNm;


	public String getpRateNm() {
		return pRateNm;
	}
	public void setpRateNm(String pRateNm) {
		this.pRateNm = pRateNm;
	}
	public String getrRateNm() {
		return rRateNm;
	}
	public void setrRateNm(String rRateNm) {
		this.rRateNm = rRateNm;
	}
	public String getpRateCd() {
		return pRateCd;
	}
	public void setpRateCd(String pRateCd) {
		this.pRateCd = pRateCd;
	}
	public String getrRateCd() {
		return rRateCd;
	}
	public void setrRateCd(String rRateCd) {
		this.rRateCd = rRateCd;
	}

}
