package com.ktmmobile.msf.domains.form.common.mplatform.vo;


public class MpEtcVO {
	private String splitDescription;//분리청구계획 코드명
	private String actvAmt;//수납금액
	private String billSeqNo;//청구일련번호
	private String messageLine;//청구항목코드

	public String getSplitDescription() {
		return splitDescription;
	}
	public void setSplitDescription(String splitDescription) {
		this.splitDescription = splitDescription;
	}
	public String getActvAmt() {
		return actvAmt;
	}
	public void setActvAmt(String actvAmt) {
		this.actvAmt = actvAmt;
	}
	public String getBillSeqNo() {
		return billSeqNo;
	}
	public void setBillSeqNo(String billSeqNo) {
		this.billSeqNo = billSeqNo;
	}
	public String getMessageLine() {
		return messageLine;
	}
	public void setMessageLine(String messageLine) {
		this.messageLine = messageLine;
	}
}
