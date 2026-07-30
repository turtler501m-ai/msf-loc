package com.ktmmobile.msf.domains.eformsign.feature.adapter.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import tools.jackson.databind.ObjectMapper;

import com.ktmmobile.msf.commons.common.exception.CommonException;
import com.ktmmobile.msf.domains.eformsign.core.application.dto.EFormSignCoreApiTokenResponse;
import com.ktmmobile.msf.domains.eformsign.core.application.port.in.EFormSignCoreReader;
import com.ktmmobile.msf.domains.eformsign.feature.adapter.client.dto.EformSendDocumentHttpResponse;
import com.ktmmobile.msf.domains.eformsign.feature.adapter.client.dto.EformSendDocumentRequest;
import com.ktmmobile.msf.domains.eformsign.feature.adapter.client.dto.EformValidateHttpRequest;
import com.ktmmobile.msf.domains.eformsign.feature.adapter.client.dto.EformValidateHttpResponse;
import com.ktmmobile.msf.domains.eformsign.feature.adapter.client.httpclient.FormHttpClient;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.EformFileDownloadResponse;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.EformSendLinkRequest;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.EformSendLinkResponse;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.EformValidateRequest;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.EformsignFileDownloadRequest;
import com.ktmmobile.msf.domains.eformsign.feature.application.exception.EformDocumentFileNotReadyException;
import com.ktmmobile.msf.domains.eformsign.feature.application.port.out.EformClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class FormClientImpl implements EformClient {

    private final EFormSignCoreReader eFormSignCoreReader;
    private final FormHttpClient formHttpClient;
    private final ObjectMapper objectMapper;

    @Override
    public EformValidateHttpResponse validateEformSignature(EformValidateRequest request) {
        return formHttpClient.validateEformSignature(
            "Bearer " + request.accessToken(),
            request.documentId(),
            EformValidateHttpRequest.of(request.componentIds())
        );
    }

    @Override
    public EformFileDownloadResponse eformsignFileDownload(
        EformsignFileDownloadRequest request
    ) {

        String authorization = "Bearer " + request.accessToken();

        byte[] fileBytes;
        try {
            fileBytes = formHttpClient.eformsignFileDownload(
                authorization,
                request.documentId(),
                request.fileType(),
                request.fileName()
            );
        } catch (CommonException e) {
            if (isDocumentFileNotReady(e.getMessage())) {
                throw new EformDocumentFileNotReadyException();
            }
            throw e;
        } catch (RestClientResponseException e) {
            if (e.getStatusCode().value() == 400 && isDocumentFileNotReady(e.getResponseBodyAsString())) {
                throw new EformDocumentFileNotReadyException();
            }
            throw e;
        }

        if (fileBytes == null || fileBytes.length == 0) {
            throw new IllegalStateException("eformsign 파일 다운로드 실패");
        }

        return new EformFileDownloadResponse(
            request.documentId(),
            request.fileName(),
            request.fileCategory(),
            "application/zip",
            fileBytes,
            null
        );
    }

    private boolean isDocumentFileNotReady(String responseMessage) {
        try {
            int responseBodyStart = responseMessage.indexOf('{');
            if (responseBodyStart < 0) {
                return false;
            }
            String responseBody = responseMessage.substring(responseBodyStart);
            EformErrorResponse response = objectMapper.readValue(responseBody, EformErrorResponse.class);
            return EformDocumentFileNotReadyException.CODE.equals(response.code());
        } catch (Exception e) {
            return false;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record EformErrorResponse(String code) {
    }

    @Override
    public EformSendLinkResponse eformsignSendLink(EformSendLinkRequest request) {
        EFormSignCoreApiTokenResponse token = eFormSignCoreReader.getEformSignApiToken();
        String companyId = token.companyId();
        String authorization = "Bearer " + token.accessToken();

        EformSendDocumentRequest httpRequest = new EformSendDocumentRequest(
            request.pwd(),
            request.hint()
        );
        EformSendDocumentHttpResponse httpResponse = formHttpClient.getEformSendDocument(authorization, companyId, request.documentId(), httpRequest);
        String viewLink = httpResponse.result() == null ? null : httpResponse.result().viewLink();
        if (!"-1".equals(httpResponse.code())) {
            return new EformSendLinkResponse(httpResponse.code(), httpResponse.message(), viewLink);
        }

        return new EformSendLinkResponse("0000", httpResponse.message(), viewLink);
    }
}
