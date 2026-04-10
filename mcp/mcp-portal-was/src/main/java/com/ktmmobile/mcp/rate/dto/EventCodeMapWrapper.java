package com.ktmmobile.mcp.rate.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.HashMap;
import java.util.Map;

@XmlRootElement(name="items")
public class EventCodeMapWrapper {

	private Map<String, EventCodeListXmlWrapper> item;

	public EventCodeMapWrapper() {
		item = new HashMap<String, EventCodeListXmlWrapper>();
	}

	public EventCodeMapWrapper(Map<String, EventCodeListXmlWrapper> item) {
		this.item = item;
	}

	public Map<String, EventCodeListXmlWrapper> getItem() {
		return item;
	}

	@XmlElement(name="item")
	public void setItem(Map<String, EventCodeListXmlWrapper> item) {
		this.item = item;
	}

}
