package com.ktmmobile.mcp.rate.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
public class EventCodeListXmlWrapper {

	private List<EventCodePrmtXML> item = new ArrayList<>();

	public List<EventCodePrmtXML> getItem() {
		return item;
	}

	public void setItem(List<EventCodePrmtXML> item) {
		this.item = item;
	}
}
