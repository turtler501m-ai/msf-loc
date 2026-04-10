package com.ktis.msp.rsk.unpayBondMgmt.mapper;

import java.util.List;

import com.ktis.msp.rsk.unpayBondMgmt.vo.UnpayBondMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("unpayBondMgmtMapper")
public interface UnpayBondMgmtMapper {
	/**
	 * 미납채권관리 조회
	 * @param unpayBondMgmtVO
	 * @return
	 */
	public List<?> getUnpayBondList(UnpayBondMgmtVO unpayBondMgmtVO);
	
	/**
	 * 미납채권관리 엑셀다운로드
	 * @param unpayBondMgmtVO
	 * @return
	 */
	public List<?> getUnpayBondListEx(UnpayBondMgmtVO unpayBondMgmtVO);
	
	/**
	 * 조정금액 상세 조회
	 * @param unpayBondMgmtVO
	 * @return
	 */
	public List<?> getAdjAmntDtl(UnpayBondMgmtVO unpayBondMgmtVO);
	
	/**
	 * 미납채권상세조회
	 * @param unpayBondMgmtVO
	 * @return
	 */
	public List<?> getUnpayBondDtlList(UnpayBondMgmtVO unpayBondMgmtVO);
	
	/**
	 * 미납채권상세(조정금액상세 조회)
	 * @param unpayBondMgmtVO
	 * @return
	 */
	public List<?> getAdjRsnList(UnpayBondMgmtVO unpayBondMgmtVO);
}
