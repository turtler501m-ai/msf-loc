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
package com.ktis.msp.rcp.custTrnsMgmt.vo;

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
public class CustTrnsVO extends BaseVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String contractNum;
	private String svcTelNo;
	private String custNm;
	private String serviceType;
	private String buyType;
	private String trnsAgrmYn;
	private String trnsAgrmDttm;
	private String procMthdCd;
	private String trnsMemo;
	private String searchCd;
	private String searchVal;
	private String subStatus;
	private String userId;
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getSvcTelNo() {
		return svcTelNo;
	}
	public void setSvcTelNo(String svcTelNo) {
		this.svcTelNo = svcTelNo;
	}
	public String getCustNm() {
		return custNm;
	}
	public void setCustNm(String custNm) {
		this.custNm = custNm;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getBuyType() {
		return buyType;
	}
	public void setBuyType(String buyType) {
		this.buyType = buyType;
	}
	public String getTrnsAgrmYn() {
		return trnsAgrmYn;
	}
	public void setTrnsAgrmYn(String trnsAgrmYn) {
		this.trnsAgrmYn = trnsAgrmYn;
	}
	public String getTrnsAgrmDttm() {
		return trnsAgrmDttm;
	}
	public void setTrnsAgrmDttm(String trnsAgrmDttm) {
		this.trnsAgrmDttm = trnsAgrmDttm;
	}
	public String getProcMthdCd() {
		return procMthdCd;
	}
	public void setProcMthdCd(String procMthdCd) {
		this.procMthdCd = procMthdCd;
	}
	public String getTrnsMemo() {
		return trnsMemo;
	}
	public void setTrnsMemo(String trnsMemo) {
		this.trnsMemo = trnsMemo;
	}
	public String getSearchCd() {
		return searchCd;
	}
	public void setSearchCd(String searchCd) {
		this.searchCd = searchCd;
	}
	public String getSearchVal() {
		return searchVal;
	}
	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
	}
	public String getSubStatus() {
		return subStatus;
	}
	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
