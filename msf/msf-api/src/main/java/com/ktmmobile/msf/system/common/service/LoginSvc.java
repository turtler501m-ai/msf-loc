package com.ktmmobile.msf.system.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ktmmobile.msf.system.common.dto.AuthSmsDto;
import com.ktmmobile.msf.system.common.dto.McpUserDarkwebDto;
import com.ktmmobile.msf.system.common.dto.NiceResDto;
import com.ktmmobile.msf.system.common.dto.PersonalPolicyDto;
import com.ktmmobile.msf.system.common.dto.RoleMenuDto;
import com.ktmmobile.msf.system.common.dto.UserSessionDto;
import com.ktmmobile.msf.system.common.dto.db.NmcpAutoLoginTxnDto;

public interface LoginSvc {

    public int addBirthGenderAjax(UserSessionDto userSessionDto);

    /**
     * <pre>
     * 프로젝트 : kt M mobile
     * 파일명   : LoginSvc.java
     * 날짜     : 2017. 10. 12
     * 작성자   : papier
     * 설명     : 로그인 확인후  로그인 실패 수 확인
     * - ID로 사용자 정보 패치
     * - 비빌번호 일치 여부 확인
     * - 불일치 경우 LOGIN_FAIL_COUNT 증가
     * - 일치한 경우 LOGIN_FAIL_COUNT 0 으로 초기화
     * </pre>
     */
    public UserSessionDto loginCheckFailCount(UserSessionDto userSessionDto);

    public UserSessionDto loginCheckFailCount(UserSessionDto userSessionDto, Boolean isTrace);

    public UserSessionDto loginProcess(UserSessionDto userSessionDto);

    public List<RoleMenuDto> getRoleMenuList(String roleCode);

    public Boolean getUserAuthSms(UserSessionDto userSessionDto);

    public UserSessionDto checkNiceCertAjax(UserSessionDto searchVO);

    public UserSessionDto checkNiceCertDormantAjax(UserSessionDto searchVO);

    public UserSessionDto checkIdNameAjax(UserSessionDto searchVO);

    public UserSessionDto checkIdNameDormantAjax(UserSessionDto searchVO);

    public boolean sendAuthSms(AuthSmsDto authSmsDto);

    public int tmpPasswordUpdate(UserSessionDto userSessionDto);

    public int updateHit(String userId);

    public PersonalPolicyDto personalPolicySelect();

    /**
     * 새 비밀번호 업데이트
     * @param map
     * @return
     */
    public int updateNewPassword(HashMap<String, String> map);

    public int updateNewPasswordDormant(HashMap<String, String> map);

    public UserSessionDto loginCheckAppAutoLogin(UserSessionDto userSessionDto);

    public UserSessionDto loginCheckAppAutoLoginDormancy(UserSessionDto userSessionDto);

    public NmcpAutoLoginTxnDto insertAutoLoginTxn(UserSessionDto userSessionDto);

    public NmcpAutoLoginTxnDto updateAutoLoginTxn(UserSessionDto userSessionDto);

    public int insertDormantToMcpUser(NiceResDto niceResDto);

    public int updateDormantUserChg(String dupInfo);

    public UserSessionDto dormancyLoginProcess(UserSessionDto userSessionDto);

    public List<McpUserDarkwebDto> getMcpUserDarkwebList(String userId);
    public int updateMcpUserDarkweb(String userId);

    public int updatePwChgInfoNoShow(String userId);

    public NmcpAutoLoginTxnDto getLoginAutoLogin(UserSessionDto userSessionDto);

    public int findPinToIdCnt(UserSessionDto searchVO);

    public int resetDormancy(UserSessionDto searchVO);

    // 접속 제한 IP 찾기
    public int limitCnt(UserSessionDto searchVO);

    // 접속 제한 IP 등록
    public int insertIpLimit(UserSessionDto searchVO);

    // 접속 제한 IP 찾기
    public int limitTime();

    // recaptcha 결과 확인
    public Map<String, String> getRecaptchaResult(UserSessionDto userSessionDto);

    // 특정시간동안 특정횟수 recaptcha 실패 카운트
    public int getRecaptchaTraceCnt(String divcd);
    public Map<String, String> certDateCheck(UserSessionDto userDto);
}
