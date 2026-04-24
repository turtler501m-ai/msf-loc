package com.ktis.msp.batch.job.org.termssendmgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.batch.manager.vo.SmsSendVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("termsSendMgmtMapper")
public interface TermsSendMgmtMapper {
	
	// 약관메일발송대상자 추출 2018년
	int insertTermsLmsSendTrgt();

	// 약관메일발송대상자 추출 2019년~
	int insertTermsLmsSendTrgt2();
	
	/**
	 * 약관발송대상자 List
	 * @return
	 */
	List<Map<String, Object>> termsLmsSendList();	
	
	int updateLmsSendList(SmsSendVO paramVO) throws Exception;
	
	
	/**
	 * 약관발송대상자 List
	 * @return
	 */
	List<Map<String, Object>> termsLmsSendListChk();	

	String lmsSendChk(String strMseq);
	
	int updateSendYn(String strMseq);
	
}
