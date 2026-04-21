package com.ktmmobile.msf.commons.file.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import org.springframework.http.MediaType;

import com.ktmmobile.msf.commons.file.support.util.FileUtils;

@Builder
public record RawFile(
    @JsonIgnore String path,
    @JsonIgnore String name,
    String extension,
    long size,
    @JsonIgnore MediaType mediaType
) {

    public String getMimeType() {
        return mediaType.toString();
    }

    @JsonIgnore
    public String getPathFileName() {
        return FileUtils.concat(path, name);
    }
}
