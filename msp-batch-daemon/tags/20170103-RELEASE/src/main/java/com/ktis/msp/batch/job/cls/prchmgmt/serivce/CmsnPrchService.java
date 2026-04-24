package com.ktis.msp.batch.job.cls.prchmgmt.serivce;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.cls.ClsAcntConfig;
import com.ktis.msp.batch.job.cls.clsacntclosemgmt.mapper.ClsAcntCloseMgmtMapper;
import com.ktis.msp.batch.job.cls.prchmgmt.mapper.PrchMgmtMapper;

@Service
public class CmsnPrchService extends BaseService {
	
	@Autowired
	private PrchMgmtMapper prchMgmtMapper;
	
	@Autowired
	private ClsAcntCloseMgmtMapper clsAcntCloseMgmtMapper;
	
	/**
	 * 수수료매입정보생성
	 * @param param 
	 * @param param
	 * @throws MvnoServiceException 
	 */
	@Transactional(rollbackFor=Exception.class)
	public void setCmsnPrchReg(Map<String, Object> param) throws MvnoServiceException {
		try {
			
			LOGGER.info("수수료매입정보생성 param={}", param);
			
			Map<String, Object> inputMap = new HashMap<String, Object>();
			inputMap.put("CLS_ACNT_YM", param.get("SALE_YM"));
			inputMap.put("CLS_ACNT_DUTY_CD", ClsAcntConfig.CLS_TSK_CMSN_CNFM_CD); // 수수료확정 C030
			inputMap.put("SRL_NUM", "1");
			
			// 수수료결산마감상태조회
			String closeStatCd = clsAcntCloseMgmtMapper.getClsAcntCloseStat(inputMap);
					
			// 완료상태가 아니면 리턴
			if(closeStatCd == null || !closeStatCd.equals(ClsAcntConfig.CLOSE_STAT_CMP)) {
				throw new MvnoServiceException("ECLS1029");
			}
					
			// 수수료매입정보 생성
			inputMap.clear();
			inputMap.put("PRCH_YM", param.get("SALE_YM"));
			inputMap.put("USER_ID", ClsAcntConfig.USER_ID);
			
			prchMgmtMapper.deleteCmsnPrchInfo(inputMap);
			
			int i = prchMgmtMapper.insertCmsnPrchInfo(inputMap);
			
			LOGGER.debug("i={}", i);
			
			if(i>0) {
				// 수수료매입 마감처리
				inputMap.clear();
				inputMap.put("CLS_ACNT_YM", param.get("SALE_YM"));
				inputMap.put("CLS_ACNT_DUTY_CD", ClsAcntConfig.CLS_TSK_CMSN_PRCH_CD);	// C040
				inputMap.put("SRL_NUM", "1");
				inputMap.put("CLOSE_STAT_CD", ClsAcntConfig.CLOSE_STAT_CMP);
				inputMap.put("USER_ID", ClsAcntConfig.USER_ID);
				
				LOGGER.info("수수료매입 마감처리 inputMap={}", inputMap);
				
				clsAcntCloseMgmtMapper.saveClsAcntCloseMerge(inputMap);
				
			}
			
		} catch(MvnoServiceException e) {
			throw e;
		} catch(Exception e) {
			throw new MvnoServiceException("ECLS1002", e);
		}
		
	}
	
}
