package com.ktis.msp.batch.job.lgs.lgsbatchmgmt.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("lgsNstepChkMapper")
public interface LgsNstepChkMapper {
	// Nstep 처리 대상 갯수 조회 및 기준 갯수와 비교.
	int getLgsNstepProgressDateCnt();
	
	// SMS 발송 대상 전화번호 목록 추출.
	String[] getSendSmsPhonenumberList();
}
