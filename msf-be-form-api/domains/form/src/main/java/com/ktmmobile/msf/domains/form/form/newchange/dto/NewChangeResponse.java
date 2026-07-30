package com.ktmmobile.msf.domains.form.form.newchange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 신규/변경 작성완료 신청서 Response? - 필요없나??
 */
@Getter
@Setter
@NoArgsConstructor
public class NewChangeResponse {

    private Long requestKey;
    private String resNo;

    private String formType; //신청서 유형
    private String cstmrNm; //고객명
    private String cstmrMobileNo; //고객연락처

    private String rsltCd;
    private String rsltMsg;

    private String preCheckCd; //사전체크 성공여부 : 0000 또는 9999
    private String preCheckMsg; //사전체크 항상 메세지 보낼 것

    private String preCheckResultCd;
    private String preCheckResultMsg;

    //private String retryPossibleYn; //재시도 가능여부???

}
