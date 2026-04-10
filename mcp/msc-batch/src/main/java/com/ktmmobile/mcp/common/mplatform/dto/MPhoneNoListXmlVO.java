package com.ktmmobile.mcp.common.mplatform.dto;

import com.ktmmobile.mcp.common.util.XmlParse;
import org.jdom.Element;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MPhoneNoListXmlVO extends CommonXmlVO{

    private List<MPhoneNoVo> list;

    @Override
    public void parse() throws UnsupportedEncodingException, ParseException {

        List<Element> itemList = XmlParse.getChildElementList(this.body, "svcNoList");
        list = new ArrayList<MPhoneNoVo>();
        for(Element item : itemList){
            MPhoneNoVo vo = new MPhoneNoVo();

            vo.setTlphNoStatCd(XmlParse.getChildValue(item, "tlphNoStatCd"));
            vo.setAsgnAgncId(XmlParse.getChildValue(item, "asgnAgncId"));
            vo.setTlphNoOwnCmncCmpnCd(XmlParse.getChildValue(item, "tlphNoOwnCmncCmpnCd"));
            vo.setOpenSvcIndCd(XmlParse.getChildValue(item, "openSvcIndCd"));
            vo.setEncdTlphNo(XmlParse.getChildValue(item, "encdTlphNo"));
            vo.setTlphNo(XmlParse.getChildValue(item, "tlphNo"));
            vo.setFvrtnoAqcsPsblYn(XmlParse.getChildValue(item, "fvrtnoAqcsPsblYn"));
            vo.setRsrvCustNo(XmlParse.getChildValue(item, "rsrvCustNo"));
            vo.setStatMntnEndPrrnDate(XmlParse.getChildValue(item, "statMntnEndPrrnDate"));
            vo.setTlphNoChrcCd(XmlParse.getChildValue(item, "tlphNoChrcCd"));
            vo.setTlphNoStatChngDt(XmlParse.getChildValue(item, "tlphNoStatChngDt"));
            vo.setTlphNoUseCd(XmlParse.getChildValue(item, "tlphNoUseCd"));
            vo.setTlphNoUseMntCd(XmlParse.getChildValue(item, "tlphNoUseMntCd"));
            list.add(vo);
        }
    }

    public List<MPhoneNoVo> getList() {
        return list;
    }

    public void setList(List<MPhoneNoVo> list) {
        this.list = list;
    }

}
