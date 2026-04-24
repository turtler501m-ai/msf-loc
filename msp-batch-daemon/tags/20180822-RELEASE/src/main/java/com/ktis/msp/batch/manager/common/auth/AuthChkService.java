package com.ktis.msp.batch.manager.common.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.BaseService;


/**
 * <PRE>
 * 1. ClassName	: 
 * 2. FileName	: AuthChkService.java
 * 3. Package	: com.ktis.msp.batch.manager.common.auth
 * 4. Commnet	: 
 * 5. 작성자	: Administrator
 * 6. 작성일	: 2014. 9. 25. 오후 3:59:19
 * </PRE>
 */
@Service
public class AuthChkService extends BaseService {

	@Autowired
	private AuthChkMapper authChkMapper;
		
	public boolean chkUsrGrpAuth(String userId, String authId) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("USER_ID", userId);
		paramMap.put("AUTH_ID", authId);
				
		return authChkMapper.chkUsrGrpAuth(paramMap) > 0;
	}

}
