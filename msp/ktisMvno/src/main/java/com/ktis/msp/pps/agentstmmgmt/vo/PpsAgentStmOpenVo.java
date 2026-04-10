package com.ktis.msp.pps.agentstmmgmt.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PpsAgentStmOpenVo
 * @Description : 대리점 개통 수수료 VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2017.05.01 김웅 생성
 * @
 * @author : 김웅
 * @Create Date : 2017.05.01
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="ppsAgentStmOpenVo")
public class PpsAgentStmOpenVo extends BaseVo  implements Serializable {
	
	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;

	/**
	 * 계약번호:VARCHAR2(10)
	 */
	private String contractNum;
	
	/**
	 * 개통번호:VARCHAR2(20)
	 */
	private String subscriberNo;

	/**
	 * 최초계약번호:VARCHAR2(10)
	 */
	private String customerId;
	
	/**
	 * 최초계약번호, 명의변경시 변경된계약번호가 들어간다. 이전 식별구분확인용:VARCHAR2(10)
	 */
	private String customerIdNew;
	
	/**
	 * 고객명:VARCHAR2(60)
	 */
	private String subLinkName;
	
	/**
	 * 주민등록번호(암호화):VARCHAR2(250)
	 */
	private String userSsn;
	
	/**
	 * 개통일자:date
	 */
	private String enterDate;
	
	/**
	 * 최초 개통시 고객식별구분:VARCHAR2(2)
	 */
	private String custIdntNoIndCd;
	
	/**
	 * 고객식별구분(최초개통시 최초식별구분이 들어가고 명변시 명변한 식별구분이 들어간다. 이전 식별구분확인용):VARCHAR2(2)
	 */
	private String custIdntNoIndCdNew;
	
	/**
	 * 요금제:VARCHAR2(9)
	 */
	private String soc;
	
	/**
	 * 1일정산시 상태:VARCHAR2(1)
	 */
	private String subStatus1;
	
	/**
	 * 15일정산시 상태:VARCHAR2(1)
	 */
	private String subStatus15;
	
	/**
	 * 1일정산시 대리점:VARCHAR2(20)
	 */
	private String agentId1;
	
	/**
	 * 15일정산시 대리점:VARCHAR2(20)
	 */
	private String agentId15;
	
	/**
	 * 1일정산시 판매점:VARCHAR2(20)
	 */
	private String agentSaleId1;
	
	/**
	 * 15일정산시 판매점:VARCHAR2(20)
	 */
	private String agentSaleId15;
	
	/**
	 * 15일 정산시 단말결합여부:CHAR(1)
	 */
	private String modelFlag15;
	
	/**
	 * 3G/LTE:VARCHAR2(10)
	 */
	private String dataType1;
	
	/**
	 * 1일 정산시 서식지수:NUMBER(5)
	 */
	private int docCnt1;
	
	/**
	 * 환수여부(중복환수방지용):CHAR(1)
	 */
	private String refundFlag;
	
	/**
	 * 환수일자:date
	 */
	private String refundDt;
	
	/**
	 * 환수사유:VARCHAR2(5)
	 */
	private String refundRsn;
	
	/**
	 * 환수관리자:VARCHAR2(20)
	 */
	private String refundAdmin;
	
	
	
	


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
	 * @return the customerIdNew
	 */
	public String getCustomerIdNew() {
		return customerIdNew;
	}






	/**
	 * @param customerIdNew the customerIdNew to set
	 */
	public void setCustomerIdNew(String customerIdNew) {
		this.customerIdNew = customerIdNew;
	}






	/**
	 * @return the subLinkName
	 */
	public String getSubLinkName() {
		return subLinkName;
	}






	/**
	 * @param subLinkName the subLinkName to set
	 */
	public void setSubLinkName(String subLinkName) {
		this.subLinkName = subLinkName;
	}






	/**
	 * @return the userSsn
	 */
	public String getUserSsn() {
		return userSsn;
	}






	/**
	 * @param userSsn the userSsn to set
	 */
	public void setUserSsn(String userSsn) {
		this.userSsn = userSsn;
	}






	/**
	 * @return the enterDate
	 */
	public String getEnterDate() {
		return enterDate;
	}






	/**
	 * @param enterDate the enterDate to set
	 */
	public void setEnterDate(String enterDate) {
		this.enterDate = enterDate;
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
	 * @return the custIdntNoIndCdNew
	 */
	public String getCustIdntNoIndCdNew() {
		return custIdntNoIndCdNew;
	}






	/**
	 * @param custIdntNoIndCdNew the custIdntNoIndCdNew to set
	 */
	public void setCustIdntNoIndCdNew(String custIdntNoIndCdNew) {
		this.custIdntNoIndCdNew = custIdntNoIndCdNew;
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
	 * @return the subStatus1
	 */
	public String getSubStatus1() {
		return subStatus1;
	}






	/**
	 * @param subStatus1 the subStatus1 to set
	 */
	public void setSubStatus1(String subStatus1) {
		this.subStatus1 = subStatus1;
	}






	/**
	 * @return the subStatus15
	 */
	public String getSubStatus15() {
		return subStatus15;
	}






	/**
	 * @param subStatus15 the subStatus15 to set
	 */
	public void setSubStatus15(String subStatus15) {
		this.subStatus15 = subStatus15;
	}






	/**
	 * @return the agentId1
	 */
	public String getAgentId1() {
		return agentId1;
	}






	/**
	 * @param agentId1 the agentId1 to set
	 */
	public void setAgentId1(String agentId1) {
		this.agentId1 = agentId1;
	}






	/**
	 * @return the agentId15
	 */
	public String getAgentId15() {
		return agentId15;
	}






	/**
	 * @param agentId15 the agentId15 to set
	 */
	public void setAgentId15(String agentId15) {
		this.agentId15 = agentId15;
	}






	/**
	 * @return the agentSaleId1
	 */
	public String getAgentSaleId1() {
		return agentSaleId1;
	}






	/**
	 * @param agentSaleId1 the agentSaleId1 to set
	 */
	public void setAgentSaleId1(String agentSaleId1) {
		this.agentSaleId1 = agentSaleId1;
	}






	/**
	 * @return the agentSaleId15
	 */
	public String getAgentSaleId15() {
		return agentSaleId15;
	}






	/**
	 * @param agentSaleId15 the agentSaleId15 to set
	 */
	public void setAgentSaleId15(String agentSaleId15) {
		this.agentSaleId15 = agentSaleId15;
	}






	/**
	 * @return the modelFlag15
	 */
	public String getModelFlag15() {
		return modelFlag15;
	}






	/**
	 * @param modelFlag15 the modelFlag15 to set
	 */
	public void setModelFlag15(String modelFlag15) {
		this.modelFlag15 = modelFlag15;
	}






	/**
	 * @return the dataType1
	 */
	public String getDataType1() {
		return dataType1;
	}






	/**
	 * @param dataType1 the dataType1 to set
	 */
	public void setDataType1(String dataType1) {
		this.dataType1 = dataType1;
	}






	/**
	 * @return the docCnt1
	 */
	public int getDocCnt1() {
		return docCnt1;
	}






	/**
	 * @param docCnt1 the docCnt1 to set
	 */
	public void setDocCnt1(int docCnt1) {
		this.docCnt1 = docCnt1;
	}






	/**
	 * @return the refundFlag
	 */
	public String getRefundFlag() {
		return refundFlag;
	}






	/**
	 * @param refundFlag the refundFlag to set
	 */
	public void setRefundFlag(String refundFlag) {
		this.refundFlag = refundFlag;
	}






	/**
	 * @return the refundDt
	 */
	public String getRefundDt() {
		return refundDt;
	}






	/**
	 * @param refundDt the refundDt to set
	 */
	public void setRefundDt(String refundDt) {
		this.refundDt = refundDt;
	}






	/**
	 * @return the refundRsn
	 */
	public String getRefundRsn() {
		return refundRsn;
	}






	/**
	 * @param refundRsn the refundRsn to set
	 */
	public void setRefundRsn(String refundRsn) {
		this.refundRsn = refundRsn;
	}






	/**
	 * @return the refundAdmin
	 */
	public String getRefundAdmin() {
		return refundAdmin;
	}






	/**
	 * @param refundAdmin the refundAdmin to set
	 */
	public void setRefundAdmin(String refundAdmin) {
		this.refundAdmin = refundAdmin;
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


	
	


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}



}
