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

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : SampleDefaultVO.java
 
 *  Copyright (C) by MOPAS All right reserved.
 */
public class AppFormMgmtVO extends BaseVo implements Serializable {
	
	private static final long serialVersionUID = 3044976242494530140L;
	
	//검색조건
	private String strtDt;
	private String endDt;
	private String procStatCd;
	private String custNm;
	private String agntCd;
	private String shopCd;
	
	//수정
	private String scanId;
	private String cMemo;
	
	private String agencyId;
	
	//
	public String getStrtDt() {
		return strtDt;
	}
	public void setStrtDt(String strtDt) {
		this.strtDt = strtDt;
	}
	public String getEndDt() {
		return endDt;
	}
	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}
	public String getProcStatCd() {
		return procStatCd;
	}
	public void setProcStatCd(String procStatCd) {
		this.procStatCd = procStatCd;
	}
	public String getCustNm() {
		return custNm;
	}
	public void setCustNm(String custNm) {
		this.custNm = custNm;
	}
	public String getAgntCd() {
		return agntCd;
	}
	public void setAgntCd(String agntCd) {
		this.agntCd = agntCd;
	}
	public String getShopCd() {
		return shopCd;
	}
	public void setShopCd(String shopCd) {
		this.shopCd = shopCd;
	}
	public String getScanId() {
		return scanId;
	}
	public void setScanId(String scanId) {
		this.scanId = scanId;
	}
	public String getcMemo() {
		return cMemo;
	}
	public void setcMemo(String cMemo) {
		this.cMemo = cMemo;
	}
	public String getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}
	
	
}
