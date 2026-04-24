package com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.BaseVo;

public class RateMgmtVO extends BaseVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7010252187382141296L;
	
	private String rateCd;
	private String rateNm;
	private String rateGrpCd;
	private String rateGrpNm;
	private String applStrtDt;
	private String applEndDt;
	private String payClCd;
	private String rateType;
	private String dataType;
	private int baseAmt;
	private int dcAmt;
	private String freeCallClCd;
	private String freeCallCnt;
	private String nwInCallCnt;
	private String nwOutCallCnt;
	private String freeSmsCnt;
	private String freeDataCnt;
	private String userId;
	private String searchGb;
	private String searchVal;
	private String stdrDt;
	private String rmk;
	private String sort;
	private String agrmTrm;
	private String onlineTypeCd;
	
	private String tmAmt;	// 단말할인
	private String rtAmt;	// 요금할인
	private String spAmt;	// 심플할인
	private String serviceType; 	// 서비스유형(기본,부가서비스)
	private String sprtTp;			// 지원금유형(단말할인,요금할인)
	private String applStrtDttm;	// 할인금액 시작일시
	private String applEndDttm;		// 할인금액 종료일시
	
	private String cmnt;
	
	public String getRateCd() {
		return rateCd;
	}
	public void setRateCd(String rateCd) {
		this.rateCd = rateCd;
	}
	public String getRateNm() {
		return rateNm;
	}
	public void setRateNm(String rateNm) {
		this.rateNm = rateNm;
	}
	public String getRateGrpCd() {
		return rateGrpCd;
	}
	public void setRateGrpCd(String rateGrpCd) {
		this.rateGrpCd = rateGrpCd;
	}
	public String getRateGrpNm() {
		return rateGrpNm;
	}
	public void setRateGrpNm(String rateGrpNm) {
		this.rateGrpNm = rateGrpNm;
	}
	public String getApplStrtDt() {
		return applStrtDt;
	}
	public void setApplStrtDt(String applStrtDt) {
		this.applStrtDt = applStrtDt;
	}
	public String getApplEndDt() {
		return applEndDt;
	}
	public void setApplEndDt(String applEndDt) {
		this.applEndDt = applEndDt;
	}
	public String getPayClCd() {
		return payClCd;
	}
	public void setPayClCd(String payClCd) {
		this.payClCd = payClCd;
	}
	public String getRateType() {
		return rateType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public int getBaseAmt() {
		return baseAmt;
	}
	public void setBaseAmt(int baseAmt) {
		this.baseAmt = baseAmt;
	}
	public int getDcAmt() {
		return dcAmt;
	}
	public void setDcAmt(int dcAmt) {
		this.dcAmt = dcAmt;
	}
	public String getFreeCallClCd() {
		return freeCallClCd;
	}
	public void setFreeCallClCd(String freeCallClCd) {
		this.freeCallClCd = freeCallClCd;
	}
	public String getFreeCallCnt() {
		return freeCallCnt;
	}
	public void setFreeCallCnt(String freeCallCnt) {
		this.freeCallCnt = freeCallCnt;
	}
	public String getNwInCallCnt() {
		return nwInCallCnt;
	}
	public void setNwInCallCnt(String nwInCallCnt) {
		this.nwInCallCnt = nwInCallCnt;
	}
	public String getNwOutCallCnt() {
		return nwOutCallCnt;
	}
	public void setNwOutCallCnt(String nwOutCallCnt) {
		this.nwOutCallCnt = nwOutCallCnt;
	}
	public String getFreeSmsCnt() {
		return freeSmsCnt;
	}
	public void setFreeSmsCnt(String freeSmsCnt) {
		this.freeSmsCnt = freeSmsCnt;
	}
	public String getFreeDataCnt() {
		return freeDataCnt;
	}
	public void setFreeDataCnt(String freeDataCnt) {
		this.freeDataCnt = freeDataCnt;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSearchGb() {
		return searchGb;
	}
	public void setSearchGb(String searchGb) {
		this.searchGb = searchGb;
	}
	public String getSearchVal() {
		return searchVal;
	}
	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
	}
	public String getStdrDt() {
		return stdrDt;
	}
	public void setStdrDt(String stdrDt) {
		this.stdrDt = stdrDt;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getRmk() {
		return rmk;
	}
	public void setRmk(String rmk) {
		this.rmk = rmk;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getAgrmTrm() {
		return agrmTrm;
	}
	public void setAgrmTrm(String agrmTrm) {
		this.agrmTrm = agrmTrm;
	}
	/**
	 * @return the onlineTypeCd
	 */
	public String getOnlineTypeCd() {
		return onlineTypeCd;
	}
	/**
	 * @param onlineTypeCd the onlineTypeCd to set
	 */
	public void setOnlineTypeCd(String onlineTypeCd) {
		this.onlineTypeCd = onlineTypeCd;
	}
	
	/**
	 * @return the serviceType
	 */
	public String getServiceType() {
		return serviceType;
	}
	/**
	 * @param serviceType the serviceType to set
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	/**
	 * @return the sprtTp
	 */
	public String getSprtTp() {
		return sprtTp;
	}
	/**
	 * @param sprtTp the sprtTp to set
	 */
	public void setSprtTp(String sprtTp) {
		this.sprtTp = sprtTp;
	}
	/**
	 * @return the applStrtDttm
	 */
	public String getApplStrtDttm() {
		return applStrtDttm;
	}
	/**
	 * @param applStrtDttm the applStrtDttm to set
	 */
	public void setApplStrtDttm(String applStrtDttm) {
		this.applStrtDttm = applStrtDttm;
	}
	/**
	 * @return the applEndDttm
	 */
	public String getApplEndDttm() {
		return applEndDttm;
	}
	/**
	 * @param applEndDttm the applEndDttm to set
	 */
	public void setApplEndDttm(String applEndDttm) {
		this.applEndDttm = applEndDttm;
	}
	/**
	 * @return the tmAmt
	 */
	public String getTmAmt() {
		return tmAmt;
	}
	/**
	 * @param tmAmt the tmAmt to set
	 */
	public void setTmAmt(String tmAmt) {
		this.tmAmt = tmAmt;
	}
	/**
	 * @return the rtAmt
	 */
	public String getRtAmt() {
		return rtAmt;
	}
	/**
	 * @param rtAmt the rtAmt to set
	 */
	public void setRtAmt(String rtAmt) {
		this.rtAmt = rtAmt;
	}
	/**
	 * @return the spAmt
	 */
	public String getSpAmt() {
		return spAmt;
	}
	/**
	 * @param spAmt the spAmt to set
	 */
	public void setSpAmt(String spAmt) {
		this.spAmt = spAmt;
	}
	
	public String getCmnt() {
		return cmnt;
	}
	public void setCmnt(String cmnt) {
		this.cmnt = cmnt;
	}
}
