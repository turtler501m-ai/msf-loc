package com.ktis.msp.batch.job.chrg.chrgmgmt.vo;

import java.util.List;

import com.ktis.msp.base.BaseVo;

public class ChrgPrmtMgmtVO extends BaseVo  {


	private String searchBaseDate;
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
	private String seq;
	private String vasCd;
	private String trgtPrmtId;
	private String reqBuyType;
	private String reqBuyTypeNm;
	private String chngTypeCd;
	private String orgnType;
	private String orgnTypeNm;
	
	/* 페이징 처리용 */
	public int TOTAL_COUNT;
	public String ROW_NUM;
	public String LINENUM;
	
	// 모집경로 추가
	private String onOffType;
	private String onOffTypeNm;
	
	/* 엑셀등록 */
	private String regNum;				//엑셀등록번호
	private String fileName;			//엑셀파일명
	private List<ChrgPrmtMgmtVO> items;	//엑셀 rows
	private List<String> orgnListExl;		//대상 조직ID
    private List<String> socListExl;		//대상 요금제코드
    private List<String> onOffListExl;		//대상 모집경로
    private List<String> addListExl;		//대상 부가서비스코드
    
    /* 자료 생성 */
	private String searchPrmtNm;		//프로모션명
	private String searchOrgnType;		//채널유형
	private String searchReqBuyType;	//구매유형
	private String searchOnOffType;		//모집경로
	
	// 엑셀다운로드 로그
 	private String dwnldRsn    ;	/*다운로드 사유*/
 	private String exclDnldId  ;	
 	private String ipAddr      ;	/*ip정보*/
 	private String menuId      ;	/*메뉴ID*/
	private String userId;				//userId
	
    /* 신청등록 관련 */
    private String totalDiscount;		//총할인액
    
    
    
	

	public String getSearchBaseDate() {
		return searchBaseDate;
	}
	public void setSearchBaseDate(String searchBaseDate) {
		this.searchBaseDate = searchBaseDate;
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
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getVasCd() {
		return vasCd;
	}
	public void setVasCd(String vasCd) {
		this.vasCd = vasCd;
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
	public String getOrgnTypeNm() {
		return orgnTypeNm;
	}
	public void setOrgnTypeNm(String orgnTypeNm) {
		this.orgnTypeNm = orgnTypeNm;
	}
	public String getRegNum() {
		return regNum;
	}
	public void setRegNum(String regNum) {
		this.regNum = regNum;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public List<ChrgPrmtMgmtVO> getItems() {
		return items;
	}
	public void setItems(List<ChrgPrmtMgmtVO> items) {
		this.items = items;
	}


	public List<String> getOrgnListExl() {
		return orgnListExl;
	}


	public void setOrgnListExl(List<String> orgnListExl) {
		this.orgnListExl = orgnListExl;
	}


	public List<String> getSocListExl() {
		return socListExl;
	}


	public void setSocListExl(List<String> socListExl) {
		this.socListExl = socListExl;
	}


	public List<String> getOnOffListExl() {
		return onOffListExl;
	}


	public void setOnOffListExl(List<String> onOffListExl) {
		this.onOffListExl = onOffListExl;
	}


	public List<String> getAddListExl() {
		return addListExl;
	}


	public void setAddListExl(List<String> addListExl) {
		this.addListExl = addListExl;
	}
	public String getTotalDiscount() {
		return totalDiscount;
	}
	public void setTotalDiscount(String totalDiscount) {
		this.totalDiscount = totalDiscount;
	}
	public String getSearchPrmtNm() {
		return searchPrmtNm;
	}
	public void setSearchPrmtNm(String searchPrmtNm) {
		this.searchPrmtNm = searchPrmtNm;
	}
	public String getSearchOrgnType() {
		return searchOrgnType;
	}
	public void setSearchOrgnType(String searchOrgnType) {
		this.searchOrgnType = searchOrgnType;
	}
	public String getSearchReqBuyType() {
		return searchReqBuyType;
	}
	public void setSearchReqBuyType(String searchReqBuyType) {
		this.searchReqBuyType = searchReqBuyType;
	}
	public String getSearchOnOffType() {
		return searchOnOffType;
	}
	public void setSearchOnOffType(String searchOnOffType) {
		this.searchOnOffType = searchOnOffType;
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
	
	
}

   