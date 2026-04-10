package com.ktmmobile.mcp.point.dto;

import java.io.Serializable;

public class CustPointTxnDto implements Serializable{
	private static final long serialVersionUID = 1L;

	private int pointTxnSeq;				// 포인트내역일련번호
	private int pointCustSeq;				// 포인트고객일련번호
	private String userId;					// 사용자ID
	private String svcCntrNo;				// 계약 번호
	private String pointDivCd;				// 포인트분류코드
	private String pointTrtCd;				// 포인트처리코드
	private int pointGiveBaseSeq;           // 포인트지급기준일련번호
	private int point;						// 포인트
	private int totRemainPoint;             // 총잔여포인트
	private String pointUsePosblStDate;		// 포인트사용가능시작일자
	private String pointUsePosblEndDate;	// 포인트사용가능종료일자
	private String pointTxnRsnCd;			// 포인트처리사유코드
	private String pointTxnDtlRsnDesc;		// 포인트처리상세사유설명
	private int pointAllGiveSeq;		    // 포인트일괄지급일련번호
	private int userPointTrtSeq;	     	// 고객포인트처리일련번호
	private int requestKey;				    // 가입신청_키
	private String pointTrtDt;				// 포인트처리일시
	private String pointTrtMemo;			// 포인트처리메모
	private String cretIp;					// 생성IP
	private String cretId;					// 생성자ID
	private String cretDt;					// 생성일시
	private String amdIp;
	private String amdDt;
	private String amdId;
	private String cntrMobileNo;            // 전화번호
	private int pointTxnDtlSeq;
	private int usePoint;

	public int getTotRemainPoint() {
		return totRemainPoint;
	}
	public void setTotRemainPoint(int totRemainPoint) {
		this.totRemainPoint = totRemainPoint;
	}
	public int getPointTxnSeq() {
		return pointTxnSeq;
	}
	public void setPointTxnSeq(int pointTxnSeq) {
		this.pointTxnSeq = pointTxnSeq;
	}
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
	public String getPointDivCd() {
		return pointDivCd;
	}
	public void setPointDivCd(String pointDivCd) {
		this.pointDivCd = pointDivCd;
	}
	public String getPointTrtCd() {
		return pointTrtCd;
	}
	public void setPointTrtCd(String pointTrtCd) {
		this.pointTrtCd = pointTrtCd;
	}
	public int getPointGiveBaseSeq() {
		return pointGiveBaseSeq;
	}
	public void setPointGiveBaseSeq(int pointGiveBaseSeq) {
		this.pointGiveBaseSeq = pointGiveBaseSeq;
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
	public String getPointTxnRsnCd() {
		return pointTxnRsnCd;
	}
	public void setPointTxnRsnCd(String pointTxnRsnCd) {
		this.pointTxnRsnCd = pointTxnRsnCd;
	}
	public String getPointTxnDtlRsnDesc() {
		return pointTxnDtlRsnDesc;
	}
	public void setPointTxnDtlRsnDesc(String pointTxnDtlRsnDesc) {
		this.pointTxnDtlRsnDesc = pointTxnDtlRsnDesc;
	}
	public int getPointAllGiveSeq() {
		return pointAllGiveSeq;
	}
	public void setPointAllGiveSeq(int pointAllGiveSeq) {
		this.pointAllGiveSeq = pointAllGiveSeq;
	}
	public int getUserPointTrtSeq() {
		return userPointTrtSeq;
	}
	public void setUserPointTrtSeq(int userPointTrtSeq) {
		this.userPointTrtSeq = userPointTrtSeq;
	}
	public int getRequestKey() {
		return requestKey;
	}
	public void setRequestKey(int requestKey) {
		this.requestKey = requestKey;
	}
	public String getPointTrtDt() {
		return pointTrtDt;
	}
	public void setPointTrtDt(String pointTrtDt) {
		this.pointTrtDt = pointTrtDt;
	}
	public String getPointTrtMemo() {
		return pointTrtMemo;
	}
	public void setPointTrtMemo(String pointTrtMemo) {
		this.pointTrtMemo = pointTrtMemo;
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
	public String getCntrMobileNo() {
		return cntrMobileNo;
	}
	public void setCntrMobileNo(String cntrMobileNo) {
		this.cntrMobileNo = cntrMobileNo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getPointTxnDtlSeq() {
		return pointTxnDtlSeq;
	}
	public void setPointTxnDtlSeq(int pointTxnDtlSeq) {
		this.pointTxnDtlSeq = pointTxnDtlSeq;
	}
	public int getUsePoint() {
		return usePoint;
	}
	public void setUsePoint(int usePoint) {
		this.usePoint = usePoint;
	}
	
	
	
	
}
