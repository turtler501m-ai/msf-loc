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

    private String formType; //신청서 유형
    private String cstmrNm; //고객명
    private String cstmrMobileNo; //고객연락처

}
