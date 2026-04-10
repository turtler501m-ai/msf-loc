package com.ktis.msp.org.prmtmgmt.vo;

import java.io.Serializable;
import java.util.List;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.mvc.BaseVo;

public class DisPrmtMgmtVO extends BaseVo implements Serializable {

    private static final long serialVersionUID = -6510970033252597452L;

    private String prmtId;				//프로모션ID
    private String prmtNm;				//프로모션명
    private String strtDttm;			//시작일시
    private String strtDt;				//시작일
    private String strtTm;				//시작시
    
    private String endDttm;				//종료일시
    private String endDt;				//시작일
    private String endTm;				//시작시

    private String chnlTp;				//채널유형코드(CMN0090)
    private String chnlNm;				//채널유형명
    private String evntCd;				//업무구분코드(CMN0091)
    private String evntNm;				//업무구분명
    private String slsTp;				//가입유형코드(CMN0093)
    private String slsNm;				//가입유형명
    private String intmYn;				//특정단말여부
    private String modelId;				//단말모델ID
    private String modelNm;				//단말모델명
    private String prdtNm;				//단말제품명
    private String enggSrl;				//약정기간시리얼(ex.'243036'일 경우 파싱하여 enggCnt24, enggCnt30, enggCnt36에 각각 'Y' 값 넣어줌)
    private String enggCnt0;			//무약정('1' or '0')
    private String enggCnt12;			//약정 12개월('1' or '0')
    private String enggCnt18;			//약정 18개월('1' or '0')
    private String enggCnt24;			//약정 24개월('1' or '0')
    private String enggCnt30;			//약정 30개월('1' or '0')
    private String enggCnt36;			//약정 36개월('1' or '0')
    private String usgYn;				//사용여부
    private String regstId;				//등록자ID
    private String regstNm;				//등록자명
    private String regstDttm;			//등록일시
    private String rvisnId;				//수정자ID
    private String rvisnNm;				//수정자명
    private String rvisnDttm;			//수정일시
    
    /* 대상 조직 */
    private String orgnId;				//조직ID
	private String orgnNm;				//조직명
	
	/* 대상 요금제 */
	private String rateCd;				//요금제코드
	private String rateNm;				//요금제명
	
	 /* 대상 부가서비스 */
	private String soc;					//부가서비스코드
	private String addNm;				//부가서비스명
	private String disAmt;				//기본료(할인금액)
    
    /* 조회 검색용 옵션 */
    private String searchBaseDate;		//기준일
    
    /* 자료 생성 */
	private String searchPrmtNm;		//프로모션명
    private String searchChnlTp;		//채널유형
    private String searchEvntCd;		//업무구분
    private String searchSlsTp;			//가입유형
    
	/* 엑셀등록 */
	private String regNum;				//엑셀등록번호
	private String fileName;			//엑셀파일명
	private List<DisPrmtMgmtVO> items;	//엑셀 rows
    private List<String> orgnList;		//대상 조직ID
    private List<String> socList;		//대상 요금제코드
    private List<String> addList;		//대상 부가서비스코드
    
	/* 페이징 처리용 */
	private int TOTAL_COUNT;
	private String ROW_NUM;
	private String LINENUM;

    
    /* 대상 조직, 요금제, 부가서비스 List */
    List<DisPrmtMgmtSubVO> orgnSubList; 	//대상 조직ID
    List<DisPrmtMgmtSubVO> socSubList; 		//대상 요금제코드
    List<DisPrmtMgmtSubVO> addSubList;  	//대상 부가서비스코드
    
    /* 다중 선택(체크박스) */
    private List<String> prmtIdList;	//프로모션ID list
    
    /* update 관련 */
    private String typeCd;				//변경 유형 코드
    private String endDttmMod;			//변경하려는 종료일
    
    /* 신청등록 관련 */
    private String operType;			//신청 유형
    private String reqBuyType;			//구매 유형
    
    /* 엑셀다운로드 관련 */
    private String rnum;
    
    /* 검색 관련 */
    private String orgnVal;
    private String orgnData;
    private String pRateVal;
    private String pRateData;
    private String rRateVal;
    private String rRateData;
    
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

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public String getChnlTp() {
		return chnlTp;
	}

	public void setChnlTp(String chnlTp) {
		this.chnlTp = chnlTp;
	}

	public String getChnlNm() {
		return chnlNm;
	}

	public void setChnlNm(String chnlNm) {
		this.chnlNm = chnlNm;
	}

	public String getEvntCd() {
		return evntCd;
	}

	public void setEvntCd(String evntCd) {
		this.evntCd = evntCd;
	}

	public String getEvntNm() {
		return evntNm;
	}

	public void setEvntNm(String evntNm) {
		this.evntNm = evntNm;
	}

	public String getSlsTp() {
		return slsTp;
	}

	public void setSlsTp(String slsTp) {
		this.slsTp = slsTp;
	}

	public String getSlsNm() {
		return slsNm;
	}

	public void setSlsNm(String slsNm) {
		this.slsNm = slsNm;
	}

	public String getIntmYn() {
		return intmYn;
	}

	public void setIntmYn(String intmYn) {
		this.intmYn = intmYn;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getModelNm() {
		return modelNm;
	}

	public void setModelNm(String modelNm) {
		this.modelNm = modelNm;
	}

	public String getPrdtNm() {
		return prdtNm;
	}

	public void setPrdtNm(String prdtNm) {
		this.prdtNm = prdtNm;
	}

	public String getEnggSrl() {
		return enggSrl;
	}

	public void setEnggSrl(String enggSrl) {
		this.enggSrl = enggSrl;
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

	public String getRegstNm() {
		return regstNm;
	}

	public void setRegstNm(String regstNm) {
		this.regstNm = regstNm;
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

	public String getRvisnNm() {
		return rvisnNm;
	}

	public void setRvisnNm(String rvisnNm) {
		this.rvisnNm = rvisnNm;
	}

	public String getRvisnDttm() {
		return rvisnDttm;
	}

	public void setRvisnDttm(String rvisnDttm) {
		this.rvisnDttm = rvisnDttm;
	}

	public String getSearchBaseDate() {
		return searchBaseDate;
	}

	public void setSearchBaseDate(String searchBaseDate) {
		this.searchBaseDate = searchBaseDate;
	}

	public String getSearchChnlTp() {
		return searchChnlTp;
	}

	public void setSearchChnlTp(String searchChnlTp) {
		this.searchChnlTp = searchChnlTp;
	}

	public String getSearchEvntCd() {
		return searchEvntCd;
	}

	public void setSearchEvntCd(String searchEvntCd) {
		this.searchEvntCd = searchEvntCd;
	}

	public String getSearchSlsTp() {
		return searchSlsTp;
	}

	public void setSearchSlsTp(String searchSlsTp) {
		this.searchSlsTp = searchSlsTp;
	}

	public List<String> getOrgnList() {
		return orgnList;
	}

	public void setOrgnList(List<String> orgnList) {
		this.orgnList = orgnList;
	}

	public List<String> getSocList() {
		return socList;
	}

	public void setSocList(List<String> socList) {
		this.socList = socList;
	}

	public List<String> getAddList() {
		return addList;
	}

	public void setAddList(List<String> addList) {
		this.addList = addList;
	}

	public List<String> getPrmtIdList() {
		return prmtIdList;
	}

	public void setPrmtIdList(List<String> prmtIdList) {
		this.prmtIdList = prmtIdList;
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

	public List<DisPrmtMgmtSubVO> getOrgnSubList() {
		return orgnSubList;
	}

	public void setOrgnSubList(List<DisPrmtMgmtSubVO> orgnSubList) {
		this.orgnSubList = orgnSubList;
	}

	public List<DisPrmtMgmtSubVO> getSocSubList() {
		return socSubList;
	}

	public void setSocSubList(List<DisPrmtMgmtSubVO> socSubList) {
		this.socSubList = socSubList;
	}

	public List<DisPrmtMgmtSubVO> getAddSubList() {
		return addSubList;
	}

	public void setAddSubList(List<DisPrmtMgmtSubVO> addSubList) {
		this.addSubList = addSubList;
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

	public List<DisPrmtMgmtVO> getItems() {
		return items;
	}

	public void setItems(List<DisPrmtMgmtVO> items) {
		this.items = items;
	}

	public String getSearchPrmtNm() {
		return searchPrmtNm;
	}

	public void setSearchPrmtNm(String searchPrmtNm) {
		this.searchPrmtNm = searchPrmtNm;
	}

	public String getTypeCd() {
		return typeCd;
	}

	public void setTypeCd(String typeCd) {
		this.typeCd = typeCd;
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

	public String getStrtDt() {
		return strtDt;
	}

	public void setStrtDt(String strtDt) {
		this.strtDt = strtDt;
	}

	public String getStrtTm() {
		return strtTm;
	}

	public void setStrtTm(String strtTm) {
		this.strtTm = strtTm;
	}

	public String getEndDt() {
		return endDt;
	}

	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}

	public String getEndTm() {
		return endTm;
	}

	public void setEndTm(String endTm) {
		this.endTm = endTm;
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

	public String getAddNm() {
		return addNm;
	}

	public void setAddNm(String addNm) {
		this.addNm = addNm;
	}

	public String getDisAmt() {
		return disAmt;
	}

	public void setDisAmt(String disAmt) {
		this.disAmt = disAmt;
	}


	public String getEndDttmMod() {
		return endDttmMod;
	}
	public void setEndDttmMod(String endDttmMod) {
		this.endDttmMod = endDttmMod;
	}
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	public String getReqBuyType() {
		return reqBuyType;
	}
	public void setReqBuyType(String reqBuyType) {
		this.reqBuyType = reqBuyType;
	}
	public String getRnum() {
		return rnum;
	}
	public void setRnum(String rnum) {
		this.rnum = rnum;
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
	
}