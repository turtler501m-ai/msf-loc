package com.ktis.msp.sale.plcyMgmt.vo;

import java.io.Serializable;
import java.util.List;

import com.ktis.msp.base.mvc.BaseVo;

public class PlcyMgmtVO extends BaseVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// 조회조건
	private String stdrDt;
	private String searchCd;
	private String searchVal;
	private String orgnType;
	private String payClCd;
	private String dataType;
	private String rateType;
	private String prdtTypeCd;
	private String prdtIndCd;
	private String cdVal;
	private String flag;
	private String userId;
	private String userOrgnTypeCd;
	
	private String salePlcyCd;
	private String salePlcyNm;
	private String saleStrtDt;
	private String saleStrtTm;
	private String saleEndDt;
	private String saleEndTm;
	private String applSctnCd;
	private String plcyTypeCd;
	private String plcySctnCd;
	private String prdtSctnCd;
	private String sprtTp;
	private String newYn;
	private String mnpYn;
	private String hcnYn;
	private String hdnYn;
	private String instRate;
	private String cnfmId;
	private String cnfmNm;
	private String cnfmDttm;
	private String orgnId;
	private String orgnNm;
	private String operType;
	private String rateGrpCd;
	private String rateCd;
	private String agrmTrm;
	private String instTrm;
	private String prdtId;
	private String oldYn;
	private int newCmsnAmt;
	private int mnpCmsnAmt;
	private int hcnCmsnAmt;
	private int hdnCmsnAmt;
	private int arpuCmsnAmt;
	private int agncySubsdAmt;
	
	private String searchPlcyTypeCd;
	private String searchPlcySctnCd;
	private String searchDataType;
	private String searchSupportTypeCd;
	
	private String actionType;
	private String plcyEventCd;
	private String resultCd;
	
	// 2017-04-21 요금제추가여부
	private String addRateYn;
	
	// 할부기간 팝업 관련
	private String instNom;

	// [SR-2024-055] 제휴사(위탁온라인, 제휴대리점) 셀프개통URL 제공 및 개발
	// 셀프개통 여부
    private String selfOpenYn;
    private String regId;
    private String regDttm;
    private String rvisnId;
    private String rvisnDttm;
    List<PlcyMgmtVO> items;

	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getRegDttm() {
		return regDttm;
	}
	public void setRegDttm(String regDttm) {
		this.regDttm = regDttm;
	}
	public String getRvisnId() {
		return rvisnId;
	}
	public void setRvisnId(String rvisnId) {
		this.rvisnId = rvisnId;
	}
	public String getRvisnDttm() {
		return rvisnDttm;
	}
	public void setRvisnDttm(String rvisnDttm) {
		this.rvisnDttm = rvisnDttm;
	}
	public List<PlcyMgmtVO> getItems() {
		return items;
	}
	public void setItems(List<PlcyMgmtVO> items) {
		this.items = items;
	}
	public String getInstNom() {
		return instNom;
	}
	public void setInstNom(String instNom) {
		this.instNom = instNom;
	}
	public String getStdrDt() {
		return stdrDt;
	}
	public void setStdrDt(String stdrDt) {
		this.stdrDt = stdrDt;
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
	public String getOrgnType() {
		return orgnType;
	}
	public void setOrgnType(String orgnType) {
		this.orgnType = orgnType;
	}
	public String getPayClCd() {
		return payClCd;
	}
	public void setPayClCd(String payClCd) {
		this.payClCd = payClCd;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getRateType() {
		return rateType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	public String getPrdtTypeCd() {
		return prdtTypeCd;
	}
	public void setPrdtTypeCd(String prdtTypeCd) {
		this.prdtTypeCd = prdtTypeCd;
	}
	public String getPrdtIndCd() {
		return prdtIndCd;
	}
	public void setPrdtIndCd(String prdtIndCd) {
		this.prdtIndCd = prdtIndCd;
	}
	public String getCdVal() {
		return cdVal;
	}
	public void setCdVal(String cdVal) {
		this.cdVal = cdVal;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserOrgnTypeCd() {
		return userOrgnTypeCd;
	}
	public void setUserOrgnTypeCd(String userOrgnTypeCd) {
		this.userOrgnTypeCd = userOrgnTypeCd;
	}
	public String getSalePlcyCd() {
		return salePlcyCd;
	}
	public void setSalePlcyCd(String salePlcyCd) {
		this.salePlcyCd = salePlcyCd;
	}
	public String getSalePlcyNm() {
		return salePlcyNm;
	}
	public void setSalePlcyNm(String salePlcyNm) {
		this.salePlcyNm = salePlcyNm;
	}
	public String getSaleStrtDt() {
		return saleStrtDt;
	}
	public void setSaleStrtDt(String saleStrtDt) {
		this.saleStrtDt = saleStrtDt;
	}
	public String getSaleStrtTm() {
		return saleStrtTm;
	}
	public void setSaleStrtTm(String saleStrtTm) {
		this.saleStrtTm = saleStrtTm;
	}
	public String getSaleEndDt() {
		return saleEndDt;
	}
	public void setSaleEndDt(String saleEndDt) {
		this.saleEndDt = saleEndDt;
	}
	public String getSaleEndTm() {
		return saleEndTm;
	}
	public void setSaleEndTm(String saleEndTm) {
		this.saleEndTm = saleEndTm;
	}
	public String getApplSctnCd() {
		return applSctnCd;
	}
	public void setApplSctnCd(String applSctnCd) {
		this.applSctnCd = applSctnCd;
	}
	public String getPlcyTypeCd() {
		return plcyTypeCd;
	}
	public void setPlcyTypeCd(String plcyTypeCd) {
		this.plcyTypeCd = plcyTypeCd;
	}
	public String getPlcySctnCd() {
		return plcySctnCd;
	}
	public void setPlcySctnCd(String plcySctnCd) {
		this.plcySctnCd = plcySctnCd;
	}
	public String getPrdtSctnCd() {
		return prdtSctnCd;
	}
	public void setPrdtSctnCd(String prdtSctnCd) {
		this.prdtSctnCd = prdtSctnCd;
	}
	public String getSprtTp() {
		return sprtTp;
	}
	public void setSprtTp(String sprtTp) {
		this.sprtTp = sprtTp;
	}
	public String getNewYn() {
		return newYn;
	}
	public void setNewYn(String newYn) {
		this.newYn = newYn;
	}
	public String getMnpYn() {
		return mnpYn;
	}
	public void setMnpYn(String mnpYn) {
		this.mnpYn = mnpYn;
	}
	public String getHcnYn() {
		return hcnYn;
	}
	public void setHcnYn(String hcnYn) {
		this.hcnYn = hcnYn;
	}
	public String getHdnYn() {
		return hdnYn;
	}
	public void setHdnYn(String hdnYn) {
		this.hdnYn = hdnYn;
	}
	public String getInstRate() {
		return instRate;
	}
	public void setInstRate(String instRate) {
		this.instRate = instRate;
	}
	public String getCnfmId() {
		return cnfmId;
	}
	public void setCnfmId(String cnfmId) {
		this.cnfmId = cnfmId;
	}
	public String getCnfmNm() {
		return cnfmNm;
	}
	public void setCnfmNm(String cnfmNm) {
		this.cnfmNm = cnfmNm;
	}
	public String getCnfmDttm() {
		return cnfmDttm;
	}
	public void setCnfmDttm(String cnfmDttm) {
		this.cnfmDttm = cnfmDttm;
	}
	public String getOrgnId() {
		return orgnId;
	}
	public void setOrgnId(String orgnId) {
		this.orgnId = orgnId;
	}
	public String getOrgnNm() {
		return orgnNm;
	}
	public void setOrgnNm(String orgnNm) {
		this.orgnNm = orgnNm;
	}
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	public String getRateGrpCd() {
		return rateGrpCd;
	}
	public void setRateGrpCd(String rateGrpCd) {
		this.rateGrpCd = rateGrpCd;
	}
	public String getRateCd() {
		return rateCd;
	}
	public void setRateCd(String rateCd) {
		this.rateCd = rateCd;
	}
	public String getAgrmTrm() {
		return agrmTrm;
	}
	public void setAgrmTrm(String agrmTrm) {
		this.agrmTrm = agrmTrm;
	}
	public String getInstTrm() {
		return instTrm;
	}
	public void setInstTrm(String instTrm) {
		this.instTrm = instTrm;
	}
	public String getPrdtId() {
		return prdtId;
	}
	public void setPrdtId(String prdtId) {
		this.prdtId = prdtId;
	}
	public String getOldYn() {
		return oldYn;
	}
	public void setOldYn(String oldYn) {
		this.oldYn = oldYn;
	}
	public int getNewCmsnAmt() {
		return newCmsnAmt;
	}
	public void setNewCmsnAmt(int newCmsnAmt) {
		this.newCmsnAmt = newCmsnAmt;
	}
	public int getMnpCmsnAmt() {
		return mnpCmsnAmt;
	}
	public void setMnpCmsnAmt(int mnpCmsnAmt) {
		this.mnpCmsnAmt = mnpCmsnAmt;
	}
	public int getHcnCmsnAmt() {
		return hcnCmsnAmt;
	}
	public void setHcnCmsnAmt(int hcnCmsnAmt) {
		this.hcnCmsnAmt = hcnCmsnAmt;
	}
	public int getHdnCmsnAmt() {
		return hdnCmsnAmt;
	}
	public void setHdnCmsnAmt(int hdnCmsnAmt) {
		this.hdnCmsnAmt = hdnCmsnAmt;
	}
	public int getArpuCmsnAmt() {
		return arpuCmsnAmt;
	}
	public void setArpuCmsnAmt(int arpuCmsnAmt) {
		this.arpuCmsnAmt = arpuCmsnAmt;
	}
	public int getAgncySubsdAmt() {
		return agncySubsdAmt;
	}
	public void setAgncySubsdAmt(int agncySubsdAmt) {
		this.agncySubsdAmt = agncySubsdAmt;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * @return the searchPlcyTypeCd
	 */
	public String getSearchPlcyTypeCd() {
		return searchPlcyTypeCd;
	}
	/**
	 * @param searchPlcyTypeCd the searchPlcyTypeCd to set
	 */
	public void setSearchPlcyTypeCd(String searchPlcyTypeCd) {
		this.searchPlcyTypeCd = searchPlcyTypeCd;
	}
	/**
	 * @return the searchPlcySctnCd
	 */
	public String getSearchPlcySctnCd() {
		return searchPlcySctnCd;
	}
	/**
	 * @param searchPlcySctnCd the searchPlcySctnCd to set
	 */
	public void setSearchPlcySctnCd(String searchPlcySctnCd) {
		this.searchPlcySctnCd = searchPlcySctnCd;
	}
	/**
	 * @return the searchDataType
	 */
	public String getSearchDataType() {
		return searchDataType;
	}
	/**
	 * @param searchDataType the searchDataType to set
	 */
	public void setSearchDataType(String searchDataType) {
		this.searchDataType = searchDataType;
	}
	
	public String getSearchSupportTypeCd() {
		return searchSupportTypeCd;
	}
	public void setSearchSupportTypeCd(String searchSupportTypeCd) {
		this.searchSupportTypeCd = searchSupportTypeCd;
	}
	
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getPlcyEventCd() {
		return plcyEventCd;
	}
	public void setPlcyEventCd(String plcyEventCd) {
		this.plcyEventCd = plcyEventCd;
	}
	public String getResultCd() {
		return resultCd;
	}
	public void setResultCd(String resultCd) {
		this.resultCd = resultCd;
	}
	public String getAddRateYn() {
		return addRateYn;
	}
	public void setAddRateYn(String addRateYn) {
		this.addRateYn = addRateYn;
	}

	public String getSelfOpenYn() {
		return selfOpenYn;
	}

	public void setSelfOpenYn(String selfOpenYn) {
		this.selfOpenYn = selfOpenYn;
	}
}
