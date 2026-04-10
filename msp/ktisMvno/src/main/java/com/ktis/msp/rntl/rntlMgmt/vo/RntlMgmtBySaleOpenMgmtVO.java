package com.ktis.msp.rntl.rntlMgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class RntlMgmtBySaleOpenMgmtVO extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String agncyId;
	private String agncyNm;
	private String resNo;
	private String contractNum;
	private String custNm;
	private String intmMdlCd;
	private String intmMdlNm;
	private String intmMdlSrl;
	private String openDt;
	private String rtrnDt;
	private String subStatus;
	private String rntlStat;
	private String buyAmnt;
	private String rentalCost;
	private String prvdTotDay;
	private String prvdTotAmnt;
	private String prvdMonDay;
	private String prvdMonAmnt;
	private String remainAmnt;
	
	/* 조회 조건 */
	private String orgnId;
	private String searchStartDt;
	private String searchEndDt;
	private String rntlStatus;
	private String searchGbn;
	private String searchName;
	
	/* 페이징 처리용 */
	public int TOTAL_COUNT;
	public String ROW_NUM;
	public String LINENUM;
	
	/* 반납 처리용 */
	private String prdtId;
	private String rtrnYm;
	private String rtrnProcId;
	private String rtrnProcYn;
	private String rntlRtrnStat;
	private String rtrnTrtmTypeCd;
	
	public String getAgncyId() {
		return agncyId;
	}
	public void setAgncyId(String agncyId) {
		this.agncyId = agncyId;
	}
	public String getAgncyNm() {
		return agncyNm;
	}
	public void setAgncyNm(String agncyNm) {
		this.agncyNm = agncyNm;
	}
	public String getResNo() {
		return resNo;
	}
	public void setResNo(String resNo) {
		this.resNo = resNo;
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
	public String getIntmMdlCd() {
		return intmMdlCd;
	}
	public void setIntmMdlCd(String intmMdlCd) {
		this.intmMdlCd = intmMdlCd;
	}
	public String getIntmMdlNm() {
		return intmMdlNm;
	}
	public void setIntmMdlNm(String intmMdlNm) {
		this.intmMdlNm = intmMdlNm;
	}
	public String getIntmMdlSrl() {
		return intmMdlSrl;
	}
	public void setIntmMdlSrl(String intmMdlSrl) {
		this.intmMdlSrl = intmMdlSrl;
	}
	public String getOpenDt() {
		return openDt;
	}
	public void setOpenDt(String openDt) {
		this.openDt = openDt;
	}
	public String getRtrnDt() {
		return rtrnDt;
	}
	public void setRtrnDt(String rtrnDt) {
		this.rtrnDt = rtrnDt;
	}
	public String getSubStatus() {
		return subStatus;
	}
	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}
	public String getRntlStat() {
		return rntlStat;
	}
	public void setRntlStat(String rntlStat) {
		this.rntlStat = rntlStat;
	}
	public String getBuyAmnt() {
		return buyAmnt;
	}
	public void setBuyAmnt(String buyAmnt) {
		this.buyAmnt = buyAmnt;
	}
	public String getRentalCost() {
		return rentalCost;
	}
	public void setRentalCost(String rentalCost) {
		this.rentalCost = rentalCost;
	}
	public String getPrvdTotDay() {
		return prvdTotDay;
	}
	public void setPrvdTotDay(String prvdTotDay) {
		this.prvdTotDay = prvdTotDay;
	}
	public String getPrvdTotAmnt() {
		return prvdTotAmnt;
	}
	public void setPrvdTotAmnt(String prvdTotAmnt) {
		this.prvdTotAmnt = prvdTotAmnt;
	}
	public String getPrvdMonDay() {
		return prvdMonDay;
	}
	public void setPrvdMonDay(String prvdMonDay) {
		this.prvdMonDay = prvdMonDay;
	}
	public String getPrvdMonAmnt() {
		return prvdMonAmnt;
	}
	public void setPrvdMonAmnt(String prvdMonAmnt) {
		this.prvdMonAmnt = prvdMonAmnt;
	}
	public String getRemainAmnt() {
		return remainAmnt;
	}
	public void setRemainAmnt(String remainAmnt) {
		this.remainAmnt = remainAmnt;
	}
	public String getOrgnId() {
		return orgnId;
	}
	public void setOrgnId(String orgnId) {
		this.orgnId = orgnId;
	}
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
	public String getRntlStatus() {
		return rntlStatus;
	}
	public void setRntlStatus(String rntlStatus) {
		this.rntlStatus = rntlStatus;
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
	public int getTOTAL_COUNT() {
		return TOTAL_COUNT;
	}
	public void setTOTAL_COUNT(int tOTAL_COUNT) {
		TOTAL_COUNT = tOTAL_COUNT;
	}
	public String getROW_NUM() {
		return ROW_NUM;
	}
	public void setROW_NUM(String rOW_NUM) {
		ROW_NUM = rOW_NUM;
	}
	public String getLINENUM() {
		return LINENUM;
	}
	public void setLINENUM(String lINENUM) {
		LINENUM = lINENUM;
	}
	public String getPrdtId() {
		return prdtId;
	}
	public void setPrdtId(String prdtId) {
		this.prdtId = prdtId;
	}
	public String getRtrnYm() {
		return rtrnYm;
	}
	public void setRtrnYm(String rtrnYm) {
		this.rtrnYm = rtrnYm;
	}
	public String getRtrnProcId() {
		return rtrnProcId;
	}
	public void setRtrnProcId(String rtrnProcId) {
		this.rtrnProcId = rtrnProcId;
	}
	public String getRtrnProcYn() {
		return rtrnProcYn;
	}
	public void setRtrnProcYn(String rtrnProcYn) {
		this.rtrnProcYn = rtrnProcYn;
	}
	public String getRntlRtrnStat() {
		return rntlRtrnStat;
	}
	public void setRntlRtrnStat(String rntlRtrnStat) {
		this.rntlRtrnStat = rntlRtrnStat;
	}
	public String getRtrnTrtmTypeCd() {
		return rtrnTrtmTypeCd;
	}
	public void setRtrnTrtmTypeCd(String rtrnTrtmTypeCd) {
		this.rtrnTrtmTypeCd = rtrnTrtmTypeCd;
	}
}
