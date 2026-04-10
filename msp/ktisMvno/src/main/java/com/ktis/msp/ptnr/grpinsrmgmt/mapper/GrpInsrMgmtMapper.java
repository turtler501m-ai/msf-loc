package com.ktis.msp.ptnr.grpinsrmgmt.mapper;

import java.util.List;

import com.ktis.msp.ptnr.grpinsrmgmt.vo.GrpInsrReqVO;
import com.ktis.msp.ptnr.grpinsrmgmt.vo.GrpInsrResVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("grpInsrMgmtMapper")
public interface GrpInsrMgmtMapper {
	
	/**
	 * 단체보험조회
	 */
	public List<?> getGrpInsrMgmtList(GrpInsrReqVO vo);
	
	/**
	 * 보험목록조회
	 */
	public List<?> getGrpInsrCdList(GrpInsrReqVO vo);
	
	/**
	 * 단체보험 등록
	 */
	public int insertGrpInsrMst(GrpInsrResVO vo);
	
	/**
	 * 단체보험 수정
	 */
	public int updateGrpInsrMst(GrpInsrResVO vo);
	
	/**
	 * 단체보험 이벤트 여부
	 */
	public int getGrpInsrYn(GrpInsrReqVO vo);
	
	/**
	 * 신청등록 단체보험 콤보조회
	 */
	public List<?> getGrpInsrCombo(GrpInsrReqVO vo);
	
}
