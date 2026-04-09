package com.ktmmobile.msf.common.mplatform.vo;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ktmmobile.msf.common.util.XmlParse;

public class MoscBillReSendInfoOutVO extends com.ktmmobile.msf.common.mplatform.vo.CommonXmlVO {

	@Deprecated
	private static Logger logger = LoggerFactory.getLogger(MoscBillReSendInfoOutVO.class);

	private List<MoscBillReSendInfoOutDTO> outReSndList;
	private String month;
	private String orieMail;
	private String year;

	@Override
	public void parse() throws UnsupportedEncodingException, ParseException {

		this.month = XmlParse.getChildValue(this.body, "month");
		this.orieMail = XmlParse.getChildValue(this.body, "orieMail");
		List<Element> outReSndListDto = XmlParse.getChildElementList(this.body, "outReSndListDto");
		outReSndList = new ArrayList<MoscBillReSendInfoOutVO.MoscBillReSendInfoOutDTO>();

		for(Element item : outReSndListDto){
			MoscBillReSendInfoOutDTO  moscBillReSendInfoOutDTO = new MoscBillReSendInfoOutDTO();
			String billAdInfo = XmlParse.getChildValue(item,"billAdInfo");
			String creationDate = XmlParse.getChildValue(item,"creationDate");
			String resultCode = XmlParse.getChildValue(item,"resultCode");
			String thisMonth = XmlParse.getChildValue(item,"thisMonth");

			moscBillReSendInfoOutDTO.setBillAdInfo(billAdInfo);
			moscBillReSendInfoOutDTO.setCreationDate(creationDate);
			moscBillReSendInfoOutDTO.setResultCode(resultCode);
			moscBillReSendInfoOutDTO.setThisMonth(thisMonth);
			outReSndList.add(moscBillReSendInfoOutDTO);
		}

		this.year = XmlParse.getChildValue(this.body, "year");
	}

	public List<MoscBillReSendInfoOutDTO> getOutReSndList() {
		return outReSndList;
	}

	public void setOutReSndList(List<MoscBillReSendInfoOutDTO> outReSndList) {
		this.outReSndList = outReSndList;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getOrieMail() {
		return orieMail;
	}

	public void setOrieMail(String orieMail) {
		this.orieMail = orieMail;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}


	public static class MoscBillReSendInfoOutDTO {

		private String billAdInfo;
		private String creationDate;
		private String resultCode;
		private String thisMonth;
		public String getBillAdInfo() {
			return billAdInfo;
		}
		public void setBillAdInfo(String billAdInfo) {
			this.billAdInfo = billAdInfo;
		}
		public String getCreationDate() {
			return creationDate;
		}
		public void setCreationDate(String creationDate) {
			this.creationDate = creationDate;
		}
		public String getResultCode() {
			return resultCode;
		}
		public void setResultCode(String resultCode) {
			this.resultCode = resultCode;
		}
		public String getThisMonth() {
			return thisMonth;
		}
		public void setThisMonth(String thisMonth) {
			this.thisMonth = thisMonth;
		}
	}


}
