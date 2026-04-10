package com.ktis.msp.pps.sttcmgmt.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PpsVStatsMinusDepositVo
 * @Description : 예치금 출금내역 VO
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="ppsVStatsMinusDepositVo")
public class PpsVStatsMinusDepositVo  extends BaseVo  implements Serializable {

	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;
	
	

	/**
	 * DEPOSIT_DATE:date(0)
	 */
	private String depositDate;

	/**
	 * AGENT_ID:varchar2(20)
	 */
	private String agentId;
	
	private String agentNm;

	/**
	 * DEPOSIT_TYPE:varchar2(20)
	 */
	private String depositType;
	
	/**
	 * DEPOSIT_TYPE_NM
	 */
	private String depositTypeNm;

	/**
	 * RECHARGE:number(0)
	 */
	private int recharge;
	
	
	

	/**
	 * @return the depositDate
	 */
	public String getDepositDate() {
		return depositDate;
	}




	/**
	 * @param depositDate the depositDate to set
	 */
	public void setDepositDate(String depositDate) {
		this.depositDate = depositDate;
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
	 * @return the depositType
	 */
	public String getDepositType() {
		return depositType;
	}




	/**
	 * @param depositType the depositType to set
	 */
	public void setDepositType(String depositType) {
		this.depositType = depositType;
	}




	/**
	 * @return the depositTypeNm
	 */
	public String getDepositTypeNm() {
		return depositTypeNm;
	}




	/**
	 * @param depositTypeNm the depositTypeNm to set
	 */
	public void setDepositTypeNm(String depositTypeNm) {
		this.depositTypeNm = depositTypeNm;
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




	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}


}
