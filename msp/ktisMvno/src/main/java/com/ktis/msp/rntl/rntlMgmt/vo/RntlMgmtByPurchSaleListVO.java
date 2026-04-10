package com.ktis.msp.rntl.rntlMgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class RntlMgmtByPurchSaleListVO extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/* 공통 */
	private String prvdYm;
	private String agncyNm;
	private String intmMdlCd;
	private String intmMdlNm;
	private String buyAmnt;
	
	/* 매입 */
	private String buyCnt;
	private String buySumAmnt;
	private String deplrictCost;
	private String buyPayAmnt;
	
	/* 매각 */
	private String saleCnt;
	private String saleAmnt;
	private String virfyCost;
	private String salePayAmnt;
	private String prvdAmnt;
	
	/* 조회 조건 */
	private String baseDt;
	private String orgnId;
	
	public String getPrvdYm() {
		return prvdYm;
	}
	public void setPrvdYm(String prvdYm) {
		this.prvdYm = prvdYm;
	}
	public String getAgncyNm() {
		return agncyNm;
	}
	public void setAgncyNm(String agncyNm) {
		this.agncyNm = agncyNm;
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
	public String getBuyAmnt() {
		return buyAmnt;
	}
	public void setBuyAmnt(String buyAmnt) {
		this.buyAmnt = buyAmnt;
	}
	public String getBuyCnt() {
		return buyCnt;
	}
	public void setBuyCnt(String buyCnt) {
		this.buyCnt = buyCnt;
	}
	public String getBuySumAmnt() {
		return buySumAmnt;
	}
	public void setBuySumAmnt(String buySumAmnt) {
		this.buySumAmnt = buySumAmnt;
	}
	public String getDeplrictCost() {
		return deplrictCost;
	}
	public void setDeplrictCost(String deplrictCost) {
		this.deplrictCost = deplrictCost;
	}
	public String getBuyPayAmnt() {
		return buyPayAmnt;
	}
	public void setBuyPayAmnt(String buyPayAmnt) {
		this.buyPayAmnt = buyPayAmnt;
	}
	public String getSaleCnt() {
		return saleCnt;
	}
	public void setSaleCnt(String saleCnt) {
		this.saleCnt = saleCnt;
	}
	public String getSaleAmnt() {
		return saleAmnt;
	}
	public void setSaleAmnt(String saleAmnt) {
		this.saleAmnt = saleAmnt;
	}
	public String getVirfyCost() {
		return virfyCost;
	}
	public void setVirfyCost(String virfyCost) {
		this.virfyCost = virfyCost;
	}
	public String getSalePayAmnt() {
		return salePayAmnt;
	}
	public void setSalePayAmnt(String salePayAmnt) {
		this.salePayAmnt = salePayAmnt;
	}
	public String getPrvdAmnt() {
		return prvdAmnt;
	}
	public void setPrvdAmnt(String prvdAmnt) {
		this.prvdAmnt = prvdAmnt;
	}
	public String getBaseDt() {
		return baseDt;
	}
	public void setBaseDt(String baseDt) {
		this.baseDt = baseDt;
	}
	public String getOrgnId() {
		return orgnId;
	}
	public void setOrgnId(String orgnId) {
		this.orgnId = orgnId;
	}
}
