package com.ktis.msp.rsk.statMgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class SaleSttcMgmtAddVO extends BaseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1729715519173418617L;
	// 부가상품가입자조회조건
	private String strtDt;
	private String endDt;
	private String searchGbn;
	private String searchName;
	private String allDt;
	
	// 부가상품가입자조회
	private String contractNum;
	private String addProd;
	private String addProdNm;
	private String statCd;
	private String statNm;
	private String openDt;
	private String fstRateCd;
	private String fstRateNm;
	private String lstRateCd;
	private String lstRateNm;
	private String dcType;
	private String dcTypeNm;
	private String salePlcyCd;
	private String salePlcyNm;
	private String fstModelId;
	private String fstModelNm;
	private String openAgntCd;
	private String openAgntNm;
	private Integer baseAmt;
	
	// 실적통계조회조건
	private String stdrDt;
	private String chnlTp;
	private String orgnId;
	
	// 실적통계조회
	private Integer keepCnt;
	private Integer mmOpenCnt;
	private Integer mmCanCnt;
	private Integer mmNetCnt;
	private Integer openCnt;
	private Integer canCnt;
	private Integer netCnt;
	
	/* 페이징 처리용 */
	private int TOTAL_COUNT;
	private String ROW_NUM;
	private String LINENUM;
	
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
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getAddProd() {
		return addProd;
	}
	public void setAddProd(String addProd) {
		this.addProd = addProd;
	}
	public String getAddProdNm() {
		return addProdNm;
	}
	public void setAddProdNm(String addProdNm) {
		this.addProdNm = addProdNm;
	}
	public String getStatCd() {
		return statCd;
	}
	public void setStatCd(String statCd) {
		this.statCd = statCd;
	}
	public String getStatNm() {
		return statNm;
	}
	public void setStatNm(String statNm) {
		this.statNm = statNm;
	}
	public String getOpenDt() {
		return openDt;
	}
	public void setOpenDt(String openDt) {
		this.openDt = openDt;
	}
	public String getFstRateCd() {
		return fstRateCd;
	}
	public void setFstRateCd(String fstRateCd) {
		this.fstRateCd = fstRateCd;
	}
	public String getFstRateNm() {
		return fstRateNm;
	}
	public void setFstRateNm(String fstRateNm) {
		this.fstRateNm = fstRateNm;
	}
	public String getLstRateCd() {
		return lstRateCd;
	}
	public void setLstRateCd(String lstRateCd) {
		this.lstRateCd = lstRateCd;
	}
	public String getLstRateNm() {
		return lstRateNm;
	}
	public void setLstRateNm(String lstRateNm) {
		this.lstRateNm = lstRateNm;
	}
	public String getDcType() {
		return dcType;
	}
	public void setDcType(String dcType) {
		this.dcType = dcType;
	}
	public String getDcTypeNm() {
		return dcTypeNm;
	}
	public void setDcTypeNm(String dcTypeNm) {
		this.dcTypeNm = dcTypeNm;
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
	public String getFstModelId() {
		return fstModelId;
	}
	public void setFstModelId(String fstModelId) {
		this.fstModelId = fstModelId;
	}
	public String getFstModelNm() {
		return fstModelNm;
	}
	public void setFstModelNm(String fstModelNm) {
		this.fstModelNm = fstModelNm;
	}
	public String getOpenAgntCd() {
		return openAgntCd;
	}
	public void setOpenAgntCd(String openAgntCd) {
		this.openAgntCd = openAgntCd;
	}
	public String getOpenAgntNm() {
		return openAgntNm;
	}
	public void setOpenAgntNm(String openAgntNm) {
		this.openAgntNm = openAgntNm;
	}
	public Integer getBaseAmt() {
		return baseAmt;
	}
	public void setBaseAmt(Integer baseAmt) {
		this.baseAmt = baseAmt;
	}
	public String getStdrDt() {
		return stdrDt;
	}
	public void setStdrDt(String stdrDt) {
		this.stdrDt = stdrDt;
	}
	public String getChnlTp() {
		return chnlTp;
	}
	public void setChnlTp(String chnlTp) {
		this.chnlTp = chnlTp;
	}
	public String getOrgnId() {
		return orgnId;
	}
	public void setOrgnId(String orgnId) {
		this.orgnId = orgnId;
	}
	public Integer getKeepCnt() {
		return keepCnt;
	}
	public void setKeepCnt(Integer keepCnt) {
		this.keepCnt = keepCnt;
	}
	public Integer getMmOpenCnt() {
		return mmOpenCnt;
	}
	public void setMmOpenCnt(Integer mmOpenCnt) {
		this.mmOpenCnt = mmOpenCnt;
	}
	public Integer getMmCanCnt() {
		return mmCanCnt;
	}
	public void setMmCanCnt(Integer mmCanCnt) {
		this.mmCanCnt = mmCanCnt;
	}
	public Integer getMmNetCnt() {
		return mmNetCnt;
	}
	public void setMmNetCnt(Integer mmNetCnt) {
		this.mmNetCnt = mmNetCnt;
	}
	public Integer getOpenCnt() {
		return openCnt;
	}
	public void setOpenCnt(Integer openCnt) {
		this.openCnt = openCnt;
	}
	public Integer getCanCnt() {
		return canCnt;
	}
	public void setCanCnt(Integer canCnt) {
		this.canCnt = canCnt;
	}
	public Integer getNetCnt() {
		return netCnt;
	}
	public void setNetCnt(Integer netCnt) {
		this.netCnt = netCnt;
	}
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
	public String getAllDt() {
		return allDt;
	}
	public void setAllDt(String allDt) {
		this.allDt = allDt;
	}
	
}
