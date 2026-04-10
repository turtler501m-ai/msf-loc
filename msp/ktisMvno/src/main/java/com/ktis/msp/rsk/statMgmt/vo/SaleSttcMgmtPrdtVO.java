package com.ktis.msp.rsk.statMgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

public class SaleSttcMgmtPrdtVO extends BaseVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4943625481673340925L;
	private String rprsPrdtId;
	private String prdtId;
	private String prdtNm;
	private String colrNm;
	private String keepCnt;
	private String mmOpenCnt;
	private String mmCanCnt;
	private String mmNewCnt;
	private String mmTmntCnt;
	private String mmNetCnt;
	private String openCnt;
	private String canCnt;
	private String newCnt;
	private String tmntCnt;
	private String netCnt;
	
	public String getRprsPrdtId() {
		return rprsPrdtId;
	}
	public void setRprsPrdtId(String rprsPrdtId) {
		this.rprsPrdtId = rprsPrdtId;
	}
	public String getPrdtId() {
		return prdtId;
	}
	public void setPrdtId(String prdtId) {
		this.prdtId = prdtId;
	}
	public String getPrdtNm() {
		return prdtNm;
	}
	public void setPrdtNm(String prdtNm) {
		this.prdtNm = prdtNm;
	}
	public String getColrNm() {
		return colrNm;
	}
	public void setColrNm(String colrNm) {
		this.colrNm = colrNm;
	}
	public String getKeepCnt() {
		return keepCnt;
	}
	public void setKeepCnt(String keepCnt) {
		this.keepCnt = keepCnt;
	}
	public String getMmOpenCnt() {
		return mmOpenCnt;
	}
	public void setMmOpenCnt(String mmOpenCnt) {
		this.mmOpenCnt = mmOpenCnt;
	}
	public String getMmCanCnt() {
		return mmCanCnt;
	}
	public void setMmCanCnt(String mmCanCnt) {
		this.mmCanCnt = mmCanCnt;
	}
	public String getMmNewCnt() {
		return mmNewCnt;
	}
	public void setMmNewCnt(String mmNewCnt) {
		this.mmNewCnt = mmNewCnt;
	}
	public String getMmTmntCnt() {
		return mmTmntCnt;
	}
	public void setMmTmntCnt(String mmTmntCnt) {
		this.mmTmntCnt = mmTmntCnt;
	}
	public String getMmNetCnt() {
		return mmNetCnt;
	}
	public void setMmNetCnt(String mmNetCnt) {
		this.mmNetCnt = mmNetCnt;
	}
	public String getOpenCnt() {
		return openCnt;
	}
	public void setOpenCnt(String openCnt) {
		this.openCnt = openCnt;
	}
	public String getCanCnt() {
		return canCnt;
	}
	public void setCanCnt(String canCnt) {
		this.canCnt = canCnt;
	}
	public String getNewCnt() {
		return newCnt;
	}
	public void setNewCnt(String newCnt) {
		this.newCnt = newCnt;
	}
	public String getTmntCnt() {
		return tmntCnt;
	}
	public void setTmntCnt(String tmntCnt) {
		this.tmntCnt = tmntCnt;
	}
	public String getNetCnt() {
		return netCnt;
	}
	public void setNetCnt(String netCnt) {
		this.netCnt = netCnt;
	}
}
