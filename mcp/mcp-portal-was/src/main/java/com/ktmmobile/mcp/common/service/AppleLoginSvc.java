package com.ktmmobile.mcp.common.service;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ktmmobile.mcp.apple.dto.ApplePayload;
import com.ktmmobile.mcp.apple.util.AppleUtil;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;

@Service
public class AppleLoginSvc {

	private static final Logger logger = LoggerFactory.getLogger(AppleLoginSvc.class);
	
    @Value("${apple.client.id}")
    public String CLIENT_ID;
    
    @Value("${apple.auth.url}")
    public String AUTH_URL;
    
    @Value("${apple.pc.redirectUrl}")
    public String PC_REDIRECT_URL;
    
    @Value("${apple.mobile.redirectUrl}")
    public String MOBILE_REDIRECT_URL;
    
    @Autowired
    private AppleUtil appleUtil;
    
    public static final String KEY_PATH = "";
    
	public String getAppleAuthUrl() {
	    String reqUrl = AUTH_URL + "/auth/authorize?client_id=" + CLIENT_ID + "&redirect_uri=" + getUri() + "&response_type=code id_token&scope=name email&response_mode=form_post";
	    return reqUrl;
	}		
	
    private String getUri() {
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    	String uri = "https://"+  request.getServerName() + PC_REDIRECT_URL;
		if("Y".equals(NmcpServiceUtils.isMobile())){
			uri = "https://"+  request.getServerName() + MOBILE_REDIRECT_URL;
        }
		
		return uri;
    }
    
	/**
	 * 유효한 id_token인 경우 true 리턴
	 *
	 * @param id_token
	 * @return boolean
	 */
	public boolean getVerifyIdentity(String id_token) {
	
		boolean verify = false;
		verify = appleUtil.verifyIdentityToken(id_token);
		return verify;
	}
	
	public ApplePayload getPayloadVO(String id_token) {
		return appleUtil.decodeFromIdToken(id_token);
	}

}
