package com.ktmmobile.msf.commons.file.application.dto;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import com.ktmmobile.msf.commons.file.domain.dto.FileVariantResult;
import com.ktmmobile.msf.commons.file.domain.vo.RawFile;

public record FileVariantResponse(
    String filePath,
    String directoryPath,
    String fileName,
    RawFile rawFile,
    String downloadSignedUrl,
    Map<String, FileResponse> variants
) {

    public static FileVariantResponse of(FileVariantResult result) {
        FileResponse file = FileResponse.of(result.file());
        Map<String, FileResponse> variants = new LinkedHashMap<>();
        result.variants().forEach((name, variant) -> variants.put(name, FileResponse.of(variant)));
        return new FileVariantResponse(
            file.filePath(),
            file.directoryPath(),
            file.fileName(),
            file.rawFile(),
            file.downloadSignedUrl(),
            Collections.unmodifiableMap(variants)
        );
    }
}
