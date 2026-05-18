package com.ktmmobile.msf.domains.koiocr.application.dto.document;

/**
 * OCR 신분증 응답 객체 정의
 */
public record OcrDocumentResponse(
    String type,                                            // OCR 문서 타입
    String copyDetectionResult,                             // 사본판별 결과
    Common common,                                          // 공통 신분증 정보
    DriverLicense driverLicense,                            // 운전면허증 고유 정보
    Passport passport,                                      // 여권 고유 정보
    ForeignerRegistrationCard foreignerRegistrationCard,    // 외국인등록증/영주증/국내거소신고증 고유 정보
    ForeignerBackSide foreignerBackSide,                    // 외국인등록증/영주증/국내거소신고증 후면 고유 정보
    VeteransCard veteransCard                               // 국가보훈증 고유 정보
) {

    /**
     * 공통 신분증 정보
     */
    public record Common(
        String residentRegistrationNumber,                  // 주민등록번호
        String name,                                        // 이름
        String issuedDate                                   // 발급일
    ) {
    }

    /**
     * 운전면허증 고유 정보
     */
    public record DriverLicense(
        String driverLicenseNumber,                         // 면허번호
        String serialNumber,                                // 시리얼번호
        String aptitudeTestPeriod                           // 적성검사 기간
    ) {
    }

    /**
     * 여권 고유 정보
     */
    public record Passport(
        String passportNumber,                              // 여권번호
        String birthDate,                                   // 생년월일
        String expirationDate,                              // 만료일
        String lastNameEn,                                  // 성(영문)
        String firstNameEn,                                 // 이름(영문)
        String gender,                                      // 성별
        String mrzLine1,                                    // MRZ 1열
        String mrzLine2                                     // MRZ 2열
    ) {
    }

    /**
     * 외국인등록증 / 영주증 / 국내거소신고증 고유 정보
     */
    public record ForeignerRegistrationCard(
        String foreignerRegistrationNumber,                 // 외국인 등록번호
        String nationalityOrRegion,                         // 국가 / 지역
        String visaStatus                                   // 체류자격
    ) {
    }

    /**
     * 외국인등록증/영주증/국내거소신고증 후면 고유 정보
     */
    public record ForeignerBackSide(
        String backSideSerialNumber                         // 일련번호
    ) {
    }

    /**
     * 국가보훈증 고유 정보
     */
    public record VeteransCard(
        String veteransNumber,                              // 보훈번호
        String veteransCardType                             // 국가보훈증 종류
    ) {
    }
}