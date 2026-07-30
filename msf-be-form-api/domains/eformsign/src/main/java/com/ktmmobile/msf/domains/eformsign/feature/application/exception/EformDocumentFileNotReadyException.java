package com.ktmmobile.msf.domains.eformsign.feature.application.exception;

public class EformDocumentFileNotReadyException extends RuntimeException {

    public static final String CODE = "2020001";

    public EformDocumentFileNotReadyException() {
        super("전자서식 문서 파일 생성 중");
    }
}
