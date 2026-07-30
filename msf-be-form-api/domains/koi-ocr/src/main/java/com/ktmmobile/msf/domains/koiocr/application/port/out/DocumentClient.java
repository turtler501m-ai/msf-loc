package com.ktmmobile.msf.domains.koiocr.application.port.out;

import com.ktmmobile.msf.domains.koiocr.application.dto.document.IdDocumentRequest;
import com.ktmmobile.msf.domains.koiocr.application.dto.document.OcrApiResponse;

public interface DocumentClient {

    OcrApiResponse scanIdDocument(IdDocumentRequest request);
}
