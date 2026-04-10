package com.ktis.msp.cmn.fileup.mapper;

import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * <PRE>
 * 1. ClassName	: 
 * 2. FileName	: FileUpMapper.java
 * 3. Package	: com.ktis.msp.cmn.fileup.mapper
 * 4. Commnet	: 
 * 5. 작성자	: Administrator
 * 6. 작성일	: 2014. 9. 25. 오후 3:55:31
 * </PRE>
 */
@Mapper("fileUpMapper")
public interface FileUpMapper {
	

	/**
	 * <PRE>
	 * 1. MethodName: insertCmnFileUpldMgmtMst
	 * 2. ClassName	: FileUpMapper
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:55:32
	 * </PRE>
	 * 		@return int
	 * 		@param pRequestParamMap
	 * 		@return
	 * 		@throws Exception
	 */
	int  insertCmnFileUpldMgmtMst(Map<String, Object> pReqParamMap) ;

	
	
}
