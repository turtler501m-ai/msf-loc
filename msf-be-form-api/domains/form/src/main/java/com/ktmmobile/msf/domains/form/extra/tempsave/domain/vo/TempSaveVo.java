package com.ktmmobile.msf.domains.form.extra.tempsave.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TempSaveVo {

    private String rowNum;
    private String requestKey;
    private String cretDt;
    private String openTypeNm;
    private String serviceTypeNm;
    private String cstmrTypeNm;
    private String modifyYn;
    private String cstmrNm;
}
