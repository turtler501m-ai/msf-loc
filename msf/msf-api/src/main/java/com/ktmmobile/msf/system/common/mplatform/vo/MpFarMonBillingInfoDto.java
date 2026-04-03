package com.ktmmobile.msf.system.common.mplatform.vo;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import com.ktmmobile.msf.system.common.util.DateTimeUtil;
import com.ktmmobile.msf.system.common.util.StringUtil;
import com.ktmmobile.msf.system.common.util.XmlParse;

public class MpFarMonBillingInfoDto extends CommonXmlVO{
	private final static String MONTH_PAYMENT = "monthPayMent";
	private final static String CTN_NUM = "ctnNum";

	private String resultMessage;
	private String billSeqNumList;
	private String billStartDateList;
	private String billEndDateList;
	private String totalSum;
	private String productionDateA;

	private List<MpMonthPayMentDto> monthList;
	private List<MpCtnNumDataDto> ctnNumList;

	@Override
	public void parse() throws UnsupportedEncodingException, ParseException {
//		this.enckey = body.getAttributeValue("a");
		this.billSeqNumList = XmlParse.getChildValue(this.body, "billSeqNumList");
		this.billStartDateList = XmlParse.getChildValue(this.body, "billStartDateList");
		this.billEndDateList = XmlParse.getChildValue(this.body, "billEndDateList");

		List<Element> monthPayMent = XmlParse.getChildElementList(this.body, "payMentDTO");

		if(monthPayMent.size() > 0 ){
			monthList = new ArrayList<MpMonthPayMentDto>();

			for(Element item : monthPayMent){
				MpMonthPayMentDto vo = new MpMonthPayMentDto();
				vo.setBillSeqNo(XmlParse.getChildValue(item, "billSeqNo"));
				vo.setBillStartDate(XmlParse.getChildValue(item, "billStartDate"));
				vo.setBillEndDate(XmlParse.getChildValue(item, "billEndDate"));
				vo.setBillMonth(XmlParse.getChildValue(item, "billMonth"));
				vo.setThisMonth(XmlParse.getChildValue(item, "thisMonth"));
				vo.setPastDueAmt(XmlParse.getChildValue(item, "pastDueAmt"));
				vo.setTotalDueAmt(XmlParse.getChildValue(item, "totalDueAmt"));
				vo.setBillDueDateList(XmlParse.getChildValue(item, "billDueDateList"));

				if(StringUtil.isNotNull(vo.getBillMonth())) {
					String year = vo.getBillMonth().substring(0,4);
					String month = vo.getBillMonth().substring(4,6);
					vo.setBillMonth(year + month);
					vo.setMonth(StringUtil.toInteger(month));
					String[] dates = StringUtil.getDateSplit(DateTimeUtil.addMonths(year+month+"01", -1));
					String searchBillMonth = dates[0]+"/"+dates[1];
					vo.setSearchBillMonth(searchBillMonth);
				}

				monthList.add(vo);
			}

//			Element ctnNum = XmlParse.getChildElement(this.body, CTN_NUM);
			this.totalSum = XmlParse.getChildValue(this.body, "ctnNumTotalSum");
			this.productionDateA = XmlParse.getChildValue(this.body, "ctnNumproductionDate");
			List<Element> ctnNumData = XmlParse.getChildElementList(this.body, "payMentSumDTO");

			if(ctnNumData.size() > 0) {
				ctnNumList = new ArrayList<MpCtnNumDataDto>();

				for( Element item : ctnNumData ){
					MpCtnNumDataDto vo = new MpCtnNumDataDto();
					vo.setTel(XmlParse.getChildValue(item, "tel"));
					vo.setLinkName(XmlParse.getChildValue(item, "linkName"));
					vo.setSocDesc(XmlParse.getChildValue(item, "socDesc"));
					vo.setCurrCharge(XmlParse.getChildValue(item, "currCharge"));

					ctnNumList.add(vo);
				}
			}
		}
	}

	public String getBillSeqNumList() {
		return billSeqNumList;
	}
	public void setBillSeqNumList(String billSeqNumList) {
		this.billSeqNumList = billSeqNumList;
	}
	public String getBillStartDateList() {
		return billStartDateList;
	}
	public void setBillStartDateList(String billStartDateList) {
		this.billStartDateList = billStartDateList;
	}
	public String getBillEndDateList() {
		return billEndDateList;
	}
	public void setBillEndDateList(String billEndDateList) {
		this.billEndDateList = billEndDateList;
	}
	public List<MpMonthPayMentDto> getMonthList() {
		return monthList;
	}
	public void setMonthList(List<MpMonthPayMentDto> monthList) {
		this.monthList = monthList;
	}
	public List<MpCtnNumDataDto> getCtnNumList() {
		return ctnNumList;
	}
	public void setCtnNumList(List<MpCtnNumDataDto> ctnNumList) {
		this.ctnNumList = ctnNumList;
	}
	public String getTotalSum() {
		return totalSum;
	}
	public void setTotalSum(String totalSum) {
		this.totalSum = totalSum;
	}
	public String getProductionDate_a() {
		return productionDateA;
	}
	public void setProductionDate_a(String productionDateA) {
		this.productionDateA = productionDateA;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}



	public static String getMonthPayment() {
		return MONTH_PAYMENT;
	}

	public static String getCtnNum() {
		return CTN_NUM;
	}

}
