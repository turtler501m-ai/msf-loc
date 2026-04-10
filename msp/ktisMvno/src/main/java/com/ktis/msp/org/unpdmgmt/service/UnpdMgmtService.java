package com.ktis.msp.org.unpdmgmt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.org.unpdmgmt.mapper.UnpdMgmtMapper;
import com.ktis.msp.org.unpdmgmt.vo.UnpdMgmtVo;

/**
 * @Class Name : UnpdMgmtService
 * @Description : 청구 Service
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2015.07.21 김연우 최초생성
 * @
 * @author : 김연우
 * @Create Date : 2015. 7. 21.
 */
@Service
public class UnpdMgmtService extends BaseService {

	@Autowired
	private UnpdMgmtMapper unpdMgmtMapper;

    /** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;
	
	/**
	 * @Description : 미납요금 리스트를 조회 한다.
	 * @Param  : UnpdMgmtVo
	 * @Return : List<?>
	 * @Author : 김연우
	 * @Create Date : 2015. 7. 21.
	 */
	public List<?> getUnpdMgmtList(UnpdMgmtVo searchVO, Map<String, Object> pRequestParamMap){
		// validation check
		if(searchVO.getBillYm() == null || searchVO.getBillYm().equals("")){
			throw new MvnoRunException(-1, "청구월이 존재하지 않습니다");
		}
		
		List<?> unpdMgmtList  = unpdMgmtMapper.getUnpdMgmtList(searchVO);
		
		HashMap<String, String> maskFields = new HashMap<String, String>();

        maskFields.put("svcTelNum"	,"TEL_NO");		//전화번호
        maskFields.put("custNm"	,"CUST_NAME");	//고객명

        logger.info(generateLogMsg("searchVO==") + searchVO.toString());
        
        pRequestParamMap.put("SESSION_USER_ID", searchVO.getSessionUserId());
		
		maskingService.setMask(unpdMgmtList, maskFields, pRequestParamMap);
		
		return unpdMgmtList;
	}
	
	/**
	 * @Description : 미납요금 리스트를 조회 한다. 엑셀용
	 * @Param  : UnpdMgmtVo
	 * @Return : List<?>
	 * @Author : 김연우
	 * @Create Date : 2015. 7. 21.
	 */
	public List<?> getUnpdMgmtListExcel(UnpdMgmtVo searchVO, Map<String, Object> pRequestParamMap){
		if(searchVO.getBillYm() == null || searchVO.getBillYm().equals("")){
			throw new MvnoRunException(-1, "청구월이 존재하지 않습니다");
		}
		
		List<?> unpdMgmtListEx = unpdMgmtMapper.getUnpdMgmtListExcel(searchVO);
		
		HashMap<String, String> maskFields = new HashMap<String, String>();

        maskFields.put("svcTelNum"	,"TEL_NO");		//전화번호
        maskFields.put("custNm"	,"CUST_NAME");	//고객명

        logger.info(generateLogMsg("searchVO==") + searchVO.toString());
        
        pRequestParamMap.put("SESSION_USER_ID", searchVO.getSessionUserId());
		
		maskingService.setMask(unpdMgmtListEx, maskFields, pRequestParamMap);
		
		return unpdMgmtListEx;
	}
	
}
