package com.ktis.msp.pps.sttcmgmt.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PpsOnlineOrderVo
 * @Description : 충전현황 VO
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="PpsVStatsRechargeVo")
public class PpsVStatsRechargeVo extends BaseVo  implements Serializable {

	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;

	/**
	 * RECHARGE_DATE:date(0)
	 */
	private String rechargeDate;

	/**
	 * CHG_TYPE:char(1)
	 */
	private String chgType;
	
	/**
	 * CHG_TYPE_NM
	 */
	private String chgTypeNm;

	/**
	 * REQ_SRC:varchar2(20)
	 */
	private String reqSrc;

	/**
	 * RECHARGE_METHOD:varchar2(30)
	 */
	private String rechargeMethod;

	/**
	 * AGENT_ID:varchar2(20)
	 */
	private String agentId;
	
	/**
	 * AGENT_NM:varchar2(20)
	 */
	private String agentNm;

	/**
	 * RECHARGE_AGENT:varchar2(20)
	 */
	private String rechargeAgent;
	
	private String rechargeAgentNm;
	

	/**
	 * RECHARGE_CNT:number(0)
	 */
	private int rechargeCnt;

	/**
	 * AMOUNT:number(0)
	 */
	private int amount;

	/**
	 * IN_AMOUNT:number(0)
	 */
	private int inAmount;

	/**
	 * VOICE_RECHARGE:number(0)
	 */
	private int voiceRecharge;

	/**
	 * FREE_RECHARGE:number(0)
	 */
	private int freeRecharge;
	
	
	
	
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
	 * @return the chgType
	 */
	public String getChgType() {
		return chgType;
	}




	/**
	 * @param chgType the chgType to set
	 */
	public void setChgType(String chgType) {
		this.chgType = chgType;
	}




	/**
	 * @return the chgTypeNm
	 */
	public String getChgTypeNm() {
		return chgTypeNm;
	}




	/**
	 * @param chgTypeNm the chgTypeNm to set
	 */
	public void setChgTypeNm(String chgTypeNm) {
		this.chgTypeNm = chgTypeNm;
	}




	/**
	 * @return the reqSrc
	 */
	public String getReqSrc() {
		return reqSrc;
	}




	/**
	 * @param reqSrc the reqSrc to set
	 */
	public void setReqSrc(String reqSrc) {
		this.reqSrc = reqSrc;
	}




	/**
	 * @return the rechargeMethod
	 */
	public String getRechargeMethod() {
		return rechargeMethod;
	}




	/**
	 * @param rechargeMethod the rechargeMethod to set
	 */
	public void setRechargeMethod(String rechargeMethod) {
		this.rechargeMethod = rechargeMethod;
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
	 * @return the agentNm
	 */
	public String getAgentNm() {
		return agentNm;
	}




	/**
	 * @param agentNm the agentNm to set
	 */
	public void setAgentNm(String agentNm) {
		this.agentNm = agentNm;
	}




	/**
	 * @return the rechargeAgent
	 */
	public String getRechargeAgent() {
		return rechargeAgent;
	}




	/**
	 * @param rechargeAgent the rechargeAgent to set
	 */
	public void setRechargeAgent(String rechargeAgent) {
		this.rechargeAgent = rechargeAgent;
	}




	/**
	 * @return the rechargeAgentNm
	 */
	public String getRechargeAgentNm() {
		return rechargeAgentNm;
	}




	/**
	 * @param rechargeAgentNm the rechargeAgentNm to set
	 */
	public void setRechargeAgentNm(String rechargeAgentNm) {
		this.rechargeAgentNm = rechargeAgentNm;
	}




	/**
	 * @return the rechargeCnt
	 */
	public int getRechargeCnt() {
		return rechargeCnt;
	}




	/**
	 * @param rechargeCnt the rechargeCnt to set
	 */
	public void setRechargeCnt(int rechargeCnt) {
		this.rechargeCnt = rechargeCnt;
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
	 * @return the inAmount
	 */
	public int getInAmount() {
		return inAmount;
	}




	/**
	 * @param inAmount the inAmount to set
	 */
	public void setInAmount(int inAmount) {
		this.inAmount = inAmount;
	}




	/**
	 * @return the voiceRecharge
	 */
	public int getVoiceRecharge() {
		return voiceRecharge;
	}




	/**
	 * @param voiceRecharge the voiceRecharge to set
	 */
	public void setVoiceRecharge(int voiceRecharge) {
		this.voiceRecharge = voiceRecharge;
	}




	/**
	 * @return the freeRecharge
	 */
	public int getFreeRecharge() {
		return freeRecharge;
	}




	/**
	 * @param freeRecharge the freeRecharge to set
	 */
	public void setFreeRecharge(int freeRecharge) {
		this.freeRecharge = freeRecharge;
	}




	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	

}
