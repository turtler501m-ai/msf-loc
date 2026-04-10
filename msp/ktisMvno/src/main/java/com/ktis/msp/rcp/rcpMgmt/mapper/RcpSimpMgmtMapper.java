package com.ktis.msp.rcp.rcpMgmt.mapper;

import java.util.List;

import com.ktis.msp.rcp.rcpMgmt.vo.OsstReqVO;
import com.ktis.msp.rcp.rcpMgmt.vo.OsstResVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpDetailVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpRateVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpSimpVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpTlphNoVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("RcpSimpMgmtMapper")
public interface RcpSimpMgmtMapper {
	
	public List<RcpRateVO> getRcpSimpRateList(RcpRateVO rcpRateVO);
	
	public List<RcpRateVO> getExclusiveRateList();
	
	public RcpTlphNoVO getRcpSimpRsvTlphNoByInfo(RcpTlphNoVO rcpTlphNoVO);
	
	int updMcpMovePayClaByInfo(RcpSimpVO simpVO);
	
	public OsstResVO getOsstRsltMsg(OsstReqVO vo);
			
	public List<?> getRcpSmsTemplateList(RcpDetailVO searchVO);
	
	List<?> getRcpSmsSendList(RcpSimpVO vo);
	
	List<?> getRcpSmsSendListOld(RcpSimpVO vo);
}
