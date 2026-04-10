package com.ktis.msp.cmn.prgmmgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @author SJLEE
 *
 */
public class PrgmMgmtVO extends BaseVo implements Serializable {
	/**
	 *  serialVersion UID
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 * UI 검색
	 */
	String searchStartDate;		// 시작날짜
	String searchEndDate;		// 종료날짜
	String searchPrgmId;		// 프로그램ID
	String searchPrgmNm;		// 프로그램명
	String searchSysSctn;		// 시스템구분
	String searchStatCd;		// 상태
	String searchExecService;	// 실행서비스
	
	/*
	 * 
	 */
	String category;		// 카테고리
	String prgmId;			// 프로그램ID
	String prgmNm;			// 프로그램명
	
	public String getSearchStartDate() {
		return searchStartDate;
	}
	public void setSearchStartDate(String searchStartDate) {
		this.searchStartDate = searchStartDate;
	}
	public String getSearchEndDate() {
		return searchEndDate;
	}
	public void setSearchEndDate(String searchEndDate) {
		this.searchEndDate = searchEndDate;
	}
	public String getSearchPrgmId() {
		return searchPrgmId;
	}
	public void setSearchPrgmId(String searchPrgmId) {
		this.searchPrgmId = searchPrgmId;
	}
	public String getSearchPrgmNm() {
		return searchPrgmNm;
	}
	public void setSearchPrgmNm(String searchPrgmNm) {
		this.searchPrgmNm = searchPrgmNm;
	}
	public String getSearchSysSctn() {
		return searchSysSctn;
	}
	public void setSearchSysSctn(String searchSysSctn) {
		this.searchSysSctn = searchSysSctn;
	}
	public String getSearchStatCd() {
		return searchStatCd;
	}
	public void setSearchStatCd(String searchStatCd) {
		this.searchStatCd = searchStatCd;
	}
	public String getSearchExecService() {
		return searchExecService;
	}
	public void setSearchExecService(String searchExecService) {
		this.searchExecService = searchExecService;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getPrgmId() {
		return prgmId;
	}
	public void setPrgmId(String prgmId) {
		this.prgmId = prgmId;
	}
	public String getPrgmNm() {
		return prgmNm;
	}
	public void setPrgmNm(String prgmNm) {
		this.prgmNm = prgmNm;
	}
}
