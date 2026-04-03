package com.ktmmobile.msf.system.common.service;

import org.springframework.stereotype.Service;

import com.ktds.crypto.annotation.Crypto;
import com.ktmmobile.msf.system.common.dto.AppInfoConvertDto;
import com.ktmmobile.msf.system.common.dto.AppInfoConvertTempDto;
import com.ktmmobile.msf.system.common.dto.EncryptDto;

@Service("encryptService")
public class EncryptService {

	@Crypto(encryptName="NStepEnc", fields = {"value"})
	public String encryptNstep(EncryptDto encryptDto){
		return encryptDto.getValue();
	}
	
	@Crypto(decryptName="NStepDec", fields = {"value"})
	public String decryptNstep(EncryptDto encryptDto){
		return encryptDto.getValue();
	}
	
	@Crypto(encryptName="DBMSEnc", fields = {"value"})
	public String encryptDBMS(EncryptDto encryptDto){
		return encryptDto.getValue();
	}
	
	@Crypto(decryptName="DBMSDec", fields = {"value"})
	public String decryptDBMS(EncryptDto encryptDto){
		return encryptDto.getValue();
	}
	
	@Crypto(encryptName="SHA512", fields = {"value"})
	public String encryptSHA(EncryptDto encryptDto){
		return encryptDto.getValue();
	}
	
	@Crypto(decryptName="DBMSDec", fields = {"cstmr_foreigner_pn","cstmr_foreigner_rrn"
			,"cstmr_native_rrn", "minor_agent_rrn", "entrust_res_rrn", "others_payment_rrn", "name_change_rrn",
			"req_account_number", "req_account_rrn", "req_card_no"})
	public AppInfoConvertDto decryptDBMS(AppInfoConvertDto appInfoConvertDto){
		return appInfoConvertDto;
	}
	@Crypto(decryptName="DBMSDec", fields = {"cstmr_foreigner_pn","cstmr_foreigner_rrn"
			,"cstmr_native_rrn", "minor_agent_rrn", "entrust_res_rrn", "others_payment_rrn", "name_change_rrn",
			"req_account_number", "req_account_rrn", "req_card_no"})
	public AppInfoConvertTempDto decryptDBMS(AppInfoConvertTempDto appInfoConvertTempDto){
		return appInfoConvertTempDto;
	}
}
