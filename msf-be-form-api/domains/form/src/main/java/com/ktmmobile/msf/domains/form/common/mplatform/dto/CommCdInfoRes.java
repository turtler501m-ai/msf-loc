package com.ktmmobile.msf.domains.form.common.mplatform.dto;

import com.ktmmobile.msf.domains.form.common.util.XmlParse;
import org.jdom.Element;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CommCdInfoRes extends com.ktmmobile.msf.domains.form.common.mplatform.vo.CommonXmlNoSelfServiceException{

    private List<CdInfoDto> cdList;


    @Override
    public void parse() throws UnsupportedEncodingException, ParseException {

        cdList = new ArrayList<>();

        //VO
        List<Element> itemList = XmlParse.getChildElementList(this.root, "cdList");
        cdList = new ArrayList<CdInfoDto>();

        for(Element item : itemList){
            CdInfoDto vo = new CdInfoDto();
            vo.setCd(XmlParse.getChildValue(item, "cd"));
            vo.setCdDscr(XmlParse.getChildValue(item, "cdDscr"));
            vo.setRfrnVal1(XmlParse.getChildValue(item, "rfrnVal1"));
            vo.setRfrnVal2(XmlParse.getChildValue(item, "rfrnVal2"));

            cdList.add(vo);
        }


    }

    public List<CdInfoDto> getCdList() {
        return cdList;
    }

    public void setCdList(List<CdInfoDto> cdList) {
        this.cdList = cdList;
    }
}