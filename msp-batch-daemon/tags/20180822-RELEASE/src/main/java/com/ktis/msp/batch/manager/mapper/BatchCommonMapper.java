package com.ktis.msp.batch.manager.mapper;

import com.ktis.msp.batch.manager.vo.BatchCommonVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name  : BatchCommonMapper.java
 * @Description : BatchCommonMapper.Class
 * @Modification Information
 * @
 * @  수정일	  수정자			  수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2016.05.16   TREXSHIN		  최초생성
 * @
 * @author TREXSHIN
 * @since 2016.05.16
 * @version 1.0
 */

@Mapper("batchCommonMapper")
public interface BatchCommonMapper {
	
	int insertBatchExecHst(BatchCommonVO vo);
	
	int updateBatchExecHst(BatchCommonVO vo);
	
	String checkBatchExecHst(BatchCommonVO vo);
	
	int getDailyBatchEndCount(BatchCommonVO vo);
	
	int getDailyBatchIngCount(BatchCommonVO vo);
	
}