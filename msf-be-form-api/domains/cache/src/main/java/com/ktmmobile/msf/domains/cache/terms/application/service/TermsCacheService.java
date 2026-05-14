package com.ktmmobile.msf.domains.cache.terms.application.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.msf.commons.cachecore.application.port.in.CacheReader;
import com.ktmmobile.msf.domains.cache.terms.application.dto.TermsCacheRequest;
import com.ktmmobile.msf.domains.cache.terms.application.dto.TermsCacheResponse;
import com.ktmmobile.msf.domains.cache.terms.application.port.in.TermsCacheReader;
import com.ktmmobile.msf.domains.cache.terms.domain.entity.TermsDetail;
import com.ktmmobile.msf.domains.cache.terms.domain.entity.TermsDetailContent;
import com.ktmmobile.msf.domains.cache.terms.domain.entity.TermsInfo;
import com.ktmmobile.msf.domains.cache.terms.domain.entity.TermsType;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TermsCacheService implements TermsCacheReader {

    private final CacheReader cacheReader;

    @Override public List<TermsCacheResponse> getListTerms(TermsCacheRequest request) {
        TermsType termsType = cacheReader.get(TermsCacheLoader.CACHE_NAME, request.cdGroupId(), TermsType.class).orElse(null);
        if (termsType == null) {
            return null;
        }
        LocalDateTime today = LocalDateTime.now();
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        List<TermsCacheRequest.SpecTerms> specList = request.specTermsList();
        List<TermsCacheResponse> result = new ArrayList<>();
        List<TermsInfo> termsList = termsType.getInfos();
        List<TermsInfo> filteredInfos = termsList.stream()
            .filter(v -> "Y".equals(v.getUseYn()) && currentTime.compareTo(v.getPstngStartDate()) >= 0 && currentTime.compareTo(v.getPstngEndDate()) <= 0)
            .filter(v -> specList == null || specList.isEmpty() || specList.stream().anyMatch(s -> !"Y".equals(v.getDtlCdDesc()) || s.dtlCd().equals(v.getDtlCd())))
            .toList();

        for  (TermsInfo termsInfo : filteredInfos) {
            List<TermsDetail> details = termsInfo.getDetails();

            TermsDetailContent filteredContent = null;
            for (TermsDetail detail : details) {
                List<TermsDetailContent> contents = detail.getContents();
                filteredContent = contents.stream()
                    .filter(v -> "Y".equals(v.getUseYn()) && !today.isBefore(v.getEventStartDt()) && !today.isAfter(v.getEventEndDt()))
                    .max(Comparator.comparing(TermsDetailContent::getDocVer))
                    .orElse(null);
            }

            result.add(TermsCacheResponse.of(termsType, termsInfo, filteredContent != null ? filteredContent.getDocVer() : null, null, request.specTermsList()));
        }

        return result;
    }

    @Override public TermsCacheResponse getTermsContent(TermsCacheRequest request) {
        TermsType termsType = cacheReader.get(TermsCacheLoader.CACHE_NAME, request.cdGroupId(), TermsType.class).orElse(null);
        if (termsType == null) {
            return null;
        }
        LocalDateTime today = LocalDateTime.now();
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        List<TermsCacheRequest.SpecTerms> specList = request.specTermsList();
        List<TermsCacheResponse> result = new ArrayList<>();
        List<TermsInfo> termsList = termsType.getInfos();
        TermsInfo filteredInfo = termsList.stream()
            .filter(v -> v.getCdGroupId().equals(request.cdGroupId()) && v.getDtlCd().equals(request.dtlCd()))
            .filter(v -> "Y".equals(v.getUseYn()) && currentTime.compareTo(v.getPstngStartDate()) >= 0 && currentTime.compareTo(v.getPstngEndDate()) <= 0)
            .filter(v -> specList == null || specList.isEmpty() || specList.stream().anyMatch(s -> !"Y".equals(v.getDtlCdDesc()) || s.dtlCd().equals(v.getDtlCd())))
            .findFirst().orElse(null);

        if (filteredInfo == null) {
            return null;
        }

        List<TermsDetail> details = filteredInfo.getDetails().stream()
            .filter(v -> v.getCdGroupId1().equals(request.expnsnStrVal1()) && v.getCdGroupId2().equals(request.expnsnStrVal2()))
            .toList();

        TermsDetailContent filteredContent = null;
        for (TermsDetail detail : details) {
            List<TermsDetailContent> contents = detail.getContents();
            filteredContent = contents.stream()
                .filter(v -> v.getDocVer().equals(request.docVer()))
                .filter(v -> "Y".equals(v.getUseYn()) && !today.isBefore(v.getEventStartDt()) && !today.isAfter(v.getEventEndDt()))
                .findFirst().orElse(null);
        }

        return TermsCacheResponse.of(termsType, filteredInfo, Objects.requireNonNull(filteredContent).getDocVer(), filteredContent.getDocContent(), request.specTermsList());
    }
}
