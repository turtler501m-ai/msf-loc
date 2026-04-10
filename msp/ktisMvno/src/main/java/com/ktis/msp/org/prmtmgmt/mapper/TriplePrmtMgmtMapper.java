package com.ktis.msp.org.prmtmgmt.mapper;

import java.util.List;

import com.ktis.msp.gift.prmtmgmt.vo.GiftPrmtMgmtSubVO;
import com.ktis.msp.org.prmtmgmt.vo.ChrgPrmtMgmtSubVO;
import com.ktis.msp.org.prmtmgmt.vo.ChrgPrmtMgmtVO;
import com.ktis.msp.org.prmtmgmt.vo.TriplePrmtMgmtVO;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("triplePrmtMgmtMapper")
public interface TriplePrmtMgmtMapper {
	
	// 트리플할인 목록 조회
	List<TriplePrmtMgmtVO> getTriplePrmtList(TriplePrmtMgmtVO triplePrmtMgmtVO) throws EgovBizException;
	
	// 트리플할인 요금제 목록 조회
	List<TriplePrmtMgmtVO> getTriplePrmtSocList(TriplePrmtMgmtVO triplePrmtMgmtVO) throws EgovBizException;
	
	// 트리플할인 부가서비스 목록 조회
	List<TriplePrmtMgmtVO> getTriplePrmtAddList(TriplePrmtMgmtVO triplePrmtMgmtVO) throws EgovBizException;
	
	// 트리플할인인 중복 체크
	List<TriplePrmtMgmtVO> getTriplePrmtDupByInfo(TriplePrmtMgmtVO triplePrmtMgmtVO) throws EgovBizException;
		
	// 트리플할인 마스터 추가
	int insertMspTriplePrmt(TriplePrmtMgmtVO triplePrmtMgmtVO) throws EgovBizException;
			
	// 트리플할인 요금제 추가
	int insertMspTriplePrmtSoc(TriplePrmtMgmtVO triplePrmtMgmtVO) throws EgovBizException;
		
	// 트리플할인 부가서비스 추가
	int insertMspTriplePrmtAdd(TriplePrmtMgmtVO triplePrmtMgmtVO) throws EgovBizException;
	
	// 트리플할인 정보 변경
	int updTriplePrmtByInfo(TriplePrmtMgmtVO triplePrmtMgmtVO) throws EgovBizException;
	
	//요금제선택 엑셀 업로드
	List<TriplePrmtMgmtVO> getExcelUpSocList(TriplePrmtMgmtVO triplePrmtMgmtVO) throws EgovBizException;
	
}
