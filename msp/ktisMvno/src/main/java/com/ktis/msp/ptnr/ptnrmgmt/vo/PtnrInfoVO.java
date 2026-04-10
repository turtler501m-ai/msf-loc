package com.ktis.msp.ptnr.ptnrmgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

public class PtnrInfoVO extends BaseVo implements Serializable {

	/** 제휴사관리 **/
	private String searchGbn;
	private String searchVal;

	private String partnerId;		/** 제휴사ID */
	private String partnerNm;		/** 제휴사명 */
	private String managerNm;		/** 담당자명 */
	private String contactNum;		/** 담당자 연락처 */
	private String contactNum1;		/** 담당자 연락처 */
	private String contactNum2;		/** 담당자 연락처 */
	private String contactNum3;		/** 담당자 연락처 */
	private String email;			/** 담당자 이메일 */
	private String partnerLinkId;	/** 연동ID */
	private String useYn;			/** 제휴여부 */

	private String ifNo;			/** IF_ID */
	private String ifRule;			/** 연동주기 */
	private String memo;			/** 내용 */
	private String upDir;			/** 업로드경로 */
	private String downDir;			/** 다운로드경로 */
	
	/** 제휴요금관리 **/
	private String ptnrId;			/** 제휴사ID */
	private String rateNm;			/** 요금제명 */
	private String pointType;		/** 지급유형 */
	
	private String rateCd;			/** 요금제코드 */
	private String rateType;		/** 요금제유형 */
	private String dataType;		/** 데이터유형 */
	private String baseAmt;			/** 기본요금 */
	private String pointVal;		/** 지급포인트 */
	
	/** 제휴부가서비스관리 **/
	private String addSvcCd;		/** 부가서비스코드 */
	private String addSvcNm;		/** 부가서비스명 */
	private String dcCd;           /** 할인율 */
	
	/** 제휴프로모션 **/
	private String prmtId;			/** 프로모션ID */
	private String prmtNm;			/** 프로모션명 */
	private String stdrDt;         /** 기준일     */
	private String strtDate;       /** 적용시작일 */
	private String endDate;        /** 적용종료일 */
	private String prvdCycl;       /** 지급주기(개월수) */

	private String orgnId;         /** 대리점 조직ID */
	private String orgnNm;         /** 대리점 조직명 */
	private String usimOrgnId;     /** 유심접점 조직ID */
	private String usimOrgnNm;     /** 유심접점 조직명 */
	private String onOffType;      /** 모집경로 */
	private String onOffTypeNm;    /** 모집경로명 */
	
	public String getSearchGbn() {
		return searchGbn;
	}
	public String getPtnrId() {
		return ptnrId;
	}
	public void setPtnrId(String ptnrId) {
		this.ptnrId = ptnrId;
	}
	public String getRateNm() {
		return rateNm;
	}
	public void setRateNm(String rateNm) {
		this.rateNm = rateNm;
	}
	public String getPointType() {
		return pointType;
	}
	public void setPointType(String pointType) {
		this.pointType = pointType;
	}
	public String getRateCd() {
		return rateCd;
	}
	public void setRateCd(String rateCd) {
		this.rateCd = rateCd;
	}
	public String getRateType() {
		return rateType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getBaseAmt() {
		return baseAmt;
	}
	public void setBaseAmt(String baseAmt) {
		this.baseAmt = baseAmt;
	}
	public String getPointVal() {
		return pointVal;
	}
	public void setPointVal(String pointVal) {
		this.pointVal = pointVal;
	}
	public void setSearchGbn(String searchGbn) {
		this.searchGbn = searchGbn;
	}
	public String getSearchVal() {
		return searchVal;
	}
	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
	}
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public String getPartnerNm() {
		return partnerNm;
	}
	public void setPartnerNm(String partnerNm) {
		this.partnerNm = partnerNm;
	}
	public String getManagerNm() {
		return managerNm;
	}
	public void setManagerNm(String managerNm) {
		this.managerNm = managerNm;
	}
	public String getContactNum() {
		return contactNum;
	}
	public void setContactNum(String contactNum) {
		this.contactNum = contactNum;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPartnerLinkId() {
		return partnerLinkId;
	}
	public void setPartnerLinkId(String partnerLinkId) {
		this.partnerLinkId = partnerLinkId;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getIfNo() {
		return ifNo;
	}
	public void setIfNo(String ifNo) {
		this.ifNo = ifNo;
	}
	public String getIfRule() {
		return ifRule;
	}
	public void setIfRule(String ifRule) {
		this.ifRule = ifRule;
	}
	public String getUpDir() {
		return upDir;
	}
	public void setUpDir(String upDir) {
		this.upDir = upDir;
	}
	public String getDownDir() {
		return downDir;
	}
	public void setDownDir(String downDir) {
		this.downDir = downDir;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getContactNum1() {
		return contactNum1;
	}
	public void setContactNum1(String contactNum1) {
		this.contactNum1 = contactNum1;
	}
	public String getContactNum2() {
		return contactNum2;
	}
	public void setContactNum2(String contactNum2) {
		this.contactNum2 = contactNum2;
	}
	public String getContactNum3() {
		return contactNum3;
	}
	public void setContactNum3(String contactNum3) {
		this.contactNum3 = contactNum3;
	}
	public String getAddSvcCd() {
		return addSvcCd;
	}
	public void setAddSvcCd(String addSvcCd) {
		this.addSvcCd = addSvcCd;
	}
	public String getAddSvcNm() {
		return addSvcNm;
	}
	public void setAddSvcNm(String addSvcNm) {
		this.addSvcNm = addSvcNm;
	}
	public String getDcCd() {
		return dcCd;
	}
	public void setDcCd(String dcCd) {
		this.dcCd = dcCd;
	}
	public String getPrmtId() {
		return prmtId;
	}
	public void setPrmtId(String prmtId) {
		this.prmtId = prmtId;
	}
	public String getPrmtNm() {
		return prmtNm;
	}
	public void setPrmtNm(String prmtNm) {
		this.prmtNm = prmtNm;
	}
	public String getStdrDt() {
		return stdrDt;
	}
	public void setStdrDt(String stdrDt) {
		this.stdrDt = stdrDt;
	}
	public String getStrtDate() {
		return strtDate;
	}
	public void setStrtDate(String strtDate) {
		this.strtDate = strtDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getPrvdCycl() {
		return prvdCycl;
	}
	public void setPrvdCycl(String prvdCycl) {
		this.prvdCycl = prvdCycl;
	}
	public String getOrgnId() {
		return orgnId;
	}
	public void setOrgnId(String orgnId) {
		this.orgnId = orgnId;
	}
	public String getUsimOrgnId() {
		return usimOrgnId;
	}
	public void setUsimOrgnId(String usimOrgnId) {
		this.usimOrgnId = usimOrgnId;
	}
	public String getOnOffType() {
		return onOffType;
	}
	public void setOnOffType(String onOffType) {
		this.onOffType = onOffType;
	}
	
	
	
}
