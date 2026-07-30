package com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;

@Getter
@RequiredArgsConstructor
public enum FaceAuthDecideCode implements CommonEnum {
    SUCCESS("SUCC", "안면인증 성공"),
    SKIP("SKIP", "안면인증 SKIP 성공"),
    WAIT("WAIT", "안면인증 진행중"),
    FAIL("FAIL", "안면인증 실패");

    private final String code;
    private final String title;
}
