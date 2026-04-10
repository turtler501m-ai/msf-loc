package com.ktmmobile.msf.domains.form.form.appform_d.application.fieldmapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.MspCdResponse;
import com.ktmmobile.msf.domains.form.form.appform_d.domain.entity.MspCd;

@Mapper
public interface MspCdFieldMapper {

    MspCdFieldMapper INSTANCE = Mappers.getMapper(MspCdFieldMapper.class);

    MspCdResponse toResponse(MspCd nmcpCd);
}
