package com.ktmmobile.msf.common.mplatform.vo;

import com.ktmmobile.msf.common.util.XmlParseUtil;

/**
 * MC0 명의변경 사전체크 응답 VO.
 * ASIS SimpleOpenService MC0 응답: osstOrdNo, rsltCd, rsltMsg.
 */
public class MpNameChgPreChkVO extends CommonXmlVO {

    /** OSST 오더번호 */
    private String osstOrdNo;

    /** 결과코드 (0000=성공) */
    private String rsltCd;

    /** 결과메시지 */
    private String rsltMsg;

    @Override
    protected void parse() throws Exception {
        this.osstOrdNo = XmlParseUtil.getChildValue(this.body, "osstOrdNo");
        this.rsltCd    = XmlParseUtil.getChildValue(this.body, "rsltCd");
        this.rsltMsg   = XmlParseUtil.getChildValue(this.body, "rsltMsg");
    }

    public String getOsstOrdNo() { return osstOrdNo; }
    public String getRsltCd()    { return rsltCd; }
    public String getRsltMsg()   { return rsltMsg; }
}
