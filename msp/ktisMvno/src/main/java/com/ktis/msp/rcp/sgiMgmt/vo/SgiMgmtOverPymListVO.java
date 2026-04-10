package com.ktis.msp.rcp.sgiMgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class SgiMgmtOverPymListVO extends BaseVo{


	private static final long serialVersionUID = -375367851857206489L;
	private String baseDate;
	private String ban;
	private String subscriberNo;
	private String userSsn;
	private String cstmrName;
	private String grntInsrMngmNo;
	private String svcCntrNo;
	private String blpymDate;
	private String invAmt;
	private String pymnAmt;
	private String excessAmt;
	
	public String getBan() {
		return ban;
	}
	public void setBan(String ban) {
		this.ban = ban;
	}
	public String getSubscriberNo() {
		return subscriberNo;
	}
	public void setSubscriberNo(String subscriberNo) {
		this.subscriberNo = subscriberNo;
	}
	public String getUserSsn() {
		return userSsn;
	}
	public void setUserSsn(String userSsn) {
		this.userSsn = userSsn;
	}
	public String getCstmrName() {
		return cstmrName;
	}
	public void setCstmrName(String cstmrName) {
		this.cstmrName = cstmrName;
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
	public String getBlpymDate() {
		return blpymDate;
	}
	public void setBlpymDate(String blpymDate) {
		this.blpymDate = blpymDate;
	}
	public String getInvAmt() {
		return invAmt;
	}
	public void setInvAmt(String invAmt) {
		this.invAmt = invAmt;
	}
	public String getPymnAmt() {
		return pymnAmt;
	}
	public void setPymnAmt(String pymnAmt) {
		this.pymnAmt = pymnAmt;
	}
	public String getExcessAmt() {
		return excessAmt;
	}
	public void setExcessAmt(String excessAmt) {
		this.excessAmt = excessAmt;
	}
	public String getBaseDate() {
		return baseDate;
	}
	public void setBaseDate(String baseDate) {
		this.baseDate = baseDate;
	}
	
}
