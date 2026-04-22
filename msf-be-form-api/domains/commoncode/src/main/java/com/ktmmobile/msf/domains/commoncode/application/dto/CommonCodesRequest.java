package com.ktmmobile.msf.domains.commoncode.application.dto;

import java.util.List;
import java.util.Objects;

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

    public static CommonCodesRequest of(List<String> groupIds, Boolean includeAll, Boolean includeDetail) {
        return new CommonCodesRequest(validateGroupIds(groupIds), includeAll, includeDetail);
    }

    public static CommonCodesRequest of(List<String> groupIds) {
        return of(groupIds, false, false);
    }

    public static CommonCodesRequest of(String... groupIds) {
        return of(List.of(groupIds));
    }

    public static CommonCodesRequest withIncludeAll(List<String> groupIds) {
        return of(groupIds, true, false);
    }

    public static CommonCodesRequest withIncludeAll(String... groupIds) {
        return withIncludeAll(List.of(groupIds));
    }

    public static CommonCodesRequest withIncludeDetail(List<String> groupIds) {
        return of(groupIds, false, true);
    }

    public static CommonCodesRequest withIncludeDetail(String... groupIds) {
        return withIncludeDetail(List.of(groupIds));
    }

    public static CommonCodesRequest withFull(List<String> groupIds) {
        return of(groupIds, true, true);
    }

    public static CommonCodesRequest withFull(String... groupIds) {
        return withFull(List.of(groupIds));
    }

    private static List<String> validateGroupIds(List<String> groupIds) {
        Objects.requireNonNull(groupIds, "groupIds must not be null");
        if (groupIds.isEmpty()) {
            throw new IllegalArgumentException("groupIds must not be empty");
        }

        return List.copyOf(groupIds);
    }
}
