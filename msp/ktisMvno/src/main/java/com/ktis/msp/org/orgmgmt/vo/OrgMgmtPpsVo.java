/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ktis.msp.org.orgmgmt.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;


/**  
 * @Class Name : OrgMgmtPpsVo.java
 * @Description : OrgMgmtPpsVo Class
 * @Modification Information  
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2014.10.06   김웅           최초생성
 * 
 * @author 김웅
 * @since 2014.10.06
 * @version 1.0
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="orgMgmtPpsVo")
public class OrgMgmtPpsVo extends BaseVo implements Serializable {
	
	/** 조직유형 */
	final public static String ORG_TYPE_CD_HDOFC	= "10";//본사조직
	final public static String ORG_TYPE_CD_AGNCY	= "20";//대리점
	final public static String ORG_TYPE_CD_SALE_AGNCY	= "30";//판매점
	
	final public static String ORG_LVL_CD_AGNCY	= "10";//대리점
	final public static String ORG_LVL_CD_SALE_AGNCY	= "20";//판매점
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3255423502191666933L;
	
	
	private String agentId; /** ORGN_ID */
	private String agentNm; /** ORGN_NAME */
	private String deposit; /** 예치금 */
	private String depositRate; /** 예치금충전 할인율 */
	private String dataRate; /** 데이터충전 할인율 */
	private String pinBuyRate /** 선불PIN 구매 할인율 */;
	private String rentalFee;/** 렌탈요금 */
	private String virAccountId; /** 가상계좌번호 */
	private String virBankNm; /** 가상계좌은행명 */
	private String virBankCd; /** 가상계좌은행코드 */
	private String pps35DepositRate; /** RS 요금제 예치금충전 할인율 */
	private String ktAgencyId; /** KOS 전산 아이디 */
	private String agentDocFlag; /** 대리점 서식지 노출여부 */
	private String adminId;  /** 상위대리점 ID */
	private String recordDate; /** 등록/변경 날짜 */
	
	private String oRetCd; /** 프로시져 호출시 리턴 CD*/
	private String oRetMsg; /** 프로시져 호출시 리턴 MSG*/
	
	
	@Override
    public String toString() {
       return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
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
	 * @return the deposit
	 */
	public String getDeposit() {
		return deposit;
	}


	/**
	 * @param deposit the deposit to set
	 */
	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}


	/**
	 * @return the depositRate
	 */
	public String getDepositRate() {
		return depositRate;
	}


	/**
	 * @param depositRate the depositRate to set
	 */
	public void setDepositRate(String depositRate) {
		this.depositRate = depositRate;
	}
	
	/**
	 * @return the pps35DepositRate
	 */
	public String getPps35DepositRate() {
		return pps35DepositRate;
	}


	/**
	 * @param pps35DepositRate the pps35DepositRate to set
	 */
	public void setPps35DepositRate(String pps35DepositRate) {
		this.pps35DepositRate = pps35DepositRate;
	}


	/**
	 * @return the dataRate
	 */
	public String getDataRate() {
		return dataRate;
	}


	/**
	 * @param dataRate the dataRate to set
	 */
	public void setDataRate(String dataRate) {
		this.dataRate = dataRate;
	}


	/**
	 * @return the pinBuyRate
	 */
	public String getPinBuyRate() {
		return pinBuyRate;
	}


	/**
	 * @param pinBuyRate the pinBuyRate to set
	 */
	public void setPinBuyRate(String pinBuyRate) {
		this.pinBuyRate = pinBuyRate;
	}


	/**
	 * @return the rentalFee
	 */
	public String getRentalFee() {
		return rentalFee;
	}


	/**
	 * @param rentalFee the rentalFee to set
	 */
	public void setRentalFee(String rentalFee) {
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
	 * @return the virBankCd
	 */
	public String getVirBankCd() {
		return virBankCd;
	}


	/**
	 * @param virBankCd the virBankCd to set
	 */
	public void setVirBankCd(String virBankCd) {
		this.virBankCd = virBankCd;
	}
	
	/**
	 * @return the ktAgencyId
	 */
	public String getKtAgencyId() {
		return ktAgencyId;
	}


	/**
	 * @param ktAgencyId the ktAgencyId to set
	 */
	public void setKtAgencyId(String ktAgencyId) {
		this.ktAgencyId = ktAgencyId;
	}
	
	/**
	 * @return the agentDocFlag
	 */
	public String getAgentDocFlag() {
		return agentDocFlag;
	}


	/**
	 * @param agentDocFlag the agentDocFlag to set
	 */
	public void setAgentDocFlag(String agentDocFlag) {
		this.agentDocFlag = agentDocFlag;
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
	 * @return the oRetCd
	 */
	public String getoRetCd() {
		return oRetCd;
	}


	/**
	 * @param oRetCd the oRetCd to set
	 */
	public void setoRetCd(String oRetCd) {
		this.oRetCd = oRetCd;
	}


	/**
	 * @return the oRetMsg
	 */
	public String getoRetMsg() {
		return oRetMsg;
	}


	/**
	 * @param oRetMsg the oRetMsg to set
	 */
	public void setoRetMsg(String oRetMsg) {
		this.oRetMsg = oRetMsg;
	}


	
}
