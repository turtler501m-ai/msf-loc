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

import org.jdom.Element;
import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : SampleDefaultVO.java
 
 *  Copyright (C) by MOPAS All right reserved.
 */
public class UsimDirDlvryChnlVO extends BaseVo implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6510970033252597452L;
	
	private String searchStartDt;
    private String searchEndDt;
    
    private String ktOrdId ="";
    private String dvryAdrZip="";
    private String dvryAdrPrimaryLn="";
    private String dvryAdrSecondaryLn="";
    private String dlvryAdrType="";
    private String rcvCustCtn="";
    private String mrktYn="";
    private String dvryBusiOrdid="";
    private String dvryStatCd="";
    private String dvryResYn="";
    private String dvryHpTime="";
    private String dvryReqMemo="";
    private String regstDttm="";
    private String rvisnDttm="";
    private String bizCd="";
    private String acceptTime = "";
    private String UsimAmt="";
    private String rvisnId="";
    private String searchCd="";
    private String searchVal="";
    private String jobGubun="";
    private String resultCd="";
    private String resultMsg="";
	private String appEventCd = "";
	private String selfStateCode = "";
	private String svcRsltCd = "";
	private String payCd = "";
	private String dlvryTel = "";
	private String dlvryPost = "";
	private String dlvryAddr = "";
	private String dlvryAddrDtl = "";
	private String selfDlvryIdx = "";
	private String selfMemo = "";
	
	protected final static String HEADER = "commHeader";
    protected final static String BODY = "outDto";
    protected final static String RESULTCODE = "responseType";
    protected final static String RESULTCODE_REAL = "responseCode";

    protected final static String RETURN = "return";
    public final static String RESULTCODE_SUCCESS = "N";


    protected String resultCode;
    protected String responseXml;
    protected String subBodyStr = "body";
    protected String enckey;
    protected String svcName        ;//안내 메시지
    protected String svcMsg ;
    protected boolean isSuccess = false;
    protected Element body;
    protected Element root;
    protected String globalNo ;
    
	private String fileName;
	List<String> arrSeq;	
    List<UsimDirDlvryChnlVO> items;
		
    public String getSelfMemo() {
		return selfMemo;
	}

	public void setSelfMemo(String selfMemo) {
		this.selfMemo = selfMemo;
	}

	public String getSelfDlvryIdx() {
		return selfDlvryIdx;
	}

	public void setSelfDlvryIdx(String selfDlvryIdx) {
		this.selfDlvryIdx = selfDlvryIdx;
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

	public String getDlvryTel() {
		return dlvryTel;
	}

	public void setDlvryTel(String dlvryTel) {
		this.dlvryTel = dlvryTel;
	}

	public String getPayCd() {
		return payCd;
	}

	public void setPayCd(String payCd) {
		this.payCd = payCd;
	}

	public String getSvcRsltCd() {
		return svcRsltCd;
	}

	public void setSvcRsltCd(String svcRsltCd) {
		this.svcRsltCd = svcRsltCd;
	}

	public String getSelfStateCode() {
		return selfStateCode;
	}

	public void setSelfStateCode(String selfStateCode) {
		this.selfStateCode = selfStateCode;
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
	
	public String getKtOrdId() {
		return ktOrdId;
	}

	public void setKtOrdId(String ktOrdId) {
		this.ktOrdId = ktOrdId;
	}

	public String getDvryAdrZip() {
		return dvryAdrZip;
	}

	public void setDvryAdrZip(String dvryAdrZip) {
		this.dvryAdrZip = dvryAdrZip;
	}

	public String getDvryAdrPrimaryLn() {
		return dvryAdrPrimaryLn;
	}

	public void setDvryAdrPrimaryLn(String dvryAdrPrimaryLn) {
		this.dvryAdrPrimaryLn = dvryAdrPrimaryLn;
	}

	public String getDvryAdrSecondaryLn() {
		return dvryAdrSecondaryLn;
	}

	public void setDvryAdrSecondaryLn(String dvryAdrSecondaryLn) {
		this.dvryAdrSecondaryLn = dvryAdrSecondaryLn;
	}

	public String getDlvryAdrType() {
		return dlvryAdrType;
	}

	public void setDlvryAdrType(String dlvryAdrType) {
		this.dlvryAdrType = dlvryAdrType;
	}

	public String getRcvCustCtn() {
		return rcvCustCtn;
	}

	public void setRcvCustCtn(String rcvCustCtn) {
		this.rcvCustCtn = rcvCustCtn;
	}

	public String getMrktYn() {
		return mrktYn;
	}

	public void setMrktYn(String mrktYn) {
		this.mrktYn = mrktYn;
	}

	public String getDvryBusiOrdid() {
		return dvryBusiOrdid;
	}

	public void setDvryBusiOrdid(String dvryBusiOrdid) {
		this.dvryBusiOrdid = dvryBusiOrdid;
	}

	public String getDvryStatCd() {
		return dvryStatCd;
	}

	public void setDvryStatCd(String dvryStatCd) {
		this.dvryStatCd = dvryStatCd;
	}

	public String getDvryResYn() {
		return dvryResYn;
	}

	public void setDvryResYn(String dvryResYn) {
		this.dvryResYn = dvryResYn;
	}

	public String getDvryHpTime() {
		return dvryHpTime;
	}

	public void setDvryHpTime(String dvryHpTime) {
		this.dvryHpTime = dvryHpTime;
	}

	public String getDvryReqMemo() {
		return dvryReqMemo;
	}

	public void setDvryReqMemo(String dvryReqMemo) {
		this.dvryReqMemo = dvryReqMemo;
	}

	public String getRegstDttm() {
		return regstDttm;
	}

	public void setRegstDttm(String regstDttm) {
		this.regstDttm = regstDttm;
	}

	public String getBizCd() {
		return bizCd;
	}

	public void setBizCd(String bizCd) {
		this.bizCd = bizCd;
	}
	
	public String getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(String acceptTime) {
		this.acceptTime = acceptTime;
	}

	public String getUsimAmt() {
		return UsimAmt;
	}

	public void setUsimAmt(String usimAmt) {
		UsimAmt = usimAmt;
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

	public String getJobGubun() {
		return jobGubun;
	}

	public void setJobGubun(String jobGubun) {
		this.jobGubun = jobGubun;
	}

	public String getResultCd() {
		return resultCd;
	}

	public void setResultCd(String resultCd) {
		this.resultCd = resultCd;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
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

	public List<UsimDirDlvryChnlVO> getItems() {
		return items;
	}

	public void setItems(List<UsimDirDlvryChnlVO> items) {
		this.items = items;
	}
	
	public String getAppEventCd() {
		return appEventCd;
	}

	public void setAppEventCd(String appEventCd) {
		this.appEventCd = appEventCd;
	}
	

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResponseXml() {
		return responseXml;
	}

	public void setResponseXml(String responseXml) {
		this.responseXml = responseXml;
	}

	public String getSubBodyStr() {
		return subBodyStr;
	}

	public void setSubBodyStr(String subBodyStr) {
		this.subBodyStr = subBodyStr;
	}

	public String getEnckey() {
		return enckey;
	}

	public void setEnckey(String enckey) {
		this.enckey = enckey;
	}

	public String getSvcName() {
		return svcName;
	}

	public void setSvcName(String svcName) {
		this.svcName = svcName;
	}

	public String getSvcMsg() {
		return svcMsg;
	}

	public void setSvcMsg(String svcMsg) {
		this.svcMsg = svcMsg;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public void setBody(Element body) {
		this.body = body;
	}

	public Element getRoot() {
		return root;
	}

	public void setRoot(Element root) {
		this.root = root;
	}

	public String getGlobalNo() {
		return globalNo;
	}

	public void setGlobalNo(String globalNo) {
		this.globalNo = globalNo;
	}

	public static String getHeader() {
		return HEADER;
	}

	public static String getBody() {
		return BODY;
	}

	public static String getResultcode() {
		return RESULTCODE;
	}

	public static String getResultcodeReal() {
		return RESULTCODE_REAL;
	}

	public static String getReturn() {
		return RETURN;
	}

	public static String getResultcodeSuccess() {
		return RESULTCODE_SUCCESS;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
