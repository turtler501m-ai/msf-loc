package com.ktis.msp.pps.hdofccustmgmt.vo;
import java.io.Serializable;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PpsCustomerDiaryVo
 * @Description : 고객정보 VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="ppsCustomerVo")
public class PpsCustomerVo extends BaseVo  implements Serializable {

	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;

	public static final String TABLE = "PPS_CUSTOMER";

	/**
	 * JUO_SUB_INFO의 PK, JUO_SUB_INFO와 1:1 매칭다른 사업자의 Service_Account와 동일 :number(9) <Primary Key>
	 */
	private String contractNum;

	/**
	 * 기본요금제 코드, Service_INFO Table에 코드 정의 :varchar2(9)
	 */
	private String soc;

	/**
	 * 요금제명
	 */
	private String socName;

	/**
	 * PP=선불, PO=후불 :varchar2(2)
	 */
	private String serviceType;
	/**
	 * 서비스구분명
	 */
	private String serviceTypeName;

	/**
	 * 등록일 :date(0)
	 */
	private String enterDate;

	/**
	 * 마지막 정지일자 :date(0)
	 */
	private String statusStopDt;

	/**
	 * JUO_SUB.SUB_STATUS_RSN_CODE 코드값과 동일:varchar2(5)
	 */
	private String statusStopCd;

	private String statusStopNm;

	/**
	 * 마지막 해지일자 (해지 복구도 될수 있음) :date(0)
	 */
	private String statusCancelDt;

	/**
	 * JUO_SUB.SUB_STATUS_RSN_CODE 코드값과 동일:varchar2(5)
	 */
	private String statusCancelCd;

	private String statusCancelNm;

	/**
	 * 기본 잔액 :number(10,2)
	 */
	private double basicRemains;

	/**
	 * 기본 잔액 유효기간 :date(0)
	 */
	private String basicExpire;

	/**
	 * 마지막 잔액 소진일자 :date(0)
	 */
	private String basicEmptDt;

	/**
	 * 음성 요율 :varchar2(10)
	 */
	private String voiceRate;

	/**
	 * 데이타 가입여부( 0= 미가입, 1=가입) :char(1)
	 */
	private String dataReg;

	/**
	 * 데이터 가입여부명
	 */
	private String dataRegNm;

	/**
	 * 데이터 마지막 가입일자 :date(0)
	 */
	private String dataRegDt;

	/**
	 *  데이타 충전 모드 ( 0=수동, 1=자동) :char(1)
	 */
	private String dataMode;
	/**
	 * 데이터 충전모드명
	 */
	private String dataModeNm;

	/**
	 * 데이터 부가서비스 가입 요금제 코드, Service_Data Table 코드 정의 :varchar2(9)
	 */
	private String dataServiceCode;

	/**
	 * 데이터 가입용량 :number(20)
	 */
	private int dataQuota;

	/**
	 * 데이터 가입용량중 남은 용량 :number(20)
	 */
	private int dataQuotaRemains;

	/**
	 * 데이타 부가서비스 잔액 :number(10,2)
	 */
	private double dataVaRemains;

	/**
	 * 데이타요금 가입자  유효기간 :date(0)
	 */
	private String dataExpire;

	/**
	 *  데이타 임계치 (Byte) :number(20)
	 */
	private  String dataLimit;

	/**
	 * 마지막 KT 지능망(IN) 동기화 동작 모드:varchar2(20)
	 */
	private String lastKtInMode;

	/**
	 * 마지막 KT 지능망(IN) 동기화 시간:date(0)
	 */
	private String lastKtSynDt;

	/**
	 * 마지막 기본잔액 충전일자 :date(0)
	 */
	private String lastBasicChgDt;

	/**
	 * 마지막 데이터 충전일자 :date(0)
	 */
	private String lastDataChgDt;

	/**
	 * 포인트 잔액 :number(20,2)
	 */
	private double point;

	/**
	 * 포인트 마지막 갱신일자 :date(0)
	 */
	private String pointLastDt;

	/**
	 * 통계>당월 음성 사용량(초) :number(12,2)
	 */
	private double usLcVoice;

	/**
	 * 통계>당월 문자사용량 (건수) :number(10)
	 */
	private int usLcSms;

	/**
	 * 통계>당월 DATA 사용량(BYTE) :number(20)
	 */
	private double usLcData;

	/**
	 * 통계>당월사용량 마지막 처리일자 :date(0)
	 */
	private String usLcLastDate;

	/**
	 * 통계>누적포인트 :number(20,2)
	 */
	private double totalPoint;

	/**
	 * 통계>누적 기본 충전금액:number(20,2)
	 */
	private double totalBasicChg;

	/**
	 * 통계>누적 기본 충전 횟수:number(10)
	 */
	private int totalBasicCnt;

	/**
	 * 통계>누적 데이터 충전금액:number(20,2)
	 */
	private double totalDataChg;

	/**
	 * 통계>누적 DATA 충전 횟수:number(10)
	 */
	private int totalDataCnt;

	/**
	 * Y=충전가능, N=충전불가 :char(1)
	 */
	private String rechargeFlag;

	/**
	 * 충전가능여부명
	 */
	private String rechargeFlagNm;


	/**
	 * Recharge Flag 변경일자 :date(0)
	 */
	private String rechargeFlagDt;

	/**
	 * 충전 / 가상계좌 조회시 문자를 보낼 폰 :varchar2(20)
	 */
	private String smsPhone;

	/**
	 * Y=문자알림, N=문자알리지 않음(모든문자) :char(1)
	 */
	private String smsFlag;

	/**
	 * 무자알림여부명
	 */
	private String smsFlagNm;

	/**
	 * 렌탈 상태(N:렌탈가능, O:출고, I:입고 ) :char(1)
	 */
	private String rentalStatus;

	/**
	 * 렌탈상태명
	 */
	private String rentalStatusNm;

	/**
	 * 렌탈 일자 :date(0)
	 */
	private String rentalDt;

	/**
	 * 선불 충전용 가상계좌 번호, VIR_ACCOUNT Table에 매핑 :varchar2(20)
	 */
	private String virAccountId;
	
	private String virBankNm; /** 가상계좌은행명 */
	private String virBankCd; /** 가상계좌은행코드 */

	/**
	 * 실시간출금방식구분(0:사용안함, 1:잔액부족시, 2:매월정기일자출금) :char(1)
	 */
	private String cmsChargeType;

	/**
	 * 출금은행코드 :varchar2(10)
	 */
	private String cmsBankCode;
	
	/**
	 * 출금은행코드 :varchar2(10)
	 */
	private String cmsBankCodeNm;

	/**
	 * 계좌번호 (암호화) :varchar2(50)
	 */
	private String cmsAccount;

	/**
	 * 예금주명 :varchar2(30)
	 */
	private String cmsUserName;

	/**
	 * 예금주주민번호 또는 사업자번호(암호화) :varchar2(50)
	 */
	private String cmsUserSsn;

	/**
	 * 청구금액(잔액부족시 출금신청액) :number(8)
	 */
	private int cmsCharge;

	/**
	 * 잔액부족시 임계치금액 (같은경우에도 출금신청됨, 이하처리) :number(8)
	 */
	private int cmsTryRemains;

	/**
	 * 정기출금일자 :varchar2(10)
	 */
	private String cmsChargeDate;

	/**
	 * 정기출금액 :number(8)
	 */
	private int cmsChargeMonth;

	/**
	 * 서류이미지1 :varchar2(100)
	 */
	private String paperImage1;

	/**
	 * 서류이미지2 :varchar2(100)
	 */
	private String paperImage2;

	/**
	 * 서류이미지3 :varchar2(100)
	 */
	private String paperImage3;

	/**
	 * 서류이미지4 :varchar2(100)
	 */
	private String paperImage4;

	/**
	 * 서류이미지5 :varchar2(100)
	 */
	private String paperImage5;
	
	private String paperImg1;
	private String paperImg2;
	private String paperImg3;
	private String paperImg4;
	private String paperImg5;
	
	private String paperImg;
	private String paperImage;

	/**
	 * KTIS내부입력>실사용자이름 :varchar2(60)
	 */
	private String adName;

	/**
	 * KTIS내부입력>실사용자아이디(암호화, 주민/사업자/법인/여권번호..) :varchar2(50)
	 */
	private String adSsn;

	/**
	 * KTIS내부입력>실사용자주소 :varchar2(200)
	 */
	private String adAddress;

	/**
	 * KTIS내부입력>실사용자국적 :varchar2(40)
	 */
	private String adNation;

	private String adNationNm;

	/**
	 * KTIS내부입력>실사용자 ARS 언어코드(KOR, ENG, CHN….) :varchar2(3)
	 */
	private String langCode;

	/**
	 * ARS코드명
	 */
	private String langCodeNm;

	/**
	 * Y=홈페이지 가입, N=미가입 :char(1)
	 */
	private String wwwRegFlag;

	/**
	 * 홈페이지 가입여부명
	 */
	private String wwwRegFlagNm;

	/**
	 * 홈페이지 가입일자 :date(0)
	 */
	private String wwwRegDt;

	/**
	 * 개통대리점 :varchar2(20)
	 */
	private String agentId;

	private String agentNm;

	/**
	 * 개통대리점명
	 */
	private String agentName;

	/**
	 * 최종 개통대리점 변경일자(부여일자) :date(0)
	 */
	private String agentChDt;

	/**
	 * 최종 관리자 작업자 :varchar2(20)
	 */
	private String adminId;

	private String adminNm;

	/**
	 * 최종 관리자 수정일자 :date(0)
	 */
	private String adminChDt;

	/**
	 * 메모… :varchar2(400)
	 */
	private String remark;

	/**
	 * 판매점코드:varchar2(40)
	 */
	private String agentSaleId;

	private String agentSaleNm;

	/**
	 * 판매점등록관리자:varchar2(40)
	 */
	private String agentSaleAdmin;
	private String agentSaleAdminNm;

	/**
	 * 판매점등록일자:date(0)
	 */
	private String agentSaleDate;

	/**
	 *PPS_ KT_JUO_SUB 고객번호:number(9)
	 */
	private int customerId;

	/**
	 * PPS_KT_JUO_SUB 전화번호-암호화:varchar2(50)
	 */
	private String subscriberNo;

	/**
	 *  PPS_KT_JUO_SUB 상태 코드 A(사용) / S(정지) /C(해지):varchar2(1)
	 */
	private String subStatus;

	/**
	 * 상태코드명
	 */
	private String subStatusName;

	/**
	 * PPS_KT_JUO_SUB 고객주민등록번호-암호화:varchar2(50)
	 */
	private String userSsn;


	/**
	 * PPS_KT_JUO_SUB 실사용자명:varchar2(60)
	 */
	private String subLinkName;

	/**
	 * PPS_KT_JUO_SUB 핸드폰 모델명:varchar2(30)
	 */
	private String modelName;

	/**
	 *  PPS_KT_JUO_SUB 모델 아이디:number(4)
	 */
	private String modelId;


	/**
	 *  PPS_KT_JUO_SUB  최종 서비스계약 개통일자:date(0)
	 */
	private String lstComActvDate;
	
	/**
	 *   충전횟수:date(0)
	 */
	private int rechargeCnt;
	
	/**
	 *   총충전금액:date(0)
	 */
	private int rechargeSum;
	
	/**
	 * 생일 
	 */
	private String birthDay;
	
	
	private String basicRemainsStr;
	
	
	private String agntDpRcgFlag;
	
	private String agntDpRcgRemains;
	
	private String agntDpRcgCharge;
	
	private String agntDpRcgCnt;
	
	private String agntDpRcgNowCnt;
	
	private String vizaFlag;
	
	private String stayExpirDt;
	
	private String stayExpirFlag;
	
	private int topupRcgAmt;
	
	private String warFlag;
		
	/**
	 * 마스킹 정보
	 */
	private String cmsAccountMsk;
	private String cmsUserNameMsk;
	private String cmsUserSsnMsk;
	
	private String adNameMsk;
	private String adSsnMsk;
	private String adAddressMsk;
	private String smsPhoneMsk;

	public String getSmsPhoneMsk() {
		return smsPhoneMsk;
	}

	public void setSmsPhoneMsk(String smsPhoneMsk) {
		this.smsPhoneMsk = smsPhoneMsk;
	}

	public String getCmsAccountMsk() {
		return cmsAccountMsk;
	}

	public void setCmsAccountMsk(String cmsAccountMsk) {
		this.cmsAccountMsk = cmsAccountMsk;
	}

	public String getCmsUserNameMsk() {
		return cmsUserNameMsk;
	}

	public void setCmsUserNameMsk(String cmsUserNameMsk) {
		this.cmsUserNameMsk = cmsUserNameMsk;
	}

	public String getCmsUserSsnMsk() {
		return cmsUserSsnMsk;
	}

	public void setCmsUserSsnMsk(String cmsUserSsnMsk) {
		this.cmsUserSsnMsk = cmsUserSsnMsk;
	}

	public String getAdNameMsk() {
		return adNameMsk;
	}

	public void setAdNameMsk(String adNameMsk) {
		this.adNameMsk = adNameMsk;
	}

	public String getAdSsnMsk() {
		return adSsnMsk;
	}

	public void setAdSsnMsk(String adSsnMsk) {
		this.adSsnMsk = adSsnMsk;
	}

	public String getAdAddressMsk() {
		return adAddressMsk;
	}

	public void setAdAddressMsk(String adAddressMsk) {
		this.adAddressMsk = adAddressMsk;
	}



	/**
	 * @return the contractNum
	 */
	public String getContractNum() {
		return contractNum;
	}



	/**
	 * @return the paperImg1
	 */
	public String getPaperImg1() {
		return paperImg1;
	}



	/**
	 * @param paperImg1 the paperImg1 to set
	 */
	public void setPaperImg1(String paperImg1) {
		this.paperImg1 = paperImg1;
	}



	/**
	 * @return the paperImg2
	 */
	public String getPaperImg2() {
		return paperImg2;
	}



	/**
	 * @param paperImg2 the paperImg2 to set
	 */
	public void setPaperImg2(String paperImg2) {
		this.paperImg2 = paperImg2;
	}



	/**
	 * @return the paperImg3
	 */
	public String getPaperImg3() {
		return paperImg3;
	}



	/**
	 * @param paperImg3 the paperImg3 to set
	 */
	public void setPaperImg3(String paperImg3) {
		this.paperImg3 = paperImg3;
	}



	/**
	 * @return the paperImg4
	 */
	public String getPaperImg4() {
		return paperImg4;
	}



	/**
	 * @param paperImg4 the paperImg4 to set
	 */
	public void setPaperImg4(String paperImg4) {
		this.paperImg4 = paperImg4;
	}



	/**
	 * @return the paperImg5
	 */
	public String getPaperImg5() {
		return paperImg5;
	}



	/**
	 * @param paperImg5 the paperImg5 to set
	 */
	public void setPaperImg5(String paperImg5) {
		this.paperImg5 = paperImg5;
	}



	/**
	 * @param contractNum the contractNum to set
	 */
	public void setContractNum(String contractNum) {
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
	 * @return the socName
	 */
	public String getSocName() {
		return socName;
	}



	/**
	 * @param socName the socName to set
	 */
	public void setSocName(String socName) {
		this.socName = socName;
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
	 * @return the serviceTypeName
	 */
	public String getServiceTypeName() {
		return serviceTypeName;
	}



	/**
	 * @param serviceTypeName the serviceTypeName to set
	 */
	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}



	/**
	 * @return the enterDate
	 */
	public String getEnterDate() {
		return enterDate;
	}



	/**
	 * @param enterDate the enterDate to set
	 */
	public void setEnterDate(String enterDate) {
		this.enterDate = enterDate;
	}



	/**
	 * @return the statusStopDt
	 */
	public String getStatusStopDt() {
		return statusStopDt;
	}



	/**
	 * @param statusStopDt the statusStopDt to set
	 */
	public void setStatusStopDt(String statusStopDt) {
		this.statusStopDt = statusStopDt;
	}



	/**
	 * @return the statusStopCd
	 */
	public String getStatusStopCd() {
		return statusStopCd;
	}



	/**
	 * @param statusStopCd the statusStopCd to set
	 */
	public void setStatusStopCd(String statusStopCd) {
		this.statusStopCd = statusStopCd;
	}



	/**
	 * @return the statusCancelDt
	 */
	public String getStatusCancelDt() {
		return statusCancelDt;
	}



	/**
	 * @param statusCancelDt the statusCancelDt to set
	 */
	public void setStatusCancelDt(String statusCancelDt) {
		this.statusCancelDt = statusCancelDt;
	}



	/**
	 * @return the statusCancelCd
	 */
	public String getStatusCancelCd() {
		return statusCancelCd;
	}



	/**
	 * @param statusCancelCd the statusCancelCd to set
	 */
	public void setStatusCancelCd(String statusCancelCd) {
		this.statusCancelCd = statusCancelCd;
	}



	/**
	 * @return the basicRemains
	 */
	public double getBasicRemains() {
		return basicRemains;
	}



	/**
	 * @param basicRemains the basicRemains to set
	 */
	public void setBasicRemains(double basicRemains) {
		this.basicRemains = basicRemains;
	}



	/**
	 * @return the basicExpire
	 */
	public String getBasicExpire() {
		return basicExpire;
	}



	/**
	 * @param basicExpire the basicExpire to set
	 */
	public void setBasicExpire(String basicExpire) {
		this.basicExpire = basicExpire;
	}



	/**
	 * @return the basicEmptDt
	 */
	public String getBasicEmptDt() {
		return basicEmptDt;
	}



	/**
	 * @param basicEmptDt the basicEmptDt to set
	 */
	public void setBasicEmptDt(String basicEmptDt) {
		this.basicEmptDt = basicEmptDt;
	}



	/**
	 * @return the voiceRate
	 */
	public String getVoiceRate() {
		return voiceRate;
	}



	/**
	 * @param voiceRate the voiceRate to set
	 */
	public void setVoiceRate(String voiceRate) {
		this.voiceRate = voiceRate;
	}



	/**
	 * @return the dataReg
	 */
	public String getDataReg() {
		return dataReg;
	}



	/**
	 * @param dataReg the dataReg to set
	 */
	public void setDataReg(String dataReg) {
		this.dataReg = dataReg;
	}



	/**
	 * @return the dataRegDt
	 */
	public String getDataRegDt() {
		return dataRegDt;
	}



	/**
	 * @param dataRegDt the dataRegDt to set
	 */
	public void setDataRegDt(String dataRegDt) {
		this.dataRegDt = dataRegDt;
	}



	/**
	 * @return the dataMode
	 */
	public String getDataMode() {
		return dataMode;
	}



	/**
	 * @param dataMode the dataMode to set
	 */
	public void setDataMode(String dataMode) {
		this.dataMode = dataMode;
	}



	/**
	 * @return the dataServiceCode
	 */
	public String getDataServiceCode() {
		return dataServiceCode;
	}



	/**
	 * @param dataServiceCode the dataServiceCode to set
	 */
	public void setDataServiceCode(String dataServiceCode) {
		this.dataServiceCode = dataServiceCode;
	}



	/**
	 * @return the dataQuota
	 */
	public int getDataQuota() {
		return dataQuota;
	}



	/**
	 * @param dataQuota the dataQuota to set
	 */
	public void setDataQuota(int dataQuota) {
		this.dataQuota = dataQuota;
	}



	/**
	 * @return the dataQuotaRemains
	 */
	public int getDataQuotaRemains() {
		return dataQuotaRemains;
	}



	/**
	 * @param dataQuotaRemains the dataQuotaRemains to set
	 */
	public void setDataQuotaRemains(int dataQuotaRemains) {
		this.dataQuotaRemains = dataQuotaRemains;
	}



	/**
	 * @return the dataVaRemains
	 */
	public double getDataVaRemains() {
		return dataVaRemains;
	}



	/**
	 * @param dataVaRemains the dataVaRemains to set
	 */
	public void setDataVaRemains(double dataVaRemains) {
		this.dataVaRemains = dataVaRemains;
	}



	/**
	 * @return the dataExpire
	 */
	public String getDataExpire() {
		return dataExpire;
	}



	/**
	 * @param dataExpire the dataExpire to set
	 */
	public void setDataExpire(String dataExpire) {
		this.dataExpire = dataExpire;
	}



	/**
	 * @return the dataLimit
	 */
	public String getDataLimit() {
		return dataLimit;
	}



	/**
	 * @param dataLimit the dataLimit to set
	 */
	public void setDataLimit(String dataLimit) {
		this.dataLimit = dataLimit;
	}



	/**
	 * @return the lastKtInMode
	 */
	public String getLastKtInMode() {
		return lastKtInMode;
	}



	/**
	 * @param lastKtInMode the lastKtInMode to set
	 */
	public void setLastKtInMode(String lastKtInMode) {
		this.lastKtInMode = lastKtInMode;
	}



	/**
	 * @return the lastKtSynDt
	 */
	public String getLastKtSynDt() {
		return lastKtSynDt;
	}



	/**
	 * @param lastKtSynDt the lastKtSynDt to set
	 */
	public void setLastKtSynDt(String lastKtSynDt) {
		this.lastKtSynDt = lastKtSynDt;
	}



	/**
	 * @return the lastBasicChgDt
	 */
	public String getLastBasicChgDt() {
		return lastBasicChgDt;
	}



	/**
	 * @param lastBasicChgDt the lastBasicChgDt to set
	 */
	public void setLastBasicChgDt(String lastBasicChgDt) {
		this.lastBasicChgDt = lastBasicChgDt;
	}



	/**
	 * @return the lastDataChgDt
	 */
	public String getLastDataChgDt() {
		return lastDataChgDt;
	}



	/**
	 * @param lastDataChgDt the lastDataChgDt to set
	 */
	public void setLastDataChgDt(String lastDataChgDt) {
		this.lastDataChgDt = lastDataChgDt;
	}



	/**
	 * @return the point
	 */
	public double getPoint() {
		return point;
	}



	/**
	 * @param point the point to set
	 */
	public void setPoint(double point) {
		this.point = point;
	}



	/**
	 * @return the pointLastDt
	 */
	public String getPointLastDt() {
		return pointLastDt;
	}



	/**
	 * @param pointLastDt the pointLastDt to set
	 */
	public void setPointLastDt(String pointLastDt) {
		this.pointLastDt = pointLastDt;
	}



	/**
	 * @return the usLcVoice
	 */
	public double getUsLcVoice() {
		return usLcVoice;
	}



	/**
	 * @param usLcVoice the usLcVoice to set
	 */
	public void setUsLcVoice(double usLcVoice) {
		this.usLcVoice = usLcVoice;
	}



	/**
	 * @return the usLcSms
	 */
	public int getUsLcSms() {
		return usLcSms;
	}



	/**
	 * @param usLcSms the usLcSms to set
	 */
	public void setUsLcSms(int usLcSms) {
		this.usLcSms = usLcSms;
	}



	/**
	 * @return the usLcData
	 */
	public double getUsLcData() {
		return usLcData;
	}



	/**
	 * @param usLcData the usLcData to set
	 */
	public void setUsLcData(double usLcData) {
		this.usLcData = usLcData;
	}



	/**
	 * @return the usLcLastDate
	 */
	public String getUsLcLastDate() {
		return usLcLastDate;
	}



	/**
	 * @param usLcLastDate the usLcLastDate to set
	 */
	public void setUsLcLastDate(String usLcLastDate) {
		this.usLcLastDate = usLcLastDate;
	}



	/**
	 * @return the totalPoint
	 */
	public double getTotalPoint() {
		return totalPoint;
	}



	/**
	 * @param totalPoint the totalPoint to set
	 */
	public void setTotalPoint(double totalPoint) {
		this.totalPoint = totalPoint;
	}



	/**
	 * @return the totalBasicChg
	 */
	public double getTotalBasicChg() {
		return totalBasicChg;
	}



	/**
	 * @param totalBasicChg the totalBasicChg to set
	 */
	public void setTotalBasicChg(double totalBasicChg) {
		this.totalBasicChg = totalBasicChg;
	}



	/**
	 * @return the totalBasicCnt
	 */
	public int getTotalBasicCnt() {
		return totalBasicCnt;
	}



	/**
	 * @param totalBasicCnt the totalBasicCnt to set
	 */
	public void setTotalBasicCnt(int totalBasicCnt) {
		this.totalBasicCnt = totalBasicCnt;
	}



	/**
	 * @return the totalDataChg
	 */
	public double getTotalDataChg() {
		return totalDataChg;
	}



	/**
	 * @param totalDataChg the totalDataChg to set
	 */
	public void setTotalDataChg(double totalDataChg) {
		this.totalDataChg = totalDataChg;
	}



	/**
	 * @return the totalDataCnt
	 */
	public int getTotalDataCnt() {
		return totalDataCnt;
	}



	/**
	 * @param totalDataCnt the totalDataCnt to set
	 */
	public void setTotalDataCnt(int totalDataCnt) {
		this.totalDataCnt = totalDataCnt;
	}



	/**
	 * @return the rechargeFlag
	 */
	public String getRechargeFlag() {
		return rechargeFlag;
	}



	/**
	 * @param rechargeFlag the rechargeFlag to set
	 */
	public void setRechargeFlag(String rechargeFlag) {
		this.rechargeFlag = rechargeFlag;
	}



	/**
	 * @return the rechargeFlagDt
	 */
	public String getRechargeFlagDt() {
		return rechargeFlagDt;
	}



	/**
	 * @param rechargeFlagDt the rechargeFlagDt to set
	 */
	public void setRechargeFlagDt(String rechargeFlagDt) {
		this.rechargeFlagDt = rechargeFlagDt;
	}



	/**
	 * @return the smsPhone
	 */
	public String getSmsPhone() {
		return smsPhone;
	}



	/**
	 * @param smsPhone the smsPhone to set
	 */
	public void setSmsPhone(String smsPhone) {
		this.smsPhone = smsPhone;
	}



	/**
	 * @return the smsFlag
	 */
	public String getSmsFlag() {
		return smsFlag;
	}



	/**
	 * @param smsFlag the smsFlag to set
	 */
	public void setSmsFlag(String smsFlag) {
		this.smsFlag = smsFlag;
	}



	/**
	 * @return the rentalStatus
	 */
	public String getRentalStatus() {
		return rentalStatus;
	}



	/**
	 * @param rentalStatus the rentalStatus to set
	 */
	public void setRentalStatus(String rentalStatus) {
		this.rentalStatus = rentalStatus;
	}



	/**
	 * @return the rentalDt
	 */
	public String getRentalDt() {
		return rentalDt;
	}



	/**
	 * @param rentalDt the rentalDt to set
	 */
	public void setRentalDt(String rentalDt) {
		this.rentalDt = rentalDt;
	}



	/**
	 * @return the virAccountId
	 */
	public String getVirAccountId() {
		return virAccountId;
	}



	/**
	 * @param virAccountId the virAccountId to set
	 */
	public void setVirAccountId(String virAccountId) {
		this.virAccountId = virAccountId;
	}



	/**
	 * @return the cmsChargeType
	 */
	public String getCmsChargeType() {
		return cmsChargeType;
	}



	/**
	 * @param cmsChargeType the cmsChargeType to set
	 */
	public void setCmsChargeType(String cmsChargeType) {
		this.cmsChargeType = cmsChargeType;
	}



	/**
	 * @return the cmsBankCode
	 */
	public String getCmsBankCode() {
		return cmsBankCode;
	}



	/**
	 * @param cmsBankCode the cmsBankCode to set
	 */
	public void setCmsBankCode(String cmsBankCode) {
		this.cmsBankCode = cmsBankCode;
	}
	
	
	/**
	 * @return the cmsBankCodeNm
	 */
	public String getCmsBankCodeNm() {
		return cmsBankCodeNm;
	}



	/**
	 * @param cmsBankCodeNm the cmsBankCodeNm to set
	 */
	public void setCmsBankCodeNm(String cmsBankCodeNm) {
		this.cmsBankCodeNm = cmsBankCodeNm;
	}



	/**
	 * @return the cmsAccount
	 */
	public String getCmsAccount() {
		return cmsAccount;
	}



	/**
	 * @param cmsAccount the cmsAccount to set
	 */
	public void setCmsAccount(String cmsAccount) {
		this.cmsAccount = cmsAccount;
	}



	/**
	 * @return the cmsUserName
	 */
	public String getCmsUserName() {
		return cmsUserName;
	}



	/**
	 * @param cmsUserName the cmsUserName to set
	 */
	public void setCmsUserName(String cmsUserName) {
		this.cmsUserName = cmsUserName;
	}



	/**
	 * @return the cmsUserSsn
	 */
	public String getCmsUserSsn() {
		return cmsUserSsn;
	}



	/**
	 * @param cmsUserSsn the cmsUserSsn to set
	 */
	public void setCmsUserSsn(String cmsUserSsn) {
		this.cmsUserSsn = cmsUserSsn;
	}



	/**
	 * @return the cmsCharge
	 */
	public int getCmsCharge() {
		return cmsCharge;
	}



	/**
	 * @param cmsCharge the cmsCharge to set
	 */
	public void setCmsCharge(int cmsCharge) {
		this.cmsCharge = cmsCharge;
	}



	/**
	 * @return the cmsTryRemains
	 */
	public int getCmsTryRemains() {
		return cmsTryRemains;
	}



	/**
	 * @param cmsTryRemains the cmsTryRemains to set
	 */
	public void setCmsTryRemains(int cmsTryRemains) {
		this.cmsTryRemains = cmsTryRemains;
	}



	/**
	 * @return the cmsChargeDate
	 */
	public String getCmsChargeDate() {
		return cmsChargeDate;
	}



	/**
	 * @param cmsChargeDate the cmsChargeDate to set
	 */
	public void setCmsChargeDate(String cmsChargeDate) {
		this.cmsChargeDate = cmsChargeDate;
	}



	/**
	 * @return the cmsChargeMonth
	 */
	public int getCmsChargeMonth() {
		return cmsChargeMonth;
	}



	/**
	 * @param cmsChargeMonth the cmsChargeMonth to set
	 */
	public void setCmsChargeMonth(int cmsChargeMonth) {
		this.cmsChargeMonth = cmsChargeMonth;
	}



	/**
	 * @return the paperImage1
	 */
	public String getPaperImage1() {
		return paperImage1;
	}



	/**
	 * @param paperImage1 the paperImage1 to set
	 */
	public void setPaperImage1(String paperImage1) {
		this.paperImage1 = paperImage1;
	}



	/**
	 * @return the paperImage2
	 */
	public String getPaperImage2() {
		return paperImage2;
	}



	/**
	 * @param paperImage2 the paperImage2 to set
	 */
	public void setPaperImage2(String paperImage2) {
		this.paperImage2 = paperImage2;
	}



	/**
	 * @return the paperImage3
	 */
	public String getPaperImage3() {
		return paperImage3;
	}



	/**
	 * @param paperImage3 the paperImage3 to set
	 */
	public void setPaperImage3(String paperImage3) {
		this.paperImage3 = paperImage3;
	}



	/**
	 * @return the paperImage4
	 */
	public String getPaperImage4() {
		return paperImage4;
	}



	/**
	 * @param paperImage4 the paperImage4 to set
	 */
	public void setPaperImage4(String paperImage4) {
		this.paperImage4 = paperImage4;
	}



	/**
	 * @return the paperImage5
	 */
	public String getPaperImage5() {
		return paperImage5;
	}



	/**
	 * @param paperImage5 the paperImage5 to set
	 */
	public void setPaperImage5(String paperImage5) {
		this.paperImage5 = paperImage5;
	}



	/**
	 * @return the adName
	 */
	public String getAdName() {
		return adName;
	}



	/**
	 * @param adName the adName to set
	 */
	public void setAdName(String adName) {
		this.adName = adName;
	}



	/**
	 * @return the adSsn
	 */
	public String getAdSsn() {
		return adSsn;
	}



	/**
	 * @param adSsn the adSsn to set
	 */
	public void setAdSsn(String adSsn) {
		this.adSsn = adSsn;
	}



	/**
	 * @return the adAddress
	 */
	public String getAdAddress() {
		return adAddress;
	}



	/**
	 * @param adAddress the adAddress to set
	 */
	public void setAdAddress(String adAddress) {
		this.adAddress = adAddress;
	}



	/**
	 * @return the adNation
	 */
	public String getAdNation() {
		return adNation;
	}



	/**
	 * @param adNation the adNation to set
	 */
	public void setAdNation(String adNation) {
		this.adNation = adNation;
	}



	/**
	 * @return the langCode
	 */
	public String getLangCode() {
		return langCode;
	}



	/**
	 * @param langCode the langCode to set
	 */
	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}



	/**
	 * @return the langCodeNm
	 */
	public String getLangCodeNm() {
		return langCodeNm;
	}



	/**
	 * @param langCodeNm the langCodeNm to set
	 */
	public void setLangCodeNm(String langCodeNm) {
		this.langCodeNm = langCodeNm;
	}



	/**
	 * @return the wwwRegFlag
	 */
	public String getWwwRegFlag() {
		return wwwRegFlag;
	}



	/**
	 * @param wwwRegFlag the wwwRegFlag to set
	 */
	public void setWwwRegFlag(String wwwRegFlag) {
		this.wwwRegFlag = wwwRegFlag;
	}



	/**
	 * @return the wwwRegDt
	 */
	public String getWwwRegDt() {
		return wwwRegDt;
	}



	/**
	 * @param wwwRegDt the wwwRegDt to set
	 */
	public void setWwwRegDt(String wwwRegDt) {
		this.wwwRegDt = wwwRegDt;
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
	 * @return the agentChDt
	 */
	public String getAgentChDt() {
		return agentChDt;
	}



	/**
	 * @param agentChDt the agentChDt to set
	 */
	public void setAgentChDt(String agentChDt) {
		this.agentChDt = agentChDt;
	}



	/**
	 * @return the adminId
	 */
	public String getAdminId() {
		return adminId;
	}



	/**
	 * @param adminId the adminId to set
	 */
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}



	/**
	 * @return the adminChDt
	 */
	public String getAdminChDt() {
		return adminChDt;
	}



	/**
	 * @param adminChDt the adminChDt to set
	 */
	public void setAdminChDt(String adminChDt) {
		this.adminChDt = adminChDt;
	}



	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}



	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}



	/**
	 * @return the agentSaleId
	 */
	public String getAgentSaleId() {
		return agentSaleId;
	}



	/**
	 * @param agentSaleId the agentSaleId to set
	 */
	public void setAgentSaleId(String agentSaleId) {
		this.agentSaleId = agentSaleId;
	}



	/**
	 * @return the agentSaleAdmin
	 */
	public String getAgentSaleAdmin() {
		return agentSaleAdmin;
	}



	/**
	 * @param agentSaleAdmin the agentSaleAdmin to set
	 */
	public void setAgentSaleAdmin(String agentSaleAdmin) {
		this.agentSaleAdmin = agentSaleAdmin;
	}



	/**
	 * @return the agentSaleDate
	 */
	public String getAgentSaleDate() {
		return agentSaleDate;
	}



	/**
	 * @param agentSaleDate the agentSaleDate to set
	 */
	public void setAgentSaleDate(String agentSaleDate) {
		this.agentSaleDate = agentSaleDate;
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
	 * @return the subStatusName
	 */
	public String getSubStatusName() {
		return subStatusName;
	}



	/**
	 * @param subStatusName the subStatusName to set
	 */
	public void setSubStatusName(String subStatusName) {
		this.subStatusName = subStatusName;
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
	 * @return the dataRegNm
	 */
	public String getDataRegNm() {
		return dataRegNm;
	}



	/**
	 * @param dataRegNm the dataRegNm to set
	 */
	public void setDataRegNm(String dataRegNm) {
		this.dataRegNm = dataRegNm;
	}



	/**
	 * @return the dataModeNm
	 */
	public String getDataModeNm() {
		return dataModeNm;
	}



	/**
	 * @param dataModeNm the dataModeNm to set
	 */
	public void setDataModeNm(String dataModeNm) {
		this.dataModeNm = dataModeNm;
	}



	/**
	 * @return the rechargeFlagNm
	 */
	public String getRechargeFlagNm() {
		return rechargeFlagNm;
	}



	/**
	 * @param rechargeFlagNm the rechargeFlagNm to set
	 */
	public void setRechargeFlagNm(String rechargeFlagNm) {
		this.rechargeFlagNm = rechargeFlagNm;
	}



	/**
	 * @return the smsFlagNm
	 */
	public String getSmsFlagNm() {
		return smsFlagNm;
	}



	/**
	 * @param smsFlagNm the smsFlagNm to set
	 */
	public void setSmsFlagNm(String smsFlagNm) {
		this.smsFlagNm = smsFlagNm;
	}



	/**
	 * @return the rentalStatusNm
	 */
	public String getRentalStatusNm() {
		return rentalStatusNm;
	}



	/**
	 * @param rentalStatusNm the rentalStatusNm to set
	 */
	public void setRentalStatusNm(String rentalStatusNm) {
		this.rentalStatusNm = rentalStatusNm;
	}



	/**
	 * @return the wwwRegFlagNm
	 */
	public String getWwwRegFlagNm() {
		return wwwRegFlagNm;
	}



	/**
	 * @param wwwRegFlagNm the wwwRegFlagNm to set
	 */
	public void setWwwRegFlagNm(String wwwRegFlagNm) {
		this.wwwRegFlagNm = wwwRegFlagNm;
	}



	/**
	 * @return adNationNm
	 */
	public String getAdNationNm() {
		return adNationNm;
	}



	/**
	 * @param adNationNm セットする adNationNm
	 */
	public void setAdNationNm(String adNationNm) {
		this.adNationNm = adNationNm;
	}



	/**
	 * @return agentNm
	 */
	public String getAgentNm() {
		return agentNm;
	}



	/**
	 * @param agentNm セットする agentNm
	 */
	public void setAgentNm(String agentNm) {
		this.agentNm = agentNm;
	}



	/**
	 * @return adminNm
	 */
	public String getAdminNm() {
		return adminNm;
	}



	/**
	 * @param adminNm セットする adminNm
	 */
	public void setAdminNm(String adminNm) {
		this.adminNm = adminNm;
	}



	/**
	 * @return agentSaleNm
	 */
	public String getAgentSaleNm() {
		return agentSaleNm;
	}



	/**
	 * @param agentSaleNm セットする agentSaleNm
	 */
	public void setAgentSaleNm(String agentSaleNm) {
		this.agentSaleNm = agentSaleNm;
	}



	/**
	 * @return agentSaleAdminNm
	 */
	public String getAgentSaleAdminNm() {
		return agentSaleAdminNm;
	}



	/**
	 * @param agentSaleAdminNm セットする agentSaleAdminNm
	 */
	public void setAgentSaleAdminNm(String agentSaleAdminNm) {
		this.agentSaleAdminNm = agentSaleAdminNm;
	}



	public int getRechargeCnt() {
		return rechargeCnt;
	}



	public void setRechargeCnt(int rechargeCnt) {
		this.rechargeCnt = rechargeCnt;
	}



	public int getRechargeSum() {
		return rechargeSum;
	}



	public void setRechargeSum(int rechargeSum) {
		this.rechargeSum = rechargeSum;
	}
	
	
	
	/**
	 * @return the statusStopNm
	 */
	public String getStatusStopNm() {
		return statusStopNm;
	}



	/**
	 * @param statusStopNm the statusStopNm to set
	 */
	public void setStatusStopNm(String statusStopNm) {
		this.statusStopNm = statusStopNm;
	}



	/**
	 * @return the statusCancelNm
	 */
	public String getStatusCancelNm() {
		return statusCancelNm;
	}



	/**
	 * @param statusCancelNm the statusCancelNm to set
	 */
	public void setStatusCancelNm(String statusCancelNm) {
		this.statusCancelNm = statusCancelNm;
	}



	/**
	 * @return the birthDay
	 */
	public String getBirthDay() {
		return birthDay;
	}



	/**
	 * @param birthDay the birthDay to set
	 */
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}



	/**
	 * @return the virBankNm
	 */
	public String getVirBankNm() {
		return virBankNm;
	}



	/**
	 * @param virBankNm the virBankNm to set
	 */
	public void setVirBankNm(String virBankNm) {
		this.virBankNm = virBankNm;
	}



	/**
	 * @return the virBankCd
	 */
	public String getVirBankCd() {
		return virBankCd;
	}



	/**
	 * @param virBankCd the virBankCd to set
	 */
	public void setVirBankCd(String virBankCd) {
		this.virBankCd = virBankCd;
	}



	/**
	 * @return the basicRemainsStr
	 */
	public String getBasicRemainsStr() {
		return basicRemainsStr;
	}



	/**
	 * @param basicRemainsStr the basicRemainsStr to set
	 */
	public void setBasicRemainsStr(String basicRemainsStr) {
		this.basicRemainsStr = basicRemainsStr;
	}



	public String getAgntDpRcgFlag() {
		return agntDpRcgFlag;
	}



	public void setAgntDpRcgFlag(String agntDpRcgFlag) {
		this.agntDpRcgFlag = agntDpRcgFlag;
	}



	public String getAgntDpRcgRemains() {
		return agntDpRcgRemains;
	}



	public void setAgntDpRcgRemains(String agntDpRcgRemains) {
		this.agntDpRcgRemains = agntDpRcgRemains;
	}



	public String getAgntDpRcgCharge() {
		return agntDpRcgCharge;
	}



	public void setAgntDpRcgCharge(String agntDpRcgCharge) {
		this.agntDpRcgCharge = agntDpRcgCharge;
	}



	/**
	 * @return the paperImg
	 */
	public String getPaperImg() {
		return paperImg;
	}



	/**
	 * @param paperImg the paperImg to set
	 */
	public void setPaperImg(String paperImg) {
		this.paperImg = paperImg;
	}



	/**
	 * @return the paperImage
	 */
	public String getPaperImage() {
		return paperImage;
	}



	/**
	 * @param paperImage the paperImage to set
	 */
	public void setPaperImage(String paperImage) {
		this.paperImage = paperImage;
	}
	
	



	/**
	 * @return the agntDpRcgCnt
	 */
	public String getAgntDpRcgCnt() {
		return agntDpRcgCnt;
	}



	/**
	 * @param agntDpRcgCnt the agntDpRcgCnt to set
	 */
	public void setAgntDpRcgCnt(String agntDpRcgCnt) {
		this.agntDpRcgCnt = agntDpRcgCnt;
	}
	
	

	

	/**
	 * @return the agntDpRcgNowCnt
	 */
	public String getAgntDpRcgNowCnt() {
		return agntDpRcgNowCnt;
	}



	/**
	 * @param agntDpRcgNowCnt the agntDpRcgNowCnt to set
	 */
	public void setAgntDpRcgNowCnt(String agntDpRcgNowCnt) {
		this.agntDpRcgNowCnt = agntDpRcgNowCnt;
	}
	
	/**
	 * @return the vizaFlag
	 */
	public String getVizaFlag() {
		return vizaFlag;
	}



	/**
	 * @param vizaFlag the vizaFlag to set
	 */
	public void setVizaFlag(String vizaFlag) {
		this.vizaFlag = vizaFlag;
	}
	
	/**
	 * @return the stayExpirDt
	 */
	public String getStayExpirDt() {
		return stayExpirDt;
	}



	/**
	 * @param stayExpirDt the stayExpirDt to set
	 */
	public void setStayExpirDt(String stayExpirDt) {
		this.stayExpirDt = stayExpirDt;
	}
	
	/**
	 * @return the stayExpirFlag
	 */
	public String getStayExpirFlag() {
		return stayExpirFlag;
	}



	/**
	 * @param stayExpirFlag the stayExpirFlag to set
	 */
	public void setStayExpirFlag(String stayExpirFlag) {
		this.stayExpirFlag = stayExpirFlag;
	}
	
	/**
	 * @return the topupRcgAmt
	 */
	public int getTopupRcgAmt() {
		return topupRcgAmt;
	}

	/**
	 * @param topupRcgAmt the topupRcgAmt to set
	 */
	public void setTopupRcgAmt(int topupRcgAmt) {
		this.topupRcgAmt = topupRcgAmt;
	}
	
	/**
	 * @return the warFlag
	 */
	public String getWarFlag() {
		return warFlag;
	}

	/**
	 * @param warFlag the warFlag to set
	 */
	public void setWarFlag(String warFlag) {
		this.warFlag = warFlag;
	}



	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	

}
