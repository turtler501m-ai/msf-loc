package com.ktis.msp.batch.job.rcp.selfhpc.vo;

import com.ktis.msp.base.BaseVo;

public class SelfHpcVO extends BaseVo {
	
	//검색조건
	private String srchStrtDt;
	private String srchEndDt;
	private String searchName;
	private String searchGbn;
	private String srchHpcStat;
	private String srchHpcRst;
	private String srchAcenRst;

	// 엑셀다운로드 로그
	private String dwnldRsn    	;	/*다운로드 사유*/
	private String exclDnldId  	;	
	private String ipAddr      	;	/*ip정보*/
	private String userId 		;
	private String menuId;
	
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getSrchStrtDt() {
		return srchStrtDt;
	}
	public void setSrchStrtDt(String srchStrtDt) {
		this.srchStrtDt = srchStrtDt;
	}
	public String getSrchEndDt() {
		return srchEndDt;
	}
	public void setSrchEndDt(String srchEndDt) {
		this.srchEndDt = srchEndDt;
	}
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	public String getSearchGbn() {
		return searchGbn;
	}
	public void setSearchGbn(String searchGbn) {
		this.searchGbn = searchGbn;
	}
	public String getSrchHpcStat() {
		return srchHpcStat;
	}
	public void setSrchHpcStat(String srchHpcStat) {
		this.srchHpcStat = srchHpcStat;
	}
	public String getSrchHpcRst() {
		return srchHpcRst;
	}
	public void setSrchHpcRst(String srchHpcRst) {
		this.srchHpcRst = srchHpcRst;
	}
	public String getSrchAcenRst() {
		return srchAcenRst;
	}
	public void setSrchAcenRst(String srchAcenRst) {
		this.srchAcenRst = srchAcenRst;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
