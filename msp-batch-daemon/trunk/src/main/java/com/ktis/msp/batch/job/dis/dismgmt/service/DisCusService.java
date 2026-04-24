package com.ktis.msp.batch.job.dis.dismgmt.service;

import com.ktis.msp.batch.job.dis.dismgmt.vo.DisVO;
import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.dis.dismgmt.mapper.DisCusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 고객별 평생할인 부가서비스 가입된 대상을 MSP_DIS_CUS_HST insert
 */
@Service
public class DisCusService extends BaseService {
		
	@Autowired
	private DisCusMapper disCusMapper;
	
	public int setDisCustHst(DisVO disVo) throws MvnoServiceException {

		int cntU = 0;

		//1. MSP_DIS_APD 에서 대상 뽑아서 PROC_YN을 T로 update
		int cntT = disCusMapper.updateApdProcT(disVo);
		LOGGER.info("====== MSP_DIS_APD select 건수 : {}", cntT);		
		
		//2. 평생할인 부가서비스 가입된 대상 찾아서 MSP_DIS_CUST_HST에 넣기
		//MSP_DIS_APD에서 PROC_YN의 대상과 MSP_DIS_FNL_INF에서 대상 찾아서 MSP_DIS_CUST_HST insert
		disVo.setWorkCnt(cntT);
		int cntW = disCusMapper.insertCusHst(disVo);
		LOGGER.info("====== MSP_DIS_CUST_HST insert 건수 : {}", cntW);		
		
		//3. MSP_REQUEST_DTL의 DIS_PRMT_ID 컬럼에 프로모션 아이디 update
		cntU = disCusMapper.updateRequestDtlPrmtID(disVo);
		LOGGER.info("====== MSP_REQUEST_DTL update 건수 : {}", cntU);
		
		//4. MSP_DIS_FNL_INF에서 PROC_YN이 T인 대상 Y로 update
		cntU = disCusMapper.updateFnlInfProcY(disVo);
		LOGGER.info("====== MSP_DIS_FNL_INF update 건수 : {}", cntU);
		
		if(cntT > 0) {
			//5. MSP_DIS_APD에서 PROC_YN이 T인 대상 Y로 update
			cntU = disCusMapper.updateApdProcY(disVo);
			LOGGER.info("====== MSP_DIS_APD update 건수 : {}", cntU);
		}	
		
		return cntW;
	}
}
