package com.ktmmobile.mcp.point.dto;

import java.io.Serializable;

public class PointGiveBaseBasDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int pointGiveBaseSeq; // 포인트지급기준일련번호
	private String pointGiveBaseCd; //	포인트지급기준코드
	private String pointGiveBaseNm;
	private String pointGiveCntCd; //	포인트지급횟수코드
	private String pointGiveCntNm;
	private String pointGiveRepeatPeriodCd;//	포인트지급반복기간코드
	private String pointGiveRepeatPeriodNm;
	private String pointGiveFormlCd; //	포인트지급방식코드
	private String pointGiveFormlNm;
	private String pointGiveDateCd; //	포인트지급일자코드
	private String pointGiveDateNm;
	private String pointGiveDate; //	포인트지급일자
	private String pointCalcCd; //	포인트계산코드
	private String pointCalcNm;
	private String pointFamt; //	포인트정액
	private String pointRatio; //	포인트비율
	private String useYn; //	사용여부
	private String pstngStartDate; //	게시시작일자
	private String pstngEndDate; //	게시종료일자
	private String cretIp; //	생성IP
	private String cretDt; //	생성일시
	private String cretId; //	생성자ID
	private String searchValue; // 검색어
	private String searchType; // 검색 유형
	
	
	public String getSearchValue() {
		return searchValue;
	}
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public int getPointGiveBaseSeq() {
		return pointGiveBaseSeq;
	}
	public void setPointGiveBaseSeq(int pointGiveBaseSeq) {
		this.pointGiveBaseSeq = pointGiveBaseSeq;
	}
	public String getPointGiveBaseCd() {
		return pointGiveBaseCd;
	}
	public void setPointGiveBaseCd(String pointGiveBaseCd) {
		this.pointGiveBaseCd = pointGiveBaseCd;
	}
	public String getPointGiveBaseNm() {
		return pointGiveBaseNm;
	}
	public void setPointGiveBaseNm(String pointGiveBaseNm) {
		this.pointGiveBaseNm = pointGiveBaseNm;
	}
	public String getPointGiveCntCd() {
		return pointGiveCntCd;
	}
	public void setPointGiveCntCd(String pointGiveCntCd) {
		this.pointGiveCntCd = pointGiveCntCd;
	}
	public String getPointGiveCntNm() {
		return pointGiveCntNm;
	}
	public void setPointGiveCntNm(String pointGiveCntNm) {
		this.pointGiveCntNm = pointGiveCntNm;
	}
	public String getPointGiveRepeatPeriodCd() {
		return pointGiveRepeatPeriodCd;
	}
	public void setPointGiveRepeatPeriodCd(String pointGiveRepeatPeriodCd) {
		this.pointGiveRepeatPeriodCd = pointGiveRepeatPeriodCd;
	}
	public String getPointGiveRepeatPeriodNm() {
		return pointGiveRepeatPeriodNm;
	}
	public void setPointGiveRepeatPeriodNm(String pointGiveRepeatPeriodNm) {
		this.pointGiveRepeatPeriodNm = pointGiveRepeatPeriodNm;
	}
	public String getPointGiveFormlCd() {
		return pointGiveFormlCd;
	}
	public void setPointGiveFormlCd(String pointGiveFormlCd) {
		this.pointGiveFormlCd = pointGiveFormlCd;
	}
	public String getPointGiveFormlNm() {
		return pointGiveFormlNm;
	}
	public void setPointGiveFormlNm(String pointGiveFormlNm) {
		this.pointGiveFormlNm = pointGiveFormlNm;
	}
	public String getPointGiveDateCd() {
		return pointGiveDateCd;
	}
	public void setPointGiveDateCd(String pointGiveDateCd) {
		this.pointGiveDateCd = pointGiveDateCd;
	}
	public String getPointGiveDateNm() {
		return pointGiveDateNm;
	}
	public void setPointGiveDateNm(String pointGiveDateNm) {
		this.pointGiveDateNm = pointGiveDateNm;
	}
	public String getPointGiveDate() {
		return pointGiveDate;
	}
	public void setPointGiveDate(String pointGiveDate) {
		this.pointGiveDate = pointGiveDate;
	}
	public String getPointCalcCd() {
		return pointCalcCd;
	}
	public void setPointCalcCd(String pointCalcCd) {
		this.pointCalcCd = pointCalcCd;
	}
	public String getPointCalcNm() {
		return pointCalcNm;
	}
	public void setPointCalcNm(String pointCalcNm) {
		this.pointCalcNm = pointCalcNm;
	}
	public String getPointFamt() {
		return pointFamt;
	}
	public void setPointFamt(String pointFamt) {
		this.pointFamt = pointFamt;
	}
	public String getPointRatio() {
		return pointRatio;
	}
	public void setPointRatio(String pointRatio) {
		this.pointRatio = pointRatio;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
