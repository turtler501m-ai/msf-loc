package com.ktis.msp.pps.hdofccustmgmt.vo;

import java.io.Serializable;



import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

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
@XmlRootElement(name="ppsKtInResVo")
public class PpsKtInResVo extends BaseVo  implements Serializable {

	private static final long serialVersionUID = 6546990189880359258L;

	public static final String TABLE = "PPS_KT_IN_RES";
	
	/**
	 * pps_req_type 테이블의 req_type
	 */
	private String gubun;
	
	/**
	 * 입력>충전금액( +, - 금액가능) 또는 설정금액 (잔액 설정금액), 최대 8자리
	 */
	private int amount;
	
	/**
	 * CHG_CASH_REFILL= 현금충전,REMAINS_QUERY=잔액조회,QUERY = 데이타상품조회,CS_CHG_REMAINS = 잔액조정/데이타량조정,REFILL_DATA_SS = 데이타상품 충전,CANCEL_DATA_SS = 데이타 상품 충전 취소,UPDATE_GEN = 데이타상품 설정변경
	 */
	private String ktMode;
	
	/**
	 * KT응답>>KT 응답 코드 (0000=성공, XXXX = 실패, ), Application 데몬 에러코드 (-1 ~ XXX)
	 */
	private String oResCode;
	
	/**
	 * oResCode의 코드네임
	 */
	private String oResCodeNm;
	
	/**
	 * KT응답>>충전 금액
	 */
	private int oAmount;
	
	/**
	 * KT응답>>충전 전 유효기간
	 */
	private int oOldRemains;
	
	/**
	 * KT응답>>충전 후 잔액 === 또는 현재 잔액
	 */
	private int oRemains;
	
	/**
	 * KT응답>>데이타 가입여부( 0= 미가입, 1=가입)
	 */
	private String oDataReq;
	
	/**
	 * KT응답>데이타 부가 서비스 ID(100M = 11, 500M= 13, 1G=14 ...)
	 */
	private String oDataVaId;
	
	/**
	 * KT응답>>데이타충전용량 / 최근 충전용량
	 */
	private String oDataAmount;
	
	/**
	 * KT응답>>데이타 충전 전 용량(Byte) / 취소전 용량
	 */
	private String oDataOldQuota;
	
	/**
	 * KT응답>>데이타요금 가입자 남은용량(Byte) / 충전후 용량 / 취소후 용량
	 */
	private String oDataQuota;
	
	/**
	 * KT응답>>데이타 부가서비스 잔액
	 */
	private String oDataVaRemains;
	
	private String oRemainsStr;
	
	/**
	 * 청소년요금제-최대충전가능금액
	 */
	private String oTesChargeMax;
	
	/**
	 * 청소년요금제-기본알
	 */
	private String oTesBaser;
	

	/**
	 * 청소년요금제-충전알
	 */
	private String oTesChgr;
	
	/**
	 * 청소년요금제-데이터알
	 */
	private String oTesMasicr;
	
	/**
	 * 청소년요금제-문자알
	 */
	private String oTesFsmsr;
	
	/**
	 * 청소년요금제-영상알
	 */
	private String oTesVideor;
	
	/**
	 * 청소년요금제-정보료전용알
	 */
	private String oTesIpvasr;
	
	/**
	 * 청소년요금제-알캡상한알
	 */
	private String oTesIpmaxr;
	
	/**
	 * 청소년요금제-문자건수
	 */
	private String oTesSmsm;
	
	/**
	 * 청소년요금제-데이타계정(단위:Byte)
	 */
	private String oTesDataplusv;
	
	/**
	 * 지능말관리하는 가입자 패스워드
	 */
	private String oPasswd;
	
	/**
	 * 남은 음성사용량(초)
	 */
	private int oRefillTime;
	
	/**
	 * TOPUP>음성사용량 만료일
	 */
	private String oRefillTimeExpire;
	
	/**
	 * TOPUP>남은 망내음성무료전용시간(초)
	 */
	private int oRefillTimeInnet;
	
	/**
	 * TOPUP>망내음성무료전용만료일
	 */
	private String oRefillTimeInnetExpire;
	
	/**
	 * 남은 문자건수(건)
	 */
	private int oRefillMsg;
	
	/**
	 * TOPUP>문자사용 만료일
	 */
	private String oRefillMsgExpire;
	
	/**
	 * TOPUP>남은 데이타량(M)
	 */
	private int oFreeData;
	
	/**
	 * TOPUP>남은 데이타량만료일
	 */
	private String oFreeDataExpire;
	
	/**
	 * TOPUP>남은 국제호전용 무료통화시간 제공량(금액)
	 */
	private int oFreeIntl;
	
	/**
	 * TOPUP>국제호전용 무료통화시간 만료일
	 */
	private String oFreeIntlExpire;
	
	/**
	 * 가입자 기본사용량복구일(일) 
	 */
	private int oRecharDay;
	
	/**
	 * @return the oFreeDataExpire
	 */
	public String getoFreeDataExpire() {
		return oFreeDataExpire;
	}

	/**
	 * @param oFreeDataExpire the oFreeDataExpire to set
	 */
	public void setoFreeDataExpire(String oFreeDataExpire) {
		this.oFreeDataExpire = oFreeDataExpire;
	}

	/**
	 * @return the oRefillTime
	 */
	public int getoRefillTime() {
		return oRefillTime;
	}

	/**
	 * @return the oTesChargeMax
	 */
	public String getoTesChargeMax() {
		return oTesChargeMax;
	}

	/**
	 * @param oTesChargeMax the oTesChargeMax to set
	 */
	public void setoTesChargeMax(String oTesChargeMax) {
		this.oTesChargeMax = oTesChargeMax;
	}

	/**
	 * @return the gubun
	 */
	public String getGubun() {
		return gubun;
	}

	/**
	 * @param gubun the gubun to set
	 */
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}

	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * @return the ktMode
	 */
	public String getKtMode() {
		return ktMode;
	}

	/**
	 * @param ktMode the ktMode to set
	 */
	public void setKtMode(String ktMode) {
		this.ktMode = ktMode;
	}

	/**
	 * @return the oResCode
	 */
	public String getoResCode() {
		return oResCode;
	}

	/**
	 * @param oResCode the oResCode to set
	 */
	public void setoResCode(String oResCode) {
		this.oResCode = oResCode;
	}

	/**
	 * @return the oResCodeNm
	 */
	public String getoResCodeNm() {
		return oResCodeNm;
	}

	/**
	 * @param oResCodeNm the oResCodeNm to set
	 */
	public void setoResCodeNm(String oResCodeNm) {
		this.oResCodeNm = oResCodeNm;
	}

	/**
	 * @return the oAmount
	 */
	public int getoAmount() {
		return oAmount;
	}

	/**
	 * @param oAmount the oAmount to set
	 */
	public void setoAmount(int oAmount) {
		this.oAmount = oAmount;
	}

	/**
	 * @return the oOldRemains
	 */
	public int getoOldRemains() {
		return oOldRemains;
	}

	/**
	 * @param oOldRemains the oOldRemains to set
	 */
	public void setoOldRemains(int oOldRemains) {
		this.oOldRemains = oOldRemains;
	}

	/**
	 * @return the oRemains
	 */
	public int getoRemains() {
		return oRemains;
	}

	/**
	 * @param oRemains the oRemains to set
	 */
	public void setoRemains(int oRemains) {
		this.oRemains = oRemains;
	}

	/**
	 * @return the oDataReq
	 */
	public String getoDataReq() {
		return oDataReq;
	}

	/**
	 * @param oDataReq the oDataReq to set
	 */
	public void setoDataReq(String oDataReq) {
		this.oDataReq = oDataReq;
	}

	/**
	 * @return the oDataVaId
	 */
	public String getoDataVaId() {
		return oDataVaId;
	}

	/**
	 * @param oDataVaId the oDataVaId to set
	 */
	public void setoDataVaId(String oDataVaId) {
		this.oDataVaId = oDataVaId;
	}

	/**
	 * @return the oDataAmount
	 */
	public String getoDataAmount() {
		return oDataAmount;
	}

	/**
	 * @param oDataAmount the oDataAmount to set
	 */
	public void setoDataAmount(String oDataAmount) {
		this.oDataAmount = oDataAmount;
	}

	/**
	 * @return the oDataOldQuota
	 */
	public String getoDataOldQuota() {
		return oDataOldQuota;
	}

	/**
	 * @param oDataOldQuota the oDataOldQuota to set
	 */
	public void setoDataOldQuota(String oDataOldQuota) {
		this.oDataOldQuota = oDataOldQuota;
	}

	/**
	 * @return the oDataQuota
	 */
	public String getoDataQuota() {
		return oDataQuota;
	}

	/**
	 * @param oDataQuota the oDataQuota to set
	 */
	public void setoDataQuota(String oDataQuota) {
		this.oDataQuota = oDataQuota;
	}

	/**
	 * @return the oDataVaRemains
	 */
	public String getoDataVaRemains() {
		return oDataVaRemains;
	}

	/**
	 * @param oDataVaRemains the oDataVaRemains to set
	 */
	public void setoDataVaRemains(String oDataVaRemains) {
		this.oDataVaRemains = oDataVaRemains;
	}

	/**
	 * @return the oRemainsStr
	 */
	public String getoRemainsStr() {
		return oRemainsStr;
	}

	/**
	 * @param oRemainsStr the oRemainsStr to set
	 */
	public void setoRemainsStr(String oRemainsStr) {
		this.oRemainsStr = oRemainsStr;
	}
	
	/**
	 * @return the oTesBaser
	 */
	public String getoTesBaser() {
		return oTesBaser;
	}

	/**
	 * @param oTesBaser the oTesBaser to set
	 */
	public void setoTesBaser(String oTesBaser) {
		this.oTesBaser = oTesBaser;
	}

	/**
	 * @return the oTesChgr
	 */
	public String getoTesChgr() {
		return oTesChgr;
	}

	/**
	 * @param oTesChgr the oTesChgr to set
	 */
	public void setoTesChgr(String oTesChgr) {
		this.oTesChgr = oTesChgr;
	}

	/**
	 * @return the oTesMasicr
	 */
	public String getoTesMasicr() {
		return oTesMasicr;
	}

	/**
	 * @param oTesMasicr the oTesMasicr to set
	 */
	public void setoTesMasicr(String oTesMasicr) {
		this.oTesMasicr = oTesMasicr;
	}

	/**
	 * @return the oTesFsmsr
	 */
	public String getoTesFsmsr() {
		return oTesFsmsr;
	}

	/**
	 * @param oTesFsmsr the oTesFsmsr to set
	 */
	public void setoTesFsmsr(String oTesFsmsr) {
		this.oTesFsmsr = oTesFsmsr;
	}

	/**
	 * @return the oTesVideor
	 */
	public String getoTesVideor() {
		return oTesVideor;
	}

	/**
	 * @param oTesVideor the oTesVideor to set
	 */
	public void setoTesVideor(String oTesVideor) {
		this.oTesVideor = oTesVideor;
	}

	/**
	 * @return the oTesIpvasr
	 */
	public String getoTesIpvasr() {
		return oTesIpvasr;
	}

	/**
	 * @param oTesIpvasr the oTesIpvasr to set
	 */
	public void setoTesIpvasr(String oTesIpvasr) {
		this.oTesIpvasr = oTesIpvasr;
	}

	/**
	 * @return the oTesIpmaxr
	 */
	public String getoTesIpmaxr() {
		return oTesIpmaxr;
	}

	/**
	 * @param oTesIpmaxr the oTesIpmaxr to set
	 */
	public void setoTesIpmaxr(String oTesIpmaxr) {
		this.oTesIpmaxr = oTesIpmaxr;
	}

	/**
	 * @return the oTesSmsm
	 */
	public String getoTesSmsm() {
		return oTesSmsm;
	}

	/**
	 * @param oTesSmsm the oTesSmsm to set
	 */
	public void setoTesSmsm(String oTesSmsm) {
		this.oTesSmsm = oTesSmsm;
	}

	/**
	 * @return the oTesDataplusv
	 */
	public String getoTesDataplusv() {
		return oTesDataplusv;
	}

	/**
	 * @param oTesDataplusv the oTesDataplusv to set
	 */
	public void setoTesDataplusv(String oTesDataplusv) {
		this.oTesDataplusv = oTesDataplusv;
	}

	/**
	 * @return the oPasswd
	 */
	public String getoPasswd() {
		return oPasswd;
	}

	/**
	 * @param oPasswd the oPasswd to set
	 */
	public void setoPasswd(String oPasswd) {
		this.oPasswd = oPasswd;
	}
	
	/**
	 * @param oRefillTime the oRefillTime to set
	 */
	public void setoRefillTime(int oRefillTime) {
		this.oRefillTime = oRefillTime;
	}

	/**
	 * @return the oRefillTimeExpire
	 */
	public String getoRefillTimeExpire() {
		return oRefillTimeExpire;
	}

	/**
	 * @param oRefillTimeExpire the oRefillTimeExpire to set
	 */
	public void setoRefillTimeExpire(String oRefillTimeExpire) {
		this.oRefillTimeExpire = oRefillTimeExpire;
	}

	/**
	 * @return the oRefillTimeInnet
	 */
	public int getoRefillTimeInnet() {
		return oRefillTimeInnet;
	}

	/**
	 * @param oRefillTimeInnet the oRefillTimeInnet to set
	 */
	public void setoRefillTimeInnet(int oRefillTimeInnet) {
		this.oRefillTimeInnet = oRefillTimeInnet;
	}

	/**
	 * @return the oRefillTimeInnetExpire
	 */
	public String getoRefillTimeInnetExpire() {
		return oRefillTimeInnetExpire;
	}

	/**
	 * @param oRefillTimeInnetExpire the oRefillTimeInnetExpire to set
	 */
	public void setoRefillTimeInnetExpire(String oRefillTimeInnetExpire) {
		this.oRefillTimeInnetExpire = oRefillTimeInnetExpire;
	}

	/**
	 * @return the oRefillMsg
	 */
	public int getoRefillMsg() {
		return oRefillMsg;
	}

	/**
	 * @param oRefillMsg the oRefillMsg to set
	 */
	public void setoRefillMsg(int oRefillMsg) {
		this.oRefillMsg = oRefillMsg;
	}

	/**
	 * @return the oRefillMsgExpire
	 */
	public String getoRefillMsgExpire() {
		return oRefillMsgExpire;
	}

	/**
	 * @param oRefillMsgExpire the oRefillMsgExpire to set
	 */
	public void setoRefillMsgExpire(String oRefillMsgExpire) {
		this.oRefillMsgExpire = oRefillMsgExpire;
	}
	
	/**
	 * @return the oFreeData
	 */
	public int getoFreeData() {
		return oFreeData;
	}

	/**
	 * @param oFreeData the oFreeData to set
	 */
	public void setoFreeData(int oFreeData) {
		this.oFreeData = oFreeData;
	}

	/**
	 * @return the oFreeIntl
	 */
	public int getoFreeIntl() {
		return oFreeIntl;
	}

	/**
	 * @param oFreeIntl the oFreeIntl to set
	 */
	public void setoFreeIntl(int oFreeIntl) {
		this.oFreeIntl = oFreeIntl;
	}

	/**
	 * @return the oFreeIntlExpire
	 */
	public String getoFreeIntlExpire() {
		return oFreeIntlExpire;
	}

	/**
	 * @param oFreeIntlExpire the oFreeIntlExpire to set
	 */
	public void setoFreeIntlExpire(String oFreeIntlExpire) {
		this.oFreeIntlExpire = oFreeIntlExpire;
	}

	/**
	 * @return the oRecharDay
	 */
	public int getoRecharDay() {
		return oRecharDay;
	}

	/**
	 * @param oRecharDay the oRecharDay to set
	 */
	public void setoRecharDay(int oRecharDay) {
		this.oRecharDay = oRecharDay;
	}
	
	
		
}
