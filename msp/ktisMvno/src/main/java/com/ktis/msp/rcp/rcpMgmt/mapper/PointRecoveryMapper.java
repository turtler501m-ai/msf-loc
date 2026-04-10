package com.ktis.msp.rcp.rcpMgmt.mapper;

import com.ktis.msp.rcp.rcpMgmt.vo.PointTxnVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("PointRecoveryMapper")
public interface PointRecoveryMapper {
	
	PointTxnVO selectCustPointTxn(String requestKey);

	int updateCustPointGiveBaseBas(PointTxnVO pointTxnVO);

	int insertCustPointTxn(PointTxnVO pointTxnVO);

	int insertCustPointAcuTxnDtl(PointTxnVO pointTxnVO);

	PointTxnVO selectUsePointAmt(String requestKey);

	PointTxnVO selectUsePointAmtOpenMgmtListDtl(String contractNum);

}
