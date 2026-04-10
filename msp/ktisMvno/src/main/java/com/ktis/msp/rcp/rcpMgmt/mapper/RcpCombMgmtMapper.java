package com.ktis.msp.rcp.rcpMgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.rcp.rcpMgmt.vo.RcpCombVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("rcpCombMgmtMapper")
public interface RcpCombMgmtMapper {

	List<?> getRcpCombMgmtList(Map<String, Object> param);

	void updateRcpCombRslt(RcpCombVO rcpCombVO);
	
	String selectMsgTelNo(int reqSeq);

	List<?> getRcpCombMgmtInfo(Map<String, Object> param);
	
	List<?> getRcpCombCntrInfo(Map<String, Object> param);
	
	List<?> getRcpCombRelationRateCdInfo(Map<String, Object> param);
	
	List<?> getRcpCombParentRateCdInfo(Map<String, Object> param);
	
	void insertRcpCombMgmtInfo(RcpCombVO rcpCombVO);
	
	void updateRcpCombMgmtInfo(RcpCombVO rcpCombVO);

	RcpCombVO getCombRateMappByRateCd(String pRateCd);
}
