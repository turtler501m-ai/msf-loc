package com.ktis.msp.pps.hdofccustmgmt.vo;

import java.io.Serializable;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PpsKtJuoFeatureVo
 * @Description : KT현행화 상품가입정보 VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="ppsKtJuoFeatureVo")
public class PpsKtJuoFeatureVo  extends BaseVo  implements Serializable {
	
	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;

	public static final String TABLE = "PPS_KT_JUO_FEATURE";

	/**
	 * 서비스 계약 번호:number(9) <Primary Key>
	 */
	private int contractNum;

	/**
	 * 상품 코드:varchar2(9) <Primary Key>
	 */
	private String soc;
	
	private String socNm;

	/**
	 * 피쳐 코드(내부 사용):varchar2(6) <Primary Key>
	 */
	private String featureCode;
	
	
	/**
	 * R 부가서비스 P 요금제:char(1)
	 */
	private String serviceType;
	
	/**
	 * 부가서비스명
	 */
	private String serviceTypeNm;
	
	

	/**
	 * 유효 시작 일시:date(0)
	 */
	private String effectiveDate;

	/**
	 * 유효 종료 일시(2030/12/31=서비스중):date(0)
	 */
	private String expirationDate;

	/**
	 * SOC 그룹 구분(K-HUB 사용):char(1)
	 */
	private String socCcrDisplayInd;

	/**
	 * 선불 상품 여부:varchar2(40)
	 */
	private String inbhId;

	/**
	 * 상품 가입 점:varchar2(7)
	 */
	private String dealerCode;

	/**
	 * SVC_CNTR_NO:number(9)
	 */
	private int svcCntrNo;

	/**
	 * EVNT_CD:varchar2(3)
	 */
	private String evntCd;

	/**
	 * EVNT_TRTM_CD:number(9)
	 */
	private int evntTrtmCd;

	/**
	 * EVNT_TRTM_DT:date(0)
	 */
	private String evntTrtmDt;
	
	

	/**
	 * @return the contractNum
	 */
	public int getContractNum() {
		return contractNum;
	}



	/**
	 * @param contractNum the contractNum to set
	 */
	public void setContractNum(int contractNum) {
		this.contractNum = contractNum;
	}



	/**
	 * @return the soc
	 */
	public String getSoc() {
		return soc;
	}



	/**
	 * @param soc the soc to set
	 */
	public void setSoc(String soc) {
		this.soc = soc;
	}



	/**
	 * @return the socNm
	 */
	public String getSocNm() {
		return socNm;
	}



	/**
	 * @param socNm the socNm to set
	 */
	public void setSocNm(String socNm) {
		this.socNm = socNm;
	}



	/**
	 * @return the featureCode
	 */
	public String getFeatureCode() {
		return featureCode;
	}



	/**
	 * @param featureCode the featureCode to set
	 */
	public void setFeatureCode(String featureCode) {
		this.featureCode = featureCode;
	}



	/**
	 * @return the serviceType
	 */
	public String getServiceType() {
		return serviceType;
	}



	/**
	 * @param serviceType the serviceType to set
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}



	/**
	 * @return the serviceTypeNm
	 */
	public String getServiceTypeNm() {
		return serviceTypeNm;
	}



	/**
	 * @param serviceTypeNm the serviceTypeNm to set
	 */
	public void setServiceTypeNm(String serviceTypeNm) {
		this.serviceTypeNm = serviceTypeNm;
	}



	/**
	 * @return the effectiveDate
	 */
	public String getEffectiveDate() {
		return effectiveDate;
	}



	/**
	 * @param effectiveDate the effectiveDate to set
	 */
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}



	/**
	 * @return the expirationDate
	 */
	public String getExpirationDate() {
		return expirationDate;
	}



	/**
	 * @param expirationDate the expirationDate to set
	 */
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}



	/**
	 * @return the socCcrDisplayInd
	 */
	public String getSocCcrDisplayInd() {
		return socCcrDisplayInd;
	}



	/**
	 * @param socCcrDisplayInd the socCcrDisplayInd to set
	 */
	public void setSocCcrDisplayInd(String socCcrDisplayInd) {
		this.socCcrDisplayInd = socCcrDisplayInd;
	}



	/**
	 * @return the inbhId
	 */
	public String getInbhId() {
		return inbhId;
	}



	/**
	 * @param inbhId the inbhId to set
	 */
	public void setInbhId(String inbhId) {
		this.inbhId = inbhId;
	}



	/**
	 * @return the dealerCode
	 */
	public String getDealerCode() {
		return dealerCode;
	}



	/**
	 * @param dealerCode the dealerCode to set
	 */
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}



	/**
	 * @return the svcCntrNo
	 */
	public int getSvcCntrNo() {
		return svcCntrNo;
	}



	/**
	 * @param svcCntrNo the svcCntrNo to set
	 */
	public void setSvcCntrNo(int svcCntrNo) {
		this.svcCntrNo = svcCntrNo;
	}



	/**
	 * @return the evntCd
	 */
	public String getEvntCd() {
		return evntCd;
	}



	/**
	 * @param evntCd the evntCd to set
	 */
	public void setEvntCd(String evntCd) {
		this.evntCd = evntCd;
	}



	/**
	 * @return the evntTrtmCd
	 */
	public int getEvntTrtmCd() {
		return evntTrtmCd;
	}



	/**
	 * @param evntTrtmCd the evntTrtmCd to set
	 */
	public void setEvntTrtmCd(int evntTrtmCd) {
		this.evntTrtmCd = evntTrtmCd;
	}



	/**
	 * @return the evntTrtmDt
	 */
	public String getEvntTrtmDt() {
		return evntTrtmDt;
	}



	/**
	 * @param evntTrtmDt the evntTrtmDt to set
	 */
	public void setEvntTrtmDt(String evntTrtmDt) {
		this.evntTrtmDt = evntTrtmDt;
	}



	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
