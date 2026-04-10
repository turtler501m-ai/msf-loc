package com.ktis.msp.cmn.btchmgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @author SJLEE
 *
 */
public class BtchMgmtVO extends BaseVo implements Serializable {
	/**
	 *  serialVersion UID
	 */
	private static final long serialVersionUID = 1L;
	
	String btchPrgmId;	// 배치프로그램ID
	String btchPrgmNm;	// 배치프로그램명
	String preOprtPrgmId;	// 선작업배치프로그램ID
	String dutySctn;	// 업무구분
	String btchType;	// 배치유형
	String algnSeq;		// 정렬순서
	String prgmDsc;		// 프로그램설명
	String flfilCycl;	// 실행주기
	String usgYn;		// 사용여부
	String oprtType;	// 작업종류
	String userId;
	String searchCd;
	String searchVal;
	
	public String getBtchPrgmId() {
		return btchPrgmId;
	}
	public void setBtchPrgmId(String btchPrgmId) {
		this.btchPrgmId = btchPrgmId;
	}
	public String getBtchPrgmNm() {
		return btchPrgmNm;
	}
	public void setBtchPrgmNm(String btchPrgmNm) {
		this.btchPrgmNm = btchPrgmNm;
	}
	public String getPreOprtPrgmId() {
		return preOprtPrgmId;
	}
	public void setPreOprtPrgmId(String preOprtPrgmId) {
		this.preOprtPrgmId = preOprtPrgmId;
	}
	public String getDutySctn() {
		return dutySctn;
	}
	public void setDutySctn(String dutySctn) {
		this.dutySctn = dutySctn;
	}
	public String getBtchType() {
		return btchType;
	}
	public void setBtchType(String btchType) {
		this.btchType = btchType;
	}
	public String getAlgnSeq() {
		return algnSeq;
	}
	public void setAlgnSeq(String algnSeq) {
		this.algnSeq = algnSeq;
	}
	public String getPrgmDsc() {
		return prgmDsc;
	}
	public void setPrgmDsc(String prgmDsc) {
		this.prgmDsc = prgmDsc;
	}
	public String getFlfilCycl() {
		return flfilCycl;
	}
	public void setFlfilCycl(String flfilCycl) {
		this.flfilCycl = flfilCycl;
	}
	public String getUsgYn() {
		return usgYn;
	}
	public void setUsgYn(String usgYn) {
		this.usgYn = usgYn;
	}
	public String getOprtType() {
		return oprtType;
	}
	public void setOprtType(String oprtType) {
		this.oprtType = oprtType;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSearchCd() {
		return searchCd;
	}
	public void setSearchCd(String searchCd) {
		this.searchCd = searchCd;
	}
	public String getSearchVal() {
		return searchVal;
	}
	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
	}
	
	
}
