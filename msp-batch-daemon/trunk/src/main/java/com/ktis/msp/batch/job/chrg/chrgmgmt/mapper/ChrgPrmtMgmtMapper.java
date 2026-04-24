
package com.ktis.msp.batch.job.chrg.chrgmgmt.mapper;


import org.apache.ibatis.session.ResultHandler;

import com.ktis.msp.batch.job.chrg.chrgmgmt.vo.ChrgPrmtMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("ChrgPrmtMgmtMapper")
public interface ChrgPrmtMgmtMapper {
	
	
	// 신청관리 엑셀 다운로드
	void selectChrgListExcel(ChrgPrmtMgmtVO vo, ResultHandler resultHandler);
	
	
}
