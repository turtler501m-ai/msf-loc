package com.ktis.msp.org.prmtmgmt.vo;

import java.util.List;

import com.ktis.msp.base.mvc.BaseVo;
import com.ktis.msp.gift.prmtmgmt.vo.GiftPrmtMgmtSubVO;

public class TriplePrmtMgmtVO extends BaseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9045258754875949571L;
	
	private String prmtId;
	private String rowCheck;
	private String searchBaseDate;
	private String usgYn;
	private String regstId;
	private String regstDttm;
	private String rvisnId;
	private String rvisnDttm;
	private String regstNm;
	private String rvisnNm;
	private String rateCd;
	private String rateNm;
	private String soc;
	private String socNm;
	private String socAmt;
	private String prmtNm;
	private String vasCd;
	private String vasNm;
	private String dupYn;
	private String baseAmt;
	private String seq;
	
	private String strtDt;
	private String endDt;
	
	/* 페이징 처리용 */
	public int TOTAL_COUNT;
	public String ROW_NUM;
	public String LINENUM;
	
	List<TriplePrmtMgmtVO> rateList;
	List<TriplePrmtMgmtVO> vasList;
	
	private String chngTypeCd;
	
	private String fileName;
	
	List<GiftPrmtMgmtSubVO> items;
	
	private List<String> socList;
	private String socStr;
	
	
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
	public String getSearchBaseDate() {
		return searchBaseDate;
	}
	public void setSearchBaseDate(String searchBaseDate) {
		this.searchBaseDate = searchBaseDate;
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
	public String getPrmtNm() {
		return prmtNm;
	}
	public void setPrmtNm(String prmtNm) {
		this.prmtNm = prmtNm;
	}
	public String getVasCd() {
		return vasCd;
	}
	public void setVasCd(String vasCd) {
		this.vasCd = vasCd;
	}
	public String getVasNm() {
		return vasNm;
	}
	public void setVasNm(String vasNm) {
		this.vasNm = vasNm;
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
	public List<TriplePrmtMgmtVO> getRateList() {
		return rateList;
	}
	public void setRateList(List<TriplePrmtMgmtVO> rateList) {
		this.rateList = rateList;
	}
	public List<TriplePrmtMgmtVO> getVasList() {
		return vasList;
	}
	public void setVasList(List<TriplePrmtMgmtVO> vasList) {
		this.vasList = vasList;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getChngTypeCd() {
		return chngTypeCd;
	}
	public void setChngTypeCd(String chngTypeCd) {
		this.chngTypeCd = chngTypeCd;
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
	
}
