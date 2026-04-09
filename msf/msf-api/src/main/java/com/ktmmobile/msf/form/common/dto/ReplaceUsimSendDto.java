package com.ktmmobile.msf.form.common.dto;

import java.io.Serializable;

public class ReplaceUsimSendDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private long seq;
	private long subSeq;
	private long osstNo;
	private String customerId;
	private String contractNum;
	private String svcCntrNo;
	private String subscriberNo;
	private String subStatus;
	private String reqInDay;
	private String chnlCd;        // 신청채널
	private String deliveryType;  // 배송방식

	public long getSeq() {
		return seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}

	public long getSubSeq() {
		return subSeq;
	}

	public void setSubSeq(long subSeq) {
		this.subSeq = subSeq;
	}

	public long getOsstNo() {
		return osstNo;
	}

	public void setOsstNo(long osstNo) {
		this.osstNo = osstNo;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getContractNum() {
		return contractNum;
	}

	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
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

	public String getSubStatus() {
		return subStatus;
	}

	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}

	public String getReqInDay() {
		return reqInDay;
	}

	public void setReqInDay(String reqInDay) {
		this.reqInDay = reqInDay;
	}

	public String getChnlCd() {
		return chnlCd;
	}

	public void setChnlCd(String chnlCd) {
		this.chnlCd = chnlCd;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
}
