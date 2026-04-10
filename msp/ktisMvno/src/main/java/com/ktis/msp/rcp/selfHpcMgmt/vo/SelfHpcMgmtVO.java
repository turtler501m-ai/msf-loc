package com.ktis.msp.rcp.selfHpcMgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

public class SelfHpcMgmtVO extends BaseVo implements Serializable {

	private static final long serialVersionUID = 1L;

	//검색조건
	private String srchStrtDt;
	private String srchEndDt;
	private String searchName;
	private String searchGbn;
	private String srchHpcStat;
	private String srchHpcRst;
	private String srchAcenRst;   // A'cen 결과
	private String fstHpcRst;
	private String fstHpcRmk;
	private String sndHpcRst;
	private String sndHpcRmk;
	private String thdHpcRst;
	private String thdHpcRmk;
	private String hpcStat;
	private String hpcRst;
	private String hpcGbn; /*해피콜 후 수정시 몇차 해피콜인지 구분하기위한 구분자*/
	private String requestKey;
	
	public String getRequestKey() {
		return requestKey;
	}
	public void setRequestKey(String requestKey) {
		this.requestKey = requestKey;
	}
	public String getHpcGbn() {
		return hpcGbn;
	}
	public void setHpcGbn(String hpcGbn) {
		this.hpcGbn = hpcGbn;
	}
	public String getFstHpcRmk() {
		return fstHpcRmk;
	}
	public void setFstHpcRmk(String fstHpcRmk) {
		this.fstHpcRmk = fstHpcRmk;
	}
	public String getSndHpcRmk() {
		return sndHpcRmk;
	}
	public void setSndHpcRmk(String sndHpcRmk) {
		this.sndHpcRmk = sndHpcRmk;
	}
	public String getThdHpcRmk() {
		return thdHpcRmk;
	}
	public void setThdHpcRmk(String thdHpcRmk) {
		this.thdHpcRmk = thdHpcRmk;
	}
	public String getSrchStrtDt() {
		return srchStrtDt;
	}
	public void setSrchStrtDt(String srchStrtDt) {
		this.srchStrtDt = srchStrtDt;
	}
	public String getSrchEndDt() {
		return srchEndDt;
	}
	public void setSrchEndDt(String srchEndDt) {
		this.srchEndDt = srchEndDt;
	}
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	public String getSearchGbn() {
		return searchGbn;
	}
	public void setSearchGbn(String searchGbn) {
		this.searchGbn = searchGbn;
	}
	public String getSrchHpcStat() {
		return srchHpcStat;
	}
	public void setSrchHpcStat(String srchHpcStat) {
		this.srchHpcStat = srchHpcStat;
	}
	public String getSrchHpcRst() {
		return srchHpcRst;
	}
	public void setSrchHpcRst(String srchHpcRst) {
		this.srchHpcRst = srchHpcRst;
	}
	public String getSrchAcenRst() {
		return srchAcenRst;
	}
	public void setSrchAcenRst(String srchAcenRst) {
		this.srchAcenRst = srchAcenRst;
	}
	public String getFstHpcRst() {
		return fstHpcRst;
	}
	public void setFstHpcRst(String fstHpcRst) {
		this.fstHpcRst = fstHpcRst;
	}
	public String getSndHpcRst() {
		return sndHpcRst;
	}
	public void setSndHpcRst(String sndHpcRst) {
		this.sndHpcRst = sndHpcRst;
	}
	public String getThdHpcRst() {
		return thdHpcRst;
	}
	public void setThdHpcRst(String thdHpcRst) {
		this.thdHpcRst = thdHpcRst;
	}
	public String getHpcStat() {
		return hpcStat;
	}
	public void setHpcStat(String hpcStat) {
		this.hpcStat = hpcStat;
	}
	public String getHpcRst() {
		return hpcRst;
	}
	public void setHpcRst(String hpcRst) {
		this.hpcRst = hpcRst;
	}
	
	
}
