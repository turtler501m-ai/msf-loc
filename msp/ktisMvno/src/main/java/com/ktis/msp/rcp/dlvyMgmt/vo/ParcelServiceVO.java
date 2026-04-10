package com.ktis.msp.rcp.dlvyMgmt.vo;

import java.util.List;

import com.ktis.msp.base.mvc.BaseVo;

public class ParcelServiceVO extends BaseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1000479942183581913L;
	
	/** 검색조건 **/
	private String frmStartDate;	/** 시작일자 **/
	private String frmEndDate;		/** 종료일자 **/
	private String frmTbCd;			/** 택배사코드 **/
	private String frmDlvrNo;		/** 송장번호 **/
	private String frmResNo;		/** 예약번호 **/
	private String frmDlvryName;	/** 배송자명 **/
	private String frmMobileNo;		/** 전화번호 **/
	
	/** 발송 DATA **/
	private String sysRdate;		/** 신청일자 **/
	private String tbCd;			/** 택배사코드 **/
	private String tbCdName;			/** 택배사코드 **/
	private String dlvrNo;			/** 송장번호 **/
	private String resNo;			/** 예약번호 **/
	private String dlvryName;		/** 수취인 **/
	private String mobileNo;		/** 전화번호 **/
	private String dlvryAddr;		/** 주소 **/
	private String dlvryAddrDtl;	/** 상세주소 **/
	private String dlvryPost;		/** 우편번호 **/
	private String dlvryMemo;		/** 메모 **/
	private String requestStateCode;	/** 진행상태 **/
	
	/* 페이징 처리용 */
	public int TOTAL_COUNT;
	public String ROW_NUM;
	public String LINENUM;
	
	private String fileName;
	
	List<ParcelServiceVO> items;
	
	public String getFrmStartDate() {
		return frmStartDate;
	}
	public void setFrmStartDate(String frmStartDate) {
		this.frmStartDate = frmStartDate;
	}
	public String getFrmEndDate() {
		return frmEndDate;
	}
	public void setFrmEndDate(String frmEndDate) {
		this.frmEndDate = frmEndDate;
	}
	public String getFrmTbCd() {
		return frmTbCd;
	}
	public void setFrmTbCd(String frmTbCd) {
		this.frmTbCd = frmTbCd;
	}
	public String getFrmDlvrNo() {
		return frmDlvrNo;
	}
	public void setFrmDlvrNo(String frmDlvrNo) {
		this.frmDlvrNo = frmDlvrNo;
	}
	public String getFrmResNo() {
		return frmResNo;
	}
	public void setFrmResNo(String frmResNo) {
		this.frmResNo = frmResNo;
	}
	public String getFrmDlvryName() {
		return frmDlvryName;
	}
	public void setFrmDlvryName(String frmDlvryName) {
		this.frmDlvryName = frmDlvryName;
	}
	public String getFrmMobileNo() {
		return frmMobileNo;
	}
	public void setFrmMobileNo(String frmMobileNo) {
		this.frmMobileNo = frmMobileNo;
	}
	public String getSysRdate() {
		return sysRdate;
	}
	public void setSysRdate(String sysRdate) {
		this.sysRdate = sysRdate;
	}
	public String getTbCd() {
		return tbCd;
	}
	public void setTbCd(String tbCd) {
		this.tbCd = tbCd;
	}
	public String getDlvrNo() {
		return dlvrNo;
	}
	public void setDlvrNo(String dlvrNo) {
		this.dlvrNo = dlvrNo;
	}
	public String getResNo() {
		return resNo;
	}
	public void setResNo(String resNo) {
		this.resNo = resNo;
	}
	public String getDlvryName() {
		return dlvryName;
	}
	public void setDlvryName(String dlvryName) {
		this.dlvryName = dlvryName;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
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
	public String getDlvryPost() {
		return dlvryPost;
	}
	public void setDlvryPost(String dlvryPost) {
		this.dlvryPost = dlvryPost;
	}
	public String getDlvryMemo() {
		return dlvryMemo;
	}
	public void setDlvryMemo(String dlvryMemo) {
		this.dlvryMemo = dlvryMemo;
	}
	public String getRequestStateCode() {
		return requestStateCode;
	}
	public void setRequestStateCode(String requestStateCode) {
		this.requestStateCode = requestStateCode;
	}
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
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public List<ParcelServiceVO> getItems() {
		return items;
	}
	public void setItems(List<ParcelServiceVO> items) {
		this.items = items;
	}
	/**
	 * @return the tbCdName
	 */
	public String getTbCdName() {
		return tbCdName;
	}
	/**
	 * @param tbCdName the tbCdName to set
	 */
	public void setTbCdName(String tbCdName) {
		this.tbCdName = tbCdName;
	}
	
}
