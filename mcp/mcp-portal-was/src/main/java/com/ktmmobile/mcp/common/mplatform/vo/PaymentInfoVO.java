package com.ktmmobile.mcp.common.mplatform.vo;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;


import com.ktmmobile.mcp.common.util.XmlParse;

public class PaymentInfoVO extends CommonXmlVO {
	private String total          ;//총 미납요금계
	private String infoMsg; //안내메시지
	private List<OutNonPymnDto> outNonPymnList;
	private String url; 
	
	//x92
	private String currMthNpayAmt; //당월 미납요금
	private String totNpayAmt;	//총미납요금
	private String payTgtAmt;	//납입대상요금

	
	public class OutNonPymnDto{
		private String invSum;
		private String month;
		public String getInvSum() {
			return invSum;
		}
		public void setInvSum(String invSum) {
			this.invSum = invSum;
		}
		public String getMonth() {
			return month;
		}
		public void setMonth(String month) {
			this.month = month;
		}
		
		
	}
	
	
	public String getTotal() {
		return total;
	}


	public void setTotal(String total) {
		this.total = total;
	}


	public String getInfoMsg() {
		return infoMsg;
	}


	public void setInfoMsg(String infoMsg) {
		this.infoMsg = infoMsg;
	}


	public List<OutNonPymnDto> getOutNonPymnList() {
		return outNonPymnList;
	}


	public void setOutNonPymnList(List<OutNonPymnDto> outNonPymnList) {
		this.outNonPymnList = outNonPymnList;
	}

	
	

	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}

	

	public String getCurrMthNpayAmt() {
		return currMthNpayAmt;
	}


	public void setCurrMthNpayAmt(String currMthNpayAmt) {
		this.currMthNpayAmt = currMthNpayAmt;
	}


	public String getTotNpayAmt() {
		return totNpayAmt;
	}


	public void setTotNpayAmt(String totNpayAmt) {
		this.totNpayAmt = totNpayAmt;
	}


	public String getPayTgtAmt() {
		return payTgtAmt;
	}


	public void setPayTgtAmt(String payTgtAmt) {
		this.payTgtAmt = payTgtAmt;
	}


	@Override
	public void parse() throws UnsupportedEncodingException, ParseException {
		this.total = XmlParse.getChildValue(this.body, "total");
		this.infoMsg = XmlParse.getChildValue(this.body, "infoMsg");
		this.url = XmlParse.getChildValue(this.body, "url");
		this.currMthNpayAmt = XmlParse.getChildValue(this.body, "currMthNpayAmt");
		this.totNpayAmt = XmlParse.getChildValue(this.body, "totNpayAmt");
		this.payTgtAmt = XmlParse.getChildValue(this.body, "payTgtAmt");

        List<Element> itemList = XmlParse.getChildElementList(this.body, "outNonPymnDto");
        outNonPymnList = new ArrayList<OutNonPymnDto>();

		for(Element item : itemList){
			OutNonPymnDto vo = new OutNonPymnDto();
			vo.setInvSum(XmlParse.getChildValue(item, "invSum"));
			vo.setMonth(XmlParse.getChildValue(item, "month"));
			outNonPymnList.add(vo);
		}
		
		
	}

}
