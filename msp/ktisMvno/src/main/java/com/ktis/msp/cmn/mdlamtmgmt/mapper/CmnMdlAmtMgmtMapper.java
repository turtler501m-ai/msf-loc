/**
 * 
 */
package com.ktis.msp.cmn.mdlamtmgmt.mapper;

import java.util.List;

import com.ktis.msp.cmn.mdlamtmgmt.vo.CmnMdlAmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : CmnMdlAmtMgmtMapper
 * @Description : 모델 단가 관리
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2015.08.05 심정보 최초생성
 * @
 * @author : 심정보
 * @Create Date : 2015. 8. 5.
 */
@Mapper("cmnMdlAmtMgmtMapper")
public interface CmnMdlAmtMgmtMapper {

	/**
	 * @Description : 모델단가 대표모델 리스트 조회
	 * @Param  : CmnMdlAmtVO
	 * @Return : List<?>
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 5.
	 */
	List<?> selectIntmMdlAmtList(CmnMdlAmtVO cmnMdlAmtVO);

	/**
	 * @Description : 입고 단가 이력 리스트 조회
	 * @Param  : CmnMdlAmtVO
	 * @Return : List<?>
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 5.
	 */
	List<?> selectMnfctAmtHisList(CmnMdlAmtVO cmnMdlAmtVO);


	/**
	 * @Description : 입고 단가 등록 전 기등록 조회
	 * @Param  : CmnMdlAmtVO
	 * @Return : List<?>
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 7.
	 */
	CmnMdlAmtVO selectOldMnfctAmt(CmnMdlAmtVO cmnMdlAmtVO);
	
	/**
	 * @Description : 입고단가 기등록건 수정 (미래데이터 수정)
	 * @Param  : CmnMdlAmtVO
	 * @Return : int
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 12.
	 */
	int updateMnfctAmt(CmnMdlAmtVO cmnMdlAmtVO);
	
	/**
	 * @Description : 최종 직전 기등록 데이터 만료일시 수정 (미래데이터 수정)
	 * @Param  : CmnMdlAmtVO
	 * @Return : int
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 26.
	 */
	int updateOldMnfctAmtExpire(CmnMdlAmtVO cmnMdlAmtVO);

	
	/**
	 * @Description : 입고단가 기등록건 이력으로 수정
	 * @Param  : CmnMdlAmtVO
	 * @Return : int
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 7.
	 */
	int updateOldMnfctAmt(CmnMdlAmtVO cmnMdlAmtVO);
	
	/**
	 * @Description : 입고단가 등록
	 * @Param  : CmnMdlAmtVO
	 * @Return : int
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 7.
	 */
	int insertMnfctAmt(CmnMdlAmtVO cmnMdlAmtVO);

	
	/**
	 * @Description : 출고 단가 이력 리스트 조회
	 * @Param  : CmnMdlAmtVO
	 * @Return : List<?>
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 5.
	 */
	List<?> selectHndstAmtHisList(CmnMdlAmtVO cmnMdlAmtVO);

	//NEW
	/**
	 * @Description : 출고 단가 등록 전 기등록 조회
	 * @Param  : CmnMdlAmtVO
	 * @Return : CmnMdlAmtVO
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 10.
	 */
	CmnMdlAmtVO selectOldHndstAmt(CmnMdlAmtVO cmnMdlAmtVO);

	/**
	 * @Description : 출고단가 기등록건 수정 (미래데이터 수정)
	 * @Param  : CmnMdlAmtVO
	 * @Return : int
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 12.
	 */
	int updateHndstAmt(CmnMdlAmtVO cmnMdlAmtVO);
	
	/**
	 * @Description : 최종 직전 기등록 데이터 만료일시 수정 (미래데이터 수정)
	 * @Param  : CmnMdlAmtVO
	 * @Return : int
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 26.
	 */
	int updateOldHndstAmtExpire(CmnMdlAmtVO cmnMdlAmtVO);


	
	/**
	 * @Description : 출고단가 기등록건 이력으로 수정
	 * @Param  : CmnMdlAmtVO
	 * @Return : int
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 10.
	 */
	int updateOldHndstAmt(CmnMdlAmtVO cmnMdlAmtVO);

	/**
	 * @Description : 출고단가 등록
	 * @Param  : CmnMdlAmtVO
	 * @Return : int
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 10.
	 */
	int insertHndstAmt(CmnMdlAmtVO cmnMdlAmtVO);

	/**
	 * @Description : 대표모델 출고단가 찾기 팝업 리스트 조회 - 단종모델 제외
	 * @Param  : CmnMdlAmtVO
	 * @Return : List<?>
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 10.
	 */
	List<?> selectRprsHndstAmtHisList(CmnMdlAmtVO cmnMdlAmtVO);
	
	/**
	 * @Description : 대표모델 출고단가 찾기 팝업 리스트 조회 - 단종모델 포함
	 * @Param  : CmnMdlAmtVO
	 * @Return : List<?>
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 10.
	 */
	List<?> selectRprsHndstAmtAllHisList(CmnMdlAmtVO cmnMdlAmtVO);

	/**
	 * @Description : 대표모델의 중고여부 단말 찾기 팝업 리스트 조회 -- 단종모델 제외
	 * @Param  : CmnMdlAmtVO
	 * @Return : List<?>
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 10.
	 */
	List<?> selectRprsHndstOldYnHisList(CmnMdlAmtVO cmnMdlAmtVO);
	
	/**
	 * @Description : 대표모델의 중고여부 단말 찾기 팝업 리스트 조회 -- 단종모델 포함
	 * @Param  : CmnMdlAmtVO
	 * @Return : List<?>
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 10.
	 */
	List<?> selectRprsHndstOldYnAllHisList(CmnMdlAmtVO cmnMdlAmtVO);



	




}
