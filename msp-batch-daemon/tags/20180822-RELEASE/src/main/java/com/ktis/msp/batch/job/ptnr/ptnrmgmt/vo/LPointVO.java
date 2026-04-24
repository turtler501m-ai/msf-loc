package com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.BaseVo;

public class LPointVO extends BaseVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 436111022760813820L;
	
	private String partnerId;		/* 제휴사ID */
	private String linkYm;			/* 연동월 */
	private String billYm;			/* 청구월 */
	private String customerId;		/* 고객ID */
	private String reqPoint;		/* 요청포인트 */
	private String resCd;			/* 응답결과 */
	private String resMsg;			/* 응답메세지 */
	private String resCustNo;		/* 롯데멤버스고객번호 */
	private String resAprvNo;		/* 승인번호 */
	private String resAprvDt;		/* 승인일자 */
	private String resAprvTm;		/* 승인시간 */
	private String resAprvPoint;	/* 승인포인트 */
	private String resEvntPoint;	/* 이벤트포인트 */
	private String payResult;		/* 지급결과 */
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public String getLinkYm() {
		return linkYm;
	}
	public void setLinkYm(String linkYm) {
		this.linkYm = linkYm;
	}
	public String getBillYm() {
		return billYm;
	}
	public void setBillYm(String billYm) {
		this.billYm = billYm;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getReqPoint() {
		return reqPoint;
	}
	public void setReqPoint(String reqPoint) {
		this.reqPoint = reqPoint;
	}
	public String getResCd() {
		return resCd;
	}
	public void setResCd(String resCd) {
		this.resCd = resCd;
	}
	public String getResMsg() {
		return resMsg;
	}
	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}
	public String getResCustNo() {
		return resCustNo;
	}
	public void setResCustNo(String resCustNo) {
		this.resCustNo = resCustNo;
	}
	public String getResAprvNo() {
		return resAprvNo;
	}
	public void setResAprvNo(String resAprvNo) {
		this.resAprvNo = resAprvNo;
	}
	public String getResAprvDt() {
		return resAprvDt;
	}
	public void setResAprvDt(String resAprvDt) {
		this.resAprvDt = resAprvDt;
	}
	public String getResAprvTm() {
		return resAprvTm;
	}
	public void setResAprvTm(String resAprvTm) {
		this.resAprvTm = resAprvTm;
	}
	public String getResAprvPoint() {
		return resAprvPoint;
	}
	public void setResAprvPoint(String resAprvPoint) {
		this.resAprvPoint = resAprvPoint;
	}
	public String getResEvntPoint() {
		return resEvntPoint;
	}
	public void setResEvntPoint(String resEvntPoint) {
		this.resEvntPoint = resEvntPoint;
	}
	public String getPayResult() {
		return payResult;
	}
	public void setPayResult(String payResult) {
		this.payResult = payResult;
	}
	
}
