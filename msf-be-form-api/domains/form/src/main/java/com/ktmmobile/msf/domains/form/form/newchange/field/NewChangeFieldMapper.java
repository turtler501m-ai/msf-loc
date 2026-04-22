package com.ktmmobile.msf.domains.form.form.newchange.field;

import com.ktmmobile.msf.commons.mybatis.annotation.AutoAuditing;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.vo.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@AutoAuditing
@Mapper
public interface NewChangeFieldMapper {

    NewChangeFieldMapper INSTANCE = Mappers.getMapper(NewChangeFieldMapper.class);

    MsfRequestVo toMsfRequestVo(NewChangeInfoRequest request); //MSF_REQUEST

    MsfRequestAgentVo toMsfRequestAgentVo(NewChangeInfoRequest request); //MSF_REQUEST

    MsfRequestCstmrVo toMsfRequestCstmrVo(NewChangeInfoRequest request); //MSF_REQUEST

    MsfRequestSaleVo toMsfRequestSaleVo(NewChangeInfoRequest request); //MSF_REQUEST

    MsfRequestBillReqVo toMsfRequestBillReqVo(NewChangeInfoRequest request); //MSF_REQUEST

}
