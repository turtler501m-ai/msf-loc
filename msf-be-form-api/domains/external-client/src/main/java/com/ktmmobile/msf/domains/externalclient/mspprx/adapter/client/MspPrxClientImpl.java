package com.ktmmobile.msf.domains.externalclient.mspprx.adapter.client;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.domains.externalclient.mspprx.adapter.client.httpclient.MspPrxHttpClient;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxFormRequest;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxJsonRequest;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxSoapResponse;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.ServiceAlterTraceId;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.ServiceAlterTraceRequest;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.port.in.ServiceAlterTraceRecorder;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.port.out.MspPrxClient;
import com.ktmmobile.msf.domains.externalclient.mspprx.support.exception.MspPrxClientException;

@Slf4j
@RequiredArgsConstructor
@Component
class MspPrxClientImpl implements MspPrxClient {

    private static final String SUCCESS_RESULT_CODE = "0000";
    private static final String ERROR_RESULT_CODE = "ERROR";
    private static final DateTimeFormatter TRACE_GROUP_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final Set<String> PARAMETER_EXCLUDED_NAMES = Set.of("xml", "svcPwd", "password", "pwd");

    private final MspPrxHttpClient httpClient;
    private final MspPrxSoapResponseParser responseParser;
    private final ServiceAlterTraceRecorder serviceAlterTraceRecorder;

    @Override
    public MspPrxSoapResponse callService(MspPrxFormRequest request) {
        return callWithTrace("callService", resolveTraceRequest("callService", request.serviceAlterTrace(), request.parameters()),
            () -> parse(httpClient.serviceCall(request.toGetUrlFormData())));
    }

    @Override
    public MspPrxSoapResponse callServiceJson(MspPrxJsonRequest request) {
        return callWithTrace("callServiceJson", resolveTraceRequest("callServiceJson", request.serviceAlterTrace(), request.properties()),
            () -> parse(httpClient.serviceCallJson(request.toJsonBody())));
    }

    @Override
    public MspPrxSoapResponse callOsstService(MspPrxFormRequest request) {
        return callWithTrace("callOsstService", resolveTraceRequest("callOsstService", request.serviceAlterTrace(), request.parameters()),
            () -> parse(httpClient.osstServiceCall(request.toGetUrlFormData())));
    }

    @Override
    public MspPrxSoapResponse callSimpleOpenService(MspPrxFormRequest request) {
        return callWithTrace("callSimpleOpenService", resolveTraceRequest("callSimpleOpenService", request.serviceAlterTrace(), request.parameters()),
            () -> parse(httpClient.simpleOpenServiceCall(request.toFormData())));
    }

    @Override
    public MspPrxSoapResponse callXmlOsstService(MspPrxFormRequest request) {
        return callWithTrace("callXmlOsstService", resolveTraceRequest("callXmlOsstService", request.serviceAlterTrace(), request.parameters()),
            () -> parse(httpClient.xmlOsstServiceCall(request.toFormData())));
    }

    @Override
    public MspPrxSoapResponse callXmlSelfService(MspPrxFormRequest request) {
        return callWithTrace("callXmlSelfService", resolveTraceRequest("callXmlSelfService", request.serviceAlterTrace(), request.parameters()),
            () -> parse(httpClient.xmlSelfServiceCall(request.toFormData())));
    }

    private MspPrxSoapResponse parse(String responseXml) {
        return responseParser.parse(responseXml);
    }

    private ServiceAlterTraceRequest resolveTraceRequest(
        String operation,
        ServiceAlterTraceRequest traceRequest,
        Map<String, ?> parameters
    ) {
        ServiceAlterTraceRequest source = traceRequest == null
            ? ServiceAlterTraceRequest.builder().build()
            : traceRequest;

        ServiceAlterTraceRequest resolvedTraceRequest = source.toBuilder()
            .ncn(valueOrDefault(source.getNcn(), firstText(parameters, "ncn", "Ncn", "svcCntrNo", "contractNum", "cntrNo")))
            .contractNum(valueOrDefault(source.getContractNum(), firstText(parameters, "contractNum", "svcCntrNo", "cntrNo", "ncn", "Ncn")))
            .subscriberNo(valueOrDefault(source.getSubscriberNo(),
                firstText(parameters, "subscriberNo", "ctn", "cntrMobileNo", "mobileNo", "tlphNo")))
            .eventCd(valueOrDefault(source.getEventCd(), firstText(parameters, "appEventCd", "eventCd")))
            .prcsMdlDivCd(valueOrDefault(source.getPrcsMdlDivCd(), prcsMdlDivCd(parameters)))
            .trtmRsltSbst(valueOrDefault(source.getTrtmRsltSbst(), firstText(parameters, "trtmRsltSbst", "trtmRsltSmst", "traceTitle", "traceName")))
            .aSocCode(valueOrDefault(source.getASocCode(),
                firstText(parameters, "aSocCode", "aSocCd", "fromSocCode", "fromSocCd", "nowPriceSocCode")))
            .tSocCode(valueOrDefault(source.getTSocCode(),
                firstText(parameters, "tSocCode", "tSocCd", "toSocCode", "toSocCd", "socCode", "socCd", "prodId")))
            .aSocAmt(amountOrDefault(source.getASocAmt(), firstText(parameters, "aSocAmt", "aSocAmnt", "fromSocAmt", "fromSocAmnt")))
            .tSocAmt(amountOrDefault(source.getTSocAmt(), firstText(parameters, "tSocAmt", "tSocAmnt", "toSocAmt", "toSocAmnt", "socAmt", "socAmnt")))
            .parameter(valueOrDefault(source.getParameter(), traceParameter(parameters)))
            .build();

        if (traceRequest == null) {
            log.warn("serviceAlterTrace is not configured. default serviceAlterTrace will be recorded. operation={}, eventCd={}, prcsMdlDivCd={}",
                operation, resolvedTraceRequest.getEventCd(), resolvedTraceRequest.getPrcsMdlDivCd());
        }
        return resolvedTraceRequest;
    }

    private MspPrxSoapResponse callWithTrace(
        String operation,
        ServiceAlterTraceRequest traceRequest,
        PrxCall prxCall
    ) {
        if (traceRequest == null) {
            log.warn("serviceAlterTrace is not configured. operation={}", operation);
        }

        try {
            MspPrxSoapResponse response = prxCall.call();
            return recordSuccess(operation, traceRequest, response);
        } catch (RuntimeException e) {
            recordFailure(operation, traceRequest, e);
            throw e;
        }
    }

    private MspPrxSoapResponse recordSuccess(
        String operation,
        ServiceAlterTraceRequest traceRequest,
        MspPrxSoapResponse response
    ) {
        if (traceRequest == null) {
            return response;
        }

        ServiceAlterTraceRequest enrichedTraceRequest = traceRequest.toBuilder()
            .globalNo(valueOrDefault(traceRequest.getGlobalNo(), response.globalNo()))
            .rsltCd(valueOrDefault(traceRequest.getRsltCd(), successResultCode(response)))
            .prcsSbst(valueOrDefault(traceRequest.getPrcsSbst(), response.responseBasic()))
            .build();
        return response.withServiceAlterTraceId(recordTrace(operation, enrichedTraceRequest));
    }

    private void recordFailure(String operation, ServiceAlterTraceRequest traceRequest, RuntimeException e) {
        if (traceRequest == null) {
            return;
        }

        ServiceAlterTraceRequest enrichedTraceRequest = traceRequest.toBuilder()
            .globalNo(valueOrDefault(traceRequest.getGlobalNo(), globalNo(e)))
            .rsltCd(valueOrDefault(traceRequest.getRsltCd(), resultCode(e)))
            .prcsSbst(valueOrDefault(traceRequest.getPrcsSbst(), e.getMessage()))
            .build();
        recordTrace(operation, enrichedTraceRequest);
    }

    private ServiceAlterTraceId recordTrace(String operation, ServiceAlterTraceRequest traceRequest) {
        try {
            ServiceAlterTraceId serviceAlterTraceId = serviceAlterTraceRecorder.recordTrace(traceRequest);
            if (serviceAlterTraceId == null) {
                log.warn("serviceAlterTrace was not inserted. operation={}, eventCd={}, prcsMdlDivCd={}",
                    operation, traceRequest.getEventCd(), traceRequest.getPrcsMdlDivCd());
            }
            return serviceAlterTraceId;
        } catch (RuntimeException e) {
            log.warn("Failed to record serviceAlterTrace. operation={}, eventCd={}, prcsMdlDivCd={}",
                operation, traceRequest.getEventCd(), traceRequest.getPrcsMdlDivCd(), e);
            return null;
        }
    }

    private String successResultCode(MspPrxSoapResponse response) {
        if (hasText(response.responseCode())) {
            return response.responseCode();
        }
        return response.success() ? SUCCESS_RESULT_CODE : null;
    }

    private String resultCode(RuntimeException e) {
        if (e instanceof MspPrxClientException prxException && hasText(prxException.responseCode())) {
            return prxException.responseCode();
        }
        return ERROR_RESULT_CODE;
    }

    private String globalNo(RuntimeException e) {
        if (e instanceof MspPrxClientException prxException) {
            return prxException.globalNo();
        }
        return null;
    }

    private String valueOrDefault(String value, String defaultValue) {
        return hasText(value) ? value : defaultValue;
    }

    private Integer amountOrDefault(Integer value, String defaultValue) {
        if (value != null && value != 0) {
            return value;
        }
        return parseInteger(defaultValue, value);
    }

    private Integer parseInteger(String value, Integer defaultValue) {
        if (!hasText(value)) {
            return defaultValue;
        }

        try {
            return Integer.valueOf(value.replace(",", ""));
        } catch (NumberFormatException _) {
            return defaultValue;
        }
    }

    private String firstText(Map<String, ?> parameters, String... names) {
        if (parameters == null || parameters.isEmpty()) {
            return null;
        }

        for (String name: names) {
            Object value = parameters.get(name);
            if (value != null && hasText(value.toString())) {
                return value.toString();
            }
        }
        return null;
    }

    private String prcsMdlDivCd(Map<String, ?> parameters) {
        String prcsMdlDivCd = firstText(parameters,
            "prcsMdlDivCd", "prcsMdlDiv", "prcsMdlInd", "trtMdlDiv", "traceGroupId", "traceId");
        if (hasText(prcsMdlDivCd)) {
            return prcsMdlDivCd;
        }
        return "PRX" + LocalDateTime.now().format(TRACE_GROUP_FORMATTER);
    }

    private String traceParameter(Map<String, ?> parameters) {
        if (parameters == null || parameters.isEmpty()) {
            return null;
        }

        StringBuilder parameter = new StringBuilder();
        for (String name: traceParameterNames(parameters)) {
            Object value = parameters.get(name);
            if (value == null || PARAMETER_EXCLUDED_NAMES.contains(name)) {
                continue;
            }
            parameter.append(name).append("[").append(value).append("]");
        }
        return parameter.isEmpty() ? null : parameter.toString();
    }

    private Set<String> traceParameterNames(Map<String, ?> parameters) {
        Set<String> names = new LinkedHashSet<>();
        addIfPresent(names, parameters, "combSvcNoCd", "combSrchId", "svcIdfyNo", "sexCd", "cmbStndSvcNo", "sameCustKtRetvYn");
        addIfPresent(names, parameters, "SCNT", "FCNT", "successCnt", "failCnt");
        addIfPresent(names, parameters, "ncn", "Ncn", "contractNum", "svcCntrNo", "cntrNo", "ctn", "subscriberNo");
        addIfPresent(names, parameters, "appEventCd", "eventCd", "aSocCode", "tSocCode", "socCode", "prodId");
        names.addAll(parameters.keySet());
        return names;
    }

    private void addIfPresent(Set<String> names, Map<String, ?> parameters, String... candidates) {
        for (String candidate: candidates) {
            if (parameters.containsKey(candidate)) {
                names.add(candidate);
            }
        }
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    @FunctionalInterface
    private interface PrxCall {

        MspPrxSoapResponse call();
    }
}
