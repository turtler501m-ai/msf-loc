package com.ktmmobile.msf.domains.shared.form.common.terms.adapter.repository.mybatis.smartform.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.shared.form.common.terms.application.dto.TermsCondition;
import com.ktmmobile.msf.domains.shared.form.common.terms.domain.entity.TermsGroup;
import com.ktmmobile.msf.domains.shared.form.common.terms.domain.entity.TermsItem;

@Mapper
public interface TermsMapper {
    TermsGroup selectTermsGroup(TermsCondition condition);

    List<TermsItem> selectListTerms(TermsCondition condition);
}
