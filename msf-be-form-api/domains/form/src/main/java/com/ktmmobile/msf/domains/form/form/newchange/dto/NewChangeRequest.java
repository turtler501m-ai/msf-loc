package com.ktmmobile.msf.domains.form.form.newchange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 신규/변경 신청서 select request parameter 정의
 * 2026.04.
 */

@Getter
@Setter
@NoArgsConstructor
public class NewChangeRequest {

    //boolean isSaved; //고객스텝 저장 완료 여부
    //boolean isVerified; //인증 완료 여부
    private String msfRequestKey;
    private Long requestKey;
}
