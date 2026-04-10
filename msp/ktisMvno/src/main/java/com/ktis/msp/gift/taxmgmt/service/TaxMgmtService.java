package com.ktis.msp.gift.taxmgmt.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.gift.taxmgmt.mapper.TaxMgmtMapper;
import com.ktis.msp.gift.taxmgmt.vo.TaxMgmtVO;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service
public class TaxMgmtService extends BaseService {

	@Autowired
	private TaxMgmtMapper taxMgmtMapper;
	
	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;
	
	@Autowired
    protected EgovPropertyService propertyService;

	/**
     * @Description : 리스트 조회
     * @Author : 이승국
     * @Create Date : 2024. 01. 10.
     */
	public List<?> getTaxSmsSendList(TaxMgmtVO taxMgmtVo, Map<String, Object> paramMap){

		List<?> list = taxMgmtMapper.getTaxSmsSendList(taxMgmtVo);
		for( EgovMap map : (List<EgovMap>)list  ){
			if(map.get("taxNo")!=null && !map.get("taxNo").equals("")){
				String taxNo =  map.get("taxNo").toString();
				StringBuffer sb = new StringBuffer();
				String tmp = "^javaScript:goTaxDetail("+taxNo+","+paramMap.get("pageSize")+","+paramMap.get("pageIndex")+");^_self";
				sb.append(taxNo).append(tmp);
				map.put("taxNoStr",sb.toString());
			}
		}
        
		paramMap.put("SESSION_USER_ID", taxMgmtVo.getSessionUserId());
		
		maskingService.setMask(list, maskingService.getMaskFields(), paramMap);
		
		return list;
	}

	/**
     * @Description : 상세리스트 조회
     * @Author : 이승국
     * @Create Date : 2024. 01. 10.
     */
	public List<?> getTaxSmsSendListDt(TaxMgmtVO taxMgmtVo , Map<String, Object> paramMap){
	
	List<?> list = taxMgmtMapper.getTaxSmsSendListDt(taxMgmtVo);
	
	paramMap.put("SESSION_USER_ID", taxMgmtVo.getSessionUserId());
	
	maskingService.setMask(list, maskingService.getMaskFields(), paramMap);

	return list;
	}

	/**
	 * @Description : 상세리스트 엑셀 다운로드
	 * @Author : 이승국
	 * @Create Date : 2024. 01. 15.
	 */
	public List<?> getTaxMgmtListEx(TaxMgmtVO taxMgmtVo , Map<String, Object> paramMap){
		
		List<?> list = taxMgmtMapper.getTaxSmsSendListByExcel(taxMgmtVo);
		
		paramMap.put("SESSION_USER_ID", taxMgmtVo.getSessionUserId());
		
		maskingService.setMask(list, maskingService.getMaskFields(), paramMap);
		
		return list;
	}
	
	
	/**
     * @Description : 제세공과금 등록
     * @Author : 이승국
     * @Create Date : 2024. 01. 01.
     */
	@Transactional(rollbackFor=Exception.class)
	public void insertTaxSmsTmp(List<Object> aryObj, TaxMgmtVO taxMgmtVo) {
		/* TAX번호 , 계약번호 중복되어있는지 체크 후 상세부터 등록 */
		if(aryObj.size() > 0){
			Set<String> contractNums = new HashSet<String>();
			for(int i=0; i<aryObj.size(); i++) {
				String contractNum = ((TaxMgmtVO) aryObj.get(i)).getContractNum();
				taxMgmtVo.setContractNum(contractNum);
				if (taxMgmtMapper.getContractNumChk(taxMgmtVo) == 0) {
					throw new MvnoRunException(-1, "등록되지 않은 계약번호가 존재 합니다.["+contractNum+"]");
				}
				
				if (contractNums.contains(contractNum)) {
					throw new MvnoRunException(-1, "이미 등록된 계약번호가 존재 합니다.["+contractNum+"]");
				}
				// 등록
				contractNums.add(contractNum);
				taxMgmtMapper.insertTaxSmsSendTmp(taxMgmtVo);
			}
		}else{
			throw new MvnoRunException(-1, "계약번호가 존재하지 않습니다.");
		}
		
		/* 상세테이블에 먼저 인서트하고나서 메인테이블에 인서트 */
		Map<String,Object> msgMap = taxMgmtMapper.getTaxSendTaxType(taxMgmtVo);
		Map<String,Object> msgAgentMap = taxMgmtMapper.getAgentSendTaxType(taxMgmtVo);
		String kkoYn = taxMgmtMapper.getTaxSendKakaoYn(taxMgmtVo);
		String agentKkoYn = taxMgmtMapper.getAgentSendKakaoYn(taxMgmtVo);
		String text = "";
		String agentText = "";
		String taxDateStr = "";
		String requestTime = taxMgmtVo.getRequestTime(); // "YYYYMMDDHH24MISS"
		try {
			// 1. 문자열 → Date 변환
			SimpleDateFormat sdfInput = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = sdfInput.parse(requestTime);
			// 2. Calendar로 +4일
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, 4);  // +4일
			// 3. yyyy-MM-dd 형식으로 포맷
			SimpleDateFormat sdfOutput = new SimpleDateFormat("yyyy-MM-dd");
			taxDateStr = sdfOutput.format(cal.getTime());
		} catch (Exception e) {
			throw new MvnoRunException(-1, "날짜 변환 중 오류가 발생했습니다.");
		}
		if("Y".equals(taxMgmtVo.getAgentYn())){
			if(msgAgentMap == null){
				throw new MvnoRunException(-1, "사용가능한 템플릿ID를 입력해주세요.");
			}else if("Y".equals(taxMgmtVo.getKakaoYn()) && !agentKkoYn.equals("Y")){
				throw new MvnoRunException(-1, "카카오 템플릿으로 등록이 되어있지 않습니다.");
			}else{
				/*법정대리인 TEXT*/
				agentText = msgAgentMap.get("AGENT_TEXT").toString();
				agentText = agentText.replaceAll(Pattern.quote("#{taxNo}"), taxMgmtVo.getTaxNo());
				agentText = agentText.replaceAll(Pattern.quote("#{taxDate}"), taxDateStr);
				taxMgmtVo.setAgentText(agentText);
			}
		}
		
		if(msgMap == null) {
			throw new MvnoRunException(-1, "사용가능한 템플릿ID를 입력해주세요.");
		}else if("Y".equals(taxMgmtVo.getKakaoYn()) && !kkoYn.equals("Y")){
			throw new MvnoRunException(-1, "카카오 템플릿으로 등록이 되어있지 않습니다.");
		}else{
			text = msgMap.get("TEXT").toString();
			text = text.replaceAll(Pattern.quote("#{taxNo}"), taxMgmtVo.getTaxNo());
			text = text.replaceAll(Pattern.quote("#{taxDate}"), taxDateStr);
			taxMgmtVo.setText(text);
			/* MSP_TAX_SMS_LIST에 인서트 */
			taxMgmtMapper.insertTaxSmsTmp(taxMgmtVo);	
			/* MSP_TAX_SMS_SEND_LIST의 MSG_NO를 가져와서 MSP_TAX_SMS_LIST SEND_CNT(전송요청건수) 업데이트 */
			taxMgmtMapper.getTaxSendListCount(taxMgmtVo);
			/* 템플릿테이블에서 템플릿ID를 조회해서 MSP_TAX_SMS_SEND_LIST의 SEND_CTN(발신번호),
			   TITLE(제목) , TEXT(내용) 업데이트 */
			taxMgmtMapper.getTaxTmpCallBack(taxMgmtVo);
			
		}
	}
	
	/**
     * @Description : 리스트삭제
     * @Author : 이승국
     * @Create Date : 2024. 01. 10.
     */
	@Transactional(rollbackFor=Exception.class)
    public void deleteTaxSmsList(TaxMgmtVO texMgmtVo){
		
		if(texMgmtVo.getTaxNo() != null && !"".equals(texMgmtVo.getTaxNo())) {
			if(taxMgmtMapper.isTaxSmsDeleteAuth(texMgmtVo).equals("Y")) {
				// MSG_SMS_SEND_LIST 상세 데이터 삭제
				taxMgmtMapper.deleteTaxSmsListDt(texMgmtVo);
				// MSG_SMS_LIST 데이터 사용여부 'N' 전송요청건수 0으로 UPDATE
				taxMgmtMapper.deleteTaxSmsList(texMgmtVo);
			} else {
				throw new MvnoRunException(-1, "해당 데이터 삭제 권한이 없습니다.");
			}
		} else {
			throw new MvnoRunException(-1, "정상적인 호출이 아닙니다.");
		}
	}
	
	public String taxSmsListTaxNo(TaxMgmtVO texMgmtVo , Map<String, Object> paramMap) {
		
		String msgSmsListTaxNo = "";
		msgSmsListTaxNo = taxMgmtMapper.taxSmsListTaxNo(texMgmtVo);
		
		return msgSmsListTaxNo;
	}
	
	public int getChkTaxCnt(TaxMgmtVO taxMgmtVO){
		int chkCnt = 0;
		chkCnt = taxMgmtMapper.getChkTaxCnt(taxMgmtVO);
		return chkCnt;
	}
}

