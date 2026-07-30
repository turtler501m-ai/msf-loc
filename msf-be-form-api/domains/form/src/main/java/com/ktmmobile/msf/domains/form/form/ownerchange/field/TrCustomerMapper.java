package com.ktmmobile.msf.domains.form.form.ownerchange.field;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeFormInfoResponse;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeInitFormInfoResponse;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface TrCustomerMapper {

    @Mapping(target = "cstmrTypeCd", source = "trnsCstmrTypeCd")
    @Mapping(target = "cstmrNm", source = "trTrnsNm")
    @Mapping(target = "identityCertTypeCd", source = "trIdentityCertTypeCd")
    // 휴대폰 번호 매핑 (양도인 대상)
    @Mapping(target = "deviceChgTel1", source = "trDeviceChgTel1")
    @Mapping(target = "deviceChgTel2", source = "trDeviceChgTel2")
    @Mapping(target = "deviceChgTel3", source = "trDeviceChgTel3")
    // 법정대리인 / 위임 정보 매핑
    @Mapping(target = "minorAgentNm", source = "trMinorAgentNm")
    @Mapping(target = "minorAgentRelTypeCd", source = "trMinorAgentRelTypeCd")
    @Mapping(target = "minorAgentTelFnNo", source = "trMinorAgentTelFnNo")
    @Mapping(target = "minorAgentTelMnNo", source = "trMinorAgentTelMnNo")
    @Mapping(target = "minorAgentTelRnNo", source = "trMinorAgentTelRnNo")
    @Mapping(target = "userBirthDate", source = "trMinorAgentBirth")
    @Mapping(target = "userGender", source = "trMinorAgentGenderCd")
    OwnerChangeInitFormInfoResponse.TrCustomer toTrCustomer(OwnerChangeFormInfoResponse source);
}
