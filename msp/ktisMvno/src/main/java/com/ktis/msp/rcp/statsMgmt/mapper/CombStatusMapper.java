package com.ktis.msp.rcp.statsMgmt.mapper;


import com.ktis.msp.rcp.statsMgmt.vo.CombStatusVo;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import java.util.List;

@Mapper("CombStatusMapper")
public interface CombStatusMapper {

	List<EgovMap> selectCombStatusList(CombStatusVo searchVO);

}
