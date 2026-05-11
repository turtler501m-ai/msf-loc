package com.ktmmobile.msf.domains.form.form.newchange.dto;

import java.util.List;

import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestAdditionVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestAgentVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestBillReqVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestCstmrVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestDvcChgVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestMoveVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestSaleVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestVo;
import com.ktmmobile.msf.domains.form.form.newchange.field.NewChangeFieldMapper;

public record MsfRequestRecord(
    MsfRequestVo msfRequestVo,
    MsfRequestCstmrVo msfRequestCstmrVo,
    MsfRequestAgentVo msfRequestAgentVo,
    MsfRequestSaleVo msfRequestSaleVo,
    MsfRequestBillReqVo msfRequestBillReqVo,
    MsfRequestMoveVo msfRequestMoveVo,
    MsfRequestDvcChgVo msfRequestDvcChgVo,
    List<MsfRequestAdditionVo> msfRequestAdditionVo
) {

    public static MsfRequestRecord requestToRecord(NewChangeInfoRequest request) {
        return new MsfRequestRecord(
            NewChangeFieldMapper.INSTANCE.toMsfRequestVo(request),
            NewChangeFieldMapper.INSTANCE.toMsfRequestCstmrVo(request),
            NewChangeFieldMapper.INSTANCE.toMsfRequestAgentVo(request),
            NewChangeFieldMapper.INSTANCE.toMsfRequestSaleVo(request),
            NewChangeFieldMapper.INSTANCE.toMsfRequestBillReqVo(request),
            NewChangeFieldMapper.INSTANCE.toMsfRequestMoveVo(request),
            NewChangeFieldMapper.INSTANCE.toMsfRequestDvcChgVo(request),
            NewChangeFieldMapper.INSTANCE.toMsfRequestAdditionVo(request.getAdditionList())
        );
    }
}



