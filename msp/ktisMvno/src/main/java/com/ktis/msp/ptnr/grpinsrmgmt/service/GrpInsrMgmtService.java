package com.ktis.msp.ptnr.grpinsrmgmt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.ptnr.grpinsrmgmt.mapper.GrpInsrMgmtMapper;
import com.ktis.msp.ptnr.grpinsrmgmt.vo.GrpInsrReqVO;
import com.ktis.msp.ptnr.grpinsrmgmt.vo.GrpInsrResVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service
public class GrpInsrMgmtService extends BaseService {

	@Autowired
	private GrpInsrMgmtMapper grpInsrMapper;
	
	@Autowired
	private MaskingService maskingService;
	
	/**
	 * 단체보험관리 목록조회
	 */
	public List<?> getGrpInsrMgmtList(GrpInsrReqVO vo){
		if(vo.getStdrDt() == null || "".equals(vo.getStdrDt())) {
			throw new MvnoRunException(-1, "기준일자가 존재하지 않습니다.");
		}
		
		List<EgovMap> result = (List<EgovMap>) grpInsrMapper.getGrpInsrMgmtList(vo);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", vo.getSessionUserId());
		
		maskingService.setMask(result, maskingService.getMaskFields(), paramMap);
		
		return result;
	}
	
	
	/**
	 * 보험코드조회
	 */
	public List<?> getGrpInsrCdList(GrpInsrReqVO vo){
		return grpInsrMapper.getGrpInsrCdList(vo);
	}
	
	/**
	 * 기간중복체크
	 */
	public int getGrpInsrCheck(GrpInsrResVO vo) {
		int i = 0;
		
		return i;
	}
	
	/**
	 * 단체보험관리 등록
	 */
	public void insertGrpInsrMst(GrpInsrResVO vo) {
		if(vo.getGrpInsrNm() == null || "".equals(vo.getGrpInsrNm())) {
			throw new MvnoRunException(-1, "단체보험명이 존재하지 않습니다.");
		}
		
		if(vo.getStrtDt() == null || "".equals(vo.getStrtDt())) {
			throw new MvnoRunException(-1, "시작일자가 존재하지 않습니다.");
		}
		
		if(vo.getEndDt() == null || "".equals(vo.getEndDt())) {
			throw new MvnoRunException(-1, "종료일자가 존재하지 않습니다.");
		}
		
		if(vo.getInsrCds() == null || "".equals(vo.getInsrCds())) {
			throw new MvnoRunException(-1, "보험코드가 존재하지 않습니다.");
		}
		
		if(vo.getInsrNms() == null || "".equals(vo.getInsrNms())) {
			throw new MvnoRunException(-1, "보험명이 존재하지 않습니다.");
		}
		
		grpInsrMapper.insertGrpInsrMst(vo);
	}
	
	/**
	 * 단체보험관리 수정
	 */
	public void updateGrpInsrMst(GrpInsrResVO vo) {
		if(vo.getSeq() == null || "".equals(vo.getSeq())) {
			throw new MvnoRunException(-1, "단체보험번호가 존재하지 않습니다.");
		}
		
		grpInsrMapper.updateGrpInsrMst(vo);
	}
	
	/**
	 * 단체보험 이벤트 여부
	 */
	public String getGrpInsrYn(GrpInsrReqVO vo){
		int i = grpInsrMapper.getGrpInsrYn(vo);
		
		if(i > 0) return "Y";
		else return "N";
	}
	
	/**
	 * 신청등록 단체보험 콤보조회
	 */
	public List<?> getGrpInsrCombo(GrpInsrReqVO vo){
		return grpInsrMapper.getGrpInsrCombo(vo);
	}
}
