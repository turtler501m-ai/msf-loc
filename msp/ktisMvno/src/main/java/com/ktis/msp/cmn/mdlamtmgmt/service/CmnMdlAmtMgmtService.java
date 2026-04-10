/**
 * 
 */
package com.ktis.msp.cmn.mdlamtmgmt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.mdlamtmgmt.mapper.CmnMdlAmtMgmtMapper;
import com.ktis.msp.cmn.mdlamtmgmt.vo.CmnMdlAmtVO;

/**
 * @Class Name : CmnMdlAmtMgmtService
 * @Description : 모델 단가 관리
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2015.08.05 심정보 최초생성
 * @
 * @author : 심정보
 * @Create Date : 2015. 8. 5.
 */
@Service
public class CmnMdlAmtMgmtService extends BaseService {

	@Autowired
	private CmnMdlAmtMgmtMapper cmnMdlAmtMgmtMapper;
	
	public CmnMdlAmtMgmtService() {
		setLogPrefix("[cmnMdlAmtMgmtService] ");
	}

	/**
	 * @Description : 모델단가 대표모델 리스트 조회
	 * @Param  : CmnMdlAmtVO
	 * @Return : List<?>
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 5.
	 */
	public List<?> selectIntmMdlAmtList(CmnMdlAmtVO cmnMdlAmtVO) {
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("모델단가 대표모델 리스트 조회 서비스 START."));
		logger.info(generateLogMsg("Return Vo [CmnMdlAmtVO] = " + cmnMdlAmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> cmnIntmMdlVOList = new ArrayList<CmnMdlAmtVO>();
		
		cmnIntmMdlVOList = cmnMdlAmtMgmtMapper.selectIntmMdlAmtList(cmnMdlAmtVO);
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("모델단가 대표모델 리스트 조회 서비스 END."));
		logger.info(generateLogMsg("================================================================="));
		
		return cmnIntmMdlVOList;
	}

	/**
	 * @Description : 입고 단가 이력 리스트 조회
	 * @Param  : CmnMdlAmtVO
	 * @Return : List<?>
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 5.
	 */
	public List<?> selectMnfctAmtHisList(CmnMdlAmtVO cmnMdlAmtVO) {
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("입고 단가 이력 리스트 조회 서비스 START."));
		logger.info(generateLogMsg("Return Vo [CmnMdlAmtVO] = " + cmnMdlAmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> cmnIntmMdlVOList = new ArrayList<CmnMdlAmtVO>();
		
		cmnIntmMdlVOList = cmnMdlAmtMgmtMapper.selectMnfctAmtHisList(cmnMdlAmtVO);
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("입고 단가 이력 리스트 조회 서비스 END."));
		logger.info(generateLogMsg("================================================================="));
		
		return cmnIntmMdlVOList;
	}

	/**
	 * @throws MvnoServiceException 
	 * @Description : 입고 단가 등록
	 * @Param  : CmnMdlAmtVO
	 * @Return : int
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 6.
	 */
	public int insertMnfctAmtHisList(CmnMdlAmtVO cmnMdlAmtVO) throws MvnoServiceException {
		/*
		Parameter의 적용일시는 오늘 이후로만 받음.
		1. 기등록건 조회
			1.1 기등록건이 있을경우
				1.1.1 오류철리
			1.2 기등록건이 없을경우 
				1.2.1 신규등록
		 */
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("입고 단가 등록 서비스 START."));
		logger.info(generateLogMsg("Return Vo [CmnMdlAmtVO] = " + cmnMdlAmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		int resultCnt = 0;
		
		// 1. 기등록건 조회
		CmnMdlAmtVO oldMnfctAmt = new CmnMdlAmtVO();
		oldMnfctAmt = cmnMdlAmtMgmtMapper.selectOldMnfctAmt(cmnMdlAmtVO);
		
		if(oldMnfctAmt != null){ //1.1 기등록건이 있을경우
			throw new MvnoServiceException("이미 등록된 입고단가가 있습니다.");
//			//1.1.1 최종데이터의 단가적용일시가 오늘보다 미래인경우
//			if(Integer.valueOf(oldMnfctAmt.getUnitPricApplDttm()) > Integer.valueOf(cmnMdlAmtVO.getToday())){
//				
//				//1.1.1.1 최종 기등록 데이터 수정 (이력생성 X)
//				resultCnt = cmnMdlAmtMgmtMapper.updateMnfctAmt(cmnMdlAmtVO);
//				logger.info(generateLogMsg("입고단가 기등록건 수정 (미래데이터 수정) : " + resultCnt));
//				//1.1.1.2 최종 직전 기등록 데이터 만료일시 수정
//				int resultOldCnt = cmnMdlAmtMgmtMapper.updateOldMnfctAmtExpire(cmnMdlAmtVO);
//				logger.info(generateLogMsg("최종 직전 기등록 데이터 만료일시 수정 (미래데이터 수정) : " + resultOldCnt));
//			} else { //1.1.2 최종데이터의 단가적용일시가 오늘을 포함하여 이전인경우
//				
//				//1.1.2.1 최종 기등록 데이터의 만료일시를 Parameter의 적용일시 "이전일+235959" 로 수정
//				resultCnt = cmnMdlAmtMgmtMapper.updateOldMnfctAmt(cmnMdlAmtVO);
//				logger.info(generateLogMsg("입고단가 기등록건 이력으로 수정 : " + resultCnt));
//				if(resultCnt > 0){
//					//1.1.2.2 신규 등록
//					resultCnt = cmnMdlAmtMgmtMapper.insertMnfctAmt(cmnMdlAmtVO);
//					logger.info(generateLogMsg("입고단가 신규등록 : " + resultCnt));
//				}
//			}
			
		} else {//1.2 기등록건이 없을경우
			//1.2.1 신규등록
			resultCnt = cmnMdlAmtMgmtMapper.insertMnfctAmt(cmnMdlAmtVO);
			logger.info(generateLogMsg("입고단가 신규등록 : " + resultCnt));
		}

		logger.info(generateLogMsg("등록 건수 = ") + resultCnt);
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("입고 단가 등록 서비스 END."));
		logger.info(generateLogMsg("================================================================="));
		
		return resultCnt;
	}
	
	/**
	 * @Description : 입고 단가 수정
	 * @Param  : CmnMdlAmtVO
	 * @Return : int
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 10.
	 */
	public int updateMnfctAmtHisList(CmnMdlAmtVO cmnMdlAmtVO) throws MvnoServiceException {
		/*
		Parameter의 적용일시는 오늘 이후로만 받음.
		1. 기등록건 조회
			1.1 최종데이터의 단가적용일시가 오늘보다 미래인경우
				1.1.1 최종 기등록 데이터 수정 (이력생성 X)
				1.1.2 최종 직전 기등록 데이터 만료일시 수정
			1.2 최종데이터의 단가적용일시가 오늘을 포함하여 이전인경우
				1.2.1 최종 기등록 데이터의 만료일시를 Parameter의 적용일시 "이전일+235959" 로 수정
				1.2.2 신규 등록
		 */
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("입고 단가 수정 서비스 START."));
		logger.info(generateLogMsg("Return Vo [CmnMdlAmtVO] = " + cmnMdlAmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		int resultCnt = 0;
		
		// 1. 기등록건 조회
		CmnMdlAmtVO oldMnfctAmt = new CmnMdlAmtVO();
		oldMnfctAmt = cmnMdlAmtMgmtMapper.selectOldMnfctAmt(cmnMdlAmtVO);
		
		if(oldMnfctAmt != null){ // 기등록건이 있을경우
			
			//1.1 최종데이터의 단가적용일시가 오늘보다 미래인경우
			if(Integer.valueOf(oldMnfctAmt.getUnitPricApplDttm()) > Integer.valueOf(cmnMdlAmtVO.getToday())){
				
				//1.1.1 최종 기등록 데이터 수정 (이력생성 X)
				resultCnt = cmnMdlAmtMgmtMapper.updateMnfctAmt(cmnMdlAmtVO);
				logger.info(generateLogMsg("입고단가 기등록건 수정 (미래데이터 수정) : " + resultCnt));
				//1.1.2 최종 직전 기등록 데이터 만료일시 수정
				int resultOldCnt = cmnMdlAmtMgmtMapper.updateOldMnfctAmtExpire(cmnMdlAmtVO);
				logger.info(generateLogMsg("최종 직전 기등록 데이터 만료일시 수정 (미래데이터 수정) : " + resultOldCnt));
				
			} else { //1.2 최종데이터의 단가적용일시가 오늘을 포함하여 이전인경우
				
				//1.2.1 최종 기등록 데이터의 만료일시를 Parameter의 적용일시 "이전일+235959" 로 수정
				resultCnt = cmnMdlAmtMgmtMapper.updateOldMnfctAmt(cmnMdlAmtVO);
				logger.info(generateLogMsg("입고단가 기등록건 이력 수정 : " + resultCnt));
				if(resultCnt > 0){
					//1.2.2 신규 등록
					resultCnt = cmnMdlAmtMgmtMapper.insertMnfctAmt(cmnMdlAmtVO);
					logger.info(generateLogMsg("입고단가 수정 : " + resultCnt));
				}
			}
		}		
					
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("입고 단가 수정 서비스 END."));
		logger.info(generateLogMsg("================================================================="));
		
		return resultCnt;
	}
	
	
	/**
	 * @Description : 출고 단가 이력 리스트 조회
	 * @Param  : CmnMdlAmtVO
	 * @Return : List<?>
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 5.
	 */
	public List<?> selectHndstAmtHisList(CmnMdlAmtVO cmnMdlAmtVO) {
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("출고 단가 이력 리스트 조회 서비스 START."));
		logger.info(generateLogMsg("Return Vo [CmnMdlAmtVO] = " + cmnMdlAmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> cmnIntmMdlVOList = new ArrayList<CmnMdlAmtVO>();
		
		cmnIntmMdlVOList = cmnMdlAmtMgmtMapper.selectHndstAmtHisList(cmnMdlAmtVO);
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("출고 단가 이력 리스트 조회 서비스 END."));
		logger.info(generateLogMsg("================================================================="));
		
		return cmnIntmMdlVOList;
	}

	/**
	 * @throws MvnoServiceException 
	 * @Description : 출고 단가 등록
	 * @Param  : CmnMdlAmtVO
	 * @Return : int
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 10.
	 */
	public int insertHndstAmtHisList(CmnMdlAmtVO cmnMdlAmtVO) throws MvnoServiceException {
		/*
		Parameter의 적용일시는 오늘 이후로만 받음.
		1. 기등록건 조회
			1.1 기등록건이 있을경우
				1.1.1 오류처리
			1.2 기등록건이 없을경우 
				1.2.1 신규등록
		 */
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("출고 단가 등록 서비스 START."));
		logger.info(generateLogMsg("Return Vo [CmnMdlAmtVO] = " + cmnMdlAmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		int resultCnt = 0;
		
		// 1. 기등록건 조회
		CmnMdlAmtVO oldMnfctAmt = new CmnMdlAmtVO();
		oldMnfctAmt = cmnMdlAmtMgmtMapper.selectOldHndstAmt(cmnMdlAmtVO);
		
		if(oldMnfctAmt != null){ //1.1 기등록건이 있을경우
			throw new MvnoServiceException("이미 등록된 출고단가가 있습니다.");
			//1.1.1 최종데이터의 단가적용일시가 오늘보다 미래인경우
//			if(Integer.valueOf(oldMnfctAmt.getUnitPricApplDttm()) > Integer.valueOf(cmnMdlAmtVO.getToday())){
//				
//				//1.1.1.1 최종 기등록 데이터 수정 (이력생성 X)
//				resultCnt = cmnMdlAmtMgmtMapper.updateHndstAmt(cmnMdlAmtVO);
//				logger.info(generateLogMsg("출고단가 기등록건 수정 (미래데이터 수정) : " + resultCnt));
//				
//				//1.1.1.2 최종 직전 기등록 데이터 만료일시 수정
//				int resultOldCnt = cmnMdlAmtMgmtMapper.updateOldHndstAmtExpire(cmnMdlAmtVO);
//				logger.info(generateLogMsg("최종 직전 기등록 데이터 만료일시 수정 (미래데이터 수정) : " + resultOldCnt));
//				
//			} else { //1.1.2 최종데이터의 단가적용일시가 오늘을 포함하여 이전인경우
//				
//				//1.1.2.1 최종 기등록 데이터의 만료일시를 Parameter의 적용일시 "이전일+235959" 로 수정
//				resultCnt = cmnMdlAmtMgmtMapper.updateOldHndstAmt(cmnMdlAmtVO);
//				logger.info(generateLogMsg("출고단가 기등록건 이력으로 수정 : " + resultCnt));
//				if(resultCnt > 0){
//					//1.1.2.2 신규 등록
//					resultCnt = cmnMdlAmtMgmtMapper.insertHndstAmt(cmnMdlAmtVO);
//					logger.info(generateLogMsg("출고단가 신규등록 : " + resultCnt));
//				}
//			}
			
		} else {//1.2 기등록건이 없을경우
			//1.2.1 신규등록
			resultCnt = cmnMdlAmtMgmtMapper.insertHndstAmt(cmnMdlAmtVO);
			logger.info(generateLogMsg("출고단가 신규등록 : " + resultCnt));
		}
				
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("출고 단가 등록 서비스 END."));
		logger.info(generateLogMsg("================================================================="));
		
		return resultCnt;
	}

	/**
	 * @Description : 출고 단가 수정
	 * @Param  : CmnMdlAmtVO
	 * @Return : int
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 10.
	 */
	public int updateHndstAmtHisList(CmnMdlAmtVO cmnMdlAmtVO) throws MvnoServiceException {
		/*
		Parameter의 적용일시는 오늘 이후로만 받음.
		1. 기등록건 조회
			1.1 최종데이터의 단가적용일시가 오늘보다 미래인경우
				1.1.1 최종 기등록 데이터 수정 (이력생성 X)
				1.1.2 최종 직전 기등록 데이터 만료일시 수정
			1.2 최종데이터의 단가적용일시가 오늘을 포함하여 이전인경우
				1.2.1 최종 기등록 데이터의 만료일시를 Parameter의 적용일시 "이전일+235959" 로 수정
				1.2.2 신규 등록
		 */
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("출고 단가 수정 서비스 START."));
		logger.info(generateLogMsg("Return Vo [CmnMdlAmtVO] = " + cmnMdlAmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));

		int resultCnt = 0;
		
		// 1. 기등록건 조회
		CmnMdlAmtVO oldMnfctAmt = new CmnMdlAmtVO();
		oldMnfctAmt = cmnMdlAmtMgmtMapper.selectOldHndstAmt(cmnMdlAmtVO);
		
		if(oldMnfctAmt != null){ // 기등록건이 있을경우
			
			//1.1 최종데이터의 단가적용일시가 오늘보다 미래인경우
			if(Integer.valueOf(oldMnfctAmt.getUnitPricApplDttm()) > Integer.valueOf(cmnMdlAmtVO.getToday())){
				
				//1.1.1 최종 기등록 데이터 수정 (이력생성 X)
				resultCnt = cmnMdlAmtMgmtMapper.updateHndstAmt(cmnMdlAmtVO);
				logger.info(generateLogMsg("출고단가 기등록건 수정 (미래데이터 수정) : " + resultCnt));
				//1.1.2 최종 직전 기등록 데이터 만료일시 수정
				int resultOldCnt = cmnMdlAmtMgmtMapper.updateOldHndstAmtExpire(cmnMdlAmtVO);
				logger.info(generateLogMsg("최종 직전 기등록 데이터 만료일시 수정 (미래데이터 수정) : " + resultOldCnt));
				
			} else { //1.2 최종데이터의 단가적용일시가 오늘을 포함하여 이전인경우
				
				//1.2.1 최종 기등록 데이터의 만료일시를 Parameter의 적용일시 "이전일+235959" 로 수정
				resultCnt = cmnMdlAmtMgmtMapper.updateOldHndstAmt(cmnMdlAmtVO);
				logger.info(generateLogMsg("출고단가 기등록건 이력으로 수정 : " + resultCnt));
				if(resultCnt > 0){
					//1.2.2 신규 등록
					resultCnt = cmnMdlAmtMgmtMapper.insertHndstAmt(cmnMdlAmtVO);
					logger.info(generateLogMsg("출고단가 신규등록 : " + resultCnt));
				}
			}
			
		}
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("출고 단가 수정 서비스 END."));
		logger.info(generateLogMsg("================================================================="));
		
		return resultCnt;
	}

	/**
	 * @Description : 대표모델 출고단가 찾기 팝업 리스트 조회 - 단종모델 제외
	 * @Param  : CmnMdlAmtVO
	 * @Return : List<?>
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 10.
	 */
	public List<?> selectRprsHndstAmtHisList(CmnMdlAmtVO cmnMdlAmtVO) {
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("대표모델 출고단가 찾기 팝업 리스트 조회 서비스 START."));
		logger.info(generateLogMsg("Return Vo [CmnMdlAmtVO] = " + cmnMdlAmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> cmnMdlAmtVOList = new ArrayList<CmnMdlAmtVO>();
		
		cmnMdlAmtVOList = cmnMdlAmtMgmtMapper.selectRprsHndstAmtHisList(cmnMdlAmtVO);
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("대표모델 출고단가 찾기 팝업 리스트 조회 서비스 END."));
		logger.info(generateLogMsg("================================================================="));
		
		return cmnMdlAmtVOList;
	}
	
	/**
	 * @Description : 대표모델 출고단가 찾기 팝업 리스트 조회 - 단종모델 포함
	 * @Param  : CmnMdlAmtVO
	 * @Return : List<?>
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 10.
	 */
	public List<?> selectRprsHndstAmtAllHisList(CmnMdlAmtVO cmnMdlAmtVO) {
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("대표모델 출고단가 찾기 팝업 리스트 조회 서비스 START."));
		logger.info(generateLogMsg("Return Vo [CmnMdlAmtVO] = " + cmnMdlAmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> cmnMdlAmtVOList = new ArrayList<CmnMdlAmtVO>();
		
		cmnMdlAmtVOList = cmnMdlAmtMgmtMapper.selectRprsHndstAmtAllHisList(cmnMdlAmtVO);
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("대표모델 출고단가 찾기 팝업 리스트 조회 서비스 END."));
		logger.info(generateLogMsg("================================================================="));
		
		return cmnMdlAmtVOList;
	}
	
	/**
	 * @Description : 대표모델의 중고여부 단말 찾기 팝업 리스트 조회 -- 단종모델 제외
	 * @Param  : CmnMdlAmtVO
	 * @Return : List<?>
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 10.
	 */
	public List<?> selectRprsHndstOldYnHisList(CmnMdlAmtVO cmnMdlAmtVO) {
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("대표모델 출고단가 찾기 팝업 리스트 조회 서비스 START."));
		logger.info(generateLogMsg("Return Vo [CmnMdlAmtVO] = " + cmnMdlAmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> cmnMdlAmtVOList = new ArrayList<CmnMdlAmtVO>();
		
		cmnMdlAmtVOList = cmnMdlAmtMgmtMapper.selectRprsHndstOldYnHisList(cmnMdlAmtVO);
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("대표모델 출고단가 찾기 팝업 리스트 조회 서비스 END."));
		logger.info(generateLogMsg("================================================================="));
		
		return cmnMdlAmtVOList;
	}
	
	/**
	 * @Description : 대표모델의 중고여부 단말 찾기 팝업 리스트 조회 -- 단종모델 포함
	 * @Param  : CmnMdlAmtVO
	 * @Return : List<?>
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 10.
	 */
	public List<?> selectRprsHndstOldYnAllHisList(CmnMdlAmtVO cmnMdlAmtVO) {
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("대표모델 출고단가 찾기 팝업 리스트 조회 서비스 START."));
		logger.info(generateLogMsg("Return Vo [CmnMdlAmtVO] = " + cmnMdlAmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> cmnMdlAmtVOList = new ArrayList<CmnMdlAmtVO>();
		
		cmnMdlAmtVOList = cmnMdlAmtMgmtMapper.selectRprsHndstOldYnAllHisList(cmnMdlAmtVO);
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("대표모델 출고단가 찾기 팝업 리스트 조회 서비스 END."));
		logger.info(generateLogMsg("================================================================="));
		
		return cmnMdlAmtVOList;
	}

}
