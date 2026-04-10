package com.ktis.msp.org.prmtmgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

public class DisPrmtMgmtSubVO extends BaseVo implements Serializable {

    private static final long serialVersionUID = -6510970033252597452L;

    private String prmtId;				//프로모션ID
    private String rowCheck;			//체크박스
    
    /* 대상 조직 */
    private String orgnId;				//조직ID
    private String orgnNm;				//조직명
    private String orgnTypeCd3;			//조직유형코드(TYPE_DTL_CD3)
    private String typeNm;				//조직유형명
    private String typeCd;				//조직유형코드
    private String statNm;				//조직상태명
    
    /* 대상 요금제 */
    private String rateCd;				//요금제코드
    private String rateNm;				//요금제명
    private String baseAmt;				//기본료
    
    /* 대상 부가서비스 */
    private String soc;					//부가서비스코드
    private String addNm;				//부가서비스명
    private String disAmt;				//기본료(할인금액)
    
    private String searchType;			// 
    
	public String getPrmtId() {
		return prmtId;
	}
	public void setPrmtId(String prmtId) {
		this.prmtId = prmtId;
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
	public String getOrgnTypeCd3() {
		return orgnTypeCd3;
	}
	public void setOrgnTypeCd3(String orgnTypeCd3) {
		this.orgnTypeCd3 = orgnTypeCd3;
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
	public String getBaseAmt() {
		return baseAmt;
	}
	public void setBaseAmt(String baseAmt) {
		this.baseAmt = baseAmt;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getRowCheck() {
		return rowCheck;
	}
	public void setRowCheck(String rowCheck) {
		this.rowCheck = rowCheck;
	}
	public String getTypeNm() {
		return typeNm;
	}
	public void setTypeNm(String typeNm) {
		this.typeNm = typeNm;
	}
	public String getTypeCd() {
		return typeCd;
	}
	public void setTypeCd(String typeCd) {
		this.typeCd = typeCd;
	}
	public String getStatNm() {
		return statNm;
	}
	public void setStatNm(String statNm) {
		this.statNm = statNm;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
}