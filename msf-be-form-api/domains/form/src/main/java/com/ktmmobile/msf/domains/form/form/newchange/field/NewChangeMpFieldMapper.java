package com.ktmmobile.msf.domains.form.form.newchange.field;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestAgentVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestBillReqVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestCstmrVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestDvcChgVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestMoveVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestSaleVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestVo;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeMpHC0Response;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeMpPC0Response;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormFHC0InDtoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormFHC0InFrmpapDtoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormFHC0InPrdcDtoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormFPC0InDtoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormFPC0InFrmpapDtoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormFPC0InNpDtoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormHC0InDtoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormHC0InPrdcDtoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormPC0InDtoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormPC0InNpDtoRequest;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NewChangeMpFieldMapper {

    NewChangeMpFieldMapper INSTANCE = Mappers.getMapper(NewChangeMpFieldMapper.class);

    //신청서 SELECT
    //@Mapping(target = ".", source = "msfRequestVo")
    //@Mapping(target = ".", source = "msfRequestCstmrVo")
    //@Mapping(target = ".", source = "msfRequestAgentVo")
    //@Mapping(target = ".", source = "msfRequestSaleVo")
    //@Mapping(target = ".", source = "msfRequestBillReqVo")
    //@Mapping(target = ".", source = "msfRequestMoveVo")
    //@Mapping(target = ".", source = "msfRequestDvcChgVo")
    //  //@Mapping(target = "additionList", source = "msfRequestAdditionVo")
    //@Mapping(target = "requestKey", source = "msfRequestVo.requestKey")
    //@Mapping(target = "cstmrEmailAdr", source = "msfRequestCstmrVo.cstmrEmailAdr")
    //NewChangeMpInfoResponse toNewChangeMpInfoResponse(MsfRequestMpRecord record);
    //  //NewChangeInfoResponse toNewChangeInfoResponse(MsfRequestRecord record);
    //NewChangeInfoResponse toNewChangeInfoResponse(NewChangeRequest newChangeRequest);

    MplatFormFPC0InDtoRequest toMplatFormFPC0InDtoRequest(NewChangeMpPC0Response newChangeMpPC0Response);

    MplatFormFPC0InFrmpapDtoRequest toMplatFormFPC0InFrmpapDtoRequest(NewChangeMpPC0Response newChangeMpPC0Response);

    MplatFormFPC0InNpDtoRequest toMplatFormFPC0InNpDtoRequest(NewChangeMpPC0Response newChangeMpPC0Response);

    //기기변경 InDto
    MplatFormFHC0InDtoRequest toMplatFormFHC0InDtoRequest(NewChangeMpHC0Response newChangeMpHC0Response);

    //기기변경 InFrmpapDto
    MplatFormFHC0InFrmpapDtoRequest toMplatFormFHC0InFrmpapDtoRequest(NewChangeMpHC0Response newChangeMpHC0Response);

    //기기변경 InPrdcDto
    MplatFormFHC0InPrdcDtoRequest toMplatFormFHC0InPrdcDtoRequest(NewChangeMpHC0Response newChangeMpHC0Response);

    MplatFormPC0InDtoRequest toMplatFormPC0InDtoRequest(NewChangeMpPC0Response newChangeMpPC0Response);

    MplatFormPC0InNpDtoRequest toMplatFormPC0InNpDtoRequest(NewChangeMpPC0Response newChangeMpPC0Response);

    //기기변경 InDto
    MplatFormHC0InDtoRequest toMplatFormHC0InDtoRequest(NewChangeMpHC0Response newChangeMpHC0Response);

    //기기변경 InPrdcDto
    MplatFormHC0InPrdcDtoRequest toMplatFormHC0InPrdcDtoRequest(NewChangeMpHC0Response newChangeMpHC0Response);


    //MplatFormNU1Request toMplatFormNU1InDtoRequest(NewChangeNUInfoResponse newChangeNUInfoResponse);

    //MplatFormNU2Request toMplatFormNU2InDtoRequest(NewChangeNUInfoResponse newChangeNUInfoResponse);


    //신청서 저장 (INSERT / UPDATE)
    //NewChangeInfoRequest ~> MSF_REQUEST
    MsfRequestVo toMsfRequestVo(NewChangeInfoRequest request); //MSF_REQUEST

    //NewChangeInfoRequest ~> MSF_REQUEST_CSTMR
    MsfRequestCstmrVo toMsfRequestCstmrVo(NewChangeInfoRequest request); //MSF_REQUEST

    //NewChangeInfoRequest ~> MSF_REQUEST_AGENT
    MsfRequestAgentVo toMsfRequestAgentVo(NewChangeInfoRequest request); //MSF_REQUEST

    //NewChangeInfoRequest ~> MSF_REQUEST_SALE
    MsfRequestSaleVo toMsfRequestSaleVo(NewChangeInfoRequest request); //MSF_REQUEST

    //NewChangeInfoRequest ~> MSF_REQUEST_BILL_REQ
    MsfRequestBillReqVo toMsfRequestBillReqVo(NewChangeInfoRequest request); //MSF_REQUEST

    //NewChangeInfoRequest ~> MSF_REQUEST_MOVE_TEMP
    MsfRequestMoveVo toMsfRequestMoveVo(NewChangeInfoRequest request); //MSF_REQUEST

    //NewChangeInfoRequest ~> MSF_REQUEST_DVC_CHG_TEMP
    MsfRequestDvcChgVo toMsfRequestDvcChgVo(NewChangeInfoRequest request); //MSF_REQUEST

    //NewChangeInfoRequest ~> MSF_REQUEST_ADDITION_TEMP
    //List<MsfRequestAdditionVo> toMsfRequestAdditionVo(List<NewChangeAdditionRequest> additionList);
}
