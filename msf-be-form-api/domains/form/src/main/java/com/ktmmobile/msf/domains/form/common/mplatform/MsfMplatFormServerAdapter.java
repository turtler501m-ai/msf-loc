package com.ktmmobile.msf.domains.form.common.mplatform;


import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tools.jackson.databind.JsonNode;
import tools.jackson.dataformat.xml.XmlMapper;

import com.ktmmobile.msf.commons.websecurity.web.util.RequestUtils;
import com.ktmmobile.msf.domains.externalclient.common.property.ExternalServiceProperties;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxFormRequest;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxSoapResponse;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.port.out.MspPrxClient;
import com.ktmmobile.msf.domains.form.common.exception.McpMplatFormException;
import com.ktmmobile.msf.domains.form.common.exception.SelfServiceException;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MpBaseRequestSpec;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.CommonXmlNoSelfServiceException;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.CommonXmlVO;
import com.ktmmobile.msf.domains.form.common.service.IpStatisticService;
import com.ktmmobile.msf.domains.form.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.domains.form.common.util.XmlParse;

import static com.ktmmobile.msf.domains.externalclient.common.code.ClientConst.SERVICE_NAME_MSP_PRX;
import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.MPLATFORM_RESPONEXML_EMPTY_EXCEPTION;

@Slf4j
@Service
@RequiredArgsConstructor
public class MsfMplatFormServerAdapter {

    //20260526 PRX 호출 URL 변경
    private static final String MSP_PRX_SERVICE_CALL_PATH = "/mPlatform/serviceCall.do";
    private static final String MSP_PRX_SERVICE_CALL_JSON_PATH = "/mPlatform/serviceCallJson.do";

    protected static final String HEADER = "commHeader";
    protected static final String GLOBAL_NO = "globalNo";
    protected static final String RESPONSE_TYPE = "responseType";
    protected static final String RESPONSE_CODE = "responseCode";
    protected static final String RESPONSE_BASIC = "responseBasic";

    private final IpStatisticService ipStatisticService;
    private final MspPrxClient mspPrxClient;
    private final ExternalServiceProperties externalServiceProperties;

    @Value("${api.interface.server:}")
    private String apiInterfaceServer;

    public boolean callService(HashMap<String, String> param, CommonXmlVO vo) throws SelfServiceException, SocketTimeoutException {
        return callService(param, vo, 30000);
    }

    public boolean callService(HashMap<String, String> param, CommonXmlVO vo, int timeout) throws SelfServiceException, SocketTimeoutException {
        boolean result = true;
        String responseXml = "";
        String callUrl = "";
        String eventCd = param == null ? "" : param.get("appEventCd");
        try {

            //엠플렛폼 로그 저장
            HashMap<String, String> pMplatform = this.saveMplateSvcLog(param);
            logMplatformRequestParameters("callService", pMplatform, timeout);

            callUrl = getMspPrxServiceCallUrl();

            log.info("*** M-PlatForm Connect Start ***");
            log.info("*** callUrl *** " + callUrl);
            log.info("*** eventCd *** " + eventCd);
            log.info("*** M-Platform parameter count *** " + pMplatform.size());
            // Use TOBE PRX client; keep raw XML so existing VO parsers continue to work.
            MspPrxSoapResponse response = mspPrxClient.callService(MspPrxFormRequest.builder()
                .parameters(pMplatform)
                .build());
            responseXml = response.rawXml();
            // AS-IS: 직접 getURL 생성 후 전송하던 방식. getURL 생성은 MspPrxClient에서 공통 처리한다.
            // String getURL = this.getURL(pMplatform);
            // NameValuePair[] data = { new NameValuePair("getURL", getURL) };
            // MspPrxSoapResponse response = mspPrxClient.callService(MspPrxFormRequest.builder()
            //     .parameter("getURL", getURL)
            //     .build());
            //responseXml = HttpClientUtil.post(callUrl, data, "UTF-8", timeout);
            log.info("*** M-PlatForm response length *** " + (responseXml == null ? 0 : responseXml.length()));
            log.info("responseXml : " + responseXml);

            if (responseXml == null || responseXml.isEmpty()) {
                result = false;
                throw new McpMplatFormException(MPLATFORM_RESPONEXML_EMPTY_EXCEPTION);
            } else {
                if (vo != null) {
                    vo.setResponseXml(responseXml);
                    vo.toResponseParse();
                }
            }

        } catch (SelfServiceException e) {
            throw e;
        } catch (SocketTimeoutException e) {
            throw e;
        } catch (McpMplatFormException e) {
            throw e;
        } catch (Exception e) {
            result = false;
            log.error("[callService] M-Platform call failed: eventCd={}, callUrl={}, timeout={}, message={}",
                eventCd, callUrl, timeout, e.getMessage(), e);
            throw new McpMplatFormException(e);
        }

        return result;
    }

    public <T> T callService2(HashMap<String, String> param, Class<T> clazz)
        throws SelfServiceException, IOException {
        XmlMapper mapper = new XmlMapper();
        JsonNode root = mapper.readTree(callService2(param, 30000).getBytes());
        JsonNode outDtoNode = root.findValue("return");
        return mapper.treeToValue(outDtoNode, clazz);
    }

    public String callService2(HashMap<String, String> param, int timeout)
        throws SelfServiceException, SocketTimeoutException {
        //boolean result = true;
        String responseXml = "";

        //엠플렛폼 로그 저장
        HashMap<String, String> pMplatform = this.saveMplateSvcLog(param);
        logMplatformRequestParameters("callService2", pMplatform, timeout);

        //String callUrl = getMspPrxServiceCallUrl();

        log.info("*** M-PlatForm Connect Start ***");
        // Use TOBE PRX client; keep raw XML so existing DTO conversion remains unchanged.
        MspPrxSoapResponse response = mspPrxClient.callService(MspPrxFormRequest.builder()
            .parameters(pMplatform)
            .build());
        responseXml = response.rawXml();
        // AS-IS: 직접 getURL 생성 후 전송하던 방식. getURL 생성은 MspPrxClient에서 공통 처리한다.
        // String getURL = this.getURL(pMplatform);
        // NameValuePair[] data = { new NameValuePair("getURL", getURL) };
        // MspPrxSoapResponse response = mspPrxClient.callService(MspPrxFormRequest.builder()
        //     .parameter("getURL", getURL)
        //     .build());
        //responseXml = HttpClientUtil.post(callUrl, data, "UTF-8", timeout);
        log.info("responseXml : " + responseXml);

        if (responseXml.isEmpty()) {
            throw new McpMplatFormException(MPLATFORM_RESPONEXML_EMPTY_EXCEPTION);
        }

        //XmlMapper xmlMapper = new XmlMapper();
        return responseXml;
    }


    public boolean callServiceNe(HashMap<String, String> param, CommonXmlNoSelfServiceException vo, int timeout)
        throws SelfServiceException, SocketTimeoutException {
        boolean result = true;
        String responseXml = "";
        try {

            //엠플렛폼 로그 저장
            HashMap<String, String> pMplatform = this.saveMplateSvcLog(param);
            logMplatformRequestParameters("callServiceNe", pMplatform, timeout);

            //String callUrl = getMspPrxServiceCallUrl();

            log.info("*** M-PlatForm Connect Start ***");
            // Use TOBE PRX client; keep raw XML so existing VO parsers continue to work.
            MspPrxSoapResponse response = mspPrxClient.callService(MspPrxFormRequest.builder()
                .parameters(pMplatform)
                .build());
            responseXml = response.rawXml();
            // AS-IS: 직접 getURL 생성 후 전송하던 방식. getURL 생성은 MspPrxClient에서 공통 처리한다.
            // String getURL = this.getURL(pMplatform);
            // NameValuePair[] data = { new NameValuePair("getURL", getURL) };
            // MspPrxSoapResponse response = mspPrxClient.callService(MspPrxFormRequest.builder()
            //     .parameter("getURL", getURL)
            //     .build());
            //responseXml = HttpClientUtil.post(callUrl, data, "UTF-8", timeout);
            log.info("responseXml : " + responseXml);

            if (responseXml.isEmpty()) {
                result = false;
                throw new McpMplatFormException(MPLATFORM_RESPONEXML_EMPTY_EXCEPTION);
            } else {
                if (vo != null) {
                    vo.setResponseXml(responseXml);
                    vo.toResponseParse();
                }
            }

        } catch (SocketTimeoutException e) {
            throw e;
        } catch (McpMplatFormException e) {
            throw e;
        } catch (Exception e) {
            result = false;
        }

        return result;
    }

    public HashMap<String, Object> mplatformCallJson(MpBaseRequestSpec baseRequest) {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        try {
            // this.setMplatformLogParametersToRequest(eventRequest);
            RestTemplate restTemplate = new RestTemplate();

            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            factory.setConnectTimeout(5000);
            factory.setReadTimeout(10000);
            restTemplate.setRequestFactory(factory);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "text/plain; charset=UTF-8");
            HttpEntity<MpBaseRequestSpec> entity = new HttpEntity<>(baseRequest, headers);

            // TOBE PRX base URL is used instead of the old hard-coded PRX host.
            String mplatformJsonUrl = getMspPrxServiceCallJsonUrl();

            String responseXml = restTemplate.exchange(mplatformJsonUrl, HttpMethod.POST, entity, String.class).getBody();

            if (responseXml == null || responseXml.isEmpty()) {
                resultMap.put("code", "9998");
                resultMap.put("msg", MPLATFORM_RESPONEXML_EMPTY_EXCEPTION);
            } else {
                resultMap = this.toResponseParse(responseXml, baseRequest.getAppEventCd());
                if (!"N".equals(resultMap.get("responseType"))) {
                    resultMap.put("code", resultMap.get("responseCode"));
                    resultMap.put("msg", resultMap.get("responseBasic"));
                } else {
                    resultMap.put("code", "0000");
                    resultMap.put("msg", "성공");
                }
            }

        } catch (Exception e) {
            resultMap.put("code", "9999");
            resultMap.put("msg", "PRX 연동 오류 [" + e.getMessage() + ("]"));
            // LOGGER.error("MPLATFORM 전송시 문제가 생긴다면 강제로 메시지 세팅 후 정상 처리.");
        }
        return resultMap;
    }

    /**
     * xml 파싱
     * @param responseXml
     * @return HashMap<String, Object>
     * @throws JDOMException
     * @throws IOException
     */
    private HashMap<String, Object> toResponseParse(String responseXml, String appEventCd) throws IOException, JDOMException {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();

        Element root = XmlParse.getRootElement("<?xml version=\"1.0\" encoding=\"euc-kr\"?>" + responseXml);
        Element rtn = XmlParse.getReturnElement(root);

        Element commHeader = XmlParse.getChildElement(rtn, HEADER);
        resultMap.put("globalNo", XmlParse.getChildValue(commHeader, GLOBAL_NO));
        resultMap.put("responseType", XmlParse.getChildValue(commHeader, RESPONSE_TYPE));
        resultMap.put("responseCode", XmlParse.getChildValue(commHeader, RESPONSE_CODE));
        resultMap.put("responseBasic", XmlParse.getChildValue(commHeader, RESPONSE_BASIC));

        if ("X85".equals(appEventCd)) {
            Element outDto = XmlParse.getChildElement(rtn, "outDto");
            resultMap.put("psblYn", XmlParse.getChildValue(outDto, "psblYn"));
        } else if ("X01".equals(appEventCd)) {
            Element outDto = XmlParse.getChildElement(rtn, "outDto");
            resultMap.put("email", XmlParse.getChildValue(outDto, "email"));
            resultMap.put("addr", XmlParse.getChildValue(outDto, "addr"));
            resultMap.put("homeTel", XmlParse.getChildValue(outDto, "homeTel"));
            resultMap.put("initActivationDate", XmlParse.getChildValue(outDto, "initActivationDate"));
        } else if ("Y24".equals(appEventCd) || "Y25".equals(appEventCd)) {
            Element outDto = XmlParse.getChildElement(rtn, "outDto");
            resultMap.put("rsltCd", XmlParse.getChildValue(outDto, "rsltCd"));
            resultMap.put("rsltMsg", XmlParse.getChildValue(outDto, "rsltMsg"));
        } else if ("T01".equals(appEventCd)) {
            Element outDto = XmlParse.getChildElement(rtn, "outDto");
            resultMap.put("rsltCd", XmlParse.getChildValue(outDto, "rsltCd"));
            resultMap.put("rsltMsg", XmlParse.getChildValue(outDto, "rsltMsg"));
            resultMap.put("ctnStatus", XmlParse.getChildValue(outDto, "ctnStatus"));
            resultMap.put("openDt", XmlParse.getChildValue(outDto, "openDt"));
            resultMap.put("usimChgDt", XmlParse.getChildValue(outDto, "usimChgDt"));
            resultMap.put("acceptDt", XmlParse.getChildValue(outDto, "acceptDt"));
            resultMap.put("usimOnlyYn", XmlParse.getChildValue(outDto, "usimOnlyYn"));
            resultMap.put("usimTypeCd", XmlParse.getChildValue(outDto, "usimTypeCd"));
        } else if ("Y07".equals(appEventCd)) {
            Element outDto = XmlParse.getChildElement(rtn, "outDto");
            resultMap.put("intmMdlId", XmlParse.getChildValue(outDto, "intmMdlId"));
            resultMap.put("intmSeq", XmlParse.getChildValue(outDto, "intmSeq"));
            resultMap.put("pukNo1", XmlParse.getChildValue(outDto, "pukNo1"));
        } else if ("X77".equals(appEventCd)) {
            String internetYn = "N";
            Element outDto = XmlParse.getChildElement(rtn, "outDto");
            List<Element> list = XmlParse.getChildElementList(outDto, "moscSrchCombInfoList");
            for (Element item: list) {
                if ("인터넷".equals(XmlParse.getChildValue(item, "svcDivCd"))) {
                    internetYn = "Y";
                    break;
                }
            }
            resultMap.put("internetYn", internetYn);
        }
        return resultMap;
    }


    //private String getURL(HashMap<String, String> param) {
    //    String result = "";
    //    Set<String> keySet = param.keySet();
    //    for (String key: keySet) {
    //        if (!result.equals("")) {
    //            result = result.concat("&");
    //        }
    //
    //        result = result.concat(key + "=" + param.get(key));
    //    }
    //    try {
    //        result = URLEncoder.encode(result, "UTF-8");
    //    } catch (UnsupportedEncodingException e) {
    //        log.error(e.getMessage());
    //    }
    //    return result;
    //}

    private void logMplatformRequestParameters(String caller, HashMap<String, String> param, int timeout) {
        if (param == null) {
            log.info("[{}] M-Platform request params: null, timeout={}", caller, timeout);
            return;
        }

        // PRX로 전달되는 최종 입력값 확인용 로그. 장애 분석에 필요한 값만 펼쳐서 남긴다.
        log.info("[{}] M-Platform request params: eventCd={}, ncn={}, ctn={}, custId={}, userid={}, ip={}, mdlInd={}, url={}, timeout={}",
            caller,
            param.get("appEventCd"),
            param.get("ncn"),
            param.get("ctn"),
            param.get("custId"),
            param.get("userid"),
            param.get("ip"),
            param.get("mdlInd"),
            param.get("url"),
            timeout);
    }

    private String getMspPrxServiceCallUrl() {
        String baseUrl = externalServiceProperties.service(SERVICE_NAME_MSP_PRX).baseUrl();
        return StringUtils.trimTrailingCharacter(baseUrl, '/') + MSP_PRX_SERVICE_CALL_PATH;
    }

    private String getMspPrxServiceCallJsonUrl() {
        String baseUrl = externalServiceProperties.service(SERVICE_NAME_MSP_PRX).baseUrl();
        return StringUtils.trimTrailingCharacter(baseUrl, '/') + MSP_PRX_SERVICE_CALL_JSON_PATH;
    }

    /**
     * 엠플렛폼 서비스 연동 로그 저장
     *
     * @param param
     */
    private HashMap<String, String> saveMplateSvcLog(HashMap<String, String> param) {

        HashMap<String, String> tmpParm = param;
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

            tmpParm.put("ip", RequestUtils.getClientIp());
            tmpParm.put("url", request.getRequestURI());
            tmpParm.put("mdlInd", NmcpServiceUtils.getDeviceType());
            //20260618 tmpParm.put("mdlInd", "ttt");

            //log.info("userid:{}", StringUtil.NVL(tmpParm.get("userid").toString(), ""));

        } catch (Exception e) {
            log.debug("엠플렛폼 연동 정보 저장 오류 : " + e.getMessage());
        }
        return tmpParm;
    }

}
