package com.ktis.msp.batch.job.msp.canmgmt.vo;

import java.sql.Timestamp;

public class CanMgmtVO {
	
	private String contractNum;		// 서비스계약번호
	private String customerId;		// 고객번호
	private String subscriberNo;	// 고객전화번호
	private String regstId;			// 등록자
	private Timestamp regstDttm;	// 등록일자
	private String rvisnId;			// 수정자
	private Timestamp rvisnDttm;	// 수정일자
	private String procStatus;		// 처리상태 R:대기 S:처리완료 E:오류
	private Timestamp procDttm;		// 처리일자
	private String procDesc;		// 처리결과메시지
	private String connRsltKey;		// 연동처리키값(globalNum)
	private String connRsltType;	// 연동처리결과유형
	private String connRsltCd;		// 연동처리결과코드
	private String connRsltMsg;		// 연동처리결과메시지
    
	
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getSubscriberNo() {
		return subscriberNo;
	}
	public void setSubscriberNo(String subscriberNo) {
		this.subscriberNo = subscriberNo;
	}
	public String getRegstId() {
		return regstId;
	}
	public void setRegstId(String regstId) {
		this.regstId = regstId;
	}
	public Timestamp getRegstDttm() {
		return regstDttm;
	}
	public void setRegstDttm(Timestamp regstDttm) {
		this.regstDttm = regstDttm;
	}
	public String getRvisnId() {
		return rvisnId;
	}
	public void setRvisnId(String rvisnId) {
		this.rvisnId = rvisnId;
	}
	public Timestamp getRvisnDttm() {
		return rvisnDttm;
	}
	public void setRvisnDttm(Timestamp rvisnDttm) {
		this.rvisnDttm = rvisnDttm;
	}
	public String getProcStatus() {
		return procStatus;
	}
	public void setProcStatus(String procStatus) {
		this.procStatus = procStatus;
	}
	public Timestamp getProcDttm() {
		return procDttm;
	}
	public void setProcDttm(Timestamp procDttm) {
		this.procDttm = procDttm;
	}
	public String getProcDesc() {
		return procDesc;
	}
	public void setProcDesc(String procDesc) {
		this.procDesc = procDesc;
	}
	public String getConnRsltKey() {
		return connRsltKey;
	}
	public void setConnRsltKey(String connRsltKey) {
		this.connRsltKey = connRsltKey;
	}
	public String getConnRsltType() {
		return connRsltType;
	}
	public void setConnRsltType(String connRsltType) {
		this.connRsltType = connRsltType;
	}
	public String getConnRsltCd() {
		return connRsltCd;
	}
	public void setConnRsltCd(String connRsltCd) {
		this.connRsltCd = connRsltCd;
	}
	public String getConnRsltMsg() {
		return connRsltMsg;
	}
	public void setConnRsltMsg(String connRsltMsg) {
		this.connRsltMsg = connRsltMsg;
	}
	
}
