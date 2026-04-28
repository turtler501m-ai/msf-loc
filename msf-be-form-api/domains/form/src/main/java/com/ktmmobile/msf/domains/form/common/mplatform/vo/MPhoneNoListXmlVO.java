package com.ktmmobile.msf.domains.form.common.mplatform.vo;

import com.ktmmobile.msf.domains.form.common.util.XmlParse;
import org.jdom.Element;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * 희망번호 조회 요청에 의한 응답 시 사용하는 VO 입니다.
 **/
public class MPhoneNoListXmlVO extends CommonXmlVO {

    private List<MPhoneNoVo> list;

    @Override
    public void parse() throws UnsupportedEncodingException, ParseException {
        List<Element> itemList = XmlParse.getChildElementList(this.body, "svcNoList");
        list = new ArrayList<MPhoneNoVo>();
        for (Element item : itemList) {
            MPhoneNoVo vo = new MPhoneNoVo();
            vo.setAsgnAgncId(XmlParse.getChildValue(item, "asgnAgncId"));
            vo.setEncdTlphNo(XmlParse.getChildValue(item, "encdTlphNo"));
            vo.setFvrtnoAqcsPsblYn(XmlParse.getChildValue(item, "fvrtnoAqcsPsblYn"));
            vo.setOpenSvcIndCd(XmlParse.getChildValue(item, "openSvcIndCd"));
            vo.setRsrvCustNo(XmlParse.getChildValue(item, "rsrvCustNo"));
            vo.setStatMntnEndPrrnDate(XmlParse.getChildValue(item, "statMntnEndPrrnDate"));
            vo.setTlphNo(XmlParse.getChildValue(item, "tlphNo"));
            vo.setTlphNoChrcCd(XmlParse.getChildValue(item, "tlphNoChrcCd"));
            vo.setTlphNoOwnCmncCmpnCd(XmlParse.getChildValue(item, "tlphNoOwnCmncCmpnCd"));
            vo.setTlphNoStatCd(XmlParse.getChildValue(item, "tlphNoStatCd"));
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
