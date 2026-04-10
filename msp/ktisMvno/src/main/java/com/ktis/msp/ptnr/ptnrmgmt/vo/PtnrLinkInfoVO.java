package com.ktis.msp.ptnr.ptnrmgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class PtnrLinkInfoVO extends BaseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3327825892993884675L;
	
	/** 제휴정책정보 **/
	private String baseDate;	/** 연동년월 **/
	private String ptnrId;		/** 제휴사ID **/
	private String ptnrNm;
	private String sendFlag;	/** 요청상태 **/
	private String sendFlagNm;
	private String sendResult;	/** 지급상태 **/
	private String sendResultNm;
	private String fileNm;		/** 연동파일 **/
	private String regstDttm;	/** 요청일자 **/
	private String reusltDate;	/** 결과일자 **/
	
	public String getBaseDate() {
		return baseDate;
	}
	public void setBaseDate(String baseDate) {
		this.baseDate = baseDate;
	}
	public String getPtnrId() {
		return ptnrId;
	}
	public void setPtnrId(String ptnrId) {
		this.ptnrId = ptnrId;
	}
	public String getPtnrNm() {
		return ptnrNm;
	}
	public void setPtnrNm(String ptnrNm) {
		this.ptnrNm = ptnrNm;
	}
	public String getSendFlag() {
		return sendFlag;
	}
	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
	}
	public String getSendFlagNm() {
		return sendFlagNm;
	}
	public void setSendFlagNm(String sendFlagNm) {
		this.sendFlagNm = sendFlagNm;
	}
	public String getSendResult() {
		return sendResult;
	}
	public void setSendResult(String sendResult) {
		this.sendResult = sendResult;
	}
	public String getSendResultNm() {
		return sendResultNm;
	}
	public void setSendResultNm(String sendResultNm) {
		this.sendResultNm = sendResultNm;
	}
	public String getFileNm() {
		return fileNm;
	}
	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}
	public String getRegstDttm() {
		return regstDttm;
	}
	public void setRegstDttm(String regstDttm) {
		this.regstDttm = regstDttm;
	}
	public String getReusltDate() {
		return reusltDate;
	}
	public void setReusltDate(String reusltDate) {
		this.reusltDate = reusltDate;
	}
	
}
