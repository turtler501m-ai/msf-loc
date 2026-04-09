package com.ktmmobile.msf.common.mplatform.dto;


import com.ktmmobile.msf.common.util.XmlParse;


import java.io.UnsupportedEncodingException;
import java.text.ParseException;

/**
 * 데이터 쉐어링 결합 중인 대상 조회
 * x71
 */


public class MoscRoamQntResDto extends com.ktmmobile.msf.common.mplatform.vo.CommonXmlNoSelfServiceException{

	private String realTimeQnt;
	private String realTimeDataQnt;

	private String realTimeTotalQnt;

	public String getRealTimeQnt() {
		return realTimeQnt;
	}

	public void setRealTimeQnt(String realTimeQnt) {
		this.realTimeQnt = realTimeQnt;
	}

	public String getRealTimeDataQnt() {
		return realTimeDataQnt;
	}

	public void setRealTimeDataQnt(String realTimeDataQnt) {
		this.realTimeDataQnt = realTimeDataQnt;
	}

	public String getRealTimeTotalQnt() {
		return realTimeTotalQnt;
	}

	public void setRealTimeTotalQnt(String realTimeTotalQnt) {
		this.realTimeTotalQnt = realTimeTotalQnt;
	}




	@Override
	public void parse() throws UnsupportedEncodingException, ParseException {
		this.realTimeQnt = XmlParse.getChildValue(this.body, "realTimeQnt");
		this.realTimeDataQnt = XmlParse.getChildValue(this.body, "realTimeDataQnt");
		this.realTimeTotalQnt = XmlParse.getChildValue(this.body, "realTimeTotalQnt");
	}



}
