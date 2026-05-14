package com.ktmmobile.msf.domains.form.form.ownerchange.field;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.ktmmobile.msf.domains.form.common.mplatform.vo.MplatFormFMC0InfoRequest;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestAgentVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestBillReqVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestCstmrVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestNameChgVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestVo;

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
    MsfRequestBillReqVo toMsfRequestBillReqVo(MsfRequestNameChgVo request); //MSF_REQUEST

    //NewChangeInfoRequest ~> MSF_REQUEST_MOVE_TEMP
    // MsfRequestMoveVo toMsfRequestMoveVo(MsfRequestNameChgVo request); //MSF_REQUEST

    //NewChangeInfoRequest ~> MSF_REQUEST_DVC_CHG_TEMP
    // MsfRequestDvcChgVo toMsfRequestDvcChgVo(MsfRequestNameChgVo request); //MSF_REQUEST

    //NewChangeInfoRequest ~> MSF_REQUEST_ADDITION_TEMP
    // MsfRequestAdditionVo toMsfRequestAdditionVo(MsfRequestNameChgVo request);

    // NewChangeInfoRequest ~> OsstMcnChgPrecheckRequest
    @Mapping(target = "baseInfo", source = ".")
    @Mapping(target = "rcvCustInfo", source = ".")
    @Mapping(target = "rcvBillAcntInfo", source = ".")
    @Mapping(target = "prdcInfo", source = ".")
    @Mapping(target = "inFrmpapDto", source = ".")
    MplatFormFMC0InfoRequest toMplatFormFMC0InfoRequest(MsfRequestNameChgVo request);
}
