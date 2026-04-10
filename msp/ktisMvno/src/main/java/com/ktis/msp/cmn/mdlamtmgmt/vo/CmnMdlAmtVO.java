/**
 * 
 */
package com.ktis.msp.cmn.mdlamtmgmt.vo;

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
 * @Create Date : 2015. 8. 5.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="cmnMdlAmtVO")
public class CmnMdlAmtVO extends BaseVo implements Serializable {

	private static final long serialVersionUID = -98940809836720841L;

	private String rprsPrdtId;					/* 대표모델ID */
	private String prdtId;						/* 모델ID */
	private String mnfctId;						/* 제조사ID */
	private String prdtTypeCd;					/* 모델유형코드 */
	private String prdtNm;						/* 모델명 */
	
	private String rprsPrdtCode;				/* 대표모델코드 */
	private String unitPricApplDttm;			/* 단가적용일시 */
	private String unitPricExprDttm;			/* 단가만료일시 */
	private String inUnitPric;					/* 입고단가 */
	private String oldYn;						/* 중고여부 */
	private String remrk;						/* 비고 */
	private String maxAmt;						/* 최대금액 */
	
	private String outUnitPric;					/* 출고단가 */
	private String mnfctGrant;					/* 제조사장려금 */
	private String newsAgaencySubsidy;			/* 통신사보조금 */
	private String subsidyMaxYn;				/* 보조금상한적용여부 */
	
	private String regId;						/* 등록자ID */
	private String rvisnId;						/* 수정자ID */
	private String today;						/* 조회일자 */
	private String totalCnt;					/* 총건수 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
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
	 * @return the unitPricApplDttm
	 */
	public String getUnitPricApplDttm() {
		return unitPricApplDttm;
	}

	/**
	 * @param unitPricApplDttm the unitPricApplDttm to set
	 */
	public void setUnitPricApplDttm(String unitPricApplDttm) {
		this.unitPricApplDttm = unitPricApplDttm;
	}

	/**
	 * @return the unitPricExprDttm
	 */
	public String getUnitPricExprDttm() {
		return unitPricExprDttm;
	}

	/**
	 * @param unitPricExprDttm the unitPricExprDttm to set
	 */
	public void setUnitPricExprDttm(String unitPricExprDttm) {
		this.unitPricExprDttm = unitPricExprDttm;
	}

	/**
	 * @return the inUnitPric
	 */
	public String getInUnitPric() {
		return inUnitPric;
	}

	/**
	 * @param inUnitPric the inUnitPric to set
	 */
	public void setInUnitPric(String inUnitPric) {
		this.inUnitPric = inUnitPric;
	}

	/**
	 * @return the oldYn
	 */
	public String getOldYn() {
		return oldYn;
	}

	/**
	 * @param oldYn the oldYn to set
	 */
	public void setOldYn(String oldYn) {
		this.oldYn = oldYn;
	}

	/**
	 * @return the remrk
	 */
	public String getRemrk() {
		return remrk;
	}

	/**
	 * @param remrk the remrk to set
	 */
	public void setRemrk(String remrk) {
		this.remrk = remrk;
	}

	/**
	 * @return the maxAmt
	 */
	public String getMaxAmt() {
		return maxAmt;
	}

	/**
	 * @param maxAmt the maxAmt to set
	 */
	public void setMaxAmt(String maxAmt) {
		this.maxAmt = maxAmt;
	}

	/**
	 * @return the outUnitPric
	 */
	public String getOutUnitPric() {
		return outUnitPric;
	}

	/**
	 * @param outUnitPric the outUnitPric to set
	 */
	public void setOutUnitPric(String outUnitPric) {
		this.outUnitPric = outUnitPric;
	}

	/**
	 * @return the mnfctGrant
	 */
	public String getMnfctGrant() {
		return mnfctGrant;
	}

	/**
	 * @param mnfctGrant the mnfctGrant to set
	 */
	public void setMnfctGrant(String mnfctGrant) {
		this.mnfctGrant = mnfctGrant;
	}

	/**
	 * @return the newsAgaencySubsidy
	 */
	public String getNewsAgaencySubsidy() {
		return newsAgaencySubsidy;
	}

	/**
	 * @param newsAgaencySubsidy the newsAgaencySubsidy to set
	 */
	public void setNewsAgaencySubsidy(String newsAgaencySubsidy) {
		this.newsAgaencySubsidy = newsAgaencySubsidy;
	}

	/**
	 * @return the subsidyMaxYn
	 */
	public String getSubsidyMaxYn() {
		return subsidyMaxYn;
	}

	/**
	 * @param subsidyMaxYn the subsidyMaxYn to set
	 */
	public void setSubsidyMaxYn(String subsidyMaxYn) {
		this.subsidyMaxYn = subsidyMaxYn;
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
	 * @return the today
	 */
	public String getToday() {
		return today;
	}

	/**
	 * @param today the today to set
	 */
	public void setToday(String today) {
		this.today = today;
	}

	/**
	 * @return the totalCnt
	 */
	public String getTotalCnt() {
		return totalCnt;
	}

	/**
	 * @param totalCnt the totalCnt to set
	 */
	public void setTotalCnt(String totalCnt) {
		this.totalCnt = totalCnt;
	}
	
	
	
}
