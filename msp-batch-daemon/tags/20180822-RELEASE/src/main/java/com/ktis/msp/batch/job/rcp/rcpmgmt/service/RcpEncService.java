package com.ktis.msp.batch.job.rcp.rcpmgmt.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ktds.crypto.annotation.Crypto;
import com.ktis.msp.base.BaseService;

@Service
public class RcpEncService extends BaseService {
	
	@Crypto(decryptName="DBMSDec", fields = {"reqCardRrn" , "minorAgentRrn" , "reqCardNo", "reqAccountRrn" , "reqAccountNumber" , "ssn1" , "ssn" , "cstmrForeignerRrn"})//2015년 1월 26일 ssn2(암호화된 필드가 아님)제거 
	public List<?> decryptDBMSCustMngtListNotEgovMap(List<?> list){
		return list;
	}
}
