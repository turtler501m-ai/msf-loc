package com.ktis.msp.org.prmtmgmt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.gift.prmtmgmt.vo.GiftPrmtMgmtSubVO;
import com.ktis.msp.org.prmtmgmt.mapper.TriplePrmtMgmtMapper;
import com.ktis.msp.org.prmtmgmt.vo.ChrgPrmtMgmtSubVO;
import com.ktis.msp.org.prmtmgmt.vo.ChrgPrmtMgmtVO;
import com.ktis.msp.org.prmtmgmt.vo.RecommenIdStateVO;
import com.ktis.msp.org.prmtmgmt.vo.TriplePrmtMgmtVO;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;

@Service
public class TriplePrmtMgmtService extends BaseService {
	
	@Autowired
	private TriplePrmtMgmtMapper triplePrmtMgmtMapper;
	
	@Autowired
	private MaskingService maskingService;
	
	private HashMap<String, String> getMaskFields() {
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		
		maskFields.put("subLinkName", "CUST_NAME");
		maskFields.put("subscriberNo","MOBILE_PHO");
		maskFields.put("deSubLinkName", "CUST_NAME");
		maskFields.put("deSubscriberNo","MOBILE_PHO");
		
		return maskFields;
	}
	//트리플 할인 프로모션 조회
	public List<TriplePrmtMgmtVO> getTriplePrmtList(TriplePrmtMgmtVO triplePrmtMgmtVO) throws EgovBizException {
		
		if(triplePrmtMgmtVO.getSearchBaseDate() == null || "".equals(triplePrmtMgmtVO.getSearchBaseDate())) {
			throw new EgovBizException("기준일자가 존재하지 않습니다.");
		}
		
		List<TriplePrmtMgmtVO> result = triplePrmtMgmtMapper.getTriplePrmtList(triplePrmtMgmtVO);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", triplePrmtMgmtVO.getSessionUserId());
		
		maskingService.setMask(result, maskingService.getMaskFields(), paramMap);
		
		return result;
	}
	
	//트리플 할인 프로모션 요금제 조회
	public List<TriplePrmtMgmtVO> getTriplePrmtSocList(TriplePrmtMgmtVO triplePrmtMgmtVO) throws EgovBizException {
		
		return triplePrmtMgmtMapper.getTriplePrmtSocList(triplePrmtMgmtVO);
	}
	
	//트리플할인 프로모션 부가서비스 조회
	public List<TriplePrmtMgmtVO> getTriplePrmtAddList(TriplePrmtMgmtVO triplePrmtMgmtVO) throws EgovBizException {
		
		return triplePrmtMgmtMapper.getTriplePrmtAddList(triplePrmtMgmtVO);
	}
	
	//트리플할인 프로모션 부가서비스 신규 저장
	public void regTriplePrmtInfo(TriplePrmtMgmtVO triplePrmtMgmtVO) throws EgovBizException {
		
		if(triplePrmtMgmtVO.getPrmtNm() == null || "".equals(triplePrmtMgmtVO.getPrmtNm())){
			throw new EgovBizException("프로모션명이 존재하지 않습니다");
		}
		
		if(triplePrmtMgmtVO.getStrtDt() == null || "".equals(triplePrmtMgmtVO.getStrtDt())){
			throw new EgovBizException("프로모션시작일자가 존재하지 않습니다");
		}
		
		if(triplePrmtMgmtVO.getEndDt() == null || "".equals(triplePrmtMgmtVO.getEndDt())){
			throw new EgovBizException("프로모션종료일자가 존재하지 않습니다");
		}
		
		if(triplePrmtMgmtVO.getRateList().size() < 1){
			throw new EgovBizException("요금제 정보가 존재하지 않습니다");
		}
		
		if(triplePrmtMgmtVO.getVasList().size() < 1){
			throw new EgovBizException("부가서비스 정보가 존재하지 않습니다");
		}
		
		
		List<TriplePrmtMgmtVO> aryDupChkList = triplePrmtMgmtMapper.getTriplePrmtDupByInfo(triplePrmtMgmtVO);
		
		// 중복된 요금제가 존재하는 지 확인
		for(int idx1 = 0; idx1 < triplePrmtMgmtVO.getRateList().size(); idx1++){
					for(int idx2 = 0; idx2 < aryDupChkList.size(); idx2++){
					   if(aryDupChkList.get(idx2).getRateCd().equals(triplePrmtMgmtVO.getRateList().get(idx1).getRateCd())){
					      String strChkPrmtId = aryDupChkList.get(idx2).getPrmtId();
                     throw new EgovBizException("이미 등록된 프로모션["+ strChkPrmtId +"]이 존재합니다.");
					   }
					}
			}
		
		
		try {
			// 트리플할인 마스터 추가
			triplePrmtMgmtMapper.insertMspTriplePrmt(triplePrmtMgmtVO);
			
			String strPrmtId = triplePrmtMgmtVO.getPrmtId();
			String strUserId = triplePrmtMgmtVO.getSessionUserId();
					
			// 트리플할인 요금제 추가
			try {
				for(TriplePrmtMgmtVO rateVO : triplePrmtMgmtVO.getRateList()) {
					rateVO.setPrmtId(strPrmtId);
					rateVO.setSessionUserId(strUserId);
					triplePrmtMgmtMapper.insertMspTriplePrmtSoc(rateVO);
				}
			} catch (EgovBizException e) {
				e.setMessage("요금제 정보 저장 오류!!");
				throw e;
			}
			
			// 채널별 요금 할인 부가서비스 추가
			try {
				List<TriplePrmtMgmtVO> addVasList = new ArrayList<TriplePrmtMgmtVO>();
				TriplePrmtMgmtVO addVasVo = null;
				
				for(int idx1 = 0; idx1 < triplePrmtMgmtVO.getVasList().size(); idx1++){
					int nSeq = 0;
					
					addVasVo = new TriplePrmtMgmtVO();
					
					addVasList.add(addVasVo);
					
					if(triplePrmtMgmtVO.getVasList().get(idx1).getVasCd() == null || "".equals(triplePrmtMgmtVO.getVasList().get(idx1).getVasCd())){
						throw new EgovBizException("부가서비스 정보가 존재하지 않습니다.");
					}
					
					addVasVo.setSoc(triplePrmtMgmtVO.getVasList().get(idx1).getVasCd());
					addVasVo.setPrmtId(strPrmtId);
					addVasVo.setSessionUserId(strUserId);
					
					for(int idx2 = 0; idx2 < addVasList.size(); idx2++){
						if(triplePrmtMgmtVO.getVasList().get(idx1).getVasCd().equals(addVasList.get(idx2).getSoc())){
							nSeq++;
						}
					}
					
					addVasVo.setSeq(String.valueOf(nSeq));
				}
				
				for(TriplePrmtMgmtVO addVO : addVasList) {
					
					triplePrmtMgmtMapper.insertMspTriplePrmtAdd(addVO);
				}
			} catch (EgovBizException e) {
				logger.info(e.getMessage());
				
				throw e;
			} catch(Exception e) {
				logger.info(e.getMessage());
				
				throw new EgovBizException("부가서비스 정보 저장 오류!!.");
			}
			
		} catch(EgovBizException e) {
			logger.info(e.getMessage());
			
			throw e;
		} catch(Exception e) {
			logger.info(e.getMessage());
			
			throw new EgovBizException("저장 처리 중 오류가 발생하였습니다.");
		}
	}
	
	//트리플 할인 정보 변경(사용여부 또는 종료일 변경)
	public int updTriplePrmtByInfo(TriplePrmtMgmtVO triplePrmtMgmtVO) throws EgovBizException {
		int result = 0;
		
		if(triplePrmtMgmtVO.getPrmtId() == null || "".equals(triplePrmtMgmtVO.getPrmtId())) {
			throw new EgovBizException("프로모션ID가 존재하지 않습니다.");
		}
		
		if(triplePrmtMgmtVO.getChngTypeCd() == null || "".equals(triplePrmtMgmtVO.getChngTypeCd())) {
			throw new EgovBizException("변경 유형 코드가 존재하지 않습니다.");
		}
		
		if("U".equals(triplePrmtMgmtVO.getChngTypeCd())){
			List<TriplePrmtMgmtVO> aryDupChkList = triplePrmtMgmtMapper.getTriplePrmtDupByInfo(triplePrmtMgmtVO);
			
			if(aryDupChkList.size() > 0){
				throw new EgovBizException("해당 기간에 프로모션["+ aryDupChkList.get(0).getPrmtId() +"]이 존재합니다.");
			}
		}
		
		try {
			result = triplePrmtMgmtMapper.updTriplePrmtByInfo(triplePrmtMgmtVO);
		} catch (EgovBizException e) {
			e.setMessage("저장 처리 중 오류가 발생하였습니다.");
			throw e;
		}
		
		return result;
	}
	
	public List<TriplePrmtMgmtVO> getExcelUpSocList(TriplePrmtMgmtVO triplePrmtMgmtVO) throws EgovBizException {
		
		return triplePrmtMgmtMapper.getExcelUpSocList(triplePrmtMgmtVO);

	}
	
}
