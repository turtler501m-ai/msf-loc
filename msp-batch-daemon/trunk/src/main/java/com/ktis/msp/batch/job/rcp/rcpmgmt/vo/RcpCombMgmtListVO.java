package com.ktis.msp.batch.job.rcp.rcpmgmt.vo;

import com.ktis.msp.base.BaseVo;

public class RcpCombMgmtListVO extends BaseVo  {
	
	private String combTypeNm;
	private String cstmrName;
	private String mSexNm;
	private String mCustBirth;
	private String tel1;
	private String mRateNm;
	private String mRateCd;
	private String mRateAdsvcNm;
	private String mRateAdsvcCd;
	private String cstmrName2;
	private String combSexNm;
	private String combBirth;
	private String tel2;
	private String combDtlTypeNm;
	private String combSocNm;
	private String combSocCd;
	private String combRateAdsvcNm;
	private String combRateAdsvcCd;
	private String sysDt;
	private String combTgtTypeNm;
	private String rsltNm;
	private String rsltCd;
	private String rsltMemo;
	private String mSvcCntrNo;
	private String combSvcCntrNo;
	
	private String mOpenAgntCd;			//모회선 대리점코드
	private String mOpenAgntNm;			//모회선 대리점명
	private String combOpenAgntCd;		//결합회선 대리점코드
	private String combOpenAgntNm;		//결합회선 대리점명
	private String moveCompanyNm;		//모회선 이동전통신사
	private String combMoveCompanyNm;	//결합회선 이동전통신사
	
	// 검색조건
	private String strtDt;
	private String endDt;
	private String searchCd;
	private String searchVal;
	private String combTypeCd;
	
	private String userId;
	
	
	// 엑셀다운로드 로그
	private String dwnldRsn;			/*다운로드 사유*/
	private String exclDnldId;	
	private String ipAddr;				/*ip정보*/
	private String menuId;				/*메뉴ID*/
	
	
	public String getCombTypeNm() {
		return combTypeNm;
	}
	public void setCombTypeNm(String combTypeNm) {
		this.combTypeNm = combTypeNm;
	}
	public String getCstmrName() {
		return cstmrName;
	}
	public void setCstmrName(String cstmrName) {
		this.cstmrName = cstmrName;
	}
	public String getmSexNm() {
		return mSexNm;
	}
	public void setmSexNm(String mSexNm) {
		this.mSexNm = mSexNm;
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
	public String getmRateNm() {
		return mRateNm;
	}
	public void setmRateNm(String mRateNm) {
		this.mRateNm = mRateNm;
	}
	public String getmRateCd() {
		return mRateCd;
	}
	public void setmRateCd(String mRateCd) {
		this.mRateCd = mRateCd;
	}
	public String getmRateAdsvcNm() {
		return mRateAdsvcNm;
	}
	public void setmRateAdsvcNm(String mRateAdsvcNm) {
		this.mRateAdsvcNm = mRateAdsvcNm;
	}
	public String getmRateAdsvcCd() {
		return mRateAdsvcCd;
	}
	public void setmRateAdsvcCd(String mRateAdsvcCd) {
		this.mRateAdsvcCd = mRateAdsvcCd;
	}
	public String getCstmrName2() {
		return cstmrName2;
	}
	public void setCstmrName2(String cstmrName2) {
		this.cstmrName2 = cstmrName2;
	}
	public String getCombSexNm() {
		return combSexNm;
	}
	public void setCombSexNm(String combSexNm) {
		this.combSexNm = combSexNm;
	}
	public String getCombBirth() {
		return combBirth;
	}
	public void setCombBirth(String combBirth) {
		this.combBirth = combBirth;
	}
	public String getTel2() {
		return tel2;
	}
	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}
	public String getCombDtlTypeNm() {
		return combDtlTypeNm;
	}
	public void setCombDtlTypeNm(String combDtlTypeNm) {
		this.combDtlTypeNm = combDtlTypeNm;
	}
	public String getCombSocNm() {
		return combSocNm;
	}
	public void setCombSocNm(String combSocNm) {
		this.combSocNm = combSocNm;
	}
	public String getCombSocCd() {
		return combSocCd;
	}
	public void setCombSocCd(String combSocCd) {
		this.combSocCd = combSocCd;
	}
	public String getCombRateAdsvcNm() {
		return combRateAdsvcNm;
	}
	public void setCombRateAdsvcNm(String combRateAdsvcNm) {
		this.combRateAdsvcNm = combRateAdsvcNm;
	}
	public String getCombRateAdsvcCd() {
		return combRateAdsvcCd;
	}
	public void setCombRateAdsvcCd(String combRateAdsvcCd) {
		this.combRateAdsvcCd = combRateAdsvcCd;
	}
	public String getSysDt() {
		return sysDt;
	}
	public void setSysDt(String sysDt) {
		this.sysDt = sysDt;
	}
	public String getCombTgtTypeNm() {
		return combTgtTypeNm;
	}
	public void setCombTgtTypeNm(String combTgtTypeNm) {
		this.combTgtTypeNm = combTgtTypeNm;
	}
	public String getRsltNm() {
		return rsltNm;
	}
	public void setRsltNm(String rsltNm) {
		this.rsltNm = rsltNm;
	}
	public String getRsltCd() {
		return rsltCd;
	}
	public void setRsltCd(String rsltCd) {
		this.rsltCd = rsltCd;
	}
	public String getRsltMemo() {
		return rsltMemo;
	}
	public void setRsltMemo(String rsltMemo) {
		this.rsltMemo = rsltMemo;
	}
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
	public String getCombTypeCd() {
		return combTypeCd;
	}
	public void setCombTypeCd(String combTypeCd) {
		this.combTypeCd = combTypeCd;
	}
	public String getmSvcCntrNo() {
		return mSvcCntrNo;
	}
	public void setmSvcCntrNo(String mSvcCntrNo) {
		this.mSvcCntrNo = mSvcCntrNo;
	}
	public String getCombSvcCntrNo() {
		return combSvcCntrNo;
	}
	public void setCombSvcCntrNo(String combSvcCntrNo) {
		this.combSvcCntrNo = combSvcCntrNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDwnldRsn() {
		return dwnldRsn;
	}
	public void setDwnldRsn(String dwnldRsn) {
		this.dwnldRsn = dwnldRsn;
	}
	public String getExclDnldId() {
		return exclDnldId;
	}
	public void setExclDnldId(String exclDnldId) {
		this.exclDnldId = exclDnldId;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getmOpenAgntCd() {
		return mOpenAgntCd;
	}
	public void setmOpenAgntCd(String mOpenAgntCd) {
		this.mOpenAgntCd = mOpenAgntCd;
	}
	public String getmOpenAgntNm() {
		return mOpenAgntNm;
	}
	public void setmOpenAgntNm(String mOpenAgntNm) {
		this.mOpenAgntNm = mOpenAgntNm;
	}
	public String getCombOpenAgntCd() {
		return combOpenAgntCd;
	}
	public void setCombOpenAgntCd(String combOpenAgntCd) {
		this.combOpenAgntCd = combOpenAgntCd;
	}
	public String getCombOpenAgntNm() {
		return combOpenAgntNm;
	}
	public void setCombOpenAgntNm(String combOpenAgntNm) {
		this.combOpenAgntNm = combOpenAgntNm;
	}
	public String getMoveCompanyNm() {
		return moveCompanyNm;
	}
	public void setMoveCompanyNm(String moveCompanyNm) {
		this.moveCompanyNm = moveCompanyNm;
	}
	public String getCombMoveCompanyNm() {
		return combMoveCompanyNm;
	}
	public void setCombMoveCompanyNm(String combMoveCompanyNm) {
		this.combMoveCompanyNm = combMoveCompanyNm;
	}
		
}
