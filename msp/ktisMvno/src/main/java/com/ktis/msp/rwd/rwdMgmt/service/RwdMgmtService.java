package com.ktis.msp.rwd.rwdMgmt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.rwd.rwdMgmt.mapper.RwdMgmtMapper;
import com.ktis.msp.rwd.rwdMgmt.vo.RwdMgmtVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service
public class RwdMgmtService extends BaseService {
	
	@Autowired
	private RwdMgmtMapper rwdMgmtMapper;
	
	@Autowired
	private MaskingService maskingService;
	
	private HashMap<String, String> getMaskFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("regstId",	"CUST_NAME");
		maskFields.put("rvisnId",	"CUST_NAME");
		return maskFields;
	}
	
	public List<RwdMgmtVO> getRwdProdList(RwdMgmtVO rwdMgmtVO){
		return rwdMgmtMapper.getRwdProdList(rwdMgmtVO);
	}
	
	public void regRwdMst(RwdMgmtVO rwdMgmtVO){
		
		if (rwdMgmtVO.getRwdProdCd() == null || "".equals(rwdMgmtVO.getRwdProdCd()) ){
			throw new MvnoRunException(-1, "보상서비스 코드가 존재 하지 않습니다.");
		}
				
		if (rwdMgmtVO.getRwdPrd() == null || "".equals(rwdMgmtVO.getRwdPrd()) ){
			throw new MvnoRunException(-1, "보상기간이 존재 하지 않습니다.");
		}
		
		int nDupCnt = rwdMgmtMapper.chkDupRwdProd(rwdMgmtVO);
		
		if(nDupCnt > 0) {
			throw new MvnoRunException(-1, "동일한 단말보험상품이 존재 합니다.");
		}
		
		rwdMgmtMapper.regRwdMst(rwdMgmtVO);
	}
	
	public void updRwdMst(RwdMgmtVO rwdMgmtVO){
		
		if (rwdMgmtVO.getRwdProdCd() == null || "".equals(rwdMgmtVO.getRwdProdCd()) ){
			throw new MvnoRunException(-1, "보상서비스 코드가 존재 하지 않습니다.");
		}
		
		if (rwdMgmtVO.getRwdPrd() == null || "".equals(rwdMgmtVO.getRwdPrd()) ){
			throw new MvnoRunException(-1, "보험기간이 존재 하지 않습니다.");
		}
		
		rwdMgmtMapper.updRwdMst(rwdMgmtVO);
	}
	
	public List<RwdMgmtVO> getRwdMstList(RwdMgmtVO rwdMgmtVO){
		
		List<RwdMgmtVO> result = rwdMgmtMapper.getRwdMstList(rwdMgmtVO);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", rwdMgmtVO.getSessionUserId());
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(result, maskFields, paramMap);
		
		return result;
	}
	
	public List<RwdMgmtVO> getPrdtListByPrdtNm(RwdMgmtVO rwdMgmtVO){
		
		if (rwdMgmtVO.getRwdProdCd() == null || "".equals(rwdMgmtVO.getRwdProdCd()) ){
			throw new MvnoRunException(-1, "보상서비스 코드가 존재 하지 않습니다.");
		}
		
		if (rwdMgmtVO.getPrdtNm() == null || "".equals(rwdMgmtVO.getPrdtNm()) ){
			throw new MvnoRunException(-1, "단말모델명이 존재 하지 않습니다.");
		}
		
		return rwdMgmtMapper.getPrdtListByPrdtNm(rwdMgmtVO);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void regRwdRel(List<RwdMgmtVO> aryList, String usrId) {
		
		for( RwdMgmtVO regVO : aryList ) {
			
			int nDupCnt = rwdMgmtMapper.chkDupRwdPrdt(regVO);
			
			if(nDupCnt > 0) {
				throw new MvnoRunException(-1, regVO.getRwdProdNm() + "[" + regVO.getRwdProdCd() + "]의 "
												+ regVO.getPrdtNm() + "[" + regVO.getPrdtCode() + "]가 중복사용되었습니다.");
			}
			
			regVO.setSessionUserId(usrId);
			
			rwdMgmtMapper.regRwdRel(regVO);
		}
	}
	
	public List<RwdMgmtVO> getRwdRelList(RwdMgmtVO rwdMgmtVO){
		List<RwdMgmtVO> result = null;
		
		if("Y".equals(rwdMgmtVO.getPagingYn())) {
			result = rwdMgmtMapper.getRwdRelListByPaging(rwdMgmtVO);
		}else {
			result = rwdMgmtMapper.getRwdRelList(rwdMgmtVO);
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", rwdMgmtVO.getSessionUserId());
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(result, maskFields, paramMap);
		
		return result;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void updRwdRel(List<RwdMgmtVO> aryList, String usrId) {
		
		for( RwdMgmtVO regVO : aryList ) {
			
			regVO.setSessionUserId(usrId);
			
			rwdMgmtMapper.updRwdRel(regVO);
		}
	}
	
	public List<EgovMap> getRwdCombo(RwdMgmtVO rwdMgmtVO){
		
		return rwdMgmtMapper.getRwdCombo(rwdMgmtVO);
	}
	public List<EgovMap> getChgRwdCombo(RwdMgmtVO rwdMgmtVO){
		
		return rwdMgmtMapper.getChgRwdCombo(rwdMgmtVO);
	}
	
}
