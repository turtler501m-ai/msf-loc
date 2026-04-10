package com.ktis.msp.sale.ktPlcyMgmt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.sale.ktPlcyMgmt.mapper.KtPlcyMgmtMapper;
import com.ktis.msp.sale.ktPlcyMgmt.vo.KtPlcyMgmtVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service
public class KtPlcyMgmtService extends BaseService {
	
	@Autowired
	private KtPlcyMgmtMapper ktPlcyMgmtMapper;
	
	@Autowired
	private MaskingService maskingService;
	
	final static private String TRTM_TYPE_NEW = "I";
	
	final static private String TRTM_TYPE_UPD = "U";
	
	final static private String SPLT_IND_CD = "&";
	
	public List<KtPlcyMgmtVO> getKtPlcyList(KtPlcyMgmtVO vo){
		
		List<KtPlcyMgmtVO> result = ktPlcyMgmtMapper.getKtPlcyList(vo);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", vo.getSessionUserId());
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("regstId",		"CUST_NAME");
		
		maskingService.setMask(result, maskFields, paramMap);
		
		return result;
	}
	
	public List<KtPlcyMgmtVO> getKtPlcyDtlList(KtPlcyMgmtVO vo){
		
		if (vo.getSlsNo() == null || "".equals(vo.getSlsNo())) {
			throw new MvnoRunException(-1, "판매번호가 존재 하지 않습니다.");
		}
		
		List<KtPlcyMgmtVO> result = ktPlcyMgmtMapper.getKtPlcyDtlList(vo);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", vo.getSessionUserId());
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("regstId",		"CUST_NAME");
		
		maskingService.setMask(result, maskFields, paramMap);
		
		return result;
	}
	
	public List<KtPlcyMgmtVO> getMspKtPlcyDiscountByGrid(KtPlcyMgmtVO vo){
		
		if (vo.getSlsNo() == null || "".equals(vo.getSlsNo())) {
			throw new MvnoRunException(-1, "판매번호가 존재 하지 않습니다.");
		}
		
		if (vo.getPlcyId() == null || "".equals(vo.getPlcyId())) {
			throw new MvnoRunException(-1, "정책ID가 존재 하지 않습니다.");
		}
		
		return ktPlcyMgmtMapper.getMspKtPlcyDiscountByGrid(vo);
	}
	
	public List<KtPlcyMgmtVO> getMspKtPlcyOperByGrid(KtPlcyMgmtVO vo){
		
		if (vo.getSlsNo() == null || "".equals(vo.getSlsNo())) {
			throw new MvnoRunException(-1, "판매번호가 존재 하지 않습니다.");
		}
		
		if (vo.getPlcyId() == null || "".equals(vo.getPlcyId())) {
			throw new MvnoRunException(-1, "정책ID가 존재 하지 않습니다.");
		}
		
		return ktPlcyMgmtMapper.getMspKtPlcyOperByGrid(vo);
	}
	
	public List<KtPlcyMgmtVO> getMspKtPlcyInstGrid(KtPlcyMgmtVO vo){
		
		if (vo.getSlsNo() == null || "".equals(vo.getSlsNo())) {
			throw new MvnoRunException(-1, "판매번호가 존재 하지 않습니다.");
		}
		
		if (vo.getPlcyId() == null || "".equals(vo.getPlcyId())) {
			throw new MvnoRunException(-1, "정책ID가 존재 하지 않습니다.");
		}
		
		return ktPlcyMgmtMapper.getMspKtPlcyInstGrid(vo);
	}
	
	public List<KtPlcyMgmtVO> getMspKtPlcyEnggByGrid(KtPlcyMgmtVO vo){
		
		if (vo.getSlsNo() == null || "".equals(vo.getSlsNo())) {
			throw new MvnoRunException(-1, "판매번호가 존재 하지 않습니다.");
		}
		
		if (vo.getPlcyId() == null || "".equals(vo.getPlcyId())) {
			throw new MvnoRunException(-1, "정책ID가 존재 하지 않습니다.");
		}
		
		return ktPlcyMgmtMapper.getMspKtPlcyEnggByGrid(vo);
	}
	
	public int getMspKtPlcyMstBySlsNo(KtPlcyMgmtVO vo){
		
		if (vo.getSlsNo() == null || "".equals(vo.getSlsNo())) {
			throw new MvnoRunException(-1, "판매번호가 존재 하지 않습니다.");
		}
		
		return ktPlcyMgmtMapper.getMspKtPlcyMstBySlsNo(vo);
	}
	
	public int savMspKtPlcyMst(KtPlcyMgmtVO vo){
		
		int rntlCnt = 0;
		
		if (vo.getTrtmTypeCd() == null || "".equals(vo.getTrtmTypeCd())) {
			throw new MvnoRunException(-1, "처리유형코드가 존재 하지 않습니다.");
		}
		
		if (vo.getSlsNo() == null || "".equals(vo.getSlsNo())) {
			throw new MvnoRunException(-1, "판매번호가 존재 하지 않습니다.");
		}
		
		if (vo.getSlsNm() == null || "".equals(vo.getSlsNm())) {
			throw new MvnoRunException(-1, "판매정책명이 존재 하지 않습니다.");
		}
		
		if (vo.getPlcyType() == null || "".equals(vo.getPlcyType())) {
			throw new MvnoRunException(-1, "정책유형이 존재 하지 않습니다.");
		}
		
		if (vo.getPppo() == null || "".equals(vo.getPppo())) {
			throw new MvnoRunException(-1, "선후불구분이 존재 하지 않습니다.");
		}
		
		if (vo.getUseYn() == null || "".equals(vo.getUseYn())) {
			throw new MvnoRunException(-1, "사용여부가 존재 하지 않습니다.");
		}
		
		if (vo.getDcType() == null || "".equals(vo.getDcType())) {
			throw new MvnoRunException(-1, "할인유형이 존재 하지 않습니다.");
		}
		
		if (vo.getOperType() == null || "".equals(vo.getOperType())) {
			throw new MvnoRunException(-1, "개통유형이 존재 하지 않습니다.");
		}
		
		if (vo.getInstCnt() == null || "".equals(vo.getInstCnt())) {
			throw new MvnoRunException(-1, "할부개월수가 존재 하지 않습니다.");
		}
		
		if (vo.getEnggCnt() == null || "".equals(vo.getEnggCnt())) {
			throw new MvnoRunException(-1, "약정개월수가 존재 하지 않습니다.");
		}
		
		if(TRTM_TYPE_NEW.equals(vo.getTrtmTypeCd())){
			
			rntlCnt = ktPlcyMgmtMapper.regMspKtPlcyMst(vo);
			
		}else if(TRTM_TYPE_UPD.equals(vo.getTrtmTypeCd())){
			
			rntlCnt = ktPlcyMgmtMapper.updMspKtPlcyMst(vo);
			
		}else{
			
			throw new MvnoRunException(-1, "정의되지 않은 처리유형코드 입니다.");
			
		}
		
		rntlCnt = ktPlcyMgmtMapper.regMspKtPlcyHst(vo);
		
		String[] aryDcType = vo.getDcType().split(SPLT_IND_CD);
		for(int idx1 = 0; idx1 < aryDcType.length; idx1++){
			vo.setDcType(aryDcType[idx1]);
			rntlCnt = ktPlcyMgmtMapper.regMspKtPlcyDiscount(vo);
		}
		
		String[] aryOperType = vo.getOperType().split(SPLT_IND_CD);
		for(int idx2 = 0; idx2 < aryOperType.length; idx2++){
			vo.setOperType(aryOperType[idx2]);
			rntlCnt = ktPlcyMgmtMapper.regMspKtPlcyOper(vo);
		}
		
		String[] aryInstCnt = vo.getInstCnt().split(SPLT_IND_CD);
		for(int idx3 = 0; idx3 < aryInstCnt.length; idx3++){
			vo.setInstCnt(aryInstCnt[idx3]);
			rntlCnt = ktPlcyMgmtMapper.regMspKtPlcyInst(vo);
		}
		
		String[] aryEnggCnt = vo.getEnggCnt().split(SPLT_IND_CD);
		for(int idx4 = 0; idx4 < aryEnggCnt.length; idx4++){
			vo.setEnggCnt(aryEnggCnt[idx4]);
			rntlCnt = ktPlcyMgmtMapper.regMspKtPlcyEngg(vo);
		}
		
		return rntlCnt;
	}
	
	public List<EgovMap> getKtPlcyListCombo(KtPlcyMgmtVO vo){
		
		if (vo.getPlcyType() == null || "".equals(vo.getPlcyType())) {
			throw new MvnoRunException(-1, "정책유형이 존재 하지 않습니다.");
		}
		
		if (vo.getPppo() == null || "".equals(vo.getPppo())) {
			throw new MvnoRunException(-1, "선후불구분이 존재 하지 않습니다.");
		}
		
		if (vo.getOperType() == null || "".equals(vo.getOperType())) {
			throw new MvnoRunException(-1, "개통유형이 존재 하지 않습니다.");
		}
		
		if (vo.getInstCnt() == null || "".equals(vo.getInstCnt())) {
			throw new MvnoRunException(-1, "할부개월수가 존재 하지 않습니다.");
		}
		
		if (vo.getEnggCnt() == null || "".equals(vo.getEnggCnt())) {
			throw new MvnoRunException(-1, "약정개월수가 존재 하지 않습니다.");
		}
		
		return ktPlcyMgmtMapper.getKtPlcyListCombo(vo);
	}
}
