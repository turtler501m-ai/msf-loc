package com.ktmmobile.mcp.common.mplatform.vo;

import java.io.UnsupportedEncodingException;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ktmmobile.mcp.appform.controller.AppformController;
import com.ktmmobile.mcp.common.util.XmlParse;

public class MpMoscSdsInfoVo extends CommonXmlVO{

    private String engtAplyStDate ;   //      약정 시작일
    private String engtExpirPamDate;//        약정 종료일
    private String engtPerdMonsNum;//     약정개월
    private String chageDcAplyYn ;//       요금할인 적용여부
    private String dcSuprtAmt ;//      월 할인 금액
    private String ppPenlt ;//     요금할인 반환금

    public String getEngtAplyStDate() {
        return engtAplyStDate;
    }

    public void setEngtAplyStDate(String engtAplyStDate) {
        this.engtAplyStDate = engtAplyStDate;
    }

    public String getEngtExpirPamDate() {
        return engtExpirPamDate;
    }

    public void setEngtExpirPamDate(String engtExpirPamDate) {
        this.engtExpirPamDate = engtExpirPamDate;
    }

    public String getEngtPerdMonsNum() {
        return engtPerdMonsNum;
    }

    public void setEngtPerdMonsNum(String engtPerdMonsNum) {
        this.engtPerdMonsNum = engtPerdMonsNum;
    }

    public String getChageDcAplyYn() {
        return chageDcAplyYn;
    }

    public void setChageDcAplyYn(String chageDcAplyYn) {
        this.chageDcAplyYn = chageDcAplyYn;
    }

    public String getDcSuprtAmt() {
        return dcSuprtAmt;
    }

    public void setDcSuprtAmt(String dcSuprtAmt) {
        this.dcSuprtAmt = dcSuprtAmt;
    }

    public String getPpPenlt() {
        return ppPenlt;
    }

    public void setPpPenlt(String ppPenlt) {
        this.ppPenlt = ppPenlt;
    }

    @Override
    public void parse() throws UnsupportedEncodingException {
        this.engtAplyStDate = XmlParse.getChildValue(this.body, "engtAplyStDate");
        this.engtExpirPamDate = XmlParse.getChildValue(this.body, "engtExpirPamDate");
        this.engtPerdMonsNum = XmlParse.getChildValue(this.body, "engtPerdMonsNum");
        this.chageDcAplyYn = XmlParse.getChildValue(this.body, "chageDcAplyYn");
        this.dcSuprtAmt = XmlParse.getChildValue(this.body, "dcSuprtAmt");
        this.ppPenlt = XmlParse.getChildValue(this.body, "ppPenlt");
    }

}
