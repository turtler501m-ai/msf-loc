package com.ktis.msp.batch.job.voc.vocMgmt.vo;


public class GiftPayStatVO {

	private String giftPayStatSeq;
	private String payMon;
	private String contractNum;
	private String custNm;
	private String ctn;
	private String taxRplyYn;
	private String promNm;
	private String giftType;
	private String giftAmt;
	private String subStatus;
	private String openAgntNm;
	private String regstId;

	// 엑셀다운로드 로그
	private String userId;
	private String dwnldRsn    ;	/*다운로드 사유*/
	private String exclDnldId  ;
	private String ipAddr      ;	/*ip정보*/
	private String menuId      ;	/*메뉴ID*/

	// 검색조건
	private String srchStrtDt;
	private String srchEndDt;
	private String searchGbn;
	private String searchName;

	private String fileName;

	public String getGiftPayStatSeq() {
		return giftPayStatSeq;
	}

	public void setGiftPayStatSeq(String giftPayStatSeq) {
		this.giftPayStatSeq = giftPayStatSeq;
	}

	public String getPayMon() {
		return payMon;
	}

	public void setPayMon(String payMon) {
		this.payMon = payMon;
	}

	public String getContractNum() {
		return contractNum;
	}

	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}

	public String getCustNm() {
		return custNm;
	}

	public void setCustNm(String custNm) {
		this.custNm = custNm;
	}

	public String getCtn() {
		return ctn;
	}

	public void setCtn(String ctn) {
		this.ctn = ctn;
	}

	public String getTaxRplyYn() {
		return taxRplyYn;
	}

	public void setTaxRplyYn(String taxRplyYn) {
		this.taxRplyYn = taxRplyYn;
	}

	public String getPromNm() {
		return promNm;
	}

	public void setPromNm(String promNm) {
		this.promNm = promNm;
	}

	public String getGiftType() {
		return giftType;
	}

	public void setGiftType(String giftType) {
		this.giftType = giftType;
	}

	public String getGiftAmt() {
		return giftAmt;
	}

	public void setGiftAmt(String giftAmt) {
		this.giftAmt = giftAmt;
	}

	public String getSubStatus() {
		return subStatus;
	}

	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}

	public String getOpenAgntNm() {
		return openAgntNm;
	}

	public void setOpenAgntNm(String openAgntNm) {
		this.openAgntNm = openAgntNm;
	}

	public String getRegstId() {
		return regstId;
	}

	public void setRegstId(String regstId) {
		this.regstId = regstId;
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
