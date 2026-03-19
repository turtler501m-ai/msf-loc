package com.ktmmobile.msf.common.mplatform.vo;

import com.ktmmobile.msf.common.util.XmlParseUtil;

/**
 * X85 USIM 유효성 체크 응답 VO. ASIS MplatFormService.moscIntmMgmtSO() 응답과 동일.
 */
public class MpUsimCheckVO extends CommonXmlVO {

    private String usimNo;       // USIM 번호
    private String usimSts;      // USIM 상태 코드
    private String usimStsCd;    // USIM 상태 설명
    private String usimType;     // USIM 유형

    public String getUsimNo()    { return usimNo; }
    public String getUsimSts()   { return usimSts; }
    public String getUsimStsCd() { return usimStsCd; }
    public String getUsimType()  { return usimType; }

    @Override
    protected void parse() throws Exception {
        if (body == null) return;
        usimNo    = XmlParseUtil.getChildValue(body, "usimNo");
        usimSts   = XmlParseUtil.getChildValue(body, "usimSts");
        usimStsCd = XmlParseUtil.getChildValue(body, "usimStsCd");
        usimType  = XmlParseUtil.getChildValue(body, "usimType");
    }
}
