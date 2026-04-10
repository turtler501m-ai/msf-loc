package com.ktis.msp.insr.insrMgmt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.insr.insrMgmt.mapper.InsrMgmtMapper;
import com.ktis.msp.insr.insrMgmt.vo.InsrMgmtVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service
public class InsrMgmtService extends BaseService {
	
	@Autowired
	private InsrMgmtMapper insrMgmtMapper;
	
	@Autowired
	private MaskingService maskingService;
	
	private HashMap<String, String> getMaskFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("regstId",	"CUST_NAME");
		maskFields.put("rvisnId",	"CUST_NAME");
		return maskFields;
	}
	
	public List<InsrMgmtVO> getIntmInsrProdList(InsrMgmtVO insrMgmtVO){
		return insrMgmtMapper.getIntmInsrProdList(insrMgmtVO);
	}
	
	public void regIntmInsrMst(InsrMgmtVO insrMgmtVO){
		
		if (insrMgmtVO.getInsrProdCd() == null || "".equals(insrMgmtVO.getInsrProdCd()) ){
			throw new MvnoRunException(-1, "보험상품코드가 존재 하지 않습니다.");
		}
		
		if (insrMgmtVO.getCmpnLmtAmt() == null || "".equals(insrMgmtVO.getCmpnLmtAmt()) ){
			throw new MvnoRunException(-1, "보상한도 금액이 존재 하지 않습니다.");
		}
		
		if (insrMgmtVO.getInsrEnggCnt() == null || "".equals(insrMgmtVO.getInsrEnggCnt()) ){
			throw new MvnoRunException(-1, "보험기간이 존재 하지 않습니다.");
		}
		
		if (insrMgmtVO.getInsrTypeCd() == null || "".equals(insrMgmtVO.getInsrTypeCd()) ){
			throw new MvnoRunException(-1, "보험유형코드가 존재 하지 않습니다.");
		}
		
		int nDupCnt = insrMgmtMapper.chkDupIntmInsrProd(insrMgmtVO);
		
		if(nDupCnt > 0) {
			throw new MvnoRunException(-1, "동일한 단말보험상품이 존재 합니다.");
		}
		
		insrMgmtMapper.regIntmInsrMst(insrMgmtVO);
	}
	
	public void updIntmInsrMst(InsrMgmtVO insrMgmtVO){
		
		if (insrMgmtVO.getInsrProdCd() == null || "".equals(insrMgmtVO.getInsrProdCd()) ){
			throw new MvnoRunException(-1, "보험상품코드가 존재 하지 않습니다.");
		}
		
		if (insrMgmtVO.getCmpnLmtAmt() == null || "".equals(insrMgmtVO.getCmpnLmtAmt()) ){
			throw new MvnoRunException(-1, "보상한도 금액이 존재 하지 않습니다.");
		}
		
		if (insrMgmtVO.getInsrEnggCnt() == null || "".equals(insrMgmtVO.getInsrEnggCnt()) ){
			throw new MvnoRunException(-1, "보험기간이 존재 하지 않습니다.");
		}
		
		if (insrMgmtVO.getInsrTypeCd() == null || "".equals(insrMgmtVO.getInsrTypeCd()) ){
			throw new MvnoRunException(-1, "보험유형코드가 존재 하지 않습니다.");
		}
		
		insrMgmtMapper.updIntmInsrMst(insrMgmtVO);
	}
	
	public List<InsrMgmtVO> getIntmInsrMstList(InsrMgmtVO insrMgmtVO){
		
		List<InsrMgmtVO> result = insrMgmtMapper.getIntmInsrMstList(insrMgmtVO);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", insrMgmtVO.getSessionUserId());
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(result, maskFields, paramMap);
		
		return result;
	}
	
	public List<InsrMgmtVO> getPrdtListByPrdtNm(InsrMgmtVO insrMgmtVO){
		
		if (insrMgmtVO.getInsrProdCd() == null || "".equals(insrMgmtVO.getInsrProdCd()) ){
			throw new MvnoRunException(-1, "보험상품코드가 존재 하지 않습니다.");
		}
		
		if (insrMgmtVO.getPrdtNm() == null || "".equals(insrMgmtVO.getPrdtNm()) ){
			throw new MvnoRunException(-1, "단말모델명이 존재 하지 않습니다.");
		}
		
		return insrMgmtMapper.getPrdtListByPrdtNm(insrMgmtVO);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void regIntmInsrRel(List<InsrMgmtVO> aryList, String usrId) {
		
		for( InsrMgmtVO regVO : aryList ) {
			
			int nDupCnt = insrMgmtMapper.chkDupIntmInsrPrdt(regVO);
			
			if(nDupCnt > 0) {
				throw new MvnoRunException(-1, regVO.getInsrProdNm() + "[" + regVO.getInsrProdCd() + "]의 "
												+ regVO.getPrdtNm() + "[" + regVO.getPrdtCode() + "]가 중복사용되었습니다.");
			}
			
			regVO.setSessionUserId(usrId);
			
			insrMgmtMapper.regIntmInsrRel(regVO);
		}
	}
	
	public List<InsrMgmtVO> getIntmInsrRelList(InsrMgmtVO insrMgmtVO){
		List<InsrMgmtVO> result = null;
		
		if("Y".equals(insrMgmtVO.getPagingYn())) {
			result = insrMgmtMapper.getIntmInsrRelListByPaging(insrMgmtVO);
		}else {
			result = insrMgmtMapper.getIntmInsrRelList(insrMgmtVO);
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", insrMgmtVO.getSessionUserId());
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(result, maskFields, paramMap);
		
		return result;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void updIntmInsrRel(List<InsrMgmtVO> aryList, String usrId) {
		
		for( InsrMgmtVO regVO : aryList ) {
			
			regVO.setSessionUserId(usrId);
			
			insrMgmtMapper.updIntmInsrRel(regVO);
		}
	}
	
	public List<EgovMap> getInsrCombo(InsrMgmtVO insrMgmtVO){
		
		return insrMgmtMapper.getInsrCombo(insrMgmtVO);
	}
	public List<EgovMap> getChgInsrCombo(InsrMgmtVO insrMgmtVO){
		
		return insrMgmtMapper.getChgInsrCombo(insrMgmtVO);
	}
	
}
