package com.ktis.msp.gift.custgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

public class PrmtResultVO extends BaseVo implements Serializable {

	/** 사은품 프로모션 결과 관리 **/
	private String prmtId;              /** 프로모션ID   */
	private String contractNum;         /** 가입계약번호   */
	private String prdtId;               /** 제품ID       */                         
	private String quantity;               /** 수량       */
	
	private String prdtNm;              /** 제품명    */   
	private String dupYn;               /** 중복여부  */  
	private String rowCheck;           /** 선택여부  */                       
	private String outUnitPric;         /** 단가       */                       
	
	
	public String getPrmtId() {
		return prmtId;
	}
	public void setPrmtId(String prmtId) {
		this.prmtId = prmtId;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getPrdtId() {
		return prdtId;
	}
	public void setPrdtId(String prdtId) {
		this.prdtId = prdtId;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getPrdtNm() {
		return prdtNm;
	}
	public void setPrdtNm(String prdtNm) {
		this.prdtNm = prdtNm;
	}
	public String getDupYn() {
		return dupYn;
	}
	public void setDupYn(String dupYn) {
		this.dupYn = dupYn;
	}
	public String getRowCheck() {
		return rowCheck;
	}
	public void setRowCheck(String rowCheck) {
		this.rowCheck = rowCheck;
	}
	public String getOutUnitPric() {
		return outUnitPric;
	}
	public void setOutUnitPric(String outUnitPric) {
		this.outUnitPric = outUnitPric;
	}                         

	
}
