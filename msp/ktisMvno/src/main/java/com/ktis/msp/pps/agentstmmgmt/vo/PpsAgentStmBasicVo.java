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
@XmlRootElement(name="ppsAgentStmBasicVo")
public class PpsAgentStmBasicVo extends BaseVo  implements Serializable {
	
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
	 * 기본카드수수료:NUMBER(10, 2)
	 */
	private int basicCardSd;
	
	/**
	 * 기본현금수수료:NUMBER(10, 2)
	 */
	private int basicCashSd;
	
	/**
	 * 기본예치금수수료:NUMBER(10, 2)
	 */
	private int basicDepSd;
	
	/**
	 * 기본수수료 상태:VARCHAR(3)
	 */
	private String basicStatus;      
	
	/**
	 * USIM카드수수료:NUMBER(10, 2)
	 */
	private int usimCardSd;
	
	/**
	 * USIM현금수수료:NUMBER(10, 2)
	 */
	private int usimCashSd;
	
	/**
	 * USIM예치금수수료:NUMBER(10, 2)
	 */
	private int usimDepSd;
	
	/**
	 * USIM수수료상태:VARCHAR(3)
	 */
	private String usimStatus;   
	
	/**
	 * 메모:VARCHAR(500)
	 */
	private String remark;
	
	/**
	 * 등록관리자:VARCHAR(20)
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
	 * @return the basicCardSd
	 */
	public int getBasicCardSd() {
		return basicCardSd;
	}






	/**
	 * @param basicCardSd the basicCardSd to set
	 */
	public void setBasicCardSd(int basicCardSd) {
		this.basicCardSd = basicCardSd;
	}






	/**
	 * @return the basicCashSd
	 */
	public int getBasicCashSd() {
		return basicCashSd;
	}






	/**
	 * @param basicCashSd the basicCashSd to set
	 */
	public void setBasicCashSd(int basicCashSd) {
		this.basicCashSd = basicCashSd;
	}






	/**
	 * @return the basicDepSd
	 */
	public int getBasicDepSd() {
		return basicDepSd;
	}






	/**
	 * @param basicDepSd the basicDepSd to set
	 */
	public void setBasicDepSd(int basicDepSd) {
		this.basicDepSd = basicDepSd;
	}






	/**
	 * @return the basicStatus
	 */
	public String getBasicStatus() {
		return basicStatus;
	}






	/**
	 * @param basicStatus the basicStatus to set
	 */
	public void setBasicStatus(String basicStatus) {
		this.basicStatus = basicStatus;
	}






	/**
	 * @return the usimCardSd
	 */
	public int getUsimCardSd() {
		return usimCardSd;
	}






	/**
	 * @param usimCardSd the usimCardSd to set
	 */
	public void setUsimCardSd(int usimCardSd) {
		this.usimCardSd = usimCardSd;
	}






	/**
	 * @return the usimCashSd
	 */
	public int getUsimCashSd() {
		return usimCashSd;
	}






	/**
	 * @param usimCashSd the usimCashSd to set
	 */
	public void setUsimCashSd(int usimCashSd) {
		this.usimCashSd = usimCashSd;
	}






	/**
	 * @return the usimDepSd
	 */
	public int getUsimDepSd() {
		return usimDepSd;
	}






	/**
	 * @param usimDepSd the usimDepSd to set
	 */
	public void setUsimDepSd(int usimDepSd) {
		this.usimDepSd = usimDepSd;
	}






	/**
	 * @return the usimStatus
	 */
	public String getUsimStatus() {
		return usimStatus;
	}






	/**
	 * @param usimStatus the usimStatus to set
	 */
	public void setUsimStatus(String usimStatus) {
		this.usimStatus = usimStatus;
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
