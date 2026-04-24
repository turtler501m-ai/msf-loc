package com.ktis.msp.batch.job.lgs.agpomgmt.mapper;

import com.ktis.msp.batch.job.lgs.agpomgmt.vo.AgncyPoOutVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name  : AgncyPoOutMapper.java
 * @Description : AgncyPoOutMapper.Class
 * @Modification Information
 * @
 * @  수정일	  수정자			  수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2014.08.11  IB		  최초생성
 * @
 * @author IB
 * @since 2014.08.11
 * @version 1.0
 */

@Mapper("agncyPoOutMapper")
public interface AgncyPoOutMapper {
	
	/**
	 * 수불수량등록
	 * @param  : AgncyPoOutVO
	 * @Return : int
	 */
	int regInOutPrdt(AgncyPoOutVO vo);
	
	/**
	 * 수불수량등록2
	 * @param  : AgncyPoOutVO
	 * @Return : int
	 */
	int regInOutMst2(AgncyPoOutVO vo);
	
	/**
	 * 수불제품등록
	 * @param  : AgncyPoOutVO
	 * @Return : int
	 */
	int regInOutPrdtSrl(AgncyPoOutVO vo);
	
	/**
	 * 수불관계등록
	 * @param  : AgncyPoOutVO
	 * @Return : int
	 */
	int regInOutRel(AgncyPoOutVO vo);
	
}