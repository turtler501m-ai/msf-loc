package com.ktis.msp.rcp.rcpMgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

public class RcpPrdtListVO extends BaseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5823077515142497052L;
	
	private String prdtId;
	private String prdtSrlNum;
	private String svcCtNum;
	private String prdtCode;
	private String prdtColrCd;
	private String mnfctId;
	private String reqInDay;
	private String orgnId;
	private String orgnNm;
	private String prdtNm;
	private String prdtColrNm;
	private String logisProcStatCd;
	private String logisProcStatNm;
	
	public String getReqInDay() {
		return reqInDay;
	}
	public void setReqInDay(String reqInDay) {
		this.reqInDay = reqInDay;
	}
	public String getPrdtId() {
		return prdtId;
	}
	public void setPrdtId(String prdtId) {
		this.prdtId = prdtId;
	}
	public String getPrdtSrlNum() {
		return prdtSrlNum;
	}
	public void setPrdtSrlNum(String prdtSrlNum) {
		this.prdtSrlNum = prdtSrlNum;
	}
	public String getSvcCtNum() {
		return svcCtNum;
	}
	public void setSvcCtNum(String svcCtNum) {
		this.svcCtNum = svcCtNum;
	}
	public String getPrdtCode() {
		return prdtCode;
	}
	public void setPrdtCode(String prdtCode) {
		this.prdtCode = prdtCode;
	}
	public String getPrdtColrCd() {
		return prdtColrCd;
	}
	public void setPrdtColrCd(String prdtColrCd) {
		this.prdtColrCd = prdtColrCd;
	}
	public String getMnfctId() {
		return mnfctId;
	}
	public void setMnfctId(String mnfctId) {
		this.mnfctId = mnfctId;
	}
	public String getOrgnId() {
		return orgnId;
	}
	public void setOrgnId(String orgnId) {
		this.orgnId = orgnId;
	}
	public String getOrgnNm() {
		return orgnNm;
	}
	public void setOrgnNm(String orgnNm) {
		this.orgnNm = orgnNm;
	}
	public String getPrdtNm() {
		return prdtNm;
	}
	public void setPrdtNm(String prdtNm) {
		this.prdtNm = prdtNm;
	}
	public String getPrdtColrNm() {
		return prdtColrNm;
	}
	public void setPrdtColrNm(String prdtColrNm) {
		this.prdtColrNm = prdtColrNm;
	}
	public String getLogisProcStatCd() {
		return logisProcStatCd;
	}
	public void setLogisProcStatCd(String logisProcStatCd) {
		this.logisProcStatCd = logisProcStatCd;
	}
	public String getLogisProcStatNm() {
		return logisProcStatNm;
	}
	public void setLogisProcStatNm(String logisProcStatNm) {
		this.logisProcStatNm = logisProcStatNm;
	}
}
