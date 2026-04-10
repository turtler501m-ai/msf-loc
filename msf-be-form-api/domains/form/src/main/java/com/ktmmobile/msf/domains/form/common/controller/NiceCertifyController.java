//package com.ktmmobile.mcp.common.controller;
//
//import Kisinfo.Check.IPINClient;
//import NiceID.Check.CPClient;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import com.google.gson.JsonSyntaxException;
//import com.ktds.crypto.exception.CryptoException;
//import com.ktmmobile.mcp.app.service.AppSvc;
//import com.ktmmobile.mcp.appform.dto.JuoSubInfoDto;
//import com.ktmmobile.mcp.appform.dto.McpUploadPhoneInfoDto;
//import com.ktmmobile.mcp.appform.service.AppformSvc;
//import com.ktmmobile.mcp.cert.dto.CertDto;
//import com.ktmmobile.mcp.cert.service.CertService;
//import com.ktmmobile.mcp.common.constants.Constants;
//import com.ktmmobile.mcp.common.dao.LoginDao;
//import com.ktmmobile.mcp.common.dto.NiceLogDto;
//import com.ktmmobile.mcp.common.dto.NiceResDto;
//import com.ktmmobile.mcp.common.dto.NiceTryLogDto;
//import com.ktmmobile.mcp.common.dto.UserSessionDto;
//import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
//import com.ktmmobile.mcp.common.exception.McpCommonException;
//import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
//import com.ktmmobile.mcp.common.mplatform.vo.UserVO;
//import com.ktmmobile.mcp.common.service.IpStatisticService;
//import com.ktmmobile.mcp.common.service.NiceCertifySvc;
//import com.ktmmobile.mcp.common.service.NiceLogSvc;
//import com.ktmmobile.mcp.common.util.*;
//import com.ktmmobile.mcp.event.dto.UserPromotionDto;
//import com.ktmmobile.mcp.join.service.JoinSvc;
//import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
//import com.ktmmobile.mcp.mypage.service.MypageService;
//import com.ktmmobile.mcp.mypage.service.MypageUserService;
//
//import org.apache.commons.codec.binary.Base64;
//import org.apache.commons.httpclient.NameValuePair;
//import org.apache.commons.lang3.StringUtils;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.dao.DataAccessException;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.util.HtmlUtils;
//
//import javax.crypto.Cipher;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.IvParameterSpec;
//import javax.crypto.spec.SecretKeySpec;
//import jakarta.servlet.http.HttpServletRequest;
//import java.net.SocketTimeoutException;
//import java.util.*;
//
//import static com.ktmmobile.mcp.common.constants.Constants.*;
//import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.*;
//
///**
// * <pre>
// * 프로젝트 : kt M mobile
// * 파일명   : NiceCertifyController.java
// * 날짜     : 2016. 2. 22. 오후 2:51:41
// * 작성자   : papier
// * 설명     : NICE 개발팀 전화번호  02-2122-4872 ~ 4873
// * </pre>
// */
//@Controller
//public class NiceCertifyController {
//
//    private static final Logger logger = LoggerFactory.getLogger(NiceCertifyController.class);
//
//    @Autowired
//    NiceCertifySvc niceCertifySvc;
//
//    @Autowired
//    JoinSvc joinSvc;
//
//    @Autowired
//    NiceLogSvc nicelog ;
//
//    @Autowired
//    CertService certService;
//
//    @Autowired
//    AppformSvc appformSvc;
//
//    @Autowired
//    MypageUserService mypageUserService;
//
//    @Autowired
//    MypageService mypageService;
//
//    @Autowired
//    LoginDao loginDao;
//
//    @Autowired
//    private IpStatisticService ipstatisticService;
//
//    @Autowired
//    private AppSvc appSvc;
//
//    @Value("${SERVER_NAME}")
//    private String serverName;
//
//
//    @Value("${NICE_SITE_PASSWORD}")
//    private String niceSitePassword;
//
//
//    @Value("${NICE_IPIN_SITE_PASSWORD}")
//    private String niceIpinSitePassword;
//
//    @Value("${TOSS_CLIENT_ID}")
//    private String tossClientId;
//
//    @Value("${TOSS_CLIENT_SECRET}")
//    private String tossClientSecret;
//
//    @Value("${N_NICE_CLIENT_ID}")
//    private String niceClientId;
//
//    @Value("${N_NICE_CLIENT_SECRET}")
//    private String niceClientSecret;
//
//    @Value("${inf.url}")
//    private String infUrl;
//
//    @Value("${ext.url}")
//    private String extUrl;
//
//    @RequestMapping(value = "/nice/popNiceBlank.do")
//    public String popNiceBlank() {
//        return "/portal/nice/popNiceBlank";
//    }
//
//    /**
//     * <pre>
//     * 설명     :
//     * @param model
//     * @param sAuthType
//     * @param sCheck // InsrProd:분실파손 휴대폰 인증 처리
//     * @param mType //없으면 기본 웹페이지 / Mobile : 모바일페이지
//     * @param request
//     * @return
//     * @return: String
//     * </pre>
//     */
//    @RequestMapping(value = "/nice/popNiceAuth.do")
//    public String popNiceAuth(
//            Model model
//            , @RequestParam("sAuthType") String sAuthType
//            , @RequestParam(defaultValue="") String sCheck
//            , @RequestParam(defaultValue="") String mType
//            , @RequestParam(defaultValue="") String returnUrl
//            , HttpServletRequest request) {
//
//
//        // 신용카드/범용인증 이용가능여부 확인 (DB통제 페이지만)
//        if("C".equalsIgnoreCase(sAuthType) || "X".equalsIgnoreCase(sAuthType)){
//
//            String prcType= StringUtil.NVL(request.getParameter("prcType"), "PARTIAL");
//
//            if("COMMON".equalsIgnoreCase(prcType)){
//
//                String grpCd= "pSimpleAuth";
//                if(!"P".equals(NmcpServiceUtils.getPlatFormCd())){
//                    grpCd= "mSimpleAuth";
//                }
//
//                NmcpCdDtlDto cdDtlDto= NmcpServiceUtils.getCodeNmDto(grpCd, sAuthType.toUpperCase());
//                if(cdDtlDto == null || !DateTimeUtil.checkValidDate(cdDtlDto.getPstngStartDate(), cdDtlDto.getPstngEndDate())){
//                    throw new McpCommonException(AUTH_TYPE_EXCEPTION);
//                }
//            }
//
//            // SMS 인증 완료 여부 확인 - 신규셀프개통인 경우만
//            if(!niceCertifySvc.preChkSimpleAuth()){
//                throw new McpCommonException(F_BIND_EXCEPTION);
//            }
//        }
//
//        //sAuthType M: 핸드폰, C: 신용카드, X: 공인인증서
//        String popgubun 	= "N";		//Y : 취소버튼 있음 / N : 취소버튼 없음
//        String customize 	= mType == null ? "":"Mobile".equals(mType)?"Mobile":"";			//없으면 기본 웹페이지 / Mobile : 모바일페이지
//
//        //CPClient niceCheck = new  CPClient();
//        //String sRequestNumber = "";        	// 요청 번호, 이는 성공/실패후에 같은 값으로 되돌려주게 되므로
//        //sRequestNumber = niceCheck.getRequestNO(NICE_SITE_CODE);
//
//        String returnUrl2= "";
//        if ("".equals(returnUrl)) {
//            returnUrl2 = NICE_SUCC_RETURN_URL ;
//        } else {
//            returnUrl2 = returnUrl ;
//        }
//
//        String tempReturnUrl = "https://"+ request.getServerName()  + returnUrl2;
//        String tempErrorUrl = "https://"+ request.getServerName() + NICE_FAILL_RETURN_URL;
//
//        if ( "DEV".equals(serverName) ) {
//            tempReturnUrl = "https://dmcpdev.ktmmobile.com:8012"+ returnUrl2;
//            tempErrorUrl = "https://dmcpdev.ktmmobile.com:8012" + NICE_FAILL_RETURN_URL;
//        }
//
//        if ( "LOCAL".equals(serverName) ) {
//            //페이스북 보안이슈로 http -> https 로 사용
//            //tempReturnUrl = "http://"+ request.getServerName() + returnUrl2;
//            //tempErrorUrl = "http://"+ request.getServerName() + NICE_FAILL_RETURN_URL;
//
//            customize = "";
//
//            //강제로 성공 처리
//            String strReqSeq= "TestReq" + DateTimeUtil.getFormatString("yyyyMMddHHmmss");
//            String strResSeq= "TestRes" + DateTimeUtil.getFormatString("yyyyMMddHHmmss");
//
//            // ========== 본인인증 리턴 로직 개선 START ==========
//            // 1. 본인인증 이력 저장 (로컬 강제성공처리를 위해서는.. niceLogDto정보 변경)
//            NiceLogDto niceLogDto = new NiceLogDto();
//            niceLogDto.setReqSeq(strReqSeq);
//            niceLogDto.setResSeq(strResSeq);
//            niceLogDto.setAuthType(sAuthType);
//            niceLogDto.setName("양우철");
//            niceLogDto.setBirthDate("19741219");
//            niceLogDto.setGender("0");
//            niceLogDto.setNationalInfo("0");
//            niceLogDto.setDupInfo("MC0GCCqGSIb3DQIJAyEAFZkqN+NbNzMJWYsjgOOmhY0o8lsP/AX4s7erKvyfoKk=");
//            niceLogDto.setConnInfo("vTAH+121212+tCH5bVO/crf7t9a3w==");
//            niceLogDto.setsMobileNo("01099999999");
//
//            if("M".equals(sAuthType)){
//                niceLogDto.setsMobileNo("01000000000");
//                niceLogDto.setsMobileCo("KTM");
//                niceLogDto.setParamR3(SMS_AUTH);
//            }else{
//                niceLogDto.setParamR3(CUST_AUTH);
//            }
//
//            nicelog.insertMcpNiceHist(niceLogDto);
//
//            // 2. 화면 리턴값
//            NiceResDto niceResDtoSess = new NiceResDto();
//            niceResDtoSess.setReqSeq(strReqSeq);
//            niceResDtoSess.setResSeq(strResSeq);
//            niceResDtoSess.setAuthType(sAuthType);
//
//            model.addAttribute("NiceRes", niceResDtoSess);
//
//            // ========== 본인인증 리턴 로직 개선 END ==========
//
//            // 화면에서 넘어온 returnUrl에 해당하는 화면으로 보내기
//            String jspPageNm= "/portal/nice/popNiceSucc";
//
//            if("/nice/popNiceSuccOpenLayer.do".equals(returnUrl2)){
//                jspPageNm= "/portal/nice/popNiceSuccOpenLayer";
//            }else if("/nice/popNiceSuccM.do".equals(returnUrl2)){
//                jspPageNm= "/portal/nice/popNiceSuccM";
//            }
//
//            return jspPageNm;
//
//        } else if ("DEV".equals(serverName)) {
//            customize = "";
//        }
//
//        // 2024-01-26 NICE 본인인증 요청로직 변경
//        // M:SMS인증, C:신용카드, X:범용인증서
//        try {
//            JsonParser jsonParser = new JsonParser();
//            CommonHttpClient client = new CommonHttpClient(extUrl+"/niceCertifyApi.do");
//
//            NameValuePair[] data = {
//                new NameValuePair("clientId", niceClientId),         // NICE 에서 제공받은 clientId
//                new NameValuePair("clientSecret", niceClientSecret), // NICE 에서 제공받은 clientSecret
//                new NameValuePair("returnUrl", tempReturnUrl),       // returnUrl  /nice/popNiceSucc.do
//                new NameValuePair("authType", sAuthType)             // 인증타입 M:SMS인증, C:신용카드, X:범용인증서
//            };
//
//            String responseBody = client.postUtf8(data);
//
//            if ("".equals(responseBody) || responseBody == null) {
//                logger.error("=========== NICE accessToken 발급 실패 >> " + responseBody);
//                throw new McpCommonException(NICE_ENC_EXCEPTION);
//            }
//
//            JsonObject jsonObject = (JsonObject) jsonParser.parse(responseBody);
//            String resultCd = jsonObject.get("resultCd").toString().replaceAll("\"", "");
//
//            if(!"0000".equals(resultCd)) {
//                logger.error("=========== NICE 암호화 토큰 발급 실패 0001: 필수입력값 오류, 0003: OTP 발급 대상 회원사 아님, 0099: 기타오류 >> " + resultCd);
//                throw new McpCommonException(NICE_ENC_EXCEPTION);
//            } else { // SUCCESS
//                String tokenVersionId = jsonObject.get("tokenVersionId").toString().replaceAll("\"", ""); // 암호화토큰요청_API 응답으로 받은 값
//                String encData = jsonObject.get("encData").toString().replaceAll("\"", "");               // 암호화된 요청데이터
//                String integrityValue = jsonObject.get("integrityValue").toString().replaceAll("\"", ""); // enc_data의 무결성 값
//
//                model.addAttribute("tokenVersionId", tokenVersionId);
//                model.addAttribute("encData", encData);
//                model.addAttribute("integrityValue", integrityValue);
//
//                // 결과값 복호화를 위해 대칭키 session 에 저장
//                String key = jsonObject.get("key").toString().replaceAll("\"", "");
//                String iv = jsonObject.get("iv").toString().replaceAll("\"", "");
//                SessionUtils.saveNiceKeySession(key, iv);
//                // logger.error("NICE 성공 {}, {}, {}, {}, {}" , tokenVersionId, encData, integrityValue, key, iv);
//            }
//        } catch(SocketTimeoutException e) {
//            logger.error("NICE 팝업 창 불러오기 연동 시 오류 발생 SocketTimeoutException");
//            throw new McpCommonException(NICE_ENC_EXCEPTION);
//        } catch (Exception e) {
//            logger.error("NICE 팝업 창 불러오기 연동 시 오류 발생 Exception");
//            throw new McpCommonException(NICE_ENC_EXCEPTION);
//        }
//
//        return "/portal/nice/popNiceAuth";
//    }
//
//    @RequestMapping(value = "/nice/popNiceSucc.do")
//    public String popNiceSucc(HttpServletRequest request, Model model, NiceResDto niceResDto) {
//
//        // 대칭키 가져오기
//        Map<String,String> niceKey = SessionUtils.getNiceKeySession();
//        String key = niceKey.get("key");
//        String iv = niceKey.get("iv");
//
//        // 응답값 복호화
//        String encData = niceResDto.getEnc_data();
//        Map<String,String> returnData = new HashMap<String,String>();
//        try {
//            SecretKey secureKey = new SecretKeySpec(key.getBytes(), "AES");
//            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
//            c.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(iv.getBytes()));
//            byte[] cipherEnc = Base64.decodeBase64(encData);
//            String resData = new String(c.doFinal(cipherEnc), "euc-kr");
//            returnData = new ObjectMapper().readValue(resData, new TypeReference<Map<String, String>>(){});
//            // logger.error("NICE 응답 복호화 : " + returnData);
//        } catch (Exception e) {
//            throw new McpCommonException(NICE_DECRYPT_EXCEPTION);
//        } finally {
//            // 대칭키 삭제
//            request.getSession().removeAttribute(SessionUtils.NICE_AUTH_ENC_KEY);
//        }
//
//        if (returnData != null) {
//            String sAuthType = "";           // 인증수단 (M:휴대폰인증, C:카드본인확인, X:공동인증서, F:금융인증서, S:PASS인증서)
//            String sName = "";               // 성명
//            String sRequestNumber = "";      // 요청 번호
//            String sResponseNumber = "";     // 인증 고유번호
//            String sBirthDate = "";          // 생년월일 8자리
//            String sGender = "";             // 성별 (0:여성, 1:남성)
//            String sNationalInfo = "";       // 내/외국인정보 (0:내국인, 1:외국인)
//            String sMobileCo = "";           // 통신사 (1:SK텔레콤, 2:KT, 3:LGU+, 5:SK텔레콤 알뜰폰, 6:KT 알뜰폰, 7:LGU+ 알뜰폰)
//            String sMobileNo = "";           // 모바일 번호 01012345678
//            String sConnInfo = "";           // 연계정보 확인값 (CI_88 byte)
//            String sDupInfo = "";            // 중복가입 확인값 (DI_64 byte)
//            String sPlainData = "";
//
//            sAuthType = (String)returnData.get("authtype");
//            sName = (String)returnData.get("name");
//            sRequestNumber = (String)returnData.get("requestno");
//            sResponseNumber = (String)returnData.get("responseno");
//            sBirthDate = (String)returnData.get("birthdate");
//            sGender = (String)returnData.get("gender");
//            sNationalInfo = (String)returnData.get("nationalinfo");
//            sMobileCo = (String)returnData.get("mobileco");
//            if ("1".equals(sMobileCo)) {
//                sMobileCo = "SKT";
//            } else if ("2".equals(sMobileCo)) {
//                sMobileCo = "KTF";
//            } else if ("3".equals(sMobileCo)) {
//                sMobileCo = "LGT";
//            } else if ("5".equals(sMobileCo)) {
//                sMobileCo = "SKM";
//            } else if ("6".equals(sMobileCo)) {
//                sMobileCo = "KTM";
//            } else if ("7".equals(sMobileCo)) {
//                sMobileCo = "LGM";
//            }
//            sMobileNo = (String)returnData.get("mobileno");
//            sConnInfo = (String)returnData.get("ci");
//            sDupInfo = (String)returnData.get("di");
//
//            // STG 미성년자 테스트를 위해 휴대폰 인증정보 미성년자로 바꿔치기
//            if("STG".equals(serverName)){
//
//                UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
//                String userId = userSessionDto == null ? "" : userSessionDto.getUserId();
//                NmcpCdDtlDto nmcpCdDtlDto = NmcpServiceUtils.getCodeNmDto("idGroupException", userId);
//
//                if(nmcpCdDtlDto != null && "Y".equals(nmcpCdDtlDto.getExpnsnStrVal1())){
//                  sBirthDate = "20111231";
//                  sGender = "4";
//                }
//            }
//
//            // ========== 본인인증 리턴 로직 개선 START ==========
//            // 1. 본인인증 이력 저장
//            NiceLogDto niceLogDto = new NiceLogDto();
//            niceLogDto.setReqSeq(sRequestNumber);
//            niceLogDto.setResSeq(sResponseNumber);
//            niceLogDto.setAuthType(sAuthType);
//            niceLogDto.setName(sName);
//            niceLogDto.setBirthDate(sBirthDate);
//            niceLogDto.setGender(sGender);
//            niceLogDto.setNationalInfo(sNationalInfo);
//            niceLogDto.setDupInfo(sDupInfo);
//            niceLogDto.setConnInfo(sConnInfo);
//            niceLogDto.setsMobileNo(sMobileNo);
//            niceLogDto.setsMobileCo(sMobileCo);
//
//            if("M".equals(sAuthType)) niceLogDto.setParamR3(SMS_AUTH);
//            else niceLogDto.setParamR3(CUST_AUTH);
//
//            nicelog.insertMcpNiceHist(niceLogDto);
//
//            // 2. 화면 리턴값
//            NiceResDto niceResDtoSess = new NiceResDto();
//            niceResDtoSess.setReqSeq(sRequestNumber);
//            niceResDtoSess.setResSeq(sResponseNumber);
//            niceResDtoSess.setAuthType(sAuthType);
//
//            model.addAttribute("NiceRes", niceResDtoSess);
//
//            NiceResDto niceResDtoNewSess = new NiceResDto();
//            niceResDtoNewSess.setsMobileNo(sMobileNo);
//
//            //셀프개통+신규가입 휴대폰인증 세션저장
//            if("M".equals(sAuthType)) {
//                SessionUtils.saveNiceMobileSession(niceResDtoNewSess);
//            }
//
//            // ========== 본인인증 리턴 로직 개선 END ==========
//
//        } else {
//            throw new McpCommonException(NICE_DECRYPT_EXCEPTION);
//        }
//
//        return "/portal/nice/popNiceSucc";
//    }
//
//    /*
//     * 팝업 레이어 인증 처리
//     * 기존 함수 하고 중복 발생 할 수 있음
//     * 신규로 만든
//     */
//    @RequestMapping(value = "/nice/popNiceSuccOpenLayer.do")
//    public String popNiceSuccOpenLayer(HttpServletRequest request, Model model, NiceResDto niceResDto) {
//
//        // 대칭키 가져오기
//        Map<String,String> niceKey = SessionUtils.getNiceKeySession();
//        String key = niceKey.get("key");
//        String iv = niceKey.get("iv");
//
//        // 응답값 복호화
//        String encData = niceResDto.getEnc_data();
//        Map<String,String> returnData = new HashMap<String,String>();
//        try {
//            SecretKey secureKey = new SecretKeySpec(key.getBytes(), "AES");
//            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
//            c.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(iv.getBytes()));
//            byte[] cipherEnc = Base64.decodeBase64(encData);
//            String resData = new String(c.doFinal(cipherEnc), "euc-kr");
//            returnData = new ObjectMapper().readValue(resData, new TypeReference<Map<String, String>>(){});
//            // logger.error("NICE 응답 복호화 : " + returnData);
//        } catch (Exception e) {
//            throw new McpCommonException(NICE_DECRYPT_EXCEPTION);
//        } finally {
//            // 대칭키 삭제
//            request.getSession().removeAttribute(SessionUtils.NICE_AUTH_ENC_KEY);
//        }
//
//        if (returnData != null) {
//            String sAuthType = "";           // 인증수단 (M:휴대폰인증, C:카드본인확인, X:공동인증서, F:금융인증서, S:PASS인증서)
//            String sName = "";               // 성명
//            String sRequestNumber = "";      // 요청 번호
//            String sResponseNumber = "";     // 인증 고유번호
//            String sBirthDate = "";          // 생년월일 8자리
//            String sGender = "";             // 성별 (0:여성, 1:남성)
//            String sNationalInfo = "";       // 내/외국인정보 (0:내국인, 1:외국인)
//            String sMobileCo = "";           // 통신사 (1:SK텔레콤, 2:KT, 3:LGU+, 5:SK텔레콤 알뜰폰, 6:KT 알뜰폰, 7:LGU+ 알뜰폰)
//            String sMobileNo = "";           // 모바일 번호 01012345678
//            String sConnInfo = "";           // 연계정보 확인값 (CI_88 byte)
//            String sDupInfo = "";            // 중복가입 확인값 (DI_64 byte)
//            String sPlainData = "";
//
//            sAuthType = (String)returnData.get("authtype");
//            sName = (String)returnData.get("name");
//            sRequestNumber = (String)returnData.get("requestno");
//            sResponseNumber = (String)returnData.get("responseno");
//            sBirthDate = (String)returnData.get("birthdate");
//            sGender = (String)returnData.get("gender");
//            sNationalInfo = (String)returnData.get("nationalinfo");
//            sMobileCo = (String)returnData.get("mobileco");
//            if ("1".equals(sMobileCo)) {
//                sMobileCo = "SKT";
//            } else if ("2".equals(sMobileCo)) {
//                sMobileCo = "KTF";
//            } else if ("3".equals(sMobileCo)) {
//                sMobileCo = "LGT";
//            } else if ("5".equals(sMobileCo)) {
//                sMobileCo = "SKM";
//            } else if ("6".equals(sMobileCo)) {
//                sMobileCo = "KTM";
//            } else if ("7".equals(sMobileCo)) {
//                sMobileCo = "LGM";
//            }
//            sMobileNo = (String)returnData.get("mobileno");
//            sConnInfo = (String)returnData.get("ci");
//            sDupInfo = (String)returnData.get("di");
//
//            // ========== 본인인증 리턴 로직 개선 START ==========
//            // 1. 본인인증 이력 저장
//            NiceLogDto niceLogDto = new NiceLogDto();
//            niceLogDto.setReqSeq(sRequestNumber);
//            niceLogDto.setResSeq(sResponseNumber);
//            niceLogDto.setAuthType(sAuthType);
//            niceLogDto.setName(sName);
//            niceLogDto.setBirthDate(sBirthDate);
//            niceLogDto.setGender(sGender);
//            niceLogDto.setNationalInfo(sNationalInfo);
//            niceLogDto.setDupInfo(sDupInfo);
//            niceLogDto.setConnInfo(sConnInfo);
//            niceLogDto.setsMobileNo(sMobileNo);
//            niceLogDto.setsMobileCo(sMobileCo);
//            niceLogDto.setParamR1("OpenLayer");
//
//            if("M".equals(sAuthType)) niceLogDto.setParamR3(SMS_AUTH);
//            else niceLogDto.setParamR3(CUST_AUTH);
//
//            nicelog.insertMcpNiceHist(niceLogDto);
//
//            // 2. 화면 리턴값
//            NiceResDto niceResDtoSess = new NiceResDto();
//            niceResDtoSess.setReqSeq(sRequestNumber);
//            niceResDtoSess.setResSeq(sResponseNumber);
//            niceResDtoSess.setAuthType(sAuthType);
//
//            model.addAttribute("NiceRes", niceResDtoSess) ;
//
//            // ========== 본인인증 리턴 로직 개선 END ==========
//
//        } else {
//            throw new McpCommonException(NICE_DECRYPT_EXCEPTION);
//        }
//        return "/portal/nice/popNiceSuccOpenLayer";
//    }
//
//    @RequestMapping(value = "/nice/popNiceSuccM.do")
//    public String popNiceSuccM(HttpServletRequest request, Model model, NiceResDto niceResDto) {
//
//        // 대칭키 가져오기
//        Map<String,String> niceKey = SessionUtils.getNiceKeySession();
//        String key = niceKey.get("key");
//        String iv = niceKey.get("iv");
//
//        // 응답값 복호화
//        String encData = niceResDto.getEnc_data();
//        Map<String,String> returnData = new HashMap<String,String>();
//        try {
//            SecretKey secureKey = new SecretKeySpec(key.getBytes(), "AES");
//            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
//            c.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(iv.getBytes()));
//            byte[] cipherEnc = Base64.decodeBase64(encData);
//            String resData = new String(c.doFinal(cipherEnc), "euc-kr");
//            returnData = new ObjectMapper().readValue(resData, new TypeReference<Map<String, String>>(){});
//            // logger.error("NICE 응답 복호화 : " + returnData);
//        } catch (Exception e) {
//            throw new McpCommonException(NICE_DECRYPT_EXCEPTION);
//        } finally {
//            // 대칭키 삭제
//            request.getSession().removeAttribute(SessionUtils.NICE_AUTH_ENC_KEY);
//        }
//
//        if (returnData != null) {
//            String sAuthType = "";           // 인증수단 (M:휴대폰인증, C:카드본인확인, X:공동인증서, F:금융인증서, S:PASS인증서)
//            String sName = "";               // 성명
//            String sRequestNumber = "";      // 요청 번호
//            String sResponseNumber = "";     // 인증 고유번호
//            String sBirthDate = "";          // 생년월일 8자리
//            String sGender = "";             // 성별 (0:여성, 1:남성)
//            String sNationalInfo = "";       // 내/외국인정보 (0:내국인, 1:외국인)
//            String sMobileCo = "";           // 통신사 (1:SK텔레콤, 2:KT, 3:LGU+, 5:SK텔레콤 알뜰폰, 6:KT 알뜰폰, 7:LGU+ 알뜰폰)
//            String sMobileNo = "";           // 모바일 번호 01012345678
//            String sConnInfo = "";           // 연계정보 확인값 (CI_88 byte)
//            String sDupInfo = "";            // 중복가입 확인값 (DI_64 byte)
//            String sPlainData = "";
//
//            sAuthType = (String)returnData.get("authtype");
//            sName = (String)returnData.get("name");
//            sRequestNumber = (String)returnData.get("requestno");
//            sResponseNumber = (String)returnData.get("responseno");
//            sBirthDate = (String)returnData.get("birthdate");
//            sGender = (String)returnData.get("gender");
//            sNationalInfo = (String)returnData.get("nationalinfo");
//            sMobileCo = (String)returnData.get("mobileco");
//            if ("1".equals(sMobileCo)) {
//                sMobileCo = "SKT";
//            } else if ("2".equals(sMobileCo)) {
//                sMobileCo = "KTF";
//            } else if ("3".equals(sMobileCo)) {
//                sMobileCo = "LGT";
//            } else if ("5".equals(sMobileCo)) {
//                sMobileCo = "SKM";
//            } else if ("6".equals(sMobileCo)) {
//                sMobileCo = "KTM";
//            } else if ("7".equals(sMobileCo)) {
//                sMobileCo = "LGM";
//            }
//            sMobileNo = (String)returnData.get("mobileno");
//            sConnInfo = (String)returnData.get("ci");
//            sDupInfo = (String)returnData.get("di");
//
//            //사용자 로그인 세션이 존재할 경우 사용자의 이름과 생년월일이 리턴값과 일치하지 않으면 에러페이지로 이동한다.
//            String sReserved1  = "";
//            String sReserved3  = "";
//
//            NiceResDto sessNiceParam =  SessionUtils.getNiceParam() ;
//            if (sessNiceParam != null) {
//                sReserved1  = sessNiceParam.getParam_r1();
//                sReserved3  = sessNiceParam.getParam_r3();
//            }
//
//            //MCP_NICE_HIST 정보조회
//            NiceLogDto niceLogTmp = new NiceLogDto();
//            niceLogTmp.setName(sName);
//            if (sBirthDate.length() > 6 ) {
//                String tempBirthDate =  sBirthDate.substring(2, 8);
//                niceLogTmp.setBirthDate(tempBirthDate);
//            } else {
//                niceLogTmp.setBirthDate(sBirthDate);
//            }
//            niceLogTmp.setEndYn("N");
//            NiceLogDto niceLogRtn = nicelog.getMcpNiceHist(niceLogTmp);
//
//            if (niceLogRtn != null) {
//                //정보가 있는것이 .. 쓰레기 정보
//                if (niceLogRtn.getReqSeq() == null || "".equals(niceLogRtn.getReqSeq())) {
//
//                    if ("".equals(sReserved1)) {
//                        sReserved1  = niceLogRtn.getParamR1();
//                        sReserved3  = niceLogRtn.getParamR3();
//                    }
//                    niceLogRtn.setReqSeq(sRequestNumber);
//                    niceLogRtn.setResSeq(sResponseNumber);
//                    niceLogRtn.setAuthType(sAuthType);
//                    niceLogRtn.setName("");
//                    niceLogRtn.setBirthDate("");
//                    niceLogRtn.setGender(sGender);
//                    niceLogRtn.setNationalInfo(sNationalInfo);
//                    niceLogRtn.setDupInfo(sDupInfo);
//                    niceLogRtn.setConnInfo(sConnInfo);
//                    niceLogRtn.setComplYn("Y"); //인증 완료 시간 UPDATE 처리
//                    nicelog.updateMcpNiceHist(niceLogRtn);
//
//                }
//            } else {
//                if ("".equals(sReserved3) && "M".equals(sAuthType)) {
//                    /*
//                     * 홍길동에서 본인인증 하고
//                     * 휴대폰 인증을 말똥이로 하면..
//                     * 정보가 없고 휴대폰 인증이며..  휴대폰 안심 서비스로 인지 처리
//                     */
//                    sReserved3 = INSR_PROD;
//                }
//            }
//
//            UserSessionDto userSession = (UserSessionDto)request.getSession().getAttribute(SessionUtils.USER_SESSION);
//            NiceResDto niceResDtoSess = new NiceResDto();
//            niceResDtoSess.setReqSeq(sRequestNumber);
//            niceResDtoSess.setResSeq(sResponseNumber);
//            niceResDtoSess.setAuthType(sAuthType);
//            niceResDtoSess.setName(sName);
//            niceResDtoSess.setBirthDate(sBirthDate);
//            niceResDtoSess.setGender(sGender);
//            niceResDtoSess.setNationalInfo(sNationalInfo);
//            niceResDtoSess.setDupInfo(sDupInfo);
//            niceResDtoSess.setConnInfo(sConnInfo);
//            niceResDtoSess.setsMobileNo(sMobileNo);
//            niceResDtoSess.setsMobileCo(sMobileCo);
//
//            // ========== 본인인증 리턴 로직 개선 START ==========
//            // 1. 본인인증 이력 저장
//            NiceLogDto nicelogDto= new NiceLogDto();
//            nicelogDto.setnReferer(sReserved1);
//            nicelog.insert(request , niceResDtoSess , nicelogDto);
//
//            NiceLogDto newNiceLogDto = new NiceLogDto();
//            newNiceLogDto.setReqSeq(sRequestNumber);
//            newNiceLogDto.setResSeq(sResponseNumber);
//            newNiceLogDto.setAuthType(sAuthType);
//            newNiceLogDto.setName(sName);
//            newNiceLogDto.setBirthDate(sBirthDate);
//            newNiceLogDto.setGender(sGender);
//            newNiceLogDto.setNationalInfo(sNationalInfo);
//            newNiceLogDto.setDupInfo(sDupInfo);
//            newNiceLogDto.setConnInfo(sConnInfo);
//            newNiceLogDto.setsMobileNo(sMobileNo);
//            newNiceLogDto.setsMobileCo(sMobileCo);
//            if("M".equals(sAuthType)) newNiceLogDto.setParamR3(SMS_AUTH);
//            nicelog.insertMcpNiceHist(newNiceLogDto);
//
//            // 2. 화면 리턴값 설정
//            NiceResDto rtnNiceResDto = new NiceResDto();
//            rtnNiceResDto.setReqSeq(sRequestNumber);
//            rtnNiceResDto.setResSeq(sResponseNumber);
//            rtnNiceResDto.setAuthType(sAuthType);
//            // ========== 본인인증 리턴 로직 개선 END ==========
//
//            // param_r3 값이 U 이면 사용자 SESSION 을 확인한다.
//            if(sReserved3 != null && "U".equalsIgnoreCase(sReserved3) && userSession != null ) {
//                if(!userSession.getName().equalsIgnoreCase(sName) || !userSession.getBirthday().equalsIgnoreCase(sBirthDate)) {
//                    model.addAttribute("ErrorMsg", "사용자 정보와 일치하지 않습니다.");
//                    return "/portal/nice/popNiceFail";
//                }
//            }
//            // 명의변경신청시에만 사용 : param_r3 값이 W 이면 사용자 SESSION 을 확인한다.
//            if(sReserved3 != null && "W".equalsIgnoreCase(sReserved3)) {
//                if(userSession != null) {
//                    if(!userSession.getName().equalsIgnoreCase(sName)){
//                        model.addAttribute("ErrorMsg", "사용자 정보와 일치하지 않습니다.");
//                        return "/portal/nice/popNiceFail";
//                    }
//                }
//            }
//
//
//            if (INSR_PROD.equals(sReserved3)) {
//
//                String seCiInfo = "";
//                niceResDtoSess.setParam_r3(INSR_PROD);
//                rtnNiceResDto.setParam_r3(INSR_PROD);
//
//                if( SessionUtils.hasNiceRes() ) {
//                    NiceResDto sessNiceRes =  SessionUtils.getNiceResCookieBean() ;
//                    seCiInfo = sessNiceRes.getConnInfo();
//                } else {
//
//                    //session 정보을 읽어 올수 없을때.. DB에서 한번더 확인
//                    //PARAM_R3
//                    niceLogTmp.setParamR3(CUST_AUTH);
//                    if (sBirthDate.length() > 6 ) {
//                        String tempBirthDate =  sBirthDate.substring(2,8);
//                        niceLogTmp.setBirthDate(tempBirthDate);
//                    } else {
//                        niceLogTmp.setBirthDate(sBirthDate);
//                    }
//                    NiceLogDto niceLogRtn2 = nicelog.getMcpNiceHist(niceLogTmp);
//                    if (niceLogRtn2 != null) {
//                        seCiInfo = niceLogRtn2.getConnInfo();
//                    } else {
//                        niceResDtoSess.setParam_r1("-1");
//                        rtnNiceResDto.setParam_r1("-1");
//                    }
//                }
//
//                if (niceResDtoSess.getConnInfo().equals(seCiInfo) ) {
//                    niceResDtoSess.setParam_r1("1");
//                    rtnNiceResDto.setParam_r1("1");
//                    SessionUtils.saveNiceInsrRes(niceResDtoSess);
//                } else {
//                    niceResDtoSess.setParam_r1("-1");
//                    rtnNiceResDto.setParam_r1("-1");
//                }
//
//            } else if (SMS_AUTH.equals(sReserved3)) {
//                niceResDtoSess.setParam_r3(SMS_AUTH);
//                rtnNiceResDto.setParam_r3(SMS_AUTH);
//            } else {
//                if (!CUST_AUTH.equals(sReserved3)) {
//                    model.addAttribute("NextUrl", sReserved3) ;
//                }
//                SessionUtils.saveNiceRes(niceResDtoSess);
//            }
//
//            // model.addAttribute("NiceRes", niceResDtoSess) ;
//            model.addAttribute("NiceRes", rtnNiceResDto);
//
//        } else {
//            throw new McpCommonException(NICE_DECRYPT_EXCEPTION);
//        }
//
//        return "/portal/nice/popNiceSuccM";
//
//    }
//
//
//    @RequestMapping(value = "/nice/popNiceResult.do")
//    public String popNiceResult(HttpServletRequest request,
//            Model model
//            , NiceResDto niceResDto) {
//
//        CPClient niceCheck = new  CPClient();
//
//        String tempEncodDate =  HtmlUtils.htmlUnescape(niceResDto.getEncodeData());
//        int iReturn = niceCheck.fnDecode(NICE_SITE_CODE, niceSitePassword, tempEncodDate);
//
//        if( iReturn == 0 ){
//            String sRequestNumber = "";          // 요청 번호
//            String sResponseNumber = "";         // 인증 고유번호
//            String sAuthType = "";                 // 인증 수단
//            String sName = "";                           // 성명
//            String sDupInfo = "";                        // 중복가입 확인값 (DI_64 byte)
//            String sConnInfo = "";                   // 연계정보 확인값 (CI_88 byte)
//            String sBirthDate = "";                  // 생일
//            String sGender = "";                         // 성별
//            String sNationalInfo = "";       // 내/외국인정보 (개발가이드 참조)
//            String sPlainData = "";
//            String sMobileNo = "";
//            String sMobileCo = "";
//
//            sPlainData = niceCheck.getPlainData();
//
//            // 데이타를 추출합니다.
//            java.util.HashMap mapresult = niceCheck.fnParse(sPlainData);
//
//            sRequestNumber  = (String)mapresult.get("REQ_SEQ");
//            sResponseNumber = (String)mapresult.get("RES_SEQ");
//            sAuthType           = (String)mapresult.get("AUTH_TYPE");
//            sName                   = (String)mapresult.get("NAME");
//            sBirthDate          = (String)mapresult.get("BIRTHDATE");
//            sGender                 = (String)mapresult.get("GENDER");
//            sNationalInfo   = (String)mapresult.get("NATIONALINFO");
//            sDupInfo                = (String)mapresult.get("DI");
//            sConnInfo           = (String)mapresult.get("CI");
//            sMobileNo = (String)mapresult.get("MOBILE_NO");
//            sMobileCo = (String)mapresult.get("MOBILE_CO");
//
//            NiceResDto niceResDtoSess = new  NiceResDto();
//            niceResDtoSess.setReqSeq(sRequestNumber);
//            niceResDtoSess.setResSeq(sResponseNumber);
//            niceResDtoSess.setAuthType(sAuthType);
//
//            model.addAttribute("NiceRes", niceResDtoSess) ;
//        } else {
//            throw new McpCommonException(NICE_DECRYPT_EXCEPTION);
//        }
//
//
//        return "/portal/nice/popNiceSuccM";
//    }
//
//    @RequestMapping(value = "/nice/insertNiceLogAjax.do")
//    @ResponseBody
//    public Map<String, Object> insertNiceLog(NiceLogDto niceLogDto) {
//        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
//        //session 저장 처리
//        NiceResDto niceResDtoSess = new  NiceResDto();
//
//        niceResDtoSess.setReqSeq(niceLogDto.getReqSeq());
//        niceResDtoSess.setResSeq(niceLogDto.getResSeq());
//        niceResDtoSess.setAuthType(niceLogDto.getAuthType());
//        niceResDtoSess.setName(niceLogDto.getName());
//        niceResDtoSess.setBirthDate(niceLogDto.getBirthDate());
//        niceResDtoSess.setDupInfo(niceLogDto.getDupInfo());
//        niceResDtoSess.setConnInfo(niceLogDto.getConnInfo());
//        niceResDtoSess.setParam_r3(niceLogDto.getParamR3());
//        niceResDtoSess.setGender(niceLogDto.getGender());
//        niceResDtoSess.setNationalInfo(niceLogDto.getNationalInfo());
//        niceResDtoSess.setsMobileNo(niceLogDto.getsMobileNo());
//        niceResDtoSess.setsMobileCo(niceLogDto.getsMobileCo());
//
//        niceLogDto.setParamR1("insertNiceLogAjax");
//
//        //시작 시간 저장 처리
//        niceLogDto.setSysRdate(niceLogDto.getStartTimeToDate());
//        long niceHistSeq = nicelog.insertMcpNiceHist(niceLogDto);
//
//        if (CUST_AUTH.equals(niceLogDto.getParamR3())) {
//            SessionUtils.saveNiceRes(niceResDtoSess);
//        } else if (CUST_AGENT_AUTH.equals(niceLogDto.getParamR3())) {
//            SessionUtils.saveNiceAgentRes(niceResDtoSess);
//        } else if (INSR_PROD.equals(niceLogDto.getParamR3())) {
//            SessionUtils.saveNiceInsrRes(niceResDtoSess);
//        } else if (OPEN_AUTH.equals(niceLogDto.getParamR3())) {
//            SessionUtils.saveNiceOpenRes(niceResDtoSess);
//        } else if(RWD_PROD.equals(niceLogDto.getParamR3())){ // 보상서비스 본인인증
//            SessionUtils.saveNiceRwdRes(niceResDtoSess);
//        }
//
//        rtnMap.put("HIST_SEQ", niceHistSeq);
//        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
//
//
//
//        /*
//        if (CUST_AUTH.equals(niceLogRtn.getParamR3())) {
//            SessionUtils.saveNiceRes(niceResDtoSess);
//        }*/
//        return rtnMap;
//    }
//
//    /**
//     * 본인인증 요청 알람(push) 인증완료 응답 건 로그 초기 insert (2023/02/06)
//     * @param niceTryLogDto
//     * @return
//     */
//    @RequestMapping(value = "/nice/insertNiceTryLogAjax.do")
//    @ResponseBody
//    public Map<String, Object> insertNiceTryLog(NiceTryLogDto niceTryLogDto) {
//
//        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
//
//        // 1. 데이터 가공
//        niceTryLogDto.setSysRdate(niceTryLogDto.getStartTimeToDate());  // 인증 시작 시간
//
//        String reqSeq= niceTryLogDto.getReqSeq();
//        if(!StringUtil.isEmpty(reqSeq)){
//            if(reqSeq.length() > 50) niceTryLogDto.setReqSeq(reqSeq.substring(0,50));
//        }
//
//        // 2. 로그 insert
//        nicelog.insertMcpNiceTryHist(niceTryLogDto);
//
//        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
//        return rtnMap;
//    }
//
//    @RequestMapping(value = "/nice/getTimeInMillisAjax.do")
//    @ResponseBody
//    public long getTimeInMillis() {
//        Calendar cal = Calendar.getInstance();
//        return cal.getTimeInMillis();
//    }
//
//    @SuppressWarnings("null")
//    @RequestMapping(value = "/nice/getToss.do")
//    @ResponseBody
//    public Map<String, Object> getToss(@RequestParam(value="prcType", required=false, defaultValue="PARTIAL") String prcType){
//
//        JsonParser jsonParser = new JsonParser();
//        HashMap<String, Object> resultMap = new HashMap<String, Object>();
//
//        // 토스 인증 이용가능여부 확인 (DB통제 페이지만)
//        if("COMMON".equalsIgnoreCase(prcType)){
//
//            String grpCd= "pSimpleAuth";
//            if(!"P".equals(NmcpServiceUtils.getPlatFormCd())) grpCd= "mSimpleAuth";
//
//            NmcpCdDtlDto cdDtlDto= NmcpServiceUtils.getCodeNmDto(grpCd, "T");
//            if(cdDtlDto == null || !DateTimeUtil.checkValidDate(cdDtlDto.getPstngStartDate(), cdDtlDto.getPstngEndDate())){
//                resultMap.put("reason", AUTH_TYPE_EXCEPTION);
//                resultMap.put("resultType", "FAIL");
//                return resultMap;
//            }
//        }
//
//        // SMS 인증 완료 여부 확인 - 신규셀프개통인 경우만
//        if(!niceCertifySvc.preChkSimpleAuth()){
//            resultMap.put("reason", F_BIND_EXCEPTION);
//            resultMap.put("resultType", "FAIL");
//            return resultMap;
//        }
//
//        // 토스 팝업창 요청
//        try{
//
//            CommonHttpClient client = new CommonHttpClient(extUrl+"/tossCallApi.do");
//
//
//            logger.info("NiceCertify--client= " + client.toString());
//
//            String clientId= null;
//            String clientSecret= null;
//            if ( "LOCAL".equals(serverName) ) { // 테스트용 키
//                clientId= "test_a8e23336d673ca70922b485fe806eb2d";
//                clientSecret= "test_418087247d66da09fda1964dc4734e453c7cf66a7a9e3";
//            }else {
//                clientId= tossClientId;
//                clientSecret= tossClientSecret;
//            }
//
//            NameValuePair[] data = {
//                    new NameValuePair("clientId",clientId),
//                    new NameValuePair("clientSecret",clientSecret)
//           };
//
//            logger.info("NiceCertify--data= " + data.toString());
//
//            String responseBody = client.postUtf8(data);
//
//            logger.info("NiceCertify--responseBody= " + responseBody.toString());
//
//            JsonObject jsonObject = (JsonObject) jsonParser.parse(responseBody);
//            String resultType= jsonObject.get("resultType").toString().replaceAll("\"", "");
//
//             if("FAIL".equals(resultType)) {
//                 String reason = jsonObject.get("reason").toString().replaceAll("\"", "");
//                 resultMap.put("reason", reason);
//                resultMap.put("resultType", resultType);
//                logger.error("TOSS 팝업 창 불러오기 실패 시 reason=====================" + reason);
//             }else { // SUCCESS
//                 String txId = jsonObject.get("txId").toString().replaceAll("\"", "");
//                 String authUrl = jsonObject.get("authUrl").toString().replaceAll("\"", "");
//                 String accessToken= jsonObject.get("accessToken").toString().replaceAll("\"", "");
//
//                 resultMap.put("txId", txId);
//                 resultMap.put("authUrl", authUrl);
//                 resultMap.put("resultType", resultType);
//                 resultMap.put("strAccToken", accessToken);
//             }
//        }catch(SocketTimeoutException e) {
//            logger.error("TOSS 팝업 창 불러오기 연동 시 오류 발생");
//            resultMap.put("reason", "시스템 장애로 인하여 통신이 원활하지 않습니다.");
//            resultMap.put("resultType", "FAIL");
//        }catch (Exception e) {
//            logger.error("TOSS 팝업 창 불러오기 연동 시 오류 발생");
//            resultMap.put("reason", "시스템 장애로 인하여 통신이 원활하지 않습니다.");
//            resultMap.put("resultType", "FAIL");
//        }
//
//         return resultMap;
//    }
//
//
//    @RequestMapping(value = "/nice/getTossCertifyInfo.do")
//    @ResponseBody
//    public Map<String, Object> getTossCertifyInfo(@RequestParam(value="txId", defaultValue="") String txId ,
//                                                  @RequestParam(value="strAccToken", defaultValue="") String strAccToken) {
//
//        HashMap<String, Object> resultMap = new HashMap<String, Object>();
//
//        // 유효성 검사
//        if (StringUtils.isBlank(txId) || StringUtils.isBlank(strAccToken)) {
//            resultMap.put("reason", F_BIND_EXCEPTION);
//            resultMap.put("resultType", "FAIL");
//            return resultMap;
//        }
//
//        // 본인인증 요청 알람(push) 인증완료 응답 건 로그 기록
//        String reqSeq= (strAccToken.length() > 50) ? strAccToken.substring(0,50) : strAccToken;
//        NiceTryLogDto niceTryDto= new NiceTryLogDto();
//        niceTryDto.setAuthType("T");
//        niceTryDto.setSuccYn("N");
//        niceTryDto.setReqSeq(reqSeq);
//
//        JsonParser jsonParser = new JsonParser();
//
//        try {
//            CommonHttpClient client = new CommonHttpClient(extUrl+"/tossCertifyInfoApi.do");
//            NameValuePair[] data = {
//                     new NameValuePair("accessToken",strAccToken),
//                     new NameValuePair("txId",txId)
//            };
//
//            String responseBody = client.postUtf8(data);
//            JsonObject jsonObject = (JsonObject) jsonParser.parse(responseBody);
//
//            String resultType= jsonObject.get("resultType").toString().replaceAll("\"", "");
//
//             if("FAIL".equals(resultType)) {
//                String reason = jsonObject.get("reason").toString().replaceAll("\"", "");
//                resultMap.put("reason", reason);
//                resultMap.put("resultType", resultType);
//             }else { // SUCCESS
//
//                String name = jsonObject.get("name").toString().replaceAll("\"", "");
//                String birthDate = jsonObject.get("birthDate").toString().replaceAll("\"", "");
//                String cid = jsonObject.get("cid").toString().replaceAll("\"", "");
//                String pin = jsonObject.get("pin").toString().replaceAll("\"", "");
//                String gender = jsonObject.get("gender").toString().replaceAll("\"", "");
//                String nationalInfo = jsonObject.get("nationalInfo").toString().replaceAll("\"", "");
//
//                // 디코딩
//                String decName= EncryptUtil.ace256Dec(name);
//                String decBirthDate= EncryptUtil.ace256Dec(birthDate);
//                String decGender= EncryptUtil.ace256Dec(gender);
//                String decNationalInfo= EncryptUtil.ace256Dec(nationalInfo);
//                String decCid= EncryptUtil.ace256Dec(cid);
//                String decPin= EncryptUtil.ace256Dec(pin);
//
//                // ========== 본인인증 리턴 로직 개선 START ==========
//                // 1. 본인인증 이력 저장
//                NiceLogDto niceLogDto = new NiceLogDto();
//                niceLogDto.setReqSeq(reqSeq);
//                niceLogDto.setResSeq(txId);
//                niceLogDto.setAuthType("T");
//                niceLogDto.setName(decName);
//                niceLogDto.setBirthDate(decBirthDate);
//                niceLogDto.setGender(decGender);
//                niceLogDto.setNationalInfo(decNationalInfo);
//                niceLogDto.setDupInfo(decPin);
//                niceLogDto.setConnInfo(decCid);
//                niceLogDto.setParamR3(CUST_AUTH);
//                nicelog.insertMcpNiceHist(niceLogDto);
//
//                // 2. 화면 리턴값
//                resultMap.put("reqSeq", reqSeq);
//                resultMap.put("resSeq", txId);
//                resultMap.put("authType", "T");
//                resultMap.put("resultType", resultType);
//
//                // 3. 본인인증 요청 알람(push) 인증완료 응답 건 로그 기록 (성공)
//                niceTryDto.setResSeq(txId);
//                niceTryDto.setName(decName);
//                niceTryDto.setBirthDate(birthDate);
//                niceTryDto.setConnInfo(decCid);
//                niceTryDto.setSuccYn("Y");
//                // ========== 본인인증 리턴 로직 개선 END ==========
//             }
//
//        } catch(SocketTimeoutException e){
//            resultMap.put("reason", "시스템 장애로 인하여 통신이 원활하지 않습니다.");
//            resultMap.put("resultType", "FAIL");
//        }catch (JsonSyntaxException e) {
//            resultMap.put("reason", "시스템 장애로 인하여 통신이 원활하지 않습니다.");
//            resultMap.put("resultType", "FAIL");
//        } catch(Exception e) {
//            resultMap.put("reason", "시스템 장애로 인하여 통신이 원활하지 않습니다.");
//            resultMap.put("resultType", "FAIL");
//        }
//
//        nicelog.insertMcpNiceTryHist(niceTryDto);
//
//        return resultMap;
//    }
//
//
//    /**
//     * <pre>
//     * 설명     : PASS 인증서 창으로 이동
//     * </pre>
//     */
//    @RequestMapping(value = "/nice/getPass.do")
//    public String getPass(Model model, @RequestParam(value="prcType", required=false, defaultValue="PARTIAL") String prcType){
//
//        String rtnPage= "/portal/popup/passLogin";  // 패스 인증창
//        String grpCd= "pSimpleAuth";
//
//        if(!"P".equals(NmcpServiceUtils.getPlatFormCd())){
//            rtnPage= "/mobile/popup/passLogin";
//            grpCd= "mSimpleAuth";
//        }
//
//        // 패스 인증 이용가능여부 확인 (DB통제 페이지만)
//        if("COMMON".equalsIgnoreCase(prcType)){
//            NmcpCdDtlDto cdDtlDto= NmcpServiceUtils.getCodeNmDto(grpCd, "A");
//            if(cdDtlDto == null || !DateTimeUtil.checkValidDate(cdDtlDto.getPstngStartDate(), cdDtlDto.getPstngEndDate())){
//                throw new McpCommonException(AUTH_TYPE_EXCEPTION);
//            }
//        }
//
//        // SMS 인증 완료 여부 확인 - 신규셀프개통인 경우만
//        if(!niceCertifySvc.preChkSimpleAuth()){
//            throw new McpCommonException(F_BIND_EXCEPTION);
//        }
//
//        return rtnPage;
//    }
//
//    /**
//     * <pre>
//     * 설명     : PASS 간편인증 알림요청
//     * </pre>
//     */
//    @RequestMapping(value = "/nice/pushPassAlram.do")
//    @ResponseBody
//    public Map<String, Object> pushPassAlram(HttpServletRequest request){
//
//        HashMap<String, Object> resultMap = new HashMap<String, Object>();
//        resultMap.put("RESULT_CODE", "9999");
//        resultMap.put("RESULT_MSG", "시스템 장애로 인하여 통신이 원활하지 않습니다.");
//
//        String name=  request.getParameter("name");  // 이름
//        String ctn= request.getParameter("ctn");     // 핸드폰번호
//
//        // 유효값 검사
//        if(StringUtils.isEmpty(name) || StringUtils.isEmpty(ctn)) {
//             throw new McpCommonJsonException("0001", F_BIND_EXCEPTION);
//        }
//
//        // 필수 동의 검사
//        if(!"Y".equals(request.getParameter("agreeYn")) || !"Y".equals(request.getParameter("agreeYn2"))){
//            throw new McpCommonJsonException("0002", "필수약관에 동의하여 주시기 바랍니다.");
//        }
//
//        // PASS 본인인증 토큰 획득, 알람 전송
//        String result= niceCertifySvc.pushPassAlram(request);
//
//        if(!StringUtils.isEmpty(result)){
//            // 알람 전송 RESPONSE PARSE
//            JsonParser jsonParser = new JsonParser();
//            JsonObject jsonObject = (JsonObject) jsonParser.parse(result);
//
//            String gwRsltCd= jsonObject.get("gwRsltCd").getAsString();
//            String gwRsltMsg= jsonObject.get("gwRsltMsg").getAsString();
//
//            // 1200 오류 없음 (정상)
//            if(!"1200".equals(gwRsltCd)){
//                resultMap.put("RESULT_CODE", "0001");
//                resultMap.put("RESULT_MSG", gwRsltMsg);
//                return resultMap;
//            }
//
//            String rspCd= jsonObject.get("rspCd").getAsString(); // gwRsltCd이 1200인 경우 유효
//            String rspCdMsg= jsonObject.get("rspCdMsg").getAsString();
//
//            // P000 정상응답
//            if(!"P000".equals(rspCd)){
//                resultMap.put("RESULT_CODE", "0002");
//                resultMap.put("RESULT_MSG", rspCdMsg);
//                return resultMap;
//            }
//
//            String resultCd= jsonObject.get("resultCd").getAsString(); // rspCd가 P000인 경우 유효
//            String resultCdMsg= jsonObject.get("resultCdMsg").getAsString();
//
//            if(!"0000".equals(resultCd)){
//                resultMap.put("RESULT_CODE", "0003");
//                resultMap.put("RESULT_MSG", resultCdMsg);
//            }else{
//                // PASS 인증요청 성공
//                // PASS 본인인증 요청 정보 세션 임시 저장
//                NiceResDto niceResDto= new NiceResDto();
//                niceResDto.setName(name);
//                niceResDto.setsMobileNo(ctn);
//                SessionUtils.saveNiceReqTmp(niceResDto);
//
//                String requestNo= jsonObject.get("requestNo").getAsString();
//                String resUniqId= jsonObject.get("resUniqId").getAsString(); // 응답고유번호
//
//                resultMap.put("RESULT_CODE", AJAX_SUCCESS);
//                resultMap.put("RESULT_MSG", resultCdMsg);
//                resultMap.put("requestNo", requestNo);
//                resultMap.put("resUniqId", resUniqId);
//            }
//        }
//
//        return resultMap;
//    }
//
//    /**
//     * <pre>
//     * 설명     : PASS 간편인증 결과확인
//     * </pre>
//     */
//    @RequestMapping(value = "/nice/passCertifyInfo.do")
//    @ResponseBody
//    public Map<String, Object> passCertifyInfo(HttpServletRequest request){
//
//        // ==== START PASS 인증완료 처리 전, 인증 요청데이터와 비교 ====
//        NiceResDto niceResDto= SessionUtils.getNiceReqTmpCookieBean();
//        if(niceResDto == null){ // 인증요청 정보 없음
//            throw new McpCommonJsonException("9998", NICE_CERT_REQ_NULL_EXCEPTION);
//        }
//
//        String name= request.getParameter("name");
//        name= (name == null) ? "" : name.toUpperCase().replaceAll(" ", "");  // 이름
//        String ctn= request.getParameter("ctn");                       // 핸드폰번호
//
//        String authReqName= niceResDto.getName().replaceAll(" ", ""); // niceResDto의 getName은 upper처리 되어짐
//
//        if(!authReqName.equals(name) || !niceResDto.getsMobileNo().equals(ctn)){
//            throw new McpCommonJsonException("9998", NICE_CERT_EXCEPTION); // 데이터 변조
//        }
//        // ==== END PASS 인증완료 처리 전, 인증 요청데이터와 비교 ====
//
//        // 유효값 검사
//        String requestNo= request.getParameter("requestNo");  // 요청고유번호
//        String resUniqId= request.getParameter("resUniqId");  // 응답고유번호
//
//        if(StringUtils.isEmpty(requestNo) || StringUtils.isEmpty(resUniqId)){
//           throw new McpCommonJsonException("9999", F_BIND_EXCEPTION);
//        }
//
//        HashMap<String, Object> resultMap = new HashMap<String, Object>();
//        resultMap.put("RESULT_CODE", "9999");
//        resultMap.put("RESULT_MSG", "시스템 장애로 인하여 통신이 원활하지 않습니다.");
//
//        // PASS 본인인증 암호화토큰 획득, 결과 확인
//        String result= niceCertifySvc.passCertifyInfo(request);
//
//        if(!StringUtils.isEmpty(result)){
//
//            // 본인인증 요청 알람(push) 인증완료 응답 건 로그 기록
//            NiceTryLogDto niceTryDto= new NiceTryLogDto();
//            niceTryDto.setAuthType("A");
//            niceTryDto.setSuccYn("N");
//            niceTryDto.setName(request.getParameter("name"));
//            niceTryDto.setReqSeq(requestNo);
//
//            // 인증 결과 RESPONSE PARSE
//            JsonParser jsonParser = new JsonParser();
//            JsonObject jsonObject = (JsonObject) jsonParser.parse(result);
//
//            String gwRsltCd= jsonObject.get("gwRsltCd").getAsString();
//            String gwRsltMsg= jsonObject.get("gwRsltMsg").getAsString();
//
//            // 1200 오류 없음 (정상)
//            if(!"1200".equals(gwRsltCd)){
//                nicelog.insertMcpNiceTryHist(niceTryDto);
//
//                resultMap.put("RESULT_CODE", "0001");
//                resultMap.put("RESULT_MSG", gwRsltMsg);
//                return resultMap;
//            }
//
//            String rspCd= jsonObject.get("rspCd").getAsString(); // gwRsltCd이 1200인 경우 유효
//            String rspCdMsg= jsonObject.get("rspCdMsg").getAsString();
//
//            // P000 정상응답
//            if(!"P000".equals(rspCd)){
//                nicelog.insertMcpNiceTryHist(niceTryDto);
//
//                resultMap.put("RESULT_CODE", "0002");
//                resultMap.put("RESULT_MSG", rspCdMsg);
//                return resultMap;
//            }
//
//            String resultCd= jsonObject.get("resultCd").getAsString(); // rspCd가 P000인 경우 유효
//            String resultCdMsg= jsonObject.get("resultCdMsg").getAsString();
//
//            if(!"0000".equals(resultCd)){
//                if(!"0006".equals(resultCd)) resultMap.put("RESULT_CODE", "0003");
//                else resultMap.put("RESULT_CODE", resultCd);
//
//                resultMap.put("RESULT_MSG", resultCdMsg);
//            }else{
//                // PASS 인증 성공
//                String encAceCi= jsonObject.get("encAceCi").getAsString();
//                String encAcejuminPrefix= jsonObject.get("encAcejuminPrefix").getAsString();
//
//                String decCid;
//                String decAcejuminPrefix;
//                String birthDate= "";
//                String gender= "0"; // 0 여자, 1 남자
//                String nationalInfo= "0";  // 0 내국인, 1 외국인
//
//                try {
//                    // 암호화된 cid와 juminPrefix 복호화
//                     decCid= EncryptUtil.ace256Dec(encAceCi);
//                     decAcejuminPrefix= EncryptUtil.ace256Dec(encAcejuminPrefix); // 주민번호 앞자리 + 성별
//                     birthDate= decAcejuminPrefix.substring(0,6);
//                     gender= decAcejuminPrefix.substring(6,decAcejuminPrefix.length());
//
//                    // 내외국인 구분
//                    if("5".equals(gender) || "6".equals(gender) || "7".equals(gender) || "8".equals(gender)){
//                        nationalInfo= "1";
//                    }
//
//                    if("1".equals(gender) || "3".equals(gender) || "5".equals(gender) || "7".equals(gender)){
//                        gender= "1";
//                    }else{
//                        gender= "0";
//                    }
//
//                    // ========== 본인인증 리턴 로직 개선 START ==========
//                    // 1. 본인인증 이력 저장
//                    NiceLogDto niceLogDto = new NiceLogDto();
//                    niceLogDto.setReqSeq(requestNo);
//                    niceLogDto.setResSeq(resUniqId);
//                    niceLogDto.setAuthType("A");
//                    niceLogDto.setName(request.getParameter("name"));
//                    niceLogDto.setBirthDate(birthDate);
//                    niceLogDto.setGender(gender);
//                    niceLogDto.setNationalInfo(nationalInfo);
//                    niceLogDto.setDupInfo("");
//                    niceLogDto.setConnInfo(decCid);
//                    niceLogDto.setsMobileNo(ctn);
//                    niceLogDto.setParamR3(CUST_AUTH);
//                    nicelog.insertMcpNiceHist(niceLogDto);
//
//                    // 2. 화면 리턴값
//                    resultMap.put("reqSeq", requestNo);
//                    resultMap.put("resSeq", resUniqId);
//                    resultMap.put("authType", "A");
//                    resultMap.put("RESULT_CODE", AJAX_SUCCESS);
//                    resultMap.put("RESULT_MSG", resultCdMsg);
//
//                    // 3. 본인인증 요청 알람(push) 인증완료 응답 건 로그 기록 (성공)
//                    niceTryDto.setBirthDate(EncryptUtil.ace256Enc(birthDate));
//                    niceTryDto.setResSeq(resUniqId);
//                    niceTryDto.setConnInfo(decCid);
//                    niceTryDto.setSuccYn("Y");
//                    // ========== 본인인증 리턴 로직 개선 END ==========
//
//                } catch (CryptoException e) {
//                    resultMap.put("RESULT_CODE", "0004");
//                    resultMap.put("RESULT_MSG", ACE_256_ENC_EXCEPTION);
//                }
//            }
//
//            if(!"0006".equals(resultMap.get("RESULT_CODE"))){
//                nicelog.insertMcpNiceTryHist(niceTryDto);
//            }
//        }
//
//        return resultMap;
//    }
//
//    @RequestMapping(value = "/nice/popNiceFail.do")
//    public String popNiceFail(HttpServletRequest request,
//            Model model
//            , NiceResDto niceResDto) {
//        CPClient niceCheck = new  CPClient();
//
//        String sRequestNumber = "";				// 요청 번호
//        String sErrorCode = "";						// 인증 결과코드
//        String sAuthType = "";						// 인증 수단
//        //String sMessage = "";
//        String sPlainData = "";
//
//        int iReturn = niceCheck.fnDecode(NICE_SITE_CODE, niceSitePassword, niceResDto.getEncodeData());
//        if( iReturn == 0 ){
//
//            sPlainData = niceCheck.getPlainData();
//
//            // 데이타를 추출합니다.
//            java.util.HashMap mapresult = niceCheck.fnParse(sPlainData);
//
//            sRequestNumber 	= (String)mapresult.get("REQ_SEQ");
//            sErrorCode 			= (String)mapresult.get("ERR_CODE");
//            sAuthType 			= (String)mapresult.get("AUTH_TYPE");
//
//            model.addAttribute("ErrorCode", sErrorCode);
//
//            String sReserved1  = StringUtil.NVL(request.getParameter("param_r1"),"");
//            NiceResDto sessNiceParam =  SessionUtils.getNiceParam() ;
//            if (sessNiceParam != null) {
//                sReserved1  = sessNiceParam.getParam_r1();
//            }
//
//            nicelog.insert(request , mapresult , sReserved1);
//            if (NICE_ERROR_CODE_MAP.containsKey(sErrorCode)) {
//                //나이스 로그 기록 20160403 나이스 에러코드가있을때만 로그에 넣는다 , 20160630 호출 페이지 추가
//                model.addAttribute("ErrorMsg",NICE_ERROR_CODE_MAP.get(sErrorCode));
//            } else {
//                model.addAttribute("ErrorMsg", "정의되지 않은 오류.");
//            }
//        } else {
//            throw new McpCommonException(NICE_DECRYPT_EXCEPTION);
//        }
//        return "/portal/nice/popNiceFail";
//    }
//
//    /**
//     * <pre>
//     * 설명     : 계좌 번호 유효성 체크 서비스
//     * @param niceResDto
//     * @return
//     * @return: String
//     * </pre>
//     */
//    @RequestMapping(value = "/nice/accountCheckAjax.do")
//    @ResponseBody
//    public Map<String, Object> checkNiceAccount(HttpServletRequest request ,  NiceResDto niceResDto) {
//
//        String result = null;
//        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
//        if ( "LOCAL".equals(serverName)) {
//
//            // ============ STEP START ============
//            Map<String, String> resultMap = certService.isAuthStepApplyUrl(request);
//            if("Y".equals(resultMap.get("isAuthStep"))) {
//
//                String ncType= "";
//                if(request.getParameter("ncType") != null) ncType= request.getParameter("ncType");
//
//                // account인증 이력 존재여부 확인
//                if(0 < certService.getModuTypeStepCnt("account", ncType)){
//                    // 계좌인증 관련 스텝 초기화
//                    CertDto certDto = new CertDto();
//                    certDto.setModuType("account");
//                    certDto.setCompType("G");
//                    certDto.setNcType(ncType);
//                    certService.getCertInfo(certDto);
//                }
//
//                // 인증종류, 대리인구분, 계좌번호, 은행코드, 이름
//                String[] certKey= {"urlType", "moduType", "ncType", "reqAccountNumber", "reqBank", "name"};
//                String[] certValue= {"chkAccount", "account", ncType, EncryptUtil.ace256Enc(niceResDto.getAccountNo()), niceResDto.getBankCode(), niceResDto.getName()};
//
//                // service가 3인 경우 이름 필수x
//                if("3".equals(niceResDto.getService())){
//                    certKey= Arrays.copyOfRange(certKey, 0, 5);
//                    certService.vdlCertInfo("C", certKey, certValue);
//                }else{
//                    certService.vdlCertInfo("C", certKey, certValue);
//
//                    // 인증종류, 대리인구분, 이름
//                    certKey= new String[]{"urlType", "moduType", "ncType", "name"};
//                    certValue= new String[]{"compAccount", "account", ncType, niceResDto.getName()};
//
//                    Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
//                    if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
//                        throw new McpCommonJsonException("STEP01", vldReslt.get("RESULT_DESC"));
//                    }
//                }
//            }
//            // ============ STEP END ============
//
//            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
//            return rtnMap;
//
//            //result = niceCertifySvc.checkNiceAccount(niceResDto);
//        } else {
//            result = niceCertifySvc.checkNiceAccount(niceResDto);
//        }
//
//
//
//        String[] results = result.split("\\|");
//
//
//        NiceLogDto nicelogDto= new NiceLogDto();
//        nicelogDto.setnReferer(request.getHeader("referer"));
//        nicelogDto.setnAuthType("A");
//
//        if (results!=null && results.length > 0 && "0000".equals(results[1])) {
//            //인증성공
//            //나이스 로그 기록 20160403
//            nicelogDto.setnResult("O");
//            nicelog.insert(request , niceResDto , nicelogDto);
//            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
//
//            // ============ STEP START ============
//            Map<String, String> resultMap = certService.isAuthStepApplyUrl(request);
//            if("Y".equals(resultMap.get("isAuthStep"))) {
//
//                String ncType= "";
//                if(request.getParameter("ncType") != null) ncType= request.getParameter("ncType");
//
//                // account인증 이력 존재여부 확인
//                if(0 < certService.getModuTypeStepCnt("account", ncType)){
//                    // 계좌인증 관련 스텝 초기화
//                    CertDto certDto = new CertDto();
//                    certDto.setModuType("account");
//                    certDto.setCompType("G");
//                    certDto.setNcType(ncType);
//                    certService.getCertInfo(certDto);
//                }
//
//                // 인증종류, 대리인구분, 계좌번호, 은행코드, 이름
//                String[] certKey= {"urlType", "moduType", "ncType", "reqAccountNumber", "reqBank", "name"};
//                String[] certValue= {"chkAccount", "account", ncType, EncryptUtil.ace256Enc(niceResDto.getAccountNo()), niceResDto.getBankCode(), niceResDto.getName()};
//
//                // service가 3인 경우 이름 필수x
//                if("3".equals(niceResDto.getService())){
//                    certKey= Arrays.copyOfRange(certKey, 0, 5);
//                    certService.vdlCertInfo("C", certKey, certValue);
//                }else{
//                    certService.vdlCertInfo("C", certKey, certValue);
//
//                    // 인증종류, 대리인구분, 이름
//                    certKey= new String[]{"urlType", "moduType", "ncType", "name"};
//                    certValue= new String[]{"compAccount", "account", ncType, niceResDto.getName()};
//
//                    Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
//                    if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
//                        throw new McpCommonJsonException("STEP01", vldReslt.get("RESULT_DESC"));
//                    }
//                }
//            }
//            // ============ STEP END ============
//
//        } else {
//            //인증실패
//            //나이스 로그 기록 20160403 나이스 에로코드가있을때만 로그에 넣는다
//            nicelogDto.setnResult("X");
//            nicelog.insert(request , niceResDto , nicelogDto);
//            rtnMap.put("RESULT_CODE", "0001");
//        }
//        return rtnMap;
//    }
//
//
//    /**
//     * <pre>
//     * 설명     : IPIN 인증창 팝업
//     * @param model
//     * @param request
//     * @return
//     * @return: String
//     * </pre>
//     */
//    @RequestMapping(value = "/nice/popNiceIpinAuth.do")
//    public String popNiceIpinAuth(
//            Model model
//            , @RequestParam(defaultValue="") String sCheck
//            , @RequestParam(defaultValue="") String returnUrl
//            , HttpServletRequest request) {
//
//        /*
//         * Chrome 80 버전 쿠키정책 변경 및 조치
//         */
//        String strReferer = request.getHeader("referer");
//        NiceResDto niceParam = new  NiceResDto();
//        niceParam.setParam_r1(strReferer);
//        niceParam.setParam_r3(sCheck);
//        SessionUtils.saveNiceParam(niceParam);
//
//        IPINClient pClient = new IPINClient();
//        String sCPRequest = "";
//        sCPRequest = pClient.getRequestNO(NICE_IPIN_SITE_CODE);
//
//        String returnUrl2= "";
//        if ("".equals(returnUrl)) {
//            returnUrl2 = NICE_SUCC_IPIN_RETURN_URL ;
//        } else {
//            returnUrl2 = returnUrl ;
//        }
//
//        String tempReturnUrl = "https://"+ request.getServerName() + returnUrl2;
//
//        int iRtn = pClient.fnRequest(NICE_IPIN_SITE_CODE, niceIpinSitePassword, sCPRequest, tempReturnUrl);
//
//        String sMessage = "";
//        String sEncData = "";
//
//
//        if(iRtn == 0){
//            sEncData = pClient.getCipherData();		//암호화 된 데이타
//            model.addAttribute("sCheck",sCheck);
//            model.addAttribute("EncodeData",sEncData);
//            model.addAttribute("referer",strReferer);
//        } else if(iRtn == -1 || iRtn == -2) {
//            sMessage =	"배포해 드린 서비스 모듈 중, 귀사 서버환경에 맞는 모듈을 이용해 주시기 바랍니다.<BR>" +
//                        "귀사 서버환경에 맞는 모듈이 없다면 ..<BR><B>iRtn 값, 서버 환경정보를 정확히 확인하여 메일로 요청해 주시기 바랍니다.</B>";
//            throw new McpCommonException(sMessage);
//        } else if(iRtn == -9) {
//            sMessage = "입력값 오류 : fnRequest 함수 처리시, 필요한 4개의 파라미터값의 정보를 정확하게 입력해 주시기 바랍니다.";
//            throw new McpCommonException(sMessage);
//        } else {
//            sMessage = "iRtn 값 확인 후, NICE평가정보 개발 담당자에게 문의해 주세요.";
//            throw new McpCommonException(sMessage);
//        }
//
//        return "/portal/nice/popNiceIpinAuth";
//    }
//
//
//    /**
//     * <pre>
//     * 설명     : IPIN 인증후 CALL
//     * @param model
//     * @param niceResDto
//     * @return
//     * @return: String
//     * </pre>
//     */
//    @RequestMapping(value = "/nice/popNiceIpinSucc.do")
//    public String popNiceIpinSucc(HttpServletRequest request,
//            Model model
//            , NiceResDto niceResDto) {
//
//        IPINClient pClient = new IPINClient();
//        String sRtnMsg = "";
//
//        int iRtn = pClient.fnResponse(NICE_IPIN_SITE_CODE, niceIpinSitePassword, niceResDto.getEnc_data());
//
//        NiceLogDto nicelogDto= new NiceLogDto();
//        nicelogDto.setnAuthType("I");
//        //20160630 호출페이지 추가
//        String sReserved1  = "";
//        NiceResDto sessNiceParam =  SessionUtils.getNiceParam() ;
//        if (sessNiceParam != null) {
//            sReserved1  = sessNiceParam.getParam_r1();
//        }
//        nicelogDto.setnReferer(sReserved1);
//
//        if( iRtn == 1 ){
//            model.addAttribute("ErrorCode", "0000");
//            model.addAttribute("ErrorMsg", "성공");
//
//            String sVNumber				= pClient.getVNumber();			// 가상주민번호 (13자리이며, 숫자 또는 문자 포함)
//            String sNameI					= pClient.getName();				// 이름
//            String sDupInfoI				= pClient.getDupInfo();			// 중복가입 확인값 (DI - 64 byte 고유값)
//            String sAgeCode				= pClient.getAgeCode();			// 연령대 코드 (개발 가이드 참조)
//            String sGenderCode			= pClient.getGenderCode();		// 성별 코드 (개발 가이드 참조)
//            String sBirthDateI			= pClient.getBirthDate();			// 생년월일 (YYYYMMDD)
//            String sNationalInfo			= pClient.getNationalInfo();		// 내/외국인 정보 (개발 가이드 참조)
//            String sCPRequestNum		= pClient.getCPRequestNO();	// CP 요청번호
//            String sCipherDateTime		= pClient.getCipherDateTime();	//응답시간?
//
//
//
//            /*세션에 고객 정보를 넣는다.*/
//            NiceResDto niceResDtoSess = new  NiceResDto();
//            niceResDtoSess.setsVNumber(sVNumber);
//            niceResDtoSess.setResSeq(sCipherDateTime);
//            niceResDtoSess.setReqSeq(sCPRequestNum);
//            niceResDtoSess.setName(sNameI);
//            niceResDtoSess.setBirthDate(sBirthDateI);
//            niceResDtoSess.setGender(sGenderCode);
//            niceResDtoSess.setNationalInfo(sNationalInfo);
//            niceResDtoSess.setDupInfo(sDupInfoI);
//            SessionUtils.saveNiceRes(niceResDtoSess);  //기존 기능 때문에.. 그대로...
//
//            // ========== 본인인증 리턴 로직 개선 START ==========
//            // 1. 본인인증 이력 저장
//            NiceLogDto niceLogDto = new NiceLogDto();
//            niceLogDto.setReqSeq(sCPRequestNum);
//            niceLogDto.setResSeq(sCipherDateTime);
//            niceLogDto.setAuthType("I");
//            niceLogDto.setName(sNameI);
//            niceLogDto.setBirthDate(sBirthDateI);
//            niceLogDto.setGender(sGenderCode);
//            niceLogDto.setNationalInfo(sNationalInfo);
//            niceLogDto.setDupInfo(sDupInfoI);
//            niceLogDto.setConnInfo("");
//
//            nicelog.insertMcpNiceHist(niceLogDto);
//
//            // 2. 화면 리턴값
//            NiceResDto rtnNiceResDto = new NiceResDto();
//            rtnNiceResDto.setReqSeq(sCPRequestNum);
//            rtnNiceResDto.setResSeq(sCipherDateTime);
//            // ========== 본인인증 리턴 로직 개선 END ==========
//
//           // model.addAttribute("NiceRes", niceResDtoSess) ;
//            model.addAttribute("NiceRes", rtnNiceResDto) ;
//
//            //나이스 로그 기록 20160403
//            nicelogDto.setnCertify("P");
//            nicelog.insert(request , niceResDtoSess , nicelogDto);
//
//        } else if (iRtn == -1 || iRtn == -4){
//            //인증실패
//            //나이스 로그 기록 20160403 나이스 에로코드가있을때만 로그에 넣는다
//            nicelogDto.setnResult("X");
//            nicelogDto.setnErrorCode(Integer.toBinaryString(iRtn));
//            nicelog.insert(request , niceResDto , nicelogDto);
//
//
//            sRtnMsg =	"iRtn 값, 서버 환경정보를 정확히 확인하여 주시기 바랍니다.";
//            model.addAttribute("ErrorCode", "0001");
//            model.addAttribute("ErrorMsg", sRtnMsg);
//
//        } else if (iRtn == -6){
//            nicelogDto.setnResult("X");
//            nicelogDto.setnErrorCode(Integer.toBinaryString(iRtn));
//            nicelog.insert(request , niceResDto , nicelogDto);
//
//            sRtnMsg =	"당사는 한글 charset 정보를 euc-kr 로 처리하고 있으니, euc-kr 에 대해서 허용해 주시기 바랍니다.<BR>" +
//                        "한글 charset 정보가 명확하다면 ..<BR><B>iRtn 값, 서버 환경정보를 정확히 확인하여 메일로 요청해 주시기 바랍니다.</B>";
//
//            model.addAttribute("ErrorCode", "0002");
//            model.addAttribute("ErrorMsg", sRtnMsg);
//
//        } else if (iRtn == -9){
//            nicelogDto.setnResult("X");
//            nicelogDto.setnErrorCode(Integer.toBinaryString(iRtn));
//            nicelog.insert(request , niceResDto , nicelogDto);
//
//            sRtnMsg = "입력값 오류 : fnResponse 함수 처리시, 필요한 파라미터값의 정보를 정확하게 입력해 주시기 바랍니다.";
//
//            model.addAttribute("ErrorCode", "0003");
//            model.addAttribute("ErrorMsg", sRtnMsg);
//
//        } else if (iRtn == -12){
//            nicelogDto.setnResult("X");
//            nicelogDto.setnErrorCode(Integer.toBinaryString(iRtn));
//            nicelog.insert(request , niceResDto , nicelogDto);
//
//            sRtnMsg = "CP 비밀번호 불일치 : IPIN 서비스 사이트 패스워드를 확인해 주시기 바랍니다.";
//            model.addAttribute("ErrorCode", "0004");
//            model.addAttribute("ErrorMsg", sRtnMsg);
//        } else if (iRtn == -13){
//            nicelogDto.setnResult("X");
//            nicelogDto.setnErrorCode(Integer.toBinaryString(iRtn));
//            nicelog.insert(request , niceResDto , nicelogDto);
//
//            sRtnMsg = "CP 요청번호 불일치 : 세션에 넣은 sCPRequest 데이타를 확인해 주시기 바랍니다.";
//            model.addAttribute("ErrorCode", "0005");
//            model.addAttribute("ErrorMsg", sRtnMsg);
//        } else{
//            nicelogDto.setnResult("X");
//            nicelogDto.setnErrorCode(Integer.toBinaryString(iRtn));
//            nicelog.insert(request , niceResDto , nicelogDto);
//
//            sRtnMsg = "iRtn["+iRtn+"] 값 확인 후, NICE평가정보 전산 담당자에게 문의해 주세요.";
//            model.addAttribute("ErrorCode", "0006");
//            model.addAttribute("ErrorMsg", sRtnMsg);
//        }
//
//        return "/portal/nice/popNiceIpinSucc";
//    }
//
//
//
//    /**
//     * <pre>
//     * 설명     : IPIN 인증후 CALL
//     * @param model
//     * @param niceResDto
//     * @return
//     * @return: String
//     * </pre>
//     */
//    @RequestMapping(value = "/nice/popNiceIpinSuccM.do")
//    public String popNiceIpinSuccM(HttpServletRequest request,
//            Model model
//            , NiceResDto niceResDto) {
//
//        IPINClient pClient = new IPINClient();
//        String sRtnMsg = "";
//
//        int iRtn = pClient.fnResponse(NICE_IPIN_SITE_CODE, niceIpinSitePassword, niceResDto.getEnc_data());
//
//        NiceLogDto nicelogDto= new NiceLogDto();
//        nicelogDto.setnCertify("M");
//        //20160630 호출페이지 추가
//        String sReserved1  = "";
//        NiceResDto sessNiceParam =  SessionUtils.getNiceParam() ;
//        if (sessNiceParam != null) {
//            sReserved1  = sessNiceParam.getParam_r1();
//        }
//        nicelogDto.setnReferer(sReserved1);
//
//        if( iRtn == 1 ){
//            model.addAttribute("ErrorCode", "0000");
//            model.addAttribute("ErrorMsg", "성공");
//
//            String sVNumber				= pClient.getVNumber();			// 가상주민번호 (13자리이며, 숫자 또는 문자 포함)
//            String sNameI					= pClient.getName();				// 이름
//            String sDupInfoI				= pClient.getDupInfo();			// 중복가입 확인값 (DI - 64 byte 고유값)
//            String sAgeCode				= pClient.getAgeCode();			// 연령대 코드 (개발 가이드 참조)
//            String sGenderCode			= pClient.getGenderCode();		// 성별 코드 (개발 가이드 참조)
//            String sBirthDateI			= pClient.getBirthDate();			// 생년월일 (YYYYMMDD)
//            String sNationalInfo			= pClient.getNationalInfo();		// 내/외국인 정보 (개발 가이드 참조)
//            String sCPRequestNum		= pClient.getCPRequestNO();	// CP 요청번호
//            String sReserved3  = niceResDto.getParam_r3();
//
//
//            /*세션에 고객 정보를 넣는다.*/
//            NiceResDto niceResDtoSess = new  NiceResDto();
//            niceResDtoSess.setsVNumber(sVNumber);
//            niceResDtoSess.setResSeq(sCPRequestNum);
//            niceResDtoSess.setName(sNameI);
//            niceResDtoSess.setBirthDate(sBirthDateI);
//            niceResDtoSess.setGender(sGenderCode);
//            niceResDtoSess.setNationalInfo(sNationalInfo);
//            niceResDtoSess.setDupInfo(sDupInfoI);
//            SessionUtils.saveNiceRes(niceResDtoSess);
//
//            // ========== 본인인증 리턴 로직 개선 START ==========
//            // 1. 화면 리턴값 (기존에 req값은 화면에 리턴되지 않음.. 해당 내용 유지)
//            NiceResDto rtnNiceResDto = new NiceResDto();
//            rtnNiceResDto.setResSeq(sCPRequestNum);
//
//            //model.addAttribute("NiceRes", niceResDtoSess);
//            model.addAttribute("NiceRes", rtnNiceResDto);
//            // ========== 본인인증 리턴 로직 개선 END ==========
//
//            model.addAttribute("NextUrl", sReserved3);
//
//            //나이스 로그 기록 20160403
//            nicelog.insert(request , niceResDtoSess , nicelogDto);
//
//        } else if (iRtn == -1 || iRtn == -4){
//            //인증실패
//            //나이스 로그 기록 20160403 나이스 에로코드가있을때만 로그에 넣는다
//            nicelogDto.setnResult("X");
//            nicelogDto.setnErrorCode(Integer.toBinaryString(iRtn));
//            nicelog.insert(request , niceResDto , nicelogDto);
//
//
//            sRtnMsg =	"iRtn 값, 서버 환경정보를 정확히 확인하여 주시기 바랍니다.";
//            model.addAttribute("ErrorCode", "0001");
//            model.addAttribute("ErrorMsg", sRtnMsg);
//
//        } else if (iRtn == -6){
//            //나이스 로그 기록 20160403 나이스 에로코드가있을때만 로그에 넣는다
//            nicelogDto.setnResult("X");
//            nicelogDto.setnErrorCode(Integer.toBinaryString(iRtn));
//            nicelog.insert(request , niceResDto , nicelogDto);
//
//            sRtnMsg =	"당사는 한글 charset 정보를 euc-kr 로 처리하고 있으니, euc-kr 에 대해서 허용해 주시기 바랍니다.<BR>" +
//                        "한글 charset 정보가 명확하다면 ..<BR><B>iRtn 값, 서버 환경정보를 정확히 확인하여 메일로 요청해 주시기 바랍니다.</B>";
//
//            model.addAttribute("ErrorCode", "0002");
//            model.addAttribute("ErrorMsg", sRtnMsg);   //
//
//        } else if (iRtn == -9){
//            //나이스 로그 기록 20160403 나이스 에로코드가있을때만 로그에 넣는다.
//            nicelogDto.setnResult("X");
//            nicelogDto.setnErrorCode(Integer.toBinaryString(iRtn));
//            nicelog.insert(request , niceResDto , nicelogDto);
//
//            sRtnMsg = "입력값 오류 : fnResponse 함수 처리시, 필요한 파라미터값의 정보를 정확하게 입력해 주시기 바랍니다.";
//
//            model.addAttribute("ErrorCode", "0003");
//            model.addAttribute("ErrorMsg", sRtnMsg);
//
//        } else if (iRtn == -12){
//            //나이스 로그 기록 20160403 나이스 에로코드가있을때만 로그에 넣는다
//            nicelogDto.setnResult("X");
//            nicelogDto.setnErrorCode(Integer.toBinaryString(iRtn));
//            nicelog.insert(request , niceResDto , nicelogDto);
//
//            sRtnMsg = "CP 비밀번호 불일치 : IPIN 서비스 사이트 패스워드를 확인해 주시기 바랍니다.";
//            model.addAttribute("ErrorCode", "0004");
//            model.addAttribute("ErrorMsg", sRtnMsg);
//        } else if (iRtn == -13){
//            //나이스 로그 기록 20160403 나이스 에로코드가있을때만 로그에 넣는다
//            nicelogDto.setnResult("X");
//            nicelogDto.setnErrorCode(Integer.toBinaryString(iRtn));
//            nicelog.insert(request , niceResDto , nicelogDto);
//
//            sRtnMsg = "CP 요청번호 불일치 : 세션에 넣은 sCPRequest 데이타를 확인해 주시기 바랍니다.";
//            model.addAttribute("ErrorCode", "0005");
//            model.addAttribute("ErrorMsg", sRtnMsg);
//        } else{
//            //나이스 로그 기록 20160403 나이스 에로코드가있을때만 로그에 넣는다
//            nicelogDto.setnResult("X");
//            nicelogDto.setnErrorCode(Integer.toBinaryString(iRtn));
//            nicelog.insert(request , niceResDto , nicelogDto);
//
//            sRtnMsg = "iRtn["+iRtn+"] 값 확인 후, NICE평가정보 전산 담당자에게 문의해 주세요.";
//            model.addAttribute("ErrorCode", "0006");
//            model.addAttribute("ErrorMsg", sRtnMsg);
//        }
//
//        return "/portal/nice/popNiceSuccM";
//    }
//
//
//    /**
//     * <pre>
//     * 설명     : 계좌 번호 점유 인증 서비스: Otp Name 생성 : 1원입금
//     * @param niceResDto
//     * @return
//     * @return: String
//     * </pre>
//     */
//    @RequestMapping(value = "/nice/niceAccountOtpNameAjax.do")
//    @ResponseBody
//    public Map<String, Object> niceAccountOtpName(HttpServletRequest request ,  NiceResDto niceResDto) {
//
//        String result = null;
//        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
//        result = niceCertifySvc.checkNiceAccount(niceResDto);
//
//        String[] results = result.split("\\|");
//
//
//        NiceLogDto nicelogDto= new NiceLogDto();
//
//        if (results!=null && results.length > 1 && "0000".equals(results[1])) {
//            //계좌 OTP NAME 생성
//            result = niceCertifySvc.niceAccountOtpName(niceResDto);
//
//            JSONObject jObject = null;
//            try {
//                jObject = new JSONObject(result);
//                JSONObject jObjectBody = jObject.getJSONObject("dataBody");
//
//                if(jObjectBody.getString("result_cd").equals("0000")) {
//                    rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
//
//                    //화면 HIDDEN에 저장 필요
//                    //OTP CONFIRM에 사용됨
//                    rtnMap.put("request_no", jObjectBody.getString("request_no"));
//                    rtnMap.put("res_uniq_id", jObjectBody.getString("res_uniq_id"));
//
//                } else {
//                    rtnMap.put("RESULT_CODE", "0002");
//                }
//
//                //로그 이력 저정
//                nicelogDto.setAuthType("A");
//                nicelogDto.setName(niceResDto.getName());
//                nicelogDto.setBirthDate(niceResDto.getAccountNo());
//                nicelogDto.setParamR2(niceResDto.getBankCode());
//                nicelogDto.setParamR1(jObjectBody.getString("result_cd"));
//                nicelogDto.setParamR3("passReq");
//                nicelogDto.setReqSeq(jObjectBody.getString("request_no"));
//                nicelogDto.setResSeq(jObjectBody.getString("res_uniq_id"));
//                nicelogDto.setsMobileCo(jObjectBody.getString("mobile_co"));
//                nicelogDto.setsMobileNo(jObjectBody.getString("mobile_no"));
//                nicelog.insertMcpNiceHist(nicelogDto);
//
//            } catch(JSONException e) {
//                rtnMap.put("RESULT_CODE", "9998");
//            } catch (Exception e) {
//                rtnMap.put("RESULT_CODE", "9999");
//            }
//
//
//        } else {
//            //유효하지 않은 계좌 번호 입니다.
//            rtnMap.put("RESULT_CODE", "0001");
//        }
//        return rtnMap;
//
//    }
//
//    /**
//     * <pre>
//     * 설명     : 계좌 번호 점유 인증 서비스: Otp Name 확인 : Otp Confirm
//     * @param niceResDto
//     * @return
//     * @return: String
//     * </pre>
//     */
//    @RequestMapping(value = "/nice/niceAccountOtpConfirmAjax.do")
//    @ResponseBody
//    public Map<String, Object> niceAccountOtpConfirm(HttpServletRequest request ,  NiceResDto niceResDto) {
//
//        String result = null;
//        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
//        result = niceCertifySvc.niceAccountOtpConfirm(niceResDto);
//
//        //String[] results = result.split("\\|");
//
//
//        NiceLogDto nicelogDto= new NiceLogDto();
//        nicelogDto.setnReferer(request.getHeader("referer"));
//        nicelogDto.setnAuthType("A");
//
//        JSONObject jObject = null;
//        try {
//            jObject = new JSONObject(result);
//
//            JSONObject jObjectBody = jObject.getJSONObject("dataBody");
//
//            if(jObjectBody.getString("rsp_cd").equals("P000") && jObjectBody.getString("result_cd").equals("0000")) {
//                //정상응답코드
//                //OTP 인증 성공
//                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
//            }else {
//                rtnMap.put("RESULT_CODE", "0001");
//            }
//
//             //로그 이력 저정
//            /*rsp_cd  :   P000
//            request_no  :   20210617561599961
//            result_cd   :   0000
//            res_uniq_id :   AC2106171136191000249545
//            err_msg :
//             var varData = ajaxCommon.getSerializedData({
//               bankCode:$("#reqBankAut").val()
//               ,accountNo:$.trim($("#reqAccountAut").val())
//               ,name:$.trim($("#cstmrName").val())
//               ,requestNo:$.trim($("#requestNo").val())
//               ,resUniqId:$.trim($("#resUniqId").val())
//               ,otp:$.trim($("#textOpt").val())
//           });
//            */
//            nicelogDto.setName(niceResDto.getName());
//            nicelogDto.setAuthType("A");
//            nicelogDto.setParamR1(jObjectBody.getString("rsp_cd"));
//            nicelogDto.setParamR2(jObjectBody.getString("result_cd"));
//            nicelogDto.setParamR3("passCheck");
//            nicelogDto.setReqSeq(jObjectBody.getString("request_no"));
//            nicelogDto.setResSeq(jObjectBody.getString("res_uniq_id"));
//            nicelogDto.setsMobileCo(jObjectBody.getString("mobile_co"));
//            nicelogDto.setsMobileNo(jObjectBody.getString("mobile_no"));
//            nicelog.insertMcpNiceHist(nicelogDto);
//
//        } catch (JSONException e) {
//            rtnMap.put("RESULT_CODE", "0002");
//        }
//
//        return rtnMap;
//    }
//
//    /**
//     * <pre>
//     * 설명     : 임의로 설정한 인증정보를 업데이트 처리
//     * @param niceLogDto
//     * @return
//     * @return: String
//     * </pre>
//     */
//    @RequestMapping(value = "/nice/updateNiceLogAjax.do")
//    @ResponseBody
//    public Map<String, Object> updateNiceLog(NiceLogDto niceLogDto) {
//        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
//        String tempResSeq = niceLogDto.getResSeq();
//        niceLogDto.setResSeq("");
//        niceLogDto.setEndYn("N");
//
//        NiceLogDto niceLogRtn = nicelog.getMcpNiceHist(niceLogDto);
//
//        if (niceLogRtn != null) {
//            if (niceLogRtn.getReqSeq() == null || "".equals(niceLogRtn.getReqSeq())) {
//
//                niceLogRtn.setReqSeq(niceLogDto.getReqSeq());
//                niceLogRtn.setResSeq(tempResSeq);
//                niceLogRtn.setAuthType(niceLogDto.getAuthType());
//                niceLogRtn.setName("");
//                niceLogRtn.setBirthDate("");
//                niceLogRtn.setToName(niceLogDto.getToName());
//                niceLogRtn.setToBirthDate(niceLogDto.getToBirthDate());
//                niceLogRtn.setGender(niceLogDto.getGender());
//                niceLogRtn.setDupInfo(niceLogDto.getDupInfo());
//                niceLogRtn.setConnInfo(niceLogDto.getConnInfo());
//                niceLogRtn.setComplYn("Y"); //인증 완료 시간 UPDATE 처리
//                nicelog.updateMcpNiceHist(niceLogRtn);
//
//                //session 저장 처리
//                NiceResDto niceResDtoSess = new  NiceResDto();
//                niceResDtoSess.setReqSeq(niceLogDto.getReqSeq());
//                niceResDtoSess.setResSeq(tempResSeq);
//                niceResDtoSess.setAuthType(niceLogDto.getAuthType());
//                niceResDtoSess.setName(niceLogDto.getToName());
//                niceResDtoSess.setBirthDate(niceLogDto.getToBirthDate());
//                niceResDtoSess.setGender(niceLogDto.getGender());
//                niceResDtoSess.setDupInfo(niceLogDto.getDupInfo());
//                niceResDtoSess.setConnInfo(niceLogDto.getConnInfo());
//
//                if (CUST_AUTH.equals(niceLogRtn.getParamR3())) {
//                    SessionUtils.saveNiceRes(niceResDtoSess);
//                } else if (CUST_AGENT_AUTH.equals(niceLogRtn.getParamR3())) {
//                    SessionUtils.saveNiceAgentRes(niceResDtoSess);
//                }
//
//                rtnMap.put("HIST_SEQ", niceLogRtn.getNiceHistSeq());
//                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
//
//            }
//        } else {
//            rtnMap.put("HIST_SEQ", 0);
//            rtnMap.put("RESULT_CODE", "001");
//        }
//
//
//        return rtnMap;
//    }
//
//    /**
//     * <pre>
//     * 설명     : SMS인증 정보 확인
//     * @param niceLogDto
//     * @return
//     * @return: String
//     * </pre>
//     */
//    @RequestMapping(value = "/nice/checkSmsAuthInfoAjax.do")
//    @ResponseBody
//    public Map<String, Object> checkUserAuthInfoAjax(NiceLogDto niceLogDto) {
//        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
//
//        // 1. 파라미터 체크
//        if(StringUtils.isBlank(niceLogDto.getReqSeq()) || StringUtils.isBlank(niceLogDto.getResSeq())){
//            throw new McpCommonJsonException("AUTH01", BIDING_EXCEPTION);
//        }
//
//        // 2. sms 인증 정보 조회
//        NiceLogDto niceLogRtn= nicelog.getMcpNiceHistWithReqSeq(niceLogDto);
//        if (niceLogRtn == null) {
//            throw new McpCommonJsonException("AUTH02", NICE_CERT_EXCEPTION_INSR);
//        }
//
//        // 3. sms인증정보 step검사
//        // 이름, 생년월일, CI
//        String[] certKey= {"urlType", "name", "birthDate", "connInfo"};
//        String[] certValue= {"chkSmsAuth", niceLogRtn.getName(), niceLogRtn.getBirthDateDec(), niceLogRtn.getConnInfo()};
//
//        Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
//        if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
//            throw new McpCommonJsonException("STEP01", vldReslt.get("RESULT_DESC"));
//        }
//
//        // 인증종류, 이름, 생년월일, CI, reqSeq, resSeq (reqSeq와 resSeq는 참조용)
//        certKey= new String[]{"urlType", "moduType", "name", "birthDate", "connInfo"
//                            , "reqSeq", "resSeq"};
//        certValue= new String[]{"smsAuth", SMS_AUTH, niceLogRtn.getName(), niceLogRtn.getBirthDateDec(), niceLogRtn.getConnInfo()
//                              , niceLogRtn.getReqSeq(), niceLogRtn.getResSeq()};
//        certService.vdlCertInfo("C", certKey, certValue);
//
//        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
//        rtnMap.put("RESULT_MSG", "성공");
//        return rtnMap;
//    }
//
//    /**
//     * <pre>
//     * 설명  : 메뉴 별 SMS인증 정보 검사
//     * @param niceLogDto
//     * @return: Map<String, String>
//     * </pre>
//     */
//    @RequestMapping(value = "/nice/checkSmsAuthInfoWithCert.do")
//    @ResponseBody
//    public Map<String, String> checkSmsAuthInfoWithCert(NiceLogDto niceLogDto) {
//
//        Map<String, String> rtnMap = new HashMap<>();
//        rtnMap.put("RESULT_CODE", "F_AUTH");
//
//        boolean needAuthSession = false;       // 핸드폰 인증 세션 필요여부
//        boolean needAgentAuthSession = false;  // 법정대리인 인증 세션 필요여부
//        boolean needCommonSmsStep = true;      // SMS 인증 STEP 필요여부 (공통과 다른 STEP을 가져가야 하는 경우 false로 바꾸기)
//
//        // 1. 파라미터 체크
//        if(StringUtils.isBlank(niceLogDto.getReqSeq())
//          || StringUtils.isBlank(niceLogDto.getResSeq())
//          || StringUtils.isBlank(niceLogDto.getMenuType())){
//            throw new McpCommonJsonException("F_AUTH", BIDING_EXCEPTION);
//        }
//
//        // 2. SMS 인증 정보 조회
//        NiceLogDto niceLogRtn= nicelog.getMcpNiceHistWithReqSeq(niceLogDto);
//        if (niceLogRtn == null) {
//            throw new McpCommonJsonException("F_AUTH", NICE_CERT_EXCEPTION_INSR);
//        }
//
//        // 3. 메뉴 별 SMS 인증 결과 검사
//        if("usimBuy".equalsIgnoreCase(niceLogDto.getMenuType())){
//
//            needAuthSession = "0".equals(niceLogDto.getNcType());
//            needAgentAuthSession = "1".equals(niceLogDto.getNcType());
//            needCommonSmsStep = needAuthSession; // 대리인인 경우 공통 SMS STEP 미사용 (개별 STEP 사용)
//
//            rtnMap = niceCertifySvc.chkUsimBuySmsResInfo(niceLogDto, niceLogRtn);
//
//        }else if("replaceUsim".equalsIgnoreCase(niceLogDto.getMenuType())){
//            needAuthSession = true;
//            needCommonSmsStep = true;
//            rtnMap = niceCertifySvc.chkReplaceUsimSmsResInfo(niceLogDto, niceLogRtn);
//        }
//
//        if(!AJAX_SUCCESS.equals(rtnMap.get("RESULT_CODE"))){
//            return rtnMap;
//        }
//
//        // 4. 공통 SMS STEP 저장
//        // 인증종류, 이름, 생년월일, CI, reqSeq, resSeq (reqSeq와 resSeq는 참조용)
//        if(needCommonSmsStep){
//            String[] certKey= {"urlType", "moduType", "name", "birthDate", "connInfo", "reqSeq", "resSeq", "ncType"};
//            String[] certValue= {"smsAuth", SMS_AUTH, niceLogRtn.getName(), niceLogRtn.getBirthDateDec(), niceLogRtn.getConnInfo(), niceLogRtn.getReqSeq(), niceLogRtn.getResSeq(), niceLogDto.getNcType()};
//            certService.vdlCertInfo("C", certKey, certValue);
//        }
//
//        // 5. 인증 세션 저장
//        NiceResDto niceResDtoSess = new NiceResDto();
//        niceResDtoSess.setReqSeq(niceLogRtn.getReqSeq());
//        niceResDtoSess.setResSeq(niceLogRtn.getResSeq());
//        niceResDtoSess.setAuthType(niceLogRtn.getAuthType());
//        niceResDtoSess.setName(niceLogRtn.getName());
//        niceResDtoSess.setBirthDate(niceLogRtn.getBirthDateDec());
//        niceResDtoSess.setDupInfo(niceLogRtn.getDupInfo());
//        niceResDtoSess.setConnInfo(niceLogRtn.getConnInfo());
//        niceResDtoSess.setParam_r3(SMS_AUTH);
//        niceResDtoSess.setGender(niceLogRtn.getGender());
//        niceResDtoSess.setNationalInfo(niceLogRtn.getNationalInfo());
//        niceResDtoSess.setsMobileNo(niceLogRtn.getsMobileNo());
//        niceResDtoSess.setsMobileCo(niceLogRtn.getsMobileCo());
//
//        if(needAuthSession){
//            SessionUtils.saveNiceRes(niceResDtoSess);
//        }else if(needAgentAuthSession){
//            SessionUtils.saveNiceAgentRes(niceResDtoSess);
//        }
//
//        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
//        rtnMap.put("RESULT_MSG", "성공");
//        return rtnMap;
//    }
//
//
//    /**
//     * 설명 : 본인인증 정보 비교
//     * @Author : papier
//     * @Date : 2023.11.07
//     * @param  @reqSeq : 인증한 키
//     * @param  @resSeq : 인증한 결과 키
//     *                  인증한 정보와 사용자 입력한 정보 일치 여부 확인
//     * @return
//     */
//    @RequestMapping(value="/auth/getReqAuthAjax.do")
//    @ResponseBody
//    public Map<String,Object> getReqAuthAjax(HttpServletRequest request, NiceLogDto niceLogDto
//            ,@RequestParam(value = "cstmrName", required = true) String cstmrName
//            ,@RequestParam(value = "cstmrNativeRrn", required = true) String cstmrNativeRrn
//            ,@RequestParam(value = "operType", required = false, defaultValue = "") String operType
//            ,@RequestParam(value = "onOffType", required = false, defaultValue = "") String onOffType
//            ,@RequestParam(value = "ncType", required = false, defaultValue = "") String ncType){
//
//        //인자값 확인
//        if (StringUtils.isBlank(niceLogDto.getReqSeq())
//                || StringUtils.isBlank(niceLogDto.getResSeq())
//                || StringUtils.isBlank(cstmrName)
//                || StringUtils.isBlank(cstmrNativeRrn) ) {
//            throw new McpCommonJsonException("9999" ,BIDING_EXCEPTION);
//        }
//
//        Map<String,Object> rtnMap = new HashMap<String, Object>();
//
//        NiceLogDto niceLogRtn = nicelog.getMcpNiceHistWithReqSeq(niceLogDto);
//
//        if (niceLogRtn == null) {
//            rtnMap.put("RESULT_CODE", "0001");
//            rtnMap.put("RESULT_DESC", NICE_CERT_EXCEPTION_INSR);
//            return rtnMap;
//        }
//
//        String autBirthDate = niceLogRtn.getBirthDateDec();
//        String autName = niceLogRtn.getName();
//        long niceHistSeq = niceLogRtn.getNiceHistSeq();
//
//        if (autBirthDate == null || autName == null)  {
//            rtnMap.put("RESULT_CODE", "0002");
//            rtnMap.put("RESULT_DESC", NICE_CERT_EXCEPTION_INSR);
//            return rtnMap;
//        }
//
//        /*logger.info("[WOO][WOO][WOO]autBirthDate==>"+autBirthDate);
//        logger.info("[WOO][WOO][WOO]getName==>"+autName);
//        logger.info("[WOO][WOO][WOO]cstmrName==>"+cstmrName);
//        logger.info("[WOO][WOO][WOO]cstmrNativeRrn==>"+cstmrNativeRrn);
//        logger.info("[WOO][WOO][WOO]niceHistSeq==>"+niceHistSeq);
//        logger.info("[WOO][WOO][WOO]autBirthDate.indexOf==>"+autBirthDate.indexOf(cstmrNativeRrn));*/
//
//        String strParamR3 =  niceLogDto.getParamR3();
//
//        int authResult = authEqual(strParamR3,niceLogRtn,cstmrName,cstmrNativeRrn); //인증 확인
//        if (authResult == 1) {
//            rtnMap.put("RESULT_CODE", Constants.AJAX_SUCCESS);
//            rtnMap.put("niceHistSeq", niceHistSeq);
//            rtnMap.put("RESULT_DESC", "인증한 정보가 동일 합니다.");
//
//            //ParamR3   및 시작  일시, referer   UPDATE 처리
//            NiceLogDto niceLogUp = new NiceLogDto();
//            niceLogUp.setNiceHistSeq(niceHistSeq);
//            niceLogUp.setParamR3(strParamR3);
//            niceLogUp.setnReferer(ipstatisticService.getReferer());
//            niceLogUp.setSysRdate(niceLogDto.getStartTimeToDate());  //시작 시간 저장 처리
//            nicelog.updateMcpNiceHist(niceLogUp);
//
//            //session 생성
//            NiceResDto niceResDtoSess = new  NiceResDto();
//            niceResDtoSess.setReqSeq(niceLogRtn.getReqSeq());
//            niceResDtoSess.setResSeq(niceLogRtn.getResSeq());
//            niceResDtoSess.setAuthType(niceLogRtn.getAuthType());
//            niceResDtoSess.setName(niceLogRtn.getName());
//            niceResDtoSess.setBirthDate(autBirthDate);
//            niceResDtoSess.setDupInfo(niceLogRtn.getDupInfo());
//            niceResDtoSess.setConnInfo(niceLogRtn.getConnInfo());
//            niceResDtoSess.setParam_r3(strParamR3);  //인자값
//            niceResDtoSess.setGender(niceLogRtn.getGender());
//            niceResDtoSess.setNationalInfo(niceLogRtn.getNationalInfo());
//            niceResDtoSess.setsMobileNo(niceLogRtn.getsMobileNo());
//            niceResDtoSess.setsMobileCo(niceLogRtn.getsMobileCo());
//
//            // 본인인증 step 적용 여부
//            Map<String, String> stepApply = certService.isAuthStepApplyUrl(request);
//
//            if (CUST_AUTH.equals(strParamR3)) {
//
//                // ============ STEP START ============
//                if("Y".equals(stepApply.get("isAuthStep"))){
//
//                    Map<String,String> vldReslt= new HashMap<>(); // 추가검증 결과
//                    vldReslt.put("RESULT_CODE", AJAX_SUCCESS);
//
//                    // 기기변경 추가검증
//                    if(OPER_TYPE_CHANGE.equals(operType) || OPER_TYPE_EXCHANGE.equals(operType)){
//                        JuoSubInfoDto changeAut = SessionUtils.getChangeAutCookieBean();
//                        if (changeAut == null || StringUtils.isBlank(changeAut.getContractNum())) {
//                            throw new McpCommonJsonException("0002", CHANGE_AUT_EXCEPTION);
//                        }
//
//                        if("1".equals(ncType)) { //미성년자
//                            UserSessionDto userSession = SessionUtils.getUserCookieBean();
//                            //기변세션의 이름과 전화번호로 SET해서 법정대리인 정보 확인
//                            NiceResDto childniceResDto = new NiceResDto();
//                            childniceResDto.setName(changeAut.getCustomerLinkName());
//                            childniceResDto.setsMobileNo(changeAut.getSubscriberNo());
//                            Map<String, String> agentCheckResult = niceCertifySvc.chkAgentInfo(cstmrName, childniceResDto, niceLogRtn, userSession);
//                            if (!AJAX_SUCCESS.equals(agentCheckResult.get("RESULT_CODE"))) {
//                                throw new McpCommonJsonException("STEP01", String.valueOf(agentCheckResult.get("RESULT_DESC")));
//                            }
//                        }
//                        vldReslt= this.crtChangeAuth(niceLogRtn, ncType);
//                    }
//                    // 셀프개통 추가검증
//                    // 2024-11-25 추가검증 대상 추가 (0 온라인, 3 모바일)
//                    else if ("5".equals(onOffType) || "7".equals(onOffType) || "6".equals(onOffType) || "8".equals(onOffType) ||
//                            "0".equals(onOffType) || "3".equals(onOffType)) {
//                        vldReslt= this.crtSelfAuth(niceLogRtn, ncType);
//                    }
//
//                    // 추가검증 결과값 확인
//                    if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
//                        throw new McpCommonJsonException("STEP01", vldReslt.get("RESULT_DESC"));
//                    }
//
//                    // 대리인구분값, 본인인증유형, 인증종류, 이름, 생년월일, CI, reqSeq, resSeq (reqSeq와 resSeq는 참조용)
//                    String[] certKey= {"urlType", "ncType", "authType", "moduType", "name"
//                                     , "birthDate", "connInfo", "reqSeq", "resSeq"};
//                    String[] certValue= {"simpleAuth", ncType, niceLogRtn.getAuthType(), strParamR3, autName
//                                        , autBirthDate, niceLogRtn.getConnInfo(), niceLogRtn.getReqSeq(), niceLogRtn.getResSeq()};
//                    certService.vdlCertInfo("C", certKey, certValue);
//                }
//                // ============ STEP END ============
//
//                // 본인인증 요청 알람(push) 인증완료 응답 건 로그 update (성공)
//                if("N".equals(niceLogRtn.getAuthType()) || "A".equals(niceLogRtn.getAuthType()) || "T".equals(niceLogRtn.getAuthType())){
//
//                    NiceTryLogDto niceTryDto= new NiceTryLogDto();
//                    niceTryDto.setReqSeq(niceLogRtn.getReqSeq());
//                    niceTryDto.setResSeq(niceLogRtn.getResSeq());
//
//                    NiceTryLogDto niceTryRtn = nicelog.getMcpNiceTryHist(niceTryDto);
//
//                    if(niceTryRtn != null){
//                        // 최종 성공으로 시도이력 update
//                        niceTryDto.setNiceTryHistSeq(niceTryRtn.getNiceTryHistSeq());
//                        niceTryDto.setSysRdate(niceLogDto.getStartTimeToDate());
//                        niceTryDto.setFnlSuccYn("Y");
//                        nicelog.updateMcpNiceTryHist(niceTryDto);
//                    }
//                }
//
//                SessionUtils.saveNiceRes(niceResDtoSess);
//
//            } else if (CUST_AGENT_AUTH.equals(strParamR3)) {
//                SessionUtils.saveNiceAgentRes(niceResDtoSess);
//            } else if (INSR_PROD.equals(strParamR3)) {
//
//                // ============ STEP START ============
//                if("Y".equals(stepApply.get("isAuthStep"))){
//
//                    // 대리인구분값, 인증종류, CI
//                    String[] certKey= {"urlType", "ncType", "moduType", "connInfo"};
//                    String[] certValue= {"chkInsrAuth", ncType, strParamR3, niceLogRtn.getConnInfo()};
//
//                    Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
//                    if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
//                        throw new McpCommonJsonException("STEP01", vldReslt.get("RESULT_DESC"));
//                    }
//
//                    // 대리인구분값, 인증종류, CI, reqSeq, resSeq (reqSeq와 resSeq는 참조용)
//                    certKey= new String[]{"urlType", "ncType", "moduType", "connInfo", "reqSeq", "resSeq"};
//                    certValue= new String[]{"insrAuth", ncType, strParamR3, niceLogRtn.getConnInfo(), niceLogRtn.getReqSeq(), niceLogRtn.getResSeq()};
//                    certService.vdlCertInfo("C", certKey, certValue);
//                }
//                // ============ STEP END ============
//
//                SessionUtils.saveNiceInsrRes(niceResDtoSess);
//            } else if (OPEN_AUTH.equals(strParamR3)) {
//                SessionUtils.saveNiceOpenRes(niceResDtoSess);
//            } else if(RWD_PROD.equals(strParamR3)) { // 보상서비스 본인인증
//
//                // ============ STEP START ============
//                if("Y".equals(stepApply.get("isAuthStep"))){
//
//                    // 대리인구분값, 인증종류, CI
//                    String[] certKey= {"urlType", "ncType", "moduType", "connInfo"};
//                    String[] certValue= {"chkRwdAuth", ncType, strParamR3, niceLogRtn.getConnInfo()};
//
//                    Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
//                    if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
//                        throw new McpCommonJsonException("STEP01", vldReslt.get("RESULT_DESC"));
//                    }
//
//                    // 대리인구분값, 인증종류, CI, reqSeq, resSeq (reqSeq와 resSeq는 참조용)
//                    certKey= new String[]{"urlType", "ncType", "moduType", "connInfo", "reqSeq", "resSeq"};
//                    certValue= new String[]{"rwdAuth", ncType, strParamR3, niceLogRtn.getConnInfo(), niceLogRtn.getReqSeq(), niceLogRtn.getResSeq()};
//                    certService.vdlCertInfo("C", certKey, certValue);
//                }
//                // ============ STEP END ============
//                SessionUtils.saveNiceRwdRes(niceResDtoSess);
//            } else if (BASE_AUTH.equals(strParamR3)) {
//                SessionUtils.saveNiceBasRes(niceResDtoSess);
//            }
//
//        } else if (authResult == -1) {
//            rtnMap.put("RESULT_CODE", "0002");
//            rtnMap.put("RESULT_DESC", NICE_CERT_EXCEPTION);
//        } else if (authResult == -2) {
//            rtnMap.put("RESULT_CODE", "0003");
//            rtnMap.put("RESULT_DESC", "인증한 정보가 없습니다.");
//        }
//
//
//        return rtnMap;
//    }
//
//    /**
//     * 셀프개통 본인인증 추가검증
//     * 2024-12-03 추가검증 대상 추가 (0 온라인, 3 모바일)
//     * @param niceLogDb
//     * @return Map<String, String>
//     */
//    private Map<String, String> crtSelfAuth(NiceLogDto niceLogDb, String ncType) {
//
//        Map<String, String> rtnMap= new HashMap<>();
//        // 1. nicePin연동 결과값과 본인인증값 일치여부 확인
//        // 대리인구분값, 이름, 생년월일, CID
//        String[] certKey= {"urlType", "ncType", "name", "birthDate", "connInfo"};
//        String[] certValue= {"chkSimpleAuth", ncType, niceLogDb.getName(), niceLogDb.getBirthDateDec(), niceLogDb.getConnInfo()};
//
//        return certService.vdlCertInfo("D", certKey, certValue);
//    }
//
//    /**
//     * 기기변경 본인인증 추가검증
//     * @param niceLogDb
//     * @param ncType
//     * @return Map<String, String>
//     */
//    private Map<String, String> crtChangeAuth(NiceLogDto niceLogDb, String ncType) {
//
//        Map<String, String> rtnMap= new HashMap<>();
//
//        // 1. 청소년인 경우, 기기변경 본인인증 추가검증 스텝 제외
////        if("1".equals(ncType)){
////            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
////            return rtnMap;
////        }
//
//        // 2. 기기변경 고객인증값과 본인인증값 일치여부 확인
//        // 대리인구분값, 이름, 생년월일
//        String[] certKey= {"urlType", "ncType", "name", "birthDate", "connInfo"};
//        String[] certValue= {"chkSimpleAuth", ncType, niceLogDb.getName(), niceLogDb.getBirthDateDec(), niceLogDb.getConnInfo()};
//
//        return certService.vdlCertInfo("D", certKey, certValue);
//    }
//
//    /**
//     * 설명 : 본인인증 정보 비교
//     * @Author : papier
//     * @Date : 2023.11.15
//     * @param  @authType : 인증 구분
//     *                     custAuth : 본인인증
//     *                     InsrProd  : 보험 인증
//     *                     rwdProd   :  자급제 보상 서비스 휴대폰 인증
//     * @param  @niceLogRtn : DB 조회한 정보
//     * @param  @cstmrName : custAuth 고객이 입력한 정보 사용자 이름
//     * @param  @cstmrNativeRrn : custAuth 고객이 입력한 정보 사용자 생년월일
//     * @return
//     */
//    private int authEqual(String authType,NiceLogDto niceLogDb,String cstmrName,String cstmrNativeRrn ) {
//        if (INSR_PROD.equals(authType) || RWD_PROD.equals(authType)) {
//            //이전에 인증 한 CI 정보 확인
//            NiceResDto niceResDto = SessionUtils.getNiceResCookieBean();
//            if (niceResDto == null) {
//                return -2;  //인증한 정보가 없습니다.
//            }
//
//            /*logger.info("[WOO][WOO][WOO]niceResDto.getConnInfo()==>"+niceResDto.getConnInfo());
//            logger.info("[WOO][WOO][WOO]niceLogDb.getConnInfo()==>"+niceLogDb.getConnInfo());*/
//            if (niceResDto.getConnInfo().equals(niceLogDb.getConnInfo())){
//                return 1;  //인증한 정보 동일
//            } else {
//                return -1; //인증한 정보가 상이 합니다.
//            }
//
//
//        } else if (BASE_AUTH.equals(authType)) {
//            /*따로 체크 하지 않음 .
//               인증한 정보 그대로 사용 처리 .
//               무조건 성공 처리
//             */
//            return 1;  //인증한 정보 동일
//
//        } else { // 간편본인인증
//            String autBirthDate = niceLogDb.getBirthDateDec();
//            String autName = niceLogDb.getName();
//            // 화면에서 입력한 이름, 생년월일과 본인인증에 사용된 이름, 생년월일 비교
//            if (cstmrName.equals(autName) &&  autBirthDate.indexOf(cstmrNativeRrn) > -1) {
//                return 1;
//            } else {
//                return -1; //인증한 정보가 상이 합니다.
//            }
//        }
//
//    }
//
//    @RequestMapping(value="/auth/resetProdAuth.do")
//    @ResponseBody
//    public Map<String,String> resetProdAuth(@RequestParam(value = "moduleType", required = true) String moduleType) {
//
//        Map<String, String> rtnMap = new HashMap<>();
//
//        // 0. 파라미터 검사
//        if(!(INSR_PROD.equals(moduleType) || RWD_PROD.equals(moduleType))){
//            throw new McpCommonJsonException("0001", F_BIND_EXCEPTION);
//        }
//
//        // 1. moduleType 인증 이력 존재여부 확인
//        if(0 < certService.getModuTypeStepCnt(moduleType, "")){
//            // 1-1. moduleType 인증 관련 스텝 초기화
//            CertDto certDto = new CertDto();
//            certDto.setModuType(moduleType);
//            certDto.setCompType("G");
//            certService.getCertInfo(certDto);
//        }
//
//        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
//        return rtnMap;
//    }
//
//    /**
//     * 설명 : 로그인 사용자 본인인증 정보 비교
//     * @Date : 2023.12.20
//     * @param  cstmrName
//     * @param  cstmrNativeRrn
//     * @param  contractNum
//     * @param  ncType : 고객타입 구분값 (양도인 0, 양수인 1)
//     * @return
//     */
//    @RequestMapping(value="/auth/getCertAuthAjax.do")
//    @ResponseBody
//    public Map<String,Object> getCertAuthAjax(NiceLogDto niceLogDto
//            ,@RequestParam(value = "cstmrName", required = false, defaultValue = "") String cstmrName
//            ,@RequestParam(value = "cstmrNativeRrn", required = false, defaultValue = "") String cstmrNativeRrn
//            ,@RequestParam(value = "contractNum", required = false, defaultValue = "") String contractNum
//            ,@RequestParam(value = "ncType", required = false, defaultValue = "") String ncType
//            ,@RequestParam(value = "cstmrType", required = false, defaultValue = "") String cstmrType
//            ,@RequestParam(value = "cstmrMinorName", required = false, defaultValue = "") String cstmrMinorName
//            ,@RequestParam(value = "cstmrMinorRrn", required = false, defaultValue = "") String cstmrMinorRrn) {
//
//        Map<String, Object> rtnMap= new HashMap<>();
//        String pNcType = ncType;
//
//        // 1.인자값 확인
//        if (StringUtils.isBlank(niceLogDto.getReqSeq())
//            || StringUtils.isBlank(niceLogDto.getResSeq())
//            || StringUtils.isBlank(niceLogDto.getParamR3())
//            || (StringUtils.isBlank(cstmrName) && !StringUtils.isBlank(cstmrNativeRrn))
//            || (!StringUtils.isBlank(cstmrName) && StringUtils.isBlank(cstmrNativeRrn))) {
//            throw new McpCommonJsonException("AUTH01", F_BIND_EXCEPTION);
//        }
//
//        // 2. 로그인 세션 확인
//        UserSessionDto userSession = SessionUtils.getUserCookieBean();
//        if (userSession == null || StringUtils.isEmpty(userSession.getUserId())) {
//            throw new McpCommonJsonException("LOGIN", NO_FRONT_SESSION_EXCEPTION);
//        }
//
//        // 3. 본인인증 정보 조회
//        NiceLogDto niceLogRtn = nicelog.getMcpNiceHistWithReqSeq(niceLogDto);
//        if (niceLogRtn == null || StringUtil.isEmpty(niceLogRtn.getBirthDateDec()) || StringUtil.isEmpty(niceLogRtn.getName())){
//            throw new McpCommonJsonException("AUTH02", NICE_CERT_EXCEPTION_INSR);
//        }
//
//        // 4. 본인인증 정보 검증
//        boolean isAuthEqual= false;
//
//        // 4-1. 회원등급에 따른 검사
//        if(!"01".equals(userSession.getUserDivision())){
//            isAuthEqual= niceCertifySvc.getAssociateAuthChk(userSession);
//        }else{
//
//            String customerId= null;
//            if(StringUtils.isEmpty(contractNum)){
//                List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
//                if(cntrList == null || cntrList.size() < 1){
//                    throw new McpCommonJsonException("0005", COMMON_EXCEPTION);
//                }
//                customerId= cntrList.get(0).getCustId();
//            }
//
//            Map<String, String> regularParam = new HashMap<>();
//            regularParam.put("contractNum", contractNum);
//            regularParam.put("ncType", pNcType);
//            regularParam.put("customerId", customerId);
//            isAuthEqual= niceCertifySvc.getRegularAuthChk(regularParam);
//        }
//
//        if(!isAuthEqual) {
//            throw new McpCommonJsonException("STEP01", F_BIND_EXCEPTION);
//        }
//
//        // 4-2. 공통 검사
//        String[] certKey= null;
//        String[] certValue= null;
//        Map<String,String> vldReslt= null;
//
//        // (화면정보) 고객구분값, 이름, 생년월일
//        if(!StringUtils.isBlank(cstmrName)){
//            certKey= new String[]{"urlType", "ncType", "name", "birthDate"};
//            certValue= new String[]{"chkReqMemSimpleAuth", pNcType, cstmrName, cstmrNativeRrn};
//
//            vldReslt= certService.vdlCertInfo("D", certKey, certValue);
//            if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
//                throw new McpCommonJsonException("STEP02", vldReslt.get("RESULT_DESC"));
//            }
//        }
//
//        // 미성년자일 경우 법정대리인 인증 정보 체크
//        if (!StringUtil.isEmpty(pNcType) && Constants.CSTMR_TYPE_NM.equals(cstmrType)) {
//            pNcType = "2"; // 양도인 법정대리인
//
//            // 고객구분값, 이름, 생년월일
//            certKey= new String[]{"urlType", "ncType", "name", "birthDate", "contractNum"};
//            certValue= new String[]{"baseMinorSimpleAuth", pNcType, cstmrMinorName, cstmrMinorRrn, contractNum};
//            certService.vdlCertInfo("C", certKey, certValue);
//        }
//        
//        // (본인인증 결과값과 비교) 고객구분값, 본인인증 이름, 생년월일
//        certKey= new String[]{"urlType", "ncType", "name", "birthDate"};
//        certValue= new String[]{"chkMemSimpleAuth", pNcType, niceLogRtn.getName(), niceLogRtn.getBirthDate()};
//
//        vldReslt= certService.vdlCertInfo("D", certKey, certValue);
//        if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
//            throw new McpCommonJsonException("STEP03", vldReslt.get("RESULT_DESC"));
//        }
//
//        // 4-3. 본인인증 정보 INSERT
//        // 고객구분값, 이름, 생년월일, reqSeq, resSeq, 본인인증 유형, CI, 인증종류 (reqSeq, resSeq는 참고용)
//        certKey= new String[]{"urlType", "ncType", "name", "birthDate", "reqSeq", "resSeq"
//                            , "authType", "connInfo", "moduType"};
//        certValue= new String[]{"memSimpleAuth", pNcType, niceLogRtn.getName(), niceLogRtn.getBirthDateDec(), niceLogRtn.getReqSeq(), niceLogRtn.getResSeq()
//                              , niceLogRtn.getAuthType(), niceLogRtn.getConnInfo(), niceLogDto.getParamR3()};
//
//        certService.vdlCertInfo("C", certKey, certValue);
//
//
//        // 본인인증 요청 알람(push) 인증완료 응답 건 로그 update (성공)
//        if("N".equals(niceLogRtn.getAuthType()) || "A".equals(niceLogRtn.getAuthType()) || "T".equals(niceLogRtn.getAuthType())){
//
//            NiceTryLogDto niceTryDto= new NiceTryLogDto();
//            niceTryDto.setReqSeq(niceLogRtn.getReqSeq());
//            niceTryDto.setResSeq(niceLogRtn.getResSeq());
//
//            NiceTryLogDto niceTryRtn = nicelog.getMcpNiceTryHist(niceTryDto);
//
//            if(niceTryRtn != null){
//                // 최종 성공으로 시도이력 update
//                niceTryDto.setNiceTryHistSeq(niceTryRtn.getNiceTryHistSeq());
//                niceTryDto.setSysRdate(niceLogDto.getStartTimeToDate());
//                niceTryDto.setFnlSuccYn("Y");
//                nicelog.updateMcpNiceTryHist(niceTryDto);
//            }
//        }
//
//        // 5. 세션 저장
//        // referer UPDATE 처리
//        NiceLogDto niceLogUp = new NiceLogDto();
//        niceLogUp.setNiceHistSeq(niceLogRtn.getNiceHistSeq());
//        niceLogUp.setParamR3(niceLogDto.getParamR3());
//        niceLogUp.setnReferer(ipstatisticService.getReferer());
//        nicelog.updateMcpNiceHist(niceLogUp);
//
//        NiceResDto niceResDtoSess = new NiceResDto();
//        niceResDtoSess.setReqSeq(niceLogRtn.getReqSeq());
//        niceResDtoSess.setResSeq(niceLogRtn.getResSeq());
//        niceResDtoSess.setAuthType(niceLogRtn.getAuthType());
//        niceResDtoSess.setName(niceLogRtn.getName());
//        niceResDtoSess.setBirthDate(niceLogRtn.getBirthDateDec());
//        niceResDtoSess.setDupInfo(niceLogRtn.getDupInfo());
//        niceResDtoSess.setConnInfo(niceLogRtn.getConnInfo());
//        niceResDtoSess.setParam_r3(niceLogDto.getParamR3());
//        niceResDtoSess.setGender(niceLogRtn.getGender());
//        niceResDtoSess.setNationalInfo(niceLogRtn.getNationalInfo());
//        niceResDtoSess.setsMobileNo(niceLogRtn.getsMobileNo());
//        niceResDtoSess.setsMobileCo(niceLogRtn.getsMobileCo());
//
//        if(CUST_AUTH.equals(niceLogDto.getParamR3())) SessionUtils.saveNiceRes(niceResDtoSess);
//
//        rtnMap.put("RESULT_CODE", Constants.AJAX_SUCCESS);
//        rtnMap.put("NICE_HIST_SEQ", niceLogRtn.getNiceHistSeq());
//        rtnMap.put("RESULT_MSG", "인증한 정보가 동일 합니다.");
//        return rtnMap;
//    }
//
//    /**
//     * 설명 : 본인인증 비교(di)
//     * @Date : 2023.12.28
//     * @param  cstmrName
//     * @param  cstmrNativeRrn
//     * @param  contractNum
//     * @param  ncType : 고객타입 구분값 (청소년 0, 대리인 1)
//     * @return
//     */
//    @RequestMapping(value="/auth/diCertAuthAjax.do")
//    @ResponseBody
//    public Map<String,Object> diCertAuthAjax(NiceLogDto niceLogDto
//            ,@RequestParam(value = "cstmrName", required = false) String cstmrName
//            ,@RequestParam(value = "cstmrNativeRrn", required = false) String cstmrNativeRrn
//            ,@RequestParam(value = "contractNum", required = false) String contractNum
//            ,@RequestParam(value = "ncType", required = false) String ncType) {
//
//        Map<String, Object> rtnMap= new HashMap<>();
//
//        boolean regularYn = false;
//        // 1.인자값 확인
//        if (StringUtils.isBlank(niceLogDto.getReqSeq())
//            || StringUtils.isBlank(niceLogDto.getResSeq())
//            || StringUtils.isBlank(niceLogDto.getParamR3())) {
//            throw new McpCommonJsonException("AUTH01", F_BIND_EXCEPTION);
//        }
//
//        // 2. 본인인증 정보 조회
//        NiceLogDto niceLogRtn = nicelog.getMcpNiceHistWithReqSeq(niceLogDto);
//        if (niceLogRtn == null || StringUtil.isEmpty(niceLogRtn.getBirthDateDec()) || StringUtil.isEmpty(niceLogRtn.getName())){
//            throw new McpCommonJsonException("AUTH02", NICE_CERT_EXCEPTION_INSR);
//        }
//
//        //리캡챠 점수 미달 일 경우, 다이렉트몰 회원 정기적 본인인증 프로세스 추가
//        //로그인 시 인증테이블 스텝에서 제외
//        if("login".equals(niceLogDto.getMenuType())){
//            UserSessionDto userInfo = new UserSessionDto();
//            userInfo.setUserId(niceLogDto.getUserId());
//            userInfo = loginDao.getUserInfo(userInfo);
//            String dupInfoByUserId = userInfo.getPin();
//
//            //로그인 예정 ID로 회원의 DI값과 본인인증 DI값 일치여부 확인
//            if(dupInfoByUserId == null || !dupInfoByUserId.equals(niceLogRtn.getDupInfo())){
//                throw new McpCommonJsonException("STEP01", "본인인증 정보가 일치하지 않습니다.");
//            }
//            Date today = new Date();
//            String sysdate = DateTimeUtil.changeFormat(today,"yyyyMMddHHmmss");
//            UserVO userVO = new UserVO();
//            userVO.setSysCdate(sysdate);
//            userVO.setMobileNo(niceLogRtn.getsMobileNo());
//            userVO.setUserid(niceLogDto.getUserId());
//            userVO.setAuthTelCode(niceLogRtn.getsMobileCo());
//            //인증한 이름과 연락처로 계약정보 확인
//            JuoSubInfoDto juoSubInfoRtn = mypageUserService.selectJuoSubInfo(niceLogRtn.getName(),niceLogRtn.getsMobileNo());
//            if(juoSubInfoRtn != null && ("I".equals(juoSubInfoRtn.getCustomerType()) || "O".equals(juoSubInfoRtn.getCustomerType()))){
//                userVO.setPhone1(niceLogRtn.getsMobileNo().substring(0, 3));
//                userVO.setPhone2(niceLogRtn.getsMobileNo().substring(3, 7));
//                userVO.setPhone3(niceLogRtn.getsMobileNo().substring(7, 11));
//                userVO.setContractNo(juoSubInfoRtn.getContractNum());
//                userVO.setCustomerId(juoSubInfoRtn.getCustomerId());
//                userVO.setRepNo("R");
//                userVO.setUserDivision("01");
//
//                // 해당 계약번호로 정회원 테이블에 등록여부 확인
//                Map<String, String> paramMap = new HashMap<String, String>();
//                paramMap.put("userId", niceLogDto.getUserId());
//                paramMap.put("ncn", juoSubInfoRtn.getContractNum());
//                paramMap.put("customerId", juoSubInfoRtn.getCustomerId());
//
//                int regularCnt = mypageUserService.getRegularCnt(paramMap);
//                // 해당 계약번호로 등록된게 없다면 정회원 테이블에 INSERT
//                if(regularCnt < 1){
//                    mypageUserService.insertRegularUpdate(userVO);  //MCP_USER_CNTR_MNG INSERT
//                    if("00".equals(userInfo.getUserDivision())) {
//                        regularYn = true;
//                    }
//                }
//                try {
//                    appSvc.userRepChange(paramMap);
//                } catch(DataAccessException e) {
//                    logger.error(e.toString());
//                } catch(Exception e) {
//                    logger.error(e.toString());
//                }
//            }
//            mypageUserService.userUpdate(userVO);
//
//            //리캡챠 점수가 낮아도 통과 처리
//            SessionUtils.saveRecaptchaSmsSession("Y");
//
//            rtnMap.put("RESULT_CODE", Constants.AJAX_SUCCESS);
//            rtnMap.put("NICE_HIST_SEQ", niceLogRtn.getNiceHistSeq());
//            rtnMap.put("RESULT_MSG", "인증한 정보가 동일 합니다.");
//            rtnMap.put("NICE_LOG_RTN", niceLogRtn);
//            rtnMap.put("regularYn", regularYn);
//            return rtnMap;
//        }
//
//        // 3. 본인인증 정보 검증
//        int dbStep = certService.getStepCnt();  // 현재 db 스텝
//        String[] certKey= null;
//        String[] certValue= null;
//        Map<String,String> vldReslt= null;
//        String insertUrlType= "memSimpleAuth";
//
//        // 3-1. 로그인 여부에 따른 검사
//        UserSessionDto userSession = SessionUtils.getUserCookieBean();
//        String crtPageNm = StringUtil.NVL(SessionUtils.getPageSession(), "");
//        if(userSession == null || StringUtils.isEmpty(userSession.getUserId())) {
//
//            insertUrlType= "simpleAuth";
//
//            String userProReq = SessionUtils.getUserPromotionRes();
//            //회원가입 프로모션 세션이 있는 경우
//            if(userProReq != null) {
//                crtPageNm = "fstPage";
//            }
//            if(dbStep > 0){
//                // 청소년 회원가입인 경우, 대리인 이름 추가 검증
//                if("fstPage".equals(crtPageNm) && "1".equals(ncType)){
//                    insertUrlType= "agentSimpleAuth";
//                    NiceResDto childniceResDto = SessionUtils.getNiceResCookieBean(); //만 14세 어린이의 인증값
//                    vldReslt = niceCertifySvc.chkAgentInfo(cstmrName, childniceResDto, niceLogRtn, userSession);
//                    if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
//                        throw new McpCommonJsonException("STEP04", vldReslt.get("RESULT_MSG"));
//                    }
//                }else{
//                    // (본인인증 결과값과 비교) 고객구분값, 본인인증 이름, DI
//                    certKey= new String[]{"urlType", "ncType", "name", "dupInfo"};
//                    certValue= new String[]{"chkSimpleAuth", ncType, niceLogRtn.getName(), niceLogRtn.getDupInfo()};
//
//                    vldReslt= certService.vdlCertInfo("D", certKey, certValue);
//
//                    if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
//                        throw new McpCommonJsonException("STEP01", vldReslt.get("RESULT_DESC"));
//                    }
//                }
//            } else if("fstPage".equals(crtPageNm)  && "0".equals(ncType)) { //회원가입 본인인증인 경우 인증한 핸드폰번호 INSERT
//                certKey= new String[]{"urlType", "ncType", "mobileNo"};
//                certValue= new String[]{"simpleAuthSub", ncType, niceLogRtn.getsMobileNo()};
//                certService.vdlCertInfo("C", certKey, certValue);
//            }
//        }else{
//            // 회원정보 변경인 경우
//            if("userInfoView".equals(crtPageNm)){
//                //본인 인증 시
//                if("0".equals(ncType)){
//                    // 로그인 PIN값과 본인인증 DI값 일치여부 확인
//                    if(!StringUtils.isEmpty(userSession.getPin())){
//                        if(!userSession.getPin().equals(niceLogRtn.getDupInfo())) {
//                            throw new McpCommonJsonException("STEP01", "로그인한 사용자와 본인인증 정보가 일치하지 않습니다.");
//                        }
//                    }
//
//                    // 생년월일 인증값과 비교 (휴대폰 번호는 최종 인증확인을 위해 INSERT)
//                    certKey= new String[]{"urlType", "ncType", "birthDate", "mobileNo"};
//                    certValue= new String[]{"baseMemSimpleAuth", ncType, userSession.getBirthday(), niceLogRtn.getsMobileNo()};
//                    certService.vdlCertInfo("C", certKey, certValue);
//
//                    // (본인인증 결과값과 비교) 고객구분값, 본인인증 생년월일
//                    certKey= new String[]{"urlType", "ncType", "birthDate"};
//                    certValue= new String[]{"chkMemSimpleAuth", ncType, niceLogRtn.getBirthDate()};
//                    vldReslt= certService.vdlCertInfo("D", certKey, certValue);
//
//                    if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
//                        throw new McpCommonJsonException("STEP04", vldReslt.get("RESULT_DESC"));
//                    }
//                } else{
//                    // 회원정보 수정 법정대리인 인증인 경우
//                    NiceResDto childniceResDto = SessionUtils.getNiceResCookieBean(); //만 14세 어린이의 인증값
//                    vldReslt = niceCertifySvc.chkAgentInfo(cstmrName, childniceResDto, niceLogRtn, userSession);
//
//                    if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
//                        throw new McpCommonJsonException("STEP04", vldReslt.get("RESULT_MSG"));
//                    }
//
//                    insertUrlType= "agentSimpleAuth";
//                }
//            } else if("cancelConsult".equals(crtPageNm)) { //상담해지 건
//                // 로그인 PIN값과 본인인증 DI값 일치여부 확인
//                if(!StringUtils.isEmpty(userSession.getPin())){
//                    if(!userSession.getPin().equals(niceLogRtn.getDupInfo())) {
//                        throw new McpCommonJsonException("STEP01", "로그인한 사용자와 본인인증 정보가 일치하지 않습니다.");
//                    }
//                }
//
//                // 생년월일 인증값과 비교
//                certKey = new String[]{"urlType", "ncType", "birthDate",};
//                certValue = new String[]{"baseMemSimpleAuth", ncType, userSession.getBirthday()};
//                certService.vdlCertInfo("C", certKey, certValue);
//
//                // (본인인증 결과값과 비교) 고객구분값, 본인인증 생년월일
//                certKey= new String[]{"urlType", "ncType", "birthDate"};
//                certValue= new String[]{"chkMemSimpleAuth", ncType, niceLogRtn.getBirthDate()};
//                vldReslt= certService.vdlCertInfo("D", certKey, certValue);
//
//                if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
//                    throw new McpCommonJsonException("STEP04", vldReslt.get("RESULT_DESC"));
//                }
//            } else { //회원정보 변경이 아닌 다른 경우 (기존로직)
//                // 로그인한 경우, 계약번호 필수파라미터
//                if(StringUtil.isEmpty(contractNum)){
//                    throw new McpCommonJsonException("AUTH03", F_BIND_EXCEPTION);
//                }
//                // 로그인 PIN값과 본인인증 DI값 일치여부 확인
//                if(!StringUtils.isEmpty(userSession.getPin())){
//                    if(!userSession.getPin().equals(niceLogRtn.getDupInfo())) {
//                        throw new McpCommonJsonException("STEP01", "로그인한 사용자와 본인인증 정보가 일치하지 않습니다.");
//                    }
//                }
//
//                // (화면정보) 고객타입 구분값(선택), 계약번호
//                certKey= new String[]{"urlType", "ncType", "contractNum"};
//                certValue= new String[]{"chkCntrNum", ncType, contractNum};
//
//                vldReslt= certService.vdlCertInfo("D", certKey, certValue);
//                if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
//                    throw new McpCommonJsonException("STEP02", vldReslt.get("RESULT_DESC"));
//                }
//
//                // 개통정보 INSERT
//                // 고객구분값, 이름, 생년월일
//                Map<String, String> juoSubMap= mypageUserService.selectContractObj("","",contractNum);
//
//                if(juoSubMap == null || juoSubMap.isEmpty()){
//                    throw new McpCommonJsonException("AUTH03", F_BIND_EXCEPTION);
//                }
//
//                certKey= new String[]{"urlType", "ncType", "name", "birthDate"};
//                certValue= new String[]{"baseMemSimpleAuth", ncType, juoSubMap.get("SUB_LINK_NAME"), juoSubMap.get("USER_SSN")};
//                certService.vdlCertInfo("C", certKey, certValue);
//
//                // (화면정보) 고객구분값, 이름, 생년월일
//                if(!StringUtils.isBlank(cstmrName) || !StringUtils.isEmpty(cstmrNativeRrn)){
//                    certKey= new String[]{"urlType", "ncType", "name", "birthDate"};
//                    certValue= new String[]{"chkReqMemSimpleAuth", ncType, cstmrName, cstmrNativeRrn};
//
//                    vldReslt= certService.vdlCertInfo("D", certKey, certValue);
//                    if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
//                        throw new McpCommonJsonException("STEP03", vldReslt.get("RESULT_DESC"));
//                    }
//                }
//                // (본인인증 결과값과 비교) 고객구분값, 본인인증 이름, 생년월일
//                certKey= new String[]{"urlType", "ncType", "name", "birthDate"};
//                certValue= new String[]{"chkMemSimpleAuth", ncType, niceLogRtn.getName(), niceLogRtn.getBirthDate()};
//
//                vldReslt= certService.vdlCertInfo("D", certKey, certValue);
//                if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
//                    throw new McpCommonJsonException("STEP04", vldReslt.get("RESULT_DESC"));
//                }
//            }
//        } // end of else-------------------
//
//
//
//        // 4. 본인인증 정보 INSERT
//        // 고객구분값, 이름, 생년월일, reqSeq, resSeq, 본인인증 유형, DI, 인증종류 (reqSeq, resSeq는 참고용)
//        certKey= new String[]{"urlType", "ncType", "name", "birthDate", "reqSeq", "resSeq"
//                            , "authType", "dupInfo", "moduType"};
//        certValue= new String[]{insertUrlType, ncType, niceLogRtn.getName(), niceLogRtn.getBirthDateDec(), niceLogRtn.getReqSeq(), niceLogRtn.getResSeq()
//                               , niceLogRtn.getAuthType(), niceLogRtn.getDupInfo(), niceLogDto.getParamR3()};
//
//        certService.vdlCertInfo("C", certKey, certValue);
//
//        // 5. 세션 저장
//        NiceResDto niceResDtoSess = new NiceResDto();
//        niceResDtoSess.setReqSeq(niceLogRtn.getReqSeq());
//        niceResDtoSess.setResSeq(niceLogRtn.getResSeq());
//        niceResDtoSess.setAuthType(niceLogRtn.getAuthType());
//        niceResDtoSess.setName(niceLogRtn.getName());
//        niceResDtoSess.setBirthDate(niceLogRtn.getBirthDateDec());
//        niceResDtoSess.setDupInfo(niceLogRtn.getDupInfo());
//        niceResDtoSess.setConnInfo(niceLogRtn.getConnInfo());
//        niceResDtoSess.setParam_r3(niceLogDto.getParamR3());
//        niceResDtoSess.setGender(niceLogRtn.getGender());
//        niceResDtoSess.setNationalInfo(niceLogRtn.getNationalInfo());
//        niceResDtoSess.setsMobileNo(niceLogRtn.getsMobileNo());
//        niceResDtoSess.setsMobileCo(niceLogRtn.getsMobileCo());
//
//        // 6. 본인인증 세션 저장
//        if(CUST_AUTH.equals(niceLogDto.getParamR3())){
//            SessionUtils.saveNiceRes(niceResDtoSess);
//        }else if(CUST_AGENT_AUTH.equals(niceLogDto.getParamR3())){
//            SessionUtils.saveNiceAgentRes(niceResDtoSess);
//        }
//
//
//        rtnMap.put("RESULT_CODE", Constants.AJAX_SUCCESS);
//        rtnMap.put("NICE_HIST_SEQ", niceLogRtn.getNiceHistSeq());
//        rtnMap.put("RESULT_MSG", "인증한 정보가 동일 합니다.");
//        rtnMap.put("NICE_LOG_RTN", niceLogRtn);
//        return rtnMap;
//    }
//
//    @RequestMapping(value="/auth/resetCertAuth.do")
//    @ResponseBody
//    public Map<String,String> resetProdAuth(HttpServletRequest request) {
//
//        String redirectPage = "/main.do";
//        if("Y".equals(NmcpServiceUtils.isMobile())){
//            redirectPage = "/m/main.do";
//        }
//
//        Map<String, String> rtnMap = new HashMap<>();
//
//        String requestUrl = (request.getHeader("referer") == null) ? "" : request.getHeader("referer");
//        if("".equals(requestUrl)) throw new McpCommonException(COMMON_EXCEPTION, redirectPage);
//
//        String uri = requestUrl.substring(request.getHeader("referer").lastIndexOf("/")+1);
//        int endIndex = uri.lastIndexOf('.');
//        if(endIndex > -1) uri= uri.substring(0, endIndex);
//
//        SessionUtils.removeCertSession();
//        SessionUtils.setPageSession(uri);
//
//        rtnMap.put("RESULT_CODE",AJAX_SUCCESS);
//        return rtnMap;
//    }
//
//    @RequestMapping(value="/nice/checkPromotionAuth.do")
//    @ResponseBody
//    public Map<String,Object> checkPromotionAuth(HttpServletRequest request
//                                            ,@RequestParam(value = "cstmrName", required = true) String cstmrName
//                                            ,@RequestParam(value = "cstmrNativeRrn", required = true) String cstmrNativeRrn
//                                            ,NiceLogDto niceLogDto) {
//
//
//        Map<String,Object> rtnMap = new HashMap<String, Object>();
//
//        //인자값 확인
//        if (StringUtils.isBlank(niceLogDto.getReqSeq())
//            || StringUtils.isBlank(niceLogDto.getResSeq())){
//            throw new McpCommonJsonException("AUTH01" ,BIDING_EXCEPTION);
//        }
//
//        // 1. 본인인증 정보 확인
//        NiceLogDto niceLogRtn = nicelog.getMcpNiceHistWithReqSeq(niceLogDto);
//        if (niceLogRtn == null || StringUtil.isEmpty(niceLogRtn.getBirthDateDec()) || StringUtil.isEmpty(niceLogRtn.getName())) {
//            throw new McpCommonJsonException("AUTH02" ,NICE_CERT_EXCEPTION_INSR);
//        }
//
//        // 2. 본인인증 정보 비교
//        int authResult = authEqual("", niceLogRtn, cstmrName.toUpperCase(), cstmrNativeRrn);
//
//        if(authResult == 1){
//            rtnMap.put("RESULT_CODE", Constants.AJAX_SUCCESS);
//            rtnMap.put("RESULT_DESC", "인증한 정보가 동일 합니다.");
//
//            //session 생성
//            NiceResDto niceResDtoSess = new  NiceResDto();
//            niceResDtoSess.setReqSeq(niceLogRtn.getReqSeq());
//            niceResDtoSess.setResSeq(niceLogRtn.getResSeq());
//            niceResDtoSess.setAuthType(niceLogRtn.getAuthType());
//            niceResDtoSess.setName(niceLogRtn.getName());
//            niceResDtoSess.setBirthDate(niceLogRtn.getBirthDateDec());
//            niceResDtoSess.setDupInfo(niceLogRtn.getDupInfo());
//            niceResDtoSess.setConnInfo(niceLogRtn.getConnInfo());
//            niceResDtoSess.setParam_r3(SMS_AUTH);  //인자값
//            niceResDtoSess.setGender(niceLogRtn.getGender());
//            niceResDtoSess.setNationalInfo(niceLogRtn.getNationalInfo());
//            niceResDtoSess.setsMobileNo(niceLogRtn.getsMobileNo());
//            niceResDtoSess.setsMobileCo(niceLogRtn.getsMobileCo());
//
//            SessionUtils.saveNiceRes(niceResDtoSess);
//
//        }else{
//
//            // 본인인증 세션 제거
//            SessionUtils.saveNiceRes(null);
//            rtnMap.put("RESULT_CODE", "0001");
//            rtnMap.put("RESULT_DESC", NICE_CERT_EXCEPTION);
//        }
//
//        return rtnMap;
//    }
//
//    @RequestMapping(value="/auth/preProcSimpleAuth.do")
//    @ResponseBody
//    public Map<String,String> preProcSimpleAuth(@RequestParam(value = "authType", required = true) String authType
//                                               ,@RequestParam(value = "onOffType", required = false) String onOffType) {
//
//        Map<String, String> rtnMap = new HashMap<>();
//
//        String pollingPageUrl= "";
//        String mPollingPageUrl= "";
//
//        if("C".equals(authType) || "X".equals(authType) || "M".equals(authType)){
//            pollingPageUrl=  NmcpServiceUtils.getPropertiesVal("simpleAuth.nice.url") + "?sAuthType=" + authType;
//            mPollingPageUrl= pollingPageUrl;
//        }else if("A".equals(authType)){
//            pollingPageUrl= NmcpServiceUtils.getPropertiesVal("simpleAuth.pass.url");
//            mPollingPageUrl= pollingPageUrl;
//        }else if("T".equals(authType)){
//            pollingPageUrl= NmcpServiceUtils.getPropertiesVal("simpleAuth.toss.url");
//            mPollingPageUrl= pollingPageUrl;
//        }else if("K".equals(authType)){
//            pollingPageUrl= NmcpServiceUtils.getPropertiesVal("simpleAuth.kakao.url");
//            mPollingPageUrl= "/m"+pollingPageUrl;
//        }else{
//
//            if(!"N".equals(authType)) throw new McpCommonJsonException("0001", F_BIND_EXCEPTION);
//        }
//
//        // 셀프개통인 경우 crtSeq 필수 존재
//        long crtSeq = SessionUtils.getCertSession();
//        if(("5".equals(onOffType) || "7".equals(onOffType)) && 0 == crtSeq){
//            throw new McpCommonJsonException("0002", F_BIND_EXCEPTION);
//        }
//
//        // 현재 referer 조회 (DB)
//        String referer= "";
//
//        if(crtSeq > 0){
//            referer= certService.getCertReferer(crtSeq);
//        }
//
//        // 현재 referer 조회 (SESSION)
//        String sessReferer= SessionUtils.getPageSession();
//
//        if("NacEsimSelfForm".equals(referer) || "NacSelfForm".equals(referer)
//           || "NacEsimSelfForm".equals(sessReferer) || "NacSelfForm".equals(sessReferer)){
//
//            // esim 신규 셀프개통, usim 신규 셀프개통인 경우 sms인증 이력 필수 - 해피콜 제외
//            String onlineYn= SessionUtils.getOnlineSession();
//            int smsModuCnt= certService.getModuTypeStepCnt(SMS_AUTH, "");
//
//            if(!"Y".equals(onlineYn) &&  0 >= smsModuCnt){
//                throw new McpCommonJsonException("0003", F_BIND_EXCEPTION);
//            }
//        }
//
//        // 포탈/모바일 구분
//        String rntUrl= pollingPageUrl;
//        if(!"P".equals(NmcpServiceUtils.getPlatFormCd())) {
//            rntUrl= mPollingPageUrl;
//        }
//
//        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
//        rtnMap.put("pollingPageUrl", rntUrl);
//        return rtnMap;
//    }
//
//    @RequestMapping(value="/auth/procOnline.do")
//    @ResponseBody
//    public Map<String,String> procOnline(@RequestParam(value = "procType", required = true) String procType) {
//
//        Map<String, String> rtnMap = new HashMap<>();
//
//        String onlineTypeYn= "N";
//        if("S".equals(procType)) onlineTypeYn= "Y";
//
//        SessionUtils.saveOnlineSession(onlineTypeYn);
//
//        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
//        return rtnMap;
//    }
//
//    /**
//     * (인증 검사 후) SMS 인증 URL 제공
//     * @param niceLogDto
//     * @return Map<String, Object>
//     */
//    @RequestMapping(value="/auth/getSmsAuthPopWithCert.do")
//    @ResponseBody
//    public Map<String,Object> getSmsAuthPopWithCert(@ModelAttribute NiceLogDto niceLogDto) {
//
//        Map<String, Object> rtnMap = new HashMap<>();
//
//        boolean certResult = false;
//
//        // 메뉴 별 인증 검사
//        if("usimBuy".equalsIgnoreCase(niceLogDto.getMenuType()) ||
//           "replaceUsim".equalsIgnoreCase(niceLogDto.getMenuType())){
//            certResult = niceCertifySvc.chkUsimBuySmsReqInfo(niceLogDto);
//        }
//
//        if(!certResult){
//            throw new McpCommonJsonException("F_AUTH", "인증요청 정보가 올바른지 확인 바랍니다.");
//        }
//
//        String pollingPageUrl= NmcpServiceUtils.getPropertiesVal("simpleAuth.nice.url") + "?sAuthType=M";
//        Calendar cal = Calendar.getInstance();
//
//        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
//        rtnMap.put("pollingPageUrl", pollingPageUrl);
//        rtnMap.put("startTime", cal.getTimeInMillis());
//        return rtnMap;
//    }
//
//    /**
//     * <pre>
//     * 설명  : 메뉴 별 간편본인인증 정보 검사
//     * @param niceLogDto
//     * @return: Map<String, String>
//     * </pre>
//     */
//    @RequestMapping(value = "/auth/checkAuthCallbackWithMenuType.do")
//    @ResponseBody
//    public Map<String, String> checkAuthCallbackWithMenuType(NiceLogDto niceLogDto) {
//
//        Map<String, String> rtnMap = new HashMap<>();
//        rtnMap.put("RESULT_CODE", "F_AUTH");
//
//        // 파라미터 체크
//        if(StringUtils.isBlank(niceLogDto.getReqSeq())
//          || StringUtils.isBlank(niceLogDto.getResSeq())
//          || StringUtils.isBlank(niceLogDto.getMenuType())){
//            throw new McpCommonJsonException("F_AUTH", BIDING_EXCEPTION);
//        }
//
//        // 간편 본인 인증 정보 조회
//        NiceLogDto niceLogRtn= nicelog.getMcpNiceHistWithReqSeq(niceLogDto);
//        if (niceLogRtn == null) {
//            throw new McpCommonJsonException("F_AUTH", NICE_CERT_EXCEPTION_INSR);
//        }
//
//        // 메뉴 별 SMS 인증 결과 검사
//        if("replaceUsimSelf".equalsIgnoreCase(niceLogDto.getMenuType())){
//            rtnMap = niceCertifySvc.chkReplaceUsimSelfAuthInfo(niceLogDto, niceLogRtn);
//        }
//
//        if(!AJAX_SUCCESS.equals(rtnMap.get("RESULT_CODE"))){
//            return rtnMap;
//        }
//
//        // 인증 세션 저장
//        NiceResDto niceResDtoSess = new NiceResDto();
//        niceResDtoSess.setReqSeq(niceLogRtn.getReqSeq());
//        niceResDtoSess.setResSeq(niceLogRtn.getResSeq());
//        niceResDtoSess.setAuthType(niceLogRtn.getAuthType());
//        niceResDtoSess.setName(niceLogRtn.getName());
//        niceResDtoSess.setBirthDate(niceLogRtn.getBirthDateDec());
//        niceResDtoSess.setDupInfo(niceLogRtn.getDupInfo());
//        niceResDtoSess.setConnInfo(niceLogRtn.getConnInfo());
//        niceResDtoSess.setParam_r3(CUST_AUTH);
//        niceResDtoSess.setGender(niceLogRtn.getGender());
//        niceResDtoSess.setNationalInfo(niceLogRtn.getNationalInfo());
//        niceResDtoSess.setsMobileNo(niceLogRtn.getsMobileNo());
//        niceResDtoSess.setsMobileCo(niceLogRtn.getsMobileCo());
//
//        SessionUtils.saveNiceRes(niceResDtoSess);
//        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
//        rtnMap.put("RESULT_MSG", "성공");
//        return rtnMap;
//    }
//
//}
