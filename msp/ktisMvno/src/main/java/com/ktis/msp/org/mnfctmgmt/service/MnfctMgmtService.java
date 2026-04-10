package com.ktis.msp.org.mnfctmgmt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.org.mnfctmgmt.mapper.MnfctMgmtMapper;
import com.ktis.msp.org.mnfctmgmt.vo.MnfctMgmtVo;

/**
 * @Class Name : MnfctMgmtService
 * @Description : 제조사/공급사 Service
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.14 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 14.
 */
@Service
public class MnfctMgmtService extends BaseService {

	@Autowired
	private MnfctMgmtMapper mnfctMgmtMapper;
	
	@Autowired
	private MaskingService maskingService;
	
	public MnfctMgmtService() {
		setLogPrefix("[MnfctMgmtService] ");
	}
	
	/**
	 * @Description : 제조사/공급사 리스트를 조회 한다.
	 * @Param  : mnfctMgmtVo
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	public List<?> selectMngctServiceList(MnfctMgmtVo mnfctMgmtVo){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("제조사/공급사 조회 START."));
        logger.info(generateLogMsg("Return Vo [searchVO] = " + mnfctMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> mnfctMgmtVoList = new ArrayList<MnfctMgmtVo>();
		
		mnfctMgmtVo.setMnfctId(mnfctMgmtVo.getMnfctId().toLowerCase());
		mnfctMgmtVoList = mnfctMgmtMapper.selectMnfctMgmtList(mnfctMgmtVo);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
        
		paramMap.put("SESSION_USER_ID", mnfctMgmtVo.getSessionUserId());
		
		maskingService.setMask(mnfctMgmtVoList, maskingService.getMaskFields(), paramMap);
		
		return mnfctMgmtVoList;
		
	}
	
    /**
     * @Description : 제조사/공급사를 생성한다.
     * @Param  : mnfctMgmtVo
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
	@Transactional(rollbackFor=Exception.class)
    public int insertMnfctMgmt(MnfctMgmtVo mnfctMgmtVo){
    	
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("제조사/공급사 등록 START."));
		logger.info(generateLogMsg("mnfctMgmtVo == " + mnfctMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		
		int resultCnt = mnfctMgmtMapper.insertMnfctMgmt(mnfctMgmtVo);
		
		logger.info(generateLogMsg("등록 건수 = ") + resultCnt);
		
        return resultCnt;
    }

    /**
     * @Description : 제조사/공급사를 수정한다
     * @Param  : MnfctMgmtVo
     * @Return : int
     * @Author : 고은정
     * @Create Date : 2014. 8. 27.
     */
	@Transactional(rollbackFor=Exception.class)
    public int updateMnfctMgmt(MnfctMgmtVo mnfctMgmtVo){
        
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("제조사/공급사 수정 START."));
        logger.info(generateLogMsg("mnfctMgmtVo == " + mnfctMgmtVo.toString()));
        logger.info(generateLogMsg("================================================================="));
        
        
        int resultCnt = mnfctMgmtMapper.updateMnfctMgmt(mnfctMgmtVo);
        
        logger.info(generateLogMsg("등록 건수 = ") + resultCnt);
        
        return resultCnt;
    }
    
    /**
     * @Description : 제조사명을 조회한다
     * @Param  : MnfctMgmtVo
     * @Return : List<?>
     * @Author : 고은정
     * @Create Date : 2014. 8. 27.
     */
    public List<?> selectMnfctList(MnfctMgmtVo mnfctMgmtVo){
        
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("제조사명 조회 START."));
        logger.info(generateLogMsg("Return Vo [searchVO] = " + mnfctMgmtVo.toString()));
        logger.info(generateLogMsg("================================================================="));
        
        List<?> mnfctMgmtList = new ArrayList<MnfctMgmtVo>();
        
        mnfctMgmtList = mnfctMgmtMapper.selectMnfctList(mnfctMgmtVo);
        
        return mnfctMgmtList;
        
    }
}
