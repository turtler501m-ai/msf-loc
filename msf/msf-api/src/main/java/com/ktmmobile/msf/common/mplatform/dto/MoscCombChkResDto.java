package com.ktmmobile.msf.common.mplatform.dto;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import com.ktmmobile.msf.common.util.XmlParse;

public class MoscCombChkResDto extends com.ktmmobile.msf.common.mplatform.vo.CommonXmlVO {

    private String sbscYn; //결합공통체크결과
    private String resltMsg; //결합공통체크메시지
    private String svcNo; //서비스번호

    private List<MoscCombPreChkListOutDTO> moscCombChkList;

    @Override
    public void parse() throws UnsupportedEncodingException, ParseException {
        this.sbscYn = XmlParse.getChildValue(this.body, "sbscYn");
        this.resltMsg = XmlParse.getChildValue(this.body, "resltMsg");

        List<Element> itemList = XmlParse.getChildElementList(this.body, "moscCombPreChkListOutDTO");
        moscCombChkList = new ArrayList<MoscCombPreChkListOutDTO>();

         for(Element item : itemList){
             MoscCombPreChkListOutDTO moscCombPreChkListOutDTO = new MoscCombPreChkListOutDTO();
             moscCombPreChkListOutDTO.setSvcNo(XmlParse.getChildValue(item, "svcNo"));
             moscCombPreChkListOutDTO.setSbscYn(XmlParse.getChildValue(item, "sbscYn"));
             moscCombPreChkListOutDTO.setResltMsg(XmlParse.getChildValue(item, "resltMsg"));
             moscCombChkList.add(moscCombPreChkListOutDTO);
         }
    }

    public class MoscCombPreChkListOutDTO {
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

    public List<MoscCombPreChkListOutDTO> getMoscCombChkList() {
        return moscCombChkList;
    }

    public void setMoscCombChkList(List<MoscCombPreChkListOutDTO> moscCombChkList) {
        this.moscCombChkList = moscCombChkList;
    }





}
