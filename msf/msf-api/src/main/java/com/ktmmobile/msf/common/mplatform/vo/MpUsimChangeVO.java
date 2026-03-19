package com.ktmmobile.msf.common.mplatform.vo;

/**
 * UC0 USIM 변경 응답 VO. 성공 여부(isSuccess) + globalNo 만 사용.
 */
public class MpUsimChangeVO extends CommonXmlVO {

    @Override
    protected void parse() throws Exception {
        // UC0 응답 본문 없음. isSuccess, globalNo 만 사용.
    }
}
