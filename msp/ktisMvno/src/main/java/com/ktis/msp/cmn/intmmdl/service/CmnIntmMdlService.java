/**
 * 
 */
package com.ktis.msp.cmn.intmmdl.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.intmmdl.mapper.CmnIntmMdlMapper;
import com.ktis.msp.cmn.intmmdl.vo.CmnIntmMdlVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * @Class Name : CmnIntmMdlService
 * @Description : 제품관리
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2015. 7. 28. 심정보 최초생성
 * @
 * @author : 심정보
 * @Create Date : 2015. 7. 28.
 */
@Service
public class CmnIntmMdlService extends BaseService {

	@Autowired
	private CmnIntmMdlMapper cmnIntmMdlMapper;
	
	public CmnIntmMdlService() {
		setLogPrefix("[cmnIntmMdlService] ");
	}

	/**
	 * @Description : 대표모델 리스트 조회
	 * @Param  : cmnIntmMdlVO
	 * @Return : List<?>
	 * @Author : 심정보
	 * @Create Date : 2015. 7. 28.
	 */
	public List<?> selectIntmMdlList(CmnIntmMdlVO cmnIntmMdlVO) {
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("제품 조회 서비스 START."));
		logger.info(generateLogMsg("Return Vo [CmnIntmMdlVO] = " + cmnIntmMdlVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> cmnIntmMdlVOList = new ArrayList<CmnIntmMdlVO>();
		
		
		cmnIntmMdlVOList = cmnIntmMdlMapper.selectIntmMdlList(cmnIntmMdlVO);
		
		return cmnIntmMdlVOList;
	}

	/**
	 * 대표모델 리스트 엑셀다운로드
	 */
	public List<?> selectIntmMdlListExcel(Map<String, Object> paramMap) {
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("대표모델 리스트 엑셀다운로드 서비스 START."));
		logger.info(generateLogMsg("================================================================="));

		logger.debug("paramMap=" + paramMap);
		
		List<EgovMap> list = (List<EgovMap>) cmnIntmMdlMapper.selectIntmMdlListExcel(paramMap);
		
		return list;
	}
	
	/**
	 * @Description : 색상모델 리스트 조회
	 * @Param  : cmnIntmMdlVO
	 * @Return : List<?>
	 * @Author : 심정보
	 * @Create Date : 2015. 7. 29.
	 */
	public List<?> selectIntmColrMdlList(CmnIntmMdlVO cmnIntmMdlVO) {
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("색상모델 리스트 조회 서비스 START."));
		logger.info(generateLogMsg("Return Vo [CmnIntmMdlVO] = " + cmnIntmMdlVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> cmnIntmMdlVOList = new ArrayList<CmnIntmMdlVO>();
		
		cmnIntmMdlVOList = cmnIntmMdlMapper.selectIntmColrMdlList(cmnIntmMdlVO);
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("색상모델 리스트 조회 서비스 END."));
		logger.info(generateLogMsg("================================================================="));
		
		return cmnIntmMdlVOList;
	}

	/**
	 * 색상모델 리스트 엑셀다운로드
	 */
	public List<?> selectIntmColrMdlListExcel(Map<String, Object> paramMap) {
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("색상모델 리스트 엑셀다운로드 서비스 START."));
		logger.info(generateLogMsg("================================================================="));

		logger.debug("paramMap=" + paramMap);
		
		List<EgovMap> list = (List<EgovMap>) cmnIntmMdlMapper.selectIntmColrMdlListExcel(paramMap);
		
		return list;
	}	

	/**
	 * @Description : 대표모델명 조회
	 * @Param  : CmnIntmMdlVO
	 * @Return : List<?>
	 * @Author : 심정보
	 * @Create Date : 2015. 7. 30.
	 */
	public List<?> selectRprsPrdtNm(CmnIntmMdlVO cmnIntmMdlVO) {
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("대표모델명 조회 서비스 START."));
		logger.info(generateLogMsg("Return Vo [CmnIntmMdlVO] = " + cmnIntmMdlVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> cmnIntmMdlVOList = new ArrayList<CmnIntmMdlVO>();
		
		cmnIntmMdlVOList = cmnIntmMdlMapper.selectRprsPrdtNm(cmnIntmMdlVO);
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("대표모델명 조회 서비스 END."));
		logger.info(generateLogMsg("================================================================="));
		
		return cmnIntmMdlVOList;
	}

	/**
	 * @Description : 대표모델ID 중복체크
	 * @Param  : CmnIntmMdlVO
	 * @Return : int
	 * @Author : 심정보
	 * @Create Date : 2015. 7. 30.
	 */
	public int isExistRprsPrdtId(CmnIntmMdlVO cmnIntmMdlVO) {
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("대표모델명 조회 서비스 START."));
		logger.info(generateLogMsg("Return Vo [CmnIntmMdlVO] = " + cmnIntmMdlVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		if(cmnIntmMdlVO.getRprsPrdtId().length() != 8){
				throw new MvnoRunException(-1, "대표모델ID는 8자리입니다.");
		}
				
		int resultCnt = cmnIntmMdlMapper.isExistRprsPrdtId(cmnIntmMdlVO);
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("대표모델명 조회 서비스 END."));
		logger.info(generateLogMsg("================================================================="));
		
		return resultCnt;
	}
	

	/**
	 * @Description : 모델코드(대표) 중복체크
	 * @Param  : CmnIntmMdlVO
	 * @Return : int
	 * @Author : 심정보
	 * @Create Date : 2015. 7. 30.
	 */
	public int isExistRprsPrdtCode(CmnIntmMdlVO cmnIntmMdlVO) {
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("대표모델명 조회 서비스 START."));
		logger.info(generateLogMsg("Return Vo [CmnIntmMdlVO] = " + cmnIntmMdlVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		int resultCnt = cmnIntmMdlMapper.isExistRprsPrdtCode(cmnIntmMdlVO);
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("대표모델명 조회 서비스 END."));
		logger.info(generateLogMsg("================================================================="));
		
		return resultCnt;
	}
	
	/**
	 * @Description : 대표모델 등록
	 * @Param  : CmnIntmMdlVO
	 * @Return : int
	 * @Author : 심정보
	 * @Create Date : 2015. 7. 28.
	 */
	@Transactional(rollbackFor=Exception.class)
	public int insertIntmMdl(CmnIntmMdlVO cmnIntmMdlVO) {
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("대표모델 등록 서비스 START."));
		logger.info(generateLogMsg("CmnIntmMdlVO == " + cmnIntmMdlVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		if(cmnIntmMdlVO.getRprsPrdtId().length() != 8){
			throw new MvnoRunException(-1, "대표모델ID는 8자리입니다.");
		}
		
		int resultCnt = cmnIntmMdlMapper.insertIntmMdl(cmnIntmMdlVO);
		
		logger.info(generateLogMsg("등록 건수 = ") + resultCnt);
		
		return resultCnt;
	}

	/**
	 * @Description : 대표모델 수정
	 * @Param  : CmnIntmMdlVO
	 * @Return : int
	 * @Author : 심정보
	 * @Create Date : 2015. 7. 31.
	 */
	public int updateIntmMdl(CmnIntmMdlVO cmnIntmMdlVO) {
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("대표모델 수정 서비스 START."));
		logger.info(generateLogMsg("CmnIntmMdlVO == " + cmnIntmMdlVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		int resultCnt = cmnIntmMdlMapper.updateIntmMdl(cmnIntmMdlVO);
		
		logger.info(generateLogMsg("수정 건수 = ") + resultCnt);
		
		int resultCntDt = cmnIntmMdlMapper.updateIntmMdlPrdtDt(cmnIntmMdlVO);
		
		logger.info(generateLogMsg("단종일자 수정 건수 = ") + resultCntDt);
		
		return resultCnt;
	}

	/**
	 * @Description : 색상모델ID 중복체크
	 * @Param  : CmnIntmMdlVO
	 * @Return : int
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 3.
	 */
	public int isExistPrdtId(CmnIntmMdlVO cmnIntmMdlVO) {
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("색상모델ID 조회 서비스 START."));
		logger.info(generateLogMsg("Return Vo [CmnIntmMdlVO] = " + cmnIntmMdlVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		if(cmnIntmMdlVO.getPrdtId().length() != 8){
			throw new MvnoRunException(-1, "색상모델ID는 8자리입니다.");
		}
		
		int resultCnt = cmnIntmMdlMapper.isExistPrdtId(cmnIntmMdlVO);
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("색상모델ID 조회 서비스 END."));
		logger.info(generateLogMsg("================================================================="));
		
		return resultCnt;
	}

	
	/**
	 * @Description : 색상모델 등록
	 * @Param  : CmnIntmMdlVO
	 * @Return : int
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 3.
	 */
	public int insertIntmColrMdl(CmnIntmMdlVO cmnIntmMdlVO) {
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("색상모델 등록 서비스 START."));
		logger.info(generateLogMsg("CmnIntmMdlVO == " + cmnIntmMdlVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		

		if(cmnIntmMdlVO.getPrdtId().length() != 8){
			throw new MvnoRunException(-1, "색상모델ID는 8자리입니다.");
		}
		
		int resultCnt = cmnIntmMdlMapper.insertIntmColrMdl(cmnIntmMdlVO);
		
		logger.info(generateLogMsg("등록 건수 = ") + resultCnt);
		
		return resultCnt;
	}

	/**
	 * @Description : 색상모델 수정
	 * @Param  : CmnIntmMdlVO
	 * @Return : int
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 3.
	 */
	public int updateIntmColrMdl(CmnIntmMdlVO cmnIntmMdlVO) {
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("색상모델 수정 서비스 START."));
		logger.info(generateLogMsg("CmnIntmMdlVO == " + cmnIntmMdlVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		int resultCnt = cmnIntmMdlMapper.updateIntmColrMdl(cmnIntmMdlVO);
		
		logger.info(generateLogMsg("수정 건수 = ") + resultCnt);
		
		return resultCnt;
	}

	/**
	 * @Description : 색상모델 팝업 리스트 조회
	 * @Param  : CmnIntmMdlVO
	 * @Return : List<?>
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 3.
	 */
	public List<?> intmColrMdlPopUpList(CmnIntmMdlVO cmnIntmMdlVO) {
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("색상모델 팝업 리스트 조회 서비스 START."));
		logger.info(generateLogMsg("Return Vo [CmnIntmMdlVO] = " + cmnIntmMdlVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> cmnIntmMdlVOList = new ArrayList<CmnIntmMdlVO>();
		
		
		cmnIntmMdlVOList = cmnIntmMdlMapper.intmColrMdlPopUpList(cmnIntmMdlVO);
		
		return cmnIntmMdlVOList;
	}



	

}
