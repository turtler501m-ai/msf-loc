package com.ktmmobile.msf.domains.form.form.appform_d.application.fieldmapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.JuoSubInfoResponse;
import com.ktmmobile.msf.domains.form.form.appform_d.domain.entity.JuoSubInfo;

@Mapper
public interface JuoSubInfoFieldMapper {

    JuoSubInfoFieldMapper INSTANCE = Mappers.getMapper(JuoSubInfoFieldMapper.class);

    JuoSubInfoResponse toResponse(JuoSubInfo juoSubInfo);

}
