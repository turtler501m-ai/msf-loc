package com.ktis.msp.gift.prmtmgmt.vo;

import java.util.List;

import com.ktis.msp.base.mvc.BaseVo;

public class GiftPrmtMgmtVO extends BaseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3234747999341497150L;
	
	private String searchBaseDate;
	private String searchBaseEndDate;
	private String prmtId;
	private String prmtNm;
	private String strtDt;
	private String endDt;
	private String nacYn;
	private String mnpYn;
	private String enggCnt0;
	private String enggCnt12;
	private String enggCnt18;
	private String enggCnt24;
	private String enggCnt30;
	private String enggCnt36;
	private String usgYn;
	private String regstId;
	private String regstDttm;
	private String rvisnId;
	private String rvisnDttm;
	private String regstNm;
	private String rvisnNm;
	private String orgnId;
	private String orgnNm;
	private String rateCd;
	private String rateNm;
	private String soc;
	private String socNm;
	private String socAmt;
	
	private String trgtPrmtId;
	private String reqBuyType;
	private String reqBuyTypeNm;
	private String chngTypeCd;
	private String orgnType;
	private String orgnTypeNm;
	private String showYn;
	private String showYnNm;
	private String billDt;
	private String prmtOrgnId;
	private String prmtOrgnNm;
	
	private String prmtText;
	
	private String prmtType;
	private String prmtTypeNm;
	
	private String wirelessYn;
	private String wirelessYnNm;
	
	// 기본 사은품은 null로 저장하기 위해서 Integer 선언
	private Integer choiceLimit;
	
	
	/* 페이징 처리용 */
	public int totalCount;
	public String rowNum;
	public String linenum;
	
	List<GiftPrmtMgmtSubVO> orgnList;
	List<GiftPrmtMgmtSubVO> rateList;
	List<GiftPrmtMgmtSubVO> giftPrdtList;
	
	// 모집경로 추가
	private String onOffType;
	private String onOffTypeNm;
	List<GiftPrmtMgmtSubVO> onOffTypeList;
	
	// 대상제품 추가
	private String modelId;
	private String modelCode;
	private String modelNm;
	List<GiftPrmtMgmtSubVO> modelList;
	
	//사은품ID
	private String prdtId;
	private int sort;
	
	//총금액 제한
	private String amountLimit;
	
	
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	public String getModelCode() {
		return modelCode;
	}
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}
	public String getModelNm() {
		return modelNm;
	}
	public void setModelNm(String modelNm) {
		this.modelNm = modelNm;
	}
	public List<GiftPrmtMgmtSubVO> getModelList() {
		return modelList;
	}
	public void setModelList(List<GiftPrmtMgmtSubVO> modelList) {
		this.modelList = modelList;
	}
	public String getSearchBaseDate() {
		return searchBaseDate;
	}
	public void setSearchBaseDate(String searchBaseDate) {
		this.searchBaseDate = searchBaseDate;
	}	
	public String getSearchBaseEndDate() {
		return searchBaseEndDate;
	}
	public void setSearchBaseEndDate(String searchBaseEndDate) {
		this.searchBaseEndDate = searchBaseEndDate;
	}
	public String getPrmtId() {
		return prmtId;
	}
	public void setPrmtId(String prmtId) {
		this.prmtId = prmtId;
	}
	public String getPrmtNm() {
		return prmtNm;
	}
	public void setPrmtNm(String prmtNm) {
		this.prmtNm = prmtNm;
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
	public String getNacYn() {
		return nacYn;
	}
	public void setNacYn(String nacYn) {
		this.nacYn = nacYn;
	}
	public String getMnpYn() {
		return mnpYn;
	}
	public void setMnpYn(String mnpYn) {
		this.mnpYn = mnpYn;
	}
	public String getEnggCnt0() {
		return enggCnt0;
	}
	public void setEnggCnt0(String enggCnt0) {
		this.enggCnt0 = enggCnt0;
	}
	public String getEnggCnt12() {
		return enggCnt12;
	}
	public void setEnggCnt12(String enggCnt12) {
		this.enggCnt12 = enggCnt12;
	}
	public String getEnggCnt18() {
		return enggCnt18;
	}
	public void setEnggCnt18(String enggCnt18) {
		this.enggCnt18 = enggCnt18;
	}
	public String getEnggCnt24() {
		return enggCnt24;
	}
	public void setEnggCnt24(String enggCnt24) {
		this.enggCnt24 = enggCnt24;
	}
	public String getEnggCnt30() {
		return enggCnt30;
	}
	public void setEnggCnt30(String enggCnt30) {
		this.enggCnt30 = enggCnt30;
	}
	public String getEnggCnt36() {
		return enggCnt36;
	}
	public void setEnggCnt36(String enggCnt36) {
		this.enggCnt36 = enggCnt36;
	}
	public String getUsgYn() {
		return usgYn;
	}
	public void setUsgYn(String usgYn) {
		this.usgYn = usgYn;
	}
	public String getRegstId() {
		return regstId;
	}
	public void setRegstId(String regstId) {
		this.regstId = regstId;
	}
	public String getRegstDttm() {
		return regstDttm;
	}
	public void setRegstDttm(String regstDttm) {
		this.regstDttm = regstDttm;
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
	public String getRegstNm() {
		return regstNm;
	}
	public void setRegstNm(String regstNm) {
		this.regstNm = regstNm;
	}
	public String getRvisnNm() {
		return rvisnNm;
	}
	public void setRvisnNm(String rvisnNm) {
		this.rvisnNm = rvisnNm;
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
	public String getSoc() {
		return soc;
	}
	public void setSoc(String soc) {
		this.soc = soc;
	}
	public String getSocNm() {
		return socNm;
	}
	public void setSocNm(String socNm) {
		this.socNm = socNm;
	}
	public String getSocAmt() {
		return socAmt;
	}
	public void setSocAmt(String socAmt) {
		this.socAmt = socAmt;
	}
	public String getTrgtPrmtId() {
		return trgtPrmtId;
	}
	public void setTrgtPrmtId(String trgtPrmtId) {
		this.trgtPrmtId = trgtPrmtId;
	}
	public String getReqBuyType() {
		return reqBuyType;
	}
	public void setReqBuyType(String reqBuyType) {
		this.reqBuyType = reqBuyType;
	}
	public String getReqBuyTypeNm() {
		return reqBuyTypeNm;
	}
	public void setReqBuyTypeNm(String reqBuyTypeNm) {
		this.reqBuyTypeNm = reqBuyTypeNm;
	}
	public String getChngTypeCd() {
		return chngTypeCd;
	}
	public void setChngTypeCd(String chngTypeCd) {
		this.chngTypeCd = chngTypeCd;
	}
	public String getOrgnType() {
		return orgnType;
	}
	public void setOrgnType(String orgnType) {
		this.orgnType = orgnType;
	}
	
	public int getTOTAL_COUNT() {
		return totalCount;
	}
	public void setTOTAL_COUNT(int totalCount) {
		this.totalCount = totalCount;
	}
	public String getROW_NUM() {
		return this.rowNum;
	}
	public void setROW_NUM(String rowNum) {
		this.rowNum = rowNum;
	}
	public String getLINENUM() {
		return this.linenum;
	}
	public void setLINENUM(String linenum) {
		this.linenum = linenum;
	}
	
	public List<GiftPrmtMgmtSubVO> getOrgnList() {
		return orgnList;
	}
	public void setOrgnList(List<GiftPrmtMgmtSubVO> orgnList) {
		this.orgnList = orgnList;
	}
	public List<GiftPrmtMgmtSubVO> getRateList() {
		return rateList;
	}
	public void setRateList(List<GiftPrmtMgmtSubVO> rateList) {
		this.rateList = rateList;
	}
	
	public List<GiftPrmtMgmtSubVO> getGiftPrdtList() {
		return giftPrdtList;
	}
	public void setGiftPrdtList(List<GiftPrmtMgmtSubVO> giftPrdtList) {
		this.giftPrdtList = giftPrdtList;
	}
	public String getOnOffType() {
		return onOffType;
	}
	public void setOnOffType(String onOffType) {
		this.onOffType = onOffType;
	}
	public String getOnOffTypeNm() {
		return onOffTypeNm;
	}
	public void setOnOffTypeNm(String onOffTypeNm) {
		this.onOffTypeNm = onOffTypeNm;
	}
	public List<GiftPrmtMgmtSubVO> getOnOffTypeList() {
		return onOffTypeList;
	}
	public void setOnOffTypeList(List<GiftPrmtMgmtSubVO> onOffTypeList) {
		this.onOffTypeList = onOffTypeList;
	}
	public String getOrgnTypeNm() {
		return orgnTypeNm;
	}
	public void setOrgnTypeNm(String orgnTypeNm) {
		this.orgnTypeNm = orgnTypeNm;
	}
	public String getPrdtId() {
		return prdtId;
	}
	public void setPrdtId(String prdtId) {
		this.prdtId = prdtId;
	}
	
	public String getAmountLimit() {
		return amountLimit;
	}
	public void setAmountLimit(String amountLimit) {
		this.amountLimit = amountLimit;
	}
	public String getPrmtText() {
		return prmtText;
	}
	public void setPrmtText(String prmtText) {
		this.prmtText = prmtText;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getPrmtType() {
		return prmtType;
	}
	public void setPrmtType(String prmtType) {
		this.prmtType = prmtType;
	}
	public Integer getChoiceLimit() {
		return choiceLimit;
	}
	public void setChoiceLimit(Integer choiceLimit) {
		this.choiceLimit = choiceLimit;
	}
	public String getPrmtTypeNm() {
		return prmtTypeNm;
	}
	public void setPrmtTypeNm(String prmtTypeNm) {
		this.prmtTypeNm = prmtTypeNm;
	}
	public String getShowYn() {
		return showYn;
	}
	public void setShowYn(String showYn) {
		this.showYn = showYn;
	}
	
	public String getShowYnNm() {
		return showYnNm;
	}
	public void setShowYnNm(String showYnNm) {
		this.showYnNm = showYnNm;
	}
	public String getBillDt() {
		return billDt;
	}
	public void setBillDt(String billDt) {
		this.billDt = billDt;
	}
	public String getPrmtOrgnId() {
		return prmtOrgnId;
	}
	public void setPrmtOrgnId(String prmtOrgnId) {
		this.prmtOrgnId = prmtOrgnId;
	}
	public String getPrmtOrgnNm() {
		return prmtOrgnNm;
	}
	public void setPrmtOrgnNm(String prmtOrgnNm) {
		this.prmtOrgnNm = prmtOrgnNm;
	}
	public String getWirelessYn() {
		return wirelessYn;
	}
	public void setWirelessYn(String wirelessYn) {
		this.wirelessYn = wirelessYn;
	}
	public String getWirelessYnNm() {
		return wirelessYnNm;
	}
	public void setWirelessYnNm(String wirelessYnNm) {
		this.wirelessYnNm = wirelessYnNm;
	}
	
	
}
