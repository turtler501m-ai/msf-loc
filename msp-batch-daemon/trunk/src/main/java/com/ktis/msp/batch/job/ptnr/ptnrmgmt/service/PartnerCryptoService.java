package com.ktis.msp.batch.job.ptnr.ptnrmgmt.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.example.SEED;


/**
 * 제휴사 연동정보 암복호화 서비스
 */
@Service
public class PartnerCryptoService extends BaseService {
	
	/**
	 * SEED 암호화
	 * @param plain
	 * @param seedKey
	 * @return
	 */
	protected String encrypt(String plain, String seedKey) {
		
		LOGGER.debug("-------------------- 암호화 시작 ----------------------");
		LOGGER.debug("평문 = [{}]- len[{}]", plain, plain.length());
		SEED seed = new SEED();
		// 평문을 16자리로 맞춰야 함. 16자리가 넘으면 16자리로 잘라서 암호화 해야함.
		String sPlain = StringUtils.rightPad(plain, 16);
		
		LOGGER.debug("sPlain = ", sPlain);
		LOGGER.debug("seedKey = ", seedKey);
		//--------------------------------------------------//
		// 암호화 모듈 호출
		//--------------------------------------------------//
		byte[] pbCipher = seed.encrypt(sPlain, seedKey);
		
		StringBuffer sb = new StringBuffer();
		
		for (int i=0; i<pbCipher.length; i++){
			sb.append(seed.appendLPad(Integer.toHexString(0xff&pbCipher[i]),2,"0"));
		}
		
		LOGGER.debug("암호화=[{}]- len[{}]",sb.toString(), sb.toString().length());
		
		LOGGER.debug("-------------------- 암호화 끝 ----------------------");
		
		return sb.toString();
		
	}
	
	/**
	 * SEED 복호화
	 * @param sHexCipher
	 * @param seedKey
	 * @return
	 */
	protected String decrypt(String sHexCipher, String seedKey) {
		
		LOGGER.debug("-------------------- 복호화 시작 ----------------------");
		LOGGER.debug("암호화 = [{}]- len[{}]",sHexCipher, sHexCipher.length());
		SEED seed = new SEED();

		byte[] bCipher = seed.getStringHextoByte(sHexCipher);
		
		//--------------------------------------------------//
		// 복호화 모듈 호출  //
		//--------------------------------------------------//
		byte[] pbPlain2 = seed.decrypt(bCipher, seedKey);
		
		for (int i=0; i<pbPlain2.length; i++){
			seed.appendLPad(Integer.toHexString(0xff&pbPlain2[i]),2,"0");
		}
		
		String temp3 = new String(pbPlain2);
		LOGGER.debug("복호화 [{}]- len[{}]", temp3, temp3.length());
		
		LOGGER.debug("-------------------- 복호화 끝 ----------------------");
		
		return temp3.trim();
		
	}
	
}
