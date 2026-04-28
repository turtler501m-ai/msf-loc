package com.ktmmobile.msf.domains.shared.form.common.terms.application.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.domains.shared.form.common.terms.application.dto.TermsCondition;
import com.ktmmobile.msf.domains.shared.form.common.terms.application.dto.TermsContentRequest;
import com.ktmmobile.msf.domains.shared.form.common.terms.application.dto.TermsGroupResponse;
import com.ktmmobile.msf.domains.shared.form.common.terms.application.dto.TermsItemResponse;
import com.ktmmobile.msf.domains.shared.form.common.terms.application.port.in.TermsReader;
import com.ktmmobile.msf.domains.shared.form.common.terms.application.port.out.TermsRepository;
import com.ktmmobile.msf.domains.shared.form.common.terms.domain.entity.TermsContent;
import com.ktmmobile.msf.domains.shared.form.common.terms.domain.entity.TermsGroup;
import com.ktmmobile.msf.domains.shared.form.common.terms.domain.entity.TermsItem;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TermsService implements TermsReader {

    private final TermsRepository termsRepository;

    @Override public TermsGroupResponse getListTerms(TermsCondition condition) {

        TermsGroup termsGroup = termsRepository.getTermsGroup(condition);
        if (termsGroup == null) {
            return null;
        }
        List<TermsItem> termsList = termsRepository.getListTerms(condition);

        List<TermsItemResponse> codes = termsList.stream().map(t -> TermsItemResponse.of(t, condition.specTermsList())).toList();

        log.info("===================================================");
        log.info("codes: {}", codes);
        log.info("===================================================");

        List<TermsContentRequest> termsContentRequests = termsList.stream().filter(item -> StringUtils.hasText(item.expnsnStrVal1()) && StringUtils.hasText(item.expnsnStrVal2())).map(
            v -> {
                if (condition.specTermsList() == null || condition.specTermsList().isEmpty()) {
                    return TermsContentRequest.of(v, null);
                } else {
                    Optional<TermsCondition.SpecTerms> specRequest = condition.specTermsList().stream().filter(spec -> spec.code().equals(v.dtlCd()))
                        .findFirst();
                    return specRequest.map(specTerms -> TermsContentRequest.of(v, specTerms)).orElseGet(() -> TermsContentRequest.of(v, null));
                }
            }
        ).toList();

        log.info("===================================================");
        log.info("termsContentRequests: {}", termsContentRequests);
        log.info("===================================================");

        List<TermsContent> contentList = termsRepository.getListTermsContent(termsContentRequests);

        codes = codes.stream().map(item -> {
            TermsContent content = contentList.stream().filter(cont -> Objects.equals(cont.expnsnStrVal1(), item.termsGroupCd()) && Objects.equals(
                cont.expnsnStrVal2(),
                item.termsItemCd())).findFirst().orElse(null);
            if (content != null) {
                return item.withVersion(content.docVer()).withContent(content.docContent());
            }
            return item;
        }).toList();



        TermsGroupResponse group = TermsGroupResponse.of(termsGroup);
        group = group.withCodes(codes);

        return group;
    }
}
