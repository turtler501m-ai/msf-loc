package com.ktmmobile.mcp.common.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ktmmobile.mcp.common.util.CommonHttpClient;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;

@Service
public class KakaoLoginSvc {

    private static final Logger logger = LoggerFactory.getLogger(KakaoLoginSvc.class);

    @Value("${inf.url}")
    private String infUrl;

    @Value("${KAKAO_AUTH_URL}")
    private String kakaoAuthUrl;

    @Value("${KAKAO_API_KEY}")
    private String kakaoApiKey;

    @Value("${KAKAO_CALLBACK_URL}")
    private String redirectURI;

    @Value("${KAKAO_M_CALLBACK_URL}")
    private String mRedirectURI;

    @Value("${ext.url}")
    private String extUrl;

    public String getKakaoAuthUrl() {

        String reqUrl = kakaoAuthUrl + "/oauth/authorize?client_id=" + kakaoApiKey + "&redirect_uri="+ getUri() + "&response_type=code&prompt=login";
        return reqUrl;
    }

    public String getAccessToken (String authorizeCode) {
        String accessToken = "";
        String refreshToken = "";
        try {

            CommonHttpClient client = new CommonHttpClient(extUrl+"/getKakaoAccessToken.do");
            NameValuePair[] data = {
                    new NameValuePair("authorizeCode",authorizeCode),
                    new NameValuePair("uri",getUri()),
                    new NameValuePair("clientId",kakaoApiKey)
            };
            String result = client.postUtf8(data);
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            accessToken = element.getAsJsonObject().get("access_token").getAsString();
            refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();

        } catch(RuntimeException e) {
            logger.error("getAccessToken.Exception = {}", e.getMessage());
        } catch (Exception e) {
            logger.error("getAccessToken.Exception = {}", e.getMessage());
        }


        return accessToken;
    }

    public HashMap<String, Object> getKakaoUserInfo (String accessToken) {

        //    요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
        HashMap<String, Object> userInfo = new HashMap<String, Object>();

        try {

            CommonHttpClient client = new CommonHttpClient(extUrl+"/getKakaoUserInfo.do");
            NameValuePair[] data = {
                    new NameValuePair("accessToken",accessToken),
            };
            String result = client.postUtf8(data);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result.toString());

            String id = element.getAsJsonObject().get("id").getAsString();
            JsonElement kakaoAccount = element.getAsJsonObject().get("kakao_account");
            String snsMobileNo = "";
            if("true".equals(kakaoAccount.getAsJsonObject().get("has_phone_number").getAsString())){
                kakaoAccount.getAsJsonObject().get("phone_number").getAsString().replace("+82 ", "0").replaceAll("-", "");
            }

            userInfo.put("id", id);
            userInfo.put("snsMobileNo", snsMobileNo);

        } catch(RuntimeException e) {
            logger.error("getKakaoUserInfo.Exception = {}", e.getMessage());
        } catch (Exception e) {
            logger.error("getKakaoUserInfo.Exception = {}", e.getMessage());
        }

        return userInfo;

    }

    public String getKakaoLogOut(String accessToken) {
        String result = "";
        try {

            CommonHttpClient client = new CommonHttpClient(extUrl+"/getKakaoLogOut.do");
            NameValuePair[] data = {
                    new NameValuePair("accessToken",accessToken),
            };
            result = client.postUtf8(data);
        } catch(RuntimeException e) {
            result = e.getMessage();
        } catch(Exception e) {
            result = e.getMessage();
        }

        return result;
    }

    private String getUri() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String uri = "https://"+  request.getServerName() + redirectURI;
        if("Y".equals(NmcpServiceUtils.isMobile())){
            uri = "https://"+  request.getServerName() + mRedirectURI;
        }

        return uri;
    }

}
