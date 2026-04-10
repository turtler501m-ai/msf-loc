package com.ktmmobile.mcp.rate.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="root")
public class RateAdsvcGdncBasXML {
	
	/** 요금제부가서비스안내일련번호 */
	private int rateAdsvcGdncSeq;
	/** 요금제부가서비스구분 */
	private String rateAdsvcDivCd;
	/** 요금제구분코드 */
	private String rateDivCd;
	/** 요금제부가서비스구분명 */
	private String rateAdsvcDivNm;
	/** 요금제부가서비스명 */
	private String rateAdsvcNm;
	/** 요금제부가서비스추가명 */
	private String rateAdsvcApdNm;
	/** 요금제부가서비스기본설명 */
	private String rateAdsvcBasDesc;	
	/** 요금제부가서비스이미지명 */
	private String rateAdsvcImgNm;	
	/** 월기본금액설명 */
	private String mmBasAmtDesc;	
	/** 월기본금액vat(포함)설명 */
	private String mmBasAmtVatDesc;	
    /** 프로모션요금설명 */
	private String promotionAmtDesc;	
    /** 프로모션요금vat(포함)설명 */
	private String promotionAmtVatDesc;	
    /** 안내파일명(xml) */
	private String gdncFileNm;	
	/** 사용유효여부 */
	private String useYn;
	/** 게시시작일 */
	private String pstngStartDate;
	/** 게시종료일 */
	private String pstngEndDate;

	/** 스티커 구분코드 */
	private String stickerCtg;


	/** 요금제 구분코드 */
	private String rateCtg;


	public int getRateAdsvcGdncSeq() {
		return rateAdsvcGdncSeq;
	}

	@XmlElement
	public void setRateAdsvcGdncSeq(int rateAdsvcGdncSeq) {
		this.rateAdsvcGdncSeq = rateAdsvcGdncSeq;
	}

	public String getRateAdsvcDivCd() {
		return rateAdsvcDivCd;
	}

	@XmlElement
	public void setRateAdsvcDivCd(String rateAdsvcDivCd) {
		this.rateAdsvcDivCd = rateAdsvcDivCd;
	}
	
	public String getRateDivCd() {
		return rateDivCd;
	}

	@XmlElement
	public void setRateDivCd(String rateDivCd) {
		this.rateDivCd = rateDivCd;
	}

	public String getRateAdsvcDivNm() {
		return rateAdsvcDivNm;
	}

	@XmlElement
	public void setRateAdsvcDivNm(String rateAdsvcDivNm) {
		this.rateAdsvcDivNm = rateAdsvcDivNm;
	}

	public String getRateAdsvcNm() {
		return rateAdsvcNm;
	}

	@XmlElement
	public void setRateAdsvcNm(String rateAdsvcNm) {
		this.rateAdsvcNm = rateAdsvcNm;
	}

	public String getRateAdsvcApdNm() {
		return rateAdsvcApdNm;
	}

	@XmlElement
	public void setRateAdsvcApdNm(String rateAdsvcApdNm) {
		this.rateAdsvcApdNm = rateAdsvcApdNm;
	}

	public String getRateAdsvcBasDesc() {
		return rateAdsvcBasDesc;
	}

	@XmlElement
	public void setRateAdsvcBasDesc(String rateAdsvcBasDesc) {
		this.rateAdsvcBasDesc = rateAdsvcBasDesc;
	}

	public String getRateAdsvcImgNm() {
		return rateAdsvcImgNm;
	}

	@XmlElement
	public void setRateAdsvcImgNm(String rateAdsvcImgNm) {
		this.rateAdsvcImgNm = rateAdsvcImgNm;
	}

	public String getMmBasAmtDesc() {
		return mmBasAmtDesc;
	}

	@XmlElement
	public void setMmBasAmtDesc(String mmBasAmtDesc) {
		this.mmBasAmtDesc = mmBasAmtDesc;
	}

	public String getMmBasAmtVatDesc() {
		return mmBasAmtVatDesc;
	}

	@XmlElement
	public void setMmBasAmtVatDesc(String mmBasAmtVatDesc) {
		this.mmBasAmtVatDesc = mmBasAmtVatDesc;
	}

	public String getPromotionAmtDesc() {
		return promotionAmtDesc;
	}

	@XmlElement
	public void setPromotionAmtDesc(String promotionAmtDesc) {
		this.promotionAmtDesc = promotionAmtDesc;
	}

	public String getPromotionAmtVatDesc() {
		return promotionAmtVatDesc;
	}

	@XmlElement
	public void setPromotionAmtVatDesc(String promotionAmtVatDesc) {
		this.promotionAmtVatDesc = promotionAmtVatDesc;
	}

	public String getGdncFileNm() {
		return gdncFileNm;
	}

	@XmlElement
	public void setGdncFileNm(String gdncFileNm) {
		this.gdncFileNm = gdncFileNm;
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

	public String getStickerCtg() {
		if (stickerCtg == null) {
			return "";
		}
		return stickerCtg;
	}

	@XmlElement
	public void setStickerCtg(String stickerCtg) {
		this.stickerCtg = stickerCtg;
	}

	public String getRateCtg() {
		if (rateCtg == null) {
			return "";
		}
		return rateCtg;
	}

	@XmlElement
	public void setRateCtg(String rateCtg) {
		this.rateCtg = rateCtg;
	}

}
