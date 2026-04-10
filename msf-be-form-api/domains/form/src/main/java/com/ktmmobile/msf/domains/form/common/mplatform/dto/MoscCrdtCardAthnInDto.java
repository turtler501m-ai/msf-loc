package com.ktmmobile.msf.domains.form.common.mplatform.dto;

import com.ktmmobile.msf.domains.form.common.util.XmlParse;
import org.jdom.Element;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;

/**
 * 결합조회 x87
 **/
public class MoscCrdtCardAthnInDto extends com.ktmmobile.msf.domains.form.common.mplatform.vo.CommonXmlNoSelfServiceException{

	private String trtResult;		//처리결과
	private String trtMsg;		//처리메시지
	private String crdtCardKindCd;		//신용카드종류코드
	private String crdtCardNm;	   //신용카드명



	@Override
	public void parse() throws UnsupportedEncodingException, ParseException {
		List<Element> itemList = XmlParse.getChildElementList(this.body, "moscCombDtlListOutDTO");

		this.trtResult = XmlParse.getChildValue(this.body, "trtResult");
		this.trtMsg = XmlParse.getChildValue(this.body, "trtMsg");
		this.crdtCardKindCd = XmlParse.getChildValue(this.body, "crdtCardKindCd");
		this.crdtCardNm = XmlParse.getChildValue(this.body, "crdtCardNm");

	}

	public String getTrtResult() {
		return trtResult;
	}

	public void setTrtResult(String trtResult) {
		this.trtResult = trtResult;
	}

	public String getTrtMsg() {
		return trtMsg;
	}

	public void setTrtMsg(String trtMsg) {
		this.trtMsg = trtMsg;
	}

	public String getCrdtCardKindCd() {
		return crdtCardKindCd;
	}

	public void setCrdtCardKindCd(String crdtCardKindCd) {
		this.crdtCardKindCd = crdtCardKindCd;
	}

	public String getCrdtCardNm() {
		return crdtCardNm;
	}

	public void setCrdtCardNm(String crdtCardNm) {
		this.crdtCardNm = crdtCardNm;
	}
}
