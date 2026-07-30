package com.ktmmobile.msf.commons.file.application.dto;

import java.util.Base64;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;
import com.ktmmobile.msf.commons.file.support.util.InMemoryMultipartFile;

public record FileBase64UploadRequest(
    String base64Data,
    String fileName,
    String fileCategory,
    FileVariantOptions variants
) {

    public FileRequest toFileRequest() {
        DecodedBase64File decodedFile = decodeBase64File();
        MultipartFile multipartFile = new InMemoryMultipartFile(
            "file",
            fileName,
            decodedFile.contentType(),
            decodedFile.bytes()
        );
        return FileRequest.of(multipartFile, fileCategory);
    }

    public FileVariantOptions toVariantOptions() {
        return variants == null ? FileVariantOptions.empty() : variants;
    }

    private DecodedBase64File decodeBase64File() {
        if (!StringUtils.hasText(fileName)) {
            throw new SimpleDomainException("파일명은 필수입니다.");
        }
        if (!StringUtils.hasText(base64Data)) {
            throw new SimpleDomainException("업로드할 Base64 데이터는 필수입니다.");
        }

        String trimmedData = base64Data.trim();
        String resolvedContentType = null;
        String payload = trimmedData;

        if (trimmedData.startsWith("data:")) {
            int separatorIndex = trimmedData.indexOf(',');
            if (separatorIndex < 0) {
                throw new SimpleDomainException("Base64 데이터 형식이 올바르지 않습니다.");
            }

            String metadata = trimmedData.substring(5, separatorIndex);
            payload = trimmedData.substring(separatorIndex + 1);

            int semicolonIndex = metadata.indexOf(';');
            if (semicolonIndex > 0) {
                resolvedContentType = metadata.substring(0, semicolonIndex);
            }
        }

        try {
            byte[] bytes = Base64.getDecoder().decode(payload);
            return new DecodedBase64File(bytes, resolvedContentType);
        } catch (IllegalArgumentException _) {
            throw new SimpleDomainException("Base64 데이터 형식이 올바르지 않습니다.");
        }
    }

    private record DecodedBase64File(
        byte[] bytes,
        String contentType
    ) {
    }
}
