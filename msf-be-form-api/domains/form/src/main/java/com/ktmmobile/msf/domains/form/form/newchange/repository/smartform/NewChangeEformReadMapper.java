package com.ktmmobile.msf.domains.form.form.newchange.repository.smartform;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestAdditionVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestAgentVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestBillReqVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestCstmrVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestDvcChgVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestMoveVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestSaleVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestVo;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeRequest;

@Mapper
public interface NewChangeEformReadMapper {

    MsfRequestVo selectMsfRequestEformInfo(NewChangeRequest request);

    MsfRequestCstmrVo selectMsfRequestCstmrEformInfo(NewChangeRequest request);

    MsfRequestAgentVo selectMsfRequestAgentEformInfo(NewChangeRequest request);

    MsfRequestSaleVo selectMsfRequestSaleEformInfo(NewChangeRequest request);

    MsfRequestBillReqVo selectMsfRequestBillReqEformInfo(NewChangeRequest request);

    MsfRequestMoveVo selectMsfRequestMoveEformInfo(NewChangeRequest request);

    MsfRequestDvcChgVo selectMsfRequestDvcChgEformInfo(NewChangeRequest request);

    List<MsfRequestAdditionVo> selectMsfRequestAdditionEformInfo(NewChangeRequest request);
}
