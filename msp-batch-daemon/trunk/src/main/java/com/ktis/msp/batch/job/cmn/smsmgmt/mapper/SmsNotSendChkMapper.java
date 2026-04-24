package com.ktis.msp.batch.job.cmn.smsmgmt.mapper;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("smsNotSendChkMapper")
public interface SmsNotSendChkMapper {

	int selectSmsNotSend();
	
	int insertSmsNotSendBackup();
	
	int deleteSmsNotSend();
	
	List<String> getSendTargetInfo();
}
