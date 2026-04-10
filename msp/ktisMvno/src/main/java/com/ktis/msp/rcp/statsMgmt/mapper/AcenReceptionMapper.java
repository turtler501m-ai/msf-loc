package com.ktis.msp.rcp.statsMgmt.mapper;

import com.ktis.msp.rcp.statsMgmt.vo.AcenReceptionVo;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import java.util.List;
import java.util.Map;

@Mapper("AcenReceptionMapper")
public interface AcenReceptionMapper {

	List<EgovMap> getAcenReceptionList(AcenReceptionVo searchVO);

	List<EgovMap> getAcenReceptionListExcel(AcenReceptionVo searchVO);

	Map<String, String> getMpData(String contractNum);

	int completeAcenReception(AcenReceptionVo vo);

	int cancelAcenReception(AcenReceptionVo vo);

	int insertAcenReceptionHist(AcenReceptionVo vo);

}
