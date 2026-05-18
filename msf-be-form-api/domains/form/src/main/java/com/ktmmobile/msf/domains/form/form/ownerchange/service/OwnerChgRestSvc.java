package com.ktmmobile.msf.domains.form.form.ownerchange.service;

import jakarta.validation.Valid;

import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestNameChgVo;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeFormDetailRequest;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeSaveResponse;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeValidationRequest;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeValidationResponse;

public interface OwnerChgRestSvc {

    // 명의 변경 가입 가능 유효성 체크
    OwnerChangeValidationResponse ownerChangeValidation(OwnerChangeValidationRequest request);

    // 명의 변경 작성 완료
    OwnerChangeSaveResponse ownerChangeFormSave(MsfRequestNameChgVo request);

    // 명의 변경 처리
    OwnerChangeSaveResponse ownerChangeProcess(@Valid MsfRequestNameChgVo request);

    // 명의 변경 신청서 상세 조회
    OwnerChangeSaveResponse ownerChangeFormGet(@Valid OwnerChangeFormDetailRequest request);
}
