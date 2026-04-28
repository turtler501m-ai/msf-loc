package com.ktmmobile.msf.domains.form.form.ownerchange.service;

import java.util.HashMap;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import com.ktmmobile.msf.domains.form.common.mplatform.MsfMplatFormService;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeJoinInfoResponse;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangePossibleValidationResponse;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeType;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeValidationRequest;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeValidationResponse;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeWireUseTimeResponse;

@Service
@RequiredArgsConstructor
public class OwnerChgRestSvcImpl implements OwnerChgRestSvc {

    private final MsfMplatFormService msfMplatFormService;
    private final ObjectMapper objectMapper;

    @Override public OwnerChangeValidationResponse ownerChangeValidation(OwnerChangeValidationRequest request) {
        // 정지 회선, 미납 회선 명의 변경 불가(Y04)
        HashMap<String, String> paramMap = objectMapper.convertValue(request, HashMap.class);
        OwnerChangePossibleValidationResponse possibleValidationResponse = null;
        try {
            possibleValidationResponse = msfMplatFormService.commonMplatform(paramMap, "Y04", OwnerChangePossibleValidationResponse.class);
        } catch (Exception e) {

        }

        if (!"00".equals(possibleValidationResponse.getResultCd())) {
            OwnerChangeType ownerChangeType = OwnerChangeType.fromCode(possibleValidationResponse.getResultCd());
            return OwnerChangeValidationResponse.builder().resultCd(ownerChangeType.getCode()).message(ownerChangeType.getMessage()).build();
        }

        // 실사용자 90일 이상인 회선만 명의변경 가능(X83)
        OwnerChangeWireUseTimeResponse wireUseTimeResponse = null;
        try {
            wireUseTimeResponse = msfMplatFormService.commonMplatform(paramMap, "X83", OwnerChangeWireUseTimeResponse.class);
        } catch (Exception e) {

        }

        if (wireUseTimeResponse.getRealUseDayNum() < 90) {
            OwnerChangeType ownerChangeType = OwnerChangeType.fromCode("10");
            return OwnerChangeValidationResponse.builder().resultCd(ownerChangeType.getCode()).message(ownerChangeType.getMessage()).build();
        }

        // 가입자정보조회(X01)
        OwnerChangeJoinInfoResponse ownerChangeJoinInfoResponse = null;
        try {
            ownerChangeJoinInfoResponse = msfMplatFormService.commonMplatform(paramMap, "X83", OwnerChangeJoinInfoResponse.class);
        } catch (Exception e) {

        }

        return OwnerChangeValidationResponse.builder().resultCd(OwnerChangeType.SUCCESS.getCode()).message(OwnerChangeType.SUCCESS.getMessage())
            .response(ownerChangeJoinInfoResponse).build();
    }
}
