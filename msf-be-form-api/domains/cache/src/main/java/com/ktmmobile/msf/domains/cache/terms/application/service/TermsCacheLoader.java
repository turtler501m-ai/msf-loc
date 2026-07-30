package com.ktmmobile.msf.domains.cache.terms.application.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.cachecore.application.port.out.CacheLoader;
import com.ktmmobile.msf.commons.cachecore.domain.code.StartupCacheLoadMode;
import com.ktmmobile.msf.domains.cache.terms.application.port.out.TermsCacheRepository;
import com.ktmmobile.msf.domains.cache.terms.domain.entity.TermsDetail;
import com.ktmmobile.msf.domains.cache.terms.domain.entity.TermsInfo;
import com.ktmmobile.msf.domains.cache.terms.domain.entity.TermsType;

/**
 * 약관 정보 캐시 적재
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class TermsCacheLoader implements CacheLoader<TermsType> {

    public static final String CACHE_NAME = "form-terms";

    static final List<String> GROUP_LIST = List.of(
        "CLAUSE_FORM_01",
        "CLAUSE_FORM_03",
        "CLAUSE_FORM_04",
        "CLAUSE_MINOR_AGENT",
        "CLAUSE_INSUR",
        "CLAUSE_SHARING",
        "CLAUSE_SOLO"
    );

    private final TermsCacheRepository repository;

    /** 약관 캐시 이름 반환 */
    @Override
    public String cacheName() {
        return CACHE_NAME;
    }

    /** 약관 그룹과 상세 정보를 그룹ID 기준으로 적재 */
    @Override
    public Map<String, TermsType> load() {
        List<TermsType> list = repository.getListTermsType(GROUP_LIST);

        for (TermsType termsType: list) {
            List<TermsInfo> infoList = termsType.getInfos();

            List<TermsDetail> detailRequest = infoList.stream()
                .filter(
                    v -> StringUtils.hasText(v.getExpnsnStrVal1()) && StringUtils.hasText(v.getExpnsnStrVal2())
                )
                .map(
                    v -> TermsDetail.builder().cdGroupId1(v.getExpnsnStrVal1()).cdGroupId2(v.getExpnsnStrVal2()).build()
                ).toList();

            if (detailRequest.isEmpty()) {
                continue;
            }
            if (detailRequest.size() == 1) {
                detailRequest = List.of(detailRequest.getFirst(), TermsDetail.builder().cdGroupId1("").cdGroupId2("").build());
            }

            List<TermsDetail> detailList = repository.getListTermsDetail(detailRequest);

            for (TermsInfo termsInfo: infoList) {
                termsInfo.setDetails(
                    detailList.stream()
                        .filter(v -> v.getCdGroupId1().equals(termsInfo.getExpnsnStrVal1()) && v.getCdGroupId2().equals(termsInfo.getExpnsnStrVal2()))
                        .toList()
                );
            }
        }

        Map<String, TermsType> termsMapByGroupId = new LinkedHashMap<>();
        for (TermsType termsType: list) {
            termsMapByGroupId.put(termsType.getCdGroupId(), termsType);
        }

        return Map.copyOf(termsMapByGroupId);
    }

    /** 애플리케이션 시작 시 약관 캐시 적재 방식 반환 */
    @Override
    public StartupCacheLoadMode startupLoadMode() {
        return StartupCacheLoadMode.LOAD_IF_ABSENT;
    }
}
