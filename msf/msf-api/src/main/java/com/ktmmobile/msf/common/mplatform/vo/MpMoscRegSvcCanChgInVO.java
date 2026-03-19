package com.ktmmobile.msf.common.mplatform.vo;

/**
 * X38 부가서비스 해지 응답 VO. ASIS MpMoscRegSvcCanChgInVO 와 동일.
 */
public class MpMoscRegSvcCanChgInVO extends CommonXmlVO {

    @Override
    protected void parse() {
        // X38 응답 본문 없음. isSuccess, globalNo 만 사용.
    }
}
