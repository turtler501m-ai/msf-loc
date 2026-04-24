package com.ktis.msp.batch.job.lgs.othercompmgmt.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name  : OtherCompInvMapper.java
 * @Description : OtherCompInvMapper.Class
 * @Modification Information
 * @
 * @  수정일	  	수정자			  수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2015.07.29  TREXSHIN		  최초생성
 * @
 * @author TREXSHIN
 * @since 2015.07.29
 * @version 1.0
 */

@Mapper("otherCompInvMapper")
public interface OtherCompInvMapper {

	int insertOtcpDayInvtr();
	
}
