package com.ktis.msp.org.prmtmgmt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.org.prmtmgmt.mapper.TripleHistMgmtMapper;
import com.ktis.msp.org.prmtmgmt.vo.TripleHistMgmtVO;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;

@Service
public class TripleHistMgmtService extends BaseService {
	
	@Autowired
	private TripleHistMgmtMapper tripleHistMgmtMapper;
	
	@Autowired
	private MaskingService maskingService;
	
	//트리플 이력관리 리스트 조회
	public List<?> getTripleHistList(TripleHistMgmtVO tripleHistMgmtVO) throws EgovBizException {
		
		if(tripleHistMgmtVO.getSearchGbn() == null || "".equals(tripleHistMgmtVO.getSearchGbn())){
			if (tripleHistMgmtVO.getSrchStrtDt() == null || "".equals(tripleHistMgmtVO.getSrchStrtDt()) ||
					tripleHistMgmtVO.getSrchEndDt() == null || "".equals(tripleHistMgmtVO.getSrchEndDt())){
						throw new MvnoRunException(-1, "조회일자가 존재 하지 않습니다.");
				}
		}
		
		List<?> result = tripleHistMgmtMapper.getTripleHistList(tripleHistMgmtVO);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", tripleHistMgmtVO.getSessionUserId());
		
		maskingService.setMask(result, maskingService.getMaskFields(), paramMap);
		
		return result;
	}

	//트리플 이력관리 리스트 엑셀 다운로드
	public List<?> getTripleHistListExcel(TripleHistMgmtVO tripleHistMgmtVO) throws EgovBizException {
		
		if(tripleHistMgmtVO.getSearchGbn() == null || "".equals(tripleHistMgmtVO.getSearchGbn())){
			if (tripleHistMgmtVO.getSrchStrtDt() == null || "".equals(tripleHistMgmtVO.getSrchStrtDt()) ||
					tripleHistMgmtVO.getSrchEndDt() == null || "".equals(tripleHistMgmtVO.getSrchEndDt())){
				throw new MvnoRunException(-1, "조회일자가 존재 하지 않습니다.");
			}
		}
		
		List<?> result = tripleHistMgmtMapper.getTripleHistListExcel(tripleHistMgmtVO);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", tripleHistMgmtVO.getSessionUserId());
		
		maskingService.setMask(result, maskingService.getMaskFields(), paramMap);
		
		return result;
	}
	
	//트리플이력 직접등록 계약번호 조회
	public List<Map<String, Object>> getTripleHistContractNum(TripleHistMgmtVO tripleHistMgmtVO) throws EgovBizException {
		List<Map<String, Object>> result = tripleHistMgmtMapper.getTripleHistContractNum(tripleHistMgmtVO);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", tripleHistMgmtVO.getSessionUserId());
		maskingService.setMask(result, maskingService.getMaskFields(), paramMap);
		
		if(result.get(0).get("lstRateCd") == null || result.get(0).get("lstRateCd") == ""){
			throw new MvnoRunException(-1, "요금제 데이터가 확인되지 않습니다.\n관리자에게 문의하여 주십시요.");
		}
		return result;
	}
	
	//트리플이력 직접등록 요금제,부가서비스 조회
	public List<?> getTripleHistRate(TripleHistMgmtVO tripleHistMgmtVO) throws EgovBizException {
		List<?> result = tripleHistMgmtMapper.getTripleHistRate(tripleHistMgmtVO);
		return result;
	}
	
	//트리플이력 직접등록 부가서비스 조회
	public List<?> getTripleHistAddition(TripleHistMgmtVO tripleHistMgmtVO) throws EgovBizException {
		List<?> result = tripleHistMgmtMapper.getTripleHistAddition(tripleHistMgmtVO);
		return result;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void insertTripleHist(TripleHistMgmtVO tripleHistMgmtVO) throws EgovBizException {
		if(KtisUtil.isEmpty(tripleHistMgmtVO.getContractNum())){
			throw new MvnoRunException(-1, "계약번호 검색을 먼저 해주십시오.");
		}
		
		if(KtisUtil.isEmpty(tripleHistMgmtVO.getRateCd()) && KtisUtil.isEmpty(tripleHistMgmtVO.getRateNm())) {
			throw new MvnoRunException(-1, "요금제를 입력해 주십시오.");
		}
		
		if(KtisUtil.isEmpty(tripleHistMgmtVO.getAdditionCd()) && KtisUtil.isEmpty(tripleHistMgmtVO.getAdditionNm())) {
			throw new MvnoRunException(-1, "부가서비스를 입력해 주십시오.");
		}
		
		if(KtisUtil.isEmpty(tripleHistMgmtVO.getnKtInternetId())){
			throw new MvnoRunException(-1, "KT인터넷ID를 입력해 주십시오.");
		}
		
		if(KtisUtil.isEmpty(tripleHistMgmtVO.getInstallDd())){
			throw new MvnoRunException(-1, "인터넷 설치일자를 입력해 주십시오.");
		}
		
		int cnt = tripleHistMgmtMapper.getTripleHistChk(tripleHistMgmtVO);
		if(cnt == 1){
			throw new MvnoRunException(-1, "중복 신청된 이력이 있어 등록이 불가합니다.");
		}else{
			if("INSERT".equals(tripleHistMgmtVO.getViewType())){
				// 변경신청등록
				tripleHistMgmtMapper.insertTripleHist(tripleHistMgmtVO);
			}else{
				// 변경신청수정
				tripleHistMgmtMapper.updateTripleHist(tripleHistMgmtVO);
			}
		}
	}

	@Transactional(rollbackFor=Exception.class)
	public void updateTripleHistStatus(TripleHistMgmtVO tripleHistMgmtVO) throws EgovBizException {
		if(KtisUtil.isEmpty(tripleHistMgmtVO.getContractNum())){
			throw new MvnoRunException(-1, "계약번호 검색을 먼저 해주십시오.");
		}
		if(KtisUtil.isEmpty(tripleHistMgmtVO.getKtInternetId())){
			throw new MvnoRunException(-1, "KT인터넷ID를 입력해 주십시오.");
		}
		// 변경신청수정
		tripleHistMgmtMapper.updateTripleHistStatus(tripleHistMgmtVO);
	}
	
	//트리플이력 직접등록 트리플혜택관리 등록되있는 데이터 가져오기
	public List<Map<String, Object>> getTriplePrmtData(TripleHistMgmtVO tripleHistMgmtVO) throws EgovBizException {
		List<Map<String, Object>> result = tripleHistMgmtMapper.getTriplePrmtData(tripleHistMgmtVO);
		if(result.size() > 1){
			throw new MvnoRunException(-1, "부가서비스 데이터가 다건 조회 되고있습니다.\n관리자에게 문의하여 주십시요.");
		}
		return result;
	}
}
