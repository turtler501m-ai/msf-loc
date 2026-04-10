package com.ktis.msp.cmn.btchmgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

public class JuiceBtchLogMgmtVO extends BaseVo implements Serializable {
	/**
	 *  serialVersion UID
	 */
	private static final long serialVersionUID = 1L;
	
	private String oprtSctnCd;
	
	private String cdDsc;
	
	private String oprtStatCd;
	
	private String oprtStatNm;
	
	private String oprtStrtDt;
	
	private String oprtEndDt;
	
	private String remrk;
	
	private String oprtSuccDataCnt;
	
	private String oprtFileNm;
	
	private String dtlCnt;

	public String getOprtSctnCd() {
		return oprtSctnCd;
	}

	public void setOprtSctnCd(String oprtSctnCd) {
		this.oprtSctnCd = oprtSctnCd;
	}

	public String getCdDsc() {
		return cdDsc;
	}

	public void setCdDsc(String cdDsc) {
		this.cdDsc = cdDsc;
	}

	public String getOprtStatCd() {
		return oprtStatCd;
	}

	public void setOprtStatCd(String oprtStatCd) {
		this.oprtStatCd = oprtStatCd;
	}

	public String getOprtStatNm() {
		return oprtStatNm;
	}

	public void setOprtStatNm(String oprtStatNm) {
		this.oprtStatNm = oprtStatNm;
	}

	public String getOprtStrtDt() {
		return oprtStrtDt;
	}

	public void setOprtStrtDt(String oprtStrtDt) {
		this.oprtStrtDt = oprtStrtDt;
	}

	public String getOprtEndDt() {
		return oprtEndDt;
	}

	public void setOprtEndDt(String oprtEndDt) {
		this.oprtEndDt = oprtEndDt;
	}

	public String getRemrk() {
		return remrk;
	}

	public void setRemrk(String remrk) {
		this.remrk = remrk;
	}

	public String getOprtSuccDataCnt() {
		return oprtSuccDataCnt;
	}

	public void setOprtSuccDataCnt(String oprtSuccDataCnt) {
		this.oprtSuccDataCnt = oprtSuccDataCnt;
	}

	public String getOprtFileNm() {
		return oprtFileNm;
	}

	public void setOprtFileNm(String oprtFileNm) {
		this.oprtFileNm = oprtFileNm;
	}

	public String getDtlCnt() {
		return dtlCnt;
	}

	public void setDtlCnt(String dtlCnt) {
		this.dtlCnt = dtlCnt;
	}
}
