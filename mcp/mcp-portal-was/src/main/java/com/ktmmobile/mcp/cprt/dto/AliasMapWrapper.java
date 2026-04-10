package com.ktmmobile.mcp.cprt.dto;

import java.util.HashMap;
import java.util.Map;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="items")
public class AliasMapWrapper {
	
	private Map<String, AliasListXmlWrapper> item;
	
	public AliasMapWrapper() {
		item = new HashMap<String, AliasListXmlWrapper>();
    }
 	
	public AliasMapWrapper(Map<String, AliasListXmlWrapper> item) {
		this.item = item;
    }
	
	public Map<String, AliasListXmlWrapper> getItem() {
		return item;
	}

	@XmlElement(name="item")
	public void setItem(Map<String, AliasListXmlWrapper> item) {
		this.item = item;
	}
}
