package com.ktis.msp.cmn.login.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.cmn.login.vo.MsgQueueVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * <PRE>
 * 1. ClassName	:
 * 2. FileName	: LoginMapper.java
 * 3. Package	: com.ktis.msp.cmn.login.mapper
 * 4. Commnet	:
 * 5. 작성자	: Administrator
 * 6. 작성일	: 2014. 9. 25. 오후 3:58:10
 * </PRE>
 */
@Mapper("LoginMapper")
public interface LoginMapper {

    /**
     * ***  항목을 가져온다.
     * @param  // searchVO - 조회할 정보가 담긴 VO
     * @return //
     * @exception Exception
     */
//
    /**
     * <PRE>
     * 1. MethodName: selectLogin
     * 2. ClassName	: LoginMapper
     * 3. Commnet	:
     * 4. 작성자	: Administrator
     * 5. 작성일	: 2014. 9. 25. 오후 3:58:12
     * </PRE>
     * 		@return List<?>
     * 		@param p_reqParamMap
     * 		@return
     * 		@throws Exception
     */
    List<?> selectLogin(Map<String, Object> pReqParamMap) ;

    /**
     * <PRE>
     * 1. MethodName: insertLog
     * 2. ClassName	: LoginMapper
     * 3. Commnet	:
     * 4. 작성자	: Administrator
     * 5. 작성일	: 2014. 9. 25. 오후 3:58:13
     * </PRE>
     * 		@return int
     * 		@param p_reqParamMap
     * 		@return
     * 		@throws Exception
     */
    int  insertLog(Map<String, Object> pReqParamMap) ;

    /**
     * <PRE>
     * 1. MethodName: updateUserLastLoginDt
     * 2. ClassName	: LoginMapper
     * 3. Commnet	:
     * 4. 작성자	: Administrator
     * 5. 작성일	: 2014. 9. 25. 오후 3:58:15
     * </PRE>
     * 		@return int
     * 		@param p_reqParamMap
     * 		@return
     * 		@throws Exception
     */
    int  updateUserLastLoginDt(Map<String, Object> pReqParamMap) ;

    /**
     * <PRE>
     * 1. MethodName: updateUserPassErrNum
     * 2. ClassName	: LoginMapper
     * 3. Commnet	:
     * 4. 작성자	: Administrator
     * 5. 작성일	: 2014. 9. 25. 오후 3:58:17
     * </PRE>
     * 		@return int
     * 		@param p_reqParamMap
     * 		@return
     * 		@throws Exception
     */
    int  updateUserPassErrNum(Map<String, Object> pReqParamMap) ;
    
    /**
     * <PRE>
     * 1. MethodName: updateUserOtpErrNum
     * 2. ClassName	: LoginMapper
     * 3. Commnet	:
     * 4. 작성자	: Administrator
     * 5. 작성일	: 2014. 9. 25. 오후 3:58:17
     * </PRE>
     * 		@return int
     * 		@param p_reqParamMap
     * 		@return
     * 		@throws Exception
     */
    int  updateUserOtpErrNum(Map<String, Object> pReqParamMap) ;

    /**
     * <PRE>
     * 1. MethodName: selectLoginChk
     * 2. ClassName	: LoginMapper
     * 3. Commnet	:
     * 4. 작성자	: Administrator
     * 5. 작성일	: 2015. 9. 23. 오후 4:23:12
     * </PRE>
     * 		@return Map<String, Object>
     * 		@param p_reqParamMap
     * 		@return
     * 		@throws Exception
     */
    Map<String, Object> selectLoginChk(Map<String, Object> pReqParamMap) ;
    
    
    /**
     * <PRE>
     * 1. MethodName: selectMacLoginChk
     * 2. ClassName	: LoginMapper
     * 3. Commnet	:
     * 4. 작성자	: Administrator
     * 5. 작성일	: 2015. 9. 23. 오후 4:23:12
     * </PRE>
     * 		@return Map<String, Object>
     * 		@param p_reqParamMap
     * 		@return
     * 		@throws Exception
     */
    Map<String, Object> selectMacLoginChk(Map<String, Object> pReqParamMap) ;


    /**
     * <PRE>
     * 1. MethodName: selectAuthUseYN
     * 2. ClassName	: LoginMapper
     * 3. Commnet	:
     * 4. 작성자	: Administrator
     * 5. 작성일	: 2015. 9. 23. 오후 4:23:12
     * </PRE>
     * 		@return Map<String, Object>
     * 		@param p_reqParamMap
     * 		@return
     * 		@throws Exception
     */
    Map<String, Object> selectAuthUseYN(Map<String, Object> pReqParamMap) ;
  
    
    /**
     * <PRE>
     * 1. MethodName: selectMacChkInfo
     * 2. ClassName	: LoginMapper
     * 3. Commnet	:
     * 4. 작성자	: Administrator
     * 5. 작성일	: 2015. 9. 23. 오후 4:23:12
     * </PRE>
     * 		@return Map<String, Object>
     * 		@param p_reqParamMap
     * 		@return
     * 		@throws Exception
     */
    List<?> selectMacChkInfo() ;

    /**
     * <PRE>
     * 1. MethodName: updateOtp
     * 2. ClassName	: LoginMapper
     * 3. Commnet	:
     * 4. 작성자	: Administrator
     * 5. 작성일	: 2014. 9. 25. 오후 3:58:15
     * </PRE>
     * 		@return int
     * 		@param p_reqParamMap
     * 		@return
     * 		@throws Exception
     */
    int  updateOtp(Map<String, Object> pReqParamMap) ;
    
    /**
     * <PRE>
     * 1. MethodName: updatePassInfo
     * 2. ClassName	: LoginMapper
     * 3. Commnet	:
     * 4. 작성자	: Administrator
     * 5. 작성일	: 2014. 9. 25. 오후 3:58:17
     * </PRE>
     * 		@return int
     * 		@param p_reqParamMap
     * 		@return
     * 		@throws Exception
     */
    int  updatePassInfo(Map<String, Object> pReqParamMap) ;

    /**
     * <PRE>
     * 1. MethodName: getUsrMskYn
     * 2. ClassName	: LoginMapper
     * 3. Commnet	:
     * 4. 작성자	: Administrator
     * 5. 작성일	: 
     * </PRE>
     * 		@return String
     * 		@param userId
     * 		@return
     * 		@throws Exception
     */
    String getUsrMskYn(String userId);
    
    /*20200810 매니저정보 / 정지고객 정보*/
	List<?> getUpMngTel(Map<String, Object> pReqParamMap) ;
	
    int  updateUsrStatusReq(Map<String, Object> pReqParamMap) ;
   
    /*20240813 패스워드 초기화 계정정보 확인*/
    Map<String, Object> selectUsrChk(Map<String, Object> pReqParamMap) ;
    /*20240813 OTP인증 확인*/
    List<?> selectUsrOtpChk(Map<String, Object> pReqParamMap) ;
    /*20240813 대상 확인*/
    int selectUsrPwdReset(Map<String, Object> pReqParamMap) ;
    /*20240813 패스워드 초기화 저장*/
    void insertUsrPwdReset(Map<String, Object> pReqParamMap) ;
    /*20260128 OTP 체크 횟수 저장*/
    void updateUserOtpChk(Map<String, Object> pReqParamMap) ;
}
