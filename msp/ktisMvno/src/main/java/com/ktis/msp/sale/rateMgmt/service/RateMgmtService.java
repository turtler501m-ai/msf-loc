package com.ktis.msp.sale.rateMgmt.service;

import java.text.SimpleDateFormat;
import java.util.*;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.sale.rateMgmt.vo.DisAddSvcVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.sale.rateMgmt.mapper.RateMgmtMapper;
import com.ktis.msp.sale.rateMgmt.vo.RateMgmtVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service
public class RateMgmtService extends BaseService {
	
	@Autowired
	private RateMgmtMapper rateMgmtMapper;
	
	@Autowired
	private MaskingService maskingService;

	/**
	 * 요금제목록조회
	 * @param searchVO
	 * @return
	 */
	public List<?> getRateMgmtList(RateMgmtVO searchVO){
		logger.debug("searchVO = " + searchVO);
		
		List<EgovMap> result = (List<EgovMap>) rateMgmtMapper.getRateMgmtList(searchVO);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", searchVO.getSessionUserId());
		
		maskingService.setMask(result, maskingService.getMaskFields(), paramMap);
		
		return result;
	}
	
	/**
	 * 요금제등록
	 * @param searchVO
	 */
	@Transactional(rollbackFor=Exception.class)
	public void insertRateCd(RateMgmtVO searchVO){

		if(searchVO.getServiceType() == null || searchVO.getServiceType().equals("")){
			throw new MvnoRunException(-1, "서비스유형을 선택하세요");
		}
		
		if(searchVO.getRateCd() == null || searchVO.getRateCd().equals("")){
			throw new MvnoRunException(-1, "요금제코드를 입력하세요");
		}
		
		if(searchVO.getRateNm() == null || searchVO.getRateNm().equals("")){
			throw new MvnoRunException(-1, "요금제명을 입력하세요");
		}
		RateMgmtVO CodeResult = new RateMgmtVO();
		
		// 서비스 유형이 부가서비스이면 선후불구분 값을 null 로
		if(searchVO.getServiceType().equals("R")){
			searchVO.setPayClCd(null);
			searchVO.setRateGrpCd(null);
			searchVO.setRateType("00");
			searchVO.setOnlineTypeCd(null);
			searchVO.setFreeCallClCd(null);
			searchVO.setFreeCallCnt(null);
			searchVO.setFreeDataCnt(null);
			searchVO.setFreeSmsCnt(null);
			searchVO.setNwInCallCnt(null);
			searchVO.setNwOutCallCnt(null);
		} else {
			// 서비스유형이 요금제면 선후불구분값은 필수
			if(searchVO.getPayClCd() == null || searchVO.getPayClCd().equals("")){
				throw new MvnoRunException(-1, "선후불구분을 선택하세요");
			}
			
			//요금제 사용량 txt가져오기
			//무료통화(분)가져오기
			if(searchVO.getFreeCallCd().equals("A1") || searchVO.getFreeCallCd().equals("") )
			{
				searchVO.setFreeCallCnt(searchVO.getFreeCallCnt());
			}else{
				searchVO.setFreeCallCnt(rateMgmtMapper.selectFreeCallCnt(searchVO));
			}
			//(망내)무료통화(분)가져오기
			if(searchVO.getNwInCallCd().equals("A1") ||searchVO.getNwInCallCd().equals("") )
			{
				searchVO.setNwInCallCnt(searchVO.getNwInCallCnt());
			}else{
				searchVO.setNwInCallCnt(rateMgmtMapper.selectNwInCallCnt(searchVO));
			}
			//무료문자 가져오기
			if(searchVO.getFreeSmsCd().equals("A1") || searchVO.getFreeSmsCd().equals(""))
			{
				searchVO.setFreeSmsCnt(searchVO.getFreeSmsCnt());
			}else{
				searchVO.setFreeSmsCnt(rateMgmtMapper.selectFreeSmslCnt(searchVO));
			}
			//무료데이터 가져오기
			if(searchVO.getFreeDataCd().equals("A1") || searchVO.getFreeDataCd().equals(""))
			{
				searchVO.setFreeDataCnt(searchVO.getFreeDataCnt());
			}else{
				searchVO.setFreeDataCnt(rateMgmtMapper.selectFreeDataCnt(searchVO));
			}
		}
		
		if(searchVO.getRateType() == null || searchVO.getRateType().equals("")){
			throw new MvnoRunException(-1, "요금제유형을 선택하세요");
		}
		
		if(rateMgmtMapper.getRateCdChk(searchVO) > 0) {
			throw new MvnoRunException(-1, "이미 등록된 요금제코드입니다");
		}
		
		rateMgmtMapper.insertRateCd(searchVO);
	}
	
	
	/**
	 * 요금제수정
	 * @param searchVO
	 */
	@Transactional(rollbackFor=Exception.class)
	public void updateRateCd(RateMgmtVO searchVO){
		if(searchVO.getRateCd() == null || searchVO.getRateCd().equals("")){
			throw new MvnoRunException(-1, "요금제코드가 존재하지 않습니다");
		}
		
		// 기존 정보 만료일자 변경
		//rateMgmtMapper.updateRateCd(searchVO);
		
		rateMgmtMapper.insertRateCd(searchVO);
	}
	
	/**
	 * 요금제그룹목록조회
	 * @param searchVO
	 * @return
	 */
	public List<?> getRateGrpMgmtList(RateMgmtVO searchVO){
		logger.debug("searchVO = " + searchVO);
		
		return rateMgmtMapper.getRateGrpMgmtList(searchVO);
	}
	
	/**
	 * 요금제그룹등록
	 * @param searchVO
	 */
	@Transactional(rollbackFor=Exception.class)
	public void insertRateGrpCd(RateMgmtVO searchVO){
		if(searchVO.getRateGrpCd() == null || searchVO.getRateGrpCd().equals("")){
			throw new MvnoRunException(-1, "요금제그룹코드를 입력하세요");
		}
		
		if(searchVO.getRateGrpNm() == null || searchVO.getRateGrpNm().equals("")){
			throw new MvnoRunException(-1, "요금제그룹명을 입력하세요");
		}
		
//		if(rateMgmtMapper.getRateGrpCdChk(searchVO) > 0){
//			throw new MvnoRunException(-1, "이미 등록된 요금제그룹코드입니다");
//		}
		
		rateMgmtMapper.insertRateGrpCd(searchVO);
	}
	
	/**
	 * 요금제그룹등록
	 * @param searchVO
	 */
	@Transactional(rollbackFor=Exception.class)
	public void deleteRateGrpCd(RateMgmtVO searchVO){
		if(searchVO.getRateGrpCd() == null || searchVO.getRateGrpCd().equals("")){
			throw new MvnoRunException(-1, "요금제그룹코드를 입력하세요");
		}
		
		rateMgmtMapper.deleteRateGrpCd(searchVO);
		
		rateMgmtMapper.updateRateGrpCd(searchVO);
		
	}

	public List<?> getAddSvcList(){
		return rateMgmtMapper.getAddSvcList();
	}

	public List<?> getDisAddSvcList(){
		return rateMgmtMapper.getDisAddSvcList();
	}

	@Transactional(rollbackFor=Exception.class)
	public void manageDisAddSvcList(DisAddSvcVO disAddSvcVO){

		List<DisAddSvcVO> disAddSvcList = disAddSvcVO.getItems(); //화면에서 받은 변경된 부가서비스 리스트
		List<DisAddSvcVO> originalList = rateMgmtMapper.getDisAddSvcToVOList(); //기존 부가서비스 리스트

		for (DisAddSvcVO vo : disAddSvcList) {
			//유효성 검사 - etc5 중복여부 빈값 체크
			if (StringUtils.isEmpty(vo.getDupYn())) {
				throw new MvnoRunException(-1, "중복할인방지를 선택해 주세요");
			}
		}

		//1. 삭제
		for (DisAddSvcVO orgnList : originalList) {
			DisAddSvcVO deleteVO = new DisAddSvcVO();
			boolean isDelete = true;
			for (DisAddSvcVO newList : disAddSvcList) {
				if(orgnList.getRateCd().equals(newList.getRateCd())){ //같지 않다면
					isDelete = false;
					break;
				}
			}
			if(isDelete){
				deleteVO.setSessionUserId(disAddSvcVO.getSessionUserId());
				deleteVO.setRateCd(orgnList.getRateCd());
				rateMgmtMapper.deleteDisAddSvc(deleteVO);
			}
		}

		//2. 추가
		List<DisAddSvcVO> allDisAddSvcList = rateMgmtMapper.getAllDisAddSvcList(); //사용 여부에 관계 없는 기존 부가서비스 리스트

		for (DisAddSvcVO newDisAddSvc : disAddSvcList) {
			boolean isNew = true;
			newDisAddSvc.setSessionUserId(disAddSvcVO.getSessionUserId());

			for (DisAddSvcVO oldDisAddSvc : allDisAddSvcList) {
				if (newDisAddSvc.getRateCd().equals(oldDisAddSvc.getRateCd())) {
					isNew = false;
					if (KtisUtil.notEquals(newDisAddSvc.getDupYn(), oldDisAddSvc.getDupYn())
						|| KtisUtil.notEquals(newDisAddSvc.getUsgYn(), oldDisAddSvc.getUsgYn())) {
						rateMgmtMapper.updateDisAddSvc(newDisAddSvc);
					}
					break;
				}
			}
			if (isNew) {
				newDisAddSvc.setDupYn("Y".equals(disAddSvcVO.getDupYn()) ? "DIS" : null);
				rateMgmtMapper.insertDisAddSvc(newDisAddSvc);
			}
		}
	}

	public List<?> getRateComboList(){
		return rateMgmtMapper.getRateComboList();
	}

	public List<?> getPlcyComboList(RateMgmtVO searchVO){
		return rateMgmtMapper.getPlcyComboList(searchVO);
	}
	
	public List<?> getRateSpecList(RateMgmtVO searchVO){
		logger.debug("searchVO = " + searchVO);
		
		return rateMgmtMapper.getRateSpecList(searchVO);
	}
	
	public List<?> getRateSpecHist(RateMgmtVO searchVO){
		logger.debug("searchVO = " + searchVO);
		
		return rateMgmtMapper.getRateSpecHist(searchVO);
	}
	
	// 할인금액 등록
	@Transactional(rollbackFor=Exception.class)
	public void insertRateSpec(RateMgmtVO searchVO){
		logger.debug("searchVO = " + searchVO);
		
		if(searchVO.getApplStrtDttm() == null || searchVO.getApplStrtDttm().equals("")){
			throw new MvnoRunException(-1, "시작일자를 입력하세요");
		}
		
		long lStrtDt = Long.parseLong(searchVO.getApplStrtDttm());

		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMMdd", Locale.KOREA );
		Date currentTime = new Date ( );
		String dTime = formatter.format ( currentTime );
		
		long lToday = Long.parseLong(dTime);
		
		if ( lStrtDt < lToday ) {
			throw new MvnoRunException(-1, "시작일자는 오늘 이후만 가능합니다");
		}
		
		/*
		Integer iResult = rateMgmtMapper.getRateSpecCheck(searchVO);
		if(iResult > 0) {
			// delete
		}
		*/
		// 출시일자보다 이후의 날이 있으면 삭제한다.
		rateMgmtMapper.deleteRateSpec(searchVO);
		
		// update
		rateMgmtMapper.updateRateSpec(searchVO);
		// insert
		rateMgmtMapper.insertRateSpec(searchVO);
	}
	
	public List<?> getRateCancelSpecList(RateMgmtVO searchVO){
		logger.debug("searchVO = " + searchVO);
		
		return rateMgmtMapper.getRateCancelSpecList(searchVO);
	}
	
	public List<?> getRateCancelSpecHist(RateMgmtVO searchVO){
		logger.debug("searchVO = " + searchVO);
		
		return rateMgmtMapper.getRateCancelSpecHist(searchVO);
	}
	
	// 할인금액 등록
	@Transactional(rollbackFor=Exception.class)
	public void insertRateCancelSpec(RateMgmtVO searchVO){
		logger.debug("searchVO = " + searchVO);
		
		if(searchVO.getApplStrtDttm() == null || searchVO.getApplStrtDttm().equals("")){
			throw new MvnoRunException(-1, "시작일자를 입력하세요");
		}
		
		long lStrtDt = Long.parseLong(searchVO.getApplStrtDttm());

		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMMdd", Locale.KOREA );
		Date currentTime = new Date ( );
		String dTime = formatter.format ( currentTime );
		
		long lToday = Long.parseLong(dTime);
		
		if ( lStrtDt < lToday ) {
			throw new MvnoRunException(-1, "시작일자는 오늘 이후만 가능합니다");
		}
		
		// 출시일자보다 이후의 날이 있으면 삭제한다.
		rateMgmtMapper.deleteRateCancelSpec(searchVO);
		// update
		rateMgmtMapper.updateRateCancelSpec(searchVO);
		// insert
		rateMgmtMapper.insertRateCancelSpec(searchVO);
	}
	
	/**
	 * 요금제목록엑셀다운로드
	 * @param searchVO
	 * @return
	 */
	public List<RateMgmtVO> getRateMgmtListExcel(RateMgmtVO searchVO){
		
		return rateMgmtMapper.getRateMgmtListExcel(searchVO);
	}
	
	public List<?> getAllRateList(RateMgmtVO searchVO){
		return rateMgmtMapper.getAllRateList(searchVO);
	}
	
	public List<?> getRateMgmtRelList(RateMgmtVO searchVO){
		return rateMgmtMapper.getRateMgmtRelList(searchVO);
	}
	/*20190102 서비스관계관리 등록팝업 부가서비스조회*/
	public List<?> getRateMgmtRelListPop(RateMgmtVO searchVO){
		return rateMgmtMapper.getRateMgmtRelListPop(searchVO);
	}
	
	public List<?> getRateRelListAll(RateMgmtVO searchVO){
		return rateMgmtMapper.getRateRelListAll(searchVO);
	}
	
	public void insertRateRel(RateMgmtVO searchVO){
		rateMgmtMapper.insertRateRelHist(searchVO);
		rateMgmtMapper.insertRateRel(searchVO);
	}
	
	public void deleteRateRel(RateMgmtVO searchVO){
		rateMgmtMapper.insertRateRelHist(searchVO);
		rateMgmtMapper.deleteRateRel(searchVO);
	}
	
	public List<?> getRateGroupList(RateMgmtVO searchVO){
		
		List<EgovMap> result = (List<EgovMap>) rateMgmtMapper.getRateGroupList(searchVO);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", searchVO.getSessionUserId());
		
		maskingService.setMask(result, maskingService.getMaskFields(), paramMap);
		
		return result;
	}
	
	public void insertRateGroupInfo(RateMgmtVO searchVO){
		rateMgmtMapper.insertRateGroupInfo(searchVO);
	}
	
	public void updateRateGroupInfo(RateMgmtVO searchVO){
		rateMgmtMapper.updateRateGroupInfo(searchVO);
	}
	
	public void deleteRateGroupInfo(RateMgmtVO searchVO){
		rateMgmtMapper.deleteRateGroupInfo(searchVO);
		rateMgmtMapper.deleteRateGroupInnInfo(searchVO);
		rateMgmtMapper.deleteRateGroupRelInnInfo(searchVO);
	}
	
	public List<?> getGroupMappingRateList(RateMgmtVO searchVO){
		
		List<EgovMap> result = (List<EgovMap>) rateMgmtMapper.getGroupMappingRateList(searchVO);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", searchVO.getSessionUserId());
		
		maskingService.setMask(result, maskingService.getMaskFields(), paramMap);
		
		return result;
	}
	
	public void insertGroupMappingRateInfo(RateMgmtVO searchVO){
		rateMgmtMapper.insertGroupMappingRateInfo(searchVO);
	}
	
	public void deleteGroupMappingRateInfo(RateMgmtVO searchVO){
		rateMgmtMapper.deleteGroupMappingRateInfo(searchVO);
	}
	
	public List<?> getGroupRelRateList(RateMgmtVO searchVO){
		
		List<EgovMap> result = (List<EgovMap>) rateMgmtMapper.getGroupRelRateList(searchVO);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", searchVO.getSessionUserId());
		
		maskingService.setMask(result, maskingService.getMaskFields(), paramMap);
		
		return result;
	}
	
	public List<?> getGroupByRateReList(RateMgmtVO searchVO){
		return rateMgmtMapper.getGroupByRateReList(searchVO);
	}
	
	public void insertGroupRelRateInfo(RateMgmtVO searchVO){
		rateMgmtMapper.insertGroupRelRateInfo(searchVO);
	}
	
	public void deleteGroupRelRateInfo(RateMgmtVO searchVO){
		rateMgmtMapper.deleteGroupRelRateInfo(searchVO);
	}
	
	/**
	 * 결합요금제 매핑정보 목록 조회
	 * @param searchVO
	 * @return
	 */
	public List<?> getRateCombMappList(RateMgmtVO searchVO){
		logger.debug("searchVO = " + searchVO);
		
		List<EgovMap> result = (List<EgovMap>) rateMgmtMapper.getRateCombMappList(searchVO);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", searchVO.getSessionUserId());
		
		maskingService.setMask(result, maskingService.getMaskFields(), paramMap);
		
		return result;
	}
	
	/**
	 * 요금제목록엑셀다운로드
	 * @param searchVO
	 * @return
	 */
	public List<RateMgmtVO> getRateCombMappListExcel(RateMgmtVO searchVO){
		
		
		List<RateMgmtVO> result = rateMgmtMapper.getRateCombMappListExcel(searchVO);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", searchVO.getSessionUserId());
		
		maskingService.setMask(result, maskingService.getMaskFields(), paramMap);
		
		return result;
	
	}
	/**
	 * 결할할 요금제 조회
	 * **/
	public List<?> getRatePListAll(RateMgmtVO searchVO){
		return rateMgmtMapper.getRatePListAll(searchVO);
	}
	
	/**
	 * 부가서비스 전체 조회
	 * **/
	public List<?> getRateRListAll(RateMgmtVO searchVO){
		return rateMgmtMapper.getRateRListAll(searchVO);
	}
	
	/**
	 * 요금제등록
	 * @param searchVO
	 */
	@Transactional(rollbackFor=Exception.class)
	public void insertRateCombMapp(RateMgmtVO searchVO){

		if(searchVO.getAppStrDt() == null || searchVO.getAppStrDt().equals("")){
			throw new MvnoRunException(-1, "적용시작일자를 입력하세요.");
		}
		
		if(searchVO.getAppEndDt() == null || searchVO.getAppEndDt().equals("")){
			throw new MvnoRunException(-1, "적용종료일자를 입력하세요.");
		}
		
		if(searchVO.getpRateCd() == null || searchVO.getpRateCd().equals("")){
			throw new MvnoRunException(-1, "요금제코드를 선택하세요.");
		}
		
		if(searchVO.getrRateCd() == null || searchVO.getrRateCd().equals("")){
			throw new MvnoRunException(-1, "부가서비스코드를 선택하세요.");
		}
			
		if(rateMgmtMapper.getCombRateCount(searchVO) > 0) {
			throw new MvnoRunException(-1, "이미 등록된 요금제코드 입니다. 적용기간을 수정한 뒤 등록해주세요.");
		}
		
		rateMgmtMapper.insertRateCombMapp(searchVO);
	}
	
	/**
	 * 요금제수정
	 * @param searchVO
	 */
	@Transactional(rollbackFor=Exception.class)
	public void updateRateCombMapp(RateMgmtVO searchVO){
		
		if(searchVO.getAppEndDt() == null || searchVO.getAppEndDt().equals("")){
			throw new MvnoRunException(-1, "적용종료일자를 입력하세요.");
		}
		
		rateMgmtMapper.updateRateCombMapp(searchVO);
	}

	/**
	 * A'Cen 요금제 리스트 조회
	 * @param searchVO
	 * @return
	 */
	public List<?> getRateAcenMgmtList(RateMgmtVO searchVO){
		logger.debug("searchVO = " + searchVO);

		if(KtisUtil.isEmpty(searchVO.getStdrDt())){
			throw new MvnoRunException(-1, "기준일자가 존재하지 않습니다");
		}

		List<EgovMap> result = (List<EgovMap>) rateMgmtMapper.getRateAcenMgmtList(searchVO);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", searchVO.getSessionUserId());

		maskingService.setMask(result, maskingService.getMaskFields(), paramMap);

		return result;
	}

	 /**
	 * A'Cen 요금제 단건 등록/수정
	 * @param searchVO
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void mergeRateAcenCd(RateMgmtVO searchVO){

		// 일시 + 시간
		if(KtisUtil.isNotEmpty(searchVO.getPstngStartDate()) && KtisUtil.isNotEmpty(searchVO.getStartTm())){
			searchVO.setPstngStartDate(searchVO.getPstngStartDate().substring(0,8)+searchVO.getStartTm());
		}
		if(KtisUtil.isNotEmpty(searchVO.getPstngEndDate()) && KtisUtil.isNotEmpty(searchVO.getEndTm())){
			searchVO.setPstngEndDate(searchVO.getPstngEndDate().substring(0,8)+searchVO.getEndTm());
		}
		if(KtisUtil.isNotEmpty(searchVO.getPstngEndDateMod()) && KtisUtil.isNotEmpty(searchVO.getEndTmMod())){
			searchVO.setPstngEndDateMod(searchVO.getPstngEndDateMod().substring(0,8)+searchVO.getEndTmMod());
		}

		Map <String, String> chkDataMap = chkAcenDataValidate(searchVO);

		if(!"0000".equals(chkDataMap.get("code"))) {
			throw new MvnoRunException(-1, "["+chkDataMap.get("errMsg")+"]이/가 존재하지 않습니다");
		}
		if("I".equals(searchVO.getFlag())){
			if(rateMgmtMapper.getRateCdChk(searchVO) < 1){
				throw new MvnoRunException(-1, "기존 [요금제관리]메뉴에 존재하지 않는 요금제코드 입니다.");
			}
			if(rateMgmtMapper.getRateAcenCdChk(searchVO) > 0) {
				throw new MvnoRunException(-1, "이미 등록된 요금제코드입니다");
			}
			try{
				rateMgmtMapper.insertRateAcenCd(searchVO);
			} catch(Exception e) {
				throw new MvnoRunException(-1,"저장 처리 중 오류가 발생하였습니다.");
			}
		}
		else{
			if(rateMgmtMapper.getRateCdChk(searchVO) < 1){
				throw new MvnoRunException(-1, "기존 [요금제관리]메뉴에 존재하지 않는 요금제코드 입니다.");
			}
			try{
				rateMgmtMapper.updateRateAcenCd(searchVO);
			} catch(Exception e) {
				throw new MvnoRunException(-1,"저장 처리 중 오류가 발생하였습니다.");
			}
		}
	}

	/**
	 * A'Cen 요금제 엑셀등록
	 * @return
	 */
	public int regRateAcenExcel(RateMgmtVO vo){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		formatter.setLenient(false);

		int insertCnt = 0;
		List<RateMgmtVO> itemList = vo.getItems();
		if(KtisUtil.isEmpty(itemList)){
			throw new MvnoRunException(-1,"등록할 데이터가 없습니다.");
		}
		for(int i = 0; i < itemList.size(); i ++){
			RateMgmtVO item = itemList.get(i);

			item.setUserId(vo.getUserId());
			item.setFlag("I");

			// 일시 + 시간
			if(KtisUtil.isNotEmpty(item.getPstngStartDate()) && KtisUtil.isNotEmpty(item.getStartTm())){
				item.setPstngStartDate(item.getPstngStartDate().substring(0,8)+item.getStartTm());
			}
			if(KtisUtil.isNotEmpty(item.getPstngEndDate()) && KtisUtil.isNotEmpty(item.getEndTm())){
				item.setPstngEndDate(item.getPstngEndDate().substring(0,8)+item.getEndTm());
			}

			// 엑셀 내 중복데이터 검사
			for(int j = i+1; j <itemList.size(); j++){
				RateMgmtVO itemNext = itemList.get(j);

				//요금제코드 중복확인
				if(!item.getRateCd().equals(itemNext.getRateCd())){
					continue;
				}
				//날짜 중복확인
				try{
					// 일시 + 시간
					if(KtisUtil.isNotEmpty(itemNext.getPstngStartDate()) && KtisUtil.isNotEmpty(itemNext.getStartTm())){
						itemNext.setPstngStartDate(itemNext.getPstngStartDate().substring(0,8)+itemNext.getStartTm());
					}
					if(KtisUtil.isNotEmpty(itemNext.getPstngEndDate()) && KtisUtil.isNotEmpty(itemNext.getEndTm())){
						itemNext.setPstngEndDate(itemNext.getPstngEndDate().substring(0,8)+itemNext.getEndTm());
					}

					Date itemPstngStart = formatter.parse(item.getPstngStartDate());
					Date itemPstngEnd = formatter.parse(item.getPstngEndDate());
					Date itemNextPstngStart = formatter.parse(itemNext.getPstngStartDate());
					Date itemNextPstngEnd = formatter.parse(itemNext.getPstngEndDate());

					if( (itemPstngStart.after(itemNextPstngEnd) && itemNextPstngStart.after(itemPstngEnd) )||   //
						 itemPstngStart.equals(itemPstngEnd) ||
						 itemPstngStart.equals(itemNextPstngStart) ||
						 itemPstngStart.equals(itemNextPstngEnd) ||
						 itemPstngEnd.equals(itemNextPstngStart) ||
						 itemPstngEnd.equals(itemNextPstngEnd) ||
						 itemNextPstngStart.equals(itemNextPstngEnd) ){
						throw new MvnoRunException(-1,  (i+1) + "행 요금제코드[" +item.getRateCd() +"]는<br>" + (j+1) + "행 요금제코드[" + itemNext.getRateCd() + "]와<br> 중복되어 업로드 할수 없습니다.");
					}
				}catch (Exception e){
					throw new MvnoRunException(-1, e.getMessage());
				}
			}

			//데이터 유효성 체크
			Map <String, String> chkDataMap = chkAcenDataValidate(item);
			if(!"0000".equals(chkDataMap.get("code"))) {
				throw new MvnoRunException(-1, "엑셀데이터 "+ (i+1) +"행 요금제코드["+item.getRateCd() + "] 의 <br>["+chkDataMap.get("errMsg")+"]이/가 존재하지 않습니다");
			}

			//기존 요금제관리 메뉴에 등록된 요금제 코드인지 확인
			if(rateMgmtMapper.getRateCdChk(item) < 1){
				throw new MvnoRunException(-1, "엑셀데이터 "+ (i+1) +"행 요금제코드["+item.getRateCd() + "]는 <br>기존 [요금제관리]메뉴에 존재하지 않는 요금제코드 입니다.");
			}
			//중복 확인
			if(rateMgmtMapper.getRateAcenCdChk(item) > 0) {
				throw new MvnoRunException(-1, "엑셀데이터 "+ (i+1) +"행의 요금제코드["+item.getRateCd() + "]은 <br>이미 등록된 요금제코드입니다.");
			}
			
			try{
				rateMgmtMapper.insertRateAcenCd(item);
				insertCnt++;
			}catch (Exception e){
				throw new MvnoRunException(-1,"엑셀업로드 중 오류가 발생했습니다.");
			}

		}


		return insertCnt;
	}

	/**
	 * A'Cen 요금제 리스트 엑셀다운로드
	 * @return
	 */
	public List<RateMgmtVO> getRateAcenMgmtListExcel(RateMgmtVO searchVO){

		List<RateMgmtVO> result = rateMgmtMapper.getRateAcenMgmtListExcel(searchVO);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", searchVO.getSessionUserId());
		maskingService.setMask(result, maskingService.getMaskFields(), paramMap);

		return result;
	}
	/**
	 * A'Cen 요금제 이력 조회
	 * @return
	 */
	public List<?> getRateAcenHist(RateMgmtVO searchVO){
		logger.debug("searchVO = " + searchVO);

		List<?> result = rateMgmtMapper.getRateAcenHist(searchVO);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", searchVO.getSessionUserId());
		maskingService.setMask(result, maskingService.getMaskFields(), paramMap);

		return result;
	}

	/**
	 * A'Cen 요금제 이력 엑셀다운로드
	 * @return
	 */
	public List<RateMgmtVO> getRateAcenHistExcel(RateMgmtVO searchVO){


		List<RateMgmtVO> result = rateMgmtMapper.getRateAcenHistExcel(searchVO);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", searchVO.getSessionUserId());

		maskingService.setMask(result, maskingService.getMaskFields(), paramMap);

		return result;
	}

	/**
	 * @Description : A'cen 요금제 List 종료일 변경
	 */

	public void updRateAcenListEndDateMod(RateMgmtVO vo) {
		List<RateMgmtVO> itemList = vo.getItems();
		if(KtisUtil.isEmpty(itemList)){
			throw new MvnoRunException(-1,"변경할 데이터가 없습니다.");
		}
		for(RateMgmtVO item : itemList){
			item.setUserId(vo.getUserId());
			this.mergeRateAcenCd(item);
		}
	}

	/**
	 * A'Cen 요금제 엑셀데이터 데이터 유효성 체크
	 * @return
	 */
	public Map<String, String> chkAcenDataValidate(RateMgmtVO vo){
		Map<String, String> chkDataMap = new HashMap<String, String>();

		chkDataMap.put("code","0001");


		if(KtisUtil.isEmpty(vo.getRateCd())){
			chkDataMap.put("errMsg","요금제코드");
			return chkDataMap;
		}
		if(KtisUtil.isEmpty(vo.getPstngStartDate())){
			chkDataMap.put("errMsg","적용시작일자");
			return chkDataMap;
		}
		if(KtisUtil.isEmpty(vo.getPstngEndDate())){
			chkDataMap.put("errMsg","적용종료일자");
			return chkDataMap;
		}

		if("I".equals(vo.getFlag()) || "U".equals(vo.getFlag())){
			if(KtisUtil.isEmpty(vo.getRateNm())){
				chkDataMap.put("errMsg","요금제명");
				return chkDataMap;
			}
			if(KtisUtil.isEmpty(vo.getBaseVatAmt())){
				chkDataMap.put("errMsg","월정액(VAT포함)");
				return chkDataMap;
			}
			if(KtisUtil.isEmpty(vo.getPrmtVatAmt())){
				chkDataMap.put("errMsg","할인후월정액(VAT포함)");
				return chkDataMap;
			}
			if(KtisUtil.isEmpty(vo.getCallCnt())){
				chkDataMap.put("errMsg","음성(기본/분)");
				return chkDataMap;
			}
			if(KtisUtil.isEmpty(vo.getPrmtCallCnt())){
				chkDataMap.put("errMsg","음성(추가/분)");
				return chkDataMap;
			}
			if(KtisUtil.isEmpty(vo.getEtcCallCnt())){
				chkDataMap.put("errMsg","기타통화(분)");
				return chkDataMap;
			}
			if(KtisUtil.isEmpty(vo.getSmsCnt())){
				chkDataMap.put("errMsg","문자(기본/분)");
				return chkDataMap;
			}
			if(KtisUtil.isEmpty(vo.getPrmtSmsCnt())){
				chkDataMap.put("errMsg","문자(추가/분)");
				return chkDataMap;
			}
			if(KtisUtil.isEmpty(vo.getDataCnt())){
				chkDataMap.put("errMsg","데이터(기본/MB)");
				return chkDataMap;
			}
			if(KtisUtil.isEmpty(vo.getPrmtDataCnt())){
				chkDataMap.put("errMsg","데이터(추가/MB)");
				return chkDataMap;
			}
			if(KtisUtil.isEmpty(vo.getDayDataCnt())){
				chkDataMap.put("errMsg","데이터(일/MB)");
				return chkDataMap;
			}
			if(KtisUtil.isEmpty(vo.getQosDataCntDesc())){
				chkDataMap.put("errMsg","QOS");
				return chkDataMap;
			}
			if(KtisUtil.isEmpty(vo.getQosDataUnit())){
				chkDataMap.put("errMsg","QOS(단위)");
				return chkDataMap;
			}
		}

		//날짜 유효성 체크
		try {
			chkDataMap = chkDateAcenValidate(vo);
			if(!"0000".equals(chkDataMap.get("code"))) {
				throw new MvnoRunException(-1, chkDataMap.get("errMsg"));
			}
		} catch (Exception e) {
			throw new MvnoRunException(-1 , e.getMessage());
		}
		chkDataMap.put("code","0000");
		return chkDataMap;
	}
	/**
	 * A'Cen 요금제 날짜 체크
	 * @return
	 */
	private Map<String, String> chkDateAcenValidate(RateMgmtVO vo) {
		Map<String, String> chkDateMap = new HashMap<String, String>();
		chkDateMap.put("code","0001");

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		formatter.setLenient(false);
		Date now = new Date();

		try {
			//날짜 형식 자리수 체크
			if(vo.getPstngStartDate().length() < 14 || vo.getPstngEndDate().length() < 14 ){
				chkDateMap.put("errMsg", "[" +vo.getRateCd() + "] 날짜형식이 올바르지 않습니다.");
				return chkDateMap;
			}

			Date pstngStart = formatter.parse(vo.getPstngStartDate());
			Date pstngEnd = formatter.parse(vo.getPstngEndDate());

			if("U".equals(vo.getFlag()) || "L".equals(vo.getFlag())){
				if( vo.getPstngEndDateMod().length() < 14) {
					chkDateMap.put("errMsg", "[" +vo.getRateCd() + "] 날짜형식이 올바르지 않습니다.");
					return chkDateMap;
				}

				//변경 종료일시가 존재하는지 확인
				Date pstngEndMod = formatter.parse(vo.getPstngEndDateMod());
				
				//적용종료일시가 현재보다 이전인 경우 (이미 종료된 요금제코드)
				if(pstngEnd.before(now)) {
					chkDateMap.put("errMsg", "[" +vo.getRateCd() + "]이미 종료된 요금제는 수정 할 수 없습니다.");
					return chkDateMap;
				}
				//변경종료일시가 시작일시보다 이전인 경우
				if(pstngEndMod.before(pstngStart)) {
					chkDateMap.put("errMsg", "[" +vo.getRateCd() + "] 변경종료일시를 시작일시보다 이전으로 수정 할 수 없습니다.");
					return chkDateMap;
				}
				//변경종료일시가 현재 이전인 경우
				if(pstngEndMod.before(now)){
					chkDateMap.put("errMsg", "[" +vo.getRateCd() + "] 변경종료일시를 현재보다 이전으로 수정 할 수 없습니다.");
					return chkDateMap;
				}
				//** 최신으로 등록된 요금제코드가 아닌경우
				if(!"Y".equals(vo.getNewestYn())){
					//변경 종료일시가 기존 적용일시보다 큰 경우
					if(pstngEndMod.after(pstngEnd)){
						chkDateMap.put("errMsg", "[" +vo.getRateCd() + "] 변경종료일시를 기존 종료일시보다 이후로 수정 할 수 없습니다.");
						return chkDateMap;
					}
				}
			}
			else if("I".equals(vo.getFlag())){
				//시작일시와 종료일시가 동일한 경우
				if(pstngStart.equals(pstngEnd)) {
					chkDateMap.put("errMsg", "[" +vo.getRateCd() + "] 적용시작일시와 적용 종료일시를 동일하게 등록 할 수 없습니다.");
					return chkDateMap;
				}
				//시작일시가 현재보다 이전인 경우
				if(pstngStart.before(now)) {
					chkDateMap.put("errMsg", "[" +vo.getRateCd() + "] 적용시작일시를 현재보다 이전으로 등록 할 수 없습니다.");
					return chkDateMap;
				}
				//시작일시가 종료일시보다 이후 인 경우
				if(pstngStart.after(pstngEnd)) {
					chkDateMap.put("errMsg", "[" +vo.getRateCd() + "] 적용시작일시가 적용 종료일시보다 이후로 등록 할 수 없습니다.");
					return chkDateMap;
				}
			}
		}
		catch (Exception e){
			throw new MvnoRunException(-1, "날짜형식이 맞지않습니다. 올바른 날짜를 입력해주세요");
		}
		chkDateMap.put("code", "0000");
		return chkDateMap;
	}

}
	
