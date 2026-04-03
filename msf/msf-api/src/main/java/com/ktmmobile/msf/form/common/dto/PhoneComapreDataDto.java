package com.ktmmobile.msf.form.common.dto;

import java.io.Serializable;

/**
 * @author key
 * 폰비교정보VO
 */
public class PhoneComapreDataDto implements Serializable{

	private static final long serialVersionUID = -8732189282562832038L;
	
	/**
	 * 상품아이디
	 */
	private String prodId;
	/**
	 * 요금제코드
	 */
	private String rateCd;
	/**
	 * 모델아이디
	 */
	private String hndsetModelId; 
	/**
	 * 자급제여부
	 */
	private String sesplsYn;
	
	/**
	 * 비교인덱스
	 */
	private int idx;
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public String getRateCd() {
		return rateCd;
	}
	public void setRateCd(String rateCd) {
		this.rateCd = rateCd;
	}
	public String getHndsetModelId() {
		return hndsetModelId;
	}
	public void setHndsetModelId(String hndsetModelId) {
		this.hndsetModelId = hndsetModelId;
	}
	public String getSesplsYn() {
		return sesplsYn;
	}
	public void setSesplsYn(String sesplsYn) {
		this.sesplsYn = sesplsYn;
	}
	
}
