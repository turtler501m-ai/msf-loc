package com.ktmmobile.msf.domains.form.common.mplatform.dto;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import com.ktmmobile.msf.domains.form.common.util.XmlParse;

public class MoscFarPriceChgDto extends com.ktmmobile.msf.domains.form.common.mplatform.vo.CommonXmlVO {
	//x88
	private String rsltYn; //최종변경여부 Y(요금제 변경 성공)/N(요금제 변경실패)
	private List<M_messageDto> msgList;
	private String rsltCode; //완료코드
	private String ruleMsg; //완료메시지

	     
	
	public String getRsltYn() {
		return rsltYn;
	}

	public void setRsltYn(String rsltYn) {
		this.rsltYn = rsltYn;
	}

	public List<M_messageDto> getMsgList() {
		return msgList;
	}

	public void setMsgList(List<M_messageDto> msgList) {
		this.msgList = msgList;
	}

	public String getRsltCode() {
		return rsltCode;
	}

	public void setRsltCode(String rsltCode) {
		this.rsltCode = rsltCode;
	}

	public String getRuleMsg() {
		return ruleMsg;
	}

	public void setRuleMsg(String ruleMsg) {
		this.ruleMsg = ruleMsg;
	}

	public class M_messageDto {
		private String rsltCd; //Y(안내메시지)/N(제약메시지)
		private String ruleId; //예)MSG_100999998_1 예)100001765
		private String ruleMsgSbst; //룰메시지내용	100	O	"예)현재 선택한 요금제에서는 가입할 수 없는 부가서비스[착신전환(무료)_대표]입니다
		public String getRsltCd() {
			return rsltCd;
		}
		public void setRsltCd(String rsltCd) {
			this.rsltCd = rsltCd;
		}
		public String getRuleId() {
			return ruleId;
		}
		public void setRuleId(String ruleId) {
			this.ruleId = ruleId;
		}
		public String getRuleMsgSbst() {
			return ruleMsgSbst;
		}
		public void setRuleMsgSbst(String ruleMsgSbst) {
			this.ruleMsgSbst = ruleMsgSbst;
		}


	}
	
	@Override
	public void parse() throws UnsupportedEncodingException, ParseException {
		this.rsltYn = XmlParse.getChildValue(this.body, "rsltYn");
		msgList = new ArrayList<M_messageDto>();

		List<Element> message = XmlParse.getChildElementList(this.body, "message");

		if(message.size() > 0) {
			for(Element item : message){
				M_messageDto vo = new M_messageDto();
				vo.setRsltCd(XmlParse.getChildValue(item, "rsltCd"));
				vo.setRuleId(XmlParse.getChildValue(item, "ruleId"));
				vo.setRuleMsgSbst(XmlParse.getChildValue(item, "ruleMsgSbst"));
				msgList.add(vo);

			}
		}

		
	}
}
