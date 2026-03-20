package com.ktmmobile.msf.common.mplatform.vo;

import com.ktmmobile.msf.common.util.XmlParseUtil;

/**
 * X19(요금상품 즉시변경), X84(요금상품 예약일 즉시변경), X88(예약변경), X90(예약취소) 응답 VO.
 * ASIS MplatFormVO 패턴: responseType=N 성공, 실패 시 responseCode/responseBasic 확인.
 */
public class MpFarPriceChgVO extends CommonXmlVO {

    /** 처리 결과 메시지 (응답 body rsltMsg) */
    private String rsltMsg;

    @Override
    protected void parse() {
        if (body == null) return;
        this.rsltMsg = XmlParseUtil.getChildValue(body, "rsltMsg");
    }

    public String getRsltMsg() { return rsltMsg; }
}
