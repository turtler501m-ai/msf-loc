package com.ktmmobile.msf.domains.form.form.servicechange.service;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import com.ktmmobile.msf.domains.form.common.dto.AuthSmsDto;
import com.ktmmobile.msf.domains.form.common.dto.McpFarPriceDto;
import com.ktmmobile.msf.domains.form.common.dto.McpIpStatisticDto;
import com.ktmmobile.msf.domains.form.common.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.domains.form.common.dto.MyCombinationResDto;
import com.ktmmobile.msf.domains.form.common.dto.UserSessionDto;
import com.ktmmobile.msf.domains.form.common.exception.McpCommonException;
import com.ktmmobile.msf.domains.form.common.exception.McpCommonJsonException;
import com.ktmmobile.msf.domains.form.common.exception.SelfServiceException;
import com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMplatFormService;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MoscCombDtlListOutDTO;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MoscCombDtlResDTO;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MpAddSvcInfoDto;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.RealtimePayInfoResponse;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpSocVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpSuspenCnlChgInVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpSuspenCnlPosInfoInVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpSuspenPosHisVO;
import com.ktmmobile.msf.domains.form.common.repository.McpApiClient;
import com.ktmmobile.msf.domains.form.common.service.FCommonSvc;
import com.ktmmobile.msf.domains.form.common.service.IpStatisticService;
import com.ktmmobile.msf.domains.form.common.util.DateTimeUtil;
import com.ktmmobile.msf.domains.form.common.util.SessionUtils;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ChargePlanChangeProcessRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ChargePlanChangeProcessResponse;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ChargePlanChangeReservationCancelRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ChargePlanChangeReservationCancelResponse;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ChargePlanChangeReservationResponse;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ChargePlanRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ChargePlanResponse;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ResumeRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ResumeSearchRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.productChangeCheckRequest;

import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;
import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.NOT_FOUND_DATA_EXCEPTION;
import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.NOT_FULL_MEMBER_EXCEPTION;

@Service
@RequiredArgsConstructor
public class SvcChgRestSvcImpl implements SvcChgRestSvc {

    private final MsfMypageSvc msfMypageSvc;
    private final MsfMplatFormService mPlatFormService;
    private final MsfMyCombinationSvc myCombinationSvc;
    private final ObjectMapper objectMapper;
    private final McpApiClient mcpApiClient;
    private final IpStatisticService ipStatisticService;
    private final FCommonSvc fCommonSvc;

//    @Value("${test.userId}")
    private String userId;
//    @Value("${test.cntrMobileNo}")
    private String cntrMobileNo;

    // 요금제 목록 조회
    @Override public List<String> selectChargePlan(ChargePlanRequest request) {
        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        if (userSessionDto != null) { // 취약성 318
            HashMap<String, String> params = new HashMap<>();
            params.put("userId", userId);
            cntrList = mcpApiClient.post("/mypage/cntrList", params, List.class);
        }

        // boolean chk = msfMypageSvc.checkUserType(request, cntrList, userSessionDto);
        // if (!chk) {
        //     ResponseSuccessDto responseSuccessDto = getMessageBox();
        //     model.addAttribute("responseSuccessDto", responseSuccessDto);
        //     return "/common/successRedirect";
        // }

        // 요금제 정보
        McpFarPriceDto mcpFarPriceDto = msfMypageSvc.selectFarPricePlan(request.getContractNum());
        if (mcpFarPriceDto == null) {
            throw new McpCommonException(NOT_FOUND_DATA_EXCEPTION);
        }


        //이용중인 부가서비스 조회
        MpAddSvcInfoDto getAddSvcInfo = new MpAddSvcInfoDto();
        try {
            getAddSvcInfo = mPlatFormService.getAddSvcInfoDto(request.getNcn(), request.getCtn(), request.getCustId());
        } catch (SocketTimeoutException e) {
            throw new McpCommonException(COMMON_EXCEPTION);
        } catch (SelfServiceException e) {
            throw new McpCommonException(COMMON_EXCEPTION);
        }

        if (getAddSvcInfo != null) {
            mcpFarPriceDto.setPromotionDcAmt(-getAddSvcInfo.getDiscountRate());
        }
        return List.of();
    }

    // 일시정지 해제 가능 여부(X28)
    @Override public MpSuspenCnlPosInfoInVO selectResumeState(ResumeSearchRequest request) {

        String ncn = request.ncn();
        String ctn = request.ctn();
        String custId = request.custId();
        MpSuspenCnlPosInfoInVO suspenCnlPosInfo = null;

        try {
            /** 28. 일시정지해제가능여부조회(X28)
             * */
            suspenCnlPosInfo = mPlatFormService.suspenCnlPosInfo(ncn, ctn, custId);

        } catch (SelfServiceException e) {
            // throw new McpCommonException(COMMON_EXCEPTION,redirectUri);
        } catch (SocketTimeoutException e) {
            // throw new McpCommonException(COMMON_EXCEPTION,redirectUri);
        }
        return suspenCnlPosInfo;
    }

    // 일시정지 이력 조회(X26)
    @Override public MpSuspenPosHisVO selectPauseHistory(ResumeSearchRequest request) {

        String ncn = request.ncn();
        String ctn = request.ctn();
        String custId = request.custId();
        MpSuspenPosHisVO suspenPosHisVO = null;

        /** 26. 일시정지이력조회(X26)
         * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
         * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
         * @param String termGubun : 조회기간 1:최근 1년, 'A' : 전체사용 기간
         * */
        try {
            suspenPosHisVO = mPlatFormService.suspenPosHis(ncn, ctn, custId, "1");
        } catch (Exception e) {

        }
        return suspenPosHisVO;
    }

    // 미납정지 해제 신청(X63)
    @Override public MpSuspenCnlPosInfoInVO unpaidResume(ResumeSearchRequest request) {
        return null;
    }

    // 부정사용 일시정지 해제 신청(X65)
    @Override public MpSuspenCnlPosInfoInVO invalidResume(ResumeSearchRequest request) {
        return null;
    }

    // 일시정지 해제 신청(X30)
    @Override public MpSuspenCnlChgInVO pauseResume(ResumeRequest request) {

        Map<String, Object> rtnMap = new HashMap<String, Object>();
        String strNcn = "";
        String cpPwdInsert = "";

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if (userSession == null || StringUtils.isEmpty(userSession.getUserId())) {
            // returnCode = "01";
            // message = "비정상적인 접근입니다.";
            // rtnMap.put("returnCode", returnCode);
            // rtnMap.put("message", message);
            // return rtnMap;
        }

        List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
        //정회원
        if (cntrList.size() <= 0) {
            // returnCode = "02";
            // message = "비정상적인 접근입니다.";
            // rtnMap.put("returnCode", returnCode);
            // rtnMap.put("message", message);
            // return rtnMap;
        }

        // valid로 빠짐
        String ncn = strNcn;
        if ("".equals(ncn)) {
            // returnCode = "03";
            // message = "비정상적인 접근입니다.";
            // rtnMap.put("returnCode", returnCode);
            // rtnMap.put("message", message);
            // return rtnMap;
        }

        String ctn = "";
        String custId = "";
        String contractNum = "";

        for (McpUserCntrMngDto mcpUserCntrMngDto: cntrList) {
            if (ncn.equals(mcpUserCntrMngDto.getSvcCntrNo())) {
                ctn = mcpUserCntrMngDto.getCntrMobileNo();
                custId = mcpUserCntrMngDto.getCustId();
                contractNum = mcpUserCntrMngDto.getContractNum();
                break;
            }
        }

        // Nice 사용하지 않음
        // NiceResDto niceResDto = (NiceResDto) session.getAttribute(SessionUtils.NICE_AUT_COOKIE);
        // //비정상 접근 확인
        // if (niceResDto == null) {
        //     returnCode = "04";
        //     message = "본인 인증한 정보가 틀립니다.";
        //     rtnMap.put("returnCode", returnCode);
        //     rtnMap.put("message", message);
        //     return rtnMap;
        // }

        // if (("LOCAL".equals(serverName) || "DEV".equals(serverName) || "STG".equals(serverName))) {
        //     logger.debug("인증 SKIP 처리");
        // } else {
        //
        //     if (!userSession.getName().equalsIgnoreCase(niceResDto.getName()) || !userSession.getBirthday()
        //         .equalsIgnoreCase(niceResDto.getBirthDate())) {
        //         returnCode = "05";
        //         message = "본인 인증한 정보가 틀립니다.";
        //         rtnMap.put("returnCode", returnCode);
        //         rtnMap.put("message", message);
        //         return rtnMap;
        //     }
        // }

        // 필요한지 모르겠음
        // 2. 최종 데이터 체크: step종료 여부, 계약번호, DI
        // String[] certKey = {"urlType", "stepEndYn", "contractNum", "dupInfo"};
        // String[] certValue = {"saveSuspenChgForm", "Y", contractNum, niceResDto.getDupInfo()};
        // Map<String, String> vldReslt = certService.vdlCertInfo("D", certKey, certValue);
        // if (!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
        //     returnCode = "STEP02";
        //     rtnMap.put("returnCode", returnCode);
        //     rtnMap.put("message", vldReslt.get("RESULT_DESC"));
        //     return rtnMap;
        // }
        // ================ STEP END ================

        String cpPwdInsert1 = cpPwdInsert;
        MpSuspenCnlChgInVO suspenCnlChgIn = null;
        try {
            //pwdType   비밀번호 타입 2   M   PP: 일시정지, CP: 개인정보 암호
            String pwdType = "PP";
            if ("".equals(cpPwdInsert1)) {
                cpPwdInsert1 = "12345678"; // 비밀번호 입력 하지 않으며  임의 번호 설정
            }
            /** 30. 일시정지해제신청(X30)
             * */
            // 오류나면 throw를 던지는지 확인해야함.
            suspenCnlChgIn = mPlatFormService.suspenCnlChgIn(ncn, ctn, custId, pwdType, cpPwdInsert1);
            // returnCode = AJAX_SUCCESS;
            // message = "성공적으로 처리 하였습니다.";

        } catch (SelfServiceException e) {
            // returnCode = "001";
            // message = getErrMsg(e.getMessage());
        } catch (SocketTimeoutException e) {
            // returnCode = "002";
            // message = getErrMsg(e.getMessage());
        }
        // 임시 리턴, 아무 값도 넘어오지 않을 수 있음
        return suspenCnlChgIn;
    }

    // 분실신고 해제 요청(X35)
    @Override public MpSuspenCnlPosInfoInVO lossReportResume(ResumeRequest request) {
        String message = "";
        String returnCode = "";
        Map<String, Object> rtnMap = new HashMap<String, Object>();

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if (userSession == null || StringUtils.isEmpty(userSession.getUserId())) {
            // returnCode = "01";
            // message = "비정상적인 접근입니다.";
            // rtnMap.put("returnCode",returnCode);
            // rtnMap.put("message",message);
            return null;
        }

        // 허용 건수 카운트
        int callCount = this.mPlatFormService.checkMpCallEventCount(userSession.getUserId(), "X35");
        if (callCount >= 10) {
            // rtnMap.put("returnCode", "99");
            // rtnMap.put("message","일일 허용 호출 건수를 초과 하였습니다.");
            return null;
        }

        List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
        //정회원
        if (cntrList.size() <= 0) {
            // returnCode = "02";
            // message = "비정상적인 접근입니다.";
            // rtnMap.put("returnCode", returnCode);
            // rtnMap.put("message", message);
            return null;
        }

        // @valid로 처리함
        String ncn = StringUtil.NVL(request.getNcn(), "");
        if ("".equals(ncn)) {
            // returnCode = "03";
            // message = "비정상적인 접근입니다.";
            // rtnMap.put("returnCode", returnCode);
            // rtnMap.put("message", message);
            return null;
        }

        String ctn = "";
        String custId = "";
        for (McpUserCntrMngDto mcpUserCntrMngDto: cntrList) {
            if (ncn.equals(mcpUserCntrMngDto.getSvcCntrNo())) {
                ctn = mcpUserCntrMngDto.getCntrMobileNo();
                custId = mcpUserCntrMngDto.getCustId();
                break;
            }
        }

        try {
            String pwdType = "PP";
            String strPwdNumInsert = "";
            //@valid로 처리
            if (!StringUtils.isEmpty(strPwdNumInsert)) {

                mPlatFormService.pcsLostCnlChg(ncn, ctn, custId, strPwdNumInsert, pwdType);
                returnCode = "00";
                message = "";
            } else {
                returnCode = "30";
                message = "파라미터가 비정상입니다.";
            }

        } catch (SelfServiceException e) {
            // returnCode = getErrCd(e.getMessage());//N:성공, S:시스템에서, E:Biz 에러
            // message = getErrMsg(e.getMessage());
        } catch (SocketTimeoutException e) {
            // returnCode = getErrCd("S");//N:성공, S:시스템에서, E:Biz 에러
            // message = getErrMsg(SOCKET_TIMEOUT_EXCEPTION);
        }

        return null;
    }

    // 아무나SOLO 결합 가능 여부 체크
    @Override public MpSuspenCnlPosInfoInVO combineSelfPossibleCheck(ResumeRequest request) {

        // AuthSmsDto rtnDto = SessionUtils.getAuthSmsBean(authSmsDto);
        AuthSmsDto rtnDto = null;

        // 인증 정보 처리 확인 & @Valid로 체크
        if (rtnDto == null || StringUtils.isBlank(rtnDto.getSvcCntrNo())) {
            throw new McpCommonJsonException("0001", ExceptionMsgConstant.NO_FAIL_SESSION_EXCEPTION);
        }

        // @Valid로 체크
        // if (!searchVO.getNcn().equals(rtnDto.getSvcCntrNo())) {
        //     throw new McpCommonJsonException("0002", ExceptionMsgConstant.NO_FAIL_SESSION_EXCEPTION);
        // }

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        // 확인 필요
        // 결합대상 구분값, 인증종류, 이름, 핸드폰번호, 계약번호
        // String[] certKey = {"urlType", "name", "mobileNo", "contractNum"};
        // String[] certValue = {"combineSelfAut", rtnDto.getSubLinkName(), rtnDto.getPhoneNum(), rtnDto.getSvcCntrNo()};  //rtnDto.getContractNum()
        //
        // Map<String, String> vldReslt = certService.vdlCertInfo("D", certKey, certValue);
        // if (!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
        //     rtnMap.put("RESULT_CODE", "-1");
        //     rtnMap.put("RESULT_MSG", vldReslt.get("RESULT_DESC"));
        //     return rtnMap;
        // }

        String customerSsn = rtnDto.getUnSSn();
        String subStatus = rtnDto.getMessage(); //회선 현재상태 A 사용중 , S 정지 , C 해지

        //비로그인 사용자 SMS인증은 .. PASS
        if (subStatus == null || subStatus.equals("")) {
            subStatus = "A";
        }

        // 공통 코드 조회로 수정 필요
        //법인 회선 여부
        // List<NmcpCdDtlDto> materCombineLineList = NmcpServiceUtils.getCodeList("MasterCombineLineInfo");
        // if (null != materCombineLineList && materCombineLineList.size() < 1) {
        //     rtnMap.put("RESULT_CODE", "0002");
        //     rtnMap.put("RESULT_MSG", "현재 서비스 이용이 불가합니다.<br> 나중에 다시 이용 바랍니다.");
        //     return rtnMap;
        // }

        //아무나 Solo 결합 여부 확인
        //이용중인 부가서비스 조회
        MpAddSvcInfoDto addSvcInfoDto = null;
        boolean isService = false;
        try {
            // 가입중인 부가서비스 조회(X20)
            addSvcInfoDto = mPlatFormService.getAddSvcInfoDto(rtnDto.getSvcCntrNo(), rtnDto.getCtn(), rtnDto.getCustId());
        } catch (SelfServiceException e) {
            rtnMap.put("RESULT_CODE", "-1");
            rtnMap.put("RESULT_MSG", e.getMessageNe());
            return null;
        } catch (SocketTimeoutException e) {
            rtnMap.put("RESULT_CODE", "-1");
            rtnMap.put("RESULT_MSG", ExceptionMsgConstant.SOCKET_TIMEOUT_EXCEPTION);
            return null;
        } catch (Exception e) {
            rtnMap.put("RESULT_CODE", "-1");
            rtnMap.put("RESULT_MSG", e.getMessage());
            return null;
        }

        if (addSvcInfoDto != null) {
            for (MpSocVO socVo: addSvcInfoDto.getList()) {
                if ("PL249Q800".equals(socVo.getSoc())) {
                    isService = true;
                    break;
                }
            }
        }

        if (isService) {
            rtnMap.put("RESULT_CODE", "0001");
            rtnMap.put("RESULT_MSG", "이미 신청 이력이 존재합니다. ");
            return null;
        }

        //3. 청소년 외국인 확인
        //외국인 청소년 구분
        if (!StringUtils.isNumeric(customerSsn) || customerSsn.length() < 7) {
            rtnMap.put("RESULT_CODE", "10008");
            rtnMap.put("RESULT_MSG", "주민번호 형식이 일치하지 않습니다. ");
            return null;
        }

        if (!subStatus.equals("A")) {
            //Kt M모바일 고객만 결합이 가능합니다.
            rtnMap.put("RESULT_CODE", "00003");
            rtnMap.put("RESULT_MSG", "현재 회선을 사용 중인 고객만 결합이 가능합니다.");
            return null;
        }

        //대상 요금제 체크
        String tempRateCd = rtnDto.getRateCd();
        //M전산 해당 요금제에 대한 결합 가능 여부 확인
        MyCombinationResDto myCombination = myCombinationSvc.selectMspCombRateMapp(tempRateCd);

        if (myCombination == null) {
            rtnMap.put("RESULT_CODE", "0004");
            rtnMap.put("RESULT_MSG", "해당 상품은 결합이 불가합니다. ");
            return null;
        } else if ("EMPTY".equals(myCombination.getrRateCd())) {
            rtnMap.put("RESULT_CODE", "0004");
            rtnMap.put("RESULT_MSG", "해당 상품은 결합이 불가합니다.(EMPTY) ");
            return null;
        }


        //아무나결합/가족결합 여부(X87)
        boolean isCombSvc = false;
        MoscCombDtlResDTO combSvcInfo = new MoscCombDtlResDTO();
        try {
            combSvcInfo = mPlatFormService.moscCombSvcInfoList(rtnDto.getCustId(), rtnDto.getSvcCntrNo(), rtnDto.getCtn());
        } catch (SelfServiceException e) {
            rtnMap.put("RESULT_CODE", "-1");
            rtnMap.put("RESULT_MSG", e.getMessageNe());
            return null;
        } catch (SocketTimeoutException e) {
            rtnMap.put("RESULT_CODE", "-1");
            rtnMap.put("RESULT_MSG", ExceptionMsgConstant.SOCKET_TIMEOUT_EXCEPTION);
            return null;
        } catch (Exception e) {
            rtnMap.put("RESULT_CODE", "-1");
            rtnMap.put("RESULT_MSG", e.getMessage());
            return null;
        }

        if (combSvcInfo != null && combSvcInfo.isSuccess()) {
            List<MoscCombDtlListOutDTO> combDtlList = combSvcInfo.getCombList();  //결합 리스트
            if (combDtlList != null && combDtlList.size() > 0) {
                isCombSvc = true;
                combDtlList.forEach(item -> item.setSvcNo("")); //개인 정보 ???
                rtnMap.put("RESULT_COMBINE_LIST", combDtlList);
            } else {
                rtnMap.put("RESULT_COMBINE_LIST", null);
            }
        } else {
            rtnMap.put("RESULT_COMBINE_LIST", null);
        }


        //마스터 결합 여부 저정
        // certValue = new String[] {"combineSelfCheck", rtnDto.getSubLinkName(), rtnDto.getPhoneNum(), rtnDto.getSvcCntrNo()};
        // certService.vdlCertInfo("C", certKey, certValue);
        //
        // rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        // rtnMap.put("IS_COMBIN", isCombSvc);
        // rtnMap.put("RESULT_SERVICE_NM", myCombination.getrRateNm());
        // rtnMap.put("RESULT_SERVICE_CD", myCombination.getrRateCd());

        return null;
    }

    // 실시간 요금 조회(X18)
    @Override public RealtimePayInfoResponse selectRealTimeCharge(ResumeRequest request) {

        HashMap<String, String> mapData = new HashMap<>();
        mapData.put("userId", userId);
        mapData.put("cntrMobileNo", cntrMobileNo);

        // 계약 정보 조회
        List<McpUserCntrMngDto> cntrList = mcpApiClient.post("/mypage/cntrList", mapData, List.class);

        if (cntrList.isEmpty()) {
            throw new McpCommonException(NOT_FULL_MEMBER_EXCEPTION);
        }

        McpUserCntrMngDto mcpUserCntrMngDto = cntrList.getFirst();
        request.setCtn(mcpUserCntrMngDto.getCntrMobileNo());
        request.setNcn(mcpUserCntrMngDto.getSvcCntrNo());
        request.setCustId(mcpUserCntrMngDto.getCustId());

        // 3. 실시간 요금 조회 (X18)
        RealtimePayInfoResponse realtimePayInfoResponse = null;

        try {
            HashMap<String, String> params = objectMapper.convertValue(request, HashMap.class);
            params.put("eventCd", "X18");
            realtimePayInfoResponse = mPlatFormService.commonMplatform(params, "X18", RealtimePayInfoResponse.class);
        } catch (SelfServiceException e) {
            // logger.info("Exception e : {}", e.getMessage());
        } catch (Exception e) {
            // logger.info("X18Error");
        }

        // String sumAmt = mpFarRealtimePayInfoVO.getSumAmt(); // 실시간 요금
        // sumAmt = (sumAmt == null) ? "-" : StringUtil.addComma(sumAmt);


        return realtimePayInfoResponse;
    }

    // 가입중인 요금제 조회(Y02)
    @Override public ChargePlanResponse selectActiveChargePlan(ChargePlanRequest request) {
        HashMap<String, String> mapData = new HashMap<>();
        mapData.put("userId", userId);
        mapData.put("cntrMobileNo", cntrMobileNo);

        // 계약 정보 조회
        List<McpUserCntrMngDto> cntrList = mcpApiClient.post("/mypage/cntrList", mapData, List.class);

        if (cntrList.isEmpty()) {
            throw new McpCommonException(NOT_FULL_MEMBER_EXCEPTION);
        }

        McpUserCntrMngDto mcpUserCntrMngDto = cntrList.getFirst();
        request.setCtn(mcpUserCntrMngDto.getCntrMobileNo());
        request.setNcn(mcpUserCntrMngDto.getSvcCntrNo());
        request.setCustId(mcpUserCntrMngDto.getCustId());

        ChargePlanResponse chargePlanResponse = null;

        try {
            HashMap<String, String> params = objectMapper.convertValue(request, HashMap.class);
            params.put("eventCd", "Y02");
            chargePlanResponse = mPlatFormService.commonMplatform(params, "Y02", ChargePlanResponse.class);
        } catch (SelfServiceException e) {
            // logger.info("Exception e : {}", e.getMessage());
        } catch (Exception e) {
            // logger.info("X18Error");
        }

        return chargePlanResponse;
    }

    // 상품변경사전체크(Y24)
    @Override public ChargePlanResponse productChangePreCheck(productChangeCheckRequest request) {

        HashMap<String, String> mapData = new HashMap<>();
        mapData.put("userId", userId);
        mapData.put("cntrMobileNo", cntrMobileNo);

        // 계약 정보 조회
        List<McpUserCntrMngDto> cntrList = mcpApiClient.post("/mypage/cntrList", mapData, List.class);

        if (cntrList.isEmpty()) {
            throw new McpCommonException(NOT_FULL_MEMBER_EXCEPTION);
        }

        McpUserCntrMngDto mcpUserCntrMngDto = cntrList.getFirst();
        request.setCtn(mcpUserCntrMngDto.getCntrMobileNo());
        request.setNcn(mcpUserCntrMngDto.getSvcCntrNo());
        request.setCustId(mcpUserCntrMngDto.getCustId());

        ChargePlanResponse chargePlanResponse = null;
        HashMap<String, String> params = objectMapper.convertValue(request, HashMap.class);

        try {
            params.put("eventCd", "Y24");
            // ncn, ctn, custId
            chargePlanResponse = mPlatFormService.commonMplatform(params, "Y24", ChargePlanResponse.class);
        } catch (SelfServiceException e) {
            // logger.info("Exception e : {}", e.getMessage());
        } catch (Exception e) {
            // logger.info("X18Error");
        }
        return chargePlanResponse;
    }

    // 상품변경처리(Y25?X19?)
    @Override public ChargePlanResponse productChangeProcess(productChangeCheckRequest request) {
        // Map<String, Object> rtnMap = new HashMap<String, Object>();
        //
        // HashMap<String, String> mapData = new HashMap<>();
        // mapData.put("userId", userId);
        // mapData.put("cntrMobileNo", cntrMobileNo);
        //
        // // 계약 정보 조회
        // List<McpUserCntrMngDto> cntrList = mcpApiClient.post("/mypage/cntrList", mapData, List.class);
        //
        // if (cntrList.isEmpty()) {
        //     throw new McpCommonException(NOT_FULL_MEMBER_EXCEPTION);
        // }
        //
        // McpUserCntrMngDto mcpUserCntrMngDto = cntrList.getFirst();
        // request.setCtn(mcpUserCntrMngDto.getCntrMobileNo());
        // request.setNcn(mcpUserCntrMngDto.getSvcCntrNo());
        // request.setCustId(mcpUserCntrMngDto.getCustId());
        //
        // /*
        //  * 번호유효성 체크 여부 확인
        //  */
        // // String sessionOtp = SessionUtils.getOtpInfo();
        // // if (!searchVO.getNcn().equals(sessionOtp)) {
        // //     throw new McpCommonJsonException("9997", F_BIND_EXCEPTION);
        // // }
        //
        // // 2-1현재 요금제 조회
        // McpFarPriceDto mcpFarPriceDto = mcpApiClient.post("/mypage/farPricePlan", request.getNcn(), McpFarPriceDto.class);
        // String nowPriceSocCode = mcpFarPriceDto.getPrvRateCd();
        //
        // //월 1회 초과 체크
        // Map<String, String> map = new HashMap<String, String>();
        // map.put("soc", mcpFarPriceDto.getPrvRateCd());
        // map.put("cntrNo", request.getNcn());
        //
        // //현재 요금제에 대한 상품 현행화 정보에서(MSP_JUO_FEATURE_INFO) 변경일 패치 /mypage/farPriceAddInfo
        // String getMonth = mcpApiClient.post("/mypage/farPriceAddInfo", map, String.class);
        //
        // // 현재 날짜 YYYYMM
        // Date nowDay = new Date();
        // String thisMonth = DateTimeUtil.changeFormat(nowDay, "yyyyMM");
        //
        // //이번 달 개통일자 체크
        // String isActivatedThisMonth = "N";
        //
        // // 개통일자가 이번 달에 해당하면 해당 계약은 요금제 변경 제한을 받지 않음(예약변경 가능, 즉시변경 불가능)
        // String lstComActvDate = mcpUserCntrMngDto.getLstComActvDate();
        //
        // if (lstComActvDate != null && lstComActvDate.substring(0, 6).equals(thisMonth)) {
        //     isActivatedThisMonth = "Y";
        // }
        //
        // //당월 개통자는 즉시변경 불가
        // if ("Y".equals(isActivatedThisMonth)) {
        //     rtnMap.put("RESULT_CODE", "9996");
        //     rtnMap.put("RESULT_MSG", "개통 당월에는 요금제 즉시 변경이 불가합니다.<br>예약변경을 이용하시거나 다음달에 요금제 변경을 진행해 주세요.");
        //     return null;
        // }
        //
        // //당월 요금제 변경자는 즉시변경 불가
        // if (thisMonth.equals(getMonth)) {
        //     rtnMap.put("RESULT_CODE", "9995");
        //     rtnMap.put("RESULT_MSG", "요금제는 월 1회만 변경 가능합니다.<br>예약변경을 이용하시거나 다음달에 요금제 변경을 진행해 주세요.");
        //     return null;
        // }
        //
        // // tobe 서비스로 소스 변환
        // // 해지할 부가 서비스 들 ?????데이득
        // // 1.부가 서비스 해지 처리
        // // 1-1. 부가 서비스 가입 여부
        // // 1-2. 부가 서비스 해지 처리
        // // 1-3. 현재에 대한 정보 NCN
        //
        // //이용중인 부가서비스 조회
        // MpAddSvcInfoDto getAddSvcInfo = new MpAddSvcInfoDto();
        // try {
        //     getAddSvcInfo = mPlatFormService.getAddSvcInfoDto(request.getNcn(), request.getCtn(), request.getCustId());
        // } catch (SocketTimeoutException e) {
        //     // rtnMap.put("RESULT_CODE", "-1");
        //     // rtnMap.put("RESULT_MSG", SOCKET_TIMEOUT_EXCEPTION);
        // } catch (SelfServiceException e) {
        //     // rtnMap.put("RESULT_CODE", "-2");
        //     // rtnMap.put("RESULT_MSG", ExceptionMsgConstant.MPLATFORM_RESPONEXML_EMPTY_EXCEPTION);
        // }
        // String prcsMdlInd = "GC" + DateTimeUtil.getFormatString("yyMMddHHmmss");
        //
        // List<McpUserCntrMngDto> closeSubList = mcpApiClient.post("/mypage/closeSubList", request.getNcn(), List.class);
        //
        // McpServiceAlterTraceDto serviceAlterTrace = new McpServiceAlterTraceDto();
        // serviceAlterTrace.setNcn(request.getNcn());
        // serviceAlterTrace.setContractNum(request.getNcn());
        // serviceAlterTrace.setSubscriberNo(request.getCtn());
        // serviceAlterTrace.setPrcsMdlInd(prcsMdlInd);
        // // serviceAlterTrace.settSocCode(toSocCode); // 이후 코드 값
        // serviceAlterTrace.setaSocCode(nowPriceSocCode); // 이전 코드 값
        // // serviceAlterTrace.settSocAmnt(paraAlterTrace.gettSocAmnt()); // 변경후 요금제 할인 월정액
        // // serviceAlterTrace.setaSocAmnt(paraAlterTrace.getaSocAmnt()); // 변경전 요금제 할인 월정액
        //
        //
        // /**
        //  *  방어 로직
        //  *  최근 60분 내 요금제 변경 성공 이력 확인
        //  */
        // if (0 < msfMypageSvc.checkAllreadPlanchgCount(serviceAlterTrace)) {
        //     rtnMap.put("RESULT_MSG", "요금제 변경이 되어 있습니다. <br/>잠시 후에 요금제 확인 하시기 바랍니다.[001]");
        //     return null;
        // }
        //
        //
        // McpServiceAlterTraceDto serviceAlterTraceSub = new McpServiceAlterTraceDto();
        // serviceAlterTraceSub.setNcn(request.getNcn());
        // serviceAlterTraceSub.setContractNum(request.getNcn());
        // serviceAlterTraceSub.setSubscriberNo(request.getCtn());
        // serviceAlterTraceSub.setPrcsMdlInd(prcsMdlInd);
        //
        // List<String> list = getAddSvcInfo.getList().stream().map(MpSocVO::getSoc).toList();
        //
        // closeSubList.stream().filter(close -> list.contains(close.getSoc())).forEach((closeSubInfo) -> {
        //     MyPageSearchDto searchVO = new MyPageSearchDto();
        //     searchVO.setNcn(request.getNcn());
        //     searchVO.setCtn(request.getCtn());
        //     searchVO.setCustId(request.getCustId());
        //     //부가 서비스 가입여부 확인
        //     RegSvcChgRes regSvcCanChgNe = null;
        //            /*
        //             * 1. 재처리는 ESB연동오류 (ITL_SYS_E0001) 에 한정한다.
        //               2. 재처리는 1회 한정한다
        //               3. 재처리에는 3초 gap time을 둔다.
        //               4. 재처리에 대한 이력은 이력테이블에 신규로 insert 하며, 기존 이력에update 하지는않는다.
        //            */
        //     String strParameter = "[" + closeSubInfo.getSocNm() + "]";
        //     for (int reTryCount = 0; reTryCount < 2; reTryCount++) {
        //         if (regSvcCanChgNe == null) {
        //             regSvcCanChgNe = mPlatFormService.moscRegSvcCanChgNeTrace(searchVO, closeSubInfo.getSocCode());
        //         } else if ("ITL_SYS_E0001".equals(regSvcCanChgNe.getResultCode())) {
        //             Thread.sleep(3000);
        //             //이력 저장
        //             serviceAlterTraceSub.setEventCode("X38");
        //             serviceAlterTraceSub.setTrtmRsltSmst("부가서비스 해지");
        //             serviceAlterTraceSub.settSocCode(closeSubInfo.getSocCode());
        //             serviceAlterTraceSub.setaSocCode("");
        //             serviceAlterTraceSub.setParameter(strParameter);
        //             serviceAlterTraceSub.setGlobalNo(regSvcCanChgNe.getGlobalNo());
        //             serviceAlterTraceSub.setRsltCd(regSvcCanChgNe.getResultCode());
        //             serviceAlterTraceSub.setPrcsSbst(regSvcCanChgNe.getSvcMsg());
        //             msfMypageSvc.insertServiceAlterTrace(serviceAlterTraceSub);
        //             regSvcCanChgNe = mPlatFormService.moscRegSvcCanChgNeTrace(searchVO, closeSubInfo.getSocCode());
        //         }
        //     }
        //
        //     if (!regSvcCanChgNe.isSuccess()) {
        //         rtnMap.put("RESULT_CODE", regSvcCanChgNe.getResultCode());
        //         rtnMap.put("RESULT_MSG", regSvcCanChgNe.getSvcMsg());
        //
        //         MspSmsTemplateMstDto mspSmsTemplateMstDto = fCommonSvc.getMspSmsTemplateMst(Constants.SMS_PRICE_CHANGE_FAIL_TEMPLATE_ID);
        //         //smsSvc.sendKakaoNoti( mspSmsTemplateMstDto.getSubject(), searchVO.getCtn(), mspSmsTemplateMstDto.getText(),mspSmsTemplateMstDto.getCallback(), mspSmsTemplateMstDto.getkTemplateCode(), KAKAO_SENDER_KEY);
        //         smsSvc.sendKakaoNoti(mspSmsTemplateMstDto.getSubject(), searchVO.getCtn(), mspSmsTemplateMstDto.getText(),
        //             mspSmsTemplateMstDto.getCallback(), mspSmsTemplateMstDto.getkTemplateCode(),
        //             KAKAO_SENDER_KEY, String.valueOf(Constants.SMS_PRICE_CHANGE_FAIL_TEMPLATE_ID));
        //
        //         //이력 저장
        //         serviceAlterTraceSub.setEventCode("X38");
        //         serviceAlterTraceSub.setTrtmRsltSmst("부가서비스 해지");
        //         serviceAlterTraceSub.settSocCode(closeSubInfo.getSocCode());
        //         serviceAlterTraceSub.setaSocCode("");
        //         serviceAlterTraceSub.setParameter(strParameter);
        //         serviceAlterTraceSub.setGlobalNo(regSvcCanChgNe.getGlobalNo());
        //         serviceAlterTraceSub.setRsltCd(regSvcCanChgNe.getResultCode());
        //         serviceAlterTraceSub.setPrcsSbst(regSvcCanChgNe.getSvcMsg());
        //         mypageService.insertServiceAlterTrace(serviceAlterTraceSub);
        //
        //         //결과 이력 저장
        //         serviceAlterTrace.setEventCode("FIN");
        //         serviceAlterTrace.setTrtmRsltSmst("FAIL");
        //         serviceAlterTrace.setParameter("부가서비스 해지 실패");
        //         mypageService.insertServiceAlterTrace(serviceAlterTrace);
        //
        //         //M 전산 이력 저장
        //         serviceAlterTrace.setSuccYn("N");
        //         mypageService.insertSocfailProcMst(serviceAlterTrace);
        //
        //         return rtnMap;
        //     } else {
        //         //이력 저장
        //         serviceAlterTraceSub.setEventCode("X38");
        //         serviceAlterTraceSub.setTrtmRsltSmst("부가서비스 해지");
        //         serviceAlterTraceSub.settSocCode(closeSubInfo.getSocCode());
        //         serviceAlterTraceSub.setaSocCode("");
        //         serviceAlterTraceSub.setParameter(strParameter);
        //         serviceAlterTraceSub.setGlobalNo(regSvcCanChgNe.getGlobalNo());
        //         serviceAlterTraceSub.setRsltCd("0000");
        //         serviceAlterTraceSub.setPrcsSbst(regSvcCanChgNe.getSvcMsg());
        //         mypageService.insertServiceAlterTrace(serviceAlterTraceSub);
        //     }
        // });
        //
        // //2.요금제 변경 처리
        // RegSvcChgRes regSvcChgSelf = null;
        // for (int reTryCount = 0; reTryCount < 2; reTryCount++) {
        //     if (regSvcChgSelf == null) {
        //         regSvcChgSelf = mPlatFormService.farPricePlanChgNeTrace(searchVO, toSocCode);
        //     } else if ("ITL_SYS_E0001".equals(regSvcChgSelf.getResultCode())) {
        //         Thread.sleep(3000);
        //         //이력 저장
        //         serviceAlterTraceSub.setEventCode("X19");
        //         serviceAlterTraceSub.setTrtmRsltSmst("요금제변경");
        //         serviceAlterTraceSub.settSocCode(toSocCode);
        //         serviceAlterTraceSub.setaSocCode(nowPriceSocCode);
        //         serviceAlterTraceSub.setGlobalNo(regSvcChgSelf.getGlobalNo());
        //         serviceAlterTraceSub.setRsltCd(regSvcChgSelf.getResultCode());
        //         serviceAlterTraceSub.setPrcsSbst(regSvcChgSelf.getSvcMsg());
        //         mypageService.insertServiceAlterTrace(serviceAlterTraceSub);
        //
        //         regSvcChgSelf = mPlatFormService.farPricePlanChgNeTrace(searchVO, toSocCode);
        //     }
        // }
        //
        // if (regSvcChgSelf.isSuccess()) {
        //     rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        //
        //     //이력 저장
        //     serviceAlterTraceSub.setEventCode("X19");
        //     serviceAlterTraceSub.setTrtmRsltSmst("요금제변경");
        //     serviceAlterTraceSub.settSocCode(toSocCode);
        //     serviceAlterTraceSub.setaSocCode(nowPriceSocCode);
        //     serviceAlterTraceSub.setGlobalNo(regSvcChgSelf.getGlobalNo());
        //     serviceAlterTraceSub.setRsltCd("0000");
        //     serviceAlterTraceSub.setPrcsSbst(regSvcChgSelf.getSvcMsg());
        //     mypageService.insertServiceAlterTrace(serviceAlterTraceSub);
        //
        //     //202312 wooki - MSP_DIS_APD(평생할인 부가서비스 기적용 대상) insert START
        //     String prmtId = mypageService.getChrgPrmtIdSocChg(toSocCode); //프로모션아이디 가져오기
        //     McpUserCntrMngDto apdDto = new McpUserCntrMngDto();
        //     apdDto.setPrmtId(prmtId); //위에서 조회한 prmtId set - prmtId는 있을수도 있고 없을수도 있음
        //     apdDto.setSocCode(toSocCode);
        //     apdDto.setContractNum(searchVO.getContractNum());
        //     mypageService.insertDisApd(apdDto); //MSP_DIS_APD insert
        //     //MSP_DIS_APD insert END
        //
        // } else {
        //     rtnMap.put("RESULT_CODE", regSvcChgSelf.getResultCode());
        //     rtnMap.put("RESULT_MSG", regSvcChgSelf.getSvcMsg());
        //
        //     MspSmsTemplateMstDto mspSmsTemplateMstDto = fCommonSvc.getMspSmsTemplateMst(Constants.SMS_PRICE_CHANGE_FAIL_TEMPLATE_ID);
        //     //smsSvc.sendKakaoNoti( mspSmsTemplateMstDto.getSubject(), searchVO.getCtn(), mspSmsTemplateMstDto.getText(),mspSmsTemplateMstDto.getCallback(), mspSmsTemplateMstDto.getkTemplateCode(), KAKAO_SENDER_KEY);
        //     smsSvc.sendKakaoNoti(mspSmsTemplateMstDto.getSubject(), searchVO.getCtn(), mspSmsTemplateMstDto.getText(),
        //         mspSmsTemplateMstDto.getCallback(), mspSmsTemplateMstDto.getkTemplateCode(),
        //         KAKAO_SENDER_KEY, String.valueOf(Constants.SMS_PRICE_CHANGE_FAIL_TEMPLATE_ID));
        //
        //     //이력 저장
        //     serviceAlterTraceSub.setEventCode("X19");
        //     serviceAlterTraceSub.setTrtmRsltSmst("요금제변경");
        //     serviceAlterTraceSub.settSocCode(toSocCode);
        //     serviceAlterTraceSub.setaSocCode(nowPriceSocCode);
        //     serviceAlterTraceSub.setGlobalNo(regSvcChgSelf.getGlobalNo());
        //     serviceAlterTraceSub.setRsltCd(regSvcChgSelf.getResultCode());
        //     serviceAlterTraceSub.setPrcsSbst(regSvcChgSelf.getSvcMsg());
        //     mypageService.insertServiceAlterTrace(serviceAlterTraceSub);
        //
        //     //결과 이력 저장
        //     serviceAlterTrace.setEventCode("FIN");
        //     serviceAlterTrace.setTrtmRsltSmst("FAIL");
        //     serviceAlterTrace.setParameter("19. 요금 상품 변경 실패");
        //     mypageService.insertServiceAlterTrace(serviceAlterTrace);
        //
        //
        //     //M 전산 이력 저장
        //     serviceAlterTrace.setSuccYn("N");
        //
        //     if ("ITL_SFC_E033".equals(regSvcChgSelf.getResultCode())) {
        //         //SRM23042026311 요금제 셀프변경 처리기준 변경 요청
        //         serviceAlterTrace.setProcMemo("가입중인 상품으로 요금제를 변경 하실 수 없습니다.");
        //         serviceAlterTrace.setProcYn("Y");
        //         serviceAlterTrace.setProcId("SYSTEM");
        //         Timestamp procDate = new Timestamp(System.currentTimeMillis());
        //         serviceAlterTrace.setProcDate(procDate);
        //
        //         //serviceAlterTraceSub.setRsltCd("0000");
        //         rtnMap.put("RESULT_MSG", "요금제 변경이 되어 있습니다. <br/>잠시 후에 요금제 확인 하시기 바랍니다.[002]");
        //     }
        //
        //     mypageService.insertSocfailProcMst(serviceAlterTrace);
        //
        //     return rtnMap;
        // }
        //
        // //3.부가 서비스 가입 처리
        // //3-1. 부가 서비스 가입 처리 해야 할 리스트 조회
        // List<McpUserCntrMngDto> serviceInfoList = mypageService.getromotionDcList(toSocCode);
        // int successCnt = 0;
        // int failCnt = 0;
        //
        //
        // for (McpUserCntrMngDto serviceInfo: serviceInfoList) {
        //     //3-2. 부가 서비스 가입
        //     //실패시.. ???  중간에 실패해도 계속 진행 해야 함...
        //     RegSvcChgRes regSvcInsert = null;
        //     String strParameter = "[" + serviceInfo.getSocNm() + "][" + serviceInfo.getSocPrice() + "]";
        //
        //     for (int reTryCount = 0; reTryCount < 2; reTryCount++) {
        //         if (regSvcInsert == null) {
        //             regSvcInsert = mPlatFormService.regSvcChgNeTrace(searchVO, serviceInfo.getSocCode(), "");
        //         } else if ("ITL_SYS_E0001".equals(regSvcInsert.getResultCode())) {
        //             Thread.sleep(3000);
        //             //이력 저장
        //             serviceAlterTraceSub.setEventCode("X21");
        //             serviceAlterTraceSub.setTrtmRsltSmst("부가서비스신청");
        //             serviceAlterTraceSub.settSocCode(serviceInfo.getSocCode());
        //             serviceAlterTraceSub.setaSocCode("");
        //             serviceAlterTraceSub.setParameter(strParameter);
        //             serviceAlterTraceSub.setGlobalNo(regSvcInsert.getGlobalNo());
        //             serviceAlterTraceSub.setRsltCd(regSvcInsert.getResultCode());
        //             serviceAlterTraceSub.setPrcsSbst(regSvcInsert.getSvcMsg());
        //             mypageService.insertServiceAlterTrace(serviceAlterTraceSub);
        //
        //             regSvcInsert = mPlatFormService.regSvcChgNeTrace(searchVO, serviceInfo.getSocCode(), "");
        //         }
        //     }
        //
        //     //이력 저장
        //     serviceAlterTraceSub.setEventCode("X21");
        //     serviceAlterTraceSub.setTrtmRsltSmst("부가서비스신청");
        //     serviceAlterTraceSub.settSocCode(serviceInfo.getSocCode());
        //     serviceAlterTraceSub.setaSocCode("");
        //     serviceAlterTraceSub.setParameter(strParameter);
        //     serviceAlterTraceSub.setGlobalNo(regSvcInsert.getGlobalNo());
        //     serviceAlterTraceSub.setRsltCd(regSvcInsert.getResultCode());
        //     serviceAlterTraceSub.setPrcsSbst(regSvcInsert.getSvcMsg());
        //
        //     if (regSvcInsert.isSuccess()) {
        //         serviceAlterTraceSub.setRsltCd("0000");
        //         successCnt++;
        //     } else {
        //         failCnt++;
        //     }
        //     mypageService.insertServiceAlterTrace(serviceAlterTraceSub);
        // }
        //
        // if (successCnt == serviceInfoList.size()) {
        //     serviceAlterTrace.setEventCode("FIN");
        //     serviceAlterTrace.setTrtmRsltSmst("SUCCESS");
        //     serviceAlterTrace.setParameter("SCNT[" + successCnt + "]FCNT[" + failCnt + "]");
        //     mypageService.insertServiceAlterTrace(serviceAlterTrace);
        //     serviceAlterTrace.setSuccYn("Y");
        //     mypageService.insertSocfailProcMst(serviceAlterTrace);
        // } else {
        //     serviceAlterTrace.setEventCode("FIN");
        //     serviceAlterTrace.setTrtmRsltSmst("FAIL");
        //     serviceAlterTrace.setParameter("SCNT[" + successCnt + "]FCNT[" + failCnt + "]");
        //     mypageService.insertServiceAlterTrace(serviceAlterTrace);
        //
        //     //실패 이력 테이블 저장
        //     serviceAlterTrace.setSuccYn("N");
        //     mypageService.insertSocfailProcMst(serviceAlterTrace);
        // }
        //
        // return rtnMap;
        //
        // if (AJAX_SUCCESS.equals(rtnMap.get("RESULT_CODE"))) {
        //     MspRateMstDto rateInfo = mspService.getMspRateMst(toSocCode);
        //     if (org.apache.commons.lang.StringUtils.isNotEmpty(rateInfo.getJehuProdType())) {
        //         try {
        //             MpCustInfoAgreeVO agreeParam = MpCustInfoAgreeVO.builder()
        //                 .cnsgInfoAdvrRcvAgreYn("Y")
        //                 .build();
        //             mplatFormService.moscContCustInfoAgreeChg(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId(), agreeParam);
        //         } catch (Exception ignored) {
        //         }
        //     }
        // }
        return null;
    }

    // 요금제 변경 예약 처리(X88)
    @Override public ChargePlanChangeProcessResponse chargePlanChangeReservationProcess(ChargePlanChangeProcessRequest request) {

        // 요금제 예약변경시에 ftrNewParam 부가파라미터 보내는게 있는데
        // 어떤 요금제일떄 보내는거인지?? 상관이 있는지 없는지 확인 필요

        String ip = "127.0.0.1";

        // if (!StringUtil.isNotNull(contractNum)) { Valid Check
        //     rtnMap.put("RESULT_CODE", "E");
        //     rtnMap.put("message", "비정상적인 접근입니다.");
        //     return rtnMap;
        // }

        HashMap<String, String> mapData = new HashMap<>();
        mapData.put("userId", userId);
        mapData.put("cntrMobileNo", cntrMobileNo);

        // 계약 정보 조회
        List<McpUserCntrMngDto> cntrList = mcpApiClient.post("/mypage/cntrList", mapData, List.class);

        if (cntrList.isEmpty()) {
            throw new McpCommonException(NOT_FULL_MEMBER_EXCEPTION);
        }

        McpUserCntrMngDto mcpUserCntrMngDto = cntrList.getFirst();
        request.setCtn(mcpUserCntrMngDto.getCntrMobileNo());
        request.setNcn(mcpUserCntrMngDto.getSvcCntrNo());
        request.setCustId(mcpUserCntrMngDto.getCustId());

        ChargePlanChangeProcessResponse res = null;
        HashMap<String, String> params = objectMapper.convertValue(request, HashMap.class);

        try {
            params.put("eventCd", "X88");
            //X88
            res = mPlatFormService.commonMplatform(params, "X88", ChargePlanChangeProcessResponse.class);

            String prcsMdlInd = "GC" + DateTimeUtil.getFormatString("yyMMddHHmmss");
            String today = DateTimeUtil.getShortDateString().replaceAll("-", "");
            String chgapyDate = DateTimeUtil.addMonths(today, +1);
            String chgDate = chgapyDate.substring(0, 6);

            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setSvcCntrNo(request.getNcn());//서비스계약번호
            mcpIpStatisticDto.setMobileNo(request.getCtn());
            mcpIpStatisticDto.setUserid(userId);
            mcpIpStatisticDto.setEventCode("X88");
            mcpIpStatisticDto.setResChgRateCd(request.getSoc());
            mcpIpStatisticDto.setResChgDate(today);
            mcpIpStatisticDto.setResChgApyDate(chgDate + "01");
            mcpIpStatisticDto.setCretIp(ip);
            mcpIpStatisticDto.setGlobalNo("123"); // globalNO 차후 수정 필요
            mcpIpStatisticDto.setTrtMdlDiv(prcsMdlInd);
            mcpIpStatisticDto.setParam("");
            mcpIpStatisticDto.setBatchRsltCd("");
            mcpIpStatisticDto.setBefChgRateCd("변경전요금제코드");
            mcpIpStatisticDto.setBefChgRateAmnt(0); // 변경전요금제금액

            String rsltYn = StringUtil.NVL(res.getRsltYn(), "N");

            if ("Y".equals(rsltYn)) {

                // logger.error("[요금제 예약변경 신청 로그]:" + mcpIpStatisticDto.getSvcCntrNo() + "_"
                //     + mcpIpStatisticDto.getMobileNo() + "_"
                //     + mcpIpStatisticDto.getUserid() + "_"
                //     + mcpIpStatisticDto.getBefChgRateCd() + "_"
                //     + mcpIpStatisticDto.getResChgRateCd() + "_"
                //     + DateTimeUtil.getFormatString("yyyyMMddHHmmss"));

                //요금제 예약변경 이력저장
                // ipStatisticService.insertRateResChgAccessTrace(mcpIpStatisticDto);
            } else {
                McpIpStatisticDto mcpIpStatisticDto2 = new McpIpStatisticDto();
                mcpIpStatisticDto2.setPrcsMdlInd("X88 ERROR");
                mcpIpStatisticDto2.setTrtmRsltSmst(request.getNcn());
                mcpIpStatisticDto2.setParameter("NCN[" + request.getNcn() + "]CTN[" + request.getCtn() + "]USERID[" + userId + "]ResChgRateCd[" + request.getSoc() + "]");
                mcpIpStatisticDto2.setPrcsSbst("결과 실패 : RsltYn[" + res.getRsltYn() + "]Success[" + rsltYn + "]");

                // ipStatisticService.insertAdminAccessTrace(mcpIpStatisticDto2);
            }

        } catch (Exception e) {
            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("X88 ERROR");
            mcpIpStatisticDto.setTrtmRsltSmst(request.getNcn());
            mcpIpStatisticDto.setParameter("NCN[" + request.getNcn() + "]CTN[" + request.getCtn() + "]USERID[" + userId + "]ResChgRateCd[" + request.getSoc() + "]");
            mcpIpStatisticDto.setPrcsSbst(e.getMessage());
            // ipStatisticService.insertAdminAccessTrace(mcpIpStatisticDto);

            throw new McpCommonException(COMMON_EXCEPTION);
        }

        return res;
    }

    // 요금제 변경 예약 조회(X89)
    @Override public ChargePlanChangeReservationResponse selectChargePlanChangeReservation(ChargePlanRequest request) {

        HashMap<String, String> mapData = new HashMap<>();
        mapData.put("userId", userId);
        mapData.put("cntrMobileNo", cntrMobileNo);

        // 계약 정보 조회
        List<McpUserCntrMngDto> cntrList = mcpApiClient.post("/mypage/cntrList", mapData, List.class);

        if (cntrList.isEmpty()) {
            throw new McpCommonException(NOT_FULL_MEMBER_EXCEPTION);
        }

        McpUserCntrMngDto mcpUserCntrMngDto = cntrList.getFirst();
        request.setCtn(mcpUserCntrMngDto.getCntrMobileNo());
        request.setNcn(mcpUserCntrMngDto.getSvcCntrNo());
        request.setCustId(mcpUserCntrMngDto.getCustId());

        ChargePlanChangeReservationResponse chargePlanResponse = null;

        try {
            HashMap<String, String> params = objectMapper.convertValue(request, HashMap.class);
            params.put("eventCd", "X89");
            // 예약된 요금제 조회(X89) ncn, ctn, custId
            chargePlanResponse = mPlatFormService.commonMplatform(params, "X89", ChargePlanChangeReservationResponse.class);
        } catch (SelfServiceException e) {
            // logger.info("Exception e : {}", e.getMessage());
        } catch (Exception e) {
            // logger.info("X18Error");
        }
        return chargePlanResponse;
    }

    // 요금제 변경 예약 취소(X90)
    @Override public ChargePlanChangeReservationCancelResponse chargePlanChangeReservationCancel(ChargePlanChangeReservationCancelRequest request) {

        HashMap<String, String> mapData = new HashMap<>();
        mapData.put("userId", userId);
        mapData.put("cntrMobileNo", cntrMobileNo);

        // 계약 정보 조회
        List<McpUserCntrMngDto> cntrList = mcpApiClient.post("/mypage/cntrList", mapData, List.class);

        if (cntrList.isEmpty()) {
            throw new McpCommonException(NOT_FULL_MEMBER_EXCEPTION);
        }

        ChargePlanChangeReservationCancelResponse response = null;
        HashMap<String, String> params = objectMapper.convertValue(request, HashMap.class);
        McpUserCntrMngDto mcpUserCntrMngDto = cntrList.getFirst();
        request.setCtn(mcpUserCntrMngDto.getCntrMobileNo());
        request.setNcn(mcpUserCntrMngDto.getSvcCntrNo());
        request.setCustId(mcpUserCntrMngDto.getCustId());

        // 예약된 요금제 취소
        try {
            params.put("eventCd", "X90");
            // 예약된 요금제 취소(X90)
            response = mPlatFormService.commonMplatform(params, "X90", ChargePlanChangeReservationCancelResponse.class);
        } catch (SelfServiceException e) {
            // logger.info("Exception e : {}", e.getMessage());
        } catch (Exception e) {
            // logger.info("X18Error");
        }

        return response;
    }
}
