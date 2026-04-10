package com.ktmmobile.mcp.rate.dto;

import java.io.Serializable;

public class RateAdsvcGdncLinkDtlDTO implements Serializable{

    private static final long serialVersionUID = 1L;
	
    /** 요금제부가서비스링크일련번호 */
    private int rateAdsvcLinkSeq;    
	/** 안내일련번호 */
	private int rateAdsvcGdncSeq;
	/** 링크코드 */
	private String linkCd;
	/** 정렬순서 */
	private int sortOdrg;
	/** 링크명 */
	private String linkNm;
	/** 링크URL */
	private String linkUrl;
	
	/** 사용유효여부 */
	private String useYn;
	/** 게시시작일 */
	private String pstngStartDate;
	/** 게시종료일 */
	private String pstngEndDate;
	/** 등록아이피 */
	private String cretIp;
	/** 생성일시 */
	private String cretDt;
	/** 생성자아이디 */
	private String cretId;
	/** 변경아이피 */
	private String amdIp;
	/** 수정일시 */
	private String amdDt;
	/** 수정자아이디 */
	private String amdId;
	
	public int getRateAdsvcLinkSeq() {
		return rateAdsvcLinkSeq;
	}
	public void setRateAdsvcLinkSeq(int rateAdsvcLinkSeq) {
		this.rateAdsvcLinkSeq = rateAdsvcLinkSeq;
	}
	
	public int getRateAdsvcGdncSeq() {
		return rateAdsvcGdncSeq;
	}
	public void setRateAdsvcGdncSeq(int rateAdsvcGdncSeq) {
		this.rateAdsvcGdncSeq = rateAdsvcGdncSeq;
	}

	public String getLinkCd() {
		return linkCd;
	}
	public void setLinkCd(String linkCd) {
		this.linkCd = linkCd;
	}

	public int getSortOdrg() {
		return sortOdrg;
	}
	public void setSortOdrg(int sortOdrg) {
		this.sortOdrg = sortOdrg;
	}

	public String getLinkNm() {
		return linkNm;
	}
	public void setLinkNm(String linkNm) {
		this.linkNm = linkNm;
	}

	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getPstngStartDate() {
		return pstngStartDate;
	}
	public void setPstngStartDate(String pstngStartDate) {
		this.pstngStartDate = pstngStartDate;
	}

	public String getPstngEndDate() {
		return pstngEndDate;
	}
	public void setPstngEndDate(String pstngEndDate) {
		this.pstngEndDate = pstngEndDate;
	}

	public String getCretIp() {
		return cretIp;
	}
	public void setCretIp(String cretIp) {
		this.cretIp = cretIp;
	}

	public String getCretDt() {
		return cretDt;
	}
	public void setCretDt(String cretDt) {
		this.cretDt = cretDt;
	}

	public String getCretId() {
		return cretId;
	}
	public void setCretId(String cretId) {
		this.cretId = cretId;
	}

	public String getAmdIp() {
		return amdIp;
	}
	public void setAmdIp(String amdIp) {
		this.amdIp = amdIp;
	}
	
	public String getAmdDt() {
		return amdDt;
	}
	public void setAmdDt(String amdDt) {
		this.amdDt = amdDt;
	}

	public String getAmdId() {
		return amdId;
	}
	public void setAmdId(String amdId) {
		this.amdId = amdId;
	}

}
