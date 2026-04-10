package com.ktis.msp.org.prmtmgmt.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.org.prmtmgmt.mapper.DisPrmtMgmtMapper;
import com.ktis.msp.org.prmtmgmt.mapper.PromotionMapper;
import com.ktis.msp.org.prmtmgmt.vo.ChrgPrmtMgmtCopyVO;
import com.ktis.msp.org.prmtmgmt.vo.ChrgPrmtMgmtSubVO;
import com.ktis.msp.org.prmtmgmt.vo.ChrgPrmtMgmtVO;
import com.ktis.msp.org.prmtmgmt.vo.DisPrmtMgmtSubVO;
import com.ktis.msp.org.prmtmgmt.vo.DisPrmtMgmtVO;
import com.ktis.msp.org.prmtmgmt.vo.RecommenIdStateVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpDetailVO;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service
public class PromotionService extends BaseService {
    @Autowired
    private DisPrmtMgmtMapper disPrmtMgmtMapper;

	@Autowired
	private PromotionMapper promotionMapper;
	
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
	
	
	public List<ChrgPrmtMgmtVO> getChrgPrmtList(ChrgPrmtMgmtVO chrgPrmtMgmtVO) throws EgovBizException {
		
		if(chrgPrmtMgmtVO.getSearchBaseDate() == null || "".equals(chrgPrmtMgmtVO.getSearchBaseDate())) {
			throw new EgovBizException("기준일자가 존재하지 않습니다.");
		}
		
		List<ChrgPrmtMgmtVO> result = promotionMapper.getChrgPrmtList(chrgPrmtMgmtVO);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", chrgPrmtMgmtVO.getSessionUserId());
		
		maskingService.setMask(result, maskingService.getMaskFields(), paramMap);
		
		return result;
	}
	
	public List<ChrgPrmtMgmtSubVO> getChrgPrmtOrgnList(ChrgPrmtMgmtSubVO chrgPrmtMgmtSubVO) throws EgovBizException {
		
		return promotionMapper.getChrgPrmtOrgnList(chrgPrmtMgmtSubVO);
	}
	
	public List<ChrgPrmtMgmtSubVO> getChrgPrmtSocList(ChrgPrmtMgmtSubVO chrgPrmtMgmtSubVO) throws EgovBizException {
		
		return promotionMapper.getChrgPrmtSocList(chrgPrmtMgmtSubVO);
	}
	
	public List<ChrgPrmtMgmtSubVO> getChrgPrmtAddList(ChrgPrmtMgmtSubVO chrgPrmtMgmtSubVO) throws EgovBizException {
		
		return promotionMapper.getChrgPrmtAddList(chrgPrmtMgmtSubVO);
	}
	
	public void regChrgPrmtInfo(ChrgPrmtMgmtVO chrgPrmtMgmtVO) throws EgovBizException {
		
		if(chrgPrmtMgmtVO.getPrmtNm() == null || "".equals(chrgPrmtMgmtVO.getPrmtNm())){
			throw new EgovBizException("프로모션명이 존재하지 않습니다");
		}
		
		if(chrgPrmtMgmtVO.getStrtDt() == null || "".equals(chrgPrmtMgmtVO.getStrtDt())){
			throw new EgovBizException("프로모션시작일자가 존재하지 않습니다");
		}
		
		if(chrgPrmtMgmtVO.getEndDt() == null || "".equals(chrgPrmtMgmtVO.getEndDt())){
			throw new EgovBizException("프로모션종료일자가 존재하지 않습니다");
		}
		
		if((chrgPrmtMgmtVO.getNacYn() == null || "".equals(chrgPrmtMgmtVO.getNacYn())) &&
				(chrgPrmtMgmtVO.getMnpYn() == null || "".equals(chrgPrmtMgmtVO.getMnpYn()))){
			throw new EgovBizException("가입유형 정보가 존재하지 않습니다");
		}
		
		if((chrgPrmtMgmtVO.getEnggCnt0() == null || "".equals(chrgPrmtMgmtVO.getEnggCnt0())) &&
				(chrgPrmtMgmtVO.getEnggCnt12() == null || "".equals(chrgPrmtMgmtVO.getEnggCnt12())) && 
				(chrgPrmtMgmtVO.getEnggCnt18() == null || "".equals(chrgPrmtMgmtVO.getEnggCnt18())) &&
				(chrgPrmtMgmtVO.getEnggCnt24() == null || "".equals(chrgPrmtMgmtVO.getEnggCnt24())) &&
				(chrgPrmtMgmtVO.getEnggCnt30() == null || "".equals(chrgPrmtMgmtVO.getEnggCnt30())) &&
				(chrgPrmtMgmtVO.getEnggCnt36() == null || "".equals(chrgPrmtMgmtVO.getEnggCnt36()))){
			throw new EgovBizException("약정기간 정보가 존재하지 않습니다");
		}
		
		if(chrgPrmtMgmtVO.getOrgnList().size() < 1){
			throw new EgovBizException("조직 정보가 존재하지 않습니다");
		}
		
		if(chrgPrmtMgmtVO.getRateList().size() < 1){
			throw new EgovBizException("요금제 정보가 존재하지 않습니다");
		}
		
		if(chrgPrmtMgmtVO.getVasList().size() < 1){
			throw new EgovBizException("부가서비스 정보가 존재하지 않습니다");
		}
		
		if(chrgPrmtMgmtVO.getOnOffTypeList().size() < 1){
			throw new EgovBizException("모집경로 정보가 존재하지 않습니다");
		}
		
		List<ChrgPrmtMgmtVO> aryDupChkList = promotionMapper.getChrgPrmtDupByInfo(chrgPrmtMgmtVO);
		
		// 중복된 조직, 요금제, 모집경로가 존재하는 지 확인
		for(int idx1 = 0; idx1 < chrgPrmtMgmtVO.getOrgnList().size(); idx1++){
			for(int idx2 = 0; idx2 < chrgPrmtMgmtVO.getRateList().size(); idx2++){
				for(int idx3 = 0; idx3 < chrgPrmtMgmtVO.getOnOffTypeList().size(); idx3++){
					for(int idx4 = 0; idx4 < aryDupChkList.size(); idx4++){
					   if(aryDupChkList.get(idx4).getOrgnId().equals(chrgPrmtMgmtVO.getOrgnList().get(idx1).getOrgnId())
					         && aryDupChkList.get(idx4).getRateCd().equals(chrgPrmtMgmtVO.getRateList().get(idx2).getRateCd())
					         && aryDupChkList.get(idx4).getOnOffType().equals(chrgPrmtMgmtVO.getOnOffTypeList().get(idx3).getOnOffType())){
					      String strChkPrmtId = aryDupChkList.get(idx4).getPrmtId();
                     throw new EgovBizException("이미 등록된 프로모션["+ strChkPrmtId +"]이 존재합니다.");
					   }
					}
				}
			}
		}
		
		try {
			// 채널별 요금 할인 마스터 추가
			promotionMapper.insertMspChrgPrmt(chrgPrmtMgmtVO);
			
			String strPrmtId = chrgPrmtMgmtVO.getPrmtId();
			String strUserId = chrgPrmtMgmtVO.getSessionUserId();
			
			// 채널별 요금 할인 조직 추가
			try {
				for(ChrgPrmtMgmtSubVO orgnVO : chrgPrmtMgmtVO.getOrgnList()) {
					orgnVO.setPrmtId(strPrmtId);
					orgnVO.setSessionUserId(strUserId);
					promotionMapper.insertMspChrgPrmtOrg(orgnVO);
				}
			} catch (EgovBizException e) {
				e.setMessage("조직 정보 저장 오류!!");
				throw e;
			}
			
			// 채널별 요금 할인 부가서비스 추가
			try {
				for(ChrgPrmtMgmtSubVO rateVO : chrgPrmtMgmtVO.getRateList()) {
					rateVO.setPrmtId(strPrmtId);
					rateVO.setSessionUserId(strUserId);
					promotionMapper.insertMspChrgPrmtSoc(rateVO);
				}
			} catch (EgovBizException e) {
				e.setMessage("요금제 정보 저장 오류!!");
				throw e;
			}
			
			// 채널별 요금 할인 부가서비스 추가
			try {
				List<ChrgPrmtMgmtVO> addVasList = new ArrayList<ChrgPrmtMgmtVO>();
				ChrgPrmtMgmtVO addVasVo = null;
				
				for(int idx1 = 0; idx1 < chrgPrmtMgmtVO.getVasList().size(); idx1++){
					int nSeq = 0;
					
					addVasVo = new ChrgPrmtMgmtVO();
					
					addVasList.add(addVasVo);
					
					if(chrgPrmtMgmtVO.getVasList().get(idx1).getVasCd() == null || "".equals(chrgPrmtMgmtVO.getVasList().get(idx1).getVasCd())){
						throw new EgovBizException("부가서비스 정보가 존재하지 않습니다.");
					}
					
					addVasVo.setSoc(chrgPrmtMgmtVO.getVasList().get(idx1).getVasCd());
					addVasVo.setPrmtId(strPrmtId);
					addVasVo.setSessionUserId(strUserId);
					
					for(int idx2 = 0; idx2 < addVasList.size(); idx2++){
						if(chrgPrmtMgmtVO.getVasList().get(idx1).getVasCd().equals(addVasList.get(idx2).getSoc())){
							nSeq++;
						}
					}
					
					addVasVo.setSeq(String.valueOf(nSeq));
				}
				
				for(ChrgPrmtMgmtVO addVO : addVasList) {
					
					promotionMapper.insertMspChrgPrmtAdd(addVO);
				}
			} catch (EgovBizException e) {
				logger.info(e.getMessage());
				
				throw e;
			} catch(Exception e) {
				logger.info(e.getMessage());
				
				throw new EgovBizException("부가서비스 정보 저장 오류!!.");
			}
			
			// 모집경로 추가
			try {
				for(ChrgPrmtMgmtSubVO onOffVO : chrgPrmtMgmtVO.getOnOffTypeList()) {
					onOffVO.setPrmtId(strPrmtId);
					onOffVO.setSessionUserId(strUserId);
					promotionMapper.insertMspChrgPrmtOnOff(onOffVO);
				}
			} catch (EgovBizException e) {
				e.setMessage("모집경로 정보 저장 오류!!");
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
	
	public int updChrgPrmtByInfo(ChrgPrmtMgmtVO chrgPrmtMgmtVO) throws EgovBizException {
		int result = 0;
		ChrgPrmtMgmtVO resultVo = chrgPrmtMgmtVO;
		
		if(chrgPrmtMgmtVO.getPrmtId() == null || "".equals(chrgPrmtMgmtVO.getPrmtId())) {
			throw new EgovBizException("프로모션ID가 존재하지 않습니다.");
		}
		
		if(chrgPrmtMgmtVO.getChngTypeCd() == null || "".equals(chrgPrmtMgmtVO.getChngTypeCd())) {
			throw new EgovBizException("변경 유형 코드가 존재하지 않습니다.");
		}
		
		if("U".equals(chrgPrmtMgmtVO.getChngTypeCd())){
			resultVo = promotionMapper.getChrgPrmtDtlInfo(chrgPrmtMgmtVO);
			if("N".equals(resultVo.getUsgYn())){
				throw new MvnoRunException (-1, "프로모션 ID [" +resultVo.getPrmtId()+ "] 프로모션은 이미 삭제된 프로모션으로 종료일시를 변경 할 수 없습니다.");
			}
			
			resultVo.setSessionUserId(chrgPrmtMgmtVO.getSessionUserId());
			resultVo.setChngTypeCd(chrgPrmtMgmtVO.getChngTypeCd());
			resultVo.setEndDt(chrgPrmtMgmtVO.getEndDt());
			List<ChrgPrmtMgmtVO> aryDupChkList = promotionMapper.getChrgPrmtDupByInfo(resultVo);
			
			if(aryDupChkList.size() > 0){
				throw new MvnoRunException(-1, "해당 기간에 프로모션["+ aryDupChkList.get(0).getPrmtId() +"]이 존재합니다.");
			}
		}
		
		try {
			result = promotionMapper.updChrgPrmtByInfo(resultVo);
		} catch (EgovBizException e) {
			e.setMessage("저장 처리 중 오류가 발생하였습니다.");
			throw e;
		}
		
		return result;
	}
	
	public List<ChrgPrmtMgmtVO> getChrgPrmtDtlList(ChrgPrmtMgmtVO chrgPrmtMgmtVO) throws EgovBizException {
		
		if(chrgPrmtMgmtVO.getPrmtId() == null || "".equals(chrgPrmtMgmtVO.getPrmtId())) {
			throw new EgovBizException("프로모션ID가 존재하지 않습니다.");
		}
		
		return promotionMapper.getChrgPrmtDtlList(chrgPrmtMgmtVO);
	}
	
	/**
	 * 모집경로
	 */
	public List<ChrgPrmtMgmtSubVO> getChrgPrmtOnoffList(ChrgPrmtMgmtSubVO chrgPrmtMgmtSubVO) throws EgovBizException {
		
		return promotionMapper.getChrgPrmtOnoffList(chrgPrmtMgmtSubVO);
	}
	
	
	/**
	 * 프로모션 복사
	 */
	@Transactional(rollbackFor=Exception.class)
	@Async
	public void copyChrgPrmtInfo(ChrgPrmtMgmtCopyVO chrgPrmtMgmtCopyVO) throws EgovBizException {
		
		if(chrgPrmtMgmtCopyVO.getPrmtId() == null || chrgPrmtMgmtCopyVO.getPrmtId().equals("")) {
			throw new MvnoRunException(-1, "기존 프로모션ID가 존재하지 않습니다");
		}
		
		try {
			
			// 신규 프로모션ID 조회
			chrgPrmtMgmtCopyVO.setNewPrmtId(promotionMapper.getPrmtId());
			
			// 채널별 요금 할인 마스터 복사
			promotionMapper.insertMspChrgPrmtCopy(chrgPrmtMgmtCopyVO);
			
			// 채널별 요금 할인 조직 복사
			promotionMapper.insertMspChrgPrmtOrgCopy(chrgPrmtMgmtCopyVO);
			// 채널별 요금 할인 요금제 복사
			promotionMapper.insertMspChrgPrmtSocCopy(chrgPrmtMgmtCopyVO);
			// 채널별 요금 할인 부가서비스 복사
			promotionMapper.insertMspChrgPrmtAddCopy(chrgPrmtMgmtCopyVO);
			// 모집경로 복사
			promotionMapper.insertMspChrgPrmtOnOffCopy(chrgPrmtMgmtCopyVO);
			
		} catch(MvnoRunException e) {
			logger.info(e.getMessage());
			throw e;
		} catch(Exception e) {
			logger.info(e.getMessage());
			throw new MvnoRunException(-1, "정책복사 중 오류가 발생하였습니다");
		}
		
	}

	public List<RecommenIdStateVO> getRecommenIdStateList(RecommenIdStateVO recommenIdStateVO, Map<String, Object> paramMap) throws EgovBizException {
		if( "".equals(recommenIdStateVO.getSearchCd()) && "".equals(recommenIdStateVO.getSearchFromDt()) ){
			throw new MvnoRunException(-1, "시작일자가 존재하지 않습니다.");
		}
		
		if( "".equals(recommenIdStateVO.getSearchCd()) && "".equals(recommenIdStateVO.getSearchToDt()) ){
			throw new MvnoRunException(-1, "종료일자가 존재하지 않습니다.");
		}
		
		if( !"".equals(recommenIdStateVO.getSearchCd()) && "".equals(recommenIdStateVO.getSearchVal()) ){
			throw new MvnoRunException(-1, "검색어가 존재하지 않습니다.");
		}

		List<RecommenIdStateVO> rsltAryListVo = promotionMapper.getRecommenIdStateList(recommenIdStateVO);

		HashMap<String, String> maskFields = getMaskFields();

		maskingService.setMask(rsltAryListVo, maskFields, paramMap);

		return rsltAryListVo;
	}

	public List<RecommenIdStateVO> getRecommenIdStateListByExcel(RecommenIdStateVO recommenIdStateVO, Map<String, Object> paramMap) throws EgovBizException {
		if( "".equals(recommenIdStateVO.getSearchCd()) && "".equals(recommenIdStateVO.getSearchFromDt()) ){
			throw new MvnoRunException(-1, "시작일자가 존재하지 않습니다.");
		}
		
		if( "".equals(recommenIdStateVO.getSearchCd()) && "".equals(recommenIdStateVO.getSearchToDt()) ){
			throw new MvnoRunException(-1, "종료일자가 존재하지 않습니다.");
		}
		
		if( !"".equals(recommenIdStateVO.getSearchCd()) && "".equals(recommenIdStateVO.getSearchVal()) ){
			throw new MvnoRunException(-1, "검색어가 존재하지 않습니다.");
		}
		
		List<RecommenIdStateVO> rsltAryListVo = promotionMapper.getRecommenIdStateListByExcel(recommenIdStateVO);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(rsltAryListVo, maskFields, paramMap);
		
		return rsltAryListVo;
	}


	public List<RecommenIdStateVO> getRecommenHstList(RecommenIdStateVO recommenIdStateVO,
			Map<String, Object> paramMap) throws EgovBizException {
		if( "".equals(recommenIdStateVO.getSearchCd()) && "".equals(recommenIdStateVO.getDeSearchCd()) && "".equals(recommenIdStateVO.getSearchFromDt()) ){
			throw new MvnoRunException(-1, "시작일자가 존재하지 않습니다.");
		}
		
		if( "".equals(recommenIdStateVO.getSearchCd()) && "".equals(recommenIdStateVO.getDeSearchCd()) && "".equals(recommenIdStateVO.getSearchToDt()) ){
			throw new MvnoRunException(-1, "종료일자가 존재하지 않습니다.");
		}
		
		if( !"".equals(recommenIdStateVO.getSearchCd()) && "".equals(recommenIdStateVO.getDeSearchCd()) && "".equals(recommenIdStateVO.getSearchVal()) ){
			throw new MvnoRunException(-1, "검색어가 존재하지 않습니다.");
		}

		List<RecommenIdStateVO> rsltAryListVo = promotionMapper.getRecommenHstList(recommenIdStateVO);

		HashMap<String, String> maskFields = getMaskFields();

		maskingService.setMask(rsltAryListVo, maskFields, paramMap);

		return rsltAryListVo;
	}


	public List<RecommenIdStateVO> getRecommenHstListByExcel(RecommenIdStateVO recommenIdStateVO,
			Map<String, Object> paramMap) throws EgovBizException {
		if( "".equals(recommenIdStateVO.getSearchCd()) && "".equals(recommenIdStateVO.getDeSearchCd()) && "".equals(recommenIdStateVO.getSearchFromDt()) ){
			throw new MvnoRunException(-1, "시작일자가 존재하지 않습니다.");
		}
		
		if( "".equals(recommenIdStateVO.getSearchCd()) && "".equals(recommenIdStateVO.getDeSearchCd()) && "".equals(recommenIdStateVO.getSearchToDt()) ){
			throw new MvnoRunException(-1, "종료일자가 존재하지 않습니다.");
		}
		
		if( !"".equals(recommenIdStateVO.getSearchCd()) && "".equals(recommenIdStateVO.getDeSearchCd()) && "".equals(recommenIdStateVO.getSearchVal()) ){
			throw new MvnoRunException(-1, "검색어가 존재하지 않습니다.");
		}

		List<RecommenIdStateVO> rsltAryListVo = promotionMapper.getRecommenHstListByExcel(recommenIdStateVO);

		HashMap<String, String> maskFields = getMaskFields();

		maskingService.setMask(rsltAryListVo, maskFields, paramMap);

		return rsltAryListVo;
	}


	public void regBenefitsList(RecommenIdStateVO recommenIdStateVO, String userId) throws EgovBizException{
		List<RecommenIdStateVO> itemList = recommenIdStateVO.getItems();
		
		for( RecommenIdStateVO vo : itemList ) {
			if (vo.getUploadContractNum() == null || "".equals(vo.getUploadContractNum())){
				throw new MvnoRunException(-1, "피추천인계약번호가 존재하지 않습니다.");
			}
			
			if (promotionMapper.getdContractChk(vo.getUploadContractNum()) == 0) {
				throw new MvnoRunException(-1, "피추천인계약번호가 존재하지 않습니다.["+vo.getUploadContractNum()+"]");
			}
			if (promotionMapper.getdContractDupChk(vo.getUploadContractNum()) > 0) {
				throw new MvnoRunException(-1, "이미 혜택이 적용된 피추천인계약번호가 존재합니다.["+vo.getUploadContractNum()+"]");
			}
						
			vo.setSessionUserId(userId);
			
			promotionMapper.updateBenefits(vo);
						
		}
	}
	
	//채널별 요금할인 엑셀 업로드 자료(Data) 유효성 검사
	public Map<String, String> chkDataChrgValidate(ChrgPrmtMgmtVO vo, String regType){
		Map<String, String> chkDataMap = new HashMap<String, String>();
		chkDataMap.put("code","0001");

		if(KtisUtil.isEmpty(vo.getPrmtNm())){
			chkDataMap.put("errMsg","프로모션명");
			return chkDataMap;
		}

		if("EXCEL".equals(regType)) {
			if(KtisUtil.isEmpty(vo.getStrtDt())){

				chkDataMap.put("errMsg","시작일자");
				return chkDataMap;
			}
			if(KtisUtil.isEmpty(vo.getEndDt())){
				chkDataMap.put("errMsg","종료일자");
				return chkDataMap;
			}

			if(KtisUtil.isEmpty(vo.getOrgnId())){
				chkDataMap.put("errMsg","조직정보");
				return chkDataMap;
			}
			if(KtisUtil.isEmpty(vo.getRateCd())){
				chkDataMap.put("errMsg","요금제정보");
				return chkDataMap;
			}
			if(KtisUtil.isEmpty(vo.getOnOffType())){
				chkDataMap.put("errMsg","모집경로정보");
				return chkDataMap;
			}
			if(KtisUtil.isEmpty(vo.getSoc())){
				chkDataMap.put("errMsg","부가서비스정보");
				return chkDataMap;
			}
		}
		//날짜 유효성 체크
		vo.setChngTypeCd("I");
		try {
			boolean isChecked = chkDateChrgValidate(vo, vo.getChngTypeCd());
			if(!isChecked) {
				throw new MvnoRunException(-1, "올바른 날짜를 입력해주세요.");
			}
		} catch (Exception e) {
			chkDataMap.put("errMsg","시작일/종료일");
			return chkDataMap;
		}

		if(KtisUtil.isEmpty(vo.getReqBuyType())){
			chkDataMap.put("errMsg","구매유형");
			return chkDataMap;
		}
		if(KtisUtil.isEmpty(vo.getNacYn()) || KtisUtil.isEmpty(vo.getMnpYn())){
			chkDataMap.put("errMsg","가입유형");
			return chkDataMap;
		}

		if(KtisUtil.isEmpty(vo.getEnggCnt0()) ||
				KtisUtil.isEmpty(vo.getEnggCnt12()) ||
				KtisUtil.isEmpty(vo.getEnggCnt18()) ||
				KtisUtil.isEmpty(vo.getEnggCnt24()) ||
				KtisUtil.isEmpty(vo.getEnggCnt30()) ||
				KtisUtil.isEmpty(vo.getEnggCnt36()) ){
			chkDataMap.put("errMsg","약정기간");
			return chkDataMap;
		}

		chkDataMap.put("code","0000");
		return chkDataMap;
	}

	//채널별 요금할인 엑셀 업로드 날짜(Date) 유효성 검사
	private boolean chkDateChrgValidate(ChrgPrmtMgmtVO vo, String typeCd) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		formatter.setLenient(false);
		Date now = new Date();
		try {

			Date strtDt = formatter.parse(vo.getStrtDt());
			Date endDt = formatter.parse(vo.getEndDt());

			if("U".equals(typeCd)) {
				// 1.프로모션의 종료일자가 현재보다 이전인 경우 (이미 종료된 프로모션)
				if(endDt.before(now)) {
					return false;
				}
				// 2.변경종료일자가 프로모션의 시작일자보다 이전인 경우
				if(endDt.before(strtDt)) {
					return false;
				}
				// 3.변경종료일자가 현재 이전인 경우
				if(endDt.before(now)) {
					return false;
				}
			}
			else if("I".equals(typeCd)) {

				// 1.시작일자가 현재보다 이전인 경우
				if(strtDt.before(now)) {
					return false;
				}
				// 2.시작일자가 종료일자보다 이후 인 경우
				if(strtDt.after(endDt)) {
					return false;
				}
			}
		}
		catch(Exception e){
			throw new MvnoRunException(-1, "올바른 날짜를 입력해주세요");
		}

		return true;
	}

    /**
	 * @Description : 채널별 요금할인 엑셀 등록
	 * @Param  : chrgPrmtMgmtVO
	 * @Author : 김동혁
	 * @Create Date : 2023.10.06.
	 */
    public int regChrgPrmtInfoExcel(ChrgPrmtMgmtVO vo) {
    	int insertCnt = 0;
    	int chkCnt = disPrmtMgmtMapper.getDisMstChk();
		if(chkCnt>0) {
			throw new MvnoRunException(-1 ,"이미 진행중인 업로드가 있어 업로드가 불가합니다. 이전 업로드가 완료된후 다시 시도해주세요");
		}

		List<ChrgPrmtMgmtVO> itemList = vo.getItems();
		if(KtisUtil.isEmpty(itemList)){
			throw new MvnoRunException(-1,"등록할 데이터가 없습니다.");
		}

		Map <String, String> chkDataMap = null;
		String lastInsertRegNo="";
		ChrgPrmtMgmtVO insertTrgVo = new ChrgPrmtMgmtVO();

		Set<String> orgnSetExl = new HashSet<String>();
		Set<String> socSetExl = new HashSet<String>();
		Set<String> onOffSetExl = new HashSet<String>();
		Set<String> addSetExl = new HashSet<String>();

		for(int i = 0; i < itemList.size(); i++) {
			ChrgPrmtMgmtVO item = itemList.get(i);
			chkDataMap = chkDataChrgValidate(item, "EXCEL");

			if(!"0000".equals(chkDataMap.get("code"))) {
				throw new MvnoRunException(-1, "엑셀데이터 "+ (i+1) +"행의 ["+chkDataMap.get("errMsg")+"]이/가 유효하지 않습니다");
			}
			// 새로운 프로모션의 시작여부
			if( i == 0 || !item.getRegNum().equals(itemList.get(i-1).getRegNum())) {
				if(i != 0) {
					List<String> insertOrgnList = new ArrayList<String>(orgnSetExl);
					List<String> insertSocList = new ArrayList<String>(socSetExl);
					List<String> insertOnOffList = new ArrayList<String>(onOffSetExl);
					List<String> insertAddList = new ArrayList<String>(addSetExl);

					insertTrgVo.setOrgnListExl(insertOrgnList);
					insertTrgVo.setSocListExl(insertSocList);
					insertTrgVo.setOnOffListExl(insertOnOffList);
					insertTrgVo.setAddListExl(insertAddList);
					insertTrgVo.setOrgnType(item.getOrgnType());

					try {
						duplicateChrgPrmtChk(insertTrgVo, "EXCEL");
					}catch (MvnoRunException e){
						if("EXCEL".equals(e.getMessage())) {
							throw new MvnoRunException(-1,"엑셀내에 중복된 프로모션이 존재하여 업로드 할수 없습니다.");
						} else if("ORGN_TYPE".equals(e.getMessage())){
							throw new MvnoRunException(-1,"채널유형과 대상조직이 맞지 않아 업로드 할수 없습니다.");
						} else {
							throw new MvnoRunException(-1,"엑셀데이터 프로모션번호 ["+ insertTrgVo.getRegNum() +"] 는 "+"이미 등록된 프로모션["+ e.getMessage() +"]이 존재합니다.");
						}
					}
					// **********************Chk 테이블로 INSERT **********************
					// 엑셀데이터 마스터 Chk테이블에 추가
					try {
						lastInsertRegNo = insertTrgVo.getRegNum();
						promotionMapper.insertChrgPrmtMstChk(insertTrgVo);    	 //마스터 Chk 테이블에 Insert
						// 엑셀데이터 조직 Chk테이블에 추가
						for(int idx=0; idx<insertTrgVo.getOrgnListExl().size(); idx++){
							insertTrgVo.setOrgnId(insertTrgVo.getOrgnListExl().get(idx));
							promotionMapper.insertChrgPrmtOrgChk(insertTrgVo);	 //조직 Chk 테이블에 Insert
						}
						// 엑셀데이터 요금제 Chk테이블에 추가
						for(int idx=0; idx<insertTrgVo.getSocListExl().size(); idx++){
							insertTrgVo.setRateCd(insertTrgVo.getSocListExl().get(idx));
							promotionMapper.insertChrgPrmtSocChk(insertTrgVo);//요금제 Chk 테이블에 Insert
						}
						// 엑셀데이터 모집경로 Chk테이블에 추가
						for(int idx=0; idx<insertTrgVo.getOnOffListExl().size(); idx++){
							insertTrgVo.setOnOffType(insertTrgVo.getOnOffListExl().get(idx));
							promotionMapper.insertChrgPrmtOnOffChk(insertTrgVo);//Chk 테이블에 Insert
						}
						// 엑셀데이터 부가서비스 Chk테이블에 추가

						for(int idx=0; idx<insertTrgVo.getAddListExl().size(); idx++){
							insertTrgVo.setSoc(insertTrgVo.getAddListExl().get(idx));
							insertTrgVo.setSeq("1");
							promotionMapper.insertChrgPrmtAddChk(insertTrgVo); //Chk 테이블에 Insert
						}
					}catch(Exception e) {
						throw new MvnoRunException(-1,"엑셀업로드 중 오류가 발생했습니다.");
				    }
					insertCnt++;
					orgnSetExl.clear();
					socSetExl.clear();
					onOffSetExl.clear();
					addSetExl.clear();
				}
				insertTrgVo = new ChrgPrmtMgmtVO();
				insertTrgVo.setSessionUserId(vo.getSessionUserId());
				insertTrgVo.setRegNum(item.getRegNum());
				insertTrgVo.setPrmtNm(item.getPrmtNm());
				insertTrgVo.setStrtDt(item.getStrtDt());
				insertTrgVo.setEndDt(item.getEndDt());
				insertTrgVo.setOrgnType(item.getOrgnType());
				insertTrgVo.setReqBuyType(item.getReqBuyType());
				insertTrgVo.setNacYn(item.getNacYn());
				insertTrgVo.setMnpYn(item.getMnpYn());
				insertTrgVo.setEnggCnt0(item.getEnggCnt0());
				insertTrgVo.setEnggCnt12(item.getEnggCnt12());
				insertTrgVo.setEnggCnt18(item.getEnggCnt18());
				insertTrgVo.setEnggCnt24(item.getEnggCnt24());
				insertTrgVo.setEnggCnt30(item.getEnggCnt30());
				insertTrgVo.setEnggCnt36(item.getEnggCnt36());
			}
			orgnSetExl.add(item.getOrgnId());
			socSetExl.add(item.getRateCd());
			onOffSetExl.add(item.getOnOffType());
			addSetExl.add(item.getSoc());
		}
		//End OF FOR ---------------------------

		if(!lastInsertRegNo.equals(itemList.get(itemList.size()-1).getRegNum())){
			List<String> insertOrgnList = new ArrayList<String>(orgnSetExl);
			List<String> insertSocList = new ArrayList<String>(socSetExl);
			List<String> insertOnOffList = new ArrayList<String>(onOffSetExl);
			List<String> insertAddList = new ArrayList<String>(addSetExl);

			insertTrgVo.setOrgnListExl(insertOrgnList);
			insertTrgVo.setSocListExl(insertSocList);
			insertTrgVo.setOnOffListExl(insertOnOffList);
			insertTrgVo.setAddListExl(insertAddList);

			try {
				duplicateChrgPrmtChk(insertTrgVo, "EXCEL");
			}catch (MvnoRunException e){
				if("EXCEL".equals(e.getMessage())) {
					throw new MvnoRunException(-1,"엑셀내에 중복된 프로모션이 존재하여 업로드 할수 없습니다.");
				}
				else {
					throw new MvnoRunException(-1,"엑셀데이터 프로모션번호 ["+ insertTrgVo.getRegNum() +"] 는 "+"이미 등록된 프로모션["+ e.getMessage() +"]이 존재합니다.");
				}

			}
			// **********************Chk 테이블로 INSERT **********************
			// 엑셀데이터 마스터 Chk테이블에 추가
			try {
				lastInsertRegNo = insertTrgVo.getRegNum();
				promotionMapper.insertChrgPrmtMstChk(insertTrgVo);    	 //마스터 Chk 테이블에 Insert
				// 엑셀데이터 조직 Chk테이블에 추가
				for(int idx=0; idx<insertTrgVo.getOrgnListExl().size(); idx++){
					insertTrgVo.setOrgnId(insertTrgVo.getOrgnListExl().get(idx));
					promotionMapper.insertChrgPrmtOrgChk(insertTrgVo);	 //조직 Chk 테이블에 Insert
				}
				// 엑셀데이터 요금제 Chk테이블에 추가
				for(int idx=0; idx<insertTrgVo.getSocListExl().size(); idx++){
					insertTrgVo.setRateCd(insertTrgVo.getSocListExl().get(idx));
					promotionMapper.insertChrgPrmtSocChk(insertTrgVo);//요금제 Chk 테이블에 Insert
				}
				// 엑셀데이터 모집경로 Chk테이블에 추가
				for(int idx=0; idx<insertTrgVo.getOnOffListExl().size(); idx++){
					insertTrgVo.setOnOffType(insertTrgVo.getOnOffListExl().get(idx));
					promotionMapper.insertChrgPrmtOnOffChk(insertTrgVo);//Chk 테이블에 Insert
				}
				// 엑셀데이터 부가서비스 Chk테이블에 추가

				for(int idx=0; idx<insertTrgVo.getAddListExl().size(); idx++){
					insertTrgVo.setSoc(insertTrgVo.getAddListExl().get(idx));
					insertTrgVo.setSeq("1");
					promotionMapper.insertChrgPrmtAddChk(insertTrgVo); //Chk 테이블에 Insert
				}
			}catch(Exception e) {
				throw new MvnoRunException(-1,"엑셀업로드 중 오류가 발생했습니다.");
		    }
			insertCnt++;
		}
		// **********************실 테이블로 INSERT **********************

		promotionMapper.insertChrgPrmtMstExl();    // Chk데이터 마스터 실테이블에 추가
		promotionMapper.insertChrgPrmtOrgExl(); 	 // Chk데이터 조직 실테이블에 추가
		promotionMapper.insertChrgPrmtSocExl();	 // Chk데이터 요금제 실테이블에 추가
		promotionMapper.insertChrgPrmtOnOffExl();	 // Chk데이터 모집경로 실테이블에 추가
		promotionMapper.insertChrgPrmtAddExl();	 // Chk데이터 부가서비스 실테이블에 추가

		// **********************Chk 테이블 DELETE **********************

		promotionMapper.deleteChrgPrmtMstChk();  	 // 마스터 Chk테이블 DELETE
		promotionMapper.deleteChrgPrmtOrgChk(); 	 // 조직 Chk테이블 DELETE
		promotionMapper.deleteChrgPrmtSocChk(); 	 // 요금제 Chk테이블 DELETE
		promotionMapper.deleteChrgPrmtOnOffChk(); 	 // 모집경로 Chk테이블 DELETE
		promotionMapper.deleteChrgPrmtAddChk(); 	 // 부가서비스 Chk테이블 DELETE

		return insertCnt;
    }

    // 채널별 요금할인 엑셀 업로드 중복 검사
    private void duplicateChrgPrmtChk(ChrgPrmtMgmtVO vo, String typeCd) {
		List<ChrgPrmtMgmtVO> aryDupChkList = new ArrayList<ChrgPrmtMgmtVO>();
		int orgnTypeChk = 0;

		aryDupChkList = promotionMapper.getChrgPrmtDupByInfoExl(vo);

		if(KtisUtil.isNotEmpty(aryDupChkList)){
			// 중복된 조직, 요금제가 존재하는 지 확인
			for(int idx1 = 0; idx1 < vo.getOrgnListExl().size(); idx1++){
				for(int idx2 = 0; idx2 < vo.getSocListExl().size(); idx2++){
					for(int idx3 = 0; idx3 < vo.getOnOffListExl().size(); idx3++){
						for(int idx4 = 0; idx4 < aryDupChkList.size(); idx4++){
							// 채널유형, 대상조직 검사
							if(!"ALL".equals(vo.getOrgnType())){
								vo.setOrgnId(vo.getOrgnListExl().get(idx1));
								orgnTypeChk = promotionMapper.getOrgnTypeChkCnt(vo);
								if(orgnTypeChk == 0){
									throw new MvnoRunException(-1, "ORGN_TYPE");
								}
							}
							
							if(aryDupChkList.get(idx4).getOrgnId().equals(vo.getOrgnListExl().get(idx1))
							&& aryDupChkList.get(idx4).getRateCd().equals(vo.getSocListExl().get(idx2))
							&& aryDupChkList.get(idx4).getOnOffType().equals(vo.getOnOffListExl().get(idx3))){
								String strChkPrmtId = aryDupChkList.get(idx4).getPrmtId();
								
								if("2".equals(aryDupChkList.get(idx4).getChngTypeCd())){
									throw new MvnoRunException(-1, "EXCEL");
								} else {
									throw new MvnoRunException(-1, strChkPrmtId );
								}
							}
						}
					}
				}
			}
		}
    }

 // 채널별 요금할인 정책 목록 콤보 조회
    public List<EgovMap> getChrgPrmtListCombo(RcpDetailVO vo) {

    	if(KtisUtil.isEmpty(vo.getCntpntShopId()) || KtisUtil.isEmpty(vo.getReqBuyType()) || KtisUtil.isEmpty(vo.getSocCode()) ||
    	   KtisUtil.isEmpty(vo.getOperType()) ||  KtisUtil.isEmpty(vo.getAgrmTrm()) ||KtisUtil.isEmpty(vo.getOnOffType()) ){
    		throw new MvnoRunException(-1, "필수 데이터가 없습니다" );
	}
        return promotionMapper.getChrgPrmtListCombo(vo);
    }
    
    
    //종료일시 일괄변경 
  	public void updPrmtEndDttm(ChrgPrmtMgmtVO chrgPrmtMgmtVO) throws EgovBizException {
  		
  		List<String> prmtIdList = chrgPrmtMgmtVO.getPrmtIdList();
  		
  		if(KtisUtil.isEmpty(prmtIdList)){
  			throw new MvnoRunException(-1, "프로모션 ID가 존재하지 않습니다.");
  		}
  		if(KtisUtil.isEmpty(chrgPrmtMgmtVO.getEndDt())){
  			throw new MvnoRunException(-1, "종료일시가 존재하지 않습니다.");
  		}
  		for(String prmtId : prmtIdList) {
  			chrgPrmtMgmtVO.setPrmtId(prmtId);
  			this.updChrgPrmtByInfo(chrgPrmtMgmtVO);
  		}
  	}
  	
  	public List<ChrgPrmtMgmtVO> getChoChrgPrmtListExcelDown(ChrgPrmtMgmtVO searchVO) throws EgovBizException {
		List<ChrgPrmtMgmtVO> prmtExcelList = promotionMapper.getChoChrgPrmtListExcelDown(searchVO);
		return prmtExcelList;
	}
  	
  	//대리점 추가
  	public void setChrgPrmtOrgAdd(ChrgPrmtMgmtVO chrgPrmtMgmtVO) throws EgovBizException {
  				
  		List<String> prmtIdList = chrgPrmtMgmtVO.getPrmtIdList();
  		List<String> orgnIdList = chrgPrmtMgmtVO.getOrgnIdList();
  		
  		if(KtisUtil.isEmpty(prmtIdList) || KtisUtil.isEmpty(orgnIdList)){
  			throw new MvnoRunException(-1, "선택된 데이터가 없습니다.<br>데이터를 선택하고 처리해주시기 바랍니다.");
  		}
  		
  		for(int i=0; i<prmtIdList.size(); i++) {
  			ChrgPrmtMgmtSubVO chrgPrmtMgmtSubVO = new ChrgPrmtMgmtSubVO();
  			chrgPrmtMgmtSubVO.setSessionUserId(chrgPrmtMgmtVO.getSessionUserId());
  			chrgPrmtMgmtSubVO.setPrmtId(prmtIdList.get(i));
  			chrgPrmtMgmtVO.setPrmtId(prmtIdList.get(i));
  			
  			List<ChrgPrmtMgmtSubVO> orgnList=promotionMapper.getChrgPrmtDtlOrgnList(chrgPrmtMgmtSubVO);
  			
  			for(int idx1=0; idx1<orgnIdList.size(); idx1++) {
  				for(int idx2=0; idx2<orgnList.size(); idx2++){
  					if(orgnList.get(idx2).getOrgnId().equals(orgnIdList.get(idx1))) {
  						throw new MvnoRunException(-1, "등록하려는 [" +prmtIdList.get(i)+ "] 프로모션에 <br> "
  								+ " 이미 [" +orgnList.get(idx2).getOrgnNm()+ "] 조직이 존재하여 대리점추가를 할수 없습니다.");
  					}
  				}
  				chrgPrmtMgmtSubVO.setOrgnId(orgnIdList.get(idx1));
  				promotionMapper.insertMspChrgPrmtOrg(chrgPrmtMgmtSubVO);
  			}
  		
  			ChrgPrmtMgmtVO resultVo = promotionMapper.getChrgPrmtDtlInfo(chrgPrmtMgmtVO);
  			if("N".equals(resultVo.getUsgYn())){
  				throw new MvnoRunException (-1, "프로모션 ID [" +prmtIdList.get(i)+ "] 프로모션은 이미 삭제된 프로모션으로 대리점을 추가 할 수 없습니다.");
  			}
  			
  			String endDttm = resultVo.getEndDt();
  			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
  			Date dateNow = new Date();
  			String stringNow = formatter.format(dateNow);
  			int result = endDttm.compareTo(stringNow);
  			if(result < 0){
  				throw new MvnoRunException(-1 ,"프로모션 ID [" +prmtIdList.get(i)+ "] 프로모션은 이미 종료된 프로모션으로 대리점을 추가 할 수 없습니다.");
  			}
  			
  			//중복검사
  			List<ChrgPrmtMgmtVO> aryDupChkList = promotionMapper.getChrgPrmtDtlAllChk(resultVo);
  			if(aryDupChkList.size() > 0){
				throw new MvnoRunException(-1 ,"[" +prmtIdList.get(i)+ "] 프로모션에 대리점 추가시<br> "
											+ "["+ aryDupChkList.get(0).getPrmtId() +"]프로모션과 중복되어 추가 할 수 없습니다.");
			}
  		}
  	}
}
