package com.ktis.msp.pps.smsmgmt.vo;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PpsSmsDumpVo
 * @Description : sms전송내역 VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="PpsSmsDumpVo")
public class PpsSmsDumpGrpVo extends BaseVo  implements Serializable {
	
	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;

	
	/**
	 * pps_sms_dump 생성고유번호:number(20)
	 */
	private int smsSeq;

	/**
	 * sms전송요청시간:Date(0)
	 */
	private String smsSendDate;

	/**
	 * sms제목:varchar2(255)
	 */
	private String smsTitle;

	/**
	 * LMS제목:varchar2(255)
	 */
	private String lmsTitle;

	/**
	 * sms내용:varchar2(4000)
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
	 * sms전송요청프로시져:varchar2(40)
	 */
	private String smsMethod;

	/**
	 * SMS/LMS구분:varchar2(5)
	 */
	private String smsType;

	/**
	 * 전송대리점:varchar(20)
	 */
	private String agentId;

	/**
	 * 전송계약번호:varchar2(10)
	 */
	private String contractNum;
	/**
	 * sms전송그룹seq:number(20)
	 */
    private int dumpSeq;
    
    /**
     * sms전송요청여부:char(1)
     */
    private String smsStatus;
    
    /**
     * sms전송요청관리자:varchar2(20)
     */
    private String adminId;
 	
	

	
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
	 * @return the smsTitle
	 */
	public String getSmsTitle() {
		return smsTitle;
	}



	/**
	 * @param smsTitle the smsTitle to set
	 */
	public void setSmsTitle(String smsTitle) {
		this.smsTitle = smsTitle;
	}



	/**
	 * @return the lmsTitle
	 */
	public String getLmsTitle() {
		return lmsTitle;
	}



	/**
	 * @param lmsTitle the lmsTitle to set
	 */
	public void setLmsTitle(String lmsTitle) {
		this.lmsTitle = lmsTitle;
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
	 * @return the smsType
	 */
	public String getSmsType() {
		return smsType;
	}



	/**
	 * @param smsType the smsType to set
	 */
	public void setSmsType(String smsType) {
		this.smsType = smsType;
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
	 * @return the contractNum
	 */
	public String getContractNum() {
		return contractNum;
	}



	/**
	 * @param contractNum the contractNum to set
	 */
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}



	/**
	 * @return the dumpSeq
	 */
	public int getDumpSeq() {
		return dumpSeq;
	}



	/**
	 * @param dumpSeq the dumpSeq to set
	 */
	public void setDumpSeq(int dumpSeq) {
		this.dumpSeq = dumpSeq;
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
	 * @return the setSmsSendDate
	 */
	public String getSetSmsSendDate() {
		return smsSendDate;
	}



	/**
	 * @param smsSeq the smsSeq to set
	 */
	public void setSmsSendDate(String smsSendDate) {
		this.smsSendDate = smsSendDate;
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
