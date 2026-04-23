package com.ktis.mcpif.example.service;

import org.springframework.stereotype.Service;

import com.ktds.crypto.annotation.Crypto;
import com.ktis.mcpif.example.ExampleVo;

/**
 * @Class Name : ExampleService
 * @Description : 샘플 서비스 입니다.
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 13.
 */
@Service("exampleService")
public class ExampleService  {// implements ExampleService {

	@Crypto(encryptName="NStepEnc", fields = {"userName", "passwd"})
	public ExampleVo encryptNstep(ExampleVo vo){
		return vo;
	}
	
	@Crypto(decryptName="NStepDec", fields = {"userName", "passwd"})
	public ExampleVo decryptNstep(ExampleVo vo){
		return vo;
	}
	
	@Crypto(encryptName="DBMSEnc", fields = {"userName", "passwd"})
	public ExampleVo encryptDBMS(ExampleVo vo){
		return vo;
	}
	
	@Crypto(decryptName="DBMSDec", fields = {"userName", "passwd"})
	public ExampleVo decryptDBMS(ExampleVo vo){
		return vo;
	}
	
	@Crypto(encryptName="SHA512", fields = {"userName", "passwd"})
	public ExampleVo encryptSHA(ExampleVo vo){
		return vo;
	}
	
	
	
	
}
