package com.ktis.msp.rcp.rcpMgmt.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : SampleDefaultVO.java
 * @Description : SampleDefaultVO Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2022.02.17           최초생성
 *
 * @author ktds si개발팀
 * @since 2022. 02.17
 * @version 1.0
 * @see
 *
 *  Copyright (C) by MOPAS All right reserved.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="pointTxnVO")
public class PointTxnVO extends BaseVo implements Serializable{

	//serialVersionUID
	private static final long serialVersionUID = 6760215222094556206L;
	
	private Integer requestKey; //가입신청_키
	private String svcCntrNo; //서비스계약번호
	private Integer pointTxnSeq; //포인트일괄내역번호
	private Integer pointCustSeq; //포인트고객일련번호
	private String pointDivCd; //포인트분류코드
	private String pointTrtCd; //포인트처리코드
	private Integer pointGiveBaseSeq; //포인트지급기준일련번호
	private Integer point;
	private Integer totRemainPoint; //잔여포인트 
	private String pointTxnRsnCd; //포인트처리사유코드
	private String pointTxnDtlRsnDesc;
	private Integer pointAllGiveSeq; //포인트일괄지급일련번호
	private Integer userPointTrtSeq; //고객포인트처리일련번호
	private String pointTrtMemo; //포인트처리메모
	private String cretIp; //생성IP
	private String cretId; //생성Id
	private String contractNum;        /*계약번호        */
	
	public String getSvcCntrNo() {
		return svcCntrNo;
	}
	public void setSvcCntrNo(String svcCntrNo) {
		this.svcCntrNo = svcCntrNo;
	}
	public int getPointTxnSeq() {
		return pointTxnSeq;
	}
	public void setPointTxnSeq(int pointTxnSeq) {
		this.pointTxnSeq = pointTxnSeq;
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
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public Integer getRequestKey() {
		return requestKey;
	}
	public void setRequestKey(Integer requestKey) {
		this.requestKey = requestKey;
	}
	public Integer getPointCustSeq() {
		return pointCustSeq;
	}
	public void setPointCustSeq(Integer pointCustSeq) {
		this.pointCustSeq = pointCustSeq;
	}
	public Integer getPointGiveBaseSeq() {
		return pointGiveBaseSeq;
	}
	public void setPointGiveBaseSeq(Integer pointGiveBaseSeq) {
		this.pointGiveBaseSeq = pointGiveBaseSeq;
	}
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	public Integer getTotRemainPoint() {
		return totRemainPoint;
	}
	public void setTotRemainPoint(Integer totRemainPoint) {
		this.totRemainPoint = totRemainPoint;
	}
	public Integer getPointAllGiveSeq() {
		return pointAllGiveSeq;
	}
	public void setPointAllGiveSeq(Integer pointAllGiveSeq) {
		this.pointAllGiveSeq = pointAllGiveSeq;
	}
	public Integer getUserPointTrtSeq() {
		return userPointTrtSeq;
	}
	public void setUserPointTrtSeq(Integer userPointTrtSeq) {
		this.userPointTrtSeq = userPointTrtSeq;
	}
	public void setPointTxnSeq(Integer pointTxnSeq) {
		this.pointTxnSeq = pointTxnSeq;
	}
	
}
