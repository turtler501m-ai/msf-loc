/**
 * 
 */
package com.ktis.msp.cmn.intmmdl.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : 
 * @Description : 
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 
 * @
 * @author : 심정보
 * @Create Date : 2015. 7. 28.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="cmnIntmMdlVO")
public class CmnIntmMdlVO extends BaseVo implements Serializable {

	private static final long serialVersionUID = -98940809836720841L;

	private String prdtId;			/* 모델ID */
	private String rprsPrdtId;		/* 대표모델ID */
	private String rprsYn;			/* 대표여부 */
	private String prdtCode;		/* 모델코드 */
	private String rprsPrdtCode;	/* 대표모델코드 */
	private String prdtNm;			/* 모델명 */
	private String mnfctId;			/* 제조사ID */
	private String mnfctNm;			/* 제조사명 */
	private String prdtTypeCd;		/* 모델유형코드 */
	private String prdtTypeNm;		/* 모델유형명 */
	private String prdtIndCd;		/* 모델구분코드 */
	private String prdtIndNm;		/* 모델구분명 */
	private String prdtLnchDt;		/* 모델출시일자 */
	private String prdtDt;			/* 모델단종일자 */
	private String prdtColrCd;		/* 모델색상코드 */
	private String prdtColrNm;		/* 모델색상명 */
	private String regId;			/* 등록자ID */
	private String regDttm;			/* 등록일시 */
	private String rvisnId;			/* 수정자ID */
	private String rvisnDttm;		/* 수정일시 */
	private String prdtCdColr;		/* 색상모델코드 (입력) */
	private String nfcUsimYn;		/* NFC지원여부 */
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}


	/**
	 * @return the prdtId
	 */
	public String getPrdtId() {
		return prdtId;
	}


	/**
	 * @param prdtId the prdtId to set
	 */
	public void setPrdtId(String prdtId) {
		this.prdtId = prdtId;
	}


	/**
	 * @return the rprsPrdtId
	 */
	public String getRprsPrdtId() {
		return rprsPrdtId;
	}


	/**
	 * @param rprsPrdtId the rprsPrdtId to set
	 */
	public void setRprsPrdtId(String rprsPrdtId) {
		this.rprsPrdtId = rprsPrdtId;
	}


	/**
	 * @return the rprsYn
	 */
	public String getRprsYn() {
		return rprsYn;
	}


	/**
	 * @param rprsYn the rprsYn to set
	 */
	public void setRprsYn(String rprsYn) {
		this.rprsYn = rprsYn;
	}


	/**
	 * @return the prdtCode
	 */
	public String getPrdtCode() {
		return prdtCode;
	}


	/**
	 * @param prdtCode the prdtCode to set
	 */
	public void setPrdtCode(String prdtCode) {
		this.prdtCode = prdtCode;
	}


	/**
	 * @return the rprsPrdtCode
	 */
	public String getRprsPrdtCode() {
		return rprsPrdtCode;
	}


	/**
	 * @param rprsPrdtCode the rprsPrdtCode to set
	 */
	public void setRprsPrdtCode(String rprsPrdtCode) {
		this.rprsPrdtCode = rprsPrdtCode;
	}


	/**
	 * @return the prdtNm
	 */
	public String getPrdtNm() {
		return prdtNm;
	}


	/**
	 * @param prdtNm the prdtNm to set
	 */
	public void setPrdtNm(String prdtNm) {
		this.prdtNm = prdtNm;
	}


	/**
	 * @return the mnfctId
	 */
	public String getMnfctId() {
		return mnfctId;
	}


	/**
	 * @param mnfctId the mnfctId to set
	 */
	public void setMnfctId(String mnfctId) {
		this.mnfctId = mnfctId;
	}


	/**
	 * @return the mnfctNm
	 */
	public String getMnfctNm() {
		return mnfctNm;
	}


	/**
	 * @param mnfctNm the mnfctNm to set
	 */
	public void setMnfctNm(String mnfctNm) {
		this.mnfctNm = mnfctNm;
	}


	/**
	 * @return the prdtTypeCd
	 */
	public String getPrdtTypeCd() {
		return prdtTypeCd;
	}


	/**
	 * @param prdtTypeCd the prdtTypeCd to set
	 */
	public void setPrdtTypeCd(String prdtTypeCd) {
		this.prdtTypeCd = prdtTypeCd;
	}


	/**
	 * @return the prdtTypeNm
	 */
	public String getPrdtTypeNm() {
		return prdtTypeNm;
	}


	/**
	 * @param prdtTypeNm the prdtTypeNm to set
	 */
	public void setPrdtTypeNm(String prdtTypeNm) {
		this.prdtTypeNm = prdtTypeNm;
	}


	/**
	 * @return the prdtIndCd
	 */
	public String getPrdtIndCd() {
		return prdtIndCd;
	}


	/**
	 * @param prdtIndCd the prdtIndCd to set
	 */
	public void setPrdtIndCd(String prdtIndCd) {
		this.prdtIndCd = prdtIndCd;
	}


	/**
	 * @return the prdtIndNm
	 */
	public String getPrdtIndNm() {
		return prdtIndNm;
	}


	/**
	 * @param prdtIndNm the prdtIndNm to set
	 */
	public void setPrdtIndNm(String prdtIndNm) {
		this.prdtIndNm = prdtIndNm;
	}


	/**
	 * @return the prdtLnchDt
	 */
	public String getPrdtLnchDt() {
		return prdtLnchDt;
	}


	/**
	 * @param prdtLnchDt the prdtLnchDt to set
	 */
	public void setPrdtLnchDt(String prdtLnchDt) {
		this.prdtLnchDt = prdtLnchDt;
	}


	/**
	 * @return the prdtDt
	 */
	public String getPrdtDt() {
		return prdtDt;
	}


	/**
	 * @param prdtDt the prdtDt to set
	 */
	public void setPrdtDt(String prdtDt) {
		this.prdtDt = prdtDt;
	}


	/**
	 * @return the prdtColrCd
	 */
	public String getPrdtColrCd() {
		return prdtColrCd;
	}


	/**
	 * @param prdtColrCd the prdtColrCd to set
	 */
	public void setPrdtColrCd(String prdtColrCd) {
		this.prdtColrCd = prdtColrCd;
	}


	/**
	 * @return the prdtColrNm
	 */
	public String getPrdtColrNm() {
		return prdtColrNm;
	}


	/**
	 * @param prdtColrNm the prdtColrNm to set
	 */
	public void setPrdtColrNm(String prdtColrNm) {
		this.prdtColrNm = prdtColrNm;
	}


	/**
	 * @return the regId
	 */
	public String getRegId() {
		return regId;
	}


	/**
	 * @param regId the regId to set
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}


	/**
	 * @return the regDttm
	 */
	public String getRegDttm() {
		return regDttm;
	}


	/**
	 * @param regDttm the regDttm to set
	 */
	public void setRegDttm(String regDttm) {
		this.regDttm = regDttm;
	}


	/**
	 * @return the rvisnId
	 */
	public String getRvisnId() {
		return rvisnId;
	}


	/**
	 * @param rvisnId the rvisnId to set
	 */
	public void setRvisnId(String rvisnId) {
		this.rvisnId = rvisnId;
	}


	/**
	 * @return the rvisnDttm
	 */
	public String getRvisnDttm() {
		return rvisnDttm;
	}


	/**
	 * @param rvisnDttm the rvisnDttm to set
	 */
	public void setRvisnDttm(String rvisnDttm) {
		this.rvisnDttm = rvisnDttm;
	}


	/**
	 * @return the prdtCdColr
	 */
	public String getPrdtCdColr() {
		return prdtCdColr;
	}


	/**
	 * @param prdtCdColr the prdtCdColr to set
	 */
	public void setPrdtCdColr(String prdtCdColr) {
		this.prdtCdColr = prdtCdColr;
	}
	
	
	/**
	 * @return the nfcUsimYn
	 */
	public String getNfcUsimYn() {
		return nfcUsimYn;
	}
	
	
	/**
	 * @param nfcUsimYn the nfcUsimYn to set
	 */
	public void setNfcUsimYn(String nfcUsimYn) {
		this.nfcUsimYn = nfcUsimYn;
	}


	
}
