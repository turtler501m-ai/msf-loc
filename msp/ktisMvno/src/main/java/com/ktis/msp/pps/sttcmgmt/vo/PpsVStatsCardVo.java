package com.ktis.msp.pps.sttcmgmt.vo;


import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PpsVStatsCardVo
 * @Description : 선불카드통계 VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="ppsVStatsCardVo")
public class PpsVStatsCardVo extends BaseVo  implements Serializable {

	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;
	

	

	/**
	 * CR_DATE:date(0)
	 */
	private String crDate;

	/**
	 * ONOFF_GUBUN:varchar2(2)
	 */
	private String onOffGubun;
	
	private String onOffGubunNm;
	

	/**
	 * PIN_GUBUN:varchar2(2)
	 */
	private String pinGubun;
	
	private String pinGubunNm;

	/**
	 * CREATE_PIN_CNT:number(0)
	 */
	private int createPinCnt;

	/**
	 * PIN_OUT_CNT:number(0)
	 */
	private int pinOutCnt;

	/**
	 * PIN_NON_OUT_CNT:number(0)
	 */
	private int pinNonOutCnt;

	/**
	 * PIN_OPEN_CNT:number(0)
	 */
	private int pinOpenCnt;

	/**
	 * OPEN_CHARGE:number(0)
	 */
	private int openCharge;

	/**
	 * NON_OPEN_CNT:number(0)
	 */
	private int nonOpenCnt;

	/**
	 * RCG_CNT:number(0)
	 */
	private int rcgCnt;

	/**
	 * RCG_CHARGE:number(0)
	 */
	private int rcgCharge;

	/**
	 * RCG_NON_CNT:number(0)
	 */
	private int rcgNonCnt;
	
	

	


	public String getCrDate() {
		return crDate;
	}






	public void setCrDate(String crDate) {
		this.crDate = crDate;
	}






	public String getOnOffGubun() {
		return onOffGubun;
	}






	public void setOnOffGubun(String onOffGubun) {
		this.onOffGubun = onOffGubun;
	}






	public String getOnOffGubunNm() {
		return onOffGubunNm;
	}






	public void setOnOffGubunNm(String onOffGubunNm) {
		this.onOffGubunNm = onOffGubunNm;
	}






	public String getPinGubun() {
		return pinGubun;
	}






	public void setPinGubun(String pinGubun) {
		this.pinGubun = pinGubun;
	}






	public String getPinGubunNm() {
		return pinGubunNm;
	}






	public void setPinGubunNm(String pinGubunNm) {
		this.pinGubunNm = pinGubunNm;
	}






	public int getCreatePinCnt() {
		return createPinCnt;
	}






	public void setCreatePinCnt(int createPinCnt) {
		this.createPinCnt = createPinCnt;
	}






	public int getPinOutCnt() {
		return pinOutCnt;
	}






	public void setPinOutCnt(int pinOutCnt) {
		this.pinOutCnt = pinOutCnt;
	}






	public int getPinNonOutCnt() {
		return pinNonOutCnt;
	}






	public void setPinNonOutCnt(int pinNonOutCnt) {
		this.pinNonOutCnt = pinNonOutCnt;
	}






	public int getPinOpenCnt() {
		return pinOpenCnt;
	}






	public void setPinOpenCnt(int pinOpenCnt) {
		this.pinOpenCnt = pinOpenCnt;
	}






	public int getOpenCharge() {
		return openCharge;
	}






	public void setOpenCharge(int openCharge) {
		this.openCharge = openCharge;
	}






	public int getNonOpenCnt() {
		return nonOpenCnt;
	}






	public void setNonOpenCnt(int nonOpenCnt) {
		this.nonOpenCnt = nonOpenCnt;
	}






	public int getRcgCnt() {
		return rcgCnt;
	}






	public void setRcgCnt(int rcgCnt) {
		this.rcgCnt = rcgCnt;
	}






	public int getRcgCharge() {
		return rcgCharge;
	}






	public void setRcgCharge(int rcgCharge) {
		this.rcgCharge = rcgCharge;
	}






	public int getRcgNonCnt() {
		return rcgNonCnt;
	}






	public void setRcgNonCnt(int rcgNonCnt) {
		this.rcgNonCnt = rcgNonCnt;
	}






	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
