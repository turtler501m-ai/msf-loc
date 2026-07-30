package com.ktmmobile.msf.domains.form.common.mplatform;


import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.NameValuePair;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tools.jackson.databind.JsonNode;
import tools.jackson.dataformat.xml.XmlMapper;

import com.ktmmobile.msf.domains.externalclient.common.property.ExternalServiceProperties;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxSoapResponse;
import com.ktmmobile.msf.domains.form.common.exception.McpMplatFormException;
import com.ktmmobile.msf.domains.form.common.exception.SelfServiceException;
import com.ktmmobile.msf.domains.form.common.mplatform.provider.MplatFormOsstMockResponse;
import com.ktmmobile.msf.domains.form.common.mplatform.provider.MplatFormOsstResponseProvider;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.CommonXmlVO;

import static com.ktmmobile.msf.domains.externalclient.common.code.ClientConst.SERVICE_NAME_MSP_PRX;
import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.MPLATFORM_RESPONEXML_EMPTY_EXCEPTION;

@Slf4j
@Service
@RequiredArgsConstructor
public class MsfMplatFormOsstServerAdapter {

    private static final String MSP_PRX_SIMPLE_OPEN_SERVICE_CALL_PATH = "/mPlatform/simpleOpenServiceCall.do";

    private final MsfMcpOsstPrxService msfmcpOsstPrxService;
    private final ExternalServiceProperties externalServiceProperties;
    private final MplatFormOsstResponseProvider osstResponseProvider;

    public boolean callService(HashMap<String, String> param, CommonXmlVO vo) throws SelfServiceException, SocketTimeoutException {
        return callService(param, vo, 10000);
    }

    public boolean callService(HashMap<String, String> param, CommonXmlVO vo, int timeout) throws SelfServiceException, SocketTimeoutException {
        boolean result = false;
        String responseXml = "";
        try {
            String callUrl = getMspPrxSimpleOpenServiceCallUrl();
            String appEventCd = param == null ? "" : param.get("appEventCd");

            NameValuePair[] data = convertParam(param);


            for (NameValuePair key: data) {
                log.debug(key.getName() + "#====>" + key.getValue());
            }


            //로컬에서 강제로 성공 처리
            MplatFormOsstMockResponse mockResponse = osstResponseProvider.simpleOpenResponse(appEventCd);
            if (mockResponse.isLocalTest()) {
                if (mockResponse.isPassthroughSuccess()) {
                    if (vo != null) {
                        vo.setSuccess(true);
                    }
                    result = true;
                    return result;
                }
                responseXml = mockResponse.responseXml();
            } else {
                log.info("*** M-PlatForm simpleOpenServiceCall Connect Start *** appEventCd={}, callUrl={}", appEventCd, callUrl);
                MspPrxSoapResponse response = msfmcpOsstPrxService.callSimpleOpenService(param, timeout);
                responseXml = response.rawXml();
                //responseXml = HttpClientUtil.post(callUrl, data, "UTF-8", timeout);
                log.info("*** M-PlatForm simpleOpenServiceCall response length *** {}", responseXml == null ? 0 : responseXml.length());
            }


            if (responseXml == null || responseXml.isEmpty()) {
                result = false;
                throw new McpMplatFormException(MPLATFORM_RESPONEXML_EMPTY_EXCEPTION);
            } else {
                if (vo != null) {
                    result = true;
                    vo.setResponseXml(responseXml);
                    vo.toResponseParse();
                }
            }

        } catch (SelfServiceException e) {
            log.error("SelfServiceException", e);
            throw e;
        } catch (SocketTimeoutException e) {
            log.error("SocketTimeoutException", e);
            throw e;
        } catch (McpMplatFormException e) {
            throw e;
        } catch (Exception e) {
            log.error("Exception", e);
        }

        return result;
    }

    public <T> T callService(Map<String, String> param, Class<T> clazz, int timeout) throws SelfServiceException, SocketTimeoutException {
        //boolean result = false;
        String responseXml = "";
        try {
            String callUrl = getMspPrxSimpleOpenServiceCallUrl();
            String appEventCd = param == null ? "" : param.get("appEventCd");

            NameValuePair[] data = convertParam(param);


            for (NameValuePair key: data) {
                log.debug(key.getName() + "#====>" + key.getValue());
            }


            //로컬에서 강제로 성공 처리
            MplatFormOsstMockResponse mockResponse = osstResponseProvider.simpleOpenDtoResponse(appEventCd);
            if (mockResponse.isLocalTest()) {
                responseXml = mockResponse.responseXml();
            } else {
                log.info("*** M-PlatForm simpleOpenServiceCall Connect Start *** appEventCd={}, callUrl={}", appEventCd, callUrl);
                MspPrxSoapResponse response = msfmcpOsstPrxService.callSimpleOpenService(param, timeout);
                responseXml = response.rawXml();
                //responseXml = HttpClientUtil.post(callUrl, data, "UTF-8", timeout);
                log.info("*** M-PlatForm simpleOpenServiceCall response length *** {}", responseXml == null ? 0 : responseXml.length());
            }

            if (responseXml == null || responseXml.isEmpty()) {
                //result = false;
                throw new McpMplatFormException(MPLATFORM_RESPONEXML_EMPTY_EXCEPTION);
            }

            XmlMapper xmlMapper = new XmlMapper();
            JsonNode root = xmlMapper.readTree(responseXml.getBytes());
            JsonNode outDtoNode = root.findValue("return");

            return xmlMapper.treeToValue(outDtoNode, clazz);

        } catch (SelfServiceException e) {
            log.error("SelfServiceException", e);
            throw e;
        } catch (SocketTimeoutException e) {
            log.error("SocketTimeoutException", e);
            throw e;
        } catch (McpMplatFormException e) {
            throw e;
        } catch (Exception e) {
            log.error("Exception", e);
        }
        return null;
    }

    private String getMspPrxSimpleOpenServiceCallUrl() {
        String baseUrl = externalServiceProperties.service(SERVICE_NAME_MSP_PRX).baseUrl();
        return StringUtils.trimTrailingCharacter(baseUrl, '/') + MSP_PRX_SIMPLE_OPEN_SERVICE_CALL_PATH;
    }

    private NameValuePair[] convertParam(Map<String, String> params) {
        NameValuePair[] data = new NameValuePair[params.size()];
        int i = 0;
        for (Map.Entry<String, String> entry: params.entrySet()) {
            String rtnStr = "";
            try {
                String value = entry.getValue() == null ? "" : entry.getValue();
                rtnStr = URLEncoder.encode(value, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                log.error("UnsupportedEncodingException", e);
            }

            data[i] = new NameValuePair(entry.getKey(), rtnStr);
            i++;
        }

        //result = result.concat(key + "=" + param.get(key));

        return data;
    }


}
