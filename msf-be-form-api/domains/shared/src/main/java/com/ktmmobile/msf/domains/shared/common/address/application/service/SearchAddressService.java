package com.ktmmobile.msf.domains.shared.common.address.application.service;

import java.util.Objects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.msf.commons.client.support.exception.ClientException;
import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;
import com.ktmmobile.msf.domains.externalclient.common.property.ExternalServiceProperties;
import com.ktmmobile.msf.domains.shared.common.address.adapter.client.httpclient.JusoHttpClient;
import com.ktmmobile.msf.domains.shared.common.address.application.dto.JusoHttpClientResponse;
import com.ktmmobile.msf.domains.shared.common.address.application.dto.SearchAddressCondition;
import com.ktmmobile.msf.domains.shared.common.address.application.dto.SearchAddressResponse;
import com.ktmmobile.msf.domains.shared.common.address.application.port.in.AddressReader;

import static com.ktmmobile.msf.domains.externalclient.common.code.ClientConst.SERVICE_NAME_JUSO;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SearchAddressService implements AddressReader {

    private final JusoHttpClient jusoHttpClient;
    private final ExternalServiceProperties externalServiceProperties;

    @Override
    public SearchAddressResponse getListAddress(SearchAddressCondition condition) {
        String apiKey = externalServiceProperties.service(SERVICE_NAME_JUSO).property("api-key");
        String encodedKeyword = escapeSqlKeyword(condition.keyword());

        try {
            JusoHttpClientResponse response = jusoHttpClient.getJusoExternal(
                apiKey, condition.currentPage(), condition.countPerPage(), encodedKeyword, "json");
            validateHasNoError(response);

            return SearchAddressResponse.of(Objects.requireNonNull(response.results()));
        } catch (ClientException e) {
            throw new SimpleDomainException("주소 검색을 실패 하였습니다.", e);
        }
    }

    private String escapeSqlKeyword(String keyword) {
        return keyword.replaceAll("(?i)(OR|SELECT|INSERT|DELETE|UPDATE|CREATE|DROP|EXEC|UNION|FETCH|DECLARE|TRUNCATE|--|;|/\\*|\\*/)",
            "");
    }

    private static void validateHasNoError(JusoHttpClientResponse response) {
        if (!"0".equals(response.results().common().errorCode())) {
            throw new SimpleDomainException(response.results().common().errorMessage().replace("SQL 예약어 또는 ", ""));
        }
    }
}
