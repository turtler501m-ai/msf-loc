package com.ktis.msp.rcp.rcpMgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class RcpTlphNoVO extends BaseVo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7709435011111994607L;
	
	private String tlphNoStatCd;		//전화번호상태코드
	private String asgnAgncId;			//할당대리점ID
	private String tlphNoOwnCmpnCd;		//번호소유통신사업자코드
	private String openSvcIndCd;		//개통서비스구분코드
	private String encdTlphNo;			//암호화전화번호
	private String tlphNo;				//전화번호
	private String resNo;
	
	public String getTlphNoStatCd() {
		return tlphNoStatCd;
	}
	public void setTlphNoStatCd(String tlphNoStatCd) {
		this.tlphNoStatCd = tlphNoStatCd;
	}
	public String getAsgnAgncId() {
		return asgnAgncId;
	}
	public void setAsgnAgncId(String asgnAgncId) {
		this.asgnAgncId = asgnAgncId;
	}
	public String getTlphNoOwnCmpnCd() {
		return tlphNoOwnCmpnCd;
	}
	public void setTlphNoOwnCmpnCd(String tlphNoOwnCmpnCd) {
		this.tlphNoOwnCmpnCd = tlphNoOwnCmpnCd;
	}
	public String getOpenSvcIndCd() {
		return openSvcIndCd;
	}
	public void setOpenSvcIndCd(String openSvcIndCd) {
		this.openSvcIndCd = openSvcIndCd;
	}
	public String getEncdTlphNo() {
		return encdTlphNo;
	}
	public void setEncdTlphNo(String encdTlphNo) {
		this.encdTlphNo = encdTlphNo;
	}
	public String getTlphNo() {
		return tlphNo;
	}
	public void setTlphNo(String tlphNo) {
		this.tlphNo = tlphNo;
	}
	public String getResNo() {
		return resNo;
	}
	public void setResNo(String resNo) {
		this.resNo = resNo;
	}
	
}
