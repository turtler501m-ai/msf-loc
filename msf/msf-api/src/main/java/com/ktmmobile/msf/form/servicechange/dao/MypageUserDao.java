/**
 *
 */
package com.ktmmobile.msf.form.servicechange.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ktmmobile.msf.system.common.mplatform.vo.CodeVO;
import com.ktmmobile.msf.system.common.mplatform.vo.UserVO;
import com.ktmmobile.msf.system.common.legacy.join.dto.JoinDto;
import com.ktmmobile.msf.form.servicechange.dto.AgreeDto;
import com.ktmmobile.msf.form.servicechange.dto.McpUserCntrMngDto;


/**
 * @author ANT_FX700_02
 *
 */
public interface MypageUserDao {
    /**
     * @Description : 공통코드를 가져온다
     * @param key
     * @return
     * @Author : ant
     * @Create Date : 2016. 1. 20
     */
    List<CodeVO> selectCodeList(String key);

    /**
     * @Description : 회원을 선택해서 가져온다.
     * @param key
     * @return
     * @Author : ant
     * @Create Date : 2016. 1. 20
     */
    UserVO selectUser(String userid);

    int pwCheck(HashMap<String, String> map);

    void pwUpdate(HashMap<String, String> map);

    int userUpdate(UserVO userVO);

    int userRegularUpdate(UserVO userVO);

    String userRegularCheck(UserVO userVO);

    void insertRegularUpdate(UserVO userVO);

    void userChange(HashMap<String, String> map);

    int userRepChange(HashMap<String, String> map);

    int updateUserCntrMng(McpUserCntrMngDto mcpUserCntrMng) ;

    void insertRec(UserVO userVO);

    void updateRec(UserVO userVO);

    void deleteUserCntr(String userid);

    UserVO selectUserByUseridAndPassword(HashMap<String, String> map);

    /**
     * ID기준 마케팅 동의 여부 히스토리 저장 프로시져 호출
     * @param userId
     * @param emailAgrYn
     * @param smsAgrYn
     */
    void callMcpMrktAgr(String userId, String emailAgrYn, String smsAgrYn);

    /**
     * 회선별 마케팅 동의 여부 조회
     * @param contractNum
     */
    Map<String, String> selectMspMrktAgrYn(String contractNum);

    /**
     * 회선별 마케팅 동의 여부 저장
     * @param ctn
     * @param mrktAgr
     */
    void callMspMrktAgr(String ctn, String personalInfoCollectAgree, String othersTrnsAgree, String mrktAgr, String othersTrnsKtAgree, String othersAdReceiveAgree, String indvLocaPrvAgree, String userId);



    /**
     * ID기준 마케팅 수신/거부 날짜 조회
     * @param userId
     * @return
     */
    List<Map<String, String>> selectMcpMrkthist(String userId);


    List<String> selectUserList(String userId);

    void deleteUserSns(JoinDto joinDto);

    List<String> checkDatHst(UserVO userVO1);

    void insertUserHst(UserVO userVO1);

    void updateUserHst(UserVO userVO1);

    void deleteDormancyUserHst(UserVO userVO1);

    void deleteUserHst(UserVO userVO1);

    AgreeDto selectMspMrktAgrTime(String contractNum);

    void deleteSnsLoginTxn(String userid);

    void updateLoginHistory(String userId);

    int updateRemindYn(HashMap<String, String> map);
    Map<String, Object> getAgentInfo(String userId);
    int getRegularCnt(Map<String, String> map);
}
