package com.ktmmobile.msf.domains.form.form.servicechange.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tools.jackson.databind.ObjectMapper;

import com.ktmmobile.msf.commons.common.context.business.BusinessContextBoundary;
import com.ktmmobile.msf.commons.common.context.business.BusinessContextHolder;
import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.commons.websecurity.web.util.RequestUtils;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxFormRequest;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxJsonRequest;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxSoapResponse;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.ServiceAlterTraceRequest;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.port.out.MspPrxClient;
import com.ktmmobile.msf.domains.externalclient.mspprx.support.util.XmlConvertUtils;
import com.ktmmobile.msf.domains.form.common.code.ResSvcChgMessage;
import com.ktmmobile.msf.domains.form.common.dto.McpIpStatisticDto;
import com.ktmmobile.msf.domains.form.common.dto.McpServiceAlterTraceDto;
import com.ktmmobile.msf.domains.form.common.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMplatFormService;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.RegSvcChgRes;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpSocVO;
import com.ktmmobile.msf.domains.form.common.repository.MspApiDirectRepository;
import com.ktmmobile.msf.domains.form.common.service.IpStatisticService;
import com.ktmmobile.msf.domains.form.common.util.DateTimeUtil;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.AdditionMyListResVO;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.AdditionReqDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MyPageSearchDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.PossibleStateCheckRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.PossibleStateCheckResponse;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.PricePlanReqDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.PricePlanY02ResDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.RealTimeInfoRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.RealTimeInfoResponse;
import com.ktmmobile.msf.domains.form.form.servicechange.repository.SvcChgPageRepositoryImpl;

//import com.ktmmobile.msf.domains.form.form.common.service.ServiceAlterTrService;

@Slf4j
@Service
@RequiredArgsConstructor
public class MsfSvgChargePlanChangeService {

    private final MspPrxClient mspPrxClient;
    private final ObjectMapper objectMapper;
    private final MsfMplatFormService mPlatFormService;
    private final MsfPricePlanServiceImpl msfPricePlanService;

    private final MsfRegSvcServiceImpl regSvcService;
    private final SvcChgPageRepositoryImpl svcChgPageRepository;
    private final MspApiDirectRepository mspApiDirectRepository;
    //private final ServiceAlterTrService serviceAlterTrService;
    private final IpStatisticService ipstatisticService;
    //private final ServiceAlterTraceMapper serviceAlterTraceMapper;

    /**
     * realTimeChargeList
     * 실시간 요금 조회(X18) - 요금제 변경 즉시변경 가능한 경우 팝업 노출
     */
    public FormResponse<RealTimeInfoResponse> realTimeChargeList(RealTimeInfoRequest req) {
        String apiMenuNm = "실시간 요금 조회";

        if (!hasValidKey(req)) {
            log.warn("[realTimeChargeList] invalid lookup key: ncn={}, custId={}, ctnPresent={}",
                req != null ? StringUtil.NVL(req.getNcn(), "") : "",
                req != null ? StringUtil.NVL(req.getCustId(), "") : "",
                req != null && !"".equals(normalizePhone(req.getCtn()))
            );
            return FormResponse.of(ResSvcChgMessage.CHANGE_REQUEST_INVALID);
        }

        try {
            HashMap<String, String> paramMap = objectMapper.convertValue(req, HashMap.class);
            paramMap.put("appEventCd", "X18");
            MspPrxSoapResponse mspResult = mspPrxClient.callService(MspPrxFormRequest.builder()
                .parameters(paramMap)
                .build());
            String rawXml = mspResult.rawXml();
            RealTimeInfoResponse res = XmlConvertUtils.xmlReturnParser(rawXml, RealTimeInfoResponse.class);

            return FormResponse.of(ResSvcChgMessage.SUCCESS, res);
        } catch (Exception e) {
            log.error("[realTimeChargeList] failed: ncn={}", req.getNcn(), e);
            return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, apiMenuNm + " 조회 중 오류가 발생했습니다.", null);
        }
    }

    /**
     * possibleStateCheck
     * 상품 변경 사전체크(Y24)
     *  - 요금제 변경, 부가서비스 가입 및 해지를 동시에 처리 가능
     *  - rule base 응답처리
     */
    //@BusinessContextBoundary
    public FormResponse<PossibleStateCheckResponse> possibleStateCheck(PossibleStateCheckRequest req) {
        //BusinessContextHolder.setParentScanId(req.getParentScanId());

        String apiMenuNm = "상품 변경 사전체크";    // Y24

        if (!hasValidKeyP(req)) {
            return FormResponse.of(ResSvcChgMessage.PRICE_CHANGE_REQUEST_INVALID);
        }

        try {
            if (StringUtils.isBlank(req.getActCode())) {
                req.setActCode("PCN");
            }
            log.debug("getActCode: {}", req.getActCode());

            // 1. 상품변경 불가 시간
            //  → Y24 에러존재 1018 | 23시 45분부터 00시 15분까지는 부가서비스 변경 업무가 불가합니다.
            boolean timeChk = DateTimeUtil.isMiddleTime("23:29", "00:30");
            if (timeChk) {
                //return FormResponse.of("006", "해당 시간은 상품변경이 불가 합니다. (23:30분 ~ 익일 00:30분, 1시간)", null);
                return FormResponse.of(ResSvcChgMessage.PRICE_CHANGE_OUTSIDEOFPROCESSHOUR_INVALID);
            }

            // 2. 미성년자
            //String birthday = req.getCustomerSsn(); // yyyymmdd
            //int age = NmcpServiceUtils.getBirthDateToAmericanAge(birthday, new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
            //
            //boolean underAge = age < 19;
            //if (underAge) {
            //    //return FormResponse.of("001", "만 19세이상 성인고객만 가능합니다.", null);
            //    return FormResponse.of(ResSvcChgMessage.PRICE_CHANGE_NOADULT_INVALID);
            //}

            // 6. 개통당월 또는 당월 요금제 변경이력이 있는 경우 즉시변경 불가 (예약변경 가능)
            if (req.getActCode().equals("PCN")) {
                // 현재 요금제 조회
                PricePlanReqDto pricePlanReqDto = new PricePlanReqDto();

                FormResponse<PricePlanY02ResDto> res = msfPricePlanService.currentPrice(pricePlanReqDto);
                Date nowDay = new Date();
                String thisMonth = DateTimeUtil.changeFormat(nowDay, "yyyyMM");
                String efctDt = Optional.ofNullable(res).map(FormResponse::resData).map(PricePlanY02ResDto::getOutDto)
                    .map(PricePlanY02ResDto.OutDto::getEfctStDt).orElse("");
                if (efctDt.length() > 6) {
                    efctDt = efctDt.substring(0, 6);

                    // 당월 요금제 변경 (개통 제외)
                    if (thisMonth.equals(efctDt)) {
                        return FormResponse.of("63005", "당월 요금제 변경이력이 있습니다.", null);
                    }
                }
                // 당월 개통
                if (!StringUtils.isBlank(req.getOpeningDate())) {
                    String openingDate = req.getOpeningDate().substring(0, 6);
                    if (thisMonth.equals(openingDate)) {
                        return FormResponse.of("63006", "당월 개통인 경우 예약 변경만 가능합니다.", null);
                    }
                }
            }

            // 상품 변경 사전체크(Y24)
            req.getPrdcList().forEach(proc -> {
                proc.setPrdcSbscTrtmCd("A");
                proc.setPrdcTypeCd("P");
            });

            MspPrxSoapResponse response = mspPrxClient.callServiceJson(buildY24PreCheckRequest(req, req.getPrdcList()));
            PossibleStateCheckResponse successRes = toPreCheckRes(response);
            if (successRes == null) {
                return FormResponse.of("999", "가능 여부를 확인할 수 없습니다.", null);
            }
            if (!response.success()) {
                String message = getMoscPrdcTrtmPreChkMessage(successRes);
                return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, message, successRes);
            }
            if (!isMoscPrdcTrtmPreChkSuccess(successRes)) {
                String message = getMoscPrdcTrtmPreChkMessage(successRes);
                return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, message, successRes);
            }

            return FormResponse.of(ResSvcChgMessage.SUCCESS, successRes);
        } catch (Exception e) {
            log.error("[possibleStateCheck] failed: ncn={}", req.getNcn(), e);
            return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, apiMenuNm + " 조회 중 오류가 발생했습니다.", null);
        }
    }

    /**
     * Y24 JSON 요청은 prdcList 배열을 유지해서 PRX serviceCallJson.do로 전달한다.
     */
    private MspPrxJsonRequest buildY24PreCheckRequest(
        PossibleStateCheckRequest req,
        List<PossibleStateCheckRequest.ProductInfo> prdcList
    ) {
        MspPrxJsonRequest.MspPrxJsonRequestBuilder builder = MspPrxJsonRequest.builder()
            .property("appEventCd", "Y24")
            .property("ncn", StringUtil.NVL(req.getNcn(), ""))
            .property("ctn", StringUtil.NVL(req.getCtn(), ""))
            .property("custId", StringUtil.NVL(req.getCustId(), ""))
            .property("actCode", StringUtil.NVL(req.getActCode(), "PCN"))
            .serviceAlterTrace(ServiceAlterTraceRequest.builder()
                .ncn(StringUtil.NVL(req.getNcn(), ""))
                .subscriberNo(StringUtil.NVL(req.getCtn(), ""))
                .eventCd("Y24")
                .trtmRsltSbst("요금제 변경 가능 여부 사전 체크-" + StringUtil.NVL(req.getActCode(), "PCN"))
                .build());

        addY24LogProperties(builder);
        builder.property("prdcList", prdcList == null
            ? List.of()
            : prdcList.stream()
                .map(this::toY24Product)
                .collect(Collectors.toList()));

        return builder.build();
    }

    private void addY24LogProperties(MspPrxJsonRequest.MspPrxJsonRequestBuilder builder) {
        try {
            if (!(RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes attributes)) {
                return;
            }
            HttpServletRequest request = attributes.getRequest();
            builder.property("ip", RequestUtils.getClientIp());
            builder.property("url", request.getRequestURI());
            builder.property("mdlInd", "MSP");

            try {
                builder.property("userid", AuthenticationUtils.getUser().getUserId());
            } catch (RuntimeException e) {
                log.debug("[moscPrdcTrtmPreChk] authenticated user is unavailable: {}", e.getMessage());
            }
        } catch (Exception e) {
            log.debug("[moscPrdcTrtmPreChk] failed to add Y24/Y25 log properties: {}", e.getMessage());
        }
    }

    private Map<String, Object> toY24Product(PossibleStateCheckRequest.ProductInfo productInfo) {
        Map<String, Object> product = new LinkedHashMap<>();
        if (productInfo == null) {
            return product;
        }
        product.put("prdcCd", StringUtil.NVL(productInfo.getPrdcCd(), ""));
        product.put("prdcSbscTrtmCd", StringUtil.NVL(productInfo.getPrdcSbscTrtmCd(), ""));
        product.put("prdcTypeCd", StringUtil.NVL(productInfo.getPrdcTypeCd(), ""));
        product.put("prdcSeqNo", StringUtil.NVL(productInfo.getPrdcSeqNo(), ""));
        product.put("ftrNewParam", StringUtil.NVL(productInfo.getFtrNewParam(), ""));
        return product;
    }

    public PossibleStateCheckResponse toPreCheckRes(MspPrxSoapResponse response) {
        if (response == null) {
            return null;
        }

        PossibleStateCheckResponse res = new PossibleStateCheckResponse();
        res.setGlobalNo(StringUtil.NVL(response.globalNo(), ""));

        if (!response.success()) {
            String responseCode = StringUtil.NVL(response.responseCode(), "");
            String responseMessage = StringUtil.NVL(response.responseBasic(), "");
            res.setResultCode(responseCode);
            res.setRsltCd(responseCode);
            res.setResltMsg(responseMessage);
            res.setSvcMsg(responseMessage);
            return res;
        }

        Map<String, Object> outDto = response.payloadObject("outDto").orElse(Map.of());
        res.setRsltCd(text(outDto, "rsltCd"));
        res.setResultCode(text(outDto, "resultCode"));
        res.setSbscYn(text(outDto, "sbscYn"));
        res.setResltMsg(firstText(outDto, "rsltMsg", "resltMsg"));
        res.setSvcMsg(text(outDto, "svcMsg"));
        res.setRuleList(ruleList(outDto.get("ruleList")));
        if (res.getRuleList() != null && !res.getRuleList().isEmpty()) {
            //PossibleStateCheckResponse.RuleInfo rule = new PossibleStateCheckResponse.RuleInfo();
            Map<String, Object> ruleInfo = response.payloadObject("ruleInfo").orElse(Map.of());
            res.setRuleMsgSbst(text(ruleInfo, "ruleMsgSbst"));
        }
        return res;
    }

    private List<PossibleStateCheckResponse.RuleInfo> ruleList(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof List<?> list) {
            return list.stream()
                .map(this::ruleInfo)
                .filter(ruleInfo -> ruleInfo != null)
                .collect(Collectors.toList());
        }
        PossibleStateCheckResponse.RuleInfo ruleInfo = ruleInfo(value);
        return ruleInfo == null ? null : List.of(ruleInfo);
    }

    private PossibleStateCheckResponse.RuleInfo ruleInfo(Object value) {
        if (!(value instanceof Map<?, ?> valueMap)) {
            return null;
        }
        Map<String, Object> map = (Map<String, Object>) valueMap;
        PossibleStateCheckResponse.RuleInfo ruleInfo = new PossibleStateCheckResponse.RuleInfo();
        ruleInfo.setPrdcCd(text(map, "prdcCd"));
        ruleInfo.setPrdcNm(text(map, "prdcNm"));
        ruleInfo.setRuleId(text(map, "ruleId"));
        ruleInfo.setRuleMsgSbst(text(map, "ruleMsgSbst"));
        ruleInfo.setRuleRsltCd(text(map, "ruleRsltCd"));
        ruleInfo.setRuleTypeCd(text(map, "ruleTypeCd"));
        ruleInfo.setTrgtPrdcCd(text(map, "trgtPrdcCd"));
        ruleInfo.setTrgtPrdcNm(text(map, "trgtPrdcNm"));
        return ruleInfo;
    }

    /**
     * Y24 응답 코드와 가입 가능 여부를 성공 기준으로 판정한다.
     */
    private boolean isMoscPrdcTrtmPreChkSuccess(PossibleStateCheckResponse res) {
        String rsltCd = StringUtil.NVL(res.getRsltCd(), "");
        String resultCode = StringUtil.NVL(res.getResultCode(), "");
        String sbscYn = StringUtil.NVL(res.getSbscYn(), "");

        if (!"".equals(rsltCd) && !"0000".equals(rsltCd)) {
            return false;
        }
        if (!"".equals(resultCode) && !"0000".equals(resultCode)) {
            return false;
        }
        if (!"".equals(sbscYn) && !"Y".equalsIgnoreCase(sbscYn)) {
            return false;
        }
        return true;
    }

    /**
     * Y24 실패 응답에서 화면에 표시할 메시지를 추출한다.
     */
    private String getMoscPrdcTrtmPreChkMessage(PossibleStateCheckResponse res) {
        if (res.getRuleList() != null) {
            for (PossibleStateCheckResponse.RuleInfo ruleInfo: res.getRuleList()) {
                String ruleMsgSbst = StringUtil.NVL(ruleInfo.getRuleMsgSbst(), "");
                if (!"".equals(ruleMsgSbst)) {
                    return ruleMsgSbst;
                }
            }
        }
        String resltMsg = StringUtil.NVL(res.getResltMsg(), "");
        if (!"".equals(resltMsg)) {
            return resltMsg;
        }
        String svcMsg = StringUtil.NVL(res.getSvcMsg(), "");
        if (!"".equals(svcMsg)) {
            return svcMsg;
        }
        return "요금제 변경이 불가합니다.";
    }

    // -- UTIL

    private String normalizePhone(String value) {
        return StringUtil.NVL(value, "").replaceAll("[^0-9]", "");
    }

    private boolean hasValidKey(RealTimeInfoRequest req) {
        return req != null
            && (!"".equals(StringUtil.NVL(req.getNcn(), ""))
            || !"".equals(StringUtil.NVL(req.getCustId(), ""))
            || !"".equals(normalizePhone(req.getCtn())));
    }

    private boolean hasValidKeyP(PossibleStateCheckRequest req) {
        return req != null
            && (!"".equals(StringUtil.NVL(req.getNcn(), ""))
            || !"".equals(StringUtil.NVL(req.getCustId(), ""))
            || !"".equals(normalizePhone(req.getCtn())));
    }

    private String firstText(Map<String, Object> map, String... keys) {
        for (String key: keys) {
            String value = text(map, key);
            if (!"".equals(value)) {
                return value;
            }
        }
        return "";
    }

    private String text(Map<String, Object> map, String key) {
        if (map == null) {
            return "";
        }
        Object value = map.get(key);
        return value == null ? "" : String.valueOf(value);
    }

    // 요금제 변경
    @BusinessContextBoundary
    public FormResponse<PossibleStateCheckResponse> possibleStateChange(PossibleStateCheckRequest req) {

        if (!hasValidKeyP(req)) {
            return FormResponse.of(ResSvcChgMessage.PRICE_CHANGE_REQUEST_INVALID);
        }

        BusinessContextHolder.setParentScanId(req.getParentScanId());

        String apiMenuNm = "상품 변경";     // Y25

        req.setActCode("PCN");
        log.debug("getActCode: {}", req.getActCode());

        // 상품 변경(Y25)
        req.getPrdcList().forEach(proc -> {
            proc.setPrdcSbscTrtmCd("A");
            proc.setPrdcTypeCd("P");
        });
        log.debug("possibleStateChange plansoc:{}", req.getPlanSoc());

        try {
            // 이용중인 부가서비스
            AdditionReqDto additionReqDto = new AdditionReqDto();
            additionReqDto.setCtn(req.getCtn());
            additionReqDto.setNcn(req.getNcn());
            additionReqDto.setCustId(req.getCustId());
            FormResponse<AdditionMyListResVO> getAddSvcInfo = regSvcService.myAddSvcList(additionReqDto);

            // 해지해야할 부가서비스
            List<McpUserCntrMngDto> closeSubList = mspApiDirectRepository.query("/mypage/closeSubList",
                req.getContractNum(),
                List.class);

            // 60분 이내 변경이력
            String prcsMdlInd = "GC" + DateTimeUtil.getFormatString("yyMMddHHmmss");
            McpServiceAlterTraceDto serviceAlterTrace = new McpServiceAlterTraceDto();
            serviceAlterTrace.setPrcsMdlInd(prcsMdlInd);
            serviceAlterTrace.setNcn(req.getNcn());
            serviceAlterTrace.settSocCode(req.getPlanSoc());
            serviceAlterTrace.setContractNum(req.getNcn());
            serviceAlterTrace.setChgType("I");
            serviceAlterTrace.setProcId("SMARTFORM");

            int soccheck = svcChgPageRepository.checkAllreadPlanchgCount(serviceAlterTrace);
            if (soccheck > 0) {
                return FormResponse.of("6351", "요금제 변경이 되어있습니다. 잠시후에 요금제 확인하시기 바랍니다.", null);
            }

            MyPageSearchDto searchVO = new MyPageSearchDto();
            searchVO.setCtn(req.getCtn());
            searchVO.setNcn(req.getNcn());
            searchVO.setCustId(req.getCustId());

            //McpServiceAlterTraceDto serviceAlterTraceSub = new McpServiceAlterTraceDto();
            //serviceAlterTraceSub.setNcn(searchVO.getNcn());
            //serviceAlterTraceSub.setContractNum(searchVO.getContractNum());
            //serviceAlterTraceSub.setSubscriberNo(searchVO.getCtn());
            //serviceAlterTraceSub.setPrcsMdlInd(prcsMdlInd);

            for (McpUserCntrMngDto closeSubInfo: closeSubList) {
                for (MpSocVO socVo: getAddSvcInfo.resData().getList()) {
                    if (socVo.getSoc().equals(closeSubInfo.getSocCode())) {
                        RegSvcChgRes regSvcCanChgNe = null;

                        //String strParameter = "[" + closeSubInfo.getSocNm() + "]";
                        for (int reTryCount = 0; reTryCount < 2; reTryCount++) {
                            if (regSvcCanChgNe == null) {
                                regSvcCanChgNe = mPlatFormService.moscRegSvcCanChgNeTrace(searchVO, closeSubInfo.getSocCode());
                            } else if ("ITL_SYS_E0001".equals(regSvcCanChgNe.getResultCode())) {
                                Thread.sleep(3000);
                                //이력 저장
                                //serviceAlterTraceSub.setEventCode("X38");
                                //serviceAlterTraceSub.setTrtmRsltSmst("부가서비스 해지");
                                //serviceAlterTraceSub.settSocCode(closeSubInfo.getSocCode());
                                //serviceAlterTraceSub.setaSocCode("");
                                //serviceAlterTraceSub.setParameter(strParameter);
                                //serviceAlterTraceSub.setGlobalNo(regSvcCanChgNe.getGlobalNo());
                                //serviceAlterTraceSub.setRsltCd(regSvcCanChgNe.getResultCode());
                                //serviceAlterTraceSub.setPrcsSbst(regSvcCanChgNe.getSvcMsg());
                                //serviceAlterTrService.insertServiceAlterTrace(serviceAlterTraceSub); -- 서비스 호출시 이력 자동 저장으로 이하 모두 주석 처리
                                regSvcCanChgNe = mPlatFormService.moscRegSvcCanChgNeTrace(searchVO, closeSubInfo.getSocCode());
                            }
                        }

                        if (!regSvcCanChgNe.isSuccess()) {

                            //MspSmsTemplateMstDto mspSmsTemplateMstDto = fCommonSvc.getMspSmsTemplateMst(Constants.SMS_PRICE_CHANGE_FAIL_TEMPLATE_ID);
                            ////smsSvc.sendKakaoNoti( mspSmsTemplateMstDto.getSubject(), searchVO.getCtn(), mspSmsTemplateMstDto.getText(),mspSmsTemplateMstDto.getCallback(), mspSmsTemplateMstDto.getkTemplateCode(), KAKAO_SENDER_KEY);
                            //smsSvc.sendKakaoNoti( mspSmsTemplateMstDto.getSubject(), searchVO.getCtn(), mspSmsTemplateMstDto.getText(),
                            //    mspSmsTemplateMstDto.getCallback(),mspSmsTemplateMstDto.getkTemplateCode(),
                            //    KAKAO_SENDER_KEY, String.valueOf(Constants.SMS_PRICE_CHANGE_FAIL_TEMPLATE_ID));

                            //이력 저장
                            //serviceAlterTraceSub.setEventCode("X38");
                            //serviceAlterTraceSub.setTrtmRsltSmst("부가서비스 해지");
                            //serviceAlterTraceSub.settSocCode(closeSubInfo.getSocCode());
                            //serviceAlterTraceSub.setaSocCode("");
                            //serviceAlterTraceSub.setParameter(strParameter);
                            //serviceAlterTraceSub.setGlobalNo(regSvcCanChgNe.getGlobalNo());
                            //serviceAlterTraceSub.setRsltCd(regSvcCanChgNe.getResultCode());
                            //serviceAlterTraceSub.setPrcsSbst(regSvcCanChgNe.getSvcMsg());
                            //serviceAlterTrService.insertServiceAlterTrace(serviceAlterTraceSub);

                            //결과 이력 저장
                            serviceAlterTrace.setEventCode("FIN");
                            serviceAlterTrace.setTrtmRsltSmst("FAIL");
                            serviceAlterTrace.setParameter("부가서비스 해지 실패");
                            //serviceAlterTrService.insertServiceAlterTrace(serviceAlterTrace);

                            //M 전산 이력 저장
                            serviceAlterTrace.setSuccYn("N");
                            mspApiDirectRepository.query("/mypage/insertSocfailProcMst", serviceAlterTrace, null);

                            return FormResponse.of(ResSvcChgMessage.PRICE_SELF_SERVICE_ERROR, "부가서비스 해지 실패", null);

                            //} else {
                            //이력 저장
                            //serviceAlterTraceSub.setEventCode("X38");
                            //serviceAlterTraceSub.setTrtmRsltSmst("부가서비스 해지");
                            //serviceAlterTraceSub.settSocCode(closeSubInfo.getSocCode());
                            //serviceAlterTraceSub.setaSocCode("");
                            //serviceAlterTraceSub.setParameter(strParameter);
                            //serviceAlterTraceSub.setGlobalNo(regSvcCanChgNe.getGlobalNo());
                            //serviceAlterTraceSub.setRsltCd("0000");
                            //serviceAlterTraceSub.setPrcsSbst(regSvcCanChgNe.getSvcMsg());
                            //serviceAlterTrService.insertServiceAlterTrace(serviceAlterTraceSub);
                        }
                        break;
                    }
                }
            }

            MspPrxSoapResponse response = null;
            PossibleStateCheckResponse regSvcChgSelf = null;

            for (int reTryCount = 0; reTryCount < 2; reTryCount++) {
                if (response == null) {
                    response = mspPrxClient.callServiceJson(buildY25PreCheckRequest(req, req.getPrdcList()));
                    regSvcChgSelf = toPreCheckRes(response);
                } else if ("ITL_SYS_E0001".equals(regSvcChgSelf.getRsltCd())) { //NSTEP ESB 연동 오류인 경우 한번 더 호출
                    Thread.sleep(3000);
                    //이력 저장
                    //serviceAlterTraceSub.setGlobalNo(regSvcChgSelf.getGlobalNo());
                    //serviceAlterTraceSub.setRsltCd(regSvcChgSelf.getRsltCd());
                    //serviceAlterTraceSub.setPrcsSbst(regSvcChgSelf.getSvcMsg());
                    //serviceAlterTrService.insertServiceAlterTrace(serviceAlterTraceSub);

                    response = mspPrxClient.callServiceJson(buildY25PreCheckRequest(req, req.getPrdcList()));
                    regSvcChgSelf = toPreCheckRes(response);
                }
            }

            //성공일 때
            if (response.success() && "0000".equals(regSvcChgSelf.getRsltCd())) {

                //이력 저장
                //serviceAlterTraceSub.setGlobalNo(regSvcChgSelf.getGlobalNo());
                //serviceAlterTraceSub.setRsltCd("0000");
                //serviceAlterTraceSub.setPrcsSbst(regSvcChgSelf.getSvcMsg());
                //serviceAlterTrService.insertServiceAlterTrace(serviceAlterTraceSub);

                //202312 wooki - MSP_DIS_APD(평생할인 부가서비스 기적용 대상) insert START
                String prmtId = mspApiDirectRepository.query("/mypage/getChrgPrmtIdSocChg", req.getPlanSoc(), String.class); //프로모션아이디 가져오기
                McpUserCntrMngDto apdDto = new McpUserCntrMngDto();
                apdDto.setPrmtId(prmtId); //위에서 조회한 prmtId set - prmtId는 있을수도 있고 없을수도 있음
                apdDto.setSocCode(req.getPlanSoc());
                apdDto.setContractNum(searchVO.getContractNum());
                mspApiDirectRepository.query("/mypage/insertDisApd", apdDto, null);
                //MSP_DIS_APD insert END

            } else {
                //결과가 N이면서 결과코드가 0000이 아니면
                if (response.success() && !"0000".equals(regSvcChgSelf.getRsltCd())) {
                    regSvcChgSelf.setSvcMsg(regSvcChgSelf.getRuleMsgSbst() == null ? regSvcChgSelf.getRsltMsg() : regSvcChgSelf.getRuleMsgSbst());
                }

                //MspSmsTemplateMstDto mspSmsTemplateMstDto = fCommonSvc.getMspSmsTemplateMst(Constants.SMS_PRICE_CHANGE_FAIL_TEMPLATE_ID);
                //smsSvc.sendKakaoNoti( mspSmsTemplateMstDto.getSubject(), searchVO.getCtn(), mspSmsTemplateMstDto.getText(),mspSmsTemplateMstDto.getCallback(), mspSmsTemplateMstDto.getkTemplateCode(), KAKAO_SENDER_KEY);
                //smsSvc.sendKakaoNoti( mspSmsTemplateMstDto.getSubject(), searchVO.getCtn(),mspSmsTemplateMstDto.getText(),
                //    mspSmsTemplateMstDto.getCallback(), mspSmsTemplateMstDto.getkTemplateCode(),
                //    KAKAO_SENDER_KEY, String.valueOf(Constants.SMS_PRICE_CHANGE_FAIL_TEMPLATE_ID));

                //이력 저장
                //serviceAlterTraceSub.setGlobalNo(regSvcChgSelf.getGlobalNo());
                //serviceAlterTraceSub.setRsltCd(regSvcChgSelf.getRsltCd());
                //serviceAlterTraceSub.setPrcsSbst(regSvcChgSelf.getSvcMsg());
                //serviceAlterTrService.insertServiceAlterTrace(serviceAlterTraceSub);

                //결과 이력 저장
                serviceAlterTrace.setEventCode("FIN");
                serviceAlterTrace.setTrtmRsltSmst("FAIL");
                serviceAlterTrace.setParameter("Y25.요금상품변경실패");
                //serviceAlterTrService.insertServiceAlterTrace(serviceAlterTrace);

                //M 전산 이력 저장
                serviceAlterTrace.setSuccYn("N");

                if ("ITL_SFC_E033".equals(regSvcChgSelf.getRsltCd())) {
                    //SRM23042026311 요금제 셀프변경 처리기준 변경 요청
                    serviceAlterTrace.setProcMemo("가입중인 상품으로 요금제를 변경 하실 수 없습니다.");
                    serviceAlterTrace.setProcYn("Y");
                    Timestamp procDate = new Timestamp(System.currentTimeMillis());
                    serviceAlterTrace.setProcDate(procDate);

                    //serviceAlterTraceSub.setRsltCd("0000");
                    return FormResponse.of("6351", "요금제 변경이 되어있습니다. 잠시후에 요금제 확인하시기 바랍니다.", null);
                }
                mspApiDirectRepository.query("/mypage/insertSocfailProcMst", serviceAlterTrace, null);

                log.debug("6352: {}", regSvcChgSelf.getSvcMsg());
                return FormResponse.of("6352", regSvcChgSelf.getSvcMsg(), null);
            }

            //3.부가 서비스 가입 처리
            //3-1. 부가 서비스 가입 처리 해야 할 리스트 조회
            List<McpUserCntrMngDto> serviceInfoList = mspApiDirectRepository.query("/mypage/romotionDcList",
                req.getPlanSoc(),
                List.class); //mypageService.getromotionDcList(toSocCode);
            int successCnt = 0;
            int failCnt = 0;

            for (McpUserCntrMngDto serviceInfo: serviceInfoList) {
                //3-2. 부가 서비스 가입
                //실패시.. ???  중간에 실패해도 계속 진행 해야 함...
                RegSvcChgRes regSvcInsert = null;
                //String strParameter = "[" + serviceInfo.getSocNm() + "][" + serviceInfo.getSocPrice() + "]";

                for (int reTryCount = 0; reTryCount < 2; reTryCount++) {
                    if (regSvcInsert == null) {
                        regSvcInsert = mPlatFormService.regSvcChgNeTrace(searchVO, serviceInfo.getSocCode(), "");
                    } else if ("ITL_SYS_E0001".equals(regSvcInsert.getResultCode())) {
                        Thread.sleep(3000);
                        //이력 저장
                        //serviceAlterTraceSub.setEventCode("X21");
                        //serviceAlterTraceSub.setTrtmRsltSmst("부가서비스신청");
                        //serviceAlterTraceSub.settSocCode(serviceInfo.getSocCode());
                        //serviceAlterTraceSub.setaSocCode("");
                        //serviceAlterTraceSub.setParameter(strParameter);
                        //serviceAlterTraceSub.setGlobalNo(regSvcInsert.getGlobalNo());
                        //serviceAlterTraceSub.setRsltCd(regSvcInsert.getResultCode());
                        //serviceAlterTraceSub.setPrcsSbst(regSvcInsert.getSvcMsg());
                        //serviceAlterTrService.insertServiceAlterTrace(serviceAlterTraceSub);

                        regSvcInsert = mPlatFormService.regSvcChgNeTrace(searchVO, serviceInfo.getSocCode(), "");
                    }
                }

                //이력 저장
                //serviceAlterTraceSub.setEventCode("X21");
                //serviceAlterTraceSub.setTrtmRsltSmst("부가서비스신청");
                //serviceAlterTraceSub.settSocCode(serviceInfo.getSocCode());
                //serviceAlterTraceSub.setaSocCode("");
                //serviceAlterTraceSub.setParameter(strParameter);
                //serviceAlterTraceSub.setGlobalNo(regSvcInsert.getGlobalNo());
                //serviceAlterTraceSub.setRsltCd(regSvcInsert.getResultCode());
                //serviceAlterTraceSub.setPrcsSbst(regSvcInsert.getSvcMsg());

                if (regSvcInsert.isSuccess()) {
                    //serviceAlterTraceSub.setRsltCd("0000");
                    successCnt++;
                } else {
                    failCnt++;
                }
                //serviceAlterTrService.insertServiceAlterTrace(serviceAlterTraceSub);
            }

            if (successCnt == serviceInfoList.size()) {
                serviceAlterTrace.setEventCode("FIN");
                serviceAlterTrace.setTrtmRsltSmst("SUCCESS");
                serviceAlterTrace.setParameter("SCNT[" + successCnt + "]FCNT[" + failCnt + "]");
                //serviceAlterTrService.insertServiceAlterTrace(serviceAlterTrace);
                serviceAlterTrace.setSuccYn("Y");
                mspApiDirectRepository.query("/mypage/insertSocfailProcMst", serviceAlterTrace, null);
            } else {
                serviceAlterTrace.setEventCode("FIN");
                serviceAlterTrace.setTrtmRsltSmst("FAIL");
                serviceAlterTrace.setParameter("SCNT[" + successCnt + "]FCNT[" + failCnt + "]");
                //serviceAlterTrService.insertServiceAlterTrace(serviceAlterTrace);

                //실패 이력 테이블 저장
                serviceAlterTrace.setSuccYn("N");
                mspApiDirectRepository.query("/mypage/insertSocfailProcMst", serviceAlterTrace, null);

                String message = getMoscPrdcTrtmPreChkMessage(regSvcChgSelf);
                return FormResponse.of(ResSvcChgMessage.PRICE_SELF_SERVICE_ERROR, message, regSvcChgSelf);
            }

            return FormResponse.of(ResSvcChgMessage.SUCCESS, regSvcChgSelf);
        } catch (Exception e) {
            log.error("[possibleStateChange] failed: ncn={}", req.getNcn(), e);
            return FormResponse.of(ResSvcChgMessage.PRICE_SELF_SERVICE_ERROR, apiMenuNm + " 조회 중 오류가 발생했습니다.", null);
        }
    }

    public MspPrxJsonRequest buildY25PreCheckRequest(
        PossibleStateCheckRequest req,
        List<PossibleStateCheckRequest.ProductInfo> prdcList
    ) {
        MspPrxJsonRequest.MspPrxJsonRequestBuilder builder = MspPrxJsonRequest.builder()
            .property("appEventCd", "Y25")
            .property("ncn", StringUtil.NVL(req.getNcn(), ""))
            .property("ctn", StringUtil.NVL(req.getCtn(), ""))
            .property("custId", StringUtil.NVL(req.getCustId(), ""))
            .property("actCode", StringUtil.NVL(req.getActCode(), "PCN"))
            .serviceAlterTrace(ServiceAlterTraceRequest.builder()
                .ncn(StringUtil.NVL(req.getNcn(), ""))
                .subscriberNo(StringUtil.NVL(req.getCtn(), ""))
                .eventCd("Y25")
                .tSocCode(req.getPlanSoc())
                .trtmRsltSbst("요금제 변경-" + StringUtil.NVL(req.getActCode(), "PCN"))
                .build());

        addY24LogProperties(builder);
        builder.property("prdcList", prdcList == null
            ? List.of()
            : prdcList.stream()
                .map(this::toY25Product)
                .collect(Collectors.toList()));

        return builder.build();
    }

    private Map<String, Object> toY25Product(PossibleStateCheckRequest.ProductInfo productInfo) {
        Map<String, Object> product = new LinkedHashMap<>();
        if (productInfo == null) {
            return product;
        }
        product.put("prdcCd", StringUtil.NVL(productInfo.getPrdcCd(), ""));
        product.put("prdcSbscTrtmCd", StringUtil.NVL(productInfo.getPrdcSbscTrtmCd(), ""));
        product.put("prdcTypeCd", StringUtil.NVL(productInfo.getPrdcTypeCd(), ""));
        product.put("prdcSeqNo", StringUtil.NVL(productInfo.getPrdcSeqNo(), ""));
        product.put("ftrNewParam", StringUtil.NVL(productInfo.getFtrNewParam(), ""));
        return product;
    }

    public FormResponse<PossibleStateCheckResponse> reservedPriceChange(PossibleStateCheckRequest req) {
        req.setActCode("RSV");
        log.debug("getActCode: {}", req.getActCode());

        // 상품 변경(Y25)
        req.getPrdcList().forEach(proc -> {
            proc.setPrdcSbscTrtmCd("A");
            proc.setPrdcTypeCd("P");
        });
        log.debug("reservedPriceChange plansoc:{}", req.getPlanSoc());
        log.debug("reservedPriceChange BeforePlanSoc:{}, BeforePlanAmt:{}", req.getBeforePlanSoc(), req.getBeforePlanAmt());

        try {
            MspPrxSoapResponse response = mspPrxClient.callServiceJson(buildY25PreCheckRequest(req, req.getPrdcList()));
            PossibleStateCheckResponse regSvcChgSelf = toPreCheckRes(response);

            if (response.success() && "0000".equals(regSvcChgSelf.getRsltCd())) {

                String prcsMdlInd = "GC" + DateTimeUtil.getFormatString("yyMMddHHmmss");
                String today = DateTimeUtil.getShortDateString().replaceAll("-", "");
                String chgapyDate = DateTimeUtil.addMonths(today, +1);
                String chgDate = chgapyDate.substring(0, 6);
                String userId = ""; // AuthenticationUtils.getUser().getUserId()
                String befChgRateAmnt = req.getBeforePlanAmt();

                McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
                mcpIpStatisticDto.setSvcCntrNo(req.getNcn());
                mcpIpStatisticDto.setMobileNo(req.getCtn());
                mcpIpStatisticDto.setUserid(userId);
                mcpIpStatisticDto.setEventCode("Y25");
                mcpIpStatisticDto.setResChgRateCd(req.getPlanSoc());
                mcpIpStatisticDto.setResChgDate(today);
                mcpIpStatisticDto.setResChgApyDate(chgDate + "01");
                mcpIpStatisticDto.setCretIp(RequestUtils.getClientIp());
                mcpIpStatisticDto.setGlobalNo(regSvcChgSelf.getGlobalNo());
                mcpIpStatisticDto.setTrtMdlDiv(prcsMdlInd);
                mcpIpStatisticDto.setParam("");
                mcpIpStatisticDto.setBatchRsltCd("");
                mcpIpStatisticDto.setBefChgRateCd(req.getBeforePlanSoc());
                mcpIpStatisticDto.setBefChgRateAmnt((befChgRateAmnt != null && befChgRateAmnt.matches("\\d+"))
                    ? Integer.parseInt(befChgRateAmnt)
                    : 0);

                log.debug("[요금제 예약변경 신청 로그]:" + mcpIpStatisticDto.getSvcCntrNo() + "_"
                    + mcpIpStatisticDto.getMobileNo() + "_"
                    + mcpIpStatisticDto.getUserid() + "_"
                    + mcpIpStatisticDto.getBefChgRateCd() + "_"
                    + mcpIpStatisticDto.getResChgRateCd() + "_"
                    + DateTimeUtil.getFormatString("yyyyMMddHHmmss"));

                //요금제 예약변경 이력저장
                svcChgPageRepository.insertRateResChgAccessTrace(mcpIpStatisticDto);
            } else {
                //결과가 N이고 rsltCd가 0000이 아닐 때
                if (response.success() && !"0000".equals(regSvcChgSelf.getRsltCd())) {
                    regSvcChgSelf.setSvcMsg(regSvcChgSelf.getRuleMsgSbst() == null ? regSvcChgSelf.getRsltMsg() : regSvcChgSelf.getRuleMsgSbst());
                }

                log.debug("RESULT_CODE:{} , RESULT_MSG:{}", regSvcChgSelf.getRsltCd(), regSvcChgSelf.getSvcMsg());

                //결과 isnert
                McpIpStatisticDto failDto = new McpIpStatisticDto();
                failDto.setPrcsMdlInd("Y25_RSV ERROR");
                failDto.setTrtmRsltSmst(req.getNcn());
                //failDto.setParameter("NCN["+req.getNcn()+"]CTN[" +req.getCtn() +"]USERID["+ userId+"]ResChgRateCd["+ soc+"]");
                failDto.setParameter("NCN[" + req.getNcn() + "]CTN[" + req.getCtn() + "]USERID[" + AuthenticationUtils.getUser()
                    .getUserId() + "]ResChgRateCd[" + req.getPlanSoc() + "]");
                failDto.setPrcsSbst("결과 실패:RsltCd[" + regSvcChgSelf.getRsltCd() + "]RsltMsg[" + regSvcChgSelf.getSvcMsg() + "]");
                ipstatisticService.insertAccessTrace(failDto);

                return FormResponse.of(ResSvcChgMessage.PRICE_SELF_SERVICE_ERROR, "요금제 변경 예약 중 오류가 발생했습니다.", null);
            }
            return FormResponse.of(ResSvcChgMessage.SUCCESS, regSvcChgSelf);
        } catch (Exception e) {
            log.error("[reservedPriceChange] failed: ncn={}", req.getNcn(), e);
            return FormResponse.of(ResSvcChgMessage.PRICE_SELF_SERVICE_ERROR, "요금제 변경 예약 중 오류가 발생했습니다.", null);
        }
    }
}
