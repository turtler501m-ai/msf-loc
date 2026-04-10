package com.ktmmobile.mcp.rate.service;


import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.MPLATFORM_RESPONEXML_EMPTY_EXCEPTION;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.SOCKET_TIMEOUT_EXCEPTION;

import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.ktmmobile.mcp.common.mplatform.dto.*;
import org.apache.commons.httpclient.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.McpMplatFormException;
import com.ktmmobile.mcp.common.exception.SelfServiceException;
import com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant;
import com.ktmmobile.mcp.common.util.HttpClientUtil;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.rate.dto.MspRateMstDto;
import com.ktmmobile.mcp.rate.dto.MyPageSearchDto;


@Service
public class RateMplatformSvc {

	private static final Logger logger = LoggerFactory.getLogger(RateMplatformSvc.class);
	
    @Value("${juice.url}")
    private String propertiesService;
    
    @Value("${SERVER_NAME}")
    private String serverLocation;

	@Autowired
	private RateApiSvc rateApiSvc;

    @Value("${juice.json.url}")
    private String mplatformJsonUrl;

    /**
     * x20 부가서비스 조회 연동 규격
     *
     * @param searchVO
     * @return
     * @throws SelfServiceException
     * @throws SocketTimeoutException
     */

    public MpAddSvcInfoDto getAddSvcInfoDto(MyPageSearchDto searchVO) throws SelfServiceException, SocketTimeoutException {
        MpAddSvcInfoDto vo = new MpAddSvcInfoDto();
        HashMap<String, String> param = getParamMap(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId(), searchVO.getUserId(), "X20");

        if ("LOCAL".equals(serverLocation)) {
//            getVo(20, vo);
            callService(param, vo, 30000);
        } else {
        	callService(param, vo, 30000);
        }

        return vo;
    }
    
    /*
     * 엠플랫폼 연동 
     */
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
    
    private HashMap<String, String> getParamMap(String ncn, String ctn, String custId, String userId, String eventCd) throws SelfServiceException, SocketTimeoutException {
        HashMap<String, String> param = new HashMap<String, String>();

        try {
            param.put("ncn", ncn);
            param.put("ctn", ctn);
            param.put("custId", custId);
            param.put("userid", userId);
            param.put("appEventCd", eventCd);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return param;
    }
    
    /**
     * 엠플렛폼 서비스 연동 로그 저장
     * @param param
     */
    private HashMap<String, String> saveMplateSvcLog(HashMap<String,String> param) {

    	HashMap<String, String> tmpParm = param;
    	try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

            tmpParm.put("ip", getClientIp());
            tmpParm.put("url", request.getRequestURI());
            tmpParm.put("mdlInd", getDeviceType());

            logger.info("userid:{}", StringUtil.NVL(tmpParm.get("userid"),""));

        } catch (Exception e) {
            logger.debug("엠플렛폼 연동 정보 저장 오류 : " + e.getMessage());
        }
        return tmpParm;
    }
    
    public String getClientIp()  {
        String clientIp = "";
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        if(request.getHeader("X-Forwarded-For") == null) {
            clientIp = request.getRemoteAddr();
        } else {
            clientIp = request.getHeader("X-Forwarded-For");
            if (clientIp !=null && ! clientIp.equals("") && clientIp.indexOf(",")>-1) {
                clientIp =  clientIp.split("\\,")[0].trim();
            }
        }

        return clientIp;
    }
    
    /**
     * Device 구분 : 모바일 or PC or APP구분
     * @param
     */
    public static String getDeviceType() {
        //[모바일 or PC 구별하기]
        String filter = "iphone|ipod|android|windows ce|blackberry|symbian|windows phone|webos|opera mini|opera mobi|polaris|iemobile|lgtelecom|nokia|sonyericsson|lg|samsung";
        String filters[] = filter.split("\\|");
        String rtnDeviceType = "WEB";

        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

            if(request.getHeader("User-Agent") != null){
                if( request.getHeader("User-Agent").indexOf("M-Mobile-App") != -1) {
                    rtnDeviceType = "APP";
                }else{
                    for(String tmp : filters){
                        if ( request.getHeader("User-Agent").toLowerCase().indexOf(tmp) != -1) {
                            rtnDeviceType = "MOB";
                            break;
                        } else {
                            rtnDeviceType = "WEB";
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new McpCommonException(COMMON_EXCEPTION);
        }
        return rtnDeviceType;
    }
    
    /*
     * URLEncoder
     */
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
     * 요금제변경시에만 사용
     * tobe 요금제 변경시 sr머지
     * x38. 부가서비스 해지
     *
     * @param searchVO
     * @param soc
     * @return
     * @throws
     */
    public RegSvcChgRes moscRegSvcCanChgNeTrace(MyPageSearchDto searchVO, String soc) {
        RegSvcChgRes vo = new RegSvcChgRes();

        boolean isCheck = true;

        //해지 신청 부가서비스 SOC 체크
        MspRateMstDto mspRateMstDto = rateApiSvc.getMspRateMst(soc);
        
        if (mspRateMstDto != null) {
            String onlineCanYn = StringUtil.NVL(mspRateMstDto.getOnlineCanYn(), "");
            if (!onlineCanYn.equals("Y")) {
                vo.setResultCode("E");
                vo.setResultCode("8888");
                vo.setSvcMsg(ExceptionMsgConstant.NO_ONLINE_CAN_CHANGE_ADD);
                isCheck = false;
            }
        } else {
            vo.setResultCode("E");
            vo.setResultCode("8887");
            vo.setSvcMsg(ExceptionMsgConstant.NO_EXSIST_RATE);
            isCheck = false;
        }
        
        try {
            if (isCheck) {
            	//getParamMap userId 추가
                HashMap<String, String> param = getParamMap(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId(), "", "X38");
                param.put("soc", StringUtil.NVL(soc, ""));
                if ("LOCAL".equals(serverLocation)) {
                    //getVoNe(38, vo);
                    callServiceNe(param, vo, 30000);
                } else {
                    callServiceNe(param, vo, 30000);
                }
            }

        } catch (SocketTimeoutException e) {
            vo.setResultCode("9999");
            vo.setSvcMsg(SOCKET_TIMEOUT_EXCEPTION);

        } catch (Exception e) {
            vo.setResultCode("9998");
            vo.setSvcMsg(e.getMessage());
        }

        return vo;
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

    /**
     * 요금제 변경후에 -> 부가서비스 가입할때
     * <p>
     * tobe sr머지
     * 21. 부가서비스신청
     *
     * @param searchVO
     * @param soc : SOC 코드
     * @param ftrNewParam : 부가정보가 있는경우 부가정보
     * @return
     * @throws
     */
    public RegSvcChgRes regSvcChgNeTrace(MyPageSearchDto searchVO, String soc, String ftrNewParam) {
        RegSvcChgRes vo = new RegSvcChgRes();

        try {
            HashMap<String, String> param = getParamMap(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId(), searchVO.getUserId(), "X21");
            param.put("soc", StringUtil.NVL(soc, ""));
            param.put("ftrNewParam", StringUtil.NVL(ftrNewParam, ""));


            if ("LOCAL".equals(serverLocation)) {
                getVoNe(21, vo);
            } else {
                callServiceNe(param, vo, 30000);
            }


        } catch (SocketTimeoutException e) {
            vo.setResultCode("9999");
            vo.setSvcMsg(SOCKET_TIMEOUT_EXCEPTION);
        } catch (Exception e) {
            vo.setResultCode("9998");
            vo.setSvcMsg(e.getMessage());
        }

        return vo;
    }

    public RegSvcChgRes moscPrdcTrtmPreChk(MoscPrdcTrtmRequest moscPrdcTrtmRequest) {
        RegSvcChgRes resultY24 = new RegSvcChgRes();

        try {
            moscPrdcTrtmRequest.setAppEventCd("Y24");
            this.callServiceJson(moscPrdcTrtmRequest, resultY24, 30000);
        } catch (SocketTimeoutException e) {
            resultY24.setResultCode("9999");
            resultY24.setSvcMsg(SOCKET_TIMEOUT_EXCEPTION);
        } catch (Exception e) {
            resultY24.setResultCode("9998");
            resultY24.setSvcMsg(e.getMessage());
        }

        return resultY24;
    }

    public RegSvcChgRes moscPrdcTrtm(MoscPrdcTrtmRequest moscPrdcTrtmRequest) {
        RegSvcChgRes resultY25 = new RegSvcChgRes();

        try {
            moscPrdcTrtmRequest.setAppEventCd("Y25");
            this.callServiceJson(moscPrdcTrtmRequest, resultY25, 30000);
        } catch (SocketTimeoutException e) {
            resultY25.setResultCode("9999");
            resultY25.setSvcMsg(SOCKET_TIMEOUT_EXCEPTION);
        } catch (Exception e) {
            resultY25.setResultCode("9998");
            resultY25.setSvcMsg(e.getMessage());
        }

        return resultY25;
    }

    public boolean callServiceJson(EventRequest request, CommonXmlNoSelfServiceException vo , int timeout) throws SocketTimeoutException {
        boolean result = true;
        try {
            this.setMplatformLogParametersToRequest(request);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
            headers.set("Accept", "application/json;charset=UTF-8");
            HttpEntity<EventRequest> entity = new HttpEntity<>(request, headers);

            logger.info("*** M-PlatForm Connect Start ***");
            RestTemplate restTemplate = new RestTemplate();
//            String responseXml = restTemplate.postForObject(mplatformJsonUrl, entity, String.class);

            ResponseEntity<byte[]> response = restTemplate.exchange(mplatformJsonUrl, HttpMethod.POST, entity, byte[].class);
            String responseXml = new String(response.getBody(), StandardCharsets.UTF_8);

            logger.info("responseXml : " + responseXml);
            if (responseXml == null || responseXml.isEmpty()) {
                throw new McpMplatFormException(MPLATFORM_RESPONEXML_EMPTY_EXCEPTION);
            } else {
                if( vo != null){
                    vo.setResponseXml(responseXml);
                    vo.toResponseParse();
                }
            }

        } catch (SocketTimeoutException | McpMplatFormException e){
            throw e;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    private void setMplatformLogParametersToRequest(EventRequest eventRequest) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        eventRequest.setIp(getClientIp());
        eventRequest.setUrl(request.getRequestURI());
        eventRequest.setMdlInd("MSC-BATCH");
    }

    /*
     * 테스트를 위해 정상적인 리턴을 강제로 넘겨준다.
     */
    private boolean getVoNe(int param, CommonXmlNoSelfServiceException vo) {
        boolean result = true;
        //////////////////////////////////
        StringBuffer responseXml = new StringBuffer();

        responseXml.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:moscPerInfoResponse xmlns:ns2=\"http://selfcare.so.itl.mvno.kt.com/\">");

        switch (param) {
            case 19://요금상품변경
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X19</appEventCd><appSendDateTime>20220406204440</appSendDateTime><appRecvDateTime>20220406204437</appRecvDateTime><appLgDateTime>20220406204437</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220406204437223</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X19</appEventCd><appSendDateTime>20220406201742</appSendDateTime><appRecvDateTime>20220406201741</appRecvDateTime><appLgDateTime>20220406201741</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220406201326559</globalNo><encYn></encYn><responseType>E</responseType><responseCode>ITL_SFC_E033</responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic>고객님께서는 일시정지 상태이므로 상품을 변경 하실 수 없습니다.</responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X19</appEventCd><appSendDateTime>20160114133336</appSendDateTime><appRecvDateTime>20160114133334</appRecvDateTime><appLgDateTime>20160114133334</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160114133330197</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X19</appEventCd><appSendDateTime>20160114133336</appSendDateTime><appRecvDateTime>20160114133334</appRecvDateTime><appLgDateTime>20160114133334</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160114133330197</globalNo><encYn></encYn><responseType>S</responseType><responseCode>ITL_SYS_E9999</responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic>M-PLATFORM SYSTEM ERROR.</responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                break;
            case 21://부가서비스신청
                responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X01</appEventCd><appSendDateTime>20160112153248</appSendDateTime><appRecvDateTime>20160112153246</appRecvDateTime><appLgDateTime>20160112153246</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112153243531</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><addr>인천 옹진군 영흥면 선재로34번길 141 </addr><email>bluemoor9521@naver.com</email><homeTel>01075116741</homeTel><initActivationDate>20140807163028</initActivationDate></outDto></return>");
                break;
            case 38://부가서비스해지
                responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X38</appEventCd><appSendDateTime>20160112153248</appSendDateTime><appRecvDateTime>20160112153246</appRecvDateTime><appLgDateTime>20160112153246</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112153243531</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                break;
            case 80://OTP인증서비스(X80)
                responseXml.append("<return>");
                responseXml.append("    <bizHeader>");
                responseXml.append("       <appEntrPrsnId>SKY</appEntrPrsnId>");
                responseXml.append("       <appAgncCd>SPT8050</appAgncCd>");
                responseXml.append("       <appEventCd>X80</appEventCd>");
                responseXml.append("       <appSendDateTime>20210203171537</appSendDateTime>");
                responseXml.append("       <appRecvDateTime>20210203171536</appRecvDateTime>");
                responseXml.append("       <appLgDateTime>20210203171536</appLgDateTime>");
                responseXml.append("       <appNstepUserId>82023154</appNstepUserId>");
                responseXml.append("       <appOrderId/>");
                responseXml.append("    </bizHeader>");
                responseXml.append("    <commHeader>");
                responseXml.append("       <globalNo>20210203104300120</globalNo>");
                responseXml.append("       <encYn/>");
                responseXml.append("       <responseType>N</responseType>");
                responseXml.append("       <responseCode/>");
                responseXml.append("       <responseLogcd/>");
                responseXml.append("       <responseTitle/>");
                responseXml.append("       <responseBasic/>");
                responseXml.append("       <langCode/>");
                responseXml.append("       <filler/>");
                responseXml.append("    </commHeader>");
                responseXml.append("    <outDto>");
                responseXml.append("       <otpNo>7805</otpNo>");
                responseXml.append("       <resltCd>00</resltCd>");
                responseXml.append("       <resltMsgSbst>정상 처리 가능</resltMsgSbst>");
                responseXml.append("    </outDto>");
                responseXml.append("</return>");
                break;
            case 87: //x87
                responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X87</appEventCd><appSendDateTime>20220408142116</appSendDateTime><appRecvDateTime>20220408142115</appRecvDateTime><appLgDateTime>20220408142115</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220408142115206</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><combDcTypeNm>회선별 기여도 할인</combDcTypeNm><combProdNm>(홈) 인터넷+모바일</combProdNm><combTypeNm>홈</combTypeNm><engtPerdMonsNum>3년</engtPerdMonsNum><moscCombDtlListOutDTO><combEngtExpirDt>20241109</combEngtExpirDt><combEngtPerdMonsNum>3년</combEngtPerdMonsNum><prodNm>인터넷 베이직</prodNm><svcContDivNm>Internet</svcContDivNm><svcNo>z!64120575992</svcNo></moscCombDtlListOutDTO>	<moscCombDtlListOutDTO><combEngtPerdMonsNum>약정없음</combEngtPerdMonsNum>	<prodNm>데이터 맘껏 15GB+/100분</prodNm><svcContDivNm>Mobile(KIS)</svcContDivNm><svcNo>01025817234</svcNo></moscCombDtlListOutDTO><moscCombDtlListOutDTO><combEngtPerdMonsNum>약정없음</combEngtPerdMonsNum><prodNm>데이터ON 비디오</prodNm><svcContDivNm>Mobile</svcContDivNm><svcNo>01096014852</svcNo>	</moscCombDtlListOutDTO></outDto></return>");
//                responseXml.append(" <return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X87</appEventCd><appSendDateTime>20220408142116</appSendDateTime><appRecvDateTime>20220408142115</appRecvDateTime><appLgDateTime>20220408142115</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220408142115206</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><combDcTypeNm>알뜰폰 동일명의 결합</combDcTypeNm><combProdNm>(MVNO 결합) 모바일</combProdNm><combTypeNm>MVNO 결합</combTypeNm><engtPerdMonsNum>0년</engtPerdMonsNum><moscCombDtlListOutDTO><combEngtPerdMonsNum>약정없음</combEngtPerdMonsNum><prodNm>실용 USIM 1.7</prodNm><svcContDivNm>Mobile(KIS)</svcContDivNm><svcNo>01097785180</svcNo></moscCombDtlListOutDTO><moscCombDtlListOutDTO><combEngtPerdMonsNum>약정없음</combEngtPerdMonsNum><prodNm>LTE 표준</prodNm><svcContDivNm>Mobile(KIS)</svcContDivNm><svcNo>01027148794</svcNo></moscCombDtlListOutDTO></outDto></return>");
//                responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X87</appEventCd><appSendDateTime>20220406165031</appSendDateTime><appRecvDateTime>20220406165030</appRecvDateTime><appLgDateTime>20220406165030</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220406165146658</globalNo><encYn></encYn><responseType>E</responseType><responseCode>ITL_SFC_E114</responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic>[01043255080]회선은 결합되지 않은 회선입니다. </responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                //데이터있음 responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X87</appEventCd><appSendDateTime>20220225103208</appSendDateTime><appRecvDateTime>20220225103206</appRecvDateTime><appLgDateTime>20220225103206</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220225102903049</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><combDcTypeNm>알뜰폰 결합</combDcTypeNm><combProdNm>(MVNO 결합) 모바일+모바일</combProdNm><combTypeNm>MVNO 결합</combTypeNm><engtPerdMonsNum>0년</engtPerdMonsNum><moscCombDtlListOutDTO><combEngtPerdMonsNum>약정없음</combEngtPerdMonsNum><prodNm>데이터 맘껏 안심 1GB+/100분</prodNm><svcContDivNm>Mobile(KIS)</svcContDivNm><svcNo>01045859158</svcNo></moscCombDtlListOutDTO><moscCombDtlListOutDTO><combEngtPerdMonsNum>약정없음</combEngtPerdMonsNum><prodNm>LTE 표준</prodNm><svcContDivNm>Mobile(KIS)</svcContDivNm><svcNo>01027148794</svcNo></moscCombDtlListOutDTO></outDto></return>");
                //불가능   responseXml.append("  <return>    <bizHeader>     <appEntrPrsnId>KIS</appEntrPrsnId>     <appAgncCd>AA00364</appAgncCd>     <appEventCd>X87</appEventCd>     <appSendDateTime>20220127162615</appSendDateTime>     <appRecvDateTime>20220127162611</appRecvDateTime>     <appLgDateTime>20220127162611</appLgDateTime>     <appNstepUserId>91225330</appNstepUserId>     <appOrderId></appOrderId>    </bizHeader>    <commHeader>     <globalNo>9122533020220127162359616</globalNo>     <encYn></encYn>     <responseType>E</responseType>     <responseCode>ITL_SFC_E114</responseCode>     <responseLogcd></responseLogcd>     <responseTitle></responseTitle>     <responseBasic>[01029672627]회선은 결합되지 않은 회선입니다. </responseBasic>     <langCode></langCode>     <filler></filler>    </commHeader>   </return>");
                break;
            case 77: //x77
                responseXml.append("<return>");
                responseXml.append("    <bizHeader>");
                responseXml.append("        <appEntrPrsnId>KIS</appEntrPrsnId>");
                responseXml.append("        <appAgncCd>AA00364</appAgncCd>");
                responseXml.append("        <appEventCd>X77</appEventCd>");
                responseXml.append("        <appSendDateTime>20230818120443</appSendDateTime>");
                responseXml.append("        <appRecvDateTime>20230818120442</appRecvDateTime>");
                responseXml.append("        <appLgDateTime>20230818120442</appLgDateTime>");
                responseXml.append("        <appNstepUserId>91225330</appNstepUserId>");
                responseXml.append("        <appOrderId></appOrderId>");
                responseXml.append("    </bizHeader>");
                responseXml.append("    <commHeader>");
                responseXml.append("        <globalNo>9122533020230818120442749</globalNo>");
                responseXml.append("        <encYn></encYn>");
                responseXml.append("        <responseType>N</responseType>");
                responseXml.append("        <responseCode></responseCode>");
                responseXml.append("        <responseLogcd></responseLogcd>");
                responseXml.append("        <responseTitle></responseTitle>");
                responseXml.append("        <responseBasic></responseBasic>");
                responseXml.append("        <langCode></langCode>");
                responseXml.append("        <filler></filler>");
                responseXml.append("    </commHeader>");
                responseXml.append("    <outDto>");
                responseXml.append("        <moscMvnoComInfo>");
                responseXml.append("            <combSvcNo>01030004307</combSvcNo>");
                responseXml.append("            <combYn>Y</combYn>");
                responseXml.append("            <prdcCd>PL225N754</prdcCd>");
                responseXml.append("            <prdcNm>M 프리미엄 11GB</prdcNm>");
                responseXml.append("            <svcDivCd>모바일</svcDivCd>");
                responseXml.append("            <svcNo>01030004307</svcNo>");
                responseXml.append("        </moscMvnoComInfo>");
                responseXml.append("        <moscSrchCombInfoList>");
                responseXml.append("            <combSvcNo>01095104513</combSvcNo>");
                responseXml.append("            <combYn>N</combYn>");
                responseXml.append("            <prdcCd>PL235Q981</prdcCd>");
                responseXml.append("            <prdcNm>모두다 맘껏 7GB+(밀리의 서재 FREE)</prdcNm>");
                responseXml.append("            <svcDivCd>모바일</svcDivCd>");
                responseXml.append("            <svcNo>01095104513</svcNo>");
                responseXml.append("        </moscSrchCombInfoList>");
                responseXml.append("    </outDto>");
                responseXml.append("</return>");

                break;
            case 78: //x78
                responseXml.append("<return>");
                responseXml.append("    <bizHeader>");
                responseXml.append("        <appEntrPrsnId>KIS</appEntrPrsnId>");
                responseXml.append("        <appAgncCd>AA00364</appAgncCd>");
                responseXml.append("        <appEventCd>X78</appEventCd>");
                responseXml.append("        <appSendDateTime>20230818120446</appSendDateTime>");
                responseXml.append("        <appRecvDateTime>20230818120443</appRecvDateTime>");
                responseXml.append("        <appLgDateTime>20230818120443</appLgDateTime>");
                responseXml.append("        <appNstepUserId>91225330</appNstepUserId>");
                responseXml.append("        <appOrderId></appOrderId>");
                responseXml.append("    </bizHeader>");
                responseXml.append("    <commHeader>");
                responseXml.append("        <globalNo>9122533020230818120443444</globalNo>");
                responseXml.append("        <encYn></encYn>");
                responseXml.append("        <responseType>N</responseType>");
                responseXml.append("        <responseCode></responseCode>");
                responseXml.append("        <responseLogcd></responseLogcd>");
                responseXml.append("        <responseTitle></responseTitle>");
                responseXml.append("        <responseBasic></responseBasic>");
                responseXml.append("        <langCode></langCode>");
                responseXml.append("        <filler></filler>");
                responseXml.append("    </commHeader>");
                responseXml.append("    <outDto>");
                responseXml.append("        <moscCombPreChkListOutDTO>");
                responseXml.append("            <resltMsg>정상</resltMsg>");
                responseXml.append("            <sbscYn>Y</sbscYn>");
                responseXml.append("        </moscCombPreChkListOutDTO>");
                responseXml.append("        <moscCombPreChkListOutDTO>");
                responseXml.append("            <resltMsg>정상</resltMsg>");
                responseXml.append("            <sbscYn>Y</sbscYn>");
                responseXml.append("        </moscCombPreChkListOutDTO>");
                responseXml.append("        <moscCombPreChkListOutDTO>");
                responseXml.append("            <resltMsg>정상</resltMsg>");
                responseXml.append("            <sbscYn>Y</sbscYn>");
                responseXml.append("        </moscCombPreChkListOutDTO>");
                responseXml.append("        <moscCombPreChkListOutDTO>");
                responseXml.append("            <resltMsg>정상</resltMsg>");
                responseXml.append("            <sbscYn>Y</sbscYn>");
                responseXml.append("        </moscCombPreChkListOutDTO>");
                responseXml.append("        <moscCombPreChkListOutDTO>");
                responseXml.append("            <resltMsg>정상</resltMsg>");
                responseXml.append("            <sbscYn>Y</sbscYn>");
                responseXml.append("        </moscCombPreChkListOutDTO>");
                responseXml.append("        <moscCombPreChkListOutDTO>");
                responseXml.append("            <resltMsg>정상</resltMsg>");
                responseXml.append("            <sbscYn>Y</sbscYn>");
                responseXml.append("        </moscCombPreChkListOutDTO>");
                responseXml.append("        <moscCombPreChkListOutDTO>");
                responseXml.append("            <resltMsg>정상</resltMsg>");
                responseXml.append("            <sbscYn>Y</sbscYn>");
                responseXml.append("        </moscCombPreChkListOutDTO>");
                responseXml.append("        <moscCombPreChkListOutDTO>");
                responseXml.append("            <resltMsg>정상</resltMsg>");
                responseXml.append("            <sbscYn>Y</sbscYn>");
                responseXml.append("        </moscCombPreChkListOutDTO>");
                responseXml.append("        <moscCombPreChkListOutDTO>");
                responseXml.append("            <resltMsg>정상</resltMsg>");
                responseXml.append("            <sbscYn>Y</sbscYn>");
                responseXml.append("        </moscCombPreChkListOutDTO>");
                responseXml.append("        <moscCombPreChkListOutDTO>");
                responseXml.append("            <resltMsg>정상</resltMsg>");
                responseXml.append("            <sbscYn>Y</sbscYn>");
                responseXml.append("        </moscCombPreChkListOutDTO>");
                responseXml.append("        <moscCombPreChkListOutDTO>");
                responseXml.append("            <resltMsg>정상</resltMsg>");
                responseXml.append("            <sbscYn>Y</sbscYn>");
                responseXml.append("        </moscCombPreChkListOutDTO>");
                responseXml.append("        <moscCombPreChkListOutDTO>");
                responseXml.append("            <resltMsg>정상</resltMsg>");
                responseXml.append("            <sbscYn>Y</sbscYn>");
                responseXml.append("        </moscCombPreChkListOutDTO>");
                responseXml.append("        <moscCombPreChkListOutDTO>");
                responseXml.append("            <resltMsg>정상</resltMsg>");
                responseXml.append("            <sbscYn>Y</sbscYn>");
                responseXml.append("        </moscCombPreChkListOutDTO>");
                responseXml.append("        <moscCombPreChkListOutDTO>");
                responseXml.append("            <resltMsg>정상</resltMsg>");
                responseXml.append("            <sbscYn>Y</sbscYn>");
                responseXml.append("        </moscCombPreChkListOutDTO>");
                responseXml.append("        <moscCombPreChkListOutDTO>");
                responseXml.append("            <resltMsg>정상</resltMsg>");
                responseXml.append("            <sbscYn>Y</sbscYn>");
                responseXml.append("        </moscCombPreChkListOutDTO>");
                responseXml.append("        <moscCombPreChkListOutDTO>");
                responseXml.append("            <resltMsg>정상</resltMsg>");
                responseXml.append("            <sbscYn>Y</sbscYn>");
                responseXml.append("        </moscCombPreChkListOutDTO>");
                responseXml.append("        <moscCombPreChkListOutDTO>");
                responseXml.append("            <resltMsg>정상</resltMsg>");
                responseXml.append("            <sbscYn>Y</sbscYn>");
                responseXml.append("        </moscCombPreChkListOutDTO>");
                responseXml.append("        <moscCombPreChkListOutDTO>");
                responseXml.append("            <resltMsg>정상</resltMsg>");
                responseXml.append("            <sbscYn>Y</sbscYn>");
                responseXml.append("            <svcNo>01095104513</svcNo>");
                responseXml.append("        </moscCombPreChkListOutDTO>");
                responseXml.append("        <resltMsg>정상</resltMsg>");
                responseXml.append("        <sbscYn>Y</sbscYn>");
                responseXml.append("    </outDto>");
                responseXml.append("</return>");
                break;
            case 71: //x77
                responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X71</appEventCd><appSendDateTime>20220304092547</appSendDateTime><appRecvDateTime>20220304092546</appRecvDateTime><appLgDateTime>20220304092546</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220304092230512</globalNo><encYn></encYn><responseType>E</responseType><responseCode>ITL_SFC_E105</responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic>데이터쉐어링 결합 중인 대상이 없습니다.</responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA11070</appAgncCd><appEventCd>X71</appEventCd><appSendDateTime>20200527133020</appSendDateTime><appRecvDateTime>20200527133019</appRecvDateTime><appLgDateTime>20200527133019</appLgDateTime><appNstepUserId>116833564</appNstepUserId><appOrderId/></bizHeader><commHeader><globalNo>9114053920180405150101014</globalNo><encYn/><responseType>N</responseType><responseCode/><responseLogcd/><responseTitle/><responseBasic/><langCode/><filler/></commHeader><outDto><outDataSharingDto><efctStDt>20200525</efctStDt><svcNo>01074285434</svcNo></outDataSharingDto><outDataSharingDto><efctStDt>20200513</efctStDt><svcNo>01073044224</svcNo></outDataSharingDto></outDto></return>");
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X71</appEventCd><appSendDateTime>20220303175825</appSendDateTime><appRecvDateTime>20220303175824</appRecvDateTime><appLgDateTime>20220303175824</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220303175509424</globalNo><encYn></encYn><responseType>E</responseType><responseCode>ITL_SFC_E105</responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic>데이터쉐어링 결합 중인 대상이 없습니다.</responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                break;
            case 69: //x69
                //실패케이스
                //responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X69</appEventCd><appSendDateTime>20220412164939</appSendDateTime><appRecvDateTime>20220412164938</appRecvDateTime><appLgDateTime>20220412164938</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220412164940813</globalNo><encYn></encYn><responseType>E</responseType><responseCode>ITL_SFC_E106</responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic>데이터쉐어링 결합 가능한 개통된 고객이 없습니다.</responseBasic><langCode></langCode><filler></filler></commHeader></return>");
                //성공케이스
                responseXml.append("<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X69</appEventCd><appSendDateTime>20220414162249</appSendDateTime><appRecvDateTime>20220414162248</appRecvDateTime><appLgDateTime>20220414162248</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220414162252205</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><outDataSharingDto><rsltInd>Y</rsltInd><svcNo>01098132788</svcNo></outDataSharingDto></outDto></return>");
                break;
            default:
                logger.debug("Default MplatFormService.java");
        }

        responseXml.append("</ns2:moscPerInfoResponse></soap:Body></soap:Envelope>");
        vo.setResponseXml(responseXml.toString());
        try {
            vo.toResponseParse();
        } catch (Exception e) {
            result = false;
        }
        //////////////////////////////////

        return result;
    }
    
}
