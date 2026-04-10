package com.ktmmobile.msf.domains.form.form.appform_d.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NmcpCdCondition {

    private final String cdGroupId; //공통코드 그룹
    private final String useYn; //사용여부
}
