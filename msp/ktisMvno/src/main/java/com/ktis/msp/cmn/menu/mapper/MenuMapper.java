package com.ktis.msp.cmn.menu.mapper;

import java.util.List;
import java.util.Map;

//@Mapper("menuMapper")
/**
 * <PRE>
 * 1. ClassName	: 
 * 2. FileName	: MenuMapper.java
 * 3. Package	: com.ktis.msp.cmn.menu.mapper
 * 4. Commnet	: 
 * 5. 작성자	: Administrator
 * 6. 작성일	: 2014. 9. 25. 오후 4:00:05
 * </PRE>
 */
public interface MenuMapper {
	
    /**
	 * 제조사 LIST 항목을 가져온다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 목록
	 * @exception Exception
	 */

	/**
	 * <PRE>
	 * 1. MethodName: menuPrgmMstList
	 * 2. ClassName	: MenuMapper
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 4:00:04
	 * </PRE>
	 * 		@return List<?>
	 * 		@param p_reqParamMap
	 * 		@return
	 */
	List<?> menuPrgmMstList(Map<String, Object> pReqParamMap) ;

	
	
}
