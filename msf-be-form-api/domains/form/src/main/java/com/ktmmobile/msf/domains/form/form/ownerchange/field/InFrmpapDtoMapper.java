package com.ktmmobile.msf.domains.form.form.ownerchange.field;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface InFrmpapDtoMapper {

    // @Mapping(target = "cntpntCd", source = "cntpntCd")   // 접점코드 (필수)
    // @Mapping(target = "frmpapId", source = "frmpapId")   // 서식지ID (필수)
    // MplatFormFMC0InfoRequest.InFrmpapDto toInFrmpapDto(MsfRequestNameChgVo source);
}
