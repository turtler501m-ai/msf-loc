package com.ktmmobile.msf.domains.shared.form.common.ocr.application.port.in;

import com.ktmmobile.msf.domains.shared.form.common.ocr.application.dto.document.IdDocumentResponse;

public interface DocumentReader {

    IdDocumentResponse scanIdDocument();
}
