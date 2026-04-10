package com.ktis.msp.cmn.login.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * <PRE>
 * 1. ClassName	: 
 * 2. FileName	: MenuAuthMapper.java
 * 3. Package	: com.ktis.msp.cmn.login.mapper
 * 4. Commnet	: 
 * 5. 작성자	: Administrator
 * 6. 작성일	: 2014. 9. 25. 오후 3:58:23
 * </PRE>
 */
@Mapper("menuAuthMapper")
public interface MenuAuthMapper {
	

//	List<?> selectMenuAuthXXXX(Map<String, Object> pReqParamMap) ;
	
	
	/**
	 * <PRE>
	 * 1. MethodName: buttonAuthForCRUD
	 * 2. ClassName	: MenuAuthMapper
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:58:27
	 * </PRE>
	 * 		@return List<?>
	 * 		@param p_reqParamMap
	 * 		@return
	 * 		@throws Exception
	 */
	List<?> buttonAuthForCRUD(Map<String, Object> pReqParamMap) ;
	
	/**
	 * <PRE>
	 * 1. MethodName: insertLog
	 * 2. ClassName	: MenuAuthMapper
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:58:31
	 * </PRE>
	 * 		@return int
	 * 		@param p_reqParamMap
	 * 		@return
	 * 		@throws Exception
	 */
	int  insertLog(Map<String, Object> pReqParamMap) ;

	

//	List<?> usrGrpAuthForButtonXXX(Map<String, Object> pReqParamMap) ;
	
	
	/**
	 * <PRE>
	 * 1. MethodName: usrGrpAuthListForButton
	 * 2. ClassName	: MenuAuthMapper
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:58:34
	 * </PRE>
	 * 		@return List<?>
	 * 		@param p_reqParamMap
	 * 		@return
	 * 		@throws Exception
	 */
	List<?> usrGrpAuthListForButton(Map<String, Object> pReqParamMap) ;
	
	/**
	 * <PRE>
	 * 1. MethodName: chkUsrGrpAuthListForButton
	 * 2. ClassName	: MenuAuthMapper
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:58:34
	 * </PRE>
	 * 		@return List<?>
	 * 		@param p_reqParamMap
	 * 		@return
	 * 		@throws Exception
	 */
	int chkUsrGrpAuthListForButton(Map<String, Object> pReqParamMap) ;
	
	
	HashMap<String, Object>	buttonAuthChk(Map<String, Object> pReqParamMap);
	
	
	List<?> breadCrumb(Map<String, Object> pReqParamMap) ;
}
