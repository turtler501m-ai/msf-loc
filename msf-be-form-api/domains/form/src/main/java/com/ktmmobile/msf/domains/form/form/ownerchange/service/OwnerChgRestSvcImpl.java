package com.ktmmobile.msf.domains.form.form.ownerchange.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import com.ktmmobile.msf.domains.form.common.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMplatFormService;
import com.ktmmobile.msf.domains.form.common.repository.McpApiClient;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeJoinInfoResponse;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeType;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeValidationRequest;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeValidationResponse;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeWireUseTimeResponse;

@Service
@RequiredArgsConstructor
public class OwnerChgRestSvcImpl implements OwnerChgRestSvc {

    private final MsfMplatFormService msfMplatFormService;
    private final ObjectMapper objectMapper;
    private final McpApiClient mcpApiClient;

    @Override public OwnerChangeValidationResponse ownerChangeValidation(OwnerChangeValidationRequest request) {

        // 계약정보 유효성 체크(Y04)
        // OwnerChangePossibleValidationResponse possibleValidationResponse = null;
        // try {
        //     possibleValidationResponse = msfMplatFormService.commonMplatform(paramMap, "Y04", OwnerChangePossibleValidationResponse.class);
        // } catch (Exception e) {
        //
        // }
        //
        // if (!"00".equals(possibleValidationResponse.getResultCd())) {
        //     OwnerChangeType ownerChangeType = OwnerChangeType.fromCode(possibleValidationResponse.getResultCd());
        //     return OwnerChangeValidationResponse.builder().resultCd(ownerChangeType.getCode()).message(ownerChangeType.getMessage()).build();
        // }

        Map<String, String> params = new HashMap<String, String>();
        params.put("userNm", request.getUserNm());
        params.put("ctn", request.getCtn());

        // UserSessionDto userSessionDto1 = SessionUtils.getUserCookieBean();
        // if (userSessionDto1 != null) {
        //     params.put("customerId", userSessionDto1.getCustomerId());
        // }

        List<McpUserCntrMngDto> cntrList = mcpApiClient.post("/mypage/cntrListNmChg", params, List.class);

        if (cntrList.isEmpty()) {
            return OwnerChangeValidationResponse.builder().resultCd(OwnerChangeType.EMPTY.getCode())
                .message(OwnerChangeType.EMPTY.getMessage()).build();
        }

        McpUserCntrMngDto mcpUserCntrMngDto = cntrList.getFirst();

        // 정지회선일때
        if ("S".equals(mcpUserCntrMngDto.getSubStatus())) {
            return OwnerChangeValidationResponse.builder().resultCd(OwnerChangeType.STATUS_STOP.getCode())
                .message(OwnerChangeType.STATUS_STOP.getMessage()).build();
        }
        // 미납회원일때
        if ("D".equals(mcpUserCntrMngDto.getColDelinqStatus())) {
            return OwnerChangeValidationResponse.builder().resultCd(OwnerChangeType.NON_PAY.getCode())
                .message(OwnerChangeType.NON_PAY.getMessage()).build();
        }

        request.setCustId(mcpUserCntrMngDto.getCustId());
        request.setNcn(mcpUserCntrMngDto.getSvcCntrNo());
        request.setClntIp("127.0.0.7");
        request.setClntUsrId(mcpUserCntrMngDto.getUserid());
        HashMap<String, String> paramMap = objectMapper.convertValue(request, HashMap.class);

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
            ownerChangeJoinInfoResponse = msfMplatFormService.commonMplatform(paramMap, "X01", OwnerChangeJoinInfoResponse.class);
        } catch (Exception e) {

        }

        ownerChangeJoinInfoResponse.setNcn(mcpUserCntrMngDto.getSvcCntrNo());
        ownerChangeJoinInfoResponse.setCtn(mcpUserCntrMngDto.getUnSvcNo());
        ownerChangeJoinInfoResponse.setUserId(mcpUserCntrMngDto.getUserid());
        ownerChangeJoinInfoResponse.setCustId(mcpUserCntrMngDto.getCustId());


        return OwnerChangeValidationResponse.builder().resultCd(OwnerChangeType.SUCCESS.getCode()).message(OwnerChangeType.SUCCESS.getMessage())
            .response(ownerChangeJoinInfoResponse).build();
    }
}
