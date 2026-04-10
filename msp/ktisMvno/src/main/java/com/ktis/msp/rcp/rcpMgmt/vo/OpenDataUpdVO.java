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
public class OpenDataUpdVO extends BaseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 계약번호
	 */
	private String contractNum;
	
	/**
	 * 가입신청키
	 */
	private String requestKey;
	
	/**
	 * 예약번호 
	 */
	private String resNo;
	
	/**
	 * 스캔아이디
	 */
	private String scanId;
	
	/**
	 * 성공여부
	 */
	private String sucYn;
	
	/**
	 * 시작일자
	 */
	private String strtDt;
	
	/**
	 * 종료일자
	 */
	private String endDt;
	
	/**
	 * 요청사유
	 */
	private String reqRsn;
	
	/**
	 * 오류메세지
	 */
	private String errMsg;
	
	/**
	 * 등록자ID
	 */
	private String regstId;
	
	/**
	 * 등록자명
	 */
	private String regstNm;
	
	/**
	 * 일련번호
	 */
	private Integer seqNum;
	
	/**
	 * 시작일자
	 */
	private String sStrtDt;
	
	/**
	 * 종료일자
	 */
	private String sEndDt;
	
	/**
	 * 검색구분
	 */
	private String searchType;
	
	/**
	 * 검색어
	 */
	private String searchWord;

	public String getContractNum() {
		return contractNum;
	}

	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}

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

	public String getScanId() {
		return scanId;
	}

	public void setScanId(String scanId) {
		this.scanId = scanId;
	}

	public String getSucYn() {
		return sucYn;
	}

	public void setSucYn(String sucYn) {
		this.sucYn = sucYn;
	}

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

	public String getReqRsn() {
		return reqRsn;
	}

	public void setReqRsn(String reqRsn) {
		this.reqRsn = reqRsn;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getRegstId() {
		return regstId;
	}

	public void setRegstId(String regstId) {
		this.regstId = regstId;
	}

	public String getRegstNm() {
		return regstNm;
	}

	public void setRegstNm(String regstNm) {
		this.regstNm = regstNm;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public String getsStrtDt() {
		return sStrtDt;
	}

	public void setsStrtDt(String sStrtDt) {
		this.sStrtDt = sStrtDt;
	}

	public String getsEndDt() {
		return sEndDt;
	}

	public void setsEndDt(String sEndDt) {
		this.sEndDt = sEndDt;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
	
	

	
	
	
}
