package com.ktmmobile.msf.domains.form.common.mplatform;

import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxSoapResponse;
import com.ktmmobile.msf.domains.form.common.dto.McpRequestOsstDto;

@Component
public class MsfOsstResponseExtractor {

    public boolean supports(String appEventCd) {
        return StringUtils.isNotBlank(appEventCd);
    }

    public Optional<McpRequestOsstDto> extract(Map<String, String> params, MspPrxSoapResponse response) {
        String appEventCd = value(params, "appEventCd");
        if (!supports(appEventCd) || response == null) {
            return Optional.empty();
        }

        String mvnoOrdNo = firstNotBlank(
            payloadText(response, "mvnoOrdNo"),
            value(params, "resNo"),
            value(params, "mvnoOrdNo"),
            value(params, "mcnResNo"),
            value(params, "requestKey")
        );
        if (StringUtils.isBlank(mvnoOrdNo)) {
            return Optional.empty();
        }

        McpRequestOsstDto dto = new McpRequestOsstDto();
        dto.setMvnoOrdNo(mvnoOrdNo);
        dto.setOsstOrdNo(firstNotBlank(
            payloadText(response, "osstOrdNo"),
            payloadText(response, "outDto", "osstOrdNo"),
            value(params, "osstOrdNo")
        ));
        dto.setPrgrStatCd(resolvePrgrStatCd(appEventCd));
        dto.setRsltCd(resolveResultCode(response));
        dto.setRsltMsg(resolveResultMessage(response));
        dto.setNstepGlobalId(firstNotBlank(
            response.globalNo(),
            payloadText(response, "nstepGlobalId")
        ));
        dto.setPrdcChkNotiMsg(payloadText(response, "prdcChkNotiMsg"));
        dto.setIfType("WEB");
        return Optional.of(dto);
    }

    private String resolvePrgrStatCd(String appEventCd) {
        // FMC0, FMP0, FPC0 등 신규로 F 추가된 것들 F삭제
        // osst 연동 후 처리를 해주는 부분에 F로 시작하는 코드들은 반영안되어 있음
        if (appEventCd.startsWith("F") && appEventCd.length() >= 4) {
            return appEventCd.substring(1);
        }
        return appEventCd;
    }

    private String resolveResultCode(MspPrxSoapResponse response) {
        if (!"N".equals(response.responseType())) {
            return firstNotBlank(response.responseCode(), response.responseType());
        }
        return firstNotBlank(
            payloadText(response, "rsltCd"),
            payloadText(response, "outDto", "rsltCd"),
            payloadText(response, "outDto", "rslt"),
            response.responseCode(),
            response.responseType()
        );
    }

    private String resolveResultMessage(MspPrxSoapResponse response) {
        if (!"N".equals(response.responseType())) {
            return response.responseBasic();
        }
        return firstNotBlank(
            payloadText(response, "rsltMsg"),
            payloadText(response, "outDto", "rsltMsg"),
            response.responseBasic()
        );
    }

    private String payloadText(MspPrxSoapResponse response, String firstName, String... childNames) {
        return response.payloadText(firstName, childNames).orElse(null);
    }

    private String value(Map<String, String> params, String key) {
        return params == null ? null : params.get(key);
    }

    private String firstNotBlank(String... values) {
        for (String value: values) {
            if (StringUtils.isNotBlank(value)) {
                return value;
            }
        }
        return null;
    }
}
