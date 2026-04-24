package com.ktis.msp.batch.job.rcp.rcpmgmt.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ktds.crypto.annotation.Crypto;
import com.ktis.msp.base.BaseService;
import com.ktis.msp.batch.job.rcp.rcpmgmt.vo.RcpDetailVO;

@Service
public class RcpEncService extends BaseService {
	
	@Crypto(decryptName="DBMSDec", fields = {"reqCardRrn" , "minorAgentRrn" , "reqCardNo", "reqAccountRrn" , "reqAccountNumber" , "ssn1" , "ssn" , "cstmrForeignerRrn"})//2015년 1월 26일 ssn2(암호화된 필드가 아님)제거 
	public List<?> decryptDBMSCustMngtListNotEgovMap(List<?> list){
		return list;
	}
	
	@Crypto(encryptName="DBMSEnc", fields = {"cstmrNativeRrn", "cstmrForeignerRrn", "reqAccountNumber", "reqCardNo", "minorAgentRrn",
			"cstmrForeignerPn","othersPaymentRrn","jrdclAgentRrn","entrustResRrn","reqAccountRrn","reqCardRrn", "selfIssuNum", "minorSelfIssuNum"})
	public RcpDetailVO encryptDBMSRcpDetailVO(RcpDetailVO vo){
		return vo;
	}
}
