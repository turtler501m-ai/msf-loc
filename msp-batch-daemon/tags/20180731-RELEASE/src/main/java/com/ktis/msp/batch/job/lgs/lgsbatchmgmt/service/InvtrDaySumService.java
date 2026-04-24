package com.ktis.msp.batch.job.lgs.lgsbatchmgmt.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.lgs.lgsbatchmgmt.mapper.InvtrDaySumMapper;
import com.ktis.msp.batch.job.lgs.lgsbatchmgmt.vo.LgsDayBatchVo;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

@Service
public class InvtrDaySumService  extends BaseService {
	
	
	@Autowired
	private InvtrDaySumMapper invtrDaySumMapper;
	
	public InvtrDaySumService() {
		setLogPrefix("[InvtrDaySumService] ");
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void createTodayInvtrDayMst(Map<String, Object> param, BatchCommonVO batchVo) throws MvnoServiceException {
		
		int nInsertCnt = -1;
		LgsDayBatchVo inVo = new LgsDayBatchVo();
		
		try {
			inVo.setStdDt((String)param.get("stdDt"));	
			nInsertCnt = invtrDaySumMapper.createTodayInvtrDayMst(inVo); // 일재고 생성 실행 LGS_INVTR_DAY_MST
			batchVo.setRemrk(" 성공 : "+batchVo.getBatchJobId()+"(일재고배치) 처리건수 =["+nInsertCnt+"]");
			batchVo.setExecCount(nInsertCnt);
			
		} catch(Exception e) {
			batchVo.setRemrk(" 실패 : "+batchVo.getBatchJobId()+"(일재고배치) 처리건수 =["+nInsertCnt+"]");
			LOGGER.error("일재고 생성시 오류 | 함수: createTodayInvtrDayMst --> createTodayInvtrDayMst");
			String[] args = new String[2];
			args[0] = batchVo.getBatchJobId();
			args[1] = "일재고 생성";
			throw new MvnoServiceException("ELGS1001", args, e);
		}
		
	}
	
}
