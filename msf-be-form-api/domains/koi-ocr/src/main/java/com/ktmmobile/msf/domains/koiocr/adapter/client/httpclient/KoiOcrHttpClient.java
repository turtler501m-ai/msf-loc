package com.ktmmobile.msf.domains.koiocr.adapter.client.httpclient;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.service.annotation.PostExchange;

import com.ktmmobile.msf.domains.koiocr.application.dto.document.OcrApiResponse;

public interface KoiOcrHttpClient {

    @PostExchange(value = "/api/ocr/idcard", contentType = MediaType.MULTIPART_FORM_DATA_VALUE)
    OcrApiResponse scanIdDocument(@RequestPart("srcFile") MultipartFile file);
}
