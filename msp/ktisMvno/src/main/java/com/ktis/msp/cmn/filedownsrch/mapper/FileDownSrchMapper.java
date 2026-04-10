package com.ktis.msp.cmn.filedownsrch.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 통계에 관한 데이터처리 매퍼 클래스
 *
 * @author  표준프레임워크센터
 * @since 2014.08.05
 * @version 1.0
 * @see <pre>
 *  == 개정이력(Modification Information) ==
 *
 *          수정일          수정자           수정내용
 *  ----------------    ------------    ---------------------------
 *   2014.08.13        임지혜          최초 생성
 *
 * </pre>
 */

/**
 * <PRE>
 * 1. ClassName	: 
 * 2. FileName	: FileDownSrchMapper.java
 * 3. Package	: com.ktis.msp.cmn.filedownsrch.mapper
 * 4. Commnet	: 
 * 5. 작성자	: Administrator
 * 6. 작성일	: 2014. 9. 25. 오후 3:54:52
 * </PRE>
 */
@Mapper("fileDownSrchMapper")
public interface FileDownSrchMapper {
	
	/**
	 * <PRE>
	 * 1. MethodName: selectList
	 * 2. ClassName	: FileDownSrchMapper
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:54:54
	 * </PRE>
	 * 		@return List<?>
	 * 		@param p_reqParamMap
	 * 		@return
	 */
	List<?> selectList(Map<String, Object> pReqParamMap);



    
}


 


