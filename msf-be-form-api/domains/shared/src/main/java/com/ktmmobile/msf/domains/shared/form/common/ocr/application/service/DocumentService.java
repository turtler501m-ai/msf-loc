package com.ktmmobile.msf.domains.shared.form.common.ocr.application.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.msf.domains.shared.form.common.ocr.application.dto.document.IdDocumentResponse;
import com.ktmmobile.msf.domains.shared.form.common.ocr.application.port.in.DocumentReader;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class DocumentService implements DocumentReader {

    private static final LocalDate START_DATE = LocalDate.of(2016, 1, 1);
    private static final LocalDate END_DATE = LocalDate.now();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    @Override
    public IdDocumentResponse scanIdDocument() {
        long randomEpochDay = ThreadLocalRandom.current().nextLong(
            START_DATE.toEpochDay(),
            END_DATE.plusDays(1).toEpochDay()
        );

        String result = LocalDate.ofEpochDay(randomEpochDay).format(DATE_FORMATTER);
        return new IdDocumentResponse(result);
    }
}
