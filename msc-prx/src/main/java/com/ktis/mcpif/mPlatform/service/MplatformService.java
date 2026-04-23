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
import java.util.*;

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

import org.apache.commons.codec.binary.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.XMLOutputter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;

import com.ktis.mcpif.common.KisaSeedUtil;
import com.ktis.mcpif.common.StringUtil;
//import com.ktis.mcpif.mPlatform.mapper.mapper.MPlatformMapper;

import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * SELFCARE 호출 서비스
 *
 */
@org.springframework.stereotype.Service("mPlatformService")
public class MplatformService {
    protected Logger logger = LogManager.getLogger(getClass());

    @Resource(name = "propertiesService")
    private EgovPropertyService propertiesService;

//    @Autowired
//    MPlatformMapper mpMapper;

    /**
     * N-Step 서비스 호출
     * @param reqMessage
     * @return
     * @throws SOAPException
     * @throws MalformedURLException
     */
    public String mPlatformServiceWebServiceCall(String reqMessage) throws SOAPException, MalformedURLException {

        RestTemplate restTemplate = new RestTemplate();
        String apiInterfaceServer = propertiesService.getString("api.interface.server");

        final int connetTime = propertiesService.getInt("maxTime");
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

        String url = propertiesService.getString("mPlatForm.url");

        String serviceName = getEventCodeInfo(reqMap.get("appEventCd"), "service");


        // 호출 URL
        URL requestUrl = new URL(new URL(url), serviceName, new URLStreamHandler() {
            @Override
            protected URLConnection openConnection(URL url) throws IOException {
                URL clone = new URL(url.toString());
                URLConnection clone_connection = clone.openConnection();
                clone_connection.setConnectTimeout(connetTime * 2000);		// 30sec
                clone_connection.setReadTimeout(connetTime * 2000);      	// 30sec

                return(clone_connection);
            }
        });

        try {
            String loginPassword = propertiesService.getString("clntUsrId")+":"+propertiesService.getString("clntUsrPw");
            String authorization = new sun.misc.BASE64Encoder().encode(loginPassword.getBytes());
            soapMessage.getMimeHeaders().addHeader("Authorization", "Basic " + authorization);

            doTrustToCertificates();

            //암복호화여부 체크
            String encryptYn = restTemplate.postForObject(apiInterfaceServer + "/mPlatform/getMplatformCryptionYn", reqMap.get("appEventCd"), String.class);

            //XML 메시지 생성
            String requestXml = xmlMessageCreate(reqMap, encryptYn);


            //파싱 객체 생성
            docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            // String 타입 변경
            doc = docBuilder.parse(new ByteArrayInputStream(requestXml.getBytes()));

            domSource = new DOMSource(doc);

            soapMessage.getSOAPPart().setContent(domSource);

            logger.error("[selfcare] ★★★★★ Call Start ★★★★★");
            logger.error("[selfcare] ★★ requestService : "+serviceName);
            logger.error("[selfcare] ★★ appEventCd : "+reqMap.get("appEventCd"));
            logger.error("[selfcare] ★★ requestXml : "+requestXml);
            logger.error("[selfcare] ★★ requestUrl : "+requestUrl);

            // SOAP CALL
            SOAPMessage responseMessage = soapConnection.call(soapMessage, requestUrl);
            soapConnection.close();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            responseMessage.writeTo(out);
            mPlatformResponse = new String(out.toByteArray());
            logger.error("[selfcare] ★★ responseXml : "+mPlatformResponse);

            // 결과 복호화
            if ("Y".equals(encryptYn)) {
                mPlatformResponse = this.toResponseParse(mPlatformResponse, reqMap.get("appEventCd"));
            }

            logger.error("[selfcare] ★★★★★  Call End  ★★★★★");

        } catch (Exception e) {
            logger.error("mPlatformServiceWebServiceCall");
            logger.error(e);
        } finally {

            try{
                /**
                 * 2018-11-16, selfcare 연동로그 생성
                 */
                //mpMapper.insertSelfCareLog(reqMap);
                restTemplate.postForObject(apiInterfaceServer + "/mPlatform/insertSelfCareLog", reqMap, Void.class);
                logger.error("insertSelfCareLog[END]:#######");

            }catch(Exception e){
                logger.error("insertSelfCareLog error : "+e.getMessage());
            }

        }

        return mPlatformResponse;
    }

    public HashMap<String, String> paramMapCreate(String reqMsg) {

        String[] result = reqMsg.split("&");
        HashMap<String, String> paramMap = new HashMap<String, String>();

        for(int i=0;  i<result.length; i++){
            String param0 = StringUtil.NVL(result[i],"");
            if(param0.indexOf("=") < 0){
                continue;
            }

            String strParam0 = param0.substring(0,param0.indexOf("="));
            String strParam1 = param0.substring(param0.indexOf("=")+1,param0.length());
            paramMap.put(strParam0, strParam1);
        }
        //20160202 암호화 value값에 ==에 들어가서 다른형식으로 파싱
        /*for(int i=0; i<result.length; i++){
            String[] strParam = result[i].split("=");
            paramMap.put(strParam[0], strParam[1]);
        }*/

        return paramMap;
    }

    public String getEventCodeInfo(String eventCd, String type) {

        if (eventCd.equals("X01")) {
            if (type.equals("service")) {
                return "MoscPerInfoService";
            } else if (type.equals("info")) {
                return "moscPerInfo";
            } else if (type.equals("vo")) {
                return "MoscPerInfoInVO";
            }
        } else if (eventCd.equals("X02")) {
            if (type.equals("service")) {
                return "MoscPerInfoService";
            } else if (type.equals("info")) {
                return "moscPerAddrChg";
            } else if (type.equals("vo")) {
                return "MoscPerAddrChgInVO";
            }
        } else if (eventCd.equals("X03")) {
            if (type.equals("service")) {
                return "MoscBilEmailService";
            } else if (type.equals("info")) {
                return "moscBilEmailInfo";
            } else if (type.equals("vo")) {
                return "MoscBilEmailInfoInVO";
            }
        } else if (eventCd.equals("X04")) {
            if (type.equals("service")) {
                return "MoscBilEmailService";
            } else if (type.equals("info")) {
                return "moscBilEmailChg";
            } else if (type.equals("vo")) {
                return "MoscBilEmailChgInVO";
            }
        } else if (eventCd.equals("X05")) {
            if (type.equals("service")) {
                return "MoscBilEmailService";
            } else if (type.equals("info")) {
                return "moscBilEmailSch";
            } else if (type.equals("vo")) {
                return "MoscBilEmailSchInVO";
            }
        } else if (eventCd.equals("X06")) {
            if (type.equals("service")) {
                return "MoscBilEmailService";
            } else if (type.equals("info")) {
                return "moscBilEmailSchDet";
            } else if (type.equals("vo")) {
                return "MoscBilEmailSchDetInVO";
            }
        } else if (eventCd.equals("X07")) {
            if (type.equals("service")) {
                return "MoscBilEmailResService";
            } else if (type.equals("info")) {
                return "moscBilEmailResInfo";
            } else if (type.equals("vo")) {
                return "MoscBilEmailResInfoInVO";
            }
        } else if (eventCd.equals("X08")) {
            if (type.equals("service")) {
                return "MoscBilEmailResService";
            } else if (type.equals("info")) {
                return "moscBilEmailReSendInfo";
            } else if (type.equals("vo")) {
                return "MoscBilEmailReSendInfoInVO";
            }
        } else if (eventCd.equals("X09")) {
            if (type.equals("service")) {
                return "MoscBilEmailResService";
            } else if (type.equals("info")) {
                return "moscBilEmailReSendChg";
            } else if (type.equals("vo")) {
                return "MoscBilEmailReSendChgInVO";
            }
        } else if (eventCd.equals("X10")) {
            if (type.equals("service")) {
                return "MoscBilPrintService";
            } else if (type.equals("info")) {
                return "moscBilPrintInfo";
            } else if (type.equals("vo")) {
                return "MoscBilPrintInfoInVO";
            }
        } else if (eventCd.equals("X11")) {
            if (type.equals("service")) {
                return "MoscBilPrintService";
            } else if (type.equals("info")) {
                return "moscBilPrintChg";
            } else if (type.equals("vo")) {
                return "MoscBilPrintChgInVO";
            }
        } else if (eventCd.equals("X12")) {
            if (type.equals("service")) {
                return "MoscTelCallService";
            } else if (type.equals("info")) {
                return "moscTelCallTotUseTime";
            } else if (type.equals("vo")) {
                return "MoscTelCallTotUseTimeInVO";
            }
        } else if (eventCd.equals("X13")) {
            if (type.equals("service")) {
                return "MoscTelCallService";
            } else if (type.equals("info")) {
                return "moscTelCallInfo";
            } else if (type.equals("vo")) {
                return "MoscTelCallInfoInVO";
            }
        } else if (eventCd.equals("X14")) {
            if (type.equals("service")) {
                return "MoscTelCallService";
            } else if (type.equals("info")) {
                return "moscTelCallInfoDet";
            } else if (type.equals("vo")) {
                return "MoscTelCallInfoDetInVO";
            }
        } else if (eventCd.equals("X15")) {
            if (type.equals("service")) {
                return "MoscFarPriceService";
            } else if (type.equals("info")) {
                return "moscFarPriceInfo";
            } else if (type.equals("vo")) {
                return "MoscFarPriceInfoInVO";
            }
        } else if (eventCd.equals("X16")) {
            if (type.equals("service")) {
                return "MoscFarPriceService";
            } else if (type.equals("info")) {
                return "moscFarPriceInfoDet";
            } else if (type.equals("vo")) {
                return "MoscFarPriceInfoDetInVO";
            }
        } else if (eventCd.equals("X17")) {
            if (type.equals("service")) {
                return "MoscFarPriceService";
            } else if (type.equals("info")) {
                return "moscFarPriceInfoDetItem";
            } else if (type.equals("vo")) {
                return "MoscFarPriceInfoDetItemInVO";
            }
        } else if (eventCd.equals("X18")) {
            if (type.equals("service")) {
                return "MoscFarPriceService";
            } else if (type.equals("info")) {
                return "moscFarPriceRealTimeInfo";
            } else if (type.equals("vo")) {
                return "MoscFarPriceRealTimeInfoInVO";
            }
        } else if (eventCd.equals("X19")) { //
            if (type.equals("service")) {
                return "MoscFarPriceService";
            } else if (type.equals("info")) {
                return "moscFarPricePlanChg";
            } else if (type.equals("vo")) {
                return "MoscFarPricePlanChgInVO";
            }
        } else if (eventCd.equals("X20")) {
            if (type.equals("service")) {
                return "MoscRegSvcService";
            } else if (type.equals("info")) {
                return "moscRegSvcInfo";
            } else if (type.equals("vo")) {
                return "MoscRegSvcInfoInVO";
            }
        } else if (eventCd.equals("X21")) { //
            if (type.equals("service")) {
                return "MoscRegSvcService";
            } else if (type.equals("info")) {
                return "moscRegSvcChg";
            } else if (type.equals("vo")) {
                return "MoscRegSvcChgInVO";
            }
        } else if (eventCd.equals("X22")) {
            if (type.equals("service")) {
                return "MoscFarPayService";
            } else if (type.equals("info")) {
                return "moscFarPayInfo";
            } else if (type.equals("vo")) {
                return "MoscFarPayInfoInVO";
            }
        } else if (eventCd.equals("X23")) {
            if (type.equals("service")) {
                return "MoscFarChgWayService";
            } else if (type.equals("info")) {
                return "moscFarChgWayInfo";
            } else if (type.equals("vo")) {
                return "MoscFarChgWayInfoInVO";
            }
        } else if (eventCd.equals("X24")) {
            if (type.equals("service")) {
                return "MoscFarChgWayService";
            } else if (type.equals("info")) {
                return "moscFarChgWayHis";
            } else if (type.equals("vo")) {
                return "MoscFarChgWayHisInVO";
            }
        } else if (eventCd.equals("X25")) {
            if (type.equals("service")) {
                return "MoscFarChgWayService";
            } else if (type.equals("info")) {
                return "moscFarChgWayChg";
            } else if (type.equals("vo")) {
                return "MoscFarChgWayChgInVO";
            }
        } else if (eventCd.equals("X26")) {
            if (type.equals("service")) {
                return "MoscSuspenService";
            } else if (type.equals("info")) {
                return "moscSuspenHis";
            } else if (type.equals("vo")) {
                return "MoscSuspenHisInVO";
            }
        } else if (eventCd.equals("X27")) {
            if (type.equals("service")) {
                return "MoscSuspenService";
            } else if (type.equals("info")) {
                return "moscSuspenPosInfo";
            } else if (type.equals("vo")) {
                return "MoscSuspenPosInfoInVO";
            }
        } else if (eventCd.equals("X28")) {
            if (type.equals("service")) {
                return "MoscSuspenService";
            } else if (type.equals("info")) {
                return "moscSuspenCnlPosInfo";
            } else if (type.equals("vo")) {
                return "MoscSuspenCnlPosInfoInVO";
            }
        } else if (eventCd.equals("X29")) { //
            if (type.equals("service")) {
                return "MoscSuspenService";
            } else if (type.equals("info")) {
                return "moscSuspenChg";
            } else if (type.equals("vo")) {
                return "MoscSuspenChgInVO";
            }
        } else if (eventCd.equals("X30")) { //
            if (type.equals("service")) {
                return "MoscSuspenService";
            } else if (type.equals("info")) {
                return "moscSuspenCnlChg";
            } else if (type.equals("vo")) {
                return "MoscSuspenCnlChgInVO";
            }
        } else if (eventCd.equals("X31")) {
            if (type.equals("service")) {
                return "MoscNumChgeService";
            } else if (type.equals("info")) {
                return "moscNumChgeList";
            } else if (type.equals("vo")) {
                return "MoscNumChgeListInVO";
            }
        } else if (eventCd.equals("X32")) { //
            if (type.equals("service")) {
                return "MoscNumChgeService";
            } else if (type.equals("info")) {
                return "moscNumChgeChg";
            } else if (type.equals("vo")) {
                return "MoscNumChgeChgInVO";
            }
        } else if (eventCd.equals("X33")) {
            if (type.equals("service")) {
                return "MoscPcsLostService";
            } else if (type.equals("info")) {
                return "moscPcsLostInfo";
            } else if (type.equals("vo")) {
                return "MoscPcsLostInfoInVO";
            }
        } else if (eventCd.equals("X34")) { //
            if (type.equals("service")) {
                return "MoscPcsLostService";
            } else if (type.equals("info")) {
                return "moscPcsLostChg";
            } else if (type.equals("vo")) {
                return "MoscPcsLostChgInVO";
            }
        } else if (eventCd.equals("X35")) { //
            if (type.equals("service")) {
                return "MoscPcsLostService";
            } else if (type.equals("info")) {
                return "moscPcsLostCnlChg";
            } else if (type.equals("vo")) {
                return "MoscPcsLostCnlChgInVO";
            }
        }  else if (eventCd.equals("X36")) { //
            if (type.equals("service")) {
                return "MoscTesMgmtService";
            } else if (type.equals("info")) {
                return "moscTesBalanceInquiry";
            } else if (type.equals("vo")) {
                return "MoscTesBalanceInquiryInVO";
            }
        } else if (eventCd.equals("X38")) {
            if (type.equals("service")) {
                return "MoscRegSvcService";
            } else if (type.equals("info")) {
                return "moscRegSvcCanChg";
            } else if (type.equals("vo")) {
                return "MoscRegSvcCanChgInVO";
            }
        } else if (eventCd.equals("X49")) {
            if (type.equals("service")) {
                return "MoscBillMgmtService";
            } else if (type.equals("info")) {
                return "kosMoscBillInfo";
            } else if (type.equals("vo")) {
                return "MoscBillInfoInVO";
            }
        } else if (eventCd.equals("X50")) {
            if (type.equals("service")) {
                return "MoscBillMgmtService";
            } else if (type.equals("info")) {
                return "kosMoscBillChg";
            } else if (type.equals("vo")) {
                return "MoscBillChgInVO";
            }
        } else if (eventCd.equals("X55")) {
            if (type.equals("service")) {
                return "MpRelaySendTcpMotService";
            } else if (type.equals("info")) {
                return "trtmMpRelaySendTcpMot";
            } else if (type.equals("vo")) {
                return "MpRelaySendTcpMotInVO";
            }
        } else if (eventCd.equals("X54")) {
            if (type.equals("service")) {
                return "MoscSpnsrMgmtService";
            } else if (type.equals("info")) {
                return "kosMoscSpnsrItgInfo";
            } else if (type.equals("vo")) {
                return "MoscSpnsrItgInfoInVO";
            }
        } else if (eventCd.equals("X59")) {
            if (type.equals("service")) {
                return "MoscSdsSvcService";
            } else if (type.equals("info")) {
                return "moscSdsSvcPreChk";
            } else if (type.equals("vo")) {
                return "MoscSdsSvcInVO";
            }
        } else if (eventCd.equals("X60")) {
            if (type.equals("service")) {
                return "MoscSdsSvcService";
            } else if (type.equals("info")) {
                return "moscSdsSvcChg";
            } else if (type.equals("vo")) {
                return "MoscSdsSvcInVO";
            }
        } else if (eventCd.equals("X61")) {
            if (type.equals("service")) {
                return "MoscSdsSvcService";
            } else if (type.equals("info")) {
                return "moscSdsSvcCanChg";
            } else if (type.equals("vo")) {
                return "MoscSdsSvcInVO";
            }
        } else if (eventCd.equals("X62")) {
            if (type.equals("service")) {
                return "MoscSdsSvcService";
            } else if (type.equals("info")) {
                return "moscSdsInfo";
            } else if (type.equals("vo")) {
                return "MoscSdsSvcInVO";
            }
        } else if (eventCd.equals("X69")) {
            if (type.equals("service")) {
                return "MoscDataSharingMgmtService";
            } else if (type.equals("info")) {
                return "moscDataSharingPosList";
            } else if (type.equals("vo")) {
                return "MoscDataSharingPosListInVO";
            }
        } else if (eventCd.equals("X70")) {
            if (type.equals("service")) {
                return "MoscDataSharingMgmtService";
            } else if (type.equals("info")) {
                return "moscDataSharingChg";
            } else if (type.equals("vo")) {
                return "MoscDataSharingChgInVO";
            }
        } else if (eventCd.equals("X71")) {
            if (type.equals("service")) {
                return "MoscDataSharingMgmtService";
            } else if (type.equals("info")) {
                return "moscDataSharingInfo";
            } else if (type.equals("vo")) {
                return "MoscDataSharingInfoInVO";
            }
        } else if (eventCd.equals("X73")) {
            if (type.equals("service")) {
                return "MoscRoamQntMgmtService";
            } else if (type.equals("info")) {
                return "moscRoamUseQntInfo";
            } else if (type.equals("vo")) {
                return "MoscRoamUseQntInVO";
            }
        } else if (eventCd.equals("X74")) {
            if (type.equals("service")) {
                return "MoscCoupMgmtService";
            } else if (type.equals("info")) {
                return "inqrCoupInfo";
            } else if (type.equals("vo")) {
                return "InqrCoupInfoInVO";
            }
        } else if (eventCd.equals("X75")) {
            if (type.equals("service")) {
                return "MoscCoupMgmtService";
            } else if (type.equals("info")) {
                return "trtmCoupUse";
            } else if (type.equals("vo")) {
                return "TrtmCoupUseInVO";
            }
        } else if (eventCd.equals("X76")) {
            if (type.equals("service")) {
                return "MoscCoupMgmtService";
            } else if (type.equals("info")) {
                return "inqrUsedCoupList";
            } else if (type.equals("vo")) {
                return "InqrUsedCoupListInVO";
            }
        } else if (eventCd.equals("X80")) {
            if (type.equals("service")) {
                return "MoscOtpSvcService";
            } else if (type.equals("info")) {
                return "moscOtpSvcInfo";
            } else if (type.equals("vo")) {
                return "MoscOtpSvcInVO";
            }
        } else if (eventCd.equals("D01")) {
            if (type.equals("service")) {
                return "DeliveryUsimMgmtService";
            } else if (type.equals("info")) {
                return "inqrPsblDeliveryAddr";
            } else if (type.equals("vo")) {
                return "InqrPsblDeliveryAddrInVO";
            }
        } else if (eventCd.equals("D02")) {
            if (type.equals("service")) {
                return "DeliveryUsimMgmtService";
            } else if (type.equals("info")) {
                return "acceptDeliveryUsim";
            } else if (type.equals("vo")) {
                return "AcceptDeliveryUsimInVO";
            }
        } else if (eventCd.equals("D03")) {
            if (type.equals("service")) {
                return "DeliveryUsimMgmtService";
            } else if (type.equals("info")) {
                return "updateDeliveryUsim";
            } else if (type.equals("vo")) {
                return "UpdateDeliveryUsimInVO";
            }
        } else if (eventCd.equals("D04")) {
            if (type.equals("service")) {
                return "DeliveryUsimMgmtService";
            } else if (type.equals("info")) {
                return "inqrDeliveryOrderInfo";
            } else if (type.equals("vo")) {
                return "InqrDeliveryOrderInfoInVO";
            }
        } else if (eventCd.equals("X82")) {
            if (type.equals("service")) {
                return "MoscSimplePaySvcService";
            } else if (type.equals("info")) {
                return "moscSimplePaySms";
            } else if (type.equals("vo")) {
                return "MoscSimplePaySmsInVO";
            }
        } else if (eventCd.equals("X83")) {
            if (type.equals("service")) {
                return "MoscWireUseMgmtService";
            } else if (type.equals("info")) {
                return "moscWireUseTimeInfo";
            } else if (type.equals("vo")) {
                return "MoscWireUseTimeInfoInVO";
            }
        } else if (eventCd.equals("X84")) {
            if (type.equals("service")) {
                return "DeliveryUsimMgmtService";
            } else if (type.equals("info")) {
                return "inqrDeliveryOrderInfo";
            } else if (type.equals("vo")) {
                return "InqrDeliveryOrderInfoInVO";
            }
        } else if (eventCd.equals("X85")) {
            if (type.equals("service")) {
                return "MoscIntmMgmtService";
            } else if (type.equals("info")) {
                return "moscInqrUsimUsePsbl";
            } else if (type.equals("vo")) {
                return "MoscInqrUsimUsePsblInVO";
            }
        } else if (eventCd.equals("X86")) {
            if (type.equals("service")) {
                return "MoscCombStatMgmtService";
            } else if (type.equals("info")) {
                return "moscCombStatMgmtInfo";
            } else if (type.equals("vo")) {
                return "MoscCombStatMgmtInfoInVO";
            }
        }   else if (eventCd.equals("X87")) {
            // mvno 결합 서비스 계약 조회

            if (type.equals("service")) {
                return "MoscCombMgmtService";
            } else if (type.equals("info")) {
                return "moscCombDtl";
            } else if (type.equals("vo")) {
                return "MoscCombDtlInVO";
            }


        } else if (eventCd.equals("X79")) {
            // mvno 결합 저장

            if (type.equals("service")) {
                return "MoscCombMgmtService";
            } else if (type.equals("info")) {
                return "moscCombChg";
            } else if (type.equals("vo")) {
                return "MoscCombChgInVO";
            }


        } else if (eventCd.equals("X78")) {
            // mvno 결합 사전 체크

            if (type.equals("service")) {
                return "MoscCombMgmtService";
            } else if (type.equals("info")) {
                return "moscCombPreChk";
            } else if (type.equals("vo")) {
                return "MoscCombChgInVO";
            }


        } else if (eventCd.equals("X77")) {
            // MVNO 결합 상태 조회

            if (type.equals("service")) {
                return "MoscCombMgmtService";
            } else if (type.equals("info")) {
                return "moscCombInfo";
            } else if (type.equals("vo")) {
                return "MoscCombInfoInVO";
            }
        } else if (eventCd.equals("X88")) {
            // 요금상품예약변경

            if (type.equals("service")) {
                return "MoscFarPriceService";
            } else if (type.equals("info")) {
                return "moscFarPricePlanRsrvChg";
            } else if (type.equals("vo")) {
                return "MoscFarPricePlanRsrvChgInVO";
            }
        }else if (eventCd.equals("X89")) {
            // 요금상품예약변경조회(X89)

            if (type.equals("service")) {
                return "MoscFarPriceService";
            } else if (type.equals("info")) {
                return "moscFarPricePlanRsrvInfo";
            } else if (type.equals("vo")) {
                return "MoscFarPriceRsrvInfoInVO";
            }
        } else if (eventCd.equals("X90")) {
            // 요금상품예약변경취소(X90)

            if (type.equals("service")) {
                return "MoscFarPriceService";
            } else if (type.equals("info")) {
                return "moscFarPricePlanRsrvCncl";
            } else if (type.equals("vo")) {
                return "MoscFarPricePlanRsrvCnclInVO";
            }
        } else if (eventCd.equals("X91")) {
            // 신용카드 인증조회(X91)
            if (type.equals("service")) {
                return "MoscCrdtCardAthnService";
            } else if (type.equals("info")) {
                return "moscCrdtCardAthnInfo";
            } else if (type.equals("vo")) {
                return "MoscCrdtCardAthnInVO";
            }
        } else if (eventCd.equals("X68")) {
            //미납요금 즉시수납 처리 (X68)

            if (type.equals("service")) {
                return "MoscPymnService";
            } else if (type.equals("info")) {
                return "moscPymnTreat";
            } else if (type.equals("vo")) {
                return "MoscPymnTreatInVO";
            }
        }else if (eventCd.equals("X67")) {
            //미납요금 월별 조회(X67)

            if (type.equals("service")) {
                return "MoscPymnService";
            } else if (type.equals("info")) {
                return "moscPymnMonthInfo";
            } else if (type.equals("vo")) {
                return "MoscPymnMonthInfoInVO";
            }
        } else if (eventCd.equals("X92")) {
            //당월요금+미납요금 조회(해당 계약에 속해 있는 청구계정 단위로 조회)  x92

            if (type.equals("service")) {
                return "MoscCurrMthNpayMgmtService";
            } else if (type.equals("info")) {
                return "moscCurrMthNpayInfo";
            } else if (type.equals("vo")) {
                return "MoscCurrMthNpayInfoInVO";
            }
        } else if (eventCd.equals("X93")) {
            //x93 즉시납부

            if (type.equals("service")) {
                return "MoscCurrMthNpayMgmtService";
            } else if (type.equals("info")) {
                return "moscCurrMthNpayTreat";
            } else if (type.equals("vo")) {
                return "MoscPymnTreatInVO";
            }
        } else if (eventCd.equals("X51")) {
            if (type.equals("service")) {
                return "MoscBillMgmtService";
            } else if (type.equals("info")) {
                return "kosMoscBillSendInfo";
            } else if (type.equals("vo")) {
                return "MoscBillSendInfoInVO";
            }
        } else if (eventCd.equals("X52")) {
            if (type.equals("service")) {
                return "MoscBillMgmtService";
            } else if (type.equals("info")) {
                return "kosMoscBillReSendInfo";
            } else if (type.equals("vo")) {
                return "MoscBillReSendInfoInVO";
            }
        } else if (eventCd.equals("X53")) {
            if (type.equals("service")) {
                return "MoscBillMgmtService";
            } else if (type.equals("info")) {
                return "kosMoscBillReSendChg";
            } else if (type.equals("vo")) {
                return "MoscBillReSendChgInVO";
            }
        } else if (eventCd.equals("Y12")) {
            if (type.equals("service")) {
                return "MoscIntmInfoRetvService";
            } else if (type.equals("info")) {
                return "moscRetvIntmMdlSpecInfo";
            } else if (type.equals("vo")) {
                return "MoscRetvIntmMdlSpecInVO";
            }
        } else if (eventCd.equals("Y13")) {
            if (type.equals("service")) {
                return "MoscIntmInfoRetvService";
            } else if (type.equals("info")) {
                return "moscRetvIntmOrrgInfo";
            } else if (type.equals("vo")) {
                return "MvnoIntmInfoDualRecInVO";
            }
        } else if (eventCd.equals("Y14")) {
            if (type.equals("service")) {
                return "MoscOmdIntmMgmtService";
            } else if (type.equals("info")) {
                return "moscBfacChkOmdIntm";
            } else if (type.equals("vo")) {
                return "MoscBfacChkOmdIntmInVO";
            }
        } else if (eventCd.equals("Y15")) {
            if (type.equals("service")) {
                return "MoscOmdIntmMgmtService";
            } else if (type.equals("info")) {
                return "moscTrtOmdIntm";
            } else if (type.equals("vo")) {
                return "MoscTrtOmdIntmInVO";
            }
        //Y07 유심PUK 조회
        } else if ("Y07".equals(eventCd)) {
            if (type.equals("service")) {
                return "MoscIntmMgmtService";
            } else if (type.equals("info")) {
                return "moscInqrUsimPuk";
            } else if (type.equals("vo")) {
                return "MoscInqrUsimPukInVO";
            }
        } else if ("X95".equals(eventCd)) {
            if (type.equals("service")) {
                return "OmdIntmTrtSvcService";
            } else if (type.equals("info")) {
                return "hndsetListInqrSvc";
            } else if (type.equals("vo")) {
                return "OmdIntmMdlInfoInVO";
            }
        } else if ("Y29".equals(eventCd)) {
            if (type.equals("service")) {
                return "MoscCommCdService";
            } else if (type.equals("info")) {
                return "moscCommCdInfo";
            } else if (type.equals("vo")) {
                return "MoscCommCdInfoInVO";
            }
        //X97 가입중인 부가서비스 리스트 조회 - 파람포함
        } else if (eventCd.equals("X97")) {
            if (type.equals("service")) {
                return "MoscRegSvcService";
            } else if (type.equals("info")) {
                return "moscRegSvcInfoNew";
            } else if (type.equals("vo")) {
                return "MoscRegSvcInfoInVO";
            }
        } else if ("Y39".equals(eventCd)) {
            if (type.equals("service")) {
                return "MoscSvcContService";
            } else if (type.equals("info")) {
                return "moscSvcContIpinInfo";
            } else if (type.equals("vo")) {
                return "MoscSvcContIpinInVO";
            }
        } else if ("Y24".equals(eventCd)) {
            if (type.equals("service")) {
                return "MoscPrdcTrtmService";
            } else if (type.equals("info")) {
                return "moscPrdcTrtmPreChk";
            } else if (type.equals("vo")) {
                return "MoscPrdcTrtmPreChkInVO";
            }
        } else if ("Y25".equals(eventCd)) {
            if (type.equals("service")) {
                return "MoscPrdcTrtmService";
            } else if (type.equals("info")) {
                return "moscPrdcTrtm";
            } else if (type.equals("vo")) {
                return "MoscPrdcTrtmInVO";
            }
        } else if ("Y40".equals(eventCd)) {
            if (type.equals("service")) {
                return "MoscContCustInfoAgreeService";
            } else if (type.equals("info")) {
                return "moscContCustInfoAgreeRetv";
            } else if (type.equals("vo")) {
                return "MoscContCustInfoAgreeRetvInVO";
            }
        } else if ("Y41".equals(eventCd)) {
            if (type.equals("service")) {
                return "MoscContCustInfoAgreeService";
            } else if (type.equals("info")) {
                return "moscContCustInfoAgreeChg";
            } else if (type.equals("vo")) {
                return "MoscContCustInfoAgreeChgInVO";
            }
        } else if ("Y42".equals(eventCd)) {
            if (type.equals("service")) {
                return "MoscRemindSmsStatMgmtService";
            } else if (type.equals("info")) {
                return "moscRemindSmsStatMgmt";
            } else if (type.equals("vo")) {
                return "MoscRemindSmsStatMgmtInVO";
            }
        } else if ("Y44".equals(eventCd)) {
            if (type.equals("service")) {
                return "MoscMstCombMgmtService";
            } else if (type.equals("info")) {
                return "moscSubMstCombChg";
            } else if (type.equals("vo")) {
                return "MoscSubMstCombChgInVO";
            }
        } else if ("Y48".equals(eventCd)) {
            if (type.equals("service")) {
                return "MoscVirtlBnkacnNoService";
            } else if (type.equals("info")) {
                return "moscVirtlBnkacnNoListInfo";
            } else if (type.equals("vo")) {
                return "MoscVirtlBnkacnNoListInfoInVO";
            }
        } else if ("X58".equals(eventCd)) {
            if (type.equals("service")) {
                return "MoscJuiceService";
            } else if (type.equals("info")) {
                return "kosMoscJuiceUpd";
            } else if (type.equals("vo")) {
                return "MoscJuiceInVO";
            }
        }

        return null;
    }

    public StringBuffer getInDto(HashMap<String, String> reqMap, String encryptYn) {
        StringBuffer selfStringBuffer = new StringBuffer();
        String eventCd = reqMap.get("appEventCd");
        if (eventCd.equals("X02")) {
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<addrZip>"+reqMap.get("addrZip")+"</addrZip>");
            selfStringBuffer.append("<adrPrimaryLn>"+this.encrypt(reqMap.get("adrPrimaryLn"), encryptYn)+"</adrPrimaryLn>");
            selfStringBuffer.append("<adrSecondaryLn>"+this.encrypt(reqMap.get("adrSecondaryLn"), encryptYn)+"</adrSecondaryLn>");
            selfStringBuffer.append("<bilCtnDisplay>"+reqMap.get("bilCtnDisplay")+"</bilCtnDisplay>");
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("X04")) {
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<status>"+reqMap.get("status")+"</status>");
            selfStringBuffer.append("<email>"+this.encrypt(reqMap.get("email"), encryptYn)+"</email>");
            selfStringBuffer.append("<securMailYn>"+reqMap.get("securMailYn")+"</securMailYn>");
            selfStringBuffer.append("<ecRcvAgreYn>"+reqMap.get("ecRcvAgreYn")+"</ecRcvAgreYn>");
            selfStringBuffer.append("<sendGubun>"+reqMap.get("sendGubun")+"</sendGubun>");
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("X09")) {
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<year>"+reqMap.get("year")+"</year>");
            selfStringBuffer.append("<month>"+reqMap.get("month")+"</month>");
            selfStringBuffer.append("<email>"+this.encrypt(reqMap.get("email"), encryptYn)+"</email>");
            selfStringBuffer.append("<securMailYn>"+reqMap.get("securMailYn")+"</securMailYn>");
            selfStringBuffer.append("<causeCode>"+reqMap.get("causeCode")+"</causeCode>");
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("X11")) {
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<addrChgType>P</addrChgType>");
            selfStringBuffer.append("<billDate>"+reqMap.get("billDate")+"</billDate>");
            selfStringBuffer.append("<requestReason>ET</requestReason>");
            selfStringBuffer.append("<sendGubun>F</sendGubun>");
            selfStringBuffer.append("<zipCode></zipCode>");
            selfStringBuffer.append("<pAddr></pAddr>");
            selfStringBuffer.append("<sAddr></sAddr>");
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("X12")) {
            selfStringBuffer.append("<inDto>");
            if(reqMap.get("useMonth") != null) {
                selfStringBuffer.append("<useMonth>"+reqMap.get("useMonth")+"</useMonth>");
            }
            selfStringBuffer.append("</inDto>");

        } else if (eventCd.equals("X14")) {
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<w2kind>"+reqMap.get("w2kind")+"</w2kind>");
            selfStringBuffer.append("<svcGroup>"+reqMap.get("svcGroup")+"</svcGroup>");
            selfStringBuffer.append("<startDate>"+reqMap.get("startDate")+"</startDate>");
            selfStringBuffer.append("<endDate>"+reqMap.get("endDate")+"</endDate>");
            selfStringBuffer.append("<dstPageNo>"+reqMap.get("dstPageNo")+"</dstPageNo>");
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("X15")) {
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<productionDate>"+reqMap.get("productionDate")+"</productionDate>");
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("X16")) {
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<billSeqNo>"+reqMap.get("billSeqNo")+"</billSeqNo>");
            selfStringBuffer.append("<billDueDateList>"+reqMap.get("billDueDateList")+"</billDueDateList>");
            selfStringBuffer.append("<billMonth>"+reqMap.get("billMonth")+"</billMonth>");
            selfStringBuffer.append("<billStartDate>"+reqMap.get("billStartDate")+"</billStartDate>");
            selfStringBuffer.append("<billEndDate>"+reqMap.get("billEndDate")+"</billEndDate>");
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("X17")) {
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<billSeqNo>"+reqMap.get("billSeqNo")+"</billSeqNo>");
            selfStringBuffer.append("<billMonth>"+reqMap.get("billMonth")+"</billMonth>");
            selfStringBuffer.append("<messageLine>"+reqMap.get("messageLine")+"</messageLine>");
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("X19")) {
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<soc>"+reqMap.get("soc")+"</soc>");
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("X21")) {
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<soc>"+reqMap.get("soc")+"</soc>");
            if(null !=reqMap.get("ftrNewParam")){
                selfStringBuffer.append("<ftrNewParam>"+reqMap.get("ftrNewParam")+"</ftrNewParam>");
            }else{
                selfStringBuffer.append("<ftrNewParam></ftrNewParam>");
            }
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("X22")) {
            if(reqMap.get("startDate") !=null && reqMap.get("endDate") !=null) {
                selfStringBuffer.append("<inDto>");
                selfStringBuffer.append("<startDate>"+reqMap.get("startDate")+"</startDate>");
                selfStringBuffer.append("<endDate>"+reqMap.get("endDate")+"</endDate>");
                selfStringBuffer.append("</inDto>");
            }
        } else if (eventCd.equals("X25")) {
            selfStringBuffer.append("<inDto>");
            if (reqMap.get("nextBlMethod") != null)
                selfStringBuffer.append("<nextBlMethod>"+reqMap.get("nextBlMethod")+"</nextBlMethod>");
            if (reqMap.get("nextBlBankCode") != null)
                selfStringBuffer.append("<nextBlBankCode>"+reqMap.get("nextBlBankCode")+"</nextBlBankCode>");
            if (reqMap.get("nextCycleDueDay") != null)
                selfStringBuffer.append("<nextCycleDueDay>"+reqMap.get("nextCycleDueDay")+"</nextCycleDueDay>");
            if (reqMap.get("nextBankAcctNo") != null)
                selfStringBuffer.append("<nextBankAcctNo>"+this.encrypt(reqMap.get("nextBankAcctNo"), encryptYn)+"</nextBankAcctNo>");
            if (reqMap.get("nextCardExpirDate") != null)
                selfStringBuffer.append("<nextCardExpirDate>"+reqMap.get("nextCardExpirDate")+"</nextCardExpirDate>");
            if (reqMap.get("nextCreditCardNo") != null)
                selfStringBuffer.append("<nextCreditCardNo>"+this.encrypt(reqMap.get("nextCreditCardNo"), encryptYn)+"</nextCreditCardNo>");
            if (reqMap.get("nextCardCode") != null)
                selfStringBuffer.append("<nextCardCode>"+reqMap.get("nextCardCode")+"</nextCardCode>");
            if (reqMap.get("blpymTmsIndCd") != null)
                selfStringBuffer.append("<blpymTmsIndCd>"+reqMap.get("blpymTmsIndCd")+"</blpymTmsIndCd>");
            if (reqMap.get("adrZip") != null)
                selfStringBuffer.append("<adrZip>"+reqMap.get("adrZip")+"</adrZip>");
            if (reqMap.get("adrPrimaryLn") != null)
                selfStringBuffer.append("<adrPrimaryLn>"+this.encrypt(reqMap.get("adrPrimaryLn"), encryptYn)+"</adrPrimaryLn>");
            if (reqMap.get("adrSecondaryLn") != null)
                selfStringBuffer.append("<adrSecondaryLn>"+this.encrypt(reqMap.get("adrSecondaryLn"), encryptYn)+"</adrSecondaryLn>");
            if (reqMap.get("agreIndCd") != null)
                selfStringBuffer.append("<agreIndCd>"+reqMap.get("agreIndCd")+"</agreIndCd>");
            if (reqMap.get("myslAthnTypeCd") != null)
                selfStringBuffer.append
                ("<myslAthnTypeCd>"+reqMap.get("myslAthnTypeCd")+"</myslAthnTypeCd>");
            selfStringBuffer.append("</inDto>");
        }else if(eventCd.equals("X26")){
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<termGubun>"+reqMap.get("termGubun")+"</termGubun>");
            selfStringBuffer.append("</inDto>");
        }else if(eventCd.equals("X27")){
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<stopRsnCd>"+reqMap.get("stopRsnCd")+"</stopRsnCd>");
            selfStringBuffer.append("</inDto>");
        }else if (eventCd.equals("X29")) {
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<reasonCode>"+reqMap.get("reasonCode")+"</reasonCode>");
            if(null !=reqMap.get("stopRsnCd")){
                selfStringBuffer.append("<stopRsnCd>"+reqMap.get("stopRsnCd")+"</stopRsnCd>");
            }else{
                selfStringBuffer.append("<stopRsnCd></stopRsnCd>");
            }
            if(null !=reqMap.get("userMemo")){
                selfStringBuffer.append("<userMemo>"+reqMap.get("userMemo")+"</userMemo>");
            }else{
                selfStringBuffer.append("<userMemo></userMemo>");
            }
            selfStringBuffer.append("<cpDateYn>"+reqMap.get("cpDateYn")+"</cpDateYn>");
            if(null !=reqMap.get("cpEndDt")){
                selfStringBuffer.append("<cpEndDt>"+reqMap.get("cpEndDt")+"</cpEndDt>");
            }else{
                selfStringBuffer.append("<cpEndDt></cpEndDt>");
            }
            if(null !=reqMap.get("cpStartDt")){
                selfStringBuffer.append("<cpStartDt>"+reqMap.get("cpStartDt")+"</cpStartDt>");
            }else{
                selfStringBuffer.append("<cpStartDt></cpStartDt>");
            }
            if(null !=reqMap.get("cpPwdInsert")){
                selfStringBuffer.append("<cpPwdInsert>"+reqMap.get("cpPwdInsert")+"</cpPwdInsert>");
            }else{
                selfStringBuffer.append("<cpPwdInsert></cpPwdInsert>");
            }
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("X30")) {
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<pwdType>"+reqMap.get("pwdType")+"</pwdType>");
            if(null !=reqMap.get("strPwdNumInsert")){
                selfStringBuffer.append("<strPwdNumInsert>"+reqMap.get("strPwdNumInsert")+"</strPwdNumInsert>");
            }else{
                selfStringBuffer.append("<strPwdNumInsert></strPwdNumInsert>");
            }
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("X31")) {
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<chkCtn>"+reqMap.get("chkCtn")+"</chkCtn>");
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("X32")) {
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<resvHkCtn>"+this.encrypt(reqMap.get("resvHkCtn"), encryptYn)+"</resvHkCtn>");
            selfStringBuffer.append("<resvHkSCtn>"+reqMap.get("resvHkSCtn")+"</resvHkSCtn>");
            selfStringBuffer.append("<resvHkMarketGubun>"+reqMap.get("resvHkMarketGubun")+"</resvHkMarketGubun>");
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("X34")) {
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<loseType>"+reqMap.get("loseType")+"</loseType>");
            selfStringBuffer.append("<guideYn>"+reqMap.get("guideYn")+"</guideYn>");
            selfStringBuffer.append("<cntcTlphNo>"+this.encrypt(reqMap.get("cntcTlphNo"), encryptYn)+"</cntcTlphNo>");
            selfStringBuffer.append("<loseCont>"+reqMap.get("loseCont")+"</loseCont>");
            selfStringBuffer.append("<loseLocation>"+reqMap.get("loseLocation")+"</loseLocation>");
            selfStringBuffer.append("<strPwdInsert>"+reqMap.get("strPwdInsert")+"</strPwdInsert>");
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("X35")) {
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<strPwdNumInsert>"+reqMap.get("strPwdNumInsert")+"</strPwdNumInsert>");
            selfStringBuffer.append("<pwdType>"+reqMap.get("pwdType")+"</pwdType>");
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("X38")) {
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<soc>"+reqMap.get("soc")+"</soc>");
            if(reqMap.get("prodHstSeq") != null) {
                selfStringBuffer.append("<prodHstSeq>"+reqMap.get("prodHstSeq")+"</prodHstSeq>");
            }
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("X50")) {
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<billAdInfo>"+this.encrypt(reqMap.get("billAdInfo"), encryptYn)+"</billAdInfo>");
            selfStringBuffer.append("<billTypeCd>"+reqMap.get("billTypeCd")+"</billTypeCd>");
            selfStringBuffer.append("<ecRcvAgreYn>"+reqMap.get("ecRcvAgreYn")+"</ecRcvAgreYn>");
            selfStringBuffer.append("<securMailYn>"+reqMap.get("securMailYn")+"</securMailYn>");
            selfStringBuffer.append("<sendGubun>"+reqMap.get("sendGubun")+"</sendGubun>");
            selfStringBuffer.append("<status>"+reqMap.get("status")+"</status>");
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("X55")) {
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<mvnoseq>"+reqMap.get("mvnoseq")+"</mvnoseq>");
            selfStringBuffer.append("<slsCmpnCd>"+reqMap.get("slsCmpnCd")+"</slsCmpnCd>");
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("X60")) {
            selfStringBuffer.append("<moscSdsSvcChgInDTO>");
            if(null !=reqMap.get("engtPerd")){
                selfStringBuffer.append("<engtPerd>"+reqMap.get("engtPerd")+"</engtPerd>");
            }else{
                selfStringBuffer.append("<engtPerd></engtPerd>");
            }
            selfStringBuffer.append("</moscSdsSvcChgInDTO>");
        } else if (eventCd.equals("X69")) {
            selfStringBuffer.append("<inDto>");
            if(reqMap.get("crprCtn") != null) {
                selfStringBuffer.append("<crprCtn>"+this.encrypt(reqMap.get("crprCtn"), encryptYn)+"</crprCtn>");
            }
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("X70")) {
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<opmdSvcNo>"+this.encrypt(reqMap.get("opmdSvcNo"), encryptYn)+"</opmdSvcNo>");
            selfStringBuffer.append("<opmdWorkDivCd>"+reqMap.get("opmdWorkDivCd")+"</opmdWorkDivCd>");
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("X73")) {
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<roamVasProdId>"+reqMap.get("roamVasProdId")+"</roamVasProdId>");
            selfStringBuffer.append("<efctStrtDt>"+reqMap.get("efctStrtDt")+"</efctStrtDt>");
            selfStringBuffer.append("<efctEndDt>"+reqMap.get("efctEndDt")+"</efctEndDt>");
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("X74")) {
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<coupSerialNo>"+reqMap.get("coupSerialNo")+"</coupSerialNo>");
            selfStringBuffer.append("<coupStatCd>"+reqMap.get("coupStatCd")+"</coupStatCd>");
            selfStringBuffer.append("<coupTypeCd>"+reqMap.get("coupTypeCd")+"</coupTypeCd>");
            selfStringBuffer.append("<ctn>"+this.encrypt(reqMap.get("ctn"), encryptYn)+"</ctn>");
            selfStringBuffer.append("<pageNo>"+reqMap.get("pageNo")+"</pageNo>");
            selfStringBuffer.append("<searchTypeCd>"+reqMap.get("searchTypeCd")+"</searchTypeCd>");
            selfStringBuffer.append("<svcTypeCd>"+reqMap.get("svcTypeCd")+"</svcTypeCd>");
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("X75")) {
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<coupSerialNo>"+reqMap.get("coupSerialNo")+"</coupSerialNo>");
            selfStringBuffer.append("<svcTypeCd>"+reqMap.get("svcTypeCd")+"</svcTypeCd>");
            selfStringBuffer.append("<useCanReason>"+reqMap.get("useCanReason")+"</useCanReason>");
            selfStringBuffer.append("<useRsvDt>"+reqMap.get("useRsvDt")+"</useRsvDt>");
            selfStringBuffer.append("<useTypeCd>"+reqMap.get("useTypeCd")+"</useTypeCd>");
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("X76")) {
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<coupSerialNo>"+reqMap.get("coupSerialNo")+"</coupSerialNo>");
            selfStringBuffer.append("<pageNo>"+reqMap.get("pageNo")+"</pageNo>");
            selfStringBuffer.append("<svcTypeCd>"+reqMap.get("svcTypeCd")+"</svcTypeCd>");
            selfStringBuffer.append("<useDateFrom>"+reqMap.get("useDateFrom")+"</useDateFrom>");
            selfStringBuffer.append("<useDateTo>"+reqMap.get("useDateTo")+"</useDateTo>");
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("X80")) {
            selfStringBuffer.append("<moscOtpInfoInDTO>");
            selfStringBuffer.append("<svcNo>"+this.encrypt(reqMap.get("svcNo"), encryptYn)+"</svcNo>");
            selfStringBuffer.append("<chkInd>"+reqMap.get("chkInd")+"</chkInd>");
            selfStringBuffer.append("<dataVal1>"+reqMap.get("dataVal1")+"</dataVal1>");
            selfStringBuffer.append("<dataVal2></dataVal2>");
            selfStringBuffer.append("<dataVal3></dataVal3>");
            selfStringBuffer.append("<dataVal4></dataVal4>");
            selfStringBuffer.append("</moscOtpInfoInDTO>");
        } else if (eventCd.equals("X82")) {
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<payBizCd>"+reqMap.get("payBizCd")+"</payBizCd>");
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("D01")) {
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<addrTypeCd>"+reqMap.get("addrTypeCd")+"</addrTypeCd>");
            selfStringBuffer.append("<bizOrgCd>"+reqMap.get("bizOrgCd")+"</bizOrgCd>");
            selfStringBuffer.append("<targetAddr1>"+this.encrypt(reqMap.get("targetAddr1"), encryptYn)+"</targetAddr1>");
            selfStringBuffer.append("<targetAddr2>"+this.encrypt(reqMap.get("targetAddr2"), encryptYn)+"</targetAddr2>");
            selfStringBuffer.append("<zipNo>"+reqMap.get("zipNo")+"</zipNo>");
            selfStringBuffer.append("<targetAddrLat>"+this.encrypt(reqMap.get("targetAddrLat"), encryptYn)+"</targetAddrLat>");
            selfStringBuffer.append("<targetAddrLng>"+this.encrypt(reqMap.get("targetAddrLng"), encryptYn)+"</targetAddrLng>");
            selfStringBuffer.append("<nfcYn>"+reqMap.get("nfcYn")+"</nfcYn>");
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("D02")) {
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<orderRcvTlphNo>"+this.encrypt(reqMap.get("orderRcvTlphNo"), encryptYn)+"</orderRcvTlphNo>");
            selfStringBuffer.append("<zipNo>"+reqMap.get("zipNo")+"</zipNo>");
            selfStringBuffer.append("<addrTypeCd>"+reqMap.get("addrTypeCd")+"</addrTypeCd>");
            selfStringBuffer.append("<targetAddr1>"+this.encrypt(reqMap.get("targetAddr1"), encryptYn)+"</targetAddr1>");
            selfStringBuffer.append("<targetAddr2>"+this.encrypt(reqMap.get("targetAddr2"), encryptYn)+"</targetAddr2>");
            selfStringBuffer.append("<custInfoAgreeYn>"+reqMap.get("custInfoAgreeYn")+"</custInfoAgreeYn>");
            selfStringBuffer.append("<rsvOrderYn>"+reqMap.get("rsvOrderYn")+"</rsvOrderYn>");
            selfStringBuffer.append("<rsvOrderDt>"+reqMap.get("rsvOrderDt")+"</rsvOrderDt>");
            selfStringBuffer.append("<orderReqMsg>"+reqMap.get("orderReqMsg")+"</orderReqMsg>");
            selfStringBuffer.append("<acceptTime>"+reqMap.get("acceptTime")+"</acceptTime>");
            selfStringBuffer.append("<usimAmt>"+reqMap.get("usimAmt")+"</usimAmt>");
            selfStringBuffer.append("<bizOrgCd>"+reqMap.get("bizOrgCd")+"</bizOrgCd>");
            selfStringBuffer.append("<targetAddrLat>"+this.encrypt(reqMap.get("targetAddrLat"), encryptYn)+"</targetAddrLat>");
            selfStringBuffer.append("<targetAddrLng>"+this.encrypt(reqMap.get("targetAddrLng"), encryptYn)+"</targetAddrLng>");
            selfStringBuffer.append("<unTact>N</unTact>");
            selfStringBuffer.append("<nfcYn>"+reqMap.get("nfcYn")+"</nfcYn>");
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("D03")) {
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<jobGubun>"+reqMap.get("jobGubun")+"</jobGubun>");
            selfStringBuffer.append("<ktOrderId>"+reqMap.get("ktOrderId")+"</ktOrderId>");
            selfStringBuffer.append("<orderRcvTlphNo>"+reqMap.get("orderRcvTlphNo")+"</orderRcvTlphNo>");
            selfStringBuffer.append("<zipNo>"+reqMap.get("zipNo")+"</zipNo>");
            selfStringBuffer.append("<addrTypeCd>"+reqMap.get("addrTypeCd")+"</addrTypeCd>");
            selfStringBuffer.append("<targetAddr1>"+reqMap.get("targetAddr1")+"</targetAddr1>");
            selfStringBuffer.append("<targetAddr2>"+reqMap.get("targetAddr2")+"</targetAddr2>");
            selfStringBuffer.append("<custInfoAgreeYn>"+reqMap.get("custInfoAgreeYn")+"</custInfoAgreeYn>");
            selfStringBuffer.append("<rsvOrderYn>"+reqMap.get("rsvOrderYn")+"</rsvOrderYn>");
            selfStringBuffer.append("<rsvOrderDt>"+reqMap.get("rsvOrderDt")+"</rsvOrderDt>");
            selfStringBuffer.append("<orderReqMsg>"+reqMap.get("orderReqMsg")+"</orderReqMsg>");
            selfStringBuffer.append("<acceptTime>"+reqMap.get("acceptTime")+"</acceptTime>");
            selfStringBuffer.append("<usimAmt>"+reqMap.get("usimAmt")+"</usimAmt>");
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("D04")) {
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<ktOrderId>"+reqMap.get("ktOrderId")+"</ktOrderId>");
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("X85")) {
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<iccid>"+this.encrypt(reqMap.get("iccid"), encryptYn)+"</iccid>");
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("X86")) {
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<retvGubunCd>"+reqMap.get("retvGubunCd")+"</retvGubunCd>");
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("X88")) {
            //요금제예약변경
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<ftrNewParam>"+reqMap.get("ftrNewParam")+"</ftrNewParam>");
            selfStringBuffer.append("<soc>"+reqMap.get("soc")+"</soc>");
            selfStringBuffer.append("</inDto>");

        } else if (eventCd.equals("X68")) {
            //미납요금 즉시수납 처리 (X68)
            selfStringBuffer.append("<inDto>");
            if(reqMap.get("bankAcctNo") != null) {
                selfStringBuffer.append("<bankAcctNo>"+this.encrypt(reqMap.get("bankAcctNo"), encryptYn)+"</bankAcctNo>");
            }

            if(reqMap.get("blBankCode") != null) {
                selfStringBuffer.append("<blBankCode>"+reqMap.get("blBankCode")+"</blBankCode>");
            }

            if(reqMap.get("cardExpirDate") != null) {
                selfStringBuffer.append("<cardExpirDate>"+reqMap.get("cardExpirDate")+"</cardExpirDate>");
            }

            if(reqMap.get("cardInstMnthCnt") != null) {
                selfStringBuffer.append("<cardInstMnthCnt>"+reqMap.get("cardInstMnthCnt")+"</cardInstMnthCnt>");
            }

            if(reqMap.get("cardNo") != null) {
                selfStringBuffer.append("<cardNo>"+this.encrypt(reqMap.get("cardNo"), encryptYn)+"</cardNo>");
            }

            if(reqMap.get("cardPwd") != null) {
                selfStringBuffer.append("<cardPwd>"+reqMap.get("cardPwd")+"</cardPwd>");
            }
            if(reqMap.get("agrDivCd") != null) {
                selfStringBuffer.append("<agrDivCd>"+reqMap.get("agrDivCd")+"</agrDivCd>");
            }
            if(reqMap.get("rmnyChId") != null) {
                selfStringBuffer.append("<rmnyChId>"+reqMap.get("rmnyChId")+"</rmnyChId>");
            }

            selfStringBuffer.append("<blMethod>"+reqMap.get("blMethod")+"</blMethod>");
            selfStringBuffer.append("<payMentMoney>"+reqMap.get("payMentMoney")+"</payMentMoney>");
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("X91")) { // 신용카드 인증조회(X91)
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<brthDate>"+ this.encrypt(reqMap.get("brthDate"), encryptYn)+"</brthDate>");
            selfStringBuffer.append("<crdtCardNo>"+ this.encrypt(reqMap.get("crdtCardNo"), encryptYn)+"</crdtCardNo>");
            selfStringBuffer.append("<crdtCardTermDay>"+reqMap.get("crdtCardTermDay")+"</crdtCardTermDay>");
            selfStringBuffer.append("<custNm>"+ this.encrypt(reqMap.get("custNm"), encryptYn)+"</custNm>");
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("X93")) {
            //미납요금 즉시수납 처리 (X93)
            selfStringBuffer.append("<inDto>");
            if(reqMap.get("bankAcctNo") != null) {
                selfStringBuffer.append("<bankAcctNo>"+this.encrypt(reqMap.get("bankAcctNo"), encryptYn)+"</bankAcctNo>");
            }

            if(reqMap.get("blBankCode") != null) {
                selfStringBuffer.append("<blBankCode>"+reqMap.get("blBankCode")+"</blBankCode>");
            }

            if(reqMap.get("cardExpirDate") != null) {
                selfStringBuffer.append("<cardExpirDate>"+reqMap.get("cardExpirDate")+"</cardExpirDate>");
            }

            if(reqMap.get("cardInstMnthCnt") != null) {
                selfStringBuffer.append("<cardInstMnthCnt>"+reqMap.get("cardInstMnthCnt")+"</cardInstMnthCnt>");
            }

            if(reqMap.get("cardNo") != null) {
                selfStringBuffer.append("<cardNo>"+this.encrypt(reqMap.get("cardNo"), encryptYn)+"</cardNo>");
            }

            if(reqMap.get("cardPwd") != null) {
                selfStringBuffer.append("<cardPwd>"+reqMap.get("cardPwd")+"</cardPwd>");
            }
            if(reqMap.get("agrDivCd") != null) {
                selfStringBuffer.append("<agrDivCd>"+reqMap.get("agrDivCd")+"</agrDivCd>");
            }
            if(reqMap.get("rmnyChId") != null) {
                selfStringBuffer.append("<rmnyChId>"+reqMap.get("rmnyChId")+"</rmnyChId>");
            }

            if(reqMap.get("myslfAthnTypeItgCd") != null) {
                selfStringBuffer.append("<myslfAthnTypeItgCd>"+reqMap.get("myslfAthnTypeItgCd")+"</myslfAthnTypeItgCd>");
            }


            selfStringBuffer.append("<blMethod>"+reqMap.get("blMethod")+"</blMethod>");
            selfStringBuffer.append("<payMentMoney>"+reqMap.get("payMentMoney")+"</payMentMoney>");
            selfStringBuffer.append("</inDto>");

        } else if (eventCd.equals("X53")) { // 청구서재발송처리(X53)

            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<billAdInfo>"+this.encrypt(reqMap.get("billAdInfo"), encryptYn)+"</billAdInfo>");
            selfStringBuffer.append("<billTypeCd>"+reqMap.get("billTypeCd")+"</billTypeCd>");
            selfStringBuffer.append("<causeCode>"+reqMap.get("causeCode")+"</causeCode>");
            selfStringBuffer.append("<month>"+reqMap.get("month")+"</month>");
            selfStringBuffer.append("<securMailYn>"+reqMap.get("securMailYn")+"</securMailYn>");
            selfStringBuffer.append("<year>"+reqMap.get("year")+"</year>");
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("Y12")) {
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<indCd>"+reqMap.get("indCd")+"</indCd>");
            selfStringBuffer.append("<intmMdlId>"+reqMap.get("intmMdlId")+"</intmMdlId>");
            if(reqMap.get("intmSpecTypeCd") != null) {
                selfStringBuffer.append("<intmSpecTypeCd>"+reqMap.get("intmSpecTypeCd")+"</intmSpecTypeCd>");
            } else {
            //	selfStringBuffer.append("<intmSpecTypeCd></intmSpecTypeCd>");
            }

            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("Y13")) {
            selfStringBuffer.append("<inMsg>");
            selfStringBuffer.append("<indCd>"+reqMap.get("indCd")+"</indCd>");
            if(reqMap.get("intmModelId") != null) {
                selfStringBuffer.append("<intmModelId>"+reqMap.get("intmModelId")+"</intmModelId>");
            }

            if(reqMap.get("intmSrlNo") != null) {
                selfStringBuffer.append("<intmSrlNo>"+reqMap.get("intmSrlNo")+"</intmSrlNo>");
            }

            if(reqMap.get("intmUniqIdntNo") != null) {
                selfStringBuffer.append("<intmUniqIdntNo>"+this.encrypt(reqMap.get("intmUniqIdntNo"), encryptYn)+"</intmUniqIdntNo>");
            }

            selfStringBuffer.append("</inMsg>");

        } else if (eventCd.equals("Y14")) {
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<wrkjobDivCd>"+reqMap.get("wrkjobDivCd")+"</wrkjobDivCd>");
            selfStringBuffer.append("<imei>"+this.encrypt(reqMap.get("imei"), encryptYn)+"</imei>");
            if(reqMap.get("imei2") != null) {
                selfStringBuffer.append("<imei2>"+this.encrypt(reqMap.get("imei2"), encryptYn)+"</imei2>");
            }

            selfStringBuffer.append("</inDto>");

        } else if (eventCd.equals("Y15")) {
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<wrkjobDivCd>"+reqMap.get("wrkjobDivCd")+"</wrkjobDivCd>");
            selfStringBuffer.append("<intmModelNm>"+reqMap.get("intmModelNm")+"</intmModelNm>");
            selfStringBuffer.append("<intmModelId>"+reqMap.get("intmModelId")+"</intmModelId>");
            selfStringBuffer.append("<imei>"+this.encrypt(reqMap.get("imei"), encryptYn)+"</imei>");

            if(reqMap.get("wifiMacAdr") != null && reqMap.get("wifiMacAdr") != "" ) {
                selfStringBuffer.append("<wifiMacAdr>"+reqMap.get("wifiMacAdr")+"</wifiMacAdr>");
            }
            if(reqMap.get("intmEtcPurpDivCd") != null && reqMap.get("intmEtcPurpDivCd") != "" ) {
                selfStringBuffer.append("<intmEtcPurpDivCd>"+reqMap.get("intmEtcPurpDivCd")+"</intmEtcPurpDivCd>");
            }
            if(reqMap.get("euiccId") != null && reqMap.get("euiccId") != "" ) {
                selfStringBuffer.append("<euiccId>"+this.encrypt(reqMap.get("euiccId"), encryptYn)+"</euiccId>");
            }
            if(reqMap.get("trtDivCd") != null && reqMap.get("trtDivCd") != "" ) {
                selfStringBuffer.append("<trtDivCd>"+reqMap.get("trtDivCd")+"</trtDivCd>");
            }
            if(reqMap.get("imei2") != null && reqMap.get("imei2") != "" ) {
                selfStringBuffer.append("<imei2>"+this.encrypt(reqMap.get("imei2"), encryptYn)+"</imei2>");
            }
            if(reqMap.get("cmpnCdIfVal") != null && reqMap.get("cmpnCdIfVal") != "" ) {
                selfStringBuffer.append("<cmpnCdIfVal>"+reqMap.get("cmpnCdIfVal")+"</cmpnCdIfVal>");
            }
            if(reqMap.get("birthday") != null && reqMap.get("birthday") != "" ) {
                selfStringBuffer.append("<birthday>"+this.encrypt(reqMap.get("birthday"), encryptYn)+"</birthday>");
            }
            if(reqMap.get("sexDiv") != null && reqMap.get("sexDiv") != "" ) {
                selfStringBuffer.append("<sexDiv>"+reqMap.get("sexDiv")+"</sexDiv>");
            }

            if(reqMap.get("ctn") != null && reqMap.get("ctn") != "" ) {
                selfStringBuffer.append("<ctn>"+this.encrypt(reqMap.get("ctn"), encryptYn)+"</ctn>");
            }

            selfStringBuffer.append("</inDto>");

        } else if (eventCd.equals("X95")) {
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<intmMdlId>"+reqMap.get("intmMdlId")+"</intmMdlId>");
            selfStringBuffer.append("<intmMdlNm>"+reqMap.get("intmMdlNm")+"</intmMdlNm>");
            selfStringBuffer.append("<inqrIndCd>"+reqMap.get("inqrIndCd")+"</inqrIndCd>");
            selfStringBuffer.append("<netDivCd>"+reqMap.get("netDivCd")+"</netDivCd>");
            selfStringBuffer.append("<mkngVndrId>"+reqMap.get("mkngVndrId")+"</mkngVndrId>");
            if(reqMap.get("intmMdlDivCd") != null) {
                selfStringBuffer.append("<intmMdlDivCd>"+reqMap.get("intmMdlDivCd")+"</intmMdlDivCd>");
            }
            selfStringBuffer.append("<inqrCascnt>"+reqMap.get("inqrCascnt")+"</inqrCascnt>");
            selfStringBuffer.append("<inqrBase>"+reqMap.get("inqrBase")+"</inqrBase>");
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("Y29")) {
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<cdKey>"+reqMap.get("cdKey")+"</cdKey>");
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("Y39")){
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<osstOrdNo>"+reqMap.get("osstOrdNo")+"</osstOrdNo>");
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("Y24") || eventCd.equals("Y25")){
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<actCode>"+reqMap.get("actCode")+"</actCode>");
            RestTemplate restTemplate = new RestTemplate();
            String apiInterfaceServer = propertiesService.getString("api.interface.server");
            Map<String, String>[] result = restTemplate.postForObject(apiInterfaceServer + "/msp/mspDisAddList", reqMap.get("prmtId"), Map[].class);
            List<Map<String, String>> prdcList = Arrays.asList(result);
            for (int i=0; i<prdcList.size(); i++) {
                Map<String, String> prdc = prdcList.get(i);
                selfStringBuffer.append("<prdcList>");
                selfStringBuffer.append("<prdcCd>"+prdc.get("SOC")+"</prdcCd>");
                selfStringBuffer.append("<prdcSbscTrtmCd>"+reqMap.get("prdcSbscTrtmCd")+"</prdcSbscTrtmCd>");
                selfStringBuffer.append("<prdcTypeCd>"+prdc.get("SERVICE_TYPE")+"</prdcTypeCd>");
                if(reqMap.get("prdcSeqNo") != null && reqMap.get("prdcSeqNo") != "" ) {
                    selfStringBuffer.append("<prdcSeqNo>" + reqMap.get("prdcSeqNo") + "</prdcSeqNo>");
                }
                if(reqMap.get("ftrNewParam") != null && reqMap.get("ftrNewParam") != "" ) {
                    selfStringBuffer.append("<ftrNewParam>" + reqMap.get("ftrNewParam") + "</ftrNewParam>");
                }
                selfStringBuffer.append("</prdcList>");
            }
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("Y42")) {
            //제휴상품 리마인드 SMS 수신 상태 조회 및 변경
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<prodGubun>"+reqMap.get("prodGubun")+"</prodGubun>");
            if(reqMap.get("smsRcvBlckYn") != null) {
                selfStringBuffer.append("<smsRcvBlckYn>"+reqMap.get("smsRcvBlckYn")+"</smsRcvBlckYn>");
            }
            selfStringBuffer.append("<wrkjobCd>"+reqMap.get("wrkjobCd")+"</wrkjobCd>");
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("Y44")) {
            selfStringBuffer.append("<inDto>");
            selfStringBuffer.append("<mstSvcContId>"+reqMap.get("mstSvcContId")+"</mstSvcContId>");
            selfStringBuffer.append("</inDto>");
        } else if (eventCd.equals("Y41")) {
            //고객의 개인정보 활용동의 변경 입력한 항목에 대해서만 변경 처리
            List<String> inDtoFields = Arrays.asList(
                    "othcmpInfoAdvrRcvAgreYn", "othcmpInfoAdvrCnsgAgreYn", "grpAgntBindSvcSbscAgreYn"
                    , "cardInsrPrdcAgreYn", "fnncDealAgreeYn", "cnsgInfoAdvrRcvAgreYn", "indvLoInfoPrvAgreeYn"
            );

            selfStringBuffer.append("<inDto>");
            for (String field : inDtoFields) {
                if (reqMap.get(field) != null) {
                    selfStringBuffer.append(this.createXmlElementByMap(reqMap, field));
                }
            }
            selfStringBuffer.append("</inDto>");
        }

        return selfStringBuffer;
    }

    /**
     * N-Step 전송 XML 초기 데이터 생성
     * @param type
     * @return
     */
    public StringBuffer initXmlMessageCreate(String type) {

        StringBuffer xmlStringBuffer = new StringBuffer();

        if(type.equals("start")) {
            xmlStringBuffer.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:sel=\"http://selfcare.so.itl.mvno.kt.com/\">");
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

//        headerStringBuffer.append("<appEntrPrsnId>"+"KTF"+"</appEntrPrsnId>");
//        headerStringBuffer.append("<appAgncCd>"+"AA11070"+"</appAgncCd>");
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
     * @param reqMap
     * @return
     */
    public String xmlMessageCreate(HashMap<String, String> reqMap, String encryptYn) {

        // -------------------------------------------------------------------
        // Initialize.
        // -------------------------------------------------------------------
        String eventCd = reqMap.get("appEventCd");

        StringBuffer requestXml = new StringBuffer();
        StringBuffer header = new StringBuffer();
        StringBuffer inDto = new StringBuffer();

//        if (isJuiceInterface(eventCd)) {
//            requestXml.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:juic=\"http://juice.so.itl.mvno.kt.com/\">");
//            requestXml.append("<soapenv:Header/>");
//            requestXml.append("<soapenv:Body>");
//            header = initHeaderCreate(eventCd);
//            inDto = getInDto(reqMap);
//
//            requestXml.append("<juic:"+getEventCodeInfo(eventCd, "info")+">");
//            requestXml.append("<"+getEventCodeInfo(eventCd, "vo")+">");
//            requestXml.append(header.toString());
//            requestXml.append(inDto.toString());
//
//            requestXml.append("</"+getEventCodeInfo(eventCd, "vo")+">");
//            requestXml.append("</juic:"+getEventCodeInfo(eventCd, "info")+">");
//        } else {
//            requestXml = initXmlMessageCreate("start");
//            header = initHeaderCreate(eventCd);
//            inDto = getInDto(reqMap);
//
//            requestXml.append("<sel:"+getEventCodeInfo(eventCd, "info")+">");
//            requestXml.append("<"+getEventCodeInfo(eventCd, "vo")+">");
//            requestXml.append(header.toString());
//            requestXml.append(inDto.toString());
//
//
//
//            // sefCareIn
//            requestXml.append("<selfCareIn>");
//            requestXml.append("<ctn>"+reqMap.get("ctn")+"</ctn>");
//            requestXml.append("<custId>"+reqMap.get("custId")+"</custId>");
//            requestXml.append("<ncn>"+reqMap.get("ncn")+"</ncn>");
//            requestXml.append("<clntIp>"+propertiesService.getString("clntIp")+"</clntIp>");
//            requestXml.append("<clntUsrId>"+propertiesService.getString("clntUsrId")+"</clntUsrId>");
//            requestXml.append("</selfCareIn>");
//
//
//            requestXml.append("</"+getEventCodeInfo(eventCd, "vo")+">");
//            requestXml.append("</sel:"+getEventCodeInfo(eventCd, "info")+">");
//
//        }

        if ("X55".equals(eventCd)) {
            requestXml.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:juic=\"http://juice.so.itl.mvno.kt.com/\">");
            requestXml.append("<soapenv:Header/>");
            requestXml.append("<soapenv:Body>");
            header = initHeaderCreate(eventCd);
            inDto = getInDto(reqMap, encryptYn);

            requestXml.append("<juic:"+getEventCodeInfo(eventCd, "info")+">");
            requestXml.append("<"+getEventCodeInfo(eventCd, "vo")+">");
            requestXml.append(header.toString());
            requestXml.append(inDto.toString());

            requestXml.append("</"+getEventCodeInfo(eventCd, "vo")+">");
            requestXml.append("</juic:"+getEventCodeInfo(eventCd, "info")+">");
        } else if("D01".equals(eventCd) || "D02".equals(eventCd) || "D03".equals(eventCd) || "D04".equals(eventCd)){

            requestXml.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:del=\"http://delivery.so.itl.mvno.kt.com/\">");
            requestXml.append("<soapenv:Header/>");
            requestXml.append("<soapenv:Body>");
            header = initHeaderCreate(eventCd);
            inDto = getInDto(reqMap, encryptYn);

            requestXml.append("<del:"+getEventCodeInfo(eventCd, "info")+">");
            requestXml.append("<"+getEventCodeInfo(eventCd, "vo")+">");
            requestXml.append(header.toString());
            requestXml.append(inDto.toString());

            requestXml.append("</"+getEventCodeInfo(eventCd, "vo")+">");
            requestXml.append("</del:"+getEventCodeInfo(eventCd, "info")+">");

        } else if ("X85".equals(eventCd) || "X95".equals(eventCd)){

            requestXml = initXmlMessageCreate("start");
            header = initHeaderCreate(eventCd);
            inDto = getInDto(reqMap, encryptYn);

            requestXml.append("<sel:"+getEventCodeInfo(eventCd, "info")+">");
            requestXml.append("<"+getEventCodeInfo(eventCd, "vo")+">");
            requestXml.append(header.toString());
            requestXml.append(inDto.toString());

            requestXml.append("</"+getEventCodeInfo(eventCd, "vo")+">");
            requestXml.append("</sel:"+getEventCodeInfo(eventCd, "info")+">");

        }  else if ("X79".equals(eventCd) || "X78".equals(eventCd)){

            requestXml = initXmlMessageCreate("start");
            header = initHeaderCreate(eventCd);
            inDto = getMoscCombChgInDTO(reqMap, encryptYn);

            requestXml.append("<sel:"+getEventCodeInfo(eventCd, "info")+">");
            requestXml.append("<"+getEventCodeInfo(eventCd, "vo")+">");
            requestXml.append(header.toString());
            requestXml.append(inDto.toString());

            // sefCareIn
            requestXml.append("<selfCareIn>");
            requestXml.append("<ctn>"+this.encrypt(reqMap.get("ctn"), encryptYn)+"</ctn>");
            requestXml.append("<custId>"+reqMap.get("custId")+"</custId>");
            requestXml.append("<ncn>"+reqMap.get("ncn")+"</ncn>");
            requestXml.append("<clntIp>"+propertiesService.getString("clntIp")+"</clntIp>");
            requestXml.append("<clntUsrId>"+propertiesService.getString("clntUsrId")+"</clntUsrId>");
            requestXml.append("</selfCareIn>");

            requestXml.append("</"+getEventCodeInfo(eventCd, "vo")+">");
            requestXml.append("</sel:"+getEventCodeInfo(eventCd, "info")+">");

        } else if ("X77".equals(eventCd)){

            requestXml = initXmlMessageCreate("start");
            header = initHeaderCreate(eventCd);
            inDto = getMoscCombInfoInDTO(reqMap, encryptYn);

            requestXml.append("<sel:"+getEventCodeInfo(eventCd, "info")+">");
            requestXml.append("<"+getEventCodeInfo(eventCd, "vo")+">");
            requestXml.append(header.toString());
            requestXml.append(inDto.toString());

            // sefCareIn
            requestXml.append("<selfCareIn>");
            requestXml.append("<ctn>"+this.encrypt(reqMap.get("ctn"), encryptYn)+"</ctn>");
            requestXml.append("<custId>"+reqMap.get("custId")+"</custId>");
            requestXml.append("<ncn>"+reqMap.get("ncn")+"</ncn>");
            requestXml.append("<clntIp>"+propertiesService.getString("clntIp")+"</clntIp>");
            requestXml.append("<clntUsrId>"+propertiesService.getString("clntUsrId")+"</clntUsrId>");
            requestXml.append("</selfCareIn>");

            requestXml.append("</"+getEventCodeInfo(eventCd, "vo")+">");
            requestXml.append("</sel:"+getEventCodeInfo(eventCd, "info")+">");
        } else if ("|Y12|Y13|Y14|Y15|Y29|Y39|X91".indexOf(eventCd) > -1) {
            requestXml = initXmlMessageCreate("start");
            header = initHeaderCreate(eventCd);
            inDto = getInDto(reqMap, encryptYn);

            requestXml.append("<sel:"+getEventCodeInfo(eventCd, "info")+">");
            requestXml.append("<"+getEventCodeInfo(eventCd, "vo")+">");
            requestXml.append(header.toString());
            requestXml.append(inDto.toString());

            requestXml.append("</"+getEventCodeInfo(eventCd, "vo")+">");
            requestXml.append("</sel:"+getEventCodeInfo(eventCd, "info")+">");
        } else {
            requestXml = initXmlMessageCreate("start");
            header = initHeaderCreate(eventCd);
            inDto = getInDto(reqMap, encryptYn);

            requestXml.append("<sel:"+getEventCodeInfo(eventCd, "info")+">");
            requestXml.append("<"+getEventCodeInfo(eventCd, "vo")+">");
            requestXml.append(header.toString());
            requestXml.append(inDto.toString());



            // sefCareIn
            requestXml.append("<selfCareIn>");
            requestXml.append("<ctn>"+this.encrypt(reqMap.get("ctn"), encryptYn)+"</ctn>");
            requestXml.append("<custId>"+reqMap.get("custId")+"</custId>");
            requestXml.append("<ncn>"+reqMap.get("ncn")+"</ncn>");
            requestXml.append("<clntIp>"+propertiesService.getString("clntIp")+"</clntIp>");
            requestXml.append("<clntUsrId>"+propertiesService.getString("clntUsrId")+"</clntUsrId>");
            requestXml.append("</selfCareIn>");


            requestXml.append("</"+getEventCodeInfo(eventCd, "vo")+">");
            requestXml.append("</sel:"+getEventCodeInfo(eventCd, "info")+">");

        }

        StringBuffer endXml = initXmlMessageCreate("end");
        requestXml.append(endXml.toString());

        return requestXml.toString();
    }

    private String createXmlElementByMap(HashMap<String, String> reqMap, String field) {
        return this.createXmlElementByMap(reqMap, field, field);
    }

    private String createXmlElementByMap(HashMap<String, String> reqMap, String field, String mapKey) {
        StringBuffer sb = new StringBuffer();
        sb.append("<").append(field).append(">");
        sb.append(reqMap.get(mapKey));
        sb.append("</").append(field).append(">");

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

    public boolean isJuiceInterface(String eventCd) {
        boolean result = false;

        if (eventCd.equals("X55")) {
            result = true;
        }
        return result;
    }


    public StringBuffer getMoscCombChgInDTO(HashMap<String, String> reqMap, String encryptYn) {
        StringBuffer selfStringBuffer = new StringBuffer();
        String eventCd = reqMap.get("appEventCd");

        if (eventCd.equals("X79")) {
            // mvno 결합 저장

            selfStringBuffer.append("<moscCombChgInDTO>");
            selfStringBuffer.append("<jobGubun>"+reqMap.get("jobGubun")+"</jobGubun>");
            selfStringBuffer.append("<svcNoCd>"+reqMap.get("svcNoCd")+"</svcNoCd>");
            selfStringBuffer.append("<svcNoTypeCd>"+reqMap.get("svcNoTypeCd")+"</svcNoTypeCd>");
            selfStringBuffer.append("<cmbStndSvcNo>"+this.encrypt(reqMap.get("cmbStndSvcNo"), encryptYn)+"</cmbStndSvcNo>");
            selfStringBuffer.append("<custNm>"+this.encrypt(reqMap.get("custNm"), encryptYn)+"</custNm>");
            selfStringBuffer.append("<svcIdfyTypeCd>"+reqMap.get("svcIdfyTypeCd")+"</svcIdfyTypeCd>");
            selfStringBuffer.append("<svcIdfyNo>"+this.encrypt(reqMap.get("svcIdfyNo"), encryptYn)+"</svcIdfyNo>");
            selfStringBuffer.append("<sexCd>"+reqMap.get("sexCd")+"</sexCd>");
            selfStringBuffer.append("<aplyMethCd>"+reqMap.get("aplyMethCd")+"</aplyMethCd>");
            selfStringBuffer.append("<aplyRelatnCd>"+reqMap.get("aplyRelatnCd")+"</aplyRelatnCd>");
            selfStringBuffer.append("<aplyNm>"+this.encrypt(reqMap.get("aplyNm"), encryptYn)+"</aplyNm>");
            selfStringBuffer.append("<homeCombTerm>"+reqMap.get("homeCombTerm")+"</homeCombTerm>");
            selfStringBuffer.append("</moscCombChgInDTO>");

        } else if (eventCd.equals("X78")) {
            // mvno 결합 사전 체크

            selfStringBuffer.append("<moscCombChgInDTO>");
            selfStringBuffer.append("<jobGubun>"+reqMap.get("jobGubun")+"</jobGubun>");
            selfStringBuffer.append("<svcNoCd>"+reqMap.get("svcNoCd")+"</svcNoCd>");
            selfStringBuffer.append("<svcNoTypeCd>"+reqMap.get("svcNoTypeCd")+"</svcNoTypeCd>");
            selfStringBuffer.append("<cmbStndSvcNo>"+this.encrypt(reqMap.get("cmbStndSvcNo"), encryptYn)+"</cmbStndSvcNo>");
            selfStringBuffer.append("<custNm>"+this.encrypt(reqMap.get("custNm"), encryptYn)+"</custNm>");
            selfStringBuffer.append("<svcIdfyTypeCd>"+reqMap.get("svcIdfyTypeCd")+"</svcIdfyTypeCd>");
            selfStringBuffer.append("<svcIdfyNo>"+this.encrypt(reqMap.get("svcIdfyNo"), encryptYn)+"</svcIdfyNo>");
            selfStringBuffer.append("<sexCd>"+reqMap.get("sexCd")+"</sexCd>");
            selfStringBuffer.append("<aplyMethCd>"+reqMap.get("aplyMethCd")+"</aplyMethCd>");
            selfStringBuffer.append("<aplyRelatnCd>"+reqMap.get("aplyRelatnCd")+"</aplyRelatnCd>");
            selfStringBuffer.append("<aplyNm>"+this.encrypt(reqMap.get("aplyNm"), encryptYn)+"</aplyNm>");
            selfStringBuffer.append("<homeCombTerm>"+reqMap.get("homeCombTerm")+"</homeCombTerm>");
            selfStringBuffer.append("</moscCombChgInDTO>");

        }

        return selfStringBuffer;
    }



    public StringBuffer getMoscCombInfoInDTO(HashMap<String, String> reqMap, String encryptYn) {
        StringBuffer selfStringBuffer = new StringBuffer();
        String eventCd = reqMap.get("appEventCd");

        if (eventCd.equals("X77")) {
            // MVNO 결합 상태 조회

            selfStringBuffer.append("<moscCombInfoInDTO>");
            selfStringBuffer.append("<combSvcNoCd>"+reqMap.get("combSvcNoCd")+"</combSvcNoCd>");
            selfStringBuffer.append("<combSrchId>"+this.encrypt(reqMap.get("combSrchId"), encryptYn)+"</combSrchId>");
            selfStringBuffer.append("<svcIdfyNo>"+this.encrypt(reqMap.get("svcIdfyNo"), encryptYn)+"</svcIdfyNo>");
            selfStringBuffer.append("<sexCd>"+reqMap.get("sexCd")+"</sexCd>");
            selfStringBuffer.append("<cmbStndSvcNo>"+this.encrypt(reqMap.get("cmbStndSvcNo"), encryptYn)+"</cmbStndSvcNo>");

            if(reqMap.get("sameCustKtRetvYn") != null && !"".equals(reqMap.get("sameCustKtRetvYn"))) {
                selfStringBuffer.append("<sameCustKtRetvYn>"+reqMap.get("sameCustKtRetvYn")+"</sameCustKtRetvYn>");
            }

            selfStringBuffer.append("</moscCombInfoInDTO>");

        }

        return selfStringBuffer;
    }


    /**
     * xml 파싱
     * @param responseXml
     * @param appEventCd
     * @return
     * @throws JDOMException
     * @throws IOException
     */
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
        if ("X01".equals(appEventCd)) {
            Element outDto = XmlParse.getChildElement(rtn, "outDto");

            this.setDecryptText(outDto, "email");
            this.setDecryptText(outDto, "addr");
            this.setDecryptText(outDto, "homeTel");
        } else if ("X03".equals(appEventCd)) {
            Element outDto = XmlParse.getChildElement(rtn, "outDto");

            Element outApplyChgDto = XmlParse.getChildElement(outDto, "outApplyChgDto");
            this.setDecryptText(outApplyChgDto, "email");

            Element outApplyDto = XmlParse.getChildElement(outDto, "outApplyDto");
            this.setDecryptText(outApplyDto, "email");

            Element outChgDto = XmlParse.getChildElement(outDto, "outChgDto");
            this.setDecryptText(outChgDto, "email");
            this.setDecryptText(outChgDto, "orieMail");

            Element outTermDto = XmlParse.getChildElement(outDto, "outTermDto");
            this.setDecryptText(outTermDto, "email");

            Element outOrgDto = XmlParse.getChildElement(outDto, "outOrgDto");
            this.setDecryptText(outOrgDto, "orgEmail");
        } else if ("X10".equals(appEventCd)) {
            Element outDto = XmlParse.getChildElement(rtn, "outDto");

            this.setDecryptText(outDto, "pAddr");
            this.setDecryptText(outDto, "sAddr");
        } else if ("X15".equals(appEventCd)) {
            Element outDto = XmlParse.getChildElement(rtn, "outDto");

            Element payMentSumDTO = XmlParse.getChildElement(outDto, "payMentSumDTO");
            this.setDecryptText(payMentSumDTO, "tel");
            this.setDecryptText(payMentSumDTO, "linkName");
        } else if ("X16".equals(appEventCd)) {
            Element outDto = XmlParse.getChildElement(rtn, "outDto");

            Element hndFarDto = XmlParse.getChildElement(outDto, "hndFarDto");
            this.setDecryptText(hndFarDto, "subscriberNo");
        } else if ("X23".equals(appEventCd)) {
            Element outDto = XmlParse.getChildElement(rtn, "outDto");

            this.setDecryptText(outDto, "blBankAcctNo");
            this.setDecryptText(outDto, "bankAcctHolderName");
            this.setDecryptText(outDto, "blAddr");
            this.setDecryptText(outDto, "prevCardNo");
        } else if ("X31".equals(appEventCd)) {
            List<Element> itemList = XmlParse.getChildElementList(rtn, "outDto");
            for (Element item : itemList) {
                this.setDecryptText(item, "ctn");
            }
        } else if ("X49".equals(appEventCd)) {
            Element outDto = XmlParse.getChildElement(rtn, "outDto");

            Element outEmailDto = XmlParse.getChildElement(outDto, "outEmailDto");
            this.setDecryptText(outEmailDto, "email");

            Element outMmsDto = XmlParse.getChildElement(outDto, "outMmsDto");
            this.setDecryptText(outMmsDto, "ctn");

            Element outMailDto = XmlParse.getChildElement(outDto, "outMailDto");
            this.setDecryptText(outMailDto, "adrCustNm");
            this.setDecryptText(outMailDto, "adrBasSbst");
            this.setDecryptText(outMailDto, "adrDtlSbst");
            this.setDecryptText(outMailDto, "rdAdrBasSbst");
            this.setDecryptText(outMailDto, "rdAdrDtlSbst");
        }else if("X51".equals(appEventCd)) {
            Element outDto = XmlParse.getChildElement(rtn, "outDto");

            List<Element> outSendInfoListlDtoList = XmlParse.getChildElementList(outDto, "outSendInfoListlDto");
            for (Element item : outSendInfoListlDtoList) {
                this.setDecryptText(item, "email");
            }
        }else if("X52".equals(appEventCd)) {
            Element outDto = XmlParse.getChildElement(rtn, "outDto");
            this.setDecryptText(outDto, "orieMail");

            List<Element> outReSndListDtoList = XmlParse.getChildElementList(outDto, "outReSndListDto");
            for (Element item : outReSndListDtoList) {
                this.setDecryptText(item, "billAdInfo");
            }
        }else if("X69".equals(appEventCd)) {
            Element outDto = XmlParse.getChildElement(rtn, "outDto");

            List<Element> outDataSharingDtoList = XmlParse.getChildElementList(outDto, "outDataSharingDto");
            for (Element item : outDataSharingDtoList) {
                this.setDecryptText(item, "svcNo");
            }
        }else if("X71".equals(appEventCd)) {
            Element outDto = XmlParse.getChildElement(rtn, "outDto");

            List<Element> outDataSharingDtoList = XmlParse.getChildElementList(outDto, "outDataSharingDto");
            for (Element item : outDataSharingDtoList) {
                this.setDecryptText(item, "svcNo");
            }
        }else if("X74".equals(appEventCd)) {
            Element outDto = XmlParse.getChildElement(rtn, "outDto");

            List<Element> coupInfoList = XmlParse.getChildElementList(outDto, "coupInfoList");
            for (Element item : coupInfoList) {
                this.setDecryptText(item, "smsRcvCtn");
            }
        }else if("X76".equals(appEventCd)) {
            Element outDto = XmlParse.getChildElement(rtn, "outDto");

            List<Element> usedCoupList = XmlParse.getChildElementList(outDto, "usedCoupList");
            for (Element item : usedCoupList) {
                this.setDecryptText(item, "smsRcvCtn");
            }
        }else if("X77".equals(appEventCd)) {
            Element outDto = XmlParse.getChildElement(rtn, "outDto");

            Element moscMvnoComInfo = XmlParse.getChildElement(outDto, "moscMvnoComInfo");
            this.setDecryptText(moscMvnoComInfo, "combSvcNo");
            this.setDecryptText(moscMvnoComInfo, "svcNo");

            List<Element> moscSrchCombInfoList = XmlParse.getChildElementList(outDto, "moscSrchCombInfoList");
            for (Element item : moscSrchCombInfoList) {
                this.setDecryptText(item, "combSvcNo");
                this.setDecryptText(item, "svcNo");
                this.setDecryptText(item, "arnoAdrDtlSbst");
                this.setDecryptText(item, "roadnAdrWholeSbst");
            }
        }else if("X78".equals(appEventCd)) {
            Element outDto = XmlParse.getChildElement(rtn, "outDto");

            List<Element> moscCombPreChkListOutDTOList = XmlParse.getChildElementList(outDto, "moscCombPreChkListOutDTO");
            for (Element item : moscCombPreChkListOutDTOList) {
                this.setDecryptText(item, "svcNo");
            }
        }else if("X79".equals(appEventCd)) {
            Element outDto = XmlParse.getChildElement(rtn, "outDto");

            List<Element> moscCombPreChkListOutDTOList = XmlParse.getChildElementList(outDto, "moscCombPreChkListOutDTO");
            for (Element item : moscCombPreChkListOutDTOList) {
                this.setDecryptText(item, "svcNo");
            }
        }else if("X86".equals(appEventCd)) {
            Element outDto = XmlParse.getChildElement(rtn, "outDto");

            List<Element> outGiveListDtoList = XmlParse.getChildElementList(outDto, "outGiveListDto");
            for (Element item : outGiveListDtoList) {
                this.setDecryptText(item, "custNm");
                this.setDecryptText(item, "rcvSvcNo");
                this.setDecryptText(item, "tgtCustNm");
            }

            List<Element> outRcvListDtoList = XmlParse.getChildElementList(outDto, "outRcvListDto");
            for (Element item : outRcvListDtoList) {
                this.setDecryptText(item, "giveCustNm");
                this.setDecryptText(item, "giveSvcNo");
                this.setDecryptText(item, "rcvCustNm");
                this.setDecryptText(item, "rcvSvcNo");
            }
        }else if("X87".equals(appEventCd)) {
            Element outDto = XmlParse.getChildElement(rtn, "outDto");

            List<Element> moscCombDtlListOutDTOList = XmlParse.getChildElementList(outDto, "moscCombDtlListOutDTO");
            for (Element item : moscCombDtlListOutDTOList) {
                this.setDecryptText(item, "svcNo");
            }
        }else if("Y07".equals(appEventCd)) {
            Element outDto = XmlParse.getChildElement(rtn, "outDto");

            this.setDecryptText(outDto, "intmSeq");
        }else if("Y13".equals(appEventCd)) {
            Element outDto = XmlParse.getChildElement(rtn, "outDto");

            this.setDecryptText(outDto, "imei");
            this.setDecryptText(outDto, "intmUniqIdntNo");
            this.setDecryptText(outDto, "wibroImei");
            this.setDecryptText(outDto, "eUiccId");
        }else if("Y14".equals(appEventCd)) {
            Element outDto = XmlParse.getChildElement(rtn, "outDto");

            this.setDecryptText(outDto, "euiccId");
        }else if("Y15".equals(appEventCd)) {
            Element outDto = XmlParse.getChildElement(rtn, "outDto");

            this.setDecryptText(outDto, "imei");
        }else if("Y39".equals(appEventCd)){
            Element outDto = XmlParse.getChildElement(rtn, "outDto");

            this.setDecryptText(outDto, "ipinCi");
            this.setDecryptText(outDto, "agntIpinCi");
        } else if("Y48".equals(appEventCd)) {
            Element outDto = XmlParse.getChildElement(rtn, "outDto");

            List<Element> moscVirtlBnkacnNoListInfoOutDTOList = XmlParse.getChildElementList(outDto, "moscVirtlBnkacnNoListInfoOutDTO");
            for (Element item : moscVirtlBnkacnNoListInfoOutDTOList) {
                this.setDecryptText(item, "virtlBnkacnNo");
            }
        }
    }

    private void setDecryptText(Element parentDto, String childNm) {
        if(parentDto == null) {
            return;
        }
        Element child = new Element(childNm);
        child.setText(this.decrypt(XmlParse.getChildValue(parentDto, childNm)));
        parentDto.removeChild(childNm);
        parentDto.addContent(child);
    }

    private String encrypt(String value, String encryptYn) {
        String result = value;
        if ("Y".equals(encryptYn)) {
            result = KisaSeedUtil.encrypt(value);
        }
        if (result == null || result == "null") {
            result = "";
        }
        return result;
    }

    private String decrypt(String value) {
        String result = KisaSeedUtil.decrypt(value);
        if (result == null || result == "null") {
            result = "";
        }
        return result;
    }
}
