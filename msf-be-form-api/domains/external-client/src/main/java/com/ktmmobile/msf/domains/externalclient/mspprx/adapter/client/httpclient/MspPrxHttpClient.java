package com.ktmmobile.msf.domains.externalclient.mspprx.adapter.client.httpclient;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
public interface MspPrxHttpClient {

    String CHARSET_UTF8 = "charset=UTF-8";
    String FORM_URLENCODED_UTF8_VALUE = MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";" + CHARSET_UTF8;
    String JSON_UTF8_VALUE = MediaType.APPLICATION_JSON_VALUE + ";" + CHARSET_UTF8;
    String TEXT_PLAIN_UTF8_VALUE = MediaType.TEXT_PLAIN_VALUE + ";" + CHARSET_UTF8;


    @PostExchange(url = "/mPlatform/serviceCall.do",
        contentType = FORM_URLENCODED_UTF8_VALUE,
        accept = TEXT_PLAIN_UTF8_VALUE
    )
    String serviceCall(@RequestBody MultiValueMap<String, String> form);


    @PostExchange(url = "/mPlatform/serviceCallJson.do",
        contentType = JSON_UTF8_VALUE,
        accept = TEXT_PLAIN_UTF8_VALUE
    )
    String serviceCallJson(@RequestBody Map<String, Object> body);


    @PostExchange(url = "/mPlatform/osstServiceCall.do",
        contentType = FORM_URLENCODED_UTF8_VALUE,
        accept = TEXT_PLAIN_UTF8_VALUE
    )
    String osstServiceCall(@RequestBody MultiValueMap<String, String> form);


    @PostExchange(url = "/mPlatform/simpleOpenServiceCall.do",
        contentType = FORM_URLENCODED_UTF8_VALUE,
        accept = TEXT_PLAIN_UTF8_VALUE
    )
    String simpleOpenServiceCall(@RequestBody MultiValueMap<String, String> form);


    @PostExchange(url = "/mPlatform/xmlOsstServiceCall.do",
        contentType = FORM_URLENCODED_UTF8_VALUE,
        accept = TEXT_PLAIN_UTF8_VALUE
    )
    String xmlOsstServiceCall(@RequestBody MultiValueMap<String, String> form);


    @PostExchange(url = "/mPlatform/xmlSelfServiceCall.do",
        contentType = FORM_URLENCODED_UTF8_VALUE,
        accept = TEXT_PLAIN_UTF8_VALUE
    )
    String xmlSelfServiceCall(@RequestBody MultiValueMap<String, String> form);
}
