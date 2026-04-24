package com.ktis.msp.batch.job.rsk.rskmgmt.vo;

import com.ktis.msp.base.BaseVo;

public class RskMgmtVO extends BaseVo {

	/*조회조건*/
	private String strtDt;
	private String endDt;
	private String searchType;
	private String orgnId;
	private String serviceType;
	private String searchCd;
	private String searchVal;
	private String userId;
	
	// 부가상품가입자조회조건
	private String searchGbn;
	private String searchName;
	
	// 엑셀다운로드 로그
	private String dwnldRsn    ;	/*다운로드 사유*/
	private String exclDnldId  ;	
	private String ipAddr      ;	/*ip정보*/
	private String menuId      ;	/*메뉴ID*/
	
	
	public String getStrtDt() {
		return strtDt;
	}
	public void setStrtDt(String strtDt) {
		this.strtDt = strtDt;
	}
	public String getEndDt() {
		return endDt;
	}
	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getOrgnId() {
		return orgnId;
	}
	public void setOrgnId(String orgnId) {
		this.orgnId = orgnId;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
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
	public String getSearchGbn() {
		return searchGbn;
	}
	public void setSearchGbn(String searchGbn) {
		this.searchGbn = searchGbn;
	}
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
}
