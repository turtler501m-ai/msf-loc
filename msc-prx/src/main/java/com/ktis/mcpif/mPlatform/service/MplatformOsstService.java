package com.ktis.mcpif.mPlatform.service;

import java.io.ByteArrayInputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.dom.DOMSource;

import com.ktis.mcpif.common.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;

import com.ktis.mcpif.common.KisaSeedUtil;

import egovframework.rte.fdl.property.EgovPropertyService;
/**
 * CP0(선불 직권해지)
 * EP0(해지)
 * M-PLATFORM 호출 서비스
 */
@org.springframework.stereotype.Service("mPlatformOsstService")
public class MplatformOsstService {
    protected Logger logger = LogManager.getLogger(getClass());
    @Resource(name = "propertiesService")
    private EgovPropertyService propertiesService;

    /**
     * N-Step 서비스 호출
     * @param nStepVo
     * @return
     * @throws SOAPException
     * @throws MalformedURLException
     */
    public String mPlatformServiceWebServiceCall(String reqMessage) throws SOAPException, MalformedURLException {

        RestTemplate restTemplate = new RestTemplate();
        String apiInterfaceServer = propertiesService.getString("api.interface.server");

        // -------------------------------------------------------------------
        // Initialize.
        // -------------------------------------------------------------------
        //Response 객체
        String mPlatformResponse = null;
        HashMap<String, String> reqMap = paramMapCreate(reqMessage);

        //connection 객체 생성
        SOAPConnection soapConnection = SOAPConnectionFactory.newInstance().createConnection();

        //전송할 XML Message 메시지 생성
        SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();

        //String -> XML 생성
        Document doc = null;
        DocumentBuilder docBuilder = null;
        DOMSource domSource = null;
        final int connetTime = propertiesService.getInt("maxTime");

        String url = propertiesService.getString("mPlatForm.osst.url");

        String serviceName = getEventCodeInfo(reqMap.get("appEventCd"), "service");

        // 호출 URL
        URL requestUrl = new URL(new URL(url), serviceName, new URLStreamHandler() {
            @Override
            protected URLConnection openConnection(URL url) throws IOException {
                URL clone = new URL(url.toString());
                URLConnection clone_connection = clone.openConnection();

                clone_connection.setConnectTimeout(connetTime * 1000);
                clone_connection.setReadTimeout(connetTime * 1000);

                return(clone_connection);
            }
        });

        try {
            String loginPassword = propertiesService.getString("clntUsrId")+":"+propertiesService.getString("clntUsrPw");
            String authorization =  new sun.misc.BASE64Encoder().encode(loginPassword.getBytes());
            soapMessage.getMimeHeaders().addHeader("Authorization", "Basic " + authorization);

            doTrustToCertificates();

            reqMap.put("encryptYn", "N");
            String encryptYn = restTemplate.postForObject(apiInterfaceServer + "/mPlatform/getMplatformCryptionYn", reqMap.get("appEventCd"), String.class);
            if(StringUtil.isNotBlank(encryptYn)) {
                reqMap.put("encryptYn", encryptYn);
            }

            //XML 메시지 생성
            String requestXml = xmlMessageCreate(reqMap);

            //파싱 객체 생성
            docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            // String 타입 변경
            doc = docBuilder.parse(new ByteArrayInputStream(requestXml.getBytes()));

            domSource = new DOMSource(doc);

            soapMessage.getSOAPPart().setContent(domSource);

            logger.error("[ostt] ☆☆☆☆☆ Call Start ☆☆☆☆☆");
            logger.error("[ostt] ☆☆ requestService : "+serviceName);
            logger.error("[ostt] ☆☆ requestXml : "+requestXml);

            // SOAP CALL
            SOAPMessage responseMessage = soapConnection.call(soapMessage, requestUrl);
            soapConnection.close();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            responseMessage.writeTo(out);
            mPlatformResponse = new String(out.toByteArray());

            logger.error("[osst] ☆☆ responseXml : "+mPlatformResponse);
            logger.error("[osst] ☆☆☆☆☆  Call End  ☆☆☆☆☆");

        } catch (Exception e) {
            logger.error(e);
        }

        return mPlatformResponse;
    }

    public HashMap<String, String> paramMapCreate(String reqMsg) {

        String[] result = reqMsg.split("&");
        HashMap<String, String> paramMap = new HashMap<String, String>();

        for(int i=0;  i<result.length; i++){
            String strParam0 = result[i].substring(0,result[i].indexOf("="));
            String strParam1 = result[i].substring(result[i].indexOf("=")+1,result[i].length());
            paramMap.put(strParam0, strParam1);
        }//20160202 암호화 value값에 ==에 들어가서 다른형식으로 파싱
        /*for(int i=0; i<result.length; i++){
            String[] strParam = result[i].split("=");
            paramMap.put(strParam[0], strParam[1]);
        }*/

        return paramMap;
    }

    public String getEventCodeInfo(String eventCd, String type) {

         if (eventCd.equals("CP0")) { //
            if (type.equals("service")) {
                return "OsstCanSvcPrcService";
            } else if (type.equals("info")) {
                return "osst:osstCanPps2Prc";
            } else if (type.equals("vo")) {
                return "OsstCanPps2PrcInVO";
            }
         } else if (eventCd.equals("EP0")) {
             if (type.equals("service")) {
                 return "OsstCanSvcPrcService";
             } else if (type.equals("info")) {
                 return "osst:osstCanPrc";
             } else if (type.equals("vo")) {
                 return "OsstCanPrcInVO";
             }
         } else if (eventCd.equals("T01")) {
             if (type.equals("service")) {
                 return "OsstUsimChgAcceptService";
             } else if (type.equals("info")) {
                 return "osst:retvUsimChgAcceptPsbl";
             } else if (type.equals("vo")) {
                 return "OsstUsimChgAcceptPsblInVO";
             }
         }  else if (eventCd.equals("T02")) {
             if (type.equals("service")) {
                 return "OsstUsimChgAcceptService";
             } else if (type.equals("info")) {
                 return "osst:usimChgAccept";
             } else if (type.equals("vo")) {
                 return "OsstUsimChgAcceptInVO";
             }
         }

        return null;
    }

    /**
     * N-Step 전송 XML 초기 데이터 생성
     * @param type
     * @return
     */
    public StringBuffer initXmlMessageCreate(String type) {

        StringBuffer xmlStringBuffer = new StringBuffer();

        if(type.equals("start")) {
            xmlStringBuffer.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:osst=\"http://osst.so.itl.mvno.kt.com/\">");
            xmlStringBuffer.append("<soapenv:Header/>");
            xmlStringBuffer.append("<soapenv:Body>");
        }else if(type.equals("end")) {
            xmlStringBuffer.append("</soapenv:Body>");
            xmlStringBuffer.append("</soapenv:Envelope>");
        }
        return xmlStringBuffer;
    }

    public StringBuffer initHeaderCreate(String eventCd) {
        StringBuffer headerStringBuffer = new StringBuffer();

        long time = System.currentTimeMillis();

        SimpleDateFormat dayTime = new SimpleDateFormat("yyyyMMddHHmmssSSS");

        String now = dayTime.format(new Date(time));

        headerStringBuffer.append("<bizHeader>");
        //test
        headerStringBuffer.append("<appEntrPrsnId>"+propertiesService.getString("appEntrPrsnId")+"</appEntrPrsnId>");
        headerStringBuffer.append("<appAgncCd>"+propertiesService.getString("appAgncCd")+"</appAgncCd>");
        headerStringBuffer.append("<appEventCd>"+eventCd+"</appEventCd>");
        headerStringBuffer.append("<appSendDateTime>"+now.substring(0,14)+"</appSendDateTime>");
        headerStringBuffer.append("<appRecvDateTime></appRecvDateTime>");
        headerStringBuffer.append("<appLgDateTime></appLgDateTime>");
        headerStringBuffer.append("<appNstepUserId>"+propertiesService.getString("appNstepUserId")+"</appNstepUserId>");
        headerStringBuffer.append("<appOrderId></appOrderId>");
        headerStringBuffer.append("</bizHeader>");
        headerStringBuffer.append("<commHeader>");
        headerStringBuffer.append("<globalNo>"+propertiesService.getString("appNstepUserId")+now+"</globalNo>");
        headerStringBuffer.append("<encYn></encYn>");
        headerStringBuffer.append("<responseType></responseType>");
        headerStringBuffer.append("<responseCode></responseCode>");
        headerStringBuffer.append("<responseLogcd></responseLogcd>");
        headerStringBuffer.append("<responseTitle></responseTitle>");
        headerStringBuffer.append("<responseBasic></responseBasic>");
        headerStringBuffer.append("<langCode></langCode>");
        headerStringBuffer.append("<filler></filler>");
        headerStringBuffer.append("</commHeader>");

        return headerStringBuffer;
    }

    /**
     * N-Step 전송 XML 생성
     * @param nStepVo
     * @return
     */
    public String xmlMessageCreate(HashMap<String, String> reqMap) {

        // -------------------------------------------------------------------
        // Initialize.
        // -------------------------------------------------------------------
        String eventCd = reqMap.get("appEventCd");

        StringBuffer requestXml = initXmlMessageCreate("start");
        StringBuffer header = initHeaderCreate(eventCd);

        requestXml.append("<"+getEventCodeInfo(eventCd, "info")+">");
        requestXml.append("<"+getEventCodeInfo(eventCd, "vo")+">");
        requestXml.append(header.toString());

        if("T01".equals(eventCd) || "T02".equals(eventCd)) {
            requestXml.append("<selfCareIn>");
            requestXml.append("<ctn>"+this.encrypt(reqMap, "ctn")+"</ctn>");
            requestXml.append("<custId>"+reqMap.get("custId")+"</custId>");
            requestXml.append("<ncn>"+reqMap.get("ncn")+"</ncn>");
            requestXml.append("<clntIp>"+propertiesService.getString("clntIp")+"</clntIp>");
            requestXml.append("<clntUsrId>"+propertiesService.getString("clntUsrId")+"</clntUsrId>");
            requestXml.append("</selfCareIn>");            
            if("T02".equals(eventCd)) {
                requestXml.append("<inDto>");
                requestXml.append("<acceptDt>"+reqMap.get("acceptDt")+"</acceptDt>");
                requestXml.append("<acceptChCd>"+reqMap.get("acceptChCd")+"</acceptChCd>");
                requestXml.append("<deliveryDivCd>"+reqMap.get("deliveryDivCd")+"</deliveryDivCd>");
                requestXml.append("</inDto>");
            }
            
        } else {
            requestXml.append("<inDto>");
            requestXml.append("<ctn>"+this.encrypt(reqMap, "ctn")+"</ctn>");
            requestXml.append("<custId>"+reqMap.get("custId")+"</custId>");
            requestXml.append("<ncn>"+reqMap.get("ncn")+"</ncn>");

            requestXml.append(this.createInDtoMessage(reqMap));

            requestXml.append("</inDto>");
        }
        
       

        requestXml.append("</"+getEventCodeInfo(eventCd, "vo")+">");
        requestXml.append("</"+getEventCodeInfo(eventCd, "info")+">");

        StringBuffer endXml = initXmlMessageCreate("end");
        requestXml.append(endXml.toString());

        return requestXml.toString();
    }

    private String createInDtoMessage(HashMap<String, String> reqMap) {
        String eventCd = reqMap.get("appEventCd");
        StringBuilder sb = new StringBuilder();

        if ("EP0".equals(eventCd)) {
            sb.append("<cntplcNo>");
            if (!StringUtil.isBlank(reqMap.get("cntplcNo")) && !"null".equals(reqMap.get("cntplcNo"))) {
                sb.append(this.encrypt(reqMap, "cntplcNo"));
            }
            sb.append("</cntplcNo>");
            sb.append("<itgOderWhyCd>").append(reqMap.get("itgOderWhyCd")).append("</itgOderWhyCd>");
            sb.append("<aftmnIncInCd>").append(reqMap.get("aftmnIncInCd")).append("</aftmnIncInCd>");
            sb.append("<apyRelTypeCd>").append(reqMap.get("apyRelTypeCd")).append("</apyRelTypeCd>");
            sb.append("<custTchMediCd>").append(reqMap.get("custTchMediCd")).append("</custTchMediCd>");
            sb.append("<smsRcvYn>").append(reqMap.get("smsRcvYn")).append("</smsRcvYn>");
        } else if ("T01".equals(eventCd)) {
            sb.append("<clntIp>"+propertiesService.getString("clntIp")+"</clntIp>");
            sb.append("<clntUsrId>"+propertiesService.getString("clntUsrId")+"</clntUsrId>");
        } else if ("T02".equals(eventCd)) {
            sb.append("<clntIp>"+propertiesService.getString("clntIp")+"</clntIp>");
            sb.append("<clntUsrId>"+propertiesService.getString("clntUsrId")+"</clntUsrId>");
            sb.append("<acceptDt>"+reqMap.get("acceptDt")+"</acceptDt>");
            sb.append("<acceptChCd>"+reqMap.get("acceptChCd")+"</acceptChCd>");
            sb.append("<deliveryDivCd>"+reqMap.get("deliveryDivCd")+"</deliveryDivCd>");
        }

        return sb.toString();
    }

    public void doTrustToCertificates() throws Exception {
//        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
                    return;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
                    return;
                }
            }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HostnameVerifier hv = new HostnameVerifier() {
            public boolean verify(String urlHostName, SSLSession session) {
                if (!urlHostName.equalsIgnoreCase(session.getPeerHost())) {
                    logger.warn("Warning: URL host '" + urlHostName + "' is different to SSLSession host '" + session.getPeerHost() + "'.");
                }
                return true;
            }
        };
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }

    private String encrypt(HashMap<String, String> map, String key) {
        String encryptYn = map.get("encryptYn");
        String value = map.get(key);
        String result = value;
        if ("Y".equals(encryptYn)) {
            result = KisaSeedUtil.encrypt(value);
        }
        if (result == null || result == "null") {
            result = "";
        }
        return result;
    }

    private String decrypt(HashMap<String, String> map, String key) {
        String encryptYn = map.get("encryptYn");
        String value = map.get(key);
        String result = value;
        if ("Y".equals(encryptYn)) {
            result = KisaSeedUtil.decrypt(value);
        }
        if (result == null || result == "null") {
            result = "";
        }
        return result;
    }
}
