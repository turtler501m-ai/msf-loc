package com.ktis.msp.cmn.favrmenu.mapper;

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
 * 2. FileName	: FavrMenuMapper.java
 * 3. Package	: com.ktis.msp.cmn.favrmenu.mapper
 * 4. Commnet	: 
 * 5. 작성자	: Administrator
 * 6. 작성일	: 2014. 9. 25. 오후 3:53:31
 * </PRE>
 */
@Mapper("favrMenuMapper")
public interface FavrMenuMapper {
	
	/**
	 * <PRE>
	 * 1. MethodName: favrMenuList
	 * 2. ClassName	: FavrMenuMapper
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:53:33
	 * </PRE>
	 * 		@return List<?>
	 * 		@param p_reqParamMap
	 * 		@return
	 */
	List<?> favrMenuList(Map<String, Object> pReqParamMap);

	/**
	 * <PRE>
	 * 1. MethodName: grpIdList
	 * 2. ClassName	: FavrMenuMapper
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:53:35
	 * </PRE>
	 * 		@return List<?>
	 * 		@return
	 */
	List<?> grpIdList();
	
	
	/**
	 * <PRE>
	 * 1. MethodName: favrMenuInsert
	 * 2. ClassName	: FavrMenuMapper
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:53:37
	 * </PRE>
	 * 		@return int
	 * 		@param p_reqParamMap
	 * 		@return
	 */
	int favrMenuInsert(Map<String, Object> pReqParamMap);
	
	
	/**
	 * <PRE>
	 * 1. MethodName: favrMenuDelete
	 * 2. ClassName	: FavrMenuMapper
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:53:38
	 * </PRE>
	 * 		@return int
	 * 		@param p_reqParamMap
	 * 		@return
	 */
	int favrMenuDelete(Map<String, Object> pReqParamMap);

    
}


 


