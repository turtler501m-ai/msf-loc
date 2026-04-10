package com.ktmmobile.msf.domains.form.common.mplatform;

import static com.ktmmobile.msf.domains.form.common.constants.Constants.*;
import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.MPLATFORM_RESPONEXML_EMPTY_EXCEPTION;

import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Set;

import com.ktmmobile.msf.domains.form.common.dao.MplatFormOsstDao;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpErrVO;
import org.apache.commons.httpclient.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.domains.form.common.exception.McpMplatFormException;
import com.ktmmobile.msf.domains.form.common.exception.SelfServiceException;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.CommonXmlVO;
import com.ktmmobile.msf.domains.form.common.util.HttpClientUtil;

@Service
public class MsfMplatFormOsstServerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(MsfMplatFormOsstServerAdapter.class);

    @Value("${osst.simple.open.url}")
    private String osstSimpleOpenUrl;

    @Value("${SERVER_NAME}")
    private String serverLocation;

    @Autowired
    private MplatFormOsstDao mplatFormOsstDao;


    public boolean callService(HashMap<String,String> param,CommonXmlVO vo) throws SelfServiceException, SocketTimeoutException{
        return callService(param,vo,10000);
    }

    public boolean callService(HashMap<String,String> param, CommonXmlVO vo,int timeout) throws SelfServiceException, SocketTimeoutException{
        boolean result = false;
        String responseXml = "";
        try {
            String callUrl = osstSimpleOpenUrl;

            NameValuePair[] data = convertParam(param);



            for(NameValuePair key : data){
                logger.debug(key.getName() + "#====>" + key.getValue());
            }


            //로컬에서 강제로 성공 처리
            if("LOCAL".equals(serverLocation)) {

                String appEventCd = param.get("appEventCd");
                if (EVENT_CODE_NP_PRE_CHECK.equals(appEventCd)) {
                    StringBuffer selfStringBuffer = new StringBuffer();
                    selfStringBuffer.append("<soap:Envelope xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>");
                    selfStringBuffer.append("   <soap:Body>");
                    selfStringBuffer.append("      <ns2:osstNpBfacAgreeResponse xmlns:ns2='http://osst.so.itl.mvno.kt.com/'>");
                    selfStringBuffer.append("         <return>");
                    selfStringBuffer.append("            <bizHeader>");
                    selfStringBuffer.append("               <appEntrPrsnId>INL</appEntrPrsnId>");
                    selfStringBuffer.append("               <appAgncCd>AA12550</appAgncCd>");
                    selfStringBuffer.append("               <appEventCd>NP1</appEventCd>");
                    selfStringBuffer.append("               <appSendDateTime>20180314152724</appSendDateTime>");
                    selfStringBuffer.append("               <appRecvDateTime>20180314152718</appRecvDateTime>");
                    selfStringBuffer.append("               <appLgDateTime>20180314152718</appLgDateTime>");
                    selfStringBuffer.append("               <appNstepUserId>115149049</appNstepUserId>");
                    selfStringBuffer.append("               <appOrderId/>");
                    selfStringBuffer.append("            </bizHeader>");
                    selfStringBuffer.append("            <commHeader>");
                    selfStringBuffer.append("               <globalNo>TEST-INL-0314-10009</globalNo>");
                    selfStringBuffer.append("               <encYn/>");
                    //selfStringBuffer.append("               <responseType>E</responseType>");
                    selfStringBuffer.append("               <responseType>N</responseType>");
                    selfStringBuffer.append("               <responseCode>ITL_SST_E1018</responseCode>");
                    selfStringBuffer.append("               <responseLogcd/>");
                    selfStringBuffer.append("               <responseTitle/>");
                    //selfStringBuffer.append("               <responseBasic>가입제한자일 경우 개통이  불가능합니다. </responseBasic>");
                    selfStringBuffer.append("               <responseBasic>오류 메세지 처리... . </responseBasic>");
                    selfStringBuffer.append("               <langCode/>");
                    selfStringBuffer.append("               <filler/>");
                    selfStringBuffer.append("            </commHeader>");
                    selfStringBuffer.append("         </return>");
                    selfStringBuffer.append("      </ns2:osstNpBfacAgreeResponse>");
                    selfStringBuffer.append("   </soap:Body>");
                    selfStringBuffer.append("</soap:Envelope>");
                    responseXml = selfStringBuffer.toString();

                } else if (EVENT_CODE_PRE_CHECK.equals(appEventCd)) {
                    StringBuffer selfStringBuffer = new StringBuffer();
                    selfStringBuffer.append("<soap:Envelope xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>");
                    selfStringBuffer.append("   <soap:Body>");
                    selfStringBuffer.append("       <ns2:osstNpPrePrcResponse xmlns:ns2='http://osst.so.itl.mvno.kt.com/'>");
                    selfStringBuffer.append("           <return>");
                    selfStringBuffer.append("               <bizHeader>");
                    selfStringBuffer.append("                   <appEntrPrsnId>KIS</appEntrPrsnId>");
                    selfStringBuffer.append("                   <appAgncCd>VKI0317</appAgncCd>");
                    selfStringBuffer.append("                   <appEventCd>PC0</appEventCd>");
                    selfStringBuffer.append("                   <appSendDateTime>20180502164208</appSendDateTime>");
                    selfStringBuffer.append("                   <appRecvDateTime>20180502164207</appRecvDateTime>");
                    selfStringBuffer.append("                   <appLgDateTime>20180502164207</appLgDateTime>");
                    selfStringBuffer.append("                   <appNstepUserId>91060728</appNstepUserId>");
                    selfStringBuffer.append("                   <appOrderId></appOrderId>");
                    selfStringBuffer.append("               </bizHeader>");
                    selfStringBuffer.append("               <commHeader>");
                    selfStringBuffer.append("                   <globalNo>9106072820180502164203662</globalNo>");
                    selfStringBuffer.append("                   <encYn></encYn>");
                    selfStringBuffer.append("                   <responseType>N</responseType>");
                    selfStringBuffer.append("                   <responseCode></responseCode>");
                    selfStringBuffer.append("                   <responseLogcd></responseLogcd>");
                    selfStringBuffer.append("                   <responseTitle></responseTitle>");
                    selfStringBuffer.append("                   <responseBasic></responseBasic>");
                    selfStringBuffer.append("                   <langCode></langCode>");
                    selfStringBuffer.append("                   <filler></filler>");
                    selfStringBuffer.append("               </commHeader>");
                    selfStringBuffer.append("    <osstOrdNo>20170818102466</osstOrdNo>");
                    selfStringBuffer.append("    <rsltCd>S</rsltCd>");
                    selfStringBuffer.append(" <rsltMsg/>");
                    selfStringBuffer.append("           </return>");
                    selfStringBuffer.append("       </ns2:osstNpPrePrcResponse>");
                    selfStringBuffer.append("   </soap:Body>");
                    selfStringBuffer.append("</soap:Envelope>");
                    responseXml = selfStringBuffer.toString();

                } else if (EVENT_CODE_REQ_OPEN.equals(appEventCd)) {
                    StringBuffer selfStringBuffer = new StringBuffer();
                    selfStringBuffer.append("<soap:Envelope xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>");
                    selfStringBuffer.append("   <soap:Body>");
                    selfStringBuffer.append("      <ns2:osstNpBfacAgreeResponse xmlns:ns2='http://osst.so.itl.mvno.kt.com/'>");
                    selfStringBuffer.append("         <return>");
                    selfStringBuffer.append("            <bizHeader>");
                    selfStringBuffer.append("               <appEntrPrsnId>INL</appEntrPrsnId>");
                    selfStringBuffer.append("               <appAgncCd>AA12550</appAgncCd>");
                    selfStringBuffer.append("               <appEventCd>NP1</appEventCd>");
                    selfStringBuffer.append("               <appSendDateTime>20180314152724</appSendDateTime>");
                    selfStringBuffer.append("               <appRecvDateTime>20180314152718</appRecvDateTime>");
                    selfStringBuffer.append("               <appLgDateTime>20180314152718</appLgDateTime>");
                    selfStringBuffer.append("               <appNstepUserId>115149049</appNstepUserId>");
                    selfStringBuffer.append("               <appOrderId/>");
                    selfStringBuffer.append("            </bizHeader>");
                    selfStringBuffer.append("            <commHeader>");
                    selfStringBuffer.append("               <globalNo>TEST-INL-0314-10009</globalNo>");
                    selfStringBuffer.append("               <encYn/>");
                    //selfStringBuffer.append("               <responseType>E</responseType>");
                    selfStringBuffer.append("               <responseType>N</responseType>");
                    selfStringBuffer.append("               <responseCode>ITL_SST_E1020</responseCode>");
                    selfStringBuffer.append("               <responseLogcd/>");
                    selfStringBuffer.append("               <responseTitle/>");
                    selfStringBuffer.append("               <responseBasic>번호이동  사전동의 실패 : [BF1039] 번호이동사전동의 내역 미확인</responseBasic>");
                    selfStringBuffer.append("               <langCode/>");
                    selfStringBuffer.append("               <filler/>");
                    selfStringBuffer.append("            </commHeader>");
                    selfStringBuffer.append("         </return>");
                    selfStringBuffer.append("      </ns2:osstNpBfacAgreeResponse>");
                    selfStringBuffer.append("   </soap:Body>");
                    selfStringBuffer.append("</soap:Envelope>");
                    responseXml = selfStringBuffer.toString();

                } else if (EVENT_CODE_SEARCH_NUMBER.equals(appEventCd)) {
                    StringBuffer selfStringBuffer = new StringBuffer();
                    selfStringBuffer.append("<soap:Envelope xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>");
                    selfStringBuffer.append("   <soap:Body>");
                    selfStringBuffer.append("      <ns2:inqrOsstSvcNoInfoResponse xmlns:ns2='http://osst.so.itl.mvno.kt.com/'>");
                    selfStringBuffer.append("         <return>");
                    selfStringBuffer.append("            <bizHeader>");
                    selfStringBuffer.append("               <appEntrPrsnId>ENX</appEntrPrsnId>");
                    selfStringBuffer.append("               <appAgncCd>AA11071</appAgncCd>");
                    selfStringBuffer.append("               <appEventCd>NU1</appEventCd>");
                    selfStringBuffer.append("               <appSendDateTime>20170818130000</appSendDateTime>");
                    selfStringBuffer.append("               <appRecvDateTime>20170818154350</appRecvDateTime>");
                    selfStringBuffer.append("               <appLgDateTime>20170818154350</appLgDateTime>");
                    selfStringBuffer.append("               <appNstepUserId>6833564</appNstepUserId>");
                    selfStringBuffer.append("               <appOrderId/>");
                    selfStringBuffer.append("            </bizHeader>");
                    selfStringBuffer.append("            <commHeader>");
                    selfStringBuffer.append("               <globalNo>TEST-LSH-0818-08182</globalNo>");
                    selfStringBuffer.append("               <encYn/>");
                    selfStringBuffer.append("               <responseType>N</responseType>");
                    selfStringBuffer.append("               <responseCode/>");
                    selfStringBuffer.append("               <responseLogcd/>");
                    selfStringBuffer.append("               <responseTitle/>");
                    selfStringBuffer.append("               <responseBasic/>");
                    selfStringBuffer.append("               <langCode/>");
                    selfStringBuffer.append("               <filler/>");
                    selfStringBuffer.append("            </commHeader>");
                    selfStringBuffer.append("            <outDto>");
                    selfStringBuffer.append("               <lastPageYn>N</lastPageYn>");
                    selfStringBuffer.append("               <svcNoList>");
                    selfStringBuffer.append("                  <asgnAgncId/>");
                    selfStringBuffer.append("                  <encdTlphNo>kodX85tAG1a/YTeS1ftk8g==</encdTlphNo>");
                    selfStringBuffer.append("                  <fvrtnoAqcsPsblYn>N</fvrtnoAqcsPsblYn>");
                    selfStringBuffer.append("                  <openSvcIndCd>02</openSvcIndCd>");
                    selfStringBuffer.append("                  <rsrvCustNo>636719750</rsrvCustNo>");
                    selfStringBuffer.append("                  <statMntnEndPrrnDate/>");
                    selfStringBuffer.append("                  <tlphNo>01029013860</tlphNo>");
                    selfStringBuffer.append("                  <tlphNoChrcCd>GEN</tlphNoChrcCd>");
                    selfStringBuffer.append("                  <tlphNoOwnCmncCmpnCd>KTF</tlphNoOwnCmncCmpnCd>");
                    selfStringBuffer.append("                  <tlphNoStatCd>AA</tlphNoStatCd>");
                    selfStringBuffer.append("                  <tlphNoStatChngDt>20130625</tlphNoStatChngDt>");
                    selfStringBuffer.append("                  <tlphNoUseCd>R</tlphNoUseCd>");
                    selfStringBuffer.append("                  <tlphNoUseMntCd>GRN</tlphNoUseMntCd>");
                    selfStringBuffer.append("               </svcNoList>");
                    selfStringBuffer.append("               <svcNoList>");
                    selfStringBuffer.append("                  <asgnAgncId/>");
                    selfStringBuffer.append("                  <encdTlphNo>m8fnSG6cMIe/YTeS1ftk8g==</encdTlphNo>");
                    selfStringBuffer.append("                  <fvrtnoAqcsPsblYn>N</fvrtnoAqcsPsblYn>");
                    selfStringBuffer.append("                  <openSvcIndCd>02</openSvcIndCd>");
                    selfStringBuffer.append("                  <rsrvCustNo>636719750</rsrvCustNo>");
                    selfStringBuffer.append("                  <statMntnEndPrrnDate/>");
                    selfStringBuffer.append("                  <tlphNo>01029053860</tlphNo>");
                    selfStringBuffer.append("                  <tlphNoChrcCd>GEN</tlphNoChrcCd>");
                    selfStringBuffer.append("                  <tlphNoOwnCmncCmpnCd>KTF</tlphNoOwnCmncCmpnCd>");
                    selfStringBuffer.append("                  <tlphNoStatCd>AA</tlphNoStatCd>");
                    selfStringBuffer.append("                  <tlphNoStatChngDt>20170720</tlphNoStatChngDt>");
                    selfStringBuffer.append("                  <tlphNoUseCd>R</tlphNoUseCd>");
                    selfStringBuffer.append("                  <tlphNoUseMntCd>GRN</tlphNoUseMntCd>");
                    selfStringBuffer.append("               </svcNoList>");
                    selfStringBuffer.append("               <svcNoList>");
                    selfStringBuffer.append("                  <asgnAgncId/>");
                    selfStringBuffer.append("                  <encdTlphNo>kUajG95X/d6/YTeS1ftk8g==</encdTlphNo>");
                    selfStringBuffer.append("                  <fvrtnoAqcsPsblYn>N</fvrtnoAqcsPsblYn>");
                    selfStringBuffer.append("                  <openSvcIndCd>02</openSvcIndCd>");
                    selfStringBuffer.append("                  <rsrvCustNo>636719750</rsrvCustNo>");
                    selfStringBuffer.append("                  <statMntnEndPrrnDate/>");
                    selfStringBuffer.append("                  <tlphNo>01029033860</tlphNo>");
                    selfStringBuffer.append("                  <tlphNoChrcCd>GEN</tlphNoChrcCd>");
                    selfStringBuffer.append("                  <tlphNoOwnCmncCmpnCd>KTF</tlphNoOwnCmncCmpnCd>");
                    selfStringBuffer.append("                  <tlphNoStatCd>AA</tlphNoStatCd>");
                    selfStringBuffer.append("                  <tlphNoStatChngDt>20170720</tlphNoStatChngDt>");
                    selfStringBuffer.append("                  <tlphNoUseCd>R</tlphNoUseCd>");
                    selfStringBuffer.append("                  <tlphNoUseMntCd>GRN</tlphNoUseMntCd>");
                    selfStringBuffer.append("               </svcNoList>  ");
                    selfStringBuffer.append("            </outDto>");
                    selfStringBuffer.append("         </return>");
                    selfStringBuffer.append("      </ns2:inqrOsstSvcNoInfoResponse>");
                    selfStringBuffer.append("   </soap:Body>");
                    selfStringBuffer.append("</soap:Envelope> ");
                    responseXml = selfStringBuffer.toString();
                } else if (EVENT_CODE_NUMBER_REG.equals(appEventCd)) {
                    StringBuffer selfStringBuffer = new StringBuffer();
                    selfStringBuffer.append("<soap:Envelope xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>");
                    selfStringBuffer.append("   <soap:Body>");
                    selfStringBuffer.append("      <ns2:resvOsstTlphNoResponse xmlns:ns2='http://osst.so.itl.mvno.kt.com/'>");
                    selfStringBuffer.append("         <return>");
                    selfStringBuffer.append("            <bizHeader>");
                    selfStringBuffer.append("               <appEntrPrsnId>ENX</appEntrPrsnId>");
                    selfStringBuffer.append("               <appAgncCd>AA11071</appAgncCd>");
                    selfStringBuffer.append("               <appEventCd>NU2</appEventCd>");
                    selfStringBuffer.append("               <appSendDateTime>20170818130000</appSendDateTime>");
                    selfStringBuffer.append("               <appRecvDateTime>20170818154821</appRecvDateTime>");
                    selfStringBuffer.append("               <appLgDateTime>20170818154821</appLgDateTime>");
                    selfStringBuffer.append("               <appNstepUserId>6833564</appNstepUserId>");
                    selfStringBuffer.append("               <appOrderId/>");
                    selfStringBuffer.append("            </bizHeader>");
                    selfStringBuffer.append("            <commHeader>");
                    selfStringBuffer.append("               <globalNo>TEST-LSH-0818-08183</globalNo>");
                    selfStringBuffer.append("               <encYn/>");
                    selfStringBuffer.append("               <responseType>N</responseType>");
                    selfStringBuffer.append("               <responseCode/>");
                    selfStringBuffer.append("               <responseLogcd/>");
                    selfStringBuffer.append("               <responseTitle/>");
                    selfStringBuffer.append("               <responseBasic/>");
                    selfStringBuffer.append("               <langCode/>");
                    selfStringBuffer.append("               <filler/>");
                    selfStringBuffer.append("            </commHeader>");
                    selfStringBuffer.append("         </return>");
                    selfStringBuffer.append("      </ns2:resvOsstTlphNoResponse>");
                    selfStringBuffer.append("   </soap:Body>");
                    selfStringBuffer.append("</soap:Envelope>");
                    responseXml = selfStringBuffer.toString();
                }else if(EVENT_CODE_USIM_SELF_CHG.equals(appEventCd)){
                    StringBuffer selfStringBuffer = new StringBuffer();
                    selfStringBuffer.append("<soap:Envelope xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>");
                    selfStringBuffer.append("   <soap:Body>");
                    selfStringBuffer.append("       <ns2:usimChgPrcResponse xmlns:ns2='http://osst.so.itl.mvno.kt.com/'>");
                    selfStringBuffer.append("           <return>");
                    selfStringBuffer.append("               <bizHeader>");
                    selfStringBuffer.append("                   <appEntrPrsnId>KIS</appEntrPrsnId>");
                    selfStringBuffer.append("                   <appAgncCd>AA11070</appAgncCd>");
                    selfStringBuffer.append("                   <appEventCd>X56</appEventCd>");
                    selfStringBuffer.append("                   <appSendDateTime>20220614155630</appSendDateTime>");
                    selfStringBuffer.append("                   <appRecvDateTime>20220614155630</appRecvDateTime>");
                    selfStringBuffer.append("                   <appLgDateTime>20220614155630</appLgDateTime>");
                    selfStringBuffer.append("                   <appNstepUserId>116833564</appNstepUserId>");
                    selfStringBuffer.append("                   <appOrderId/>");
                    selfStringBuffer.append("               </bizHeader>");
                    selfStringBuffer.append("               <commHeader>");
                    selfStringBuffer.append("                   <globalNo>9114053920181029150101001</globalNo>");
                    selfStringBuffer.append("                   <encYn/>");
                    selfStringBuffer.append("                   <responseType>N</responseType>");
                    selfStringBuffer.append("                   <responseCode></responseCode>");
                    selfStringBuffer.append("                   <responseLogcd/>");
                    selfStringBuffer.append("                   <responseTitle/>");
                    selfStringBuffer.append("                   <responseBasic></responseBasic>");
                    selfStringBuffer.append("                   <langCode/>");
                    selfStringBuffer.append("                   <filler/>");
                    selfStringBuffer.append("               </commHeader>");
                    selfStringBuffer.append("               <outDto>");
                    selfStringBuffer.append("                   <osstOrdNo>2022061012345</osstOrdNo>");
                    selfStringBuffer.append("                   <rsltCd>S</rsltCd>");
                    selfStringBuffer.append("               </outDto>");
                    selfStringBuffer.append("           </return>");
                    selfStringBuffer.append("       </ns2:usimChgPrcResponse>");
                    selfStringBuffer.append("   </soap:Body>");
                    selfStringBuffer.append("</soap:Envelope>");
                    responseXml = selfStringBuffer.toString();
                } else if(EVENT_CODE_PRE_SCH.equals(appEventCd)){
                    StringBuffer selfStringBuffer = new StringBuffer();
                    selfStringBuffer.append("<soap:Envelope xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>");
                    selfStringBuffer.append("   <soap:Body>");
                    selfStringBuffer.append("       <ns2:osstNpPrePrcResponse xmlns:ns2='http://osst.so.itl.mvno.kt.com/'>");
                    selfStringBuffer.append("           <return>");
                    selfStringBuffer.append("               <bizHeader>");
                    selfStringBuffer.append("                   <appEntrPrsnId>KIS</appEntrPrsnId>");
                    selfStringBuffer.append("                   <appAgncCd>AA11071</appAgncCd>");
                    selfStringBuffer.append("                   <appEventCd>ST1</appEventCd>");
                    selfStringBuffer.append("                   <appSendDateTime>20240314164237</appSendDateTime>");
                    selfStringBuffer.append("                   <appRecvDateTime>20240314164237</appRecvDateTime>");
                    selfStringBuffer.append("                   <appLgDateTime>20240314164237</appLgDateTime>");
                    selfStringBuffer.append("                   <appNstepUserId>6833564</appNstepUserId>");
                    selfStringBuffer.append("                   <appOrderId/>");
                    selfStringBuffer.append("               </bizHeader>");
                    selfStringBuffer.append("               <commHeader>");
                    selfStringBuffer.append("                   <globalNo>TEST-LSH-0818-08185</globalNo>");
                    selfStringBuffer.append("                   <encYn/>");
                    selfStringBuffer.append("                   <responseType>N</responseType>");
                    //selfStringBuffer.append("                   <responseType>E</responseType>");
                    selfStringBuffer.append("                   <responseCode/>");
                    //selfStringBuffer.append("                   <responseCode>오류코드</responseCode>");
                    selfStringBuffer.append("                   <responseLogcd/>");
                    selfStringBuffer.append("                   <responseTitle/>");
                    selfStringBuffer.append("                   <responseBasic/>");
                    //selfStringBuffer.append("                   <responseBasic>오류메세지</responseBasic>");
                    selfStringBuffer.append("                   <langCode/>");
                    selfStringBuffer.append("                   <filler/>");
                    selfStringBuffer.append("               </commHeader>");
                    selfStringBuffer.append("               <custId/>");
                    selfStringBuffer.append("               <svcCntrNo/>");
                    selfStringBuffer.append("               <nstepGlobalId/>");
                    selfStringBuffer.append("               <rsltDt>20240314164235</rsltDt>");
                    selfStringBuffer.append("               <rsltCd>0000</rsltCd>");
                    //selfStringBuffer.append("               <rsltCd>3000</rsltCd>");
                    selfStringBuffer.append("               <rsltMsg>처리성공</rsltMsg>");
                    //selfStringBuffer.append("               <rsltMsg>개통 불가 고객입니다</rsltMsg>");
                    selfStringBuffer.append("               <mvnoOrdNo>00000000955349</mvnoOrdNo>");
                    selfStringBuffer.append("               <prgrStatCd>PC2</prgrStatCd>");
                    //selfStringBuffer.append("               <prgrStatCd>PC1</prgrStatCd>");
                    //selfStringBuffer.append("               <prgrStatCd>PC0</prgrStatCd>");
                    selfStringBuffer.append("           </return>");
                    selfStringBuffer.append("       </ns2:osstNpPrePrcResponse>");
                    selfStringBuffer.append("   </soap:Body>");
                    selfStringBuffer.append("</soap:Envelope>");
                    responseXml = selfStringBuffer.toString();
                } else if(EVENT_CODE_NP_ARREE.equals(appEventCd)){
                    StringBuffer selfStringBuffer = new StringBuffer();
                    selfStringBuffer.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:osstNpBfacAgreeRpyRetvResponse xmlns:ns2=\"http://osst.so.itl.mvno.kt.com/\"><return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>VKI0011</appAgncCd><appEventCd>NP3</appEventCd><appSendDateTime>20250502102707</appSendDateTime><appRecvDateTime>20250502102707</appRecvDateTime><appLgDateTime>20250502102707</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId /></bizHeader><commHeader><globalNo>9122533020250502102707379</globalNo><encYn /><responseType>N</responseType><responseCode /><responseLogcd /><responseTitle /><responseBasic /><langCode /><filler /></commHeader><outDto><rsltCd>S</rsltCd><rsltMsg>변경전사업자 사전동의 장애입니다. 사전동의 SKIP 후 인증요청 하십시요.</rsltMsg></outDto></return></ns2:osstNpBfacAgreeRpyRetvResponse></soap:Body></soap:Envelope>");
                    //selfStringBuffer.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:osstNpBfacAgreeRpyRetvResponse xmlns:ns2=\"http://osst.so.itl.mvno.kt.com/\"><return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>VKI0011</appAgncCd><appEventCd>NP3</appEventCd><appSendDateTime>20250502170134</appSendDateTime><appRecvDateTime>20250502170133</appRecvDateTime><appLgDateTime>20250502170133</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId /></bizHeader><commHeader><globalNo>9122533020250502170133880</globalNo><encYn /><responseType>N</responseType><responseCode /><responseLogcd /><responseTitle /><responseBasic /><langCode /><filler /></commHeader><outDto><rsltCd>S</rsltCd><rsltMsg>성공</rsltMsg></outDto></return></ns2:osstNpBfacAgreeRpyRetvResponse></soap:Body></soap:Envelope>");
                    //selfStringBuffer.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:osstNpBfacAgreeRpyRetvResponse xmlns:ns2=\"http://osst.so.itl.mvno.kt.com/\"><return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>VKI0011</appAgncCd><appEventCd>NP3</appEventCd><appSendDateTime>20250502170134</appSendDateTime><appRecvDateTime>20250502170133</appRecvDateTime><appLgDateTime>20250502170133</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId /></bizHeader><commHeader><globalNo>9122533020250502170133880</globalNo><encYn /><responseType>E</responseType><responseCode /><responseLogcd /><responseTitle /><responseBasic>오류메세지</responseBasic><langCode /><filler /></commHeader></return></ns2:osstNpBfacAgreeRpyRetvResponse></soap:Body></soap:Envelope>");
                    responseXml = selfStringBuffer.toString();
                } else {
                    vo.setSuccess(true);
                    result = true;
                    return result ;
                }

                // *********************** STG 환경 강제로 성공 처리 시작
            /*} else if("STG".equals(serverLocation)) {

                    String appEventCd = param.get("appEventCd");
                    if (EVENT_CODE_NP_PRE_CHECK.equals(appEventCd)) {
                        StringBuffer selfStringBuffer = new StringBuffer();
                        selfStringBuffer.append("<soap:Envelope xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>");
                        selfStringBuffer.append("   <soap:Body>");
                        selfStringBuffer.append("      <ns2:osstNpBfacAgreeResponse xmlns:ns2='http://osst.so.itl.mvno.kt.com/'>");
                        selfStringBuffer.append("         <return>");
                        selfStringBuffer.append("            <bizHeader>");
                        selfStringBuffer.append("               <appEntrPrsnId>INL</appEntrPrsnId>");
                        selfStringBuffer.append("               <appAgncCd>AA12550</appAgncCd>");
                        selfStringBuffer.append("               <appEventCd>NP1</appEventCd>");
                        selfStringBuffer.append("               <appSendDateTime>20180314152724</appSendDateTime>");
                        selfStringBuffer.append("               <appRecvDateTime>20180314152718</appRecvDateTime>");
                        selfStringBuffer.append("               <appLgDateTime>20180314152718</appLgDateTime>");
                        selfStringBuffer.append("               <appNstepUserId>115149049</appNstepUserId>");
                        selfStringBuffer.append("               <appOrderId/>");
                        selfStringBuffer.append("            </bizHeader>");
                        selfStringBuffer.append("            <commHeader>");
                        selfStringBuffer.append("               <globalNo>TEST-INL-0314-10009</globalNo>");
                        selfStringBuffer.append("               <encYn/>");
                        //selfStringBuffer.append("               <responseType>E</responseType>");
                        selfStringBuffer.append("               <responseType>N</responseType>");
                        selfStringBuffer.append("               <responseCode>ITL_SST_E1018</responseCode>");
                        selfStringBuffer.append("               <responseLogcd/>");
                        selfStringBuffer.append("               <responseTitle/>");
                        //selfStringBuffer.append("               <responseBasic>가입제한자일 경우 개통이  불가능합니다. </responseBasic>");
                        selfStringBuffer.append("               <responseBasic>오류 메세지 처리... . </responseBasic>");
                        selfStringBuffer.append("               <langCode/>");
                        selfStringBuffer.append("               <filler/>");
                        selfStringBuffer.append("            </commHeader>");
                        selfStringBuffer.append("         </return>");
                        selfStringBuffer.append("      </ns2:osstNpBfacAgreeResponse>");
                        selfStringBuffer.append("   </soap:Body>");
                        selfStringBuffer.append("</soap:Envelope>");
                        responseXml = selfStringBuffer.toString();

                    } else if (EVENT_CODE_PRE_CHECK.equals(appEventCd)) {
                        StringBuffer selfStringBuffer = new StringBuffer();
                        selfStringBuffer.append("<soap:Envelope xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>");
                        selfStringBuffer.append("   <soap:Body>");
                        selfStringBuffer.append("       <ns2:osstNpPrePrcResponse xmlns:ns2='http://osst.so.itl.mvno.kt.com/'>");
                        selfStringBuffer.append("           <return>");
                        selfStringBuffer.append("               <bizHeader>");
                        selfStringBuffer.append("                   <appEntrPrsnId>KIS</appEntrPrsnId>");
                        selfStringBuffer.append("                   <appAgncCd>VKI0317</appAgncCd>");
                        selfStringBuffer.append("                   <appEventCd>PC0</appEventCd>");
                        selfStringBuffer.append("                   <appSendDateTime>20180502164208</appSendDateTime>");
                        selfStringBuffer.append("                   <appRecvDateTime>20180502164207</appRecvDateTime>");
                        selfStringBuffer.append("                   <appLgDateTime>20180502164207</appLgDateTime>");
                        selfStringBuffer.append("                   <appNstepUserId>91060728</appNstepUserId>");
                        selfStringBuffer.append("                   <appOrderId></appOrderId>");
                        selfStringBuffer.append("               </bizHeader>");
                        selfStringBuffer.append("               <commHeader>");
                        selfStringBuffer.append("                   <globalNo>9106072820180502164203662</globalNo>");
                        selfStringBuffer.append("                   <encYn></encYn>");
                        //selfStringBuffer.append("                   <responseType>E</responseType>");
                        selfStringBuffer.append("                   <responseType>N</responseType>");
                        selfStringBuffer.append("                   <responseCode>S</responseCode>");
                        selfStringBuffer.append("                   <responseLogcd></responseLogcd>");
                        selfStringBuffer.append("                   <responseTitle></responseTitle>");
                        selfStringBuffer.append("                   <responseBasic></responseBasic>");
                        selfStringBuffer.append("                   <langCode></langCode>");
                        selfStringBuffer.append("                   <filler></filler>");
                        selfStringBuffer.append("               </commHeader>");
                        selfStringBuffer.append("               <osstOrdNo>20170818102466</osstOrdNo>");
                        selfStringBuffer.append("               <rsltCd>S</rsltCd>");
                        selfStringBuffer.append("               <rsltMsg/>");
                        selfStringBuffer.append("           </return>");
                        selfStringBuffer.append("       </ns2:osstNpPrePrcResponse>");
                        selfStringBuffer.append("   </soap:Body>");
                        selfStringBuffer.append("</soap:Envelope>");
                        responseXml = selfStringBuffer.toString();

                    } else if (EVENT_CODE_REQ_OPEN.equals(appEventCd)) {
                        StringBuffer selfStringBuffer = new StringBuffer();
                        selfStringBuffer.append("<soap:Envelope xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>");
                        selfStringBuffer.append("   <soap:Body>");
                        selfStringBuffer.append("      <ns2:osstNpBfacAgreeResponse xmlns:ns2='http://osst.so.itl.mvno.kt.com/'>");
                        selfStringBuffer.append("         <return>");
                        selfStringBuffer.append("            <bizHeader>");
                        selfStringBuffer.append("               <appEntrPrsnId>INL</appEntrPrsnId>");
                        selfStringBuffer.append("               <appAgncCd>AA12550</appAgncCd>");
                        selfStringBuffer.append("               <appEventCd>NP1</appEventCd>");
                        selfStringBuffer.append("               <appSendDateTime>20180314152724</appSendDateTime>");
                        selfStringBuffer.append("               <appRecvDateTime>20180314152718</appRecvDateTime>");
                        selfStringBuffer.append("               <appLgDateTime>20180314152718</appLgDateTime>");
                        selfStringBuffer.append("               <appNstepUserId>115149049</appNstepUserId>");
                        selfStringBuffer.append("               <appOrderId/>");
                        selfStringBuffer.append("            </bizHeader>");
                        selfStringBuffer.append("            <commHeader>");
                        selfStringBuffer.append("               <globalNo>TEST-INL-0314-10009</globalNo>");
                        selfStringBuffer.append("               <encYn/>");
//                        selfStringBuffer.append("               <responseType>E</responseType>");
                        selfStringBuffer.append("               <responseType>N</responseType>");
                        selfStringBuffer.append("               <responseCode>S</responseCode>");
                        selfStringBuffer.append("               <responseLogcd/>");
                        selfStringBuffer.append("               <responseTitle/>");
                        selfStringBuffer.append("               <responseBasic></responseBasic>");
                        selfStringBuffer.append("               <langCode/>");
                        selfStringBuffer.append("               <filler/>");
                        selfStringBuffer.append("            </commHeader>");
                        selfStringBuffer.append("         </return>");
                        selfStringBuffer.append("      </ns2:osstNpBfacAgreeResponse>");
                        selfStringBuffer.append("   </soap:Body>");
                        selfStringBuffer.append("</soap:Envelope>");
                        responseXml = selfStringBuffer.toString();

                    } else if (EVENT_CODE_SEARCH_NUMBER.equals(appEventCd)) {
                        StringBuffer selfStringBuffer = new StringBuffer();
                        selfStringBuffer.append("<soap:Envelope xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>");
                        selfStringBuffer.append("   <soap:Body>");
                        selfStringBuffer.append("      <ns2:inqrOsstSvcNoInfoResponse xmlns:ns2='http://osst.so.itl.mvno.kt.com/'>");
                        selfStringBuffer.append("         <return>");
                        selfStringBuffer.append("            <bizHeader>");
                        selfStringBuffer.append("               <appEntrPrsnId>ENX</appEntrPrsnId>");
                        selfStringBuffer.append("               <appAgncCd>AA11071</appAgncCd>");
                        selfStringBuffer.append("               <appEventCd>NU1</appEventCd>");
                        selfStringBuffer.append("               <appSendDateTime>20170818130000</appSendDateTime>");
                        selfStringBuffer.append("               <appRecvDateTime>20170818154350</appRecvDateTime>");
                        selfStringBuffer.append("               <appLgDateTime>20170818154350</appLgDateTime>");
                        selfStringBuffer.append("               <appNstepUserId>6833564</appNstepUserId>");
                        selfStringBuffer.append("               <appOrderId/>");
                        selfStringBuffer.append("            </bizHeader>");
                        selfStringBuffer.append("            <commHeader>");
                        selfStringBuffer.append("               <globalNo>TEST-LSH-0818-08182</globalNo>");
                        selfStringBuffer.append("               <encYn/>");
                        selfStringBuffer.append("               <responseType>N</responseType>");
                        selfStringBuffer.append("               <responseCode/>");
                        selfStringBuffer.append("               <responseLogcd/>");
                        selfStringBuffer.append("               <responseTitle/>");
                        selfStringBuffer.append("               <responseBasic/>");
                        selfStringBuffer.append("               <langCode/>");
                        selfStringBuffer.append("               <filler/>");
                        selfStringBuffer.append("            </commHeader>");
                        selfStringBuffer.append("            <outDto>");
                        selfStringBuffer.append("               <lastPageYn>N</lastPageYn>");
                        selfStringBuffer.append("               <svcNoList>");
                        selfStringBuffer.append("                  <asgnAgncId/>");
                        selfStringBuffer.append("                  <encdTlphNo>kodX85tAG1a/YTeS1ftk8g==</encdTlphNo>");
                        selfStringBuffer.append("                  <fvrtnoAqcsPsblYn>N</fvrtnoAqcsPsblYn>");
                        selfStringBuffer.append("                  <openSvcIndCd>02</openSvcIndCd>");
                        selfStringBuffer.append("                  <rsrvCustNo>636719750</rsrvCustNo>");
                        selfStringBuffer.append("                  <statMntnEndPrrnDate/>");
                        selfStringBuffer.append("                  <tlphNo>01029013860</tlphNo>");
                        selfStringBuffer.append("                  <tlphNoChrcCd>GEN</tlphNoChrcCd>");
                        selfStringBuffer.append("                  <tlphNoOwnCmncCmpnCd>KTF</tlphNoOwnCmncCmpnCd>");
                        selfStringBuffer.append("                  <tlphNoStatCd>AA</tlphNoStatCd>");
                        selfStringBuffer.append("                  <tlphNoStatChngDt>20130625</tlphNoStatChngDt>");
                        selfStringBuffer.append("                  <tlphNoUseCd>R</tlphNoUseCd>");
                        selfStringBuffer.append("                  <tlphNoUseMntCd>GRN</tlphNoUseMntCd>");
                        selfStringBuffer.append("               </svcNoList>");
                        selfStringBuffer.append("               <svcNoList>");
                        selfStringBuffer.append("                  <asgnAgncId/>");
                        selfStringBuffer.append("                  <encdTlphNo>m8fnSG6cMIe/YTeS1ftk8g==</encdTlphNo>");
                        selfStringBuffer.append("                  <fvrtnoAqcsPsblYn>N</fvrtnoAqcsPsblYn>");
                        selfStringBuffer.append("                  <openSvcIndCd>02</openSvcIndCd>");
                        selfStringBuffer.append("                  <rsrvCustNo>636719750</rsrvCustNo>");
                        selfStringBuffer.append("                  <statMntnEndPrrnDate/>");
                        selfStringBuffer.append("                  <tlphNo>01029053860</tlphNo>");
                        selfStringBuffer.append("                  <tlphNoChrcCd>GEN</tlphNoChrcCd>");
                        selfStringBuffer.append("                  <tlphNoOwnCmncCmpnCd>KTF</tlphNoOwnCmncCmpnCd>");
                        selfStringBuffer.append("                  <tlphNoStatCd>AA</tlphNoStatCd>");
                        selfStringBuffer.append("                  <tlphNoStatChngDt>20170720</tlphNoStatChngDt>");
                        selfStringBuffer.append("                  <tlphNoUseCd>R</tlphNoUseCd>");
                        selfStringBuffer.append("                  <tlphNoUseMntCd>GRN</tlphNoUseMntCd>");
                        selfStringBuffer.append("               </svcNoList>");
                        selfStringBuffer.append("               <svcNoList>");
                        selfStringBuffer.append("                  <asgnAgncId/>");
                        selfStringBuffer.append("                  <encdTlphNo>kUajG95X/d6/YTeS1ftk8g==</encdTlphNo>");
                        selfStringBuffer.append("                  <fvrtnoAqcsPsblYn>N</fvrtnoAqcsPsblYn>");
                        selfStringBuffer.append("                  <openSvcIndCd>02</openSvcIndCd>");
                        selfStringBuffer.append("                  <rsrvCustNo>636719750</rsrvCustNo>");
                        selfStringBuffer.append("                  <statMntnEndPrrnDate/>");
                        selfStringBuffer.append("                  <tlphNo>01029033860</tlphNo>");
                        selfStringBuffer.append("                  <tlphNoChrcCd>GEN</tlphNoChrcCd>");
                        selfStringBuffer.append("                  <tlphNoOwnCmncCmpnCd>KTF</tlphNoOwnCmncCmpnCd>");
                        selfStringBuffer.append("                  <tlphNoStatCd>AA</tlphNoStatCd>");
                        selfStringBuffer.append("                  <tlphNoStatChngDt>20170720</tlphNoStatChngDt>");
                        selfStringBuffer.append("                  <tlphNoUseCd>R</tlphNoUseCd>");
                        selfStringBuffer.append("                  <tlphNoUseMntCd>GRN</tlphNoUseMntCd>");
                        selfStringBuffer.append("               </svcNoList>  ");
                        selfStringBuffer.append("            </outDto>");
                        selfStringBuffer.append("         </return>");
                        selfStringBuffer.append("      </ns2:inqrOsstSvcNoInfoResponse>");
                        selfStringBuffer.append("   </soap:Body>");
                        selfStringBuffer.append("</soap:Envelope> ");
                        responseXml = selfStringBuffer.toString();
                    } else if (EVENT_CODE_NUMBER_REG.equals(appEventCd)) {
                        StringBuffer selfStringBuffer = new StringBuffer();
                        selfStringBuffer.append("<soap:Envelope xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>");
                        selfStringBuffer.append("   <soap:Body>");
                        selfStringBuffer.append("      <ns2:resvOsstTlphNoResponse xmlns:ns2='http://osst.so.itl.mvno.kt.com/'>");
                        selfStringBuffer.append("         <return>");
                        selfStringBuffer.append("            <bizHeader>");
                        selfStringBuffer.append("               <appEntrPrsnId>ENX</appEntrPrsnId>");
                        selfStringBuffer.append("               <appAgncCd>AA11071</appAgncCd>");
                        selfStringBuffer.append("               <appEventCd>NU2</appEventCd>");
                        selfStringBuffer.append("               <appSendDateTime>20170818130000</appSendDateTime>");
                        selfStringBuffer.append("               <appRecvDateTime>20170818154821</appRecvDateTime>");
                        selfStringBuffer.append("               <appLgDateTime>20170818154821</appLgDateTime>");
                        selfStringBuffer.append("               <appNstepUserId>6833564</appNstepUserId>");
                        selfStringBuffer.append("               <appOrderId/>");
                        selfStringBuffer.append("            </bizHeader>");
                        selfStringBuffer.append("            <commHeader>");
                        selfStringBuffer.append("               <globalNo>TEST-LSH-0818-08183</globalNo>");
                        selfStringBuffer.append("               <encYn/>");
                        selfStringBuffer.append("               <responseType>N</responseType>");
                        selfStringBuffer.append("               <responseCode/>");
                        selfStringBuffer.append("               <responseLogcd/>");
                        selfStringBuffer.append("               <responseTitle/>");
                        selfStringBuffer.append("               <responseBasic/>");
                        selfStringBuffer.append("               <langCode/>");
                        selfStringBuffer.append("               <filler/>");
                        selfStringBuffer.append("            </commHeader>");
                        selfStringBuffer.append("         </return>");
                        selfStringBuffer.append("      </ns2:resvOsstTlphNoResponse>");
                        selfStringBuffer.append("   </soap:Body>");
                        selfStringBuffer.append("</soap:Envelope>");
                        responseXml = selfStringBuffer.toString();
                    } else {
                        vo.setSuccess(true);
                        result = true;
                        return result ;
                    }*/
                // *********************** STG 환경 강제로 성공 처리 끝


            } else {
                responseXml = HttpClientUtil.post(callUrl,data, "UTF-8",timeout);
            }



            if (responseXml == null || responseXml.isEmpty()) {
                result = false;
                throw new McpMplatFormException(MPLATFORM_RESPONEXML_EMPTY_EXCEPTION);
            } else {
                if( vo != null){
                    result = true;
                    vo.setResponseXml(responseXml);
                    vo.toResponseParse();
                }
            }

        } catch (SelfServiceException e) {
            logger.error("SelfServiceException",e);
            throw e;
        } catch (SocketTimeoutException e){

            // OSST 연동 타임아웃 이력 INSERT
            MpErrVO errVO= new MpErrVO(param.get("resNo"), param.get("appEventCd"));
            errVO.setPrntsContractNo(param.get("prntsContractNo"));
            errVO.setCustomerId(param.get("custNo"));
            errVO.setErrInfo(e);
            mplatFormOsstDao.insertOsstErrLog(errVO);

            logger.error("SocketTimeoutException",e);
            throw e;
        } catch (McpMplatFormException e){
            throw e;
        }  catch (Exception e) {
            logger.error("Exception",e);
        }

        return result ;
    }




    private  NameValuePair[] convertParam(HashMap<String, String> params){
        Set<String> keySet = params.keySet();
        NameValuePair[] data = new NameValuePair[keySet.size()];
        int i =0;
        for(String key : keySet){
            String rtnStr = "";
            try {
                rtnStr = URLEncoder.encode(params.get(key).toString(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error("UnsupportedEncodingException",e);
            }

            data[i]= new NameValuePair(key, rtnStr);
            i++;
        }

        //result = result.concat(key + "=" + param.get(key));

        return data;
    }








}
