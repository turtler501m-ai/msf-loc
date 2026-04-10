package com.ktmmobile.mcp.point.dto;

import java.io.Serializable;

public class CustPointGiveBaseBasDto implements Serializable{
	private static final long serialVersionUID = 1L;

	public  int pointCustSeq;  // 포인트고객일련번호
	public  String userId;  // 사용자ID
	public  String svcCntrNo;  // 서비스계약번호
	public  int totAcuPoint;  // 총적립포인트
	public  int totUsePoint;  // 총사용포인트
	public  int totRemainPoint;  // 총잔여포인트
	public  int totExtinctionPoint;  // 총소멸포인트
	public  String pointSumBaseDate;  // 포인트집계기준일자
	public  String cretIp;  // 생성IP
	public  String cretDt;  // 생성일시
	public  String cretId;  // 생성자ID
	public  String amdIp;  // 수정IP
	public  String amdDt;  // 수정일시
	public  String amdId;  // 수정자ID
	
	
	public int getPointCustSeq() {
		return pointCustSeq;
	}
	public void setPointCustSeq(int pointCustSeq) {
		this.pointCustSeq = pointCustSeq;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSvcCntrNo() {
		return svcCntrNo;
	}
	public void setSvcCntrNo(String svcCntrNo) {
		this.svcCntrNo = svcCntrNo;
	}
	public int getTotAcuPoint() {
		return totAcuPoint;
	}
	public void setTotAcuPoint(int totAcuPoint) {
		this.totAcuPoint = totAcuPoint;
	}
	public int getTotUsePoint() {
		return totUsePoint;
	}
	public void setTotUsePoint(int totUsePoint) {
		this.totUsePoint = totUsePoint;
	}
	public int getTotRemainPoint() {
		return totRemainPoint;
	}
	public void setTotRemainPoint(int totRemainPoint) {
		this.totRemainPoint = totRemainPoint;
	}
	public int getTotExtinctionPoint() {
		return totExtinctionPoint;
	}
	public void setTotExtinctionPoint(int totExtinctionPoint) {
		this.totExtinctionPoint = totExtinctionPoint;
	}
	public String getPointSumBaseDate() {
		return pointSumBaseDate;
	}
	public void setPointSumBaseDate(String pointSumBaseDate) {
		this.pointSumBaseDate = pointSumBaseDate;
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
