package com.ktmmobile.msf.domains.form.common.mplatform.vo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import org.jdom.JDOMException;


public interface ParseVO {
	void parse() throws UnsupportedEncodingException, ParseException;
	void toResponseParse() throws JDOMException, IOException;
}
