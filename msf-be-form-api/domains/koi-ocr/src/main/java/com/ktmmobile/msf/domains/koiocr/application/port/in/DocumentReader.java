package com.ktmmobile.msf.domains.koiocr.application.port.in;


import com.ktmmobile.msf.domains.koiocr.application.dto.document.IdDocumentRequest;
import com.ktmmobile.msf.domains.koiocr.application.dto.document.IdDocumentResponse;

public interface DocumentReader {

    IdDocumentResponse scanIdDocument(IdDocumentRequest request);
}
