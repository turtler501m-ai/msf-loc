package com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.BaseVo;

public class DongbuPointVO extends BaseVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String partnerId;
	private String contractNum;
	private String endDttm;
	private String strtDttm;
	private String customerId;
	private String rateCd;
	private String statCd;
	private String evntCd;
	
	// 파일생성시 사용할 변수
	private String custNm;
	private String ctn;
	private String minorAgentNm;
	private String minorAgentTel;
	private String subStatus;
	private String joinDt;
	private String changeDt;
	private String efctStrtDt;
	private String rateNm;
	private String openAgntNm;
	private String pymnYn;
	private String birthDt;
	
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getEndDttm() {
		return endDttm;
	}
	public void setEndDttm(String endDttm) {
		this.endDttm = endDttm;
	}
	public String getStrtDttm() {
		return strtDttm;
	}
	public void setStrtDttm(String strtDttm) {
		this.strtDttm = strtDttm;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getRateCd() {
		return rateCd;
	}
	public void setRateCd(String rateCd) {
		this.rateCd = rateCd;
	}
	public String getStatCd() {
		return statCd;
	}
	public void setStatCd(String statCd) {
		this.statCd = statCd;
	}
	public String getEvntCd() {
		return evntCd;
	}
	public void setEvntCd(String evntCd) {
		this.evntCd = evntCd;
	}
	public String getCustNm() {
		return custNm;
	}
	public void setCustNm(String custNm) {
		this.custNm = custNm;
	}
	public String getCtn() {
		return ctn;
	}
	public void setCtn(String ctn) {
		this.ctn = ctn;
	}
	public String getMinorAgentNm() {
		return minorAgentNm;
	}
	public void setMinorAgentNm(String minorAgentNm) {
		this.minorAgentNm = minorAgentNm;
	}
	public String getMinorAgentTel() {
		return minorAgentTel;
	}
	public void setMinorAgentTel(String minorAgentTel) {
		this.minorAgentTel = minorAgentTel;
	}
	public String getSubStatus() {
		return subStatus;
	}
	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}
	public String getJoinDt() {
		return joinDt;
	}
	public void setJoinDt(String joinDt) {
		this.joinDt = joinDt;
	}
	public String getChangeDt() {
		return changeDt;
	}
	public void setChangeDt(String changeDt) {
		this.changeDt = changeDt;
	}
	public String getEfctStrtDt() {
		return efctStrtDt;
	}
	public void setEfctStrtDt(String efctStrtDt) {
		this.efctStrtDt = efctStrtDt;
	}
	public String getRateNm() {
		return rateNm;
	}
	public void setRateNm(String rateNm) {
		this.rateNm = rateNm;
	}
	public String getOpenAgntNm() {
		return openAgntNm;
	}
	public void setOpenAgntNm(String openAgntNm) {
		this.openAgntNm = openAgntNm;
	}
	public String getPymnYn() {
		return pymnYn;
	}
	public void setPymnYn(String pymnYn) {
		this.pymnYn = pymnYn;
	}
	public String getBirthDt() {
		return birthDt;
	}
	public void setBirthDt(String birthDt) {
		this.birthDt = birthDt;
	}
	
	
}
