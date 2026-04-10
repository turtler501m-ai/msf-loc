package com.ktis.msp.rsk.retentionMgmt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.rsk.retentionMgmt.mapper.RetentionMapper;
import com.ktis.msp.rsk.retentionMgmt.vo.RetentionVO;

@Service
public class RetentionService extends BaseService {

	@Autowired
	private RetentionMapper retentionMapper;
    
    /** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;
	
    /**
     * @Description : 해지 후 재가입 정보를 가져온다.
     * @Param  : RetentionVO
     * @Return : List<?>
     * @Author : 박준성
     * @Create Date : 2016.08.31 
     */
    public List<?> retentionMgmtList(RetentionVO retentionVO, Map<String, Object> pRequestParamMap){
    	
    	List<?> retentionVOList = new ArrayList<RetentionVO>();
    	retentionVOList = retentionMapper.retentionMgmtList(retentionVO);
    	

		HashMap<String, String> maskFields = new HashMap<String, String>();

        maskFields.put("subscriberNo"	,"TEL_NO");		//전화번호
        maskFields.put("subLinkName"	,"CUST_NAME");	//고객명
        maskFields.put("intmSrlNo"		,"DEV_NO");		//일련번호
        maskFields.put("cSubscriberNo"	,"TEL_NO");		//전화번호(해지)
        maskFields.put("cSubLinkName"	,"CUST_NAME");	//고객명(해지)
        maskFields.put("cIntmSrlNo"		,"DEV_NO");		//일련번호(해지)

        logger.info(generateLogMsg("retentionVO==") + retentionVO.toString());
        
        pRequestParamMap.put("SESSION_USER_ID", retentionVO.getSessionUserId());
		
		maskingService.setMask(retentionVOList, maskFields, pRequestParamMap);
    	
    	
    	return retentionVOList;
    }
    
	/**
	 * @Description : 해지후 재가입 정보를 조회 한다. 엑셀용
	 * @Param  : RetentionVO
	 * @Return : List<?>
	 * @Author : 박준성
	 * @Create Date : 2016.09.02
	 */
	public List<?> retentionMgmtEx(RetentionVO retentionVO, Map<String, Object> pRequestParamMap){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("해지후 재가입 정보 START."));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> retentionVOList = new ArrayList<RetentionVO>();
		
		retentionVOList = retentionMapper.retentionMgmtEx(retentionVO);
		
		
		HashMap<String, String> maskFields = new HashMap<String, String>();

        maskFields.put("subscriberNo"	,"TEL_NO");		//전화번호
        maskFields.put("subLinkName"	,"CUST_NAME");	//고객명
        maskFields.put("intmSrlNo"		,"DEV_NO");		//일련번호
        maskFields.put("cSubscriberNo"	,"TEL_NO");		//전화번호(해지)
        maskFields.put("cSubLinkName"	,"CUST_NAME");	//고객명(해지)
        maskFields.put("cIntmSrlNo"		,"DEV_NO");		//일련번호(해지)

        logger.info(generateLogMsg("retentionVO==") + retentionVO.toString());
        
        pRequestParamMap.put("SESSION_USER_ID", retentionVO.getSessionUserId());
		
		maskingService.setMask(retentionVOList, maskFields, pRequestParamMap);
		
		
		return retentionVOList;
	}
    
}
