package com.ktis.msp.org.termssendmgmt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.org.termssendmgmt.mapper.TermsSendMgmtMapper;
import com.ktis.msp.org.termssendmgmt.vo.TermsSendVO;

@Service
public class TermsSendMgmtService extends BaseService {
	
	@Autowired
	private TermsSendMgmtMapper termsSendMgmtMapper;
	
	@Autowired
	private MaskingService maskingService;
	
	public TermsSendMgmtService() {
		setLogPrefix("[TermsSendMgmtService]");
	}
	
	/**
	 * 약관발송대상자 리스트 조회
	 * @param termsSendVO
	 * @return
	 */
	public List<?> getTermsSendTrgt(TermsSendVO termsSendVO, Map<String, Object> paramMap) {
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("getShopList START."));
		logger.info(generateLogMsg("ShopMgmtVO == " + termsSendVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> list = new ArrayList<TermsSendVO>();
		
		list = termsSendMgmtMapper.getTermsSendTrgt(termsSendVO);
		list = getMaskingData(list, paramMap);
		
		return list;
	}
	
    /**
     * 약관발송 결과 업데이트
     * @param termsSendVO
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
	public int updateResultYN(TermsSendVO termsSendVO){
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("updateResultYN START."));
		logger.info(generateLogMsg("ShopMgmtVO == " + termsSendVO.toString()));
		logger.info(generateLogMsg("================================================================="));
    	
		int returnCnt = 0;
		
		returnCnt = termsSendMgmtMapper.updateResultYn(termsSendVO);
		
		return returnCnt;
	}
    
    /**
     * 약관발송대상자 엑셀다운로드
     * @param termsSendVO
     * @return
     */
    public List<?> termsSendListEx(TermsSendVO termsSendVO, Map<String, Object> paramMap) {
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("termsSendListEx START."));
		logger.info(generateLogMsg("ShopMgmtVO == " + termsSendVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> list = new ArrayList<TermsSendVO>();
		
		list = termsSendMgmtMapper.termsSendListEx(termsSendVO);
		list = getMaskingData(list, paramMap);
		
		return list;
    }
    
    /**
     * 약관발송대상자 추출
     * @param param
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
	public Map<String, Object> insertTermsSendTrgt(Map<String, Object> param) {
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("약관발송대상자 추출 START"));
		logger.info(generateLogMsg("================================================================="));
		
		try {
			
			int resultCnt = termsSendMgmtMapper.insertTermsSendTrgt();
			
			param.put("PROC_STAT_CD", "S");
			param.put("PROC_CNT", resultCnt);
			param.put("READ_CNT", resultCnt);
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		
		return param;
	}
	
	public List<?> getMaskingData(List<?> result, Map<String, Object> paramMap) {
		HashMap<String, String> maskFields = getMaksFields();
		maskingService.setMask(result, maskFields, paramMap);
		
		return result;
	}

	public HashMap<String, String> getMaksFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();

		maskFields.put("name", "CUST_NAME");
		maskFields.put("mobileNo", "TEL_NO");
		maskFields.put("email", "EMAIL");
		maskFields.put("rvisnNm", "CUST_NAME"); //수정자
		
		
		return maskFields;
	}
}
