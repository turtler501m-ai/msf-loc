package com.ktmmobile.mcp.rate.dto;

import java.io.Serializable;

public class RateAdsvcBnfitGdncDtlDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    /** 요금제부가서비스혜택안내일련번호 */
    private int rateAdsvcBnfitSeq;
	/** 요금제부가서비스안내일련번호 */
	private int rateAdsvcGdncSeq;
	/** 요금제부가서비스항목코드 */
	private String rateAdsvcBnfitItemCd;
	/** 요금제부가서비스항목코드명 */
	private String rateAdsvcBnfitItemNm;
	/** 정렬순서 */
	private String sortOdrg;
	/** 요금제부가서비스항목이미지명 */
	private String rateAdsvcItemImgNm;
	/** 요금제부가서비스항목명 */
	private String rateAdsvcItemNm;
	/** 요금제부가서비스항목설명 */
	private String rateAdsvcItemDesc;
	/** 요금제부가서비스항목추가설명 */
	private String rateAdsvcItemApdDesc;
	/** 사용여부 */
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
		
	public int getRateAdsvcBnfitSeq() {
		return rateAdsvcBnfitSeq;
	}
	public void setRateAdsvcBnfitSeq(int rateAdsvcBnfitSeq) {
		this.rateAdsvcBnfitSeq = rateAdsvcBnfitSeq;
	}
	
	public int getRateAdsvcGdncSeq() {
		return rateAdsvcGdncSeq;
	}
	public void setRateAdsvcGdncSeq(int rateAdsvcGdncSeq) {
		this.rateAdsvcGdncSeq = rateAdsvcGdncSeq;
	}
	
	public String getRateAdsvcBnfitItemCd() {
		return rateAdsvcBnfitItemCd;
	}
	public void setRateAdsvcBnfitItemCd(String rateAdsvcBnfitItemCd) {
		this.rateAdsvcBnfitItemCd = rateAdsvcBnfitItemCd;
	}
	
	public String getRateAdsvcBnfitItemNm() {
		return rateAdsvcBnfitItemNm;
	}
	public void setRateAdsvcBnfitItemNm(String rateAdsvcBnfitItemNm) {
		this.rateAdsvcBnfitItemNm = rateAdsvcBnfitItemNm;
	}
	
	public String getSortOdrg() {
		return sortOdrg;
	}
	public void setSortOdrg(String sortOdrg) {
		this.sortOdrg = sortOdrg;
	}
	
	public String getRateAdsvcItemImgNm() {
		return rateAdsvcItemImgNm;
	}
	public void setRateAdsvcItemImgNm(String rateAdsvcItemImgNm) {
		this.rateAdsvcItemImgNm = rateAdsvcItemImgNm;
	}
	
	public String getRateAdsvcItemNm() {
		return rateAdsvcItemNm;
	}
	public void setRateAdsvcItemNm(String rateAdsvcItemNm) {
		this.rateAdsvcItemNm = rateAdsvcItemNm;
	}
	
	public String getRateAdsvcItemDesc() {
		return rateAdsvcItemDesc;
	}
	public void setRateAdsvcItemDesc(String rateAdsvcItemDesc) {
		this.rateAdsvcItemDesc = rateAdsvcItemDesc;
	}
	
	public String getRateAdsvcItemApdDesc() {
		return rateAdsvcItemApdDesc;
	}
	public void setRateAdsvcItemApdDesc(String rateAdsvcItemApdDesc) {
		this.rateAdsvcItemApdDesc = rateAdsvcItemApdDesc;
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
