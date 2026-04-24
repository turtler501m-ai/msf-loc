package com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.BaseVo;

public class TmoneyPointVO extends BaseVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 436111022760813820L;
	
	private String partnerId = "tmoney";
	private String linkYm;
	private String billYm;
	private String contractNum;
	private String uicc;
	private String pointType = "01";
	private String ctn;
	private int point;
	private String responseCode;
	private String payResult;
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
	 * @return the contractNum
	 */
	public String getContractNum() {
		return contractNum;
	}
	/**
	 * @param contractNum the contractNum to set
	 */
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	/**
	 * @return the ctn
	 */
	public String getCtn() {
		return ctn;
	}
	/**
	 * @param ctn the ctn to set
	 */
	public void setCtn(String ctn) {
		this.ctn = ctn;
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
	/**
	 * @return the linkYm
	 */
	public String getLinkYm() {
		return linkYm;
	}
	/**
	 * @param linkYm the linkYm to set
	 */
	public void setLinkYm(String linkYm) {
		this.linkYm = linkYm;
	}
	/**
	 * @return the uicc
	 */
	public String getUicc() {
		return uicc;
	}
	/**
	 * @param uicc the uicc to set
	 */
	public void setUicc(String uicc) {
		this.uicc = uicc;
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
	
	
}
