package com.ktis.msp.org.prmtmgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.org.prmtmgmt.vo.TripleHistMgmtVO;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("tripleHistMgmtMapper")
public interface TripleHistMgmtMapper {
	
	// 트리플이력 목록 조회
	List<?> getTripleHistList(TripleHistMgmtVO tripleHistMgmtVO) throws EgovBizException;
	// 트리플이력 목록 엑셀 다운로드
	List<?> getTripleHistListExcel(TripleHistMgmtVO tripleHistMgmtVO) throws EgovBizException;
	// 트리플이력 직접등록 계약번호 조회
	List<Map<String, Object>> getTripleHistContractNum(TripleHistMgmtVO tripleHistMgmtVO) throws EgovBizException;
	// 트리플이력 직접등록 요금제 조회
	List<?> getTripleHistRate(TripleHistMgmtVO tripleHistMgmtVO) throws EgovBizException;
	// 트리플이력 직접등록 요금제 조회
	List<Map<String, Object>> getTriplePrmtData(TripleHistMgmtVO tripleHistMgmtVO) throws EgovBizException;
	// 트리플이력 직접등록 부가서비스 조회
	List<?> getTripleHistAddition(TripleHistMgmtVO tripleHistMgmtVO) throws EgovBizException;
	// 트리플이력 validation 체크
	int getTripleHistChk(TripleHistMgmtVO tripleHistMgmtVO) throws EgovBizException;
	// 트리플이력 직접등록 저장
	void insertTripleHist(TripleHistMgmtVO tripleHistMgmtVO) throws EgovBizException;
	// 트리플이력 직접등록 수정
	void updateTripleHist(TripleHistMgmtVO tripleHistMgmtVO) throws EgovBizException;
	// 트리플이력 처리상태변경
	void updateTripleHistStatus(TripleHistMgmtVO tripleHistMgmtVO) throws EgovBizException;
}
