package com.ktmmobile.mcp.rate.dto;

import java.io.Serializable;

public class RateAdsvcGdncDtlDTO implements Serializable{

    private static final long serialVersionUID = 1L;
	
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
	
	public int getRateAdsvcGdncDtlSeq() {
		return rateAdsvcGdncDtlSeq;
	}	
	public void setRateAdsvcGdncDtlSeq(int rateAdsvcGdncDtlSeq) {
		this.rateAdsvcGdncDtlSeq = rateAdsvcGdncDtlSeq;
	}
	
	public int getRateAdsvcGdncSeq() {
		return rateAdsvcGdncSeq;
	}
	public void setRateAdsvcGdncSeq(int rateAdsvcGdncSeq) {
		this.rateAdsvcGdncSeq = rateAdsvcGdncSeq;
	}
	
	public String getRateAdsvcItemCd() {
		return rateAdsvcItemCd;
	}
	public void setRateAdsvcItemCd(String rateAdsvcItemCd) {
		this.rateAdsvcItemCd = rateAdsvcItemCd;
	}
	
	public int getItemSortKey() {
		return itemSortKey;
	}
	public void setItemSortKey(int itemSortKey) {
		this.itemSortKey = itemSortKey;
	}
	
	public int getSortOdrg() {
		return sortOdrg;
	}
	public void setSortOdrg(int sortOdrg) {
		this.sortOdrg = sortOdrg;
	}
	
	public String getRateAdsvcItemNm() {
		return rateAdsvcItemNm;
	}
	public void setRateAdsvcItemNm(String rateAdsvcItemNm) {
		this.rateAdsvcItemNm = rateAdsvcItemNm;
	}
	
	public String getFontStyleCd() {
		return fontStyleCd;
	}
	public void setFontStyleCd(String fontStyleCd) {
		this.fontStyleCd = fontStyleCd;
	}
	
	public String getRateAdsvcItemSbst() {
		return rateAdsvcItemSbst;
	}
	public void setRateAdsvcItemSbst(String rateAdsvcItemSbst) {
		this.rateAdsvcItemSbst = rateAdsvcItemSbst;
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
