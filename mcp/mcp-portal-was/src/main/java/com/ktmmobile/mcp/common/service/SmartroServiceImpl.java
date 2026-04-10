package com.ktmmobile.mcp.common.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktmmobile.mcp.appform.dao.AppformDao;
import com.ktmmobile.mcp.appform.dto.AppformReqDto;
import com.ktmmobile.mcp.appform.service.AppformSvc;
import com.ktmmobile.mcp.common.constants.Constants;
import com.ktmmobile.mcp.common.dao.SmartroDao;
import com.ktmmobile.mcp.common.dto.SmartroDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.dto.db.McpRequestDto;
import com.ktmmobile.mcp.common.dto.db.McpRequestSelfDlvryDto;
import com.ktmmobile.mcp.common.dto.db.MspSmsTemplateMstDto;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.util.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ktmmobile.mcp.common.constants.Constants.CSTMR_TYPE_NM;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;


@Service
public class SmartroServiceImpl implements SmartroService{


    @Value("${inf.url}")
    private String infUrl;

    @Value("${SMARTRO_APPROVAL_URL}")
    private String approvalUrl;

    @Value("${SMARTRO_CANCEL_URL}")
    private String cancelUrl;

    @Value("${SMARTRO_CANCEL_PW}")
    private String cancelPw;

    @Value("${SMARTRO_PC_RETURN_URL}")
    private String smartroPcReturnUrl;

    @Value("${SMARTRO_MOBILE_RETURN_URL}")
    private String smartroMobileReturnUrl;

    @Value("${SMARTRO_REQ_SELF_STOP_URL}")
    private String smartroReqSelfStopUrl;

    @Value("${SMARTRO_RETRY_URL}")
    private String smartroRetryUrl;

    @Value("${SERVER_NAME}")
    private String serverName;

    @Value("${MERCHANTKEY}")
    private String merchantKey;

    @Value("${MERCHANTKEY_N}")
    private String merchantKeyN;

    @Value("${MID}")
    private String mid;

    @Value("${MID_N}")
    private String midN;

    @Value("${MALL_IP}")
    private String mallIp;

    @Autowired
    private AppformSvc appformSvc;

    @Autowired
    private FCommonSvc fCommonSvc;

    @Autowired
    private SmsSvc smsSvc;

    @Autowired
    private SmartroDao smartroDao;

    @Autowired
    private AppformDao appformDao;

    @Value("${ext.url}")
    private String extUrl;

    private static final Logger logger = LoggerFactory.getLogger(SmartroServiceImpl.class);

    @Override
    public HashMap<String, String> smartroPayInit(HttpServletRequest req) {

        HashMap<String, String> reslt = new HashMap<String, String>();
        try {

            if("".equals(StringUtil.NVL(req.getParameter("buyType"), "")) ) {
                reslt.put("resltCd", "-2");
                reslt.put("msg", "유효하지 않은 접근입니다.");
                return reslt;
            }

            String buyType = req.getParameter("buyType");
            Integer usimAmt = 0;
            NmcpCdDtlDto nmcpCdDtlDto = null;

            if("DIRECT_MALL_USIM".equals(buyType)) {

                if( "".equals(StringUtil.NVL(req.getParameter("prodCd"), ""))
                        || "".equals(StringUtil.NVL(req.getParameter("reqBuyQnty"), ""))) {
                    reslt.put("resltCd", "-2");
                    reslt.put("msg", "유효하지 않은 접근입니다.");
                    return reslt;
                }

                String prodCd = req.getParameter("prodCd");
                String reqBuyQnty = req.getParameter("reqBuyQnty");
                /** 요청유형 [01 일반(유심구매) 02 신청서요청] */
                String reqType = req.getParameter("reqType");
                //String requestKey = req.getParameter("requestKey");
                String requestKeyTemp = req.getParameter("requestKeyTemp");
                String prdtSctnCd = req.getParameter("prdtSctnCd");

                nmcpCdDtlDto = NmcpServiceUtils.getCodeNmDto(Constants.USIM_PROD_ID_GROP_CODE, prodCd);


                int reqBuyQntyInt = 0;

                try {
                    reqBuyQntyInt = Integer.parseInt(reqBuyQnty);
                } catch (NumberFormatException e) {
                    reqBuyQntyInt = 0 ;
                }

                if(nmcpCdDtlDto == null || reqBuyQntyInt < 1) {
                    reslt.put("resltCd", "-3");
                    reslt.put("msg", "유효하지 않은 접근입니다.");
                }else {
                    int amt = Integer.parseInt(nmcpCdDtlDto.getExpnsnStrVal1());
                    usimAmt = amt * reqBuyQntyInt ;

                    String ediDate = getyyyyMMddHHmmss(); // 전문생성일시

                    String mode = "STG";
                    //사용자 테스트시 개발 바라봄으로 주석처리
                    if("REAL".equals(serverName)) {
                        mode = "REAL";
                    }

                    String returnUrl = smartroPcReturnUrl;
                    String stopUrl = "";
                    if ("Y".equals(NmcpServiceUtils.isMobile())) {
                        returnUrl = smartroMobileReturnUrl;
                        stopUrl = smartroReqSelfStopUrl;
                    }

                    //02 신청서요청
                    if ("02".equals(reqType)) {
                        if ("Y".equals(NmcpServiceUtils.isMobile())) {
                            stopUrl = "https://"+ req.getServerName() +"/m/appForm/appForm.do?requestKey=" + requestKeyTemp;
                        } else {
                            stopUrl = "https://"+ req.getServerName() + "/appForm/appForm.do?requestKey=" + requestKeyTemp;
                        }
                    }

                    String encryptData	= encodeMD5HexBase64(ediDate + mid + usimAmt + merchantKey);
                    String goodsName = "";

                    if ("5G".equals(prdtSctnCd)) {
                        goodsName = nmcpCdDtlDto.getDtlCdNm()+"[5G]";
                    } else {
                        goodsName = nmcpCdDtlDto.getDtlCdNm();
                    }


                    reslt.put("PayMethod", "CARD");
                    reslt.put("Mid", mid);
                    reslt.put("Amt", String.valueOf(usimAmt));
                    reslt.put("ReturnUrl", returnUrl);
                    reslt.put("StopUrl", stopUrl);
                    reslt.put("GoodsName", goodsName);
                    reslt.put("MallIP", mallIp);
                    reslt.put("EncryptData", encryptData);
                    reslt.put("ediDate", ediDate);
                    reslt.put("reqBuyQnty", reqBuyQnty);

                    Double tmp = usimAmt / 1.1;
                    long vatAmt = (long)Math.floor(usimAmt - tmp);
                    long taxAmt = usimAmt - vatAmt;

                    reslt.put("TaxAmt", String.valueOf(taxAmt));
                    reslt.put("VatAmt", String.valueOf(vatAmt));

                    //reslt.put("TaxAmt", "");
                    //reslt.put("VatAmt", "");

                    reslt.put("mode", mode);
                    reslt.put("resltCd", "0000");

                }
            } else if ("APPFORM_SELF_SUF".equals(buyType)) {

                if( "".equals(StringUtil.NVL(req.getParameter("requestKey"), "")) ) {
                    reslt.put("resltCd", "-12");
                    reslt.put("msg", "유효하지 않은 접근입니다.");
                    return reslt;
                }

                String requestKey = req.getParameter("requestKey");
                String requestKeyTemp = req.getParameter("requestKeyTemp");


                //자급제 폰 결제
                AppformReqDto appformReq =  appformSvc.getNmcpRequestApdSaleinfo(Long.parseLong(requestKey) ) ;

                if(appformReq == null || appformReq.getPaymentHndsetPrice() < 1) {
                    reslt.put("resltCd", "-11");
                    reslt.put("msg", "유효하지 않은 접근입니다.");
                    return reslt;
                }

                usimAmt = appformReq.getPaymentHndsetPrice();


                String ediDate = getyyyyMMddHHmmss(); // 전문생성일시

                String mode = "STG";
                if("REAL".equals(serverName)) {
                    mode = "REAL";
                }

                String returnUrl = smartroPcReturnUrl;
                String stopUrl = "";

                //02 신청서요청
                if ("Y".equals(NmcpServiceUtils.isMobile())) {
                    stopUrl = "https://"+ req.getServerName() +"/m/appForm/appForm.do?requestKey=" + requestKeyTemp;
                    returnUrl = smartroMobileReturnUrl;
                } else {
                    stopUrl = "https://"+ req.getServerName() + "/appForm/appForm.do?requestKey=" + requestKeyTemp;
                }

                String encryptData	= "" ;
                if (StringUtils.isBlank(appformReq.getCardDcCd()) ) {
                    encryptData	= encodeMD5HexBase64(ediDate + mid + usimAmt + merchantKey);
                    reslt.put("Mid", mid);
                } else {
                    encryptData	= encodeMD5HexBase64(ediDate + midN + usimAmt + merchantKeyN);
                    reslt.put("Mid", midN);
                }

                reslt.put("PayMethod", "CARD");
                reslt.put("Amt", String.valueOf(usimAmt));
                reslt.put("ReturnUrl", returnUrl);
                reslt.put("StopUrl", stopUrl);
                reslt.put("GoodsName", appformReq.getProdNm());
                reslt.put("MallIP", mallIp);
                reslt.put("EncryptData", encryptData);
                reslt.put("ediDate", ediDate);
                reslt.put("reqBuyQnty", "1");

                Double tmp = usimAmt / 1.1;
                long vatAmt = (long)Math.floor(usimAmt - tmp);
                long taxAmt = usimAmt - vatAmt;

                //reslt.put("TaxAmt", String.valueOf(taxAmt));
                //reslt.put("VatAmt", String.valueOf(vatAmt));

                reslt.put("TaxAmt", "");
                reslt.put("VatAmt", "");

                reslt.put("mode", mode);
                reslt.put("resltCd", "0000");
            } else if ("APPFORM_DIRECT_PHONE".equals(buyType)) {

                if( "".equals(StringUtil.NVL(req.getParameter("requestKey"), "")) ) {
                    reslt.put("resltCd", "-12");
                    reslt.put("msg", "유효하지 않은 접근입니다.");
                    return reslt;
                }

                String requestKey = req.getParameter("requestKey");
                String requestKeyTemp = req.getParameter("requestKeyTemp");
                String prodNm = req.getParameter("prodNm");

                if( "".equals(StringUtil.NVL(prodNm, "")) ) {
                    reslt.put("resltCd", "-13");
                    reslt.put("msg", "유효하지 않은 접근입니다.");
                    return reslt;
                }

                //신청서 정보
                AppformReqDto appformReqInput = new AppformReqDto();
                appformReqInput.setRequestKey(Long.parseLong(requestKey));
                AppformReqDto appformReq =  appformSvc.getAppForm(appformReqInput) ;

                if(appformReq == null || appformReq.getSettlAmt() < 1) {
                    reslt.put("resltCd", "-11");
                    reslt.put("msg", "유효하지 않은 접근입니다.");
                    return reslt;
                }
                usimAmt = appformReq.getSettlAmt();
                String ediDate = getyyyyMMddHHmmss(); // 전문생성일시

                String mode = "STG";
                if("REAL".equals(serverName)) {
                    mode = "REAL";
                } else if ("DEV".equals(serverName) || "LOCAL".equals(serverName)) {
                    usimAmt = 1000;  //테스트 ....
                }

                String returnUrl = smartroPcReturnUrl;
                String stopUrl = "";

                //02 신청서요청
                if ("Y".equals(NmcpServiceUtils.isMobile())) {
                    stopUrl = "https://"+ req.getServerName() +"/m/appForm/appForm.do?requestKey=" + requestKeyTemp;
                    returnUrl = smartroMobileReturnUrl;
                } else {
                    stopUrl = "https://"+ req.getServerName() + "/appForm/appForm.do?requestKey=" + requestKeyTemp;
                }

                String encryptData	= "" ;
                encryptData	= encodeMD5HexBase64(ediDate + mid + usimAmt + merchantKey);
                reslt.put("Mid", mid);

                reslt.put("PayMethod", "CARD");
                reslt.put("Amt", String.valueOf(usimAmt));
                reslt.put("ReturnUrl", returnUrl);
                reslt.put("StopUrl", stopUrl);
                reslt.put("GoodsName", prodNm);
                reslt.put("MallIP", mallIp);
                reslt.put("EncryptData", encryptData);
                reslt.put("ediDate", ediDate);
                reslt.put("reqBuyQnty", "1");

                Double tmp = usimAmt / 1.1;
                /*long vatAmt = (long)Math.floor(usimAmt - tmp);
                long taxAmt = usimAmt - vatAmt;*/
                //reslt.put("TaxAmt", String.valueOf(taxAmt));
                //reslt.put("VatAmt", String.valueOf(vatAmt));

                reslt.put("TaxAmt", "");
                reslt.put("VatAmt", "");

                reslt.put("mode", mode);
                reslt.put("resltCd", "0000");
            } else {
                reslt.put("resltCd", "-4");
                reslt.put("msg", "유효하지 않은 접근입니다.");
            }

        } catch(RuntimeException e) {
            reslt.put("resltCd", "-5");
            reslt.put("msg", "처리중 오류가 발생하였습니다. 잠시 후 다시 이용해 주세요.");
        } catch(Exception e) {
            reslt.put("resltCd", "-5");
            reslt.put("msg", "처리중 오류가 발생하였습니다. 잠시 후 다시 이용해 주세요.");
        }

        return reslt;
    }



    /**
     * 현재날짜를 YYYYMMDDHHMMSS로 리턴
     */
    public final static String getyyyyMMddHHmmss(){
        /** yyyyMMddHHmmss Date Format */
        SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
        synchronized(new Date()) {
            return yyyyMMddHHmmss.format(new Date());
        }
    }

    public final String encodeMD5HexBase64(String pw){
        return new String(Base64.encodeBase64(DigestUtils.md5Hex(pw).getBytes()));
    }

    @SuppressWarnings("unchecked")
    @Override
    public HashMap<String, Object> callApi(HttpServletRequest req) {

        HashMap<String, Object> result = new HashMap<String, Object>();

        if("".equals(StringUtil.NVL(req.getParameter("TrAuthKey"), "")) || "".equals(StringUtil.NVL(req.getParameter("Tid"), ""))){
            result.put("resltCd", "-1");
            result.put("msg", "유효하지 않은 접근입니다.");
            return result;
        }

        try {

            if("LOCAL".equals(serverName)) {
                String Tid = req.getParameter("Tid");
                String TrAuthKey = req.getParameter("TrAuthKey");

                StringBuilder responseBody = null;

                // http urlCall 승인 요청 및 TrAuthKey 유효성 검증
                int connectTimeout = 1000;
                int readTimeout = 5000; // 가맹점에 맞게 TimeOut 조절

                URL url = null;
                HttpsURLConnection connection = null;
                OutputStreamWriter osw = null;
                BufferedReader br = null;
                try {
                    SSLContext sslCtx = SSLContext.getInstance("TLSv1.2");
                    sslCtx.init(null, null, new SecureRandom());

                    url = new URL(approvalUrl);

                    connection = (HttpsURLConnection) url.openConnection();
                    connection.setSSLSocketFactory(sslCtx.getSocketFactory());

                    connection.addRequestProperty("Content-Type", "application/json");
                    connection.addRequestProperty("Accept", "application/json");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setConnectTimeout(connectTimeout);
                    connection.setReadTimeout(readTimeout);

                    osw = new OutputStreamWriter(new BufferedOutputStream(connection.getOutputStream()), "utf-8");

                    JSONObject body = new JSONObject();
                    body.put("Tid", Tid);
                    body.put("TrAuthKey", TrAuthKey);

                    char[] bytes = body.toString().toCharArray();
                    osw.write(bytes, 0, bytes.length);
                    osw.flush();
                    osw.close();

                    br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                    String line = null;
                    responseBody = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        responseBody.append(line);
                    }
                    br.close();

                    // 결제결과
                    result = new ObjectMapper().readValue(responseBody.toString(), HashMap.class);

                } catch(IOException e) {
                    logger.error("Exception e : {}", e.getMessage());
                } catch(Exception e) {
                    logger.error("Exception e : {}", e.getMessage());
                } finally {
                    if(br != null) br.close();
                    if(osw != null) osw.close();
                }
            } else {
                CommonHttpClient client = new CommonHttpClient(extUrl+"/smartroCallApi.do");

                NameValuePair[] data = {
                        new NameValuePair("trAuthKey",req.getParameter("TrAuthKey")),
                        new NameValuePair("tid",req.getParameter("Tid")),
                        new NameValuePair("approvalUrl",approvalUrl),
                };

                String responseBody = client.postUtf8(data);
                result = new ObjectMapper().readValue(responseBody.toString(), HashMap.class);
            }

            String payMethod = result.get("PayMethod") == null ? "" : (String) result.get("PayMethod");				// 지불수단 - CARD:신용카드, BANK:계좌이체, VBANK:가상계좌, CELLPHONE:휴대폰결제, NAVER:네이버페이, KAKAO:카카오페이, PAYCO:페이코페이
            String mid = result.get("Mid") == null ? "" : (String) result.get("Mid");								// 상점ID
            String tid = result.get("Tid") == null ? "" : (String) result.get("Tid");								// 거래번호
            String moid = result.get("Moid") == null ? "" : (String) result.get("Moid");							// 상품주문번호
            String goodsName = result.get("GoodsName") == null ? "" : (String) result.get("GoodsName");				// 상품명
            String goodsCnt = result.get("GoodsCnt") == null ? "" : (String) result.get("GoodsCnt");				// 결제상품 품목 개수
            String amt = result.get("Amt") == null ? "" : (String) result.get("Amt");								// 금액
            String buyerName = result.get("BuyerName") == null ? "" : (String) result.get("BuyerName");				// 결제자명
            String buyerAuthNum = result.get("BuyerAuthNum") == null ? "" : (String) result.get("BuyerAuthNum");	// 구매자 인증번호 - 휴대폰 결제 시 마스킹 처리된 전화번호 전달
            String mallUserId = result.get("MallUserId") == null ? "" : (String) result.get("MallUserId");			// 고객사회원ID
            String authCode = result.get("AuthCode") == null ? "" : (String) result.get("AuthCode");				// 승인번호
            String resultCode = result.get("ResultCode") == null ? "" : (String) result.get("ResultCode");			// 결과코드

            // STG 테스트 시 'PG 하위사업자번호 없음' 오류 떨어짐.. 테스트를 위해 강제 성공처리
            if("STG".equals(serverName) && "002H".equals(resultCode)){
                resultCode = "3001";
                result.put("ResultCode", "3001");
            }

            String resultMsg = result.get("ResultMsg") == null ? "" : (String) result.get("ResultMsg");				// 결과메시지
            String signValue = result.get("SignValue") == null ? "" : (String) result.get("SignValue");				// 위/변조 사인값 가변 위·변조 방지를 위해 전송되는 암호화 데이터

            String buyerEmail = result.get("BuyerEmail") == null ? "" : (String) result.get("BuyerEmail");			// 구매자 이메일
            String cardCode =  result.get("AppCardCode") == null ? "" : (String) result.get("AppCardCode");			// 발급사코드
            String cardName =  result.get("AppCardName") == null ? "" : (String) result.get("AppCardName");			// 발급사명
            String cardNum =  result.get("CardNum") == null ? "" : (String) result.get("CardNum");					// 카드번호(7~12자리 Masking 처리)

            String mallReserved = result.get("MallReserved") == null ? "" : (String) result.get("MallReserved");	// 상점 예비 필드

            //상점 예비 필드 - 메뉴[0],구매타입[1],상품코드[2]
            //온라인 신청서 등록 key[3],임시정장 key[4]
            String[] mallReservedArr = mallReserved.split(",");

            String strRequestKey = "0";
            String strRequestKeyTemp ="0";

            if (mallReservedArr.length > 4) {
                strRequestKey = mallReservedArr[3];
                strRequestKeyTemp = mallReservedArr[4];
            }

            SmartroDto smartroDto = new SmartroDto();
            smartroDto.setPaymethod(payMethod);
            smartroDto.setCpid(mid);
            smartroDto.setDaoutrx(tid);
            smartroDto.setOrderno(moid);
            smartroDto.setAmount(amt);
            smartroDto.setSettdate(DateTimeUtil.getFormatString("yyyyMMddHHmmss"));
            smartroDto.setAuthno(authCode);
            /*
            if(authCode != null && authCode.length() > 8) {
                smartroDto.setAuthno(authCode.substring(0, 8));
            }else {
                smartroDto.setAuthno(authCode);
            }
            */
            smartroDto.setEmail(buyerEmail);
            smartroDto.setCardcode(cardCode);
            smartroDto.setCardname(cardName);
            smartroDto.setUsername(buyerName);
            smartroDto.setProductcode(mallReservedArr[2]);
            smartroDto.setProductname(goodsName);
            smartroDto.setCardno(cardNum);
            smartroDto.setReservedindex1(null);
            smartroDto.setReservedindex2(null);
            smartroDto.setReservedstring(mallReservedArr[1]);
            smartroDto.setCretDt(DateTimeUtil.getFormatString("yyyyMMddHHmmss"));
            smartroDto.setMenu(mallReservedArr[0]);
            smartroDto.setRequestKey(strRequestKey);
            smartroDto.setRequestKeyTemp(strRequestKeyTemp);

            result.put("requestKey", strRequestKey);//인자값 추가
            result.put("requestKeyTemp", strRequestKeyTemp);//인자값 추가


            if (SessionUtils.hasLoginUserSessionBean()) {
                UserSessionDto userSession = SessionUtils.getUserCookieBean();
                smartroDto.setUserid(userSession.getUserId());
            }else {
                smartroDto.setUserid("GUEST");
            }

            //추가해서 사용
            if("DIRECTMALL".equals(mallReservedArr[0])) {
                result.put("succUrl", "/appForm/selfDlvryComplete.do");
                if("NowDlvry".equals(mallReservedArr[1])) {
                    result.put("succUrl", "/appForm/nowDlvryComplete.do");
                }
                result.put("failUrl", "/appForm/reqSelfDlvry.do");
                if ("Y".equals(NmcpServiceUtils.isMobile())) {
                    result.put("failUrl", "/m/appForm/reqSelfDlvry.do");
                    if("NowDlvry".equals(mallReservedArr[1])) {
                        result.put("succUrl", "/m/appForm/nowDlvryComplete.do");
                    }
                    result.put("succUrl", "/m/appForm/selfDlvryComplete.do");
                }
            } else if ("DIRECT_FORM".equals(mallReservedArr[0])) {
                result.put("failUrl", "/appForm/appForm.do");
                result.put("succUrl", "/appForm/appFormComplete.do");


                if ("Y".equals(NmcpServiceUtils.isMobile())) {
                    result.put("failUrl", "/m/appForm/appForm.do");
                    result.put("succUrl", "/m/appForm/appFormComplete.do");
                }
            } else if("TEST".equals(mallReservedArr[0])) {
                result.put("failUrl", "/m/main.do");
                result.put("succUrl", "/appFormTestComplete.do?replaceYn=Y");
            }

            result.put("resltCd", "0000");
            result.put("msg", "정상적으로 결제가 처리되었습니다.");

            if("3001".equals(resultCode)) {
                //카드 결제 성공
                logger.debug("resultCode = {}", resultCode);
            }else if("4000".equals(resultCode)) {
                //계좌이체 성공
                logger.debug("resultCode = {}", resultCode);
            }else if("4100".equals(resultCode)) {
                //가상계좌발급 성공
                logger.debug("resultCode = {}", resultCode);
            }else if("A000".equals(resultCode)) {
                //휴대폰 결제 성공
                logger.debug("resultCode = {}", resultCode);
            }else if("KP00".equals(resultCode)) {
                //카카오페이 결제 성공
                logger.debug("resultCode = {}", resultCode);
            }else if("NP00".equals(resultCode)) {
                logger.debug("resultCode = {}", resultCode);
                //네이버페이 결제 성공
            }else if("PP00".equals(resultCode)) {
                logger.debug("resultCode = {}", resultCode);
                //페이코페이 결제 성공
            }else if("2001".equals(resultCode)) {
                //취소 성공
                result.put("resltCd", "0001");
                result.put("msg", "정상적으로 결제 취소가 처리되었습니다.");
            }else {
                result.put("resltCd", "-1");
                result.put("msg", resultMsg);
            }

            if("0000".equals(result.get("resltCd"))){
                String ordrReslt = insertSmartroOrder(smartroDto);
                if("FAIL".equals(ordrReslt)) {
                    result.put("resltCd", "-2");
                    result.put("msg", "처리중 오류가 발생하였습니다. 잠시후 다시 이용해 주세요.");

                    JSONObject cancelBody = new JSONObject();
                    JSONObject paramData = new JSONObject();

                    // 검증값 SHA256 암호화(Tid + MerchantKey + CancelAmt + PartialCancelCode)
                    String hashData = encodeSHA256Base64(tid + merchantKey + amt + "0");

                    // 취소 요청 파라미터 셋팅
                    paramData.put("SERVICE_MODE", "CL1");
                    paramData.put("Tid", tid);
                    paramData.put("Mid", mid);
                    paramData.put("CancelAmt", amt);
                    paramData.put("CancelPwd", cancelPw);
                    paramData.put("CancelMsg", "사용자 취소");
                    paramData.put("CancelSeq", "1");		// 취소차수(기본값: 1, 부분취소 시마다 차수가 1씩 늘어남. 첫번째 부분취소=1, 두번째 부분취소=2, ...)
                    paramData.put("PartialCancelCode", "0");// 0: 전체취소, 1: 부분취소

                    // 과세, 비과세, 부가세 셋팅(부가세 직접 계산 가맹점의 경우 각 값을 계산하여 설정해야 합니다.)
                    paramData.put("CancelTaxAmt", "");
                    paramData.put("CancelTaxFreeAmt", "");
                    paramData.put("CancelVatAmt", "");

                    // 서브몰 사용 가맹점의 경우, DivideInfo 파라미터를 가맹점에 맞게 설정해 주세요. (일반연동 참고)
                    paramData.put("DivideInfo", "");

                    // HASH 설정 (필수)
                    paramData.put("HashData", hashData);
                    String encData = "";
                    try {
                        encData = AES256Cipher.AES_Encode(paramData.toString(), merchantKey.substring(0, 32), AES256Cipher.ivBytes);
                    } catch (UnsupportedEncodingException e) {
                        logger.error("Exception e : {}", e.getMessage());
                    } catch (GeneralSecurityException e) {
                        logger.error("Exception e : {}", e.getMessage());
                    } catch(Exception e){
                        logger.error("Exception e : {}", e.getMessage());
                    }

                    Map<String, Object> cancelResult = new HashMap<String, Object>();

                    try {

                        if("LOCAL".equals(serverName)) {
                            int connectTimeout = 1000;
                            int readTimeout = 5000; // 가맹점에 맞게 TimeOut 조절

                            URL url = null;
                            HttpsURLConnection connection = null;
                            OutputStreamWriter osw = null;
                            BufferedReader br = null;
                            try {
                                SSLContext sslCtx = SSLContext.getInstance("TLSv1.2");
                                sslCtx.init(null, null, new SecureRandom());

                                url = new URL(cancelUrl);

                                connection = (HttpsURLConnection)url.openConnection();
                                connection.setSSLSocketFactory(sslCtx.getSocketFactory());

                                connection.addRequestProperty("Content-Type", "application/json");
                                connection.addRequestProperty("Accept", "application/json");
                                connection.setDoOutput(true);
                                connection.setDoInput(true);
                                connection.setConnectTimeout(connectTimeout);
                                connection.setReadTimeout(readTimeout);

                                osw = new OutputStreamWriter(new BufferedOutputStream(connection.getOutputStream()) , "utf-8" );
                                char[] bytes = cancelBody.toString().toCharArray();
                                osw.write(bytes,0,bytes.length);
                                osw.flush();
                                osw.close();

                                br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                                String line = null;
                                StringBuilder responseBody = new StringBuilder();
                                while ((line = br.readLine()) != null) {
                                    responseBody.append(line);
                                }
                                br.close();

                                // 결제결과
                                cancelResult = new ObjectMapper().readValue(responseBody.toString(), HashMap.class);

                                if("2001".equals(cancelResult.get("ResultCode"))) {
                                    result.put("msg", "결제 도중 오류가 발생하여 정상적으로 결제 취소가 완료 되었습니다.");
                                }

                            } catch(IOException e) {
                                logger.error("Exception e : {}", e.getMessage());
                            } catch(Exception e) {
                                logger.error("Exception e : {}", e.getMessage());
                            } finally{
                                if(br != null) br.close();
                                if(osw != null) osw.close();
                            }

                        }else {
                            CommonHttpClient client = new CommonHttpClient(extUrl+"/smartroCancelApi.do");

                            NameValuePair[] cancelData = {
                                    new NameValuePair("cancelUrl",cancelUrl),
                                    new NameValuePair("encData",encData),
                                    new NameValuePair("mid",mid),
                            };
                            String cancelResponseBody = client.postUtf8(cancelData);
                            cancelResult = new ObjectMapper().readValue(cancelResponseBody.toString(), HashMap.class);

                            if("2001".equals(cancelResult.get("ResultCode"))) {
                                result.put("msg", "결제 도중 오류가 발생하여 정상적으로 결제 취소가 완료 되었습니다.");
                            }
                        }

                    } catch (MalformedURLException e) {
                        logger.error("Exception e : {}", e.getMessage());
                    } catch (IOException e) {
                        logger.error("Exception e : {}", e.getMessage());
                    } catch (Exception e) {
                        logger.error("Exception e : {}", e.getMessage());
                    }
                }
            }

        } catch(SocketTimeoutException e) {
            result.put("resltCd", "-1");
            result.put("msg", e.getMessage());
        } catch (Exception e) {
            result.put("resltCd", "-1");
            result.put("msg", e.getMessage());
        }

        return result;
    }

    @Override
    public List<SmartroDto> getSmartroDataList(String orderno) {
        return smartroDao.getSmartroDataList(orderno);
    }

    @Override
    public String insertSmartroOrder(SmartroDto smartroDto) {

        String returnUrl = "";
        try{
            int result = 0;
            String daoutrx = StringUtil.NVL(smartroDto.getDaoutrx(),"");
            String orderNo = StringUtil.NVL(smartroDto.getOrderno(),"0");
            String reservedString = StringUtil.NVL(smartroDto.getReservedstring(),"");

            if("".equals(daoutrx) || "0".equals(orderNo)){
                return "FAIL";
            }

            result = smartroDao.insertSmartroOrder(smartroDto);
            // 우리쪽 데이터와 DaouPay 데이터가 맞지 않는다면 fail 로 하자..
            // 비교할 데이터가 없으면 이슈가 발생할수 있음.

            if( result > 0 ){

                 // 추가 해서 사용하삼.
                if("SelfDlvry".equals(reservedString)){ // 셀프개통 유심 배송 요청
                    Long selfDlvryIdx = Long.parseLong(orderNo);
                    McpRequestSelfDlvryDto mcpRequestSelfDlvryDto = appformSvc.getMcpSelfDlvryData(selfDlvryIdx) ;
                    if( mcpRequestSelfDlvryDto != null ){ // 호출했을때 FAIL 인경우 1시간동안 3분간 호출한다니... 데이터 있으면 PK 오류남으로 지우고 최신으로 넣기.
                        //return "/common/daouPaySuccComplete";
                        return "SUCC";
                    }

                    McpRequestSelfDlvryDto reqDto = new McpRequestSelfDlvryDto();
                    reqDto.setSelfDlvryIdx(selfDlvryIdx);
                    Long resIdx = appformSvc.saveSelfDlvry(reqDto);
                    if( resIdx > 0 ){
                        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                                .currentRequestAttributes()).getRequest();

                        mcpRequestSelfDlvryDto = appformSvc.getMcpSelfDlvryData(selfDlvryIdx);
                        AppformReqDto param = new AppformReqDto();
                        if(mcpRequestSelfDlvryDto != null) {
                            int cnt = Integer.parseInt(mcpRequestSelfDlvryDto.getReqBuyQnty());
                            for(int i = 0; i < cnt; i++) {
                                param.setSelfDlvryIdx(selfDlvryIdx);
                                param.setRip(request.getRemoteAddr());
                                param.setUsimBuyDivCd("SELF");
                                appformSvc.insertNmcpUsimBuyTxn(param);
                            }
                        }

                        //returnUrl = "/common/daouPaySuccComplete";
                        returnUrl = "SUCC";
                    } else {
                        //returnUrl = "/common/daouPayFailComplete";
                        returnUrl = "FAIL";
                    }

                } else  if("NowDlvry".equals(reservedString) || "NowDlvry_Form".equals(reservedString) ){ // 셀프개통 유심 바로배송 요청

                    Long selfDlvryIdx = Long.parseLong(orderNo);

                    McpRequestSelfDlvryDto mcpRequestSelfDlvryDto = null;
                    Map<String,Object> map = new HashMap<String, Object>();
                    String resultCode = "99";
                    // 새로고침 막음
                    McpRequestSelfDlvryDto mcpRequestNowDlvryDto = appformSvc.getMcpNowDlvryData(selfDlvryIdx);
                    if(mcpRequestNowDlvryDto !=null ){
                        //return "/common/daouPaySuccComplete";
                        return "SUCC";
                    } else {
                        map = appformSvc.nowDlvryComplete(selfDlvryIdx);
                        mcpRequestSelfDlvryDto = (McpRequestSelfDlvryDto)map.get("mcpRequestSelfDlvryDto");
                        resultCode = (String)map.get("resultCode");

                        try{
                            MspSmsTemplateMstDto mspSmsTemplateMstDto = null;
                            // 고객연락처
                            StringBuffer str1 = new StringBuffer();
                            str1 = str1.append(mcpRequestSelfDlvryDto.getDlvryTelFn()).append(mcpRequestSelfDlvryDto.getDlvryTelMn()).append(mcpRequestSelfDlvryDto.getDlvryTelRn());
                            String orderRcvTlphNo = str1.toString();

                            if("00".equals(resultCode)){ // 성공

                                if (!"NowDlvry_Form".equals(reservedString)) {
                                    mspSmsTemplateMstDto =  fCommonSvc.getMspSmsTemplateMst(Constants.SMS_NOW_DELIVERY_TEMPLATE_ID);
                                    if (mspSmsTemplateMstDto != null) {
                                        Date today = new Date();
                                        String strToday = DateTimeUtil.changeFormat(today,"yy년 MM월 dd일");
                                        String addr =  StringUtil.NVL(mcpRequestSelfDlvryDto.getDlvryAddr(),"").trim();
                                        String addrDtl =  StringUtil.NVL(mcpRequestSelfDlvryDto.getDlvryAddrDtl(),"").trim();

                                        StringBuffer str = new StringBuffer();
                                        str = str.append(addr).append(" ").append(addrDtl);
                                        String smsMsg = mspSmsTemplateMstDto.getText();
                                        smsMsg = smsMsg.replace("#{date}", strToday).replace("#{address}", str);

                                        mspSmsTemplateMstDto.setText(smsMsg);
                                    }
                                } else {
                                  //온라인 신청서 상태값 변경 처리
                                  appformSvc.updateAppForPstate(mcpRequestSelfDlvryDto.getRequestKey());
                                  McpRequestDto mcpRequest = appformSvc.getMcpRequest(mcpRequestSelfDlvryDto.getRequestKey()) ;

                                  mspSmsTemplateMstDto =  fCommonSvc.getMspSmsTemplateMst(Constants.SMS_APP_NOW_DELIVERY_TEMPLATE_ID);
                                  if (mspSmsTemplateMstDto != null) {
                                      /*
                                       * 유심 수령 후, 영업일 기준 5일 이내에 유심번호를 등록
                                       * 영업일 기준 확인 할 수 없음
                                       */
                                      Date openExpDate = DateTimeUtil.getDateToCurrent(5);
                                      String strOpenExpDate = DateTimeUtil.changeFormat(openExpDate,"yy년 MM월 dd일");
                                      String resNo = mcpRequest.getResNo();
                                      String smsMsg = mspSmsTemplateMstDto.getText();
                                      smsMsg = smsMsg.replace("#{openExpDate}", strOpenExpDate).replace("#{resNo}", resNo);

                                      mspSmsTemplateMstDto.setText(smsMsg);
                                  }

                                }

                                //smsSvc.sendKakaoNoti( mspSmsTemplateMstDto.getSubject(), orderRcvTlphNo, mspSmsTemplateMstDto.getText(),mspSmsTemplateMstDto.getCallback(), mspSmsTemplateMstDto.getkTemplateCode(), Constants.KAKAO_SENDER_KEY);
                                smsSvc.sendKakaoNoti( mspSmsTemplateMstDto.getSubject(), orderRcvTlphNo, mspSmsTemplateMstDto.getText(),
                                        mspSmsTemplateMstDto.getCallback(), mspSmsTemplateMstDto.getkTemplateCode(),
                                        Constants.KAKAO_SENDER_KEY, String.valueOf(mspSmsTemplateMstDto.getTemplateId()));

                            } else { // 실패
                                if (!"NowDlvry_Form".equals(reservedString)) {
                                    mspSmsTemplateMstDto =  fCommonSvc.getMspSmsTemplateMst(Constants.SMS_NOW_DELIVERY_FAIL_TEMPLATE_ID);
                                } else {
                                    //온라인 신청서 바로 배송
                                    mspSmsTemplateMstDto =  fCommonSvc.getMspSmsTemplateMst(Constants.SMS_APP_NOW_DELIVERY_FAIL_TEMPLATE_ID);
                                }

                                if (mspSmsTemplateMstDto != null) {
                                    //smsSvc.sendKakaoNoti( mspSmsTemplateMstDto.getSubject(), orderRcvTlphNo, mspSmsTemplateMstDto.getText(),mspSmsTemplateMstDto.getCallback(), mspSmsTemplateMstDto.getkTemplateCode(), Constants.KAKAO_SENDER_KEY);
                                    smsSvc.sendKakaoNoti( mspSmsTemplateMstDto.getSubject(), orderRcvTlphNo, mspSmsTemplateMstDto.getText(),
                                            mspSmsTemplateMstDto.getCallback(), mspSmsTemplateMstDto.getkTemplateCode(),
                                            Constants.KAKAO_SENDER_KEY, String.valueOf(mspSmsTemplateMstDto.getTemplateId()));
                                }
                            }

                        } catch(DataAccessException e) {
                            logger.error("sms error"+e.getMessage());
                        } catch(Exception e){
                            logger.error("sms error"+e.getMessage());
                        }

                    }

                    if(!"".equals(resultCode)){
                        //returnUrl = "/common/daouPaySuccComplete";
                        returnUrl = "SUCC";
                    } else {
                        //returnUrl = "/common/daouPayFailComplete";
                        returnUrl = "FAIL";
                    }
                } else if ("DIRECT_PHONE".equals(reservedString)) {
                    //즉시 결제
                    /*

                    1. 상태값 , MCP_REQUEST_SALEINFO  UPDATE
                    COMMENT ON COLUMN MCP_MNG.MCP_REQUEST_SALEINFO.SETTL_APV_NO IS '결제 승인번호' ;
                    COMMENT ON COLUMN MCP_MNG.MCP_REQUEST_SALEINFO.SETTL_TRA_NO IS '결제 거래번호' ;

                    COMMENT ON COLUMN MCP_MNG.NMCP_DAOUPAY_INFO.AUTHNO IS '신용카드 승인번호' ;  authno;
                    COMMENT ON COLUMN MCP_MNG.NMCP_DAOUPAY_INFO.DAOUTRX IS '다우거래번호' ;    daoutrx
                    */
                    AppformReqDto appformReq = new AppformReqDto();
                    long requestKey = Long.parseLong(smartroDto.getRequestKey());
                    appformReq.setRequestKey(requestKey);
                    appformReq.setSettlApvNo(smartroDto.getAuthno());
                    appformReq.setSettlTraNo(smartroDto.getDaoutrx());


                    if (appformSvc.updateDirectPhone(appformReq)) {
                        //2. SMS 전송 처리 ..
                        AppformReqDto appformReqDb = appformSvc.getAppForm(appformReq);
                        try {
                            MspSmsTemplateMstDto mspSmsTemplateMstDto = fCommonSvc.getMspSmsTemplateMst(Constants.SMS_TEMPLATE_ID_NEW);

                            if (mspSmsTemplateMstDto != null) {
                                String resNo = appformReqDb.getResNo();
                                String rMobile = appformReqDb.getCstmrMobileFn() + appformReqDb.getCstmrMobileMn() + appformReqDb.getCstmrMobileRn();

                                mspSmsTemplateMstDto.setText(mspSmsTemplateMstDto.getText().replace("#{resNo}", resNo));
                                //smsSvc.sendKakaoNoti(mspSmsTemplateMstDto.getSubject(), rMobile, mspSmsTemplateMstDto.getText(), mspSmsTemplateMstDto.getCallback(), mspSmsTemplateMstDto.getkTemplateCode(), Constants.KAKAO_SENDER_KEY);
                                smsSvc.sendKakaoNoti(mspSmsTemplateMstDto.getSubject(), rMobile, mspSmsTemplateMstDto.getText(),
                                        mspSmsTemplateMstDto.getCallback(), mspSmsTemplateMstDto.getkTemplateCode(),
                                        Constants.KAKAO_SENDER_KEY, String.valueOf(Constants.SMS_TEMPLATE_ID_NEW));

                                //청소년 법정대리인에서도 SMS전송
                                if (CSTMR_TYPE_NM.equals(appformReqDb.getCstmrType())) {
                                    String rMinorMobile = appformReqDb.getMinorAgentTelFn() + appformReqDb.getMinorAgentTelMn() + appformReqDb.getMinorAgentTelRn();
                                    if (StringUtil.checkMobile(rMinorMobile)) {
                                        //smsSvc.sendKakaoNoti(mspSmsTemplateMstDto.getSubject(), rMinorMobile, mspSmsTemplateMstDto.getText(), mspSmsTemplateMstDto.getCallback(), mspSmsTemplateMstDto.getkTemplateCode(), Constants.KAKAO_SENDER_KEY);
                                        smsSvc.sendKakaoNoti(mspSmsTemplateMstDto.getSubject(), rMinorMobile, mspSmsTemplateMstDto.getText(),
                                                mspSmsTemplateMstDto.getCallback(), mspSmsTemplateMstDto.getkTemplateCode(),
                                                Constants.KAKAO_SENDER_KEY, String.valueOf(Constants.SMS_TEMPLATE_ID_NEW));
                                    }
                                }
                            }
                        }  catch (RestClientException e) {
                            throw new McpCommonJsonException("API 연동 오류");
                        }   catch (Exception e) {        //예외 전환 처리
                            throw new McpCommonJsonException(COMMON_EXCEPTION);
                        }
                        // SMS 전송 끝~!


                        returnUrl = "SUCC";
                    } else {
                        returnUrl = "FAIL";
                    }

                } else if ("SELF_SUF_PAY".equals(reservedString)) {
                    //자급제 완료 처리
                    //smartroDto.setRequestKey(strRequestKey);
                    if (appformSvc.useSelfFormSufPay(Long.parseLong(smartroDto.getRequestKey()))) {
                        returnUrl = "SUCC";
                    } else {
                        returnUrl = "FAIL";
                    }

                } else {
                    returnUrl = "FAIL";
                }

            } else {
                //returnUrl = "/common/daouPayFailComplete";
                returnUrl = "FAIL";
            }

        } catch(DataAccessException e) {
            return "FAIL";
        } catch(Exception e){
            return "FAIL";
        }

        return returnUrl;
    }

    @Override
    public Map<String, Object> cancelPay(HttpServletRequest req) {

        Map<String, Object> result = new HashMap<String, Object>();

        if("".equals(StringUtil.NVL(req.getParameter("Tid"), "")) || "".equals(StringUtil.NVL(req.getParameter("Mid"), ""))
                || "".equals(StringUtil.NVL(req.getParameter("CancelAmt"), ""))) {
            result.put("resltCd", "-1");
            result.put("msg", "유효하지 않은 접근입니다.");
            return result;
        }

        try {
            JSONObject body = new JSONObject();
            JSONObject paramData = new JSONObject();


            String Tid = req.getParameter("Tid");			// 취소 요청할 Tid 입력
            String Mid = req.getParameter("Mid");			// 발급받은 테스트 Mid 설정(Real 전환 시 운영 Mid 설정)
            String CancelAmt = "1004";	// 취소할 거래금액
            String CancelSeq = "1";		// 취소차수(기본값: 1, 부분취소 시마다 차수가 1씩 늘어남. 첫번째 부분취소=1, 두번째 부분취소=2, ...)
            String PartialCancelCode = "0";		// 0: 전체취소, 1: 부분취소
            String MerchantKey = "";  			// 발급받은 테스트 상점키 설정(Real 전환 시 운영 상점키 설정)
            // 검증값 SHA256 암호화(Tid + MerchantKey + CancelAmt + PartialCancelCode)
            String HashData = encodeSHA256Base64(Tid + MerchantKey + CancelAmt + PartialCancelCode);

        } catch(RuntimeException e) {
            logger.error("cancelPay Exception e : {}", e.getMessage());
        } catch(Exception e) {
            logger.error("cancelPay Exception e : {}", e.getMessage());
        }

        return result;
    }

    // 암호화 함수
    public static final String encodeSHA256Base64(String strPW) {
        String passACL = null;
        MessageDigest md = null;
        if (strPW == null ) { return "";}
        synchronized(strPW) {
            try {
                md = MessageDigest.getInstance("SHA-256");
            } catch (RuntimeException e) {
                logger.error("Exception e : {}", e.getMessage());
            } catch (Exception e) {
                logger.error("Exception e : {}", e.getMessage());
            }
            if (md == null ) { return "";}

            md.update(strPW.getBytes());
            byte[] raw = md.digest();
            byte[] encodedBytes = Base64.encodeBase64(raw);
            passACL = new String(encodedBytes);
        }

        return passACL;
    }
}
