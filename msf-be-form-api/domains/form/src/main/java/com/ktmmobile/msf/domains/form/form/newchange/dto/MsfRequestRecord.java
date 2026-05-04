package com.ktmmobile.msf.domains.form.form.newchange.dto;

import com.ktmmobile.msf.domains.form.form.common.vo.*;
import com.ktmmobile.msf.domains.form.form.newchange.field.NewChangeFieldMapper;

public record MsfRequestRecord(
        MsfRequestVo msfRequestVo,
        MsfRequestAgentVo msfRequestAgentVo,
        MsfRequestCstmrVo msfRequestCstmrVo,
        MsfRequestSaleVo msfRequestSaleVo,
        MsfRequestBillReqVo msfRequestBillReqVo,
        MsfRequestMoveVo msfRequestMoveVo,
        MsfRequestDvcChgVo msfRequestDvcChgVo
) {
    public static MsfRequestRecord requestToRecord(NewChangeInfoRequest request) {
        return new MsfRequestRecord(
                NewChangeFieldMapper.INSTANCE.toMsfRequestVo(request),
                NewChangeFieldMapper.INSTANCE.toMsfRequestAgentVo(request),
                NewChangeFieldMapper.INSTANCE.toMsfRequestCstmrVo(request),
                NewChangeFieldMapper.INSTANCE.toMsfRequestSaleVo(request),
                NewChangeFieldMapper.INSTANCE.toMsfRequestBillReqVo(request),
                NewChangeFieldMapper.INSTANCE.toMsfRequestMoveVo(request),
                NewChangeFieldMapper.INSTANCE.toMsfRequestDvcChgVo(request)
        );
    }
}
