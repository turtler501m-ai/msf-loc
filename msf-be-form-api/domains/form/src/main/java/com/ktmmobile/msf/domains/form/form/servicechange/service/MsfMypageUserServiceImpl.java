/**
 *
 */

package com.ktmmobile.msf.domains.form.form.servicechange.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ktmmobile.msf.domains.form.common.commCode.dao.CommCodeDAO;
import com.ktmmobile.msf.domains.form.common.commCode.dto.CommCodeInstDTO;
import com.ktmmobile.msf.domains.form.common.dto.UserSessionDto;
import com.ktmmobile.msf.domains.form.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.msf.domains.form.common.exception.McpErropPageException;
import com.ktmmobile.msf.domains.form.common.legacy.join.dto.JoinDto;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.CodeVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.UserVO;
import com.ktmmobile.msf.domains.form.common.service.SmsSvc;
import com.ktmmobile.msf.domains.form.common.util.EncryptUtil;
import com.ktmmobile.msf.domains.form.common.util.MaskingUtil;
import com.ktmmobile.msf.domains.form.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.domains.form.common.util.SessionUtils;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
import com.ktmmobile.msf.domains.form.form.newchange.dto.JuoSubInfoDto;
import com.ktmmobile.msf.domains.form.form.servicechange.repository.MypageUserDao;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.AgreeDto;

import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;

/**
 * @author ANT_FX700_02
 *
 */
@Service
public class MsfMypageUserServiceImpl implements MsfMypageUserService {

    private static final Logger logger = LoggerFactory.getLogger(MsfMypageUserService.class);

    @Autowired
    MypageUserDao mypageUserDao;

    //    @Autowired
    //    MstoreDao mstoreDao;

    @Autowired
    SmsSvc smsSvc;

    //    @Autowired
    //    JoinSvc joinSvc;

    @Autowired
    CommCodeDAO commCodeDAO;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;


    /**
     * @Description : 공통코드를 가져온다
     * @param key
     * @return
     * @Author : ant
     * @Create Date : 2016. 1. 20
     */
    @Override
    public List<CodeVO> selectCodeList(String key) {
        // TODO Auto-generated method stub
        return mypageUserDao.selectCodeList(key);
    }

    /**
     * @Description : 회원을 선택해서 가져온다.
     * @param
     * @return
     * @Author : ant
     * @Create Date : 2016. 1. 20
     */
    @Override
    public UserVO selectUser(String userid) {
        // TODO Auto-generated method stub
        UserVO userVO = mypageUserDao.selectUser(userid);

        // 주소 마스킹 처리 2022.10.05
        if (userVO != null) {
            String addr = userVO.getAddress1() + " " + userVO.getAddress2();
            if (SessionUtils.getMaskingSession() > 0) {
                userVO.setMkAddress(addr);
            } else {
                userVO.setMkAddress(MaskingUtil.getMaskedByAddressNew2(addr));
            }
        }

        //ID기준 마케팅 수신/거부 날짜 조회
        List<Map<String, String>> mcpMrkthistList = mypageUserDao.selectMcpMrkthist(userid);
        if (mcpMrkthistList != null && mcpMrkthistList.size() > 0) {
            for (Map<String, String> hm: mcpMrkthistList) {
                String tempDttm = hm.get("REGST_DTTM") != null ? hm.get("REGST_DTTM") : "";
                if ("01".equals(hm.get("GUBUN"))) {
                    userVO.setEmailRcvHist(tempDttm);
                } else if ("02".equals(hm.get("GUBUN"))) {
                    userVO.setSmsRcvHist(tempDttm);

                } else if ("03".equals(hm.get("GUBUN"))) { //수정
                    userVO.setPersonAgreeTime(tempDttm);
                } else if ("04".equals(hm.get("GUBUN"))) {
                    userVO.setClauerPriTiem(tempDttm);
                } else if ("05".equals(hm.get("GUBUN"))) {
                    userVO.setOthersTime(tempDttm);
                }
            }
            // 신규 활둉동의가 없을 경우 (기존 고객) 기존 정보/광고 수신동의 유무로 할당
            if (StringUtil.isEmpty(userVO.getPersonalInfoCollectAgree()) && StringUtil.isEmpty(userVO.getOthersTrnsAgree())) {
                userVO.setPersonalInfoCollectAgree(userVO.getSmsRcvYn());
                userVO.setOthersTrnsAgree(userVO.getSmsRcvYn());
                userVO.setClausePriAdFlag(StringUtil.isEmpty(userVO.getClausePriAdFlag()) ? userVO.getSmsRcvYn() : userVO.getClausePriAdFlag());
                userVO.setClauerPriTiem(userVO.getSmsRcvHist());
                userVO.setPersonAgreeTime(userVO.getSmsRcvHist());
                userVO.setOthersTime(userVO.getSmsRcvHist());
            }
        }
        return userVO;
    }

    @Override
    public int pwCheck(HashMap<String, String> map) {
        // TODO Auto-generated method stub
        return mypageUserDao.pwCheck(map);
    }

    @Override
    public String prodUserSms(
        String name, String phone1, String phone2,
        String phone3, String msg
    ) {

        //6자리 인증번호 생성
        StringBuffer smsNoBuffer = new StringBuffer();
        String smsNo = "";
        String msgTmp = msg;
        msgTmp = "[kt M모바일][인증번호:";
        String msg2 = "] 인증번호를 입력해 주세요.";
        Random objRandom;
        try {
            objRandom = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            throw new McpErropPageException(COMMON_EXCEPTION);
        }

        for (int i = 0; i < 6; i++) {
            smsNoBuffer.append(objRandom.nextInt(10));
        }
        smsNo = smsNoBuffer.toString();

        //SMS 인증번호 발송
        String message = msgTmp + smsNo + msg2;
        smsSvc.sendSmsForAuth(phone1 + phone2 + phone3, false, message);
        return smsNo;

    }

    @Override
    public void pwUpdate(HashMap<String, String> map) {
        // TODO Auto-generated method stub
        mypageUserDao.pwUpdate(map);
    }

    @Override
    public int userUpdate(UserVO userVO) {
        // TODO Auto-generated method stub
        //        StringBuffer mobileNo = new StringBuffer();
        //        StringBuffer email = new StringBuffer();
        //        mobileNo.append(userVO.getPhone1()).append(userVO.getPhone2()).append(userVO.getPhone3());
        //        email.append(userVO.getEmail1()).append("@").append(userVO.getEmail2());
        //        userVO.setMobileNo(mobileNo.toString());
        //        userVO.setEmail(email.toString());
        //        userVO.setPost(userVO.getPost1());
        String pw = userVO.getPassword();
        pw = EncryptUtil.sha512HexEnc(pw);
        userVO.setPassword(pw);
        int result = mypageUserDao.userUpdate(userVO);

        if (result == 1 && userVO.getPersonalInfoCollectAgree() != null) {
            //마케팅 동의 변경 내역 저장
            JoinDto joinDto = new JoinDto();
            joinDto.setUserId(userVO.getUserid());
            joinDto.setAgreeEmail(userVO.getEmailRcvYn());
            joinDto.setAgreeSMS(userVO.getSmsRcvYn());
            joinDto.setPersonalInfoCollectAgree(userVO.getPersonalInfoCollectAgree());
            joinDto.setOthersTrnsAgree(userVO.getOthersTrnsAgree());
            joinDto.setClausePriAdFlag(userVO.getClausePriAdFlag());
            joinDto.setMobileNo(userVO.getMobileNo());
            joinDto.setMtkAgrReferer(userVO.getMtkAgrReferer());
            //joinSvc.insertUpdateMrktHist(joinDto);
        }

        return result;
    }

    @Override
    public int userRegularUpdate(UserVO userVO) {
        StringBuffer mobileNo = new StringBuffer();
        mobileNo.append(userVO.getPhone1()).append(userVO.getPhone2()).append(userVO.getPhone3());
        userVO.setMobileNo(mobileNo.toString());

        int result = mypageUserDao.userRegularUpdate(userVO);

        //사용자 session 다시 저장 처리
        //회원가입시는 스킵처리
        if (!"Y".equals(StringUtil.NVL(userVO.getJoinYn(), ""))) {
            UserSessionDto userSession = SessionUtils.getUserCookieBean();
            userSession.setMobileNo(userVO.getPhone1() + userVO.getPhone2() + userVO.getPhone3());
            userSession.setUserDivision("01");
            userSession.setCustomerId(userVO.getCustomerId());
            SessionUtils.saveUserSession(userSession);
        }

        return result;
    }

    @Override
    public String userRegularCheck(UserVO userVO) {

        StringBuffer mobileNo = new StringBuffer();
        mobileNo.append(userVO.getPhone1()).append(userVO.getPhone2()).append(userVO.getPhone3());
        userVO.setMobileNo(mobileNo.toString());
        return mypageUserDao.userRegularCheck(userVO);
    }

    @Override
    public List<UserVO> selectUserMultiLine(String userId) {
        // TODO Auto-generated method stub
        RestTemplate restTemplate = new RestTemplate();
        UserSessionDto userSessionDto1 = SessionUtils.getUserCookieBean();
        //MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", userId);
        params.put("customerId", userSessionDto1 != null ? userSessionDto1.getCustomerId() : ""); // 취약성 341

        UserVO[] userVoArr = restTemplate.postForObject(apiInterfaceServer + "/mypage/userMultiLine", params, UserVO[].class);
        List<UserVO> userVOList = Arrays.asList(userVoArr);
        return userVOList;
    }

    @Override
    public String selectContractNum(String name, String phone) {

        //---- API 호출 S ----//
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", name);
        params.put("mobileNo", phone);
        String contractNum = restTemplate.postForObject(apiInterfaceServer + "/mypage/contractNum", params, String.class);
        //---- API 호출 E ----//

        //      return mypageUserDao.selectContractNum(map);
        return contractNum;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, String> selectContractObj(String name, String phone, String contractNum) {

        Map<String, String> rtnMap = null;

        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<String, String>();

        params.put("name", name);
        params.put("mobileNo", phone);
        params.put("contractNum", contractNum);
        rtnMap = restTemplate.postForObject(apiInterfaceServer + "/mypage/selectContractObj", params, Map.class);

        return rtnMap;
    }


    @Override
    public String selectContractNum(String name, String phone1, String phone2, String phone3) {
        StringBuffer mobileNo = new StringBuffer();
        mobileNo.append(phone1).append(phone2).append(phone3);

        //---- API 호출 S ----//
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", name);
        params.put("mobileNo", mobileNo.toString());
        String contractNum = restTemplate.postForObject(apiInterfaceServer + "/mypage/contractNum", params, String.class);
        //---- API 호출 E ----//

        //      return mypageUserDao.selectContractNum(map);
        return contractNum;
    }

    @Override
    public void insertRegularUpdate(UserVO userVO) {
        // TODO Auto-generated method stub
        StringBuffer mobileNo = new StringBuffer();
        mobileNo.append(userVO.getPhone1()).append(userVO.getPhone2()).append(userVO.getPhone3());
        userVO.setMobileNo(mobileNo.toString());
        mypageUserDao.insertRegularUpdate(userVO);
    }

    @Override
    public int userRepChange(String userId, String phone) {
        // TODO Auto-generated method stub
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("userId", userId);
        map.put("phone", phone);
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        map.put("customerId", userSession != null ? userSession.getCustomerId() : ""); // 취약성
        this.mypageUserDao.userChange(map);
        return mypageUserDao.userRepChange(map);

    }

    @Override
    public void insertRec(UserVO userVO) {
        // TODO Auto-generated method stub
        this.mypageUserDao.insertRec(userVO);
        this.mypageUserDao.updateRec(userVO);

        /* START: M스토어 관련 처리 */
        // M스토어 이용 가능 대상이 정회원에서 준회원(M모바일 이용고객이 아니어도 포함)까지 확대됨에 따라, 기존 M스토어를 이용하던 정회원은 NMCP_MSTORE_SSO_INFO 테이블에 정보가 없음
        // NMCP_MSTORE_SSO_INFO 테이블에 해당 정보를 INSERT하기 위해, CONTRACT_NUM 추출
        //        String contractNum= mstoreDao.getMcpUserCntrMngInfo(userVO.getUserid());
        //        userVO.setContractNum(contractNum);
        /* END: M스토어 관련 처리 */

        this.mypageUserDao.deleteUserCntr(userVO.getUserid());
    }

    @Override
    public UserVO selectUserByUseridAndPassword(HashMap<String, String> map) {
        // TODO Auto-generated method stub
        return mypageUserDao.selectUserByUseridAndPassword(map);
    }

    @Override
    public void insertRec2(UserVO userVO) {
        // TODO Auto-generated method stub
        this.mypageUserDao.insertRec(userVO);
        this.mypageUserDao.updateRec(userVO);
    }

    @Override
    public Map<String, String> selectMspMrktAgrYn(String contractNum) {
        return mypageUserDao.selectMspMrktAgrYn(contractNum);
    }

    @Override
    public void callMspMrktAgr(
        String ctn,
        String personalInfoCollectAgree,
        String othersTrnsAgree,
        String mrktAgr,
        String othersTrnsKtAgree,
        String othersAdReceiveAgree,
        String indvLocaPrvAgree,
        String userId
    ) {
        mypageUserDao.callMspMrktAgr(ctn,
            personalInfoCollectAgree,
            othersTrnsAgree,
            mrktAgr,
            othersTrnsKtAgree,
            othersAdReceiveAgree,
            indvLocaPrvAgree,
            userId);
    }

    @Override
    public boolean selectPrePayment(String contractNum) {

        boolean result = false;

        //선불요금제 여부 조회
        //      int cnt = mypageUserDao.selectPrePayment(contractNum);
        //---- API 호출 S ----//
        RestTemplate restTemplate = new RestTemplate();
        int cnt = restTemplate.postForObject(apiInterfaceServer + "/mypage/prePayment", contractNum, int.class);
        //---- API 호출 E ----//
        if (cnt >= 1) {
            result = true;
        }

        return result;
    }

    @Override
    public String selectSvcCntrNo(String name, String phone) {

        RestTemplate restTemplate = new RestTemplate();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("name", name);
        params.put("mobileNo", phone);
        String svcCntrNo = restTemplate.postForObject(apiInterfaceServer + "/mypage/svcCntrNo", params, String.class);
        return svcCntrNo;
    }

    @Override
    public List<String> selectUserSnsList(String userId) {

        return mypageUserDao.selectUserList(userId);
    }

    //    @Override
    //    public void deleteUserSns(JoinDto joinDto) {
    //        mypageUserDao.deleteUserSns(joinDto);
    //
    //    }

    @Override
    public List<UserVO> getUserMultiLineList(String userId) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", userId);
        UserSessionDto userSessionDto1 = SessionUtils.getUserCookieBean();
        params.put("customerId", userSessionDto1 != null ? userSessionDto1.getCustomerId() : ""); // 취약성 344
        UserVO[] resultList = restTemplate.postForObject(apiInterfaceServer + "/mypage/userMultiLine", params, UserVO[].class);
        List<UserVO> userVOList = Arrays.asList(resultList);
        return userVOList;
    }

    @Override
    public int insertUpdateUserHst(UserVO userVO1) {
        try {
            List<String> val = mypageUserDao.checkDatHst(userVO1);
            mypageUserDao.deleteDormancyUserHst(userVO1);
            if (Optional.ofNullable(val).isEmpty()) {
                mypageUserDao.insertUserHst(userVO1);
            } else {
                mypageUserDao.updateUserHst(userVO1);
            }
            return 0;
        } catch (DataAccessException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public void deleteUserHst(UserVO userVO1) {
        mypageUserDao.deleteUserHst(userVO1);
    }

    @Override
    public JuoSubInfoDto selectJuoSubInfo(String name, String mobileNo) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", name);
        params.put("mobileNo", mobileNo);

        return restTemplate.postForObject(apiInterfaceServer + "/mypage/juoSubInfo", params, JuoSubInfoDto.class);
    }

    @Override
    public AgreeDto selectMspMrktAgrTime(String contractNum) {
        return mypageUserDao.selectMspMrktAgrTime(contractNum);
    }

    //이름,주민번호,연락처로 계약번호조회
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, String> selectConSsnObj(String name, String phone, String userSsn) {

        Map<String, String> rtnMap = null;

        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<String, String>();

        params.put("name", name);
        params.put("mobileNo", phone);
        params.put("userSsn", userSsn);
        rtnMap = restTemplate.postForObject(apiInterfaceServer + "/mypage/selectConSsnObj", params, Map.class);

        return rtnMap;
    }

    @Override
    public void deleteSnsLoginTxn(String userId) {
        mypageUserDao.deleteSnsLoginTxn(userId);
    }

    @Override
    public void updateLoginHistory(String userId) {
        mypageUserDao.updateLoginHistory(userId);
    }

    @Override
    public int updateRemindYn(String svcCntrNo, String remindYn) {
        // TODO Auto-generated method stub
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("svcCntrNo", svcCntrNo);
        map.put("remindYn", remindYn);

        return mypageUserDao.updateRemindYn(map);

    }

    @Override
    public Map<String, Object> getAgentInfo(String userId) {
        return mypageUserDao.getAgentInfo(userId);
    }

    @Override
    public int getRegularCnt(Map<String, String> map) {
        return mypageUserDao.getRegularCnt(map);
    }

    @Override
    public boolean isOpenMktAgreePop(String userId) {
        CommCodeInstDTO commCodeInstDto = commCodeDAO.getFstCodeTble("mktAgrPopShowId");
        if (!"Y".equals(commCodeInstDto.getStatus())) {
            return true;
        }

        List<NmcpCdDtlDto> mktAgreePopIdList = NmcpServiceUtils.getCodeList("mktAgrPopShowId");
        return mktAgreePopIdList.stream()
            .anyMatch(dto -> userId.equals(dto.getDtlCd()));
    }
}
