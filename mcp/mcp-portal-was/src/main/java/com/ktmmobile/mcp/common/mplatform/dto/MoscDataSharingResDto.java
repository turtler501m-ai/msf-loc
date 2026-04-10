package com.ktmmobile.mcp.common.mplatform.dto;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import com.ktmmobile.mcp.common.util.StringMakerUtil;
import com.ktmmobile.mcp.common.util.XmlParse;

/**
 * 데이터 쉐어링 결합 중인 대상 조회
 * x71
 */


public class MoscDataSharingResDto extends com.ktmmobile.mcp.common.mplatform.vo.CommonXmlNoSelfServiceException{


	//데이터쉐어링 결합중인 list
	private List<OutDataSharingDto> sharingList;



	public List<OutDataSharingDto> getSharingList() {
		return sharingList;
	}


	public void setSharingList(List<OutDataSharingDto> sharingList) {
		this.sharingList = sharingList;
	}


	@Override
	public void parse() throws UnsupportedEncodingException, ParseException {
		List<Element> itemList = XmlParse.getChildElementList(this.body, "outDataSharingDto");
		sharingList = new ArrayList<OutDataSharingDto>();

	     for(Element item : itemList){
	    	 OutDataSharingDto outDataSharingDto = new OutDataSharingDto();
	    	 outDataSharingDto.setSvcNo(StringMakerUtil.getPhoneNum(XmlParse.getChildValue(item, "svcNo")));
	    	 outDataSharingDto.setEfctStDt(XmlParse.getChildValue(item, "efctStDt"));
	    	 outDataSharingDto.setRsltInd(XmlParse.getChildValue(item, "rsltInd"));
	    	 outDataSharingDto.setRsltMsg(XmlParse.getChildValue(item, "rsltMsg"));
	    	 sharingList.add(outDataSharingDto);
	     }

	}



}
