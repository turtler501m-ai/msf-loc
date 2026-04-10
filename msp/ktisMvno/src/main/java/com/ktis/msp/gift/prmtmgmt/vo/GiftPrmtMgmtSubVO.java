package com.ktis.msp.gift.prmtmgmt.vo;

import java.util.List;

import com.ktis.msp.base.mvc.BaseVo;


public class GiftPrmtMgmtSubVO extends BaseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9045258754875949571L;
	
	private String prmtId;
	private String rowCheck;
	private String orgnId;
	private String orgnNm;
	private String typeNm;
	private String orgnType;
	private String rateCd;
	private String rateNm;
	private String dataType;
	private String baseAmt;

	private String prdtId;
	private String prdtNm;
	private String dupYn;
	private String outUnitPric;
	private int     sort;
	
	private String onOffType;
	private String onOffTypeNm;

	private String reqBuyType;
	private String modelId;
	private String modelCode;
	private String modelNm;
	
	
	private String fileName;
	
	List<GiftPrmtMgmtSubVO> items;
	
	
	private List<String> socList;
	private String socStr;
	private String soc;
	
	private List<String> modelCdList;
	private String modelCdStr;
	private String modelCd;
		
	public String getReqBuyType() {
		return reqBuyType;
	}
	public void setReqBuyType(String reqBuyType) {
		this.reqBuyType = reqBuyType;
	}
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
	public String getPrmtId() {
		return prmtId;
	}
	public void setPrmtId(String prmtId) {
		this.prmtId = prmtId;
	}
	public String getRowCheck() {
		return rowCheck;
	}
	public void setRowCheck(String rowCheck) {
		this.rowCheck = rowCheck;
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
	public String getTypeNm() {
		return typeNm;
	}
	public void setTypeNm(String typeNm) {
		this.typeNm = typeNm;
	}
	public String getOrgnType() {
		return orgnType;
	}
	public void setOrgnType(String orgnType) {
		this.orgnType = orgnType;
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
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	public String getPrdtId() {
		return prdtId;
	}
	public void setPrdtId(String prdtId) {
		this.prdtId = prdtId;
	}
	public String getPrdtNm() {
		return prdtNm;
	}
	public void setPrdtNm(String prdtNm) {
		this.prdtNm = prdtNm;
	}
	public String getOutUnitPric() {
		return outUnitPric;
	}
	public void setOutUnitPric(String outUnitPric) {
		this.outUnitPric = outUnitPric;
	}
	public String getDupYn() {
		return dupYn;
	}
	public void setDupYn(String dupYn) {
		this.dupYn = dupYn;
	}
	public String getBaseAmt() {
		return baseAmt;
	}
	public void setBaseAmt(String baseAmt) {
		this.baseAmt = baseAmt;
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
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public List<GiftPrmtMgmtSubVO> getItems() {
		return items;
	}
	public void setItems(List<GiftPrmtMgmtSubVO> items) {
		this.items = items;
	}
	public String getSoc() {
		return soc;
	}
	public void setSoc(String soc) {
		this.soc = soc;
	}
	public List<String> getSocList() {
		return socList;
	}
	public void setSocList(List<String> socList) {
		this.socList = socList;
	}
	public String getSocStr() {
		return socStr;
	}
	public void setSocStr(String socStr) {
		this.socStr = socStr;
	}
	public List<String> getModelCdList() {
		return modelCdList;
	}
	public void setModelCdList(List<String> modelCdList) {
		this.modelCdList = modelCdList;
	}
	public String getModelCdStr() {
		return modelCdStr;
	}
	public void setModelCdStr(String modelCdStr) {
		this.modelCdStr = modelCdStr;
	}
	public String getModelCd() {
		return modelCd;
	}
	public void setModelCd(String modelCd) {
		this.modelCd = modelCd;
	}
	
}
