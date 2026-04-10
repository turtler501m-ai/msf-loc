package com.ktis.msp.pps.datamgmt.mapper;
import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : ppsHdofcDataMgmtMapper
 * @Description :   데이타관리 Mapper
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@Mapper("PpsAgencyDataMgmtMapper")
public interface PpsAgencyDataMgmtMapper {
	
	/**
	 * 대리점 실시간선불정산대상 목록조회
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getAgencyDataInfoMgmtList(Map<String, Object> pRequestParamMap);
	
	/**
	 * 실시간선불정산대상 엑셀출력
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	List<?> getAgencyDataInfoMgmtListExcel(Map<String, Object> pRequestParamMap);

}
