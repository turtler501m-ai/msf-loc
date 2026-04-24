package com.ktis.msp.batch.job.rcp.rcpmgmt.vo;

import com.ktis.msp.base.BaseVo;

public class Daily3gSubVO extends BaseVo  {
	
	// 조회 조건
	private String stdrDt;
	private String subStatus;
	private String searchCd;
	private String searchVal;
	private String lstModelTp;
	private String lstRateTp;
	private String lstUsimTp;
	private String volteYn;

	// 엑셀다운로드 로그
	private String dwnldRsn;	/*다운로드 사유*/
	private String exclDnldId;	
	private String ipAddr;		/*ip정보*/
	private String userId;
	private String menuId;
	
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getStdrDt() {
		return stdrDt;
	}
	public void setStdrDt(String stdrDt) {
		this.stdrDt = stdrDt;
	}
	public String getSubStatus() {
		return subStatus;
	}
	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}
	public String getSearchCd() {
		return searchCd;
	}
	public void setSearchCd(String searchCd) {
		this.searchCd = searchCd;
	}
	public String getSearchVal() {
		return searchVal;
	}
	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
	}
	public String getLstModelTp() {
		return lstModelTp;
	}
	public void setLstModelTp(String lstModelTp) {
		this.lstModelTp = lstModelTp;
	}
	public String getLstRateTp() {
		return lstRateTp;
	}
	public void setLstRateTp(String lstRateTp) {
		this.lstRateTp = lstRateTp;
	}
	public String getLstUsimTp() {
		return lstUsimTp;
	}
	public void setLstUsimTp(String lstUsimTp) {
		this.lstUsimTp = lstUsimTp;
	}
	public String getVolteYn() {
		return volteYn;
	}
	public void setVolteYn(String volteYn) {
		this.volteYn = volteYn;
	}
	
}
