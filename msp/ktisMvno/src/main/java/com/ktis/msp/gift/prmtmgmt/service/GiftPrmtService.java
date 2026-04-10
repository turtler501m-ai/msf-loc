package com.ktis.msp.gift.prmtmgmt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.gift.prmtmgmt.mapper.GiftPrmtMapper;
import com.ktis.msp.gift.prmtmgmt.vo.GiftPrmtMgmtSubVO;
import com.ktis.msp.gift.prmtmgmt.vo.GiftPrmtMgmtVO;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;

@Service
public class GiftPrmtService extends BaseService {
	
	@Autowired
	private GiftPrmtMapper giftPrmtMapper;
	
	@Autowired
	private MaskingService maskingService;
	
	
	public List<GiftPrmtMgmtVO> getGiftPrmtList(GiftPrmtMgmtVO giftPrmtMgmtVO) throws EgovBizException {
		
		if(giftPrmtMgmtVO.getSearchBaseDate() == null || "".equals(giftPrmtMgmtVO.getSearchBaseDate())) {
			throw new EgovBizException("기준일자가 존재하지 않습니다.");
		}
		
		List<GiftPrmtMgmtVO> result = giftPrmtMapper.getGiftPrmtList(giftPrmtMgmtVO);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", giftPrmtMgmtVO.getSessionUserId());
		
		maskingService.setMask(result, maskingService.getMaskFields(), paramMap);
		
		return result;
	}
	
	public List<GiftPrmtMgmtSubVO> getGiftPrmtOrgnList(GiftPrmtMgmtSubVO giftPrmtMgmtSubVO) throws EgovBizException {
		
		return giftPrmtMapper.getGiftPrmtOrgnList(giftPrmtMgmtSubVO);
	}
	
	public List<GiftPrmtMgmtSubVO> getGiftPrmtSocList(GiftPrmtMgmtSubVO giftPrmtMgmtSubVO) throws EgovBizException {
		
		return giftPrmtMapper.getGiftPrmtSocList(giftPrmtMgmtSubVO);
	}
	
	public List<GiftPrmtMgmtSubVO> getGiftPrmtPrdtList(GiftPrmtMgmtSubVO giftPrmtMgmtSubVO) throws EgovBizException {
		
		return giftPrmtMapper.getGiftPrmtPrdtList(giftPrmtMgmtSubVO);
	}

	/*
	 * 사은품 프로모션 등록
	 */
	public void regGiftPrmtInfo(GiftPrmtMgmtVO giftPrmtMgmtVO) throws EgovBizException {
		
		if(giftPrmtMgmtVO.getPrmtNm() == null || "".equals(giftPrmtMgmtVO.getPrmtNm())){
			throw new EgovBizException("프로모션명이 존재하지 않습니다");
		}
		
		if(giftPrmtMgmtVO.getStrtDt() == null || "".equals(giftPrmtMgmtVO.getStrtDt())){
			throw new EgovBizException("프로모션시작일자가 존재하지 않습니다");
		}
		
		if(giftPrmtMgmtVO.getEndDt() == null || "".equals(giftPrmtMgmtVO.getEndDt())){
			throw new EgovBizException("프로모션종료일자가 존재하지 않습니다");
		}
		
		if((giftPrmtMgmtVO.getNacYn() == null || "".equals(giftPrmtMgmtVO.getNacYn())) &&
				(giftPrmtMgmtVO.getMnpYn() == null || "".equals(giftPrmtMgmtVO.getMnpYn()))){
			throw new EgovBizException("가입유형 정보가 존재하지 않습니다");
		}
		
		if((giftPrmtMgmtVO.getEnggCnt0() == null || "".equals(giftPrmtMgmtVO.getEnggCnt0())) &&
				(giftPrmtMgmtVO.getEnggCnt12() == null || "".equals(giftPrmtMgmtVO.getEnggCnt12())) && 
				(giftPrmtMgmtVO.getEnggCnt18() == null || "".equals(giftPrmtMgmtVO.getEnggCnt18())) &&
				(giftPrmtMgmtVO.getEnggCnt24() == null || "".equals(giftPrmtMgmtVO.getEnggCnt24())) &&
				(giftPrmtMgmtVO.getEnggCnt30() == null || "".equals(giftPrmtMgmtVO.getEnggCnt30())) &&
				(giftPrmtMgmtVO.getEnggCnt36() == null || "".equals(giftPrmtMgmtVO.getEnggCnt36()))){
			throw new EgovBizException("약정기간 정보가 존재하지 않습니다");
		}
		
		if(giftPrmtMgmtVO.getOrgnList().size() < 1){
			throw new EgovBizException("조직 정보가 존재하지 않습니다");
		}
		
		if(giftPrmtMgmtVO.getRateList().size() < 1){
			throw new EgovBizException("요금제 정보가 존재하지 않습니다");
		}
		
		if(giftPrmtMgmtVO.getGiftPrdtList().size() < 1){
			throw new EgovBizException("사은품제품 정보가 존재하지 않습니다");
		}
		
		if(giftPrmtMgmtVO.getOnOffTypeList().size() < 1){
			throw new EgovBizException("모집경로 정보가 존재하지 않습니다");
		}
		
		if(giftPrmtMgmtVO.getModelList().size() < 1){
			throw new EgovBizException("대상제품 정보가 존재하지 않습니다");
		}
		
		if ("C".equals(giftPrmtMgmtVO.getPrmtType()) && giftPrmtMgmtVO.getChoiceLimit() < 1) {
			throw new EgovBizException("선택 사은품의 경우 총 선택 개수 제한을 입력해야 됩니다.");
		}
		
		
		/* 2022.02 프로모션 중복 등록 허용
		List<GiftPrmtMgmtVO> aryDupChkList = giftPrmtMapper.getGiftPrmtDupByInfo(giftPrmtMgmtVO);
		
		// 중복된 조직, 요금제, 모집경로가 존재하는 지 확인
		for(int idx1 = 0; idx1 < giftPrmtMgmtVO.getOrgnList().size(); idx1++){
			for(int idx2 = 0; idx2 < giftPrmtMgmtVO.getRateList().size(); idx2++){
				for(int idx3 = 0; idx3 < giftPrmtMgmtVO.getOnOffTypeList().size(); idx3++){
					for(int idx4 = 0; idx4 < aryDupChkList.size(); idx4++){
						//v2018.11 PMD 적용 소스 수정
//					   if(aryDupChkList.get(idx4).getOrgnId().equals(giftPrmtMgmtVO.getOrgnList().get(idx1).getOrgnId())){
//							if(aryDupChkList.get(idx4).getRateCd().equals(giftPrmtMgmtVO.getRateList().get(idx2).getRateCd())){
//								if(aryDupChkList.get(idx4).getOnOffType().equals(giftPrmtMgmtVO.getOnOffTypeList().get(idx3).getOnOffType())){
//									String strChkPrmtId = aryDupChkList.get(idx4).getPrmtId();
//									throw new EgovBizException("이미 등록된 프로모션["+ strChkPrmtId +"]이 존재합니다.");
//								}
//							}
//						}
					   if(aryDupChkList.get(idx4).getOrgnId().equals(giftPrmtMgmtVO.getOrgnList().get(idx1).getOrgnId())
					         && aryDupChkList.get(idx4).getRateCd().equals(giftPrmtMgmtVO.getRateList().get(idx2).getRateCd())
					         && aryDupChkList.get(idx4).getOnOffType().equals(giftPrmtMgmtVO.getOnOffTypeList().get(idx3).getOnOffType())){
					      String strChkPrmtId = aryDupChkList.get(idx4).getPrmtId();
                     throw new EgovBizException("이미 등록된 프로모션["+ strChkPrmtId +"]이 존재합니다.");
					   }
					}
				}
			}
		}
		*/
		
		try {
			// 사은품 프로모션 마스터 추가
			giftPrmtMapper.insertMspGiftPrmt(giftPrmtMgmtVO);
			
			String strPrmtId = giftPrmtMgmtVO.getPrmtId();
			String strUserId = giftPrmtMgmtVO.getSessionUserId();
			
			// 사은품 프로모션 조직 추가
			try {
				for(GiftPrmtMgmtSubVO orgnVO : giftPrmtMgmtVO.getOrgnList()) {
					orgnVO.setPrmtId(strPrmtId);
					orgnVO.setSessionUserId(strUserId);
					giftPrmtMapper.insertMspGiftPrmtOrg(orgnVO);
				}
			} catch (EgovBizException e) {
				e.setMessage("조직 정보 저장 오류!!");
				throw e;
			}
			
			// 사은품 프로모션 요금제 추가
			try {
				for(GiftPrmtMgmtSubVO rateVO : giftPrmtMgmtVO.getRateList()) {
					rateVO.setPrmtId(strPrmtId);
					rateVO.setSessionUserId(strUserId);
					giftPrmtMapper.insertMspGiftPrmtSoc(rateVO);
				}
			} catch (EgovBizException e) {
				e.setMessage("요금제 정보 저장 오류!!");
				throw e;
			}
			
			// 사은품 프로모션 사은품 추가
			try {
				List<GiftPrmtMgmtSubVO> giftPrdtList = new ArrayList<GiftPrmtMgmtSubVO>();
				GiftPrmtMgmtSubVO giftPrdtVo = null;
				
				for(int idx1 = 0; idx1 < giftPrmtMgmtVO.getGiftPrdtList().size(); idx1++){
					
					giftPrdtVo = new GiftPrmtMgmtSubVO();
					
					
					if(giftPrmtMgmtVO.getGiftPrdtList().get(idx1).getPrdtId() == null || "".equals(giftPrmtMgmtVO.getGiftPrdtList().get(idx1).getPrdtId())){
						throw new EgovBizException("사은품 제품 정보가 존재하지 않습니다.");
					}
					
					giftPrdtVo.setPrdtId(giftPrmtMgmtVO.getGiftPrdtList().get(idx1).getPrdtId());
					giftPrdtVo.setPrmtId(strPrmtId);
					giftPrdtVo.setSessionUserId(strUserId);
					giftPrdtVo.setSort(giftPrmtMgmtVO.getGiftPrdtList().get(idx1).getSort());

					giftPrdtList.add(giftPrdtVo);
					
				}
				
				for(GiftPrmtMgmtSubVO addVO : giftPrdtList) {
					
					giftPrmtMapper.insertMspGiftPrmtPrdt(addVO);
				}
			} catch (EgovBizException e) {
				logger.info(e.getMessage());
				
				throw e;
			} catch(Exception e) {
				logger.info(e.getMessage());
				
				throw new EgovBizException("사은품 제품 정보 저장 오류!!.");
			}
			
			// 모집경로 추가
			try {
				for(GiftPrmtMgmtSubVO onOffVO : giftPrmtMgmtVO.getOnOffTypeList()) {
					onOffVO.setPrmtId(strPrmtId);
					onOffVO.setSessionUserId(strUserId);
					giftPrmtMapper.insertMspGiftPrmtOnOff(onOffVO);
				}
			} catch (EgovBizException e) {
				e.setMessage("모집경로 정보 저장 오류!!");
				throw e;
			}
			
			// 대상제품 추가
			try {
				for(GiftPrmtMgmtSubVO modelVO : giftPrmtMgmtVO.getModelList()) {
					modelVO.setPrmtId(strPrmtId);
					modelVO.setSessionUserId(strUserId);
					giftPrmtMapper.insertMspGiftPrmtModel(modelVO);
				}
			} catch (EgovBizException e) {
				e.setMessage("대상제품 정보 저장 오류!!");
				throw e;
			}
		} catch(EgovBizException e) {
			logger.info(e.getMessage());
			
			throw e;
		} catch(Exception e) {
			logger.info(e.getMessage());
			
			throw new EgovBizException("저장 처리 중 오류가 발생하였습니다.");
		}
	}

	/*
	 * 사은품 프로모션 삭제 후 등록
	 */
	public void modGiftPrmtInfo(GiftPrmtMgmtVO giftPrmtMgmtVO) throws EgovBizException {
		
		if(giftPrmtMgmtVO.getPrmtNm() == null || "".equals(giftPrmtMgmtVO.getPrmtNm())){
			throw new EgovBizException("프로모션명이 존재하지 않습니다");
		}
		
		if(giftPrmtMgmtVO.getStrtDt() == null || "".equals(giftPrmtMgmtVO.getStrtDt())){
			throw new EgovBizException("프로모션시작일자가 존재하지 않습니다");
		}
		
		if(giftPrmtMgmtVO.getEndDt() == null || "".equals(giftPrmtMgmtVO.getEndDt())){
			throw new EgovBizException("프로모션종료일자가 존재하지 않습니다");
		}
		
		if((giftPrmtMgmtVO.getNacYn() == null || "".equals(giftPrmtMgmtVO.getNacYn())) &&
				(giftPrmtMgmtVO.getMnpYn() == null || "".equals(giftPrmtMgmtVO.getMnpYn()))){
			throw new EgovBizException("가입유형 정보가 존재하지 않습니다");
		}
		
		if((giftPrmtMgmtVO.getEnggCnt0() == null || "".equals(giftPrmtMgmtVO.getEnggCnt0())) &&
				(giftPrmtMgmtVO.getEnggCnt12() == null || "".equals(giftPrmtMgmtVO.getEnggCnt12())) && 
				(giftPrmtMgmtVO.getEnggCnt18() == null || "".equals(giftPrmtMgmtVO.getEnggCnt18())) &&
				(giftPrmtMgmtVO.getEnggCnt24() == null || "".equals(giftPrmtMgmtVO.getEnggCnt24())) &&
				(giftPrmtMgmtVO.getEnggCnt30() == null || "".equals(giftPrmtMgmtVO.getEnggCnt30())) &&
				(giftPrmtMgmtVO.getEnggCnt36() == null || "".equals(giftPrmtMgmtVO.getEnggCnt36()))){
			throw new EgovBizException("약정기간 정보가 존재하지 않습니다");
		}
		
		if(giftPrmtMgmtVO.getOrgnList().size() < 1){
			throw new EgovBizException("조직 정보가 존재하지 않습니다");
		}
		
		if(giftPrmtMgmtVO.getRateList().size() < 1){
			throw new EgovBizException("요금제 정보가 존재하지 않습니다");
		}
		
		if(giftPrmtMgmtVO.getGiftPrdtList().size() < 1){
			throw new EgovBizException("사은품제품 정보가 존재하지 않습니다");
		}
		
		if(giftPrmtMgmtVO.getOnOffTypeList().size() < 1){
			throw new EgovBizException("모집경로 정보가 존재하지 않습니다");
		}
		
		if(giftPrmtMgmtVO.getModelList().size() < 1){
			throw new EgovBizException("대상제품 정보가 존재하지 않습니다");
		}

		if ("C".equals(giftPrmtMgmtVO.getPrmtType()) && giftPrmtMgmtVO.getChoiceLimit() < 1) {
			throw new EgovBizException("선택 사은품의 경우 총 선택 개수 제한을 입력해야 됩니다.");
		}
		
		/*
		 * 2022.02 프로모션 중복 등록 허용
		List<GiftPrmtMgmtVO> aryDupChkList = giftPrmtMapper.getGiftPrmtDupByInfoToMod(giftPrmtMgmtVO);
		// 중복된 조직, 요금제, 모집경로가 존재하는 지 확인
		for(int idx1 = 0; idx1 < giftPrmtMgmtVO.getOrgnList().size(); idx1++){
			for(int idx2 = 0; idx2 < giftPrmtMgmtVO.getRateList().size(); idx2++){
				for(int idx3 = 0; idx3 < giftPrmtMgmtVO.getOnOffTypeList().size(); idx3++){
					for(int idx4 = 0; idx4 < aryDupChkList.size(); idx4++){
						//v2018.11 PMD 적용 소스 수정
//					   if(aryDupChkList.get(idx4).getOrgnId().equals(giftPrmtMgmtVO.getOrgnList().get(idx1).getOrgnId())){
//							if(aryDupChkList.get(idx4).getRateCd().equals(giftPrmtMgmtVO.getRateList().get(idx2).getRateCd())){
//								if(aryDupChkList.get(idx4).getOnOffType().equals(giftPrmtMgmtVO.getOnOffTypeList().get(idx3).getOnOffType())){
//									String strChkPrmtId = aryDupChkList.get(idx4).getPrmtId();
//									throw new EgovBizException("이미 등록된 프로모션["+ strChkPrmtId +"]이 존재합니다.");
//								}
//							}
//						}
					   if(aryDupChkList.get(idx4).getOrgnId().equals(giftPrmtMgmtVO.getOrgnList().get(idx1).getOrgnId())
					         && aryDupChkList.get(idx4).getRateCd().equals(giftPrmtMgmtVO.getRateList().get(idx2).getRateCd())
					         && aryDupChkList.get(idx4).getOnOffType().equals(giftPrmtMgmtVO.getOnOffTypeList().get(idx3).getOnOffType())){
					      String strChkPrmtId = aryDupChkList.get(idx4).getPrmtId();
                     throw new EgovBizException("이미 등록된 프로모션["+ strChkPrmtId +"]이 존재합니다.");
					   }
					}
				}
			}
		}
		*/
		
		try {
			// 사은품 프로모션 마스터 수정
			giftPrmtMapper.updateMspGiftPrmt(giftPrmtMgmtVO);
			
			String strPrmtId = giftPrmtMgmtVO.getPrmtId();
			String strUserId = giftPrmtMgmtVO.getSessionUserId();
			
			// 사은품 프로모션 조직 추가
			try {
				//추가전 전체삭제
				giftPrmtMapper.deleteMspGiftPrmtOrg(giftPrmtMgmtVO);
				
				for(GiftPrmtMgmtSubVO orgnVO : giftPrmtMgmtVO.getOrgnList()) {
					orgnVO.setPrmtId(strPrmtId);
					orgnVO.setSessionUserId(strUserId);
					giftPrmtMapper.insertMspGiftPrmtOrg(orgnVO);
				}
			} catch (EgovBizException e) {
				e.setMessage("조직 정보 저장 오류!!");
				throw e;
			}
			
			// 사은품 프로모션 요금제 추가
			try {
				//추가전 전체삭제
				giftPrmtMapper.deleteMspGiftPrmtSoc(giftPrmtMgmtVO);
				
				for(GiftPrmtMgmtSubVO rateVO : giftPrmtMgmtVO.getRateList()) {
					rateVO.setPrmtId(strPrmtId);
					rateVO.setSessionUserId(strUserId);
					giftPrmtMapper.insertMspGiftPrmtSoc(rateVO);
				}
			} catch (EgovBizException e) {
				e.setMessage("요금제 정보 저장 오류!!");
				throw e;
			}
			
			// 사은품 프로모션 사은품 추가
			try {
				List<GiftPrmtMgmtSubVO> giftPrdtList = new ArrayList<GiftPrmtMgmtSubVO>();
				GiftPrmtMgmtSubVO giftPrdtVo = null;
				
				for(int idx1 = 0; idx1 < giftPrmtMgmtVO.getGiftPrdtList().size(); idx1++){
					
					giftPrdtVo = new GiftPrmtMgmtSubVO();
					
					
					if(giftPrmtMgmtVO.getGiftPrdtList().get(idx1).getPrdtId() == null || "".equals(giftPrmtMgmtVO.getGiftPrdtList().get(idx1).getPrdtId())){
						throw new EgovBizException("사은품 제품 정보가 존재하지 않습니다.");
					}
					
					giftPrdtVo.setPrdtId(giftPrmtMgmtVO.getGiftPrdtList().get(idx1).getPrdtId());
					giftPrdtVo.setPrmtId(strPrmtId);
					giftPrdtVo.setSessionUserId(strUserId);
					giftPrdtVo.setSort(giftPrmtMgmtVO.getGiftPrdtList().get(idx1).getSort());

					giftPrdtList.add(giftPrdtVo);

				}
				
				//추가전 전체삭제
				giftPrmtMapper.deleteMspGiftPrmtPrdt(giftPrmtMgmtVO);
				
				for(GiftPrmtMgmtSubVO addVO : giftPrdtList) {
					giftPrmtMapper.insertMspGiftPrmtPrdt(addVO);
				}
			} catch (EgovBizException e) {
				logger.info(e.getMessage());
				
				throw e;
			} catch(Exception e) {
				logger.info(e.getMessage());
				
				throw new EgovBizException("사은품 제품 정보 저장 오류!!.");
			}
			
			// 모집경로 추가
			try {

				//추가전 전체삭제
				giftPrmtMapper.deleteMspGiftPrmtOnOff(giftPrmtMgmtVO);
				
				for(GiftPrmtMgmtSubVO onOffVO : giftPrmtMgmtVO.getOnOffTypeList()) {
					onOffVO.setPrmtId(strPrmtId);
					onOffVO.setSessionUserId(strUserId);
					giftPrmtMapper.insertMspGiftPrmtOnOff(onOffVO);
				}
			} catch (EgovBizException e) {
				e.setMessage("모집경로 정보 저장 오류!!");
				throw e;
			}
			
			// 대상제품 추가
			try {

				//추가전 전체삭제
				giftPrmtMapper.deleteMspGiftPrmtModel(giftPrmtMgmtVO);
				
				for(GiftPrmtMgmtSubVO modelVO : giftPrmtMgmtVO.getModelList()) {
					modelVO.setPrmtId(strPrmtId);
					modelVO.setSessionUserId(strUserId);
					giftPrmtMapper.insertMspGiftPrmtModel(modelVO);
				}
			} catch (EgovBizException e) {
				e.setMessage("대상제품 정보 저장 오류!!");
				throw e;
			}
		} catch(EgovBizException e) {
			logger.info(e.getMessage());
			
			throw e;
		} catch(Exception e) {
			logger.info(e.getMessage());
			
			throw new EgovBizException("저장 처리 중 오류가 발생하였습니다.");
		}
	}
	
	public int updGiftPrmtByInfo(GiftPrmtMgmtVO giftPrmtMgmtVO) throws EgovBizException {
		int result = 0;
		
		if(giftPrmtMgmtVO.getPrmtId() == null || "".equals(giftPrmtMgmtVO.getPrmtId())) {
			throw new EgovBizException("프로모션ID가 존재하지 않습니다.");
		}
		
		if(giftPrmtMgmtVO.getChngTypeCd() == null || "".equals(giftPrmtMgmtVO.getChngTypeCd())) {
			throw new EgovBizException("변경 유형 코드가 존재하지 않습니다.");
		}
		
		if("U".equals(giftPrmtMgmtVO.getChngTypeCd())){
			List<GiftPrmtMgmtVO> aryDupChkList = giftPrmtMapper.getGiftPrmtDupByInfo(giftPrmtMgmtVO);
			
			if(aryDupChkList.size() > 0){
				throw new EgovBizException("해당 기간에 프로모션["+ aryDupChkList.get(0).getPrmtId() +"]이 존재합니다.");
			}
		}
		
		try {
			result = giftPrmtMapper.updGiftPrmtByInfo(giftPrmtMgmtVO);
		} catch (EgovBizException e) {
			e.setMessage("저장 처리 중 오류가 발생하였습니다.");
			throw e;
		}
		
		return result;
	}

	/**
	 * 모집경로
	 */
	public List<GiftPrmtMgmtSubVO> getGiftPrmtOnoffList(GiftPrmtMgmtSubVO giftPrmtMgmtSubVO) throws EgovBizException {
		
		return giftPrmtMapper.getGiftPrmtOnoffList(giftPrmtMgmtSubVO);
	}

	/**
	 * 대상제품
	 */
	public List<GiftPrmtMgmtSubVO> getGiftPrmtModelList(GiftPrmtMgmtSubVO giftPrmtMgmtSubVO) throws EgovBizException {
		
		return giftPrmtMapper.getGiftPrmtModelList(giftPrmtMgmtSubVO);
	}
	
	
	/**
	 * 프로모션 복사
	@Transactional(rollbackFor=Exception.class)
	@Async
	public void copyGiftPrmtInfo(GiftPrmtMgmtCopyVO giftPrmtMgmtCopyVO) throws EgovBizException {
		
		if(giftPrmtMgmtCopyVO.getPrmtId() == null || giftPrmtMgmtCopyVO.getPrmtId().equals("")) {
			throw new MvnoRunException(-1, "기존 프로모션ID가 존재하지 않습니다");
		}
		
		try {
			
			// 신규 프로모션ID 조회
			giftPrmtMgmtCopyVO.setNewPrmtId(giftPrmtMapper.getPrmtId());
			
			// 사은품 프로모션 마스터 복사
			giftPrmtMapper.insertMspGiftPrmtCopy(giftPrmtMgmtCopyVO);
			
			// 사은품 프로모션조직 복사
			giftPrmtMapper.insertMspGiftPrmtOrgCopy(giftPrmtMgmtCopyVO);
			// 사은품 프로모션 요금제 복사
			giftPrmtMapper.insertMspGiftPrmtSocCopy(giftPrmtMgmtCopyVO);
			// 사은품 프로모션 사은품 제품 복사
			giftPrmtMapper.insertMspGiftPrmtPrdtCopy(giftPrmtMgmtCopyVO);
			// 모집경로 복사
			giftPrmtMapper.insertMspGiftPrmtOnOffCopy(giftPrmtMgmtCopyVO);
			
		} catch(MvnoRunException e) {
			logger.info(e.getMessage());
			throw e;
		} catch(Exception e) {
			logger.info(e.getMessage());
			throw new MvnoRunException(-1, "정책복사 중 오류가 발생하였습니다");
		}
		
	}
	 */
	public List<GiftPrmtMgmtSubVO> getExcelUpSocList(GiftPrmtMgmtSubVO giftPrmtMgmtSubVO) throws EgovBizException {
		
		return giftPrmtMapper.getExcelUpSocList(giftPrmtMgmtSubVO);

	}
	public List<GiftPrmtMgmtSubVO> getExcelUpModelList(GiftPrmtMgmtSubVO giftPrmtMgmtSubVO) throws EgovBizException {
		
		return giftPrmtMapper.getExcelUpModelList(giftPrmtMgmtSubVO);

	}
	
	/**
	 * 사은품 프로모션 관리 엑셀다운로드
	 */
	public List<?> getGiftPrmtListEx(GiftPrmtMgmtVO searchVO, Map<String, Object> pRequestParamMap) throws EgovBizException {
		List<?> result = new ArrayList<GiftPrmtMgmtVO>();
		result = giftPrmtMapper.getGiftPrmtListEx(searchVO);
		maskingService.setMask(result, maskingService.getMaskFields(), pRequestParamMap);
		return result;
	}
	
	/**
	 * 사은품 프로모션 권한여부 조회
	 */
    @Transactional(rollbackFor=Exception.class)
    public String groupUserAuth(String usrId){
		return giftPrmtMapper.groupUserAuth(usrId) > 0 ? "N":"Y";
    }
}
