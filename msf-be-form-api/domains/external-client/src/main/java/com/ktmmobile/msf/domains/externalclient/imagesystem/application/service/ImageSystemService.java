package com.ktmmobile.msf.domains.externalclient.imagesystem.application.service;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;
import com.ktmmobile.msf.commons.file.application.port.in.CommonFileService;
import com.ktmmobile.msf.commons.file.domain.dto.FileContent;
import com.ktmmobile.msf.domains.externalclient.imagesystem.application.dto.ByteArrayMultipartFile;
import com.ktmmobile.msf.domains.externalclient.imagesystem.application.dto.ImageSystemFileUploadRequest;
import com.ktmmobile.msf.domains.externalclient.imagesystem.application.dto.ImageSystemPdfUploadRequest;
import com.ktmmobile.msf.domains.externalclient.imagesystem.application.dto.ImageSystemPdfUploadResponse;
import com.ktmmobile.msf.domains.externalclient.imagesystem.application.port.in.ImageSystemUploader;
import com.ktmmobile.msf.domains.externalclient.imagesystem.application.port.out.ImageSystemClient;
import com.ktmmobile.msf.domains.externalclient.imagesystem.support.util.ImageSystemUtils;

@Service
@RequiredArgsConstructor
public class ImageSystemService implements ImageSystemUploader {

    private final ImageSystemClient imageSystemClient;
    private final CommonFileService commonFileService;

    @Override
    public List<ImageSystemPdfUploadResponse> uploadPdf(ImageSystemFileUploadRequest request) {
        if (request == null) {
            throw new SimpleDomainException("이미지 시스템 업로드 요청값이 없습니다.");
        }

        return uploadPdfByFiles(request.files(), request);
    }

    private List<ImageSystemPdfUploadResponse> uploadPdfByFiles(
        List<ImageSystemFileUploadRequest.UploadFile> files,
        ImageSystemFileUploadRequest request
    ) {
        if (files == null || files.isEmpty()) {
            throw new SimpleDomainException("업로드할 파일이 없습니다.");
        }

        List<ImageSystemPdfUploadResponse> responses = new ArrayList<>();

        for (ImageSystemFileUploadRequest.UploadFile file: files) {
            if (file == null || file.pathFileName() == null || file.pathFileName().isBlank()) {
                throw new SimpleDomainException("업로드할 파일 경로가 없습니다.");
            }

            String pathFileName = file.pathFileName();
            String fileTypeCd = normalizeFileTypeCd(file.fileTypeCd());

            ImageSystemUtils.ImageSystemMeta meta = ImageSystemUtils.resolveMeta(
                request.formTypeCd(),
                request.operTypeCd(),
                fileTypeCd
            );

            String originalFileName = ImageSystemUtils.extractFileName(pathFileName);
            String uploadFileName = originalFileName.replace("-", "");

            FileContent fileContent = commonFileService.readFile(pathFileName)
                .orElseThrow(() ->
                    new SimpleDomainException("파일을 찾을 수 없습니다. pathFileName=" + pathFileName)
                );

            ImageSystemUtils.UploadFile uploadFile = ImageSystemUtils.prepareUploadFile(uploadFileName, fileContent);

            ImageSystemPdfUploadRequest uploadRequest = ImageSystemPdfUploadRequest.builder()
                .docCd(meta.docCd())
                .parentScanId(request.parentScanId())
                .fileId(uploadFile.fileName())
                .workCd(meta.workCd())
                .workNm(meta.workNm())
                .rgstPrsnId(request.rgstPrsnId())
                .orgId(request.orgId())
                .custNm(request.custNm())
                .memo(request.memo())
                .onlineYn(request.onlineYn())
                .companyId(request.companyId())
                .file(toMultipartFile(uploadFile))
                .build();

            responses.add(imageSystemClient.uploadPdf(uploadRequest));
        }

        return responses;
    }

    private String normalizeFileTypeCd(String fileTypeCd) {
        return fileTypeCd == null || fileTypeCd.isBlank() ? null : fileTypeCd;
    }

    private MultipartFile toMultipartFile(ImageSystemUtils.UploadFile uploadFile) {
        return new ByteArrayMultipartFile(
            "file",
            uploadFile.fileName(),
            uploadFile.contentType(),
            uploadFile.content()
        );
    }
}