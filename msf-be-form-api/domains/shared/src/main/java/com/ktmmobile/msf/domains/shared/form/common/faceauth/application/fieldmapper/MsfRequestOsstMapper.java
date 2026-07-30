package com.ktmmobile.msf.domains.shared.form.common.faceauth.application.fieldmapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.entity.McpRequestOsst;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.entity.MsfRequestOsst;

@Mapper(componentModel = "spring")
public interface MsfRequestOsstMapper {
    MsfRequestOsstMapper INSTANCE = Mappers.getMapper(MsfRequestOsstMapper.class);

    @Mapping(target = "rsltDate", source = "rsltDt")
    @Mapping(target = "ifTypeCd", source = "ifType")
    @Mapping(target = "regDt", source = "regstDttm")
    MsfRequestOsst toMsfRequestOsst(McpRequestOsst mcpRequestOsst);
}
