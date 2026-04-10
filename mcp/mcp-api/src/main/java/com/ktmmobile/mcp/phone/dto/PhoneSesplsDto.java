package com.ktmmobile.mcp.phone.dto;

import java.io.Serializable;
import java.sql.Date;

/**
 * @author key
 * 자급제 정보
 */
public class PhoneSesplsDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 대표모델ID */
    private String rprsPrdtId;
    /* 대표모델코드 */
    private String rprsPrdtCode;
    /* 모델명 */
    private String prdtNm;
    /* 중고여부 */
    private String oldYn;
    /* 중고여부명 */
    private String oldYnNm;
    /* 제조사ID */
    private String mnfctId;
    /* 제조사명 */
    private String mnfctNm;
    /* 단가만료일시 */
    private String unitPricExprDttm;
    /* 단가적용일시 */
    private String unitPricApplDttm;
    /* 입고단가 */
    private int inUnitPric;
    /* 출고단가 */
    private int outUnitPric;
    /* 비고 */
    private String remrk;
    /* 등록자ID */
    private String regId;
    /* 등록일시 */
    private Date regDttm;
    /* 수정자ID */
    private String rvisnId;
    /* 수정일시 */
    private Date rvisnDttm;

    public String getRprsPrdtId() {
		return rprsPrdtId;
	}
	public void setRprsPrdtId(String rprsPrdtId) {
		this.rprsPrdtId = rprsPrdtId;
	}
	public String getRprsPrdtCode() {
		return rprsPrdtCode;
	}
	public void setRprsPrdtCode(String rprsPrdtCode) {
		this.rprsPrdtCode = rprsPrdtCode;
	}
	public String getPrdtNm() {
		return prdtNm;
	}
	public void setPrdtNm(String prdtNm) {
		this.prdtNm = prdtNm;
	}
	public String getOldYn() {
		return oldYn;
	}
	public void setOldYn(String oldYn) {
		this.oldYn = oldYn;
	}
	public String getOldYnNm() {
		return oldYnNm;
	}
	public void setOldYnNm(String oldYnNm) {
		this.oldYnNm = oldYnNm;
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
	public String getUnitPricExprDttm() {
		return unitPricExprDttm;
	}
	public void setUnitPricExprDttm(String unitPricExprDttm) {
		this.unitPricExprDttm = unitPricExprDttm;
	}
	public String getUnitPricApplDttm() {
		return unitPricApplDttm;
	}
	public void setUnitPricApplDttm(String unitPricApplDttm) {
		this.unitPricApplDttm = unitPricApplDttm;
	}
	public int getInUnitPric() {
		return inUnitPric;
	}
	public void setInUnitPric(int inUnitPric) {
		this.inUnitPric = inUnitPric;
	}

	public int getOutUnitPric() {
		return outUnitPric;
	}
	public void setOutUnitPric(int outUnitPric) {
		this.outUnitPric = outUnitPric;
	}
	public String getRemrk() {
		return remrk;
	}
	public void setRemrk(String remrk) {
		this.remrk = remrk;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public Date getRegDttm() {
		return regDttm;
	}
	public void setRegDttm(Date regDttm) {
		this.regDttm = regDttm;
	}
	public String getRvisnId() {
		return rvisnId;
	}
	public void setRvisnId(String rvisnId) {
		this.rvisnId = rvisnId;
	}
	public Date getRvisnDttm() {
		return rvisnDttm;
	}
	public void setRvisnDttm(Date rvisnDttm) {
		this.rvisnDttm = rvisnDttm;
	}


}
