package com.ktmmobile.msf.domains.form.form.newchange.field;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.vo.MsfRequestAgentVo;
import com.ktmmobile.msf.domains.form.form.newchange.vo.MsfRequestBillReqVo;
import com.ktmmobile.msf.domains.form.form.newchange.vo.MsfRequestCstmrVo;
import com.ktmmobile.msf.domains.form.form.newchange.vo.MsfRequestSaleVo;
import com.ktmmobile.msf.domains.form.form.newchange.vo.MsfRequestVo;

@Mapper
public interface NewChangeFieldMapper {

    NewChangeFieldMapper INSTANCE = Mappers.getMapper(NewChangeFieldMapper.class);

    MsfRequestVo toMsfRequestVo(NewChangeInfoRequest request); //MSF_REQUEST

    MsfRequestAgentVo toMsfRequestAgentVo(NewChangeInfoRequest request); //MSF_REQUEST

    MsfRequestCstmrVo toMsfRequestCstmrVo(NewChangeInfoRequest request); //MSF_REQUEST

    MsfRequestSaleVo toMsfRequestSaleVo(NewChangeInfoRequest request); //MSF_REQUEST

    MsfRequestBillReqVo toMsfRequestBillReqVo(NewChangeInfoRequest request); //MSF_REQUEST

}