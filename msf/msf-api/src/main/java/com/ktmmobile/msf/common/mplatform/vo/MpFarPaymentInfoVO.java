package com.ktmmobile.msf.common.mplatform.vo;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import com.ktmmobile.msf.common.util.StringUtil;
import com.ktmmobile.msf.common.util.XmlParse;

/**
 *
 * 22. 납부/미납요금 조회 연동 규격
 * tobe x67 미납요금 월별 조회 테스트후에 같이 사용할수 있는건지 확인 필요
 */
public class MpFarPaymentInfoVO extends CommonXmlVO {
	private final static String PAYMENT_SARCH = "payMentSarch";
//	private final static String ITEM_PAY = "itemPay";
	private final static String NON_PAYMENT_SARCH = "nonPayMentSarch";
	private final static String NON_PAYMENT_TOT = "nonPayMentTot";
	private final static String NON_PAYMENT = "nonPayMent";

	private String chargesAmtSum  ;//당월요금계
	private String totPymCrdAmtSum;//총 청구요금계
	private String total          ;//총 미납요금계

	private String infoMsg; //안내메시지
	private List<ItemPay> itemPay;
	
	private List<NonPayMent> nonPayMent;
	@Override
	public void parse()  {
		itemPay = new ArrayList<ItemPay>();
		List<Element> payList = XmlParse.getChildElementList(this.body, "outItemPayDto");
		for(Element item : payList){
			ItemPay vo = new ItemPay();
			vo.setPayMentDate(XmlParse.getChildValue(item, "payMentDate"));
			vo.setPayMentMethod(XmlParse.getChildValue(item, "payMentMethod"));
			vo.setConfirmDate(XmlParse.getChildValue(item, "confirmDate"));
			vo.setPayMentMoney(XmlParse.getChildValue(item, "payMentMoney"));
			itemPay.add(vo);
		}

		// 미납 요금이 없을 경우 <noDate>현재 미납된 요금이 없습니다.</noDate> 값이 넘어옴.
		if (!StringUtil.isNotNull(XmlParse.getChildValue(this.body, "noDate"))) {
			nonPayMent = new ArrayList<NonPayMent>();
			List<Element> nonPayist = XmlParse.getChildElementList(this.body, "outItemNonPayDto");
			for(Element item : nonPayist){
				NonPayMent vo = new NonPayMent();
				vo.setMonth(XmlParse.getChildValue(item, "month"));
				vo.setDay(XmlParse.getChildValue(item, "day"));
				vo.setChargesAmt(XmlParse.getChildValue(item, "chargesAmt"));
				vo.setTotPymCrdAmt(XmlParse.getChildValue(item, "totPymCrdAmt"));
				vo.setInvSum(XmlParse.getChildValue(item, "invSum"));
				nonPayMent.add(vo);
			}

			//미납요금 합계
			Element nonPayMentTot = XmlParse.getChildElement(this.body, "outItemNonPaySumDto");
			this.chargesAmtSum = XmlParse.getChildValue(nonPayMentTot, "chargesAmtSum");
			this.totPymCrdAmtSum = XmlParse.getChildValue(nonPayMentTot, "totPymCrdAmtSum");
			this.total = XmlParse.getChildValue(nonPayMentTot, "total");
			this.infoMsg = XmlParse.getChildValue(nonPayMentTot, "infoMsg");
		}
		
	
		
	}


	public String getInfoMsg() {
		return infoMsg;
	}


	public void setInfoMsg(String infoMsg) {
		this.infoMsg = infoMsg;
	}


	public String getChargesAmtSum() {
		return chargesAmtSum;
	}

	public void setChargesAmtSum(String chargesAmtSum) {
		this.chargesAmtSum = chargesAmtSum;
	}

	public String getTotPymCrdAmtSum() {
		return totPymCrdAmtSum;
	}

	public void setTotPymCrdAmtSum(String totPymCrdAmtSum) {
		this.totPymCrdAmtSum = totPymCrdAmtSum;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public List<ItemPay> getItemPay() {
		return itemPay;
	}

	public void setItemPay(List<ItemPay> itemPay) {
		this.itemPay = itemPay;
	}

	public List<NonPayMent> getNonPayMent() {
		return nonPayMent;
	}

	public void setNonPayMent(List<NonPayMent> nonPayMent) {
		this.nonPayMent = nonPayMent;
	}

	public class OutNonPymnDto{
		private String month; //청구월
		private String invSum;	//미납요금
		public String getMonth() {
			return month;
		}
		public void setMonth(String month) {
			this.month = month;
		}
		public String getInvSum() {
			return invSum;
		}
		public void setInvSum(String invSum) {
			this.invSum = invSum;
		}

		
	}
	public class ItemPay{
		private String payMentDate    ;//수납일자
		private String payMentMethod  ;//수납방법
		private String confirmDate    ;//입금 확인일
		private String payMentMoney   ;//수납금액
		public String getPayMentDate() {
			return payMentDate;
		}
		public void setPayMentDate(String payMentDate) {
			this.payMentDate = payMentDate;
		}
		public String getPayMentMethod() {
			return payMentMethod;
		}
		public void setPayMentMethod(String payMentMethod) {
			this.payMentMethod = payMentMethod;
		}
		public String getConfirmDate() {
			return confirmDate;
		}
		public void setConfirmDate(String confirmDate) {
			this.confirmDate = confirmDate;
		}
		public String getPayMentMoney() {
			return payMentMoney;
		}
		public void setPayMentMoney(String payMentMoney) {
			this.payMentMoney = payMentMoney;
		}
	}

	public class NonPayMent{
		private String month          ;//청구월
		private String day            ;//납기일
		private String chargesAmt     ;//당월요금
		private String totPymCrdAmt   ;//총 청구요금계
		private String invSum         ;//미납요금
		public String getMonth() {
			return month;
		}
		public void setMonth(String month) {
			this.month = month;
		}
		public String getDay() {
			return day;
		}
		public void setDay(String day) {
			this.day = day;
		}
		public String getChargesAmt() {
			return chargesAmt;
		}
		public void setChargesAmt(String chargesAmt) {
			this.chargesAmt = chargesAmt;
		}
		public String getTotPymCrdAmt() {
			return totPymCrdAmt;
		}
		public void setTotPymCrdAmt(String totPymCrdAmt) {
			this.totPymCrdAmt = totPymCrdAmt;
		}
		public String getInvSum() {
			return invSum;
		}
		public void setInvSum(String invSum) {
			this.invSum = invSum;
		}
	}

	public static String getPaymentSarch() {
		return PAYMENT_SARCH;
	}

	public static String getNonPaymentSarch() {
		return NON_PAYMENT_SARCH;
	}

	public static String getNonPaymentTot() {
		return NON_PAYMENT_TOT;
	}

	public static String getNonPayment() {
		return NON_PAYMENT;
	}



	
}
