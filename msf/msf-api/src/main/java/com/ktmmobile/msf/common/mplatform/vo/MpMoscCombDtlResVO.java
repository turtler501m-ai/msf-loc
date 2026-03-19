package com.ktmmobile.msf.common.mplatform.vo;

import com.ktmmobile.msf.common.util.XmlParseUtil;

/**
 * X87 결합 서비스 계약 조회 응답 VO.
 */
public class MpMoscCombDtlResVO extends CommonXmlVO {

    private String combTypeNm;
    private String combProdNm;

    @Override
    protected void parse() {
        if (body == null) return;
        this.combTypeNm = XmlParseUtil.getChildValue(body, "combTypeNm");
        this.combProdNm = XmlParseUtil.getChildValue(body, "combProdNm");
    }

    public String getCombTypeNm() { return combTypeNm; }
    public String getCombProdNm() { return combProdNm; }
}
