package com.ktis.msp.pps.hdofccustmgmt.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PpsCodeServiceInfoVo
 * @Description : 요금제 정보  VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="ppsCodeServiceInfoVo")

public class PpsCodeServiceInfoVo extends BaseVo  implements Serializable {

/**
 * serialVersion UID
 */
	private static final long serialVersionUID = 6546990189880359258L;


	public static final String TABLE = "PPS_CODE_SERVICE_INFO";

	/**
	 * KT_JUO_FEATURE의 SOC 요금제 코드, PK:varchar2(90) <Primary Key>
	 */
	private String soc;

	/**
	 * 부가서비스이름:varchar2(300)
	 */
	private String serviceName;

	/**
	 * 월기본료/사용료:number(10)
	 */
	private int serviceBasic;

	/**
	 * PO=후불기본요금제, PP=선불기본요금제, VA=부가요금제 :varchar2(15)
	 */
	private String serviceType;
	
	/**
	 * 서비스타입명 
	 */
	private String serviceTypeNm;

	/**
	 * 무료음성분수:number(20,5)
	 */
	private double basicVoice;

	/**
	 * 무료문자:number(20,5)
	 */
	private double basicSms;

	/**
	 * 무료데이타:number(20,5)
	 */
	private double basicData;

	/**
	 * 초과사용시 음성요금:number(20,5)
	 */
	private double overVoice;

	/**
	 * 초과시 건당 SMS요금:number(20,5)
	 */
	private double overSms;

	/**
	 * 1MByte당 요금:number(20,5)
	 */
	private double overData;

	/**
	 * 영상요금:number(20,5)
	 */
	private double overVideo;

	/**
	 * 초과시 건당 MMS요금:number(20,5)
	 */
	private double overMms;

	/**
	 * 등록날짜:date(0)
	 */
	private String  recordDate;

	/**
	 * 등록관리자:varchar2(30)
	 */
	private String adminId;
	
	
	

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
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}




	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}




	/**
	 * @return the serviceBasic
	 */
	public int getServiceBasic() {
		return serviceBasic;
	}




	/**
	 * @param serviceBasic the serviceBasic to set
	 */
	public void setServiceBasic(int serviceBasic) {
		this.serviceBasic = serviceBasic;
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
	 * @return the serviceTypeNm
	 */
	public String getServiceTypeNm() {
		return serviceTypeNm;
	}




	/**
	 * @param serviceTypeNm the serviceTypeNm to set
	 */
	public void setServiceTypeNm(String serviceTypeNm) {
		this.serviceTypeNm = serviceTypeNm;
	}




	/**
	 * @return the basicVoice
	 */
	public double getBasicVoice() {
		return basicVoice;
	}




	/**
	 * @param basicVoice the basicVoice to set
	 */
	public void setBasicVoice(double basicVoice) {
		this.basicVoice = basicVoice;
	}




	/**
	 * @return the basicSms
	 */
	public double getBasicSms() {
		return basicSms;
	}




	/**
	 * @param basicSms the basicSms to set
	 */
	public void setBasicSms(double basicSms) {
		this.basicSms = basicSms;
	}




	/**
	 * @return the basicData
	 */
	public double getBasicData() {
		return basicData;
	}




	/**
	 * @param basicData the basicData to set
	 */
	public void setBasicData(double basicData) {
		this.basicData = basicData;
	}




	/**
	 * @return the overVoice
	 */
	public double getOverVoice() {
		return overVoice;
	}




	/**
	 * @param overVoice the overVoice to set
	 */
	public void setOverVoice(double overVoice) {
		this.overVoice = overVoice;
	}




	/**
	 * @return the overSms
	 */
	public double getOverSms() {
		return overSms;
	}




	/**
	 * @param overSms the overSms to set
	 */
	public void setOverSms(double overSms) {
		this.overSms = overSms;
	}




	/**
	 * @return the overData
	 */
	public double getOverData() {
		return overData;
	}




	/**
	 * @param overData the overData to set
	 */
	public void setOverData(double overData) {
		this.overData = overData;
	}




	/**
	 * @return the overVideo
	 */
	public double getOverVideo() {
		return overVideo;
	}




	/**
	 * @param overVideo the overVideo to set
	 */
	public void setOverVideo(double overVideo) {
		this.overVideo = overVideo;
	}




	/**
	 * @return the overMms
	 */
	public double getOverMms() {
		return overMms;
	}




	/**
	 * @param overMms the overMms to set
	 */
	public void setOverMms(double overMms) {
		this.overMms = overMms;
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




	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
