package com.ktmmobile.msf.domains.koiocr.application.port.in;


import com.ktmmobile.msf.domains.koiocr.application.dto.document.CaptureDocumentRequest;
import com.ktmmobile.msf.domains.koiocr.application.dto.document.CaptureDocumentResponse;
import com.ktmmobile.msf.domains.koiocr.application.dto.document.IdDocumentRequest;
import com.ktmmobile.msf.domains.koiocr.application.dto.document.IdDocumentResponse;

public interface DocumentReader {

    IdDocumentResponse scanIdDocument(IdDocumentRequest request);

    CaptureDocumentResponse captureDocument(CaptureDocumentRequest request);
}
