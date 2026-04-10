package com.ktmmobile.mcp.common.mplatform.vo;

import java.io.UnsupportedEncodingException;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ktmmobile.mcp.appform.controller.AppformController;
import com.ktmmobile.mcp.common.util.XmlParse;

public class MpMoscSdsSvcPreChkVo extends CommonXmlVO{

    /**
     * Y(신청가능) -> 신청만 가능함.
     *  E(중도해지) -> 해지만 가능함.
     *   N(신청불가) -> 신청, 해지 불가
     */
    private String sbscYn;//사전체크 결과코드
    private String resltMsg;//결과 메시지

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


    @Override
    public void parse() throws UnsupportedEncodingException {
        this.sbscYn = XmlParse.getChildValue(this.body, "sbscYn");
        this.resltMsg = XmlParse.getChildValue(this.body, "resltMsg");
    }

}
