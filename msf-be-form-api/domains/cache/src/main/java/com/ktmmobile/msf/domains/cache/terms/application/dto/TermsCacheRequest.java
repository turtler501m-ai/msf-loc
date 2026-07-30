package com.ktmmobile.msf.domains.cache.terms.application.dto;

import java.util.List;
import jakarta.validation.constraints.NotBlank;

/**
 * 약관 캐시 조회 요청
 */
public record TermsCacheRequest(
    @NotBlank String cdGroupId,
    String dtlCd,
    String expnsnStrVal1,
    String expnsnStrVal2,
    String docVer,
    List<SpecTerms> specTermsList
) {
    /**
     * 특정 약관 조건 조회에 사용하는 부가 약관 조건
     */
    public record SpecTerms(
        @NotBlank String dtlCd,
        String docType,
        String expnsnStrVal,
        String docTypeName
    ) {
        /** 부가 약관 조건 생성 */
        public static SpecTerms of(String dtlCd, String docType, String expnsnStrVal, String docTypeName) {
            return new SpecTerms(dtlCd, docType, expnsnStrVal, docTypeName);
        }
    }

    /** 약관 목록 조회 요청 생성 */
    public static TermsCacheRequest of(String cdGroupId, List<SpecTerms> specTermsList) {
        return of(cdGroupId, null, null, null, null,  specTermsList);
    }

    /** 약관 본문 조회 요청 생성 */
    public static TermsCacheRequest of(String cdGroupId, String dtlCd, String expnsnStrVal1, String expnsnStrVal2, String docVer, List<SpecTerms> specTermsList) {
        return new TermsCacheRequest(cdGroupId, dtlCd, expnsnStrVal1, expnsnStrVal2, docVer, specTermsList);
    }
}
