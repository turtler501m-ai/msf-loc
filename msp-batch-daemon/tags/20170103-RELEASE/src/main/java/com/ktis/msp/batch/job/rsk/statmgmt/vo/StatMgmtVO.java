package com.ktis.msp.batch.job.rsk.statmgmt.vo;


public class StatMgmtVO {
	
	// 조회조건
	private String trgtYm;
	private String strtYm;
	private String endYm;
	private String pppo;
	private String contractNum;
	private String unpdYn;
	
	private String userId;
	
	public String getTrgtYm() {
		return trgtYm;
	}
	public void setTrgtYm(String trgtYm) {
		this.trgtYm = trgtYm;
	}
	public String getStrtYm() {
		return strtYm;
	}
	public void setStrtYm(String strtYm) {
		this.strtYm = strtYm;
	}
	public String getEndYm() {
		return endYm;
	}
	public void setEndYm(String endYm) {
		this.endYm = endYm;
	}
	public String getPppo() {
		return pppo;
	}
	public void setPppo(String pppo) {
		this.pppo = pppo;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getUnpdYn() {
		return unpdYn;
	}
	public void setUnpdYn(String unpdYn) {
		this.unpdYn = unpdYn;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
