package com.ktmmobile.msf.domains.externalclient.nice.application.dto;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Builder;
import lombok.Singular;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

/**
 * NICE API form-urlencoded 요청 값 객체
 */
@Builder
public record NiceApiFormRequest(
    @Singular("parameter")
    Map<String, String> parameters
) {

    public NiceApiFormRequest {
        Map<String, String> filteredParameters = new LinkedHashMap<>();
        if (parameters != null) {
            parameters.forEach((key, value) -> {
                if (StringUtils.hasText(key) && value != null) {
                    filteredParameters.put(key, value);
                }
            });
        }
        parameters = Collections.unmodifiableMap(filteredParameters);
    }

    public MultiValueMap<String, String> toFormData() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        parameters.forEach(form::add);
        return form;
    }
}
