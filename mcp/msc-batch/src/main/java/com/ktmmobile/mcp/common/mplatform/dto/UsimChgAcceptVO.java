package com.ktmmobile.mcp.common.mplatform.dto;

import com.ktmmobile.mcp.common.util.XmlParse;
import org.jdom.Element;

import java.io.UnsupportedEncodingException;

public class UsimChgAcceptVO extends CommonXmlVO {

	private String rsltCd;	      // 결과코드 (00 성공, 99 실패)
	private String rsltMsg;	      // 결과메세지
	private long osstNo;          // 유심교체신청 OSST연동 이력 시퀀스
	private String contractNum;   // 계약번호
	private String svcCntrNo;     // 서비스계약번호

	@Override
	public void parse() throws UnsupportedEncodingException {
		Element item = this.body;
		this.rsltCd = XmlParse.getChildValue(item, "rsltCd");
		this.rsltMsg = XmlParse.getChildValue(item, "rsltMsg");
	}

	public String getRsltCd() {
		return rsltCd;
	}

	public void setRsltCd(String rsltCd) {
		this.rsltCd = rsltCd;
	}

	public String getRsltMsg() {
		return rsltMsg;
	}

	public void setRsltMsg(String rsltMsg) {
		this.rsltMsg = rsltMsg;
	}

	public long getOsstNo() {
		return osstNo;
	}

	public void setOsstNo(long osstNo) {
		this.osstNo = osstNo;
	}

	public String getContractNum() {
		return contractNum;
	}

	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}

	public String getSvcCntrNo() {
		return svcCntrNo;
	}

	public void setSvcCntrNo(String svcCntrNo) {
		this.svcCntrNo = svcCntrNo;
	}
}
