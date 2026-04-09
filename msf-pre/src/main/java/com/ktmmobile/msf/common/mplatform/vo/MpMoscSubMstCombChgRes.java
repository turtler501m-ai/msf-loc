package com.ktmmobile.msf.common.mplatform.vo;

import com.ktmmobile.msf.common.util.XmlParseUtil;

/**
 * Y44 서브회선 마스터 결합 신청 응답 VO.
 */
public class MpMoscSubMstCombChgRes extends CommonXmlVO {

    private String resultCd;
    private String resultMsg;

    @Override
    protected void parse() {
        if (body == null) return;
        this.resultCd = XmlParseUtil.getChildValue(body, "resultCd");
        this.resultMsg = XmlParseUtil.getChildValue(body, "resultMsg");
    }

    public String getResultCd() { return resultCd; }
    public String getResultMsg() { return resultMsg; }
}
