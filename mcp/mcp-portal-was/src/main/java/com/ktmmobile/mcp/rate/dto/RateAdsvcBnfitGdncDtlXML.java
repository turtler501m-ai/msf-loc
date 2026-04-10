package com.ktmmobile.mcp.rate.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name="item")
@XmlType(propOrder = {"rateAdsvcBnfitSeq", "rateAdsvcGdncSeq", "rateAdsvcBnfitItemCd", "rateAdsvcBnfitItemNm", "sortOdrg", "rateAdsvcItemImgCd", "rateAdsvcItemImgNm", 
		"rateAdsvcItemNm", "rateAdsvcItemDesc", "rateAdsvcItemApdDesc", "useYn", "pstngStartDate", "pstngEndDate"})
public class RateAdsvcBnfitGdncDtlXML {
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
	/** 요금제부가서비스항목이미지 코드 */
	private String rateAdsvcItemImgCd;
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
		
	public int getRateAdsvcBnfitSeq() {
		return rateAdsvcBnfitSeq;
	}
	@XmlElement	
	public void setRateAdsvcBnfitSeq(int rateAdsvcBnfitSeq) {
		this.rateAdsvcBnfitSeq = rateAdsvcBnfitSeq;
	}
	
	public int getRateAdsvcGdncSeq() {
		return rateAdsvcGdncSeq;
	}
	@XmlElement
	public void setRateAdsvcGdncSeq(int rateAdsvcGdncSeq) {
		this.rateAdsvcGdncSeq = rateAdsvcGdncSeq;
	}
	
	public String getRateAdsvcBnfitItemCd() {
		return rateAdsvcBnfitItemCd;
	}
	@XmlElement
	public void setRateAdsvcBnfitItemCd(String rateAdsvcBnfitItemCd) {
		this.rateAdsvcBnfitItemCd = rateAdsvcBnfitItemCd;
	}
	
	public String getRateAdsvcBnfitItemNm() {
		return rateAdsvcBnfitItemNm;
	}
	@XmlElement
	public void setRateAdsvcBnfitItemNm(String rateAdsvcBnfitItemNm) {
		this.rateAdsvcBnfitItemNm = rateAdsvcBnfitItemNm;
	}
	
	public String getRateAdsvcItemImgCd() {
		return rateAdsvcItemImgCd;
	}
	@XmlElement
	public void setRateAdsvcItemImgCd(String rateAdsvcItemImgCd) {
		this.rateAdsvcItemImgCd = rateAdsvcItemImgCd;
	}
	
	public String getSortOdrg() {
		return sortOdrg;
	}
	@XmlElement
	public void setSortOdrg(String sortOdrg) {
		this.sortOdrg = sortOdrg;
	}
	
	public String getRateAdsvcItemImgNm() {
		return rateAdsvcItemImgNm;
	}
	
	@XmlElement
	public void setRateAdsvcItemImgNm(String rateAdsvcItemImgNm) {
		this.rateAdsvcItemImgNm = rateAdsvcItemImgNm;
	}
	
	public String getRateAdsvcItemNm() {
		return rateAdsvcItemNm;
	}
	
	@XmlElement
	public void setRateAdsvcItemNm(String rateAdsvcItemNm) {
		this.rateAdsvcItemNm = rateAdsvcItemNm;
	}
	
	public String getRateAdsvcItemDesc() {
		return rateAdsvcItemDesc;
	}
	@XmlElement
	public void setRateAdsvcItemDesc(String rateAdsvcItemDesc) {
		this.rateAdsvcItemDesc = rateAdsvcItemDesc;
	}
	
	public String getRateAdsvcItemApdDesc() {
		return rateAdsvcItemApdDesc;
	}
	@XmlElement
	public void setRateAdsvcItemApdDesc(String rateAdsvcItemApdDesc) {
		this.rateAdsvcItemApdDesc = rateAdsvcItemApdDesc;
	}
	
	public String getUseYn() {
		return useYn;
	}
	@XmlElement
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	
	public String getPstngStartDate() {
		return pstngStartDate;
	}
	@XmlElement
	public void setPstngStartDate(String pstngStartDate) {
		this.pstngStartDate = pstngStartDate;
	}
	
	public String getPstngEndDate() {
		return pstngEndDate;
	}
	@XmlElement
	public void setPstngEndDate(String pstngEndDate) {
		this.pstngEndDate = pstngEndDate;
	}

}
