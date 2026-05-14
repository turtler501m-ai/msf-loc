package com.ktmmobile.msf.domains.cache.terms.application.dto;

import java.util.List;
import jakarta.validation.constraints.NotBlank;

public record TermsCacheRequest(
    @NotBlank String cdGroupId,
    String dtlCd,
    String expnsnStrVal1,
    String expnsnStrVal2,
    String docVer,
    List<SpecTerms> specTermsList
) {
    public record SpecTerms(
        @NotBlank String dtlCd,
        String docType,
        String expnsnStrVal,
        String docTypeName
    ) {
        public static SpecTerms of(String dtlCd, String docType, String expnsnStrVal, String docTypeName) {
            return new SpecTerms(dtlCd, docType, expnsnStrVal, docTypeName);
        }
    }

    public static TermsCacheRequest of(String cdGroupId, List<SpecTerms> specTermsList) {
        return of(cdGroupId, null, null, null, null,  specTermsList);
    }

                                       public static TermsCacheRequest of(String cdGroupId, String dtlCd, String expnsnStrVal1, String expnsnStrVal2, String docVer, List<SpecTerms> specTermsList) {
        return new TermsCacheRequest(cdGroupId, dtlCd, expnsnStrVal1, expnsnStrVal2, docVer, specTermsList);
    }
}
