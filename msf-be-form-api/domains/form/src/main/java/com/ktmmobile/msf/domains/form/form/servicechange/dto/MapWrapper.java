package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import java.util.HashMap;
import java.util.Map;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="items")
public class MapWrapper {

	private Map<String, ListXmlWrapper> item;
	
	public MapWrapper() {
		item = new HashMap<String, ListXmlWrapper>();
    }
 	
	public MapWrapper(Map<String, ListXmlWrapper> item) {
		this.item = item;
    }
	
	public Map<String, ListXmlWrapper> getItem() {
		return item;
	}

	@XmlElement(name="item")
	public void setItem(Map<String, ListXmlWrapper> item) {
		this.item = item;
	}
}
