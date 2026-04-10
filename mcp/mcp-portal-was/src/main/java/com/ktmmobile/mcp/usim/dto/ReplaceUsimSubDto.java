package com.ktmmobile.mcp.usim.dto;

import com.ktmmobile.mcp.common.util.MaskingUtil;
import com.ktmmobile.mcp.common.util.StringUtil;

import java.io.Serializable;

public class ReplaceUsimSubDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String mskMobileNo;
	private String resultCd;
	private String resultMsg;
	private long osstNo;

	public String getMskMobileNo() {
		return mskMobileNo;
	}

	public void setMskMobileNo(String mobileNo) {
		this.mskMobileNo = MaskingUtil.getMaskedTelNo(StringUtil.getMobileFullNum(mobileNo));
	}

	public String getResultCd() {
		return resultCd;
	}

	public void setResultCd(String resultCd) {
		this.resultCd = resultCd;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public long getOsstNo() {
		return osstNo;
	}

	public void setOsstNo(long osstNo) {
		this.osstNo = osstNo;
	}
}
