package com.ktis.msp.sale.rateMgmt.vo;

import java.io.Serializable;
import java.util.List;

import com.ktis.msp.base.mvc.BaseVo;

public class RateMgmtVO extends BaseVo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String rateCd;
	private String rateNm;
	private String rateGrpCd;
	private String rateGrpNm;
	private String applStrtDt;
	private String applEndDt;
	private String payClCd;
	private String rateType;
	private String dataType;
	private int baseAmt;
	private int dcAmt;
	private String freeCallClCd;
	private String freeCallCnt;
	private String nwInCallCnt;
	private String nwOutCallCnt;
	private String freeSmsCnt;
	private String freeDataCnt;
	private String userId;
	private String searchGb;
	private String searchVal;
	private String stdrDt;
	private String rmk;
	private String sort;
	private String agrmTrm;
	private String onlineTypeCd;
	
	private String tmAmt;	// 단말할인
	private String rtAmt;	// 요금할인
	private String spAmt;	// 심플할인
	private String serviceType; 	// 서비스유형(기본,부가서비스)
	private String sprtTp;			// 지원금유형(단말할인,요금할인)
	private String applStrtDttm;	// 할인금액 시작일시
	private String applEndDttm;		// 할인금액 종료일시
	
	private String cmnt;
	private String orgnId;
	private String prdtId;
	
	private String ccAmt12;
	private String ccAmt18;
	private String tmAmt12;	// 단말위약금12
	private String tmAmt18;	// 단말위약금18
	private String rtAmt12;	// 요금위약금12
	private String rtAmt18;	// 요금위약금18
	private String spAmt12;	// 심플위약금12
	private String spAmt18;	// 심플위약금18
	
	// 2017-03-17
	private String sttcYn;	// 통계대상여부
	private String onlineCanYn;	// 온라인해지가능여부
	private String canCmnt;		// 해지안내
	
	/* v2017.07 요금제 관리 엑셀 다운로드 */
	private String serviceTypeNm;
	private String payClCdNm;
	private String rateTypeNm;
	private String onlineTypeNm;
	
	
	private String svcRelTp;		// 관계유형(B:기본, C:선택) 
	private String addSvcCd;		// 부가서비스 코드
	
	/** 요금그룹정보 **/
	private String mstRateGrpCd;	/** 마스터그룹코드 **/
	private String mstRateGrpNm;	/** 마스터그룹코드명 **/
	private String subRateGrpCd;	/** 서브그룹코드 **/
	private String rateGrpTypeCd;	/** 그룹유형코드 **/
	private String popupTypeCd;		/** 팝업타입코드 **/
	private String strtDate;		/** 적용시작일시 **/
	private String endDate;			/** 적용종료일시 **/
	private String usrNm;			/** 등록자 **/
	private String usrDttm;			/** 등록일시 **/
	
	private String freeCallCd;   /** 무료통화(분)코드 **/
	private String nwInCallCd; /** 망내무료통화 코드**/
	private String freeSmsCd; /** 무료문자코드 **/
	private String freeDataCd; /** 무료데이터코드 **/
	//pRateCd","pRateNm","rRateCd","rRateNm","appStrDt","appEndDt","regNm","regDt","rvisnNm","rvisnDt
	
	/**결합요금제 조회**/
	private String pRateCd;
	private String pRateNm;
	private String rRateCd;
	private String rRateNm;
	private String appStrDt;
	private String appEndDt;
	private String regNm;
	private String regDt;
	private String rvisnNm;
	private String rvisnDt;

	/**A'CEN 요금제 조회**/
	private String useYn;				//운영여부
	private String rateDivCd;           //요금제유형
	private String baseVatAmt;          //기본금액(VAT 포함)
	private String prmtVatAmt;          //프로모션할인적용금액(VAT 포함)
	private String callCnt;             //음성(기본/분) 음성통화
	private String callUnit;            //음성통화 단위
	private String prmtCallCnt;         //프로모션 추가 음성통화
	private String prmtCallUnit;        //프로모션 추가 음성통화 단위
	private String etcCallCnt;          //기타 음성통화
	private String etcCallUnit;         //기타 음성통화 단위
	private String smsCnt;              //문자
	private String smsUnit;             //문자 단위
	private String prmtSmsCnt;          //추가 문자
	private String prmtSmsUnit;         //추가 문자 단위
	private String dataCnt;             //데이터 (MB)
	private String dataUnit;            //데이터 단위
	private String prmtDataCnt;         //추가 데이터 (MB)
	private String prmtDataUnit;        //추가 데이터 단위
	private String dayDataCnt;          //일 데이터 (DB)
	private String dayDataUnit;         //일 데이터 단위
	private String qosDataCntDesc;      //데이터 최대속도 QOS
	private String qosDataUnit;         //데이터 최대속도 QOS 단위
	private String pstngStartDate;      //적용시작일자
	private String startTm;      		//적용시작일시
	private String pstngEndDate;        //적용종료일자
	private String endTm;        		//적용종료일시
	private String pstngEndDateMod;		//변경종료일자
	private String endTmMod;			//변경종료일시
	private String cretId;              //생성자 ID
	private String cretNm;              //생성자 이름
	private String cretDt;              //생성일시
	private String amdId;               //수정자 ID
	private String amdNm;               //수정자 이름
	private String amdDt;               //수정일시
	private String flag;                //등록 , 수정 구분
	private String fileName;			//엑셀 파일 이름
	private List<RateMgmtVO> items;		//엑셀 rows
	private String newestYn;			//가장 최근 요금제 코드 여부 ("Y"인 경우 변경 적용종료일자 기존 적용종료일자 이후로 변경할수 있게함)
	/* 2024-11-12 리마인드 대상여부 추가 */
	private String remindYn;			//리마인드 대상여부
	private String remindProdType;			//리마인드 상품구분
	
	/* 2025-03-04 요금제 제휴처 약관코드 추가 */
	private String jehuProdType;   // 요금제 제휴처 약관코드

	/* 2025-12-11 온라인해지가능 DAY 추가 */
	private String onlineCanDay;   // 온라인해지가능 DAY

	public String getRateCd() {
		return rateCd;
	}
	public String getFreeCallCd() {
		return freeCallCd;
	}
	public void setFreeCallCd(String freeCallCd) {
		this.freeCallCd = freeCallCd;
	}
	public String getNwInCallCd() {
		return nwInCallCd;
	}
	public void setNwInCallCd(String nwInCallCd) {
		this.nwInCallCd = nwInCallCd;
	}
	public String getFreeSmsCd() {
		return freeSmsCd;
	}
	public void setFreeSmsCd(String freeSmsCd) {
		this.freeSmsCd = freeSmsCd;
	}
	public String getFreeDataCd() {
		return freeDataCd;
	}
	public void setFreeDataCd(String freeDataCd) {
		this.freeDataCd = freeDataCd;
	}
	public void setRateCd(String rateCd) {
		this.rateCd = rateCd;
	}
	public String getRateNm() {
		return rateNm;
	}
	public void setRateNm(String rateNm) {
		this.rateNm = rateNm;
	}
	public String getRateGrpCd() {
		return rateGrpCd;
	}
	public void setRateGrpCd(String rateGrpCd) {
		this.rateGrpCd = rateGrpCd;
	}
	public String getRateGrpNm() {
		return rateGrpNm;
	}
	public void setRateGrpNm(String rateGrpNm) {
		this.rateGrpNm = rateGrpNm;
	}
	public String getApplStrtDt() {
		return applStrtDt;
	}
	public void setApplStrtDt(String applStrtDt) {
		this.applStrtDt = applStrtDt;
	}
	public String getApplEndDt() {
		return applEndDt;
	}
	public void setApplEndDt(String applEndDt) {
		this.applEndDt = applEndDt;
	}
	public String getPayClCd() {
		return payClCd;
	}
	public void setPayClCd(String payClCd) {
		this.payClCd = payClCd;
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
	public int getBaseAmt() {
		return baseAmt;
	}
	public void setBaseAmt(int baseAmt) {
		this.baseAmt = baseAmt;
	}
	public int getDcAmt() {
		return dcAmt;
	}
	public void setDcAmt(int dcAmt) {
		this.dcAmt = dcAmt;
	}
	public String getFreeCallClCd() {
		return freeCallClCd;
	}
	public void setFreeCallClCd(String freeCallClCd) {
		this.freeCallClCd = freeCallClCd;
	}
	public String getFreeCallCnt() {
		return freeCallCnt;
	}
	public void setFreeCallCnt(String freeCallCnt) {
		this.freeCallCnt = freeCallCnt;
	}
	public String getNwInCallCnt() {
		return nwInCallCnt;
	}
	public void setNwInCallCnt(String nwInCallCnt) {
		this.nwInCallCnt = nwInCallCnt;
	}
	public String getNwOutCallCnt() {
		return nwOutCallCnt;
	}
	public void setNwOutCallCnt(String nwOutCallCnt) {
		this.nwOutCallCnt = nwOutCallCnt;
	}
	public String getFreeSmsCnt() {
		return freeSmsCnt;
	}
	public void setFreeSmsCnt(String freeSmsCnt) {
		this.freeSmsCnt = freeSmsCnt;
	}
	public String getFreeDataCnt() {
		return freeDataCnt;
	}
	public void setFreeDataCnt(String freeDataCnt) {
		this.freeDataCnt = freeDataCnt;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSearchGb() {
		return searchGb;
	}
	public void setSearchGb(String searchGb) {
		this.searchGb = searchGb;
	}
	public String getSearchVal() {
		return searchVal;
	}
	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
	}
	public String getStdrDt() {
		return stdrDt;
	}
	public void setStdrDt(String stdrDt) {
		this.stdrDt = stdrDt;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getRmk() {
		return rmk;
	}
	public void setRmk(String rmk) {
		this.rmk = rmk;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getAgrmTrm() {
		return agrmTrm;
	}
	public void setAgrmTrm(String agrmTrm) {
		this.agrmTrm = agrmTrm;
	}
	/**
	 * @return the onlineTypeCd
	 */
	public String getOnlineTypeCd() {
		return onlineTypeCd;
	}
	/**
	 * @param onlineTypeCd the onlineTypeCd to set
	 */
	public void setOnlineTypeCd(String onlineTypeCd) {
		this.onlineTypeCd = onlineTypeCd;
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
	 * @return the sprtTp
	 */
	public String getSprtTp() {
		return sprtTp;
	}
	/**
	 * @param sprtTp the sprtTp to set
	 */
	public void setSprtTp(String sprtTp) {
		this.sprtTp = sprtTp;
	}
	/**
	 * @return the applStrtDttm
	 */
	public String getApplStrtDttm() {
		return applStrtDttm;
	}
	/**
	 * @param applStrtDttm the applStrtDttm to set
	 */
	public void setApplStrtDttm(String applStrtDttm) {
		this.applStrtDttm = applStrtDttm;
	}
	/**
	 * @return the applEndDttm
	 */
	public String getApplEndDttm() {
		return applEndDttm;
	}
	/**
	 * @param applEndDttm the applEndDttm to set
	 */
	public void setApplEndDttm(String applEndDttm) {
		this.applEndDttm = applEndDttm;
	}
	/**
	 * @return the tmAmt
	 */
	public String getTmAmt() {
		return tmAmt;
	}
	/**
	 * @param tmAmt the tmAmt to set
	 */
	public void setTmAmt(String tmAmt) {
		this.tmAmt = tmAmt;
	}
	/**
	 * @return the rtAmt
	 */
	public String getRtAmt() {
		return rtAmt;
	}
	/**
	 * @param rtAmt the rtAmt to set
	 */
	public void setRtAmt(String rtAmt) {
		this.rtAmt = rtAmt;
	}
	/**
	 * @return the spAmt
	 */
	public String getSpAmt() {
		return spAmt;
	}
	/**
	 * @param spAmt the spAmt to set
	 */
	public void setSpAmt(String spAmt) {
		this.spAmt = spAmt;
	}
	
	public String getCmnt() {
		return cmnt;
	}
	public void setCmnt(String cmnt) {
		this.cmnt = cmnt;
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
	
	
	public String getCcAmt12() {
		return ccAmt12;
	}
	public void setCcAmt12(String ccAmt12) {
		this.ccAmt12 = ccAmt12;
	}
	public String getCcAmt18() {
		return ccAmt18;
	}
	public void setCcAmt18(String ccAmt18) {
		this.ccAmt18 = ccAmt18;
	}
	public String getTmAmt12() {
		return tmAmt12;
	}
	public void setTmAmt12(String tmAmt12) {
		this.tmAmt12 = tmAmt12;
	}
	public String getTmAmt18() {
		return tmAmt18;
	}
	public void setTmAmt18(String tmAmt18) {
		this.tmAmt18 = tmAmt18;
	}
	public String getRtAmt12() {
		return rtAmt12;
	}
	public void setRtAmt12(String rtAmt12) {
		this.rtAmt12 = rtAmt12;
	}
	public String getRtAmt18() {
		return rtAmt18;
	}
	public void setRtAmt18(String rtAmt18) {
		this.rtAmt18 = rtAmt18;
	}
	public String getSpAmt12() {
		return spAmt12;
	}
	public void setSpAmt12(String spAmt12) {
		this.spAmt12 = spAmt12;
	}
	public String getSpAmt18() {
		return spAmt18;
	}
	public void setSpAmt18(String spAmt18) {
		this.spAmt18 = spAmt18;
	}
	public String getSttcYn() {
		return sttcYn;
	}
	public void setSttcYn(String sttcYn) {
		this.sttcYn = sttcYn;
	}
	public String getOnlineCanYn() {
		return onlineCanYn;
	}
	public void setOnlineCanYn(String onlineCanYn) {
		this.onlineCanYn = onlineCanYn;
	}
	public String getCanCmnt() {
		return canCmnt;
	}
	public void setCanCmnt(String canCmnt) {
		this.canCmnt = canCmnt;
	}
	public String getServiceTypeNm() {
		return serviceTypeNm;
	}
	public void setServiceTypeNm(String serviceTypeNm) {
		this.serviceTypeNm = serviceTypeNm;
	}
	public String getPayClCdNm() {
		return payClCdNm;
	}
	public void setPayClCdNm(String payClCdNm) {
		this.payClCdNm = payClCdNm;
	}
	public String getRateTypeNm() {
		return rateTypeNm;
	}
	public void setRateTypeNm(String rateTypeNm) {
		this.rateTypeNm = rateTypeNm;
	}
	public String getOnlineTypeNm() {
		return onlineTypeNm;
	}
	public void setOnlineTypeNm(String onlineTypeNm) {
		this.onlineTypeNm = onlineTypeNm;
	}
	public String getSvcRelTp() {
		return svcRelTp;
	}
	public void setSvcRelTp(String svcRelTp) {
		this.svcRelTp = svcRelTp;
	}
	public String getAddSvcCd() {
		return addSvcCd;
	}
	public void setAddSvcCd(String addSvcCd) {
		this.addSvcCd = addSvcCd;
	}
	public String getMstRateGrpCd() {
		return mstRateGrpCd;
	}
	public void setMstRateGrpCd(String mstRateGrpCd) {
		this.mstRateGrpCd = mstRateGrpCd;
	}
	public String getMstRateGrpNm() {
		return mstRateGrpNm;
	}
	public void setMstRateGrpNm(String mstRateGrpNm) {
		this.mstRateGrpNm = mstRateGrpNm;
	}
	public String getSubRateGrpCd() {
		return subRateGrpCd;
	}
	public void setSubRateGrpCd(String subRateGrpCd) {
		this.subRateGrpCd = subRateGrpCd;
	}
	public String getRateGrpTypeCd() {
		return rateGrpTypeCd;
	}
	public void setRateGrpTypeCd(String rateGrpTypeCd) {
		this.rateGrpTypeCd = rateGrpTypeCd;
	}
	public String getPopupTypeCd() {
		return popupTypeCd;
	}
	public void setPopupTypeCd(String popupTypeCd) {
		this.popupTypeCd = popupTypeCd;
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
	public String getUsrNm() {
		return usrNm;
	}
	public void setUsrNm(String usrNm) {
		this.usrNm = usrNm;
	}
	public String getUsrDttm() {
		return usrDttm;
	}
	public void setUsrDttm(String usrDttm) {
		this.usrDttm = usrDttm;
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
	public String getAppStrDt() {
		return appStrDt;
	}
	public void setAppStrDt(String appStrDt) {
		this.appStrDt = appStrDt;
	}
	public String getAppEndDt() {
		return appEndDt;
	}
	public void setAppEndDt(String appEndDt) {
		this.appEndDt = appEndDt;
	}
	public String getRegNm() {
		return regNm;
	}
	public void setRegNm(String regNm) {
		this.regNm = regNm;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getRvisnNm() {
		return rvisnNm;
	}
	public void setRvisnNm(String rvisnNm) {
		this.rvisnNm = rvisnNm;
	}
	public String getRvisnDt() {
		return rvisnDt;
	}
	public void setRvisnDt(String rvisnDt) {
		this.rvisnDt = rvisnDt;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getRateDivCd() {
		return rateDivCd;
	}

	public void setRateDivCd(String rateDivCd) {
		this.rateDivCd = rateDivCd;
	}

	public String getBaseVatAmt() {
		return baseVatAmt;
	}

	public void setBaseVatAmt(String baseVatAmt) {
		this.baseVatAmt = baseVatAmt;
	}

	public String getPrmtVatAmt() {
		return prmtVatAmt;
	}

	public void setPrmtVatAmt(String prmtVatAmt) {
		this.prmtVatAmt = prmtVatAmt;
	}

	public String getCallCnt() {
		return callCnt;
	}

	public void setCallCnt(String callCnt) {
		this.callCnt = callCnt;
	}

	public String getCallUnit() {
		return callUnit;
	}

	public void setCallUnit(String callUnit) {
		this.callUnit = callUnit;
	}

	public String getPrmtCallCnt() {
		return prmtCallCnt;
	}

	public void setPrmtCallCnt(String prmtCallCnt) {
		this.prmtCallCnt = prmtCallCnt;
	}

	public String getPrmtCallUnit() {
		return prmtCallUnit;
	}

	public void setPrmtCallUnit(String prmtCallUnit) {
		this.prmtCallUnit = prmtCallUnit;
	}

	public String getEtcCallCnt() {
		return etcCallCnt;
	}

	public void setEtcCallCnt(String etcCallCnt) {
		this.etcCallCnt = etcCallCnt;
	}

	public String getEtcCallUnit() {
		return etcCallUnit;
	}

	public void setEtcCallUnit(String etcCallUnit) {
		this.etcCallUnit = etcCallUnit;
	}

	public String getSmsCnt() {
		return smsCnt;
	}

	public void setSmsCnt(String smsCnt) {
		this.smsCnt = smsCnt;
	}

	public String getSmsUnit() {
		return smsUnit;
	}

	public void setSmsUnit(String smsUnit) {
		this.smsUnit = smsUnit;
	}

	public String getPrmtSmsCnt() {
		return prmtSmsCnt;
	}

	public void setPrmtSmsCnt(String prmtSmsCnt) {
		this.prmtSmsCnt = prmtSmsCnt;
	}

	public String getPrmtSmsUnit() {
		return prmtSmsUnit;
	}

	public void setPrmtSmsUnit(String prmtSmsUnit) {
		this.prmtSmsUnit = prmtSmsUnit;
	}

	public String getDataCnt() {
		return dataCnt;
	}

	public void setDataCnt(String dataCnt) {
		this.dataCnt = dataCnt;
	}

	public String getDataUnit() {
		return dataUnit;
	}

	public void setDataUnit(String dataUnit) {
		this.dataUnit = dataUnit;
	}

	public String getPrmtDataCnt() {
		return prmtDataCnt;
	}

	public void setPrmtDataCnt(String prmtDataCnt) {
		this.prmtDataCnt = prmtDataCnt;
	}

	public String getPrmtDataUnit() {
		return prmtDataUnit;
	}

	public void setPrmtDataUnit(String prmtDataUnit) {
		this.prmtDataUnit = prmtDataUnit;
	}

	public String getDayDataCnt() {
		return dayDataCnt;
	}

	public void setDayDataCnt(String dayDataCnt) {
		this.dayDataCnt = dayDataCnt;
	}

	public String getDayDataUnit() {
		return dayDataUnit;
	}

	public void setDayDataUnit(String dayDataUnit) {
		this.dayDataUnit = dayDataUnit;
	}

	public String getQosDataCntDesc() {
		return qosDataCntDesc;
	}

	public void setQosDataCntDesc(String qosDataCntDesc) {
		this.qosDataCntDesc = qosDataCntDesc;
	}

	public String getQosDataUnit() {
		return qosDataUnit;
	}

	public void setQosDataUnit(String qosDataUnit) {
		this.qosDataUnit = qosDataUnit;
	}

	public String getPstngStartDate() {
		return pstngStartDate;
	}

	public void setPstngStartDate(String pstngStartDate) {
		this.pstngStartDate = pstngStartDate;
	}

	public String getStartTm() {
		return startTm;
	}

	public void setStartTm(String startTm) {
		this.startTm = startTm;
	}

	public String getPstngEndDate() {
		return pstngEndDate;
	}
	public String getEndTm() {
		return endTm;
	}

	public void setEndTm(String endTm) {
		this.endTm = endTm;
	}

	public String getEndTmMod() {
		return endTmMod;
	}

	public void setEndTmMod(String endTmMod) {
		this.endTmMod = endTmMod;
	}

	public void setPstngEndDate(String pstngEndDate) {
		this.pstngEndDate = pstngEndDate;
	}

	public String getPstngEndDateMod() {
		return pstngEndDateMod;
	}
	public void setPstngEndDateMod(String pstngEndDateMod) {
		this.pstngEndDateMod = pstngEndDateMod;
	}

	public String getCretId() {
		return cretId;
	}

	public void setCretId(String cretId) {
		this.cretId = cretId;
	}

	public String getCretNm() {
		return cretNm;
	}

	public void setCretNm(String cretNm) {
		this.cretNm = cretNm;
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

	public String getAmdNm() {
		return amdNm;
	}

	public void setAmdNm(String amdNm) {
		this.amdNm = amdNm;
	}

	public String getAmdDt() {
		return amdDt;
	}

	public void setAmdDt(String amdDt) {
		this.amdDt = amdDt;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public List<RateMgmtVO> getItems() {
		return items;
	}

	public void setItems(List<RateMgmtVO> items) {
		this.items = items;
	}

	public String getNewestYn() {
		return newestYn;
	}

	public void setNewestYn(String newestYn) {
		this.newestYn = newestYn;
	}
	
	public String getRemindYn() {
		return remindYn;
	}
	
	public void setRemindYn(String remindYn) {
		this.remindYn = remindYn;
	}
	
	public String getRemindProdType() {
		return remindProdType;
	}
	
	public void setRemindProdType(String remindProdType) {
		this.remindProdType = remindProdType;
	}

	public String getJehuProdType() {
		return jehuProdType;
	}

	public void setJehuProdType(String jehuProdType) {
		this.jehuProdType = jehuProdType;
	}
	public String getOnlineCanDay() {
		return onlineCanDay;
	}
	public void setOnlineCanDay(String onlineCanDay) {
		this.onlineCanDay = onlineCanDay;
	}

}
