package com.ktmmobile.msf.domains.koiocr.support.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import com.ktmmobile.msf.commons.file.domain.dto.CommonFile;
import com.ktmmobile.msf.domains.koiocr.application.dto.document.IdDocumentRequest;
import com.ktmmobile.msf.domains.koiocr.application.dto.document.IdDocumentResponse;
import com.ktmmobile.msf.domains.koiocr.application.dto.document.OcrApiResponse;
import com.ktmmobile.msf.domains.koiocr.application.dto.document.OcrDocumentResponse;
import com.ktmmobile.msf.domains.koiocr.domain.code.DocumentType;

@Slf4j
public final class DocumentUtils {

    private static final DateTimeFormatter OCR_DATE_FORMATTER =
        DateTimeFormatter.ofPattern("yyyyMMdd");

    private static final DateTimeFormatter PASSPORT_DATE_FORMATTER =
        new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("dd MMM yyyy")
            .toFormatter(Locale.ENGLISH);

    /**
     * 요청 문서 타입 ↔ OCR 문서 타입 매핑
     */
    private static final Map<DocumentType, String> OCR_TYPE_MAPPING = Map.of(
        DocumentType.RESIDENT_REGISTRATION_CARD, OcrDocumentResponse.Type.RESIDENT_REGISTRATION_CARD,
        DocumentType.DISABILITY_CARD, OcrDocumentResponse.Type.DISABILITY_CARD,
        DocumentType.DRIVER_LICENSE, OcrDocumentResponse.Type.DRIVER_LICENSE,
        DocumentType.NATIONAL_MERIT_CARD, OcrDocumentResponse.Type.VETERANS_CARD,
        DocumentType.PASSPORT_FOR_FOREIGNER, OcrDocumentResponse.Type.PASSPORT,
        DocumentType.FOREIGNER_REGISTRATION_CARD, OcrDocumentResponse.Type.FOREIGNER_REGISTRATION_CARD
    );

    public static IdDocumentResponse toIdDocumentResponse(
        IdDocumentRequest request,
        OcrApiResponse response,
        CommonFile file
    ) {

        OcrDocumentResponse ocrDocumentResponse = response.formResult();

        validateOcrType(request.ocrType(), ocrDocumentResponse);

        String maskImageFile = Boolean.TRUE.equals(request.returnFile()) ? response.maskImage() : null;

        return switch (request.ocrType()) {
            case RESIDENT_REGISTRATION_CARD, DISABILITY_CARD -> toResidentRegistrationCardResponse(ocrDocumentResponse,
                response,
                file,
                maskImageFile);
            case NATIONAL_MERIT_CARD -> toVeteransCardResponse(ocrDocumentResponse, response, file, maskImageFile);
            case DRIVER_LICENSE -> toDriverLicenseResponse(ocrDocumentResponse, response, file, maskImageFile);
            case PASSPORT_FOR_FOREIGNER -> toPassportResponse(ocrDocumentResponse, response, file, maskImageFile);
            case FOREIGNER_REGISTRATION_CARD -> toForeignerRegistrationCardResponse(ocrDocumentResponse, response, file, maskImageFile);
        };
    }

    private static IdDocumentResponse toResidentRegistrationCardResponse(
        OcrDocumentResponse document,
        OcrApiResponse response,
        CommonFile file,
        String maskImageFile
    ) {
        return baseResponse(response, file, maskImageFile)
            .identityIssuDate(parseDate(getFieldValue(document, OcrDocumentResponse.FieldId.ResidentRegistrationCard.ISSUED_DATE)))
            .cstmrNm(getFieldValue(document, OcrDocumentResponse.FieldId.ResidentRegistrationCard.NAME))
            .cstmrNativeRrn(parseResidentRegistrationNumber(getFieldValue(document,
                OcrDocumentResponse.FieldId.ResidentRegistrationCard.RESIDENT_REGISTRATION_NUMBER)))
            .address(getFieldValue(document, OcrDocumentResponse.FieldId.ResidentRegistrationCard.ADDRESS))
            .build();
    }

    private static IdDocumentResponse toVeteransCardResponse(
        OcrDocumentResponse document,
        OcrApiResponse response,
        CommonFile file,
        String maskImageFile
    ) {
        return baseResponse(response, file, maskImageFile)
            .identityIssuDate(parseDate(getFieldValue(document, OcrDocumentResponse.FieldId.VeteransCard.ISSUED_DATE)))
            .cstmrNm(getFieldValue(document, OcrDocumentResponse.FieldId.VeteransCard.NAME))
            .cstmrNativeRrn(parseResidentRegistrationNumber(getFieldValue(document,
                OcrDocumentResponse.FieldId.VeteransCard.RESIDENT_REGISTRATION_NUMBER)))
            .address(getFieldValue(document, OcrDocumentResponse.FieldId.VeteransCard.ADDRESS))
            .build();
    }

    private static IdDocumentResponse toDriverLicenseResponse(
        OcrDocumentResponse document,
        OcrApiResponse response,
        CommonFile file,
        String maskImageFile
    ) {
        String licenseNumber = getFieldValue(document, OcrDocumentResponse.FieldId.DriverLicense.DRIVER_LICENSE_NUMBER);

        return baseResponse(response, file, maskImageFile)
            .identityIssuDate(parseDate(getFieldValue(document, OcrDocumentResponse.FieldId.DriverLicense.ISSUED_DATE)))
            .identityIssuRegion(parseIssuingRegionCode(licenseNumber))
            .driveLicnsNo(removeHyphen(licenseNumber))
            .cstmrNm(getFieldValue(document, OcrDocumentResponse.FieldId.DriverLicense.NAME))
            .cstmrNativeRrn(parseResidentRegistrationNumber(getFieldValue(document,
                OcrDocumentResponse.FieldId.DriverLicense.RESIDENT_REGISTRATION_NUMBER)))
            .address(getFieldValue(document, OcrDocumentResponse.FieldId.DriverLicense.ADDRESS))
            .build();
    }

    private static IdDocumentResponse toPassportResponse(
        OcrDocumentResponse document,
        OcrApiResponse response,
        CommonFile file,
        String maskImageFile
    ) {
        return baseResponse(response, file, maskImageFile)
            .identityIssuDate(parsePassportDate(getFieldValue(document, OcrDocumentResponse.FieldId.Passport.ISSUED_DATE)))
            .cstmrNm(getFieldValue(document, OcrDocumentResponse.FieldId.Passport.NAME))
            .build();
    }

    private static IdDocumentResponse toForeignerRegistrationCardResponse(
        OcrDocumentResponse document,
        OcrApiResponse response,
        CommonFile file,
        String maskImageFile
    ) {
        return baseResponse(response, file, maskImageFile)
            .identityIssuDate(parseDate(getFieldValue(document, OcrDocumentResponse.FieldId.ForeignerRegistrationCard.ISSUED_DATE)))
            .cstmrNm(getFieldValue(document, OcrDocumentResponse.FieldId.ForeignerRegistrationCard.NAME))
            .cstmrForeignerRrn(parseResidentRegistrationNumber(getFieldValue(document,
                OcrDocumentResponse.FieldId.ForeignerRegistrationCard.FOREIGNER_REGISTRATION_NUMBER)))
            .build();
    }

    private static IdDocumentResponse.IdDocumentResponseBuilder baseResponse(
        OcrApiResponse response,
        CommonFile file,
        String maskImageFile
    ) {
        return IdDocumentResponse.builder()
            .ocrResultCode(response.resultCode())
            .ocrResultMessage(response.message())
            .file(file)
            .maskImageFile(maskImageFile);
    }

    /**
     * 요청 신분증 타입과 OCR 실제 인식 타입 검증
     */
    public static void validateOcrType(
        DocumentType requestType,
        OcrDocumentResponse response
    ) {

        if (response == null || response.type() == null) {
            throw new IllegalArgumentException("OCR 문서 타입이 존재하지 않습니다.");
        }

        String expectedOcrType = OCR_TYPE_MAPPING.get(requestType);

        if (expectedOcrType == null) {
            throw new IllegalArgumentException("지원하지 않는 신분증 타입입니다.");
        }

        if (!expectedOcrType.equals(response.type())) {
            throw new IllegalArgumentException("신분증 종류가 일치하지 않습니다.");
        }
    }

    public static String getFieldValue(
        OcrDocumentResponse response,
        String fieldId
    ) {

        if (response == null || response.fieldResults() == null) {
            return null;
        }

        List<OcrDocumentResponse.FieldResult> fieldResults =
            response.fieldResults();

        return fieldResults.stream()
            .filter(field -> fieldId.equals(field.fieldId()))
            .map(OcrDocumentResponse.FieldResult::value)
            .findFirst()
            .orElse(null);
    }

    public static LocalDate parseDate(String date) {

        String numericDate = digitsOnly(date);

        if (numericDate == null) {
            return null;
        }

        return LocalDate.parse(numericDate, OCR_DATE_FORMATTER);
    }

    public static String removeHyphen(String value) {
        return digitsOnly(value);
    }

    public static String parseResidentRegistrationNumber(String value) {
        return digitsOnly(value);
    }

    public static String parseIssuingRegionCode(String licenseNumber) {

        String numericValue = digitsOnly(licenseNumber);

        if (numericValue == null || numericValue.length() < 2) {
            return null;
        }

        return numericValue.substring(0, 2);
    }

    public static LocalDate parsePassportDate(String date) {

        if (date == null || date.isBlank()) {
            return null;
        }

        try {
            String normalizedDate = date
                .trim()
                .replaceAll("\\s+", " ");

            return LocalDate.parse(
                normalizedDate,
                PASSPORT_DATE_FORMATTER
            );

        } catch (Exception e) {
            log.warn("여권 날짜 파싱 실패. date={}", date, e);
            return null;
        }
    }

    private static String digitsOnly(String value) {

        if (value == null || value.isBlank()) {
            return null;
        }

        String numericValue = value.replaceAll("[^0-9]", "");

        return numericValue.isBlank() ? null : numericValue;
    }
}
