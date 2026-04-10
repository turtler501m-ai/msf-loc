package com.ktis.msp.org.hndsetamtmgmt.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : HndstAmtVo
 * @Description : 제품 단가 관리 VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.14 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="hndstAmtVo")
public class HndstAmtVo extends BaseVo implements Serializable {

	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = -6392049202719887961L;


	
    private String prdtId; /** 제품ID */
    private String oldYn; /** 중고여부 */
    private String oldYnNm; /** 중고여부 */
    private String unitPricExprDttm; /** 단가만료일시 */
    private String unitPricApplDttm; /** 단가적용일시 */
    private int inUnitPric; /** 입고단가 */
    private int outUnitPric; /** 출고단가 */
    private String remrk; /** 비고 */
    private int newsAgaencySubsidy; /** 통신사보조금 */
    private int mnfctGrant; /** 제조사장려금 */
    private int mnfctGrantPlcy; /** 제조사장려금정책용 */
    private String subsidyPropType; /** 보조금비례유형 */
    private String subsidyMaxYn; /** 보조금상한적용여부 */
    private String regId; /** 등록자ID */
    private String regDttm; /** 등록일시 */
    private String rvisnId; /** 수정자ID */
    private String rvisnDttm; /** 수정일시 */
    private String prdtNm;
    private String mnfctId;
    private String mnfctNm;
    
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	

	/**
	 * @return the oldYnNm
	 */
	public String getOldYnNm() {
		return oldYnNm;
	}



	/**
	 * @param oldYnNm the oldYnNm to set
	 */
	public void setOldYnNm(String oldYnNm) {
		this.oldYnNm = oldYnNm;
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
	 * @return the outUnitPric
	 */
	public int getOutUnitPric() {
		return outUnitPric;
	}


	/**
	 * @param outUnitPric the outUnitPric to set
	 */
	public void setOutUnitPric(int outUnitPric) {
		this.outUnitPric = outUnitPric;
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
	 * @return the inUnitPric
	 */
	public int getInUnitPric() {
		return inUnitPric;
	}


	/**
	 * @param inUnitPric the inUnitPric to set
	 */
	public void setInUnitPric(int inUnitPric) {
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
	 * @return the newsAgaencySubsidy
	 */
	public int getNewsAgaencySubsidy() {
		return newsAgaencySubsidy;
	}


	/**
	 * @param newsAgaencySubsidy the newsAgaencySubsidy to set
	 */
	public void setNewsAgaencySubsidy(int newsAgaencySubsidy) {
		this.newsAgaencySubsidy = newsAgaencySubsidy;
	}


	/**
	 * @return the mnfctGrant
	 */
	public int getMnfctGrant() {
		return mnfctGrant;
	}


	/**
	 * @param mnfctGrant the mnfctGrant to set
	 */
	public void setMnfctGrant(int mnfctGrant) {
		this.mnfctGrant = mnfctGrant;
	}


	/**
	 * @return the mnfctGrantPlcy
	 */
	public int getMnfctGrantPlcy() {
		return mnfctGrantPlcy;
	}


	/**
	 * @param mnfctGrantPlcy the mnfctGrantPlcy to set
	 */
	public void setMnfctGrantPlcy(int mnfctGrantPlcy) {
		this.mnfctGrantPlcy = mnfctGrantPlcy;
	}


	/**
	 * @return the subsidyPropType
	 */
	public String getSubsidyPropType() {
		return subsidyPropType;
	}


	/**
	 * @param subsidyPropType the subsidyPropType to set
	 */
	public void setSubsidyPropType(String subsidyPropType) {
		this.subsidyPropType = subsidyPropType;
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


	
}
