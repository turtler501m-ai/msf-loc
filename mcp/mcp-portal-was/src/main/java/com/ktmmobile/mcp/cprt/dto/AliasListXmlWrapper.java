package com.ktmmobile.mcp.cprt.dto;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
public class AliasListXmlWrapper {

	private List<AlliCardCtgaContXML> item = new ArrayList<AlliCardCtgaContXML>();

	public List<AlliCardCtgaContXML> getItem() {
		return item;
	}

	public void setItem(List<AlliCardCtgaContXML> item) {
		this.item = item;
	}
}
