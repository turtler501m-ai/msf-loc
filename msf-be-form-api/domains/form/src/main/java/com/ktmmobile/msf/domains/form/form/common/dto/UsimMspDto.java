package com.ktmmobile.msf.domains.form.form.common.dto;

import java.io.Serializable;

public class UsimMspDto implements Serializable{


	/**
	 *
	 */
	private static final long serialVersionUID = -2128662475308855633L;

	/** 정책코드 */
	private String salePlcyCd;

	/** 정책명 */
	private String salePlcyNm;

	/** 상품코드 */
	private String rateCd;

	/** 상품명 */
	private String rateNm;

	/** 선불/후불여부 */
	private String cdDsc;

	/** 선불 후불구분 */
	private String payClCd;

	/** 데이터 타입 */
	private String dataType;

	public String getSalePlcyCd() {
		return salePlcyCd;
	}

	public void setSalePlcyCd(String salePlcyCd) {
		this.salePlcyCd = salePlcyCd;
	}

	public String getRateCd() {
		return rateCd;
	}

	public void setRateCd(String rateCd) {
		this.rateCd = rateCd;
	}

	public String getRateNm() {
		return rateNm;
	}

	public void setRateNm(String rateNm) {
		this.rateNm = rateNm;
	}

	public String getCdDsc() {
		return cdDsc;
	}

	public void setCdDsc(String cdDsc) {
		this.cdDsc = cdDsc;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPayClCd() {
		return payClCd;
	}

	public void setPayClCd(String payClCd) {
		this.payClCd = payClCd;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getSalePlcyNm() {
		return salePlcyNm;
	}

	public void setSalePlcyNm(String salePlcyNm) {
		this.salePlcyNm = salePlcyNm;
	}


}
