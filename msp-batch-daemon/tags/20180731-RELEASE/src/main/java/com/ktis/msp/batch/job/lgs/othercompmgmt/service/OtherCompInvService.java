package com.ktis.msp.batch.job.lgs.othercompmgmt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.lgs.othercompmgmt.mapper.OtherCompInvMapper;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

/**
 * @Class Name  : OtherCompInvService.java
 * @Description : OtherCompInvService.Class
 * @Modification Information
 * @
 * @  수정일	  	수정자			  수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2015.07.29  TREXSHIN		  최초생성
 * @
 * @author TREXSHIN
 * @since 2015.07.29
 * @version 1.0
 */

@Service
public class OtherCompInvService extends BaseService {

	@Autowired
	OtherCompInvMapper otherCompInvMapper;
	
	public OtherCompInvService() {
		setLogPrefix("[OtherCompInvService] ");
	}
	
	// 타사단말 일재고 배치
	@Transactional(rollbackFor=Exception.class)
	public void makeDayInvtr(BatchCommonVO batchVo) throws MvnoServiceException {
		
		int nInsertCnt = -1;
		
		try {
			// 타사단말 일배치 실행
			nInsertCnt = otherCompInvMapper.insertOtcpDayInvtr();
			batchVo.setRemrk(" 성공 : "+batchVo.getBatchJobId()+"(타사단말일재고배치) 처리건수 =["+nInsertCnt+"]");
			batchVo.setExecCount(nInsertCnt);
		} catch(Exception e) {
			batchVo.setRemrk(" 실패 : "+batchVo.getBatchJobId()+"(타사단말일재고배치) 처리건수 =["+nInsertCnt+"]");
			LOGGER.error("타사단말 일재고 생성시 오류 | 함수: makeDayInvtr --> insertOtcpDayInvtr");
			String[] args = new String[2];
			args[0] = batchVo.getBatchJobId();
			args[1] = "타사단말 일재고 생성";
			throw new MvnoServiceException("ELGS1001", args, e);
		}
		
	}
	
}
