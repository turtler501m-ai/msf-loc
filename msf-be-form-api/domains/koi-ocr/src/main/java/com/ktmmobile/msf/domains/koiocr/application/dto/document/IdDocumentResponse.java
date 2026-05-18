package com.ktmmobile.msf.domains.koiocr.application.dto.document;


import java.time.LocalDate;

public record IdDocumentResponse(
    LocalDate identityIssuDate,         // 발급일자
    String identityIssuRegion,          // 발급지역
    Long driveLicnsNo,                  // 면허번호
    String cstmrNm,                     // 고객명
    String trnsNm,                      // 양도인명
    String cstmrNativeRrn,              // 고객정보내국인주민등록번호
    String cstmrForeignerRrn            // 고객정보외국인외국인등록번호
) {
}
