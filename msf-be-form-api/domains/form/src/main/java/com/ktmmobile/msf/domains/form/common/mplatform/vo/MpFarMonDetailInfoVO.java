package com.ktmmobile.msf.domains.form.common.mplatform.vo;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import com.ktmmobile.msf.domains.form.common.util.DateTimeUtil;
import com.ktmmobile.msf.domains.form.common.util.StringMakerUtil;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
import com.ktmmobile.msf.domains.form.common.util.XmlParse;

public class MpFarMonDetailInfoVO extends CommonXmlVO{
	private final static String MON_DETAIL_INFO = "monDetailInfo";
	private final static String INSTALLMENT = "installment";
	private final static String ITEM_ETC = "itemEtc";

	private String dateView;//청구년월
	private String useDate;//청구시작일 ~ 청구종료일 --청구시작월과 청구종료월 데이터가 있을 경우
	private String allOfBillSeqNo;//전체청구일련번호

	private String installmentYN = "N";
	private String subscriberNo;//전화번호
	private String installmentAmt;//잔여할부금액
	private String totalNoOfInstall;//잔여개월
	private String lastInstallMonth;

	private List<ItemEtcVO> itemList;
	@Override
	public void parse() throws ParseException {
//		Element monDetailInfo = XmlParse.getChildElement(this.body, MON_DETAIL_INFO);
		this.dateView = XmlParse.getChildValue(this.body, "dateView");
		this.useDate = XmlParse.getChildValue(this.body, "useDate");
//		this.allOfBillSeqNo = XmlParse.getChildValue(monDetailInfo, "allOfBillSeqNo");

		List<Element> detList = XmlParse.getChildElementList(this.body, "detListDto");
		itemList = new ArrayList<ItemEtcVO>();
		for(Element item : detList){
			if(!XmlParse.getChildValue(item, "splitDescription").trim().equals("원단위절사금액")){//원단위절사금액 삭제
			ItemEtcVO vo = new ItemEtcVO();
			vo.setSplitDescription(XmlParse.getChildValue(item, "splitDescription"));
			vo.setActvAmt(XmlParse.getChildValue(item, "actvAmt"));
			vo.setBillSeqNo(XmlParse.getChildValue(item, "billSeqNo"));
			vo.setMessageLine(XmlParse.getChildValue(item, "messageLine"));
			itemList.add(vo);
			}
		}

		Element hndFarDto = XmlParse.getChildElement(this.body, "hndFarDto");
		this.subscriberNo = StringMakerUtil.getPhoneNum(XmlParse.getChildValue(hndFarDto, "subscriberNo"));
		//this.subscriberNo = XmlParse.getChildValue(hndFarDto, "subscriberNo");
		this.installmentAmt = XmlParse.getChildValue(hndFarDto, "installmentAmt");
		this.totalNoOfInstall = XmlParse.getChildValue(hndFarDto, "totalNoOfInstall");
		if( StringUtil.isNotNull(totalNoOfInstall) ){
			this.installmentYN = "Y";
			int month = StringUtil.toInteger(this.totalNoOfInstall.split(" ")[0]);
			this.lastInstallMonth = DateTimeUtil.addMonths(DateTimeUtil.getDateString(), month, "yyyy-MM-dd");
			this.lastInstallMonth = StringUtil.substringBeforeLast(this.lastInstallMonth, "-");
		}
	}
	public String getDateView() {
		return dateView;
	}
	public void setDateView(String dateView) {
		this.dateView = dateView;
	}
	public String getUseDate() {
		return useDate;
	}
	public void setUseDate(String useDate) {
		this.useDate = useDate;
	}
	public String getAllOfBillSeqNo() {
		return allOfBillSeqNo;
	}
	public void setAllOfBillSeqNo(String allOfBillSeqNo) {
		this.allOfBillSeqNo = allOfBillSeqNo;
	}
	public String getSubscriberNo() {
		return subscriberNo;
	}
	public void setSubscriberNo(String subscriberNo) {
		this.subscriberNo = subscriberNo;
	}
	public String getInstallmentAmt() {
		return installmentAmt;
	}
	public void setInstallmentAmt(String installmentAmt) {
		this.installmentAmt = installmentAmt;
	}
	public String getTotalNoOfInstall() {
		return totalNoOfInstall;
	}
	public void setTotalNoOfInstall(String totalNoOfInstall) {
		this.totalNoOfInstall = totalNoOfInstall;
	}
	public String getLastInstallMonth() {
		return lastInstallMonth;
	}
	public void setLastInstallMonth(String lastInstallMonth) {
		this.lastInstallMonth = lastInstallMonth;
	}
	public String getInstallmentYN() {
		return installmentYN;
	}
	public void setInstallmentYN(String installmentYN) {
		this.installmentYN = installmentYN;
	}
	public List<ItemEtcVO> getItemList() {
		return itemList;
	}
	public void setItemList(List<ItemEtcVO> itemList) {
		this.itemList = itemList;
	}
	public class ItemEtcVO {
		private String splitDescription;//분리청구계획 코드명
		private String actvAmt;//수납금액
		private String billSeqNo;//청구일련번호
		private String messageLine;//청구항목코드

		public String getSplitDescription() {
			return splitDescription;
		}
		public void setSplitDescription(String splitDescription) {
			this.splitDescription = splitDescription;
		}
		public String getActvAmt() {
			return actvAmt;
		}
		public void setActvAmt(String actvAmt) {
			this.actvAmt = actvAmt;
		}
		public String getBillSeqNo() {
			return billSeqNo;
		}
		public void setBillSeqNo(String billSeqNo) {
			this.billSeqNo = billSeqNo;
		}
		public String getMessageLine() {
			return messageLine;
		}
		public void setMessageLine(String messageLine) {
			this.messageLine = messageLine;
		}
	}
	public static String getMonDetailInfo() {
		return MON_DETAIL_INFO;
	}
	public static String getInstallment() {
		return INSTALLMENT;
	}
	public static String getItemEtc() {
		return ITEM_ETC;
	}

}
