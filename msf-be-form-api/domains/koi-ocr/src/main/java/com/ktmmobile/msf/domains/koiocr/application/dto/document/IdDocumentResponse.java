package com.ktmmobile.msf.domains.koiocr.application.dto.document;


import java.time.LocalDate;

import lombok.Builder;

import com.ktmmobile.msf.commons.file.domain.dto.CommonFile;

@Builder(toBuilder = true)
public record IdDocumentResponse(
    LocalDate identityIssuDate,         // 발급일자
    String identityIssuRegion,          // 발급지역
    String driveLicnsNo,                // 면허번호
    String cstmrNm,                     // 고객명
    String trnsNm,                      // 양도인명
    String cstmrNativeRrn,              // 고객정보내국인주민등록번호
    String cstmrForeignerRrn,           // 고객정보외국인외국인등록번호
    String zipNo,                       // 우편번호
    String address,                     // 신분증 주소
    String ocrResultCode,               // OCR 결과 코드
    String ocrResultMessage,            // OCR 결과 메시지
    CommonFile file,                    // OCR 결과 이미지
    String maskImageFile                // 응답 Base64 이미지
) {
}
