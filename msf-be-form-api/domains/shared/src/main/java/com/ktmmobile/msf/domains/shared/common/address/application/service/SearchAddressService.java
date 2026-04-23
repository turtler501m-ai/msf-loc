package com.ktmmobile.msf.domains.shared.common.address.application.service;

import java.util.Objects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.msf.commons.client.support.exception.ClientException;
import com.ktmmobile.msf.commons.client.support.properties.ExternalServiceProperties;
import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;
import com.ktmmobile.msf.domains.shared.common.address.adapter.client.httpclient.JusoHttpClient;
import com.ktmmobile.msf.domains.shared.common.address.application.dto.JusoExternalRequest;
import com.ktmmobile.msf.domains.shared.common.address.application.dto.JusoHttpClientResponse;
import com.ktmmobile.msf.domains.shared.common.address.application.dto.SearchAddressCondition;
import com.ktmmobile.msf.domains.shared.common.address.application.dto.SearchAddressResponse;
import com.ktmmobile.msf.domains.shared.common.address.application.port.in.AddressReader;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SearchAddressService implements AddressReader {

    private final JusoHttpClient jusoHttpClient;
    private final ExternalServiceProperties externalServiceProperties;

    @Override public SearchAddressResponse getListAddress(SearchAddressCondition condition) {
        String encodedKeyword = resetKeyword(condition.keyword());

        String apiKey = externalServiceProperties.service("juso").property("api-key");

        JusoExternalRequest request = JusoExternalRequest.of(apiKey, encodedKeyword, condition.currentPage(), condition.countPerPage());

        JusoHttpClientResponse response;
        try {
            response = jusoHttpClient.getJusoExternal(apiKey, condition.currentPage(), condition.countPerPage(), encodedKeyword, "json");
        } catch (ClientException e) {
            throw new SimpleDomainException("주소 검색을 실패 하였습니다.", e);
        }

        if (!"0".equals(response.results().common().errorCode())) {
            throw new SimpleDomainException(response.results().common().errorMessage());
        }

        return SearchAddressResponse.of(Objects.requireNonNull(response.results()));
    }

    private String resetKeyword(String keyword) {
        return keyword.toUpperCase().replaceAll("(?i)(OR|SELECT|INSERT|DELETE|UPDATE|CREATE|DROP|EXEC|UNION|FETCH|DECLARE|TRUNCATE|--|;|/\\*|\\*/)", "");
    }
}
