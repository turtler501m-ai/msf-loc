package com.ktmmobile.msf.system.common.mplatform.vo;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import com.ktmmobile.msf.system.common.util.XmlParse;

public class MSimpleOsstXmlUc0VO extends CommonXmlVO{

	// UC0
	private String osstOrdNo; // OSST 오더 번호
	private String rsltCd;    // 처리 결과 S : 성공, F : 실패
	
	private String mvnoOrdNo; // mvno 오더번호
	
    @Override
    public void parse() throws UnsupportedEncodingException, ParseException {
        this.osstOrdNo = XmlParse.getChildValue(this.body, "osstOrdNo");
        this.rsltCd = XmlParse.getChildValue(this.body, "rsltCd");
    }

	public String getMvnoOrdNo() {
		return mvnoOrdNo;
	}

	public void setMvnoOrdNo(String mvnoOrdNo) {
		this.mvnoOrdNo = mvnoOrdNo;
	}

	public String getOsstOrdNo() {
		return osstOrdNo;
	}

	public void setOsstOrdNo(String osstOrdNo) {
		this.osstOrdNo = osstOrdNo;
	}

	public String getRsltCd() {
		return rsltCd;
	}

	public void setRsltCd(String rsltCd) {
		this.rsltCd = rsltCd;
	}


}