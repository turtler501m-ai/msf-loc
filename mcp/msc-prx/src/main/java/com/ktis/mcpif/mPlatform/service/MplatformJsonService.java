package com.ktis.mcpif.mPlatform.service;

import com.ktis.mcpif.mPlatform.event.EventCode;
import com.ktis.mcpif.mPlatform.event.EventRequest;
import com.ktis.mcpif.soap.XmlWrapperParameter;
import com.ktis.mcpif.soap.XmlWrapper;
import egovframework.rte.fdl.property.EgovPropertyService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.XMLOutputter;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import javax.annotation.Resource;
import javax.net.ssl.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.*;
import javax.xml.transform.dom.DOMSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class MplatformJsonService {
    protected Logger logger = LogManager.getLogger(getClass());

    @Resource
    EgovPropertyService propertiesService;

    public String call(EventRequest eventRequest) throws MalformedURLException {
        String url = propertiesService.getString("mPlatForm.url");
        EventCode eventCode = eventRequest.getEventCode();
        String appEventCd = eventCode.name();
        String serviceName = eventCode.getService();
        final int connetTime = propertiesService.getInt("maxTime");

        URL requestUrl = createRequestUrl(url, serviceName, connetTime);

        String mPlatformResponse = null;
        try {
            SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
            soapMessage.getMimeHeaders().addHeader("Authorization", "Basic " + getBasicAuthentication());
            doTrustToCertificates();

            //XML 메시지 생성
            XmlWrapperParameter xmlWrapperParameter = getXmlWrapperParameter(eventRequest);
            XmlWrapper xmlWrapper = new XmlWrapper(xmlWrapperParameter);
            String requestXml = xmlWrapper.buildXml();

            //파싱 객체 생성
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = docBuilder.parse(new ByteArrayInputStream(requestXml.getBytes()));
            DOMSource domSource = new DOMSource(doc);
            soapMessage.getSOAPPart().setContent(domSource);

            logger.error("[selfcareJson] ★★★★★ Call Start ★★★★★");
            logger.error("[selfcareJson] ★★ requestService : "+serviceName);
            logger.error("[selfcareJson] ★★ appEventCd : "+appEventCd);
            logger.error("[selfcareJson] ★★ requestXml : "+requestXml);
            logger.error("[selfcareJson] ★★ requestUrl : "+requestUrl);

            // SOAP CALL
            SOAPConnection soapConnection = SOAPConnectionFactory.newInstance().createConnection();
            SOAPMessage responseMessage = soapConnection.call(soapMessage, requestUrl);
            soapConnection.close();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            responseMessage.writeTo(out);
            mPlatformResponse = out.toString();
            logger.error("[selfcareJson] ★★ responseXml : "+mPlatformResponse);

            // 결과 복호화
            if ("Y".equals(eventRequest.getEncryptYn())) {
                mPlatformResponse = this.toResponseParse(mPlatformResponse, appEventCd);
            }
            logger.error("[selfcareJson] ★★★★★  Call End  ★★★★★");
        } catch (Exception e) {
            logger.error("mPlatformServiceWebServiceCall");
            logger.error(e);
        }
        return mPlatformResponse;
    }

    private XmlWrapperParameter getXmlWrapperParameter(EventRequest eventRequest) {
        XmlWrapperParameter xmlWrapperParameter = new XmlWrapperParameter();
        xmlWrapperParameter.setAppEntrPrsnId(propertiesService.getString("appEntrPrsnId"));
        xmlWrapperParameter.setAppAgncCd(propertiesService.getString("appAgncCd"));
        xmlWrapperParameter.setAppSendDateTime(getCurrentTimeMillisString());
        xmlWrapperParameter.setAppNstepUserId(propertiesService.getString("appNstepUserId"));
        xmlWrapperParameter.setBodyContent(eventRequest.toBodyContentXml());
        xmlWrapperParameter.setEventCode(eventRequest.getEventCode());
        return xmlWrapperParameter;
    }

    private static URL createRequestUrl(String url, String serviceName, final int connetTime) throws MalformedURLException {
        return new URL(new URL(url), serviceName, new URLStreamHandler() {
            @Override
            protected URLConnection openConnection(URL url1) throws IOException {
                URL clone = new URL(url1.toString());
                URLConnection clone_connection = clone.openConnection();
                clone_connection.setConnectTimeout(connetTime * 2000);
                clone_connection.setReadTimeout(connetTime * 2000);
                return(clone_connection);
            }
        });
    }

    private static String getCurrentTimeMillisString() {
        long time = System.currentTimeMillis();
        SimpleDateFormat dayTime = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return dayTime.format(new Date(time));
    }

    private String getBasicAuthentication() {
        String loginPassword = propertiesService.getString("clntUsrId")+":"+propertiesService.getString("clntUsrPw");
        return new sun.misc.BASE64Encoder().encode(loginPassword.getBytes());
    }

    private void doTrustToCertificates() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        return;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
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

    private String toResponseParse(String responseXml, String appEventCd) throws JDOMException, IOException {
        String result = responseXml;

        Element root = XmlParse.getRootElement("<?xml version=\"1.0\" encoding=\"euc-kr\"?>"+responseXml);
        Element rtn = XmlParse.getReturnElement(root);

        Element commHeader = XmlParse.getChildElement(rtn, "commHeader");
        String resultCode= XmlParse.getChildValue(commHeader, "responseType");

        if("N".equals(resultCode)) {
            try {
                this.parse(rtn, appEventCd);
                XMLOutputter outp = new XMLOutputter();
                result = outp.outputString(root);
            } catch (Exception e) {
                return result;
            }
        }

        return result;
    }

    private void parse(Element rtn, String appEventCd) {

    }
}
