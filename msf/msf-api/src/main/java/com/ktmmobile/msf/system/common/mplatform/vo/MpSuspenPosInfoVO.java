package com.ktmmobile.msf.system.common.mplatform.vo;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import com.ktmmobile.msf.system.common.util.XmlParse;

public class MpSuspenPosInfoVO extends CommonXmlVO{
	private Map<String, String> map;

	@Override
	public void parse() throws UnsupportedEncodingException, ParseException {
		map = new HashMap<String, String>();
		map.put("ctnStatus", XmlParse.getChildValue(this.body, "ctnStatus"));
		map.put("rsltInd", XmlParse.getChildValue(this.body, "rsltInd"));
		map.put("rsltMsg", XmlParse.getChildValue(this.body, "rsltMsg"));
		map.put("insurMsg", XmlParse.getChildValue(this.body, "insurMsg"));
	}

	/**
	 * @return the map
	 */
	public Map<String, String> getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(Map<String, String> map) {
		this.map = map;
	}

}
