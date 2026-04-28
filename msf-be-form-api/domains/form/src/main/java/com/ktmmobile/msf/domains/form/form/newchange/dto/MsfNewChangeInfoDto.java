package com.ktmmobile.msf.domains.form.form.newchange.dto;

import com.ktmmobile.msf.domains.form.form.common.vo.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MsfNewChangeInfoDto {
    private MsfRequestVo msfRequestVo;
    private MsfRequestCstmrVo msfRequestCstmrVo;
    private MsfRequestAgentVo msfRequestAgentVo;
    private MsfRequestSaleVo msfRequestSaleVo;
    private MsfRequestBillReqVo msfRequestBillReqVo;
    private MsfRequestMoveVo msfRequestMoveVo;
    private MsfRequestOsstVo msfRequestOsstVo;

}
