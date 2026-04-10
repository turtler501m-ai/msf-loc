package com.ktis.msp.pps.hdofccustmgmt.vo;

import java.io.Serializable;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PpsKtJuoSubVo
 * @Description : 고객정보 VO (계약정보VO)
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="ppsKtJuoSubVo")
public class PpsKtJuoSubVo extends BaseVo  implements Serializable {
	
	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;
	
	public static final String TABLE = "PPS_KT_JUO_SUB";

	/**
	 * 서비스 계약 번호 (KEY 값):number(9) <Primary Key>
	 */
	private int contractNum;

	/**
	 * 청구계정번호:number(9)
	 */
	private String customerBan;

	/**
	 * 고객번호:number(9)
	 */
	private int customerId;

	/**
	 * 전화번호-암호화:varchar2(50)
	 */
	private String subscriberNo;

	/**
	 * 상태 코드 A(사용) / S(정지) /C(해지):varchar2(1)
	 */
	private String subStatus;
	
	/**
	 * 상태코드명
	 */
	private String subStatusNm;

	/**
	 * 상태 변경 일시:date(0)
	 */
	private String subStatusDate;

	/**
	 * 상태변경사유코드:varchar2(4)
	 */
	private String subStatusRsnCode;
	
	/**
	 * 상태변경사유코드명
	 */
	private String subStatusRsnCodeNm;

	/**
	 * 주민등록번호-암호화:varchar2(50)
	 */
	private String userSsn;

	/**
	 * CTN 실 사용자 주소:varchar2(6)
	 */
	private String subAdrZip;

	/**
	 * CTN 실 사용자 주소:varchar2(100)
	 */
	private String subAdrPrimaryLn;

	/**
	 * CTN 실 사용자 주소:varchar2(100)
	 */
	private String subAdrSecondaryLn;

	/**
	 * 실사용자명:varchar2(60)
	 */
	private String subLinkName;

	/**
	 * 핸드폰 모델명:varchar2(30)
	 */
	private String modelName;

	/**
	 * 모델 아이디:number(4)
	 */
	private String modelId;

	/**
	 * NCN(K-HUB에서 사용 되는 Key값):varchar2(20)
	 */
	private String ncn;

	/**
	 * 고객 등급 1 WHITE 2 YELLOW 3 BLUE 4 다이아몬드1 5 VIP4 6 다이아몬드2 7 VIP5 8 기타1 9 기타2 A VIP3 B VIP2 C RED D BLACK E 골드+:char(1)
	 */
	private String ncnGrade;

	/**
	 * 단말기 IMSI:varchar2(15)
	 */
	private String imsi;

	/**
	 * ICCID(USIM ICCID):varchar2(19)
	 */
	private String iccId;

	/**
	 * 2G/3G 여부:char(1)
	 */
	private String g3Ind;

	/**
	 * 개통 대리점 아이디:varchar2(7)
	 */
	private String dealerCode;

	/**
	 * 최종 서비스계약 개통일자:date(0)
	 */
	private String lstComActvDate;

	/**
	 * 최종 단말기 변경 일시:date(0)
	 */
	private String lstDevChgDate;

	/**
	 * 지능망 잔액 상태 (Y잔액 있음, N잔액 없음):char(1)
	 */
	private String itnetBalYn;

	/**
	 * 기기 IMEI 값:varchar2(16)
	 */
	private String imei;

	/**
	 * 상태 변경 업무 처리 코드:varchar2(3)
	 */
	private String subStatusLastActvCd;

	/**
	 * 판매회사코드(KIS):varchar2(3)
	 */
	private String slsCmpnCd;

	/**
	 * 단말기기 일련번호:varchar2(19)
	 */
	private String intmSrlNo;

	/**
	 * Wibro단말 imei:varchar2(19)
	 */
	private String wibroImei;

	/**
	 * Wibro단말 mac:varchar2(12)
	 */
	private String wibroMacId;

	/**
	 * 통합usim id:varchar2(16)
	 */
	private String usimFxdId;

	/**
	 * Wifi mac:varchar2(12)
	 */
	private String wifiMacId;

	/**
	 * 계약용도코드:varchar2(2)
	 */
	private String cntrUseCd;

	/**
	 * 서비스계약번호(명의변경후):number(9)
	 */
	private int svcCntrNo;

	/**
	 * 실사용 CUSTOMER_ID:number(9)
	 */
	private int realUseCustNo;

	/**
	 * 온라인예약가입 KEY:number(9)
	 */
	private int srlIfId;

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
	 * PPS_CUSTOMER 개통대리점 :varchar2(20)
	 */
	private String agentId;
	
	/**
	 * 개통대리점명
	 */
	private String agentName;
	
	/**
	 * 신규/이동 구분
	 */
	private String mnpIndCd;
	
	/**
	 * 신규/이동 구분명
	 */
	private String mnpIndCdNm;
	

	/**
	 * 마스킹 정보
	 */
	private String subscriberNoMsk;
	private String userSsnMsk;
	private String subAdrSecondaryLnMsk;
	private String subLinkNameMsk;
	private String iccIdMsk;
	private String intmSrlNoMsk;
		
	public String getSubscriberNoMsk() {
		return subscriberNoMsk;
	}

	public void setSubscriberNoMsk(String subscriberNoMsk) {
		this.subscriberNoMsk = subscriberNoMsk;
	}

	public String getUserSsnMsk() {
		return userSsnMsk;
	}

	public void setUserSsnMsk(String userSsnMsk) {
		this.userSsnMsk = userSsnMsk;
	}

	public String getSubAdrSecondaryLnMsk() {
		return subAdrSecondaryLnMsk;
	}

	public void setSubAdrSecondaryLnMsk(String subAdrSecondaryLnMsk) {
		this.subAdrSecondaryLnMsk = subAdrSecondaryLnMsk;
	}

	public String getSubLinkNameMsk() {
		return subLinkNameMsk;
	}

	public void setSubLinkNameMsk(String subLinkNameMsk) {
		this.subLinkNameMsk = subLinkNameMsk;
	}

	public String getIccIdMsk() {
		return iccIdMsk;
	}

	public void setIccIdMsk(String iccIdMsk) {
		this.iccIdMsk = iccIdMsk;
	}

	public String getIntmSrlNoMsk() {
		return intmSrlNoMsk;
	}

	public void setIntmSrlNoMsk(String intmSrlNoMsk) {
		this.intmSrlNoMsk = intmSrlNoMsk;
	}



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
	 * @return the customerBan
	 */
	public String getCustomerBan() {
		return customerBan;
	}



	/**
	 * @param customerBan the customerBan to set
	 */
	public void setCustomerBan(String customerBan) {
		this.customerBan = customerBan;
	}



	/**
	 * @return the customerId
	 */
	public int getCustomerId() {
		return customerId;
	}



	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}



	/**
	 * @return the subscriberNo
	 */
	public String getSubscriberNo() {
		return subscriberNo;
	}



	/**
	 * @param subscriberNo the subscriberNo to set
	 */
	public void setSubscriberNo(String subscriberNo) {
		this.subscriberNo = subscriberNo;
	}



	/**
	 * @return the subStatus
	 */
	public String getSubStatus() {
		return subStatus;
	}



	/**
	 * @param subStatus the subStatus to set
	 */
	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}



	/**
	 * @return the subStatusNm
	 */
	public String getSubStatusNm() {
		return subStatusNm;
	}



	/**
	 * @param subStatusNm the subStatusNm to set
	 */
	public void setSubStatusNm(String subStatusNm) {
		this.subStatusNm = subStatusNm;
	}



	/**
	 * @return the subStatusDate
	 */
	public String getSubStatusDate() {
		return subStatusDate;
	}



	/**
	 * @param subStatusDate the subStatusDate to set
	 */
	public void setSubStatusDate(String subStatusDate) {
		this.subStatusDate = subStatusDate;
	}



	/**
	 * @return the subStatusRsnCode
	 */
	public String getSubStatusRsnCode() {
		return subStatusRsnCode;
	}



	/**
	 * @param subStatusRsnCode the subStatusRsnCode to set
	 */
	public void setSubStatusRsnCode(String subStatusRsnCode) {
		this.subStatusRsnCode = subStatusRsnCode;
	}



	/**
	 * @return the subStatusRsnCodeNm
	 */
	public String getSubStatusRsnCodeNm() {
		return subStatusRsnCodeNm;
	}



	/**
	 * @param subStatusRsnCodeNm the subStatusRsnCodeNm to set
	 */
	public void setSubStatusRsnCodeNm(String subStatusRsnCodeNm) {
		this.subStatusRsnCodeNm = subStatusRsnCodeNm;
	}



	/**
	 * @return the userSsn
	 */
	public String getUserSsn() {
		return userSsn;
	}



	/**
	 * @param userSsn the userSsn to set
	 */
	public void setUserSsn(String userSsn) {
		this.userSsn = userSsn;
	}



	/**
	 * @return the subAdrZip
	 */
	public String getSubAdrZip() {
		return subAdrZip;
	}



	/**
	 * @param subAdrZip the subAdrZip to set
	 */
	public void setSubAdrZip(String subAdrZip) {
		this.subAdrZip = subAdrZip;
	}



	/**
	 * @return the subAdrPrimaryLn
	 */
	public String getSubAdrPrimaryLn() {
		return subAdrPrimaryLn;
	}



	/**
	 * @param subAdrPrimaryLn the subAdrPrimaryLn to set
	 */
	public void setSubAdrPrimaryLn(String subAdrPrimaryLn) {
		this.subAdrPrimaryLn = subAdrPrimaryLn;
	}



	/**
	 * @return the subAdrSecondaryLn
	 */
	public String getSubAdrSecondaryLn() {
		return subAdrSecondaryLn;
	}



	/**
	 * @param subAdrSecondaryLn the subAdrSecondaryLn to set
	 */
	public void setSubAdrSecondaryLn(String subAdrSecondaryLn) {
		this.subAdrSecondaryLn = subAdrSecondaryLn;
	}



	/**
	 * @return the subLinkName
	 */
	public String getSubLinkName() {
		return subLinkName;
	}



	/**
	 * @param subLinkName the subLinkName to set
	 */
	public void setSubLinkName(String subLinkName) {
		this.subLinkName = subLinkName;
	}



	/**
	 * @return the modelName
	 */
	public String getModelName() {
		return modelName;
	}



	/**
	 * @param modelName the modelName to set
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}



	/**
	 * @return the modelId
	 */
	public String getModelId() {
		return modelId;
	}



	/**
	 * @param modelId the modelId to set
	 */
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}



	/**
	 * @return the ncn
	 */
	public String getNcn() {
		return ncn;
	}



	/**
	 * @param ncn the ncn to set
	 */
	public void setNcn(String ncn) {
		this.ncn = ncn;
	}



	/**
	 * @return the ncnGrade
	 */
	public String getNcnGrade() {
		return ncnGrade;
	}



	/**
	 * @param ncnGrade the ncnGrade to set
	 */
	public void setNcnGrade(String ncnGrade) {
		this.ncnGrade = ncnGrade;
	}



	/**
	 * @return the imsi
	 */
	public String getImsi() {
		return imsi;
	}



	/**
	 * @param imsi the imsi to set
	 */
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}



	/**
	 * @return the iccId
	 */
	public String getIccId() {
		return iccId;
	}



	/**
	 * @param iccId the iccId to set
	 */
	public void setIccId(String iccId) {
		this.iccId = iccId;
	}



	/**
	 * @return the g3Ind
	 */
	public String getG3Ind() {
		return g3Ind;
	}



	/**
	 * @param g3Ind the g3Ind to set
	 */
	public void setG3Ind(String g3Ind) {
		this.g3Ind = g3Ind;
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
	 * @return the lstComActvDate
	 */
	public String getLstComActvDate() {
		return lstComActvDate;
	}



	/**
	 * @param lstComActvDate the lstComActvDate to set
	 */
	public void setLstComActvDate(String lstComActvDate) {
		this.lstComActvDate = lstComActvDate;
	}



	/**
	 * @return the lstDevChgDate
	 */
	public String getLstDevChgDate() {
		return lstDevChgDate;
	}



	/**
	 * @param lstDevChgDate the lstDevChgDate to set
	 */
	public void setLstDevChgDate(String lstDevChgDate) {
		this.lstDevChgDate = lstDevChgDate;
	}



	/**
	 * @return the itnetBalYn
	 */
	public String getItnetBalYn() {
		return itnetBalYn;
	}



	/**
	 * @param itnetBalYn the itnetBalYn to set
	 */
	public void setItnetBalYn(String itnetBalYn) {
		this.itnetBalYn = itnetBalYn;
	}



	/**
	 * @return the imei
	 */
	public String getImei() {
		return imei;
	}



	/**
	 * @param imei the imei to set
	 */
	public void setImei(String imei) {
		this.imei = imei;
	}



	/**
	 * @return the subStatusLastActvCd
	 */
	public String getSubStatusLastActvCd() {
		return subStatusLastActvCd;
	}



	/**
	 * @param subStatusLastActvCd the subStatusLastActvCd to set
	 */
	public void setSubStatusLastActvCd(String subStatusLastActvCd) {
		this.subStatusLastActvCd = subStatusLastActvCd;
	}



	/**
	 * @return the slsCmpnCd
	 */
	public String getSlsCmpnCd() {
		return slsCmpnCd;
	}



	/**
	 * @param slsCmpnCd the slsCmpnCd to set
	 */
	public void setSlsCmpnCd(String slsCmpnCd) {
		this.slsCmpnCd = slsCmpnCd;
	}



	/**
	 * @return the intmSrlNo
	 */
	public String getIntmSrlNo() {
		return intmSrlNo;
	}



	/**
	 * @param intmSrlNo the intmSrlNo to set
	 */
	public void setIntmSrlNo(String intmSrlNo) {
		this.intmSrlNo = intmSrlNo;
	}



	/**
	 * @return the wibroImei
	 */
	public String getWibroImei() {
		return wibroImei;
	}



	/**
	 * @param wibroImei the wibroImei to set
	 */
	public void setWibroImei(String wibroImei) {
		this.wibroImei = wibroImei;
	}



	/**
	 * @return the wibroMacId
	 */
	public String getWibroMacId() {
		return wibroMacId;
	}



	/**
	 * @param wibroMacId the wibroMacId to set
	 */
	public void setWibroMacId(String wibroMacId) {
		this.wibroMacId = wibroMacId;
	}



	/**
	 * @return the usimFxdId
	 */
	public String getUsimFxdId() {
		return usimFxdId;
	}



	/**
	 * @param usimFxdId the usimFxdId to set
	 */
	public void setUsimFxdId(String usimFxdId) {
		this.usimFxdId = usimFxdId;
	}



	/**
	 * @return the wifiMacId
	 */
	public String getWifiMacId() {
		return wifiMacId;
	}



	/**
	 * @param wifiMacId the wifiMacId to set
	 */
	public void setWifiMacId(String wifiMacId) {
		this.wifiMacId = wifiMacId;
	}



	/**
	 * @return the cntrUseCd
	 */
	public String getCntrUseCd() {
		return cntrUseCd;
	}



	/**
	 * @param cntrUseCd the cntrUseCd to set
	 */
	public void setCntrUseCd(String cntrUseCd) {
		this.cntrUseCd = cntrUseCd;
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
	 * @return the realUseCustNo
	 */
	public int getRealUseCustNo() {
		return realUseCustNo;
	}



	/**
	 * @param realUseCustNo the realUseCustNo to set
	 */
	public void setRealUseCustNo(int realUseCustNo) {
		this.realUseCustNo = realUseCustNo;
	}



	/**
	 * @return the srlIfId
	 */
	public int getSrlIfId() {
		return srlIfId;
	}



	/**
	 * @param srlIfId the srlIfId to set
	 */
	public void setSrlIfId(int srlIfId) {
		this.srlIfId = srlIfId;
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



	/**
	 * @return the agentId
	 */
	public String getAgentId() {
		return agentId;
	}



	/**
	 * @param agentId the agentId to set
	 */
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}



	/**
	 * @return the agentName
	 */
	public String getAgentName() {
		return agentName;
	}



	/**
	 * @param agentName the agentName to set
	 */
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	
	/**
	 * @return the mnpIndCd
	 */
	public String getMnpIndCd() {
		return mnpIndCd;
	}



	/**
	 * @param mnpIndCd the mnpIndCd to set
	 */
	public void setMnpIndCd(String mnpIndCd) {
		this.mnpIndCd = mnpIndCd;
	}
	
	/**
	 * @return the mnpIndCdNm
	 */
	public String getMnpIndCdNm() {
		return mnpIndCdNm;
	}



	/**
	 * @param mnpIndCdNm the mnpIndCdNm to set
	 */
	public void setMnpIndCdNm(String mnpIndCdNm) {
		this.mnpIndCdNm = mnpIndCdNm;
	}



	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	

}
