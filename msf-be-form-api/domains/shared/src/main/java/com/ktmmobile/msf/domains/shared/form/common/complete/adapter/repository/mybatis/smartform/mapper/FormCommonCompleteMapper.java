package com.ktmmobile.msf.domains.shared.form.common.complete.adapter.repository.mybatis.smartform.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.shared.form.common.complete.domain.entity.CompletedRequestForm;

@Mapper
public interface FormCommonCompleteMapper {

    CompletedRequestForm selectCompletedRequestForm(Long requestKey);
}
