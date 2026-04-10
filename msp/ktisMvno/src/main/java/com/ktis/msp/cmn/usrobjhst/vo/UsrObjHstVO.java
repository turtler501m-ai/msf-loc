package com.ktis.msp.cmn.usrobjhst.vo;

import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

public class UsrObjHstVO extends BaseVo implements Serializable{
	
	private String objectName; //object명
	private String owner; //소유자
	private String objectType; //object타입
	private String created; //최초등록일
	private String lastDdlTime; //수정일
	private String regstDttm; //등록일
	private String sourceText; //소스
	
	//검색조건
	private String dateGbn; //일자구분
	private String pSearchStartDt; //검색시작일
	private String pSearchEndDt; //검색종료일
	private String pOwner; //소유자
	private String pObjType; //object타입
	private String pObjName; //object명
	
	// 페이징
	public int TOTAL_COUNT;
	public String ROW_NUM;
	
	//v2018.11 PMD 적용 소스 수정
	public String LINENUM;
	
	public int getTOTAL_COUNT() {
		return TOTAL_COUNT;
	}
	public void setTOTAL_COUNT(int tOTAL_COUNT) {
		TOTAL_COUNT = tOTAL_COUNT;
	}
	public String getROW_NUM() {
		return ROW_NUM;
	}
	public void setROW_NUM(String rOW_NUM) {
		ROW_NUM = rOW_NUM;
	}
	public String getLINENUM() {
		return LINENUM;
	}
	public void setLINENUM(String lINENUM) {
		LINENUM = lINENUM;
	}
	//public String LINENUM;	
	
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getObjectType() {
		return objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getLastDdlTime() {
		return lastDdlTime;
	}
	public void setLastDdlTime(String lastDdlTime) {
		this.lastDdlTime = lastDdlTime;
	}
	public String getRegstDttm() {
		return regstDttm;
	}
	public void setRegstDttm(String regstDttm) {
		this.regstDttm = regstDttm;
	}
	public String getSourceText() {
		return sourceText;
	}
	public void setSourceText(String sourceText) {
		this.sourceText = sourceText;
	}
	public String getDateGbn() {
		return dateGbn;
	}
	public void setDateGbn(String dateGbn) {
		this.dateGbn = dateGbn;
	}
	public String getpSearchStartDt() {
		return pSearchStartDt;
	}
	public void setpSearchStartDt(String pSearchStartDt) {
		this.pSearchStartDt = pSearchStartDt;
	}
	public String getpSearchEndDt() {
		return pSearchEndDt;
	}
	public void setpSearchEndDt(String pSearchEndDt) {
		this.pSearchEndDt = pSearchEndDt;
	}
	public String getpOwner() {
		return pOwner;
	}
	public void setpOwner(String pOwner) {
		this.pOwner = pOwner;
	}
	public String getpObjType() {
		return pObjType;
	}
	public void setpObjType(String pObjType) {
		this.pObjType = pObjType;
	}
	public String getpObjName() {
		return pObjName;
	}
	public void setpObjName(String pObjName) {
		this.pObjName = pObjName;
	}
	
	
}