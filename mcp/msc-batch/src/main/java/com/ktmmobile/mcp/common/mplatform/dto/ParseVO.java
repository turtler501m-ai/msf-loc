package com.ktmmobile.mcp.common.mplatform.dto;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import org.jdom.JDOMException;


public interface ParseVO {
	public void parse() throws UnsupportedEncodingException, ParseException;
	public void toResponseParse() throws JDOMException, IOException;
}
