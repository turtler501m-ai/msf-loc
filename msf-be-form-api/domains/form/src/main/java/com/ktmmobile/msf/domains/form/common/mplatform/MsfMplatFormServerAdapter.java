package com.ktmmobile.msf.domains.form.common.mplatform;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jakarta.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.commons.httpclient.NameValuePair;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ktmmobile.msf.commons.websecurity.web.util.RequestUtils;
import com.ktmmobile.msf.domains.form.common.exception.McpMplatFormException;
import com.ktmmobile.msf.domains.form.common.exception.SelfServiceException;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MpBaseRequestSpec;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.CommonXmlNoSelfServiceException;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.CommonXmlVO;
import com.ktmmobile.msf.domains.form.common.service.IpStatisticService;
import com.ktmmobile.msf.domains.form.common.util.HttpClientUtil;
import com.ktmmobile.msf.domains.form.common.util.XmlParse;

import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.MPLATFORM_RESPONEXML_EMPTY_EXCEPTION;

@Service
public class MsfMplatFormServerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(MsfMplatFormServerAdapter.class);

    @Value("${juice.url}")
    private String propertiesService;
    // @Value("${juice.json.url}")
    // private String mplatformJsonUrl;

    protected final static String HEADER = "commHeader";
    protected final static String GLOBAL_NO = "globalNo";
    protected final static String RESPONSE_TYPE = "responseType";
    protected final static String RESPONSE_CODE = "responseCode";
    protected final static String RESPONSE_BASIC = "responseBasic";

    @Autowired
    private IpStatisticService ipStatisticService;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    public boolean callService(HashMap<String, String> param, CommonXmlVO vo) throws SelfServiceException, SocketTimeoutException {
        return callService(param, vo, 30000);
    }

    public boolean callService(HashMap<String, String> param, CommonXmlVO vo, int timeout) throws SelfServiceException, SocketTimeoutException {
        boolean result = true;
        String responseXml = "";
        try {

            //엠플렛폼 로그 저장
            HashMap<String, String> pMplatform = this.saveMplateSvcLog(param);

            String getURL = this.getURL(pMplatform);

            String callUrl = propertiesService;

            //CommonHttpClient client = new CommonHttpClient(callUrl);
            NameValuePair[] data = {
                new NameValuePair("getURL", getURL)
            };
            logger.info("*** M-PlatForm Connect Start ***");
            logger.info("*** callUrl *** " + callUrl);
            logger.info("*** data *** " + data);
            responseXml = HttpClientUtil.post(callUrl, data, "UTF-8", timeout);
            logger.info("responseXml : " + responseXml);

            if (responseXml.isEmpty()) {
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
        }

        return result;
    }

    public <T> T callService2(HashMap<String, String> param, Class<T> clazz)
        throws SelfServiceException, IOException {
        XmlMapper mapper = new XmlMapper();
        JsonNode root = mapper.readTree(callService2(param, 30000).getBytes());
        JsonNode outDtoNode = root.findValue("outDto");

        return mapper.treeToValue(outDtoNode, clazz);
    }

    public String callService2(HashMap<String, String> param, int timeout)
        throws SelfServiceException, SocketTimeoutException {
        boolean result = true;
        String responseXml = "";

        //엠플렛폼 로그 저장
        HashMap<String, String> pMplatform = this.saveMplateSvcLog(param);

        String getURL = this.getURL(pMplatform);

        String callUrl = propertiesService;

        //CommonHttpClient client = new CommonHttpClient(callUrl);
        NameValuePair[] data = {
            new NameValuePair("getURL", getURL)
        };
        logger.info("*** M-PlatForm Connect Start ***");
        responseXml = HttpClientUtil.post(callUrl, data, "UTF-8", timeout);
        logger.info("responseXml : " + responseXml);

        if (responseXml.isEmpty()) {
            throw new McpMplatFormException(MPLATFORM_RESPONEXML_EMPTY_EXCEPTION);
        }

        XmlMapper xmlMapper = new XmlMapper();
        return responseXml;
    }


    public boolean callServiceNe(HashMap<String, String> param, CommonXmlNoSelfServiceException vo, int timeout)
        throws SelfServiceException, SocketTimeoutException {
        boolean result = true;
        String responseXml = "";
        try {

            //엠플렛폼 로그 저장
            HashMap<String, String> pMplatform = this.saveMplateSvcLog(param);

            String getURL = this.getURL(pMplatform);

            String callUrl = propertiesService;

            //CommonHttpClient client = new CommonHttpClient(callUrl);
            NameValuePair[] data = {
                new NameValuePair("getURL", getURL)
            };
            logger.info("*** M-PlatForm Connect Start ***");
            responseXml = HttpClientUtil.post(callUrl, data, "UTF-8", timeout);
            logger.info("responseXml : " + responseXml);

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

            // 임시 하드코딩
            String mplatformJsonUrl = "http://10.110.150.59:18080/mPlatform/serviceCallJson.do";

            byte[] responseBtye = restTemplate.exchange(mplatformJsonUrl, HttpMethod.POST, entity, byte[].class).getBody();
            String responseXml = new String(responseBtye, "UTF-8");

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


    private String getURL(HashMap<String, String> param) {
        String result = "";
        Set<String> keySet = param.keySet();
        for (String key: keySet) {
            if (!result.equals("")) {
                result = result.concat("&");
            }

            result = result.concat(key + "=" + param.get(key));
        }
        try {
            result = URLEncoder.encode(result, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        return result;
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
            //tmpParm.put("mdlInd", NmcpServiceUtils.getDeviceType());
            tmpParm.put("mdlInd", "ttt");

            //logger.info("userid:{}", StringUtil.NVL(tmpParm.get("userid").toString(), ""));

        } catch (Exception e) {
            logger.debug("엠플렛폼 연동 정보 저장 오류 : " + e.getMessage());
        }
        return tmpParm;
    }

    /**
     * 당일 X35 로그 Count
     *
     * @param userId
     * @param eventCd
     * @return
     */
    public int checkMpCallEventCount(String userId, String eventCd) {

        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> reMap = new HashMap<>();
        reMap.put("userId", userId);
        reMap.put("eventCd", eventCd);
        return restTemplate.postForObject(apiInterfaceServer + "/mPlatform/checkMpCallCount", reMap, Integer.class);
    }
}
