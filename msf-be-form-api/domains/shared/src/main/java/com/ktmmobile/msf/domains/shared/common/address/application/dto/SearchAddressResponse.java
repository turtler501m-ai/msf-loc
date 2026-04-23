package com.ktmmobile.msf.domains.shared.common.address.application.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SearchAddressResponse(Integer totalCount, List<JusoResponse> list) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record JusoResponse(String zipNo, String roadAddress1, String roadAddress2, String roadAddress, String jibunAddress) {

        public static JusoResponse of(JusoHttpClientResponse.Juso juso) {
            return new JusoResponse(juso.zipNo(), juso.roadAddrPart1(), juso.roadAddrPart2(), juso.roadAddr(), juso.jibunAddr());
        }
    }

    public static SearchAddressResponse of(JusoHttpClientResponse.Results results) {
        return new SearchAddressResponse(Integer.parseInt(results.common().totalCount()), results.juso().stream().map(JusoResponse::of).toList());
    }
}
