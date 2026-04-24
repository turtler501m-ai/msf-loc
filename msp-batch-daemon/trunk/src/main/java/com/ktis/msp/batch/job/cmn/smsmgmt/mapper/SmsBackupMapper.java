package com.ktis.msp.batch.job.cmn.smsmgmt.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("smsBackupMapper")
public interface SmsBackupMapper {

	int insertSmsBackup();
	
	int deleteSmsBackupData();
	
	int deleteSmsResend();
}
