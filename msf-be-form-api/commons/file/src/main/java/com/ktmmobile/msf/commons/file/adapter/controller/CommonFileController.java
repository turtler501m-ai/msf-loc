package com.ktmmobile.msf.commons.file.adapter.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.file.application.dto.FileResponse;
import com.ktmmobile.msf.commons.file.application.dto.FileUploadRequest;
import com.ktmmobile.msf.commons.file.application.port.in.CommonFileService;
import com.ktmmobile.msf.commons.file.application.service.LocalFilesystemFileService;
import com.ktmmobile.msf.commons.file.application.service.ObjectStorageFileService;
import com.ktmmobile.msf.commons.file.domain.dto.CommonFile;
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
    public CommonResponse<FileResponse> uploadToObjectStorage(@ModelAttribute FileUploadRequest request) {
        CommonFile file = objectStorageFileService.writeFile(request.toFileRequest());
        return ResponseUtils.ok(FileResponse.of(file));
    }

    @PostMapping("/local/upload")
    public CommonResponse<FileResponse> uploadToLocalFilesystem(@ModelAttribute FileUploadRequest request) {
        CommonFile file = localFilesystemFileService.writeFile(request.toFileRequest());
        return ResponseUtils.ok(FileResponse.of(file));
    }
}
