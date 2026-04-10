package com.ktis.msp.pps.schedule.service;



import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktds.crypto.base.CryptoFactory;
import com.ktis.msp.base.mvc.ExcelAwareService;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.pps.schedule.mapper.PpsEncScheduleMapper;
import com.ktis.msp.pps.usimmgmt.mapper.PpsUsimMgmtMapper;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;


/**
 * @param <PpsEncScheduleMapper>
 * @Class Name : PpsEncScheduleService
 * @Description : 선불
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2016.03.16  최초생성
 * @
 * @author : 
 * @Create Date : 2016. 3. 16.
 */

@Service
public class PpsEncScheduleService extends ExcelAwareService {

	@Autowired
	private PpsEncScheduleMapper encScheduleMapper;

	
	/**
	 * 서식지 파일 암호화 처리
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> ppsEncScheduleProc(Map<String, Object> pRequestParamMap) {
		

		pRequestParamMap.put("oRetCd","" );
		pRequestParamMap.put("oRetMsg","" );
		
		
		
		logger.debug("==============처리 전 Service ==============");
		logger.debug("resultMap===>"+pRequestParamMap+"\n");
		logger.debug("===========================");
		
		encScheduleMapper.ppsEncScheduleProc(pRequestParamMap);
		logger.debug("==============처리 후 Service ==============");
		logger.debug("resultMap===>"+pRequestParamMap+"\n");
		logger.debug("===========================");


		return pRequestParamMap;
	}

}
