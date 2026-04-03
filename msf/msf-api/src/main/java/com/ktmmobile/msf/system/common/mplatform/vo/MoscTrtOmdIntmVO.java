package com.ktmmobile.msf.system.common.mplatform.vo;

import com.ktmmobile.msf.system.common.util.XmlParse;

public class MoscTrtOmdIntmVO extends com.ktmmobile.msf.system.common.mplatform.vo.CommonXmlNoSelfServiceException {

	private String trtResult; // 처리결과
	private String trtMsg; // 처리메시지
	private String intmModelNm; // 기기모델명
	private String intmModelId; // 기기모델ID
	private String imei; // imei

	@Override
	public void parse()  {
		this.trtResult = XmlParse.getChildValue(this.body, "trtResult");
		this.trtMsg = XmlParse.getChildValue(this.body, "trtMsg");
		this.intmModelNm = XmlParse.getChildValue(this.body, "intmModelNm");
		this.intmModelId = XmlParse.getChildValue(this.body, "intmModelId");
		this.imei = XmlParse.getChildValue(this.body, "imei");
	}

	public String getTrtResult() {
		return trtResult;
	}

	public void setTrtResult(String trtResult) {
		this.trtResult = trtResult;
	}

	public String getTrtMsg() {
		return trtMsg;
	}

	public void setTrtMsg(String trtMsg) {
		this.trtMsg = trtMsg;
	}

	public String getIntmModelNm() {
		return intmModelNm;
	}

	public void setIntmModelNm(String intmModelNm) {
		this.intmModelNm = intmModelNm;
	}

	public String getIntmModelId() {
		return intmModelId;
	}

	public void setIntmModelId(String intmModelId) {
		this.intmModelId = intmModelId;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}


}
