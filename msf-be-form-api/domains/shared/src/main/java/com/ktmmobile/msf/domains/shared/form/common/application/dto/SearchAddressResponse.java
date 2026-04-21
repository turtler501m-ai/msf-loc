package com.ktmmobile.msf.domains.shared.form.common.application.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.ktmmobile.msf.domains.shared.form.common.domain.entity.SearchAddressResult;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SearchAddressResponse(Integer totalCount, List<JusoResponse> list) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record JusoResponse(String zipNo, String roadAddress1, String roadAddress2, String roadAddress, String jibunAddress) {

        public static JusoResponse of(SearchAddressResult.Juso juso) {
            return new JusoResponse(juso.zipNo(), juso.roadAddrPart1(), juso.roadAddrPart2(), juso.roadAddr(), juso.jibunAddr());
        }
    }

    public static SearchAddressResponse of(SearchAddressResult result) {
        return new SearchAddressResponse(Integer.parseInt(result.results().common().totalCount()), result.results().juso().stream().map(JusoResponse::of).toList());
    }
}
