package com.ktmmobile.mcp.rate.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name="item")
@XmlType(propOrder = {"rateAdsvcLinkSeq", "rateAdsvcGdncSeq", "linkCd", "sortOdrg", "linkNm", 
		"linkUrl", "useYn", "pstngStartDate", "pstngEndDate"})
public class RateAdsvcGdncLinkDtlXML {
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
	
	public int getRateAdsvcLinkSeq() {
		return rateAdsvcLinkSeq;
	}
	
	@XmlElement
	public void setRateAdsvcLinkSeq(int rateAdsvcLinkSeq) {
		this.rateAdsvcLinkSeq = rateAdsvcLinkSeq;
	}
	public int getRateAdsvcGdncSeq() {
		return rateAdsvcGdncSeq;
	}
	
	@XmlElement
	public void setRateAdsvcGdncSeq(int rateAdsvcGdncSeq) {
		this.rateAdsvcGdncSeq = rateAdsvcGdncSeq;
	}
	public String getLinkCd() {
		return linkCd;
	}
	
	@XmlElement
	public void setLinkCd(String linkCd) {
		this.linkCd = linkCd;
	}
	public int getSortOdrg() {
		return sortOdrg;
	}
	
	@XmlElement
	public void setSortOdrg(int sortOdrg) {
		this.sortOdrg = sortOdrg;
	}
	public String getLinkNm() {
		return linkNm;
	}
	
	@XmlElement
	public void setLinkNm(String linkNm) {
		this.linkNm = linkNm;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	
	@XmlElement
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
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
