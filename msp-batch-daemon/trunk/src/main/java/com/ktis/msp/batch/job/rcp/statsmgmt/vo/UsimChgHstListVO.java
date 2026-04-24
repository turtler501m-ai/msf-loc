package com.ktis.msp.batch.job.rcp.statsmgmt.vo;

import com.ktis.msp.base.BaseVo;

public class UsimChgHstListVO extends BaseVo  {
	
	// 엑셀 다운로드
	private String lstComActvDate;
	private String contractNum;
	private String svcCntrNo;
	private String subStatusNm;
	private String usimModnm;
//	private String usimNo;
	private String usimChgDate;
	private String chgType;
	private String hdnYn;
		
	
	// 검색조건	
	private String srchStrtDt;
	private String srchEndDt;
	private String searchName;
	private String searchGbn;
	
	private String seq;
	
	private String userId;
	
	
	// 복호화
	private String usimNo;				/* 유심일련번호    */
	
	// 엑셀다운로드 로그
	private String dwnldRsn;			/*다운로드 사유*/
	private String exclDnldId;	
	private String ipAddr;				/*ip정보*/
	private String menuId;				/*메뉴ID*/
	public String getLstComActvDate() {
		return lstComActvDate;
	}
	public void setLstComActvDate(String lstComActvDate) {
		this.lstComActvDate = lstComActvDate;
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
	public String getSubStatusNm() {
		return subStatusNm;
	}
	public void setSubStatusNm(String subStatusNm) {
		this.subStatusNm = subStatusNm;
	}
	public String getUsimModnm() {
		return usimModnm;
	}
	public void setUsimModnm(String usimModnm) {
		this.usimModnm = usimModnm;
	}
	public String getUsimChgDate() {
		return usimChgDate;
	}
	public void setUsimChgDate(String usimChgDate) {
		this.usimChgDate = usimChgDate;
	}
	public String getChgType() {
		return chgType;
	}
	public void setChgType(String chgType) {
		this.chgType = chgType;
	}
	public String getHdnYn() {
		return hdnYn;
	}
	public void setHdnYn(String hdnYn) {
		this.hdnYn = hdnYn;
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
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUsimNo() {
		return usimNo;
	}
	public void setUsimNo(String usimNo) {
		this.usimNo = usimNo;
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
	
}
