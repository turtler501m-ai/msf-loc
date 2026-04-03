package com.ktmmobile.msf.system.common.mplatform.vo;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import com.ktmmobile.msf.system.common.util.XmlParse;

public class MpPcsLostInfoVO extends CommonXmlVO{
	private Map<String, String> map;

	@Override
	public void parse() throws UnsupportedEncodingException, ParseException {
		map = new HashMap<String, String>();
		map.put("runMode", XmlParse.getChildValue(this.body, "runMode"));
		map.put("asfdYn", XmlParse.getChildValue(this.body, "asfdYn"));
		map.put("subStatusLastAct", XmlParse.getChildValue(this.body, "subStatusLastAct"));
		map.put("coldLinqStatus", XmlParse.getChildValue(this.body, "coldLinqStatus"));
		map.put("rsltCd", XmlParse.getChildValue(this.body, "rsltCd"));
		map.put("rsltMsg", XmlParse.getChildValue(this.body, "rsltMsg"));
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
