package com.ktis.msp.cmn.btchmgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.cmn.btchmgmt.vo.BatchJobVO;
import com.ktis.msp.cmn.btchmgmt.vo.JuiceBtchLogMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("BtchMgmtMapper")
public interface BtchMgmtMapper {
	
	List<Map<String, Object>> getJuiceBtchIdList();
	
	List<Map<String, Object>> getCmnJuiceBtchLogList(JuiceBtchLogMgmtVO vo);
	
	void insertBatchRequest(BatchJobVO vo);
	
	List<Map<String, Object>> getBatchInfo(BatchJobVO vo);
	
	String getExecDupCheck(BatchJobVO vo);
	
	void insertBatchInfo(BatchJobVO vo);
	
	void updateBatchInfo(BatchJobVO vo);
	
	List<Map<String, Object>> getBatchExecHst(BatchJobVO vo);
	
	int getExecBatchCnt(BatchJobVO vo);

}