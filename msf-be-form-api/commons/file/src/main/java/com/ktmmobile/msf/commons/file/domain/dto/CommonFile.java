package com.ktmmobile.msf.commons.file.domain.dto;

import lombok.Builder;
import lombok.With;

import com.ktmmobile.msf.commons.file.domain.vo.RawFile;

@Builder
public record CommonFile(
    String pathFileName,
    RawFile rawFile,
    @With String signedUrl
) {
}
