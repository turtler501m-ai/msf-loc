package com.ktmmobile.msf.form.common.dto;

import java.io.Serializable;

public class ReplaceUsimDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private long seq;
	private String cstmrName;
	private String dlvryName;
	private String dlvryTelFn;
	private String dlvryTelMn;
	private String dlvryTelRn;
	private String dlvryPost;
	private String dlvryAddr;
	private String dlvryAddrDtl;
	private String dlvryMemo;
	private String agrmYn;
	private String customerId;
	private int reqQnty;
	private String onlineAuthInfo;
	private String cretId;
	private String cstmrTel;
	private String connInfo;
	private String contractNum;
    private String subscriberNo;
    private String subStatus;
    private String svcCntrNo;
    private String usimType;
    private long subSeq;
    private String preChkRsltCd;
    private String applyRsltCd;
	private String nfcUsimYn;	  // NFC 유심 여부
	private String usimModelCd;   // 유심모델코드
	private String usimModelId;   // 유심모델ID

	public long getSeq() {
		return seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}

	public String getCstmrName() {
		return cstmrName;
	}

	public void setCstmrName(String cstmrName) {
		this.cstmrName = cstmrName;
	}

	public String getDlvryName() {
		return dlvryName;
	}

	public void setDlvryName(String dlvryName) {
		this.dlvryName = dlvryName;
	}

	public String getDlvryTelFn() {
		return dlvryTelFn;
	}

	public void setDlvryTelFn(String dlvryTelFn) {
		this.dlvryTelFn = dlvryTelFn;
	}

	public String getDlvryTelMn() {
		return dlvryTelMn;
	}

	public void setDlvryTelMn(String dlvryTelMn) {
		this.dlvryTelMn = dlvryTelMn;
	}

	public String getDlvryTelRn() {
		return dlvryTelRn;
	}

	public void setDlvryTelRn(String dlvryTelRn) {
		this.dlvryTelRn = dlvryTelRn;
	}

	public String getDlvryPost() {
		return dlvryPost;
	}

	public void setDlvryPost(String dlvryPost) {
		this.dlvryPost = dlvryPost;
	}

	public String getDlvryAddr() {
		return dlvryAddr;
	}

	public void setDlvryAddr(String dlvryAddr) {
		this.dlvryAddr = dlvryAddr;
	}

	public String getDlvryAddrDtl() {
		return dlvryAddrDtl;
	}

	public void setDlvryAddrDtl(String dlvryAddrDtl) {
		this.dlvryAddrDtl = dlvryAddrDtl;
	}

	public String getDlvryMemo() {
		return dlvryMemo;
	}

	public void setDlvryMemo(String dlvryMemo) {
		this.dlvryMemo = dlvryMemo;
	}

	public String getAgrmYn() {
		return agrmYn;
	}

	public void setAgrmYn(String agrmYn) {
		this.agrmYn = agrmYn;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public int getReqQnty() {
		return reqQnty;
	}

	public void setReqQnty(int reqQnty) {
		this.reqQnty = reqQnty;
	}

	public String getOnlineAuthInfo() {
		return onlineAuthInfo;
	}

	public void setOnlineAuthInfo(String onlineAuthInfo) {
		this.onlineAuthInfo = onlineAuthInfo;
	}

	public String getCretId() {
		return cretId;
	}

	public void setCretId(String cretId) {
		this.cretId = cretId;
	}

	public String getCstmrTel() {
		return cstmrTel;
	}

	public void setCstmrTel(String cstmrTel) {
		this.cstmrTel = cstmrTel;
	}

	public String getConnInfo() {
		return connInfo;
	}

	public void setConnInfo(String connInfo) {
		this.connInfo = connInfo;
	}

	public String getContractNum() {
		return contractNum;
	}

	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
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

	public String getSvcCntrNo() {
		return svcCntrNo;
	}

	public void setSvcCntrNo(String svcCntrNo) {
		this.svcCntrNo = svcCntrNo;
	}

	public String getUsimType() {
		return usimType;
	}

	public void setUsimType(String usimType) {
		this.usimType = usimType;
	}

	public long getSubSeq() {
		return subSeq;
	}

	public void setSubSeq(long subSeq) {
		this.subSeq = subSeq;
	}

	public String getPreChkRsltCd() {
		return preChkRsltCd;
	}

	public void setPreChkRsltCd(String preChkRsltCd) {
		this.preChkRsltCd = preChkRsltCd;
	}

	public String getApplyRsltCd() {
		return applyRsltCd;
	}

	public void setApplyRsltCd(String applyRsltCd) {
		this.applyRsltCd = applyRsltCd;
	}

	public String getNfcUsimYn() {
		return nfcUsimYn;
	}

	public void setNfcUsimYn(String nfcUsimYn) {
		this.nfcUsimYn = nfcUsimYn;
	}

	public String getUsimModelCd() {
		return usimModelCd;
	}

	public void setUsimModelCd(String usimModelCd) {
		this.usimModelCd = usimModelCd;
	}

	public String getUsimModelId() {
		return usimModelId;
	}

	public void setUsimModelId(String usimModelId) {
		this.usimModelId = usimModelId;
	}
}
