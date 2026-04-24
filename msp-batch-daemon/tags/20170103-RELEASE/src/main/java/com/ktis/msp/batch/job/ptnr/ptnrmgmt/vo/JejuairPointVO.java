package com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.BaseVo;

public class JejuairPointVO extends BaseVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5009147058951577966L;
	
	private String partnerId = "jejuair";
	private String memberCi;
	private String tradeType = "ACC";
	private String pointType = "A07";
	private int point;
	private String channelId = "KT";
	private String partnerCd = "KMM";
	private String orderNo = "";
	private int amount;
	private String responseCode;
	private String payResult;
	private String billYm;
	
	/**
	 * @return the memberCi
	 */
	public String getMemberCi() {
		return memberCi;
	}
	/**
	 * @param memberCi the memberCi to set
	 */
	public void setMemberCi(String memberCi) {
		this.memberCi = memberCi;
	}
	/**
	 * @return the tradeType
	 */
	public String getTradeType() {
		return tradeType;
	}
	/**
	 * @param tradeType the tradeType to set
	 */
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	/**
	 * @return the pointType
	 */
	public String getPointType() {
		return pointType;
	}
	/**
	 * @param pointType the pointType to set
	 */
	public void setPointType(String pointType) {
		this.pointType = pointType;
	}
	/**
	 * @return the point
	 */
	public int getPoint() {
		return point;
	}
	/**
	 * @param point the point to set
	 */
	public void setPoint(int point) {
		this.point = point;
	}
	/**
	 * @return the channelId
	 */
	public String getChannelId() {
		return channelId;
	}
	/**
	 * @param channelId the channelId to set
	 */
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	/**
	 * @return the partnerCd
	 */
	public String getPartnerCd() {
		return partnerCd;
	}
	/**
	 * @param partnerCd the partnerCd to set
	 */
	public void setPartnerCd(String partnerCd) {
		this.partnerCd = partnerCd;
	}
	/**
	 * @return the orderNo
	 */
	public String getOrderNo() {
		return orderNo;
	}
	/**
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	/**
	 * @return the responseCode
	 */
	public String getResponseCode() {
		return responseCode;
	}
	/**
	 * @param responseCode the responseCode to set
	 */
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	/**
	 * @return the partnerId
	 */
	public String getPartnerId() {
		return partnerId;
	}
	/**
	 * @param partnerId the partnerId to set
	 */
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	/**
	 * @return the payResult
	 */
	public String getPayResult() {
		return payResult;
	}
	/**
	 * @param payResult the payResult to set
	 */
	public void setPayResult(String payResult) {
		this.payResult = payResult;
	}
	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}
	/**
	 * @return the billYm
	 */
	public String getBillYm() {
		return billYm;
	}
	/**
	 * @param billYm the billYm to set
	 */
	public void setBillYm(String billYm) {
		this.billYm = billYm;
	}
	
	
	
}
