package com.ktmmobile.msf.domains.form.common.mplatform.vo;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import com.ktmmobile.msf.domains.form.common.util.XmlParse;

/**
 * EP0 — MVNO-OSST 해지처리 응답 VO
 * OsstCanSvcPrcService.osstCanPrc
 */
public class MpOsstCanPrcVO extends CommonXmlVO {

    private String osstOrdNo;   // OSST 오더번호 (YYYYMMDD+SEQ6자리)
    private String rslt;        // 접수결과 (S=접수정상, F=접수에러)
    private String rsltMsg;     // 처리 결과 메시지

    @Override
    public void parse() throws UnsupportedEncodingException, ParseException {
        this.osstOrdNo = XmlParse.getChildValue(body, "osstOrdNo");
        this.rslt      = XmlParse.getChildValue(body, "rslt");
        this.rsltMsg   = XmlParse.getChildValue(body, "rsltMsg");
    }

    public String getOsstOrdNo() { return osstOrdNo; }
    public void setOsstOrdNo(String osstOrdNo) { this.osstOrdNo = osstOrdNo; }

    public String getRslt() { return rslt; }
    public void setRslt(String rslt) { this.rslt = rslt; }

    public String getRsltMsg() { return rsltMsg; }
    public void setRsltMsg(String rsltMsg) { this.rsltMsg = rsltMsg; }
}
