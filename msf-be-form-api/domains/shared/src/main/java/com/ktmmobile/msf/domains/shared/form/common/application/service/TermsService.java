package com.ktmmobile.msf.domains.shared.form.common.application.service;

import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.domains.shared.form.common.application.dto.TermsCondition;
import com.ktmmobile.msf.domains.shared.form.common.application.dto.TermsGroupResponse;
import com.ktmmobile.msf.domains.shared.form.common.application.dto.TermsItemResponse;
import com.ktmmobile.msf.domains.shared.form.common.application.port.in.TermsReader;
import com.ktmmobile.msf.domains.shared.form.common.application.port.out.TermsRepository;
import com.ktmmobile.msf.domains.shared.form.common.domain.entity.TermsContent;
import com.ktmmobile.msf.domains.shared.form.common.domain.entity.TermsGroup;
import com.ktmmobile.msf.domains.shared.form.common.domain.entity.TermsItem;

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

        List<TermsItemResponse> codes = termsList.stream().map(TermsItemResponse::of).toList();

        List<TermsContent> contentList = termsRepository.getListTermsContent(termsList.stream().filter(item -> StringUtils.hasText(item.expnsnStrVal1()) && StringUtils.hasText(item.expnsnStrVal2())).toList());

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
