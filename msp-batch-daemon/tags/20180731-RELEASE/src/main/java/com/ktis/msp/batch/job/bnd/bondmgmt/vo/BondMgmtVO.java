package com.ktis.msp.batch.job.bnd.bondmgmt.vo;


public class BondMgmtVO {

	private String searchStartDt;
	private String searchEndDt;
	
	private String pSearchName = "";
	private String pSearchGbn = "";
	private String pChrgItemCd = "";
	private String userId = "";
	
	private String bondSeqNo;
	private String billYm;
	private String payCnt;
	
	private String billArrAmtYn;
	private String adjsAmtYn;
	private String midPrfpayAmtYn;
	private String midPayAmtYn;
	private String insrBillYn;
	private String insrPayYn;
	
	private String trgtStrtDt;                // 개통대상시작일자
	private String trgtEndDt;                 // 개통대상종료일자
	private int    trgtRmnAmt;                // 할부잔액
	private int    trgtRmnCnt;                // 잔여개월
	private int    trgtPymCnt;                // 수납개월
	private int    trgtUnpaidCnt;             // 미납회차
	private String trgtUnpaidYn;              // 미납여부
	
	private String searchRemainAmt;			  //검색조건 잔여할부금
	private String searchRemainCnt;			  //검색조건 잔여할부개월수
	private String searchUnpaidCnt;			  //검색조건 미납회차
	
	private String dwnldRsn;
	private Integer exclDnldId;
	private String ipAddr;
	private String menuId;
	
	public String getSearchStartDt() {
		return searchStartDt;
	}
	public void setSearchStartDt(String searchStartDt) {
		this.searchStartDt = searchStartDt;
	}
	public String getSearchEndDt() {
		return searchEndDt;
	}
	public void setSearchEndDt(String searchEndDt) {
		this.searchEndDt = searchEndDt;
	}
	public String getpSearchName() {
		return pSearchName;
	}
	public void setpSearchName(String pSearchName) {
		this.pSearchName = pSearchName;
	}
	public String getpSearchGbn() {
		return pSearchGbn;
	}
	public void setpSearchGbn(String pSearchGbn) {
		this.pSearchGbn = pSearchGbn;
	}
	public String getpChrgItemCd() {
		return pChrgItemCd;
	}
	public void setpChrgItemCd(String pChrgItemCd) {
		this.pChrgItemCd = pChrgItemCd;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBondSeqNo() {
		return bondSeqNo;
	}
	public void setBondSeqNo(String bondSeqNo) {
		this.bondSeqNo = bondSeqNo;
	}
	public String getBillYm() {
		return billYm;
	}
	public void setBillYm(String billYm) {
		this.billYm = billYm;
	}
	public String getBillArrAmtYn() {
		return billArrAmtYn;
	}
	public void setBillArrAmtYn(String billArrAmtYn) {
		this.billArrAmtYn = billArrAmtYn;
	}
	public String getAdjsAmtYn() {
		return adjsAmtYn;
	}
	public void setAdjsAmtYn(String adjsAmtYn) {
		this.adjsAmtYn = adjsAmtYn;
	}
	public String getMidPrfpayAmtYn() {
		return midPrfpayAmtYn;
	}
	public void setMidPrfpayAmtYn(String midPrfpayAmtYn) {
		this.midPrfpayAmtYn = midPrfpayAmtYn;
	}
	public String getMidPayAmtYn() {
		return midPayAmtYn;
	}
	public void setMidPayAmtYn(String midPayAmtYn) {
		this.midPayAmtYn = midPayAmtYn;
	}
	public String getInsrBillYn() {
		return insrBillYn;
	}
	public void setInsrBillYn(String insrBillYn) {
		this.insrBillYn = insrBillYn;
	}
	public String getInsrPayYn() {
		return insrPayYn;
	}
	public void setInsrPayYn(String insrPayYn) {
		this.insrPayYn = insrPayYn;
	}
	public String getTrgtStrtDt() {
		return trgtStrtDt;
	}
	public void setTrgtStrtDt(String trgtStrtDt) {
		this.trgtStrtDt = trgtStrtDt;
	}
	public String getTrgtEndDt() {
		return trgtEndDt;
	}
	public void setTrgtEndDt(String trgtEndDt) {
		this.trgtEndDt = trgtEndDt;
	}
	public int getTrgtRmnAmt() {
		return trgtRmnAmt;
	}
	public void setTrgtRmnAmt(int trgtRmnAmt) {
		this.trgtRmnAmt = trgtRmnAmt;
	}
	public int getTrgtRmnCnt() {
		return trgtRmnCnt;
	}
	public void setTrgtRmnCnt(int trgtRmnCnt) {
		this.trgtRmnCnt = trgtRmnCnt;
	}
	public int getTrgtPymCnt() {
		return trgtPymCnt;
	}
	public void setTrgtPymCnt(int trgtPymCnt) {
		this.trgtPymCnt = trgtPymCnt;
	}
	public int getTrgtUnpaidCnt() {
		return trgtUnpaidCnt;
	}
	public void setTrgtUnpaidCnt(int trgtUnpaidCnt) {
		this.trgtUnpaidCnt = trgtUnpaidCnt;
	}
	public String getTrgtUnpaidYn() {
		return trgtUnpaidYn;
	}
	public void setTrgtUnpaidYn(String trgtUnpaidYn) {
		this.trgtUnpaidYn = trgtUnpaidYn;
	}
	public String getSearchRemainAmt() {
		return searchRemainAmt;
	}
	public void setSearchRemainAmt(String searchRemainAmt) {
		this.searchRemainAmt = searchRemainAmt;
	}
	public String getSearchRemainCnt() {
		return searchRemainCnt;
	}
	public void setSearchRemainCnt(String searchRemainCnt) {
		this.searchRemainCnt = searchRemainCnt;
	}
	public String getSearchUnpaidCnt() {
		return searchUnpaidCnt;
	}
	public void setSearchUnpaidCnt(String searchUnpaidCnt) {
		this.searchUnpaidCnt = searchUnpaidCnt;
	}
	public String getDwnldRsn() {
		return dwnldRsn;
	}
	public void setDwnldRsn(String dwnldRsn) {
		this.dwnldRsn = dwnldRsn;
	}
	public Integer getExclDnldId() {
		return exclDnldId;
	}
	public void setExclDnldId(Integer exclDnldId) {
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
	public String getPayCnt() {
		return payCnt;
	}
	public void setPayCnt(String payCnt) {
		this.payCnt = payCnt;
	}
	
	
}
