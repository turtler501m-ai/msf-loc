package com.ktmmobile.msf.commons.file.support.util;

import jakarta.annotation.Nullable;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.ktmmobile.msf.commons.file.domain.dto.FileContent;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileResponseUtils {

    /**
     * 파일 다운로드
     */
    public static ResponseEntity<Resource> downloadFile(@Nullable FileContent contentInfo) {
        if (contentInfo == null || contentInfo.getContentLength() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return downloadFile(
            contentInfo.content(),
            contentInfo.commonFile().rawFile().fileName(),
            contentInfo.getContentType()
        );
    }

    /**
     * 바이너리 파일 다운로드
     */
    public static ResponseEntity<Resource> downloadFile(@Nullable byte[] content, String fileName, MediaType contentType) {
        if (content == null || content.length == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        ByteArrayResource resource = new ByteArrayResource(content);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(contentType);
        httpHeaders.setContentLength(content.length);
        String encodedFileName = FileUtils.getUrlEncodedFileName(fileName);
        httpHeaders.setContentDispositionFormData("attachment", encodedFileName);

        return new ResponseEntity<>(resource, httpHeaders, HttpStatus.OK);
    }
}
