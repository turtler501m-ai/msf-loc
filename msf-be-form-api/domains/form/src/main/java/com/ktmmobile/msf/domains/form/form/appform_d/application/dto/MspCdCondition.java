package com.ktmmobile.msf.domains.form.form.appform_d.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MspCdCondition {

    private final String grpId; //공통코드 그룹
    private final String usgYn; //사용여부
}
