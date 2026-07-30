package com.ktmmobile.msf.domains.cache.terms.application.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.cachecore.application.port.in.CacheReader;
import com.ktmmobile.msf.domains.cache.terms.application.dto.TermsCacheRequest;
import com.ktmmobile.msf.domains.cache.terms.application.dto.TermsCacheResponse;
import com.ktmmobile.msf.domains.cache.terms.application.port.in.TermsCacheReader;
import com.ktmmobile.msf.domains.cache.terms.domain.entity.TermsDetail;
import com.ktmmobile.msf.domains.cache.terms.domain.entity.TermsDetailContent;
import com.ktmmobile.msf.domains.cache.terms.domain.entity.TermsInfo;
import com.ktmmobile.msf.domains.cache.terms.domain.entity.TermsType;

/**
 * 외부 도메인 약관 캐시 조회 기능 제공
 */
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TermsCacheService implements TermsCacheReader {

    private final CacheReader cacheReader;

    /** 요청 조건 기준 약관 목록 조회 */
    @Override
    public List<TermsCacheResponse> getListTerms(TermsCacheRequest request) {
        TermsType termsType = cacheReader.get(TermsCacheLoader.CACHE_NAME, request.cdGroupId(), TermsType.class).orElse(null);
        if (termsType == null) {
            return Collections.emptyList();
        }
        LocalDateTime today = LocalDateTime.now();
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        List<TermsCacheRequest.SpecTerms> specList = request.specTermsList();
        List<TermsCacheResponse> result = new ArrayList<>();
        List<TermsInfo> termsList = termsType.getInfos();
        List<TermsInfo> filteredInfos = termsList.stream()
            .filter(v -> "Y".equals(v.getUseYn()) && currentTime.compareTo(v.getPstngStartDate()) >= 0 && currentTime.compareTo(v.getPstngEndDate()) <= 0)
            .filter(v -> specList == null || specList.isEmpty()
                || specList.stream().anyMatch(s -> !"Y".equals(v.getDtlCdDesc()) || s.dtlCd().equals(v.getDtlCd())))
            .toList();

        for (TermsInfo termsInfo: filteredInfos) {
            List<TermsDetail> details = termsInfo.getDetails();

            TermsDetailContent filteredContent = null;
            for (TermsDetail detail: details) {
                List<TermsDetailContent> contents = detail.getContents();
                filteredContent = contents.stream()
                    .filter(v -> "Y".equals(v.getUseYn()) && !today.isBefore(v.getEventStartDt()) && !today.isAfter(v.getEventEndDt()))
                    .max(Comparator.comparing(TermsDetailContent::getDocVer))
                    .orElse(null);
            }

            result.add(TermsCacheResponse.of(termsType,
                termsInfo,
                filteredContent != null ? filteredContent.getDocVer() : null,
                null,
                request.specTermsList()));
        }

        return result;
    }

    /** 요청 조건 기준 약관 본문 조회 */
    @Override
    public TermsCacheResponse getTermsContent(TermsCacheRequest request) {
        TermsType termsType = cacheReader.get(TermsCacheLoader.CACHE_NAME, request.cdGroupId(), TermsType.class).orElse(null);
        if (termsType == null) {
            return null;
        }
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        List<TermsCacheRequest.SpecTerms> specList = request.specTermsList();
        List<TermsInfo> termsList = termsType.getInfos();
        TermsInfo filteredInfo = termsList.stream()
            .filter(v -> v.getCdGroupId().equals(request.cdGroupId()) && v.getDtlCd().equals(request.dtlCd()))
            .filter(v -> "Y".equals(v.getUseYn()) &&
                currentTime.compareTo(v.getPstngStartDate()) >= 0 &&
                currentTime.compareTo(v.getPstngEndDate()) <= 0
            )
            .filter(v -> specList == null || specList.isEmpty()
                || specList.stream().anyMatch(s -> !"Y".equals(v.getDtlCdDesc())
                || s.dtlCd().equals(v.getDtlCd())))
            .findFirst().orElse(null);
        if (filteredInfo == null) {
            return null;
        }

        TermsCacheRequest.SpecTerms specInfo = null;
        if (specList != null && !specList.isEmpty()) {
            for (TermsCacheRequest.SpecTerms info: specList) {
                if (StringUtils.hasText(info.docType()) || StringUtils.hasText(info.expnsnStrVal())) {
                    specInfo = info;
                    break;
                }
            }
        }

        List<TermsDetail> details = filteredInfo.getDetails().stream()
            .filter(v -> v.getCdGroupId1().equals(request.expnsnStrVal1()) && v.getCdGroupId2().equals(request.expnsnStrVal2()))
            .toList();

        // List<String> excludedGroupId1 = List.of("BOARDINFO", "BOARDFORM", "INFO", "INFOPRMT");
        TermsDetailContent filteredContent = null;

        if (specInfo != null) {
            for (TermsDetail detail: details) {
                TermsCacheRequest.SpecTerms finalSpecInfo = specInfo;
                List<TermsDetailContent> contents = detail.getContents().stream().filter(v -> v.getDocType()
                    .equals(finalSpecInfo.docType()) && v.getExpnsnStrVal().equals(finalSpecInfo.expnsnStrVal())).toList();
                if (!contents.isEmpty()) {
                    filteredContent = contents.getFirst();
                    break;
                }
            }
        } else {
            for (TermsDetail detail: details) {
                List<TermsDetailContent> contents = detail.getContents();
                if (contents == null || contents.isEmpty()) {
                    continue;
                }

                if (contents.size() == 1) {
                    filteredContent = contents.get(0);
                    break;
                }

                filteredContent = contents.stream()
                    .filter(v -> v.getDocVer().equals(request.docVer()))
                    .findFirst().orElse(null);
                if (filteredContent != null) {
                    break;
                }
            }
        }

        if (filteredContent == null) {
            filteredContent = TermsDetailContent.builder().docVer("").docContent("").build();
        }

        return TermsCacheResponse.of(termsType,
            filteredInfo,
            filteredContent.getDocVer(),
            filteredContent.getDocContent(),
            request.specTermsList());
    }
}
