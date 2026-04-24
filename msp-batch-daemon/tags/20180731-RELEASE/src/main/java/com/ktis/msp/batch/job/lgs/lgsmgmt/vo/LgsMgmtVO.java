package com.ktis.msp.batch.job.lgs.lgsmgmt.vo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.BaseVo;

/**
 * @Class Name  : LgsMgmtVO.java
 * @Description : LgsMgmtVO Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2014.08.24     IB      최초생성
 *
 * @author IB
 * @since 2014. 08.24
 * @version 1.0
 * @see
 *
 *  Copyright (C) by MOPAS All right reserved.
 */
public class LgsMgmtVO extends BaseVo implements Serializable {

	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 2833433042138170138L;
	
	private String orgnId;				/** 조직ID */
	private String stdDt;				/** 기준일자 */
	private String prdtId;				/** 제품ID */
	private String oldYn;				/** 신품여부 */
	private int    goodStrgInvtrQnty;	/** 양품창고재고수량 */
	private int    ngStrgInvtrQnty;		/** 불량창고재고수량 */
	private int    inDueQnty;			/** 입고예정수량 */
	private int    outRsvQnty;			/** 출고예약수량 */
	
	private String unitPricApplDttm;	/** 단가적용일시 */
	private String unitPricExprDttm;	/** 단가만료일시 */
	private int    outUnitPric;			/** 출고단가 */
	private int    inUnitPric;			/** 입고단가 */
	
	private String userId;				/** 사용자ID */
	private String mnfctId;				/** 공급사ID */
	private String mnfctNm;				/** 공급사명   */
	
	private int saleQty;				/** 타사판매수량 */
	private int canQty;					/** 타사해지수량 */

	private String rprsPrdtId;			/** 대표모델ID */
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String getOrgnId() {
		return orgnId;
	}

	public void setOrgnId(String orgnId) {
		this.orgnId = orgnId;
	}

	public String getStdDt() {
		return stdDt;
	}

	public void setStdDt(String stdDt) {
		this.stdDt = stdDt;
	}

	public String getPrdtId() {
		return prdtId;
	}

	public void setPrdtId(String prdtId) {
		this.prdtId = prdtId;
	}

	public String getOldYn() {
		return oldYn;
	}

	public void setOldYn(String oldYn) {
		this.oldYn = oldYn;
	}

	public int getGoodStrgInvtrQnty() {
		return goodStrgInvtrQnty;
	}

	public void setGoodStrgInvtrQnty(int goodStrgInvtrQnty) {
		this.goodStrgInvtrQnty = goodStrgInvtrQnty;
	}

	public int getNgStrgInvtrQnty() {
		return ngStrgInvtrQnty;
	}

	public void setNgStrgInvtrQnty(int ngStrgInvtrQnty) {
		this.ngStrgInvtrQnty = ngStrgInvtrQnty;
	}

	public int getInDueQnty() {
		return inDueQnty;
	}

	public void setInDueQnty(int inDueQnty) {
		this.inDueQnty = inDueQnty;
	}

	public int getOutRsvQnty() {
		return outRsvQnty;
	}

	public void setOutRsvQnty(int outRsvQnty) {
		this.outRsvQnty = outRsvQnty;
	}

	public String getUnitPricApplDttm() {
		return unitPricApplDttm;
	}

	public void setUnitPricApplDttm(String unitPricApplDttm) {
		this.unitPricApplDttm = unitPricApplDttm;
	}

	public String getUnitPricExprDttm() {
		return unitPricExprDttm;
	}

	public void setUnitPricExprDttm(String unitPricExprDttm) {
		this.unitPricExprDttm = unitPricExprDttm;
	}

	public int getOutUnitPric() {
		return outUnitPric;
	}

	public void setOutUnitPric(int outUnitPric) {
		this.outUnitPric = outUnitPric;
	}

	public int getInUnitPric() {
		return inUnitPric;
	}

	public void setInUnitPric(int inUnitPric) {
		this.inUnitPric = inUnitPric;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMnfctId() {
		return mnfctId;
	}

	public void setMnfctId(String mnfctId) {
		this.mnfctId = mnfctId;
	}

	public String getMnfctNm() {
		return mnfctNm;
	}

	public void setMnfctNm(String mnfctNm) {
		this.mnfctNm = mnfctNm;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getSaleQty() {
		return saleQty;
	}

	public void setSaleQty(int saleQty) {
		this.saleQty = saleQty;
	}

	public int getCanQty() {
		return canQty;
	}

	public void setCanQty(int canQty) {
		this.canQty = canQty;
	}

	/**
	 * @return the rprsPrdtId
	 */
	public String getRprsPrdtId() {
		return rprsPrdtId;
	}

	/**
	 * @param rprsPrdtId the rprsPrdtId to set
	 */
	public void setRprsPrdtId(String rprsPrdtId) {
		this.rprsPrdtId = rprsPrdtId;
	}
	
}