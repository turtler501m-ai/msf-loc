package com.ktis.msp.org.powertmntmgmt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.org.powertmntmgmt.mapper.PowerTmntMgmtMapper;
import com.ktis.msp.org.powertmntmgmt.vo.PowerTmntMgmtVo;

/**
 * @Class Name : PowerTmntMgmtService
 * @Description : 직권해지 Service
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.14 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 14.
 */
@Service
public class PowerTmntMgmtService extends BaseService {

	@Autowired
	private PowerTmntMgmtMapper powerTmntMgmtMapper;
	
	public PowerTmntMgmtService() {
		setLogPrefix("[PowerTmntMgmtService] ");
	}
	
	/**
	 * @Description : 직권해지 리스트를 조회 한다.
	 * @Param  : PowerTmntMgmtVo
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	public List<?> selectPowerTmntMgmtList(PowerTmntMgmtVo powerTmntMgmtVo){
		
		logger.debug(generateLogMsg("================================================================="));
		logger.debug(generateLogMsg("직권해지 조회 서비스 START."));
        logger.debug(generateLogMsg("Return Vo [powerTmntMgmtVo] = " + powerTmntMgmtVo.toString()));
		logger.debug(generateLogMsg("================================================================="));
		
		List<?> powerTmntMgmtVoList = new ArrayList<PowerTmntMgmtVo>();
		
		powerTmntMgmtVoList = powerTmntMgmtMapper.selectPowerTmntMgmtList(powerTmntMgmtVo);
		
		return powerTmntMgmtVoList;
	}
	
	/**
	 * @Description : 직권해지 리스트를 조회 한다. 엑셀용
	 * @Param  : PowerTmntMgmtVo
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	public List<?> selectPowerTmntMgmtListEx(PowerTmntMgmtVo powerTmntMgmtVo){
		
		logger.debug(generateLogMsg("================================================================="));
		logger.debug(generateLogMsg("직권해지 조회 서비스 START."));
        logger.debug(generateLogMsg("Return Vo [powerTmntMgmtVo] = " + powerTmntMgmtVo.toString()));
		logger.debug(generateLogMsg("================================================================="));
		
		List<?> powerTmntMgmtVoList = new ArrayList<PowerTmntMgmtVo>();
		
		powerTmntMgmtVoList = powerTmntMgmtMapper.selectPowerTmntMgmtListEx(powerTmntMgmtVo);
		
		return powerTmntMgmtVoList;
	}
}