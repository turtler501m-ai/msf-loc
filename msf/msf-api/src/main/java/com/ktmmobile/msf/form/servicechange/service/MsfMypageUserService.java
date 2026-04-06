///**
// *
// */
//package com.ktmmobile.msf.form.servicechange.service;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.ktmmobile.msf.form.newchange.dto.JuoSubInfoDto;
//import com.ktmmobile.msf.system.common.mplatform.vo.CodeVO;
//import com.ktmmobile.msf.system.common.mplatform.vo.UserVO;
//import com.ktmmobile.msf.system.common.legacy.join.dto.JoinDto;
//import com.ktmmobile.msf.form.servicechange.dto.AgreeDto;
//
///**
// * @author ANT_FX700_02
// *
// */
//public interface MsfMypageUserService {
//    /**
//     * @Description : MCP 휴대폰 회선관리 리스트를 가지고 온다.
//     * @param mcpUserCntrMngDto
//     * @return
//     * @Author : ant
//     * @Create Date : 2016. 1. 12.
//     */
//
//    List<CodeVO> selectCodeList(String key);
//
//    UserVO selectUser(String userid);
//
//    int pwCheck(HashMap<String, String> map);
//
//    String prodUserSms(String name, String phone1, String phone2, String phone3,String msg);
//
//    void pwUpdate(HashMap<String, String> map);
//
//    int userUpdate(UserVO userVO);
//
//    int userRegularUpdate(UserVO userVO);
//
//    String userRegularCheck(UserVO userVO);
//
//    List<UserVO> selectUserMultiLine(String userId);
//
//    String selectContractNum(String name, String phone1, String phone2, String phone3);
//
//    String selectContractNum(String name, String phone);
//
//    /**
//     * @Description : MCP 휴대폰 회선 정보 패치
//     * @param
//     * @return
//     * @Author : papier
//     * @Create Date : 2023. 01. 06.
//     */
//    Map<String, String> selectContractObj(String name, String phone , String contractNum) ;
//
//    void insertRegularUpdate(UserVO userVO);
//
//    int userRepChange(String userId, String phone);
//
//    void insertRec(UserVO userVO);
//
//    UserVO selectUserByUseridAndPassword(HashMap<String, String> map);
//
//    void insertRec2(UserVO userVO);
//
//    /**
//     * 회선별 마케팅 수신 동의 여부 조회
//     * @param contractNum
//     * @return
//     */
//    Map<String, String> selectMspMrktAgrYn(String contractNum);
//
//    /**
//     * 회선별 마케팅 수신 동의 여부 저장
//     * @param ctn
//     * @param mrktAgr
//     */
//    void callMspMrktAgr(String ctn, String personalInfoCollectAgree, String othersTrnsAgree, String mrktAgr, String othersTrnsKtAgree, String othersAdReceiveAgree, String indvLocaPrvAgree, String userId);
//
//    /**
//     * 선불 요금제 여부 조회
//     * @param contractNum
//     * @return
//     */
//    boolean selectPrePayment(String contractNum);
//
//    String selectSvcCntrNo(String name, String phone);
//
//    List<String> selectUserSnsList(String userId);
//
//    void deleteUserSns(JoinDto joinDto);
//
//    List<UserVO> getUserMultiLineList(String userId);
//
//    int insertUpdateUserHst(UserVO userVO1);
//
//    void deleteUserHst(UserVO userVO1);
//
//    JuoSubInfoDto selectJuoSubInfo(String name, String mobileNo);
//
//    AgreeDto selectMspMrktAgrTime(String contractNum);
//
//    Map<String, String> selectConSsnObj(String name, String phone, String userSsn);
//
//    void deleteSnsLoginTxn(String userId);
//
//    void updateLoginHistory(String userId);
//
//    int updateRemindYn(String svcCntrNo, String remindYn);
//
//    Map<String, Object> getAgentInfo(String userId);
//
//    int getRegularCnt(Map<String, String> map);
//
//    boolean isOpenMktAgreePop(String userId);
//}
