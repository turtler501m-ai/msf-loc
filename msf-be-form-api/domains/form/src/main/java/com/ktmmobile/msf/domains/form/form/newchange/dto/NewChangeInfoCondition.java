package com.ktmmobile.msf.domains.form.form.newchange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NewChangeInfoCondition {

    //boolean isSaved; //고객스텝 저장 완료 여부
    //boolean isVerified; //인증 완료 여부
    private String formTypeCd; //신청서유형 : 1-신규/변경
    private Long requestKey;
}
