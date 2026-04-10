package com.ktis.msp.cmn.fileup2.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * <PRE>
 * 1. ClassName	: 
 * 2. FileName	: FileUp2Mapper.java
 * 3. Package	: com.ktis.msp.cmn.fileup2.mapper
 * 4. Commnet	: 
 * 5. 작성자	: Administrator
 * 6. 작성일	: 2014. 9. 25. 오후 3:56:53
 * </PRE>
 */
@Mapper("fileUp2Mapper")
public interface FileUp2Mapper {
	
    /**
	 * 제조사 LIST 항목을 가져온다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 목록
	 * @exception Exception
	 */

	
	/**
	 * <PRE>
	 * 1. MethodName: insertCmnFileUpldMgmtMst
	 * 2. ClassName	: FileUp2Mapper
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:56:54
	 * </PRE>
	 * 		@return int
	 * 		@param pRequestParamMap
	 * 		@return
	 * 		@throws Exception
	 */
	int  insertCmnFileUpldMgmtMst(Map<String, Object> pReqParamMap) ;

	int  insertFileMgmtMst(Map<String, Object> pReqParamMap) ;
	
	List<?>   getFileMgmtMstId(Map<String, Object> pReqParamMap) ;
	
	
	int  deleteFileMgmtById(Map<String, Object> pReqParamMap) ;
	
}
