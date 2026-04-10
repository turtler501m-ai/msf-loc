package com.ktmmobile.msf.domains.form.form.appform_d.application.fieldmapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.NmcpCdResponse;
import com.ktmmobile.msf.domains.form.form.appform_d.domain.entity.NmcpCd;

@Mapper
public interface NmcpCdFieldMapper {

    NmcpCdFieldMapper INSTANCE = Mappers.getMapper(NmcpCdFieldMapper.class);

    NmcpCdResponse toResponse(NmcpCd nmcpCd);
}
