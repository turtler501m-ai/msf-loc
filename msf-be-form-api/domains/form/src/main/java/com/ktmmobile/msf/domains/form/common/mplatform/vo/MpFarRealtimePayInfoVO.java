package com.ktmmobile.msf.domains.form.common.mplatform.vo;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import com.ktmmobile.msf.domains.form.common.util.XmlParse;

public class MpFarRealtimePayInfoVO extends CommonXmlVO {
	private final static String BILL_DATE = "billDate";
	private final static String REAL_FARE = "realFare";
	private final static String GUBUN = "gubun";
	private final static String PAYMENT = "payMent";

	private String searchDay;//조회 날짜(현재 날짜)
	private String searchTime;//조회 기간(현재 월 1일 ~ 현재 월 일)
	private String sumItem;
	private String sumAmt;


	private List<RealFareVO> list;
	@Override
	public void parse()  {
//		Element billDate = XmlParse.getChildElement(this.body, BILL_DATE);
		this.searchDay = XmlParse.getChildValue(this.body, "searchDay");
		this.searchTime = XmlParse.getChildValue(this.body, "searchTime");

		List<Element> itemList = XmlParse.getChildElementList(this.body, "amntDto");
		list = new ArrayList<RealFareVO>();
		for(Element item : itemList){
			if(!XmlParse.getChildValue(item, GUBUN).trim().equals("원단위절사금액")){
			RealFareVO vo = new RealFareVO();
			vo.setGubun(XmlParse.getChildValue(item, GUBUN));
			vo.setPayment(XmlParse.getChildValue(item, PAYMENT));

			list.add(vo);

			if (XmlParse.getChildValue(item, GUBUN).trim().equals("당월요금계"))
				this.sumAmt = XmlParse.getChildValue(item, PAYMENT);
			}
		}
	}
	public String getSearchDay() {
		if (searchDay == null) {return "";}
		return searchDay;
	}
	public void setSearchDay(String searchDay) {
		this.searchDay = searchDay;
	}
	public String getSearchTime() {
		return searchTime;
	}
	public void setSearchTime(String searchTime) {
		this.searchTime = searchTime;
	}
	public List<RealFareVO> getList() {
		return list;
	}
	public void setList(List<RealFareVO> list) {
		this.list = list;
	}
	public String getSumItem() {
		return sumItem;
	}
	public void setSumItem(String sumItem) {
		this.sumItem = sumItem;
	}
	public String getSumAmt() {
		return sumAmt;
	}
	public void setSumAmt(String sumAmt) {
		this.sumAmt = sumAmt;
	}

	public class RealFareVO {
		private String gubun;//요금항목명 예) 단말기대금, 월정액, 요금할인액 등…
		private String payment;//요금금액예) 19,000 원
		public String getGubun() {
			return gubun;
		}
		public void setGubun(String gubun) {
			this.gubun = gubun;
		}
		public String getPayment() {
			return payment;
		}
		public void setPayment(String payment) {
			this.payment = payment;
		}
	}

	public static String getBillDate() {
		return BILL_DATE;
	}
	public static String getRealFare() {
		return REAL_FARE;
	}
	public static String getGubun() {
		return GUBUN;
	}
	public static String getPayment() {
		return PAYMENT;
	}


}
