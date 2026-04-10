package com.ktmmobile.mcp.rate.guide.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name="item")
@XmlType(propOrder = {"rateAdsvcGdncDtlSeq", "rateAdsvcGdncSeq", "rateAdsvcItemCd", "itemSortKey", "sortOdrg", 
		"rateAdsvcItemNm", "fontStyleCd", "rateAdsvcItemSbst", "useYn", "pstngStartDate", "pstngEndDate"})
public class RateAdsvcGdncDtlXML {
	
	/** 요금제부가서비스상세안내일련번호 */
    private int rateAdsvcGdncDtlSeq;
    /** 요금제부가서비스안내일련번호 */
    private int rateAdsvcGdncSeq;
    /** 요금제부가서비스항목코드 */
    private String rateAdsvcItemCd;
    /** 항목정렬키 */
    private int itemSortKey;
    /** 정렬순서 */
    private int sortOdrg;
    /** 요금제부가서비스항목명 */
    private String rateAdsvcItemNm;
    /** 폰트스타일코드 */
    private String fontStyleCd;
    /** 요금제부가서비스항목내용 */
    private String rateAdsvcItemSbst;
	/** 사용여부 */
	private String useYn;
	/** 게시시작일 */
	private String pstngStartDate;
	/** 게시종료일 */
	private String pstngEndDate;
	
	public int getRateAdsvcGdncDtlSeq() {
		return rateAdsvcGdncDtlSeq;
	}
	
	@XmlElement
	public void setRateAdsvcGdncDtlSeq(int rateAdsvcGdncDtlSeq) {
		this.rateAdsvcGdncDtlSeq = rateAdsvcGdncDtlSeq;
	}
	public int getRateAdsvcGdncSeq() {
		return rateAdsvcGdncSeq;
	}
	
	@XmlElement
	public void setRateAdsvcGdncSeq(int rateAdsvcGdncSeq) {
		this.rateAdsvcGdncSeq = rateAdsvcGdncSeq;
	}
	public String getRateAdsvcItemCd() {
		return rateAdsvcItemCd;
	}
	
	@XmlElement
	public void setRateAdsvcItemCd(String rateAdsvcItemCd) {
		this.rateAdsvcItemCd = rateAdsvcItemCd;
	}
	public int getItemSortKey() {
		return itemSortKey;
	}
	
	@XmlElement
	public void setItemSortKey(int itemSortKey) {
		this.itemSortKey = itemSortKey;
	}
	public int getSortOdrg() {
		return sortOdrg;
	}
	
	@XmlElement
	public void setSortOdrg(int sortOdrg) {
		this.sortOdrg = sortOdrg;
	}
	public String getRateAdsvcItemNm() {
		return rateAdsvcItemNm;
	}
	
	@XmlElement
	public void setRateAdsvcItemNm(String rateAdsvcItemNm) {
		this.rateAdsvcItemNm = rateAdsvcItemNm;
	}
	public String getFontStyleCd() {
		return fontStyleCd;
	}
	
	@XmlElement
	public void setFontStyleCd(String fontStyleCd) {
		this.fontStyleCd = fontStyleCd;
	}
	public String getRateAdsvcItemSbst() {
		return rateAdsvcItemSbst;
	}
	
	@XmlElement
	public void setRateAdsvcItemSbst(String rateAdsvcItemSbst) {
		this.rateAdsvcItemSbst = rateAdsvcItemSbst;
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
