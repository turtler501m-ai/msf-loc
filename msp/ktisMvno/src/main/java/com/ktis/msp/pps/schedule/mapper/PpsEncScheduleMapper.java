package com.ktis.msp.pps.schedule.mapper;
import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : PpsEncScheduleMapper
 * @Description :   파일암호화 Mapper
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2016.04.05  최초생성
 * @
 * @author : 
 * @Create Date : 2016. 4. 05.
 */
@Mapper("PpsEncScheduleMapper")
public interface PpsEncScheduleMapper {
	
	/**
	 * 서식지 파일 암호화
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	void ppsEncScheduleProc(Map<String, Object> pRequestParamMap);
	
}
