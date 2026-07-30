package com.ktmmobile.msf.domains.form.form.servicechange.service;

import java.util.HashMap;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import com.ktmmobile.msf.commons.common.context.business.BusinessContextBoundary;
import com.ktmmobile.msf.commons.common.context.business.BusinessContextHolder;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxFormRequest;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxSoapResponse;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.port.out.MspPrxClient;
import com.ktmmobile.msf.domains.externalclient.mspprx.domain.code.MplatformServiceType;
import com.ktmmobile.msf.domains.externalclient.mspprx.support.util.XmlConvertUtils;
import com.ktmmobile.msf.domains.form.common.code.ResSvcChgMessage;
import com.ktmmobile.msf.domains.form.common.dto.McpIpStatisticDto;
import com.ktmmobile.msf.domains.form.common.dto.MplatFormXmlSelfcareRequest;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMcpOsstPrxService;
import com.ktmmobile.msf.domains.form.common.service.IpStatisticService;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.PricePlanReqDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.PricePlanX89ResDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.PricePlanX90ResDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.PricePlanY02ResDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class MsfPricePlanServiceImpl {

    private final MsfMcpOsstPrxService msfMcpOsstPrxService;
    private final IpStatisticService ipStatisticService;
    private final MspPrxClient mspPrxClient;
    private final ObjectMapper objectMapper;

    //@BusinessContextBoundary
    public FormResponse<PricePlanY02ResDto> currentPrice(PricePlanReqDto req) {
        //BusinessContextHolder.setParentScanId(req.getParentScanId());
        if (!hasValidKey(req)) {
            log.warn("[currentPrice] invalid lookup key: ncn={}, custId={}, ctnPresent={}",
                req != null ? StringUtil.NVL(req.getNcn(), "") : "",
                req != null ? StringUtil.NVL(req.getCustId(), "") : "",
                req != null && !"".equals(normalizePhone(req.getCtn()))
            );
            return FormResponse.of(ResSvcChgMessage.PRICE_CHANGE_REQUEST_INVALID);
        }
        log.debug("[currentPrice] start: ncn={}, ctn={}, custId={}", req.getNcn(), req.getCtn(), req.getCustId());

        try {
            // 가입중인 요금제 조회(Y02)
            MplatFormXmlSelfcareRequest mplatFormXmlSelfcareRequest = MplatFormXmlSelfcareRequest
                .builder()
                .ctn(req.getCtn())
                .custId(req.getCustId())
                .ncn(req.getNcn())
                .build();
            MspPrxSoapResponse selfResult = msfMcpOsstPrxService.callXmlSelfService(List.of(),
                MplatformServiceType.Y02,
                mplatFormXmlSelfcareRequest);

            return FormResponse.of(ResSvcChgMessage.SUCCESS, XmlConvertUtils.xmlReturnParser(selfResult.rawXml(), PricePlanY02ResDto.class));
        } catch (Exception e) {
            log.error("[reservedPrice] failed: ncn={}", req.getNcn(), e);
            return FormResponse.of(ResSvcChgMessage.PRICE_SELF_SERVICE_ERROR, "가입중인 요금제 조회 중 오류가 발생했습니다.", null);
        }
    }

    //@BusinessContextBoundary
    public FormResponse<PricePlanX89ResDto> reservedPrice(PricePlanReqDto req) {
        //BusinessContextHolder.setParentScanId(req.getParentScanId());
        if (!hasValidKey(req)) {
            log.warn("[reservedPrice] invalid lookup key: ncn={}, custId={}, ctnPresent={}",
                req != null ? StringUtil.NVL(req.getNcn(), "") : "",
                req != null ? StringUtil.NVL(req.getCustId(), "") : "",
                req != null && !"".equals(normalizePhone(req.getCtn()))
            );
            return FormResponse.of(ResSvcChgMessage.PRICE_CHANGE_REQUEST_INVALID);
        }
        log.debug("[reservedPrice] start: ncn={}, ctn={}, custId={}", req.getNcn(), req.getCtn(), req.getCustId());

        try {
            // 요금제 변경 예약 조회(X89)
            HashMap<String, String> paramMap = objectMapper.convertValue(req, HashMap.class);
            paramMap.put("appEventCd", "X89");
            MspPrxSoapResponse mspResult = mspPrxClient.callService(MspPrxFormRequest.builder()
                .parameters(paramMap)
                .build());
            String rawXml = mspResult.rawXml();
            PricePlanX89ResDto res = XmlConvertUtils.xmlReturnParser(rawXml, PricePlanX89ResDto.class);

            return FormResponse.of(ResSvcChgMessage.SUCCESS, res);
        } catch (Exception e) {
            log.error("[reservedPrice] failed: ncn={}", req.getNcn(), e);
            return FormResponse.of(ResSvcChgMessage.PRICE_SELF_SERVICE_ERROR, "요금제 변경 예약 조회 중 오류가 발생했습니다.", null);
        }
    }

    //@BusinessContextBoundary
    //public FormResponse<PricePlanX88ResDto> reservedPriceChange(PricePlanReqDto req) {
    //    BusinessContextHolder.setParentScanId(req != null ? req.getParentScanId() : null);
    //    if (!hasValidKey(req)) {
    //        log.warn("[reservedPriceChange] invalid lookup key: ncn={}, custId={}, ctnPresent={}",
    //            req != null ? StringUtil.NVL(req.getNcn(), "") : "",
    //            req != null ? StringUtil.NVL(req.getCustId(), "") : "",
    //            req != null && !"".equals(normalizePhone(req.getCtn()))
    //        );
    //        return FormResponse.of(ResSvcChgMessage.PRICE_CHANGE_REQUEST_INVALID);
    //    }
    //    log.debug("[reservedPriceChange] start: ncn={}, ctn={}, custId={}", req.getNcn(), req.getCtn(), req.getCustId());
    //
    //    try {
    //        // 요금제 변경 예약 (X88)
    //        HashMap<String, String> paramMap = objectMapper.convertValue(req, HashMap.class);
    //        paramMap.put("appEventCd", "X88");
    //        MspPrxSoapResponse mspResult = mspPrxClient.callService(MspPrxFormRequest.builder()
    //            .parameters(paramMap)
    //            .build());
    //        String rawXml = mspResult.rawXml();
    //        PricePlanX88ResDto res = XmlConvertUtils.xmlReturnParser(rawXml, PricePlanX88ResDto.class);
    //
    //        MplatformBase.CommHeader commHeader = res == null ? null : res.getCommHeader();
    //        if (commHeader == null || !commHeader.isSuccess()) {
    //            String responseCode = commHeader == null ? "" : StringUtil.NVL(commHeader.getResponseCode(), "");
    //            String responseBasic = commHeader == null ? "" : StringUtil.NVL(commHeader.getResponseBasic(), "");
    //            String failCode = !"".equals(responseCode) ? responseCode : ResSvcChgMessage.PRICE_SELF_SERVICE_ERROR.getCode();
    //            String failMessage = !"".equals(responseBasic) ? responseBasic : ResSvcChgMessage.PRICE_SELF_SERVICE_ERROR.getMessage();
    //
    //            log.warn("[reservedPriceChange] X88 failed: ncn={}, responseCode={}, responseBasic={}",
    //                req.getNcn(), failCode, failMessage);
    //            return FormResponse.of(failCode, failMessage, res);
    //        }
    //
    //        return FormResponse.of(ResSvcChgMessage.SUCCESS, res);
    //    } catch (Exception e) {
    //        log.error("[reservedPriceChange] failed: ncn={}", req.getNcn(), e);
    //        return FormResponse.of(ResSvcChgMessage.PRICE_SELF_SERVICE_ERROR, "요금제 변경 예약 중 오류가 발생했습니다.", null);
    //    }
    //}

    @BusinessContextBoundary
    public FormResponse<PricePlanX90ResDto> reservedPriceCancel(PricePlanReqDto req) {
        BusinessContextHolder.setParentScanId(req != null ? req.getParentScanId() : null);
        if (!hasValidKey(req)) {
            log.warn("[reservedPriceCancel] invalid lookup key: ncn={}, custId={}, ctnPresent={}",
                req != null ? StringUtil.NVL(req.getNcn(), "") : "",
                req != null ? StringUtil.NVL(req.getCustId(), "") : "",
                req != null && !"".equals(normalizePhone(req.getCtn()))
            );
            return FormResponse.of(ResSvcChgMessage.PRICE_CHANGE_REQUEST_INVALID);
        }
        log.debug("[reservedPriceCancel] start: ncn={}, ctn={}, custId={}", req.getNcn(), req.getCtn(), req.getCustId());

        try {
            // 요금제 변경 예약 취소 (X90)
            HashMap<String, String> paramMap = objectMapper.convertValue(req, HashMap.class);
            paramMap.put("appEventCd", "X90");
            MspPrxSoapResponse mspResult = mspPrxClient.callService(MspPrxFormRequest.builder()
                .parameters(paramMap)
                .build());
            String rawXml = mspResult.rawXml();
            PricePlanX90ResDto res = XmlConvertUtils.xmlReturnParser(rawXml, PricePlanX90ResDto.class);
            if (res.getCommHeader().isSuccess()) {
                McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
                mcpIpStatisticDto.setSvcCntrNo(req.getNcn());
                mcpIpStatisticDto.setMobileNo(req.getCtn());
                String rateResChgSeq = ipStatisticService.selectRateResChgAccessTrace(mcpIpStatisticDto);
                log.debug("[reservedPriceCancel] rateResChgSeq: {}", rateResChgSeq);
                if (rateResChgSeq != null) {
                    ipStatisticService.deleteRateResChgAccessTrace(rateResChgSeq);
                }
            }

            return FormResponse.of(ResSvcChgMessage.SUCCESS, res);
        } catch (Exception e) {
            log.error("[reservedPriceCancel] failed: ncn={}", req.getNcn(), e);
            return FormResponse.of(ResSvcChgMessage.PRICE_SELF_SERVICE_ERROR, "요금제 변경 예약 취소 중 오류가 발생했습니다.", null);
        }
    }

    private String normalizePhone(String value) {
        return StringUtil.NVL(value, "").replaceAll("[^0-9]", "");
    }

    private boolean hasValidKey(PricePlanReqDto req) {
        return req != null
            && (!"".equals(StringUtil.NVL(req.getNcn(), ""))
            || !"".equals(StringUtil.NVL(req.getCustId(), ""))
            || !"".equals(normalizePhone(req.getCtn())));
    }

}
