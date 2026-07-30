package com.ktmmobile.msf.domains.eformsign.file.adapter.controller;

import java.util.List;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.eformsign.file.application.dto.EFormSignFileListRequest;
import com.ktmmobile.msf.domains.eformsign.file.application.dto.EFormSignFileListResponse;
import com.ktmmobile.msf.domains.eformsign.file.application.dto.EFormSignFilePathRequest;
import com.ktmmobile.msf.domains.eformsign.file.application.dto.EFormSignFileResponse;
import com.ktmmobile.msf.domains.eformsign.file.application.dto.EFormSignFileUploadPrepareRequest;
import com.ktmmobile.msf.domains.eformsign.file.application.dto.EFormSignFileUploadUrlResponse;
import com.ktmmobile.msf.domains.eformsign.file.application.port.in.EFormSignFileReader;
import com.ktmmobile.msf.domains.eformsign.file.application.port.in.EFormSignFileRemover;
import com.ktmmobile.msf.domains.eformsign.file.application.port.in.EFormSignFileUploadPreparer;

/**
 * eFormSign 파일 API
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/external-services/eformsign/files")
public class EFormSignFileController {

    private final EFormSignFileUploadPreparer eformSignFileUploadPreparer;
    private final EFormSignFileReader eformSignFileReader;
    private final EFormSignFileRemover eformSignFileRemover;

    /**
     * eFormSign 파일 업로드 준비
     */
    @PostMapping("/upload/prepare")
    public CommonResponse<EFormSignFileUploadUrlResponse> prepareUpload(@RequestBody @Valid EFormSignFileUploadPrepareRequest request) {
        return ResponseUtils.ok(eformSignFileUploadPreparer.issueUploadUrl(request));
    }

    /**
     * eFormSign 파일 조회
     */
    @PostMapping("/get")
    public CommonResponse<EFormSignFileResponse> getFile(@RequestBody @Valid EFormSignFilePathRequest request) {
        return ResponseUtils.ok(eformSignFileReader.getFile(request));
    }

    /**
     * eFormSign 파일 목록 조회
     */
    @PostMapping("/list")
    public CommonResponse<List<EFormSignFileListResponse>> listFiles(@RequestBody @Valid EFormSignFileListRequest request) {
        return ResponseUtils.ok(eformSignFileReader.listFiles(request));
    }

    /**
     * eFormSign 파일 삭제
     */
    @PostMapping("/remove")
    public CommonResponse<Void> removeFile(@RequestBody @Valid EFormSignFilePathRequest request) {
        eformSignFileRemover.removeFile(request);
        return ResponseUtils.ok();
    }
}
