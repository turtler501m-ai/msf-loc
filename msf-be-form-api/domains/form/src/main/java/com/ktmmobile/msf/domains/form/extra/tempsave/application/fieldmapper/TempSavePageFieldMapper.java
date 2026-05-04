package com.ktmmobile.msf.domains.form.extra.tempsave.application.fieldmapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.ktmmobile.msf.domains.form.extra.tempsave.application.dto.TempSavePageListResponse;
import com.ktmmobile.msf.domains.form.extra.tempsave.domain.vo.TempSaveVo;

@Mapper(componentModel = "spring")
public interface TempSavePageFieldMapper {
    TempSavePageFieldMapper INSTANCE = Mappers.getMapper(TempSavePageFieldMapper.class);

    TempSavePageListResponse toTempSavePageListResponse(TempSaveVo tempSaveVo);
}
