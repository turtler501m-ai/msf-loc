package com.ktmmobile.msf.system.common.service;

import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;

import java.net.SocketTimeoutException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.ktmmobile.msf.system.common.constants.Constants;
import com.ktmmobile.msf.form.servicechange.dao.MypageUserDao;
import com.ktmmobile.msf.form.servicechange.dto.McpUserCntrMngDto;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ktmmobile.msf.system.common.dao.LoginDao;
import com.ktmmobile.msf.system.common.dto.AuthSmsDto;
import com.ktmmobile.msf.system.common.dto.McpIpStatisticDto;
import com.ktmmobile.msf.system.common.dto.McpUserDarkwebDto;
import com.ktmmobile.msf.system.common.dto.NiceResDto;
import com.ktmmobile.msf.system.common.dto.PersonalPolicyDto;
import com.ktmmobile.msf.system.common.dto.RoleMenuDto;
import com.ktmmobile.msf.system.common.dto.UserSessionDto;
import com.ktmmobile.msf.system.common.dto.db.NmcpAutoLoginTxnDto;
import com.ktmmobile.msf.system.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.msf.system.common.exception.McpCommonException;
import com.ktmmobile.msf.system.common.exception.McpErropPageException;
import com.ktmmobile.msf.system.common.util.CommonHttpClient;
import com.ktmmobile.msf.system.common.util.DateTimeUtil;
import com.ktmmobile.msf.system.common.util.EncryptUtil;
import com.ktmmobile.msf.system.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.system.common.util.SessionUtils;
import com.ktmmobile.msf.system.common.util.StringUtil;
import org.springframework.web.client.RestTemplate;

@Service
public class LoginSvcImpl implements LoginSvc {

    private static final Logger logger = LoggerFactory.getLogger(LoginSvcImpl.class);

    @Value("${api.interface.server}")
    private String apiInterfaceServer;


    @Autowired
    LoginDao loginDao;

    @Autowired
    MypageUserDao mypageUserDao;

    @Autowired
    SmsSvc smsSvc;

    @Autowired
    IpStatisticService ipstatisticService;

    @Value("${SERVER_NAME}")
    private String serverName;

    @Value("${recaptcha.sectKey}")
    private String recaptchaSectKey;

    @Value("${inf.url}")
    private String infUrl;

    @Value("${ext.url}")
    private String extUrl;

    @Value("${sms.callcenter}")
    private String callCenter;


    @Override
    public int addBirthGenderAjax(UserSessionDto userSessionDto) {
        return loginDao.addBirthGenderAjax(userSessionDto);
    }

    @Override
    public UserSessionDto loginCheckFailCount(UserSessionDto userSessionDto ) {
        return loginCheckFailCount(userSessionDto , true) ;
    }
    @Override
    public UserSessionDto loginCheckFailCount(UserSessionDto userSessionDto , Boolean isTrace) {

        UserSessionDto sessionDto = new UserSessionDto();

        // IP 셋팅
        String accessIp = ipstatisticService.getClientIp();

        // 로그인 제한 시간 공통코드에서 찾아오기
        int limitTime = 0;
        limitTime = loginDao.limitTime() ;

        userSessionDto.setLimitTime(limitTime);
        userSessionDto.setAccessIp(accessIp);
        userSessionDto.setLimitType(null);

        //신규 테이블 읽어서 해당 아이디가 존재하면 특정 시간이 될때까지 차단 하는 로직만 추가
        int intLimitCnt = loginDao.limitCnt(userSessionDto) ;

        if(intLimitCnt > 0) {

            McpIpStatisticDto mcpIpStatisticDto =  new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("LOGIN_EXECUTE");
            mcpIpStatisticDto.setUserid(userSessionDto.getUserId());
            mcpIpStatisticDto.setPrcsSbst("userId[" +userSessionDto.getUserId() +"]");
            mcpIpStatisticDto.setTrtmRsltSmst("LIMIT EXIT");
            mcpIpStatisticDto.setLoginDivCd("ID");

            ipstatisticService.insertAccessTrace(mcpIpStatisticDto);

            sessionDto.setStatus("LIMIT");
            sessionDto.setLoginFailCount(6);
            return sessionDto;
        }

        //TO_DO checkLoginAttack
        /*
         * 동일 시:분:초에 3회 이상 접속시도 실패 시 접속 차단
         */
        if ("LOCAL".equals(serverName)) {
            userSessionDto.setLoginFailCount(10); //개발계에서 20초로 설정
        } else {
            userSessionDto.setLoginFailCount(1); //1초로 설정
        }

        int checkLoginAttakInt = loginDao.checkLoginAttack(userSessionDto) ;

        UserSessionDto rtnUserSessionDto = loginDao.getUserInfo(userSessionDto);//mcp_user

        if (rtnUserSessionDto != null)  {

            String pramPassWord = EncryptUtil.sha512HexEnc(userSessionDto.getPassWord());
            if (pramPassWord.equals(rtnUserSessionDto.getPassWord())) {

                if (!rtnUserSessionDto.isLoginFailCntOver()) {
                    //로그인 성공
                    rtnUserSessionDto.setLogin(true);

                    if (isTrace) {
                        //LOGIN_FAIL_COUNT 0 으로 초기화
                        rtnUserSessionDto.setLoginFailCount(0);
                        loginDao.updateUserSet(rtnUserSessionDto);

                        //SRM19093025436  무작위 대입공격 대응체계 강화  ACCESS_TRACE 저장 처리 execute
                        McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
                        mcpIpStatisticDto.setPrcsMdlInd("LOGIN_EXECUTE");
                        mcpIpStatisticDto.setUserid(userSessionDto.getUserId());
                        mcpIpStatisticDto.setPrcsSbst("userId[" + userSessionDto.getUserId() + "]");
                        mcpIpStatisticDto.setTrtmRsltSmst("SUCCEED");
                        mcpIpStatisticDto.setLoginDivCd("ID");

                        ipstatisticService.insertAccessTrace(mcpIpStatisticDto);

                    }

                    String userDivision = rtnUserSessionDto.getUserDivision();
                    if (Constants.DIVISION_CODE_LEGALLY_MEMBER.equals(userDivision)) {
                        /***정회원 정보 현행화 처리
                         * MSP_JUO_SUB_INFO@DL_MSP
                         * 동일한 계약정보
                         * 동일한 사용자 아이디 CustomerId
                         * 전화번호만.. 상이 할 경우
                         * 정보를 업데이트 처리
                         * */
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("userId", userSessionDto.getUserId());
                        RestTemplate restTemplate = new RestTemplate();
                        McpUserCntrMngDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/mypage/cntrMngList", params, McpUserCntrMngDto[].class);

                        if (resultList != null && resultList.length > 0) {
                            Arrays.stream(resultList)
                                    .filter(Objects::nonNull)
                                    .forEach(cntrMngObj -> {
                                        boolean isSameCustomer = Objects.equals(cntrMngObj.getCustomerId(), cntrMngObj.getCustId());
                                        boolean isDiffSubNo     = !Objects.equals(cntrMngObj.getCntrMobileNo(), cntrMngObj.getSubscriberNo());

                                        //전화번호 상이
                                        if (isSameCustomer && isDiffSubNo) {
                                            McpIpStatisticDto mcpIpStatistic =  new McpIpStatisticDto();
                                            mcpIpStatistic.setPrcsMdlInd("CHANGE_CNTR_MNG");
                                            mcpIpStatistic.setUserid(userSessionDto.getUserId());
                                            mcpIpStatistic.setPrcsSbst("CntrMobileNo[" +cntrMngObj.getCntrMobileNo() +"]SubscriberNo["+cntrMngObj.getSubscriberNo()+"]");
                                            mcpIpStatistic.setTrtmRsltSmst("SUCCEED");
                                            ipstatisticService.insertAdminAccessTrace(mcpIpStatistic);

                                            // DB 정보 업데이트 처리
                                            McpUserCntrMngDto upUserCnt = new McpUserCntrMngDto();
                                            upUserCnt.setCustId(cntrMngObj.getCustomerId());
                                            upUserCnt.setSvcCntrNo(cntrMngObj.getContractNum());
                                            upUserCnt.setUserid(userSessionDto.getUserId());

                                            upUserCnt.setSubscriberNo(cntrMngObj.getSubscriberNo()); //M 전산 전화 번호
                                            mypageUserDao.updateUserCntrMng(upUserCnt);
                                        }
                                    });
                        }
                    }

                } else if (rtnUserSessionDto.isLoginFailAttack()) {
                    //무차별 대입 공격 (Brute Forece Attack) history
                    rtnUserSessionDto.setLogin(false);
                    //SRM19093025436  무작위 대입공격 대응체계 강화  ACCESS_TRACE 저장 처리 execute
                    McpIpStatisticDto mcpIpStatisticDto =  new McpIpStatisticDto();
                    mcpIpStatisticDto.setPrcsMdlInd("LOGIN_EXECUTE");
                    mcpIpStatisticDto.setUserid(userSessionDto.getUserId());
                    mcpIpStatisticDto.setPrcsSbst("userId[" +userSessionDto.getUserId() +"]");
                    mcpIpStatisticDto.setTrtmRsltSmst("FAIL_ATTACK_HISTORY");
                    mcpIpStatisticDto.setLoginDivCd("ID");

                    ipstatisticService.insertAccessTrace(mcpIpStatisticDto);
                } else {
                    //10회 제한
                    rtnUserSessionDto.setLogin(false);

                    //SRM19093025436  무작위 대입공격 대응체계 강화  ACCESS_TRACE 저장 처리 execute
                    McpIpStatisticDto mcpIpStatisticDto =  new McpIpStatisticDto();
                    mcpIpStatisticDto.setPrcsMdlInd("LOGIN_EXECUTE");
                    mcpIpStatisticDto.setUserid(userSessionDto.getUserId());
                    mcpIpStatisticDto.setPrcsSbst("userId[" +userSessionDto.getUserId() +"]");
                    mcpIpStatisticDto.setTrtmRsltSmst("FAIL_LIMIT_10");
                    mcpIpStatisticDto.setLoginDivCd("ID");

                    ipstatisticService.insertAccessTrace(mcpIpStatisticDto);

                }
            } else {

                if (2 <= checkLoginAttakInt ) {
                    /*
                     * 동일 시:분:초에 3회 이상 접속시도 실패 시 접속 차단
                     */
                    userSessionDto.setLoginFailCount(999);
                    userSessionDto.setLogin(false);
                    loginDao.updateUserSet(userSessionDto);

                    McpIpStatisticDto mcpIpStatisticDto =  new McpIpStatisticDto();
                    mcpIpStatisticDto.setPrcsMdlInd("LOGIN_EXECUTE");
                    mcpIpStatisticDto.setUserid(userSessionDto.getUserId());
                    mcpIpStatisticDto.setPrcsSbst("userId[" +userSessionDto.getUserId() +"]");
                    mcpIpStatisticDto.setTrtmRsltSmst("FAIL_ATTACK_LOCK");
                    mcpIpStatisticDto.setLoginDivCd("ID");

                    ipstatisticService.insertAccessTrace(mcpIpStatisticDto);
                    return userSessionDto;

                } else {

                    if (999 > rtnUserSessionDto.getLoginFailCount()) {
                        rtnUserSessionDto.setLoginFailCount(rtnUserSessionDto.getLoginFailCount()+1);
                        rtnUserSessionDto.setLogin(false);
                        loginDao.updateUserSet(rtnUserSessionDto);
                    }

                    //SRM19093025436  무작위 대입공격 대응체계 강화  ACCESS_TRACE 저장 처리 execute
                    McpIpStatisticDto mcpIpStatisticDto =  new McpIpStatisticDto();
                    mcpIpStatisticDto.setPrcsMdlInd("LOGIN_EXECUTE");
                    mcpIpStatisticDto.setUserid(userSessionDto.getUserId());
                    mcpIpStatisticDto.setPrcsSbst("userId[" +userSessionDto.getUserId() +"]");
                    mcpIpStatisticDto.setTrtmRsltSmst("FAIL");
                    mcpIpStatisticDto.setLoginDivCd("ID");

                    ipstatisticService.insertAccessTrace(mcpIpStatisticDto);
                }
            }
        } else {

            // 고객정보가 없을 경우 초당 2회 이상 로그인 시도 했을때 차단하는 로직 추가 2023-03-21 BY 장익준
            if (2 <= checkLoginAttakInt ) {
                userSessionDto.setAccessIp(accessIp);
                // USERID가 존재 하지는 않지만 다량접속 시도를 막기 위한 MNCP_IP_LIMIT 테이블에 INSERT 한다.
                loginDao.insertIpLimit(userSessionDto);
            }

            McpIpStatisticDto mcpIpStatisticDto =  new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("LOGIN_EXECUTE");
            mcpIpStatisticDto.setUserid(userSessionDto.getUserId());
            mcpIpStatisticDto.setPrcsSbst("userId[" +userSessionDto.getUserId() +"]");
            mcpIpStatisticDto.setTrtmRsltSmst("FAIL_ID_NULL");
            mcpIpStatisticDto.setLoginDivCd("ID");

            ipstatisticService.insertAccessTrace(mcpIpStatisticDto);
        }


        return rtnUserSessionDto;
    }

    @Override
    public UserSessionDto loginCheckAppAutoLogin(UserSessionDto userSessionDto) {

        UserSessionDto rtnUserSessionDto = null;

        NmcpAutoLoginTxnDto nmcpAutoLoginTxnDto = loginDao.getLoginAutoLogin(userSessionDto);

        if(nmcpAutoLoginTxnDto == null) {
            logger.debug("nmcpAutoLoginTxnDto is null");
        } else {

            userSessionDto.setUserId(nmcpAutoLoginTxnDto.getUserId());

            rtnUserSessionDto = loginDao.getUserInfo(userSessionDto);
            if (rtnUserSessionDto != null)  {
                //로그인 성공
                rtnUserSessionDto.setLogin(true);
                //SRM19093025436  무작위 대입공격 대응체계 강화  ACCESS_TRACE 저장 처리 execute
                McpIpStatisticDto mcpIpStatisticDto =  new McpIpStatisticDto();
                mcpIpStatisticDto.setPrcsMdlInd("LOGIN_EXECUTE");
                mcpIpStatisticDto.setUserid(userSessionDto.getUserId());
                mcpIpStatisticDto.setPrcsSbst("userId[" +userSessionDto.getUserId() +"]");
                mcpIpStatisticDto.setTrtmRsltSmst("SUCCEED");
                mcpIpStatisticDto.setLoginDivCd(nmcpAutoLoginTxnDto.getLoginDivCd());
                mcpIpStatisticDto.setLoginSeq(nmcpAutoLoginTxnDto.getAutoLoginSeq()+"");
                ipstatisticService.insertAccessTrace(mcpIpStatisticDto);

            } else {
                McpIpStatisticDto mcpIpStatisticDto =  new McpIpStatisticDto();
                mcpIpStatisticDto.setPrcsMdlInd("LOGIN_EXECUTE");
                mcpIpStatisticDto.setUserid(userSessionDto.getUserId());
                mcpIpStatisticDto.setPrcsSbst("userId[" +userSessionDto.getUserId() +"]");
                mcpIpStatisticDto.setTrtmRsltSmst("FAIL_AUTOLOGIN");

                ipstatisticService.insertAccessTrace(mcpIpStatisticDto);
            }
        }

        return rtnUserSessionDto;
    }

    @Override
    public UserSessionDto loginCheckAppAutoLoginDormancy(UserSessionDto userSessionDto) {

        UserSessionDto rtnUserSessionDto = null;

        NmcpAutoLoginTxnDto nmcpAutoLoginTxnDto = loginDao.getLoginAutoLogin(userSessionDto);

        if(nmcpAutoLoginTxnDto == null) {
            logger.debug("nmcpAutoLoginTxnDto is null");
        } else {
            userSessionDto.setUserId(nmcpAutoLoginTxnDto.getUserId());
            rtnUserSessionDto = loginDao.dormancyLoginProcess(userSessionDto);
        }

        return rtnUserSessionDto;
    }

    @Override
    public NmcpAutoLoginTxnDto insertAutoLoginTxn(UserSessionDto userSessionDto) {
        logger.debug("자동로그인 신규 토큰 생성");
        NmcpAutoLoginTxnDto nmcpAutoLoginTxnDto = new NmcpAutoLoginTxnDto();

        nmcpAutoLoginTxnDto.setUserId(userSessionDto.getUserId());

        //token 생성
        String token = RandomStringUtils.randomAlphanumeric(16)+userSessionDto.getMobileNo()+DateTimeUtil.getFormatString("yyyyMMddHHmmss");
        nmcpAutoLoginTxnDto.setToken(EncryptUtil.sha512HexEnc(token));

        //token 유효기간 연장 - 14일(2주)
        Date nextTokenValidDate = DateTimeUtil.getDateToCurrent(14);
        nmcpAutoLoginTxnDto.setTokenValidPeriod(DateTimeUtil.changeFormat(nextTokenValidDate, "yyyyMMddHHmmss"));
        nmcpAutoLoginTxnDto.setPlatformCd(NmcpServiceUtils.getPlatFormCd());
        nmcpAutoLoginTxnDto.setLoginDivCd("APP");
        if(!"".equals(StringUtil.NVL(userSessionDto.getLoginDivCd(), ""))) {
            nmcpAutoLoginTxnDto.setLoginDivCd(userSessionDto.getLoginDivCd());
        }
        loginDao.insertAutoLoginTxn(nmcpAutoLoginTxnDto);

        return nmcpAutoLoginTxnDto;
    }

    @Override
    public NmcpAutoLoginTxnDto updateAutoLoginTxn(UserSessionDto userSessionDto) {
        logger.debug("자동로그인 토큰 update");
        NmcpAutoLoginTxnDto nmcpAutoLoginTxnDto = new NmcpAutoLoginTxnDto();
        //nmcpAutoLoginTxnDto.setAutoLoginSeq(userSessionDto.getAutoLoginSeq());

        //token 생성
        String token = RandomStringUtils.randomAlphanumeric(16)+userSessionDto.getMobileNo()+DateTimeUtil.getFormatString("yyyyMMddHHmmss");
        nmcpAutoLoginTxnDto.setToken(EncryptUtil.sha512HexEnc(token));

        //token 유효기간 연장 - 14일(2주)
        Date nextTokenValidDate = DateTimeUtil.getDateToCurrent(14);
        nmcpAutoLoginTxnDto.setTokenValidPeriod(DateTimeUtil.changeFormat(nextTokenValidDate, "yyyyMMddHHmmss"));
        nmcpAutoLoginTxnDto.setPlatformCd(NmcpServiceUtils.getPlatFormCd());
        nmcpAutoLoginTxnDto.setLoginDivCd(userSessionDto.getLoginDivCd());
        nmcpAutoLoginTxnDto.setUserId(userSessionDto.getUserId());

        int retVal = loginDao.updateAutoLoginTxn(nmcpAutoLoginTxnDto);

        if(retVal > 0) {
            logger.debug("updateAutoLoginTxn new token:{}", nmcpAutoLoginTxnDto.getToken());
        } else {
            logger.debug("updateAutoLoginTxn same token:{}", userSessionDto.getToken());
        }

        return nmcpAutoLoginTxnDto;
    }

    @Override
    public UserSessionDto loginProcess(UserSessionDto userSessionDto) {
        userSessionDto.setPassWord(EncryptUtil.sha512HexEnc(userSessionDto.getPassWord()));
        return loginDao.loginProcess(userSessionDto);
    }

    @Override
    public List<RoleMenuDto> getRoleMenuList(String roleCode) {
        // TODO Auto-generated method stub
        return loginDao.getRoleMenuList(roleCode);
    }

    @Override
    public Boolean getUserAuthSms(UserSessionDto userSessionDto) {
        userSessionDto.setPassWord(EncryptUtil.sha512HexEnc(userSessionDto.getPassWord()));
        UserSessionDto rtnObj = null;
        rtnObj = loginDao.getUserAuthSms(userSessionDto) ;

        if (rtnObj != null) {
            //6자리 인증번호 생성
            StringBuffer authSmsNo = new StringBuffer();
            Random objRandom;
            try {
                objRandom = SecureRandom.getInstance("SHA1PRNG");
            } catch (NoSuchAlgorithmException e) {
                throw new McpErropPageException(COMMON_EXCEPTION);
            }

            for(int i=0 ; i<6 ; i++) {
                authSmsNo.append(objRandom.nextInt(10)) ;
            }

            //SMS 인증번호 발송
            String message = "(kt M모바일 관리자 인증번호)"+authSmsNo;
            smsSvc.sendSmsForAuth(rtnObj.getMobileNo() ,false,message);
            rtnObj.setAuthSmsNo(authSmsNo.toString());

            //사용자 정보 session 저장
            SessionUtils.saveUserSession(rtnObj);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public UserSessionDto checkNiceCertAjax(UserSessionDto searchVO) {
        return loginDao.checkNiceCertAjax(searchVO);
    }


    @Override
    public UserSessionDto checkNiceCertDormantAjax(UserSessionDto searchVO) {
        return loginDao.checkNiceCertDormantAjax(searchVO);
    }

    @Override
    public UserSessionDto checkIdNameAjax(UserSessionDto searchVO) {
        return loginDao.checkIdNameAjax(searchVO);
    }

    @Override
    public UserSessionDto checkIdNameDormantAjax(UserSessionDto searchVO) {
        return loginDao.checkIdNameDormantAjax(searchVO);
    }

    /*아이디찾기 sms 발송*/
    @Override
    public boolean sendAuthSms(AuthSmsDto authSmsDto) {
        boolean result = true;

        String userId = authSmsDto.getAuthNum();
        String message = authSmsDto.getMessage();

        try {
            //smsSvc.sendLms("[kt M모바일] 아이디 안내", authSmsDto.getPhoneNum(), message);
            smsSvc.sendLms("[kt M모바일] 아이디 안내", authSmsDto.getPhoneNum(), message,callCenter,"sendIdSmsAjax",userId);
        } catch(Exception e) {
            return false;
        }

        authSmsDto.setAuthNum(userId);
        String today = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
        authSmsDto.setStartDate(today);
        //관리자 정보 session 저장
        SessionUtils.setAuthSmsSession(authSmsDto);

        return result;
    }

    @Override
    public int tmpPasswordUpdate(UserSessionDto userSessionDto) {

        Random numGen = null;
        try {
            numGen = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            throw new McpCommonException("NoSuchAlgorithmException");
        }

        StringBuilder password = new StringBuilder("");

        for(int i = 0; i < 8; i++){
            char upperStr = (char)(numGen.nextDouble() * 26 + 65);
            if(i%2 == 0){
                password.append((int)(numGen.nextDouble() * 10)) ;
            }else{
                password.append(upperStr) ;
            }
        }

        userSessionDto.setPassWord(EncryptUtil.sha512HexEnc(password.toString()));
        userSessionDto.setTmpPasswrod(password.toString());
        return loginDao.tmpPasswordUpdate(userSessionDto);
    }

    @Override
    public int updateHit(String userId) {
        return loginDao.updateHit(userId);
    }

    @Override
    public PersonalPolicyDto personalPolicySelect() {
        return loginDao.personalPolicySelect();
    }

    @Override
    public int updateNewPassword(HashMap<String, String> map) {
        return loginDao.updateNewPassword(map);
    }

    @Override
    public int updateNewPasswordDormant(HashMap<String, String> map) {
        return loginDao.updateNewPasswordDormant(map);
    }

    @Override
    public int insertDormantToMcpUser(NiceResDto niceResDto) {
        return loginDao.insertDormantToMcpUser(niceResDto);
    }

    @Override
    public int updateDormantUserChg(String dupInfo) {
        return loginDao.updateDormantUserChg(dupInfo);

    }

    @Override
    public UserSessionDto dormancyLoginProcess(UserSessionDto userSessionDto) {
        return loginDao.dormancyLoginProcess(userSessionDto);
    }

    @Override
    public List<McpUserDarkwebDto> getMcpUserDarkwebList(String userId) {
        return loginDao.getMcpUserDarkwebList(userId);
    }

    @Override
    public int updateMcpUserDarkweb(String userId) {
        return loginDao.updateMcpUserDarkweb(userId);
    }

    @Override
    public int updatePwChgInfoNoShow(String userId) {
        return loginDao.updatePwChgInfoNoShow(userId);
    }

    @Override
    public NmcpAutoLoginTxnDto getLoginAutoLogin(UserSessionDto userSessionDto) {
        return loginDao.getLoginAutoLogin(userSessionDto);
    }

    @Override
    public int findPinToIdCnt(UserSessionDto searchVO) {
        return loginDao.findPinToIdCnt(searchVO);
    }

    @Override
    public int resetDormancy(UserSessionDto searchVO) {
        return loginDao.resetDormancy(searchVO);
    }

    @Override
    public int limitCnt(UserSessionDto searchVO) {
        if(StringUtil.isEmpty(searchVO.getAccessIp())){
            searchVO.setAccessIp(ipstatisticService.getClientIp());
        }

        return loginDao.limitCnt(searchVO);
    }

    @Override
    public int insertIpLimit(UserSessionDto searchVO) {
        if(StringUtil.isEmpty(searchVO.getAccessIp())){
            searchVO.setAccessIp(ipstatisticService.getClientIp());
        }

        return loginDao.insertIpLimit(searchVO);
    }

    @Override
    public int limitTime() {
        return loginDao.limitTime();
    }

    /** recaptcha 결과 확인 */
    @Override
    public Map<String, String> getRecaptchaResult(UserSessionDto userSessionDto) {

        /*
            [RESULT_CODE]
            1. 0000 : 성공
            2. 0001 : 리캡챠 토큰 누락
            3. 0002 : 통신 오류
            4. 0003 : 리캡챠 점수 연동 결과 false
            5. 0004 : 리캡챠 점수 연동 결과 true + 점수가 기준점수(공통코드)보다 같거나 작은경우
        */

        Map<String, String> rtnMap= new HashMap<>();

        // 1. 토큰 유효하지 않은 경우
        if(StringUtil.isEmpty(userSessionDto.getRecaptchaToken())){
            rtnMap.put("RESULT_CODE", "0001");
            rtnMap.put("RESULT_MSG", "페이지 새로고침 후 재시도 부탁드립니다.");
            return rtnMap;
        }

        // 로컬이 경우 prx 안태우고, 간단성공처리
        if("LOCAL".equals(serverName)){
            rtnMap.put("RESULT_CODE", "0000");
            SessionUtils.saveRecaptchaSession("Y");  // 세션 저장
            return rtnMap;
        }


        // 2. recaptcha 결과 확인 (PRX 호출)
        String accessIp= ipstatisticService.getClientIp();
        CommonHttpClient client = new CommonHttpClient(extUrl+"/getRecaptchaResult.do");
        String result= null;
        NameValuePair[] data = {
                new NameValuePair("secret", recaptchaSectKey),  // 시크릿키
                new NameValuePair("response", userSessionDto.getRecaptchaToken()), // 사용자 응답 토큰
                new NameValuePair("remoteip", accessIp), // 사용자 ip
        };

        try {
            result = client.postUtf8(data);
            // 샘플 : {"success": false,  "error-codes": ["missing-input-secret"]}
            // 샘플 : {"success": true,  "challenge_ts": "2023-11-24T11:00:16Z",  "hostname": "dmcpstg.ktmmobile.com",  "score": 0.9,  "action": "login"}

            if(StringUtil.isEmpty(result)){
                throw new SocketTimeoutException();
            }

            // 3. result 변환
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse(result);

            String success= (jsonObject.has("success")) ?  jsonObject.get("success").getAsString() : "false";
            String challengeTs= null;
            String hostname= null;
            String score= null;
            String action= null;
            String errorCode= null;

            if("true".equals(success)){
                challengeTs= (jsonObject.has("challenge_ts")) ? jsonObject.get("challenge_ts").getAsString() :  "";
                hostname= (jsonObject.has("hostname")) ? jsonObject.get("hostname").getAsString() :  "";
                score= (jsonObject.has("score")) ? jsonObject.get("score").getAsString() :  "0.0";
                action= (jsonObject.has("action")) ? jsonObject.get("action").getAsString() :  "";
            }else{
                JsonArray errorCodeArr= (jsonObject.has("error-codes")) ? (JsonArray) jsonObject.get("error-codes") : null;
                errorCode = (errorCodeArr != null && errorCodeArr.size() > 0) ? errorCodeArr.get(0).getAsString() : "";
            }

            // 4. eventDb 로그 기록
            Map<String,String> recaptchaLogMap= new HashMap<>();
            recaptchaLogMap.put("success",success);
            recaptchaLogMap.put("challengeTs",challengeTs);
            recaptchaLogMap.put("hostname",hostname);
            recaptchaLogMap.put("score",score);
            recaptchaLogMap.put("requestAction",action);
            recaptchaLogMap.put("errorCode",errorCode);
            recaptchaLogMap.put("accessIp",accessIp);
            recaptchaLogMap.put("userId",userSessionDto.getUserId());
            recaptchaLogMap.put("platformCd",NmcpServiceUtils.getPlatFormCd());

            ipstatisticService.insertRecaptchaLog(recaptchaLogMap);

            // 5. 포탈로그 기록 + 성공/실패 여부 리턴
            // 5-1. 점수 확인
            boolean recaptchaResult= false;
            if("true".equals(success)){
                NmcpCdDtlDto resCodeVo= NmcpServiceUtils.getCodeNmDto("RECAPTCHALIMIT", "THRESHOLDSCORE");
                try {
                    int baseScore= (resCodeVo == null) ? 5 : Integer.parseInt(resCodeVo.getExpnsnStrVal1());
                    int recaptchaScore= (int) (Float.parseFloat(score) * 10);
                    logger.error("====== recaptcha api score : baseScore["+baseScore+"], recaptchaScore["+recaptchaScore+"]");
                    if(recaptchaScore > baseScore) recaptchaResult= true;
                }catch (ArithmeticException e) {
                    logger.error("ArithmeticException e : {}", e.getMessage());
                }catch (Exception e) {
                    logger.error("Exception e : {}", e.getMessage());
                }
            }

            // 5-2. 포탈로그 기록
            McpIpStatisticDto mcpIpStatisticDto =  new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("LOGIN_EXECUTE");
            mcpIpStatisticDto.setUserid(userSessionDto.getUserId());
            mcpIpStatisticDto.setPrcsSbst("userId[" +userSessionDto.getUserId() +"]");
            mcpIpStatisticDto.setLoginDivCd("ID");

            if(userSessionDto.getRecaptchaToken().length() > 900){
                mcpIpStatisticDto.setParameter("response="+userSessionDto.getRecaptchaToken().substring(0,900));
            }else{
                mcpIpStatisticDto.setParameter("response="+userSessionDto.getRecaptchaToken());
            }

            if(recaptchaResult){ // 성공
                mcpIpStatisticDto.setTrtmRsltSmst("RECAPTCHA_SUCC");
                rtnMap.put("RESULT_CODE", "0000");
                SessionUtils.saveRecaptchaSession("Y");  // 세션 저장
            }else{ // 실패

                mcpIpStatisticDto.setTrtmRsltSmst("RECAPTCHA_FAIL");

                // success 여부가 true인데 점수를 통과하지 못한 경우 분기처리
                if("true".equals(success)){
                    rtnMap.put("RESULT_CODE", "0004");
                    //rtnMap.put("RESULT_MSG", "SMS 인증 후 이용바랍니다.");
                    rtnMap.put("RESULT_MSG", "본인인증 후</br>로그인 및 서비스 이용이 가능합니다.");
                }else{
                    rtnMap.put("RESULT_CODE", "0003");
                    rtnMap.put("RESULT_MSG", "페이지 새로고침 후 재시도 부탁드립니다.");
                }
            }

            ipstatisticService.insertAccessTrace(mcpIpStatisticDto);

        } catch (SocketTimeoutException e) {
            rtnMap.put("RESULT_CODE", "0002");
            rtnMap.put("RESULT_MSG", "시스템 장애로 인하여 통신이 원활하지 않습니다.<br>잠시 후 이용 바랍니다.");
            return rtnMap;
        }

        return rtnMap;
    }

    /** 특정시간동안 특정횟수 recaptcha 이력 카운트 */
    @Override
    public int getRecaptchaTraceCnt(String divcd) {

        // default 5초
        NmcpCdDtlDto resCodeVo= NmcpServiceUtils.getCodeNmDto("RECAPTCHALIMIT", "LIMITSECOND");
        String limitTime= (resCodeVo == null) ? "5" : resCodeVo.getExpnsnStrVal1();

        Map<String,String> paraMap= new HashMap<>();
        paraMap.put("limitTime", limitTime);
        paraMap.put("accessIp", ipstatisticService.getClientIp());

        // divCd값이 없으면 실패 이력으로 조회
        String rtnDivcd = (StringUtil.isEmpty(divcd)) ? "RECAPTCHA_FAIL" : divcd;
        paraMap.put("trtmRsltSmst", rtnDivcd);

        return loginDao.getRecaptchaTraceCnt(paraMap);
    }

    /** 정/준회원 대상 특정일 경과 후 본인인증건*/
    @Override
    public Map<String, String> certDateCheck(UserSessionDto userDto) {

        Map<String, String> result = new HashMap<>();
        int baseDate = 180;
        String code = "00";
        String message = "";

        if(userDto.getSysCdate() == null || "".equals(userDto.getSysCdate())) {
            code = "80";
            message = "로그인시 최초 1회 본인인증이 필요합니다.<br>" +
                    "본인인증 후 로그인이 가능하며, 본인인증에 따른 양해를 부탁드립니다.";
        } else {
            if("01".equals(userDto.getUserDivision())) { //정회원인 경우 365일 , 준회원 180일
                baseDate = 365;
                message = "본인인증 후 1년(365일) 경과 시 <br>재인증이 필요합니다." +
                        "본인인증 후 로그인이 가능하며, 본인인증에 따른 양해를 부탁드립니다.";
            } else {
                message = "본인인증 후 6개월(180일) 경과 시 <br>재인증이 필요합니다." +
                        "본인인증 후 로그인이 가능하며, 본인인증에 따른 양해를 부탁드립니다.";
            }
            Date today = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(today);

            cal.add(Calendar.DAY_OF_YEAR, - baseDate);
            Date agoDate = new Date(cal.getTimeInMillis()); // 인증기준날짜

            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            formatter.setLenient(false);

            try {
                Date sysCdate = formatter.parse(userDto.getSysCdate());
                if(sysCdate.before(agoDate)) {
                    code = "80";
                }

            } catch (ParseException e) {
                throw new McpCommonException("ParseException");
            } catch (Exception e) {
                logger.error("Exception e : {}", e.getMessage());
            }
        }
        result.put("code", code);
        result.put("message", message);

        return result;
    }
}
