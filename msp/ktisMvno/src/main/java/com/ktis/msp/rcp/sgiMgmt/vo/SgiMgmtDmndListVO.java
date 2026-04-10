package com.ktis.msp.rcp.sgiMgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class SgiMgmtDmndListVO extends BaseVo{
	

	private static final long serialVersionUID = -2100811514306057193L;

	private String baseDate;
	private String dutySctn;
	private String cmpnSctn;
	private String insrBillReqNo;
	private String resultCd;
	private String errResultCntt;
	private String blanks;
	private String securitiesNo;
	private String insrMngmNo;
	private String svcNo;
	private String svcNoBlanks; 
	private String cstmrCd;
	private String userSsn;
	private String cstmrName;
	private String indvEntrNo;
	private String payerId;
	private String lstModelName;
	private String lstModelSerialNo;
	private String subscriberNo;
	private String officioRevocationDate;
	private String partPayRevocationYn;
	private String nameChangeDate;
	private String phoneJoinDate;
	private String insrStrtDate;
	private String insrTrmnDate;
	private String instCnt;
	private String instPayNum;
	private String insrJoinAmt;
	private String billDate;
	private String billAmt;
	private String instRate;
	private String fstUnpayDate;
	private String fstUnpayNum;
	private String residenceSubscriberNo;
	private String residencePost;
	private String billAddrSubscriberNo; 
	private String billAddrPost;
	private String residenceCd;
	private String residenceAddr;
	private String residenceAddrDtl;
	private String billAddrCd;
	private String billAddr;
	private String billAddrDtl;
	private String branchCd;
	private String minorName;
	private String minorSsn;
	
	//2017-01-16 컬럼 추가 (박준성)
	private String indvRevivalYn;
	private String indvRevivalStep;
	private String indvRevivalRepayYn;
	private String indvRevivalRepayAmt;
	private String bankruptcyExemptionYn;
	
	public String getIndvRevivalYn() {
		return indvRevivalYn;
	}
	public void setIndvRevivalYn(String indvRevivalYn) {
		this.indvRevivalYn = indvRevivalYn;
	}
	public String getIndvRevivalStep() {
		return indvRevivalStep;
	}
	public void setIndvRevivalStep(String indvRevivalStep) {
		this.indvRevivalStep = indvRevivalStep;
	}
	public String getIndvRevivalRepayYn() {
		return indvRevivalRepayYn;
	}
	public void setIndvRevivalRepayYn(String indvRevivalRepayYn) {
		this.indvRevivalRepayYn = indvRevivalRepayYn;
	}
	public String getIndvRevivalRepayAmt() {
		return indvRevivalRepayAmt;
	}
	public void setIndvRevivalRepayAmt(String indvRevivalRepayAmt) {
		this.indvRevivalRepayAmt = indvRevivalRepayAmt;
	}
	public String getBankruptcyExemptionYn() {
		return bankruptcyExemptionYn;
	}
	public void setBankruptcyExemptionYn(String bankruptcyExemptionYn) {
		this.bankruptcyExemptionYn = bankruptcyExemptionYn;
	}
	public String getDutySctn() {
		return dutySctn;
	}
	public void setDutySctn(String dutySctn) {
		this.dutySctn = dutySctn;
	}
	public String getCmpnSctn() {
		return cmpnSctn;
	}
	public void setCmpnSctn(String cmpnSctn) {
		this.cmpnSctn = cmpnSctn;
	}
	public String getInsrBillReqNo() {
		return insrBillReqNo;
	}
	public void setInsrBillReqNo(String insrBillReqNo) {
		this.insrBillReqNo = insrBillReqNo;
	}
	public String getResultCd() {
		return resultCd;
	}
	public void setResultCd(String resultCd) {
		this.resultCd = resultCd;
	}
	public String getErrResultCntt() {
		return errResultCntt;
	}
	public void setErrResultCntt(String errResultCntt) {
		this.errResultCntt = errResultCntt;
	}
	public String getBlanks() {
		return blanks;
	}
	public void setBlanks(String blanks) {
		this.blanks = blanks;
	}
	public String getSecuritiesNo() {
		return securitiesNo;
	}
	public void setSecuritiesNo(String securitiesNo) {
		this.securitiesNo = securitiesNo;
	}
	public String getInsrMngmNo() {
		return insrMngmNo;
	}
	public void setInsrMngmNo(String insrMngmNo) {
		this.insrMngmNo = insrMngmNo;
	}
	public String getSvcNo() {
		return svcNo;
	}
	public void setSvcNo(String svcNo) {
		this.svcNo = svcNo;
	}
	public String getSvcNoBlanks() {
		return svcNoBlanks;
	}
	public void setSvcNoBlanks(String svcNoBlanks) {
		this.svcNoBlanks = svcNoBlanks;
	}
	public String getCstmrCd() {
		return cstmrCd;
	}
	public void setCstmrCd(String cstmrCd) {
		this.cstmrCd = cstmrCd;
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
	public String getIndvEntrNo() {
		return indvEntrNo;
	}
	public void setIndvEntrNo(String indvEntrNo) {
		this.indvEntrNo = indvEntrNo;
	}
	public String getPayerId() {
		return payerId;
	}
	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}
	public String getLstModelName() {
		return lstModelName;
	}
	public void setLstModelName(String lstModelName) {
		this.lstModelName = lstModelName;
	}
	public String getLstModelSerialNo() {
		return lstModelSerialNo;
	}
	public void setLstModelSerialNo(String lstModelSerialNo) {
		this.lstModelSerialNo = lstModelSerialNo;
	}
	public String getSubscriberNo() {
		return subscriberNo;
	}
	public void setSubscriberNo(String subscriberNo) {
		this.subscriberNo = subscriberNo;
	}
	public String getOfficioRevocationDate() {
		return officioRevocationDate;
	}
	public void setOfficioRevocationDate(String officioRevocationDate) {
		this.officioRevocationDate = officioRevocationDate;
	}
	public String getPartPayRevocationYn() {
		return partPayRevocationYn;
	}
	public void setPartPayRevocationYn(String partPayRevocationYn) {
		this.partPayRevocationYn = partPayRevocationYn;
	}
	public String getNameChangeDate() {
		return nameChangeDate;
	}
	public void setNameChangeDate(String nameChangeDate) {
		this.nameChangeDate = nameChangeDate;
	}
	public String getPhoneJoinDate() {
		return phoneJoinDate;
	}
	public void setPhoneJoinDate(String phoneJoinDate) {
		this.phoneJoinDate = phoneJoinDate;
	}
	public String getInsrStrtDate() {
		return insrStrtDate;
	}
	public void setInsrStrtDate(String insrStrtDate) {
		this.insrStrtDate = insrStrtDate;
	}
	public String getInsrTrmnDate() {
		return insrTrmnDate;
	}
	public void setInsrTrmnDate(String insrTrmnDate) {
		this.insrTrmnDate = insrTrmnDate;
	}
	public String getInstCnt() {
		return instCnt;
	}
	public void setInstCnt(String instCnt) {
		this.instCnt = instCnt;
	}
	public String getInstPayNum() {
		return instPayNum;
	}
	public void setInstPayNum(String instPayNum) {
		this.instPayNum = instPayNum;
	}
	public String getInsrJoinAmt() {
		return insrJoinAmt;
	}
	public void setInsrJoinAmt(String insrJoinAmt) {
		this.insrJoinAmt = insrJoinAmt;
	}
	public String getBillDate() {
		return billDate;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public String getBillAmt() {
		return billAmt;
	}
	public void setBillAmt(String billAmt) {
		this.billAmt = billAmt;
	}
	public String getInstRate() {
		return instRate;
	}
	public void setInstRate(String instRate) {
		this.instRate = instRate;
	}
	public String getFstUnpayDate() {
		return fstUnpayDate;
	}
	public void setFstUnpayDate(String fstUnpayDate) {
		this.fstUnpayDate = fstUnpayDate;
	}
	public String getFstUnpayNum() {
		return fstUnpayNum;
	}
	public void setFstUnpayNum(String fstUnpayNum) {
		this.fstUnpayNum = fstUnpayNum;
	}
	public String getResidenceSubscriberNo() {
		return residenceSubscriberNo;
	}
	public void setResidenceSubscriberNo(String residenceSubscriberNo) {
		this.residenceSubscriberNo = residenceSubscriberNo;
	}
	public String getResidencePost() {
		return residencePost;
	}
	public void setResidencePost(String residencePost) {
		this.residencePost = residencePost;
	}
	public String getBillAddrSubscriberNo() {
		return billAddrSubscriberNo;
	}
	public void setBillAddrSubscriberNo(String billAddrSubscriberNo) {
		this.billAddrSubscriberNo = billAddrSubscriberNo;
	}
	public String getBillAddrPost() {
		return billAddrPost;
	}
	public void setBillAddrPost(String billAddrPost) {
		this.billAddrPost = billAddrPost;
	}
	public String getResidenceCd() {
		return residenceCd;
	}
	public void setResidenceCd(String residenceCd) {
		this.residenceCd = residenceCd;
	}
	public String getResidenceAddr() {
		return residenceAddr;
	}
	public void setResidenceAddr(String residenceAddr) {
		this.residenceAddr = residenceAddr;
	}
	public String getResidenceAddrDtl() {
		return residenceAddrDtl;
	}
	public void setResidenceAddrDtl(String residenceAddrDtl) {
		this.residenceAddrDtl = residenceAddrDtl;
	}
	public String getBillAddrCd() {
		return billAddrCd;
	}
	public void setBillAddrCd(String billAddrCd) {
		this.billAddrCd = billAddrCd;
	}
	public String getBillAddr() {
		return billAddr;
	}
	public void setBillAddr(String billAddr) {
		this.billAddr = billAddr;
	}
	public String getBillAddrDtl() {
		return billAddrDtl;
	}
	public void setBillAddrDtl(String billAddrDtl) {
		this.billAddrDtl = billAddrDtl;
	}
	public String getBranchCd() {
		return branchCd;
	}
	public void setBranchCd(String branchCd) {
		this.branchCd = branchCd;
	}
	public String getMinorName() {
		return minorName;
	}
	public void setMinorName(String minorName) {
		this.minorName = minorName;
	}
	public String getMinorSsn() {
		return minorSsn;
	}
	public void setMinorSsn(String minorSsn) {
		this.minorSsn = minorSsn;
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
