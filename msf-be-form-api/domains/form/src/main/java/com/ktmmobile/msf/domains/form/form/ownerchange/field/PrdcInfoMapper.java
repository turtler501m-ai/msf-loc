package com.ktmmobile.msf.domains.form.form.ownerchange.field;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.ktmmobile.msf.domains.form.common.mplatform.vo.MplatFormFMC0InfoRequest;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestNameChgVo;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PrdcInfoMapper {

    @Mapping(target = "prdcCd", source = "soc")           // 상품코드(요금제) (필수)
        // @Mapping(target = "prdcTypeCd", source = "prdcTypeCd") // 상품타입코드 (필수) P
        // @Mapping(target = "ftrNewParam", source = "ftrNewParam") // 상품 파람 C
    MplatFormFMC0InfoRequest.PrdcInfo toPrdcInfo(MsfRequestNameChgVo source);
}
