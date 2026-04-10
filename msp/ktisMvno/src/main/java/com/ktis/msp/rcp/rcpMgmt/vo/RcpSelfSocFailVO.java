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
 * @Class Name : RcpSelfSocFailVO.java
 * @Description : RcpSelfSocFailVO Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2021.11.16           최초생성
 *
 * @author
 * @since 
 * @version 1.0
 * @see
 *
 *  Copyright (C) by MOPAS All right reserved.
 */
public class RcpSelfSocFailVO extends BaseVo implements Serializable {
	
	public String strDate;
	public String endDate;
    public String contractNum;
    public String procYn;
    
    public String prcsMdlInd;
    public String sysRdate;
    public String tSocNm;
    
    public String custNm;
    public String procDate;
    public String procId;
    public String procMemo;
        
    public String aSocNm;
    public String resultCd;
    public String rstNm;
    public String succYn;
    public String ctn;
    public String gender;
    public String lstComActvDate;
    public String age;
    public String openAgntNm;
    public String onOffType;
    public String fstUsimOrgNm;
    public String aSocAmnt;
    public String tSocAmnt;
    public String prcsSbst;
    public String succNm;
    public String chgType;
    public String chgTypeNm;

	// 페이징
	public int TOTAL_COUNT;
	public String ROW_NUM;
	public String LINENUM;

	//재처리
	private String tSocCd;
	private String sysDt;
	private String seq;
	private String customerId;
	private String ncn;
	private String userId;
	private String accessIp;
	private String globalNo;
	private String rsltCd;
	private String rtySeq;

	public int getTOTAL_COUNT() {
		return TOTAL_COUNT;
	}
	public void setTOTAL_COUNT(int tOTAL_COUNT) {
		TOTAL_COUNT = tOTAL_COUNT;
	}
	public String getROW_NUM() {
		return ROW_NUM;
	}
	public void setROW_NUM(String rOW_NUM) {
		ROW_NUM = rOW_NUM;
	}
	public String getLINENUM() {
		return LINENUM;
	}
	public void setLINENUM(String lINENUM) {
		LINENUM = lINENUM;
	}
	public String getPrcsMdlInd() {
		return prcsMdlInd;
	}
	public void setPrcsMdlInd(String prcsMdlInd) {
		this.prcsMdlInd = prcsMdlInd;
	}
	public String getSysRdate() {
		return sysRdate;
	}
	public void setSysRdate(String sysRdate) {
		this.sysRdate = sysRdate;
	}
	public String gettSocNm() {
		return tSocNm;
	}
	public void settSocNm(String tSocNm) {
		this.tSocNm = tSocNm;
	}
	public String getCustNm() {
		return custNm;
	}
	public void setCustNm(String custNm) {
		this.custNm = custNm;
	}
	public String getProcDate() {
		return procDate;
	}
	public void setProcDate(String procDate) {
		this.procDate = procDate;
	}
	public String getProcId() {
		return procId;
	}
	public void setProcId(String procId) {
		this.procId = procId;
	}
	public String getProcMemo() {
		return procMemo;
	}
	public void setProcMemo(String procMemo) {
		this.procMemo = procMemo;
	}
	public String getStrDate() {
		return strDate;
	}
	public void setStrDate(String strDate) {
		this.strDate = strDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getProcYn() {
		return procYn;
	}
	public void setProcYn(String procYn) {
		this.procYn = procYn;
	}
	public String getaSocNm() {
		return aSocNm;
	}
	public void setaSocNm(String aSocNm) {
		this.aSocNm = aSocNm;
	}
	public String getResultCd() {
		return resultCd;
	}
	public void setResultCd(String resultCd) {
		this.resultCd = resultCd;
	}
	public String getRstNm() {
		return rstNm;
	}
	public void setRstNm(String rstNm) {
		this.rstNm = rstNm;
	}
	public String getSuccYn() {
		return succYn;
	}
	public void setSuccYn(String succYn) {
		this.succYn = succYn;
	}
	public String getCtn() {
		return ctn;
	}
	public void setCtn(String ctn) {
		this.ctn = ctn;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getLstComActvDate() {
		return lstComActvDate;
	}
	public void setLstComActvDate(String lstComActvDate) {
		this.lstComActvDate = lstComActvDate;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getOpenAgntNm() {
		return openAgntNm;
	}
	public void setOpenAgntNm(String openAgntNm) {
		this.openAgntNm = openAgntNm;
	}
	public String getOnOffType() {
		return onOffType;
	}
	public void setOnOffType(String onOffType) {
		this.onOffType = onOffType;
	}
	public String getFstUsimOrgNm() {
		return fstUsimOrgNm;
	}
	public void setFstUsimOrgNm(String fstUsimOrgNm) {
		this.fstUsimOrgNm = fstUsimOrgNm;
	}
	public String getaSocAmnt() {
		return aSocAmnt;
	}
	public void setaSocAmnt(String aSocAmnt) {
		this.aSocAmnt = aSocAmnt;
	}
	public String gettSocAmnt() {
		return tSocAmnt;
	}
	public void settSocAmnt(String tSocAmnt) {
		this.tSocAmnt = tSocAmnt;
	}
	public String getPrcsSbst() {
		return prcsSbst;
	}
	public void setPrcsSbst(String prcsSbst) {
		this.prcsSbst = prcsSbst;
	}
	public String getSuccNm() {
		return succNm;
	}
	public void setSuccNm(String succNm) {
		this.succNm = succNm;
	}
	public String getChgType() {
		return chgType;
	}
	public void setChgType(String chgType) {
		this.chgType = chgType;
	}
	public String getChgTypeNm() {
		return chgTypeNm;
	}
	public void setChgTypeNm(String chgTypeNm) {
		this.chgTypeNm = chgTypeNm;
	}

	public String gettSocCd() {
		return tSocCd;
	}

	public void settSocCd(String tSocCd) {
		this.tSocCd = tSocCd;
	}

	public String getSysDt() {
		return sysDt;
	}

	public void setSysDt(String sysDt) {
		this.sysDt = sysDt;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getNcn() {
		return ncn;
	}

	public void setNcn(String ncn) {
		this.ncn = ncn;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccessIp() {
		return accessIp;
	}

	public void setAccessIp(String accessIp) {
		this.accessIp = accessIp;
	}

	public String getGlobalNo() {
		return globalNo;
	}

	public void setGlobalNo(String globalNo) {
		this.globalNo = globalNo;
	}

	public String getRsltCd() {
		return rsltCd;
	}

	public void setRsltCd(String rsltCd) {
		this.rsltCd = rsltCd;
	}

	public String getRtySeq() {
		return rtySeq;
	}

	public void setRtySeq(String rtySeq) {
		this.rtySeq = rtySeq;
	}

}
