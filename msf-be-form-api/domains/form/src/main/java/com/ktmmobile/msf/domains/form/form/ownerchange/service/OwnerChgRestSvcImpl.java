package com.ktmmobile.msf.domains.form.form.ownerchange.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import com.ktmmobile.msf.domains.form.common.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.domains.form.common.exception.McpCommonJsonException;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMplatFormService;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MplatFormFMC0InfoRequest;
import com.ktmmobile.msf.domains.form.common.repository.McpApiClient;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestNameChgVo;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeFormDetailRequest;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeJoinInfoResponse;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeSaveResponse;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeType;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeValidationRequest;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeValidationResponse;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeWireUseTimeResponse;
import com.ktmmobile.msf.domains.form.form.ownerchange.field.OwnerChangeFieldMapper;

import static com.ktmmobile.msf.domains.form.common.constants.Constants.EVENT_CODE_NAME_CHG_PROCESS;
import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.F_BIND_EXCEPTION;

@Service
@RequiredArgsConstructor
public class OwnerChgRestSvcImpl implements OwnerChgRestSvc {

    private final MsfMplatFormService msfMplatFormService;
    private final ObjectMapper objectMapper;
    private final McpApiClient mcpApiClient;
    private final OwnerChangeFieldMapper ownerChangeFieldMapper;
    private final OwnerChgMsfSaveSvc ownerChgMsfSaveSvc;
    private final OwnerChgMcpSaveSvc ownerChgMcpSaveSvc;

    @Override
    public OwnerChangeValidationResponse ownerChangeValidation(OwnerChangeValidationRequest request) {

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
        request.setNcn(mcpUserCntrMngDto.getContractNum());
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

        ownerChangeJoinInfoResponse.setNcn(mcpUserCntrMngDto.getContractNum());
        ownerChangeJoinInfoResponse.setCtn(mcpUserCntrMngDto.getSubscriberNo());
        ownerChangeJoinInfoResponse.setCustId(mcpUserCntrMngDto.getCustId());
        ownerChangeJoinInfoResponse.setFstEsimYn(mcpUserCntrMngDto.getFstEsimYn());


        return OwnerChangeValidationResponse.builder().resultCd(OwnerChangeType.SUCCESS.getCode()).message(OwnerChangeType.SUCCESS.getMessage())
            .response(ownerChangeJoinInfoResponse).build();
    }

    @Override
    public OwnerChangeSaveResponse ownerChangeFormSave(MsfRequestNameChgVo request) {

        /*** 데이터 유효성 검사 ***/
        ownerChangeValidate(request);

        /*** msf 데이터 저장 ***/
        ownerChgMsfSaveSvc.save(request);

        /*** mcp 데이터 저장 ***/
        ownerChgMcpSaveSvc.save(request);

        /*** mcp 데이터 저장 종료 ***/


        /*** FMC0 호출 ***/
        MplatFormFMC0InfoRequest mplatFormFMC0InfoRequest = ownerChangeFieldMapper.toMplatFormFMC0InfoRequest(request);
        // OsstMcnChgPrecheckResponse OsstMcnChgPrecheckResponse = null;
        HashMap<String, Object> OsstMcnChgPrecheckResponse = null;

        try {
            // OsstMcnChgPrecheckResponse = msfMplatFormService.mplatformFMC0CallJson(mplatFormFMC0InfoRequest, EVENT_CODE_NAME_CHG_PRE_CHK);
        } catch (Exception e) {

        }

        /*** FMC0 호출 종료 ***/

        /************************/

        /************************/


        return OwnerChangeSaveResponse.builder().success(true).requestKey(request.getRequestKey()).build();
    }

    @Override public OwnerChangeSaveResponse ownerChangeProcess(MsfRequestNameChgVo request) {

        /*** FMP0 호출 ***/
        MplatFormFMC0InfoRequest mplatFormFMC0InfoRequest = ownerChangeFieldMapper.toMplatFormFMC0InfoRequest(request);
        // OsstMcnChgPrecheckResponse OsstMcnChgPrecheckResponse = null;
        HashMap<String, Object> OsstMcnChgResponse = null;

        try {
            OsstMcnChgResponse = msfMplatFormService.mplatformFMC0CallJson(mplatFormFMC0InfoRequest, EVENT_CODE_NAME_CHG_PROCESS);
        } catch (Exception e) {

        }
        /*** FMP0 호출 종료 ***/

        return null;
    }

    @Override public OwnerChangeSaveResponse ownerChangeFormGet(OwnerChangeFormDetailRequest request) {
        return null;
    }

    private void ownerChangeValidate(MsfRequestNameChgVo request) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("userNm", request.getTrnsNm());
        params.put("ctn", request.getTrnsMobileNo());

        // 양도인 회선 존재 확인
        List<McpUserCntrMngDto> cntrList = mcpApiClient.post("/mypage/cntrListNmChg", params, List.class);
        if (cntrList == null || cntrList.size() == 0) {
            throw new McpCommonJsonException("AUTH02", F_BIND_EXCEPTION);
        }

        McpUserCntrMngDto cntrMngDto = cntrList.get(0);
        // myNameChgReqDto.setMobileNo(cntrMngDto.getUnSvcNo());

        // 양도인 고객유형코드 변경 불가
        if (!cntrMngDto.getCstmrType().equals(request.getTrnsCstmrTypeCd())) {
            // throw new McpCommonJsonException("AUTH02", F_BIND_EXCEPTION);
        }

        // AS-IS 기준 개인만 변경 가능 확인 필요 (I:개인 B:법인 G: 공공)
        // if (!"I".equals(cntrMngDto.getCustomerType())) {
        //     throw new McpCommonJsonException("AUTH02", F_BIND_EXCEPTION);
        // }

        // if (StringUtils.isEmpty(request.getTrnsCstmrTypeCd()) && "I".equals(cntrMngDto.getCustomerType())) {
        // throw new McpCommonJsonException("AUTH02", F_BIND_EXCEPTION);
        // }

        // AS-IS필수값 | 세팅 NICE 본인인증 관련 정보 확인 필요
        // myNameChgReqDto.setGrOnlineAuthInfo("ReqNo:" + myNameChgReqDto.getGrReqSeq() + ", ResNo:" + myNameChgReqDto.getGrResSeq());
        // myNameChgReqDto.setOnlineAuthInfo("ReqNo:" + myNameChgReqDto.getReqSeq() + ", ResNo:" + myNameChgReqDto.getResSeq());
        // myNameChgReqDto.setSocNm(teCustomerInfo.getSocNm());
    }
}
