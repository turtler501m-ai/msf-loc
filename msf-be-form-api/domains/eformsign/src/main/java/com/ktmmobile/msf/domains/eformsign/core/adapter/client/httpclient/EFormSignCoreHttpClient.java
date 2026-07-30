package com.ktmmobile.msf.domains.eformsign.core.adapter.client.httpclient;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

import com.ktmmobile.msf.domains.eformsign.core.adapter.client.dto.EFormSignCoreApiTokenHttpResponse;
import com.ktmmobile.msf.domains.eformsign.core.adapter.client.dto.EFormSignCoreDocumentCancelRequest;
import com.ktmmobile.msf.domains.eformsign.core.application.dto.EFormSignCoreApiTokenRequest;

public interface EFormSignCoreHttpClient {

    @PostExchange(value = "/Service/v2.0/api_auth/access_token", contentType = MediaType.APPLICATION_JSON_VALUE)
    EFormSignCoreApiTokenHttpResponse issueApiToken(
        @RequestHeader("eformsign-signature") String eformsignSignature,
        @RequestHeader("Authorization") String authorization,
        @RequestBody EFormSignCoreApiTokenRequest request
    );

    @GetExchange(value = "/Service/v2.0/api/documents/{documentId}/download_files")
    byte[] downloadDocumentFile(
        @RequestHeader("Authorization") String authorization,
        @PathVariable String documentId,
        @RequestParam("file_type") String fileType,
        @RequestParam("file_name") String fileName
    );

    @PostExchange(value = "/Service/v2.0/api/documents/cancel", contentType = MediaType.APPLICATION_JSON_VALUE)
    void cancelDocument(
        @RequestHeader("Authorization") String authorization,
        @RequestBody EFormSignCoreDocumentCancelRequest request
    );
}
