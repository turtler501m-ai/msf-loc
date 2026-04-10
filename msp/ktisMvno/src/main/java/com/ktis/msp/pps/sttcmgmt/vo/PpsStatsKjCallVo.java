package com.ktis.msp.pps.sttcmgmt.vo;


import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PpsStatsKjCallVo
 * @Description : 국제통계 VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="PpsStatsKjCallVo")
public class PpsStatsKjCallVo extends BaseVo  implements Serializable {

	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;
	

	

	/**
	 * 통화날짜:date(0)
	 */
	private String callDate;

	/**
	 * 국가코드:varchar2(20)
	 */
	private String prefix;
	
	private String prefixNm;

	/**
	 * Telco사업자:varchar2(20)
	 */
	private String telcoPx;
	
	private String telcoPxNm;
	

	/**
	 * 통화건수:number(20)
	 */
	private int callCnt;

	/**
	 * 통화시간(초):number(20)
	 */
	private int callTime;

	/**
	 * 통화과금액:number(20,5)
	 */
	private double callAmount;

	/**
	 * 문자건수:number(20)
	 */
	private int smsCnt;

	/**
	 * 문자과금액:number(20,5)
	 */
	private double smsAmount;
	
	
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
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}


	/**
	 * @param prefix the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}


	/**
	 * @return the prefixNm
	 */
	public String getPrefixNm() {
		return prefixNm;
	}


	/**
	 * @param prefixNm the prefixNm to set
	 */
	public void setPrefixNm(String prefixNm) {
		this.prefixNm = prefixNm;
	}


	/**
	 * @return the telcoPx
	 */
	public String getTelcoPx() {
		return telcoPx;
	}


	/**
	 * @param telcoPx the telcoPx to set
	 */
	public void setTelcoPx(String telcoPx) {
		this.telcoPx = telcoPx;
	}


	/**
	 * @return the telcoPxNm
	 */
	public String getTelcoPxNm() {
		return telcoPxNm;
	}


	/**
	 * @param telcoPxNm the telcoPxNm to set
	 */
	public void setTelcoPxNm(String telcoPxNm) {
		this.telcoPxNm = telcoPxNm;
	}


	/**
	 * @return the callCnt
	 */
	public int getCallCnt() {
		return callCnt;
	}


	/**
	 * @param callCnt the callCnt to set
	 */
	public void setCallCnt(int callCnt) {
		this.callCnt = callCnt;
	}


	/**
	 * @return the callTime
	 */
	public int getCallTime() {
		return callTime;
	}


	/**
	 * @param callTime the callTime to set
	 */
	public void setCallTime(int callTime) {
		this.callTime = callTime;
	}


	/**
	 * @return the callAmount
	 */
	public double getCallAmount() {
		return callAmount;
	}


	/**
	 * @param callAmount the callAmount to set
	 */
	public void setCallAmount(double callAmount) {
		this.callAmount = callAmount;
	}


	/**
	 * @return the smsCnt
	 */
	public int getSmsCnt() {
		return smsCnt;
	}


	/**
	 * @param smsCnt the smsCnt to set
	 */
	public void setSmsCnt(int smsCnt) {
		this.smsCnt = smsCnt;
	}


	/**
	 * @return the smsAmount
	 */
	public double getSmsAmount() {
		return smsAmount;
	}


	/**
	 * @param smsAmount the smsAmount to set
	 */
	public void setSmsAmount(double smsAmount) {
		this.smsAmount = smsAmount;
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	

}
