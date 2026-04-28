package com.ktmmobile.msf.domains.form.form.termination.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.common.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.domains.form.form.newchange.dto.AgentInfoDto;
import com.ktmmobile.msf.domains.form.form.newchange.dto.AgentInfoRequest;
import com.ktmmobile.msf.domains.form.form.common.service.FormCommService;
import com.ktmmobile.msf.domains.form.form.servicechange.service.MsfChangPageSvc;
import com.ktmmobile.msf.domains.form.form.termination.dto.CancelConsultDto.RemainChargeReqDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CancelConsultDto.RemainChargeResVO;
import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationApplyReqDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationApplyResVO;
import com.ktmmobile.msf.domains.form.form.termination.service.MsfCancelPageSvc;
import jakarta.validation.Valid;

@RestController
public class MsfCancelPageController {
    private static Logger logger = LoggerFactory.getLogger(MsfCancelPageController.class);

    @Autowired
    private MsfCancelPageSvc msfCancelPageSvc;
    @Autowired
    private FormCommService formCommService;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;
    @Autowired
    private MsfChangPageSvc msfChangPageSvc;

    /**
     * 해지화면 대리점 목록 조회
     */
    @PostMapping("/api/msf/formTermination/agent/list")
    public CommonResponse<AgentInfoDto> getTerminationAgentInfo(@RequestBody @Valid AgentInfoRequest request) {
        return ResponseUtils.ok(formCommService.getAgentInfo(request.shopOrgnId()));
    }

    /**
     * X18 잔여요금·위약금 실시간 조회
     * POST /remainCharge/list
     * [TOBE] ncn만 프론트에서 수신, ctn·custId는 세션 계약 목록에서 조회 (getRealTimePriceAjax.do 방식 동일)
     */
    // AS-IS reference: mcp/mcp-portal-was MyinfoController#getRealTimePriceAjax
    // flow: session user -> contract list(ncn match) -> ctn/custId -> X18(farRealtimePayInfo)
    @RequestMapping(value = "/remainCharge/list")
    public RemainChargeResVO getRemainCharge(@RequestBody RemainChargeReqDto reqDto) {
        logger.debug("[getRemainCharge][controller] request: reqNull={}, ncn={}, ctn={}, custIdPresent={}",
            reqDto == null,
            reqDto == null ? "" : reqDto.getNcn(),
            reqDto == null ? "" : reqDto.getCtn(),
            reqDto != null && StringUtils.isNotBlank(reqDto.getCustId()));

        RemainChargeResVO errVO = new RemainChargeResVO();
        if (reqDto == null) {
            logger.debug("[getRemainCharge][controller] early-return: reqDto is null");
            errVO.setSuccess(false);
            errVO.setMessage("요청 정보가 없습니다.");
            //TEST_SKIP return errVO;
        }

        // 1. ncn 필수 검증
        if (StringUtils.isBlank(reqDto.getNcn())) {
            logger.debug("[getRemainCharge][controller] early-return: ncn is blank");
            errVO.setSuccess(false);
            errVO.setMessage("계약번호(ncn)는 필수입니다.");
            //TEST_SKIP return errVO;
        }


        // 3. 세션에서 userId 조회
        // UserSessionDto userSession = SessionUtils.getUserCookieBean();
        // if (userSession == null || StringUtils.isEmpty(userSession.getUserId())) {
        //     logger.debug("[getRemainCharge][controller] early-return: invalid session. sessionNull={}, userId={}",

        //         userSession == null, userSession == null ? "" : userSession.getUserId());
        //     errVO.setSuccess(false);
        //     errVO.setMessage("세션 정보가 없습니다.");
        //     //TEST_SKIP return errVO;
        // }

        //4. MP 연동 파라미터 조회 제외
        // logger.debug("[getRemainCharge][controller] pass-validation: userId={}", userSession.getUserId());
        // List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
        // McpUserCntrMngDto cntrInfo = null;
        // if (cntrList != null && !cntrList.isEmpty()) {
        //     cntrInfo = cntrList.stream()
        //         .filter(item -> reqDto.getNcn().equals(item.getSvcCntrNo()))
        //         .findFirst().orElse(null);
        // }

        //4. MP 연동 전화번호
        logger.debug("[getRemainCharge][controller] selectCntrListNoLogin: ncn={}", reqDto.getNcn());
        McpUserCntrMngDto cntrInfo = msfChangPageSvc.selectCntrListNoLogin(reqDto.getNcn());

        if (cntrInfo == null) {
            errVO.setSuccess(false);
            errVO.setMessage("계약 정보를 찾을 수 없습니다.");
            return errVO;
        }
        reqDto.setCtn(cntrInfo.getCntrMobileNo());
        reqDto.setCustId(cntrInfo.getCustId());

        logger.info("X18 잔여요금 조회 요청: ncn={}, ctn={}", reqDto.getNcn(), reqDto.getCtn());
        return msfCancelPageSvc.getRemainCharge(reqDto);
    }

    @PostMapping(value = "/api/msf/formTermination/{applicationKey}/complete")
    public TerminationApplyResVO complete(
        @PathVariable("applicationKey") String applicationKey,
        @RequestBody TerminationApplyReqDto reqDto
    ) {
        long startedAt = System.currentTimeMillis();
        String ncn = (reqDto != null && reqDto.getCustomer() != null) ? reqDto.getCustomer().getNcn() : "";

        logger.info("서비스해지 작성완료 요청: applicationKey={}, ncn={}", applicationKey, ncn);

        TerminationApplyResVO res = msfCancelPageSvc.apply(reqDto);
        long elapsed = System.currentTimeMillis() - startedAt;

        if (res != null && res.isSuccess()) {
            logger.info("서비스해지 작성완료 결과: applicationKey={}, ncn={}, success={}, applicationNo={}, elapsedMs={}",
                applicationKey, ncn, true, res.getApplicationNo(), elapsed);
        } else {
            String message = res != null ? res.getMessage() : "null response";
            logger.warn("서비스해지 작성완료 실패: applicationKey={}, ncn={}, success={}, message={}, elapsedMs={}",
                applicationKey, ncn, false, message, elapsed);
        }

        return res;
    }
}
