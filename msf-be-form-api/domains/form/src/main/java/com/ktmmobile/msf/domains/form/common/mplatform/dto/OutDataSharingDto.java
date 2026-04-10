package com.ktmmobile.msf.domains.form.common.mplatform.dto;

import java.io.Serializable;

/**
 * 데이터 쉐어링 결합 중인 대상 조회
 * x71
 * 데이터쉐어링 사전체크 및 가입 가능 대상조회
 * x69
 */


public class OutDataSharingDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String svcNo; //전화번호
	private String efctStDt; //결합시작일 YYYYMMDD
	private String rsltMsg; //결과메시지
	private String rsltInd;  //결합가능여부


	public String getSvcNo() {
		return svcNo;
	}
	public void setSvcNo(String svcNo) {
		this.svcNo = svcNo;
	}
	public String getEfctStDt() {
		return efctStDt;
	}
	public void setEfctStDt(String efctStDt) {
		this.efctStDt = efctStDt;
	}
	public String getRsltMsg() {
		return rsltMsg;
	}
	public void setRsltMsg(String rsltMsg) {
		this.rsltMsg = rsltMsg;
	}
	public String getRsltInd() {
		return rsltInd;
	}
	public void setRsltInd(String rsltInd) {
		this.rsltInd = rsltInd;
	}

		





}
