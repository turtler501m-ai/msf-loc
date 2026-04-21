package com.ktmmobile.msf.domains.shared.form.common.adapter.repository;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.domains.shared.form.common.adapter.repository.mybatis.msp.mapper.McpTermsMapper;
import com.ktmmobile.msf.domains.shared.form.common.adapter.repository.mybatis.smartform.mapper.TermsMapper;
import com.ktmmobile.msf.domains.shared.form.common.application.dto.TermsCondition;
import com.ktmmobile.msf.domains.shared.form.common.application.port.out.TermsRepository;
import com.ktmmobile.msf.domains.shared.form.common.domain.entity.TermsContent;
import com.ktmmobile.msf.domains.shared.form.common.domain.entity.TermsGroup;
import com.ktmmobile.msf.domains.shared.form.common.domain.entity.TermsItem;

@RequiredArgsConstructor
@Repository
public class TermsRepositoryImpl implements TermsRepository {

    private final TermsMapper termsMapper;
    private final McpTermsMapper mcpTermsMapper;

    @Override public TermsGroup getTermsGroup(TermsCondition condition) {
        return termsMapper.selectTermsGroup(condition);
    }

    @Override public List<TermsItem> getListTerms(TermsCondition condition) {
        return termsMapper.selectListTerms(condition);
    }

    @Override public List<TermsContent> getListTermsContent(List<TermsItem> itemList) {
        return mcpTermsMapper.selectListTermsContent(itemList);
    }
}
