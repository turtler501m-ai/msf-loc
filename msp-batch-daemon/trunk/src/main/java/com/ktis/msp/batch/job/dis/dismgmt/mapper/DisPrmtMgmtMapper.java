
package com.ktis.msp.batch.job.dis.dismgmt.mapper;


import org.apache.ibatis.session.ResultHandler;

import com.ktis.msp.batch.job.dis.dismgmt.vo.DisPrmtMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("DisPrmtMgmtMapper")
public interface DisPrmtMgmtMapper {
	
	
	// 신청관리 엑셀 다운로드
	void selectDisListExcel(DisPrmtMgmtVO vo, ResultHandler resultHandler);
	
	
}
