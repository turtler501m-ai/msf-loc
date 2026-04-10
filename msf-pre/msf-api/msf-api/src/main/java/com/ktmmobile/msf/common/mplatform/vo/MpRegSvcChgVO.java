package com.ktmmobile.msf.common.mplatform.vo;

/**
 * X21 부가서비스 신청 응답 VO. ASIS MpRegSvcChgVO 와 동일. globalNo 는 CommonXmlVO 에서 파싱.
 */
public class MpRegSvcChgVO extends CommonXmlVO {

    @Override
    protected void parse() {
        // X21 응답 본문 없음. isSuccess, globalNo 만 사용.
    }
}
