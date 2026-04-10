package com.ktmmobile.mcp.point.dto;

import java.io.Serializable;

public class CustPointGiveTgtBasDto implements Serializable{
	private static final long serialVersionUID = 1L;

	private int pointGiveBaseSeq;  //포인트지급기준일련번호
	private String pointGiveBaseCd;  //포인트지급기준코드
	private String pointGiveCntCd;  //포인트지급횟수코드
	private String pointGiveRepeatPeriodCd;  //포인트지급반복기간코드
	private String pointGiveFormlCd;  //포인트지급방식코드
	private String pointGiveDateCd;  //포인트지급일자코드
	private String pointGiveDate;  //포인트지급일자
	private String pointCalcCd;  //포인트계산코드
	private int pointFamt;  //포인트정액
	private String pointRatio;  //포인트비율
	private String useYn;  //사용여부
	private String pstngStartDate;  //게시시작일자
	private String pstngEndDate;  //게시종료일자
	private String cretIp;  //생성IP
	private String cretDt;  //생성일시
	private String cretId;  //생성자ID
	private String amdIp;  //수정IP
	private String amdDt;  //수정일시
	private String amdId;  //수정자ID
	private int custPointGiveTgtSeq;  //고객포인트지급대상일련번호
	private String svcCntrNo;  //서비스계약번호
	private String userId;  //사용자ID
	private int pointGiveBaseDtlSeq;  //포인트지급기준상세일련번호
	private int ordrNo;  //주문번호
	private String pointGiveBaseRateCd;  //포인트지급기준요금제코드
	private int pointGiveBaseRateAmt;  //포인트지급기준요금제금액
	private String pointGiveValidYn;  //포인트지급유효여부
	private String pointDivCd;  //포인트분류코드
	private String pointTrtCd;  //포인트처리코드
	private int point;  //포인트
	private String pointUsePosblStDate;  //포인트사용가능시작일자
	private String pointUsePosblEndDate;  //포인트사용가능종료일자
	private String pointTxnRsnCd;  //포인트처리사유코드
	private String pointTxnDtlRsnDesc;  //포인트처리상세사유설명
	private int pointCustSeq;  //포인트고객일련번호
	private String pointGiveCpltYn;  //포인트지급완료여부
	private String pointGiveCpltDate;  //포인트지급완료일자
	private int pointAllGiveSeq;		    // 포인트일괄지급일련번호
	private int userPointTrtSeq;	     	// 고객포인트처리일련번호

		
	public int getPointGiveBaseSeq() {
		return pointGiveBaseSeq;
	}
	public void setPointGiveBaseSeq(int pointGiveBaseSeq) {
		this.pointGiveBaseSeq = pointGiveBaseSeq;
	}
	public String getPointGiveBaseCd() {
		return pointGiveBaseCd;
	}
	public void setPointGiveBaseCd(String pointGiveBaseCd) {
		this.pointGiveBaseCd = pointGiveBaseCd;
	}
	public String getPointGiveCntCd() {
		return pointGiveCntCd;
	}
	public void setPointGiveCntCd(String pointGiveCntCd) {
		this.pointGiveCntCd = pointGiveCntCd;
	}
	public String getPointGiveRepeatPeriodCd() {
		return pointGiveRepeatPeriodCd;
	}
	public void setPointGiveRepeatPeriodCd(String pointGiveRepeatPeriodCd) {
		this.pointGiveRepeatPeriodCd = pointGiveRepeatPeriodCd;
	}
	public String getPointGiveFormlCd() {
		return pointGiveFormlCd;
	}
	public void setPointGiveFormlCd(String pointGiveFormlCd) {
		this.pointGiveFormlCd = pointGiveFormlCd;
	}
	public String getPointGiveDateCd() {
		return pointGiveDateCd;
	}
	public void setPointGiveDateCd(String pointGiveDateCd) {
		this.pointGiveDateCd = pointGiveDateCd;
	}
	public String getPointGiveDate() {
		return pointGiveDate;
	}
	public void setPointGiveDate(String pointGiveDate) {
		this.pointGiveDate = pointGiveDate;
	}
	public String getPointCalcCd() {
		return pointCalcCd;
	}
	public void setPointCalcCd(String pointCalcCd) {
		this.pointCalcCd = pointCalcCd;
	}
	public int getPointFamt() {
		return pointFamt;
	}
	public void setPointFamt(int pointFamt) {
		this.pointFamt = pointFamt;
	}
	public String getPointRatio() {
		return pointRatio;
	}
	public void setPointRatio(String pointRatio) {
		this.pointRatio = pointRatio;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getPstngStartDate() {
		return pstngStartDate;
	}
	public void setPstngStartDate(String pstngStartDate) {
		this.pstngStartDate = pstngStartDate;
	}
	public String getPstngEndDate() {
		return pstngEndDate;
	}
	public void setPstngEndDate(String pstngEndDate) {
		this.pstngEndDate = pstngEndDate;
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
	public int getCustPointGiveTgtSeq() {
		return custPointGiveTgtSeq;
	}
	public void setCustPointGiveTgtSeq(int custPointGiveTgtSeq) {
		this.custPointGiveTgtSeq = custPointGiveTgtSeq;
	}
	public String getSvcCntrNo() {
		return svcCntrNo;
	}
	public void setSvcCntrNo(String svcCntrNo) {
		this.svcCntrNo = svcCntrNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getPointGiveBaseDtlSeq() {
		return pointGiveBaseDtlSeq;
	}
	public void setPointGiveBaseDtlSeq(int pointGiveBaseDtlSeq) {
		this.pointGiveBaseDtlSeq = pointGiveBaseDtlSeq;
	}
	public int getOrdrNo() {
		return ordrNo;
	}
	public void setOrdrNo(int ordrNo) {
		this.ordrNo = ordrNo;
	}
	public String getPointGiveBaseRateCd() {
		return pointGiveBaseRateCd;
	}
	public void setPointGiveBaseRateCd(String pointGiveBaseRateCd) {
		this.pointGiveBaseRateCd = pointGiveBaseRateCd;
	}
	public int getPointGiveBaseRateAmt() {
		return pointGiveBaseRateAmt;
	}
	public void setPointGiveBaseRateAmt(int pointGiveBaseRateAmt) {
		this.pointGiveBaseRateAmt = pointGiveBaseRateAmt;
	}
	public String getPointGiveValidYn() {
		return pointGiveValidYn;
	}
	public void setPointGiveValidYn(String pointGiveValidYn) {
		this.pointGiveValidYn = pointGiveValidYn;
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
	public int getPointCustSeq() {
		return pointCustSeq;
	}
	public void setPointCustSeq(int pointCustSeq) {
		this.pointCustSeq = pointCustSeq;
	}
	public String getPointGiveCpltYn() {
		return pointGiveCpltYn;
	}
	public void setPointGiveCpltYn(String pointGiveCpltYn) {
		this.pointGiveCpltYn = pointGiveCpltYn;
	}
	public String getPointGiveCpltDate() {
		return pointGiveCpltDate;
	}
	public void setPointGiveCpltDate(String pointGiveCpltDate) {
		this.pointGiveCpltDate = pointGiveCpltDate;
	}	public int getPointAllGiveSeq() {
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

}
