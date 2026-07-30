package com.ktmmobile.msf.domains.cache.commoncode.application.dto;

import java.util.List;
import java.util.Objects;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

/**
 * 공통코드 캐시 조회 요청
 */
public record CommonCodesRequest(
    @NotEmpty List<@NotBlank String> groupIds,
    Boolean includeAll,
    Boolean includeDetail,
    Boolean validDate
) {

    /** 미사용 코드 포함 조회 여부 */
    public boolean shouldIncludeAll() {
        return Boolean.TRUE.equals(includeAll);
    }

    /** 상세 항목 응답 포함 여부 */
    public boolean shouldIncludeDetail() {
        return Boolean.TRUE.equals(includeDetail);
    }

    /** 코드 유효기간 확인 여부 */
    public boolean shouldCheckValidDate() {
        return !Boolean.FALSE.equals(validDate);
    }

    /** 공통코드 조회 요청 생성 */
    public static CommonCodesRequest of(
        List<String> groupIds,
        Boolean includeAll,
        Boolean includeDetail,
        Boolean validDate
    ) {
        return new CommonCodesRequest(validateGroupIds(groupIds), includeAll, includeDetail, validDate);
    }

    /** 공통코드 조회 요청 생성 */
    public static CommonCodesRequest of(List<String> groupIds, Boolean includeAll, Boolean includeDetail) {
        return of(groupIds, includeAll, includeDetail, true);
    }

    /** 사용 중이고 유효기간에 포함되는 공통코드 조회 요청 생성 */
    public static CommonCodesRequest of(List<String> groupIds) {
        return of(groupIds, false, false);
    }

    /** 사용 중이고 유효기간에 포함되는 공통코드 조회 요청 생성 */
    public static CommonCodesRequest of(String... groupIds) {
        return of(List.of(groupIds));
    }

    /** 미사용 코드 포함 공통코드 조회 요청 생성 */
    public static CommonCodesRequest withIncludeAll(List<String> groupIds) {
        return of(groupIds, true, false, false);
    }

    /** 미사용 코드 포함 공통코드 조회 요청 생성 */
    public static CommonCodesRequest withIncludeAll(String... groupIds) {
        return withIncludeAll(List.of(groupIds));
    }

    /** 상세 항목 포함 공통코드 조회 요청 생성 */
    public static CommonCodesRequest withIncludeDetail(List<String> groupIds) {
        return of(groupIds, false, true);
    }

    /** 상세 항목 포함 공통코드 조회 요청 생성 */
    public static CommonCodesRequest withIncludeDetail(String... groupIds) {
        return withIncludeDetail(List.of(groupIds));
    }

    /** 미사용 코드와 상세 항목 포함 공통코드 조회 요청 생성 */
    public static CommonCodesRequest withFull(List<String> groupIds) {
        return of(groupIds, true, true, false);
    }

    /** 미사용 코드와 상세 항목 포함 공통코드 조회 요청 생성 */
    public static CommonCodesRequest withFull(String... groupIds) {
        return withFull(List.of(groupIds));
    }

    /** 사용 중인 공통코드 조회 요청 생성 */
    public static CommonCodesRequest withUsedOnly(List<String> groupIds) {
        return of(groupIds, false, false, false);
    }

    /** 사용 중인 공통코드 조회 요청 생성 */
    public static CommonCodesRequest withUsedOnly(String... groupIds) {
        return withUsedOnly(List.of(groupIds));
    }

    /** 사용 중인 공통코드와 상세 항목 조회 요청 생성 */
    public static CommonCodesRequest withUsedOnlyAndDetail(List<String> groupIds) {
        return of(groupIds, false, true, false);
    }

    /** 사용 중인 공통코드와 상세 항목 조회 요청 생성 */
    public static CommonCodesRequest withUsedOnlyAndDetail(String... groupIds) {
        return withUsedOnlyAndDetail(List.of(groupIds));
    }

    private static List<String> validateGroupIds(List<String> groupIds) {
        Objects.requireNonNull(groupIds, "groupIds must not be null");
        if (groupIds.isEmpty()) {
            throw new IllegalArgumentException("groupIds must not be empty");
        }

        return List.copyOf(groupIds);
    }
}
