package com.ktmmobile.msf.domains.cache.terms.application.dto;

import java.util.List;

import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;

import com.ktmmobile.msf.domains.cache.terms.domain.entity.TermsInfo;
import com.ktmmobile.msf.domains.cache.terms.domain.entity.TermsType;

/**
 * 약관 캐시 조회 응답
 */
public record TermsCacheResponse(
    String groupCode,
    String groupName,
    String code,
    String name,
    String abbreviation,
    Boolean commonStatus,
    String parentCode,
    Integer sortNumber,
    String termsGroupCd,
    String termsItemCd,
    String required,
    String path,
    String version,
    String content
) {

    /** 약관 캐시 원천 데이터의 응답 변환 */
    public static TermsCacheResponse of(TermsType type, TermsInfo info, String docVer, String docContent, List<TermsCacheRequest.SpecTerms> specTermsList) {
        String dtlCdNm = info.getDtlCdNm();
        if ("Y".equals(info.getDtlCdDesc()) && specTermsList != null && !specTermsList.isEmpty()) {
            TermsCacheRequest.SpecTerms specTerms = specTermsList.stream()
                .filter(v -> v.dtlCd().equals(info.getDtlCd())).findFirst().orElse(null);
            if (specTerms != null && specTerms.docTypeName() != null) {
                dtlCdNm  = dtlCdNm.replace("#{jehuProdName}", specTerms.docTypeName());
            }
        }
        return new TermsCacheResponse(
            type.getCdGroupId(),
            type.getCdGroupNm(),
            info.getDtlCd(),
            dtlCdNm,
            info.getDtlCdAbbrNm(),
            "Y".equals(info.getDtlCdDesc()),
            info.getUpGrpCd(),
            info.getSortOdrg(),
            info.getExpnsnStrVal1(),
            info.getExpnsnStrVal2(),
            info.getExpnsnStrVal3(),
            info.getFilePathNm(),
            StringUtils.hasText(docVer) ? docVer: null,
            StringUtils.hasText(docContent) ? HtmlUtils.htmlUnescape(docContent) : docContent
        );
    }
}
