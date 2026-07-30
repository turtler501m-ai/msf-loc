package com.ktmmobile.msf.domains.externalclient.imagesystem.adapter.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.externalclient.imagesystem.application.dto.ImageSystemFileUploadRequest;
import com.ktmmobile.msf.domains.externalclient.imagesystem.application.dto.ImageSystemPdfUploadResponse;
import com.ktmmobile.msf.domains.externalclient.imagesystem.application.port.in.ImageSystemUploader;

@RequestMapping("/api/image-system")
@RestController
@RequiredArgsConstructor
public class ImageSystemController {

    private final ImageSystemUploader imageSystemUploader;

    @PostMapping("/file/upload")
    public CommonResponse<List<ImageSystemPdfUploadResponse>> uploadFile(@RequestBody ImageSystemFileUploadRequest request) {
        return ResponseUtils.ok(imageSystemUploader.uploadPdf(request));
    }
}