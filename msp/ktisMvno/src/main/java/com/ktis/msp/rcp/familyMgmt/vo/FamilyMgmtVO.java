package com.ktis.msp.rcp.familyMgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class FamilyMgmtVO extends BaseVo {

	private static final long serialVersionUID = 1000479942183581913L;
	
	/* 검색조건 */
	private String srchStrtDt;		// 시작일자
	private String srchEndDt;		// 종료일자
	private String useYn;			// 상태
	private String searchGbn;		// 검새구분
	private String searchName;		// 검색명

	private String famSeq;			// 가족관계 시퀀스
	private String strtDttm;		// 시작일시
	private String endDttm;			// 종료일시
	private String endCode;			// 종료코드
	private String endMsg;			// 종료메시지

	public String getSrchStrtDt() {
		return srchStrtDt;
	}

	public void setSrchStrtDt(String srchStrtDt) {
		this.srchStrtDt = srchStrtDt;
	}

	public String getSrchEndDt() {
		return srchEndDt;
	}

	public void setSrchEndDt(String srchEndDt) {
		this.srchEndDt = srchEndDt;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getSearchGbn() {
		return searchGbn;
	}

	public void setSearchGbn(String searchGbn) {
		this.searchGbn = searchGbn;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getFamSeq() {
		return famSeq;
	}

	public void setFamSeq(String famSeq) {
		this.famSeq = famSeq;
	}

	public String getStrtDttm() {
		return strtDttm;
	}

	public void setStrtDttm(String strtDttm) {
		this.strtDttm = strtDttm;
	}

	public String getEndDttm() {
		return endDttm;
	}

	public void setEndDttm(String endDttm) {
		this.endDttm = endDttm;
	}

	public String getEndCode() {
		return endCode;
	}

	public void setEndCode(String endCode) {
		this.endCode = endCode;
	}

	public String getEndMsg() {
		return endMsg;
	}

	public void setEndMsg(String endMsg) {
		this.endMsg = endMsg;
	}
}
