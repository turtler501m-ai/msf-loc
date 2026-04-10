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

import com.ktis.mcpif.mPlatform.vo.MPlatformErrVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.XMLOutputter;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;

import com.ktis.mcpif.common.KisaSeedUtil;
import com.ktis.mcpif.common.StringUtil;
import com.ktis.mcpif.mPlatform.vo.MPlatformNstepVO;
import com.ktis.mcpif.mPlatform.vo.MPlatformReqVO;
import com.ktis.mcpif.mPlatform.vo.MPlatformResVO;

import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * 개통간소화 서비스
 */
@org.springframework.stereotype.Service("simpleOpenService")
public class SimpleOpenService {
    protected Logger logger = LogManager.getLogger(getClass());

    @Resource(name = "propertiesService")
    private EgovPropertyService propertiesService;

    protected final static String HEADER = "commHeader";
    protected final static String GLOBAL_NO = "globalNo";
    protected final static String RESPONSE_TYPE = "responseType";
    protected final static String RESPONSE_CODE = "responseCode";
    protected final static String RESPONSE_BASIC = "responseBasic";

    /**
     * OSST 개통간소화 서비스 호출
     * @param nStepVo
     * @return
     * @throws SOAPException
     * @throws MalformedURLException
     */
    public String SimpleOpenServiceCall(MPlatformReqVO searchVO) throws SOAPException, MalformedURLException {

        RestTemplate restTemplate = new RestTemplate();
        String apiInterfaceServer = propertiesService.getString("api.interface.server");

        // -------------------------------------------------------------------
        // Initialize.
        // -------------------------------------------------------------------
        //Response 객체
        String mPlatformResponse = null;
//		HashMap<String, String> reqMap = paramMapCreate(reqMessage);

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

        logger.error("SimpleOpenServiceCall parameters START ======================================");
        logger.error("SimpleOpenServiceCall::resNo=" + searchVO.getResNo());
        logger.error("SimpleOpenServiceCall::mcnResNo=" + searchVO.getMcnResNo());
        logger.error("SimpleOpenServiceCall::appEventCd=" + searchVO.getAppEventCd());
        logger.error("SimpleOpenServiceCall::gubun=" + searchVO.getGubun());
        logger.error("SimpleOpenServiceCall::npTlphNo=" + searchVO.getNpTlphNo());
        logger.error("SimpleOpenServiceCall::moveCompany=" + searchVO.getMoveCompany());
        logger.error("SimpleOpenServiceCall::cstmrType=" + searchVO.getCstmrType());
        logger.error("SimpleOpenServiceCall::indvBizrYn=" + searchVO.getIndvBizrYn());
        logger.error("SimpleOpenServiceCall::selfCertType=" + searchVO.getSelfCertType());
        logger.error("SimpleOpenServiceCall::custIdntNo=" + searchVO.getCustIdntNo());
        logger.error("SimpleOpenServiceCall::crprNo=" + searchVO.getCrprNo());
        logger.error("SimpleOpenServiceCall::cstmrName=" + searchVO.getCstmrName());
        logger.error("SimpleOpenServiceCall::cntpntShopId=" + searchVO.getCntpntShopId());
        logger.error("SimpleOpenServiceCall::tlphNo=" + searchVO.getTlphNo());
        logger.error("SimpleOpenServiceCall parameters END   ======================================");
        logger.error("SimpleOpenServiceCall::☆☆☆☆☆  url  ☆☆☆☆☆" + url);
        MPlatformResVO vo = new MPlatformResVO();
        URL requestUrl = null;
        try {
            if("NP1".equals(searchVO.getAppEventCd())){
                //vo = osstMapper.getXmlMessageNP1(searchVO);
                logger.error("SimpleOpenServiceCall searchVO.getAppEventCd() = " + searchVO.getAppEventCd());

                vo = restTemplate.postForObject(apiInterfaceServer + "/mPlatform/getXmlMessageNP1", searchVO, MPlatformResVO.class);
                logger.error("SimpleOpenServiceCall /mPlatform/getXmlMessageNP1 = " + vo);

            } else if("NP3".equals(searchVO.getAppEventCd())){
                //vo = osstMapper.getXmlMessageNP1(searchVO);
                logger.error("SimpleOpenServiceCall searchVO.getAppEventCd() = " + searchVO.getAppEventCd());

                vo = restTemplate.postForObject(apiInterfaceServer + "/mPlatform/getXmlMessageNP3", searchVO, MPlatformResVO.class);
                logger.error("SimpleOpenServiceCall /mPlatform/getXmlMessageNP3 = " + vo);

            }else if ("UC0".equals(searchVO.getAppEventCd())) {
                vo = restTemplate.postForObject(apiInterfaceServer + "/mPlatform/getXmlMessageUC0", searchVO, MPlatformResVO.class);

                //202302 wooki - 사전승낙제 추가(DB조회할 값이 없어 바로 set)
            }else if("FS0".equals(searchVO.getAppEventCd()) || "FS1".equals(searchVO.getAppEventCd()) || "FS2".equals(searchVO.getAppEventCd())) {
                vo.setMngmAgncId(searchVO.getMngmAgncId());
                vo.setCntpntCd(searchVO.getCntpntCd());
                vo.setAsgnAgncId(searchVO.getMngmAgncId()); //할당대리점ID를 mngmAgncId(관리대리점ID)로
                vo.setAppEventCd(searchVO.getAppEventCd());
                vo.setServiceName("OsstFrmpapService");

                if ("FS0".equals(searchVO.getAppEventCd())) { //서식지 목록조회
                    vo.setRetvStrtDt(searchVO.getRetvStrtDt());
                    vo.setRetvEndDt(searchVO.getRetvEndDt());
                    vo.setSvcApyTrtStatCd(searchVO.getSvcApyTrtStatCd());
                    vo.setRetvSeq(searchVO.getRetvSeq());
                    vo.setRetvCascnt(searchVO.getRetvCascnt());
                    vo.setOperationName("osst:osstFrmpapListRetv");
                    vo.setVoName("OsstFrmpapListRetvInVO");
                } else if ("FS1".equals(searchVO.getAppEventCd())) { //서식지 상태조회
                    vo.setFrmpapId(searchVO.getFrmpapId());
                    vo.setOperationName("osst:osstFrmpapRetv");
                    vo.setVoName("OsstFrmpapRetvInVO");
                } else if ("FS2".equals(searchVO.getAppEventCd())) { //서식지 상태변경
                    vo.setFrmpapId(searchVO.getFrmpapId());
                    vo.setFrmpapStatCd(searchVO.getFrmpapStatCd());
                    vo.setOperationName("osst:osstFrmpapStatChg");
                    vo.setVoName("OsstFrmpapStatChgInVO");
                }

            }else if("FS3".equals(searchVO.getAppEventCd()) || "FS4".equals(searchVO.getAppEventCd())){
                vo.setAppEventCd(searchVO.getAppEventCd());
                vo.setAsgnAgncId(searchVO.getOderTrtOrgId());
                vo.setOderTrtOrgId(searchVO.getOderTrtOrgId());
                vo.setOderTypeCd(searchVO.getOderTypeCd());
                vo.setTlphNo(searchVO.getTlphNo());
                vo.setOderTrtId(searchVO.getOderTrtId());
                vo.setServiceName("OsstNthnFrmpapAtcTrtService");
                if("FS3".equals(searchVO.getAppEventCd())){ //무서식지 오더 데이터 조회
                    vo.setRetvStDt(searchVO.getRetvStDt());
                    vo.setRetvFnsDt(searchVO.getRetvFnsDt());
                    vo.setRetvSeq(searchVO.getRetvSeq());
                    vo.setRetvCascnt(searchVO.getRetvCascnt());
                    vo.setOperationName("osst:retvOsstNthnFrmpapTgt");
                    vo.setVoName("OsstNthnFrmpapTgtRetvInVO");
                }
                else if("FS4".equals(searchVO.getAppEventCd())){    //무서식지 오더 후첨부 처리
                    vo.setItgFrmpapSeq(searchVO.getItgFrmpapSeq());
                    vo.setFrmpapId(searchVO.getFrmpapId());
                    vo.setScanDt(searchVO.getScanDt());
                    vo.setSvcContId(searchVO.getSvcContId());
                    vo.setOperationName("osst:trtOsstNthnFrmpapAtc");
                    vo.setVoName("OsstNthnFrmpapAtcTrtInVO");
                }
            } else if("FT0".equals(searchVO.getAppEventCd()) || "FT1".equals(searchVO.getAppEventCd())
                    || "FS8".equals(searchVO.getAppEventCd()) || "FS9".equals(searchVO.getAppEventCd())){
                vo.setServiceName("OsstCustFathMgmtService");
                vo.setAppEventCd(searchVO.getAppEventCd());
                vo.setAsgnAgncId(searchVO.getAsgnAgncId());
                if("FT0".equals(searchVO.getAppEventCd())){        //고객 안면인증 대상 여부 조회
                    vo.setOnlineOfflnDivCd(searchVO.getOnlineOfflnDivCd());
                    vo.setOrgId(searchVO.getAsgnAgncId());
                    vo.setRetvCdVal(searchVO.getRetvCdVal());
                    vo.setCpntId(searchVO.getCpntId());
                    vo.setOperationName("osst:custFathTgtYnRetv");
                    vo.setVoName("CustFathTgtYnRetvInVO");
                } else if("FT1".equals(searchVO.getAppEventCd())) {
                    vo.setFathTransacId(searchVO.getFathTransacId());
                    vo.setOperationName("osst:custFathTxnSkipReq");
                    vo.setVoName("CustFathTxnSkipReqInVO");
                } else if("FS8".equals(searchVO.getAppEventCd())){    //고객 안면인증 URL 요청
                    vo.setOrgId(searchVO.getAsgnAgncId());
                    vo.setCpntId(searchVO.getCpntId());
                    vo.setOnlineOfflnDivCd(searchVO.getOnlineOfflnDivCd());
                    vo.setSmsRcvTelNo(searchVO.getSmsRcvTelNo());
                    vo.setRetvCdVal(searchVO.getRetvCdVal());
                    vo.setFathSbscDivCd(searchVO.getFathSbscDivCd());
                    vo.setOperationName("osst:custFathUrlRqt");
                    vo.setVoName("CustFathUrlRqtInVO");
                } else if("FS9".equals(searchVO.getAppEventCd())){    //고객 안면인증 URL 요청
                    vo.setFathTransacId(searchVO.getFathTransacId());
                    vo.setRetvDivCd(searchVO.getRetvDivCd());
                    vo.setOperationName("osst:custFathTxnRetv");
                    vo.setVoName("CustFathTxnRetvInVO");
                }
            }else if("MC0".equals(searchVO.getAppEventCd())) {
                vo = restTemplate.postForObject(apiInterfaceServer + "/mPlatform/getXmlMessageMC0", searchVO, MPlatformResVO.class);
            }else if("MP0".equals(searchVO.getAppEventCd())) {
                vo = restTemplate.postForObject(apiInterfaceServer + "/mPlatform/getXmlMessageMcnMP0", searchVO, MPlatformResVO.class);
            }else {
                //vo = osstMapper.getRequestInfo(searchVO);
                logger.error("SimpleOpenServiceCall searchVO.getAppEventCd() = " + searchVO.getAppEventCd());
                vo = restTemplate.postForObject(apiInterfaceServer + "/mPlatform/getRequestInfo", searchVO, MPlatformResVO.class);
                logger.error("SimpleOpenServiceCall /mPlatform/getRequestInfo = " + vo);

                if(vo != null && ("FPC0".equals(searchVO.getAppEventCd()) || "FOP0".equals(searchVO.getAppEventCd()))) {
                    vo.setMngmAgncId(searchVO.getMngmAgncId());
                    vo.setCntpntCd(searchVO.getCntpntCd());
                    vo.setFrmpapId(searchVO.getFrmpapId());
                    logger.error("SimpleOpenServiceCall::☆☆☆☆☆  getMngmAgncId  ☆☆☆☆☆"+vo.getMngmAgncId());
                    logger.error("SimpleOpenServiceCall::☆☆☆☆☆  getCntpntCd  ☆☆☆☆☆"+vo.getCntpntCd());
                    logger.error("SimpleOpenServiceCall::☆☆☆☆☆  getFrmpapId  ☆☆☆☆☆"+vo.getFrmpapId());
                }
            }

            if(vo == null || "".equals(vo.getServiceName()) ){
                logger.error("SimpleOpenServiceCall::☆☆☆☆☆  vo null  ☆☆☆☆☆" + searchVO.getResNo());
                throw new NoSuchElementException("Request vo is null");
            }

            // 호출 URL
            requestUrl = new URL(new URL(url), vo.getServiceName(), new URLStreamHandler() {
                @Override
                protected URLConnection openConnection(URL url) throws IOException {
                    URL clone = new URL(url.toString());
                    URLConnection clone_connection = clone.openConnection();

                    clone_connection.setConnectTimeout(connetTime * 1000);
                    clone_connection.setReadTimeout(connetTime * 1000);

                    return(clone_connection);
                }
            });

            //logger.error("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++::requestUrl.toString()=" + requestUrl.toString());
            //logger.error("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++::vo.getServiceName()=" + vo.getServiceName());

        }catch(NoSuchElementException e){
            try{if(soapConnection != null) soapConnection.close();}catch(Exception e1){}
            throw e;
        }catch(MalformedURLException e){
            try{if(soapConnection != null) soapConnection.close();}catch(Exception e1){}
            throw e;
        }catch(Exception e) {

            // 실패 이력 insert
            this.insertOsstErrLog(searchVO, e);
            logger.error(e);
        }

        // m-platform 로그인
        try {
            String loginPassword = propertiesService.getString("clntUsrId")+":"+propertiesService.getString("clntUsrPw");
            String authorization = new sun.misc.BASE64Encoder().encode(loginPassword.getBytes());
            soapMessage.getMimeHeaders().addHeader("Authorization", "Basic " + authorization);

            doTrustToCertificates();
        }catch(Exception e){

            // 실패 이력 insert
            this.insertOsstErrLog(searchVO, e);

            logger.error("SimpleOpenServiceCall::M-PLATEFORM 로그인 실패");
            logger.error(e);
        }

        // XML 메세지 생성 및 Service Call
        try{

            String billAcntNo = searchVO.getBillAcntNo();
            if (billAcntNo != null && !"".equals(billAcntNo)) {
                vo.setBillAcntNo(billAcntNo);
            }

            //2023.08-wooki 암호화 여부 set을 위해 API에서 공통코드에 등록된 암호화여부를 조회 by appEventCd
            vo.setEncrypt(false); //암호화여부 디폴트값 false
            String encryptYn = restTemplate.postForObject(apiInterfaceServer + "/mPlatform/getMplatformCryptionYn", vo.getAppEventCd(), String.class);
            if(StringUtil.isNotBlank(encryptYn)) {
                if("Y".equals(encryptYn)) {
                    vo.setEncrypt(true);
                }
            }

            // XML 메시지 생성
            String requestXml = this.xmlMessageCreate(vo);

            // 파싱 객체 생성
            docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            // String 타입 변경
            doc = docBuilder.parse(new ByteArrayInputStream(requestXml.getBytes()));

            domSource = new DOMSource(doc);

            soapMessage.getSOAPPart().setContent(domSource);
//
            logger.error("SimpleOpenServiceCall::☆☆☆☆☆ Call Start ☆☆☆☆☆");
            logger.error("SimpleOpenServiceCall::☆☆ requestService : "+vo.getServiceName());
            logger.error("SimpleOpenServiceCall::☆☆ requestXml : "+requestXml.toString());

            // SOAP CALL
            SOAPMessage responseMessage = soapConnection.call(soapMessage, requestUrl);
            soapConnection.close();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            responseMessage.writeTo(out);

            mPlatformResponse = new String(out.toByteArray());
            logger.error("SimpleOpenServiceCall::☆☆ responseMessage 리턴 성공");

            //연동결과저장
            HashMap<String, Object> resultMap = this.toResponseParse(mPlatformResponse, searchVO.getAppEventCd());

            MPlatformReqVO reqVO = new MPlatformReqVO();
            reqVO.setResNo(searchVO.getResNo());
            reqVO.setAppEventCd(searchVO.getAppEventCd());
            reqVO.setOsstOrdNo((String) resultMap.get("osstOrdNo"));
            reqVO.setNstepGlobalId((String) resultMap.get("globalNo"));

            // FS4일때 param데이터 추가
            if("FS4".equals(searchVO.getAppEventCd())){
                reqVO.setPrdcChkNotiMsg("{"+ "itgFrmpapSeq : " +searchVO.getItgFrmpapSeq() +","+
                        "oderTypeCd : "     +searchVO.getOderTypeCd()   +","+
                        "tlphNo : "         +searchVO.getTlphNo()       +","+
                        "frmpapId : "       +searchVO.getFrmpapId()     +","+
                        "scanDt : "         +searchVO.getScanDt()       +","+
                        "oderTrtOrgId : "   +searchVO.getOderTrtOrgId() +","+
                        "oderTrtId : "      +searchVO.getOderTrtId()    +","+
                        "svcContId : "      +searchVO.getSvcContId()    +"}");
                logger.error("FS4 param ===> "+reqVO.getPrdcChkNotiMsg());
            } else if ("FT0".equals(searchVO.getAppEventCd())) { // FS8 일때 param 추가
                reqVO.setPrdcChkNotiMsg("{"+ "orgId : " +searchVO.getOrgId()    +","+
                        "onlineOfflnDivCd : "   +searchVO.getOnlineOfflnDivCd() +","+
                        "retvCdVal : "          +searchVO.getRetvCdVal()        +","+
                        "cpntId : "             +searchVO.getCpntId()           +","+
                        "trtResltCd : "         +resultMap.get("trtResltCd")    +","+
                        "trtResltSbst : "       +resultMap.get("trtResltSbst")  +","+
                        "stbznPerdYn : "        +resultMap.get("stbznPerdYn")  +"}");
            } else if ("FT1".equals(searchVO.getAppEventCd())) { // FS8 일때 param 추가
                reqVO.setPrdcChkNotiMsg("{" + "fathTransacId : " + searchVO.getFathTransacId() + "}");
            } else if ("FS8".equals(searchVO.getAppEventCd())) { // FS8 일때 param 추가
                reqVO.setPrdcChkNotiMsg("{"+ "orgId : " +searchVO.getOrgId()    +","+
                        "cpntId : "             +searchVO.getCpntId()           +","+
                        "onlineOfflnDivCd : "   +searchVO.getOnlineOfflnDivCd() +","+
                        "smsRcvTelNo : "        +searchVO.getSmsRcvTelNo()      +","+
                        "fathSbscDivCd : "      +searchVO.getFathSbscDivCd()    +","+
                        "retvCdVal : "          +searchVO.getRetvCdVal()        +","+
                        "resltCd : "            +resultMap.get("resltCd")       +","+
                        "resltSbst : "          +resultMap.get("resltSbst")     +","+
                        "urlAdr : "             +resultMap.get("urlAdr")        +","+
                        "fathTransacId : "      +resultMap.get("fathTransacId") +"}");
            } else if ("FS9".equals(searchVO.getAppEventCd())) { // FS8 일때 param 추가
                reqVO.setPrdcChkNotiMsg("{"+ "fathTransacId : " +searchVO.getFathTransacId() +","+
                        "retvDivCd : "          +searchVO.getRetvDivCd()                     +","+
                        "fathResltCd : "        +resultMap.get("fathResltCd")                +","+
                        "fathDecideCd : "       +resultMap.get("fathDecideCd")               +","+
                        "fathResltMsgSbst : "   +resultMap.get("fathResltMsgSbst")           +"}");
            }

            //명의변경 시 resNo에 mcnResNo를 담는다
            if(StringUtil.isNotEmpty(searchVO.getMcnResNo())) {
                List<String> trgList = Arrays.asList("FT0", "FT1", "FS8", "FS9", "MC0", "MP0");
                boolean trgExists = trgList.contains(searchVO.getAppEventCd());
                if(trgExists) {
                    reqVO.setResNo(searchVO.getMcnResNo());
                }
            }

            if(!"N".equals((String) resultMap.get("responseType"))) { //성공이 아닐 때

                reqVO.setRsltCd((String) resultMap.get("responseCode"));
                reqVO.setRsltMsg((String) resultMap.get("responseBasic"));

            }else { //성공일 때

                if("N".equals((String) resultMap.get("responseType")) && (resultMap.get("rsltCd") == null || "".equals((String)resultMap.get("rsltCd")) ) ){
                    reqVO.setRsltCd((String) resultMap.get("responseCode"));
                    // responseCode 가 없으면 responseType 세팅
                    if("".equals(reqVO.getRsltCd())) reqVO.setRsltCd((String) resultMap.get("responseType"));
                }

                // ST1인 경우 진행상태 추가세팅
                if("ST1".equals(vo.getAppEventCd())){
                    reqVO.setPrgrStatCd((String) resultMap.get("prgrStatCd"));
                    reqVO.setRsltMsg((String) resultMap.get("rsltMsg"));
                }

                //FS2일 때 responseType 값 상관 없이 outDto 하위의 rsltCd, rsltMsg를 담는다
                //FS5일 때 outDto 하위의 SVC_STATUS, ERR_MSG를 담는다
                if("FS2".equals(vo.getAppEventCd()) || "FS4".equals(vo.getAppEventCd()) || "FS5".equals(vo.getAppEventCd()) || "FT1".equals(vo.getAppEventCd())) {
                    reqVO.setRsltCd((String) resultMap.get("rsltCd"));
                    reqVO.setRsltMsg((String) resultMap.get("rsltMsg"));
                }

                //202308 wooki - 결과가 성공이고, NU1이고, 암호화 적용일 때에만 responseXml에서 tlphNo 복호화
                //202402 wooki - 결과가 성공이고, 암호화 적용일 때에만 responseXml 내용 복호화
                if("N".equals((String) resultMap.get("responseType")) && vo.isEncrypt()) {

                    try {
                        String result = mPlatformResponse;

                        Element root = XmlParse.getRootElement("<?xml version=\"1.0\" encoding=\"euc-kr\"?>"+mPlatformResponse);
                        Element rtn = XmlParse.getReturnElement(root);

                        if("NU1".equals(vo.getAppEventCd())) {
                            Element outDto = XmlParse.getChildElement(rtn, "outDto");
                            List<Element> svcNoList = XmlParse.getChildElementList(outDto, "svcNoList");
                            for (Element item : svcNoList) {
                                this.setDecryptText(item, "tlphNo");
                            }
                        }else if("FS0".equals(vo.getAppEventCd())) {  //서식지 목록조회
                            List<Element> list = XmlParse.getChildElementList(rtn, "outDto");
                            for (Element item : list) {
                                this.setDecryptText(item, "custIdntNo");
                                this.setDecryptText(item, "custNm");
                            }
                        }else if("FS1".equals(vo.getAppEventCd())) { //서식지 상태조회
                            Element outDto = XmlParse.getChildElement(rtn, "outDto");
                            this.setDecryptText(outDto, "userNm");
                            this.setDecryptText(outDto, "nflCustNm");
                            this.setDecryptText(outDto, "nflCustIdfyNo");
                            this.setDecryptText(outDto, "custNm");
                            this.setDecryptText(outDto, "realCustIdntNo");
                        }else if("FS3".equals(vo.getAppEventCd())) { //무서식지 오더 데이터 조회
                            Element outDto = XmlParse.getChildElement(rtn, "outDto");
                            List<Element> list = XmlParse.getChildElementList(outDto, "frmpapInfoDTO");
                            for (Element item : list) {
                                this.setDecryptText(item, "custNm");
                                this.setDecryptText(item, "tlphNo");
                            }
                        }else if("FS9".equals(vo.getAppEventCd())) { //안면인증 결과 조회
                            Element outDto = XmlParse.getChildElement(rtn, "outDto");
                            this.setDecryptText(outDto, "smsRcvTelNo");
                            this.setDecryptText(outDto, "fathCustIdfyNo");
                            this.setDecryptText(outDto, "fathCustNm");
                            this.setDecryptText(outDto, "fathDriveLicnsNo");
                            this.setDecryptText(outDto, "fathBthday");
                            this.setDecryptText(outDto, "idcardPhotoImg");
                            this.setDecryptText(outDto, "idcardCopiesImg");
                            this.setDecryptText(outDto, "mblIdcardQrImg");
                        }

                        XMLOutputter outp = new XMLOutputter();
                        result = outp.outputString(root);

                        mPlatformResponse = result;
                    }catch(Exception e) {

                        // 실패 이력 insert
                        this.insertOsstErrLog(searchVO, e);

                        logger.error("SimpleOpenServiceCall :: mPlatformResponse::복호화실패");
                        logger.error(e.getMessage());
                    }
                }
            }

            restTemplate.postForObject(apiInterfaceServer + "/mPlatform/insertRequestOsst", reqVO, Void.class);

            logger.error("SimpleOpenServiceCall::☆☆ responseXml : "+mPlatformResponse);
            logger.error("SimpleOpenServiceCall::☆☆☆☆☆  Call End  ☆☆☆☆☆");

        }catch (Exception e) {

            try{if(soapConnection != null) soapConnection.close();}catch(Exception e1){}

            // 실패 이력 insert
            this.insertOsstErrLog(searchVO, e);

            logger.error("SimpleOpenServiceCall ===================================");
            logger.error("SimpleOpenServiceCall::" + e.getMessage());
            logger.error("SimpleOpenServiceCall ===================================");
        }

        return mPlatformResponse;
    }

    /**
     * OSST 인증
     * @throws Exception
     */
    public void doTrustToCertificates() throws Exception {
//		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
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

    /**
     * N-Step 전송 XML 생성
     * @param nStepVo
     * @return
     */
    public String xmlMessageCreate(MPlatformResVO vo) {

        RestTemplate restTemplate = new RestTemplate();
        String apiInterfaceServer = propertiesService.getString("api.interface.server");

        logger.error("SimpleOpenServiceCall::xml 메세지 생성 초기값 START ================================================");
        logger.error("SimpleOpenServiceCall::getAsgnAgncId=" + vo.getAsgnAgncId());
        logger.error("SimpleOpenServiceCall::appEventCd=" + vo.getAppEventCd());
        logger.error("SimpleOpenServiceCall::resNo=" + vo.getResNo());			// M전산 예약번호
        logger.error("SimpleOpenServiceCall::mvnoOrdNo=" + vo.getMvnoOrdNo());	// 14자리로 변경한 예약번호(OSST 연동시 사용)
        logger.error("SimpleOpenServiceCall::gubun=" + vo.getGubun());
        logger.error("SimpleOpenServiceCall::wantTlphNo=" + vo.getWantTlphNo());
        logger.error("SimpleOpenServiceCall::cntpntCd=" + vo.getCntpntCd());
        logger.error("SimpleOpenServiceCall::xml 메세지 생성 초기값 END   ================================================");

        // -------------------------------------------------------------------
        // Initialize.
        // -------------------------------------------------------------------
        long time = System.currentTimeMillis();
        SimpleDateFormat dayTime = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String now = dayTime.format(new Date(time));

        StringBuffer requestXml = new StringBuffer();

        try{
            requestXml.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:osst=\"http://osst.so.itl.mvno.kt.com/\">");
            requestXml.append("<soapenv:Header/>");
            requestXml.append("<soapenv:Body>");

            requestXml.append("<" + vo.getOperationName().replaceAll("&", "<![CDATA[&]]>") + ">");
            requestXml.append("<" + vo.getVoName().replaceAll("&", "<![CDATA[&]]>") + ">");

            requestXml.append("<bizHeader>");
            requestXml.append("<appEntrPrsnId>" + propertiesService.getString("appEntrPrsnId") + "</appEntrPrsnId>");
            requestXml.append("<appAgncCd>" + vo.getAsgnAgncId().replaceAll("&", "<![CDATA[&]]>") +"</appAgncCd>");
            requestXml.append("<appEventCd>" + vo.getAppEventCd().replaceAll("&", "<![CDATA[&]]>") + "</appEventCd>");
            requestXml.append("<appSendDateTime>" + now.substring(0,14).replaceAll("&", "<![CDATA[&]]>") + "</appSendDateTime>");
            requestXml.append("<appRecvDateTime></appRecvDateTime>");
            requestXml.append("<appLgDateTime></appLgDateTime>");
            // NSTEP_USER_ID 가 없는 경우 프러퍼티에 설정된 값 사용
            if(vo.getNstepUserId() == null || "".equals(vo.getNstepUserId())){
                requestXml.append("<appNstepUserId>" + propertiesService.getString("appNstepUserId") + "</appNstepUserId>");
            }else{
                requestXml.append("<appNstepUserId>" + vo.getNstepUserId().replaceAll("&", "<![CDATA[&]]>") + "</appNstepUserId>");
            }
            requestXml.append("<appOrderId></appOrderId>");
            requestXml.append("</bizHeader>");

            // commHeader 생성
            requestXml.append("<commHeader>");
            requestXml.append("<globalNo>" + propertiesService.getString("appNstepUserId") + now + "</globalNo>");
            requestXml.append("<encYn></encYn>");
            requestXml.append("<responseType></responseType>");
            requestXml.append("<responseCode></responseCode>");
            requestXml.append("<responseLogcd></responseLogcd>");
            requestXml.append("<responseTitle></responseTitle>");
            requestXml.append("<responseBasic></responseBasic>");
            requestXml.append("<langCode></langCode>");
            requestXml.append("<filler></filler>");
            requestXml.append("</commHeader>");

            if("FS5".equals(vo.getAppEventCd())) {
                requestXml.append("<indto>");
            } else {
                requestXml.append("<inDto>");
            }
            //String preFix = "nst:";
            MPlatformResVO xmlVO = new MPlatformResVO();

            if("PC0".equals(vo.getAppEventCd()) || "FPC0".equals(vo.getAppEventCd())){
                // 사전체크 및 고객등록
                //xmlVO = osstMapper.getXmlMessagePC0(vo.getResNo());
                xmlVO = restTemplate.postForObject(apiInterfaceServer + "/mPlatform/getXmlMessagePC0", vo.getResNo(), MPlatformResVO.class);
                if(xmlVO == null)  throw new NoSuchElementException("Request xmlVO is null");

                xmlVO.setEncrypt(vo.isEncrypt());
                requestXml.append(xmlMessagePC0(xmlVO, vo.getAppEventCd()));
            }
            else if("NU1".equals(vo.getAppEventCd())){
                // 번호조회
                //xmlVO = osstMapper.getXmlMessageNU1(vo);
                xmlVO = restTemplate.postForObject(apiInterfaceServer + "/mPlatform/getXmlMessageNU1", vo, MPlatformResVO.class);
                if(xmlVO == null)  throw new NoSuchElementException("Request xmlVO is null");

                xmlVO.setEncrypt(vo.isEncrypt());
                requestXml.append(xmlMessageNU1(xmlVO));
            }
            else if("NU2".equals(vo.getAppEventCd())){
                // 번호예약
                // 고객포털에서 MCP_REQUEST_OSST.PRGR_STAT_CD = 'NU2' 로 데이터 생성후 PRX 호출(예약번호 14자리로 변경하여 등록)
                //xmlVO = osstMapper.getXmlMessageNU2(vo);
                xmlVO = restTemplate.postForObject(apiInterfaceServer + "/mPlatform/getXmlMessageNU2", vo, MPlatformResVO.class);
                if(xmlVO == null)  throw new NoSuchElementException("Request xmlVO is null");

                xmlVO.setEncrypt(vo.isEncrypt());
                requestXml.append(xmlMessageNU2(xmlVO));
            }
            else if("OP0".equals(vo.getAppEventCd()) || "FOP0".equals(vo.getAppEventCd())){
                // 개통 및 수납
                //xmlVO = osstMapper.getXmlMessageOP0(vo.getResNo());
                xmlVO = restTemplate.postForObject(apiInterfaceServer + "/mPlatform/getXmlMessageOP0", vo.getResNo(), MPlatformResVO.class);
                if(xmlVO == null)  throw new NoSuchElementException("Request xmlVO is null");

                xmlVO.setEncrypt(vo.isEncrypt());
                //FOP0 set 추가
                if("FOP0".equals(vo.getAppEventCd())) {
                    xmlVO.setMngmAgncId(vo.getMngmAgncId());
                    xmlVO.setCntpntCd(vo.getCntpntCd());
                    logger.error("xmlMessageCreate::☆☆☆☆☆  getMngmAgncId  ☆☆☆☆☆"+xmlVO.getMngmAgncId());
                    logger.error("xmlMessageCreate::☆☆☆☆☆  getCntpntCd  ☆☆☆☆☆"+xmlVO.getCntpntCd());
                }
                requestXml.append(xmlMessageOP0(xmlVO));

                String billAcntNo = vo.getBillAcntNo();
                // 타인납부를 위한 처리
                if(billAcntNo != null && !"".equals(billAcntNo)){
                    requestXml.append("<billAcntNo>" + billAcntNo + "</billAcntNo>");
                }
            }
            else if("ST1".equals(vo.getAppEventCd())){
                // 상태체크
                //xmlVO = osstMapper.getXmlMessageST1(vo.getMvnoOrdNo());
                xmlVO = restTemplate.postForObject(apiInterfaceServer + "/mPlatform/getXmlMessageST1", vo.getMvnoOrdNo(), MPlatformResVO.class);
                if(xmlVO == null)  throw new NoSuchElementException("Request xmlVO is null");

                xmlVO.setEncrypt(vo.isEncrypt());
                requestXml.append(xmlMessageST1(xmlVO));
            }
            else if("NP1".equals(vo.getAppEventCd())){
                // 번호이동사전승인
                // URL 호출시 사용한 파라미터를 이용 ( getXmlMessageNP1 )
                requestXml.append(xmlMessageNP1(vo));
            }
            else if("NP3".equals(vo.getAppEventCd())){
                // 사전동의 결과조회
                requestXml.append(xmlMessageNP3(vo));
            }
            else if("NP2".equals(vo.getAppEventCd())){
                // 번호이동납부주장
                //xmlVO = osstMapper.getXmlMessageNP2(vo.getResNo());
                xmlVO = restTemplate.postForObject(apiInterfaceServer + "/mPlatform/getXmlMessageNP2", vo.getResNo(), MPlatformResVO.class);
                if(xmlVO == null)  throw new NoSuchElementException("Request xmlVO is null");

                xmlVO.setEncrypt(vo.isEncrypt());
                requestXml.append(xmlMessageNP2(xmlVO));
            }
            else if("UC0".equals(vo.getAppEventCd())){
                // 유심변경 사전체크 및 유심 변경 처리.
                requestXml.append(xmlMessageUC0(vo));
            }
            //사전승낙제 wooki
            else if("FS0".equals(vo.getAppEventCd()) || "FS1".equals(vo.getAppEventCd()) || "FS2".equals(vo.getAppEventCd())) {
                requestXml.append(this.xmlMessageFS(vo));
            }
            else if("FS3".equals(vo.getAppEventCd()) || "FS4".equals(vo.getAppEventCd())) {
                requestXml.append(this.xmlMessageFS34(vo));
            }
            else if("FS5".equals(vo.getAppEventCd())) {
                String real_yn = propertiesService.getString("nStep.real.yn");

                MPlatformNstepVO nstepVo = new MPlatformNstepVO();
                nstepVo = restTemplate.postForObject(apiInterfaceServer + "/mPlatform/getXmlMessageFS5", vo.getResNo(), MPlatformNstepVO.class);
                if(nstepVo == null)  throw new NoSuchElementException("Request nstepVo is null");

                logger.error("nstepVo ======================================== resNo :: " + nstepVo.getSrlNo());
                nstepVo.setEncrypt(vo.isEncrypt());
                nstepVo.setAppEventCd(vo.getAppEventCd());

                //개발용 테스트 setting
                if(!"Y".equals(real_yn)) {
                    if(StringUtil.isEmpty(nstepVo.getCntpntShopId())) {
                        nstepVo.setCntpntShopId("1100011741");
                    }
                    if(StringUtil.isEmpty(nstepVo.getHndsetMdlId())) {
                        nstepVo.setHndsetMdlId("5176");
                    }
                    if(StringUtil.isEmpty(nstepVo.getAgncId())) {
                        nstepVo.setAgncId("AA00364");
                    }
                }

                requestXml.append(this.xmlMessageFS5(nstepVo));
            } else if("FT0".equals(vo.getAppEventCd())) {
                requestXml.append(this.xmlMessageFT0(vo));
            } else if("FT1".equals(vo.getAppEventCd())) {
                requestXml.append(this.xmlMessageFT1(vo));
            } else if("FS8".equals(vo.getAppEventCd())) {
                requestXml.append(this.xmlMessageFS8(vo));
            } else if("FS9".equals(vo.getAppEventCd())) {
                requestXml.append(this.xmlMessageFS9(vo));
            }else if("MC0".equals(vo.getAppEventCd())) { //명의변경사전체크
                requestXml.append(this.xmlMessageMC0(vo));
            }else if("MP0".equals(vo.getAppEventCd())) { //명의변경
                requestXml.append(this.xmlMessage_MP0(vo));
            }

            if("FS5".equals(vo.getAppEventCd())) {
                requestXml.append("</indto>");
            } else {
                requestXml.append("</inDto>");
            }

            //FPC0, FOP0 일 때 inDto 와 동일 레벨 XML 생성
            if("FPC0".equals(vo.getAppEventCd()) || "FOP0".equals(vo.getAppEventCd())) {
                requestXml.append(this.xmlMessageFprc(vo));
            }

            // 개통처리인 경우 상품정보 XML 생성
            if("OP0".equals(vo.getAppEventCd()) || "FOP0".equals(vo.getAppEventCd())){
                requestXml.append(xmlMessageProd(vo.getResNo()));
            }

            // 번호이동인 경우 추가 구성
            if("MNP".equals(vo.getOrderTypeCd())){
                if("PC0".equals(vo.getAppEventCd()) || "FPC0".equals(vo.getAppEventCd())){
                    xmlVO.setEncrypt(vo.isEncrypt());
                    requestXml.append(xmlMessagePC0NP(xmlVO));
                }
                else if("OP0".equals(vo.getAppEventCd()) || "FOP0".equals(vo.getAppEventCd())){
                    xmlVO.setEncrypt(vo.isEncrypt());
                    requestXml.append(xmlMessageOP0NP(xmlVO));
                }
            }

            requestXml.append("</" + vo.getVoName() + ">");
            requestXml.append("</" + vo.getOperationName() + ">");

            requestXml.append("</soapenv:Body>");
            requestXml.append("</soapenv:Envelope>");

        }catch(NoSuchElementException e){
            throw e;
        }catch(NullPointerException e){
            throw e;
        }catch(Exception e){

            logger.error("xmlMessageCreate ========================================");
            logger.error("SimpleOpenServiceCall::" + e.getMessage());
            logger.error("xmlMessageCreate ========================================");
        }

        return requestXml.toString();
    }

    /**
     * 사전체크 xml 본문 생성
     * @param reqMap
     * @return
     */
    public String xmlMessagePC0(MPlatformResVO vo, String appEventCd){
        StringBuffer sb = new StringBuffer();

        try{

            sb.append("<mvnoOrdNo>" + vo.getMvnoOrdNo().replaceAll("&", "<![CDATA[&]]>") + "</mvnoOrdNo>");
            sb.append("<slsCmpnCd>" + vo.getSlsCmpnCd().replaceAll("&", "<![CDATA[&]]>") + "</slsCmpnCd>");
            sb.append("<custTypeCd>" + vo.getCustTypeCd().replaceAll("&", "<![CDATA[&]]>") + "</custTypeCd>");
            sb.append("<custIdntNoIndCd>" + vo.getCustIdntNoIndCd().replaceAll("&", "<![CDATA[&]]>") + "</custIdntNoIndCd>");
            sb.append("<custIdntNo>" + vo.getCustIdntNo().replaceAll("&", "<![CDATA[&]]>") + "</custIdntNo>");
            sb.append("<custNm>" + vo.getCustNm().replaceAll("&", "<![CDATA[&]]>") + "</custNm>");
            sb.append("<cntrUseCd>" + vo.getCntrUseCd().replaceAll("&", "<![CDATA[&]]>") + "</cntrUseCd>");
            sb.append("<instYn>" + vo.getInstYn().replaceAll("&", "<![CDATA[&]]>") + "</instYn>");
            sb.append("<scnhndPhonInstYn>" + vo.getScnhndPhonInstYn().replaceAll("&", "<![CDATA[&]]>") + "</scnhndPhonInstYn>");
            sb.append("<myslAgreYn>" + vo.getMyslAgreYn().replaceAll("&", "<![CDATA[&]]>") + "</myslAgreYn>");
            sb.append("<nativeRlnamAthnEvdnPprCd>" + vo.getNativeRlnamAthnEvdnPprCd().replaceAll("&", "<![CDATA[&]]>") + "</nativeRlnamAthnEvdnPprCd>");
            sb.append("<athnRqstcustCntplcNo>" + vo.getAthnRqstcustCntplcNo().replaceAll("&", "<![CDATA[&]]>") + "</athnRqstcustCntplcNo>");
            sb.append("<rsdcrtIssuDate>" + vo.getRsdcrtIssuDate().replaceAll("&", "<![CDATA[&]]>") + "</rsdcrtIssuDate>");
            if("DRIVE".equals(vo.getNativeRlnamAthnEvdnPprCd())){
                //운전면허증번호
                sb.append("<lcnsNo>" + vo.getLcnsNo().replaceAll("&", "<![CDATA[&]]>") + "</lcnsNo>");
                sb.append("<lcnsRgnCd>" + vo.getLcnsRgnCd().replaceAll("&", "<![CDATA[&]]>") + "</lcnsRgnCd>");
            }
            if("MERIT".equals(vo.getNativeRlnamAthnEvdnPprCd())){
                //유공자번호
                sb.append("<mrtrPrsnNo>" + vo.getMrtrPrsnNo().replaceAll("&", "<![CDATA[&]]>") + "</mrtrPrsnNo>");
            }
            if(vo.getNationalityCd() != null && !"".equals(vo.getNationalityCd())){
                //외국인
                sb.append("<nationalityCd>" + vo.getNationalityCd().replaceAll("&", "<![CDATA[&]]>") + "</nationalityCd>");
//				sb.append("<fornBrthDate>" + vo.getFornBrthDate() + "</fornBrthDate>");
            }

            sb.append("<crdtInfoAgreYn>" + vo.getCrdtInfoAgreYn().replaceAll("&", "<![CDATA[&]]>") + "</crdtInfoAgreYn>");
            sb.append("<indvInfoInerPrcuseAgreYn>" + vo.getIndvInfoInerPrcuseAgreYn().replaceAll("&", "<![CDATA[&]]>") + "</indvInfoInerPrcuseAgreYn>");
            sb.append("<cnsgInfoAdvrRcvAgreYn>" + vo.getCnsgInfoAdvrRcvAgreYn().replaceAll("&", "<![CDATA[&]]>") + "</cnsgInfoAdvrRcvAgreYn>");
            sb.append("<othcmpInfoAdvrRcvAgreYn>" + vo.getOthcmpInfoAdvrRcvAgreYn().replaceAll("&", "<![CDATA[&]]>") + "</othcmpInfoAdvrRcvAgreYn>");
            sb.append("<othcmpInfoAdvrCnsgAgreYn>" + vo.getOthcmpInfoAdvrCnsgAgreYn().replaceAll("&", "<![CDATA[&]]>")+ "</othcmpInfoAdvrCnsgAgreYn>");
            sb.append("<grpAgntBindSvcSbscAgreYn>" + vo.getGrpAgntBindSvcSbscAgreYn().replaceAll("&", "<![CDATA[&]]>") + "</grpAgntBindSvcSbscAgreYn>");
            sb.append("<cardInsrPrdcAgreYn>" + vo.getCardInsrPrdcAgreYn().replaceAll("&", "<![CDATA[&]]>") + "</cardInsrPrdcAgreYn>");
            sb.append("<olngDscnHynmtrAgreYn>" + vo.getOlngDscnHynmtrAgreYn().replaceAll("&", "<![CDATA[&]]>") + "</olngDscnHynmtrAgreYn>");
            sb.append("<wlfrDscnAplyAgreYn>" + vo.getWlfrDscnAplyAgreYn().replaceAll("&", "<![CDATA[&]]>") + "</wlfrDscnAplyAgreYn>");
            sb.append("<spamPrvdAgreYn>" + vo.getSpamPrvdAgreYn().replaceAll("&", "<![CDATA[&]]>") + "</spamPrvdAgreYn>");
            sb.append("<prttlpStlmUseAgreYn>" + vo.getPrttlpStlmUseAgreYn().replaceAll("&", "<![CDATA[&]]>") + "</prttlpStlmUseAgreYn>");
            sb.append("<prttlpStlmPwdUseAgreYn>" + vo.getPrttlpStlmPwdUseAgreYn().replaceAll("&", "<![CDATA[&]]>") + "</prttlpStlmPwdUseAgreYn>");
            sb.append("<wrlnTlphNo>" + vo.getWrlnTlphNo().replaceAll("&", "<![CDATA[&]]>") + "</wrlnTlphNo>");
//			sb.append("<tlphNo>" + vo.getTlphNo() + "</tlphNo>");
            if(vo.getZipNo() != null && !"".equals(vo.getZipNo())){
                sb.append("<zipNo>" + vo.getZipNo().replaceAll("&", "<![CDATA[&]]>") + "</zipNo>");
                sb.append("<fndtCntplcSbst>" + vo.getFndtCntplcSbst().replaceAll("&", "<![CDATA[&]]>") + "</fndtCntplcSbst>");
                sb.append("<mntCntplcSbst>" + vo.getMntCntplcSbst().replaceAll("&", "<![CDATA[&]]>") + "</mntCntplcSbst>");
            }
//			sb.append("<brthDate>" + vo.getBrthDate() + "</brthDate>");
//			sb.append("<brthNnpIndCd>" + vo.getBrthNnpIndCd() + "</brthNnpIndCd>");
//			sb.append("<jobCd>" + vo.getJobCd() + "</jobCd>");
//			sb.append("<emlAdrsNm>" + vo.getEmlAdrsNm() + "</emlAdrsNm>");
            sb.append("<custInfoChngYn>" + vo.getCustInfoChngYn().replaceAll("&", "<![CDATA[&]]>") + "</custInfoChngYn>");
            sb.append("<m2mHndsetYn>" + vo.getM2mHndsetYn().replaceAll("&", "<![CDATA[&]]>") + "</m2mHndsetYn>");
            sb.append("<fnncDealAgreeYn>" + vo.getFnncDealAgreeYn().replaceAll("&", "<![CDATA[&]]>") + "</fnncDealAgreeYn>");
            if(vo.getFathTransacId() != null && !"".equals(vo.getFathTransacId())) {
                sb.append("<fathTransacId>" + vo.getFathTransacId().replaceAll("&", "<![CDATA[&]]>") + "</fathTransacId>");
            }
            sb.append("<cpntId>" + vo.getCpntId().replaceAll("&", "<![CDATA[&]]>") + "</cpntId>");
            sb.append(this.getXmlElementIfNotEmpty("indvLoInfoPrvAgreeYn", vo.getIndvLoInfoPrvAgreeYn()));

            //법인고객인 경우
            if(!"I".equals(vo.getCustTypeCd())){
                sb.append("<crprNo>" + vo.getCrprNo().replaceAll("&", "<![CDATA[&]]>") + "</crprNo>");
                sb.append("<upjnCd>" + vo.getUpjnCd().replaceAll("&", "<![CDATA[&]]>") + "</upjnCd>");
                sb.append("<bcuSbst>" + vo.getBcuSbst().replaceAll("&", "<![CDATA[&]]>") + "</bcuSbst>");
//				sb.append("<rprsPrsnNm>" + vo.getRprsPrsnNm() + "</rprsPrsnNm>");
//				sb.append("<lstdIndCd>" + vo.getLstdIndCd() + "</lstdIndCd>");
//				sb.append("<emplCnt>" + vo.getEmplCnt() + "</emplCnt>");
//				sb.append("<slngAmt>" + vo.getSlngAmt() + "</slngAmt>");
//				sb.append("<cptlAmnt>" + vo.getCptlAmnt() + "</cptlAmnt>");
//				sb.append("<crprUpjnCd>" + vo.getCrprUpjnCd() + "</crprUpjnCd>");
//				sb.append("<crprBcuSbst>" + vo.getCrprBcuSbst() + "</crprBcuSbst>");
//				sb.append("<crprZipNo>" + vo.getCrprZipNo() + "</crprZipNo>");
//				sb.append("<crprFndtCntplcSbst>" + vo.getCrprFndtCntplcSbst() + "</crprFndtCntplcSbst>");
//				sb.append("<crprMntCntplcSbst>" + vo.getCrprMntCntplcSbst() + "</crprMntCntplcSbst>");
            }

            // =============== START: PC0 연동 파라미터 추가 2024.03.26 ===============
            if("PC0".equals(appEventCd)){

                String myslfAthnYn= "N";

                if(!StringUtil.isEmpty(vo.getIpinCi())){
                    myslfAthnYn= "Y";

                    sb.append("<myslfAthnYn>" + myslfAthnYn + "</myslfAthnYn>");
                    sb.append("<ipinCi>" + vo.getIpinCi().replaceAll("&", "<![CDATA[&]]>") + "</ipinCi>");
                    sb.append("<onlineAthnDivCd>" + vo.getOnlineAthnDivCd().replaceAll("&", "<![CDATA[&]]>") + "</onlineAthnDivCd>");

                }else{
                    sb.append("<myslfAthnYn>" + myslfAthnYn + "</myslfAthnYn>");
                }

            }
            // =============== END: PC0 연동 파라미터 추가 2024.03.26 ===============

        }catch(Exception e){
            logger.error("SimpleOpenServiceCall::" + vo.getAppEventCd() + " ERROR=======================================");
            logger.error("SimpleOpenServiceCall::PC0 = " + sb.toString());
            logger.error("SimpleOpenServiceCall::" + e.getMessage());
            logger.error("SimpleOpenServiceCall::" + vo.getAppEventCd() + " ERROR=======================================");
        }

        return sb.toString();
    }

    /**
     * 사전체크 번호이동 추가
     * @param vo
     * @return
     */
    public String xmlMessagePC0NP(MPlatformResVO vo){
        StringBuffer sb = new StringBuffer();

        try{
            sb.append("<inNpDto>");
            sb.append("<oderTypeCd>" + vo.getOrderTypeCd().replaceAll("&", "<![CDATA[&]]>") + "</oderTypeCd>");
            sb.append("<npTlphNo>" + vo.getNpTlphNo().replaceAll("&", "<![CDATA[&]]>") + "</npTlphNo>");
            sb.append("<indvBizrYn>" + vo.getIndvBizrYn().replaceAll("&", "<![CDATA[&]]>") + "</indvBizrYn>");
            sb.append("<bchngNpCommCmpnCd>" + vo.getBchngNpCommCmpnCd().replaceAll("&", "<![CDATA[&]]>") + "</bchngNpCommCmpnCd>");
            sb.append("<athnItemCd>" + vo.getAthnItemCd().replaceAll("&", "<![CDATA[&]]>") + "</athnItemCd>");
            sb.append("<athnSbst>" + vo.getAthnSbst().replaceAll("&", "<![CDATA[&]]>") + "</athnSbst>");
            sb.append("<npRstrtnContYn>" + vo.getNpRstrtnContYn().replaceAll("&", "<![CDATA[&]]>") + "</npRstrtnContYn>");
            sb.append("<ytrpaySoffAgreYn>" + vo.getYtrpaySoffAgreYn().replaceAll("&", "<![CDATA[&]]>") + "</ytrpaySoffAgreYn>");
            sb.append("</inNpDto>");

        }catch(Exception e){
            logger.error("SimpleOpenServiceCall::PC0NP = " + sb.toString());
            logger.error("SimpleOpenServiceCall::" + e.getMessage());
        }

        return sb.toString();
    }

    /**
     * 번호조회 xml 본문 생성
     * @param reqMap
     * @return
     */
    public String xmlMessageNU1(MPlatformResVO vo){
        StringBuffer sb = new StringBuffer();

        try{
            sb.append("<inqrSvcNoInfoInDTO>");
            sb.append("<asgnAgncId>" + vo.getAsgnAgncId().replaceAll("&", "<![CDATA[&]]>") + "</asgnAgncId>");
            sb.append("<asgnAgncYn>" + vo.getAsgnAgncYn().replaceAll("&", "<![CDATA[&]]>") + "</asgnAgncYn>");
            sb.append("<cntryCd>" + vo.getCntryCd().replaceAll("&", "<![CDATA[&]]>") + "</cntryCd>");
            sb.append("<custNo>" + vo.getCustNo().replaceAll("&", "<![CDATA[&]]>") + "</custNo>");
            sb.append("<inqrBase>" + vo.getInqrBase().replaceAll("&", "<![CDATA[&]]>") + "</inqrBase>");
            sb.append("<inqrCascnt>" + vo.getInqrCascnt().replaceAll("&", "<![CDATA[&]]>") + "</inqrCascnt>");
            sb.append("<nowSvcIndCd>" + vo.getNowSvcIndCd().replaceAll("&", "<![CDATA[&]]>") + "</nowSvcIndCd>");
            sb.append("<searchGubun>" + vo.getSearchGubun().replaceAll("&", "<![CDATA[&]]>") + "</searchGubun>");
            //		sb.append("<arPrGubun>" + vo.getArPrGubun() + "</arPrGubun>");
            sb.append("<custIdntNo></custIdntNo>");
            sb.append("<custIdntNoIndCd></custIdntNoIndCd>");
            sb.append("<tlphNoChrcCd>" + vo.getTlphNoChrcCd().replaceAll("&", "<![CDATA[&]]>") + "</tlphNoChrcCd>");
            sb.append("<tlphNoIndCd>" + vo.getTlphNoIndCd().replaceAll("&", "<![CDATA[&]]>") + "</tlphNoIndCd>");
            sb.append("<tlphNoPtrn>" + vo.getTlphNoPtrn().replaceAll("&", "<![CDATA[&]]>") + "</tlphNoPtrn>");
            sb.append("<tlphNoUseCd>" + vo.getTlphNoUseCd().replaceAll("&", "<![CDATA[&]]>") + "</tlphNoUseCd>");
            if(vo.getTlphNoUseMntCd() != null && !"".equals(vo.getTlphNoUseMntCd())){
                sb.append("<tlphNoUseMntCd>" + vo.getTlphNoUseMntCd() + "</tlphNoUseMntCd>");
            }
            sb.append("</inqrSvcNoInfoInDTO>");
            sb.append("<osstOrdNo>" + vo.getOsstOrdNo().replaceAll("&", "<![CDATA[&]]>") + "</osstOrdNo>");

        }catch(Exception e){
            logger.error("SimpleOpenServiceCall::" + vo.getAppEventCd() + " ERROR=======================================");
            logger.error("SimpleOpenServiceCall::NU1 = " + sb.toString());
            logger.error("SimpleOpenServiceCall::" + e.getMessage());
            logger.error("SimpleOpenServiceCall::" + vo.getAppEventCd() + " ERROR=======================================");
        }

        return sb.toString();
    }

    /**
     * 번호예약 xml 본문 생성
     * @param reqMap
     * @return
     */
    public String xmlMessageNU2(MPlatformResVO vo){
        StringBuffer sb = new StringBuffer();

        try{
            sb.append("<osstOrdNo>" + vo.getOsstOrdNo().replaceAll("&", "<![CDATA[&]]>") + "</osstOrdNo>");
            sb.append("<resvTlphNoInDTO>");
            sb.append("<gubun>" + vo.getGubun().replaceAll("&", "<![CDATA[&]]>") + "</gubun>");
            sb.append("<tlphNo>" + vo.getTlphNo().replaceAll("&", "<![CDATA[&]]>") + "</tlphNo>");
            sb.append("<custNo>" + vo.getCustNo().replaceAll("&", "<![CDATA[&]]>") + "</custNo>");
            sb.append("<custIdntNoIndCd></custIdntNoIndCd>");
            sb.append("<custIdntNo></custIdntNo>");
            sb.append("<tlphNoStatChngRsnCd>" + vo.getTlphNoStatChngRsnCd().replaceAll("&", "<![CDATA[&]]>") + "</tlphNoStatChngRsnCd>");
            sb.append("<tlphNoStatCd>" + vo.getTlphNoStatCd().replaceAll("&", "<![CDATA[&]]>") + "</tlphNoStatCd>");
            sb.append("<custTypeCd>" + vo.getCustTypeCd().replaceAll("&", "<![CDATA[&]]>") + "</custTypeCd>");
            sb.append("<nowSvcIndCd>" + vo.getNowSvcIndCd().replaceAll("&", "<![CDATA[&]]>") + "</nowSvcIndCd>");
            if(vo.getMnpIndCd() != null && !"".equals(vo.getMnpIndCd())){
                sb.append("<mnpIndCd>" + vo.getMnpIndCd().replaceAll("&", "<![CDATA[&]]>") + "</mnpIndCd>");
            }
            sb.append("<mnpMngmNo></mnpMngmNo>");
            sb.append("<encdTlphNo>" + vo.getEncdTlphNo().replaceAll("&", "<![CDATA[&]]>") + "</encdTlphNo>");
            sb.append("<mpngTlphNoYn></mpngTlphNoYn>");
            sb.append("<asgnAgncId>" + vo.getAsgnAgncId().replaceAll("&", "<![CDATA[&]]>") + "</asgnAgncId>");
            sb.append("</resvTlphNoInDTO>");

        }catch(Exception e){
            logger.error("SimpleOpenServiceCall::" + vo.getAppEventCd() + " ERROR=======================================");
            logger.error("SimpleOpenServiceCall::NU2 = " + sb.toString());
            logger.error("SimpleOpenServiceCall::" + e.getMessage());
            logger.error("SimpleOpenServiceCall::" + vo.getAppEventCd() + " ERROR=======================================");
        }

        return sb.toString();
    }

    /**
     * 개통수납 xml 본문 생성
     * @param reqMap
     * @return
     */
    public String xmlMessageOP0(MPlatformResVO vo){
        StringBuffer sb = new StringBuffer();


        try{
            sb.append("<osstOrdNo>" + vo.getOsstOrdNo().replaceAll("&", "<![CDATA[&]]>") + "</osstOrdNo>");
            sb.append("<custNo>" + vo.getCustNo().replaceAll("&", "<![CDATA[&]]>") + "</custNo>");

            //sb.append("<billAcntNo>" + vo.getBillAcntNo() + "</billAcntNo>");

            //청구서양식코드 값이 없으면 전달 하지 않음
            if(vo.getRqsshtPprfrmCd() != null && !"".equals(vo.getRqsshtPprfrmCd())){
                sb.append("<rqsshtPprfrmCd>" + vo.getRqsshtPprfrmCd().replaceAll("&", "<![CDATA[&]]>") + "</rqsshtPprfrmCd>");
            }


            // 문자청구서인 경우 수신번호 필수
            if("MB".equals(vo.getRqsshtPprfrmCd())){
                sb.append("<rqsshtTlphNo>" + vo.getRqsshtTlphNo().replaceAll("&", "<![CDATA[&]]>") + "</rqsshtTlphNo>");
            }
            if(vo.getRqsshtEmlAdrsNm() != null && !"".equals(vo.getRqsshtEmlAdrsNm())){
                sb.append("<rqsshtEmlAdrsNm>" + vo.getRqsshtEmlAdrsNm().replaceAll("&", "<![CDATA[&]]>") + "</rqsshtEmlAdrsNm>");
            }
            sb.append("<billZipNo>" + vo.getBillZipNo() + "</billZipNo>");
            sb.append("<billFndtCntplcSbst>" + vo.getBillFndtCntplcSbst().replaceAll("&", "<![CDATA[&]]>") + "</billFndtCntplcSbst>");
            sb.append("<billMntCntplcSbst>" + vo.getBillMntCntplcSbst().replaceAll("&", "<![CDATA[&]]>") + "</billMntCntplcSbst>");
            sb.append("<blpymMthdCd>" + vo.getBlpymMthdCd().replaceAll("&", "<![CDATA[&]]>") + "</blpymMthdCd>");
            sb.append("<duedatDateIndCd>" + vo.getDuedatDateIndCd().replaceAll("&", "<![CDATA[&]]>") + "</duedatDateIndCd>");

            if("C".equals(vo.getBlpymMthdCd())){
                sb.append("<crdtCardExprDate>" + vo.getCrdtCardExprDate().replaceAll("&", "<![CDATA[&]]>") + "</crdtCardExprDate>");
                sb.append("<crdtCardKindCd>" + vo.getCrdtCardKindCd().replaceAll("&", "<![CDATA[&]]>") + "</crdtCardKindCd>");
            }

            if("D".equals(vo.getBlpymMthdCd())){
                sb.append("<bankCd>" + vo.getBankCd().replaceAll("&", "<![CDATA[&]]>") + "</bankCd>");
            }

            sb.append("<blpymMthdIdntNo>" + vo.getBlpymMthdIdntNo().replaceAll("&", "<![CDATA[&]]>") + "</blpymMthdIdntNo>");

            // 타인납부를 위한 처리
            if(vo.getBlpymCustNm() != null && !"".equals(vo.getBlpymCustNm())){
                sb.append("<blpymCustNm>" + vo.getBlpymCustNm().replaceAll("&", "<![CDATA[&]]>") + "</blpymCustNm>");
                sb.append("<blpymCustIdntNo>" + vo.getBlpymCustIdntNo().replaceAll("&", "<![CDATA[&]]>") + "</blpymCustIdntNo>");
            }

            if(!"R".equals(vo.getBlpymMthdCd())){
                sb.append("<blpymMthdIdntNoHideYn>" + vo.getBlpymMthdIdntNoHideYn().replaceAll("&", "<![CDATA[&]]>") + "</blpymMthdIdntNoHideYn>");
            }
//			sb.append("<bankSkipYn>" + vo.getBankSkipYn() + "</bankSkipYn>");
            sb.append("<agreIndCd>" + vo.getAgreIndCd() + "</agreIndCd>");

            if(StringUtil.isNotEmpty(vo.getMyslAthnTypeCd()) && !"".equals(vo.getMyslAthnTypeCd())){
                sb.append("<myslAthnTypeCd>" + vo.getMyslAthnTypeCd().replaceAll("&", "<![CDATA[&]]>") + "</myslAthnTypeCd>");
            }
//
            sb.append("<billAtchExclYn>" + vo.getBillAtchExclYn() + "</billAtchExclYn>");

            // 청구서전화번호숨김여부
            sb.append("<rqsshtTlphNoHideYn>" + vo.getRqsshtTlphNoHideYn() + "</rqsshtTlphNoHideYn>");
//			sb.append("<rqsshtDsptYn>" + vo.getRqsshtDsptYn() + "</rqsshtDsptYn>");
//			sb.append("<enclBillTrmnYn>" + vo.getEnclBillTrmnYn() + "</enclBillTrmnYn>");
            if(StringUtil.isNotEmpty(vo.getRealUseCustNm()) && !"".equals(vo.getRealUseCustNm())){
                sb.append("<realUseCustNm>" + vo.getRealUseCustNm().replaceAll("&", "<![CDATA[&]]>") + "</realUseCustNm>");
            }

            sb.append("<mngmAgncId>" + vo.getMngmAgncId().replaceAll("&", "<![CDATA[&]]>") + "</mngmAgncId>");
            sb.append("<cntpntCd>" + vo.getCntpntCd().replaceAll("&", "<![CDATA[&]]>") + "</cntpntCd>");
//			sb.append("<slsPrsnId>" + vo.getSlsPrsnId() + "</slsPrsnId>");


            /*
            iccId       , A.REQ_USIM_SN AS iccId
            eUiccId <====  EID
            intmMdlId  , D.MODEL_ID AS intmMdlId  A.ESIM_PHONE_ID                                  기기모델ID
            intmSrlNo   , A.REQ_PHONE_SN AS intmSrlNo                              기기일련번호
            eSimOpenYn  <=== Y
            usimOpenYn     (CASE WHEN A.PROD_TYPE = '02' OR A.REQ_BUY_TYPE = 'UU' THEN 'Y' ELSE 'N' END) AS usimOpenYn    유심개통여부
            motliSvcNo  <====#####모회선전화번호	11	O	애플워치 개통시 모회선 연결할 경우 모회선 전화번호. (원넘버부가 자동가입) ????


            usimPymnMthdCd	USIM 수납방법코드	1	M	"R:즉납, B:후청구, N:비구매
            *코드정의서 참조(수납방법코드)
            *eSIM개통시 즉납불가"
            <=== DECODE(D.USIM_PAY_MTHD_CD, '1', 'N', '2', 'R', '3', 'B') AS usimPymnMthdCd        USIM수납방법
            */

            // eSIM
            if ("09".equals(vo.getUsimKindsCd()) ) {
                sb.append("<iccId></iccId>");
                sb.append("<eUiccId>"+ vo.getEsimUiccId() +"</eUiccId>");
                sb.append("<intmMdlId>" + vo.getEsimPhoneId() + "</intmMdlId>");
                sb.append("<intmSrlNo>" + vo.getIntmSrlNo() + "</intmSrlNo>");
                sb.append("<eSimOpenYn>Y</eSimOpenYn>");
                sb.append("<usimOpenYn>N</usimOpenYn>");
                if(vo.getPrntsCtn() != null && !"".equals(vo.getPrntsCtn())){
                    sb.append("<motliSvcNo>" + vo.getPrntsCtn() + "</motliSvcNo>");
                }
            } else {
                sb.append("<iccId>" + vo.getIccId().replaceAll("&", "<![CDATA[&]]>") + "</iccId>");
                sb.append("<eSimOpenYn>N</eSimOpenYn>");
                if(!"Y".equals(vo.getUsimOpenYn())){
                    sb.append("<intmMdlId>" + vo.getIntmMdlId().replaceAll("&", "<![CDATA[&]]>") + "</intmMdlId>");
                    sb.append("<intmSrlNo>" + vo.getIntmSrlNo().replaceAll("&", "<![CDATA[&]]>") + "</intmSrlNo>");
                }
                sb.append("<usimOpenYn>" + vo.getUsimOpenYn().replaceAll("&", "<![CDATA[&]]>") + "</usimOpenYn>");
            }

            sb.append("<spclSlsNo>" + vo.getSpclSlsNo().replaceAll("&", "<![CDATA[&]]>") + "</spclSlsNo>");
            if(StringUtil.isNotEmpty(vo.getAgntRltnCd()) && !"".equals(vo.getAgntRltnCd())){
                sb.append("<agntRltnCd>" + vo.getAgntRltnCd().replaceAll("&", "<![CDATA[&]]>") + "</agntRltnCd>");
                sb.append("<agntBrthDate>" + vo.getAgntBrthDate().replaceAll("&", "<![CDATA[&]]>") + "</agntBrthDate>");
                sb.append("<agntCustNm>" + vo.getAgntCustNm().replaceAll("&", "<![CDATA[&]]>") + "</agntCustNm>");
                sb.append("<homeTlphNo>" + vo.getHomeTlphNo().replaceAll("&", "<![CDATA[&]]>") + "</homeTlphNo>");
            }

            //			sb.append("<wrkplcTlphNo>" + vo.getWrkplcTlphNo() + "</wrkplcTlphNo>");
//			sb.append("<prttlpNo>" + vo.getPrttlpNo() + "</prttlpNo>");
            if(StringUtil.isNotEmpty(vo.getSpnsDscnTypeCd()) && !"".equals(vo.getSpnsDscnTypeCd())){
                sb.append("<spnsDscnTypeCd>" + vo.getSpnsDscnTypeCd().replaceAll("&", "<![CDATA[&]]>") + "</spnsDscnTypeCd>");
            }

            // 대리점보조금
            if(!"0".equals(vo.getAgncSupotAmnt())){
                sb.append("<agncSupotAmnt>" + vo.getAgncSupotAmnt().replaceAll("&", "<![CDATA[&]]>") + "</agncSupotAmnt>");
            }else{
                sb.append("<agncSupotAmnt></agncSupotAmnt>");
            }

            // 약정개월
            if(!"0".equals(vo.getEnggMnthCnt())){
                sb.append("<enggMnthCnt>" + vo.getEnggMnthCnt().replaceAll("&", "<![CDATA[&]]>") + "</enggMnthCnt>");
            }else{
                sb.append("<enggMnthCnt></enggMnthCnt>");
            }

            // 할부개월
            if(!"0".equals(vo.getInstMnthCnt())){
                sb.append("<hndsetInstAmnt>" + vo.getHndsetInstAmnt().replaceAll("&", "<![CDATA[&]]>") + "</hndsetInstAmnt>");
                sb.append("<hndsetPrpyAmnt>" + vo.getHndsetPrpyAmnt().replaceAll("&", "<![CDATA[&]]>") + "</hndsetPrpyAmnt>");
                sb.append("<instMnthCnt>" + vo.getInstMnthCnt().replaceAll("&", "<![CDATA[&]]>") + "</instMnthCnt>");
            }else{
                sb.append("<instMnthCnt></instMnthCnt>");
            }

            // 유심비납부방법
            sb.append("<usimPymnMthdCd>" + vo.getUsimPymnMthdCd().replaceAll("&", "<![CDATA[&]]>") + "</usimPymnMthdCd>");
            sb.append("<sbscstPymnMthdCd>" + vo.getSbscstPymnMthdCd().replaceAll("&", "<![CDATA[&]]>") + "</sbscstPymnMthdCd>");

            if("P".equals(vo.getSbscstPymnMthdCd())){
                sb.append("<sbscstImpsExmpRsnCd>" + vo.getSbscstImpsExmpRsnCd().replaceAll("&", "<![CDATA[&]]>") + "</sbscstImpsExmpRsnCd>");
            }

            sb.append("<bondPrsrFeePymnMthdCd>" + vo.getBondPrsrFeePymnMthdCd().replaceAll("&", "<![CDATA[&]]>") + "</bondPrsrFeePymnMthdCd>");
            sb.append("<tlphNo>" + vo.getTlphNo() + "</tlphNo>");
            sb.append("<sbscPrtlstRcvEmlAdrsNm>" + vo.getSbscPrtlstRcvEmlAdrsNm().replaceAll("&", "<![CDATA[&]]>") + "</sbscPrtlstRcvEmlAdrsNm>");

        }catch(Exception e){
            logger.error("SimpleOpenServiceCall::" + vo.getAppEventCd() + " ERROR=======================================");
            logger.error("SimpleOpenServiceCall::OP0 = " + sb.toString());
            logger.error("SimpleOpenServiceCall::" + e.getMessage());
            logger.error("SimpleOpenServiceCall::" + vo.getAppEventCd() + " ERROR=======================================");
        }


        return sb.toString();
    }

    /**
     * 개통전문 번호이동 추가
     * @param vo
     * @return
     */
    public String xmlMessageOP0NP(MPlatformResVO vo){
        StringBuffer sb = new StringBuffer();

        try{
            sb.append("<inNpDto>");
            sb.append("<npFeePayMethCd>" + vo.getNpFeePayMethCd().replaceAll("&", "<![CDATA[&]]>") + "</npFeePayMethCd>");
            sb.append("<npBillMethCd>" + vo.getNpBillMethCd().replaceAll("&", "<![CDATA[&]]>") + "</npBillMethCd>");
            sb.append("<npHndsetInstaDuratDivCd>" + vo.getNpHndsetInstaDuratDivCd().replaceAll("&", "<![CDATA[&]]>") + "</npHndsetInstaDuratDivCd>");
            // 2018-05-29, 단말기할부금분납지속구분코드가 완납("1")인 경우 번호이동수납코드 필수
            if("1".equals(vo.getNpHndsetInstaDuratDivCd())){
                sb.append("<npRmnyMethCd>" + vo.getNpRmnyMethCd().replaceAll("&", "<![CDATA[&]]>") + "</npRmnyMethCd>");
            }
            // 2015-05-29, 단말기할부금분납지속구분코드가 분납지속("3")인 경우만 청구수신전화번호 입력
            if("3".equals(vo.getNpHndsetInstaDuratDivCd()) && (vo.getNpHndsetInstaLmsTlphNo() != null && !"".equals(vo.getNpHndsetInstaLmsTlphNo()))){
                sb.append("<npHndsetInstaLmsTlphNo>" + vo.getNpHndsetInstaLmsTlphNo().replaceAll("&", "<![CDATA[&]]>") + "</npHndsetInstaLmsTlphNo>");
            }
            sb.append("<npTotRmnyAmt>" + vo.getNpTotRmnyAmt().replaceAll("&", "<![CDATA[&]]>") + "</npTotRmnyAmt>");
            sb.append("<npCashRmnyAmt>" + vo.getNpCashRmnyAmt().replaceAll("&", "<![CDATA[&]]>") + "</npCashRmnyAmt>");
            sb.append("<npCcardRmnyAmt>" + vo.getNpCcardRmnyAmt().replaceAll("&", "<![CDATA[&]]>") + "</npCcardRmnyAmt>");
//			sb.append("<rfundNpBankCd>" + vo.getRfundNpBankCd() + "</rfundNpBankCd>");
//			sb.append("<rfundBankBnkacnNo>" + vo.getRfundBankBnkacnNo() + "</rfundBankBnkacnNo>");
//			sb.append("<npCcardNo>" + vo.getNpCcardNo() + "</npCcardNo>");
//			sb.append("<npCcardExpirYm>" + vo.getNpCcardExpirYm() + "</npCcardExpirYm>");
//			sb.append("<npInslMonsNum>" + vo.getNpInslMonsNum() + "</npInslMonsNum>");
//			sb.append("<npCcardSnctTypeCd>" + vo.getNpCcardSnctTypeCd() + "</npCcardSnctTypeCd>");
//			sb.append("<npCcardOwnrIdfyNo>" + vo.getNpCcardOwnrIdfyNo() + "</npCcaardOwnrIdfyNo>");
//			sb.append("<npSgntInfo>" + vo.getNpSgntInfo() + "</npSgntInfo>");
            sb.append("</inNpDto>");

        }catch(Exception e){
            logger.error("SimpleOpenServiceCall::" + vo.getAppEventCd() + " ERROR=======================================");
            logger.error("SimpleOpenServiceCall::OP0NP = " + sb.toString());
            logger.error("SimpleOpenServiceCall::" + e.getMessage());
            logger.error("SimpleOpenServiceCall::" + vo.getAppEventCd() + " ERROR=======================================");
        }

        return sb.toString();
    }

    /**
     * 상태체크 xml 본문 생성
     * @param vo
     * @return
     */
    public String xmlMessageST1(MPlatformResVO vo){
        StringBuffer sb = new StringBuffer();
        sb.append("<osstOrdNo>" + vo.getOsstOrdNo() + "</osstOrdNo>");

        //logger.error("SimpleOpenServiceCall::ST1 = " + sb.toString());
        return sb.toString();
    }

    /**
     * 개통처리인 경우 상품 xml 본문 생성
     * @param reqMap
     * @return
     */
    public String xmlMessageProd(String resNo){
        StringBuffer sb = new StringBuffer();
        RestTemplate restTemplate = new RestTemplate();
        String apiInterfaceServer = propertiesService.getString("api.interface.server");

        try{
            //상품목록조회
            //List<MPlatformResVO> list = osstMapper.getXmlMessageOP0Prod(resNo);
            MPlatformResVO[] resultList = restTemplate.postForObject(apiInterfaceServer + "/mPlatform/getXmlMessageOP0Prod", resNo, MPlatformResVO[].class);
            List<MPlatformResVO> list = Arrays.asList(resultList);

            for(int i=0; i<list.size(); i++){
                MPlatformResVO vo = list.get(i);

                sb.append("<inPrdcDto>");
                sb.append("<prdcCd>" + vo.getPrdcCd().replaceAll("&", "<![CDATA[&]]>") + "</prdcCd>");
                sb.append("<prdcTypecd>" + vo.getPrdcTypeCd().replaceAll("&", "<![CDATA[&]]>") +"</prdcTypecd>");

                if ("PL249Q800".equals(vo.getPrdcCd())) {
                    /**
                     * 테스트 대상 부가서비스
                     *  - 상품코드 : PL249Q800  MVNO마스터결합전용 더미부가서비스(엠모바일)
                     * - 상품타입코드 : R (부가서비스)
                     * - 상품 파람 : 587838774
                     * ※ CTN : 01027435432
                     */
                    sb.append("<ftrNewParam>587838774</ftrNewParam>");
                }

                sb.append("</inPrdcDto>");

                // 부가서비스 PROC_YN UPDATE
                if("R".equals(vo.getPrdcTypeCd())){
                    //osstMapper.updateRequestAddition(vo);
                    restTemplate.postForObject(apiInterfaceServer + "/mPlatform/updateRequestAddition", vo, Void.class);

                }
            }

            logger.error("SimpleOpenServiceCall::PROD LIST=" + sb.toString());
        }catch(Exception e){
            logger.error("SimpleOpenServiceCall::" + e.getMessage());
        }

        return sb.toString();
    }

    /**
     * 번호이동사전인증 xml 본문 생성
     * @param reqMap
     * @return
     */
    public String xmlMessageNP1(MPlatformResVO vo){
        StringBuffer sb = new StringBuffer();

        try{
            sb.append("<npTlphNo>" + vo.getNpTlphNo().replaceAll("&", "<![CDATA[&]]>") + "</npTlphNo>");
            sb.append("<bchngNpCommCmpnCd>" + vo.getBchngNpCommCmpnCd().replaceAll("&", "<![CDATA[&]]>") + "</bchngNpCommCmpnCd>");
            sb.append("<slsCmpnCd>" + vo.getSlsCmpnCd().replaceAll("&", "<![CDATA[&]]>") + "</slsCmpnCd>");
            sb.append("<custTypeCd>" + vo.getCustTypeCd().replaceAll("&", "<![CDATA[&]]>") + "</custTypeCd>");
            sb.append("<indvBizrYn>" + vo.getIndvBizrYn().replaceAll("&", "<![CDATA[&]]>") + "</indvBizrYn>");
            sb.append("<custIdntNoIndCd>" + vo.getCustIdntNoIndCd().replaceAll("&", "<![CDATA[&]]>") + "</custIdntNoIndCd>");
            sb.append("<custIdntNo>" + vo.getCustIdntNo().replaceAll("&", "<![CDATA[&]]>") + "</custIdntNo>");
            if(vo.getCrprNo() != null && !"".equals(vo.getCrprNo())){
                sb.append("<crprNo>" + vo.getCrprNo().replaceAll("&", "<![CDATA[&]]>") + "</crprNo>");
            }
            sb.append("<custNm>" + vo.getCustNm().replaceAll("&", "<![CDATA[&]]>") + "</custNm>");

            logger.error("SimpleOpenServiceCall::NP1 = " + sb.toString());
        }catch(Exception e){
            logger.error("SimpleOpenServiceCall::" + vo.getAppEventCd() + " ERROR=======================================");
            logger.error("SimpleOpenServiceCall::" + e.getMessage());
            logger.error("SimpleOpenServiceCall::" + vo.getAppEventCd() + " ERROR=======================================");
        }

        return sb.toString();
    }


    /**
     * 번호이동사전인증 xml 본문 생성
     * @param reqMap
     * @return
     */
    public String xmlMessageNP3(MPlatformResVO vo){
        StringBuffer sb = new StringBuffer();

        try{
            sb.append("<telNo>" + vo.getNpTlphNo().replaceAll("&", "<![CDATA[&]]>") + "</telNo>");
            sb.append("<bchngNpCommCmpnCd>" + vo.getBchngNpCommCmpnCd().replaceAll("&", "<![CDATA[&]]>") + "</bchngNpCommCmpnCd>");
            logger.error("SimpleOpenServiceCall::NP1 = " + sb.toString());
        }catch(Exception e){
            logger.error("SimpleOpenServiceCall::" + vo.getAppEventCd() + " ERROR=======================================");
            logger.error("SimpleOpenServiceCall::" + e.getMessage());
            logger.error("SimpleOpenServiceCall::" + vo.getAppEventCd() + " ERROR=======================================");
        }

        return sb.toString();
    }

    /**
     * 유심변경 사전체크 및 유심 변경 처리.
     * @param reqMap
     * @return String
     */
    public String xmlMessageUC0(MPlatformResVO vo){
        StringBuffer sb = new StringBuffer();

        try{
            sb.append("<mvnoOrdNo>" + vo.getMvnoOrdNo().replaceAll("&", "<![CDATA[&]]>") + "</mvnoOrdNo>");
            sb.append("<custNo>" + vo.getCustNo().replaceAll("&", "<![CDATA[&]]>") + "</custNo>");
            sb.append("<tlphNo>" + vo.getTlphNo().replaceAll("&", "<![CDATA[&]]>") + "</tlphNo>");
            sb.append("<svcContId>" + vo.getSvcContId().replaceAll("&", "<![CDATA[&]]>") + "</svcContId>");
            sb.append("<oderTypeCd>" + vo.getOderTypeCd().replaceAll("&", "<![CDATA[&]]>") + "</oderTypeCd>");
            sb.append("<usimPymnMthdCd>" + vo.getUsimPymnMthdCd().replaceAll("&", "<![CDATA[&]]>") + "</usimPymnMthdCd>");

            if(StringUtils.isNotBlank(vo.getIccId())){
                sb.append("<iccId>" + vo.getIccId().replaceAll("&", "<![CDATA[&]]>") + "</iccId>");
            }

            sb.append("<cntpntCd>" + vo.getCntpntCd().replaceAll("&", "<![CDATA[&]]>") + "</cntpntCd>");
//            sb.append("<slsPrsnId>" + vo.getSlsPrsnId().replaceAll("&", "<![CDATA[&]]>") + "</slsPrsnId>");
            sb.append("<usimChgRsnCd>" + vo.getUsimChgRsnCd().replaceAll("&", "<![CDATA[&]]>") + "</usimChgRsnCd>");

            logger.error("SimpleOpenServiceCall::UC0 = " + sb.toString());
        }catch(Exception e){
            logger.error("SimpleOpenServiceCall:UC0:" + vo.getAppEventCd() + " ERROR=======================================");
            logger.error("SimpleOpenServiceCall:UC0:" + e.getMessage());
            logger.error("SimpleOpenServiceCall:UC0:" + vo.getAppEventCd() + " ERROR=======================================");
        }

        return sb.toString();
    }

    /**
     * 번호이동납부주장 xml 본문 생성
     * @param reqMap
     * @return
     */
    public String xmlMessageNP2(MPlatformResVO vo){
        StringBuffer sb = new StringBuffer();

        try{
            sb.append("<osstOrdNo>" + vo.getOsstOrdNo().replaceAll("&", "<![CDATA[&]]>") + "</osstOrdNo>");
            sb.append("<slsCmpnCd>" + vo.getSlsCmpnCd().replaceAll("&", "<![CDATA[&]]>") + "</slsCmpnCd>");
            sb.append("<npTlphNo>" + vo.getNpTlphNo().replaceAll("&", "<![CDATA[&]]>") + "</npTlphNo>");
            sb.append("<payAsertDt>" + vo.getPayAsertDt().replaceAll("&", "<![CDATA[&]]>") + "</payAsertDt>");
            sb.append("<payAsertAmt>" + vo.getPayAsertAmt().replaceAll("&", "<![CDATA[&]]>") + "</payAsertAmt>");
            sb.append("<payMethCd>" + vo.getPayMethCd().replaceAll("&", "<![CDATA[&]]>") + "</payMethCd>");

            logger.error("SimpleOpenServiceCall::NP2 = " + sb.toString());
        }catch(Exception e){
            logger.error("SimpleOpenServiceCall::" + vo.getAppEventCd() + " ERROR=======================================");
            logger.error("SimpleOpenServiceCall::" + e.getMessage());
            logger.error("SimpleOpenServiceCall::" + vo.getAppEventCd() + " ERROR=======================================");
        }

        return sb.toString();
    }

    /**
     * 직권해지 xml 본문 생성
     * @param reqMap
     * @return
     */
    public String xmlMessageCP0(MPlatformResVO vo){
        StringBuffer sb = new StringBuffer();

        sb.append("<ctn>" + vo.getCtn().replaceAll("&", "<![CDATA[&]]>") + "</ctn>");
        sb.append("<custId>" + vo.getCustId().replaceAll("&", "<![CDATA[&]]>") + "</custId>");
        sb.append("<ncn>" + vo.getNcn().replaceAll("&", "<![CDATA[&]]>") + "</ncn>");

        return sb.toString();
    }

    /**
     * 해지 xml 본문 생성
     * @param reqMap
     * @return
     */
    public String xmlMessageEP0(MPlatformResVO vo){
        StringBuffer sb = new StringBuffer();

        sb.append("<custId>" + vo.getCustId().replaceAll("&", "<![CDATA[&]]>") + "</custId>");
        sb.append("<ncn>" + vo.getNcn().replaceAll("&", "<![CDATA[&]]>") + "</ncn>");
        sb.append("<ctn>" + vo.getCtn().replaceAll("&", "<![CDATA[&]]>") + "</ctn>");
        sb.append("<cntplcNo>" + vo.getCntplcNo().replaceAll("&", "<![CDATA[&]]>") + "</cntplcNo>");
        sb.append("<itgOderWhyCd>" + vo.getItgOderWhyCd().replaceAll("&", "<![CDATA[&]]>") + "</itgOderWhyCd>");
        sb.append("<aftmnIncInCd>" + vo.getAftmnIncInCd().replaceAll("&", "<![CDATA[&]]>") + "</aftmnIncInCd>");
        sb.append("<apyRelTypeCd>" + vo.getApyRelTypeCd().replaceAll("&", "<![CDATA[&]]>") + "</apyRelTypeCd>");
        sb.append("<custTchMediCd>" + vo.getCustTchMediCd().replaceAll("&", "<![CDATA[&]]>") + "</custTchMediCd>");
        sb.append("<smsRcvYn>" + vo.getSmsRcvYn().replaceAll("&", "<![CDATA[&]]>") + "</smsRcvYn>");

        return sb.toString();
    }

    /**
     * M2M해지 xml 본문 생성
     * @param reqMap
     * @return
     */
    public String xmlMessageMP0(MPlatformResVO vo){
        StringBuffer sb = new StringBuffer();

        sb.append("<custId>" + vo.getCustId().replaceAll("&", "<![CDATA[&]]>") + "</custId>");
        sb.append("<ncn>" + vo.getNcn().replaceAll("&", "<![CDATA[&]]>") + "</ncn>");
        sb.append("<ctn>" + vo.getCtn().replaceAll("&", "<![CDATA[&]]>") + "</ctn>");
        sb.append("<cntplcNo>" + vo.getCntplcNo().replaceAll("&", "<![CDATA[&]]>") + "</cntplcNo>");
        sb.append("<itgOderWhyCd>" + vo.getItgOderWhyCd().replaceAll("&", "<![CDATA[&]]>") + "</itgOderWhyCd>");
        sb.append("<aftmnIncInCd>" + vo.getAftmnIncInCd().replaceAll("&", "<![CDATA[&]]>") + "</aftmnIncInCd>");
        sb.append("<apyRelTypeCd>" + vo.getApyRelTypeCd().replaceAll("&", "<![CDATA[&]]>") + "</apyRelTypeCd>");
        sb.append("<custTchMediCd>" + vo.getCustTchMediCd().replaceAll("&", "<![CDATA[&]]>") + "</custTchMediCd>");
        sb.append("<smsRcvYn>" + vo.getSmsRcvYn().replaceAll("&", "<![CDATA[&]]>") + "</smsRcvYn>");

        return sb.toString();
    }

    /**
     * xml 파싱
     * @param responseXml
     * @param appEventCd
     * @return
     * @throws JDOMException
     * @throws IOException
     */
    public HashMap<String, Object> toResponseParse(String responseXml, String appEventCd) throws JDOMException, IOException {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();

        Element root = XmlParse.getRootElement("<?xml version=\"1.0\" encoding=\"euc-kr\"?>"+responseXml);
        Element rtn = XmlParse.getReturnElement(root);

        Element commHeader = XmlParse.getChildElement(rtn, "commHeader");
        resultMap.put("globalNo",      XmlParse.getChildValue(commHeader, "globalNo"));
        resultMap.put("responseType",  XmlParse.getChildValue(commHeader, "responseType"));
        resultMap.put("responseCode",  XmlParse.getChildValue(commHeader, "responseCode"));
        resultMap.put("responseBasic", XmlParse.getChildValue(commHeader, "responseBasic"));
        resultMap.put("rsltCd",        XmlParse.getChildValue(rtn, "rsltCd"));
        resultMap.put("rsltMsg",       XmlParse.getChildValue(rtn, "rsltMsg"));
        resultMap.put("osstOrdNo",     XmlParse.getChildValue(rtn, "osstOrdNo"));


        if("ST1".equals(appEventCd)){
            resultMap.put("prgrStatCd", XmlParse.getChildValue(rtn, "prgrStatCd"));
        }

        if("FS2".equals(appEventCd) || "FS4".equals(appEventCd) || "FT1".equals(appEventCd)) {
            Element outDto = XmlParse.getChildElement(rtn, "outDto");
            resultMap.put("rsltCd", XmlParse.getChildValue(outDto, "rsltCd"));
            resultMap.put("rsltMsg", XmlParse.getChildValue(outDto, "rsltMsg"));
        }

        if("FS5".equals(appEventCd)) {
            Element outDto = XmlParse.getChildElement(rtn, "outDto");
            resultMap.put("rsltCd", XmlParse.getChildValue(outDto, "SVC_STATUS"));
            resultMap.put("rsltMsg", XmlParse.getChildValue(outDto, "ERR_MSG"));
        }
        if("FT0".equals(appEventCd)) {
            Element outDto = XmlParse.getChildElement(rtn, "outDto");
            resultMap.put("trtResltCd", XmlParse.getChildValue(outDto, "trtResltCd"));
            resultMap.put("trtResltSbst", XmlParse.getChildValue(outDto, "trtResltSbst"));
            resultMap.put("fathTxnRegYn", XmlParse.getChildValue(outDto, "fathTxnRegYn"));
            resultMap.put("stbznPerdYn", XmlParse.getChildValue(outDto, "stbznPerdYn"));
        }
        if("FS8".equals(appEventCd)) {
            Element outDto = XmlParse.getChildElement(rtn, "outDto");
            resultMap.put("urlAdr", XmlParse.getChildValue(outDto, "urlAdr"));
            resultMap.put("fathTransacId", XmlParse.getChildValue(outDto, "fathTransacId"));
            resultMap.put("resltCd", XmlParse.getChildValue(outDto, "resltCd"));
            resultMap.put("resltSbst", XmlParse.getChildValue(outDto, "resltSbst"));
        }
        if("FS9".equals(appEventCd)) {
            Element outDto = XmlParse.getChildElement(rtn, "outDto");
            resultMap.put("fathTransacId", XmlParse.getChildValue(outDto, "fathTransacId"));
            resultMap.put("fathResltCd", XmlParse.getChildValue(outDto, "fathResltCd"));
            resultMap.put("fathDecideCd", XmlParse.getChildValue(outDto, "fathDecideCd"));
            resultMap.put("fathResltMsgSbst", XmlParse.getChildValue(outDto, "fathResltMsgSbst"));
        }

        if("MC0".equals(appEventCd)) {
            Element outDto = XmlParse.getChildElement(rtn, "outDto");
            resultMap.put("osstOrdNo", XmlParse.getChildValue(outDto, "osstOrdNo"));
            resultMap.put("rsltCd", XmlParse.getChildValue(outDto, "rsltCd"));
            resultMap.put("rsltMsg", XmlParse.getChildValue(outDto, "rsltMsg"));
        }

        return resultMap;
    }

    /**
     * FS0, FS1, FS2 xml 본문 생성
     * @param MPlatformResVO
     * @return String
     */
    public String xmlMessageFS(MPlatformResVO vo){

        StringBuffer sb = new StringBuffer();

        try{
            sb.append("<mngmAgncId>" + vo.getMngmAgncId().replaceAll("&", "<![CDATA[&]]>") + "</mngmAgncId>");
            sb.append("<cntpntCd>" + vo.getCntpntCd().replaceAll("&", "<![CDATA[&]]>") + "</cntpntCd>");

            if("FS0".equals(vo.getAppEventCd())) {
                sb.append("<retvStrtDt>" + vo.getRetvStrtDt().replaceAll("&", "<![CDATA[&]]>") + "</retvStrtDt>");
                sb.append("<retvEndDt>" + vo.getRetvEndDt().replaceAll("&", "<![CDATA[&]]>") + "</retvEndDt>");
                sb.append("<svcApyTrtStatCd>" + vo.getSvcApyTrtStatCd().replaceAll("&", "<![CDATA[&]]>") + "</svcApyTrtStatCd>");
                if(StringUtils.isEmpty(vo.getRetvSeq())) vo.setRetvSeq("0");
                if(StringUtils.isEmpty(vo.getRetvCascnt())) vo.setRetvCascnt("40");
                sb.append("<retvSeq>" + vo.getRetvSeq().replaceAll("&", "<![CDATA[&]]>") + "</retvSeq>");
                sb.append("<retvCascnt>" + vo.getRetvCascnt().replaceAll("&", "<![CDATA[&]]>") + "</retvCascnt>");
            }else if("FS1".equals(vo.getAppEventCd())) {
                sb.append("<frmpapId>" + vo.getFrmpapId().replaceAll("&", "<![CDATA[&]]>") + "</frmpapId>");
            }else if("FS2".equals(vo.getAppEventCd())) {
                sb.append("<frmpapId>" + vo.getFrmpapId().replaceAll("&", "<![CDATA[&]]>") + "</frmpapId>");
                sb.append("<frmpapStatCd>" + vo.getFrmpapStatCd().replaceAll("&", "<![CDATA[&]]>") + "</frmpapStatCd>");
            }

            logger.error("xmlMessageFS::" + vo.getAppEventCd() + " = " + sb.toString());
        }catch(Exception e){
            logger.error("xmlMessageFS:" + vo.getAppEventCd() + ":" + vo.getAppEventCd() + " ERROR=======================================");
            logger.error("xmlMessageFS:" + vo.getAppEventCd() + ":" + e.getMessage());
            logger.error("xmlMessageFS:" + vo.getAppEventCd() + ":" + vo.getAppEventCd() + " ERROR=======================================");
        }

        return sb.toString();
    }

    /**
     * FS3, FS4 xml 본문 생성
     * @param MPlatformResVO
     * @return String
     */
    public String xmlMessageFS34(MPlatformResVO vo){

        StringBuffer sb = new StringBuffer();

        try{
            sb.append("<oderTrtOrgId>" + vo.getOderTrtOrgId().replaceAll("&", "<![CDATA[&]]>") + "</oderTrtOrgId>");
            sb.append("<tlphNo>" + vo.getTlphNo().replaceAll("&", "<![CDATA[&]]>") + "</tlphNo>");
            sb.append("<oderTrtId>" + vo.getOderTrtId().replaceAll("&", "<![CDATA[&]]>") + "</oderTrtId>");
            sb.append("<oderTypeCd>" + vo.getOderTypeCd().replaceAll("&", "<![CDATA[&]]>") + "</oderTypeCd>");
            if("FS3".equals(vo.getAppEventCd())) {
                sb.append("<retvStDt>" + vo.getRetvStDt().replaceAll("&", "<![CDATA[&]]>") + "</retvStDt>");
                sb.append("<retvFnsDt>" + vo.getRetvFnsDt().replaceAll("&", "<![CDATA[&]]>") + "</retvFnsDt>");
                sb.append("<retvSeq>" + vo.getRetvSeq().replaceAll("&", "<![CDATA[&]]>") + "</retvSeq>");
                sb.append("<retvCascnt>" + vo.getRetvCascnt().replaceAll("&", "<![CDATA[&]]>") + "</retvCascnt>");
            }else if("FS4".equals(vo.getAppEventCd())) {
                sb.append("<itgFrmpapSeq>" + vo.getItgFrmpapSeq().replaceAll("&", "<![CDATA[&]]>") + "</itgFrmpapSeq>");
                sb.append("<frmpapId>" + vo.getFrmpapId().replaceAll("&", "<![CDATA[&]]>") + "</frmpapId>");
                sb.append("<scanDt>" + vo.getScanDt().replaceAll("&", "<![CDATA[&]]>") + "</scanDt>");
                sb.append("<svcContId>" + vo.getSvcContId().replaceAll("&", "<![CDATA[&]]>") + "</svcContId>");
            }
            logger.error("xmlMessageFS::" + vo.getAppEventCd() + " = " + sb.toString());
        }catch(Exception e){
            logger.error("xmlMessageFS:" + vo.getAppEventCd() + ":" + vo.getAppEventCd() + " ERROR=======================================");
            logger.error("xmlMessageFS:" + vo.getAppEventCd() + ":" + e.getMessage());
            logger.error("xmlMessageFS:" + vo.getAppEventCd() + ":" + vo.getAppEventCd() + " ERROR=======================================");
        }

        return sb.toString();
    }

    /**
     * FS5 xml 본문 생성
     * @param MPlatformNstepVO
     * @return String
     */
    public String xmlMessageFS5(MPlatformNstepVO vo){

        StringBuffer sb = new StringBuffer();

        try{
            sb.append(this.getXmlElementIfNotEmpty("CNTPNT_SHOP_ID",                vo.getCntpntShopId()));
            sb.append(this.getXmlElementIfNotEmpty("SPCL_SLS_NO",                   vo.getSpclSlsNo()));
            sb.append(this.getXmlElementIfNotEmpty("SRL_NO",                        vo.getSrlNo()));
            sb.append(this.getXmlElementIfNotEmpty("SLS_CMPN_CD",                   vo.getSlsCmpnCd()));
            sb.append(this.getXmlElementIfNotEmpty("CUST_IDNT_NO",                  vo.getCustIdntNo()));
            sb.append(this.getXmlElementIfNotEmpty("TRMN_YET_RPYM_AMT_YN",          vo.getTrmnYetRpymAmtYn()));
            sb.append(this.getXmlElementIfNotEmpty("CUST_IDNT_NO_IND_CD",           vo.getCustIdntNoIndCd()));
            sb.append(this.getXmlElementIfNotEmpty("PRDC_CD",                       vo.getPrdcCd()));
            sb.append(this.getXmlElementIfNotEmpty("HNDSET_MDL_ID",                 vo.getHndsetMdlId()));
            sb.append(this.getXmlElementIfNotEmpty("IND_TYPE_CD",                   vo.getIndTypeCd()));
            sb.append(this.getXmlElementIfNotEmpty("CUST_NM",                       vo.getCustNm()));
            sb.append(this.getXmlElementIfNotEmpty("CUST_DTL_TYPE_CD",              vo.getCustDtlTypeCd()));
            sb.append(this.getXmlElementIfNotEmpty("CRPR_NO",                       vo.getCrprNo()));
            sb.append(this.getXmlElementIfNotEmpty("CUST_TYPE_CD",                  vo.getCustTypeCd()));
            sb.append(this.getXmlElementIfNotEmpty("WLFR_CD",                       vo.getWlfrCd()));
            sb.append(this.getXmlElementIfNotEmpty("WRLN_TLPH_NO",                  vo.getWrlnTlphNo()));
            sb.append(this.getXmlElementIfNotEmpty("TLPH_NO",                       vo.getTlphNo()));
            sb.append(this.getXmlElementIfNotEmpty("EML_ADRS_NM",                   vo.getEmlAdrsNm()));
            sb.append(this.getXmlElementIfNotEmpty("EML_BILL_AGRE_YN",              vo.getEmlBillAgreYn()));
            sb.append(this.getXmlElementIfNotEmpty("BILL_ADRS_DONG_NM",             vo.getBillAdrsDongNm()));
            sb.append(this.getXmlElementIfNotEmpty("BILL_ADRS_NM",                  vo.getBillAdrsNm()));
            sb.append(this.getXmlElementIfNotEmpty("BILL_ADRS_ZIP_NO",              vo.getBillAdrsZipNo()));
            sb.append(this.getXmlElementIfNotEmpty("BLPYM_MTHD_CD",                 vo.getBlpymMthdCd()));
            sb.append(this.getXmlElementIfNotEmpty("BLPYM_BANK_NM",                 vo.getBlpymBankNm()));
            sb.append(this.getXmlElementIfNotEmpty("BLPYM_CUST_INHB_RGST_NO",       vo.getBlpymCustInhbRgstNo()));
            sb.append(this.getXmlElementIfNotEmpty("BLPYM_CUST_NM",                 vo.getBlpymCustNm()));
            sb.append(this.getXmlElementIfNotEmpty("CRDT_CARD_NO",                  vo.getCrdtCardNo()));
            sb.append(this.getXmlElementIfNotEmpty("EFCT_YM",                       vo.getEfctYm()));
            sb.append(this.getXmlElementIfNotEmpty("BLPYM_BNKACN_NO",               vo.getBlpymBnkacnNo()));
            sb.append(this.getXmlElementIfNotEmpty("SBSCST_TYPE_CD",                vo.getSbscstTypeCd()));
            sb.append(this.getXmlElementIfNotEmpty("USIM_BILL_TYPE_CD",             vo.getUsimBillTypeCd()));
            sb.append(this.getXmlElementIfNotEmpty("CUST_FVRT_1_TLPH_NO",           vo.getCustFvrt1TlphNo()));
            sb.append(this.getXmlElementIfNotEmpty("CUST_FVRT_2_TLPH_NO",           vo.getCustFvrt2TlphNo()));
            sb.append(this.getXmlElementIfNotEmpty("NOW_SBSC_TLCM_CD",              vo.getNowSbscTlcmCd()));
            sb.append(this.getXmlElementIfNotEmpty("NOW_CUST_TLPH_NO",              vo.getNowCustTlphNo()));
            sb.append(this.getXmlElementIfNotEmpty("MSN",                           vo.getMsn()));
            sb.append(this.getXmlElementIfNotEmpty("ICC_ID",                        vo.getIccId()));
            sb.append(this.getXmlElementIfNotEmpty("AGNC_ID",                       vo.getAgncId()));
            sb.append(this.getXmlElementIfNotEmpty("APLSHT_CNCL_YN",                vo.getAplshtCnclYn()));
            sb.append(this.getXmlElementIfNotEmpty("APLSHT_RGST_DATE",              vo.getAplshtRgstDate()));
            sb.append(this.getXmlElementIfNotEmpty("INDV_INFO_INER_PRCUSE_AGRE_YN", vo.getIndvInfoInerPrcuseAgreYn()));
            sb.append(this.getXmlElementIfNotEmpty("AGNT_NM",                       vo.getAgntNm()));
            sb.append(this.getXmlElementIfNotEmpty("AGNT_INHB_RGST_NO",             vo.getAgntInhbRgstNo()));
            sb.append(this.getXmlElementIfNotEmpty("AGNT_WRKJOB_IND_CD",            vo.getAgntWrkjobIndCd()));
            sb.append(this.getXmlElementIfNotEmpty("AGNT_PRTTLP_NO",                vo.getAgntPrttlpNo()));
            sb.append(this.getXmlElementIfNotEmpty("AGNT_HOUS_TLPH_NO",             vo.getAgntHousTlphNo()));
            sb.append(this.getXmlElementIfNotEmpty("REAL_USER_INHB_RGST_NO",        vo.getRealUserInhbRgstNo()));
            sb.append(this.getXmlElementIfNotEmpty("REAL_USER_NM",                  vo.getRealUserNm()));
            sb.append(this.getXmlElementIfNotEmpty("MEMO_SBST",                     vo.getMemoSbst()));
            sb.append(this.getXmlElementIfNotEmpty("BIZR_RGST_NO",                  vo.getBizrRgstNo()));
            sb.append(this.getXmlElementIfNotEmpty("RQSSHT_DSPT_TYPE_CD",           vo.getRqsshtDsptTypeCd()));
            sb.append(this.getXmlElementIfNotEmpty("USIM_PYMN_MTHD_CD",             vo.getUsimPymnMthdCd()));
            sb.append(this.getXmlElementIfNotEmpty("USIM_UNIQ_OPEN_YN",             vo.getUsimUniqOpenYn()));
            sb.append(this.getXmlElementIfNotEmpty("BOND_PRSR_FEE_CD",              vo.getBondPrsrFeeCd()));
            sb.append(this.getXmlElementIfNotEmpty("HNDSET_DSCN_1_AMNT",            vo.getHndsetDscn1Amnt()));
            sb.append(this.getXmlElementIfNotEmpty("INST_MNTH_CNT",                 vo.getInstMnthCnt()));
            sb.append(this.getXmlElementIfNotEmpty("ENGG_MNTH_CNT",                 vo.getEnggMnthCnt()));
            sb.append(this.getXmlElementIfNotEmpty("USIM_TYPE_DIV_CD",              vo.getUsimTypeDivCd()));
            sb.append(this.getXmlElementIfNotEmpty("SVC_CNTR_NO",                   vo.getSvcCntrNo()));
            sb.append(this.getXmlElementIfNotEmpty("IPIN_CI",                       vo.getIpinCi()));
            sb.append(this.getXmlElementIfNotEmpty("MY_SELF_ATHN_YN",               vo.getMySelfAthnYn()));
            sb.append(this.getXmlElementIfNotEmpty("ONLINE_ATHN_DIV_CD",            vo.getOnlineAthnDivCd()));
            sb.append(this.getXmlElementIfNotEmpty("CRDT_INFO_AGRE_YN",             vo.getCrdtInfoAgreYn()));
            sb.append(this.getXmlElementIfNotEmpty("CSGN_INFO_ADVM_RCV_AGREE_YN",   vo.getCsgnInfoAdvmRcvAgreeYn()));
            sb.append(this.getXmlElementIfNotEmpty("OTCOM_INFO_ADVM_RCV_AGREE_YN",  vo.getOtcomInfoAdvmRcvAgreeYn()));
            sb.append(this.getXmlElementIfNotEmpty("OTCOM_INFO_ADVM_CSGN_AGREE_YN", vo.getOtcomInfoAdvmCsgnAgreeYn()));
            sb.append(this.getXmlElementIfNotEmpty("FNNC_DEAL_AGREE_YN",            vo.getFnncDealAgreeYn()));
            sb.append(this.getXmlElementIfNotEmpty("GPCOM_COMSVC_SBSC_AGREE_YN",    vo.getGpcomComsvcSbscAgreeYn()));
            sb.append(this.getXmlElementIfNotEmpty("CARD_INSUR_PROD_AGREE_YN",      vo.getCardInsurProdAgreeYn()));
            sb.append(this.getXmlElementIfNotEmpty("FATH_TRANSAC_ID",               vo.getFathTransacId()));
            sb.append(this.getXmlElementIfNotEmpty("M2M_HNDSET_YN",                 vo.getM2mHndsetYn()));
            sb.append(this.getXmlElementIfNotEmpty("INDV_LO_INFO_PRV_AGREE_YN",     vo.getIndvLoInfoPrvAgreeYn()));

            logger.error("xmlMessageFS5::" + vo.getAppEventCd() + " = " + sb.toString());
        }catch(Exception e){
            logger.error("xmlMessageFS5:" + vo.getAppEventCd() + ":" + vo.getAppEventCd() + " ERROR=======================================");
            logger.error("xmlMessageFS5:" + vo.getAppEventCd() + ":" + e.getMessage());
            logger.error("xmlMessageFS5:" + vo.getAppEventCd() + ":" + vo.getAppEventCd() + " ERROR=======================================");
        }

        return sb.toString();
    }

    /**
     * FPC0, FOP0 xml 추가 본문 생성
     * @param MPlatformResVO
     * @return String
     */
    public String xmlMessageFprc(MPlatformResVO vo) {

        StringBuffer sb = new StringBuffer();
        try{
            sb.append("<inFrmpapDto>");
            if("FPC0".equals(vo.getAppEventCd())) {
                sb.append("<mngmAgncId>" + vo.getMngmAgncId().replaceAll("&", "<![CDATA[&]]>") + "</mngmAgncId>");
                sb.append("<cntpntCd>" + vo.getCntpntCd().replaceAll("&", "<![CDATA[&]]>") + "</cntpntCd>");
            }
            sb.append("<frmpapId>" + vo.getFrmpapId().replaceAll("&", "<![CDATA[&]]>") + "</frmpapId>");
            sb.append("</inFrmpapDto>");

            logger.error("xmlMessageFprc::" + vo.getAppEventCd() + " = " + sb.toString());
        }catch(Exception e){
            logger.error("xmlMessageFprc:" + vo.getAppEventCd() + ":" + vo.getAppEventCd() + " ERROR=======================================");
            logger.error("xmlMessageFprc:" + vo.getAppEventCd() + ":" + e.getMessage());
            logger.error("xmlMessageFprc:" + vo.getAppEventCd() + ":" + vo.getAppEventCd() + " ERROR=======================================");
        }

        return sb.toString();
    }

    /**
     * FT0 xml 본문 생성
     * @param MPlatformResVO
     * @return String
     */
    public String xmlMessageFT0(MPlatformResVO vo){

        StringBuffer sb = new StringBuffer();

        try{
            sb.append("<onlineOfflnDivCd>" + vo.getOnlineOfflnDivCd().replaceAll("&", "<![CDATA[&]]>") + "</onlineOfflnDivCd>");
            sb.append("<orgId>" + vo.getOrgId().replaceAll("&", "<![CDATA[&]]>") + "</orgId>");
            sb.append("<retvCdVal>" + vo.getRetvCdVal().replaceAll("&", "<![CDATA[&]]>") + "</retvCdVal>");
            sb.append("<cpntId>" + vo.getCpntId().replaceAll("&", "<![CDATA[&]]>") + "</cpntId>");
            logger.error("xmlMessageFT0::" + vo.getAppEventCd() + " = " + sb.toString());
        }catch(Exception e){
            logger.error("xmlMessageFT0:" + vo.getAppEventCd() + ":" + vo.getAppEventCd() + " ERROR=======================================");
            logger.error("xmlMessageFT0:" + vo.getAppEventCd() + ":" + e.getMessage());
            logger.error("xmlMessageFT0:" + vo.getAppEventCd() + ":" + vo.getAppEventCd() + " ERROR=======================================");
        }
        return sb.toString();
    }

    /**
     * FT1 xml 본문 생성
     * @param MPlatformResVO
     * @return String
     */
    public String xmlMessageFT1(MPlatformResVO vo){

        StringBuffer sb = new StringBuffer();

        try{
            sb.append("<fathTransacId>" + vo.getFathTransacId().replaceAll("&", "<![CDATA[&]]>") + "</fathTransacId>");
            logger.error("xmlMessageFT1::" + vo.getAppEventCd() + " = " + sb.toString());
        }catch(Exception e){
            logger.error("xmlMessageFT1:" + vo.getAppEventCd() + ":" + vo.getAppEventCd() + " ERROR=======================================");
            logger.error("xmlMessageFT1:" + vo.getAppEventCd() + ":" + e.getMessage());
            logger.error("xmlMessageFT1:" + vo.getAppEventCd() + ":" + vo.getAppEventCd() + " ERROR=======================================");
        }
        return sb.toString();
    }

    /**
     * FS8 xml 본문 생성
     * @param MPlatformResVO
     * @return String
     */
    public String xmlMessageFS8(MPlatformResVO vo){

        StringBuffer sb = new StringBuffer();

        try{
            sb.append("<custFathInfoDTO>");
            sb.append("<orgId>" + vo.getOrgId().replaceAll("&", "<![CDATA[&]]>") + "</orgId>");
            sb.append("<cpntId>" + vo.getCpntId().replaceAll("&", "<![CDATA[&]]>") + "</cpntId>");
            sb.append("<onlineOfflnDivCd>" + vo.getOnlineOfflnDivCd().replaceAll("&", "<![CDATA[&]]>") + "</onlineOfflnDivCd>");
//            sb.append("<smsRcvTelNo>" + vo.getSmsRcvTelNo().replaceAll("&", "<![CDATA[&]]>") + "</smsRcvTelNo>");
            sb.append("<fathSbscDivCd>" + vo.getFathSbscDivCd().replaceAll("&", "<![CDATA[&]]>") + "</fathSbscDivCd>");
            sb.append("<retvCdVal>" + vo.getRetvCdVal().replaceAll("&", "<![CDATA[&]]>") + "</retvCdVal>");
            sb.append("<frmpapId></frmpapId>");
            sb.append("<photoAthnNcstYn></photoAthnNcstYn>");
            sb.append("<photoAthnTxnSeq></photoAthnTxnSeq>");
            sb.append("<scanTypeCd></scanTypeCd>");
            sb.append("</custFathInfoDTO>");
            logger.error("xmlMessageFS8::" + vo.getAppEventCd() + " = " + sb.toString());
        }catch(Exception e){
            logger.error("xmlMessageFS8:" + vo.getAppEventCd() + ":" + vo.getAppEventCd() + " ERROR=======================================");
            logger.error("xmlMessageFS8:" + vo.getAppEventCd() + ":" + e.getMessage());
            logger.error("xmlMessageFS8:" + vo.getAppEventCd() + ":" + vo.getAppEventCd() + " ERROR=======================================");
        }
        return sb.toString();
    }

    /**
     * FS9 xml 본문 생성
     * @param MPlatformResVO
     * @return String
     */
    public String xmlMessageFS9(MPlatformResVO vo){

        StringBuffer sb = new StringBuffer();

        try{
            sb.append("<fathTransacId>" + vo.getFathTransacId().replaceAll("&", "<![CDATA[&]]>") + "</fathTransacId>");
            sb.append("<retvDivCd>" + vo.getRetvDivCd().replaceAll("&", "<![CDATA[&]]>") + "</retvDivCd>");
            logger.error("xmlMessageFS9::" + vo.getAppEventCd() + " = " + sb.toString());
        }catch(Exception e){
            logger.error("xmlMessageFS9:" + vo.getAppEventCd() + ":" + vo.getAppEventCd() + " ERROR=======================================");
            logger.error("xmlMessageFS9:" + vo.getAppEventCd() + ":" + e.getMessage());
            logger.error("xmlMessageFS9:" + vo.getAppEventCd() + ":" + vo.getAppEventCd() + " ERROR=======================================");
        }
        return sb.toString();
    }

    /**
     * MC0 xml 본문 생성 - 명의변경사전체크(MC0)
     * @param MPlatformResVO
     * @return String
     */
    public String xmlMessageMC0(MPlatformResVO vo){

        StringBuffer sb = new StringBuffer();

        try{
            /**기본정보*/
            sb.append("<baseInfo>");
            sb.append("<mvnoOrdNo>" + vo.getMvnoOrdNo().replaceAll("&", "<![CDATA[&]]>") + "</mvnoOrdNo>");
            sb.append("<slsCmpnCd>" + vo.getSlsCmpnCd().replaceAll("&", "<![CDATA[&]]>") + "</slsCmpnCd>");
            sb.append("<custNo>" + vo.getCustNo().replaceAll("&", "<![CDATA[&]]>") + "</custNo>");
            sb.append("<svcContId>" + vo.getSvcContId().replaceAll("&", "<![CDATA[&]]>") + "</svcContId>");
            sb.append("<tlphNo>" + vo.getTlphNo().replaceAll("&", "<![CDATA[&]]>") + "</tlphNo>");
            sb.append("<mcnStatRsnCd>" + vo.getMcnStatRsnCd().replaceAll("&", "<![CDATA[&]]>") + "</mcnStatRsnCd>");
            sb.append("<usimSuccYn>" + vo.getUsimSuccYn().replaceAll("&", "<![CDATA[&]]>") + "</usimSuccYn>");
            if("N".equals(vo.getUsimSuccYn())){ //유심승계여부가 N일때
                sb.append("<iccId>" + vo.getIccId().replaceAll("&", "<![CDATA[&]]>") + "</iccId>");
            }
            //법인인경우 필수
            if("B".equals(vo.getCustTypeCd())){
                sb.append("<realUseCustNm>" + vo.getRealUseCustNm().replaceAll("&", "<![CDATA[&]]>") + "</realUseCustNm>");
                sb.append("<realUseCustBrthDate>" + vo.getRealUseCustBrthDate().replaceAll("&", "<![CDATA[&]]>") + "</realUseCustBrthDate>");
            }
            sb.append("</baseInfo>");

            /**양수인정보*/
            sb.append("<rcvCustInfo>");
            sb.append("<custTypeCd>" + vo.getCustTypeCd().replaceAll("&", "<![CDATA[&]]>") + "</custTypeCd>");
            sb.append("<custIdntNoIndCd>" + vo.getCustIdntNoIndCd().replaceAll("&", "<![CDATA[&]]>") + "</custIdntNoIndCd>");
            sb.append("<custIdntNo>" + vo.getCustIdntNo().replaceAll("&", "<![CDATA[&]]>") + "</custIdntNo>");
            //법인인경우 필수
            if("B".equals(vo.getCustTypeCd())) {
                sb.append("<crprNo>" + vo.getCrprNo().replaceAll("&", "<![CDATA[&]]>") + "</crprNo>");
            }
            sb.append("<custNm>" + vo.getCustNm().replaceAll("&", "<![CDATA[&]]>") + "</custNm>");
            sb.append("<myslAgreYn>" + vo.getMyslAgreYn().replaceAll("&", "<![CDATA[&]]>") + "</myslAgreYn>");
            if(StringUtil.isNotEmpty(vo.getNativeRlnamAthnEvdnPprCd())){
                sb.append("<nativeRlnamAthnEvdnPprCd>" + vo.getNativeRlnamAthnEvdnPprCd().replaceAll("&", "<![CDATA[&]]>") + "</nativeRlnamAthnEvdnPprCd>");
            }
            sb.append("<athnRqstcustCntplcNo>" + vo.getAthnRqstcustCntplcNo().replaceAll("&", "<![CDATA[&]]>") + "</athnRqstcustCntplcNo>");
            if(StringUtil.isNotEmpty(vo.getRsdcrtIssuDate())) {
                sb.append("<rsdcrtIssuDate>" + vo.getRsdcrtIssuDate().replaceAll("&", "<![CDATA[&]]>") + "</rsdcrtIssuDate>");
            }
            if("DRIVE".equals(vo.getNativeRlnamAthnEvdnPprCd())) { //운전면허증
                sb.append("<lcnsNo>" + vo.getLcnsNo().replaceAll("&", "<![CDATA[&]]>") + "</lcnsNo>");
                sb.append("<lcnsRgnCd>" + vo.getLcnsRgnCd().replaceAll("&", "<![CDATA[&]]>") + "</lcnsRgnCd>");
            }
            if("MERIT".equals(vo.getNativeRlnamAthnEvdnPprCd())){ //유공자
                sb.append("<mrtrPrsnNo>" + vo.getMrtrPrsnNo().replaceAll("&", "<![CDATA[&]]>") + "</mrtrPrsnNo>");
            }
            if(StringUtil.isNotEmpty(vo.getNationalityCd())) {
                sb.append("<nationalityCd>" + vo.getNationalityCd().replaceAll("&", "<![CDATA[&]]>") + "</nationalityCd>");
            }
            if("04".equals(vo.getCustIdntNoIndCd())) { //고객식별번호구분코드가 04일 때
                sb.append("<fornBrthDate>" + vo.getFornBrthDate().replaceAll("&", "<![CDATA[&]]>") + "</fornBrthDate>");
            }
            sb.append("<crdtInfoAgreYn>" + vo.getCrdtInfoAgreYn().replaceAll("&", "<![CDATA[&]]>") + "</crdtInfoAgreYn>");
            sb.append("<indvInfoInerPrcuseAgreYn>" + vo.getIndvInfoInerPrcuseAgreYn().replaceAll("&", "<![CDATA[&]]>") + "</indvInfoInerPrcuseAgreYn>");
            sb.append("<cnsgInfoAdvrRcvAgreYn>" + vo.getCnsgInfoAdvrRcvAgreYn().replaceAll("&", "<![CDATA[&]]>") + "</cnsgInfoAdvrRcvAgreYn>");
            sb.append("<othcmpInfoAdvrRcvAgreYn>" + vo.getOthcmpInfoAdvrRcvAgreYn().replaceAll("&", "<![CDATA[&]]>") + "</othcmpInfoAdvrRcvAgreYn>");
            sb.append("<othcmpInfoAdvrCnsgAgreYn>" + vo.getOthcmpInfoAdvrCnsgAgreYn().replaceAll("&", "<![CDATA[&]]>")+ "</othcmpInfoAdvrCnsgAgreYn>");
            sb.append("<grpAgntBindSvcSbscAgreYn>" + vo.getGrpAgntBindSvcSbscAgreYn().replaceAll("&", "<![CDATA[&]]>") + "</grpAgntBindSvcSbscAgreYn>");
            sb.append("<cardInsrPrdcAgreYn>" + vo.getCardInsrPrdcAgreYn().replaceAll("&", "<![CDATA[&]]>") + "</cardInsrPrdcAgreYn>");
            sb.append("<olngDscnHynmtrAgreYn>" + vo.getOlngDscnHynmtrAgreYn().replaceAll("&", "<![CDATA[&]]>") + "</olngDscnHynmtrAgreYn>");
            sb.append("<wlfrDscnAplyAgreYn>" + vo.getWlfrDscnAplyAgreYn().replaceAll("&", "<![CDATA[&]]>") + "</wlfrDscnAplyAgreYn>");
            sb.append("<spamPrvdAgreYn>" + vo.getSpamPrvdAgreYn().replaceAll("&", "<![CDATA[&]]>") + "</spamPrvdAgreYn>");
            sb.append("<prttlpStlmUseAgreYn>" + vo.getPrttlpStlmUseAgreYn().replaceAll("&", "<![CDATA[&]]>") + "</prttlpStlmUseAgreYn>");
            sb.append("<prttlpStlmPwdUseAgreYn>" + vo.getPrttlpStlmPwdUseAgreYn().replaceAll("&", "<![CDATA[&]]>") + "</prttlpStlmPwdUseAgreYn>");
            sb.append("<wrlnTlphNo>" + vo.getWrlnTlphNo().replaceAll("&", "<![CDATA[&]]>") + "</wrlnTlphNo>");
            //법인과 공공기관일 때 필수
            if(!"I".equals(vo.getCustTypeCd())) {
                sb.append("<rprsPrsnNm>" + vo.getRprsPrsnNm().replaceAll("&", "<![CDATA[&]]>") + "</rprsPrsnNm>");
                sb.append("<upjnCd>" + vo.getUpjnCd().replaceAll("&", "<![CDATA[&]]>") + "</upjnCd>");
                sb.append("<bcuSbst>" + vo.getBcuSbst().replaceAll("&", "<![CDATA[&]]>") + "</bcuSbst>");
                sb.append("<zipNo>" + vo.getZipNo().replaceAll("&", "<![CDATA[&]]>") + "</zipNo>");
                sb.append("<fndtCntplcSbst>" + vo.getFndtCntplcSbst().replaceAll("&", "<![CDATA[&]]>") + "</fndtCntplcSbst>");
                sb.append("<mntCntplcSbst>" + vo.getMntCntplcSbst().replaceAll("&", "<![CDATA[&]]>") + "</mntCntplcSbst>");
            }
            if(StringUtil.isNotEmpty(vo.getBrthDate())) {
                sb.append("<brthDate>" + vo.getBrthDate().replaceAll("&", "<![CDATA[&]]>") + "</brthDate>");
            }
            if(StringUtil.isNotEmpty(vo.getBrthNnpIndCd())) {
                sb.append("<brthNnpIndCd>" + vo.getBrthNnpIndCd().replaceAll("&", "<![CDATA[&]]>") + "</brthNnpIndCd>");
            }
            if(StringUtil.isNotEmpty(vo.getJobCd())) {
                sb.append("<jobCd>" + vo.getJobCd().replaceAll("&", "<![CDATA[&]]>") + "</jobCd>");
            }
            if(StringUtil.isNotEmpty(vo.getEmlAdrsNm())) {
                sb.append("<emlAdrsNm>" + vo.getEmlAdrsNm().replaceAll("&", "<![CDATA[&]]>") + "</emlAdrsNm>");
            }
            //법인과 공공기관일 때 optional
            if(!"I".equals(vo.getCustTypeCd())) {
                if(StringUtil.isNotEmpty(vo.getLstdIndCd())) {
                    sb.append("<lstdIndCd>" + vo.getLstdIndCd().replaceAll("&", "<![CDATA[&]]>") + "</lstdIndCd>");
                }
                if(StringUtil.isNotEmpty(vo.getEmplCnt())) {
                    sb.append("<emplCnt>" + vo.getEmplCnt().replaceAll("&", "<![CDATA[&]]>") + "</emplCnt>");
                }
                if(StringUtil.isNotEmpty(vo.getSlngAmt())) {
                    sb.append("<slngAmt>" + vo.getSlngAmt().replaceAll("&", "<![CDATA[&]]>") + "</slngAmt>");
                }
                if(StringUtil.isNotEmpty(vo.getCptlAmnt())) {
                    sb.append("<cptlAmnt>" + vo.getCptlAmnt().replaceAll("&", "<![CDATA[&]]>") + "</cptlAmnt>");
                }
                if(StringUtil.isNotEmpty(vo.getCrprUpjnCd())) {
                    sb.append("<crprUpjnCd>" + vo.getCrprUpjnCd().replaceAll("&", "<![CDATA[&]]>") + "</crprUpjnCd>");
                }
                if(StringUtil.isNotEmpty(vo.getCrprBcuSbst())) {
                    sb.append("<crprBcuSbst>" + vo.getCrprBcuSbst().replaceAll("&", "<![CDATA[&]]>") + "</crprBcuSbst>");
                }
                if(StringUtil.isNotEmpty(vo.getCrprZipNo())) {
                    sb.append("<crprZipNo>" + vo.getCrprZipNo().replaceAll("&", "<![CDATA[&]]>") + "</crprZipNo>");
                }
                if(StringUtil.isNotEmpty(vo.getCrprFndtCntplcSbst())) {
                    sb.append("<crprFndtCntplcSbst>" + vo.getCrprFndtCntplcSbst().replaceAll("&", "<![CDATA[&]]>") + "</crprFndtCntplcSbst>");
                }
                if(StringUtil.isNotEmpty(vo.getCrprMntCntplcSbst())) {
                    sb.append("<crprMntCntplcSbst>" + vo.getCrprMntCntplcSbst().replaceAll("&", "<![CDATA[&]]>") + "</crprMntCntplcSbst>");
                }
            }
            sb.append("<custInfoChngYn>" + vo.getCustInfoChngYn().replaceAll("&", "<![CDATA[&]]>") + "</custInfoChngYn>");
            //미성년자, 법인일 때 필수
            if("NM".equals(vo.getCstmrType()) || "B".equals(vo.getCustTypeCd())) {
                sb.append("<agntCustNm>" + vo.getAgntCustNm().replaceAll("&", "<![CDATA[&]]>") + "</agntCustNm>");
                sb.append("<agntRltnCd>" + vo.getAgntRltnCd().replaceAll("&", "<![CDATA[&]]>") + "</agntRltnCd>");
                sb.append("<agntBrthDate>" + vo.getAgntBrthDate().replaceAll("&", "<![CDATA[&]]>") + "</agntBrthDate>");
                sb.append("<agntTelNo>" + vo.getAgntTelNo().replaceAll("&", "<![CDATA[&]]>") + "</agntTelNo>");
            }
            //미성년자의 경우 필수
            if("NM".equals(vo.getCstmrType())) {
                sb.append("<agntCustIdfyNoType>" + vo.getAgntCustIdfyNoType().replaceAll("&", "<![CDATA[&]]>") + "</agntCustIdfyNoType>");
                sb.append("<agntIdfyNoVal>" + vo.getAgntIdfyNoVal().replaceAll("&", "<![CDATA[&]]>") + "</agntIdfyNoVal>");
                sb.append("<agntPersonSexDiv>" + vo.getAgntPersonSexDiv().replaceAll("&", "<![CDATA[&]]>") + "</agntPersonSexDiv>");
                sb.append("<agntAgreYn>" + vo.getAgntAgreYn().replaceAll("&", "<![CDATA[&]]>") + "</agntAgreYn>");
                sb.append("<agntTelAthn>" + vo.getAgntTelAthn().replaceAll("&", "<![CDATA[&]]>") + "</agntTelAthn>");
                sb.append("<agntTypeCd>" + vo.getAgntTypeCd().replaceAll("&", "<![CDATA[&]]>") + "</agntTypeCd>");
                sb.append("<agntRlnamAthnEvdnPprCd>" + vo.getAgntRlnamAthnEvdnPprCd().replaceAll("&", "<![CDATA[&]]>") + "</agntRlnamAthnEvdnPprCd>");
                if("DRIVE".equals(vo.getAgntRlnamAthnEvdnPprCd())) { //운전면허증
                    sb.append("<agntLicnsRgnCd>" + vo.getAgntLicnsRgnCd().replaceAll("&", "<![CDATA[&]]>") + "</agntLicnsRgnCd>");
                    sb.append("<agntLicnsNo>" + vo.getAgntLicnsNo().replaceAll("&", "<![CDATA[&]]>") + "</agntLicnsNo>");
                }
                if("FORGN".equals(vo.getAgntRlnamAthnEvdnPprCd())) { //외국인등록번호
                    sb.append("<agntNationalityCd>" + vo.getAgntNationalityCd().replaceAll("&", "<![CDATA[&]]>") + "</agntNationalityCd>");
                }
                sb.append("<agntRsdcrtIssuDate>" + vo.getAgntRsdcrtIssuDate().replaceAll("&", "<![CDATA[&]]>") + "</agntRsdcrtIssuDate>");
            }
            if(StringUtil.isNotEmpty(vo.getHomeTlphNo())) {
                sb.append("<homeTlphNo>" + vo.getHomeTlphNo().replaceAll("&", "<![CDATA[&]]>") + "</homeTlphNo>");
            }

            String myslfAthnYn= "N";
            if (!StringUtil.isEmpty(vo.getIpinCi())){
                myslfAthnYn= "Y";
                sb.append("<myslfAthnYn>" + myslfAthnYn + "</myslfAthnYn>");
                sb.append("<ipinCi>" + vo.getIpinCi().replaceAll("&", "<![CDATA[&]]>") + "</ipinCi>");
                sb.append("<onlineAthnDivCd>" + vo.getOnlineAthnDivCd().replaceAll("&", "<![CDATA[&]]>") + "</onlineAthnDivCd>");
            } else {
                sb.append("<myslfAthnYn>" + myslfAthnYn + "</myslfAthnYn>");
            }
            if(StringUtil.isNotEmpty(vo.getFnncDealAgreeYn())) {
                sb.append("<fnncDealAgreeYn>" + vo.getFnncDealAgreeYn().replaceAll("&", "<![CDATA[&]]>") + "</fnncDealAgreeYn>");
            }
            if(StringUtil.isNotEmpty(vo.getPhotoAthnTxnSeq())) {
                sb.append("<photoAthnTxnSeq>" + vo.getPhotoAthnTxnSeq().replaceAll("&", "<![CDATA[&]]>") + "</photoAthnTxnSeq>");
            }
            if(StringUtil.isNotEmpty(vo.getFathTransacId())) {
                sb.append("<fathTransacId>" + vo.getFathTransacId().replaceAll("&", "<![CDATA[&]]>") + "</fathTransacId>");
            }
            sb.append("<cpntId>" + vo.getCpntId().replaceAll("&", "<![CDATA[&]]>") + "</cpntId>");
            sb.append(this.getXmlElementIfNotEmpty("indvLoInfoPrvAgreeYn", vo.getIndvLoInfoPrvAgreeYn()));
            sb.append("</rcvCustInfo>");

            /**양수인청구정보*/
            sb.append("<rcvBillAcntInfo>");
            sb.append("<rqsshtPprfrmCd>" + vo.getRqsshtPprfrmCd().replaceAll("&", "<![CDATA[&]]>") + "</rqsshtPprfrmCd>");
            if("MB".equals(vo.getRqsshtPprfrmCd())){ //청구서양식코드가 모바일MMS(MB)일 때
                sb.append("<rqsshtTlphNo>" + vo.getRqsshtTlphNo().replaceAll("&", "<![CDATA[&]]>") + "</rqsshtTlphNo>");
            }
            if("CB".equals(vo.getRqsshtPprfrmCd())){ //청구서양식코드가 이메일(CB)일 때
                sb.append("<rqsshtEmlAdrsNm>" + vo.getRqsshtEmlAdrsNm().replaceAll("&", "<![CDATA[&]]>") + "</rqsshtEmlAdrsNm>");
            }
            sb.append("<billZipNo>" + vo.getBillZipNo().replaceAll("&", "<![CDATA[&]]>") + "</billZipNo>");
            sb.append("<billFndtCntplcSbst>" + vo.getBillFndtCntplcSbst().replaceAll("&", "<![CDATA[&]]>") + "</billFndtCntplcSbst>");
            sb.append("<billMntCntplcSbst>" + vo.getBillMntCntplcSbst().replaceAll("&", "<![CDATA[&]]>") + "</billMntCntplcSbst>");
            //납부방법 C:카드/D:계좌이체/R:지로
            sb.append("<blpymMthdCd>" + vo.getBlpymMthdCd().replaceAll("&", "<![CDATA[&]]>") + "</blpymMthdCd>");
            sb.append("<duedatDateIndCd>" + vo.getDuedatDateIndCd().replaceAll("&", "<![CDATA[&]]>") + "</duedatDateIndCd>");
            //납부방법이 카드
            if("C".equals(vo.getBlpymMthdCd())){
                sb.append("<crdtCardExprDate>" + vo.getCrdtCardExprDate().replaceAll("&", "<![CDATA[&]]>") + "</crdtCardExprDate>");
                sb.append("<crdtCardKindCd>" + vo.getCrdtCardKindCd().replaceAll("&", "<![CDATA[&]]>") + "</crdtCardKindCd>");
            }
            //납부방법이 자동이체
            if("D".equals(vo.getBlpymMthdCd())){
                sb.append("<bankCd>" + vo.getBankCd().replaceAll("&", "<![CDATA[&]]>") + "</bankCd>");
                sb.append("<agreIndCd>" + vo.getAgreIndCd().replaceAll("&", "<![CDATA[&]]>") + "</agreIndCd>");
                if("03".equals(vo.getAgreIndCd())){
                    sb.append("<myslAthnTypeCd>" + vo.getMyslAthnTypeCd().replaceAll("&", "<![CDATA[&]]>") + "</myslAthnTypeCd>");
                }
            }
            //납부방법이 카드나 자동이체
            if("C".equals(vo.getBlpymMthdCd()) || "D".equals(vo.getBlpymMthdCd())) {
                sb.append("<blpymMthdIdntNo>" + vo.getBlpymMthdIdntNo().replaceAll("&", "<![CDATA[&]]>") + "</blpymMthdIdntNo>");
                if(StringUtil.isNotEmpty(vo.getBlpymCustNm())){
                    sb.append("<blpymCustNm>" + vo.getBlpymCustNm().replaceAll("&", "<![CDATA[&]]>") + "</blpymCustNm>");
                }
                if(StringUtil.isNotEmpty(vo.getBlpymCustIdntNo())){
                    sb.append("<blpymCustIdntNo>" + vo.getBlpymCustIdntNo().replaceAll("&", "<![CDATA[&]]>") + "</blpymCustIdntNo>");
                }
                if(StringUtil.isNotEmpty(vo.getBlpymMthdIdntNoHideYn())) {
                    sb.append("<blpymMthdIdntNoHideYn>" + vo.getBlpymMthdIdntNoHideYn().replaceAll("&", "<![CDATA[&]]>") + "</blpymMthdIdntNoHideYn>");
                }
                if(StringUtil.isNotEmpty(vo.getBankSkipYn())) {
                    sb.append("<bankSkipYn>" + vo.getBankSkipYn() + "</bankSkipYn>");
                }
            }
            if(StringUtil.isNotEmpty(vo.getBillAtchExclYn())){
                sb.append("<billAtchExclYn>" + vo.getBillAtchExclYn().replaceAll("&", "<![CDATA[&]]>") + "</billAtchExclYn>");
            }
            if(StringUtil.isNotEmpty(vo.getRqsshtTlphNoHideYn())) {
                sb.append("<rqsshtTlphNoHideYn>" + vo.getRqsshtTlphNoHideYn().replaceAll("&", "<![CDATA[&]]>") + "</rqsshtTlphNoHideYn>");
            }
            if(StringUtil.isNotEmpty(vo.getRqsshtDsptYn())) {
                sb.append("<rqsshtDsptYn>" + vo.getRqsshtDsptYn().replaceAll("&", "<![CDATA[&]]>") + "</rqsshtDsptYn>");
            }
            if(StringUtil.isNotEmpty(vo.getEnclBillTrmnYn())) {
                sb.append("<enclBillTrmnYn>" + vo.getEnclBillTrmnYn().replaceAll("&", "<![CDATA[&]]>") + "</enclBillTrmnYn>");
            }
            sb.append("</rcvBillAcntInfo>");

            /**상품정보*/
            sb.append("<prdcList>");
            sb.append("<prdcCd>" + vo.getPrdcCd().replaceAll("&", "<![CDATA[&]]>") + "</prdcCd>");
            sb.append("<prdcTypecd>" + vo.getPrdcTypeCd().replaceAll("&", "<![CDATA[&]]>") + "</prdcTypecd>");
            if(StringUtil.isNotEmpty(vo.getFtrNewParam())){
                sb.append("<ftrNewParam>" + vo.getFtrNewParam().replaceAll("&", "<![CDATA[&]]>") + "</ftrNewParam>");
            }
            sb.append("</prdcList>");

            logger.error("xmlMessageMC0::" + vo.getAppEventCd() + " = " + sb.toString());
        }catch(Exception e){
            logger.error("xmlMessageMC0:" + vo.getAppEventCd() + ":" + vo.getAppEventCd() + " ERROR=======================================");
            logger.error("xmlMessageMC0:" + vo.getAppEventCd() + ":" + e.getMessage());
            logger.error("xmlMessageMC0:" + vo.getAppEventCd() + ":" + vo.getAppEventCd() + " ERROR=======================================");
        }
        return sb.toString();
    }

    /**
     * MP0 xml 본문 생성 - 명의변경(MP0)
     * @param MPlatformResVO
     * @return String
     */
    public String xmlMessage_MP0(MPlatformResVO vo){

        StringBuffer sb = new StringBuffer();

        try{
            sb.append("<baseInfo>");
            sb.append("<osstOrdNo>" + vo.getOsstOrdNo().replaceAll("&", "<![CDATA[&]]>") + "</osstOrdNo>");
            sb.append("<custNo>" + vo.getCustNo().replaceAll("&", "<![CDATA[&]]>") + "</custNo>");
            sb.append("<tlphNo>" + vo.getTlphNo().replaceAll("&", "<![CDATA[&]]>") + "</tlphNo>");
            sb.append("<svcContId>" + vo.getSvcContId().replaceAll("&", "<![CDATA[&]]>") + "</svcContId>");
            sb.append("<rcvCustNo>" + vo.getRcvCustNo().replaceAll("&", "<![CDATA[&]]>") + "</rcvCustNo>");
            sb.append("<rcvBillAcntNo>" + vo.getRcvBillAcntNo().replaceAll("&", "<![CDATA[&]]>") + "</rcvBillAcntNo>");
            sb.append("<mcnStatRsnCd>" + vo.getMcnStatRsnCd().replaceAll("&", "<![CDATA[&]]>") + "</mcnStatRsnCd>");
            if(StringUtil.isNotEmpty(vo.getIccId())) {
                sb.append("<iccId>" + vo.getIccId().replaceAll("&", "<![CDATA[&]]>") + "</iccId>");
            }
            if(StringUtil.isNotEmpty(vo.getUsimPymnMthdCd())) {
                sb.append("<usimPymnMthdCd>" + vo.getUsimPymnMthdCd().replaceAll("&", "<![CDATA[&]]>") + "</usimPymnMthdCd>");
            }
            sb.append("<docConfirmYn>" + vo.getDocConfirmYn().replaceAll("&", "<![CDATA[&]]>") + "</docConfirmYn>");
            sb.append("<followupYn>" + vo.getFollowupYn().replaceAll("&", "<![CDATA[&]]>") + "</followupYn>");
            sb.append("<slsCmpnCd>" + vo.getSlsCmpnCd().replaceAll("&", "<![CDATA[&]]>") + "</slsCmpnCd>");
            if(StringUtil.isNotEmpty(vo.getSbscPrtlstRcvEmlAdrsNm())) {
                sb.append("<sbscPrtlstRcvEmlAdrsNm>" + vo.getSbscPrtlstRcvEmlAdrsNm().replaceAll("&", "<![CDATA[&]]>") + "</sbscPrtlstRcvEmlAdrsNm>");
            }
            sb.append("<cntpntCd>" + vo.getCntpntCd().replaceAll("&", "<![CDATA[&]]>") + "</cntpntCd>");
            sb.append("<cntplcNo>" + vo.getCntplcNo().replaceAll("&", "<![CDATA[&]]>") + "</cntplcNo>");
            if(StringUtil.isNotEmpty(vo.getChgRqsshtEmlAdrsNm())) {
                sb.append("<chgRqsshtEmlAdrsNm>" + vo.getChgRqsshtEmlAdrsNm().replaceAll("&", "<![CDATA[&]]>") + "</chgRqsshtEmlAdrsNm>");
            }
            sb.append("</baseInfo>");
        }catch(Exception e){
            logger.error("xmlMessage_MP0:" + vo.getAppEventCd() + ":" + vo.getAppEventCd() + " ERROR=======================================");
            logger.error("xmlMessage_MP0:" + vo.getAppEventCd() + ":" + e.getMessage());
            logger.error("xmlMessage_MP0:" + vo.getAppEventCd() + ":" + vo.getAppEventCd() + " ERROR=======================================");
        }
        return sb.toString();
    }


    /** OSST exception 이력 저장 */
    public void insertOsstErrLog(MPlatformReqVO mPlatformReqVO,  Exception exception){

        RestTemplate restTemplate = new RestTemplate();
        String apiInterfaceServer = propertiesService.getString("api.interface.server");

        try{

            MPlatformErrVO errVO= new MPlatformErrVO(mPlatformReqVO.getResNo(), mPlatformReqVO.getAppEventCd());
            errVO.setErrInfo(exception);
            errVO.setPrntsContractNo(mPlatformReqVO.getPrntsContractNo());
            errVO.setCustomerId(mPlatformReqVO.getCustNo());

            restTemplate.postForObject(apiInterfaceServer + "/mPlatform/insertOsstErrLog", errVO, Void.class);

        }catch(Exception e){
            logger.error("insertOsstErrLog Exception={}", e.getMessage());
        }
    }

    /**
     * 암호화 된 값 복호화
     * @param Element
     * @param String
     */
    private void setDecryptText(Element parentDto, String childNm) {
        //파라미터 없으면 리턴
        if(parentDto == null || StringUtils.isBlank(childNm)) return;

        Element child = new Element(childNm);
        child.setText(KisaSeedUtil.decrypt(XmlParse.getChildValue(parentDto, childNm)));
        parentDto.removeChild(childNm);
        parentDto.addContent(child);
    }

    private StringBuilder getXmlElementIfNotEmpty(String elementName, String elementValue) {
        StringBuilder sb = new StringBuilder();

        if(!StringUtil.isEmpty(elementValue)) {
            sb.append("<");
            sb.append(elementName);
            sb.append(">");
            sb.append(elementValue.replaceAll("&", "<![CDATA[&]]>"));
            sb.append("</");
            sb.append(elementName);
            sb.append(">");
        }

        return sb;
    }
}
