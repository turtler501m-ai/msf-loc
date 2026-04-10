package com.ktis.msp.sale.ktPlcyMgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

public class KtPlcyMgmtVO extends BaseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1674651673212744809L;
	
	private String ktPlcyNum;	//순번
	private String trtmTypeCd;	//처리유형코드(I:등록,U:변경)
	private String plcyId;		//정책ID(YYYYMMDD+00)
	private String slsNo;		//판매번호
	private String slsNm;		//판매정책명
	private String plcyType;	//정책유형
	private String plcyTypeNm;	//정책유형
	private String pppo;		//선후불구분
	private String pppoNm;		//선후불구분
	private String useYn;		//사용여부
	private String useYnNm;		//사용여부
	private String dcType;		//할인유형
	private String dcTypeNm;	//할인유형
	private String operType;	//개통유형
	private String operTypeNm;	//개통유형
	private String instCnt;		//할부개월수
	private String instCntNm;	//할부개월수
	private String enggCnt;		//약정개월수
	private String enggCntNm;	//약정개월수
	private String regstId;		//등록자
	private String regstDttm;	//등록일시
	
	/* 페이징 처리용 */
	public int TOTAL_COUNT;
	public String ROW_NUM;
	public String LINENUM;
	
	/* Grid 처리용 */
	private String chk;
	private String cdVal;
	private String cdNm;
	
	public String getKtPlcyNum() {
		return ktPlcyNum;
	}
	public void setKtPlcyNum(String ktPlcyNum) {
		this.ktPlcyNum = ktPlcyNum;
	}
	public String getTrtmTypeCd() {
		return trtmTypeCd;
	}
	public void setTrtmTypeCd(String trtmTypeCd) {
		this.trtmTypeCd = trtmTypeCd;
	}
	public String getPlcyId() {
		return plcyId;
	}
	public void setPlcyId(String plcyId) {
		this.plcyId = plcyId;
	}
	public String getSlsNo() {
		return slsNo;
	}
	public void setSlsNo(String slsNo) {
		this.slsNo = slsNo;
	}
	public String getSlsNm() {
		return slsNm;
	}
	public void setSlsNm(String slsNm) {
		this.slsNm = slsNm;
	}
	public String getPlcyType() {
		return plcyType;
	}
	public void setPlcyType(String plcyType) {
		this.plcyType = plcyType;
	}
	public String getPlcyTypeNm() {
		return plcyTypeNm;
	}
	public void setPlcyTypeNm(String plcyTypeNm) {
		this.plcyTypeNm = plcyTypeNm;
	}
	public String getPppo() {
		return pppo;
	}
	public void setPppo(String pppo) {
		this.pppo = pppo;
	}
	public String getPppoNm() {
		return pppoNm;
	}
	public void setPppoNm(String pppoNm) {
		this.pppoNm = pppoNm;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getUseYnNm() {
		return useYnNm;
	}
	public void setUseYnNm(String useYnNm) {
		this.useYnNm = useYnNm;
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
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	public String getOperTypeNm() {
		return operTypeNm;
	}
	public void setOperTypeNm(String operTypeNm) {
		this.operTypeNm = operTypeNm;
	}
	public String getInstCnt() {
		return instCnt;
	}
	public void setInstCnt(String instCnt) {
		this.instCnt = instCnt;
	}
	public String getInstCntNm() {
		return instCntNm;
	}
	public void setInstCntNm(String instCntNm) {
		this.instCntNm = instCntNm;
	}
	public String getEnggCnt() {
		return enggCnt;
	}
	public void setEnggCnt(String enggCnt) {
		this.enggCnt = enggCnt;
	}
	public String getEnggCntNm() {
		return enggCntNm;
	}
	public void setEnggCntNm(String enggCntNm) {
		this.enggCntNm = enggCntNm;
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
	public String getChk() {
		return chk;
	}
	public void setChk(String chk) {
		this.chk = chk;
	}
	public String getCdVal() {
		return cdVal;
	}
	public void setCdVal(String cdVal) {
		this.cdVal = cdVal;
	}
	public String getCdNm() {
		return cdNm;
	}
	public void setCdNm(String cdNm) {
		this.cdNm = cdNm;
	}
	
}
