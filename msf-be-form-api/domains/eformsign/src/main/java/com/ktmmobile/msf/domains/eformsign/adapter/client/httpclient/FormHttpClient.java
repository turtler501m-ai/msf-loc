package com.ktmmobile.msf.domains.eformsign.adapter.client.httpclient;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.PostExchange;

import com.ktmmobile.msf.domains.eformsign.application.dto.EformApiTokenRequest;
import com.ktmmobile.msf.domains.eformsign.application.dto.EformApiTokenResponse;

public interface FormHttpClient {

    // public List<FormResponse> formSubmit(FormRequest formRequest) {
    //     return null;
    // }

    @PostExchange(value = "/v2.0/api_auth/access_token", contentType = MediaType.APPLICATION_JSON_VALUE)
    EformApiTokenResponse getEformApiToken(
        @RequestHeader("eformsign_signature") String eformsignSignature,
        @RequestHeader("Authorization") String Authorization,
        @RequestBody EformApiTokenRequest clientRequest
    );
}
