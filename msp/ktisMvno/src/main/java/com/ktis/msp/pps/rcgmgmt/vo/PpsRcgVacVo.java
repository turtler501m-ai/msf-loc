package com.ktis.msp.pps.rcgmgmt.vo;

import java.io.Serializable;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PpsRcgVacVo
 * @Description : 가상계좌입금내역 VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="ppsRcgVacVo")
public class PpsRcgVacVo extends BaseVo  implements Serializable {
	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;

	

	/**
	 * 고유번호:number(20)
	 */
	private int vacSeq;

	/**
	 * 거래일자:varchar2(60)
	 */
	private String txDate;

	/**
	 * 거래시간:varchar2(60)
	 */
	private String txTime;

	/**
	 * 전문일련번호:varchar2(60)
	 */
	private String jmSeqNo;

	/**
	 * 전문종류:varchar2(60)
	 */
	private String txType;

	/**
	 * 거래코드:varchar2(60)
	 */
	private String txCode;

	/**
	 * 기관코드:varchar2(60)
	 */
	private String mallCode;

	/**
	 * 공급업체코드:varchar2(60)
	 */
	private String subMall;

	/**
	 * 가상계좌은행코드:varchar2(60)
	 */
	private String bankCode;

	/**
	 * 가상계좌번호:varchar2(60)
	 */
	private String vaccNo;

	/**
	 * 고객명:varchar2(60)
	 */
	private String custName;

	/**
	 * 입금의뢰인:varchar2(60)
	 */
	private String requester;

	/**
	 * 입금은행코드:varchar2(60)
	 */
	private String payBankCode;

	/**
	 * 입금은행명:varchar2(60)
	 */
	private String payBankName;

	/**
	 * 입금은행지점명:varchar2(60)
	 */
	private String branchName;

	/**
	 * 총금액:varchar2(60)
	 */
	private String payTotAmt;

	/**
	 * 현금금액:varchar2(60)
	 */
	private String cash;

	/**
	 * 수표금액:varchar2(60)
	 */
	private String check1;

	/**
	 * 입금매체:varchar2(60)
	 */
	private String payMedia;

	/**
	 * 오류메세지:varchar2(500)
	 */
	private String errorMsg;

	/**
	 * 이용자구분(A:AGENT, U:USER, N:미사용):varchar2(10)
	 */
	private String userType;

	/**
	 * 은행명:varchar2(20)
	 */
	private String bankName;

	/**
	 * 계약번호:number(9)
	 */
	private int contractNum;

	/**
	 * 대리점코드:varchar2(20)
	 */
	private String agentId;

	/**
	 * 결과코드:varchar2(20)
	 */
	private String endCode;

	/**
	 * 결과메세지:varchar2(500)
	 */
	private String endMsg;

	/**
	 * 충전고유번호:number(20)
	 */
	private int rcgSeq;
	
	
	

	
	/**
	 * @return the vacSeq
	 */
	public int getVacSeq() {
		return vacSeq;
	}





	/**
	 * @param vacSeq the vacSeq to set
	 */
	public void setVacSeq(int vacSeq) {
		this.vacSeq = vacSeq;
	}





	/**
	 * @return the txDate
	 */
	public String getTxDate() {
		return txDate;
	}





	/**
	 * @param txDate the txDate to set
	 */
	public void setTxDate(String txDate) {
		this.txDate = txDate;
	}





	/**
	 * @return the txTime
	 */
	public String getTxTime() {
		return txTime;
	}





	/**
	 * @param txTime the txTime to set
	 */
	public void setTxTime(String txTime) {
		this.txTime = txTime;
	}





	/**
	 * @return the jmSeqNo
	 */
	public String getJmSeqNo() {
		return jmSeqNo;
	}





	/**
	 * @param jmSeqNo the jmSeqNo to set
	 */
	public void setJmSeqNo(String jmSeqNo) {
		this.jmSeqNo = jmSeqNo;
	}





	/**
	 * @return the txType
	 */
	public String getTxType() {
		return txType;
	}





	/**
	 * @param txType the txType to set
	 */
	public void setTxType(String txType) {
		this.txType = txType;
	}





	/**
	 * @return the txCode
	 */
	public String getTxCode() {
		return txCode;
	}





	/**
	 * @param txCode the txCode to set
	 */
	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}





	/**
	 * @return the mallCode
	 */
	public String getMallCode() {
		return mallCode;
	}





	/**
	 * @param mallCode the mallCode to set
	 */
	public void setMallCode(String mallCode) {
		this.mallCode = mallCode;
	}





	/**
	 * @return the subMall
	 */
	public String getSubMall() {
		return subMall;
	}





	/**
	 * @param subMall the subMall to set
	 */
	public void setSubMall(String subMall) {
		this.subMall = subMall;
	}





	/**
	 * @return the bankCode
	 */
	public String getBankCode() {
		return bankCode;
	}





	/**
	 * @param bankCode the bankCode to set
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}





	/**
	 * @return the vaccNo
	 */
	public String getVaccNo() {
		return vaccNo;
	}





	/**
	 * @param vaccNo the vaccNo to set
	 */
	public void setVaccNo(String vaccNo) {
		this.vaccNo = vaccNo;
	}





	/**
	 * @return the custName
	 */
	public String getCustName() {
		return custName;
	}





	/**
	 * @param custName the custName to set
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}





	/**
	 * @return the requester
	 */
	public String getRequester() {
		return requester;
	}





	/**
	 * @param requester the requester to set
	 */
	public void setRequester(String requester) {
		this.requester = requester;
	}





	/**
	 * @return the payBankCode
	 */
	public String getPayBankCode() {
		return payBankCode;
	}





	/**
	 * @param payBankCode the payBankCode to set
	 */
	public void setPayBankCode(String payBankCode) {
		this.payBankCode = payBankCode;
	}





	/**
	 * @return the payBankName
	 */
	public String getPayBankName() {
		return payBankName;
	}





	/**
	 * @param payBankName the payBankName to set
	 */
	public void setPayBankName(String payBankName) {
		this.payBankName = payBankName;
	}





	/**
	 * @return the branchName
	 */
	public String getBranchName() {
		return branchName;
	}





	/**
	 * @param branchName the branchName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}





	/**
	 * @return the payTotAmt
	 */
	public String getPayTotAmt() {
		return payTotAmt;
	}





	/**
	 * @param payTotAmt the payTotAmt to set
	 */
	public void setPayTotAmt(String payTotAmt) {
		this.payTotAmt = payTotAmt;
	}





	/**
	 * @return the cash
	 */
	public String getCash() {
		return cash;
	}





	/**
	 * @param cash the cash to set
	 */
	public void setCash(String cash) {
		this.cash = cash;
	}





	/**
	 * @return the check1
	 */
	public String getCheck1() {
		return check1;
	}





	/**
	 * @param check1 the check1 to set
	 */
	public void setCheck1(String check1) {
		this.check1 = check1;
	}





	/**
	 * @return the payMedia
	 */
	public String getPayMedia() {
		return payMedia;
	}





	/**
	 * @param payMedia the payMedia to set
	 */
	public void setPayMedia(String payMedia) {
		this.payMedia = payMedia;
	}





	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}





	/**
	 * @param errorMsg the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}





	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}





	/**
	 * @param userType the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}





	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}





	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
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
	 * @return the endCode
	 */
	public String getEndCode() {
		return endCode;
	}





	/**
	 * @param endCode the endCode to set
	 */
	public void setEndCode(String endCode) {
		this.endCode = endCode;
	}





	/**
	 * @return the endMsg
	 */
	public String getEndMsg() {
		return endMsg;
	}





	/**
	 * @param endMsg the endMsg to set
	 */
	public void setEndMsg(String endMsg) {
		this.endMsg = endMsg;
	}





	/**
	 * @return the rcgSeq
	 */
	public int getRcgSeq() {
		return rcgSeq;
	}





	/**
	 * @param rcgSeq the rcgSeq to set
	 */
	public void setRcgSeq(int rcgSeq) {
		this.rcgSeq = rcgSeq;
	}





	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	
}