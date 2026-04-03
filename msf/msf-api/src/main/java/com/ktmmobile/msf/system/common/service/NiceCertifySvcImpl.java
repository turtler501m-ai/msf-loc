//package com.ktmmobile.msf.system.common.service;
//
//import java.net.SocketTimeoutException;
//import java.security.NoSuchAlgorithmException;
//import java.security.SecureRandom;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//import javax.servlet.http.HttpServletRequest;
//
//import com.ktds.crypto.exception.CryptoException;
//import com.ktmmobile.mcp.appform.dto.JuoSubInfoDto;
//import com.ktmmobile.mcp.cert.service.CertService;
//import com.ktmmobile.mcp.common.constants.Constants;
//import com.ktmmobile.mcp.common.dto.NiceLogDto;
//import com.ktmmobile.mcp.common.dto.UserSessionDto;
//import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
//import com.ktmmobile.mcp.common.util.*;
//import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
//import com.ktmmobile.mcp.mypage.service.MypageService;
//import com.ktmmobile.mcp.mypage.service.MypageUserService;
//import com.ktmmobile.mcp.usim.service.ReplaceUsimService;
//import org.apache.commons.httpclient.NameValuePair;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import com.google.gson.JsonObject;
//import com.ktmmobile.mcp.common.dto.NiceResDto;
//import com.ktmmobile.mcp.common.exception.McpCommonException;
//import org.springframework.web.client.RestTemplate;
//
//import static com.ktmmobile.mcp.common.constants.Constants.*;
//import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.*;
//
//@Service
//public class NiceCertifySvcImpl implements NiceCertifySvc {
//
//    private static Logger logger = LoggerFactory.getLogger(NiceCertifySvcImpl.class);
//
//    @Autowired
//    private IpStatisticService ipstatisticService;
//
//    @Autowired
//    private CertService certService;
//
//    @Autowired
//    private MypageUserService mypageUserService;
//
//    @Autowired
//    private ReplaceUsimService replaceUsimService;
//
//    @Autowired
//    private MypageService mypageService;
//
//    @Value("${inf.url}")
//    private String infUrl;
//
//    @Value("${NICE_UID_PASSWORD}")
//    private String niceUidPassword;
//
//    @Value("${NICE_CLIENT_ID}")
//    private String clientId;
//
//    @Value("${NICE_CLIENT_SECRET}")
//    private String clientSecret;
//
//    @Value("${NICE_CLIENT_PASS_ID}")
//    private String passClientId;
//
//    @Value("${NICE_CLIENT_PASS_SECRET}")
//    private String passClientSecret;
//
//    @Value("${api.interface.server}")
//    private String apiInterfaceServer;
//
//    @Value("${ext.url}")
//    private String extUrl;
//
//    @Override
//    public String checkNiceAccount(NiceResDto niceResDto) {
//
//        Random numGen = null;
//        try {
//            numGen = SecureRandom.getInstance("SHA1PRNG");
//        } catch (NoSuchAlgorithmException e) {
//            throw new McpCommonException("NoSuchAlgorithmException");
//        }
//
//        String result = "";
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd",Locale.KOREA);
//        String strOrderNo   = sdf.format(new Date()) + (Math.round(numGen.nextDouble() * 10000000000L) + "");           //주문번호 : 매 요청마다 중복되지 않도록 유의
//
//        CommonHttpClient client = new CommonHttpClient(extUrl+"/rlnmCheck.do");
//        NameValuePair[] data = {
//                new NameValuePair("niceUid",NICE_UID),
//                new NameValuePair("svcPwd",niceUidPassword),
//                new NameValuePair("service",niceResDto.getService()),  //서비스구분 1=계좌소유주확인 2=계좌성명확인 3=계좌유효성확인
//                new NameValuePair("strGbn","1"),  // 1 : 개인, 2: 사업자
//                new NameValuePair("strResId",niceResDto.getResId()), //주민번호(사업자 번호,법인번호)
//                new NameValuePair("strNm",StringUtil.substringByBytes(niceResDto.getName(),0,60) ), //계좌소유주명
//                new NameValuePair("strBankCode",niceResDto.getBankCode()), //은행코드(전문참조)
//                new NameValuePair("strAccountNo",niceResDto.getAccountNo()), //계좌번호
//                new NameValuePair("svcGbn",niceResDto.getSvcGbn()),   //업무구분(전문참조)
//                new NameValuePair("strOrderNo",strOrderNo),
//                new NameValuePair("svc_cls",niceResDto.getSvcCls()),  //내-외국인구분
//                new NameValuePair("inq_rsn","10") // 조회사유 - 10:회원가입 20:기존회원가입 30:성인인증 40:비회원확인 90:기타사유
//        };
//        try {
//            result = client.postUtf8(data);
//            //result = client.post(data, "UTF-8");
//        } catch (SocketTimeoutException e) {
//            throw new McpCommonException("SocketTimeoutException");
//        }
//
//        //data:"service=3&svcGbn=4&strBankCode=" + $('#reqBankTmp').val() + "&strAccountNo=" +  $('#reqAccountNumberTmp').val() + "&USERNM=" + $('#reqAccountNameTmp').val(),
//
//       /*
//        * 확인용도 주석 처리
//        private String service   ; //서비스구분 1=계좌소유주확인 2=계좌성명확인 3=계좌유효성확인
//        private String resId     ; //주민번호(사업자 번호,법인번호)
//        private String bankCode  ; //은행코드(전문참조)
//        private String accountNo ; //계좌번호
//        private String svcGbn    ; //업무구분(전문참조)
//        private String svcCls    ; //내-외국인구분
//
//        service 서비스구분 1: 소유주 확인, 2: 예금주명 확인, 3: 계좌 유효성 확인
//        svcGbn 업무구분 5: 소유주 확인, 2: 예금주명 확인, 4: 계좌 유효성 확인
//        */
//
//        return result;
//    }
//
//    @Override
//    public String niceAccountOtpName(NiceResDto niceResDto) {
//
//        Random numGen = null;
//        try {
//            numGen = SecureRandom.getInstance("SHA1PRNG");
//        } catch (NoSuchAlgorithmException e) {
//            throw new McpCommonException("NoSuchAlgorithmException");
//        }
//
//        String result = "";
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd",Locale.KOREA);
//        String strRequestNo   = sdf.format(new Date()) + (Math.round(numGen.nextDouble() * 10000000000L) + "");           //주문번호 : 매 요청마다 중복되지 않도록 유의
//
//        CommonHttpClient client = new CommonHttpClient(extUrl+"/niceAccountOtpName.do");
//
//        NameValuePair[] data = {
//                new NameValuePair("clientId",clientId),
//                new NameValuePair("clientSecret",clientSecret),
//                new NameValuePair("strNm",niceResDto.getName()), //계좌소유주명
//                new NameValuePair("strBankCode",niceResDto.getBankCode()), //은행코드(전문참조)
//                new NameValuePair("strAccountNo",niceResDto.getAccountNo()), //계좌번호
//                new NameValuePair("strRequestNo",strRequestNo),                   //요청고유번호
//
//        };
//        try {
//            result = client.postUtf8(data);
//        } catch (SocketTimeoutException e) {
//            throw new McpCommonException("SocketTimeoutException");
//        }
//
//        //data:"service=3&svcGbn=4&strBankCode=" + $('#reqBankTmp').val() + "&strAccountNo=" +  $('#reqAccountNumberTmp').val() + "&USERNM=" + $('#reqAccountNameTmp').val(),
//
//       /*
//        * 확인용도 주석 처리
//        private String service   ; //서비스구분 1=계좌소유주확인 2=계좌성명확인 3=계좌유효성확인
//        private String resId     ; //주민번호(사업자 번호,법인번호)
//        private String bankCode  ; //은행코드(전문참조)
//        private String accountNo ; //계좌번호
//        private String svcGbn    ; //업무구분(전문참조)
//        private String svcCls    ; //내-외국인구분
//        */
//
//        return result;
//    }
//
//    @Override
//    public String niceAccountOtpConfirm(NiceResDto niceResDto) {
//
//        Random numGen = null;
//        try {
//            numGen = SecureRandom.getInstance("SHA1PRNG");
//        } catch (NoSuchAlgorithmException e) {
//            throw new McpCommonException("NoSuchAlgorithmException");
//        }
//
//        String result = "";
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd",Locale.KOREA);
//        String strOrderNo   = sdf.format(new Date()) + (Math.round(numGen.nextDouble() * 10000000000L) + "");           //주문번호 : 매 요청마다 중복되지 않도록 유의
//
//        CommonHttpClient client = new CommonHttpClient(extUrl+"/niceAccountOtpConfirm.do");
//
//        NameValuePair[] data = {
//                new NameValuePair("clientId",clientId),
//                new NameValuePair("clientSecret",clientSecret),
//                new NameValuePair("strRequestNo",niceResDto.getRequestNo()), //요청고유번호
//                new NameValuePair("strResUniqId",niceResDto.getResUniqId()), //응답고유번호
//                new NameValuePair("otp",niceResDto.getOtp()),                         //otp
//
//        };
//        try {
//            result = client.postUtf8(data);
//        } catch (SocketTimeoutException e) {
//            throw new McpCommonException("SocketTimeoutException");
//        }
//
//
//        return result;
//    }
//
//    /*
//     *  PASS 본인인증 알람 전송
//     */
//    @Override
//    public String pushPassAlram(HttpServletRequest request) {
//
//         /*
//                    - PW : PC Web
//               - PA : PC Application
//               - MW : Mobile Web
//               - MA : Mobile App
//           */
//           String mType= request.getParameter("mType");
//           if("Mobile".equalsIgnoreCase(mType)) { // 모바일, 포탈 구분
//              mType= "MW";
//           }else {
//              mType= "PW";
//           }
//
//          String clientIp= ipstatisticService.getClientIp();
//
//        // 고객정보 서버통신으로 넘겨주긴 전 암호화 처리
//          String name= EncryptUtil.ace256Enc(request.getParameter("name"));
//        String birthDate= EncryptUtil.ace256Enc(request.getParameter("birthDate"));
//        String ctn= EncryptUtil.ace256Enc(request.getParameter("ctn"));
//
//        CommonHttpClient client = new CommonHttpClient(extUrl+"/passAlramCallApi.do");
//
//         String result= null;
//         NameValuePair[] data = {
//             new NameValuePair("clientId",passClientId),
//             new NameValuePair("clientSecret",passClientSecret),
//             new NameValuePair("name", name),
//             new NameValuePair("birthDate", birthDate),
//             new NameValuePair("ctn", ctn),
//             new NameValuePair("channel", mType),
//             new NameValuePair("clientIp", clientIp)
//        };
//
//        try {
//            result = client.postUtf8(data);
//        } catch (SocketTimeoutException e) {
//            throw new McpCommonException("SocketTimeoutException");
//        }
//
//        return result;
//    }
//
//     /*
//     *  PASS 본인인증 결과 확인
//     */
//    @Override
//    public String passCertifyInfo(HttpServletRequest request) {
//
//         CommonHttpClient client = new CommonHttpClient(extUrl+"/passCertifyInfoApi.do");
//         String result= null;
//
//         NameValuePair[] data = {
//             new NameValuePair("clientId",passClientId),
//             new NameValuePair("clientSecret",passClientSecret),
//             new NameValuePair("requestNo", request.getParameter("requestNo")), // 요청고유번호
//             new NameValuePair("resUniqId", request.getParameter("resUniqId"))  // 응답고유번호
//         };
//
//        try {
//            result = client.postUtf8(data);
//        } catch (SocketTimeoutException e) {
//            throw new McpCommonException("SocketTimeoutException");
//        }
//
//        return result;
//    }
//
//    /**
//     * 개통정보와 본인인증 정보 비교
//     */
//    @Override
//    public boolean getRegularAuthChk(Map<String, String> regularParam) {
//
//        String contractNum = regularParam.get("contractNum");
//        String customerId = regularParam.get("customerId");
//        String ncType = regularParam.get("ncType");
//
//        String[] certKey= null;
//        String[] certValue= null;
//        Map<String, String> vldReslt= null;
//        Map<String,String> certInfo= null;
//
//        if(!StringUtils.isBlank(contractNum)){ // 1. 파라미터 계약번호 존재
//
//            // 1-1. (화면정보) 고객구분값, 계약번호
//            certKey= new String[]{"urlType", "ncType", "contractNum"};
//            certValue= new String[]{"chkCntrNum", ncType, contractNum};
//
//            vldReslt= certService.vdlCertInfo("F", certKey, certValue);
//            if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
//                return false;
//            }
//
//            // 1-2. 개통 정보 조회 (SUB_INFO)
//            certInfo= mypageUserService.selectContractObj("","",contractNum);
//
//        }else{ // 2. 파라미터 계약번호 미존재
//
//            // 2-1. 개통 정보 조회 (CUS_INFO)
//            RestTemplate restTemplate = new RestTemplate();
//            certInfo = restTemplate.postForObject(apiInterfaceServer + "/mypage/getUserCusInfo", customerId, Map.class);
//
//        }
//
//        if(certInfo == null || certInfo.isEmpty()) return false;
//
//        // 3. 개통정보 INSERT
//        // 고객구분값, 이름, 생년월일
//        certKey= new String[]{"urlType", "ncType", "name", "birthDate"};
//        certValue= new String[]{"baseMemSimpleAuth", ncType, certInfo.get("SUB_LINK_NAME"), certInfo.get("USER_SSN")};
//        certService.vdlCertInfo("C", certKey, certValue);
//        return true;
//    }
//
//    /**
//     * 로그인세션 정보와 본인인증 정보 비교
//     */
//    @Override
//    public boolean getAssociateAuthChk(UserSessionDto userSession) {
//
//        // 로그인 세션 정보 INSERT
//        // 이름, 생년월일
//        String[] certKey= {"urlType", "name", "birthDate"};
//        String[] certValue= {"baseMemSimpleAuth", userSession.getName(), userSession.getBirthday()};
//        certService.vdlCertInfo("C", certKey, certValue);
//
//        return true;
//    }
//
//    /** 간편본인인증 알림요청 전 SMS본인인증 완료 여부 확인 - 셀프개통 */
//    @Override
//    public boolean preChkSimpleAuth() {
//
//        boolean preChk= true;
//
//        long crtSeq = SessionUtils.getCertSession();
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
//                preChk= false;
//            }
//        }
//
//        return preChk;
//    }
//
//    /** 유심구매하기 휴대폰 인증 요청 전, STEP 처리 */
//    @Override
//    public boolean chkUsimBuySmsReqInfo(NiceLogDto niceLogDto) {
//
//        boolean certResult = true;
//
//        // 필수 요청값 확인 (이름)
//        if(StringUtils.isEmpty(niceLogDto.getName())){
//            return false;
//        }
//
//        // 구매자인 경우 인가된 사용자 체크 + step 처리 (ncType: 구매자 0, 대리인 1)
//        if(!"1".equals(niceLogDto.getNcType())){
//
//            Map<String, String> certMap = mypageService.checkAuthUser(niceLogDto.getName(), null);
//            certResult = "0000".equals(certMap.get("returnCode"));
//
//            // ============ STEP START ============
//            if(certResult){
//                certService.delStepInfo(1);   // STEP 초기화
//
//                String[] certKey= {"urlType", "name", "ncType"};
//                String[] certValue= {"baseMemSimpleAuth", niceLogDto.getName().replaceAll(" ", ""), "0"};
//                certService.vdlCertInfo("C", certKey, certValue);
//            }
//            // ============ STEP END ============
//        }
//
//        return certResult;
//    }
//
//    /** 유심구매하기 휴대폰 인증 응답 후, STEP 처리 */
//    @Override
//    public Map<String, String> chkUsimBuySmsResInfo(NiceLogDto niceLogDto, NiceLogDto niceLogRtn) {
//
//        Map<String,String> vldReslt= new HashMap<>();
//
//        String inputName = StringUtil.NVL(niceLogDto.getName(), "").replaceAll(" ", "");
//        String authName = StringUtil.NVL(niceLogRtn.getName(), "").replaceAll(" ", "");
//
//        String authBirth = StringUtil.NVL(niceLogRtn.getBirthDateDec(), "");
//        int age = NmcpServiceUtils.getBirthDateToAmericanAge(authBirth, new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
//        boolean isUnderAge = age < USIM_BUY_CHILD_AGE;  // 법정대리인 동의가 필요한 미성년자 (14세 미만)
//        boolean isAdult = USIM_BUY_MINOR_AGENT_AGE <= age;
//
//        String[] certKey = null;
//        String[] certValue= null;
//
//        // 파라미터 이름과 sms인증 이름 비교
//        if(!inputName.equalsIgnoreCase(authName)){
//            vldReslt.put("RESULT_CODE", "F_AUTH");
//            vldReslt.put("RESULT_MSG", NICE_CERT_EXCEPTION);
//            return vldReslt;
//        }
//
//        // 구매자인 경우 인가된 사용자 체크 + step 추가 처리 (ncType: 구매자 0, 대리인 1)
//        if(!"1".equals(niceLogDto.getNcType())){
//
//            // 파라미터 이름과 로그인세션 이름 비교
//            Map<String, String> certMap = mypageService.checkAuthUser(inputName, null);
//            if(!"0000".equals(certMap.get("returnCode"))){
//                vldReslt.put("RESULT_CODE", "F_LOGIN");
//                vldReslt.put("RESULT_MSG", "회원정보와 본인인증 정보가 일치하지 않습니다");
//                return vldReslt;
//            }
//
//            // 휴대폰 인증 정보 step 처리
//            certKey = new String[]{"urlType", "name", "ncType"};
//            certValue = new String[]{"chkSmsAuth", authName, "0"};
//
//            vldReslt = certService.vdlCertInfo("D", certKey, certValue);
//            if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
//                vldReslt.put("RESULT_CODE", "F_STEP");
//                vldReslt.put("RESULT_MSG", vldReslt.get("RESULT_DESC"));
//                return vldReslt;
//            }
//
//        }else{
//            // 대리인 성인 나이검사
//            if(!isAdult){
//                vldReslt.put("RESULT_CODE", "F_AUTH_AGNT");
//                vldReslt.put("RESULT_MSG", "법정대리인은 만 19세 이상 성인이어야 합니다.");
//                return vldReslt;
//            }
//
//            // 대리인 휴대폰 인증 정보 step 처리
//            certKey= new String[]{"urlType", "moduType", "name", "reqSeq", "resSeq", "ncType"};
//            certValue= new String[]{"agentSmsAuth", SMS_AUTH, niceLogRtn.getName(), niceLogRtn.getReqSeq(), niceLogRtn.getResSeq(), niceLogDto.getNcType()};
//            certService.vdlCertInfo("C", certKey, certValue);
//        }
//
//        vldReslt.put("UNDER_AGE_YN", isUnderAge ? "Y" : "N");
//        vldReslt.put("RESULT_CODE", AJAX_SUCCESS);
//        vldReslt.put("RESULT_MSG", "성공");
//        return vldReslt;
//    }
//
//    /** 회원 가입 & 수정 시 법정대리인 정보 확인 */
//    @Override
//    public Map<String, String> chkAgentInfo(String cstmrName, NiceResDto childniceResDto, NiceLogDto agentNiceLogRtn,UserSessionDto userSession) {
//
//        Map<String,String> vldReslt= new HashMap<>();
//
//        if(childniceResDto == null){
//            vldReslt.put("RESULT_CODE", "AUTH04");
//            vldReslt.put("RESULT_DESC", NICE_CERT_EXCEPTION_INSR);
//            return vldReslt;
//        }
//
//        // 화면에 입력한 법정대리인 이름과 인증한 법정대리인 이름 비교
//        String niceUserNm= agentNiceLogRtn.getName().toUpperCase().replaceAll(" ", "");
//        String inputCstmrName= StringUtil.NVL(cstmrName, "").toUpperCase().replaceAll(" ", "");
//
//        if(!niceUserNm.equals(inputCstmrName)){
//            vldReslt.put("RESULT_CODE", "AUTH05");
//            vldReslt.put("RESULT_DESC", "법정대리인 정보가 일치하지 않습니다");
//            return vldReslt;
//        }
//
//
//        // 어린이 인증값(이름,핸드폰번호)으로 계약정보 조회
//        JuoSubInfoDto juoSubInfoRtn = mypageUserService.selectJuoSubInfo(childniceResDto.getName(),childniceResDto.getsMobileNo());
//
//        String userDivision = "";
//        if(userSession != null) userDivision = userSession.getUserDivision();
//
//        // 계약정보가 null 인데 정회원인 경우 (회원정보 수정)
//        if(juoSubInfoRtn == null && "01".equals(userDivision)) {
//            // 세션ID로 정회원목록 조회
//            List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
//            if(cntrList == null || cntrList.size() < 0){
//                vldReslt.put("RESULT_CODE", "AUTH07");
//                vldReslt.put("RESULT_DESC", "정회원 정보가 존재하지 않습니다. 고객센터에 문의 하시기 바랍니다.");
//                return vldReslt;
//            }
//            // 정회원 정보로 계약정보 재 조회
//            juoSubInfoRtn = mypageUserService.selectJuoSubInfo(cntrList.get(0).getUserName(), cntrList.get(0).getCntrMobileNo());
//            if(juoSubInfoRtn == null) {
//                vldReslt.put("RESULT_CODE", "AUTH08");
//                vldReslt.put("RESULT_DESC", "계약정보가 존재하지 않습니다. 고객센터에 문의 하시기 바랍니다. ");
//                return vldReslt;
//            }
//        }
//        // 기변인 경우 계약정보 없으면 실패
//        JuoSubInfoDto changeAut = SessionUtils.getChangeAutCookieBean();
//        if (changeAut != null && !StringUtils.isBlank(changeAut.getContractNum()) && juoSubInfoRtn == null) {
//            vldReslt.put("RESULT_CODE", "AUTH08");
//            vldReslt.put("RESULT_DESC", "입력하신 정보는 kt M모바일에 가입된 정보가 아니거나 사용중인 상태가 아닙니다 <br> 확인 후 다시 입력 해 주세요.");
//            return vldReslt;
//        }
//
//        // 계약정보존재
//        if(juoSubInfoRtn != null && ("I".equals(juoSubInfoRtn.getCustomerType()) || "O".equals(juoSubInfoRtn.getCustomerType()))){
//            String dbLegalCi = juoSubInfoRtn.getLegalCi(); //DB 법정대리인 Ci값
//            // 기변이면 dbLegalCi가 존재하면 비교 / 비어있으면 비교하지않고 통과
//            if (changeAut != null && !StringUtils.isBlank(changeAut.getContractNum())) {
//                if (!StringUtils.isBlank(dbLegalCi) && !agentNiceLogRtn.getConnInfo().equals(dbLegalCi)) {
//                    vldReslt.put("RESULT_CODE", "AUTH06");
//                    vldReslt.put("RESULT_DESC", "미성년자의 경우 기기변경 휴대폰 번호를 개통할 당시의 법정대리인 정보로 본인인증이 필요합니다. <br><br>" +
//                            "법정대리인 정보 확인 후 다시 시도를 부탁드리며, 불가할 경우 고객센터(1899-5000)으로 문의를 부탁드립니다.");
//                    return vldReslt;
//                } else {
//                    //dbLegalCi값이 비어있을경우
//                    if(StringUtils.isBlank(dbLegalCi)) {
//                        vldReslt.put("RESULT_CODE", "AUTH06");
//                        vldReslt.put("RESULT_DESC", "법정대리인 정보 확인까지 몇분정도 걸릴수 있습니다. 잠시 후 다시 시도해주세요.");
//                        return vldReslt;
//                    }
//                }
//            } else {
//                //dbLegalCi값이 비어있을경우
//                if(StringUtils.isBlank(dbLegalCi)) {
//                    vldReslt.put("RESULT_CODE", "AUTH06");
//                    vldReslt.put("RESULT_DESC", "법정대리인 정보 확인까지 몇분정도 걸릴수 있습니다. 잠시 후 다시 시도해주세요.");
//                    return vldReslt;
//                }
//                // 계약정보의 법정대리인 Ci값과 인증한 Ci값이 다른경우
//                if(!agentNiceLogRtn.getConnInfo().equals(dbLegalCi)) {
//                    vldReslt.put("RESULT_CODE", "AUTH06");
//                    vldReslt.put("RESULT_DESC", "부 혹은 모와 동일한 법정대리인의 휴대폰인증을 해야 합니다.");
//                    return vldReslt;
//                }
//            }
//        } else {
//            /**
//             * 기변일때 법정 대리인 나이 확인 하지 않음...
//             * 회원 가입 / 수정 할때 법정 대리인 정보 나이 확인 처리
//             */
//            //법정대리인의 나이 확인
//            int agentAge = 0;
//            try{
//                String agentBirthDate = agentNiceLogRtn.getBirthDate().length() > 8 ? EncryptUtil.ace256Dec(agentNiceLogRtn.getBirthDate()) : agentNiceLogRtn.getBirthDate();
//                agentAge = NmcpServiceUtils.getBirthDateToAge(agentBirthDate, new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
//            } catch (CryptoException e) {
//                logger.error("Exception e : {}", e.getMessage());
//            }
//
//            if(agentAge < 19) {
//                vldReslt.put("RESULT_CODE", "AUTH05");
//                vldReslt.put("RESULT_DESC", "법정대리인의 나이는 만 19세 이상이어야 합니다.");
//                return vldReslt;
//            }
//        }
//
//
//
//
//        vldReslt.put("RESULT_CODE", AJAX_SUCCESS);
//        vldReslt.put("RESULT_DESC", "성공");
//        return vldReslt;
//    }
//
//    @Override
//    public Map<String, String> chkReplaceUsimSmsResInfo(NiceLogDto niceLogDto, NiceLogDto niceLogRtn) {
//
//        Map<String,String> vldReslt= new HashMap<>();
//
//        String inputName = StringUtil.NVL(niceLogDto.getName(), "").replaceAll(" ", "");
//        String authName = StringUtil.NVL(niceLogRtn.getName(), "").replaceAll(" ", "");
//
//        if(!inputName.equalsIgnoreCase(authName)){
//            vldReslt.put("RESULT_CODE", "F_AUTH");
//            vldReslt.put("RESULT_MSG", NICE_CERT_EXCEPTION);
//            return vldReslt;
//        }
//
//        // 인가된 사용자 체크 (로그인)
//        Map<String, String> certMap = mypageService.checkAuthUser(inputName, null);
//        if(!"0000".equals(certMap.get("returnCode"))){
//            vldReslt.put("RESULT_CODE", "F_LOGIN");
//            vldReslt.put("RESULT_MSG", "회원정보와 본인인증 정보가 일치하지 않습니다");
//            return vldReslt;
//        }
//
//        // 인증 STEP 체크
//        String[] certKey = {"urlType", "name", "ncType"};
//        String[] certValue= {"chkSmsAuth", authName, "0"};
//        vldReslt = certService.vdlCertInfo("D", certKey, certValue);
//
//        if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
//            vldReslt.put("RESULT_CODE", "F_STEP");
//            vldReslt.put("RESULT_MSG", vldReslt.get("RESULT_DESC"));
//            return vldReslt;
//        }
//
//        // 미성년자 (만 19세 미만) 제한
//        int age = NmcpServiceUtils.getBirthDateToAge(niceLogRtn.getBirthDateDec(), new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
//        if (age < 19) {
//            vldReslt.put("RESULT_CODE", "F_AGE");
//            vldReslt.put("RESULT_MSG", "교체 유심 신청은 만 19세 이상 고객만 가능합니다.");
//            return vldReslt;
//        }
//
//        vldReslt.put("RESULT_CODE", AJAX_SUCCESS);
//        vldReslt.put("RESULT_MSG", "성공");
//        return vldReslt;
//    }
//
//    @Override
//    public Map<String, String> chkReplaceUsimSelfAuthInfo(NiceLogDto niceLogDto, NiceLogDto niceLogRtn) {
//
//        Map<String,String> vldReslt= new HashMap<>();
//
//        if(StringUtil.isEmpty(niceLogDto.getName()) || StringUtil.isEmpty(niceLogDto.getsMobileNo())){
//            throw new McpCommonJsonException("F_AUTH", BIDING_EXCEPTION);
//        }
//
//        String inputName = StringUtil.NVL(niceLogDto.getName(), "").replaceAll(" ", "");
//        String authName = StringUtil.NVL(niceLogRtn.getName(), "").replaceAll(" ", "");
//
//        if(!inputName.equalsIgnoreCase(authName)){
//            vldReslt.put("RESULT_CODE", "F_AUTH");
//            vldReslt.put("RESULT_MSG", NICE_CERT_EXCEPTION);
//            return vldReslt;
//        }
//
//        // 교체유심 셀프변경 가능 조건
//        // 1) 사용중 또는 정지고객
//        // 2) 개인고객
//        // 3) 19세 이상 성인
//
//        McpUserCntrMngDto mcpUserCntrMngDto = new McpUserCntrMngDto();
//        mcpUserCntrMngDto.setSubLinkName(authName);
//        mcpUserCntrMngDto.setCntrMobileNo(niceLogDto.getsMobileNo());
//        McpUserCntrMngDto contractInfo = mypageService.selectCntrListNoLogin(mcpUserCntrMngDto);
//
//        if(contractInfo == null){
//            vldReslt.put("RESULT_CODE", "F_MEMBER");
//            vldReslt.put("RESULT_MSG", "kt M모바일에 가입 정보 확인이 불가합니다.");
//            return vldReslt;
//        }
//
//        if(!"I".equals(contractInfo.getCustomerType())){
//            vldReslt.put("RESULT_CODE", "F_MEMBER");
//            vldReslt.put("RESULT_MSG", "유심 셀프변경은 개인 고객만 가능합니다.");
//            return vldReslt;
//        }
//
//        if(!"PO".equals(contractInfo.getPppo())){
//            vldReslt.put("RESULT_CODE", "F_MEMBER");
//            vldReslt.put("RESULT_MSG", "유심 셀프변경은 후불 고객만 가능합니다.");
//            return vldReslt;
//        }
//
//        int age = NmcpServiceUtils.getAge(contractInfo.getUnUserSSn(), new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
//        if (age < 19) {
//            vldReslt.put("RESULT_CODE", "F_MEMBER");
//            vldReslt.put("RESULT_MSG", "유심 셀프변경은 만 19세 이상 성인 고객만 가능합니다.");
//            return vldReslt;
//        }
//
//        // STEP 저장
//        String[] certKey= {"urlType", "moduType", "name", "birthDate", "connInfo", "reqSeq", "resSeq"};
//        String[] certValue= {"simpleAuth", CUST_AUTH, niceLogRtn.getName(), niceLogRtn.getBirthDateDec(), niceLogRtn.getConnInfo(), niceLogRtn.getReqSeq(), niceLogRtn.getResSeq()};
//        certService.vdlCertInfo("C", certKey, certValue);
//
//        certKey= new String[]{"urlType", "name", "mobileNo"};
//        certValue= new String[]{"baseMemSimpleAuth", niceLogDto.getName(), niceLogDto.getsMobileNo()};
//        certService.vdlCertInfo("C", certKey, certValue);
//
//        vldReslt.put("RESULT_CODE", AJAX_SUCCESS);
//        vldReslt.put("RESULT_MSG", "확인 성공");
//        return vldReslt;
//    }
//}
