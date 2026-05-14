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
public interface BaseInfoMapper {

    // @Mapping(target = "mvnoOrdNo", source = "mvnoOrdNo")                 // MVNO 오더 번호 (필수)
    @Mapping(target = "slsCmpnCd", source = "shopCd")                 // 판매회사코드 (필수)
    // @Mapping(target = "custNo", source = "custNo")                       // 고객번호
    // @Mapping(target = "svcContId", source = "svcContId")                 // 계약번호 (필수)
    @Mapping(target = "tlphNo", source = "trnsMobileNo")                       // 전화번호 (필수)

    @Mapping(target = "mcnStatRsnCd", source = "mcnStatRsnCd")           // 명변 사유코드 (필수)
    @Mapping(target = "usimSuccYn", source = "usimSuccYn")               // USIM 승계 여부 (eSim계약 명의변경시 승계 Y 고정) (필수)
    @Mapping(target = "iccId", source = "iccId")                         // USIM 일련번호 (eSim계약 명의변경시 NULL로 연동) C

    @Mapping(target = "realUseCustNm", source = "cstmrJuridicalUserNm")         // 실사용고객명 C
    @Mapping(target = "realUseCustBrthDate", source = "cstmrJuridicalBirth") // 실사용자 생년월일 C
    MplatFormFMC0InfoRequest.BaseInfo toBaseInfo(MsfRequestNameChgVo source);
}
