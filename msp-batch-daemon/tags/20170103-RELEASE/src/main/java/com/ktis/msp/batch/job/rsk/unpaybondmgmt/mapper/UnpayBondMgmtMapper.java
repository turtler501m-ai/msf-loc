package com.ktis.msp.batch.job.rsk.unpaybondmgmt.mapper;

import java.util.List;

import com.ktis.msp.batch.job.rsk.unpaybondmgmt.vo.UnpayBondMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("unpayBondMgmtMapper")
public interface UnpayBondMgmtMapper {
	
	/**
	 * 미납채권상세 엑셀다운로드
	 * @param unpayBondMgmtVO
	 * @return
	 */
	public List<?> getUnpayBondDtlListEx(UnpayBondMgmtVO unpayBondMgmtVO);
	
	/**
	 * 미납채권자료생성
	 * @return
	 */
	public void callUnpdBondSttc();
	
}
