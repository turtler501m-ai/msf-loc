package com.ktmmobile.msf.domains.form.form.ownerchange.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import com.ktmmobile.msf.domains.form.common.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.domains.form.common.exception.McpCommonJsonException;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMplatFormOsstWebService;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMplatFormService;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MplatFormFMC0InfoRequest;
import com.ktmmobile.msf.domains.form.common.repository.McpApiClient;
import com.ktmmobile.msf.domains.form.form.common.repository.MsfRequestRepositoryImpl;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestNameChgVo;
import com.ktmmobile.msf.domains.form.form.newchange.dao.AppformDao;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeJoinInfoResponse;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeSaveResponse;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeType;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeValidationRequest;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeValidationResponse;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeWireUseTimeResponse;
import com.ktmmobile.msf.domains.form.form.ownerchange.field.OwnerChangeFieldMapper;
import com.ktmmobile.msf.domains.form.form.termination.repository.CancelPageRepositoryImpl;

import static com.ktmmobile.msf.domains.form.common.constants.Constants.EVENT_CODE_NAME_CHG_PRE_CHK;
import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.F_BIND_EXCEPTION;

@Service
@RequiredArgsConstructor
public class OwnerChgRestSvcImpl implements OwnerChgRestSvc {

    private final MsfMplatFormService msfMplatFormService;
    private final MsfMplatFormOsstWebService msfMplatFormOsstWebService;
    private final MsfRequestRepositoryImpl msfRequestRepository;
    private final CancelPageRepositoryImpl cancelPageRepository;
    private final ObjectMapper objectMapper;
    private final McpApiClient mcpApiClient;
    private final AppformDao appformDao;
    private final OwnerChangeFieldMapper ownerChangeFieldMapper;

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
        ownerChangeJoinInfoResponse.setFstEsimYn(mcpUserCntrMngDto.getFstEsimYn());


        return OwnerChangeValidationResponse.builder().resultCd(OwnerChangeType.SUCCESS.getCode()).message(OwnerChangeType.SUCCESS.getMessage())
            .response(ownerChangeJoinInfoResponse).build();
    }

    @Transactional
    @Override
    public OwnerChangeSaveResponse ownerChangeFormSave(MsfRequestNameChgVo request) {


        Map<String, String> params = new HashMap<String, String>();
        params.put("userNm", request.getTrnsNm());
        params.put("ctn", request.getTrnsMobileNo());

        // 양도인 회선 존재 확인
        List<McpUserCntrMngDto> cntrList = mcpApiClient.post("/mypage/cntrListNmChg", params, List.class);
        // if (cntrList == null || cntrList.size() == 0) {
        //     throw new McpCommonJsonException("AUTH02", F_BIND_EXCEPTION);
        // }

        McpUserCntrMngDto cntrMngDto = cntrList.get(0);
        // myNameChgReqDto.setMobileNo(cntrMngDto.getUnSvcNo());

        //조회한 고객유형과 양도인 고객유형 비교
        if (!cntrMngDto.getCstmrType().equals(request.getTrnsCstmrTypeCd())) {
            throw new McpCommonJsonException("AUTH02", F_BIND_EXCEPTION);
        }

        //I가 아닌 경우?
        if (!"I".equals(cntrMngDto.getCustomerType())) {
            // throw new McpCommonJsonException("AUTH02", F_BIND_EXCEPTION);
        }

        if (StringUtils.isEmpty(request.getTrnsCstmrTypeCd()) && "I".equals(cntrMngDto.getCustomerType())) {
            // throw new McpCommonJsonException("AUTH02", F_BIND_EXCEPTION);
        }

        // 계좌인증 실패로인해 신용카드 인증으로 진행한 경우, 계좌인증 관련 스텝 초기화
        // if ("Y".equals(myNameChgReqDto.getReqInfoChgYn()) && !"D".equals(myNameChgReqDto.getReqPayType())) {
        //     if (0 < certService.getModuTypeStepCnt("account", "1")) {
        //         CertDto certDto = new CertDto();
        //         certDto.setModuType("account");
        //         certDto.setCompType("G");
        //         certDto.setNcType("1");
        //         certService.getCertInfo(certDto);
        //     }
        // }

        // AS-IS필수값 | 세팅 NICE 본인인증 관련 정보 확인 필요
        // myNameChgReqDto.setGrOnlineAuthInfo("ReqNo:" + myNameChgReqDto.getGrReqSeq() + ", ResNo:" + myNameChgReqDto.getGrResSeq());
        // myNameChgReqDto.setOnlineAuthInfo("ReqNo:" + myNameChgReqDto.getReqSeq() + ", ResNo:" + myNameChgReqDto.getResSeq());
        // myNameChgReqDto.setSocNm(teCustomerInfo.getSocNm());

        long requestKey = cancelPageRepository.nextRequestKey();
        //
        // /*** msf 데이터 저장  ***/
        //
        // // 명의변경신청정보 저장
        // request.setRequestKey(requestKey);
        // msfRequestRepository.insertMsfRequestNameChg(request);
        // // 명의변경양도인정보 저장
        // msfRequestRepository.insertMsfRequestNameTrns(request);
        //
        // // 가입신청정보 저장
        // MsfRequestCstmrVo msfRequestCstmrVo = ownerChangeFieldMapper.toMsfRequestCstmrVo(request);
        // msfRequestRepository.insertMsfRequestCstmr(msfRequestCstmrVo);
        // // 가입신청대리인정보 저장(미성년자인 경우)
        // MsfRequestAgentVo msfRequestAgentVo = ownerChangeFieldMapper.toMsfRequestAgentVo(request);
        // msfRequestRepository.insertMsfRequestAgent(msfRequestAgentVo);
        // // 가입신청청구신청정보 저장
        // MsfRequestBillReqVo msfRequestBillReqVo = ownerChangeFieldMapper.toMsfRequestBillReqVo(request);
        // msfRequestRepository.insertMsfRequestBillReq(msfRequestBillReqVo);

        /*** msf 데이터 저장 종료  ***/

        /*** mcp 데이터 저장 ***/

        // logger.error("## 명의변경 신청 myNameChgReqDto : " + myNameChgReqDto);

        // String result = "";
        //
        // //예약번호
        // teCustomerInfo.setMcnResNo(appformDao.generateResNo());
        //
        // //안면인증
        // String fathTrgYn = teCustomerInfo.getFathTrgYn();
        // if ("Y".equals(fathTrgYn)) {
        //     // FathSessionDto fathSessionDto = fathService.validateFathSession();
        //     // myNameChgReqDto.setFathTransacId(fathSessionDto.getTransacId());
        //     // myNameChgReqDto.setFathCmpltNtfyDt(fathSessionDto.getCmpltNtfyDt());
        //     // myNameChgReqDto.setFathTelNo(myNameChgReqDto.getCstmrReceiveTelFn() + myNameChgReqDto.getCstmrReceiveTelMn() + myNameChgReqDto.getCstmrReceiveTelRn());
        //     //안면인증 관련 OSST 연동이력 MVNO_ORD_NO 컬럼데이터 '임시예약번호'를 -> '실제예약번호'로 업데이트
        //     // fathService.updateFathMcpRequestOsst(myNameChgReqDto.getMcnResNo());
        // }
        //
        // myNameChgDao.insertNmcpCustReqMst(myNameChgReqDto);
        // myNameChgDao.insertNmcpCustReqNameChg(myNameChgReqDto);
        //
        //
        // // 미성년자 신청일때
        // if ("NM".equals(myNameChgReqDto.getGrCstmrType()) || "NM".equals(myNameChgReqDto.getCstmrType())) {
        //     myNameChgDao.insertNmcpCustReqNameChgAgent(myNameChgReqDto);
        //     // 양도인 미성년자
        //     if ("NM".equals(myNameChgReqDto.getGrCstmrType())) {
        //         myNameChgReqDto.setGrOnlineAuthInfo("");
        //         myNameChgReqDto.setGrOnlineAuthType("");
        //         // 양수인 법정대리인 인증정보는 NMCP_CUST_REQUEST_MST 에 넣지 않는다.
        //         myNameChgDao.updateNmcpCustReqMst(myNameChgReqDto);
        //     }
        //     // 양수인 미성년자
        //     if ("NM".equals(myNameChgReqDto.getCstmrType())) {
        //         myNameChgReqDto.setOnlineAuthInfo("");
        //         myNameChgReqDto.setOnlineAuthType("");
        //         myNameChgReqDto.setSelfCertType("");
        //         myNameChgReqDto.setSelfIssuExprDt("");
        //         myNameChgReqDto.setSelfIssuNum("");
        //         myNameChgReqDto.setSelfCstmrCi("");
        //         // 양수인 법정대리인 인증정보는 NMCP_CUST_REQUEST_NAME_CHG 에 넣지 않는다.
        //         myNameChgDao.updateNmcpCustReqNameChg(myNameChgReqDto);
        //     }
        // }
        // result = "SUCCESS";
        //
        // return result;

        /*** mcp 데이터 저장 종료 ***/


        /*** FMC0 호출 ***/
        MplatFormFMC0InfoRequest mplatFormFMC0InfoRequest = ownerChangeFieldMapper.toMplatFormFMC0InfoRequest(request);
        // OsstMcnChgPrecheckResponse OsstMcnChgPrecheckResponse = null;
        HashMap<String, Object> OsstMcnChgPrecheckResponse = null;
        params.put("mcnResNo", "1140002316");

        // MplatFormFMC0InfoResponse mplatFormFMC0InfoResponse = mcpApiClient.post("/mPlatform/getXmlMessageMC0",
        //     params,
        //     MplatFormFMC0InfoResponse.class);
        try {
            OsstMcnChgPrecheckResponse = msfMplatFormService.mplatformFMC0CallJson(mplatFormFMC0InfoRequest, EVENT_CODE_NAME_CHG_PRE_CHK);
        } catch (Exception e) {

        }

        /*** FMC0 호출 종료 ***/

        /************************/

        /************************/


        return OwnerChangeSaveResponse.builder().success(true).requestKey(requestKey).build();
    }
}
