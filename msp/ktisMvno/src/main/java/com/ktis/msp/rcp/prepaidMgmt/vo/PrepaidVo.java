package com.ktis.msp.rcp.prepaidMgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

public class PrepaidVo extends BaseVo implements Serializable{
	private static final long serialVersionUID = 1L;
	String sysRdateS;
	String sysRdateE;
	String reqType;
	String contractNum;
	String rechargeAgent;
	String recharge;
	String retCode;
	String retCodeNm;
	String retMsg;
	String rcgSeq;
	String oAmount;
	String oTesChargeMax;
	String oTesBaser;
	String oTesChgr;
	String oTesMagicr;
	String oTesFsmsr;
	String reqDate;
	String subscriberNo;
	
	/** 검색구분 */
	private String searchCd;
	private String searchVal;
	
	public String getSysRdateS() {
		return sysRdateS;
	}
	public void setSysRdateS(String sysRdateS) {
		this.sysRdateS = sysRdateS;
	}
	public String getSysRdateE() {
		return sysRdateE;
	}
	public void setSysRdateE(String sysRdateE) {
		this.sysRdateE = sysRdateE;
	}
	public String getReqType() {
		return reqType;
	}
	public void setReqType(String reqType) {
		this.reqType = reqType;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getRechargeAgent() {
		return rechargeAgent;
	}
	public void setRechargeAgent(String rechargeAgent) {
		this.rechargeAgent = rechargeAgent;
	}
	public String getRecharge() {
		return recharge;
	}
	public void setRecharge(String recharge) {
		this.recharge = recharge;
	}
	public String getRetCode() {
		return retCode;
	}
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	public String getRetCodeNm() {
		return retCodeNm;
	}
	public void setRetCodeNm(String retCodeNm) {
		this.retCodeNm = retCodeNm;
	}
	public String getRetMsg() {
		return retMsg;
	}
	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
	public String getRcgSeq() {
		return rcgSeq;
	}
	public void setRcgSeq(String rcgSeq) {
		this.rcgSeq = rcgSeq;
	}
	public String getoAmount() {
		return oAmount;
	}
	public void setoAmount(String oAmount) {
		this.oAmount = oAmount;
	}
	public String getoTesChargeMax() {
		return oTesChargeMax;
	}
	public void setoTesChargeMax(String oTesChargeMax) {
		this.oTesChargeMax = oTesChargeMax;
	}
	public String getoTesBaser() {
		return oTesBaser;
	}
	public void setoTesBaser(String oTesBaser) {
		this.oTesBaser = oTesBaser;
	}
	public String getoTesChgr() {
		return oTesChgr;
	}
	public void setoTesChgr(String oTesChgr) {
		this.oTesChgr = oTesChgr;
	}
	public String getoTesMagicr() {
		return oTesMagicr;
	}
	public void setoTesMagicr(String oTesMagicr) {
		this.oTesMagicr = oTesMagicr;
	}
	public String getoTesFsmsr() {
		return oTesFsmsr;
	}
	public void setoTesFsmsr(String oTesFsmsr) {
		this.oTesFsmsr = oTesFsmsr;
	}
	public String getReqDate() {
		return reqDate;
	}
	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}
	public String getSubscriberNo() {
		return subscriberNo;
	}
	public void setSubscriberNo(String subscriberNo) {
		this.subscriberNo = subscriberNo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
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
}
