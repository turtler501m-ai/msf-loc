package com.ktis.msp.batch.job.cmn.smsmgmt.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("smsInfoAlienationMapper")
public interface SmsInfoAlienationMapper {

	// SMS 전송 대상 조회
	List<Map<String,Object>> selectInfoAnSendList();
}
