package com.ktmmobile.msf.domains.form.form.servicechange.service;

import java.util.HashMap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import com.ktmmobile.msf.commons.common.context.business.BusinessContextBoundary;
import com.ktmmobile.msf.commons.common.context.business.BusinessContextHolder;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxFormRequest;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxSoapResponse;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.port.out.MspPrxClient;
import com.ktmmobile.msf.domains.externalclient.mspprx.support.util.XmlConvertUtils;
import com.ktmmobile.msf.domains.form.common.code.ResSvcChgMessage;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.UnpausePcsLostResponse;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.UnpauseProcessRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.UnpauseProcessResponse;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.UnpauseRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.UnpauseResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class MsfSvgUnpauseService {

    private final static String APIMENUNM = "분실복구/일시정지해제 신청";

    private final MspPrxClient mspPrxClient;
    private final ObjectMapper objectMapper;

    /**
     * unpauseCheck
     * 일시정지해제가능여부조회(X28) → 분실신고이력조회(분실신고가능여부)(X33)
     */
    public FormResponse<UnpauseResponse> unpauseCheck(UnpauseRequest req) {
        if (!hasValidKey(req)) {
            log.warn("[unpauseCheck] invalid lookup key: ncn={}, custId={}, ctnPresent={}",
                req != null ? StringUtil.NVL(req.getNcn(), "") : "",
                req != null ? StringUtil.NVL(req.getCustId(), "") : "",
                req != null && !"".equals(normalizePhone(req.getCtn()))
            );
            return FormResponse.of(ResSvcChgMessage.CHANGE_REQUEST_INVALID);
        }

        try {
            // 일시정지해제가능여부조회(X28)
            HashMap<String, String> paramMap = objectMapper.convertValue(req, HashMap.class);
            paramMap.put("appEventCd", "X28");
            MspPrxSoapResponse mspResult = mspPrxClient.callService(MspPrxFormRequest.builder()
                .parameters(paramMap)
                .build());
            String rawXml = mspResult.rawXml();
            UnpauseResponse res = XmlConvertUtils.xmlReturnParser(rawXml, UnpauseResponse.class);

            String rsltInd = "N";
            if (res.getOutDto() == null) {
                return FormResponse.of(ResSvcChgMessage.SUCCESS, res);
            }
            rsltInd = res.getOutDto().getRsltInd();

            // 분실신고이력조회(분실신고가능여부)(X33)
            if ("N".equals(rsltInd)) {
                HashMap<String, String> paramMap33 = objectMapper.convertValue(req, HashMap.class);
                paramMap33.put("appEventCd", "X33");
                MspPrxSoapResponse mspResult33 = mspPrxClient.callService(MspPrxFormRequest.builder()
                    .parameters(paramMap33)
                    .build());
                String rawXml33 = mspResult33.rawXml();
                UnpausePcsLostResponse res33 = XmlConvertUtils.xmlReturnParser(rawXml33, UnpausePcsLostResponse.class);

                if (res33.getOutDto() != null && "U".equals(res33.getOutDto().getRunMode())) {
                    rsltInd = "Y";
                    res.getOutDto().setRsltInd(rsltInd);
                }
            }

            return FormResponse.of(ResSvcChgMessage.SUCCESS, res);

        } catch (Exception e) {
            log.error("[unpauseCheck] failed: ncn={}", req.getNcn(), e);
            return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, APIMENUNM + " 가능 여부 조회 중 오류가 발생했습니다.", null);
        }
    }

    /**
     * [TEST] pauseCheck
     * 일시정지가능여부조회(X27)
     */
    //public FormResponse<UnpauseResponse> testPauseCheck(UnpauseRequest req) {
    //    if (!hasValidKey(req)) {
    //        log.warn("[pauseCheck] invalid lookup key: ncn={}, custId={}, ctnPresent={}",
    //            req != null ? StringUtil.NVL(req.getNcn(), "") : "",
    //            req != null ? StringUtil.NVL(req.getCustId(), "") : "",
    //            req != null && !"".equals(normalizePhone(req.getCtn()))
    //        );
    //        return FormResponse.of(ResSvcChgMessage.CHANGE_REQUEST_INVALID);
    //    }
    //
    //    try {
    //        HashMap<String, String> paramMap = objectMapper.convertValue(req, HashMap.class);
    //        paramMap.put("appEventCd", "X27");
    //        MspPrxSoapResponse mspResult = mspPrxClient.callService(MspPrxFormRequest.builder()
    //            .parameters(paramMap)
    //            .build());
    //        String rawXml = mspResult.rawXml();
    //        UnpauseResponse res = XmlConvertUtils.xmlReturnParser(rawXml, UnpauseResponse.class);
    //
    //        return FormResponse.of(ResSvcChgMessage.SUCCESS, res);
    //    } catch (Exception e) {
    //        log.error("[pauseCheck] failed: ncn={}", req.getNcn(), e);
    //        return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, APIMENUNM + " 가능 여부 조회 중 오류가 발생했습니다.1", null);
    //    }
    //}

    /**
     * [TEST] pauseProcess
     * 일시정지(X29)
     */
    public FormResponse<UnpauseResponse> testPauseProcess(UnpauseRequest req) {
        if (!hasValidKey(req)) {
            log.warn("[pauseProcess] invalid lookup key: ncn={}, custId={}, ctnPresent={}",
                req != null ? StringUtil.NVL(req.getNcn(), "") : "",
                req != null ? StringUtil.NVL(req.getCustId(), "") : "",
                req != null && !"".equals(normalizePhone(req.getCtn()))
            );
            return FormResponse.of(ResSvcChgMessage.CHANGE_REQUEST_INVALID);
        }

        try {
            HashMap<String, String> paramMap = objectMapper.convertValue(req, HashMap.class);
            paramMap.put("appEventCd", "X29");
            MspPrxSoapResponse mspResult = mspPrxClient.callService(MspPrxFormRequest.builder()
                .parameters(paramMap)
                .build());
            String rawXml = mspResult.rawXml();
            UnpauseResponse res = XmlConvertUtils.xmlReturnParser(rawXml, UnpauseResponse.class);

            return FormResponse.of(ResSvcChgMessage.SUCCESS, res);
        } catch (Exception e) {
            log.error("[pauseCheck] failed: ncn={}", req.getNcn(), e);
            return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, APIMENUNM + " 가능 여부 조회 중 오류가 발생했습니다.2", null);
        }
    }

    /**
     * unpauseProcess
     * 일시정지해제가능여부조회(X28)
     *   Y→일시정지해제신청(X30)
     *   N→분실신고이력조회(분실신고가능여부)(X33)
     *      →분실신고 취소신청(X35)
     */
    @BusinessContextBoundary
    public FormResponse<UnpauseProcessResponse> unpauseProcess(UnpauseProcessRequest req) {
        log.warn("[unpauseProcess] invalid lookup key: ncn={}, custId={}, ctnPresent={}",
            req != null ? StringUtil.NVL(req.getNcn(), "") : "",
            req != null ? StringUtil.NVL(req.getCustId(), "") : "",
            req != null ? StringUtil.NVL(req.getCtn(), "") : ""
        );

        try {
            // 일시정지해제가능여부조회(X28)
            HashMap<String, String> paramMap = objectMapper.convertValue(req, HashMap.class);
            paramMap.put("appEventCd", "X28");
            MspPrxSoapResponse mspResult = mspPrxClient.callService(MspPrxFormRequest.builder()
                .parameters(paramMap)
                .build());
            String rawXml = mspResult.rawXml();
            UnpauseResponse res = XmlConvertUtils.xmlReturnParser(rawXml, UnpauseResponse.class);

            String rsltInd = "N";
            if (res.getOutDto() == null) {
                return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, "분실복구/일시정지해제 신청 처리 중 오류가 발생했습니다.", null);
            }
            rsltInd = res.getOutDto().getRsltInd();

            // 일시정지해제신청(X30)
            if ("Y".equals(rsltInd)) {
                BusinessContextHolder.setParentScanId(req != null ? req.getParentScanId() : null);
                HashMap<String, String> paramMap30 = objectMapper.convertValue(req, HashMap.class);
                paramMap30.put("appEventCd", "X30");
                MspPrxSoapResponse mspResult30 = mspPrxClient.callService(MspPrxFormRequest.builder()
                    .parameters(paramMap30)
                    .build());
                String rawXml30 = mspResult30.rawXml();
                UnpauseProcessResponse res30 = XmlConvertUtils.xmlReturnParser(rawXml30, UnpauseProcessResponse.class);
                if ("E".equals(res30.getCommHeader().getResponseType())) {
                    // ITL_999_PWD0001 비밀번호 오류-비밀번호가 틀렸습니다. 다시 입력해 주십시요
                    return FormResponse.of(ResSvcChgMessage.CHANGE_PROCESS_ERROR, res30);
                }
                return FormResponse.of(ResSvcChgMessage.SUCCESS, res30);
            }

            // 분실신고이력조회(분실신고가능여부)(X33)
            HashMap<String, String> paramMap33 = objectMapper.convertValue(req, HashMap.class);
            paramMap33.put("appEventCd", "X33");
            MspPrxSoapResponse mspResult33 = mspPrxClient.callService(MspPrxFormRequest.builder()
                .parameters(paramMap33)
                .build());
            String rawXml33 = mspResult33.rawXml();
            UnpausePcsLostResponse res33 = XmlConvertUtils.xmlReturnParser(rawXml33, UnpausePcsLostResponse.class);

            // 분실신고 취소신청(X35)
            if (res33.getOutDto() != null && "U".equals(res33.getOutDto().getRunMode())) {
                BusinessContextHolder.setParentScanId(req != null ? req.getParentScanId() : null);
                HashMap<String, String> paramMap35 = objectMapper.convertValue(req, HashMap.class);
                paramMap35.put("appEventCd", "X35");
                MspPrxSoapResponse mspResult35 = mspPrxClient.callService(MspPrxFormRequest.builder()
                    .parameters(paramMap35)
                    .build());
                String rawXml35 = mspResult35.rawXml();
                UnpauseProcessResponse res35 = XmlConvertUtils.xmlReturnParser(rawXml35, UnpauseProcessResponse.class);
                if ("E".equals(res35.getCommHeader().getResponseType())) {
                    // ITL_999_PWD0001 비밀번호 오류-비밀번호가 틀렸습니다. 다시 입력해 주십시요
                    return FormResponse.of(ResSvcChgMessage.CHANGE_PROCESS_ERROR, res35);
                }
                return FormResponse.of(ResSvcChgMessage.SUCCESS, res35);
            }

            return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, "분실복구/일시정지해제 신청 처리 중 오류가 발생했습니다..", null);

        } catch (Exception e) {
            log.error("[unpauseProcess] failed: ncn={}", req.getNcn(), e);
            return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, "분실복구/일시정지해제 신청 처리 중 오류가 발생했습니다.", null);
        }
    }


    // -- UTIL

    private String normalizePhone(String value) {
        return StringUtil.NVL(value, "").replaceAll("[^0-9]", "");
    }

    private boolean hasValidKey(UnpauseRequest req) {
        return req != null
            && (!"".equals(StringUtil.NVL(req.getNcn(), ""))
            || !"".equals(StringUtil.NVL(req.getCustId(), ""))
            || !"".equals(normalizePhone(req.getCtn())));
    }

}
