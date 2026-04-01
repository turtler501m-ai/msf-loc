package com.ktmmobile.msf.common.mplatform.vo;

import com.ktmmobile.msf.common.util.XmlParseUtil;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * X69/X71 데이터쉐어링 응답 VO.
 * ASIS MoscDataSharingResDto 와 동일 구조.
 * outDto > outDataSharingDto 목록 파싱.
 */
public class MpDataSharingResVO extends CommonXmlVO {

    private List<Item> items;

    @Override
    protected void parse() {
        items = new ArrayList<>();
        if (body == null) return;
        List<Element> dtos = XmlParseUtil.getChildElementList(body, "outDataSharingDto");
        for (Element el : dtos) {
            Item item = new Item();
            item.setRsltInd(XmlParseUtil.getChildValue(el, "rsltInd"));
            item.setSvcNo(XmlParseUtil.getChildValue(el, "svcNo"));
            item.setEfctStDt(XmlParseUtil.getChildValue(el, "efctStDt"));
            item.setRsltMsg(XmlParseUtil.getChildValue(el, "rsltMsg"));
            items.add(item);
        }
    }

    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }

    public static class Item {
        private String rsltInd;
        private String svcNo;
        private String efctStDt;
        private String rsltMsg;

        public String getRsltInd() { return rsltInd; }
        public void setRsltInd(String rsltInd) { this.rsltInd = rsltInd; }
        public String getSvcNo() { return svcNo; }
        public void setSvcNo(String svcNo) { this.svcNo = svcNo; }
        public String getEfctStDt() { return efctStDt; }
        public void setEfctStDt(String efctStDt) { this.efctStDt = efctStDt; }
        public String getRsltMsg() { return rsltMsg; }
        public void setRsltMsg(String rsltMsg) { this.rsltMsg = rsltMsg; }
    }
}
