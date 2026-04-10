package com.ktmmobile.mcp.common.mplatform.dto;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import com.ktmmobile.mcp.common.util.XmlParse;

public class NowDlvryResDto extends com.ktmmobile.mcp.common.mplatform.vo.CommonXmlVO {


	private String rsltCd	;
	private String rsltMsg;
	private String psblYn; // 배달 가능 여부	1	M	Y 배달가능, N 배달불가
	private String acceptTime; // 접수 가능 시간	2	M	접수 가능 시간
	private String bizOrgCd	; // 배달 업체 코드	2	M	01: 생각대로, 02: 부르심
	private String ktOrderId; // "사업자코드(3자리) + YYYYMMDD + SEQ(5자리)	예) KIS2021043010001"
	private String deliveryOrderId; //
	private String orderStatCd;
	private String acceptDt;
	private String allocDt;
	private String pickupDt;
	private String completeDt;
	private String cancelDt;
	private String kmProduct;
	private String orderAmt;


    @Override
    public void parse() throws UnsupportedEncodingException, ParseException {

        this.rsltCd = XmlParse.getChildValue(this.body, "rsltCd");
        this.rsltMsg = XmlParse.getChildValue(this.body, "rsltMsg");
        this.psblYn = XmlParse.getChildValue(this.body, "psblYn");
        this.acceptTime = XmlParse.getChildValue(this.body, "acceptTime");
        this.bizOrgCd = XmlParse.getChildValue(this.body, "bizOrgCd");
        this.ktOrderId = XmlParse.getChildValue(this.body, "ktOrderId");
        this.deliveryOrderId = XmlParse.getChildValue(this.body, "deliveryOrderId");
        this.orderStatCd = XmlParse.getChildValue(this.body, "orderStatCd");
        this.acceptDt = XmlParse.getChildValue(this.body, "acceptDt");
        this.allocDt = XmlParse.getChildValue(this.body, "allocDt");
        this.pickupDt = XmlParse.getChildValue(this.body, "pickupDt");
        this.completeDt = XmlParse.getChildValue(this.body, "completeDt");
        this.cancelDt = XmlParse.getChildValue(this.body, "cancelDt");
        this.kmProduct = XmlParse.getChildValue(this.body, "kmProduct");
        this.orderAmt = XmlParse.getChildValue(this.body, "orderAmt");
    }


	public String getOrderStatCd() {
		return orderStatCd;
	}


	public void setOrderStatCd(String orderStatCd) {
		this.orderStatCd = orderStatCd;
	}


	public String getAcceptDt() {
		return acceptDt;
	}


	public void setAcceptDt(String acceptDt) {
		this.acceptDt = acceptDt;
	}


	public String getAllocDt() {
		return allocDt;
	}


	public void setAllocDt(String allocDt) {
		this.allocDt = allocDt;
	}


	public String getPickupDt() {
		return pickupDt;
	}


	public void setPickupDt(String pickupDt) {
		this.pickupDt = pickupDt;
	}


	public String getCompleteDt() {
		return completeDt;
	}


	public void setCompleteDt(String completeDt) {
		this.completeDt = completeDt;
	}


	public String getCancelDt() {
		return cancelDt;
	}


	public void setCancelDt(String cancelDt) {
		this.cancelDt = cancelDt;
	}


	public String getKmProduct() {
		return kmProduct;
	}


	public void setKmProduct(String kmProduct) {
		this.kmProduct = kmProduct;
	}


	public String getOrderAmt() {
		return orderAmt;
	}


	public void setOrderAmt(String orderAmt) {
		this.orderAmt = orderAmt;
	}


	public String getKtOrderId() {
		return ktOrderId;
	}

	public void setKtOrderId(String ktOrderId) {
		this.ktOrderId = ktOrderId;
	}

	public String getDeliveryOrderId() {
		return deliveryOrderId;
	}

	public void setDeliveryOrderId(String deliveryOrderId) {
		this.deliveryOrderId = deliveryOrderId;
	}

	public String getRsltCd() {
		return rsltCd;
	}

	public void setRsltCd(String rsltCd) {
		this.rsltCd = rsltCd;
	}

	public String getRsltMsg() {
		return rsltMsg;
	}

	public void setRsltMsg(String rsltMsg) {
		this.rsltMsg = rsltMsg;
	}

	public String getPsblYn() {
		return psblYn;
	}

	public void setPsblYn(String psblYn) {
		this.psblYn = psblYn;
	}

	public String getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(String acceptTime) {
		this.acceptTime = acceptTime;
	}

	public String getBizOrgCd() {
		return bizOrgCd;
	}

	public void setBizOrgCd(String bizOrgCd) {
		this.bizOrgCd = bizOrgCd;
	}




}
