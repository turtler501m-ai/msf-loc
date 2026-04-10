package com.ktis.msp.pps.hdofccustmgmt.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.ktds.crypto.annotation.Crypto;
import com.ktis.msp.base.mvc.BaseService;


import egovframework.rte.psl.dataaccess.util.EgovMap;






@Service
public class CustEncService extends BaseService {
	
	
	
	@Crypto(decryptName="DBMSDec", fields = {"userSsn","adSsn","cmsUserSsn","customerSsn"})
	public List<EgovMap> decryptCustMngtList(List<EgovMap> list){
		return list;
	}

}
