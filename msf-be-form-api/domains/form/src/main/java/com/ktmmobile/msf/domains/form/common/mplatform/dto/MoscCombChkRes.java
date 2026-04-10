package com.ktmmobile.msf.domains.form.common.mplatform.dto;

import com.ktmmobile.msf.domains.form.common.util.XmlParse;
import org.jdom.Element;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MoscCombChkRes extends com.ktmmobile.msf.domains.form.common.mplatform.vo.CommonXmlNoSelfServiceException {

    private String sbscYn; //결합공통체크결과111
    private String resltMsg; //결합공통체크메시지
    private String svcNo; //서비스번호

    private List<MoscCombPreChkListOut> moscCombChkList;

    @Override
    public void parse() throws UnsupportedEncodingException, ParseException {
        this.sbscYn = XmlParse.getChildValue(this.body, "sbscYn");
        this.resltMsg = XmlParse.getChildValue(this.body, "resltMsg");

        List<Element> itemList = XmlParse.getChildElementList(this.body, "moscCombPreChkListOutDTO");
        moscCombChkList = new ArrayList<MoscCombPreChkListOut>();

        for(Element item : itemList){
            MoscCombPreChkListOut moscCombPreChkListOut = new MoscCombPreChkListOut();
            moscCombPreChkListOut.setSvcNo(XmlParse.getChildValue(item, "svcNo"));
            moscCombPreChkListOut.setSbscYn(XmlParse.getChildValue(item, "sbscYn"));
            moscCombPreChkListOut.setResltMsg(XmlParse.getChildValue(item, "resltMsg"));
            moscCombChkList.add(moscCombPreChkListOut);
        }
    }

    public class MoscCombPreChkListOut {
        private String sbscYn; //결합공통체크결과
        private String resltMsg; //결합공통체크메시지
        private String svcNo; //서비스번호
        public String getSbscYn() {
            return sbscYn;
        }
        public void setSbscYn(String sbscYn) {
            this.sbscYn = sbscYn;
        }
        public String getResltMsg() {
            return resltMsg;
        }
        public void setResltMsg(String resltMsg) {
            this.resltMsg = resltMsg;
        }
        public String getSvcNo() {
            return svcNo;
        }
        public void setSvcNo(String svcNo) {
            this.svcNo = svcNo;
        }
    }

    public String getSbscYn() {
        return sbscYn;
    }

    public void setSbscYn(String sbscYn) {
        this.sbscYn = sbscYn;
    }

    public String getResltMsg() {
        return resltMsg;
    }

    public void setResltMsg(String resltMsg) {
        this.resltMsg = resltMsg;
    }

    public String getSvcNo() {
        return svcNo;
    }

    public void setSvcNo(String svcNo) {
        this.svcNo = svcNo;
    }

    public List<MoscCombPreChkListOut> getMoscCombChkList() {
        return moscCombChkList;
    }

    public void setMoscCombChkList(List<MoscCombPreChkListOut> moscCombChkList) {
        this.moscCombChkList = moscCombChkList;
    }





}
