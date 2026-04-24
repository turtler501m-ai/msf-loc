package com.ktis.msp.batch.job.cmn.smsmgmt.mapper;

import java.util.HashMap;
import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("juicefailOnlineChkMapper")
public interface JuicefailOnlineChkMapper {
	int setMsgQueue(HashMap<String, String>map);
	
	List<String> getPhoneNumber();
}
