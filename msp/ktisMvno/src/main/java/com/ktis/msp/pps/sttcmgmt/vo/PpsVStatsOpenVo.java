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
 * @Description : 개통현황 VO
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="ppsVStatsOpenVo")
public class PpsVStatsOpenVo extends BaseVo  implements Serializable {

	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;
	
	
	

	/**
	 * STATUS_DATE:undefined(0)
	 */
	private String statusDate;

	/**
	 * AGENT_ID:undefined(0)
	 */
	private String agentId;
	
	/**
	 * AGENT_NM
	 */
	private String agentNm;

	/**
	 * SOC:undefined(0)
	 */
	private String soc;
	
	/**
	 * SOC_NM 
	 */
	private String socNm;
	

	/**
	 * TOTAL_OPEN_CNT
	 */
	private int totalOpenCnt;
	
	/**
	 * OPEN_CNT:undefined(0)
	 */
	private int openCnt;

	/**
	 * STOP_CNT:undefined(0)
	 */
	private int stopCnt;

	/**
	 * CANCEL_CNT:undefined(0)
	 */
	private int cancelCnt;
	
	

	/**
	 * @return the statusDate
	 */
	public String getStatusDate() {
		return statusDate;
	}



	/**
	 * @param statusDate the statusDate to set
	 */
	public void setStatusDate(String statusDate) {
		this.statusDate = statusDate;
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
	 * @return the socNm
	 */
	public String getSocNm() {
		return socNm;
	}



	/**
	 * @param socNm the socNm to set
	 */
	public void setSocNm(String socNm) {
		this.socNm = socNm;
	}



	/**
	 * @return the openCnt
	 */
	public int getOpenCnt() {
		return openCnt;
	}



	/**
	 * @param openCnt the openCnt to set
	 */
	public void setOpenCnt(int openCnt) {
		this.openCnt = openCnt;
	}



	/**
	 * @return the stopCnt
	 */
	public int getStopCnt() {
		return stopCnt;
	}



	/**
	 * @param stopCnt the stopCnt to set
	 */
	public void setStopCnt(int stopCnt) {
		this.stopCnt = stopCnt;
	}



	/**
	 * @return the cancelCnt
	 */
	public int getCancelCnt() {
		return cancelCnt;
	}



	/**
	 * @param cancelCnt the cancelCnt to set
	 */
	public void setCancelCnt(int cancelCnt) {
		this.cancelCnt = cancelCnt;
	}

	/**
	 * @return the totalOpenCnt
	 */
	public int getTotalOpenCnt() {
		return totalOpenCnt;
	}


	/**
	 * @param totalOpenCnt the totalOpenCnt to set
	 */
	public void setTotalOpenCnt(int totalOpenCnt) {
		this.totalOpenCnt = totalOpenCnt;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
