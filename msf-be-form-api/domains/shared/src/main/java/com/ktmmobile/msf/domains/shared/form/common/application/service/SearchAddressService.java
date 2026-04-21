package com.ktmmobile.msf.domains.shared.form.common.application.service;

import java.time.Duration;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;
import com.ktmmobile.msf.domains.shared.form.common.support.properties.AddressApiProperties;
import com.ktmmobile.msf.domains.shared.form.common.application.dto.SearchAddressCondition;
import com.ktmmobile.msf.domains.shared.form.common.application.dto.SearchAddressResponse;
import com.ktmmobile.msf.domains.shared.form.common.application.port.in.AddressReader;
import com.ktmmobile.msf.domains.shared.form.common.domain.entity.SearchAddressResult;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SearchAddressService implements AddressReader {

    private final AddressApiProperties addressApiProperties;

    @Override public SearchAddressResponse getListAddress(SearchAddressCondition condition) {
        String encodedKeyword = resetKeyword(condition.keyword());

        JdkClientHttpRequestFactory factory = new JdkClientHttpRequestFactory();
        factory.setReadTimeout(Duration.ofSeconds(30));    // socket.timeout (responseTimeout)에 해당

        RestClient restClient = RestClient.builder()
            .requestFactory(factory)
            .baseUrl(addressApiProperties.url())
            .build();

        // Form 데이터 구성을 위한 MultiValueMap (form serialize 방식 대응)
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("confmKey", addressApiProperties.key());
        formData.add("currentPage", String.valueOf(condition.currentPage()));
        formData.add("countPerPage", String.valueOf(condition.countPerPage()));
        formData.add("keyword", encodedKeyword);
        formData.add("resultType", "json");

        SearchAddressResult result = restClient.post()
            .contentType(MediaType.APPLICATION_FORM_URLENCODED) // form serialize 설정
            .body(formData)
            .retrieve() // 응답 추출 시작
            .onStatus(HttpStatusCode::isError, (request, response) -> {
                throw new SimpleDomainException("주소 검색을 실패 하였습니다.: " + response.getStatusCode());
            })
            .body(SearchAddressResult.class);

        SearchAddressResult.Common common = Objects.requireNonNull(result).results().common();
        if (!"0".equals(common.errorCode())) {
            throw new SimpleDomainException(common.errorMessage());
        }

        return SearchAddressResponse.of(Objects.requireNonNull(result));
    }

    private String resetKeyword(String keyword) {
        return keyword.toUpperCase().replaceAll("(?i)(OR|SELECT|INSERT|DELETE|UPDATE|CREATE|DROP|EXEC|UNION|FETCH|DECLARE|TRUNCATE|--|;|/\\*|\\*/)", "");
    }
}
