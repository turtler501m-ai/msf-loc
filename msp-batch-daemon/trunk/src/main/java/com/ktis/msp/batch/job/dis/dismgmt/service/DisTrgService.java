package com.ktis.msp.batch.job.dis.dismgmt.service;

import com.ktis.msp.batch.job.dis.dismgmt.vo.DisVO;
import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.dis.dismgmt.mapper.DisTrgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 평생할인 정책 적용 대상 수집
 */
@Service
public class DisTrgService extends BaseService {
		
	@Autowired
	private DisTrgMapper disTrgMapper;	
		
	public int setDisTrgMst(DisVO disVo) throws MvnoServiceException {

		LOGGER.info("====== startDate : {}", disVo.getStrDt());
		LOGGER.info("====== endDate : {}", disVo.getEndDt());

		//1. MSP_DIS_TRG_DTL 에서 대상 뽑아서 PROC_YN을 T로 update
		int cntT = disTrgMapper.updateTrgDtlT(disVo);
		LOGGER.info("====== MSP_DIS_TRG_DTL select 건수 : {}", cntT);
		
		if(cntT <= 0) return 0;	//대상 없으면 리턴
		
		//2. 대상 있을 경우 최종(MAX(EFFECTIVE_DATE)) 전문 정보를 뽑아서 MSP_DIS_TRG_MST에 insert
		int cntM = disTrgMapper.inserTrgMst(disVo);
					
		//3. MSP_DIS_TRG_DTL 에서 PROC_YN이 T인 데이터 Y로 update
		disTrgMapper.updateTrgDtlY(disVo);
		
		LOGGER.info("====== MSP_DIS_TRG_MST insert 건수 : {}", cntM);
		
		return cntM;
	}
}
