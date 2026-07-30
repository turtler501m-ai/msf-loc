package com.ktmmobile.msf.commons.file.adapter.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.file.application.dto.FileBase64UploadRequest;
import com.ktmmobile.msf.commons.file.application.dto.FileMetadataResponse;
import com.ktmmobile.msf.commons.file.application.dto.FilePathRequest;
import com.ktmmobile.msf.commons.file.application.dto.FileResponse;
import com.ktmmobile.msf.commons.file.application.dto.FileUploadRequest;
import com.ktmmobile.msf.commons.file.application.dto.FileVariantResponse;
import com.ktmmobile.msf.commons.file.application.port.in.CommonFileService;
import com.ktmmobile.msf.commons.file.application.service.LocalFilesystemFileService;
import com.ktmmobile.msf.commons.file.application.service.ObjectStorageFileService;
import com.ktmmobile.msf.commons.file.domain.dto.FileContent;
import com.ktmmobile.msf.commons.file.support.util.FileResponseUtils;
import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;

@RequestMapping("/api/files")
@RestController
public class CommonFileController {

    private final CommonFileService localFilesystemFileService;
    private final CommonFileService objectStorageFileService;

    public CommonFileController(
        @Qualifier(LocalFilesystemFileService.BEAN_NAME) CommonFileService localFilesystemFileService,
        @Qualifier(ObjectStorageFileService.BEAN_NAME) CommonFileService objectStorageFileService
    ) {
        this.localFilesystemFileService = localFilesystemFileService;
        this.objectStorageFileService = objectStorageFileService;
    }

    @PostMapping("/object/upload")
    public CommonResponse<FileVariantResponse> uploadToObjectStorage(@ModelAttribute FileUploadRequest request) {
        return ResponseUtils.ok(FileVariantResponse.of(objectStorageFileService.writeFile(request.toFileRequest(), request.toVariantOptions())));
    }

    @PostMapping("/local/upload")
    public CommonResponse<FileVariantResponse> uploadToLocalFilesystem(@ModelAttribute FileUploadRequest request) {
        return ResponseUtils.ok(FileVariantResponse.of(localFilesystemFileService.writeFile(request.toFileRequest(), request.toVariantOptions())));
    }

    @PostMapping("/object/download")
    public ResponseEntity<Resource> downloadFromObjectStorage(@RequestBody FilePathRequest request) {
        FileContent fileContent = objectStorageFileService.readFile(request.filePath())
            .orElseGet(FileContent::noContent);
        return FileResponseUtils.downloadFile(fileContent);
    }

    @PostMapping("/local/download")
    public ResponseEntity<Resource> downloadFromLocalFilesystem(@RequestBody FilePathRequest request) {
        FileContent fileContent = localFilesystemFileService.readFile(request.filePath())
            .orElseGet(FileContent::noContent);
        return FileResponseUtils.downloadFile(fileContent);
    }

    @PostMapping("/object/meta/get")
    public CommonResponse<FileMetadataResponse> getObjectStorageMetadata(@RequestBody FilePathRequest request) {
        return ResponseUtils.ok(createFileMetadataResponse(objectStorageFileService, request.filePath()));
    }

    @PostMapping("/object/signed-url/get")
    public CommonResponse<String> getObjectStorageSignedUrl(@RequestBody FilePathRequest request) {
        return ResponseUtils.ok(objectStorageFileService.generateSignedUrl(request.filePath()));
    }

    @PostMapping("/local/meta/get")
    public CommonResponse<FileMetadataResponse> getLocalFilesystemMetadata(@RequestBody FilePathRequest request) {
        return ResponseUtils.ok(createFileMetadataResponse(localFilesystemFileService, request.filePath()));
    }

    @PostMapping("/object/remove")
    public CommonResponse<Void> removeFromObjectStorage(@RequestBody FilePathRequest request) {
        objectStorageFileService.removeFile(request.filePath());
        return ResponseUtils.ok();
    }

    @PostMapping("/local/remove")
    public CommonResponse<Void> removeFromLocalFilesystem(@RequestBody FilePathRequest request) {
        localFilesystemFileService.removeFile(request.filePath());
        return ResponseUtils.ok();
    }

    @PostMapping("/object/base64/upload")
    public CommonResponse<FileVariantResponse> uploadBase64ToObjectStorage(@RequestBody FileBase64UploadRequest request) {
        return ResponseUtils.ok(FileVariantResponse.of(objectStorageFileService.writeFile(request.toFileRequest(), request.toVariantOptions())));
    }

    @PostMapping("/local/base64/upload")
    public CommonResponse<FileVariantResponse> uploadBase64ToLocalFilesystem(@RequestBody FileBase64UploadRequest request) {
        return ResponseUtils.ok(FileVariantResponse.of(localFilesystemFileService.writeFile(request.toFileRequest(), request.toVariantOptions())));
    }

    private FileMetadataResponse createFileMetadataResponse(CommonFileService commonFileService, String filePath) {
        return FileMetadataResponse.of(createFileResponse(commonFileService, filePath));
    }

    private FileResponse createFileResponse(CommonFileService commonFileService, String filePath) {
        return commonFileService.getFile(filePath)
            .map(FileResponse::of)
            .orElse(null);
    }
}
