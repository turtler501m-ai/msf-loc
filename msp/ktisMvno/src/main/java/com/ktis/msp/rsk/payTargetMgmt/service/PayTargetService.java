package com.ktis.msp.rsk.payTargetMgmt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.org.rqstmgmt.vo.RqstMgmtVo;
import com.ktis.msp.rsk.payTargetMgmt.mapper.PayTargetMapper;
import com.ktis.msp.rsk.payTargetMgmt.vo.PayTargetVO;

@Service
public class PayTargetService extends BaseService {

	@Autowired
	private PayTargetMapper payTargetMapper;
    
    /** 마스킹 처리 서비스 */
//	@Autowired
//	private MaskingService maskingService;
	

	/**
	 * @Description : 납부대상 리스트 조회
	 * @Param  : payTargetVO
	 * @Return : List<?>
	 * @Author : 박준성
	 * @Create Date : 2016.10.14
	 */
    public List<?> payTargetMgmtList(PayTargetVO payTargetVO, Map<String, Object> pRequestParamMap){
    	
    	List<?> payTargetVOList = new ArrayList<PayTargetVO>();
    	payTargetVOList = payTargetMapper.payTargetMgmtList(payTargetVO);
    	

/*		HashMap<String, String> maskFields = new HashMap<String, String>();

        maskFields.put("subscriberNo"	,"TEL_NO");		//전화번호
        maskFields.put("subLinkName"	,"CUST_NAME");	//고객명
        maskFields.put("intmSrlNo"		,"DEV_NO");		//일련번호
        maskFields.put("cSubscriberNo"	,"TEL_NO");		//전화번호(해지)
        maskFields.put("cSubLinkName"	,"CUST_NAME");	//고객명(해지)
        maskFields.put("cIntmSrlNo"		,"DEV_NO");		//일련번호(해지)

        logger.info(generateLogMsg("payTargetVO==") + payTargetVO.toString());
        
        pRequestParamMap.put("SESSION_USER_ID", payTargetVO.getSessionUserId());
		
		maskingService.setMask(payTargetVOList, maskFields, pRequestParamMap);*/
    	
    	
    	return payTargetVOList;
    }
    
    
	/**
	 * @Description : 납부대상리스트를 조회한다. 엑셀용
	 * @Param  : payTargetVO
	 * @Return : List<?>
	 * @Author : 박준성
	 * @Create Date : 2016.10.14
	 */
	public List<?> payTargetMgmtEx(PayTargetVO payTargetVO, Map<String, Object> pRequestParamMap){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("해지후 재가입 정보 START."));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> payTargetVOList = new ArrayList<RqstMgmtVo>();
		
		payTargetVOList = payTargetMapper.payTargetMgmtEx(payTargetVO);
		
		
/*		HashMap<String, String> maskFields = new HashMap<String, String>();

        maskFields.put("subscriberNo"	,"TEL_NO");		//전화번호
        maskFields.put("subLinkName"	,"CUST_NAME");	//고객명
        maskFields.put("intmSrlNo"		,"DEV_NO");		//일련번호
        maskFields.put("cSubscriberNo"	,"TEL_NO");		//전화번호(해지)
        maskFields.put("cSubLinkName"	,"CUST_NAME");	//고객명(해지)
        maskFields.put("cIntmSrlNo"		,"DEV_NO");		//일련번호(해지)

        logger.info(generateLogMsg("payTargetVO==") + payTargetVO.toString());
        
        pRequestParamMap.put("SESSION_USER_ID", payTargetVO.getSessionUserId());
		
		maskingService.setMask(payTargetVOList, maskFields, pRequestParamMap);*/
		
		
		return payTargetVOList;
	}
}