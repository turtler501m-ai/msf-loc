package com.ktis.msp.pps.rcgmgmt.vo;


import java.io.Serializable;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PpsRcgPosVo
 * @Description : POS충전내역 VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="ppsRcgPosVo")
public class PpsRcgPosVo extends BaseVo  implements Serializable {
	
	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;

	
	
	
	/**
	 * 주문번호:number(20)
	 */
	private int posSeq;

	/**
	 * 요청구분(POS_RCG_AUTH:인증, POS_RCG_VOICE:음성충전, POS_CAN_VOICE:음성취소, POS_RCG_DATA:데이타충전, POS_CAN_DATA:데이타취소):varchar2(30)
	 */
	private String reqType;

	/**
	 * 서비스계정:number(9)
	 */
	private int contractNum;

	/**
	 * 휴대폰번호:varchar2(20)
	 */
	private String subscriberNo;

	/**
	 * 판매점코드:varchar2(30)
	 */
	private String storeCode;
	
	/**
	 * 판매점명
	 */
	private int storeCodeNm;


	/**
	 * 구매요청코드:varchar2(40)
	 */
	private String storeOrderCode;

	/**
	 * 충전요청금액:number(20)
	 */
	private int recharge;

	/**
	 * 충전승인코드(취소시 필요):varchar2(30)
	 */
	private String authCode;

	/**
	 * 취소사유(갤럭시아 정의필요):varchar2(20)
	 */
	private String cancelType;

	/**
	 * 만료일자:date(0)
	 */
	private String expireDate;

	/**
	 * 결과코드(0000:성공, 1001:가입자미확인, 1002:해지가입자, 2001:충전불가금액, 2002:이통사오류, 3001:승인번호오류, 3002:취소금액오류, 3003:잔액부족, 3004:이통사오류, 3005:이미취소됨, 9999:시스템오류):varchar2(20)
	 */
	private String retCode;

	/**
	 * 결과메세지:varchar2(200)
	 */
	private String retMsg;

	/**
	 * 등록일자:date(0)
	 */
	private String recordDate;

	/**
	 * 전송결과:varchar2(10)
	 */
	private String sendResult;

	/**
	 * 충전취소여부 Y / N:varchar2(10)
	 */
	private String cancelFlag;

	/**
	 * RCG_SEQ:number(20)
	 */
	private int rcgSeq;
	

	/**
	 * @return the posSeq
	 */
	public int getPosSeq() {
		return posSeq;
	}




	/**
	 * @param posSeq the posSeq to set
	 */
	public void setPosSeq(int posSeq) {
		this.posSeq = posSeq;
	}




	/**
	 * @return the reqType
	 */
	public String getReqType() {
		return reqType;
	}




	/**
	 * @param reqType the reqType to set
	 */
	public void setReqType(String reqType) {
		this.reqType = reqType;
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
	 * @return the storeCode
	 */
	public String getStoreCode() {
		return storeCode;
	}




	/**
	 * @param storeCode the storeCode to set
	 */
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}




	/**
	 * @return the storeOrderCode
	 */
	public String getStoreOrderCode() {
		return storeOrderCode;
	}




	/**
	 * @param storeOrderCode the storeOrderCode to set
	 */
	public void setStoreOrderCode(String storeOrderCode) {
		this.storeOrderCode = storeOrderCode;
	}




	/**
	 * @return the recharge
	 */
	public int getRecharge() {
		return recharge;
	}




	/**
	 * @param recharge the recharge to set
	 */
	public void setRecharge(int recharge) {
		this.recharge = recharge;
	}




	/**
	 * @return the authCode
	 */
	public String getAuthCode() {
		return authCode;
	}




	/**
	 * @param authCode the authCode to set
	 */
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}




	/**
	 * @return the cancelType
	 */
	public String getCancelType() {
		return cancelType;
	}




	/**
	 * @param cancelType the cancelType to set
	 */
	public void setCancelType(String cancelType) {
		this.cancelType = cancelType;
	}




	/**
	 * @return the expireDate
	 */
	public String getExpireDate() {
		return expireDate;
	}




	/**
	 * @param expireDate the expireDate to set
	 */
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}




	/**
	 * @return the retCode
	 */
	public String getRetCode() {
		return retCode;
	}




	/**
	 * @param retCode the retCode to set
	 */
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}




	/**
	 * @return the retMsg
	 */
	public String getRetMsg() {
		return retMsg;
	}




	/**
	 * @param retMsg the retMsg to set
	 */
	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
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
	 * @return the sendResult
	 */
	public String getSendResult() {
		return sendResult;
	}




	/**
	 * @param sendResult the sendResult to set
	 */
	public void setSendResult(String sendResult) {
		this.sendResult = sendResult;
	}




	/**
	 * @return the cancelFlag
	 */
	public String getCancelFlag() {
		return cancelFlag;
	}




	/**
	 * @param cancelFlag the cancelFlag to set
	 */
	public void setCancelFlag(String cancelFlag) {
		this.cancelFlag = cancelFlag;
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
	
	public int getStoreCodeNm() {
		return storeCodeNm;
	}

	public void setStoreCodeNm(int storeCodeNm) {
		this.storeCodeNm = storeCodeNm;
	}




	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}



}
