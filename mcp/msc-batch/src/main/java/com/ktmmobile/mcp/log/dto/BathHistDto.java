package com.ktmmobile.mcp.log.dto;

import java.io.Serializable;

public class BathHistDto implements Serializable{

	private static final long serialVersionUID = 1L;

	private String batchCd; // 코드관리(SY0018)
	private String exeMthd; // 실행메소드
	private String batchExeDate; // 배치실행일자
	private String batchExeStDate; // 배치실행시작일자
	private String batchExeEndDate; // 배치실행종료일자
	private int batchExeCascnt; // 배치실행건수
	private int successCascnt; // 성공건수
	private int failCascnt; // 실패건수
	private String batchRsltCd; // 코드관리(SY0019)
	private String batchFailCd; // 코드관리(SY0020)
	private String msg; // 메세지
	private String msgDtl; // 메시지상세
	private String manualYn; // 코드값(Y : 수동 / N : 자동)
	private String param; // 파라미터
	private String cretIp; // 생성IP
	private String cretDt; // 생성일시
	private String cretId; // 생성자ID
	private String amdIp; // 수정IP
	private String amdDt; // 수정일시
	private String amdId; // 수정자ID

	public String getBatchCd() {
		return batchCd;
	}
	public void setBatchCd(String batchCd) {
		this.batchCd = batchCd;
	}
	public String getExeMthd() {
		return exeMthd;
	}
	public void setExeMthd(String exeMthd) {
		this.exeMthd = exeMthd;
	}
	public String getBatchExeDate() {
		return batchExeDate;
	}
	public void setBatchExeDate(String batchExeDate) {
		this.batchExeDate = batchExeDate;
	}
	public String getBatchExeStDate() {
		return batchExeStDate;
	}
	public void setBatchExeStDate(String batchExeStDate) {
		this.batchExeStDate = batchExeStDate;
	}
	public String getBatchExeEndDate() {
		return batchExeEndDate;
	}
	public void setBatchExeEndDate(String batchExeEndDate) {
		this.batchExeEndDate = batchExeEndDate;
	}

	public int getBatchExeCascnt() {
		return batchExeCascnt;
	}
	public void setBatchExeCascnt(int batchExeCascnt) {
		this.batchExeCascnt = batchExeCascnt;
	}
	public int getSuccessCascnt() {
		return successCascnt;
	}
	public void setSuccessCascnt(int successCascnt) {
		this.successCascnt = successCascnt;
	}
	public int getFailCascnt() {
		return failCascnt;
	}
	public void setFailCascnt(int failCascnt) {
		this.failCascnt = failCascnt;
	}
	public String getBatchRsltCd() {
		return batchRsltCd;
	}
	public void setBatchRsltCd(String batchRsltCd) {
		this.batchRsltCd = batchRsltCd;
	}
	public String getBatchFailCd() {
		return batchFailCd;
	}
	public void setBatchFailCd(String batchFailCd) {
		this.batchFailCd = batchFailCd;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getMsgDtl() {
		return msgDtl;
	}
	public void setMsgDtl(String msgDtl) {
		this.msgDtl = msgDtl;
	}
	public String getManualYn() {
		return manualYn;
	}
	public void setManualYn(String manualYn) {
		this.manualYn = manualYn;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getCretIp() {
		return cretIp;
	}
	public void setCretIp(String cretIp) {
		this.cretIp = cretIp;
	}
	public String getCretDt() {
		return cretDt;
	}
	public void setCretDt(String cretDt) {
		this.cretDt = cretDt;
	}
	public String getCretId() {
		return cretId;
	}
	public void setCretId(String cretId) {
		this.cretId = cretId;
	}
	public String getAmdIp() {
		return amdIp;
	}
	public void setAmdIp(String amdIp) {
		this.amdIp = amdIp;
	}
	public String getAmdDt() {
		return amdDt;
	}
	public void setAmdDt(String amdDt) {
		this.amdDt = amdDt;
	}
	public String getAmdId() {
		return amdId;
	}
	public void setAmdId(String amdId) {
		this.amdId = amdId;
	}




}
