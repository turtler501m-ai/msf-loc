package com.ktis.msp.rsk.statMgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

public class SaleSttcMgmtAgncyVO extends BaseVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8033231992271643404L;
	private String agncyCd;
	private String agncyNm;
	private String buyType;
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
	private String dataType;
	
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getAgncyCd() {
		return agncyCd;
	}
	public void setAgncyCd(String agncyCd) {
		this.agncyCd = agncyCd;
	}
	public String getAgncyNm() {
		return agncyNm;
	}
	public void setAgncyNm(String agncyNm) {
		this.agncyNm = agncyNm;
	}
	public String getBuyType() {
		return buyType;
	}
	public void setBuyType(String buyType) {
		this.buyType = buyType;
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
