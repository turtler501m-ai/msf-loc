package com.ktmmobile.msf.system.common.mplatform;

import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.MPLATFORM_RESPONEXML_EMPTY_EXCEPTION;

import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ktmmobile.msf.system.common.exception.McpMplatFormException;
import com.ktmmobile.msf.system.common.exception.SelfServiceException;
import com.ktmmobile.msf.system.common.mplatform.vo.CommonXmlNoSelfServiceException;
import com.ktmmobile.msf.system.common.mplatform.vo.CommonXmlVO;
import com.ktmmobile.msf.system.common.service.IpStatisticService;
import com.ktmmobile.msf.system.common.util.HttpClientUtil;
import com.ktmmobile.msf.system.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.system.common.util.StringUtil;

@Service
public class MplatFormServerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(MplatFormServerAdapter.class);

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
            responseXml = HttpClientUtil.post(callUrl, data, "UTF-8",timeout);
            logger.info("responseXml : " + responseXml);

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
            throw e;
        } catch (SocketTimeoutException e){
            throw e;
        } catch (McpMplatFormException e){
            throw e;
        }  catch (Exception e) {
            result = false;
        }

        return result;
    }


    public boolean callServiceNe(HashMap<String,String> param, CommonXmlNoSelfServiceException vo ,int timeout) throws SelfServiceException, SocketTimeoutException{
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
            responseXml = HttpClientUtil.post(callUrl,data, "UTF-8",timeout);
            logger.info("responseXml : " + responseXml);

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
            throw e;
        } catch (McpMplatFormException e){
            throw e;
        }  catch (Exception e) {
            result = false;
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
