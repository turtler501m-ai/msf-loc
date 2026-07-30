package com.ktmmobile.msf.domains.externalclient.imagesystem.adapter.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;
import com.ktmmobile.msf.domains.externalclient.imagesystem.adapter.client.httpclient.ImageSystemHttpClient;
import com.ktmmobile.msf.domains.externalclient.imagesystem.application.dto.ImageSystemPdfUploadRequest;
import com.ktmmobile.msf.domains.externalclient.imagesystem.application.dto.ImageSystemPdfUploadResponse;
import com.ktmmobile.msf.domains.externalclient.imagesystem.application.port.out.ImageSystemClient;

/**
 * 이미지 시스템 외부 연동 어댑터
 */
@Component
@RequiredArgsConstructor
class ImageSystemClientImpl implements ImageSystemClient {

    private static final String FILE_PART_NAME = "file";
    private static final String PDF_UPLOAD_METHOD = "PDFUPLOAD";

    private final ImageSystemHttpClient httpClient;

    @Override
    public ImageSystemPdfUploadResponse uploadPdf(ImageSystemPdfUploadRequest request) {
        if (request == null) {
            throw new SimpleDomainException("이미지 시스템 PDF 업로드 요청값이 없습니다.");
        }

        return httpClient.uploadPdf(
            PDF_UPLOAD_METHOD,
            request.docCd(),
            request.parentScanId(),
            request.fileId(),
            request.workCd(),
            request.workNm(),
            request.rgstPrsnId(),
            request.orgId(),
            request.custNm(),
            request.memo(),
            request.onlineYn(),
            request.companyId(),
            multipartFormData(request.file())
        );
    }

    private MultiValueMap<String, Object> multipartFormData(MultipartFile file) {
        validateFile(file);

        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        form.add(FILE_PART_NAME, new HttpEntity<>(file.getResource(), filePartHeaders(file)));
        return form;
    }

    private HttpHeaders filePartHeaders(MultipartFile file) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(fileContentType(file));
        return headers;
    }

    private MediaType fileContentType(MultipartFile file) {
        String contentType = file.getContentType();
        if (StringUtils.hasText(contentType)) {
            return MediaType.parseMediaType(contentType);
        }

        String name = filename(file).toLowerCase();
        if (name.endsWith(".tif") || name.endsWith(".tiff")) {
            return MediaType.parseMediaType("image/tiff");
        }
        if (name.endsWith(".pdf")) {
            return MediaType.APPLICATION_PDF;
        }
        return MediaType.APPLICATION_OCTET_STREAM;
    }

    private String filename(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (StringUtils.hasText(originalFilename)) {
            return originalFilename;
        }
        return file.getName();
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new SimpleDomainException("이미지 시스템 업로드 파일이 없습니다.");
        }
    }
}
