package com.ktmmobile.msf.domains.externalclient.nice.adapter.client.httpclient;

import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

/**
 * NICE API 선언형 HTTP client
 */
@HttpExchange
public interface NiceApiHttpClient {

    String CHARSET_UTF8 = "charset=UTF-8";
    String FORM_URLENCODED_UTF8_VALUE = MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";" + CHARSET_UTF8;

    /**
     * mcp-api-ext /rlnmCheck.do가 중계하던 NICE 실제 endpoint 직접 호출
     */
    @PostExchange(url = "/nuguya2/service/realname/sprealnameactconfirm.do", contentType = FORM_URLENCODED_UTF8_VALUE)
    String checkAccount(@RequestBody MultiValueMap<String, String> form);
}
