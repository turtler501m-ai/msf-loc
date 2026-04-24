package com.ktis.msp.batch.job.org.userstatus.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("userStatusMapper")
public interface UserStatusMapper {
	
	/**
	 * 사용자 상태변경(정지상태) Procedure 호출
	 */
	void callUserStopStatusProc();
	
	/**
	 * 사용자 상태변경(삭제상태) Procedure 호출
	 */
	void callUserDelStatusProc();
	
}
