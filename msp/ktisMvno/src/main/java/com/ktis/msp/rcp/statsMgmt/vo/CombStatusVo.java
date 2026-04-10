package com.ktis.msp.rcp.statsMgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class CombStatusVo extends BaseVo {

	private static final long serialVersionUID = 1L;

	/** 검색영역 */
	private String srchStrtDt;
	private String srchEndDt;
	private String searchGbn;
	private String searchName;

	/** MSP_JUO_SUB_INFO */
	private String subLinkName;			//고객명
	private String customerId;			//고객ID
	private String subStatus;			//계약상태코드
	private String subStatusNm;			//계약상태코드명
	private String subscriberNo;		//휴대폰번호
	private String lstComActvDate; 		//개통일자
	private String combTypeCd;			//결합유형코드
	private String combTypeNm;			//결합유형코드명
	private String combEfctStDt;		//결합유효시작일
	private String combEfctFnsDt;		//결합유효종료일
	private String combSvcContSttusCd;	//결합상태코드
	private String combSvcContSttusNm;	//결합상태코드명
	private String openAgntCd;			//개통대리점
	private String openAgntNm;			//개통대리점명

	/** MSP_COMB_MST */
	private String contractNum;			//계약번호
	private String combInfo;			//결합정보(결합유형코드 결합유효시작일 결합유효종료일 결합상태코드)
	private String apiTarget;			//상태 (S:정상, F=실패, E=에러, Y=연동전, N=연동실패)

	private String globalNo;			//mplateForm 연동 번호
	private String rsltCd; 				//처리 결과 코드
	private String prcsSbst;			//처리 결과 내용

	/** MSP_COMB_DTL */
	private String seq;					//시퀀스
	private String combProdNm;			//결합상품
	private String svcContDivNm;		//KT구분

	private String prodNm;				//상품명
	private String svcNo;				//서비스 번호
	private String combEngtPerdMonsNum; //결합약정기간
	private String combEngtExpirDt;		//결합만료예정일
	private String combEngtStDt;		//결합시작일


	/**결합요금제 조회**/
	private String pRateCd;
	private String pRateNm;
	private String rRateCd;
	private String rRateNm;

	private String prodCd;				//상품코드

	//==========상품리스트 항목==========

	/** 등록자ID */
	private String cretId;

	/** 등록일시 */
	private String cretDt;

	/** 수정자ID */
	private String amdId;

	/** 수정일시 */
	private String amdDt;

	public String getSrchStrtDt() {
		return srchStrtDt;
	}

	public void setSrchStrtDt(String srchStrtDt) {
		this.srchStrtDt = srchStrtDt;
	}

	public String getSrchEndDt() {
		return srchEndDt;
	}

	public void setSrchEndDt(String srchEndDt) {
		this.srchEndDt = srchEndDt;
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

	public String getSubLinkName() {
		return subLinkName;
	}

	public void setSubLinkName(String subLinkName) {
		this.subLinkName = subLinkName;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getSubStatus() {
		return subStatus;
	}

	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}

	public String getSubStatusNm() {
		return subStatusNm;
	}

	public void setSubStatusNm(String subStatusNm) {
		this.subStatusNm = subStatusNm;
	}

	public String getSubscriberNo() {
		return subscriberNo;
	}

	public void setSubscriberNo(String subscriberNo) {
		this.subscriberNo = subscriberNo;
	}

	public String getLstComActvDate() {
		return lstComActvDate;
	}

	public void setLstComActvDate(String lstComActvDate) {
		this.lstComActvDate = lstComActvDate;
	}

	public String getCombTypeCd() {
		return combTypeCd;
	}

	public void setCombTypeCd(String combTypeCd) {
		this.combTypeCd = combTypeCd;
	}

	public String getCombTypeNm() {
		return combTypeNm;
	}

	public void setCombTypeNm(String combTypeNm) {
		this.combTypeNm = combTypeNm;
	}

	public String getCombEfctStDt() {
		return combEfctStDt;
	}

	public void setCombEfctStDt(String combEfctStDt) {
		this.combEfctStDt = combEfctStDt;
	}

	public String getCombEfctFnsDt() {
		return combEfctFnsDt;
	}

	public void setCombEfctFnsDt(String combEfctFnsDt) {
		this.combEfctFnsDt = combEfctFnsDt;
	}

	public String getCombSvcContSttusCd() {
		return combSvcContSttusCd;
	}

	public void setCombSvcContSttusCd(String combSvcContSttusCd) {
		this.combSvcContSttusCd = combSvcContSttusCd;
	}

	public String getCombSvcContSttusNm() {
		return combSvcContSttusNm;
	}

	public void setCombSvcContSttusNm(String combSvcContSttusNm) {
		this.combSvcContSttusNm = combSvcContSttusNm;
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

	public String getContractNum() {
		return contractNum;
	}

	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}

	public String getCombInfo() {
		return combInfo;
	}

	public void setCombInfo(String combInfo) {
		this.combInfo = combInfo;
	}

	public String getApiTarget() {
		return apiTarget;
	}

	public void setApiTarget(String apiTarget) {
		this.apiTarget = apiTarget;
	}

	public String getGlobalNo() {
		return globalNo;
	}

	public void setGlobalNo(String globalNo) {
		this.globalNo = globalNo;
	}

	public String getRsltCd() {
		return rsltCd;
	}

	public void setRsltCd(String rsltCd) {
		this.rsltCd = rsltCd;
	}

	public String getPrcsSbst() {
		return prcsSbst;
	}

	public void setPrcsSbst(String prcsSbst) {
		this.prcsSbst = prcsSbst;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getCombProdNm() {
		return combProdNm;
	}

	public void setCombProdNm(String combProdNm) {
		this.combProdNm = combProdNm;
	}

	public String getSvcContDivNm() {
		return svcContDivNm;
	}

	public void setSvcContDivNm(String svcContDivNm) {
		this.svcContDivNm = svcContDivNm;
	}

	public String getProdNm() {
		return prodNm;
	}

	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}

	public String getSvcNo() {
		return svcNo;
	}

	public void setSvcNo(String svcNo) {
		this.svcNo = svcNo;
	}

	public String getCombEngtPerdMonsNum() {
		return combEngtPerdMonsNum;
	}

	public void setCombEngtPerdMonsNum(String combEngtPerdMonsNum) {
		this.combEngtPerdMonsNum = combEngtPerdMonsNum;
	}

	public String getCombEngtExpirDt() {
		return combEngtExpirDt;
	}

	public void setCombEngtExpirDt(String combEngtExpirDt) {
		this.combEngtExpirDt = combEngtExpirDt;
	}

	public String getCombEngtStDt() {
		return combEngtStDt;
	}

	public void setCombEngtStDt(String combEngtStDt) {
		this.combEngtStDt = combEngtStDt;
	}

	public String getCretId() {
		return cretId;
	}

	public void setCretId(String cretId) {
		this.cretId = cretId;
	}

	public String getCretDt() {
		return cretDt;
	}

	public void setCretDt(String cretDt) {
		this.cretDt = cretDt;
	}

	public String getAmdId() {
		return amdId;
	}

	public void setAmdId(String amdId) {
		this.amdId = amdId;
	}

	public String getAmdDt() {
		return amdDt;
	}

	public void setAmdDt(String amdDt) {
		this.amdDt = amdDt;
	}

	public String getpRateCd() {
		return pRateCd;
	}

	public void setpRateCd(String pRateCd) {
		this.pRateCd = pRateCd;
	}

	public String getpRateNm() {
		return pRateNm;
	}

	public void setpRateNm(String pRateNm) {
		this.pRateNm = pRateNm;
	}

	public String getrRateCd() {
		return rRateCd;
	}

	public void setrRateCd(String rRateCd) {
		this.rRateCd = rRateCd;
	}

	public String getrRateNm() {
		return rRateNm;
	}

	public void setrRateNm(String rRateNm) {
		this.rRateNm = rRateNm;
	}

	public String getProdCd() {
		return prodCd;
	}

	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
}
