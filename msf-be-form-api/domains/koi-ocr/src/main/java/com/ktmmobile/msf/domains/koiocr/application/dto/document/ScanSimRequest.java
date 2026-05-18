package com.ktmmobile.msf.domains.koiocr.application.dto.document;

import org.springframework.web.multipart.MultipartFile;

public record ScanSimRequest(
    MultipartFile srcFile
) {
}
