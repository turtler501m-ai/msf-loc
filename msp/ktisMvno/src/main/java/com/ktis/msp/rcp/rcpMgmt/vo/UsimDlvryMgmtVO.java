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
public class UsimDlvryMgmtVO extends BaseVo implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6510970033252597452L;
	
	private String searchStartDt;
    private String searchEndDt;
    
    
    private String selfDlvryIdx = "";
    private String usimProdId = "";
    private String usimProdDtlId = "";
    private String cstmrName = "";
    private String cstmrNativeRrn = "";
	private String dlvryName = "";
	private String dlvryTel = "";
	private String dlvryTelFn ="";
	private String dlvryTelMn = "";
	private String dlvryTelRn = "";
	private String dlvryPost = "";
	private String dlvryAddr = "";
	private String dlvryAddrDtl = "";
	private String dlvryMemo = "";
	private String selfMemo = "";
	private String reqUsimSn = "";
	private String dlvryStateCode = "";
	private String selfStateCode = "";
	private String dlvryNo = "";
	private String tbCd = "";
	private String tbNm = "";
	private String contractNum = "";
	private String rvisnId = "";
	private String rvisnDttm = "";
	private String sysRdate = "";
	
	private String fileName;
	List<String> arrSeq;	
    List<UsimDlvryMgmtVO> items;
    
    private String dlvryType;
    private String usimBuySeq;
    private String ipAddr;
    private String reqBuyQnty;
	private String reqUsimSnOrg;
	private String reqUsimSn1org;
	private String reqUsimSn2org;
	private String reqUsimSn3org;
	private String reqUsimSn4org;
	private String reqUsimSn5org;
	private String reqUsimSn6org;
	private String reqUsimSn7org;
	private String reqUsimSn8org;
	private String reqUsimSn9org;
	private String reqUsimSn10org;
	private String reqUsimSn1;
	private String reqUsimSn2;
	private String reqUsimSn3;
	private String reqUsimSn4;
	private String reqUsimSn5;
	private String reqUsimSn6;
	private String reqUsimSn7;
	private String reqUsimSn8;
	private String reqUsimSn9;
	private String reqUsimSn10;
	private String saveType;
	

	public String getSaveType() {
		return saveType;
	}

	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}

	public String getReqUsimSnOrg() {
		return reqUsimSnOrg;
	}

	public void setReqUsimSnOrg(String reqUsimSnOrg) {
		this.reqUsimSnOrg = reqUsimSnOrg;
	}

	public String getReqBuyQnty() {
		return reqBuyQnty;
	}

	public void setReqBuyQnty(String reqBuyQnty) {
		this.reqBuyQnty = reqBuyQnty;
	}

	public String getReqUsimSn1org() {
		return reqUsimSn1org;
	}

	public void setReqUsimSn1org(String reqUsimSn1org) {
		this.reqUsimSn1org = reqUsimSn1org;
	}

	public String getReqUsimSn2org() {
		return reqUsimSn2org;
	}

	public void setReqUsimSn2org(String reqUsimSn2org) {
		this.reqUsimSn2org = reqUsimSn2org;
	}

	public String getReqUsimSn3org() {
		return reqUsimSn3org;
	}

	public void setReqUsimSn3org(String reqUsimSn3org) {
		this.reqUsimSn3org = reqUsimSn3org;
	}

	public String getReqUsimSn4org() {
		return reqUsimSn4org;
	}

	public void setReqUsimSn4org(String reqUsimSn4org) {
		this.reqUsimSn4org = reqUsimSn4org;
	}

	public String getReqUsimSn5org() {
		return reqUsimSn5org;
	}

	public void setReqUsimSn5org(String reqUsimSn5org) {
		this.reqUsimSn5org = reqUsimSn5org;
	}

	public String getReqUsimSn6org() {
		return reqUsimSn6org;
	}

	public void setReqUsimSn6org(String reqUsimSn6org) {
		this.reqUsimSn6org = reqUsimSn6org;
	}

	public String getReqUsimSn7org() {
		return reqUsimSn7org;
	}

	public void setReqUsimSn7org(String reqUsimSn7org) {
		this.reqUsimSn7org = reqUsimSn7org;
	}

	public String getReqUsimSn8org() {
		return reqUsimSn8org;
	}

	public void setReqUsimSn8org(String reqUsimSn8org) {
		this.reqUsimSn8org = reqUsimSn8org;
	}

	public String getReqUsimSn9org() {
		return reqUsimSn9org;
	}

	public void setReqUsimSn9org(String reqUsimSn9org) {
		this.reqUsimSn9org = reqUsimSn9org;
	}

	public String getReqUsimSn10org() {
		return reqUsimSn10org;
	}

	public void setReqUsimSn10org(String reqUsimSn10org) {
		this.reqUsimSn10org = reqUsimSn10org;
	}

	public String getReqUsimSn1() {
		return reqUsimSn1;
	}

	public void setReqUsimSn1(String reqUsimSn1) {
		this.reqUsimSn1 = reqUsimSn1;
	}

	public String getReqUsimSn2() {
		return reqUsimSn2;
	}

	public void setReqUsimSn2(String reqUsimSn2) {
		this.reqUsimSn2 = reqUsimSn2;
	}

	public String getReqUsimSn3() {
		return reqUsimSn3;
	}

	public void setReqUsimSn3(String reqUsimSn3) {
		this.reqUsimSn3 = reqUsimSn3;
	}

	public String getReqUsimSn4() {
		return reqUsimSn4;
	}

	public void setReqUsimSn4(String reqUsimSn4) {
		this.reqUsimSn4 = reqUsimSn4;
	}

	public String getReqUsimSn5() {
		return reqUsimSn5;
	}

	public void setReqUsimSn5(String reqUsimSn5) {
		this.reqUsimSn5 = reqUsimSn5;
	}

	public String getReqUsimSn6() {
		return reqUsimSn6;
	}

	public void setReqUsimSn6(String reqUsimSn6) {
		this.reqUsimSn6 = reqUsimSn6;
	}

	public String getReqUsimSn7() {
		return reqUsimSn7;
	}

	public void setReqUsimSn7(String reqUsimSn7) {
		this.reqUsimSn7 = reqUsimSn7;
	}

	public String getReqUsimSn8() {
		return reqUsimSn8;
	}

	public void setReqUsimSn8(String reqUsimSn8) {
		this.reqUsimSn8 = reqUsimSn8;
	}

	public String getReqUsimSn9() {
		return reqUsimSn9;
	}

	public void setReqUsimSn9(String reqUsimSn9) {
		this.reqUsimSn9 = reqUsimSn9;
	}

	public String getReqUsimSn10() {
		return reqUsimSn10;
	}

	public void setReqUsimSn10(String reqUsimSn10) {
		this.reqUsimSn10 = reqUsimSn10;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getUsimBuySeq() {
		return usimBuySeq;
	}

	public void setUsimBuySeq(String usimBuySeq) {
		this.usimBuySeq = usimBuySeq;
	}

	public String getDlvryType() {
		return dlvryType;
	}

	public void setDlvryType(String dlvryType) {
		this.dlvryType = dlvryType;
	}

	public String getSearchStartDt() {
		return searchStartDt;
	}

	public void setSearchStartDt(String searchStartDt) {
		this.searchStartDt = searchStartDt;
	}

	public String getSearchEndDt() {
		return searchEndDt;
	}

	public void setSearchEndDt(String searchEndDt) {
		this.searchEndDt = searchEndDt;
	}

	public String getSelfDlvryIdx() {
		return selfDlvryIdx;
	}

	public void setSelfDlvryIdx(String selfDlvryIdx) {
		this.selfDlvryIdx = selfDlvryIdx;
	}

	public String getUsimProdId() {
		return usimProdId;
	}

	public void setUsimProdId(String usimProdId) {
		this.usimProdId = usimProdId;
	}

	public String getUsimProdDtlId() {
		return usimProdDtlId;
	}

	public void setUsimProdDtlId(String usimProdDtlId) {
		this.usimProdDtlId = usimProdDtlId;
	}

	public String getCstmrName() {
		return cstmrName;
	}

	public void setCstmrName(String cstmrName) {
		this.cstmrName = cstmrName;
	}

	public String getCstmrNativeRrn() {
		return cstmrNativeRrn;
	}

	public void setCstmrNativeRrn(String cstmrNativeRrn) {
		this.cstmrNativeRrn = cstmrNativeRrn;
	}

	public String getDlvryName() {
		return dlvryName;
	}

	public void setDlvryName(String dlvryName) {
		this.dlvryName = dlvryName;
	}
	
	public String getDlvryTel() {
		return dlvryTel;
	}

	public void setDlvryTel(String dlvryTel) {
		this.dlvryTel = dlvryTel;
	}

	public String getDlvryTelFn() {
		return dlvryTelFn;
	}

	public void setDlvryTelFn(String dlvryTelFn) {
		this.dlvryTelFn = dlvryTelFn;
	}

	public String getDlvryTelMn() {
		return dlvryTelMn;
	}

	public void setDlvryTelMn(String dlvryTelMn) {
		this.dlvryTelMn = dlvryTelMn;
	}

	public String getDlvryTelRn() {
		return dlvryTelRn;
	}

	public void setDlvryTelRn(String dlvryTelRn) {
		this.dlvryTelRn = dlvryTelRn;
	}

	public String getDlvryPost() {
		return dlvryPost;
	}

	public void setDlvryPost(String dlvryPost) {
		this.dlvryPost = dlvryPost;
	}

	public String getDlvryAddr() {
		return dlvryAddr;
	}

	public void setDlvryAddr(String dlvryAddr) {
		this.dlvryAddr = dlvryAddr;
	}

	public String getDlvryAddrDtl() {
		return dlvryAddrDtl;
	}

	public void setDlvryAddrDtl(String dlvryAddrDtl) {
		this.dlvryAddrDtl = dlvryAddrDtl;
	}

	public String getDlvryMemo() {
		return dlvryMemo;
	}

	public void setDlvryMemo(String dlvryMemo) {
		this.dlvryMemo = dlvryMemo;
	}

	public String getSelfMemo() {
		return selfMemo;
	}

	public void setSelfMemo(String selfMemo) {
		this.selfMemo = selfMemo;
	}

	public String getReqUsimSn() {
		return reqUsimSn;
	}

	public void setReqUsimSn(String reqUsimSn) {
		this.reqUsimSn = reqUsimSn;
	}

	public String getDlvryStateCode() {
		return dlvryStateCode;
	}

	public void setDlvryStateCode(String dlvryStateCode) {
		this.dlvryStateCode = dlvryStateCode;
	}

	public String getSelfStateCode() {
		return selfStateCode;
	}

	public void setSelfStateCode(String selfStateCode) {
		this.selfStateCode = selfStateCode;
	}

	public String getDlvryNo() {
		return dlvryNo;
	}

	public void setDlvryNo(String dlvryNo) {
		this.dlvryNo = dlvryNo;
	}

	public String getTbCd() {
		return tbCd;
	}

	public void setTbCd(String tbCd) {
		this.tbCd = tbCd;
	}

	public String getTbNm() {
		return tbNm;
	}

	public void setTbNm(String tbNm) {
		this.tbNm = tbNm;
	}

	public String getContractNum() {
		return contractNum;
	}

	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}

	public String getRvisnId() {
		return rvisnId;
	}

	public void setRvisnId(String rvisnId) {
		this.rvisnId = rvisnId;
	}

	public String getRvisnDttm() {
		return rvisnDttm;
	}

	public void setRvisnDttm(String rvisnDttm) {
		this.rvisnDttm = rvisnDttm;
	}

	public String getSysRdate() {
		return sysRdate;
	}

	public void setSysRdate(String sysRdate) {
		this.sysRdate = sysRdate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public List<String> getArrSeq() {
		return arrSeq;
	}

	public void setArrSeq(List<String> arrSeq) {
		this.arrSeq = arrSeq;
	}

	public List<UsimDlvryMgmtVO> getItems() {
		return items;
	}

	public void setItems(List<UsimDlvryMgmtVO> items) {
		this.items = items;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
