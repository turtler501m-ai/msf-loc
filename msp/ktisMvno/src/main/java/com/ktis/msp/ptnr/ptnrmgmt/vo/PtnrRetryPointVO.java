package com.ktis.msp.ptnr.ptnrmgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class PtnrRetryPointVO extends BaseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6882308797132708673L;
	
	private String partnerId;
	private String fileNm;
	
	private String memberCi;
	private int point;
	private int amount;
	private String contractNum;
	private String ctn;
	private String usgYm;
	private String uicc;
	private String billYm;
	
	private String tradeType = "ACC";
	private String pointType = "A07";
	private String channelId = "KT";
	private String partnerCd = "KMM";
	private String orderNo = "";
	private String responseCode;
	private String payResult;
	private String linkYm;
	
	//google play
	private String svcType;
	private String svcCd;
	private String dealNum; /*거래번호 구글Play측에서 생성*/
	
	//롯데멤버스
	private String customerId;
	private String selfCstmrCi;
	
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public String getFileNm() {
		return fileNm;
	}
	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}
	public String getMemberCi() {
		return memberCi;
	}
	public void setMemberCi(String memberCi) {
		this.memberCi = memberCi;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getCtn() {
		return ctn;
	}
	public void setCtn(String ctn) {
		this.ctn = ctn;
	}
	public String getUsgYm() {
		return usgYm;
	}
	public void setUsgYm(String usgYm) {
		this.usgYm = usgYm;
	}
	public String getUicc() {
		return uicc;
	}
	public void setUicc(String uicc) {
		this.uicc = uicc;
	}
	public String getBillYm() {
		return billYm;
	}
	public void setBillYm(String billYm) {
		this.billYm = billYm;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getPointType() {
		return pointType;
	}
	public void setPointType(String pointType) {
		this.pointType = pointType;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getPartnerCd() {
		return partnerCd;
	}
	public void setPartnerCd(String partnerCd) {
		this.partnerCd = partnerCd;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getPayResult() {
		return payResult;
	}
	public void setPayResult(String payResult) {
		this.payResult = payResult;
	}
	public String getLinkYm() {
		return linkYm;
	}
	public void setLinkYm(String linkYm) {
		this.linkYm = linkYm;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getSelfCstmrCi() {
		return selfCstmrCi;
	}
	public void setSelfCstmrCi(String selfCstmrCi) {
		this.selfCstmrCi = selfCstmrCi;
	}
	public String getSvcType() {
		return svcType;
	}
	public void setSvcType(String svcType) {
		this.svcType = svcType;
	}
	public String getSvcCd() {
		return svcCd;
	}
	public void setSvcCd(String svcCd) {
		this.svcCd = svcCd;
	}
	public String getDealNum() {
		return dealNum;
	}
	public void setDealNum(String dealNum) {
		this.dealNum = dealNum;
	}
	
	
	
}
