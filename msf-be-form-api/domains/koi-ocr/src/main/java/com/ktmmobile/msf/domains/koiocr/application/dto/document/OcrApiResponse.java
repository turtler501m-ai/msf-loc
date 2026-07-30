package com.ktmmobile.msf.domains.koiocr.application.dto.document;

public record OcrApiResponse(

    String resultCode,                     // 결과 코드
    String message,                        // 결과 메시지
    OcrDocumentResponse formResult,        // OCR 결과
    String maskImage,                      // 마스킹 이미지
    String cropImage,                      // 크롭 이미지
    String photoImage                      // 얼굴 이미지
) {
}
