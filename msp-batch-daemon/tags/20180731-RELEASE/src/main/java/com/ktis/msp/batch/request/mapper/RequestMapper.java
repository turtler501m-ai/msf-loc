package com.ktis.msp.batch.request.mapper;

import java.util.List;

import com.ktis.msp.batch.manager.vo.BatchCommonVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name  : RequestMapper.java
 * @Description : RequestMapper.Class
 * @Modification Information
 * @
 * @  수정일	  수정자			  수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2016.06.13   TREXSHIN		  최초생성
 * @
 * @author TREXSHIN
 * @since 2016.06.13
 * @version 1.0
 */

@Mapper("requestMapper")
public interface RequestMapper {
	
	List<BatchCommonVO> getRequestList();
	
	int updateRequest(BatchCommonVO batchCommonVO);
	
}