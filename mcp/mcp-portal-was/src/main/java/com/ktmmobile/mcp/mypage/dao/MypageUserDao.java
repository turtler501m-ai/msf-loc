/**
 *
 */
package com.ktmmobile.mcp.mypage.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ktmmobile.mcp.common.mplatform.vo.CodeVO;
import com.ktmmobile.mcp.common.mplatform.vo.UserVO;
import com.ktmmobile.mcp.join.dto.JoinDto;
import com.ktmmobile.mcp.mypage.dto.AgreeDto;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;


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
    public List<CodeVO> selectCodeList(String key);

    /**
    * @Description : 회원을 선택해서 가져온다.
    * @param key
    * @return
    * @Author : ant
    * @Create Date : 2016. 1. 20
    */
    public UserVO selectUser(String userid);

    public int pwCheck(HashMap<String, String> map);

    public void pwUpdate(HashMap<String, String> map);

    public int userUpdate(UserVO userVO);

    public int userRegularUpdate(UserVO userVO);

    public String userRegularCheck(UserVO userVO);

//	public List<UserVO> selectUserMultiLine(String userId);
//
//	public String selectContractNum(HashMap<String, String> map);

    public void insertRegularUpdate(UserVO userVO);

    public void userChange(HashMap<String, String> map);

    public int userRepChange(HashMap<String, String> map);

    public int updateUserCntrMng(McpUserCntrMngDto mcpUserCntrMng) ;

    public void insertRec(UserVO userVO);

    public void updateRec(UserVO userVO);

    public void deleteUserCntr(String userid);

    public UserVO selectUserByUseridAndPassword(HashMap<String, String> map);

    /**
     * ID기준 마케팅 동의 여부 히스토리 저장 프로시져 호출
     * @param userId
     * @param emailAgrYn
     * @param smsAgrYn
     */
    public void callMcpMrktAgr(String userId, String emailAgrYn, String smsAgrYn);

    /**
     * 회선별 마케팅 동의 여부 조회
     * @param contractNum
     */
    public Map<String, String> selectMspMrktAgrYn(String contractNum);

    /**
     * 회선별 마케팅 동의 여부 저장
     * @param ctn
     * @param mrktAgr
     */
    public void callMspMrktAgr(String ctn, String personalInfoCollectAgree, String othersTrnsAgree, String mrktAgr, String othersTrnsKtAgree, String othersAdReceiveAgree, String userId);



    /**
     * ID기준 마케팅 수신/거부 날짜 조회
     * @param userId
     * @return
     */
    public List<Map<String, String>> selectMcpMrkthist(String userId);


    public List<String> selectUserList(String userId);

    public void deleteUserSns(JoinDto joinDto);

    //public void deleteUser(UserVO userVO1);

    public List<String> checkDatHst(UserVO userVO1);

    public void insertUserHst(UserVO userVO1);

    public void updateUserHst(UserVO userVO1);

    public void deleteDormancyUserHst(UserVO userVO1);

    public void deleteUserHst(UserVO userVO1);

    AgreeDto selectMspMrktAgrTime(String contractNum);

    public void deleteSnsLoginTxn(String userid);

    public void updateLoginHistory(String userId);

    public int updateRemindYn(HashMap<String, String> map);
    public Map<String, Object> getAgentInfo(String userId);
    public int getRegularCnt(Map<String, String> map);
}
