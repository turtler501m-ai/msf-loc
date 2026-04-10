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
package com.ktis.msp.rcp.creditMgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : SampleDefaultVO.java
 
 *  Copyright (C) by MOPAS All right reserved.
 */
public class CreditVO extends BaseVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
	
	
	/** 신용정보 동의서 화면*/
	private String creditId;		/** 순번 **/
	private String regDate;		/** 등록일자 **/
	private String orgCode;		/** 조직코드 **/
	private String orgName;	/** 조직명 **/
	private String custName;	/** 고객명 **/
	private String telNo;			/** 연락처 **/
	private String regId;			/** 등록자ID **/
	private String regName;	/** 등록자명 **/

	/** 조직 */
	private String orgnId;			/** 조직ID */
	private String orgnNm;			/** 조직명 */
	private String inOrgnId;		/** 입고조직ID */
	private String inOrgnNm;		/** 입고조직명 */
	private String outOrgnId;		/** 출고조직ID */
	private String outOrgnNm;		/** 출고조직명 */
	private String hndstOrdYn; 		/** 주문가능여부 */
	
	/** 기타 */
	private String startDate;		/** 검색시작일시 */
	private String endDate;			/** 검색종료일시 */
	private String userId;			/** 사용자ID */
	private String userOrgnId;		/** 사용자조직ID */
	private String userOrgnNm;		/** 사용자조직명 */
	private String userOrgnTypeCd;
	
	
	public String getCreditId() {
		return creditId;
	}
	public void setCreditId(String creditId) {
		this.creditId = creditId;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getRegName() {
		return regName;
	}
	public void setRegName(String regName) {
		this.regName = regName;
	}
	public String getOrgnId() {
		return orgnId;
	}
	public void setOrgnId(String orgnId) {
		this.orgnId = orgnId;
	}
	public String getOrgnNm() {
		return orgnNm;
	}
	public void setOrgnNm(String orgnNm) {
		this.orgnNm = orgnNm;
	}
	public String getInOrgnId() {
		return inOrgnId;
	}
	public void setInOrgnId(String inOrgnId) {
		this.inOrgnId = inOrgnId;
	}
	public String getInOrgnNm() {
		return inOrgnNm;
	}
	public void setInOrgnNm(String inOrgnNm) {
		this.inOrgnNm = inOrgnNm;
	}
	public String getOutOrgnId() {
		return outOrgnId;
	}
	public void setOutOrgnId(String outOrgnId) {
		this.outOrgnId = outOrgnId;
	}
	public String getOutOrgnNm() {
		return outOrgnNm;
	}
	public void setOutOrgnNm(String outOrgnNm) {
		this.outOrgnNm = outOrgnNm;
	}
	public String getHndstOrdYn() {
		return hndstOrdYn;
	}
	public void setHndstOrdYn(String hndstOrdYn) {
		this.hndstOrdYn = hndstOrdYn;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserOrgnId() {
		return userOrgnId;
	}
	public void setUserOrgnId(String userOrgnId) {
		this.userOrgnId = userOrgnId;
	}
	public String getUserOrgnNm() {
		return userOrgnNm;
	}
	public void setUserOrgnNm(String userOrgnNm) {
		this.userOrgnNm = userOrgnNm;
	}
	public String getUserOrgnTypeCd() {
		return userOrgnTypeCd;
	}
	public void setUserOrgnTypeCd(String userOrgnTypeCd) {
		this.userOrgnTypeCd = userOrgnTypeCd;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}