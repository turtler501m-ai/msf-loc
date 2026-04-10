package com.ktis.msp.rcp.rcpMgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.rcp.rcpMgmt.vo.RcpSelfSocFailVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * @Class Name : RcpSelfSocFailMapper
 * @Description : 요금제 셀프변경 실패 고객 관리 mapper
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2021.11.16  권오승 최초생성
 * @
 * @author : 권오승
 * @Create Date : 2021. 11. 16.
 */

@Mapper("rcpSelfSocFailMapper")
public interface RcpSelfSocFailMapper {
	
	/**
	 * @Description : 요금제 셀프변경 실패 고객 조회
	 * @Param  : rcpSelfSocFailVo
	 * @Return : List<?>
	 * @Author : 권오승
	 * @Create Date : 2021. 11. 16.
	 */
	List<RcpSelfSocFailVO> getRcpSelfSocFailList(RcpSelfSocFailVO rcpSelfSocFailVo);
	
	
	/**
	 * @Description : 요금제 셀프변경 실패 고객 처리상세 조회
	 * @Param  : rcpSelfSocFailVo
	 * @Return : List<?>
	 * @Author : 권오승
	 * @Create Date : 2021. 11. 17.
	 */
	List<?> getRcpSelfSocFailDetail(RcpSelfSocFailVO rcpSelfSocFailVo);
	
	/**
	 * 요금제 셀프변경 실패 고객 삼당뭔 처리 프로세스
	 *
	 * @param param
	 * @throws Exception
	 */
	public void updateSelfSocFailProc(RcpSelfSocFailVO rcpSelfSocFailVo);
	
	/**
	 * @Description : 요금제 셀프변경 실패 고객 엑셀다운로드
	 * @Param  : rcpSelfSocFailVo
	 * @Return : List<?>
	 * @Author : 권오승
	 * @Create Date : 2021. 11. 16.
	 */
	List<RcpSelfSocFailVO> getRcpSelfSocFailListExcel(RcpSelfSocFailVO rcpSelfSocFailVo);

	/**
	 * @Description (재처리 금지 조건 체크) 1. 고객 계약번호 / 이벤트 코드 / 부가서비스 확인
	 * @Param : searchVO
	 * @Return : Map<String, String>
	 * @Author : hsy
	 * @CreateDate : 2023.11.07
	 */
	Map<String, String> getCstmrChk(RcpSelfSocFailVO searchVO);

	/**
	 * @Description (재처리 금지 조건 체크) 2. 고객 SUB_INFO 정보 확인
	 * @Param : String
	 * @Return : Map<String, String>
	 * @Author : hsy
	 * @CreateDate : 2023.11.07
	 */
	Map<String,String> getCstmrSubInfo(String contractNum);

	/**
	 * @Description (재처리 금지 조건 체크) 3. 현재 가입된 프로모션 확인
	 * @Param : String
	 * @Return : Map<String, String>
	 * @Author : hsy
	 * @CreateDate : 2023.11.08
	 */
	Map<String,String> getCstmrPrmtId(String ncn);

	/**
	 * @Description (재처리 금지 조건 체크) 6. 부가서비스 성공여부 / 재처리 횟수 / 재처리 가능일 체크
	 * @Param : searchVO
	 * @Return : List<EgovMap>
	 * @Author : hsy
	 * @CreateDate : 2023.11.08
	 */
	List<EgovMap> getSocRtyChkList(RcpSelfSocFailVO searchVO);

	/**
	 * @Description 요금제 셀프 변경 실패 재처리 시퀀스 생성
	 * @Return : String
	 * @Author : hsy
	 * @CreateDate : 2023.11.08
	 */
	String getRtySeq();

	/**
	 * @Description 요금제 셀프 변경 실패 재처리 이력 쌓기
	 * @Param : searchVO
	 * @Return : int
	 * @Author : hsy
	 * @CreateDate : 2023.11.08
	 */
	int insertRtySocFail(RcpSelfSocFailVO searchVO);
}
