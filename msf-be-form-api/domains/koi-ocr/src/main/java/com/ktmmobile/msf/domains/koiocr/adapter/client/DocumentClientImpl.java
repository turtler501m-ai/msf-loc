package com.ktmmobile.msf.domains.koiocr.adapter.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.domains.koiocr.adapter.client.httpclient.KoiOcrHttpClient;
import com.ktmmobile.msf.domains.koiocr.application.dto.document.IdDocumentRequest;
import com.ktmmobile.msf.domains.koiocr.application.dto.document.OcrDocumentResponse;
import com.ktmmobile.msf.domains.koiocr.application.port.out.DocumentClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class DocumentClientImpl implements DocumentClient {

    private final KoiOcrHttpClient koiOcrHttpClient;

    @Override
    public OcrDocumentResponse scanIdDocument(IdDocumentRequest request) {

        return koiOcrHttpClient.scanIdDocument(request.srcFile());
    }

}
