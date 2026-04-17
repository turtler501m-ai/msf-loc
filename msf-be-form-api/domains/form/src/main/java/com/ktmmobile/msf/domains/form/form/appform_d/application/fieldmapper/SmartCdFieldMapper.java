package com.ktmmobile.msf.domains.form.form.appform_d.application.fieldmapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.SmartCdResponse;
import com.ktmmobile.msf.domains.form.form.appform_d.domain.entity.SmartCd;

@Mapper
public interface SmartCdFieldMapper {

    SmartCdFieldMapper INSTANCE = Mappers.getMapper(SmartCdFieldMapper.class);

    SmartCdResponse toResponse(SmartCd smartCd);
}
