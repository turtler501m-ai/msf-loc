package com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.BaseVo;

public class RwdCmpnVO extends BaseVo implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2605638468458193976L;

	
	private String rwdSeq;			//보상서비스 시퀀스
	private String rwdCmpnSeq;		//권리실행 접수번호
	private String cmpnCt;			//권리실행 접수센터
	private String cmpnDt;			//권리실행 접수일자
	private String cmpnId;			//권리실행 접수담당자
	private String cmpnStatCd;		//권리실행 접수 상태코드
	private String ctInDt;			//보상센터 입고일자
	private String ctInId;			//보상센터 입고담당자
	private String ctOutDt;			//보상센터 출고일자
	private String ctOutId;			//보상센터 출고담당자
	private String buyPric;			//단말기구입가
	private String rwdRt;			//적용비율
	private String ftrPric;			//미래가격
	private String ostPric;			//차감금액
	private String asrPric;			//보장금액
	private String ostResn;			//차감사유
	private String cnfmLv;			//판정 등급
	private String cnfmDt;			//등급판정 일자
	private String cnfmId;			//등급판정 담당자
	private String payPlanDttm;		//보상금 지급예정일자
	private String obDt;			//OB 결과 확정 일자
	private String obId;			//OB 결과 확정 상담 담당자
	private String bankNm;			//은행명
	private String acntNo;			//입금계좌번호
	private String dpstNm;			//예금주
	private String retnDt;			//반송 출고일자
	private String retnId;			//반송 출고담당자
	private String retnIv;			//반송 출고 송장번호
	private String payDttm;			//보상금 지급 완료일자
	private String realCmpnAmt;		//실보상금액
	
	public String getRwdSeq() {
		return rwdSeq;
	}
	public void setRwdSeq(String rwdSeq) {
		this.rwdSeq = rwdSeq;
	}
	public String getRwdCmpnSeq() {
		return rwdCmpnSeq;
	}
	public void setRwdCmpnSeq(String rwdCmpnSeq) {
		this.rwdCmpnSeq = rwdCmpnSeq;
	}
	public String getCmpnCt() {
		return cmpnCt;
	}
	public void setCmpnCt(String cmpnCt) {
		this.cmpnCt = cmpnCt;
	}
	public String getCmpnDt() {
		return cmpnDt;
	}
	public void setCmpnDt(String cmpnDt) {
		this.cmpnDt = cmpnDt;
	}
	public String getCmpnId() {
		return cmpnId;
	}
	public void setCmpnId(String cmpnId) {
		this.cmpnId = cmpnId;
	}
	public String getCmpnStatCd() {
		return cmpnStatCd;
	}
	public void setCmpnStatCd(String cmpnStatCd) {
		this.cmpnStatCd = cmpnStatCd;
	}
	public String getCtInDt() {
		return ctInDt;
	}
	public void setCtInDt(String ctInDt) {
		this.ctInDt = ctInDt;
	}
	public String getCtInId() {
		return ctInId;
	}
	public void setCtInId(String ctInId) {
		this.ctInId = ctInId;
	}
	public String getCtOutDt() {
		return ctOutDt;
	}
	public void setCtOutDt(String ctOutDt) {
		this.ctOutDt = ctOutDt;
	}
	public String getCtOutId() {
		return ctOutId;
	}
	public void setCtOutId(String ctOutId) {
		this.ctOutId = ctOutId;
	}
	public String getBuyPric() {
		return buyPric;
	}
	public void setBuyPric(String buyPric) {
		this.buyPric = buyPric;
	}
	public String getRwdRt() {
		return rwdRt;
	}
	public void setRwdRt(String rwdRt) {
		this.rwdRt = rwdRt;
	}
	public String getFtrPric() {
		return ftrPric;
	}
	public void setFtrPric(String ftrPric) {
		this.ftrPric = ftrPric;
	}
	public String getOstPric() {
		return ostPric;
	}
	public void setOstPric(String ostPric) {
		this.ostPric = ostPric;
	}
	public String getAsrPric() {
		return asrPric;
	}
	public void setAsrPric(String asrPric) {
		this.asrPric = asrPric;
	}
	public String getOstResn() {
		return ostResn;
	}
	public void setOstResn(String ostResn) {
		this.ostResn = ostResn;
	}
	public String getCnfmLv() {
		return cnfmLv;
	}
	public void setCnfmLv(String cnfmLv) {
		this.cnfmLv = cnfmLv;
	}
	public String getCnfmDt() {
		return cnfmDt;
	}
	public void setCnfmDt(String cnfmDt) {
		this.cnfmDt = cnfmDt;
	}
	public String getCnfmId() {
		return cnfmId;
	}
	public void setCnfmId(String cnfmId) {
		this.cnfmId = cnfmId;
	}
	public String getPayPlanDttm() {
		return payPlanDttm;
	}
	public void setPayPlanDttm(String payPlanDttm) {
		this.payPlanDttm = payPlanDttm;
	}
	public String getObDt() {
		return obDt;
	}
	public void setObDt(String obDt) {
		this.obDt = obDt;
	}
	public String getObId() {
		return obId;
	}
	public void setObId(String obId) {
		this.obId = obId;
	}
	public String getBankNm() {
		return bankNm;
	}
	public void setBankNm(String bankNm) {
		this.bankNm = bankNm;
	}
	public String getAcntNo() {
		return acntNo;
	}
	public void setAcntNo(String acntNo) {
		this.acntNo = acntNo;
	}
	public String getDpstNm() {
		return dpstNm;
	}
	public void setDpstNm(String dpstNm) {
		this.dpstNm = dpstNm;
	}
	public String getRetnDt() {
		return retnDt;
	}
	public void setRetnDt(String retnDt) {
		this.retnDt = retnDt;
	}
	public String getRetnId() {
		return retnId;
	}
	public void setRetnId(String retnId) {
		this.retnId = retnId;
	}
	public String getRetnIv() {
		return retnIv;
	}
	public void setRetnIv(String retnIv) {
		this.retnIv = retnIv;
	}
	public String getPayDttm() {
		return payDttm;
	}
	public void setPayDttm(String payDttm) {
		this.payDttm = payDttm;
	}
	public String getRealCmpnAmt() {
		return realCmpnAmt;
	}
	public void setRealCmpnAmt(String realCmpnAmt) {
		this.realCmpnAmt = realCmpnAmt;
	}	

	
}
