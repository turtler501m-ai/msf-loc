package com.ktis.msp.rcp.idntMgmt.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ktds.crypto.annotation.Crypto;
import com.ktis.msp.rcp.idntMgmt.vo.IdntMgmtVO;

@Service("idntMgmtCryptoService")
public class IdntMgmtCryptoService {
	@Crypto(decryptName="DBMSDec", fields =  {"userSsn"})
	public List<?> decryptDBMSList(List<?> list){
		return list;
	}
	
	@Crypto(encryptName="DBMSEnc", fields = {"userSsn","searchVal"})
	public IdntMgmtVO encryptDBMS(IdntMgmtVO vo){
		return vo;
	}

	@Crypto(encryptName="DBMSEnc", fields = {"userSsn"})
	public IdntMgmtVO decryptDBM(IdntMgmtVO vo){
		return vo;
	}
	
	@Crypto(encryptName="DBMSEnc", fields = {"userSsn"})
	public List<?> encryptDBMSList(List<?> list){
		return list;
	}

}
