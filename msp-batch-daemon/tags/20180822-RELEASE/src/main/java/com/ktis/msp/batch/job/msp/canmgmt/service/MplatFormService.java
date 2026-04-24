package com.ktis.msp.batch.job.msp.canmgmt.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.util.XmlParse;

import egovframework.rte.fdl.property.EgovPropertyService;


@Service
public class MplatFormService extends BaseService  {
    
    /** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
        
    protected final static String HEADER = "commHeader";
    protected final static String RESULTKEY = "globalNo";
    protected final static String RESULTTYPE = "responseType";
    protected final static String RESULTCODE = "responseCode";
    protected final static String RESULTMSG = "responseBasic";
    protected final static String RESULTCODE_SUCCESS = "N";

    public HashMap<String,String> callService(HashMap<String,String> paramMap) throws MvnoServiceException {
    	String result = "";
        String responseXml = "";
                
        try {
            String getURL = this.getURL(paramMap);
            
            NameValuePair[] data = {
                new NameValuePair("getURL", getURL)
            };
            
            LOGGER.info("★☆★ M-PlatForm Connect Start ☆★☆");

    		HttpClient client = new HttpClient();
    		client.getParams().setParameter("http.useragent", "Test Client");
    		BufferedReader br = null;
    		PostMethod method = new PostMethod(propertiesService.getString("mplatform.osst.url"));
    		method.setRequestBody(data);
    		try{
    			int returnCode = client.executeMethod(method);
    			if(returnCode == HttpStatus.SC_NOT_IMPLEMENTED) {
    				method.getResponseBodyAsString();
    			} else {
    				StringBuffer sb = new StringBuffer();
    				
    				br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), "UTF-8" ));
    				String readLine;
    				while(((readLine = br.readLine()) != null)) {
    					sb.append(readLine);
    				}
    				responseXml = sb.toString();
    				
    				LOGGER.info("responseXml : " + responseXml);
    			}
    		} catch (Exception e) {
    			LOGGER.error(e.toString());
    		} finally {
    			
    			method.releaseConnection();
    			client.getHttpConnectionManager().closeIdleConnections(0);
    			if(br != null) try { br.close(); } catch (Exception fe) {LOGGER.error(fe.toString());}
    		}

            if (responseXml.isEmpty()) {
                result = "response massage is null.";
                LOGGER.error(result);
            } else {
            	result = "SUCCESS";
                this.toResponseParse(responseXml, paramMap);
            }
            
            paramMap.put("result", result);

            LOGGER.info("★☆★ M-PlatForm Connect End ☆★☆");

        } catch (Exception e) {
        	paramMap.put("result", e.toString());
            LOGGER.error(result);
            throw new MvnoServiceException("EMSP1007", e);
        }

        return paramMap;
    }
    
    public void toResponseParse(String responseXml, HashMap<String,String> resultMap) throws JDOMException, IOException {
    	
    	Element root = XmlParse.getRootElement("<?xml version=\"1.0\" encoding=\"euc-kr\"?>"+responseXml);
        Element rtn = XmlParse.getReturnElement(root);

        Element commHeader = XmlParse.getChildElement(rtn, HEADER);
        
        resultMap.put("resultKey", XmlParse.getChildValue(commHeader, RESULTKEY));
        resultMap.put("resultType", XmlParse.getChildValue(commHeader, RESULTTYPE));
        resultMap.put("resultCd", XmlParse.getChildValue(commHeader, RESULTCODE));
        resultMap.put("resultMsg", XmlParse.getChildValue(commHeader, RESULTMSG));

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
        	LOGGER.error(e.toString());
        }
        return result;
    }

}
