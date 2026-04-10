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
package com.ktis.msp.rcp.custMgmt.vo;

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
public class CustListVO extends BaseVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/*09.30 추가*/
	private String requestKey ="";
	private String cnslKey ="";
	private String sysRdate ="";
	private String reqGbnCd ="";
	private String progGbnCd ="";
	private String cstmrRelCd ="";
	private String progSttCd ="";
	private String sessionUserId="user";
	private String cstmrNativeRrn = "";
	private String reqBuyType = "";
	private String reqBuyTypeS = "";
	private String pAgentCode;
	private String reqGbn ="";
	private String cstmrRe ="";
	private String progGbn ="";
	private String progStt ="";
	private String cstmrRel="";
	private String reqCntt ="";
	private String progCntt ="";
	private String userid ="";
	private String removeFlag ="";
	
	private String lstComActvDateF = "";
	private String lstComActvDateT = "";
	private String subStatus = "";
	private String openShopCd = "";
	private String contractNum = "";
	private String customerLinkName = "";
	private String subscriberNo = "";
	private String searchGbn = "";
	private String searchName = "";
	
	private String dstaddrFn ="";
	private String dstaddrMn ="";
	private String dstaddrRn ="";
	private String callbackFn ="";
	private String callbackMn ="";
	private String callbackRn ="";
	private String text ="";
	private String usrId ="";
	private String cntpntShopId;
	private String requestMemo;
	
	// 2015-04-10 추가 ( 청소년유해차단관련 )
	private String minorYn;
	private String appBlckAgrmYn;
	private String appInstYn;
	private String instCnfmId;

	// 2015-08-12 추가 ( 모집경로, 가입비납부방법, 가입비, USIM납부방법, USIM비, 해지사유 )
	private String onOffType;
	private String joinPayMthdCd;
	private String joinPrice;
	private String usimPayMthdCd;
	private String usimPrice;
	private String canRsn;
	
	// 2015-08-19 추가 ( 판매수수료, ARPU수수료 )
	private String saleCmsnAmt;
	private String cmsnAmt;
	
	// 2016-03-02 추가
	private String sprtTp;
	
	// 추천인 추가
	private String recommendFlagNm;			// 추천인 구분명(RCP9011)
	private String recommendInfo;			// 추천인 정보
	
	// 2016-06-02 0원렌탈
	private String prodType;
	
	// 2016-09-02 업무구분추가
	private String operType;
	
	public String getRequestMemo() {
		return requestMemo;
	}
	public void setRequestMemo(String requestMemo) {
		this.requestMemo = requestMemo;
	}
	public String getRequestKey() {
		return requestKey;
	}
	public void setRequestKey(String requestKey) {
		this.requestKey = requestKey;
	}
	public String getCnslKey() {
		return cnslKey;
	}
	public void setCnslKey(String cnslKey) {
		this.cnslKey = cnslKey;
	}
	public String getSysRdate() {
		return sysRdate;
	}
	public void setSysRdate(String sysRdate) {
		this.sysRdate = sysRdate;
	}
	public String getReqGbnCd() {
		return reqGbnCd;
	}
	public void setReqGbnCd(String reqGbnCd) {
		this.reqGbnCd = reqGbnCd;
	}
	public String getProgGbnCd() {
		return progGbnCd;
	}
	public void setProgGbnCd(String progGbnCd) {
		this.progGbnCd = progGbnCd;
	}
	public String getCstmrRelCd() {
		return cstmrRelCd;
	}
	public void setCstmrRelCd(String cstmrRelCd) {
		this.cstmrRelCd = cstmrRelCd;
	}
	public String getProgSttCd() {
		return progSttCd;
	}
	public void setProgSttCd(String progSttCd) {
		this.progSttCd = progSttCd;
	}
	public String getSessionUserId() {
		return sessionUserId;
	}
	public void setSessionUserId(String sessionUserId) {
		this.sessionUserId = sessionUserId;
	}
	public String getCstmrNativeRrn() {
		return cstmrNativeRrn;
	}
	public void setCstmrNativeRrn(String cstmrNativeRrn) {
		this.cstmrNativeRrn = cstmrNativeRrn;
	}
	public String getReqBuyType() {
		return reqBuyType;
	}
	public void setReqBuyType(String reqBuyType) {
		this.reqBuyType = reqBuyType;
	}
	public String getReqBuyTypeS() {
		return reqBuyTypeS;
	}
	public void setReqBuyTypeS(String reqBuyTypeS) {
		this.reqBuyTypeS = reqBuyTypeS;
	}
	public String getpAgentCode() {
		return pAgentCode;
	}
	public void setpAgentCode(String pAgentCode) {
		this.pAgentCode = pAgentCode;
	}
	public String getReqGbn() {
		return reqGbn;
	}
	public void setReqGbn(String reqGbn) {
		this.reqGbn = reqGbn;
	}
	public String getCstmrRe() {
		return cstmrRe;
	}
	public void setCstmrRe(String cstmrRe) {
		this.cstmrRe = cstmrRe;
	}
	public String getProgGbn() {
		return progGbn;
	}
	public void setProgGbn(String progGbn) {
		this.progGbn = progGbn;
	}
	public String getProgStt() {
		return progStt;
	}
	public void setProgStt(String progStt) {
		this.progStt = progStt;
	}
	public String getCstmrRel() {
		return cstmrRel;
	}
	public void setCstmrRel(String cstmrRel) {
		this.cstmrRel = cstmrRel;
	}
	public String getReqCntt() {
		return reqCntt;
	}
	public void setReqCntt(String reqCntt) {
		this.reqCntt = reqCntt;
	}
	public String getProgCntt() {
		return progCntt;
	}
	public void setProgCntt(String progCntt) {
		this.progCntt = progCntt;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getRemoveFlag() {
		return removeFlag;
	}
	public void setRemoveFlag(String removeFlag) {
		this.removeFlag = removeFlag;
	}
	public String getLstComActvDateF() {
		return lstComActvDateF;
	}
	public void setLstComActvDateF(String lstComActvDateF) {
		this.lstComActvDateF = lstComActvDateF;
	}
	public String getLstComActvDateT() {
		return lstComActvDateT;
	}
	public void setLstComActvDateT(String lstComActvDateT) {
		this.lstComActvDateT = lstComActvDateT;
	}
	public String getSubStatus() {
		return subStatus;
	}
	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}
	public String getOpenShopCd() {
		return openShopCd;
	}
	public void setOpenShopCd(String openShopCd) {
		this.openShopCd = openShopCd;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getCustomerLinkName() {
		return customerLinkName;
	}
	public void setCustomerLinkName(String customerLinkName) {
		this.customerLinkName = customerLinkName;
	}
	public String getSubscriberNo() {
		return subscriberNo;
	}
	public void setSubscriberNo(String subscriberNo) {
		this.subscriberNo = subscriberNo;
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
	public String getDstaddrFn() {
		return dstaddrFn;
	}
	public void setDstaddrFn(String dstaddrFn) {
		this.dstaddrFn = dstaddrFn;
	}
	public String getDstaddrMn() {
		return dstaddrMn;
	}
	public void setDstaddrMn(String dstaddrMn) {
		this.dstaddrMn = dstaddrMn;
	}
	public String getDstaddrRn() {
		return dstaddrRn;
	}
	public void setDstaddrRn(String dstaddrRn) {
		this.dstaddrRn = dstaddrRn;
	}
	public String getCallbackFn() {
		return callbackFn;
	}
	public void setCallbackFn(String callbackFn) {
		this.callbackFn = callbackFn;
	}
	public String getCallbackMn() {
		return callbackMn;
	}
	public void setCallbackMn(String callbackMn) {
		this.callbackMn = callbackMn;
	}
	public String getCallbackRn() {
		return callbackRn;
	}
	public void setCallbackRn(String callbackRn) {
		this.callbackRn = callbackRn;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUsrId() {
		return usrId;
	}
	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}
	public String getCntpntShopId() {
		return cntpntShopId;
	}
	public void setCntpntShopId(String cntpntShopId) {
		this.cntpntShopId = cntpntShopId;
	}
	public String getMinorYn() {
		return minorYn;
	}
	public void setMinorYn(String minorYn) {
		this.minorYn = minorYn;
	}
	public String getAppBlckAgrmYn() {
		return appBlckAgrmYn;
	}
	public void setAppBlckAgrmYn(String appBlckAgrmYn) {
		this.appBlckAgrmYn = appBlckAgrmYn;
	}
	public String getAppInstYn() {
		return appInstYn;
	}
	public void setAppInstYn(String appInstYn) {
		this.appInstYn = appInstYn;
	}
	public String getInstCnfmId() {
		return instCnfmId;
	}
	public void setInstCnfmId(String instCnfmId) {
		this.instCnfmId = instCnfmId;
	}
	public String getOnOffType() {
		return this.onOffType;
	}
	public void setOnOffType(String onOffType) {
		this.onOffType = onOffType;
	}
	public String getJoinPayMthdCd() {
		return joinPayMthdCd;
	}
	public void setJoinPayMthdCd(String joinPayMthdCd) {
		this.joinPayMthdCd = joinPayMthdCd;
	}
	public String getJoinPrice() {
		return joinPrice;
	}
	public void setJoinPrice(String joinPrice) {
		this.joinPrice = joinPrice;
	}
	public String getUsimPayMthdCd() {
		return usimPayMthdCd;
	}
	public void setUsimPayMthdCd(String usimPayMthdCd) {
		this.usimPayMthdCd = usimPayMthdCd;
	}
	public String getUsimPrice() {
		return usimPrice;
	}
	public void setUsimPrice(String usimPrice) {
		this.usimPrice = usimPrice;
	}
	public String getCanRsn() {
		return canRsn;
	}
	public void setCanRsn(String canRsn) {
		this.canRsn = canRsn;
	}
	public String getSaleCmsnAmt() {
		return saleCmsnAmt;
	}
	public void setSaleCmsnAmt(String saleCmsnAmt) {
		this.saleCmsnAmt = saleCmsnAmt;
	}
	public String getCmsnAmt() {
		return cmsnAmt;
	}
	public void setCmsnAmt(String cmsnAmt) {
		this.cmsnAmt = cmsnAmt;
	}
	// 할인유형추가
	public String getSprtTp() {
		return sprtTp;
	}
	public void setSprtTp(String sprtTp) {
		this.sprtTp = sprtTp;
	}
	// 추천인
	public String getRecommendFlagNm() {
		return recommendFlagNm;
	}
	public void setRecommendFlagNm(String recommendFlagNm) {
		this.recommendFlagNm = recommendFlagNm;
	}
	public String getRecommendInfo() {
		return recommendInfo;
	}
	public void setRecommendInfo(String recommendInfo) {
		this.recommendInfo = recommendInfo;
	}
	// 상품구분
	public String getProdType() {
		return prodType;
	}
	public void setProdType(String prodType) {
		this.prodType = prodType;
	}
	// 업무구분추가
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	
	
}
