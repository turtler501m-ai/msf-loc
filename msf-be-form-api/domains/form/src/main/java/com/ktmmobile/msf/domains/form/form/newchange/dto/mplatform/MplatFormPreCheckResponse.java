package com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.ktmmobile.msf.domains.form.common.mplatform.dto.MplatformBase;

@Data
@EqualsAndHashCode(callSuper = false)
public class MplatFormPreCheckResponse extends MplatformBase {

    private String osstOrdNo; //OSST 오더 번호
    private String rsltCd; //처리 결과
    private String rsltMsg; //처리 결과 메시지
}
