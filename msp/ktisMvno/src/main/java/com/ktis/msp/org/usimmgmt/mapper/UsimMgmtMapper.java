package com.ktis.msp.org.usimmgmt.mapper;

import java.util.List;

import com.ktis.msp.org.usimmgmt.vo.UsimMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : UsimMgmtMapper
 * @Description : 유심 관리 Mapper
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2017.06.09 TREXSHIN 최초생성
 * @
 * @author : TREXSHIN
 * @Create Date : 2017.06.09.
 */

@Mapper("usimMgmtMapper")
public interface UsimMgmtMapper {
	
	/**
	 * 유심관리리스트 조회
	 * @param UsimMgmtVO
	 * @return
	 */
	List<?> getUsimMgmtList(UsimMgmtVO vo);
	
	List<?> getActiveUsimList(UsimMgmtVO vo);
	
	List<?> getActiveUsimListByExcel(UsimMgmtVO vo);
	
	List<?> getUsimHistList(UsimMgmtVO vo);
	
	int getUsimDupChk(String usimNo);
	
	int insertUsimMgmt(UsimMgmtVO vo);
	
	int updateUsimMgmt(UsimMgmtVO vo);
	
	int deleteUsimMgmt(UsimMgmtVO vo);
	
	int insertUsimHist(UsimMgmtVO vo);
	
	//2021.01.21 [SRM21011583814] M전산 제휴유심관리 內 단말기코드 항목 추가요청  단말ID 조회 추가
	String isExistRprsPrdtIdUsimMgmt(UsimMgmtVO vo);

	//int updateUsimVrfyYn(UsimMgmtVO vo);

	UsimMgmtVO getMdsIntmDlvrTxn(String usimNo);

}

