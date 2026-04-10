package com.ktis.msp.rntl.rntlMgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class RntlMgmtByMnthCalcListVO extends BaseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String prvdYm;
	private String agncyId;
	private String agncyNm;
	private String contractNum;
	private String openDt;
	private String rtrnDt;
	private String intmMdlCd;
	private String intmMdlNm;
	private String rntlStat;
	private String buyAmnt;
	private String rentalCost;
	private String prvdTotDay;
	private String prvdTotAmnt;
	private String prvdMonDay;
	private String prvdMonAmnt;
	private String backUsgDay;
	private String backAmnt;
	private String virfyCost;
	private String saleAmnt;
	private String remainAmnt;
	private String grntAmnt;
	
	private String intmMdlId;
	private String prvRemainAmnt;
	private String usgDay;
	private String prvdAmnt;
	
	/* 조회 조건 */
	private String rntlStatus;
	private String orgnId;
	private String baseDt;
	private String prdtId;
	private String searchGbn;
	private String searchName;
	
	/* 페이징 처리용 */
	public int TOTAL_COUNT;
	public String ROW_NUM;
	public String LINENUM;
	public String getPrvdYm() {
		return prvdYm;
	}
	public void setPrvdYm(String prvdYm) {
		this.prvdYm = prvdYm;
	}
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
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
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
	public String getBackUsgDay() {
		return backUsgDay;
	}
	public void setBackUsgDay(String backUsgDay) {
		this.backUsgDay = backUsgDay;
	}
	public String getBackAmnt() {
		return backAmnt;
	}
	public void setBackAmnt(String backAmnt) {
		this.backAmnt = backAmnt;
	}
	public String getVirfyCost() {
		return virfyCost;
	}
	public void setVirfyCost(String virfyCost) {
		this.virfyCost = virfyCost;
	}
	public String getSaleAmnt() {
		return saleAmnt;
	}
	public void setSaleAmnt(String saleAmnt) {
		this.saleAmnt = saleAmnt;
	}
	public String getRemainAmnt() {
		return remainAmnt;
	}
	public void setRemainAmnt(String remainAmnt) {
		this.remainAmnt = remainAmnt;
	}
	public String getGrntAmnt() {
		return grntAmnt;
	}
	public void setGrntAmnt(String grntAmnt) {
		this.grntAmnt = grntAmnt;
	}
	public String getIntmMdlId() {
		return intmMdlId;
	}
	public void setIntmMdlId(String intmMdlId) {
		this.intmMdlId = intmMdlId;
	}
	public String getPrvRemainAmnt() {
		return prvRemainAmnt;
	}
	public void setPrvRemainAmnt(String prvRemainAmnt) {
		this.prvRemainAmnt = prvRemainAmnt;
	}
	public String getUsgDay() {
		return usgDay;
	}
	public void setUsgDay(String usgDay) {
		this.usgDay = usgDay;
	}
	public String getPrvdAmnt() {
		return prvdAmnt;
	}
	public void setPrvdAmnt(String prvdAmnt) {
		this.prvdAmnt = prvdAmnt;
	}
	public String getRntlStatus() {
		return rntlStatus;
	}
	public void setRntlStatus(String rntlStatus) {
		this.rntlStatus = rntlStatus;
	}
	public String getOrgnId() {
		return orgnId;
	}
	public void setOrgnId(String orgnId) {
		this.orgnId = orgnId;
	}
	public String getBaseDt() {
		return baseDt;
	}
	public void setBaseDt(String baseDt) {
		this.baseDt = baseDt;
	}
	public String getPrdtId() {
		return prdtId;
	}
	public void setPrdtId(String prdtId) {
		this.prdtId = prdtId;
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
}
