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

public class RcpSimpVO extends BaseVo implements Serializable {
	
	//serialVersionUID
	private static final long serialVersionUID = 6760215222094556206L;
	
	private String requestKey;
	private String resNo;
	private String appEventCd;
	private String osstPayDay;
	private String osstPayType;
	// 개통SMS
	private String msgType;
	private String dstaddr;
	private String callback;
	private String subject;
	private String text;
	private String expireTime;
	private String templateId;
	// SMS발송이력
	private String searchStartDt;
	private String searchEndDt;
	private String searchGb;
	private String searchVal;
	
	public String getRequestKey() {
		return requestKey;
	}
	public void setRequestKey(String requestKey) {
		this.requestKey = requestKey;
	}
	public String getResNo() {
		return resNo;
	}
	public void setResNo(String resNo) {
		this.resNo = resNo;
	}
	public String getAppEventCd() {
		return appEventCd;
	}
	public void setAppEventCd(String appEventCd) {
		this.appEventCd = appEventCd;
	}
	public String getOsstPayDay() {
		return osstPayDay;
	}
	public void setOsstPayDay(String osstPayDay) {
		this.osstPayDay = osstPayDay;
	}
	public String getOsstPayType() {
		return osstPayType;
	}
	public void setOsstPayType(String osstPayType) {
		this.osstPayType = osstPayType;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getDstaddr() {
		return dstaddr;
	}
	public void setDstaddr(String dstaddr) {
		this.dstaddr = dstaddr;
	}
	public String getCallback() {
		return callback;
	}
	public void setCallback(String callback) {
		this.callback = callback;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
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
	public String getSearchGb() {
		return searchGb;
	}
	public void setSearchGb(String searchGb) {
		this.searchGb = searchGb;
	}
	public String getSearchVal() {
		return searchVal;
	}
	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
	}
	
	
	
	
}
