package com.ktis.mcpif.mPlatform.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class XmlParse extends StringUtils {

	public static Element getRootElement(String xml) throws JDOMException, IOException {
		InputStream in = new ByteArrayInputStream(xml.getBytes("euc-kr"));
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(in);

		Element root = document.getRootElement();

		return root;
	}

	@SuppressWarnings("unchecked")
	public static List<Element> getChildElementList(Element element, String name){
		List<Element> list = null;
		try {
			list = (List<Element>)element.getChildren(name);
		} catch (NullPointerException e) {
			list = null;
		}
		return list;
	}

	public static Element getChildElement(Element element, String name){
		Element result = null;
		result = element.getChild(name);
		return result;
	}

	public static String getChildValue(Element element, String name){
		String result = null;
		try {
			result = element.getChild(name).getValue();
		} catch (NullPointerException ne) {
			result = "";
		}
		return result == null ? "" : decode(result, "null", "", result);
	}

	public static String getAttributeValue(Element element, String name){
		String result = null;
		try {
			result = element.getAttributeValue(name);
		} catch (NullPointerException ne) {
			result = "";
		}
		return result == null ? "" : decode(result, "null", "", result);
	}

	public static Element getReturnElement(Element element) {
		Element result = element;
		if (result != null) {
			while(!result.getName().equals("return")) {
				if (result.getChildren() != null) {
					result = (Element) result.getChildren().get(0);
				}
			}
		}
		return result;
	}
	
	public static String decode(String str1, String str2, String ifStr, String elseStr){
		String result = str1;
		try{
			if( StringUtils.equalsIgnoreCase(str1, str2) ) result = ifStr;
			else result = elseStr;
		}catch (Exception e) {

		}
		return result;
	}

	public static String decode(boolean flag, String ifStr, String elseStr){
		String result = ifStr;
		try{
			if(flag) result = ifStr;
			else result = elseStr;
		}catch (Exception e) {

		}
		return result;
	}
}

