package com.ktis.msp.org.orgnrelmgmt.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : OrgnRelMgmtVO
 * @Description : 조직관계관리 VO
 * @
 * @ 수정일       수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @
 * @
 * @author : 고은정
 * @Create Date : 2014. 8. 20.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="orgnRelMgmtVO")
public class OrgnRelMgmtVO extends BaseVo  implements Serializable {
	
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4765247886602207995L;
	
	private String relOrgnLvl; /** 상대조직레벨 */
    private String relOrgnLvlNm; /** 상대조직레벨 */
    private String relOrgnDtlType; /** 상대상세유형 */
    private String relOrgnDtlTypeNm; /** 상대상세유형 */
    private String relOrgnType; /** 상대유형 */
    private String relOrgnTypeNm; /** 상대유형 */
    private String relOrgnNm; /** 상대조직명 */
    private String relOrgnId; /** 상대조직ID */
    private String applEndDt; /** 적용만료일자 */
    private String applStrtDt; /** 적용시작일자 */
    private String relType; /** 관계유형 */
    private String relTypeNm; /** 관계유형 */
    private String orgnId; /** 조직 ID */
    private String orgnRelId; /** 조직관계ID */
    private String orgnNm;
    private String oldRelOrgnId; /** update 구문에 사용하기 위한 키값 */
    
    @Override
    public String toString() {
       return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    
	public String getOldRelOrgnId() {
		return oldRelOrgnId;
	}


	public void setOldRelOrgnId(String oldRelOrgnId) {
		this.oldRelOrgnId = oldRelOrgnId;
	}


	/**
	 * @return the relOrgnLvl
	 */
	public String getRelOrgnLvl() {
		return relOrgnLvl;
	}

	/**
	 * @param relOrgnLvl the relOrgnLvl to set
	 */
	public void setRelOrgnLvl(String relOrgnLvl) {
		this.relOrgnLvl = relOrgnLvl;
	}

	/**
	 * @return the relOrgnLvlNm
	 */
	public String getRelOrgnLvlNm() {
		return relOrgnLvlNm;
	}

	/**
	 * @param relOrgnLvlNm the relOrgnLvlNm to set
	 */
	public void setRelOrgnLvlNm(String relOrgnLvlNm) {
		this.relOrgnLvlNm = relOrgnLvlNm;
	}

	/**
	 * @return the relOrgnDtlType
	 */
	public String getRelOrgnDtlType() {
		return relOrgnDtlType;
	}

	/**
	 * @param relOrgnDtlType the relOrgnDtlType to set
	 */
	public void setRelOrgnDtlType(String relOrgnDtlType) {
		this.relOrgnDtlType = relOrgnDtlType;
	}

	/**
	 * @return the relOrgnDtlTypeNm
	 */
	public String getRelOrgnDtlTypeNm() {
		return relOrgnDtlTypeNm;
	}

	/**
	 * @param relOrgnDtlTypeNm the relOrgnDtlTypeNm to set
	 */
	public void setRelOrgnDtlTypeNm(String relOrgnDtlTypeNm) {
		this.relOrgnDtlTypeNm = relOrgnDtlTypeNm;
	}

	/**
	 * @return the relOrgnType
	 */
	public String getRelOrgnType() {
		return relOrgnType;
	}

	/**
	 * @param relOrgnType the relOrgnType to set
	 */
	public void setRelOrgnType(String relOrgnType) {
		this.relOrgnType = relOrgnType;
	}

	/**
	 * @return the relOrgnTypeNm
	 */
	public String getRelOrgnTypeNm() {
		return relOrgnTypeNm;
	}

	/**
	 * @param relOrgnTypeNm the relOrgnTypeNm to set
	 */
	public void setRelOrgnTypeNm(String relOrgnTypeNm) {
		this.relOrgnTypeNm = relOrgnTypeNm;
	}

	/**
	 * @return the relOrgnNm
	 */
	public String getRelOrgnNm() {
		return relOrgnNm;
	}

	/**
	 * @param relOrgnNm the relOrgnNm to set
	 */
	public void setRelOrgnNm(String relOrgnNm) {
		this.relOrgnNm = relOrgnNm;
	}

	/**
	 * @return the relOrgnId
	 */
	public String getRelOrgnId() {
		return relOrgnId;
	}

	/**
	 * @param relOrgnId the relOrgnId to set
	 */
	public void setRelOrgnId(String relOrgnId) {
		this.relOrgnId = relOrgnId;
	}

	/**
	 * @return the applEndDt
	 */
	public String getApplEndDt() {
		return applEndDt;
	}

	/**
	 * @param applEndDt the applEndDt to set
	 */
	public void setApplEndDt(String applEndDt) {
		this.applEndDt = applEndDt;
	}

	/**
	 * @return the applStrtDt
	 */
	public String getApplStrtDt() {
		return applStrtDt;
	}

	/**
	 * @param applStrtDt the applStrtDt to set
	 */
	public void setApplStrtDt(String applStrtDt) {
		this.applStrtDt = applStrtDt;
	}

	/**
	 * @return the relType
	 */
	public String getRelType() {
		return relType;
	}

	/**
	 * @param relType the relType to set
	 */
	public void setRelType(String relType) {
		this.relType = relType;
	}

	/**
	 * @return the relTypeNm
	 */
	public String getRelTypeNm() {
		return relTypeNm;
	}

	/**
	 * @param relTypeNm the relTypeNm to set
	 */
	public void setRelTypeNm(String relTypeNm) {
		this.relTypeNm = relTypeNm;
	}

	/**
	 * @return the orgnId
	 */
	public String getOrgnId() {
		return orgnId;
	}

	/**
	 * @param orgnId the orgnId to set
	 */
	public void setOrgnId(String orgnId) {
		this.orgnId = orgnId;
	}

	/**
	 * @return the orgnRelId
	 */
	public String getOrgnRelId() {
		return orgnRelId;
	}

	/**
	 * @param orgnRelId the orgnRelId to set
	 */
	public void setOrgnRelId(String orgnRelId) {
		this.orgnRelId = orgnRelId;
	}

	/**
	 * @return the orgnNm
	 */
	public String getOrgnNm() {
		return orgnNm;
	}

	/**
	 * @param orgnNm the orgnNm to set
	 */
	public void setOrgnNm(String orgnNm) {
		this.orgnNm = orgnNm;
	}
    
}

