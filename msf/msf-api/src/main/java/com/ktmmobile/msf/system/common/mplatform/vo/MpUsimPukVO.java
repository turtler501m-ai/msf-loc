package com.ktmmobile.msf.system.common.mplatform.vo;

import com.ktmmobile.msf.system.common.util.XmlParse;

public class MpUsimPukVO extends CommonXmlVO {

	private String intmMdlId;  //유심모델ID
	private String intmSeq;    //유심일련번호
	private String pukNo1;     //PUK NO

	@Override
	public void parse()  {
		this.intmMdlId = XmlParse.getChildValue(this.body, "intmMdlId");
		this.intmSeq = XmlParse.getChildValue(this.body, "intmSeq");
		this.pukNo1 = XmlParse.getChildValue(this.body, "pukNo1");
	}
	
	public String getIntmMdlId() {
		return intmMdlId;
	}

	public void setIntmMdlId(String intmMdlId) {
		this.intmMdlId = intmMdlId;
	}

	public String getIntmSeq() {
		return intmSeq;
	}

	public void setIntmSeq(String intmSeq) {
		this.intmSeq = intmSeq;
	}

	public String getPukNo1() {
		return pukNo1;
	}

	public void setPukNo1(String pukNo1) {
		this.pukNo1 = pukNo1;
	}
}
