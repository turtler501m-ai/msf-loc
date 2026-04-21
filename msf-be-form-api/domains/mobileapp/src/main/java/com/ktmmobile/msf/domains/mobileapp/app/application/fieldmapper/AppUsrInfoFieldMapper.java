package com.ktmmobile.msf.domains.mobileapp.app.application.fieldmapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.ktmmobile.msf.domains.mobileapp.app.application.dto.AppInitResponse;
import com.ktmmobile.msf.domains.mobileapp.app.application.vo.UsrAppInfoVo;

@Mapper(componentModel = "spring")
public interface AppUsrInfoFieldMapper {
    AppUsrInfoFieldMapper INSTANCE = Mappers.getMapper(AppUsrInfoFieldMapper.class);

    AppInitResponse toAppInitResponse(UsrAppInfoVo usrAppInfoVo);
}
