package com.ktis.msp.batch.job.cmn.smsmgmt.mapper;

import java.util.List;

import com.ktis.msp.batch.manager.vo.KtSmsCommonVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("smsRetryMapper")
public interface SmsRetryMapper {

	List<KtSmsCommonVO> getSmsRetryList();
	
}
