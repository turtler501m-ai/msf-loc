package com.ktis.msp.batch.manager.common.auth;

import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * <PRE>
 * 1. ClassName	: 
 * 2. FileName	: AuthChkMapper.java
 * 3. Package	: com.ktis.msp.cmn.auth
 * 4. Commnet	: 
 * 5. 작성자	: Administrator
 * 6. 작성일	: 2014. 9. 25. 오후 3:59:13
 * </PRE>
 */
@Mapper("authChkMapper")
public interface AuthChkMapper {
	
	/**
	 * <PRE>
	 * 1. MethodName: selectList
	 * 2. ClassName	: AuthChkMapper
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:59:15
	 * </PRE>
	 * 		@return List<?>
	 * 		@param p_reqParamMap
	 * 		@return
	 * 		@throws Exception
	 */
	int chkUsrGrpAuth(Map<String, Object> pReqParamMap) ;	
	
}
