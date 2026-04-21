package com.ktmmobile.msf.domains.commoncode.application.dto;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record CommonCodesRequest(
    @NotEmpty List<@NotBlank String> groupIds,
    Boolean includeAll,
    Boolean includeDetail
) {

    public boolean shouldIncludeAll() {
        return Boolean.TRUE.equals(includeAll);
    }

    public boolean shouldIncludeDetail() {
        return Boolean.TRUE.equals(includeDetail);
    }

    public static CommonCodesRequest of(List<String> groupIds) {
        return of(groupIds, false, false);
    }

    public static CommonCodesRequest of(List<String> groupIds, Boolean includeAll, Boolean includeDetail) {
        return new CommonCodesRequest(groupIds, includeAll, includeDetail);
    }
}
