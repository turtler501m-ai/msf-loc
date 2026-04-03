package com.ktmmobile.msf.system.common.dto;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="items")
public class XmlWrapper<T> {
	private List<T> items;
	 
    public XmlWrapper() {
        items = new ArrayList<T>();
    }
 
    public XmlWrapper(List<T> items) {
        this.items = items;
    }
 
    public List<T> getItems() {
        return items;
    }
 
    @XmlElement(name="item")
	public void setItems(List<T> items) {
		this.items = items;
	}
}
