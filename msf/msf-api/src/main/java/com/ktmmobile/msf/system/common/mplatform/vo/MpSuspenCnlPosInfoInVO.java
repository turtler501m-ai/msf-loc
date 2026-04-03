package com.ktmmobile.msf.system.common.mplatform.vo;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import com.ktmmobile.msf.system.common.util.XmlParse;

public class MpSuspenCnlPosInfoInVO extends CommonXmlVO{
	private Map<String, String> map;

	@Override
	public void parse() throws UnsupportedEncodingException, ParseException {
		map = new HashMap<String, String>();
		map.put("rsltInd", XmlParse.getChildValue(this.body, "rsltInd"));									//일시정지 가능여부
		map.put("rsltMsg", XmlParse.getChildValue(this.body, "rsltMsg"));									//일시정지메시지
		map.put("subStatusRsnCode", XmlParse.getChildValue(this.body, "subStatusRsnCode"));	//전화번호 상태사유코드
		map.put("sndarvStatCd", XmlParse.getChildValue(this.body, "sndarvStatCd"));					//발/착신구분코드
		map.put("rsnDesc", XmlParse.getChildValue(this.body, "rsnDesc"));								//사유코드설명
		map.put("subStatusDate", XmlParse.getChildValue(this.body, "subStatusDate"));				//일시정지일자
		map.put("ctnStatus", XmlParse.getChildValue(this.body, "ctnStatus"));							//전화번호상태
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
