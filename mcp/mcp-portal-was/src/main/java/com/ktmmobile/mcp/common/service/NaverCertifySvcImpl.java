package com.ktmmobile.mcp.common.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ktmmobile.mcp.common.dao.NaverCertfyDao;
import com.ktmmobile.mcp.common.dto.NaverDto;
import com.ktmmobile.mcp.common.util.CommonHttpClient;
import com.ktmmobile.mcp.common.util.EncryptUtil;
import com.ktmmobile.mcp.common.util.SessionUtils;
import org.apache.commons.httpclient.NameValuePair;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.cert.X509Certificate;

@Service
public class NaverCertifySvcImpl implements NaverCertifySvc {

    private static Logger logger = LoggerFactory.getLogger(NaverCertifySvcImpl.class);

    @Autowired
    private NaverCertfyDao naverCertfyDao;

    @Value("${inf.url}")
    private String infUrl;

    @Value("${NAVER_CLIENT_ID}")
    private String naverClientId;

    @Value("${NAVER_CLIENT_SECRET}")
    private String naverClientSecret;

    @Value("${NAVER_PC_CALLBACK_URL}")
    private String naverPcCallbackUrl;

    @Value("${NAVER_MOBILE_CALLBACK_URL}")
    private String naverMobileCallbackUrl;

    @Value("${NAVER_PC_FAIL_CALLBACK_URL}")
    private String naverPcFailCallbackUrl;

    @Value("${NAVER_MOBILE_FAIL_CALLBACK_URL}")
    private String naverMobileFailCallbackUrl;

    @Value("${SERVER_NAME}")
    private String serverName;

    @Value("${ext.url}")
    private String extUrl;


    @Override
    public String naverAuthSession(NaverDto naverDto) {

        String result = "";
        CommonHttpClient client = new CommonHttpClient(extUrl+"/authSession.do");
        String accessToken = naverDto.getAccessToken();
        // String callCenterNo = "1988-5000";
        String callCenterNo = "1899-5000"; // 고객센터 번호
        String callbackPageUrl = "";
        String failurePageUrl = "";
        String remoteAddress = naverDto.getRemoteAddress();

        String platform = naverDto.getPlatform();
        if("PC".equals(platform)){
            callbackPageUrl = SessionUtils.getServerInfo()+ naverPcCallbackUrl;
            failurePageUrl = SessionUtils.getServerInfo()+naverPcFailCallbackUrl;
        } else {
            callbackPageUrl = SessionUtils.getServerInfo()+ naverMobileCallbackUrl;
            failurePageUrl = SessionUtils.getServerInfo()+naverMobileFailCallbackUrl;
        }

        if("".equals(remoteAddress)){
            InetAddress local;
            try{
                local = InetAddress.getLocalHost();
                remoteAddress = local.getHostAddress();
            }catch(Exception e){
                logger.error("remoteAddress Exception = {}", e.getMessage());
            }
        }

        NameValuePair[] data = {
                new NameValuePair("accessToken",accessToken),
                new NameValuePair("callbackPageUrl",callbackPageUrl),
                new NameValuePair("callCenterNo",callCenterNo),
                new NameValuePair("remoteAddress",remoteAddress),
                new NameValuePair("failurePageUrl",failurePageUrl)
        };
        try {
            result = client.postUtf8(data);
        } catch (Exception e) {
            logger.error("naverAuthSession Exception = {}", e.getMessage());
        }
        return result;

    }


    @Override
    public String naverGetResult(NaverDto naverDto) {

        String result = "";

        CommonHttpClient client = new CommonHttpClient(extUrl+"/naverGetResult.do");
        String txId = naverDto.getTxId();
        String accessToken = naverDto.getAccessToken();
        String clientId = naverClientId;
        String clientSecret = naverClientSecret;
        NameValuePair[] data = {
                new NameValuePair("txId",txId),
                new NameValuePair("accessToken",accessToken),
                new NameValuePair("clientId",clientId),
                new NameValuePair("clientSecret",clientSecret)
        };
        try {
           // result = client.postUtf8(data);
            result = client.post(data, "UTF-8");
        } catch(SocketTimeoutException e) {
            logger.error("naverGetResult Exception = {}", e.getMessage());
        } catch (Exception e) {
            logger.error("naverGetResult Exception = {}", e.getMessage());
        }

        return result;

    }

    @Override
    public boolean updateMcpAuthData(NaverDto NaverDto){
        return naverCertfyDao.updateMcpAuthData(NaverDto);
    }

    @Override
    public NaverDto getMcpAuthData(NaverDto NaverDto){
        return naverCertfyDao.getMcpAuthData(NaverDto);
    }

    @Override
    public String naverDeleteAccessToken(NaverDto naverDto) {

        String result = "";
        CommonHttpClient client = new CommonHttpClient(extUrl+"/deleteAccessToken.do");
        String accessToken = naverDto.getAccessToken();
        String grantType = "delete";
        String clientId = naverClientId;
        String clientSecret = naverClientSecret;
        String serviceProvider = "NAVER";

        NameValuePair[] data = {
                new NameValuePair("accessToken",accessToken),
                new NameValuePair("grantType",grantType),
                new NameValuePair("clientId",clientId),
                new NameValuePair("clientSecret",clientSecret),
                new NameValuePair("serviceProvider",serviceProvider)
        };
        try {
            result = client.postUtf8(data);
        } catch (Exception e) {
            logger.error("naverDeleteAccessToken Exception = {}", e.getMessage());
        }
        return result;

    }


    @Override
    public String naverLoginCallBack(NaverDto naverDto) {

        String result = "";

        if (!"LOCAL".equals(serverName)) {
            CommonHttpClient client = new CommonHttpClient(extUrl+"/naverLoginCallBack.do");
            String accessToken = naverDto.getAccessToken();
            NameValuePair[] data = {
                    new NameValuePair("accessToken",accessToken),
            };
            try {
                // result = client.postUtf8(data);
                result = client.post(data, "UTF-8");
            } catch(SocketTimeoutException e) {
                logger.error("naverGetResult Exception = {}", e.getMessage());
            } catch (Exception e) {
                logger.error("naverGetResult Exception = {}", e.getMessage());
            }
        } else {
            String accessToken = naverDto.getAccessToken();
            String urlStr = "https://openapi.naver.com/v1/nid/me";

            JsonParser jsonParser = new JsonParser();
            JSONObject jo= new JSONObject();
            HttpsURLConnection conn = null;
            BufferedReader bufferedReader = null;

            try {
                TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs,
                                                   String authType) {
                        //checkClientTrusted
                    }

                    public void checkServerTrusted(X509Certificate[] certs,
                                                   String authType) {
                        //checkServerTrusted
                    }
                } };

                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

                URL url = new URL(urlStr);
                conn = (HttpsURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-type", "application/json;charset=utf-8");
                conn.setRequestProperty("Authorization", "Bearer " + accessToken);
                conn.setDoOutput(true);

                int responseCode = conn.getResponseCode();
                logger.error("네이버 본인인증 결과 확인 responseCode2222: " + responseCode);

                bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuffer stringBuffer = new StringBuffer();
                String inputLine = "";

                while ((inputLine = bufferedReader.readLine()) != null)  {
                    stringBuffer.append(inputLine);
                }
                bufferedReader.close();
                result = stringBuffer.toString();


                JsonObject jsonObject = (JsonObject) jsonParser.parse(result);
                String naverResultcode = jsonObject.get("resultcode").toString().replaceAll("\"", "");

                JsonObject responseObject = (JsonObject) jsonObject.get("response");
                String naverUniqueNo = responseObject.get("id").toString().replaceAll("\"", "");
                String snsGender = responseObject.get("gender").toString().replaceAll("\"", "");
                String snsEmail = responseObject.get("email").toString().replaceAll("\"", "");



                logger.error("naverUniqueNo: " + naverUniqueNo);


                // ace256 인코딩
                naverUniqueNo= EncryptUtil.ace256Enc(naverUniqueNo);
                snsGender= EncryptUtil.ace256Enc(snsGender);
                snsEmail= EncryptUtil.ace256Enc(snsEmail);

                jo.put("resultcode", naverResultcode);
                jo.put("id", naverUniqueNo);
                jo.put("gender", snsGender);
                jo.put("email", snsEmail);
                result= jo.toString();

            } catch (Exception e) {
                //e.printStackTrace();
                logger.error("naverGetResult Exception:"+e.getMessage());
            } finally{
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        logger.error("naverGetResult Exception:"+e.getMessage());
                    }
                }

                conn.disconnect();
            }
        }


        return result;

    }

}
