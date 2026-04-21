package com.ktmmobile.msf.domains.shared.form.common.adapter.repository.mybatis.smartform.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.shared.form.common.application.dto.TermsCondition;
import com.ktmmobile.msf.domains.shared.form.common.domain.entity.TermsGroup;
import com.ktmmobile.msf.domains.shared.form.common.domain.entity.TermsItem;

@Mapper
public interface TermsMapper {
    TermsGroup selectTermsGroup(TermsCondition condition);

    List<TermsItem> selectListTerms(TermsCondition condition);
}
