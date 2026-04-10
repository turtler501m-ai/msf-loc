package com.ktmmobile.msf.domains.form.common.mplatform.dto;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import com.ktmmobile.msf.domains.form.common.util.XmlParse;

public class MoscInqrUsimUsePsblOutDTO extends com.ktmmobile.msf.domains.form.common.mplatform.vo.CommonXmlVO{

    private String psblYn ;
    private String rsltMsg  ;


    @Override
    public void parse() throws UnsupportedEncodingException, ParseException {

        this.psblYn = XmlParse.getChildValue(this.body, "psblYn");
        this.rsltMsg = XmlParse.getChildValue(this.body, "rsltMsg");
    }


	public String getPsblYn() {
		return psblYn;
	}


	public void setPsblYn(String psblYn) {
		this.psblYn = psblYn;
	}


	public String getRsltMsg() {
		return rsltMsg;
	}


	public void setRsltMsg(String rsltMsg) {
		this.rsltMsg = rsltMsg;
	}

}