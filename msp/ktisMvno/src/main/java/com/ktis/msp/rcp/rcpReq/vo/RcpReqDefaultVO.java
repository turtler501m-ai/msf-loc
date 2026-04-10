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
package com.ktis.msp.rcp.rcpReq.vo;

import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : SampleDefaultVO.java
 * @Description : SampleDefaultVO Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2009.03.16           최초생성
 *
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2009. 03.16
 * @version 1.0
 * @see
 *
 *  Copyright (C) by MOPAS All right reserved.
 */
public class RcpReqDefaultVO extends BaseVo implements Serializable {

	/**
	 *  serialVersion UID
	 */
	private static final long serialVersionUID = 1L;

	/** 검색조건 */
    private String searchCondition = "";

    /** 검색Keyword */
    private String searchKeyword = "";

    /** 검색사용여부 */
    private String searchUseYn = "";

    /** 현재페이지 */
    private int pageIndex = 1;

    /** 페이지갯수 */
    private int pageUnit = 10;

    /** 페이지사이즈 */
    private int pageSize = 10;

    /** firstIndex */
    private int firstIndex = 1;

    /** lastIndex */
    private int lastIndex = 1;
    
	/** recordCountPerPage */
    private int recordCountPerPage = 10;

    
    /******************************/
    
    
    
    /******************************/
    
    private String reqInDayF = "";
    private String reqInDayT = "";
    private String serviceType = "";  
    private String operType = "";
    private String agentCode = "";
    private String requestStateCode = "";
    private String openNoExist = "";
    private String searchName = "";
    private String searchGbn = "";

    private String sortNo = "";
    private String resNo = "";
    private String cstmrName = "";
    private String birthDay = "";
    private String cstmrMobile = "";
    private String agentName = "";
    private String socName = "";
    private String reqInDay = "";
    private String requestStateName = "";
    private String openReqDate = "";
    private String pstateName = "";
    
    /******************************/
    

    
    public String getSearchCondition() {
		return searchCondition;
	}

	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public String getSearchUseYn() {
		return searchUseYn;
	}

	public void setSearchUseYn(String searchUseYn) {
		this.searchUseYn = searchUseYn;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageUnit() {
		return pageUnit;
	}

	public void setPageUnit(int pageUnit) {
		this.pageUnit = pageUnit;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getFirstIndex() {
		return firstIndex;
	}

	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}

	public int getLastIndex() {
		return lastIndex;
	}

	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}
	/***************************/

	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}

	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}

	public String getReqInDayF() {
		return reqInDayF;
	}

	public void setReqInDayF(String reqInDayF) {
		this.reqInDayF = reqInDayF;
	}

	public String getReqInDayT() {
		return reqInDayT;
	}

	public void setReqInDayT(String reqInDayT) {
		this.reqInDayT = reqInDayT;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getRequestStateCode() {
		return requestStateCode;
	}

	public void setRequestStateCode(String requestStateCode) {
		this.requestStateCode = requestStateCode;
	}

	public String getOpenNoExist() {
		return openNoExist;
	}

	public void setOpenNoExist(String openNoExist) {
		this.openNoExist = openNoExist;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getSearchGbn() {
		return searchGbn;
	}

	public void setSearchGbn(String searchGbn) {
		this.searchGbn = searchGbn;
	}

	public String getSortNo() {
		return sortNo;
	}

	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}

	public String getResNo() {
		return resNo;
	}

	public void setResNo(String resNo) {
		this.resNo = resNo;
	}

	public String getCstmrName() {
		return cstmrName;
	}

	public void setCstmrName(String cstmrName) {
		this.cstmrName = cstmrName;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getCstmrMobile() {
		return cstmrMobile;
	}

	public void setCstmrMobile(String cstmrMobile) {
		this.cstmrMobile = cstmrMobile;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getSocName() {
		return socName;
	}

	public void setSocName(String socName) {
		this.socName = socName;
	}

	public String getReqInDay() {
		return reqInDay;
	}

	public void setReqInDay(String reqInDay) {
		this.reqInDay = reqInDay;
	}

	public String getRequestStateName() {
		return requestStateName;
	}

	public void setRequestStateName(String requestStateName) {
		this.requestStateName = requestStateName;
	}

	public String getOpenReqDate() {
		return openReqDate;
	}

	public void setOpenReqDate(String openReqDate) {
		this.openReqDate = openReqDate;
	}

	public String getPstateName() {
		return pstateName;
	}

	public void setPstateName(String pstateName) {
		this.pstateName = pstateName;
	}

	
	
	

}
