package com.ktmmobile.msf.domains.externalclient.mspprx.application.dto;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Singular;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.domains.externalclient.mspprx.domain.code.MplatformOsstServiceType;
import com.ktmmobile.msf.domains.externalclient.mspprx.support.util.XmlConvertUtils;

@Builder
public record MspPrxFormRequest(
    @Singular("parameter")
    Map<String, String> parameters,
    ServiceAlterTraceRequest serviceAlterTrace
) {

    private static final String GET_URL_PARAMETER_NAME = "getURL";

    public MspPrxFormRequest {
        Map<String, String> filteredParameters = new LinkedHashMap<>();
        if (parameters != null) {
            parameters.forEach((key, value) -> {
                if (StringUtils.hasText(key) && value != null) {
                    filteredParameters.put(key, value);
                }
            });
        }
        parameters = Collections.unmodifiableMap(filteredParameters);
    }

    public MultiValueMap<String, String> toFormData() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        parameters.forEach((key, value) -> {
            if (StringUtils.hasText(key) && value != null) {
                form.add(key, value);
            }
        });
        return form;
    }

    public MultiValueMap<String, String> toGetUrlFormData() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add(GET_URL_PARAMETER_NAME, URLEncoder.encode(toQueryString(), StandardCharsets.UTF_8));
        return form;
    }

    private String toQueryString() {
        StringBuilder queryString = new StringBuilder();
        parameters.forEach((key, value) -> {
            if (!queryString.isEmpty()) {
                queryString.append("&");
            }
            queryString.append(key).append("=").append(value);
        });
        return queryString.toString();
    }

    public static MspPrxFormRequest createXmlRequest(
        List<Object> requestDtoList,
        String eventCd
    ) {
        return createXmlRequest(requestDtoList, MplatformOsstServiceType.findByEventCd(eventCd), null);
    }

    public static MspPrxFormRequest createXmlRequest(
        List<Object> requestDtoList,
        String eventCd,
        ServiceAlterTraceRequest serviceAlterTrace
    ) {
        return createXmlRequest(requestDtoList, MplatformOsstServiceType.findByEventCd(eventCd), serviceAlterTrace);
    }

    public static MspPrxFormRequest createXmlRequest(
        List<Object> requestDtoList,
        String appAgncCd,
        MplatformOsstServiceType serviceType
    ) {
        return createXmlRequest(requestDtoList, serviceType, appAgncCd, null);
    }

    public static MspPrxFormRequest createXmlRequest(
        List<Object> requestDtoList,
        MplatformOsstServiceType serviceType
    ) {
        return createXmlRequest(requestDtoList, serviceType, null);
    }

    public static MspPrxFormRequest createXmlRequest(
        List<Object> requestDtoList,
        MplatformOsstServiceType serviceType,
        ServiceAlterTraceRequest serviceAlterTrace
    ) {
        StringBuilder xml = new StringBuilder();

        for (Object obj: requestDtoList) {
            xml.append(XmlConvertUtils.convertObjectToXml(obj));
        }

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("xml", xml.toString());
        paramMap.put("appEventCd", serviceType.getEventCd());
        paramMap.put("serviceName", serviceType.getServiceName());
        paramMap.put("serviceInfo", serviceType.getServiceInfo());
        paramMap.put("serviceVo", serviceType.getServiceVo());

        return MspPrxFormRequest.builder()
            .parameters(paramMap)
            .serviceAlterTrace(serviceAlterTrace)
            .build();
    }

    public static MspPrxFormRequest createXmlRequest(
        List<Object> requestDtoList,
        MplatformOsstServiceType serviceType,
        String appAgncCd,
        ServiceAlterTraceRequest serviceAlterTrace
    ) {
        StringBuilder xml = new StringBuilder();

        for (Object obj: requestDtoList) {
            xml.append(XmlConvertUtils.convertObjectToXml(obj));
        }

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("xml", xml.toString());
        paramMap.put("appEventCd", serviceType.getEventCd());
        paramMap.put("serviceName", serviceType.getServiceName());
        paramMap.put("serviceInfo", serviceType.getServiceInfo());
        paramMap.put("serviceVo", serviceType.getServiceVo());

        if (appAgncCd != null) {
            paramMap.put("appAgncCd", appAgncCd);
        }

        return MspPrxFormRequest.builder()
            .parameters(paramMap)
            .serviceAlterTrace(serviceAlterTrace)
            .build();
    }

    public static MspPrxFormRequest createXmlSelfRequest(
        Map<String, String> paramMap,
        ServiceAlterTraceRequest serviceAlterTrace
    ) {
        return MspPrxFormRequest.builder()
            .parameters(paramMap)
            .serviceAlterTrace(serviceAlterTrace)
            .build();
    }

}
