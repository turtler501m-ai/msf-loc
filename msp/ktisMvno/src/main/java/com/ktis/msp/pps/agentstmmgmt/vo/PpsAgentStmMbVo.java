package com.ktis.msp.pps.agentstmmgmt.vo;


import java.io.Serializable;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PpsAgentStmBasicVo
 * @Description : 대리점 기본 수수료 VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2017.05.01 김웅 생성
 * @
 * @author : 김웅
 * @Create Date : 2017.05.01
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="ppsAgentStmMbVo")
public class PpsAgentStmMbVo extends BaseVo  implements Serializable {
	
	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;

	
	
	
	
	/**
	 * 정산월:date
	 */
	private String billMonth;
	
	
	/**
	 * 계약번호:VARCHAR2(10)
	 */
	private String contractNum;
	
	/**
	 * 전화번호:VARCHAR2(13)
	 */
	private String subscriberNo;
	
	/**
	 * 명변전 고객번호:VARCHAR2(10)
	 */
	private String customerIdOld;
	
	/**
	 * 명변후 고객번호:VARCHAR2(10)
	 */
	private String customerId;
	
	/**
	 * 명변시 대리점:VARCHAR2(20)
	 */
	private String agentId;
	
	/**
	 * 명변시 판매점:VARCHAR2(20)
	 */
	private String agentSaleId;
	
	/**
	 * 고객상태:VARCHAR2(1)
	 */
	private String subStatus;
	
	/**
	 * 명변전 식별구분:VARCHAR2(2)
	 */
	private String custIdntNoIndCdOld;
	
	/**
	 * 명변후 식별구분:VARCHAR2(2)
	 */
	private String custIdntNoIndCd;
	
	/**
	 * 명의변경일자:date
	 */
	private String mbDt;
    
	/**
	 * 무료충전금액:number(10)
	 */
	private int rcgFreeAmt;
    
	/**
	 * 유료충전금액:number(10)
	 */
	private int rcgPayAmt;
    
	/**
	 * 충전금액: number(10)
	 */
	private int rcgAmt;
    
	/**
	 * 카드수수료: number(10,2)
	 */
	private int cardSd;
    
	/**
	 * 현금수수료: number(10,2)
	 */
	private int cashSd;
    
	/**
	 * 예치금수수료: number(10,2)
	 */
	private int depSd;
    
    /**
	 * 여권->여권, 여권->등록증, 등록증->등록증, 기타: VARCHAR2(3)
	 */
    private String gubun;
    
    /**
	 * 수수료상태:VARCHAR2(3)
	 */
    private String status;
    
    /**
	 * 메모:VARCHAR2(500)
	 */
    private String remark;
    
    /**
	 * 등록관리자:VARCHAR2(20)
	 */
    private String regAdmin;
    
    /**
	 * 등록일자:date
	 */
    private String regDt;






	/**
	 * @return the billMonth
	 */
	public String getBillMonth() {
		return billMonth;
	}






	/**
	 * @param billMonth the billMonth to set
	 */
	public void setBillMonth(String billMonth) {
		this.billMonth = billMonth;
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
	 * @return the subscriberNo
	 */
	public String getSubscriberNo() {
		return subscriberNo;
	}






	/**
	 * @param subscriberNo the subscriberNo to set
	 */
	public void setSubscriberNo(String subscriberNo) {
		this.subscriberNo = subscriberNo;
	}






	/**
	 * @return the customerIdOld
	 */
	public String getCustomerIdOld() {
		return customerIdOld;
	}






	/**
	 * @param customerIdOld the customerIdOld to set
	 */
	public void setCustomerIdOld(String customerIdOld) {
		this.customerIdOld = customerIdOld;
	}






	/**
	 * @return the customerId
	 */
	public String getCustomerId() {
		return customerId;
	}






	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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
	 * @return the agentSaleId
	 */
	public String getAgentSaleId() {
		return agentSaleId;
	}






	/**
	 * @param agentSaleId the agentSaleId to set
	 */
	public void setAgentSaleId(String agentSaleId) {
		this.agentSaleId = agentSaleId;
	}






	/**
	 * @return the subStatus
	 */
	public String getSubStatus() {
		return subStatus;
	}






	/**
	 * @param subStatus the subStatus to set
	 */
	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}






	/**
	 * @return the custIdntNoIndCdOld
	 */
	public String getCustIdntNoIndCdOld() {
		return custIdntNoIndCdOld;
	}






	/**
	 * @param custIdntNoIndCdOld the custIdntNoIndCdOld to set
	 */
	public void setCustIdntNoIndCdOld(String custIdntNoIndCdOld) {
		this.custIdntNoIndCdOld = custIdntNoIndCdOld;
	}






	/**
	 * @return the custIdntNoIndCd
	 */
	public String getCustIdntNoIndCd() {
		return custIdntNoIndCd;
	}






	/**
	 * @param custIdntNoIndCd the custIdntNoIndCd to set
	 */
	public void setCustIdntNoIndCd(String custIdntNoIndCd) {
		this.custIdntNoIndCd = custIdntNoIndCd;
	}






	/**
	 * @return the mbDt
	 */
	public String getMbDt() {
		return mbDt;
	}






	/**
	 * @param mbDt the mbDt to set
	 */
	public void setMbDt(String mbDt) {
		this.mbDt = mbDt;
	}






	/**
	 * @return the rcgFreeAmt
	 */
	public int getRcgFreeAmt() {
		return rcgFreeAmt;
	}






	/**
	 * @param rcgFreeAmt the rcgFreeAmt to set
	 */
	public void setRcgFreeAmt(int rcgFreeAmt) {
		this.rcgFreeAmt = rcgFreeAmt;
	}






	/**
	 * @return the rcgPayAmt
	 */
	public int getRcgPayAmt() {
		return rcgPayAmt;
	}






	/**
	 * @param rcgPayAmt the rcgPayAmt to set
	 */
	public void setRcgPayAmt(int rcgPayAmt) {
		this.rcgPayAmt = rcgPayAmt;
	}






	/**
	 * @return the rcgAmt
	 */
	public int getRcgAmt() {
		return rcgAmt;
	}






	/**
	 * @param rcgAmt the rcgAmt to set
	 */
	public void setRcgAmt(int rcgAmt) {
		this.rcgAmt = rcgAmt;
	}






	/**
	 * @return the cardSd
	 */
	public int getCardSd() {
		return cardSd;
	}






	/**
	 * @param cardSd the cardSd to set
	 */
	public void setCardSd(int cardSd) {
		this.cardSd = cardSd;
	}






	/**
	 * @return the cashSd
	 */
	public int getCashSd() {
		return cashSd;
	}






	/**
	 * @param cashSd the cashSd to set
	 */
	public void setCashSd(int cashSd) {
		this.cashSd = cashSd;
	}






	/**
	 * @return the depSd
	 */
	public int getDepSd() {
		return depSd;
	}






	/**
	 * @param depSd the depSd to set
	 */
	public void setDepSd(int depSd) {
		this.depSd = depSd;
	}






	/**
	 * @return the gubun
	 */
	public String getGubun() {
		return gubun;
	}






	/**
	 * @param gubun the gubun to set
	 */
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}






	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}






	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
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
	 * @return the regAdmin
	 */
	public String getRegAdmin() {
		return regAdmin;
	}






	/**
	 * @param regAdmin the regAdmin to set
	 */
	public void setRegAdmin(String regAdmin) {
		this.regAdmin = regAdmin;
	}






	/**
	 * @return the regDt
	 */
	public String getRegDt() {
		return regDt;
	}






	/**
	 * @param regDt the regDt to set
	 */
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}




	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}



}
