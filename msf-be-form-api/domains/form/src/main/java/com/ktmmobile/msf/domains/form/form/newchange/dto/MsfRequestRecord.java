package com.ktmmobile.msf.domains.form.form.newchange.dto;

import com.ktmmobile.msf.domains.form.form.common.vo.*;
import com.ktmmobile.msf.domains.form.form.newchange.field.NewChangeFieldMapper;

import java.util.Collections;
import java.util.List;

public record MsfRequestRecord(
        MsfRequestVo msfRequestVo,
        MsfRequestAgentVo msfRequestAgentVo,
        MsfRequestCstmrVo msfRequestCstmrVo,
        MsfRequestSaleVo msfRequestSaleVo,
        MsfRequestBillReqVo msfRequestBillReqVo,
        MsfRequestMoveVo msfRequestMoveVo,
        MsfRequestDvcChgVo msfRequestDvcChgVo,
        List<MsfRequestAdditionVo> msfRequestAdditionVo
) {
    public static MsfRequestRecord requestToRecord(NewChangeInfoRequest request) {
        return new MsfRequestRecord(
                NewChangeFieldMapper.INSTANCE.toMsfRequestVo(request),
                NewChangeFieldMapper.INSTANCE.toMsfRequestAgentVo(request),
                NewChangeFieldMapper.INSTANCE.toMsfRequestCstmrVo(request),
                NewChangeFieldMapper.INSTANCE.toMsfRequestSaleVo(request),
                NewChangeFieldMapper.INSTANCE.toMsfRequestBillReqVo(request),
                NewChangeFieldMapper.INSTANCE.toMsfRequestMoveVo(request),
                NewChangeFieldMapper.INSTANCE.toMsfRequestDvcChgVo(request),
                Collections.singletonList(NewChangeFieldMapper.INSTANCE.toMsfRequestAdditionVo(request))
                /*request.getAdditionList().stream()
                        .map(NewChangeFieldMapper.INSTANCE::toMsfRequestAdditionVo)
                        .collect(Collectors.toList())*/
        );
    }
}



