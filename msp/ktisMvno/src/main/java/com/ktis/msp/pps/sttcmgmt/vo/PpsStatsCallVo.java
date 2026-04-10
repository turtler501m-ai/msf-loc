package com.ktis.msp.pps.sttcmgmt.vo;


import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PpsVStatsOpenVo
 * @Description : 사용통계현황 VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="ppsVStatsOpenVo")
public class PpsStatsCallVo extends BaseVo  implements Serializable {

	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;

	

	/**
	 * 통계일자:date(0)
	 */
	private String callDate;

	/**
	 * 일차감건수:number(20)
	 */
	private int dayMinusCnt;

	/**
	 * 일차감액:number(20,2)
	 */
	private double dayMinusAmount;

	/**
	 * 국내사용초(음성):number(20)
	 */
	private int inCallDuration;

	/**
	 * 국내통화료:number(20,2)
	 */
	private double inCallAmount;

	/**
	 * 국제통화사용초(음성):number(20)
	 */
	private int outCallDuration;

	/**
	 * 국제통화금액:number(20,2)
	 */
	private double outCallAmount;

	/**
	 * 국내데이타패킷량:number(20)
	 */
	private int inDataPktDuration;

	/**
	 * 국내데이타료:number(20,2)
	 */
	private double inDataPktAmount;

	/**
	 * 국내문자건수:number(20)
	 */
	private int inSmsCnt;

	/**
	 * 국내문자료:number(20,2)
	 */
	private double inSmsAmount;

	/**
	 * 국제문자건수:number(20)
	 */
	private int outSmsCnt;

	/**
	 * 국제문자료:number(20,2)
	 */
	private double outSmsAmount;

	/**
	 * 기타건수:number(20)
	 */
	private int etcCnt;

	/**
	 * 기타금액:number(20,2)
	 */
	private double etcAmount;

	/**
	 * CHARGE:number(20,2)
	 */
	private double charge;
	
	
	
	/**
	 * @return the callDate
	 */
	public String getCallDate() {
		return callDate;
	}



	/**
	 * @param callDate the callDate to set
	 */
	public void setCallDate(String callDate) {
		this.callDate = callDate;
	}



	/**
	 * @return the dayMinusCnt
	 */
	public int getDayMinusCnt() {
		return dayMinusCnt;
	}



	/**
	 * @param dayMinusCnt the dayMinusCnt to set
	 */
	public void setDayMinusCnt(int dayMinusCnt) {
		this.dayMinusCnt = dayMinusCnt;
	}



	/**
	 * @return the dayMinusAmount
	 */
	public double getDayMinusAmount() {
		return dayMinusAmount;
	}



	/**
	 * @param dayMinusAmount the dayMinusAmount to set
	 */
	public void setDayMinusAmount(double dayMinusAmount) {
		this.dayMinusAmount = dayMinusAmount;
	}



	/**
	 * @return the inCallDuration
	 */
	public int getInCallDuration() {
		return inCallDuration;
	}



	/**
	 * @param inCallDuration the inCallDuration to set
	 */
	public void setInCallDuration(int inCallDuration) {
		this.inCallDuration = inCallDuration;
	}



	/**
	 * @return the inCallAmount
	 */
	public double getInCallAmount() {
		return inCallAmount;
	}



	/**
	 * @param inCallAmount the inCallAmount to set
	 */
	public void setInCallAmount(double inCallAmount) {
		this.inCallAmount = inCallAmount;
	}



	/**
	 * @return the outCallDuration
	 */
	public int getOutCallDuration() {
		return outCallDuration;
	}



	/**
	 * @param outCallDuration the outCallDuration to set
	 */
	public void setOutCallDuration(int outCallDuration) {
		this.outCallDuration = outCallDuration;
	}



	/**
	 * @return the outCallAmount
	 */
	public double getOutCallAmount() {
		return outCallAmount;
	}



	/**
	 * @param outCallAmount the outCallAmount to set
	 */
	public void setOutCallAmount(double outCallAmount) {
		this.outCallAmount = outCallAmount;
	}



	/**
	 * @return the inDataPktDuration
	 */
	public int getInDataPktDuration() {
		return inDataPktDuration;
	}



	/**
	 * @param inDataPktDuration the inDataPktDuration to set
	 */
	public void setInDataPktDuration(int inDataPktDuration) {
		this.inDataPktDuration = inDataPktDuration;
	}



	/**
	 * @return the inDataPktAmount
	 */
	public double getInDataPktAmount() {
		return inDataPktAmount;
	}



	/**
	 * @param inDataPktAmount the inDataPktAmount to set
	 */
	public void setInDataPktAmount(double inDataPktAmount) {
		this.inDataPktAmount = inDataPktAmount;
	}



	/**
	 * @return the inSmsCnt
	 */
	public int getInSmsCnt() {
		return inSmsCnt;
	}



	/**
	 * @param inSmsCnt the inSmsCnt to set
	 */
	public void setInSmsCnt(int inSmsCnt) {
		this.inSmsCnt = inSmsCnt;
	}



	/**
	 * @return the inSmsAmount
	 */
	public double getInSmsAmount() {
		return inSmsAmount;
	}



	/**
	 * @param inSmsAmount the inSmsAmount to set
	 */
	public void setInSmsAmount(double inSmsAmount) {
		this.inSmsAmount = inSmsAmount;
	}



	/**
	 * @return the outSmsCnt
	 */
	public int getOutSmsCnt() {
		return outSmsCnt;
	}



	/**
	 * @param outSmsCnt the outSmsCnt to set
	 */
	public void setOutSmsCnt(int outSmsCnt) {
		this.outSmsCnt = outSmsCnt;
	}



	/**
	 * @return the outSmsAmount
	 */
	public double getOutSmsAmount() {
		return outSmsAmount;
	}



	/**
	 * @param outSmsAmount the outSmsAmount to set
	 */
	public void setOutSmsAmount(double outSmsAmount) {
		this.outSmsAmount = outSmsAmount;
	}



	/**
	 * @return the etcCnt
	 */
	public int getEtcCnt() {
		return etcCnt;
	}



	/**
	 * @param etcCnt the etcCnt to set
	 */
	public void setEtcCnt(int etcCnt) {
		this.etcCnt = etcCnt;
	}



	/**
	 * @return the etcAmount
	 */
	public double getEtcAmount() {
		return etcAmount;
	}



	/**
	 * @param etcAmount the etcAmount to set
	 */
	public void setEtcAmount(double etcAmount) {
		this.etcAmount = etcAmount;
	}



	/**
	 * @return the charge
	 */
	public double getCharge() {
		return charge;
	}



	/**
	 * @param charge the charge to set
	 */
	public void setCharge(double charge) {
		this.charge = charge;
	}



	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	
}
