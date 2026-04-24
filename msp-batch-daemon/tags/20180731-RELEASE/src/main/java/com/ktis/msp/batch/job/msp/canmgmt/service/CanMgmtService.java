package com.ktis.msp.batch.job.msp.canmgmt.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.msp.canmgmt.mapper.CanMgmtMapper;
import com.ktis.msp.batch.job.msp.canmgmt.vo.CanMgmtVO;

@Service
public class CanMgmtService extends BaseService {
	
	@Autowired
	private CanMgmtMapper canMgmtMapper;
	@Autowired
	private MplatFormService mplatFormService;
	
	/**
	 * 직권해지처리
	 * @param param
	 * @return
	 * @throws MvnoServiceException 
	 */
	@Transactional(rollbackFor=Exception.class)
	public int procCanMgmt() throws MvnoServiceException{
		int procCnt = 0;
		HashMap<String,String> paramMap;
		try {
				
			List<CanMgmtVO> list = canMgmtMapper.getCanMgmtList();
			
			for(CanMgmtVO vo : list) {
				paramMap = new HashMap<String,String>();
				paramMap.put("appEventCd", "CP0");
				paramMap.put("ctn", vo.getSubscriberNo());
				paramMap.put("ncn", vo.getContractNum());
				paramMap.put("custId", vo.getCustomerId());
								
				paramMap = mplatFormService.callService(paramMap);
				
				if (paramMap.get("result").equals("SUCCESS")) {
					vo.setProcStatus("S");
					
					vo.setConnRsltKey(paramMap.get("resultKey"));
					vo.setConnRsltType(paramMap.get("resultType"));
					vo.setConnRsltCd(paramMap.get("resultCd"));
					vo.setConnRsltMsg(paramMap.get("resultMsg"));
					vo.setProcDesc("");
					
				} else {
					vo.setProcStatus("E");
					vo.setProcDesc(paramMap.get("result"));
				}
				
				canMgmtMapper.setCanMgmt(vo);
				
				LOGGER.info("★★★★★ CALL PROCEDURE ★★★★★");
				try {
					canMgmtMapper.callPpsCanUsrRes(vo);
				} catch(Exception e) {
					LOGGER.error("직권해지연동 처리 프로시져 호출 에러");
					LOGGER.error(e.getMessage());
				}
				
				LOGGER.info("☆☆☆☆☆ END  PROCEDURE ☆☆☆☆☆");
				
				procCnt++;
			}
				
		} catch(Exception e) {
			throw new MvnoServiceException("EMSP1008", e);
		}
		
		return procCnt;
		
	}
	
}
