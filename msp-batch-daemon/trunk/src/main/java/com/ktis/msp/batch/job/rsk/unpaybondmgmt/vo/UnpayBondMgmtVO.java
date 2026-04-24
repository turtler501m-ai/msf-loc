package com.ktis.msp.batch.job.rsk.unpaybondmgmt.vo;


public class UnpayBondMgmtVO {
	
	private String stdrYm; //기준월
	private String strtYm; //청구월 시작
	private String endYm;  //청구월 종료
	
	private String userId;
	
	// 엑셀다운로드 로그
	private String dwnldRsn    ;	/*다운로드 사유*/
	private String exclDnldId  ;	
	private String ipAddr      ;	/*ip정보*/
	private String menuId      ;	/*메뉴ID*/
	
	
	public String getDwnldRsn() {
		return dwnldRsn;
	}
	public void setDwnldRsn(String dwnldRsn) {
		this.dwnldRsn = dwnldRsn;
	}
	public String getExclDnldId() {
		return exclDnldId;
	}
	public void setExclDnldId(String exclDnldId) {
		this.exclDnldId = exclDnldId;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getStdrYm() {
		return stdrYm;
	}
	public void setStdrYm(String stdrYm) {
		this.stdrYm = stdrYm;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
