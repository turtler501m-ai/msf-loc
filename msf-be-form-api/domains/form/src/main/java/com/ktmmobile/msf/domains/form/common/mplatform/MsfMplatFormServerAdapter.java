package com.ktmmobile.msf.domains.form.common.mplatform;

import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.MPLATFORM_RESPONEXML_EMPTY_EXCEPTION;

import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ktmmobile.msf.domains.form.common.exception.McpMplatFormException;
import com.ktmmobile.msf.domains.form.common.exception.SelfServiceException;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.CommonXmlNoSelfServiceException;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.CommonXmlVO;
import com.ktmmobile.msf.domains.form.common.service.IpStatisticService;
import com.ktmmobile.msf.domains.form.common.util.HttpClientUtil;
import com.ktmmobile.msf.domains.form.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;

@Service
public class MsfMplatFormServerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(MsfMplatFormServerAdapter.class);
    private static final boolean SKIP_EXTERNAL_CALL = true;

    @Value("${juice.url}")
    private String propertiesService;

    @Autowired
    private IpStatisticService ipStatisticService;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    public boolean callService(HashMap<String,String> param, CommonXmlVO vo) throws SelfServiceException, SocketTimeoutException{
        return callService(param,vo,30000);
    }

    public boolean callService(HashMap<String,String> param, CommonXmlVO vo ,int timeout) throws SelfServiceException, SocketTimeoutException{
        boolean result = true;
        String responseXml = "";
        String eventCd = StringUtil.NVL(param.get("appEventCd"), "");
        try {

            //엠플렛폼 로그 저장
            HashMap<String, String> pMplatform = this.saveMplateSvcLog(param);
            logger.debug("[MPlatform][callService] param(beforeGetURL)={}", param);
            logger.debug("[MPlatform][callService] pMplatform={}", pMplatform);

            String getURL = this.getURL(pMplatform);
            logger.debug("[MPlatform][callService] getURL(encoded)={}", getURL);

            String callUrl = propertiesService;

            //CommonHttpClient client = new CommonHttpClient(callUrl);
            NameValuePair[] data = {
                new NameValuePair("getURL", getURL)
            };
            logger.info("[MPlatform][callService] start: eventCd={}, timeout={}, ncn={}, ctn={}, custId={}, userId={}, callUrl={}",
                eventCd, timeout, pMplatform.get("ncn"), pMplatform.get("ctn"), pMplatform.get("custId"), pMplatform.get("userid"), callUrl);
            if (SKIP_EXTERNAL_CALL) {
                logger.warn("[MPlatform][callService] external call skipped: eventCd={}", eventCd);
                if (vo != null) {
                    vo.setResultCode(CommonXmlVO.RESULTCODE_SUCCESS);
                    vo.setSuccess(true);
                    vo.setSvcMsg("SKIPPED");
                    vo.setResponseXml("<return><outDto></outDto></return>");
                }
                return true;
            }

            responseXml = HttpClientUtil.post(callUrl, data, "UTF-8",timeout);

            logger.info("[MPlatform][callService] response: eventCd={}, xmlLength={}",
                eventCd, responseXml == null ? 0 : responseXml.length());
            logger.debug("[MPlatform][callService] responseXml: eventCd={}, xml={}", eventCd, responseXml);

            if (responseXml.isEmpty()) {
                result = false;
                throw new McpMplatFormException(MPLATFORM_RESPONEXML_EMPTY_EXCEPTION);
            } else {
                if( vo != null){
                    vo.setResponseXml(responseXml);
                    vo.toResponseParse();
                }
            }

        } catch (SelfServiceException e) {
            logger.error("[MPlatform][callService] SelfServiceException: eventCd={}, message={}", eventCd, e.getMessage(), e);
            throw e;
        } catch (SocketTimeoutException e){
            logger.error("[MPlatform][callService] SocketTimeoutException: eventCd={}, timeout={}", eventCd, timeout, e);
            throw e;
        } catch (McpMplatFormException e){
            logger.error("[MPlatform][callService] McpMplatFormException: eventCd={}, message={}", eventCd, e.getMessage(), e);
            throw e;
        }  catch (Exception e) {
            result = false;
            logger.error("[MPlatform][callService] Exception: eventCd={}, message={}", eventCd, e.getMessage(), e);
        }

        return result;
    }


    public boolean callServiceNe(HashMap<String,String> param, CommonXmlNoSelfServiceException vo ,int timeout) throws SelfServiceException, SocketTimeoutException{
        boolean result = true;
        String responseXml = "";
        String eventCd = StringUtil.NVL(param.get("appEventCd"), "");
        try {

            //엠플렛폼 로그 저장
            HashMap<String, String> pMplatform = this.saveMplateSvcLog(param);
            logger.debug("[MPlatform][callServiceNe] param(beforeGetURL)={}", param);
            logger.debug("[MPlatform][callServiceNe] pMplatform={}", pMplatform);

            String getURL = this.getURL(pMplatform);
            logger.debug("[MPlatform][callServiceNe] getURL(encoded)={}", getURL);

            String callUrl = propertiesService;

            //CommonHttpClient client = new CommonHttpClient(callUrl);
            NameValuePair[] data = {
                new NameValuePair("getURL", getURL)
            };
            logger.info("[MPlatform][callServiceNe] start: eventCd={}, timeout={}, ncn={}, ctn={}, custId={}, userId={}, callUrl={}",
                eventCd, timeout, pMplatform.get("ncn"), pMplatform.get("ctn"), pMplatform.get("custId"), pMplatform.get("userid"), callUrl);
            if (SKIP_EXTERNAL_CALL) {
                logger.warn("[MPlatform][callServiceNe] external call skipped: eventCd={}", eventCd);
                if (vo != null) {
                    vo.setResultCode(CommonXmlNoSelfServiceException.RESULTCODE_SUCCESS);
                    vo.setSuccess(true);
                    vo.setSvcMsg("SKIPPED");
                    vo.setResponseXml("<return><outDto></outDto></return>");
                }
                return true;
            }

            responseXml = HttpClientUtil.post(callUrl,data, "UTF-8",timeout);

            logger.info("[MPlatform][callServiceNe] response: eventCd={}, xmlLength={}",
                eventCd, responseXml == null ? 0 : responseXml.length());
            logger.debug("[MPlatform][callServiceNe] responseXml: eventCd={}, xml={}", eventCd, responseXml);

            if (responseXml.isEmpty()) {
                result = false;
                throw new McpMplatFormException(MPLATFORM_RESPONEXML_EMPTY_EXCEPTION);
            } else {
                if( vo != null){
                    vo.setResponseXml(responseXml);
                    vo.toResponseParse();
                }
            }

        } catch (SocketTimeoutException e){
            logger.error("[MPlatform][callServiceNe] SocketTimeoutException: eventCd={}, timeout={}", eventCd, timeout, e);
            throw e;
        } catch (McpMplatFormException e){
            logger.error("[MPlatform][callServiceNe] McpMplatFormException: eventCd={}, message={}", eventCd, e.getMessage(), e);
            throw e;
        }  catch (Exception e) {
            result = false;
            logger.error("[MPlatform][callServiceNe] Exception: eventCd={}, message={}", eventCd, e.getMessage(), e);
        }

        return result;
    }


    private String getURL(HashMap<String, String> param){
        String result = "";
        Set<String> keySet = param.keySet();
        for(String key : keySet){
            if(!result.equals("")){
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
     * @param param
     */
    private HashMap<String, String> saveMplateSvcLog(HashMap<String,String> param) {

    	HashMap<String, String> tmpParm = param;
    	try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

            tmpParm.put("ip", ipStatisticService.getClientIp());
            tmpParm.put("url", request.getRequestURI());
            tmpParm.put("mdlInd", NmcpServiceUtils.getDeviceType());

            logger.info("userid:{}", StringUtil.NVL(tmpParm.get("userid").toString(),""));

        } catch (Exception e) {
            logger.debug("엠플렛폼 연동 정보 저장 오류 : " + e.getMessage());
        }
        return tmpParm;
    }

    /**
     * 당일 X35 로그 Count
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
