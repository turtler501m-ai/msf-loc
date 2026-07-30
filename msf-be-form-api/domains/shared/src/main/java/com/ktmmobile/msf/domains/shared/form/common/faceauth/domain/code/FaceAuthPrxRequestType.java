package com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;

@Getter
@RequiredArgsConstructor
public enum FaceAuthPrxRequestType implements CommonEnum {
    FT0("FT0", ""),
    FT1("FT1", ""),
    FS8("FS8", ""),
    FS9("FS9", "");

    private final String code;
    private final String title;
}
