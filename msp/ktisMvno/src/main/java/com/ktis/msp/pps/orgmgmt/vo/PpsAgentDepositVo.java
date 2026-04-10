package com.ktis.msp.pps.orgmgmt.vo;

import java.io.Serializable;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PpsAgentDepositVo
 * @Description : 대리점 예치금 입출금내역 VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="PpsAgentDepositVo")
public class PpsAgentDepositVo  extends BaseVo  implements Serializable {
	
	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;


	public static final String TABLE = "PPS_AGENT_DEPOSIT";

	/**
	 * 고유번호:number(20)
	 */
	private int depositSeq;

	/**
	 * 대리점코드:varchar2(20)
	 */
	private String agentId;

	/**
	 * 예치금구분(B:본사충전, A:가상계좌충전, R:고객요금충전, P:카드구매, D:데이타충전, O:카드출고):varchar2(20)
	 */
	private String depositType;

	/**
	 * 예치금일자:date(0)
	 */
	private String depositDate;

	/**
	 * 고객 번호:number(20)
	 */
	private int contractNum;

	/**
	 * 충전금액:number(20,5)
	 */
	private int recharge;

	/**
	 * 출금액:number(20,5)
	 */
	private int minusDeposit;

	/**
	 * 입금액:number(20,5)
	 */
	private int plusDeposit;

	/**
	 * 잔액:number(20,5)
	 */
	private int remains;

	/**
	 * 비고:varchar2(200)
	 */
	private String remark;

	/**
	 * 등록관리자:varchar2(20)
	 */
	private String adminId;
	
	/**
	 * 등록관리자명
	 */
	private String adminNm;


	/**
	 * @return the depositSeq
	 */
	public int getDepositSeq() {
		return depositSeq;
	}



	/**
	 * @param depositSeq the depositSeq to set
	 */
	public void setDepositSeq(int depositSeq) {
		this.depositSeq = depositSeq;
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



	/**
	 * @return the minusDeposit
	 */
	public int getMinusDeposit() {
		return minusDeposit;
	}



	/**
	 * @param minusDeposit the minusDeposit to set
	 */
	public void setMinusDeposit(int minusDeposit) {
		this.minusDeposit = minusDeposit;
	}



	/**
	 * @return the plusDeposit
	 */
	public int getPlusDeposit() {
		return plusDeposit;
	}



	/**
	 * @param plusDeposit the plusDeposit to set
	 */
	public void setPlusDeposit(int plusDeposit) {
		this.plusDeposit = plusDeposit;
	}



	/**
	 * @return the remains
	 */
	public int getRemains() {
		return remains;
	}



	/**
	 * @param remains the remains to set
	 */
	public void setRemains(int remains) {
		this.remains = remains;
	}



	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}



	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
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
	
	
	public String getAdminNm() {
		return adminNm;
	}



	public void setAdminNm(String adminNm) {
		this.adminNm = adminNm;
	}



	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
