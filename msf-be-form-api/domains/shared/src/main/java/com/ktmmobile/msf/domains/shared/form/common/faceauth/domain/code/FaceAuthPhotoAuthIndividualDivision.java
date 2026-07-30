package com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;

@Getter
@RequiredArgsConstructor
public enum FaceAuthPhotoAuthIndividualDivision implements CommonEnum {
    PRINCIPAL("01", "본인(명의자)"),
    AGENT("02", "대리인"),
    LEGAL("03", "법정대리인");

    private final String code;
    private final String title;

    @JsonCreator
    public static FaceAuthPhotoAuthIndividualDivision from(String code) {
        for (FaceAuthPhotoAuthIndividualDivision type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }

    public static FaceAuthPhotoAuthIndividualDivision parse(
        FaceAuthCustomerType faceAuthCustomerType,
        FaceAuthVisitType faceAuthVisitType
    ) {
        if (faceAuthCustomerType == null) {
            return FaceAuthPhotoAuthIndividualDivision.PRINCIPAL;
        }
        if (
            faceAuthCustomerType.equals(FaceAuthCustomerType.CORPORATION) ||
                faceAuthCustomerType.equals(FaceAuthCustomerType.GOVERNMENT)
        ) {
            if (faceAuthVisitType == null) {
                return  FaceAuthPhotoAuthIndividualDivision.PRINCIPAL;
            }
            if (faceAuthVisitType != null && faceAuthVisitType.equals(FaceAuthVisitType.VDP)) {
                return FaceAuthPhotoAuthIndividualDivision.AGENT;
            }
        }
        if (faceAuthCustomerType.equals(FaceAuthCustomerType.MINOR) || faceAuthCustomerType.equals(FaceAuthCustomerType.FOREIGN_MINOR)) {
            return FaceAuthPhotoAuthIndividualDivision.LEGAL;
        }
        return FaceAuthPhotoAuthIndividualDivision.PRINCIPAL;
    }
}
