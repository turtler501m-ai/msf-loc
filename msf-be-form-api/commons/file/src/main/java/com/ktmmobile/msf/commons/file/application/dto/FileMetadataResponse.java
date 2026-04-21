package com.ktmmobile.msf.commons.file.application.dto;

public record FileMetadataResponse(
    boolean exists,
    FileResponse meta
) {

    public static FileMetadataResponse of(FileResponse meta) {
        return new FileMetadataResponse(meta != null, meta);
    }
}
