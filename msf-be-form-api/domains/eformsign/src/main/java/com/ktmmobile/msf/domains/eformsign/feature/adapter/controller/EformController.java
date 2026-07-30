package com.ktmmobile.msf.domains.eformsign.feature.adapter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.eformsign.core.application.port.in.EFormSignCoreWriter;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.EformCancelRequest;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.EformFileDownloadResponse;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.EformResponse;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.EformSendLinkRequest;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.EformSendLinkResponse;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.EformValidateRequest;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.EformValidateResponse;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.EformsignFileDownloadRequest;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.VerifyFormPwRequest;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.VerifyFormPwResponse;
import com.ktmmobile.msf.domains.eformsign.feature.application.exception.EformDocumentFileNotReadyException;
import com.ktmmobile.msf.domains.eformsign.feature.application.port.in.EformReader;
import com.ktmmobile.msf.domains.eformsign.feature.application.port.in.EformWriter;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeRequest;

@RestController
@RequestMapping("/api/form/common")
@RequiredArgsConstructor
public class EformController {

    private final EformWriter writer;
    private final EformReader reader;
    private final EFormSignCoreWriter eFormSignCoreWriter;

    @PostMapping("/validate-eform/signature/get")
    public CommonResponse<EformValidateResponse> validateEformSignature(@RequestBody EformValidateRequest request) {
        return ResponseUtils.ok(reader.validateEformSignature(request));
    }

    @PostMapping("/eform/documents/files/create")
    public CommonResponse<EformFileDownloadResponse> eformsignFileDownload(@RequestBody EformsignFileDownloadRequest request) {
        return ResponseUtils.ok(reader.eformsignFileDownload(request));
    }

    @ExceptionHandler(EformDocumentFileNotReadyException.class)
    public ResponseEntity<CommonResponse<Void>> eformDocumentFileNotReady(
        EformDocumentFileNotReadyException e
    ) {
        return ResponseEntity.accepted()
            .body(CommonResponse.of(EformDocumentFileNotReadyException.CODE, e.getMessage()));
    }

    @PostMapping("/verifyFormPw/get")
    public CommonResponse<VerifyFormPwResponse> verifyFormPw(@RequestBody VerifyFormPwRequest request) {
        return ResponseUtils.ok(reader.verifyFormPw(request));
    }

    @PostMapping("/form-info/get")
    public CommonResponse<EformResponse> getFormInfo(@RequestBody NewChangeRequest request) {
        return ResponseUtils.ok(reader.getFormInfo(request));
    }

    @PostMapping("/eform/documents/link/send")
    public CommonResponse<EformSendLinkResponse> eformsignLinkSend(@RequestBody EformSendLinkRequest request) {
        return ResponseUtils.ok(writer.eformsignSendLink(request));
    }

    @PostMapping("/eform/documents/cancel")
    public void cancelDocument(@RequestBody EformCancelRequest request) {
        eFormSignCoreWriter.cancelDocument(request.documentIds());
    }

}
