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
package com.ktis.msp.rcp.rcpMgmt.vo;

import java.io.Serializable;
import java.util.List;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : SampleDefaultVO.java
 
 *  Copyright (C) by MOPAS All right reserved.
 */
public class DvcChgMgmtVO extends BaseVo implements Serializable {
	
	private static final long serialVersionUID = 3044976242494530140L;
	
	//검색조건
	private String crtYm;
	private String openAgntCd;
	private String tmStatCd;
	private String tmRsltCd;
	private String srchTp;
	private String srchVal;
	private String mktAgrmYn;
	private String custNm;
	private String subscriberNo;
	private String idntNum;
	
	//수정
	private String contractNum;
	private String tmCntt;
	private Integer vocRcpId;
	private String vocPrsnId;
	private String cnslMstCd;
	private String cnslMidCd;
	private String cnslDtlCd;
	private String cnslStatCd;
	private String dvcChgType;
	private String dvcChgRsnCd;
	private String dvcChgRsnDtlCd;
	private String userId;
	private String userOrgnId;
	
	//할당처리
	List<DvcChgMgmtVO> items;

	//변경목록
	private String applDateFrom;
	private String applDateTo;
	private String searchGbn;
	private String searchName;
	private String subStatus;
	private String cntpntShopId;
	private String operType;
	private String onOffType;	
	
	public String getCrtYm() {
		return crtYm;
	}

	public void setCrtYm(String crtYm) {
		this.crtYm = crtYm;
	}

	public String getOpenAgntCd() {
		return openAgntCd;
	}

	public void setOpenAgntCd(String openAgntCd) {
		this.openAgntCd = openAgntCd;
	}

	public String getTmStatCd() {
		return tmStatCd;
	}

	public void setTmStatCd(String tmStatCd) {
		this.tmStatCd = tmStatCd;
	}

	public String getTmRsltCd() {
		return tmRsltCd;
	}

	public void setTmRsltCd(String tmRsltCd) {
		this.tmRsltCd = tmRsltCd;
	}

	public String getSrchTp() {
		return srchTp;
	}

	public void setSrchTp(String srchTp) {
		this.srchTp = srchTp;
	}

	public String getSrchVal() {
		return srchVal;
	}

	public void setSrchVal(String srchVal) {
		this.srchVal = srchVal;
	}

	public String getMktAgrmYn() {
		return mktAgrmYn;
	}

	public void setMktAgrmYn(String mktAgrmYn) {
		this.mktAgrmYn = mktAgrmYn;
	}

	public String getCustNm() {
		return custNm;
	}

	public void setCustNm(String custNm) {
		this.custNm = custNm;
	}

	public String getSubscriberNo() {
		return subscriberNo;
	}

	public void setSubscriberNo(String subscriberNo) {
		this.subscriberNo = subscriberNo;
	}

	public String getIdntNum() {
		return idntNum;
	}

	public void setIdntNum(String idntNum) {
		this.idntNum = idntNum;
	}

	public String getContractNum() {
		return contractNum;
	}

	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}

	public String getTmCntt() {
		return tmCntt;
	}

	public void setTmCntt(String tmCntt) {
		this.tmCntt = tmCntt;
	}

	public Integer getVocRcpId() {
		return vocRcpId;
	}

	public void setVocRcpId(Integer vocRcpId) {
		this.vocRcpId = vocRcpId;
	}

	public String getVocPrsnId() {
		return vocPrsnId;
	}

	public void setVocPrsnId(String vocPrsnId) {
		this.vocPrsnId = vocPrsnId;
	}

	public String getCnslMstCd() {
		return cnslMstCd;
	}

	public void setCnslMstCd(String cnslMstCd) {
		this.cnslMstCd = cnslMstCd;
	}

	public String getCnslMidCd() {
		return cnslMidCd;
	}

	public void setCnslMidCd(String cnslMidCd) {
		this.cnslMidCd = cnslMidCd;
	}

	public String getCnslDtlCd() {
		return cnslDtlCd;
	}

	public void setCnslDtlCd(String cnslDtlCd) {
		this.cnslDtlCd = cnslDtlCd;
	}

	public String getCnslStatCd() {
		return cnslStatCd;
	}

	public void setCnslStatCd(String cnslStatCd) {
		this.cnslStatCd = cnslStatCd;
	}

	public String getDvcChgType() {
		return dvcChgType;
	}

	public void setDvcChgType(String dvcChgType) {
		this.dvcChgType = dvcChgType;
	}

	public String getDvcChgRsnCd() {
		return dvcChgRsnCd;
	}

	public void setDvcChgRsnCd(String dvcChgRsnCd) {
		this.dvcChgRsnCd = dvcChgRsnCd;
	}

	public String getDvcChgRsnDtlCd() {
		return dvcChgRsnDtlCd;
	}

	public void setDvcChgRsnDtlCd(String dvcChgRsnDtlCd) {
		this.dvcChgRsnDtlCd = dvcChgRsnDtlCd;
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

	public List<DvcChgMgmtVO> getItems() {
		return items;
	}

	public void setItems(List<DvcChgMgmtVO> items) {
		this.items = items;
	}

	public String getApplDateFrom() {
		return applDateFrom;
	}

	public void setApplDateFrom(String applDateFrom) {
		this.applDateFrom = applDateFrom;
	}

	public String getApplDateTo() {
		return applDateTo;
	}

	public void setApplDateTo(String applDateTo) {
		this.applDateTo = applDateTo;
	}

	public String getSearchGbn() {
		return searchGbn;
	}

	public void setSearchGbn(String searchGbn) {
		this.searchGbn = searchGbn;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getSubStatus() {
		return subStatus;
	}

	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}

	public String getCntpntShopId() {
		return cntpntShopId;
	}

	public void setCntpntShopId(String cntpntShopId) {
		this.cntpntShopId = cntpntShopId;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public String getOnOffType() {
		return onOffType;
	}

	public void setOnOffType(String onOffType) {
		this.onOffType = onOffType;
	}

}
