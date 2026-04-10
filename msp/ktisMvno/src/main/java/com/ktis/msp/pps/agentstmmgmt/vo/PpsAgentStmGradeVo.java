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
@XmlRootElement(name="ppsAgentStmGradeVo")
public class PpsAgentStmGradeVo extends BaseVo implements Serializable  {
	
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
	 * 1일 정산 Grade 카드 수당:NUMBER(10, 2)
	 */
	private String cardSd1;
	
	/**
	 * 1일 정산 Grade 현금 수당:NUMBER(10, 2)
	 */
	private String cashSd1;
	
	/**
	 * 1일 정산 Grade 예치금 수당:NUMBER(10, 2)
	 */
	private String depSd1;
	
	/**
	 * 1일 정산 Grade 상태:VARCHAR2(3)
	 */
	private String status1;
	
	/**
	 * 15일 정산 Grade 카드 수당:NUMBER(10, 2)
	 */
	private String cardSd15;
	
	/**
	 * 15일 정산 Grade 현금 수당:NUMBER(10, 2)
	 */
	private String cashSd15;
	
	/**
	 * 15일 정산 Grade 예치금 수당:NUMBER(10, 2)
	 */
	private String depSd15;
	
	/**
	 * 15일 정산 Grade 상태:VARCHAR2(3)
	 */
	private String status15;
	
	/**
	 * 메모:VARCHAR2(500)
	 */
	private String remark;
	
	/**
	 * 등록관리자:VARCHAR2(20)
	 */
	private String regAdmin;
	
	/**
	 * 등록일:date
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
	 * @return the cardSd1
	 */
	public String getCardSd1() {
		return cardSd1;
	}


	/**
	 * @param cardSd1 the cardSd1 to set
	 */
	public void setCardSd1(String cardSd1) {
		this.cardSd1 = cardSd1;
	}


	/**
	 * @return the cashSd1
	 */
	public String getCashSd1() {
		return cashSd1;
	}


	/**
	 * @param cashSd1 the cashSd1 to set
	 */
	public void setCashSd1(String cashSd1) {
		this.cashSd1 = cashSd1;
	}


	/**
	 * @return the depSd1
	 */
	public String getDepSd1() {
		return depSd1;
	}


	/**
	 * @param depSd1 the depSd1 to set
	 */
	public void setDepSd1(String depSd1) {
		this.depSd1 = depSd1;
	}


	/**
	 * @return the status1
	 */
	public String getStatus1() {
		return status1;
	}


	/**
	 * @param status1 the status1 to set
	 */
	public void setStatus1(String status1) {
		this.status1 = status1;
	}


	/**
	 * @return the cardSd15
	 */
	public String getCardSd15() {
		return cardSd15;
	}


	/**
	 * @param cardSd15 the cardSd15 to set
	 */
	public void setCardSd15(String cardSd15) {
		this.cardSd15 = cardSd15;
	}


	/**
	 * @return the cashSd15
	 */
	public String getCashSd15() {
		return cashSd15;
	}


	/**
	 * @param cashSd15 the cashSd15 to set
	 */
	public void setCashSd15(String cashSd15) {
		this.cashSd15 = cashSd15;
	}


	/**
	 * @return the depSd15
	 */
	public String getDepSd15() {
		return depSd15;
	}


	/**
	 * @param depSd15 the depSd15 to set
	 */
	public void setDepSd15(String depSd15) {
		this.depSd15 = depSd15;
	}


	/**
	 * @return the status15
	 */
	public String getStatus15() {
		return status15;
	}


	/**
	 * @param status15 the status15 to set
	 */
	public void setStatus15(String status15) {
		this.status15 = status15;
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
