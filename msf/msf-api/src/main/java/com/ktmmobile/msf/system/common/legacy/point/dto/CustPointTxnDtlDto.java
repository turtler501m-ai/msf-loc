package com.ktmmobile.msf.system.common.legacy.point.dto;

import java.io.Serializable;

public class CustPointTxnDtlDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int pointTxnDtlSeq; // 포인트내역일련번호
	private int pointTxnSeq; // 포인트내역일련번호
	private int acuPointTxnSeq;
	private int usePoint;
	private int remainPoint; // 총잔여포인트
	private String cretIp; // 생성IP
	private String cretId; // 생성자ID
	private String cretDt; // 생성일시
	private String amdIp; // 생성IP
	private String amdId; // 생성자ID
	private String amdDt; // 생성일시
	private String searchValue; // 검색조건
	
	
	public int getAcuPointTxnSeq() {
		return acuPointTxnSeq;
	}
	public void setAcuPointTxnSeq(int acuPointTxnSeq) {
		this.acuPointTxnSeq = acuPointTxnSeq;
	}
	public int getUsePoint() {
		return usePoint;
	}
	public void setUsePoint(int usePoint) {
		this.usePoint = usePoint;
	}
	public int getPointTxnDtlSeq() {
		return pointTxnDtlSeq;
	}
	public void setPointTxnDtlSeq(int pointTxnDtlSeq) {
		this.pointTxnDtlSeq = pointTxnDtlSeq;
	}
	public int getPointTxnSeq() {
		return pointTxnSeq;
	}
	public void setPointTxnSeq(int pointTxnSeq) {
		this.pointTxnSeq = pointTxnSeq;
	}
	
	public int getRemainPoint() {
		return remainPoint;
	}
	public void setRemainPoint(int remainPoint) {
		this.remainPoint = remainPoint;
	}
	public String getCretIp() {
		return cretIp;
	}
	public void setCretIp(String cretIp) {
		this.cretIp = cretIp;
	}
	public String getCretId() {
		return cretId;
	}
	public void setCretId(String cretId) {
		this.cretId = cretId;
	}
	public String getCretDt() {
		return cretDt;
	}
	public void setCretDt(String cretDt) {
		this.cretDt = cretDt;
	}
	public String getAmdIp() {
		return amdIp;
	}
	public void setAmdIp(String amdIp) {
		this.amdIp = amdIp;
	}
	public String getAmdId() {
		return amdId;
	}
	public void setAmdId(String amdId) {
		this.amdId = amdId;
	}
	public String getAmdDt() {
		return amdDt;
	}
	public void setAmdDt(String amdDt) {
		this.amdDt = amdDt;
	}
	public String getSearchValue() {
		return searchValue;
	}
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
}
