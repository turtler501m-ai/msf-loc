package com.ktis.msp.org.prmtmgmt.vo;

import java.util.List;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.mvc.BaseVo;

public class ChrgPrmtMgmtVO extends BaseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3234747999341497150L;
	
	private String searchBaseDate;
	private String prmtId;
	private String prmtNm;
	private String strtDt;
	private String endDt;
	private String nacYn;
	private String mnpYn;
	private String enggSrl;				//약정기간시리얼(ex.'243036'일 경우 파싱하여 enggCnt24, enggCnt30, enggCnt36에 각각 'Y' 값 넣어줌)
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
	
	List<ChrgPrmtMgmtSubVO> orgnList;
	List<ChrgPrmtMgmtSubVO> rateList;
	List<ChrgPrmtMgmtSubVO> vasList;
	
	// 모집경로 추가
	private String onOffType;
	private String onOffTypeNm;
	List<ChrgPrmtMgmtSubVO> onOffTypeList;
	
	/* 엑셀등록 */
	private String regNum;				//엑셀등록번호
	private String fileName;			//엑셀파일명
	private List<ChrgPrmtMgmtVO> items;	//엑셀 rows
	private List<String> orgnListExl;		//대상 조직ID
    private List<String> socListExl;		//대상 요금제코드
    private List<String> onOffListExl;		//대상 모집경로
    private List<String> addListExl;		//대상 부가서비스코드
    private String orgnVal;
    private String orgnData;
    
    private String pRateVal;
    private String pRateData;

    private String rRateVal;
    private String rRateData;
    
    /* 다중 선택(체크박스) */
    private List<String> prmtIdList;	//프로모션ID list
    private List<String> orgnIdList;	//조직ID list
    private String endDttmMod;			//변경하려는 종료일
    private String typeCd;				//변경 유형 코드
    
    private String strtDttm;			//시작일시
    private String endDttm;				//종료일시
    
    private String rnum;
    
    /* enggSrl -> enggCnt 파싱 */
    public void parseEngg() {
		int enggLength = 2;
		
		if(KtisUtil.isEmpty(enggSrl) || ((enggSrl.length() % enggLength) != 0)) {
			return;
		}
		String[] enggArray = enggSrl.split("(?<=\\G.{" + enggLength + "})"); //enggLenth = 2 자리씩 split
			
		for(String engg : enggArray) {
			
			if("00".equals(engg))
				enggCnt0 = "Y";
			
			if("12".equals(engg))
				enggCnt12 = "Y";
			
			if("18".equals(engg))
				enggCnt18 = "Y";
			
			if("24".equals(engg))
				enggCnt24 = "Y";
			
			if("30".equals(engg))
				enggCnt30 = "Y";
			
			if("36".equals(engg)) 
				enggCnt36 = "Y";
			
		}
		// Y가 아니면		
		if(!"Y".equals(enggCnt0)) 
			enggCnt0 = "N";
		
		if(!"Y".equals(enggCnt12)) 
			enggCnt12 = "N";
		
		if(!"Y".equals(enggCnt18)) 
			enggCnt18 = "N";
		
		if(!"Y".equals(enggCnt24)) 
			enggCnt24 = "N";
		
		if(!"Y".equals(enggCnt30)) 
			enggCnt30 = "N";
		
		if(!"Y".equals(enggCnt36)) 
			enggCnt36 = "N";		
		
    }
    /* enggCnt -> enggSrl 변환  checkBox 선택값 */    
    public void serialEngg() {
    	StringBuilder sb = new StringBuilder();
    	
    	if("00".equals(enggCnt0)) 
    		sb.append("00");
    	
    	if("12".equals(enggCnt12)) 
    		sb.append("12");
    	
    	if("18".equals(enggCnt18)) 
    		sb.append("18");
    	
    	if("24".equals(enggCnt24)) 
    		sb.append("24");
    	
    	if("30".equals(enggCnt30)) 
    		sb.append("30");
    	
    	if("36".equals(enggCnt36)) 
    		sb.append("36");
    	
    	enggSrl = sb.toString();
    }
   
    /* enggCnt excel 입력값 "Y or N" -> "00 or 12 ...." 숫자로 변환   */
    public void changeEngg() {
    	
    	if("Y".equals(enggCnt0) || "1".equals(enggCnt0)) {
    		enggCnt0 = "00";
    	}
    	if("Y".equals(enggCnt12) || "1".equals(enggCnt12)) {
    		enggCnt12 = "12";
    	}
    	if("Y".equals(enggCnt18) || "1".equals(enggCnt18)) {
    		enggCnt18 = "18";
    	}
    	if("Y".equals(enggCnt24) || "1".equals(enggCnt24)) {
    		enggCnt24 = "24";
    	}
    	if("Y".equals(enggCnt30) || "1".equals(enggCnt30)) {
    		enggCnt30 = "30";
    	}
    	if("Y".equals(enggCnt36) || "1".equals(enggCnt36)) {
    		enggCnt36 = "36";
    	}
    }

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
	public List<ChrgPrmtMgmtSubVO> getOrgnList() {
		return orgnList;
	}
	public void setOrgnList(List<ChrgPrmtMgmtSubVO> orgnList) {
		this.orgnList = orgnList;
	}
	public List<ChrgPrmtMgmtSubVO> getRateList() {
		return rateList;
	}
	public void setRateList(List<ChrgPrmtMgmtSubVO> rateList) {
		this.rateList = rateList;
	}
	public List<ChrgPrmtMgmtSubVO> getVasList() {
		return vasList;
	}
	public void setVasList(List<ChrgPrmtMgmtSubVO> vasList) {
		this.vasList = vasList;
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
	public List<ChrgPrmtMgmtSubVO> getOnOffTypeList() {
		return onOffTypeList;
	}
	public void setOnOffTypeList(List<ChrgPrmtMgmtSubVO> onOffTypeList) {
		this.onOffTypeList = onOffTypeList;
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
	public String getOrgnVal() {
		return orgnVal;
	}
	public void setOrgnVal(String orgnVal) {
		this.orgnVal = orgnVal;
	}
	public String getOrgnData() {
		return orgnData;
	}
	public void setOrgnData(String orgnData) {
		this.orgnData = orgnData;
	}
	public String getpRateVal() {
		return pRateVal;
	}
	public void setpRateVal(String pRateVal) {
		this.pRateVal = pRateVal;
	}
	public String getpRateData() {
		return pRateData;
	}
	public void setpRateData(String pRateData) {
		this.pRateData = pRateData;
	}
	public String getrRateVal() {
		return rRateVal;
	}
	public void setrRateVal(String rRateVal) {
		this.rRateVal = rRateVal;
	}
	public String getrRateData() {
		return rRateData;
	}
	public void setrRateData(String rRateData) {
		this.rRateData = rRateData;
	}
	public List<String> getPrmtIdList() {
		return prmtIdList;
	}
	public void setPrmtIdList(List<String> prmtIdList) {
		this.prmtIdList = prmtIdList;
	}
	public String getEndDttmMod() {
		return endDttmMod;
	}
	public void setEndDttmMod(String endDttmMod) {
		this.endDttmMod = endDttmMod;
	}
	public String getTypeCd() {
		return typeCd;
	}
	public void setTypeCd(String typeCd) {
		this.typeCd = typeCd;
	}
	public String getEnggSrl() {
		return enggSrl;
	}
	public void setEnggSrl(String enggSrl) {
		this.enggSrl = enggSrl;
	}
	public String getStrtDttm() {
		return strtDttm;
	}
	public void setStrtDttm(String strtDttm) {
		this.strtDttm = strtDttm;
	}
	public String getEndDttm() {
		return endDttm;
	}
	public void setEndDttm(String endDttm) {
		this.endDttm = endDttm;
	}
	public String getRnum() {
		return rnum;
	}
	public void setRnum(String rnum) {
		this.rnum = rnum;
	}
	public List<String> getOrgnIdList() {
		return orgnIdList;
	}
	public void setOrgnIdList(List<String> orgnIdList) {
		this.orgnIdList = orgnIdList;
	}
	
}
