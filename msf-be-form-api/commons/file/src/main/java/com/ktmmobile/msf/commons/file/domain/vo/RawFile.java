package com.ktmmobile.msf.commons.file.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import org.springframework.http.MediaType;

import com.ktmmobile.msf.commons.file.support.util.FileUtils;

@Builder
public record RawFile(
    @JsonIgnore String directoryPath,
    @JsonIgnore String fileName,
    String extension,
    long size,
    @JsonIgnore MediaType mediaType
) {

    public String getMimeType() {
        return mediaType.toString();
    }

    @JsonIgnore
    public String getFilePath() {
        return FileUtils.concat(directoryPath, fileName);
    }
}
