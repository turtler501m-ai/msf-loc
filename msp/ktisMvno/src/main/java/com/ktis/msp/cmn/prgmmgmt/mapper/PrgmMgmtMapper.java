package com.ktis.msp.cmn.prgmmgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.cmn.prgmmgmt.vo.PrgmMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("PrgmMgmtMapper")
public interface PrgmMgmtMapper {
	List<Map<String, Object>> getPrgmMgmtHst(PrgmMgmtVO vo);
}
