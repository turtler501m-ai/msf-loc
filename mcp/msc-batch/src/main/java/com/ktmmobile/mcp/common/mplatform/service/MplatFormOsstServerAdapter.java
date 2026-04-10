package com.ktmmobile.mcp.common.mplatform.service;

import com.ktmmobile.mcp.common.exception.McpMplatFormException;
import com.ktmmobile.mcp.common.exception.SelfServiceException;
import com.ktmmobile.mcp.common.mplatform.dto.CommonXmlVO;
import com.ktmmobile.mcp.common.util.HttpClientUtil;
import org.apache.commons.httpclient.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Set;

import static com.ktmmobile.mcp.common.constant.Constants.*;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.MPLATFORM_RESPONEXML_EMPTY_EXCEPTION;

@Service
public class MplatFormOsstServerAdapter{

    private static final Logger logger = LoggerFactory.getLogger(MplatFormOsstServerAdapter.class);

    @Value("${osst.simple.open.url}")
    private String osstSimpleOpenUrl;

    @Value("${SERVER_NAME}")
    private String serverName;

    /**
     * 설명 : OSST 서비스 호출
     * @Date : 2024.05.09
     */
    public boolean callService(HashMap<String,String> param, CommonXmlVO vo, int timeout) throws SelfServiceException, SocketTimeoutException {

        boolean result = false;
        String responseXml = "";

        try {
            String callUrl = osstSimpleOpenUrl;

            NameValuePair[] data = convertParam(param);

            for(NameValuePair key : data){
                logger.error(key.getName() + "#====>" + key.getValue());
            }

            if("LOCAL".equals(serverName)) {
                //로컬에서 강제로 성공 처리
                String appEventCd = param.get("appEventCd");
                if(EVENT_CODE_PRE_CHECK.equals(appEventCd)){
                    responseXml = this.getResXmlMessagePC0();
                }else if(EVENT_CODE_REQ_OPEN.equals(appEventCd)){
                    responseXml = this.getResXmlMessageOP0();
                }else if(EVENT_CODE_SEARCH_NUMBER.equals(appEventCd)){
                    responseXml = this.getResXmlMessageNU1();
                }else if(EVENT_CODE_NUMBER_REG.equals(appEventCd)){
                    responseXml = this.getResXmlMessageNU2();
                }else{
                    vo.setSuccess(true);
                    result= true;
                    return result;
                }
            }else{
                // OSST 서비스 호출
                responseXml = HttpClientUtil.post(callUrl, data, "UTF-8", timeout);
            }

            if(responseXml.isEmpty()){
                result = false;
                throw new McpMplatFormException(MPLATFORM_RESPONEXML_EMPTY_EXCEPTION);
            }else{
                if(vo != null){
                    result = true;
                    vo.setResponseXml(responseXml);
                    vo.toResponseParse();
                }
            }

        }catch(SelfServiceException e) {
            logger.error("SelfServiceException",e);
            throw e;
        }catch(SocketTimeoutException e){
            logger.error("SocketTimeoutException",e);
            throw e;
        }catch(McpMplatFormException e){
            throw e;
        }catch(Exception e) {
            logger.error("Exception",e);
        }

        return result ;
    }

    /**
     * 설명 : OSST 서비스 호출 파라미터 전처리
     * @Date : 2024.05.09
     */
    private NameValuePair[] convertParam(HashMap<String, String> params){
        Set<String> keySet = params.keySet();
        NameValuePair[] data = new NameValuePair[keySet.size()];

        int i =0;
        for(String key : keySet){

            String rtnStr = "";
            try {
                rtnStr = URLEncoder.encode(params.get(key).toString(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error("UnsupportedEncodingException", e);
            }

            data[i]= new NameValuePair(key, rtnStr);
            i++;
        }

        return data;
    }

    /**
     * 설명 : PC0 응답 XML 강제 생성 (LOCAL)
     * @Date : 2024.05.09
     */
    private String getResXmlMessagePC0(){

        StringBuffer selfStringBuffer = new StringBuffer();
        selfStringBuffer.append("<soap:Envelope xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>");
        selfStringBuffer.append("   <soap:Body>");
        selfStringBuffer.append("       <ns2:osstPrePrcResponse xmlns:ns2='http://osst.so.itl.mvno.kt.com/'>");
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
        //selfStringBuffer.append("                   <responseType>E</responseType>");
        selfStringBuffer.append("                   <responseCode></responseCode>");
        //selfStringBuffer.append("                   <responseCode>로컬 PC0 실패 코드</responseCode>");
        selfStringBuffer.append("                   <responseLogcd></responseLogcd>");
        selfStringBuffer.append("                   <responseTitle></responseTitle>");
        selfStringBuffer.append("                   <responseBasic></responseBasic>");
        //selfStringBuffer.append("                   <responseBasic>로컬 PC0 실패 메세지</responseBasic>");
        selfStringBuffer.append("                   <langCode></langCode>");
        selfStringBuffer.append("                   <filler></filler>");
        selfStringBuffer.append("               </commHeader>");
        selfStringBuffer.append("               <osstOrdNo>20170818102466</osstOrdNo>");
        selfStringBuffer.append("               <rsltCd>S</rsltCd>");
        //selfStringBuffer.append("               <rsltCd>F</rsltCd>");
        selfStringBuffer.append("               <rsltMsg/>");
        selfStringBuffer.append("           </return>");
        selfStringBuffer.append("       </ns2:osstPrePrcResponse>");
        selfStringBuffer.append("   </soap:Body>");
        selfStringBuffer.append("</soap:Envelope>");

        return selfStringBuffer.toString();
    }

    /**
     * 설명 : OP0 응답 XML 강제 생성 (LOCAL)
     * @Date : 2024.05.09
     */

    private String getResXmlMessageOP0(){
        StringBuffer selfStringBuffer = new StringBuffer();
        selfStringBuffer.append("<soap:Envelope xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>");
        selfStringBuffer.append("   <soap:Body>");
        selfStringBuffer.append("      <ns2:osstOpenPrcResponse xmlns:ns2='http://osst.so.itl.mvno.kt.com/'>");
        selfStringBuffer.append("         <return>");
        selfStringBuffer.append("            <bizHeader>");
        selfStringBuffer.append("               <appEntrPrsnId>INL</appEntrPrsnId>");
        selfStringBuffer.append("               <appAgncCd>AA12550</appAgncCd>");
        selfStringBuffer.append("               <appEventCd>OP0</appEventCd>");
        selfStringBuffer.append("               <appSendDateTime>20180314152724</appSendDateTime>");
        selfStringBuffer.append("               <appRecvDateTime>20180314152718</appRecvDateTime>");
        selfStringBuffer.append("               <appLgDateTime>20180314152718</appLgDateTime>");
        selfStringBuffer.append("               <appNstepUserId>115149049</appNstepUserId>");
        selfStringBuffer.append("               <appOrderId/>");
        selfStringBuffer.append("            </bizHeader>");
        selfStringBuffer.append("            <commHeader>");
        selfStringBuffer.append("               <globalNo>TEST-INL-0314-10009</globalNo>");
        selfStringBuffer.append("               <encYn/>");
        selfStringBuffer.append("               <responseType>N</responseType>");
        //selfStringBuffer.append("               <responseType>E</responseType>");
        selfStringBuffer.append("               <responseCode></responseCode>");
        //selfStringBuffer.append("               <responseCode>로컬 OP0 실패 코드</responseCode>");
        selfStringBuffer.append("               <responseLogcd/>");
        selfStringBuffer.append("               <responseTitle/>");
        selfStringBuffer.append("               <responseBasic></responseBasic>");
        //selfStringBuffer.append("               <responseBasic>로컬 OP0 실패 메세지</responseBasic>");
        selfStringBuffer.append("               <langCode/>");
        selfStringBuffer.append("               <filler/>");
        selfStringBuffer.append("            </commHeader>");
        selfStringBuffer.append("            <osstOrdNo>20170818102466</osstOrdNo>");
        selfStringBuffer.append("            <rsltCd>S</rsltCd>");
        //selfStringBuffer.append("          <rsltCd>F</rsltCd>");
        selfStringBuffer.append("            <rsltMsg/>");
        selfStringBuffer.append("         </return>");
        selfStringBuffer.append("      </ns2:osstOpenPrcResponse>");
        selfStringBuffer.append("   </soap:Body>");
        selfStringBuffer.append("</soap:Envelope>");
        return selfStringBuffer.toString();
    }

    /**
     * 설명 : NU1 응답 XML 강제 생성 (LOCAL)
     * @Date : 2024.05.09
     */
    private String getResXmlMessageNU1(){
        StringBuffer selfStringBuffer = new StringBuffer();

        selfStringBuffer.append("<soap:Envelope xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>");
        selfStringBuffer.append("   <soap:Body>");
        selfStringBuffer.append("       <ns2:inqrOsstSvcNoInfoResponse xmlns:ns2='http://osst.so.itl.mvno.kt.com/'>");
        selfStringBuffer.append("         <return>");
        selfStringBuffer.append("            <bizHeader>");
        selfStringBuffer.append("               <appEntrPrsnId>INL</appEntrPrsnId>");
        selfStringBuffer.append("               <appAgncCd>AA12550</appAgncCd>");
        selfStringBuffer.append("               <appEventCd>NU1</appEventCd>");
        selfStringBuffer.append("               <appSendDateTime>20180314152724</appSendDateTime>");
        selfStringBuffer.append("               <appRecvDateTime>20180314152718</appRecvDateTime>");
        selfStringBuffer.append("               <appLgDateTime>20180314152718</appLgDateTime>");
        selfStringBuffer.append("               <appNstepUserId>115149049</appNstepUserId>");
        selfStringBuffer.append("               <appOrderId/>");
        selfStringBuffer.append("            </bizHeader>");
        selfStringBuffer.append("            <commHeader>");
        selfStringBuffer.append("               <globalNo>TEST-INL-0314-10009</globalNo>");
        selfStringBuffer.append("               <encYn/>");
        selfStringBuffer.append("               <responseType>N</responseType>");
        //selfStringBuffer.append("               <responseType>E</responseType>");
        selfStringBuffer.append("               <responseCode></responseCode>");
        //selfStringBuffer.append("               <responseCode>로컬 NU1 실패 코드</responseCode>");
        selfStringBuffer.append("               <responseLogcd/>");
        selfStringBuffer.append("               <responseTitle/>");
        selfStringBuffer.append("               <responseBasic></responseBasic>");
        //selfStringBuffer.append("               <responseBasic>로컬 NU1 실패 메세지</responseBasic>");
        selfStringBuffer.append("               <langCode/>");
        selfStringBuffer.append("               <filler/>");
        selfStringBuffer.append("            </commHeader>");
        selfStringBuffer.append("            <outDto>");
        selfStringBuffer.append("               <lastPageYn>N</lastPageYn>");
        selfStringBuffer.append("               <svcNoList>");
        selfStringBuffer.append("                   <asgnAgncId/>");
        selfStringBuffer.append("                   <encdTlphNo>kodX85tAG1a/YTeS1ftk8g==</encdTlphNo>");
        selfStringBuffer.append("                   <fvrtnoAqcsPsblYn>N</fvrtnoAqcsPsblYn>");
        selfStringBuffer.append("                   <openSvcIndCd>02</openSvcIndCd>");
        selfStringBuffer.append("                   <rsrvCustNo>636719750</rsrvCustNo>");
        selfStringBuffer.append("                   <statMntnEndPrrnDate/>");
        selfStringBuffer.append("                   <tlphNo>01025331384</tlphNo>");
        selfStringBuffer.append("                   <tlphNoChrcCd>GEN</tlphNoChrcCd>");
        selfStringBuffer.append("                   <tlphNoOwnCmncCmpnCd>KTF</tlphNoOwnCmncCmpnCd>");
        selfStringBuffer.append("                   <tlphNoStatCd>AA</tlphNoStatCd>");
        selfStringBuffer.append("                   <tlphNoStatChngDt>20130625</tlphNoStatChngDt>");
        selfStringBuffer.append("                   <tlphNoUseCd>R</tlphNoUseCd>");
        selfStringBuffer.append("                   <tlphNoUseMntCd>GRN</tlphNoUseMntCd>");
        selfStringBuffer.append("               </svcNoList>");
        selfStringBuffer.append("               <svcNoList>");
        selfStringBuffer.append("                   <asgnAgncId/>");
        selfStringBuffer.append("                   <encdTlphNo>m8fnSG6cMIe/YTeS1ftk8g==</encdTlphNo>");
        selfStringBuffer.append("                   <fvrtnoAqcsPsblYn>N</fvrtnoAqcsPsblYn>");
        selfStringBuffer.append("                   <openSvcIndCd>02</openSvcIndCd>");
        selfStringBuffer.append("                   <rsrvCustNo>636719750</rsrvCustNo>");
        selfStringBuffer.append("                   <statMntnEndPrrnDate/>");
        selfStringBuffer.append("                   <tlphNo>01027641384</tlphNo>");
        selfStringBuffer.append("                   <tlphNoChrcCd>GEN</tlphNoChrcCd>");
        selfStringBuffer.append("                   <tlphNoOwnCmncCmpnCd>KTF</tlphNoOwnCmncCmpnCd>");
        selfStringBuffer.append("                   <tlphNoStatCd>AA</tlphNoStatCd>");
        selfStringBuffer.append("                   <tlphNoStatChngDt>20170720</tlphNoStatChngDt>");
        selfStringBuffer.append("                   <tlphNoUseCd>R</tlphNoUseCd>");
        selfStringBuffer.append("                   <tlphNoUseMntCd>GRN</tlphNoUseMntCd>");
        selfStringBuffer.append("               </svcNoList>");
        selfStringBuffer.append("               <svcNoList>");
        selfStringBuffer.append("                   <asgnAgncId/>");
        selfStringBuffer.append("                   <encdTlphNo>kUajG95X/d6/YTeS1ftk8g==</encdTlphNo>");
        selfStringBuffer.append("                   <fvrtnoAqcsPsblYn>N</fvrtnoAqcsPsblYn>");
        selfStringBuffer.append("                   <openSvcIndCd>02</openSvcIndCd>");
        selfStringBuffer.append("                   <rsrvCustNo>636719750</rsrvCustNo>");
        selfStringBuffer.append("                   <statMntnEndPrrnDate/>");
        selfStringBuffer.append("                   <tlphNo>01027761384</tlphNo>");
        selfStringBuffer.append("                   <tlphNoChrcCd>GEN</tlphNoChrcCd>");
        selfStringBuffer.append("                   <tlphNoOwnCmncCmpnCd>KTF</tlphNoOwnCmncCmpnCd>");
        selfStringBuffer.append("                   <tlphNoStatCd>AA</tlphNoStatCd>");
        selfStringBuffer.append("                   <tlphNoStatChngDt>20170720</tlphNoStatChngDt>");
        selfStringBuffer.append("                   <tlphNoUseCd>R</tlphNoUseCd>");
        selfStringBuffer.append("                   <tlphNoUseMntCd>GRN</tlphNoUseMntCd>");
        selfStringBuffer.append("               </svcNoList>");
        selfStringBuffer.append("               <svcNoList>");
        selfStringBuffer.append("                   <asgnAgncId/>");
        selfStringBuffer.append("                   <encdTlphNo>2BWU/A/r5KK/YTeS1ftk8g==</encdTlphNo>");
        selfStringBuffer.append("                   <fvrtnoAqcsPsblYn>N</fvrtnoAqcsPsblYn>");
        selfStringBuffer.append("                   <openSvcIndCd>02</openSvcIndCd>");
        selfStringBuffer.append("                   <rsrvCustNo>636719750</rsrvCustNo>");
        selfStringBuffer.append("                   <statMntnEndPrrnDate/>");
        selfStringBuffer.append("                   <tlphNo>01027921384</tlphNo>");
        selfStringBuffer.append("                   <tlphNoChrcCd>GEN</tlphNoChrcCd>");
        selfStringBuffer.append("                   <tlphNoOwnCmncCmpnCd>KTF</tlphNoOwnCmncCmpnCd>");
        selfStringBuffer.append("                   <tlphNoStatCd>AA</tlphNoStatCd>");
        selfStringBuffer.append("                   <tlphNoStatChngDt>20170719</tlphNoStatChngDt>");
        selfStringBuffer.append("                   <tlphNoUseCd>R</tlphNoUseCd>");
        selfStringBuffer.append("                   <tlphNoUseMntCd>GRN</tlphNoUseMntCd>");
        selfStringBuffer.append("               </svcNoList>");
        selfStringBuffer.append("            </outDto>");
        selfStringBuffer.append("         </return>");
        selfStringBuffer.append("      </ns2:inqrOsstSvcNoInfoResponse>");
        selfStringBuffer.append("   </soap:Body>");
        selfStringBuffer.append("</soap:Envelope>");
        return selfStringBuffer.toString();
    }

    private String getResXmlMessageNU2() {

        StringBuffer selfStringBuffer = new StringBuffer();
        selfStringBuffer.append("<soap:Envelope xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>");
        selfStringBuffer.append("   <soap:Body>");
        selfStringBuffer.append("      <ns2:resvOsstTlphNoResponse xmlns:ns2='http://osst.so.itl.mvno.kt.com/'>");
        selfStringBuffer.append("         <return>");
        selfStringBuffer.append("            <bizHeader>");
        selfStringBuffer.append("               <appEntrPrsnId>INL</appEntrPrsnId>");
        selfStringBuffer.append("               <appAgncCd>AA12550</appAgncCd>");
        selfStringBuffer.append("               <appEventCd>NU2</appEventCd>");
        selfStringBuffer.append("               <appSendDateTime>20180314152724</appSendDateTime>");
        selfStringBuffer.append("               <appRecvDateTime>20180314152718</appRecvDateTime>");
        selfStringBuffer.append("               <appLgDateTime>20180314152718</appLgDateTime>");
        selfStringBuffer.append("               <appNstepUserId>115149049</appNstepUserId>");
        selfStringBuffer.append("               <appOrderId/>");
        selfStringBuffer.append("            </bizHeader>");
        selfStringBuffer.append("            <commHeader>");
        selfStringBuffer.append("               <globalNo>TEST-INL-0314-10009</globalNo>");
        selfStringBuffer.append("               <encYn/>");
        selfStringBuffer.append("               <responseType>N</responseType>");
        //selfStringBuffer.append("               <responseType>E</responseType>");
        selfStringBuffer.append("               <responseCode></responseCode>");
        //selfStringBuffer.append("               <responseCode>로컬 NU2 실패 코드</responseCode>");
        selfStringBuffer.append("               <responseLogcd/>");
        selfStringBuffer.append("               <responseTitle/>");
        selfStringBuffer.append("               <responseBasic></responseBasic>");
        //selfStringBuffer.append("               <responseBasic>로컬 NU2 실패 메세지</responseBasic>");
        selfStringBuffer.append("               <langCode/>");
        selfStringBuffer.append("               <filler/>");
        selfStringBuffer.append("            </commHeader>");
        selfStringBuffer.append("         </return>");
        selfStringBuffer.append("      </ns2:resvOsstTlphNoResponse>");
        selfStringBuffer.append("   </soap:Body>");
        selfStringBuffer.append("</soap:Envelope>");
        return selfStringBuffer.toString();
    }

}
