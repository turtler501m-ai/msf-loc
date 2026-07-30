package com.ktmmobile.msf.domains.form.common.mplatform;


import java.net.SocketTimeoutException;
import java.util.HashMap;
import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ktmmobile.msf.commons.websecurity.web.util.RequestUtils;
import com.ktmmobile.msf.domains.externalclient.common.property.ExternalServiceProperties;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxSoapResponse;
import com.ktmmobile.msf.domains.form.common.exception.McpMplatFormException;
import com.ktmmobile.msf.domains.form.common.exception.SelfServiceException;
import com.ktmmobile.msf.domains.form.common.mplatform.provider.MplatFormOsstMockResponse;
import com.ktmmobile.msf.domains.form.common.mplatform.provider.MplatFormOsstResponseProvider;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.CommonXmlVO;
import com.ktmmobile.msf.domains.form.common.service.IpStatisticService;
import com.ktmmobile.msf.domains.form.common.util.NmcpServiceUtils;

import static com.ktmmobile.msf.domains.externalclient.common.code.ClientConst.SERVICE_NAME_MSP_PRX;
import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.MPLATFORM_RESPONEXML_EMPTY_EXCEPTION;

@Slf4j
@Service
@RequiredArgsConstructor
public class MsfMplatFormOsstWebServerAdapter {

    private static final String MSP_PRX_OSST_SERVICE_CALL_PATH = "/mPlatform/osstServiceCall.do";

    private final IpStatisticService ipStatisticService;
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

            String callUrl = getMspPrxOsstServiceCallUrl();
            String appEventCd = param == null ? "" : param.get("appEventCd");
            log.debug("[OsstWeb.callService] start: appEventCd={}, callUrl={}, timeout={}",
                appEventCd, callUrl, timeout);
            HashMap<String, String> pMplatform = this.saveMplateSvcLog(param);
            log.debug("[OsstWeb.callService] request prepared: appEventCd={}, parameterCount={}",
                appEventCd, pMplatform.size());

            // 로컬에서 강제로 성공 처리
            MplatFormOsstMockResponse mockResponse = osstResponseProvider.osstWebResponse(appEventCd);
            if (mockResponse.isLocalTest()) {
                log.debug("[OsstWeb.callService] LOCAL branch: appEventCd={}", appEventCd);
                if (mockResponse.isPassthroughSuccess()) {
                    if (vo != null) {
                        vo.setSuccess(true);
                    }
                    result = true;
                    return result;
                }
                responseXml = mockResponse.responseXml();
            } else {
                log.info("*** M-PlatForm osstServiceCall Connect Start *** appEventCd={}, callUrl={}", appEventCd, callUrl);
                // Use TOBE PRX OSST client; keep raw XML for existing CommonXmlVO parsing.
                MspPrxSoapResponse response = msfmcpOsstPrxService.callOsstService(pMplatform, timeout);
                responseXml = response.rawXml();
                // AS-IS: 직접 getURL 생성 후 전송하던 방식. getURL 생성은 MspPrxClient에서 공통 처리한다.
                // String getURL = this.getURL(pMplatform);
                // NameValuePair[] data = { new NameValuePair("getURL", getURL) };
                // MspPrxSoapResponse response = msfmcpOsstPrxService.callOsstService(pMplatform, getURL, timeout);
                //responseXml = HttpClientUtil.post(callUrl, data, "UTF-8", timeout);
                log.info("*** M-PlatForm osstServiceCall response length *** {}", responseXml == null ? 0 : responseXml.length());
                log.info("responseXml : " + responseXml);
            }

            log.debug("[OsstWeb.callService] response received: appEventCd={}, responseLength={}",
                appEventCd, responseXml == null ? 0 : responseXml.length());
            if (responseXml == null || responseXml.isEmpty()) {
                result = false;
                throw new McpMplatFormException(MPLATFORM_RESPONEXML_EMPTY_EXCEPTION);
            } else {
                if (vo != null) {
                    result = true;
                    vo.setResponseXml(responseXml);
                    vo.toResponseParse();
                    log.debug("[OsstWeb.callService] parse success: appEventCd={}, success={}, resultCode={}, globalNo={}",
                        appEventCd, vo.isSuccess(), vo.getResultCode(), vo.getGlobalNo());
                }
            }

        } catch (SelfServiceException e) {
            log.warn("[OsstWeb.callService] self-service error: appEventCd={}, resultCode={}, globalNo={}, message={}",
                param == null ? "" : param.get("appEventCd"), e.getResultCode(), e.getGlobalNo(), e.getMessage());
            // responseType값이 N이 아닌 경우
            throw e;
        } catch (SocketTimeoutException e) {
            throw e;
        } catch (McpMplatFormException e) {
            // responseXml이 빈값인 경우
            throw e;
        } catch (Exception e) {
            result = false;
        }

        return result;
    }

    private HashMap<String, String> saveMplateSvcLog(HashMap<String, String> param) {

        HashMap<String, String> tmpParm = param;

        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            tmpParm.put("ip", RequestUtils.getClientIp());
            tmpParm.put("url", request.getRequestURI());
            tmpParm.put("mdlInd", NmcpServiceUtils.getDeviceType());
        } catch (Exception e) {
            log.debug("saveMplateSvcLog 연동 정보 저장 오류={}", e.getMessage());
        }

        return tmpParm;
    }

    //private String getURL(HashMap<String, String> param) {
    //
    //    String result = "";
    //    Set<String> keySet = param.keySet();
    //
    //    for (String key: keySet) {
    //        if (!result.equals("")) {
    //            result = result.concat("&");
    //        }
    //        result = result.concat(key + "=" + param.get(key));
    //    }
    //
    //    try {
    //        result = URLEncoder.encode(result, "UTF-8");
    //    } catch (UnsupportedEncodingException e) {
    //        log.error("getURL UnsupportedEncodingException={}", e.getMessage());
    //    }
    //
    //    return result;
    //}

    private String getMspPrxOsstServiceCallUrl() {
        String baseUrl = externalServiceProperties.service(SERVICE_NAME_MSP_PRX).baseUrl();
        return StringUtils.trimTrailingCharacter(baseUrl, '/') + MSP_PRX_OSST_SERVICE_CALL_PATH;
    }

}
