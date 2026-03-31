package com.ktmmobile.msf.common.mplatform.vo;

import com.ktmmobile.msf.common.util.XmlParseUtil;

/**
 * OSST 단순 응답 VO. ASIS MSimpleOsstXmlVO / MSimpleOsstXmlSt1VO 와 동일.
 * PC0 사전체크, ST1 상태조회, NU2 번호예약, OP0 개통 응답에 사용.
 * - prgrStatCd: 진행상태코드 (PC0, PC2, NU2, OP0 ...)
 * - rsltCd    : 처리결과코드 (성공: "0000")
 * - rsltMsg   : 처리결과메세지
 * - osstOrdNo : OSST 오더번호
 */
public class MpOsstSimpleVO extends CommonXmlVO {

    private String prgrStatCd;
    private String rsltCd;
    private String rsltMsg;
    private String osstOrdNo;

    public String getPrgrStatCd() { return prgrStatCd; }
    public String getRsltCd()     { return rsltCd; }
    public String getRsltMsg()    { return rsltMsg; }
    public String getOsstOrdNo()  { return osstOrdNo; }

    @Override
    protected void parse() throws Exception {
        if (body == null) return;
        prgrStatCd = XmlParseUtil.getChildValue(body, "prgrStatCd");
        rsltCd     = XmlParseUtil.getChildValue(body, "rsltCd");
        rsltMsg    = XmlParseUtil.getChildValue(body, "rsltMsg");
        osstOrdNo  = XmlParseUtil.getChildValue(body, "osstOrdNo");
    }
}
