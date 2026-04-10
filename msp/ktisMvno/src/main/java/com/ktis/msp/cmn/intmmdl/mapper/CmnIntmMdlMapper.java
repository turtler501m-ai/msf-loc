/**
 * 
 */
package com.ktis.msp.cmn.intmmdl.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.cmn.intmmdl.vo.CmnIntmMdlVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : CmnIntmMdlMapper
 * @Description : 제품관리
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2015. 7. 28. 심정보 최초생성
 * @
 * @author : 심정보
 * @Create Date : 2015. 7. 28.
 */
@Mapper("cmnIntmMdlMapper")
public interface CmnIntmMdlMapper {

	/**
	 * @Description : 대표모델 리스트 조회
	 * @Param  : cmnIntmMdlVO
	 * @Return : List<?>
	 * @Author : 심정보
	 * @Create Date : 2015. 7. 28.
	 */
	List<?> selectIntmMdlList(CmnIntmMdlVO cmnIntmMdlVO);

	/**
	 * 대표모델 리스트 엑셀다운로드
	 */
	List<?> selectIntmMdlListExcel(Map<String, Object> param);
	
	/**
	 * @Description : 색상모델 리스트 조회
	 * @Param  : cmnIntmMdlVO
	 * @Return : List<?>
	 * @Author : 심정보
	 * @Create Date : 2015. 7. 29.
	 */
	List<?> selectIntmColrMdlList(CmnIntmMdlVO cmnIntmMdlVO);

	/**
	 * 색상모델 리스트 엑셀다운로드
	 */
	List<?> selectIntmColrMdlListExcel(Map<String, Object> param);
	
	/**
	 * @Description : 대표모델명 조회
	 * @Param  : CmnIntmMdlVO
	 * @Return : List<?>
	 * @Author : 심정보
	 * @Create Date : 2015. 7. 30.
	 */
	List<?> selectRprsPrdtNm(CmnIntmMdlVO cmnIntmMdlVO);
	
	/**
	 * @Description : 대표모델ID 중복체크
	 * @Param  : CmnIntmMdlVO
	 * @Return : int
	 * @Author : 심정보
	 * @Create Date : 2015. 7. 30.
	 */
	int isExistRprsPrdtId(CmnIntmMdlVO cmnIntmMdlVO);

	/**
	 * @Description : 모델코드(대표) 중복체크
	 * @Param  : CmnIntmMdlVO
	 * @Return : int
	 * @Author : 심정보
	 * @Create Date : 2015. 7. 30.
	 */
	int isExistRprsPrdtCode(CmnIntmMdlVO cmnIntmMdlVO);	
	
	/**
	 * @Description : 대표모델 등록
	 * @Param  : CmnIntmMdlVO
	 * @Return : int
	 * @Author : 심정보
	 * @Create Date : 2015. 7. 28.
	 */
	int insertIntmMdl(CmnIntmMdlVO cmnIntmMdlVO);

	/**
	 * @Description : 대표모델 수정
	 * @Param  : CmnIntmMdlVO
	 * @Return : int
	 * @Author : 심정보
	 * @Create Date : 2015. 7. 31.
	 */
	int updateIntmMdl(CmnIntmMdlVO cmnIntmMdlVO);

	/**
	 @Description : 대표모델 단종일자 수정
	 * @Param  : CmnIntmMdlVO
	 * @Return : int
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 26.
	 */
	int updateIntmMdlPrdtDt(CmnIntmMdlVO cmnIntmMdlVO);
	
	/**
	 * @Description : 색상모델ID 중복체크
	 * @Param  : CmnIntmMdlVO
	 * @Return : int
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 3.
	 */
	int isExistPrdtId(CmnIntmMdlVO cmnIntmMdlVO);
	
	/**
	 * @Description : 색상모델 등록
	 * @Param  : CmnIntmMdlVO
	 * @Return : int
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 3.
	 */
	int insertIntmColrMdl(CmnIntmMdlVO cmnIntmMdlVO);

	/**
	 * @Description : 색상모델 수정
	 * @Param  : CmnIntmMdlVO
	 * @Return : int
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 3.
	 */
	int updateIntmColrMdl(CmnIntmMdlVO cmnIntmMdlVO);

	/**
	 * @Description : 색상모델 팝업 리스트 조회
	 * @Param  : CmnIntmMdlVO
	 * @Return : List<?>
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 3.
	 */
	List<?> intmColrMdlPopUpList(CmnIntmMdlVO cmnIntmMdlVO);










}
