package com.ktmmobile.mcp.point.dto;

import java.io.Serializable;

public class CustPointTxnDtlDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int pointCustSeq;			// 포인트고객일련번호
	private int pointTxnDtlSeq;		// 포인트내역일련번호
	private int pointTxnSeq;		// 포인트내역일련번호
	private int acuPointTxnSeq;		//
	private int usePoint;			//
	private int remainPoint;		// 총잔여포인트
	private int point; 
	private String pointUsePosblStDate;
	private String pointUsePosblEndDate;
	private String cretIp;
	private String cretDt;
	private String cretId;
	private String amdIp;
	private String amdDt;
	private String amdId;

	public int getPointCustSeq() {
		return pointCustSeq;
	}
	public void setPointCustSeq(int pointCustSeq) {
		this.pointCustSeq = pointCustSeq;
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
	public int getRemainPoint() {
		return remainPoint;
	}
	public void setRemainPoint(int remainPoint) {
		this.remainPoint = remainPoint;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public String getPointUsePosblStDate() {
		return pointUsePosblStDate;
	}
	public void setPointUsePosblStDate(String pointUsePosblStDate) {
		this.pointUsePosblStDate = pointUsePosblStDate;
	}
	public String getPointUsePosblEndDate() {
		return pointUsePosblEndDate;
	}
	public void setPointUsePosblEndDate(String pointUsePosblEndDate) {
		this.pointUsePosblEndDate = pointUsePosblEndDate;
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
