package com.ktis.msp.org.powertmntmgmt.mapper;

import java.util.List;

import com.ktis.msp.org.powertmntmgmt.vo.PowerTmntMgmtVo;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : PowerTmntMgmtMapper
 * @Description : 직권해지 Mapper
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.14 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 14.
 */
@Mapper("powerTmntMgmtMapper")
public interface PowerTmntMgmtMapper {
	
	/**
	 * @Description : 직권해지 LIST 항목을 가져온다.
	 * @Param  : PowerTmntMgmtVo
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	List<?> selectPowerTmntMgmtList(PowerTmntMgmtVo powerTmntMgmtVo);
	
	/**
	 * @Description : 직권해지 LIST 항목을 가져온다. 엑셀용
	 * @Param  : PowerTmntMgmtVo
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	List<?> selectPowerTmntMgmtListEx(PowerTmntMgmtVo powerTmntMgmtVo);
}
