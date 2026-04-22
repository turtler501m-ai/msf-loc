package com.ktmmobile.msf.domains.shared.form.common.adapter.client.httpclient;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.PostExchange;

import com.ktmmobile.msf.domains.shared.form.common.application.dto.JusoHttpClientResponse;

public interface JusoHttpClient {

    @PostExchange(url = "/addrlink/addrLinkApi.do", contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    JusoHttpClientResponse getJusoExternal(
        @RequestParam("confmKey") String confmKey,
        @RequestParam("currentPage") Integer currentPage,
        @RequestParam("countPerPage") Integer countPerPage,
        @RequestParam("keyword") String keyword,
        @RequestParam("resultType") String resultType
    );
}
