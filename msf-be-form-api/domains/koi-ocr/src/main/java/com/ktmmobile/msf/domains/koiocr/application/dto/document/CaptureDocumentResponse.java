package com.ktmmobile.msf.domains.koiocr.application.dto.document;

import com.ktmmobile.msf.commons.file.application.dto.FileResponse;
import com.ktmmobile.msf.commons.file.domain.dto.CommonFile;

public record CaptureDocumentResponse(
    FileResponse file
) {

    public static CaptureDocumentResponse of(CommonFile file) {
        return new CaptureDocumentResponse(
            FileResponse.of(file)
        );
    }
}
