package com.ktmmobile.msf.common.mplatform.vo;

import com.ktmmobile.msf.common.util.XmlParseUtil;

/**
 * X32 번호변경 응답 VO. ASIS MplatFormService.numChgeChg() 응답.
 * 성공 여부 + 변경된 번호 반환.
 */
public class MpNumberChangeVO extends CommonXmlVO {

    private String newTlphNo;  // 변경 후 전화번호

    public String getNewTlphNo() { return newTlphNo; }

    @Override
    protected void parse() throws Exception {
        if (body == null) return;
        newTlphNo = XmlParseUtil.getChildValue(body, "newTlphNo");
    }
}
