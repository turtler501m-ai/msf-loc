package com.ktmmobile.msf.domains.form.form.newchange.dto;

import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestAgentVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestBillReqVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestCstmrVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestDvcChgVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestMoveVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestSaleVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestVo;
import com.ktmmobile.msf.domains.form.form.newchange.field.NewChangeMpFieldMapper;

public record MsfRequestMpRecord(
    MsfRequestVo msfRequestVo,
    MsfRequestCstmrVo msfRequestCstmrVo,
    MsfRequestAgentVo msfRequestAgentVo,
    MsfRequestSaleVo msfRequestSaleVo,
    MsfRequestBillReqVo msfRequestBillReqVo,
    MsfRequestMoveVo msfRequestMoveVo,
    MsfRequestDvcChgVo msfRequestDvcChgVo
    // List<MsfRequestAdditionVo> msfRequestAdditionVo
) {

    public static MsfRequestMpRecord requestToRecord(NewChangeInfoRequest request) {
        return new MsfRequestMpRecord(
            NewChangeMpFieldMapper.INSTANCE.toMsfRequestVo(request),
            NewChangeMpFieldMapper.INSTANCE.toMsfRequestCstmrVo(request),
            NewChangeMpFieldMapper.INSTANCE.toMsfRequestAgentVo(request),
            NewChangeMpFieldMapper.INSTANCE.toMsfRequestSaleVo(request),
            NewChangeMpFieldMapper.INSTANCE.toMsfRequestBillReqVo(request),
            NewChangeMpFieldMapper.INSTANCE.toMsfRequestMoveVo(request),
            NewChangeMpFieldMapper.INSTANCE.toMsfRequestDvcChgVo(request)
            //NewChangeEformFieldMapper.INSTANCE.toMsfRequestAdditionVo(request.getAdditionList())
        );
    }
}