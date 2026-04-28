package com.ktmmobile.msf.domains.form.form.newchange.field;

import com.ktmmobile.msf.commons.mybatis.annotation.AutoAuditing;
import com.ktmmobile.msf.domains.form.form.common.vo.*;
import com.ktmmobile.msf.domains.form.form.newchange.dto.MsfNewChangeInfoDto;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@AutoAuditing
@Mapper
public interface NewChangeFieldMapper {

    NewChangeFieldMapper INSTANCE = Mappers.getMapper(NewChangeFieldMapper.class);

    @Mapping(target = ".", source = "msfRequestVo")
    @Mapping(target = ".", source = "msfRequestCstmrVo")
    @Mapping(target = ".", source = "msfRequestAgentVo")
    @Mapping(target = ".", source = "msfRequestSaleVo")
    @Mapping(target = ".", source = "msfRequestBillReqVo")
    @Mapping(target = ".", source = "msfRequestMoveVo")
    @Mapping(target = "requestKey", source = "msfRequestVo.requestKey")
    @Mapping(target = "formTypeCd", source = "msfRequestCstmrVo.formTypeCd")
    @Mapping(target = "cstmrEmailAdr", source = "msfRequestCstmrVo.cstmrEmailAdr")
    NewChangeInfoResponse toNewChangeInfoResponse(MsfNewChangeInfoDto dto);

    MsfRequestVo toMsfRequestVo(NewChangeInfoRequest request); //MSF_REQUEST

    MsfRequestAgentVo toMsfRequestAgentVo(NewChangeInfoRequest request); //MSF_REQUEST

    MsfRequestCstmrVo toMsfRequestCstmrVo(NewChangeInfoRequest request); //MSF_REQUEST

    MsfRequestSaleVo toMsfRequestSaleVo(NewChangeInfoRequest request); //MSF_REQUEST

    MsfRequestBillReqVo toMsfRequestBillReqVo(NewChangeInfoRequest request); //MSF_REQUEST

    MsfRequestMoveVo toMsfRequestMoveVo(NewChangeInfoRequest request); //MSF_REQUEST

    MsfRequestAdditionVo toMsfRequestAdditionVo(NewChangeInfoRequest request);


    //MsfRequestOsstVo toMsfRequestOsstVo(NewChangeInfoRequest request); //MSF_REQUEST

}
