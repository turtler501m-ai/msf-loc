package com.ktis.msp.pps.hdofccustmgmt.vo;

import java.io.Serializable;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PpsRcgRealCmsVo
 * @Description : 실시간이제정보  VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="PpsRcgRealCmsVo")
public class PpsRcgRealCmsVo extends BaseVo  implements Serializable {
	
	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;


	public static final String TABLE = "PPS_RCG_REAL_CMS";

	/**
	 * cms 고유번호:number(20)
	 */
	private int cmsSeq;

	/**
	 * 요청일자:date(0)
	 */
	private java.sql.Timestamp reqDate;

	/**
	 * 요청일련번호(일단위 갱신):number(20)
	 */
	private int reqNo;

	/**
	 * 출금결과코드:varchar2(20)
	 */
	private String resCode;
	
	/**
	 * 출금결과코드명:varchar2(20)
	 */
	private String resCodeName;

	/**
	 * 결과일자:date(0)
	 */
	private java.sql.Timestamp resDate;

	/**
	 * 성공여부:varchar2(10)
	 */
	private String succFlag;

	/**
	 * 은행코드:varchar2(10)
	 */
	private String bankCode;
	
	/**
	 * 은행명:varchar2(10)
	 */
	private String bankCodeNm;	

	/**
	 * 계좌번호(암호화):varchar2(100)
	 */
	private String bankAccount;

	/**
	 * 예금주주민번호(암호화):varchar2(100)
	 */
	private String bankPeopleId;

	/**
	 * 예금주명:varchar2(20)
	 */
	private String bankUserName;

	/**
	 * 서비스어카운트(고객키값):number(9)
	 */
	private int contractNum;

	/**
	 * 출금회차:number(20)
	 */
	private int chargeSeq;

	/**
	 * 출금요청금액:number(20)
	 */
	private int reqAmount;

	/**
	 * 실제출금액:number(20)
	 */
	private int resAmount;

	/**
	 * 출금수수료:number(20,5)
	 */
	private int chargeFee;

	/**
	 * 출금시도 횟수:number(20)
	 */
	private int tryCnt;

	/**
	 * 기록날짜:date(0)
	 */
	private String recordDate;

	/**
	 * 충전결과:varchar2(10)
	 */
	private String rechargeResult;

	/**
	 * 충전날짜:date(0)
	 */
	private String rechargeDate;

	/**
	 * cms시작일자:date(0)
	 */
	private String cmsStartDate;

	/**
	 * CMS_TYPE:varchar2(20)
	 */
	private String cmsType;

	/**
	 * KT_IN_REQ와의 연결 SEQUENCE(충전시 모든 로직에 공통으로 적용되는 KEY값):number(20)
	 */
	private int rcgSeq;

	/**
	 * ADMIN_ID:varchar2(20)
	 */
	private String adminId;

	/**
	 * KT_RES_CD:varchar2(10)
	 */
	private String ktResCd;

	/**
	 * KT_RES_MSG:varchar2(300)
	 */
	private String ktResMsg;

	

	/**
	 * @return the cmsSeq
	 */
	public int getCmsSeq() {
		return cmsSeq;
	}



	/**
	 * @param cmsSeq the cmsSeq to set
	 */
	public void setCmsSeq(int cmsSeq) {
		this.cmsSeq = cmsSeq;
	}



	/**
	 * @return the reqDate
	 */
	public java.sql.Timestamp getReqDate() {
		return reqDate;
	}



	/**
	 * @param reqDate the reqDate to set
	 */
	public void setReqDate(java.sql.Timestamp reqDate) {
		this.reqDate = reqDate;
	}



	/**
	 * @return the reqNo
	 */
	public int getReqNo() {
		return reqNo;
	}



	/**
	 * @param reqNo the reqNo to set
	 */
	public void setReqNo(int reqNo) {
		this.reqNo = reqNo;
	}



	/**
	 * @return the resCode
	 */
	public String getResCode() {
		return resCode;
	}



	/**
	 * @param resCode the resCode to set
	 */
	public void setResCode(String resCode) {
		this.resCode = resCode;
	}
	
	/**
	 * @return the resCodeName
	 */
	public String getResCodeName() {
		return resCodeName;
	}



	/**
	 * @param resCodeName the resCodeName to set
	 */
	public void setResCodeName(String resCodeName) {
		this.resCodeName = resCodeName;
	}



	/**
	 * @return the resDate
	 */
	public java.sql.Timestamp getResDate() {
		return resDate;
	}



	/**
	 * @param resDate the resDate to set
	 */
	public void setResDate(java.sql.Timestamp resDate) {
		this.resDate = resDate;
	}



	/**
	 * @return the succFlag
	 */
	public String getSuccFlag() {
		return succFlag;
	}



	/**
	 * @param succFlag the succFlag to set
	 */
	public void setSuccFlag(String succFlag) {
		this.succFlag = succFlag;
	}



	/**
	 * @return the bankCode
	 */
	public String getBankCode() {
		return bankCode;
	}



	/**
	 * @param bankCode the bankCode to set
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}



	/**
	 * @return the bankCodeNm
	 */
	public String getBankCodeNm() {
		return bankCodeNm;
	}



	/**
	 * @param bankCodeNm the bankCodeNm to set
	 */
	public void setBankCodeNm(String bankCodeNm) {
		this.bankCodeNm = bankCodeNm;
	}



	/**
	 * @return the bankAccount
	 */
	public String getBankAccount() {
		return bankAccount;
	}



	/**
	 * @param bankAccount the bankAccount to set
	 */
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}



	/**
	 * @return the bankPeopleId
	 */
	public String getBankPeopleId() {
		return bankPeopleId;
	}



	/**
	 * @param bankPeopleId the bankPeopleId to set
	 */
	public void setBankPeopleId(String bankPeopleId) {
		this.bankPeopleId = bankPeopleId;
	}



	/**
	 * @return the bankUserName
	 */
	public String getBankUserName() {
		return bankUserName;
	}



	/**
	 * @param bankUserName the bankUserName to set
	 */
	public void setBankUserName(String bankUserName) {
		this.bankUserName = bankUserName;
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
	 * @return the chargeSeq
	 */
	public int getChargeSeq() {
		return chargeSeq;
	}



	/**
	 * @param chargeSeq the chargeSeq to set
	 */
	public void setChargeSeq(int chargeSeq) {
		this.chargeSeq = chargeSeq;
	}



	/**
	 * @return the reqAmount
	 */
	public int getReqAmount() {
		return reqAmount;
	}



	/**
	 * @param reqAmount the reqAmount to set
	 */
	public void setReqAmount(int reqAmount) {
		this.reqAmount = reqAmount;
	}



	/**
	 * @return the resAmount
	 */
	public int getResAmount() {
		return resAmount;
	}



	/**
	 * @param resAmount the resAmount to set
	 */
	public void setResAmount(int resAmount) {
		this.resAmount = resAmount;
	}



	/**
	 * @return the chargeFee
	 */
	public int getChargeFee() {
		return chargeFee;
	}



	/**
	 * @param chargeFee the chargeFee to set
	 */
	public void setChargeFee(int chargeFee) {
		this.chargeFee = chargeFee;
	}



	/**
	 * @return the tryCnt
	 */
	public int getTryCnt() {
		return tryCnt;
	}



	/**
	 * @param tryCnt the tryCnt to set
	 */
	public void setTryCnt(int tryCnt) {
		this.tryCnt = tryCnt;
	}



	/**
	 * @return the recordDate
	 */
	public String getRecordDate() {
		return recordDate;
	}



	/**
	 * @param recordDate the recordDate to set
	 */
	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}



	/**
	 * @return the rechargeResult
	 */
	public String getRechargeResult() {
		return rechargeResult;
	}



	/**
	 * @param rechargeResult the rechargeResult to set
	 */
	public void setRechargeResult(String rechargeResult) {
		this.rechargeResult = rechargeResult;
	}



	/**
	 * @return the rechargeDate
	 */
	public String getRechargeDate() {
		return rechargeDate;
	}



	/**
	 * @param rechargeDate the rechargeDate to set
	 */
	public void setRechargeDate(String rechargeDate) {
		this.rechargeDate = rechargeDate;
	}



	/**
	 * @return the cmsStartDate
	 */
	public String getCmsStartDate() {
		return cmsStartDate;
	}



	/**
	 * @param cmsStartDate the cmsStartDate to set
	 */
	public void setCmsStartDate(String cmsStartDate) {
		this.cmsStartDate = cmsStartDate;
	}



	/**
	 * @return the cmsType
	 */
	public String getCmsType() {
		return cmsType;
	}



	/**
	 * @param cmsType the cmsType to set
	 */
	public void setCmsType(String cmsType) {
		this.cmsType = cmsType;
	}



	/**
	 * @return the rcgSeq
	 */
	public int getRcgSeq() {
		return rcgSeq;
	}



	/**
	 * @param rcgSeq the rcgSeq to set
	 */
	public void setRcgSeq(int rcgSeq) {
		this.rcgSeq = rcgSeq;
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
	 * @return the ktResCd
	 */
	public String getKtResCd() {
		return ktResCd;
	}



	/**
	 * @param ktResCd the ktResCd to set
	 */
	public void setKtResCd(String ktResCd) {
		this.ktResCd = ktResCd;
	}



	/**
	 * @return the ktResMsg
	 */
	public String getKtResMsg() {
		return ktResMsg;
	}



	/**
	 * @param ktResMsg the ktResMsg to set
	 */
	public void setKtResMsg(String ktResMsg) {
		this.ktResMsg = ktResMsg;
	}



	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	
}
