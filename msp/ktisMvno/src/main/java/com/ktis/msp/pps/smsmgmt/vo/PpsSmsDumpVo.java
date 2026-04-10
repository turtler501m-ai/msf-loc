package com.ktis.msp.pps.smsmgmt.vo;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PpsSmsDumpGrpVo
 * @Description : sms전송내역 group VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="PpsSmsDumpGrpVo")
public class PpsSmsDumpVo extends BaseVo  implements Serializable {
	
	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;

	/**
	 * sms전송요청시간:Date(0)
	 */
	private String smsSendDate;

	/**
	 * sms제목:varchar2(255)
	 */
	private String smsTitle;

	/**
	 * sms전송그룹seq:number(20)
	 */
    private int dumpSeq;
    
    /**
     * sms전송요청관리자:varchar2(20)
     */
    private String adminId;
 	
	

	
	
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
