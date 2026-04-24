package com.ktis.msp.batch.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.manager.mapper.BatchCommonMapper;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

/**
 * @Class Name  : BatchCommonService.java
 * @Description : BatchCommonService.Class
 * @Modification Information
 * @
 * @  수정일	  	수정자			  수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2016.05.16		TREXSHIN		  최초생성
 * @
 * @author TREXSHIN
 * @since 2016.05.16
 * @version 1.0
 */

@Service
public class BatchCommonService extends BaseService {

	@Autowired
	BatchCommonMapper batchCommonMapper;
	
	public BatchCommonService() {
		setLogPrefix("[BatchCommonService] ");
	}
	
	// 배치 실행이력 insert
	@Transactional(rollbackFor=Exception.class)
	public void insertBatchExecHst(BatchCommonVO vo) throws MvnoServiceException {
		try {
			batchCommonMapper.insertBatchExecHst(vo);
		} catch(Exception e) {
			String errCode = "ECMN1002";
			String[] errParam = new String[1];
			if(vo.getBatchJobId() == null || vo.getBatchJobId().isEmpty()) {
				errParam[0] = vo.getExecService();
			}
			throw new MvnoServiceException(errCode, errParam, e.getCause());
		}
	}
	
	// 배치 실행이력 update
	@Transactional(rollbackFor=Exception.class)
	public void updateBatchExecHst(BatchCommonVO vo) throws MvnoServiceException {
		try {
			batchCommonMapper.updateBatchExecHst(vo);
		} catch(Exception e) {
			throw new MvnoServiceException("ECMN1001", e);
		}
	}
	
	public String checkBatchExecHst(BatchCommonVO vo) {
		return batchCommonMapper.checkBatchExecHst(vo);
	}
	
	
	/**
	 * 일배치 종료카운트 조회
	 * @param vo
	 * @return int
	 */
	public int getDailyBatchEndCount(BatchCommonVO vo) {
		return batchCommonMapper.getDailyBatchEndCount(vo);
	}
	
	/**
	 * 일배치 진행중카운트 조회
	 * @param vo
	 * @return int
	 */
	public int getDailyBatchIngCount(BatchCommonVO vo) {
		return batchCommonMapper.getDailyBatchIngCount(vo);
	}
	
}
