package com.ktmmobile.msf.common.mplatform.vo;

import com.ktmmobile.msf.common.util.XmlParseUtil;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * NU1 희망번호 조회 응답 VO (OSST 데이터쉐어링 신규개통용).
 * ASIS MPhoneNoListXmlVO 와 동일 구조.
 * Item 필드: tlphNo, tlphNoStatCd, encdTlphNo, tlphNoOwnCmpnCd
 */
public class MpOsstPhoneNoVO extends CommonXmlVO {

    public static class PhoneNoItem {
        private String tlphNo;
        private String tlphNoStatCd;
        private String encdTlphNo;
        private String tlphNoOwnCmpnCd;

        public String getTlphNo()          { return tlphNo; }
        public String getTlphNoStatCd()    { return tlphNoStatCd; }
        public String getEncdTlphNo()      { return encdTlphNo; }
        public String getTlphNoOwnCmpnCd() { return tlphNoOwnCmpnCd; }
    }

    private List<PhoneNoItem> items = new ArrayList<>();

    public List<PhoneNoItem> getItems() { return items; }

    @Override
    protected void parse() throws Exception {
        items = new ArrayList<>();
        if (body == null) return;
        NodeList nodes = body.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            if (!(nodes.item(i) instanceof Element)) continue;
            Element el = (Element) nodes.item(i);
            PhoneNoItem item = new PhoneNoItem();
            item.tlphNo          = XmlParseUtil.getChildValue(el, "tlphNo");
            item.tlphNoStatCd    = XmlParseUtil.getChildValue(el, "tlphNoStatCd");
            item.encdTlphNo      = XmlParseUtil.getChildValue(el, "encdTlphNo");
            item.tlphNoOwnCmpnCd = XmlParseUtil.getChildValue(el, "tlphNoOwnCmncCmpnCd");
            if (item.tlphNo != null && !item.tlphNo.trim().isEmpty()) {
                items.add(item);
            }
        }
    }
}
