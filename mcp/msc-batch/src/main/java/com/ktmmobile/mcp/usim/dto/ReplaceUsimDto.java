package com.ktmmobile.mcp.usim.dto;

import java.io.Serializable;

public class ReplaceUsimDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private long seq;
	private long subSeq;
	private String applyRsltCd;
	private String applyRsltMsg;
	private long osstNo;

	public long getSeq() {
		return seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}

	public long getSubSeq() {
		return subSeq;
	}

	public void setSubSeq(long subSeq) {
		this.subSeq = subSeq;
	}

	public String getApplyRsltCd() {
		return applyRsltCd;
	}

	public void setApplyRsltCd(String applyRsltCd) {
		this.applyRsltCd = applyRsltCd;
	}

	public String getApplyRsltMsg() {
		return applyRsltMsg;
	}

	public void setApplyRsltMsg(String applyRsltMsg) {
		this.applyRsltMsg = applyRsltMsg;
	}

	public long getOsstNo() {
		return osstNo;
	}

	public void setOsstNo(long osstNo) {
		this.osstNo = osstNo;
	}
}
