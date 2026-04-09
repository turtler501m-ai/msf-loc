package com.ktmmobile.msf.common.mplatform.vo;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ktmmobile.msf.common.util.XmlParse;

public class MoscBillSendInfoOutVO extends com.ktmmobile.msf.common.mplatform.vo.CommonXmlVO {
	@Deprecated
	private static Logger logger = LoggerFactory.getLogger(MoscBillSendInfoOutVO.class);

	private List<MoscBillSendInfoOutDTO> outSendInfoListlDto;

	@Override
	public void parse() throws UnsupportedEncodingException, ParseException {

		// 공통
		List<Element> outSendInfoListlDtoList = XmlParse.getChildElementList(this.body, "outSendInfoListlDto");
		outSendInfoListlDto = new ArrayList<MoscBillSendInfoOutVO.MoscBillSendInfoOutDTO>();
		for(Element item : outSendInfoListlDtoList){
			MoscBillSendInfoOutDTO  moscBillSendInfoOutDTO = new MoscBillSendInfoOutDTO();
			String email = XmlParse.getChildValue(item,"email");
			String sendDay = XmlParse.getChildValue(item,"sendDay");
			String state = XmlParse.getChildValue(item,"state");
			String thisMonth = XmlParse.getChildValue(item,"thisMonth");

			moscBillSendInfoOutDTO.setEmail(email);
			moscBillSendInfoOutDTO.setSendDay(sendDay);
			moscBillSendInfoOutDTO.setState(state);
			moscBillSendInfoOutDTO.setThisMonth(thisMonth);
			outSendInfoListlDto.add(moscBillSendInfoOutDTO);
		}
	}

	public List<MoscBillSendInfoOutDTO> getMoscBillSendInfoOutDTO() {
		return outSendInfoListlDto;
	}

	public void setMoscBillSendInfoOutDTO(List<MoscBillSendInfoOutDTO> outSendInfoListlDto) {
		this.outSendInfoListlDto = outSendInfoListlDto;
	}

	public static class MoscBillSendInfoOutDTO {

		private String email;
		private String sendDay;
		private String state;
		private String thisMonth;
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getSendDay() {
			return sendDay;
		}
		public void setSendDay(String sendDay) {
			this.sendDay = sendDay;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getThisMonth() {
			return thisMonth;
		}
		public void setThisMonth(String thisMonth) {
			this.thisMonth = thisMonth;
		}
	}


}
