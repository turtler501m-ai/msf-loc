package com.ktis.msp.cmn.masking.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.base.mvc.BaseVo;
import com.ktis.msp.cmn.masking.vo.MaskingVO;

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
	 * 제조사 LIST 항목을 가져온다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 목록
	 * @exception Exception
	 */
//	List<?> selectX3X(X3XVo mnfctMgmtVo) throws Exception;
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
    List<?> selectListByBaseVo(BaseVo baseVo) ;
	List<?> selectOnlyList(Map<String, Object> pReqParamMap) ;
	
	List<MaskingVO> selectListNotEgovMap(Map<String, Object> pReqParamMap) ;
}
