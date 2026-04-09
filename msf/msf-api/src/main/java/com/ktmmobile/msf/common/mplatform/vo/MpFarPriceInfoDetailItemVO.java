package com.ktmmobile.msf.common.mplatform.vo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Element;

import com.ktmmobile.msf.common.util.XmlParse;

public class MpFarPriceInfoDetailItemVO extends CommonXmlVO{
    private List<Map<String, String>> list;

    @Override
    public void parse() throws UnsupportedEncodingException {
        List<Element> itemList = XmlParse.getChildElementList(root, "outDto");
        list = new ArrayList<Map<String, String>>();

        for (Element item : itemList) {
            Map<String, String>map = new HashMap<String, String>();
            map.put("amt", XmlParse.getChildValue(item, "amt"));
            map.put("descr", XmlParse.getChildValue(item, "descr"));
            list.add(map);
        }

    }

    public List<Map<String, String>> getList() {
        return list;
    }

    public void setMap(List<Map<String, String>> list) {
        this.list = list;
    }

}
