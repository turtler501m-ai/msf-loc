package com.ktmmobile.mcp.common.mplatform.vo;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.common.util.JKTFCryptoUtil;
import com.ktmmobile.mcp.common.util.StringMakerUtil;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.common.util.XmlParse;

public class MpFarMonBillingInfoVO extends CommonXmlVO{
	private final static String MONTH_PAYMENT = "monthPayMent";
	private final static String CTN_NUM = "ctnNum";

	private String resultMessage;
	private String billSeqNumList;
	private String billStartDateList;
	private String billEndDateList;
	private String totalSum;
	private String productionDateA;

	private List<M_MonthPayMentVO> monthList;
	private List<M_CtnNumDataVO> ctnNumList;

	@Override
	public void parse() throws UnsupportedEncodingException, ParseException {
//		this.enckey = body.getAttributeValue("a");
		this.billSeqNumList = XmlParse.getChildValue(this.body, "billSeqNumList");
		this.billStartDateList = XmlParse.getChildValue(this.body, "billStartDateList");
		this.billEndDateList = XmlParse.getChildValue(this.body, "billEndDateList");

		List<Element> monthPayMent = XmlParse.getChildElementList(this.body, "payMentDTO");

		if(monthPayMent.size() > 0 ){
			monthList = new ArrayList<M_MonthPayMentVO>();

			for(Element item : monthPayMent){
				M_MonthPayMentVO vo = new M_MonthPayMentVO();
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
				ctnNumList = new ArrayList<M_CtnNumDataVO>();

				for( Element item : ctnNumData ){
					M_CtnNumDataVO vo = new M_CtnNumDataVO();
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
	public List<M_MonthPayMentVO> getMonthList() {
		return monthList;
	}
	public void setMonthList(List<M_MonthPayMentVO> monthList) {
		this.monthList = monthList;
	}
	public List<M_CtnNumDataVO> getCtnNumList() {
		return ctnNumList;
	}
	public void setCtnNumList(List<M_CtnNumDataVO> ctnNumList) {
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

	public class M_CtnNumDataVO {
		private String tel;//전화번호         암호화
		private String linkName;//고객명     암호화
		private String socDesc;//요금상품명
		private String currCharge;//당월 요금계
		public String getTel() {
			return tel;
		}
		public void setTel(String tel) {
			this.tel = tel;
		}
		public String getLinkName() {
			return linkName;
		}
		public void setLinkName(String linkName) {
			this.linkName = linkName;
		}
		public String getSocDesc() {
			return socDesc;
		}
		public void setSocDesc(String socDesc) {
			this.socDesc = socDesc;
		}
		public String getCurrCharge() {
			return currCharge;
		}
		public void setCurrCharge(String currCharge) {
			this.currCharge = currCharge;
		}
	}

	public class M_MonthPayMentVO {
		private String billSeqNo;//청구일련번호
		private String billStartDate;//요금사용시작일
		private String billEndDate;//요금사용종료일
		private String billMonth ;// 청구년월
		private String searchBillMonth ;// 청구년월
		private String thisMonth ;// 당월요금
		private String pastDueAmt ;// 미납요금
		private String totalDueAmt ;// 청구총액
		private String billDueDateList ;// 전체청구년월
		private int month;

		public String getSearchBillMonth() {
			return searchBillMonth;
		}
		public void setSearchBillMonth(String searchBillMonth) {
			this.searchBillMonth = searchBillMonth;
		}
		public String getBillSeqNo() {
			return billSeqNo;
		}
		public void setBillSeqNo(String billSeqNo) {
			this.billSeqNo = billSeqNo;
		}
		public String getBillStartDate() {
			return billStartDate;
		}
		public void setBillStartDate(String billStartDate) {
			this.billStartDate = billStartDate;
		}
		public String getBillEndDate() {
			return billEndDate;
		}
		public void setBillEndDate(String billEndDate) {
			this.billEndDate = billEndDate;
		}
		public String getBillMonth() {
			return billMonth;
		}
		public void setBillMonth(String billMonth) {
			this.billMonth = billMonth;
		}
		public String getThisMonth() {
			return thisMonth;
		}
		public void setThisMonth(String thisMonth) {
			this.thisMonth = thisMonth;
		}
		public String getPastDueAmt() {
			return pastDueAmt;
		}
		public void setPastDueAmt(String pastDueAmt) {
			this.pastDueAmt = pastDueAmt;
		}
		public String getTotalDueAmt() {
			return totalDueAmt;
		}
		public void setTotalDueAmt(String totalDueAmt) {
			this.totalDueAmt = totalDueAmt;
		}
		public String getBillDueDateList() {
			return billDueDateList;
		}
		public void setBillDueDateList(String billDueDateList) {
			this.billDueDateList = billDueDateList;
		}
		public int getMonth() {
			return month;
		}
		public void setMonth(int month) {
			this.month = month;
		}

	}

	public static String getMonthPayment() {
		return MONTH_PAYMENT;
	}

	public static String getCtnNum() {
		return CTN_NUM;
	}

}
