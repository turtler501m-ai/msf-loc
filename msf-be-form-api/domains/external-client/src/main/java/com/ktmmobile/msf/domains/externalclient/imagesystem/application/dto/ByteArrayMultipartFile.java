package com.ktmmobile.msf.domains.externalclient.imagesystem.application.dto;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import org.jspecify.annotations.Nullable;
import org.springframework.web.multipart.MultipartFile;

public record ByteArrayMultipartFile(
    String name,
    String originalFilename,
    String contentType,
    byte[] bytes
) implements MultipartFile {

    @Override
    public String getName() {
        return name;
    }

    @Override
    public @Nullable String getOriginalFilename() {
        return originalFilename;
    }

    @Override
    public @Nullable String getContentType() {
        return contentType;
    }

    @Override
    public boolean isEmpty() {
        return bytes == null || bytes.length == 0;
    }

    @Override
    public long getSize() {
        return bytes == null ? 0 : bytes.length;
    }

    @Override
    public byte[] getBytes() {
        return bytes;
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(bytes == null ? new byte[0] : bytes);
    }

    @Override
    public void transferTo(File dest) throws IOException {
        Files.write(dest.toPath(), bytes == null ? new byte[0] : bytes);
    }
}