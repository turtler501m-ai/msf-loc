package com.ktmmobile.mcp.main.dto;

import java.io.Serializable;
import java.util.Date;

public class NmcpRateRecommendInqrBasDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 문의일련번호 */
	private int rateRecommendInqrSeq;
	/** 정렬순서 */
	private String sortOdrg;
	/** 문의내용 */
	private String rateRecommendInqrSbst;
	/** 답변1 */
	private String ansSbst1;
	/** 답변2 */
	private String ansSbst2;
	/** 답변3 */
	private String ansSbst3;
	/** 답변4 */
	private String ansSbst4;
	/** 코드값(Y : 유효 / N : 유효하지않음) */
	private String useYn;
	/** 게시시작일자 */
	private String pstngStartDate;
	/** 게시종료일자 */
	private String pstngEndDate;
	/** 답변1설명 */
	private String ansSbst1Desc;
	/** 답변2설명 */
	private String ansSbst2Desc;
	/** 답변3설명 */
	private String ansSbst3Desc;
	/** 답변4설명 */
	private String ansSbst4Desc;
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
    
	public int getRateRecommendInqrSeq() {
		return rateRecommendInqrSeq;
	}
	public void setRateRecommendInqrSeq(int rateRecommendInqrSeq) {
		this.rateRecommendInqrSeq = rateRecommendInqrSeq;
	}
	public String getSortOdrg() {
		return sortOdrg;
	}
	public void setSortOdrg(String sortOdrg) {
		this.sortOdrg = sortOdrg;
	}
	public String getRateRecommendInqrSbst() {
		return rateRecommendInqrSbst;
	}
	public void setRateRecommendInqrSbst(String rateRecommendInqrSbst) {
		this.rateRecommendInqrSbst = rateRecommendInqrSbst;
	}
	public String getAnsSbst1() {
		return ansSbst1;
	}
	public void setAnsSbst1(String ansSbst1) {
		this.ansSbst1 = ansSbst1;
	}
	public String getAnsSbst2() {
		return ansSbst2;
	}
	public void setAnsSbst2(String ansSbst2) {
		this.ansSbst2 = ansSbst2;
	}
	public String getAnsSbst3() {
		return ansSbst3;
	}
	public void setAnsSbst3(String ansSbst3) {
		this.ansSbst3 = ansSbst3;
	}
	public String getAnsSbst4() {
		return ansSbst4;
	}
	public void setAnsSbst4(String ansSbst4) {
		this.ansSbst4 = ansSbst4;
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
	public String getAnsSbst1Desc() {
		return ansSbst1Desc;
	}
	public void setAnsSbst1Desc(String ansSbst1Desc) {
		this.ansSbst1Desc = ansSbst1Desc;
	}
	public String getAnsSbst2Desc() {
		return ansSbst2Desc;
	}
	public void setAnsSbst2Desc(String ansSbst2Desc) {
		this.ansSbst2Desc = ansSbst2Desc;
	}
	public String getAnsSbst3Desc() {
		return ansSbst3Desc;
	}
	public void setAnsSbst3Desc(String ansSbst3Desc) {
		this.ansSbst3Desc = ansSbst3Desc;
	}
	public String getAnsSbst4Desc() {
		return ansSbst4Desc;
	}
	public void setAnsSbst4Desc(String ansSbst4Desc) {
		this.ansSbst4Desc = ansSbst4Desc;
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
