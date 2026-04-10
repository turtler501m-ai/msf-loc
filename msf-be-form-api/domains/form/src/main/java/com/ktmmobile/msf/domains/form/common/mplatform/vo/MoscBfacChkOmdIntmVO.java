package com.ktmmobile.msf.domains.form.common.mplatform.vo;

import com.ktmmobile.msf.domains.form.common.util.XmlParse;

public class MoscBfacChkOmdIntmVO extends com.ktmmobile.msf.domains.form.common.mplatform.vo.CommonXmlNoSelfServiceException {

	private String trtResult; // 처리결과
	private String trtMsg; // 처리메시지
	private String intmModelId; // 기기모델ID
	private String intmModelNm; // 기기모델명
	private String intmSeq; // 기기일련번호
	private String euiccId; // eIccId
	private String rlthnIntmSeq; // 실물기기일련번호
	private String useYn; // 사용단말여부

	@Override
	public void parse()  {
		this.trtResult = XmlParse.getChildValue(this.body, "trtResult");
		this.trtMsg = XmlParse.getChildValue(this.body, "trtMsg");
		this.intmModelId = XmlParse.getChildValue(this.body, "intmModelId");
		this.intmModelNm = XmlParse.getChildValue(this.body, "intmModelNm");
		this.intmSeq = XmlParse.getChildValue(this.body, "intmSeq");
		this.euiccId = XmlParse.getChildValue(this.body, "euiccId");
		this.rlthnIntmSeq = XmlParse.getChildValue(this.body, "rlthnIntmSeq");
		this.useYn = XmlParse.getChildValue(this.body, "useYn");
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

	public String getIntmModelId() {
		return intmModelId;
	}

	public void setIntmModelId(String intmModelId) {
		this.intmModelId = intmModelId;
	}

	public String getIntmModelNm() {
		return intmModelNm;
	}

	public void setIntmModelNm(String intmModelNm) {
		this.intmModelNm = intmModelNm;
	}

	public String getIntmSeq() {
		return intmSeq;
	}

	public void setIntmSeq(String intmSeq) {
		this.intmSeq = intmSeq;
	}

	public String getEuiccId() {
		return euiccId;
	}

	public void setEuiccId(String euiccId) {
		this.euiccId = euiccId;
	}

	public String getRlthnIntmSeq() {
		return rlthnIntmSeq;
	}

	public void setRlthnIntmSeq(String rlthnIntmSeq) {
		this.rlthnIntmSeq = rlthnIntmSeq;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}



}
