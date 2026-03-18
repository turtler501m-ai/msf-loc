package com.ktmmobile.msf.common.mplatform.vo;

import com.ktmmobile.msf.common.util.XmlParseUtil;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * X69/X71 데이터쉐어링 응답 VO.
 */
public class MpDataSharingResVO extends CommonXmlVO {

    private List<DataSharingItem> items;

    @Override
    protected void parse() {
        items = new ArrayList<>();
        if (body == null) return;
        List<Element> list = XmlParseUtil.getChildElementList(body, "outDataSharingDto");
        for (Element item : list) {
            DataSharingItem dto = new DataSharingItem();
            dto.setRsltInd(XmlParseUtil.getChildValue(item, "rsltInd"));
            dto.setSvcNo(XmlParseUtil.getChildValue(item, "svcNo"));
            dto.setEfctStDt(XmlParseUtil.getChildValue(item, "efctStDt"));
            dto.setRsltMsg(XmlParseUtil.getChildValue(item, "rsltMsg"));
            items.add(dto);
        }
    }

    public List<DataSharingItem> getItems() { return items; }

    public static class DataSharingItem {
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
