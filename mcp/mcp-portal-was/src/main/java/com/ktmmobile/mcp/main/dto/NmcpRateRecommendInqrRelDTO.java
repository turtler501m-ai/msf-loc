package com.ktmmobile.mcp.main.dto;

import java.io.Serializable;
import java.util.Date;

public class NmcpRateRecommendInqrRelDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 문의관계일련번호 */
	private int rateRecommendInqrRelSeq;
	/** 질문별답변순서대로 1111 */
	private int ansNo;
	/** NMCP_RATE_ADSVC_GDNC_BAS */
	private int rateAdsvcGdncSeq;
	/** 요금제상품코드 */
	private String rateCd;
	/** 추천상품설명 */
	private String recommendProdDesc;
	/** 정렬순서 */
	private String sortOdrg;
	/** 정렬순서 */
	private String useYn;
	/** 게시시작일자 */
	private String pstngStartDate;
	/** 게시종료일자 */
	private String pstngEndDate;	
	/** 등록IP */
    private Date cretIp;
	/** 등록일시 */
    private Date cretDt;
	 /** 등록자 아이디 */
    private String cretId;
    /** 수정IP */
    private Date amdIp;
    /** 수정일시 */
    private Date amdDt;
    /** 수정자 아이디 */
    private String amdId;
    
	public int getRateRecommendInqrRelSeq() {
		return rateRecommendInqrRelSeq;
	}
	public void setRateRecommendInqrRelSeq(int rateRecommendInqrRelSeq) {
		this.rateRecommendInqrRelSeq = rateRecommendInqrRelSeq;
	}
	public int getAnsNo() {
		return ansNo;
	}
	public void setAnsNo(int ansNo) {
		this.ansNo = ansNo;
	}
	public int getRateAdsvcGdncSeq() {
		return rateAdsvcGdncSeq;
	}
	public void setRateAdsvcGdncSeq(int rateAdsvcGdncSeq) {
		this.rateAdsvcGdncSeq = rateAdsvcGdncSeq;
	}
	public String getRateCd() {
		return rateCd;
	}
	public void setRateCd(String rateCd) {
		this.rateCd = rateCd;
	}
	public String getRecommendProdDesc() {
		return recommendProdDesc;
	}
	public void setRecommendProdDesc(String recommendProdDesc) {
		this.recommendProdDesc = recommendProdDesc;
	}
	public String getSortOdrg() {
		return sortOdrg;
	}
	public void setSortOdrg(String sortOdrg) {
		this.sortOdrg = sortOdrg;
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
	public Date getCretIp() {
		return cretIp;
	}
	public void setCretIp(Date cretIp) {
		this.cretIp = cretIp;
	}
	public Date getCretDt() {
		return cretDt;
	}
	public void setCretDt(Date cretDt) {
		this.cretDt = cretDt;
	}
	public String getCretId() {
		return cretId;
	}
	public void setCretId(String cretId) {
		this.cretId = cretId;
	}
	public Date getAmdIp() {
		return amdIp;
	}
	public void setAmdIp(Date amdIp) {
		this.amdIp = amdIp;
	}
	public Date getAmdDt() {
		return amdDt;
	}
	public void setAmdDt(Date amdDt) {
		this.amdDt = amdDt;
	}
	public String getAmdId() {
		return amdId;
	}
	public void setAmdId(String amdId) {
		this.amdId = amdId;
	}
    
}
