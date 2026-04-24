package com.ktis.msp.batch.manager.common.masking;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * <PRE>
 * 1. ClassName	: 
 * 2. FileName	: MaskingMapper.java
 * 3. Package	: com.ktis.msp.cmn.masking.mapper
 * 4. Commnet	: 
 * 5. 작성자	: Administrator
 * 6. 작성일	: 2014. 9. 25. 오후 3:59:13
 * </PRE>
 */
@Mapper("maskingMapper")
public interface MaskingMapper {
	
	/**
	 * <PRE>
	 * 1. MethodName: selectList
	 * 2. ClassName	: MaskingMapper
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:59:15
	 * </PRE>
	 * 		@return List<?>
	 * 		@param p_reqParamMap
	 * 		@return
	 * 		@throws Exception
	 */
	List<?> selectList(Map<String, Object> pReqParamMap) ;
	
	
}
