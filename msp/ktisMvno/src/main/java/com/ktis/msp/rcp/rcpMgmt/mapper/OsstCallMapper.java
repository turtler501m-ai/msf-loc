package com.ktis.msp.rcp.rcpMgmt.mapper;

import com.ktis.msp.rcp.rcpMgmt.vo.OsstReqVO;
import com.ktis.msp.rcp.rcpMgmt.vo.OsstResVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpDetailVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.Map;

@Mapper("osstCallMapper")
public interface OsstCallMapper {
	/**
	 * 번호예약/취소 이력
	 * @param vo
	 */
	void insertRequestOsstHist(OsstReqVO vo);
	
	/**
	 * 최종결과조회
	 * @param vo
	 * @return
	 */
	OsstResVO getTcpResult(OsstReqVO vo);
	
	/**
	 * 번호이동 사전인증
	 * @param vo
	 * @return
	 */
	OsstResVO getNpReqInfo(OsstReqVO vo);
	
	int isOrgChk(String requestKey);


	/**
	 * 기적용 테이블 INSERT
	 * @param vo
	 * @return int
	 */
	int insertDisApd(RcpDetailVO vo);

	int knoteScanInfoChk(String frmpapId);

	String getKnoteKtOrgId(String orgnId);

	//K-Note 조직 조회
	Map<String, Object> getKnoteOrgn(Map<String, String> param);

	//K-Note 코드 조회
	Map<String, Object> getKnoteCode(Map<String, String> param);
}
