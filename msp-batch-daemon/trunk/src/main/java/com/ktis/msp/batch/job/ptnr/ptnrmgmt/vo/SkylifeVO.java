package com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.BaseVo;

public class SkylifeVO extends BaseVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String partnerId;
	private String endDttm;
	private String strtDttm;
	private String customerId;
	private String rateCd;
	private String statCd;
	private String evntCd;
	
	// 파일생성시 사용할 변수
	private String seq;
	private String ci;					//CI
	private String mblTelNo;			//회선번호
	private String custNm;				//고객명
	private String birthDh;				//생년월일
	private String svcOpenDh;			//서비스개통일시
	private String chgDh;				//변경일시
	//101 개통,121 명의변경(양수인),122 번호변경,102 정지,103 정지복구,123 CTN정보변경,301 상품변경,106 해지, 195 명의변경(양도인),129 해지복구
	private String chgKindCd;			//변경종류코드
	private String rscsDh;				//해지일시
	private String scrbrNo;				//주계약번호
	private String nsContractNo;		//N-STEP 가입계약번호
	private String prdtCd;				//상품코드
	private String prdtNm;				//상품명
	private String agncyNm;				//기관명
	private String addCol1;				//추가항목1
	private String addCol2;				//추가항목2
	
	
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public String getEndDttm() {
		return endDttm;
	}
	public void setEndDttm(String endDttm) {
		this.endDttm = endDttm;
	}
	public String getStrtDttm() {
		return strtDttm;
	}
	public void setStrtDttm(String strtDttm) {
		this.strtDttm = strtDttm;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getRateCd() {
		return rateCd;
	}
	public void setRateCd(String rateCd) {
		this.rateCd = rateCd;
	}
	public String getStatCd() {
		return statCd;
	}
	public void setStatCd(String statCd) {
		this.statCd = statCd;
	}
	public String getEvntCd() {
		return evntCd;
	}
	public void setEvntCd(String evntCd) {
		this.evntCd = evntCd;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getCi() {
		return ci;
	}
	public void setCi(String ci) {
		this.ci = ci;
	}
	public String getMblTelNo() {
		return mblTelNo;
	}
	public void setMblTelNo(String mblTelNo) {
		this.mblTelNo = mblTelNo;
	}
	public String getCustNm() {
		return custNm;
	}
	public void setCustNm(String custNm) {
		this.custNm = custNm;
	}
	public String getBirthDh() {
		return birthDh;
	}
	public void setBirthDh(String birthDh) {
		this.birthDh = birthDh;
	}
	public String getSvcOpenDh() {
		return svcOpenDh;
	}
	public void setSvcOpenDh(String svcOpenDh) {
		this.svcOpenDh = svcOpenDh;
	}
	public String getChgDh() {
		return chgDh;
	}
	public void setChgDh(String chgDh) {
		this.chgDh = chgDh;
	}
	public String getChgKindCd() {
		return chgKindCd;
	}
	public void setChgKindCd(String chgKindCd) {
		this.chgKindCd = chgKindCd;
	}
	public String getRscsDh() {
		return rscsDh;
	}
	public void setRscsDh(String rscsDh) {
		this.rscsDh = rscsDh;
	}
	public String getScrbrNo() {
		return scrbrNo;
	}
	public void setScrbrNo(String scrbrNo) {
		this.scrbrNo = scrbrNo;
	}
	public String getNsContractNo() {
		return nsContractNo;
	}
	public void setNsContractNo(String nsContractNo) {
		this.nsContractNo = nsContractNo;
	}
	public String getPrdtCd() {
		return prdtCd;
	}
	public void setPrdtCd(String prdtCd) {
		this.prdtCd = prdtCd;
	}
	public String getPrdtNm() {
		return prdtNm;
	}
	public void setPrdtNm(String prdtNm) {
		this.prdtNm = prdtNm;
	}
	public String getAgncyNm() {
		return agncyNm;
	}
	public void setAgncyNm(String agncyNm) {
		this.agncyNm = agncyNm;
	}
	public String getAddCol1() {
		return addCol1;
	}
	public void setAddCol1(String addCol1) {
		this.addCol1 = addCol1;
	}
	public String getAddCol2() {
		return addCol2;
	}
	public void setAddCol2(String addCol2) {
		this.addCol2 = addCol2;
	}
		
}
