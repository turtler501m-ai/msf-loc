package com.ktmmobile.msf.commons.file.support.util;

import jakarta.annotation.Nullable;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

        ByteArrayResource resource = new ByteArrayResource(contentInfo.content());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(contentInfo.getContentType());
        httpHeaders.setContentLength(contentInfo.getContentLength());
        String encodedFileName = FileUtils.getUrlEncodedFileName(contentInfo.commonFile().rawFile().name());
        httpHeaders.setContentDispositionFormData("attachment", encodedFileName);

        return new ResponseEntity<>(resource, httpHeaders, HttpStatus.OK);
    }
}
