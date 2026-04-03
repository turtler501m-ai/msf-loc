package com.ktmmobile.msf.system.common.mplatform.vo;

import com.ktmmobile.msf.system.common.util.XmlParse;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

public class MSimpleOsstXmlFt1VO extends CommonXmlVO{

	private String rsltCd;
	private String rsltMsg;

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

	@Override
    public void parse() throws UnsupportedEncodingException, ParseException {
		this.rsltCd = XmlParse.getChildValue(this.body, "rsltCd");
		this.rsltMsg = XmlParse.getChildValue(this.body, "rsltMsg");
	}
}