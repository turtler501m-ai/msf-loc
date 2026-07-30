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
public interface TeCustomerMapper {

    @Mapping(target = "country", source = "cstmrForeignerCountryCd")
    @Mapping(target = "visaType", source = "cstmrForeignerVisaNo")
    // 일반 전화번호 (cstmrTel -> telNo)
    @Mapping(target = "telNo1", source = "cstmrTelFnNo")
    @Mapping(target = "telNo2", source = "cstmrTelMnNo")
    @Mapping(target = "telNo3", source = "cstmrTelRnNo")
    // 휴대폰 번호 (cstmrMobile -> mobileNo)
    @Mapping(target = "mobileNo1", source = "cstmrMobileFnNo")
    @Mapping(target = "mobileNo2", source = "cstmrMobileMnNo")
    @Mapping(target = "mobileNo3", source = "cstmrMobileRnNo")
    // 주소 및 이메일
    @Mapping(target = "zipNo", source = "cstmrZipcd")
    @Mapping(target = "address", source = "cstmrAdr")
    @Mapping(target = "detailAddress", source = "cstmrAdrDtl")
    @Mapping(target = "emailAddr1", source = "emailAddr1")
    @Mapping(target = "emailAddr2", source = "emailAddr2")
    // 대리인 정보
    @Mapping(target = "minorAgentNm", source = "minorAgentNm")
    @Mapping(target = "minorAgentRelTypeCd", source = "minorAgentRelTypeCd")
    @Mapping(target = "minorAgentTelFnNo", source = "minorAgentTelFnNo")
    @Mapping(target = "minorAgentTelMnNo", source = "minorAgentTelMnNo")
    @Mapping(target = "minorAgentTelRnNo", source = "minorAgentTelRnNo")
    @Mapping(target = "agentGender", source = "minorAgentGenderCd")
    @Mapping(target = "minorUserGender", source = "cstmrNativeGenderCd")
        // @Mapping(target = "deviceChgTel1", ignore = true)
        // @Mapping(target = "deviceChgTel2", ignore = true)
        // @Mapping(target = "deviceChgTel3", ignore = true)
        // @Mapping(target = "identityCertTypeCd", ignore = true)
        // @Mapping(target = "identityTypeCd", ignore = true)
        // @Mapping(target = "cstmrJuridicalRrn1", ignore = true)
        // @Mapping(target = "cstmrJuridicalRrn2", ignore = true)
        // @Mapping(target = "cstmrJuridicalBizNo1", ignore = true)
        // @Mapping(target = "cstmrJuridicalBizNo2", ignore = true)
        // @Mapping(target = "cstmrJuridicalBizNo3", ignore = true)
        // @Mapping(target = "cstmrJuridicalRepNm", ignore = true)
        // @Mapping(target = "repName", ignore = true)
        // @Mapping(target = "realUserName", ignore = true)
        // @Mapping(target = "agentBirthDate", ignore = true)
        // @Mapping(target = "teStayPeriod", ignore = true)
    OwnerChangeInitFormInfoResponse.TeCustomer toTeCustomer(OwnerChangeFormInfoResponse source);
}
