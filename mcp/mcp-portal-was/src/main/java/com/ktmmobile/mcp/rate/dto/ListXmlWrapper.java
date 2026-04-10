package com.ktmmobile.mcp.rate.dto;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
public class ListXmlWrapper {
	
	/** 요금제부가서비스안내상품관계 */
	private List<RateAdsvcGdncProdXML> item = new ArrayList<RateAdsvcGdncProdXML>();

	public List<RateAdsvcGdncProdXML> getItem() {
		return item;
	}

	public void setItem(List<RateAdsvcGdncProdXML> item) {
		this.item = item;
	}
}
