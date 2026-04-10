package com.ktis.msp.rcp.rcpMgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

public class RcpCombVO extends BaseVo implements Serializable {
	
	private static final long serialVersionUID = 3044976242494530140L;

	private String strtDt;
	private String endDt;
	private String searchCd;
	private String searchVal;
	private String rsltCd;
	private String combTypeCd; // 결합유형
	private String rvisnId;
	private String mobileNo;
	private int reqSeq; //idx
	private String rsltMemo;
	
	private String combTgtTypeCd; // 결합대상										
	private String mSvcCntrNo;  // 모계약 계약번호
	private String cstmrName;  // 모계약 고객명
	private String mCustBirth;  // 모계약 생년월일								
	private String tel1;
	private String tel1Fn;  // 모계약 전화1
	private String tel1Mn;  // 모계약 전화2
	private String tel1Rn;  // 모계약 전화3								
	private String mRateCd; // 모계약 상품코드
	private String mRateNm;  // 모계약상품명
	private String mSexCd; // 모계약 성별
	private String mSexNm; // 모계약 성별
	private String mRateAdsvcCd; // 모계약 부가서비스코드
	private String mRateAdsvcNm; // 모계약 부가서비스코명							
	private String combSvcCntrNo; // 결합 계약번호
	private String cstmrName2;  // 결합 고객명
	private String combCustBirth; // 결합 생년월일
	private String tel2;
	private String tel2Fn; // 결합  전화1
	private String tel2Mn; // 결합  전화2
	private String tel2Rn; // 결합  전화3
	private String combSocCd; // 결합 상품코드
	private String combSocNm; // 결합 상품명
	private String combSexCd; // 결합 성별
	private String combSexNm; // 결합 성별
	private String combRateAdsvcCd; // 결합 부가서비스코드
	private String combRateAdsvcNm; // 결합 부가서비스명
    private String regstId; // 등록자 ID 

	private String pRateCd;
	private String rRateCd;

	public String getStrtDt() {
		return strtDt;
	}
	public void setStrtDt(String strtDt) {
		this.strtDt = strtDt;
	}
	public String getEndDt() {
		return endDt;
	}
	public void setEndDt(String endDt) {
		this.endDt = endDt;
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
	public String getRsltCd() {
		return rsltCd;
	}
	public void setRsltCd(String rsltCd) {
		this.rsltCd = rsltCd;
	}
	public String getCombTypeCd() {
		return combTypeCd;
	}
	public void setCombTypeCd(String combTypeCd) {
		this.combTypeCd = combTypeCd;
	}
	public String getRvisnId() {
		return rvisnId;
	}
	public void setRvisnId(String rvisnId) {
		this.rvisnId = rvisnId;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getRsltMemo() {
		return rsltMemo;
	}
	public void setRsltMemo(String rsltMemo) {
		this.rsltMemo = rsltMemo;
	}
	public int getReqSeq() {
		return reqSeq;
	}
	public void setReqSeq(int reqSeq) {
		this.reqSeq = reqSeq;
	}
	public String getCombTgtTypeCd() {
		return combTgtTypeCd;
	}
	public void setCombTgtTypeCd(String combTgtTypeCd) {
		this.combTgtTypeCd = combTgtTypeCd;
	}
	public String getmSvcCntrNo() {
		return mSvcCntrNo;
	}
	public void setmSvcCntrNo(String mSvcCntrNo) {
		this.mSvcCntrNo = mSvcCntrNo;
	}
	public String getCstmrName() {
		return cstmrName;
	}
	public void setCstmrName(String cstmrName) {
		this.cstmrName = cstmrName;
	}
	public String getmCustBirth() {
		return mCustBirth;
	}
	public void setmCustBirth(String mCustBirth) {
		this.mCustBirth = mCustBirth;
	}
	public String getTel1() {
		return tel1;
	}
	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}
	public String getTel1Fn() {
		return tel1Fn;
	}
	public void setTel1Fn(String tel1Fn) {
		this.tel1Fn = tel1Fn;
	}
	public String getTel1Mn() {
		return tel1Mn;
	}
	public void setTel1Mn(String tel1Mn) {
		this.tel1Mn = tel1Mn;
	}
	public String getTel1Rn() {
		return tel1Rn;
	}
	public void setTel1Rn(String tel1Rn) {
		this.tel1Rn = tel1Rn;
	}
	public String getmRateCd() {
		return mRateCd;
	}
	public void setmRateCd(String mRateCd) {
		this.mRateCd = mRateCd;
	}
	public String getmRateNm() {
		return mRateNm;
	}
	public void setmRateNm(String mRateNm) {
		this.mRateNm = mRateNm;
	}
	public String getmSexCd() {
		return mSexCd;
	}
	public void setmSexCd(String mSexCd) {
		this.mSexCd = mSexCd;
	}
	public String getmSexNm() {
		return mSexNm;
	}
	public void setmSexNm(String mSexNm) {
		this.mSexNm = mSexNm;
	}
	public String getmRateAdsvcCd() {
		return mRateAdsvcCd;
	}
	public void setmRateAdsvcCd(String mRateAdsvcCd) {
		this.mRateAdsvcCd = mRateAdsvcCd;
	}
	public String getmRateAdsvcNm() {
		return mRateAdsvcNm;
	}
	public void setmRateAdsvcNm(String mRateAdsvcNm) {
		this.mRateAdsvcNm = mRateAdsvcNm;
	}
	public String getCombSvcCntrNo() {
		return combSvcCntrNo;
	}
	public void setCombSvcCntrNo(String combSvcCntrNo) {
		this.combSvcCntrNo = combSvcCntrNo;
	}
	public String getCstmrName2() {
		return cstmrName2;
	}
	public void setCstmrName2(String cstmrName2) {
		this.cstmrName2 = cstmrName2;
	}
	public String getCombCustBirth() {
		return combCustBirth;
	}
	public void setCombCustBirth(String combCustBirth) {
		this.combCustBirth = combCustBirth;
	}
	public String getTel2() {
		return tel2;
	}
	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}
	public String getTel2Fn() {
		return tel2Fn;
	}
	public void setTel2Fn(String tel2Fn) {
		this.tel2Fn = tel2Fn;
	}
	public String getTel2Mn() {
		return tel2Mn;
	}
	public void setTel2Mn(String tel2Mn) {
		this.tel2Mn = tel2Mn;
	}
	public String getTel2Rn() {
		return tel2Rn;
	}
	public void setTel2Rn(String tel2Rn) {
		this.tel2Rn = tel2Rn;
	}
	public String getCombSocCd() {
		return combSocCd;
	}
	public void setCombSocCd(String combSocCd) {
		this.combSocCd = combSocCd;
	}
	public String getCombSocNm() {
		return combSocNm;
	}
	public void setCombSocNm(String combSocNm) {
		this.combSocNm = combSocNm;
	}
	public String getCombSexCd() {
		return combSexCd;
	}
	public void setCombSexCd(String combSexCd) {
		this.combSexCd = combSexCd;
	}
	public String getCombSexNm() {
		return combSexNm;
	}
	public void setCombSexNm(String combSexNm) {
		this.combSexNm = combSexNm;
	}
	public String getCombRateAdsvcCd() {
		return combRateAdsvcCd;
	}
	public void setCombRateAdsvcCd(String combRateAdsvcCd) {
		this.combRateAdsvcCd = combRateAdsvcCd;
	}
	public String getCombRateAdsvcNm() {
		return combRateAdsvcNm;
	}
	public void setCombRateAdsvcNm(String combRateAdsvcNm) {
		this.combRateAdsvcNm = combRateAdsvcNm;
	}
	public String getRegstId() {
		return regstId;
	}
	public void setRegstId(String regstId) {
		this.regstId = regstId;
	}

	public String getpRateCd() {
		return pRateCd;
	}

	public void setpRateCd(String pRateCd) {
		this.pRateCd = pRateCd;
	}

	public String getrRateCd() {
		return rRateCd;
	}

	public void setrRateCd(String rRateCd) {
		this.rRateCd = rRateCd;
	}
}
