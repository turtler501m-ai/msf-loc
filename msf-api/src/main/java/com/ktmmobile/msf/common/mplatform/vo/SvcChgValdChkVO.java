package com.ktmmobile.msf.common.mplatform.vo;

/**
 * Y04 계약정보 유효성 체크 응답 VO. ASIS SvcChgValdChkVO 와 동일.
 * isSuccess() 만 사용. 별도 파싱 없음.
 */
public class SvcChgValdChkVO extends CommonXmlVO {

    @Override
    protected void parse() {
        // Y04는 isSuccess 만 확인
    }
}
