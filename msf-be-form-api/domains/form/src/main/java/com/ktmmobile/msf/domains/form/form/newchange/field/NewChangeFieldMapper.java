package com.ktmmobile.msf.domains.form.form.newchange.field;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ktmmobile.msf.commons.mybatis.annotation.AutoAuditing;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestAdditionVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestAgentVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestBillReqVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestCstmrVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestDvcChgVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestMoveVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestSaleVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestVo;
import com.ktmmobile.msf.domains.form.form.newchange.dto.MsfRequestRecord;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeAdditionRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoResponse;

/**
 * 신규/변경 신청서
 * insert/update : from NewChangeInfoRequest to 신규/변경 VO
 * select        : from resultType 신규/변경 VO to NewChangeInfoResponse
 * 2026.04.
 */

@AutoAuditing
@Mapper
public interface NewChangeFieldMapper {

    NewChangeFieldMapper INSTANCE = Mappers.getMapper(NewChangeFieldMapper.class);

    //신청서 SELECT
    @Mapping(target = ".", source = "msfRequestVo")
    @Mapping(target = ".", source = "msfRequestCstmrVo")
    @Mapping(target = ".", source = "msfRequestAgentVo")
    @Mapping(target = ".", source = "msfRequestSaleVo")
    @Mapping(target = ".", source = "msfRequestBillReqVo")
    @Mapping(target = ".", source = "msfRequestMoveVo")
    @Mapping(target = ".", source = "msfRequestDvcChgVo")
    @Mapping(target = "additionList", source = "msfRequestAdditionVo")
    @Mapping(target = "requestKey", source = "msfRequestVo.requestKey")
    @Mapping(target = "cstmrEmailAdr", source = "msfRequestCstmrVo.cstmrEmailAdr")
    NewChangeInfoResponse toNewChangeInfoResponse(MsfRequestRecord record);

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
    //MsfRequestAdditionVo toMsfRequestAdditionVo(NewChangeInfoRequest request);
    List<MsfRequestAdditionVo> toMsfRequestAdditionVo(List<NewChangeAdditionRequest> additionList);


    //MsfRequestOsstVo toMsfRequestOsstVo(NewChangeInfoRequest request); //MSF_REQUEST

}
