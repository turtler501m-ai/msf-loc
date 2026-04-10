package com.ktis.msp.sale.subsdMgmt.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.sale.subsdMgmt.mapper.SubsdMgmtMapper;
import com.ktis.msp.sale.subsdMgmt.vo.SubsdMgmtByExcelVO;
import com.ktis.msp.sale.subsdMgmt.vo.SubsdMgmtVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service
public class SubsdMgmtService extends BaseService {
	
	@Autowired
	private SubsdMgmtMapper subsdMgmtMapper;
	
	@Autowired
	private MaskingService maskingService;

	/**
	 * 
	 * @param searchVO
	 * @return
	 */
	public List<?> getSubsdMgmtMainList(SubsdMgmtVO searchVO){
		logger.debug("searchVO123 = " + searchVO);
		
		if(searchVO.getStdrDt() == null || searchVO.getStdrDt().equals("")){
			throw new MvnoRunException(-1, "기준일자가 존재하지 않습니다");
		}
		
		return subsdMgmtMapper.getSubsdMgmtMainList(searchVO);
	}
	
	public List<?> getSubsdMgmtMainListExcel(SubsdMgmtVO searchVO){
		logger.debug("searchVO = " + searchVO);
		
		if(searchVO.getStdrDt() == null || searchVO.getStdrDt().equals("")){
			throw new MvnoRunException(-1, "기준일자가 존재하지 않습니다");
		}
		
		return subsdMgmtMapper.getSubsdMgmtMainListExcel(searchVO);
	}
	
	/**
	 * 
	 * @param searchVO
	 * @return
	 */
	public List<?> getSubsdMgmtDtlList(SubsdMgmtVO searchVO){
		logger.debug("searchVO = " + searchVO);
		
		if(searchVO.getStdrDt() == null || searchVO.getStdrDt().equals("")){
			throw new MvnoRunException(-1, "기준일자가 존재하지 않습니다");
		}
		
		List<EgovMap> result = (List<EgovMap>) subsdMgmtMapper.getSubsdMgmtDtlList(searchVO);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", searchVO.getSessionUserId());
		
		maskingService.setMask(result, maskingService.getMaskFields(), paramMap);
		
		return result;
	}
	
	public List<?> getSubsdMgmtDtlListExcel(SubsdMgmtVO searchVO){
		logger.debug("searchVO = " + searchVO);
		
		if(searchVO.getStdrDt() == null || searchVO.getStdrDt().equals("")){
			throw new MvnoRunException(-1, "기준일자가 존재하지 않습니다");
		}
		
		return subsdMgmtMapper.getSubsdMgmtDtlListExcel(searchVO);
	}
	
	/**
	 * 공시지원금등록
	 * @param searchVO
	 */
	@Transactional(rollbackFor=Exception.class)
	public void insertSubsdAmtMst(SubsdMgmtVO searchVO){
		if(searchVO.getApplStrtDt() == null || searchVO.getApplStrtDt().equals("")){
			throw new MvnoRunException(-1, "적용시작일자를 선택하세요");
		}
		
		if(searchVO.getPrdtId() == null || searchVO.getPrdtId().equals("")){
			throw new MvnoRunException(-1, "단말모델을 선택하세요");
		}
		
		if(searchVO.getOldYn() == null || searchVO.getOldYn().equals("")){
			throw new MvnoRunException(-1, "중고여부를 선택하세요");
		}
		
		if(searchVO.getRateCd() == null || searchVO.getRateCd().equals("")){
			throw new MvnoRunException(-1, "요금제를 선택하세요");
		}
		
		if(searchVO.getOperType() == null || searchVO.getOperType().equals("")){
			throw new MvnoRunException(-1, "개통유형을 선택하세요");
		}
		
		if(searchVO.getAgrmTrm() == null || searchVO.getAgrmTrm().equals("")){
			throw new MvnoRunException(-1, "약정기간을 선택하세요");
		}
		
		if(searchVO.getSubsdAmt() < 0){
			throw new MvnoRunException(-1, "공시지원금은 0보다 작을 수 없습니다");
		}
		
		//기존 공시지원금 확인 및 종료일자 UPDATE
		EgovMap aMap = subsdMgmtMapper.getSubsbEndDt(searchVO);
		if(aMap != null) {
			subsdMgmtMapper.updateOfclSubsdEndDt(aMap);
		}
		
		subsdMgmtMapper.insertOfclSubsdMst(searchVO);
	}
	
	public List<?> getRateGrpRateCdList(SubsdMgmtVO searchVO){
		return subsdMgmtMapper.getRateGrpRateCdList(searchVO);
	}
	
	/**
	 * 단말 데이터 타입 조회
	 * @return
	 */
	public String getPrdtIndCd(String prdtId){
		return subsdMgmtMapper.getPrdtIndCd(prdtId);
	}
	
	/**
	 * 단말모델조회
	 * @return
	 */
	public List<?> getPrdtComboList(){
		return subsdMgmtMapper.getPrdtComboList();
	}
	
	/**
	 * 공통코드 그리드 조회
	 * @param param
	 * @return
	 */
	public List<?> getCommonGridList(Map<String, Object> param){
		return subsdMgmtMapper.getCommonGridList(param);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void updateSubsdAmtInfo(SubsdMgmtVO searchVO){
		if(searchVO.getApplStrtDt() == null || searchVO.getApplStrtDt().equals("")){
			throw new MvnoRunException(-1, "적용시작일자를 선택하세요");
		}
		
		if(searchVO.getApplEndDt() == null || searchVO.getApplEndDt().equals("")){
			throw new MvnoRunException(-1, "적용종료일자를 선택하세요");
		}
		
		if(searchVO.getPrdtId() == null || searchVO.getPrdtId().equals("")){
			throw new MvnoRunException(-1, "단말모델을 선택하세요");
		}
		
		if(searchVO.getOldYn() == null || searchVO.getOldYn().equals("")){
			throw new MvnoRunException(-1, "중고여부를 선택하세요");
		}
		
		if(searchVO.getRateCd() == null || searchVO.getRateCd().equals("")){
			throw new MvnoRunException(-1, "요금제를 선택하세요");
		}
		
//		if(searchVO.getOperType() == null || searchVO.getOperType().equals("")){
//			throw new MvnoRunException(-1, "개통유형을 선택하세요");
//		}
//		
//		if(searchVO.getAgrmTrm() == null || searchVO.getAgrmTrm().equals("")){
//			throw new MvnoRunException(-1, "약정기간을 선택하세요");
//		}
		
		if(searchVO.getSubsdAmt() < 0){
			throw new MvnoRunException(-1, "공시지원금은 0보다 작을 수 없습니다");
		}
		
		subsdMgmtMapper.updateSubsdAmtInfo(searchVO);
	}
	
	public ArrayList<String> getDataTokenList(String strTrgtData, String strDelim, String strExcl){
		
		ArrayList<String> aryRslt = new ArrayList<String>();
		
		StringTokenizer strTkzLst = new StringTokenizer(strTrgtData, strDelim);
		
		while(strTkzLst.hasMoreTokens()) {
			String strTkz = strTkzLst.nextToken();
			
			if(!strExcl.equals(strTkz)) {
				aryRslt.add(strTkz);
			}
		}
		
		return aryRslt;
	}
	
	/**
	 * 공시지원금 복사 대상 조회
	 * @param searchVO
	 * @return
	 */
	public List<?> getCopySubsdAmtInfo(SubsdMgmtVO searchVO){
		logger.debug("searchVO = " + searchVO);
		
		if(searchVO.getApplStrtDt() == null || searchVO.getApplStrtDt().equals("")){
			throw new MvnoRunException(-1, "시작일자가 존재하지 않습니다");
		}
		
		if(searchVO.getRateCd() == null || searchVO.getRateCd().equals("")){
			throw new MvnoRunException(-1, "요금제 코드가 존재하지 않습니다");
		}
		
		return subsdMgmtMapper.getCopySubsdAmtInfo(searchVO);
	}
	
	/**
	 * 공통코드 조회 By Etc5
	 * @return
	 */
	public List<?> getCmnGrpCdListByEtc5(Map<String, Object> param){
		return subsdMgmtMapper.getCmnGrpCdListByEtc5(param);
	}
		
	@Async
	@Transactional(rollbackFor=Exception.class)
	public void regTmpOfclSubsdData(List<Object> aryObj, SubsdMgmtByExcelVO subsdMgmtByExcelVO, String[] arrCell){
		
		subsdMgmtByExcelVO.setTrtmDt(getTodate());
		
		if("1".equals(subsdMgmtByExcelVO.getOperAll())) {
			subsdMgmtByExcelVO.setNacYn("1");
			subsdMgmtByExcelVO.setMnpYn("1");
			subsdMgmtByExcelVO.setHcnYn("1");
		}
		
		if("1".equals(subsdMgmtByExcelVO.getAgrmAll())) {
			subsdMgmtByExcelVO.setAgrm12Yn("1");
			subsdMgmtByExcelVO.setAgrm18Yn("1");
			subsdMgmtByExcelVO.setAgrm24Yn("1");
			subsdMgmtByExcelVO.setAgrm30Yn("1");
			subsdMgmtByExcelVO.setAgrm36Yn("1");
		}
		
		subsdMgmtByExcelVO.setHdnYn(subsdMgmtByExcelVO.getHcnYn());
		
		int nCount = 0;
		int nColCnt = 0;
		int nChkCnt = 0;
		
		String getMethodNm = "";
		String setMethodNm = "";
		
		try {
			
			for( Object refObj : aryObj ) {
				
				nChkCnt = 0;
				
				for(String cell : arrCell) {
					
					getMethodNm = "get" + cell.substring(0,1).toUpperCase() + cell.substring(1);
					setMethodNm = "set" + cell.substring(0,1).toUpperCase() + cell.substring(1);
					
					
					Method getMethod = refObj.getClass().getMethod(getMethodNm);
					
					Object getValue = getMethod.invoke(refObj, new Object[0]);
					
					if(getValue != null && !"".equals(getValue.toString())) {
						if(nCount == 0) {
							nColCnt++;
						} else {
							nChkCnt++;
						}
					} else {
						if(nCount != 0 && nChkCnt < nColCnt) {
							if( nChkCnt == 0) {
								throw new MvnoRunException(1, "단말모델ID[행:" + (nCount+1) + "]가 존재 하지 않습니다.");
							} else {
								nChkCnt++;
								
								getValue = "0";
							}
						}
					}
					
					Method setMethod = subsdMgmtByExcelVO.getClass().getMethod(setMethodNm, new Class[]{String.class});
					
					setMethod.invoke(subsdMgmtByExcelVO, getValue);
					
					subsdMgmtByExcelVO.setSeqNum(String.valueOf(nCount));
					
				}
				
				nCount = nCount + subsdMgmtMapper.regMspOfclSubsdDataUpload(subsdMgmtByExcelVO);
			}
			
			if(nCount > 0) {
				subsdMgmtMapper.callOfclSubsdMst(subsdMgmtByExcelVO);
			}
			
		} catch (NoSuchMethodException e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "[ERR001]시스템 오류 관리자에게 문의 하세요.");
		} catch (SecurityException e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "[ERR002]시스템 오류 관리자에게 문의 하세요.");
		} catch (IllegalAccessException e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "[ERR003]시스템 오류 관리자에게 문의 하세요.");
		} catch (IllegalArgumentException e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "[ERR004]시스템 오류 관리자에게 문의 하세요.");
		} catch (InvocationTargetException e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "[ERR005]시스템 오류 관리자에게 문의 하세요.");
		} catch (Exception e) {
			//e.printStackTrace();
			
			String errMsg = "[ERR999]시스템 오류 관리자에게 문의 하세요.";
			
			if (e instanceof DataAccessException) {
				throw new MvnoRunException(-1, "[ERR006]시스템 오류 관리자에게 문의 하세요.");
			} else if ( e instanceof MvnoRunException) {
				errMsg = e.getMessage();
			}
			
			throw new MvnoRunException(-1, errMsg);
		}
		
	}
	
	private String getTodate() {
		SimpleDateFormat  formatter  = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
		
		return formatter.format(new Date());
	}
	
}
