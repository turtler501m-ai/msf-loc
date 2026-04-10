package com.ktmmobile.mcp.common.cti;
import static com.ktmmobile.mcp.common.constants.Constants.EVENT_CODE_PRE_CHECK;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;

import com.ktmmobile.mcp.common.exception.McpCommonException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import com.ktmmobile.mcp.common.dao.FCommonDao;
import com.ktmmobile.mcp.common.dto.McpIpStatisticDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.exception.SelfServiceException;
import com.ktmmobile.mcp.common.service.IpStatisticService;
import com.ktmmobile.mcp.common.util.HttpClientUtil;
import com.ktmmobile.mcp.common.util.SessionUtils;

@Service
public class CtiServerAdapter {

    private static Logger logger = LoggerFactory.getLogger(CtiServerAdapter.class);

    @Value("${cti.url}")
    private String propertiesService;



    @Autowired
    private FCommonDao fCommonDao;

    @Autowired
    private IpStatisticService ipStatisticService;

    @Value("${SERVER_NAME}")
    private String serverLocation;

    public boolean callService(HashMap<String,String> param) throws SelfServiceException, SocketTimeoutException{
        return callService(param,10000);
    }

    public boolean callService(HashMap<String,String> param, int timeout) throws SelfServiceException, SocketTimeoutException{
        boolean result = true;
        String responseXml = "";
        try {

        	//CTI 로그 저장
        	this.saveCtiSvcLog(param);

            String getURL = this.getURL(param);

            String callUrl = propertiesService;


            NameValuePair[] data = {
                new NameValuePair("getURL", getURL)
            };

           //로컬/개발에서 강제로 성공 처리 (CTI 개발 서버 부재)
           if("LOCAL".equals(serverLocation)) {
             responseXml ="<?xml version='1.0' encoding='UTF-8'?><commHeader><globalNo>20181013034644160</globalNo><responseType>S</responseType><responseCode>RES_00</responseCode><responseMsg>정상처리 되었습니다.</responseMsg><serviceCode>INF_APP_013</serviceCode></commHeader>";
	           //responseXml ="<?xml version='1.0' encoding='UTF-8'?><commHeader><globalNo>20181013034644160</globalNo><responseType>E</responseType><responseCode>RES_00</responseCode><responseMsg>연동오류</responseMsg><serviceCode>INF_APP_013</serviceCode></commHeader>";
           } else {
            	responseXml = HttpClientUtil.post(callUrl,data, "UTF-8",timeout);
           }


            if (responseXml.isEmpty()) {
                result = false;
            	throw new McpCommonException(COMMON_EXCEPTION);
            } else {

            	InputStream in = new ByteArrayInputStream(responseXml.getBytes("UTF-8"));
                SAXBuilder builder = new SAXBuilder();
                Document document = builder.build(in);

                Element root = document.getRootElement();
                Element rtn = root.getChild("responseType");

                if("S".equals(rtn.getValue())) {
                	result = true;
                } else {
                	Element responseMsg = root.getChild("responseMsg");
                	throw new McpCommonException(responseMsg.getValue());
                }
            }

        } catch (SelfServiceException e) {
            throw e;
        } catch (SocketTimeoutException e){
            throw e;
        } catch (Exception e) {
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

            result = result.concat(key + "=" + StringEscapeUtils.unescapeHtml(param.get(key)).replaceAll("&", ""));
        }

        logger.error("상담 AP 연동 URL (BEFORE ENCODING) > " + result);

        try {
            result = URLEncoder.encode(result, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        	logger.error(e.getMessage());
        }

        return result;
    }

    /**
     * CTI 서비스 연동 로그 저장
     * @param param
     */
    private void saveCtiSvcLog(HashMap<String,String> param) {

    	try {
    		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    		UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();

    		McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
    		mcpIpStatisticDto.setPrcsMdlInd("CTI CALL");
    		mcpIpStatisticDto.setPrcsSbst(param.get("serviceCode")+"||"+param.get("qnaCtg"));
            mcpIpStatisticDto.setIp(ipStatisticService.getClientIp());
    		if (userSessionDto != null) {
    			mcpIpStatisticDto.setUserid(userSessionDto.getUserId());
    		}
    		mcpIpStatisticDto.setUrl(request.getRequestURI());
    		fCommonDao.insertIpStat(mcpIpStatisticDto);
    	} catch (Exception e) {
    		logger.error("CTI 연동 정보 저장 오류 : " + e.getMessage());
    	}

    }

}
