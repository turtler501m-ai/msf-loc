package com.ktmmobile.msf.domains.form.form.ownerchange.field;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.ktmmobile.msf.domains.form.common.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.domains.form.common.dto.MplatFormCommonRequest;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MplatFormFMC0FrmRequest;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MplatFormFMC0InfoRequest;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MplatFormFMC0InfoResponse;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MplatFormFMP0FrmRequest;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MplatFormFMP0InfoRequest;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MplatFormFMP0InfoResponse;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MplatFormMC0InfoRequest;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MplatFormMP0InfoRequest;
import com.ktmmobile.msf.domains.form.form.common.vo.McpCustRequestChangeVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpCustRequestMstVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpCustRequestNameChgAgentVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpCustRequestNameChgVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestAgentVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestBillReqVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestClauseVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestCstmrVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestDocVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestJoinFormVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestNameChgVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestRecVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestSaleVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestVo;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeFormDetailRequest;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeFormInfoResponse;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeInitFormInfoResponse;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeValidationRequest;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeValidationResponse;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OwnerChangeFieldMapper {

    //신청서 저장 (INSERT / UPDATE)
    //NewChangeInfoRequest ~> MSF_REQUEST
    MsfRequestVo toMsfRequestVo(MsfRequestNameChgVo request); //MSF_REQUEST

    //NewChangeInfoRequest ~> MSF_REQUEST_CSTMR
    MsfRequestCstmrVo toMsfRequestCstmrVo(MsfRequestNameChgVo request); //MSF_REQUEST

    //NewChangeInfoRequest ~> MSF_REQUEST_AGENT
    MsfRequestAgentVo toMsfRequestAgentVo(MsfRequestNameChgVo request); //MSF_REQUEST

    //NewChangeInfoRequest ~> MSF_REQUEST_BILL_REQ
    @Mapping(target = "reqAccountRelTypeCd", source = "othersPaymentRelTypeCd")
    MsfRequestBillReqVo toMsfRequestBillReqVo(MsfRequestNameChgVo request); //MSF_REQUEST

    //NewChangeInfoRequest ~> MSF_REQUEST_MOVE_TEMP
    // MsfRequestMoveVo toMsfRequestMoveVo(MsfRequestNameChgVo request); //MSF_REQUEST

    //NewChangeInfoRequest ~> MSF_REQUEST_DVC_CHG_TEMP
    // MsfRequestDvcChgVo toMsfRequestDvcChgVo(MsfRequestNameChgVo request); //MSF_REQUEST

    //NewChangeInfoRequest ~> MSF_REQUEST_ADDITION_TEMP
    // MsfRequestAdditionVo toMsfRequestAdditionVo(MsfRequestNameChgVo request);


    @Mapping(target = "baseInfo", source = ".")
    @Mapping(target = "rcvCustInfo", source = ".")
    @Mapping(target = "rcvBillAcntInfo", source = ".")
    @Mapping(target = "prdcList", source = ".")
    MplatFormFMC0InfoRequest toMplatFormFMC0InfoRequest(MplatFormFMC0InfoResponse response);

    @Mapping(target = "cntpntCd", source = "cpntId")
    MplatFormFMC0FrmRequest toMplatFormFMC0FrmRequest(MplatFormFMC0InfoResponse response);

    @Mapping(target = "baseInfo", source = ".")
    @Mapping(target = "rcvCustInfo", source = ".")
    @Mapping(target = "rcvBillAcntInfo", source = ".")
    @Mapping(target = "prdcList", source = ".")
    MplatFormMC0InfoRequest toMplatFormMC0InfoRequest(MplatFormFMC0InfoResponse response);

    @Mapping(target = "baseInfo", source = ".")
        // @Mapping(target = "inFrmpapDto", source = ".")
    MplatFormFMP0InfoRequest toMplatFormFMP0InfoRequest(MplatFormFMP0InfoResponse request);

    MplatFormFMP0FrmRequest toMplatFormFMP0FrmRequest(MplatFormFMP0InfoResponse mp0response);

    @Mapping(target = "ncn", source = "contractNum")
    @Mapping(target = "ctn", source = "subscriberNo")
    @Mapping(target = "prodId", source = "soc")
    @Mapping(target = "prodNm", source = "socNm")
    OwnerChangeValidationResponse.OwnerChangeInfo toOwnerChangeInfo(McpUserCntrMngDto request);

    MsfRequestClauseVo toMsfRequestClauseVo(MsfRequestNameChgVo request);

    @Mapping(target = "cstmrMobileNo", source = "trnsTrnsfeMobileNo")
    @Mapping(target = "etcMemo", source = "memo")
    MsfRequestJoinFormVo toMsfRequestJoinFormVo(MsfRequestNameChgVo request);

    MsfRequestDocVo toMsfRequestDocVo(MsfRequestNameChgVo.RequestDocList request);

    MsfRequestRecVo toMsfRequestRecVo(MsfRequestNameChgVo request);

    @Mapping(target = "requestKey", source = "requestKey")
    @Mapping(target = "reqType", source = "reqType")
    @Mapping(target = "custNm", source = "cstmrNm")
    @Mapping(target = "mobileNo", source = "trnsMobileNo")
    @Mapping(target = "cstmrNativeRrn", source = "cstmrNativeRrn")
    @Mapping(target = "ncn", source = "ncn")
    @Mapping(target = "cstmrTypeCd", source = "cstmrTypeCd")
    @Mapping(target = "etcMobile", source = "cstmrReceiveTelNo")
    McpCustRequestMstVo toMcpCustRequestMstVo(MsfRequestNameChgVo request);

    McpCustRequestNameChgVo toMcpCustRequestNameChgVo(MsfRequestNameChgVo request);

    McpCustRequestNameChgAgentVo toMcpCustRequestNameChgAgentVo(MsfRequestNameChgVo request);

    @Mapping(source = "jrdclAgentNm", target = "minorAgentNm")
    @Mapping(source = "jrdclAgentRrn", target = "minorAgentRrn")
    @Mapping(source = "jrdclAgentRelTypeCd", target = "jrdclAgentRelTypeCd") // 필드명 동일
    @Mapping(source = "jrdclAgentTelNo", target = "minorAgentTelNo")
    McpCustRequestNameChgAgentVo toMcpCustRequestNameChgAgentGovernVo(MsfRequestNameChgVo request);

    MplatFormCommonRequest toMplatFormCommonRequest(OwnerChangeValidationRequest request);

    MplatFormCommonRequest toOwnerChangeFormDetailRequest(OwnerChangeFormDetailRequest request);

    @Mapping(target = "baseInfo", source = ".")
    MplatFormMP0InfoRequest toMplatFormMP0InfoRequest(MplatFormFMP0InfoResponse response);

    // =========================================================================
    // 1. 양도인 정보 매핑 (trCustomer)
    // =========================================================================
    @Mapping(target = "trCustomer", source = "ownerChangeFormInfoResponse")
    // =========================================================================
    // 2. 양수인 정보 매핑 (teCustomer) - 실제 DTO 변수명에 100% 동기화
    // =========================================================================
    @Mapping(target = "teCustomer", source = "ownerChangeFormInfoResponse")
    // =========================================================================
    // 3. 납부 정보 매핑 (productPayment)
    // =========================================================================
    @Mapping(target = "productPayment", source = "ownerChangeFormInfoResponse")
    @Mapping(target = "planInfo", source = "ownerChangeFormInfoResponse")
    @Mapping(target = "usimInfo", source = "ownerChangeFormInfoResponse")
    @Mapping(target = "juridical", source = "ownerChangeFormInfoResponse")
    @Mapping(target = "memo", source = "memo")
    OwnerChangeInitFormInfoResponse toOwnerChangeInitFormInfoResponse(OwnerChangeFormInfoResponse ownerChangeFormInfoResponse);

    @Mapping(target = "planName2", source = "soc")
    @Mapping(target = "planNm", source = "socNm")
    @Mapping(target = "planAmt", source = "socBaseChrgAmt")
    OwnerChangeInitFormInfoResponse.PlanInfo toPlanInfo(OwnerChangeFormInfoResponse ownerChangeFormInfoResponse);

    @Mapping(target = "cstmrTypeCd", source = "trnsCstmrTypeCd")
    @Mapping(target = "cstmrNm", source = "trTrnsNm")
    @Mapping(target = "deviceChgTel1", source = "trDeviceChgTel1")
    @Mapping(target = "deviceChgTel2", source = "trDeviceChgTel2")
    @Mapping(target = "deviceChgTel3", source = "trDeviceChgTel3")
    @Mapping(target = "repName", source = "trMinorAgentNm")
    @Mapping(target = "repGender", source = "trMinorAgentGenderCd")
    @Mapping(target = "minorUserGender", source = "trMinorAgentGenderCd")
    @Mapping(target = "minorAgentRelTypeCd", source = "trMinorAgentRelTypeCd")
    @Mapping(target = "minorUserBirthDate", source = "trMinorAgentBirth")
    @Mapping(target = "minorAgentTelFnNo", source = "trMinorAgentTelFnNo")
    @Mapping(target = "minorAgentTelMnNo", source = "trMinorAgentTelMnNo")
    @Mapping(target = "minorAgentTelRnNo", source = "trMinorAgentTelRnNo")
    @Mapping(target = "userBirthDate", source = "trnsBirth")
    @Mapping(target = "userGender", source = "trnsGenderCd")
    @Mapping(target = "cstmrJuridicalRrn1", source = "trCstmrJuridicalRrn1")
    @Mapping(target = "cstmrJuridicalRrn2", source = "trCstmrJuridicalRrn2")
    @Mapping(target = "cstmrJuridicalBizNo1", source = "trCstmrJuridicalBizNo1")
    @Mapping(target = "cstmrJuridicalBizNo2", source = "trCstmrJuridicalBizNo2")
    @Mapping(target = "cstmrJuridicalBizNo3", source = "trCstmrJuridicalBizNo3")
    @Mapping(target = "cstmrJuridicalRepNm", source = "trCstmrJuridicalRepNm")
    @Mapping(target = "identityCertTypeCd", source = "trIdentityCertTypeCd")
    OwnerChangeInitFormInfoResponse.TrCustomer toTrCustomer(OwnerChangeFormInfoResponse ownerChangeFormInfoResponse);

    @Mapping(target = "country", source = "cstmrForeignerCountryCd")
    @Mapping(target = "visaType", source = "cstmrForeignerVisaNo")
    // 일반 전화번호 (소스 DTO 변수명 매칭)
    @Mapping(target = "telNo1", source = "cstmrTelFnNo")
    @Mapping(target = "telNo2", source = "cstmrTelMnNo")
    @Mapping(target = "telNo3", source = "cstmrTelRnNo")
    // 휴대폰 번호 (소스 DTO 변수명 매칭)
    @Mapping(target = "mobileNo1", source = "cstmrReceiveTelFnNo")
    @Mapping(target = "mobileNo2", source = "cstmrReceiveTelNmNo")
    @Mapping(target = "mobileNo3", source = "cstmrReceiveTelRnNo")
    // 주소 및 이메일
    @Mapping(target = "zipNo", source = "cstmrZipcd") // 소문자 cd 저격
    @Mapping(target = "address", source = "cstmrAdr")
    @Mapping(target = "detailAddress", source = "cstmrAdrDtl")
    // 대리인 관련 정보
    @Mapping(target = "agentGender", source = "minorAgentGenderCd")
    @Mapping(target = "minorUserGender", source = "cstmrNativeGenderCd")
    @Mapping(target = "realUserName", source = "cstmrJuridicalUserNm")
    @Mapping(target = "realUserBirthDate", source = "cstmrJuridicalBirth")
    @Mapping(target = "repName", source = "minorAgentNm")
    OwnerChangeInitFormInfoResponse.TeCustomer toTeCustomer(OwnerChangeFormInfoResponse ownerChangeFormInfoResponse);

    @Mapping(target = "minorAgentNm", source = "jrdclAgentNm")
    @Mapping(target = "agentBirthDate", source = "jrdclAgentRrn")
    // @Mapping(target = "agentGender", source = "")
    @Mapping(target = "minorAgentRelTypeCd", source = "jrdclAgentRelTypeCd")
    @Mapping(target = "minorAgentTelFnNo", source = "jrdclAgentTelFnNo")
    @Mapping(target = "minorAgentTelMnNo", source = "jrdclAgentTelMnNo")
    @Mapping(target = "minorAgentTelRnNo", source = "jrdclAgentTelRnNo")
    OwnerChangeInitFormInfoResponse.Juridical toJuridical(OwnerChangeFormInfoResponse ownerChangeFormInfoResponse);

    @Mapping(target = "socCode", source = "soc")
    MsfRequestSaleVo toMsfRequestSaleVo(MsfRequestNameChgVo request);

    @Mapping(target = "nameChangeNm", source = "cstmrNm")
    @Mapping(target = "nameChangeTelFn", source = "trnsMobileFnNo")
    @Mapping(target = "nameChangeTelMn", source = "trnsMobileMnNo")
    @Mapping(target = "nameChangeTelRn", source = "trnsMobileRnNo")
    @Mapping(target = "nameChangePinstallment", source = "remainPayDivCd")
    @Mapping(target = "requestKey", source = "requestKey")
    @Mapping(target = "rvisnId", source = "userId")
    McpCustRequestChangeVo toMcpCustRequestChangeVo(MsfRequestNameChgVo request);
}
