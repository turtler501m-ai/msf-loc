package com.ktmmobile.msf.domains.shared.form.common.complete.adapter.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.domains.shared.form.common.complete.adapter.repository.mybatis.smartform.mapper.FormCommonCompleteMapper;
import com.ktmmobile.msf.domains.shared.form.common.complete.application.dto.CompletedFormCondition;
import com.ktmmobile.msf.domains.shared.form.common.complete.application.port.out.FormCommonCompleteRepository;
import com.ktmmobile.msf.domains.shared.form.common.complete.domain.entity.CompletedRequestForm;

@RequiredArgsConstructor
@Repository
public class FormCommonCompleteRepositoryImpl implements FormCommonCompleteRepository {

    private final FormCommonCompleteMapper formcommonCompleteMapper;

    @Override public CompletedRequestForm getCompletedRequestForm(CompletedFormCondition condition) {
        return formcommonCompleteMapper.selectCompletedRequestForm(Long.parseLong(condition.requestKey()));
    }
}
