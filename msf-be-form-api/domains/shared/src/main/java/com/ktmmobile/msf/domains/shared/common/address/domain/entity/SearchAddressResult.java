package com.ktmmobile.msf.domains.shared.common.address.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SearchAddressResult(
    Results results
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Results(Common common, java.util.List<Juso> juso) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Common(String totalCount, String errorCode, String errorMessage) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Juso(String roadAddrPart1, String roadAddrPart2, String roadAddr, String jibunAddr, String zipNo) {}
}
