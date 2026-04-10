package com.ktmmobile.mcp.rate.guide.dto;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
public class ListXmlWrapper {

    private List<RateEventCodePrmtXML> item = new ArrayList<RateEventCodePrmtXML>();

    public List<RateEventCodePrmtXML> getItem() {
        return item;
    }

    public void setItem(List<RateEventCodePrmtXML> item) {
        this.item = item;
    }
}
