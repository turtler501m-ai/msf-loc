package com.ktis.msp.rsk.grntInsrMgmt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.rsk.grntInsrMgmt.mapper.GrntInsrMgmtMapper;
import com.ktis.msp.rsk.grntInsrMgmt.vo.GrntInsrMgmtVO;

@Service
public class GrntInsrMgmtService extends BaseService {
	
	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;
	
	@Autowired
	private GrntInsrMgmtMapper grntInsrMgmtMapper;
	
	/**
	 * 보증보험청구 조회
	 * @param grntInsrMgmtVO
	 * @param paramMap
	 * @return
	 */
	public List<?> getGrntInsrList(GrntInsrMgmtVO grntInsrMgmtVO, Map<String, Object> paramMap) {
		if(grntInsrMgmtVO.getStrtYm() == null || "".equals(grntInsrMgmtVO.getStrtYm())){
			throw new MvnoRunException(-1, "연동월을 확인해주세요.");
		}
		if(grntInsrMgmtVO.getEndYm() == null || "".equals(grntInsrMgmtVO.getEndYm())){
			throw new MvnoRunException(-1, "연동월을 확인해주세요.");
		}
		HashMap<String, String> maskFields = getMaskFields();
		
		List<?> list = grntInsrMgmtMapper.getGrntInsrList(grntInsrMgmtVO);
		
		maskingService.setMask(list, maskFields, paramMap);
		
		return list;
		
	}
	
	/**
	 * 보증보험청구조회 엑셀다운로드
	 * @param grntInsrMgmtVO
	 * @param paramMap
	 * @return
	 */
	public List<?> getGrntInsrListEx(GrntInsrMgmtVO grntInsrMgmtVO, Map<String, Object> paramMap) {
		HashMap<String, String> maskFields = getMaskFields();
		
		List<?> list = grntInsrMgmtMapper.getGrntInsrListEx(grntInsrMgmtVO);
		
		maskingService.setMask(list, maskFields, paramMap);
		
		return list;
	}
	
	public HashMap<String, String> getMaskFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("cstmrNm","CUST_NAME");
		maskFields.put("subscriberNo","MOBILE_PHO");

		return maskFields;
	}
}
