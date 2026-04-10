package com.ktis.msp.pps.tesmgmt.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.mvc.ExcelAwareService;
import com.ktis.msp.pps.hdofccustmgmt.service.PpsHdofcCommonService;
import com.ktis.msp.pps.hdofccustmgmt.vo.PpsKtInResVo;
import com.ktis.msp.pps.tesmgmt.mapper.PpsTesMgmtMapper;
import com.ktis.msp.pps.tesmgmt.vo.PpsRcgTesVo;

import egovframework.rte.fdl.property.EgovPropertyService;


/**
 * @param <PpsTesMgmtMapper>
 * @Class Name : PpsTesMgmtService
 * @Description : 선불
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.13 정윤덕 최초생성
 * @
 * @author : 정윤덕
 * @Create Date : 2014. 8. 13.
 */

@Service
public class PpsTesMgmtService extends ExcelAwareService {

	
	
	 @Autowired
	  protected EgovPropertyService propertyService;

	@Autowired
	private PpsTesMgmtMapper tesMgmtMapper;
	
	@Autowired
	private PpsHdofcCommonService  hdofcCommonService;
	
	/**
	 * 청소년요금제 충전/조회내역 
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getTesMgmtList(Map<String, Object> pRequestParamMap){
		List<?> resultList = new ArrayList<PpsRcgTesVo>();
		
		resultList = tesMgmtMapper.getTesMgmtList(pRequestParamMap);
		
		return resultList;
	}
	
	/**
	 *  청소년요금제 충전/조회
	 * @param pRequestParamMap
	 * @return
	 * @throws InterruptedException 
	 * @
	 */
	//@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> getPpsRcgTesProc(Map<String, Object> pRequestParamMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		tesMgmtMapper.getPpsRcgTesProc(pRequestParamMap);
		
		if(pRequestParamMap.get("oRetCd").equals("0000")){
			PpsKtInResVo ppsKtInResVo = new PpsKtInResVo();
			ppsKtInResVo = hdofcCommonService.ppsHdofcSleep(pRequestParamMap);
			
			if(ppsKtInResVo.getoResCode().equals("0000")){
				resultMap.put("oRetCd", ppsKtInResVo.getoResCode());
				resultMap.put("oRetMsg", ppsKtInResVo.getoResCodeNm());
				resultMap.put("oAmount", ppsKtInResVo.getoAmount());
				resultMap.put("oTesChargeMax", ppsKtInResVo.getoTesChargeMax());
				resultMap.put("oTesBaser", ppsKtInResVo.getoTesBaser());
				resultMap.put("oTesChgr", ppsKtInResVo.getoTesChgr());
				resultMap.put("oTesMasicr", ppsKtInResVo.getoTesMasicr());
				resultMap.put("oTesFsmsr", ppsKtInResVo.getoTesFsmsr());
				resultMap.put("oTesVideor", ppsKtInResVo.getoTesVideor());
				resultMap.put("oTesIpvasr", ppsKtInResVo.getoTesIpvasr());
				resultMap.put("oTesIpmaxr", ppsKtInResVo.getoTesIpmaxr());
				resultMap.put("oTesSmsm", ppsKtInResVo.getoTesSmsm());
				resultMap.put("oTesDataplusv", ppsKtInResVo.getoTesDataplusv());
				
			}else{
				resultMap.put("oRetCd", ppsKtInResVo.getoResCode());
				resultMap.put("oRetMsg", ppsKtInResVo.getoResCodeNm());
			}
			
			
		}else{
			resultMap.put("oRetCd", pRequestParamMap.get("oRetCd"));
			resultMap.put("oRetMsg", pRequestParamMap.get("oRetMsg"));
		}
		
		
		return resultMap;
	}
	
	

	public String getCurrentTimes()
	{
		String currDateTime = "";
		
		Date currtentDate = new Date(System.currentTimeMillis());
		SimpleDateFormat sdfFileName = new SimpleDateFormat("yyyyMMddHHmmss",Locale.getDefault());
		currDateTime = "_"+sdfFileName.format(currtentDate);
		
		
		return currDateTime;
	}

}
