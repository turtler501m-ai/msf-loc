package com.ktmmobile.msf.domains.shared.form.common.ocr.domain.code;

public enum OcrDocumentType {
    RESIDENT_REGISTRATION_CARD("00100", "주민등록증"),
    DRIVER_LICENSE("00110", "운전면허증"),
    PASSPORT("00120", "여권"),
    FOREIGNER_ID("00140", "외국인등록증"),
    PERMANENT_RESIDENCE_CARD("00141", "영주증"),
    DOMESTIC_RESIDENT_REPORT("00142", "국내거소신고증"),
    DOMESTIC_RESIDENT_REPORT_BACK("00150", "외국인등록증/영주증/국내거소신고증 후면"),
    VETERANS_CARD("00160", "국가보훈증");

    private final String code;
    private final String value;

    OcrDocumentType(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}