package com.ktmmobile.msf.domains.koiocr.application.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.msf.domains.koiocr.application.dto.document.IdDocumentRequest;
import com.ktmmobile.msf.domains.koiocr.application.dto.document.IdDocumentResponse;
import com.ktmmobile.msf.domains.koiocr.application.dto.document.OcrDocumentResponse;
import com.ktmmobile.msf.domains.koiocr.application.port.in.DocumentReader;
import com.ktmmobile.msf.domains.koiocr.application.port.out.DocumentClient;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class DocumentService implements DocumentReader {

    private static final DateTimeFormatter OCR_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    private final DocumentClient documentClient;

    @Override
    public IdDocumentResponse scanIdDocument(IdDocumentRequest request) {
        OcrDocumentResponse ocrDocumentResponse = documentClient.scanIdDocument(request);

        return toIdDocumentResponse(request, ocrDocumentResponse);
    }

    private IdDocumentResponse toIdDocumentResponse(
        IdDocumentRequest request,
        OcrDocumentResponse ocrDocumentResponse
    ) {
        OcrDocumentResponse.Common common = ocrDocumentResponse.common();

        return switch (request.ocrType()) {
            case RESIDENT_REGISTRATION_CARD, DISABILITY_CARD, NATIONAL_MERIT_CARD -> new IdDocumentResponse(
                parseDate(common.issuedDate()),                // 발급일자
                null,                                          // 발급지역
                null,                                          // 면허번호
                common.name(),                                 // 고객명
                null,                                          // 양도인명
                common.residentRegistrationNumber(),           // 고객정보내국인주민등록번호
                null                                           // 고객정보외국인외국인등록번호
            );

            case DRIVER_LICENSE -> new IdDocumentResponse(
                parseDate(common.issuedDate()),                // 발급일자
                null,                                          // 발급지역
                parseLong(ocrDocumentResponse.driverLicense().driverLicenseNumber()), // 면허번호
                common.name(),                                 // 고객명
                null,                                          // 양도인명
                common.residentRegistrationNumber(),           // 고객정보내국인주민등록번호
                null                                           // 고객정보외국인외국인등록번호
            );

            case PASSPORT_FOR_FOREIGNER -> new IdDocumentResponse(
                parseDate(common.issuedDate()),                // 발급일자
                null,                                          // 발급지역
                null,                                          // 면허번호
                common.name(),                                 // 고객명
                null,                                          // 양도인명
                null,                                          // 고객정보내국인주민등록번호
                null                                           // 고객정보외국인외국인등록번호
            );

            case FOREIGNER_REGISTRATION_CARD -> new IdDocumentResponse(
                parseDate(common.issuedDate()),                // 발급일자
                null,                                          // 발급지역
                null,                                          // 면허번호
                common.name(),                                 // 고객명
                null,                                          // 양도인명
                null,                                          // 고객정보내국인주민등록번호
                ocrDocumentResponse.foreignerRegistrationCard().foreignerRegistrationNumber() // 고객정보외국인외국인등록번호
            );
        };
    }

    private LocalDate parseDate(String date) {
        if (date == null || date.isBlank()) {
            return null;
        }

        String numericDate = date.replaceAll("[^0-9]", "");

        if (numericDate.isBlank()) {
            return null;
        }

        return LocalDate.parse(numericDate, OCR_DATE_FORMATTER);
    }

    private Long parseLong(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        String numericValue = value.replaceAll("[^0-9]", "");

        if (numericValue.isBlank()) {
            return null;
        }

        return Long.parseLong(numericValue);
    }
}