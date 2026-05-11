package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="items")
@Getter
@Setter
@NoArgsConstructor
public class MapWrapper {

	private Map<String, ListXmlWrapper> item = new HashMap<String, ListXmlWrapper>();
 	
	public MapWrapper(Map<String, ListXmlWrapper> item) {
		this.item = item;
    }
	
	@XmlElement(name="item")
	public void setItem(Map<String, ListXmlWrapper> item) {
		this.item = item;
	}
}
