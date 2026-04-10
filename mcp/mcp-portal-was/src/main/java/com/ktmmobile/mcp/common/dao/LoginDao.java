package com.ktmmobile.mcp.common.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ktmmobile.mcp.common.dto.McpUserDarkwebDto;
import com.ktmmobile.mcp.common.dto.NiceResDto;
import com.ktmmobile.mcp.common.dto.PersonalPolicyDto;
import com.ktmmobile.mcp.common.dto.RoleMenuDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.dto.db.NmcpAutoLoginTxnDto;

public interface LoginDao {

    public int addBirthGenderAjax(UserSessionDto userSessionDto);

    public UserSessionDto loginProcess(UserSessionDto userSessionDto);

    /**
     * <pre>
     * 프로젝트 : kt M mobile
     * 파일명   : LoginDao.java
     * 날짜     : 2017. 10. 13
     * 작성자   : papier
     * 설명     : 사용자 정보 UPDATE
     * </pre>
     */
    public boolean updateUserSet(UserSessionDto userSessionDto) ;

    /**
     * <pre>
     * 프로젝트 : kt M mobile
     * 파일명   : LoginDao.java
     * 날짜     : 2017. 10. 13
     * 작성자   : papier
     * 설명     : 사용자 정보 패치
     * </pre>
     */
    public UserSessionDto getUserInfo(UserSessionDto userSessionDto);

    public UserSessionDto getDormancyInfo(UserSessionDto userSessionDto);

    public List<RoleMenuDto> getRoleMenuList(String roleCode);

    public int selectIdCheckCount(Map paraMap);

    public UserSessionDto getUserAuthSms(UserSessionDto userSessionDto);

    public UserSessionDto checkNiceCertAjax(UserSessionDto searchVO);

    public UserSessionDto checkNiceCertDormantAjax(UserSessionDto searchVO);

    public UserSessionDto checkIdNameAjax(UserSessionDto searchVO);

    public UserSessionDto checkIdNameDormantAjax(UserSessionDto searchVO);

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

    /**
     * <pre>
     * 프로젝트 : kt M mobile
     * 파일명   : LoginDao.java
     * 날짜     : 2019. 12. 18
     * 작성자   : papier
     * 설명     : 무차별 대입 공격 (Brute Forece Attack) 체크
     * </pre>
     */
    public int checkLoginAttack(UserSessionDto userSessionDto) ;

    public NmcpAutoLoginTxnDto getLoginAutoLogin(UserSessionDto userSessionDto);

    public void insertAutoLoginTxn(NmcpAutoLoginTxnDto nmcpAutoLoginTxnDto);

    public int updateAutoLoginTxn(NmcpAutoLoginTxnDto nmcpAutoLoginTxnDto);

    public int insertDormantToMcpUser(NiceResDto niceResDto);

    public int updateDormantUserChg(String dupInfo);

    public UserSessionDto dormancyLoginProcess(UserSessionDto userSessionDto);

    public List<McpUserDarkwebDto> getMcpUserDarkwebList(String userId);
    public int updateMcpUserDarkweb(String userId);

    public int updatePwChgInfoNoShow(String userId);

    public int findPinToIdCnt(UserSessionDto searchVO);

    public int resetDormancy(UserSessionDto searchVO);

    // 접속 제한 IP 찾기
    public int limitCnt(UserSessionDto searchVO);

    // 접속 제한 IP 등록
    public int insertIpLimit(UserSessionDto searchVO);

    // 제한 시간 찾아옴.
    public int limitTime();

    // 휴면 계정 본테이블로 insert
    public boolean intDormancyInfoSub(UserSessionDto userSessionDto);

    // 휴면 계정 hst 테이블 에서 삭제
    public boolean delDormancyInfoDel(UserSessionDto userSessionDto);

    // 정회원 정보 건수 가져오기
    public int selCntrCnt(String userId);

    // 정회원을 준회원으로 변경 처리
    public boolean updCntr(String userId);

    // 특정시간동안 특정횟수 recaptcha 카운트 (실패 또는 성공 이력)
    public int getRecaptchaTraceCnt(Map<String, String> paraMap);

    // 회원 아이디로 DI 조회
    public String getDupInfoByUserId(String userId);
}
