package com.ktis.msp.org.hndsetamtmgmt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.org.hndsetamtmgmt.mapper.HndstAmtMapper;
import com.ktis.msp.org.hndsetamtmgmt.vo.HndstAmtVo;

/**
 * @Class Name : HndstModelService
 * @Description : 제품단가 Service
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.14 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 14.
 */
@Service
public class HndstAmtService extends BaseService {

	@Autowired
	private HndstAmtMapper hndstAmtMapper;
	
	public HndstAmtService() {
		setLogPrefix("[HndstAmtService] ");
	}
	    
    
    /**
     * @Description : 단통법 관련 보조금 MAX를 찾아온다.
     * @Param  : HndstAmtVo
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
    public String selectMaxAmt(HndstAmtVo hndstAmtVo){
    	
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("단통법 관련 보조금 MAX 서비스 START."));
		logger.info(generateLogMsg("hndstAmtVo == " + hndstAmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		String strMaxAmt = hndstAmtMapper.selectMaxAmt(hndstAmtVo);
		
        return strMaxAmt;
    }
    
}
