package com.ktis.msp.pps.hdofccustmgmt.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PpsAgentVo
 * @Description : 대리점 정보 VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="PpsAgentVo")
public class PpsAgentVo extends BaseVo  implements Serializable {
	
	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;
	
	/**
	 *  TABLE_NAME 
	 */
	public static final String TABLE = "PPS_AGENT";

	/**
	 * 대리점ID:varchar2(20) <Primary Key>
	 */
	private String agentId;
	
	private String agentNm;

	/**
	 * 예치금:number(20)
	 */
	private int deposit;

	/**
	 * 예치금충전할인율:number(20,5)
	 */
	private double depositRate;

	/**
	 * 데이타충전할인율:number(20,5)
	 */
	private double dataRate;

	/**
	 * 핀구매할인율:number(20,5)
	 */
	private double pinBuyRate;

	/**
	 * 렌탈요금:number(20,5)
	 */
	private double rentalFee;

	/**
	 * 가상계좌번호:varchar2(20)
	 */
	private String virAccountId;
	
	/**
	 * 가상계좌은행명
	 */
	private String virBankNm;

	/**
	 * ADMIN_ID:varchar2(20)
	 */
	private String adminId;
	
	private String adminNm;

	/**
	 * RECORD_DATE:date(0)
	 */
	private String  recordDate;
	
	/**
	 * 전화번호
	 */
	private String telNum;
	
	/**
	 * fax 번호
	 */
	private String fax;
	
	/**
	 * 대표자명
	 */
	private String rprsenNm;
	
	/**
	 * 등록일
	 */
	private String regDttm;
	
	
	
	
	
	
	



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
	 * @return the deposit
	 */
	public int getDeposit() {
		return deposit;
	}






	/**
	 * @param deposit the deposit to set
	 */
	public void setDeposit(int deposit) {
		this.deposit = deposit;
	}






	/**
	 * @return the depositRate
	 */
	public double getDepositRate() {
		return depositRate;
	}






	/**
	 * @param depositRate the depositRate to set
	 */
	public void setDepositRate(double depositRate) {
		this.depositRate = depositRate;
	}






	/**
	 * @return the dataRate
	 */
	public double getDataRate() {
		return dataRate;
	}






	/**
	 * @param dataRate the dataRate to set
	 */
	public void setDataRate(double dataRate) {
		this.dataRate = dataRate;
	}






	/**
	 * @return the pinBuyRate
	 */
	public double getPinBuyRate() {
		return pinBuyRate;
	}






	/**
	 * @param pinBuyRate the pinBuyRate to set
	 */
	public void setPinBuyRate(double pinBuyRate) {
		this.pinBuyRate = pinBuyRate;
	}






	/**
	 * @return the rentalFee
	 */
	public double getRentalFee() {
		return rentalFee;
	}






	/**
	 * @param rentalFee the rentalFee to set
	 */
	public void setRentalFee(double rentalFee) {
		this.rentalFee = rentalFee;
	}


	/**
	 * @return the virAccountId
	 */
	public String getVirAccountId() {
		return virAccountId;
	}






	/**
	 * @param virAccountId the virAccountId to set
	 */
	public void setVirAccountId(String virAccountId) {
		this.virAccountId = virAccountId;
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
	 * @return the adminNm
	 */
	public String getAdminNm() {
		return adminNm;
	}






	/**
	 * @param adminNm the adminNm to set
	 */
	public void setAdminNm(String adminNm) {
		this.adminNm = adminNm;
	}






	/**
	 * @return the recordDate
	 */
	public String getRecordDate() {
		return recordDate;
	}






	/**
	 * @param recordDate the recordDate to set
	 */
	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}

	






	/**
	 * @return the virBankNm
	 */
	public String getVirBankNm() {
		return virBankNm;
	}






	/**
	 * @param virBankNm the virBankNm to set
	 */
	public void setVirBankNm(String virBankNm) {
		this.virBankNm = virBankNm;
	}




	/**
	 * @return the telNum
	 */
	public String getTelNum() {
		return telNum;
	}






	/**
	 * @param telNum the telNum to set
	 */
	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}






	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}






	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}






	/**
	 * @return the rprsenNm
	 */
	public String getRprsenNm() {
		return rprsenNm;
	}






	/**
	 * @param rprsenNm the rprsenNm to set
	 */
	public void setRprsenNm(String rprsenNm) {
		this.rprsenNm = rprsenNm;
	}






	/**
	 * @return the regDttm
	 */
	public String getRegDttm() {
		return regDttm;
	}






	/**
	 * @param regDttm the regDttm to set
	 */
	public void setRegDttm(String regDttm) {
		this.regDttm = regDttm;
	}






	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
