package com.ktis.msp.batch.job.org.userlmsmgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.batch.manager.vo.SmsCommonVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("webUsrLmsMapper")
public interface WebUsrLmsMapper {

	List<Map<String, Object>> userInfoLmsSendList() throws Exception;
	
	int updateLmsSendList(SmsCommonVO paramVO) throws Exception;

	String selectMsgSeq();
	
	int insertTemplateMsgQueue(SmsCommonVO paramVO);
	
	List<Map<String, Object>> userInfoLmsSendListChk() throws Exception;

	String lmsSendChk(String strMseq);
	
	int updateSendYn(String strMseq);
}
