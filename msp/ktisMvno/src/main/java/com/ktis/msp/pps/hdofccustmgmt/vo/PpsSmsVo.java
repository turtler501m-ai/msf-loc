package com.ktis.msp.pps.hdofccustmgmt.vo;

import java.io.Serializable;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PpsSmsVo
 * @Description : 문자발송 VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="ppsSmsVo")
public class PpsSmsVo extends BaseVo  implements Serializable {
	
	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;
	

	public static final String TABLE = "PPS_SMS";

	/**
	 * 고유번호:number(20)
	 */
	private int smsSeq;

	/**
	 * 발송일자:date(0)
	 */
	private String smsSendDate;

	/**
	 * 내용:varchar2(255)
	 */
	private String smsMsg;

	/**
	 * 발신번호:varchar2(20)
	 */
	private String callingNumber;

	/**
	 * 착신번호:varchar2(20)
	 */
	private String calledNumber;

	/**
	 * 발송상태:char(1)
	 */
	private String smsStatus;

	/**
	 * 결과코드:char(4)
	 */
	private String smsResultCode;
	
	/**
	 * 결과코드네임
	 */
	private String smsResultNm;

	/**
	 * 결과수신일자:date(0)
	 */
	private String smsResultDate;

	
	/**
	 * 등록관리자:varchar2(20)
	 */
	private String adminId;

	/**
	 * SMS전송주체:varchar2(40)
	 */
	private String smsSrc;

	/**
	 * SMS방법:varchar2(40)
	 */
	private String smsMethod;

	/**
	 * 발송구분(카드충전, 가상계좌안내, 충전완료메세지 등):varchar2(40)
	 */
	private String smsGubun;

	/**
	 * PIN번호:varchar2(40)
	 */
	private String pinNumber;

	/**
	 * 개통대리점:varchar2(20)
	 */
	private String openAgentId;

	/**
	 * 등록일자:date(0)
	 */
	private String recordDate;

	/**
	 * CONTRACT_NUM:number(9)
	 */
	private int contractNum;
	
	/**
	 * @return the smsSeq
	 */
	public int getSmsSeq() {
		return smsSeq;
	}

	/**
	 * @param smsSeq the smsSeq to set
	 */
	public void setSmsSeq(int smsSeq) {
		this.smsSeq = smsSeq;
	}

	/**
	 * @return the smsSendDate
	 */
	public String getSmsSendDate() {
		return smsSendDate;
	}

	/**
	 * @param smsSendDate the smsSendDate to set
	 */
	public void setSmsSendDate(String smsSendDate) {
		this.smsSendDate = smsSendDate;
	}

	/**
	 * @return the smsMsg
	 */
	public String getSmsMsg() {
		return smsMsg;
	}

	/**
	 * @param smsMsg the smsMsg to set
	 */
	public void setSmsMsg(String smsMsg) {
		this.smsMsg = smsMsg;
	}

	/**
	 * @return the callingNumber
	 */
	public String getCallingNumber() {
		return callingNumber;
	}

	/**
	 * @param callingNumber the callingNumber to set
	 */
	public void setCallingNumber(String callingNumber) {
		this.callingNumber = callingNumber;
	}

	/**
	 * @return the calledNumber
	 */
	public String getCalledNumber() {
		return calledNumber;
	}

	/**
	 * @param calledNumber the calledNumber to set
	 */
	public void setCalledNumber(String calledNumber) {
		this.calledNumber = calledNumber;
	}

	/**
	 * @return the smsStatus
	 */
	public String getSmsStatus() {
		return smsStatus;
	}

	/**
	 * @param smsStatus the smsStatus to set
	 */
	public void setSmsStatus(String smsStatus) {
		this.smsStatus = smsStatus;
	}

	/**
	 * @return the smsResultCode
	 */
	public String getSmsResultCode() {
		return smsResultCode;
	}

	/**
	 * @param smsResultCode the smsResultCode to set
	 */
	public void setSmsResultCode(String smsResultCode) {
		this.smsResultCode = smsResultCode;
	}

	/**
	 * @return the smsResultDate
	 */
	public String getSmsResultDate() {
		return smsResultDate;
	}

	/**
	 * @param smsResultDate the smsResultDate to set
	 */
	public void setSmsResultDate(String smsResultDate) {
		this.smsResultDate = smsResultDate;
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
	 * @return the smsSrc
	 */
	public String getSmsSrc() {
		return smsSrc;
	}

	/**
	 * @param smsSrc the smsSrc to set
	 */
	public void setSmsSrc(String smsSrc) {
		this.smsSrc = smsSrc;
	}

	/**
	 * @return the smsMethod
	 */
	public String getSmsMethod() {
		return smsMethod;
	}

	/**
	 * @param smsMethod the smsMethod to set
	 */
	public void setSmsMethod(String smsMethod) {
		this.smsMethod = smsMethod;
	}

	/**
	 * @return the smsGubun
	 */
	public String getSmsGubun() {
		return smsGubun;
	}

	/**
	 * @param smsGubun the smsGubun to set
	 */
	public void setSmsGubun(String smsGubun) {
		this.smsGubun = smsGubun;
	}

	/**
	 * @return the pinNumber
	 */
	public String getPinNumber() {
		return pinNumber;
	}

	/**
	 * @param pinNumber the pinNumber to set
	 */
	public void setPinNumber(String pinNumber) {
		this.pinNumber = pinNumber;
	}

	/**
	 * @return the openAgentId
	 */
	public String getOpenAgentId() {
		return openAgentId;
	}

	/**
	 * @param openAgentId the openAgentId to set
	 */
	public void setOpenAgentId(String openAgentId) {
		this.openAgentId = openAgentId;
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
	 * @return the smsResultNm
	 */
	public String getSmsResultNm() {
		return smsResultNm;
	}
	
	/**
	 * @param smsResultNm the smsResultNm to set
	 */
	public void setSmsResultNm(String smsResultNm) {
		this.smsResultNm = smsResultNm;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	

}
