package com.ktis.msp.org.termssendmgmt.mapper;

import java.util.List;

import com.ktis.msp.org.termssendmgmt.vo.TermsSendVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("termsSendMgmtMapper")
public interface TermsSendMgmtMapper {
	/**
	 * 약관발송대상자 리스트 조회
	 * @param termsSendVO
	 * @return
	 */
	List<?> getTermsSendTrgt(TermsSendVO termsSendVO);
	
	/**
	 * 약관 메일발송 결과 업데이트
	 * @param termsSendVO
	 * @return
	 */
	int updateResultYn(TermsSendVO termsSendVO);
	
	/**
	 * 약관메일발송대상자 엑셀 다운로드
	 * @param termsSendVO
	 * @return
	 */
	List<?> termsSendListEx(TermsSendVO termsSendVO);
	
	/**
	 * 약관메일발송대상자 추출
	 * @return
	 */
	int insertTermsSendTrgt();
}
