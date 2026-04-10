package com.ktmmobile.mcp.etc.dto;

import java.io.Serializable;

public class RateAdsvcGdncProdRelDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private int rateAdsvcProdRelSeq;    // 요금제부가서비스상품관계일련번호
	private int rateAdsvcGdncSeq;       // 안내일련번호
	private String rateAdsvcCd;         // 요금제부가서비스코드
	private String rateAdsvcNm;         // 요금제부가서비스명
	private String useYn;               // 사용유효여부
	private String pstngStartDate;      // 게시시작일
	private String pstngEndDate;        // 게시종료일
	private String giftPrmtSeqs;        // 요금제 혜택요약 일련번호 (구분값 |)
	private String evntCd;                 // 이벤트코드 프로모션 이벤트코드

	/** 이벤트코드순번 */
	private String ecpSeq;

	public int getRateAdsvcProdRelSeq() {
		return rateAdsvcProdRelSeq;
	}

	public void setRateAdsvcProdRelSeq(int rateAdsvcProdRelSeq) {
		this.rateAdsvcProdRelSeq = rateAdsvcProdRelSeq;
	}

	public int getRateAdsvcGdncSeq() {
		return rateAdsvcGdncSeq;
	}

	public void setRateAdsvcGdncSeq(int rateAdsvcGdncSeq) {
		this.rateAdsvcGdncSeq = rateAdsvcGdncSeq;
	}

	public String getRateAdsvcCd() {
		return rateAdsvcCd;
	}

	public void setRateAdsvcCd(String rateAdsvcCd) {
		this.rateAdsvcCd = rateAdsvcCd;
	}

	public String getRateAdsvcNm() {
		return rateAdsvcNm;
	}

	public void setRateAdsvcNm(String rateAdsvcNm) {
		this.rateAdsvcNm = rateAdsvcNm;
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

	public String getGiftPrmtSeqs() {
		return giftPrmtSeqs;
	}

	public void setGiftPrmtSeqs(String giftPrmtSeqs) {
		this.giftPrmtSeqs = giftPrmtSeqs;
	}

	public String getEvntCd() { return evntCd; }

	public void setEvntCd(String evntCd) { this.evntCd = evntCd; }

	public String getEcpSeq() {
		return ecpSeq;
	}

	public void setEcpSeq(String ecpSeq) {
		this.ecpSeq = ecpSeq;
	}
}
