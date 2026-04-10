package com.ktis.msp.rsk.grntInsrMgmt.mapper;

import java.util.List;

import com.ktis.msp.rsk.grntInsrMgmt.vo.GrntInsrMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("grntInsrMgmtMapper")
public interface GrntInsrMgmtMapper {
	/**
	 * 보증보험청구 조회
	 * @param grntInsrMgmtVO
	 * @return
	 */
	public List<?> getGrntInsrList(GrntInsrMgmtVO grntInsrMgmtVO);
	
	/**
	 * 보증보험청구조회 엑셀다운로드
	 * @param grntInsrMgmtVO
	 * @return
	 */
	public List<?> getGrntInsrListEx(GrntInsrMgmtVO grntInsrMgmtVO);
}
