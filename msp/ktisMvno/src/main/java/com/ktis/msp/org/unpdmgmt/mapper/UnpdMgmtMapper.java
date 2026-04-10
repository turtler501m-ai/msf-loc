package com.ktis.msp.org.unpdmgmt.mapper;

import java.util.List;

import com.ktis.msp.org.unpdmgmt.vo.UnpdMgmtVo;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : UnpdMgmtMapper
 * @Description : 미납요금 Mapper
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2015.07.21 김연우 최초생성
 * @
 * @author : 김연우
 * @Create Date : 2015. 7. 21.
 */
@Mapper("unpdMgmtMapper")
public interface UnpdMgmtMapper {
	
	/**
	 * @Description : 미납요금 LIST 항목을 가져온다.
	 * @Param  : UnpdMgmtVo
	 * @Return : List<?>
	 * @Author : 김연우
	 * @Create Date : 2015. 7. 21.
	 */
	List<?> getUnpdMgmtList(UnpdMgmtVo searchVO);
	
	/**
	 * @Description : 미납요금 LIST 항목을 가져온다. 엑셀용
	 * @Param  : UnpdMgmtVo
	 * @Return : List<?>
	 * @Author : 김연우
	 * @Create Date : 2015. 7. 21.
	 */
	List<?> getUnpdMgmtListExcel(UnpdMgmtVo searchVO);
	

	
}
