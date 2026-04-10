package com.ktis.msp.rcp.sgiMgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class SgiMgmtCanListVO extends BaseVo{

	private static final long serialVersionUID = -2508277449594103301L;
	private String baseDate;
	private String compensationBillDate;
	private String compensationBillNo;
	private String cstmrName;
	private String userSsn;
	private String grntInsrMngmNo;
	private String svcCntrNo;
	private String subscriberNo;
	private String canRsn;
	private String billCanAmt;
	private String canRsnCd;
	
	public String getCompensationBillDate() {
		return compensationBillDate;
	}
	public void setCompensationBillDate(String compensationBillDate) {
		this.compensationBillDate = compensationBillDate;
	}
	public String getCompensationBillNo() {
		return compensationBillNo;
	}
	public void setCompensationBillNo(String compensationBillNo) {
		this.compensationBillNo = compensationBillNo;
	}
	public String getCstmrName() {
		return cstmrName;
	}
	public void setCstmrName(String cstmrName) {
		this.cstmrName = cstmrName;
	}
	public String getUserSsn() {
		return userSsn;
	}
	public void setUserSsn(String userSsn) {
		this.userSsn = userSsn;
	}
	public String getGrntInsrMngmNo() {
		return grntInsrMngmNo;
	}
	public void setGrntInsrMngmNo(String grntInsrMngmNo) {
		this.grntInsrMngmNo = grntInsrMngmNo;
	}
	public String getSvcCntrNo() {
		return svcCntrNo;
	}
	public void setSvcCntrNo(String svcCntrNo) {
		this.svcCntrNo = svcCntrNo;
	}
	public String getSubscriberNo() {
		return subscriberNo;
	}
	public void setSubscriberNo(String subscriberNo) {
		this.subscriberNo = subscriberNo;
	}
	public String getCanRsn() {
		return canRsn;
	}
	public void setCanRsn(String canRsn) {
		this.canRsn = canRsn;
	}
	public String getBillCanAmt() {
		return billCanAmt;
	}
	public void setBillCanAmt(String billCanAmt) {
		this.billCanAmt = billCanAmt;
	}
	public String getCanRsnCd() {
		return canRsnCd;
	}
	public void setCanRsnCd(String canRsnCd) {
		this.canRsnCd = canRsnCd;
	}
	/**
	 * @return the baseDate
	 */
	public String getBaseDate() {
		return baseDate;
	}
	/**
	 * @param baseDate the baseDate to set
	 */
	public void setBaseDate(String baseDate) {
		this.baseDate = baseDate;
	}
}
