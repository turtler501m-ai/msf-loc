/**
 *
 */
package com.ktmmobile.mcp.mypage.service;

import com.ktmmobile.mcp.appform.dto.JuoSubInfoDto;
import com.ktmmobile.mcp.common.mplatform.vo.CodeVO;
import com.ktmmobile.mcp.common.mplatform.vo.UserVO;
import com.ktmmobile.mcp.join.dto.JoinDto;
import com.ktmmobile.mcp.mypage.dto.AgreeDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ANT_FX700_02
 *
 */
public interface MypageUserService {
    /**
    * @Description : MCP 휴대폰 회선관리 리스트를 가지고 온다.
    * @param mcpUserCntrMngDto
    * @return
    * @Author : ant
    * @Create Date : 2016. 1. 12.
    */

    public List<CodeVO> selectCodeList(String key);

    public UserVO selectUser(String userid);

    public int pwCheck(HashMap<String, String> map);

    public String prodUserSms(String name, String phone1, String phone2, String phone3,String msg);

    public void pwUpdate(HashMap<String, String> map);

    public int userUpdate(UserVO userVO);

    public int userRegularUpdate(UserVO userVO);

    public String userRegularCheck(UserVO userVO);

    public List<UserVO> selectUserMultiLine(String userId);

    public String selectContractNum(String name, String phone1, String phone2, String phone3);

    public String selectContractNum(String name, String phone);

    /**
     * @Description : MCP 휴대폰 회선 정보 패치
     * @param
     * @return
     * @Author : papier
     * @Create Date : 2023. 01. 06.
     */
    public Map<String, String> selectContractObj(String name, String phone , String contractNum) ;

    public void insertRegularUpdate(UserVO userVO);

    public int userRepChange(String userId, String phone);

    public void insertRec(UserVO userVO);

    public UserVO selectUserByUseridAndPassword(HashMap<String, String> map);

    public void insertRec2(UserVO userVO);

    /**
     * 회선별 마케팅 수신 동의 여부 조회
     * @param contractNum
     * @return
     */
    public Map<String, String> selectMspMrktAgrYn(String contractNum);

    /**
     * 회선별 마케팅 수신 동의 여부 저장
     * @param ctn
     * @param mrktAgr
     */
    public void callMspMrktAgr(String ctn, String personalInfoCollectAgree, String othersTrnsAgree, String mrktAgr, String othersTrnsKtAgree, String othersAdReceiveAgree, String userId);

    /**
     * 선불 요금제 여부 조회
     * @param contractNum
     * @return
     */
    public boolean selectPrePayment(String contractNum);

    public String selectSvcCntrNo(String name, String phone);

    public List<String> selectUserSnsList(String userId);

    public void deleteUserSns(JoinDto joinDto);

    public List<UserVO> getUserMultiLineList(String userId);

    public int insertUpdateUserHst(UserVO userVO1);

    public void deleteUserHst(UserVO userVO1);

    public JuoSubInfoDto selectJuoSubInfo(String name, String mobileNo);

    AgreeDto selectMspMrktAgrTime(String contractNum);

    public Map<String, String> selectConSsnObj(String name, String phone, String userSsn);

    public void deleteSnsLoginTxn(String userId);

    public void updateLoginHistory(String userId);

    public int updateRemindYn(String svcCntrNo, String remindYn);

    public Map<String, Object> getAgentInfo(String userId);

    public int getRegularCnt(Map<String, String> map);

    boolean isOpenMktAgreePop(String userId);
}
