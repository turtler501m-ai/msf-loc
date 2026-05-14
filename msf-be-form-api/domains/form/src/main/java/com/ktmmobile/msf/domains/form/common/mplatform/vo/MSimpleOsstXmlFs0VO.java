package com.ktmmobile.msf.domains.form.common.mplatform.vo;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import com.ktmmobile.msf.domains.form.common.util.XmlParse;

public class MSimpleOsstXmlFs0VO extends CommonXmlVO {

    private List<KnoteScanInfoFs0Vo> list;

    @Override
    public void parse() throws UnsupportedEncodingException, ParseException {
        List<Element> itemList = XmlParse.getChildElementList(this.body, "knoteScanIdList");
        list = new ArrayList<KnoteScanInfoFs0Vo>();
        for (Element item: itemList) {
            KnoteScanInfoFs0Vo vo = new KnoteScanInfoFs0Vo();
            vo.setOnlineCustTrtSttusChgCd(XmlParse.getChildValue(item, "onlineCustTrtSttusChgCd"));
            vo.setCustIdntNo(XmlParse.getChildValue(item, "custIdntNo"));
            vo.setCustNm(XmlParse.getChildValue(item, "custNm"));
            vo.setWapplRegDate(XmlParse.getChildValue(item, "wapplRegDate"));
            vo.setFrmpapId(XmlParse.getChildValue(item, "frmpapId"));
            vo.setCustTypeNm(XmlParse.getChildValue(item, "custTypeNm"));
            vo.setCustIdntNoIndCd(XmlParse.getChildValue(item, "custIdntNoIndCd"));
            vo.setApyTypeCd(XmlParse.getChildValue(item, "apyTypeCd"));
            vo.setSlsCmpnCd(XmlParse.getChildValue(item, "slsCmpnCd"));
            vo.setSlsNm(XmlParse.getChildValue(item, "slsNm"));
            vo.setSvcApyTrtStatCd(XmlParse.getChildValue(item, "svcApyTrtStatCd"));
            vo.setFxdformIngrsPath1Cd(XmlParse.getChildValue(item, "fxdformIngrsPath1Cd"));
            vo.setCntpntCd(XmlParse.getChildValue(item, "cntpntCd"));
            vo.setMngmAgncId(XmlParse.getChildValue(item, "mngmAgncId"));
            vo.setCntpntNm(XmlParse.getChildValue(item, "cntpntNm"));

            list.add(vo);
        }
    }

    public List<KnoteScanInfoFs0Vo> getList() {
        return list;
    }

    public void setList(List<KnoteScanInfoFs0Vo> list) {
        this.list = list;
    }

}
