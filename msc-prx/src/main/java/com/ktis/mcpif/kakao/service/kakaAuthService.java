package com.ktis.mcpif.kakao.service;


import com.ktis.mcpif.common.EncryptUtil;
import com.ktis.mcpif.kakao.vo.KakaoDto;
import egovframework.rte.fdl.property.EgovPropertyService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.UUID;

@Service("kakaAuthService")
public class kakaAuthService {

    final private Logger logger = Logger.getLogger(getClass());

    @Resource(name = "propertiesService")
    private EgovPropertyService propertiesService;

    public String cellKakaoCertify(KakaoDto kakaoDto) throws NoSuchAlgorithmException, KeyManagementException, IOException, JSONException {

        String targetUrl = this.propertiesService.getString("kakao.url");
        String enCodeKey = this.propertiesService.getString("kakao.sectKey");
        String enCodeIv = this.propertiesService.getString("kakao.iv");

        kakaoDto.setPhone_No(EncryptUtil.ace256NoPaddingEnc(enCodeKey, enCodeIv, kakaoDto.getPhone_No()));
        kakaoDto.setUserName(EncryptUtil.ace256NoPaddingEnc(enCodeKey, enCodeIv, kakaoDto.getUserName()));
        kakaoDto.setBirthday(EncryptUtil.ace256NoPaddingEnc(enCodeKey, enCodeIv, kakaoDto.getBirthday()));

        // certify
        String url = targetUrl + "/sign/v2/iv/prepare/" + kakaoDto.getCertifyType();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phone_no", kakaoDto.getPhone_No());
            jsonObject.put("name", kakaoDto.getUserName());
            jsonObject.put("birthday", kakaoDto.getBirthday());
        } catch (JSONException e) {
            logger.error("** kakao ready createKey :" + e.getMessage());
        }
        String result = this.callKakaoAuths(url, kakaoDto, jsonObject);
        logger.info("kakao createkey " + result);
        JSONObject resultJson = new JSONObject(result);
        kakaoDto.setTxId(String.valueOf(resultJson.get("tx_id")));

        // request
        String request = "";
        if (StringUtils.isNotEmpty(kakaoDto.getTxId())) {
            JSONObject jsonRequest = new JSONObject();
            String num = UUID.randomUUID().toString().replace("-", "");
            kakaoDto.setRequest_type("본인인증 요청");
            kakaoDto.setData(num);
            url = targetUrl + "/sign/v2/iv/request";
            try {
                jsonRequest.put("tx_id", kakaoDto.getTxId());
                jsonRequest.put("data", kakaoDto.getData());
                jsonRequest.put("request_type", kakaoDto.getRequest_type());
                logger.info("kakao request data" + jsonRequest);
            } catch (JSONException e) {
                logger.error("** kakao request :" + e.getMessage());
            }
            request = this.callKakaoAuths(url, kakaoDto, jsonRequest);
            logger.info("kakao request " + request);
        }
        return request;
    }

    public String selectKakaVerify(KakaoDto kakaoDto) throws NoSuchAlgorithmException, KeyManagementException, IOException, JSONException {

        String targetUrl = this.propertiesService.getString("kakao.url");
        String enCodeKey = this.propertiesService.getString("kakao.sectKey");
        String enCodeIv = this.propertiesService.getString("kakao.iv");
        String resultStr = "";
        // status
        String urlStr = targetUrl+"/sign/v2/iv/status?tx_id=" +kakaoDto.getTxId();
        String statuData = this.callKakaoAuthsGet(urlStr, kakaoDto, null);
        logger.info("kakao status " + statuData);
        JSONObject statuJson = new JSONObject(statuData);
        kakaoDto.setStatus(statuJson.getString("status"));
        resultStr += statuData;
        logger.info("kakao status resultStr" + resultStr);

        // verify
        if (StringUtils.isNotEmpty(kakaoDto.getStatus()) && "COMPLETED".equals(kakaoDto.getStatus())) {
            kakaoDto.setTxId(statuJson.getString("tx_id"));
            kakaoDto.setCreatedAt(statuJson.getString("created_at"));
            kakaoDto.setExpiredAt(statuJson.getString("expired_at"));

            logger.info("kakao verify status " + statuJson.getString("status"));
//            urlStr = targetUrl + "/sign/v2/iv/verify";
            urlStr = targetUrl+"/sign/v2/iv/verify?tx_id=" +kakaoDto.getTxId();
            resultStr = this.callKakaoAuthsGet(urlStr, kakaoDto, null);
            logger.info("kakao verify resultStr" + resultStr);
            JSONObject verifyJson = new JSONObject(resultStr);
            JSONObject statusJson = new JSONObject(statuData);
            verifyJson.put("created_at", statusJson.isNull("created_at") ? null : statusJson.getString("created_at"));
            verifyJson.put("tx_id", statusJson.isNull("tx_id") ? null : statusJson.getString("tx_id"));
            verifyJson.put("viewed_at", statusJson.isNull("viewed_at") ? null : statusJson.getString("viewed_at"));
            verifyJson.put("completed_at", statusJson.isNull("completed_at") ? null : statusJson.getString("completed_at"));
            verifyJson.put("expired_at", statusJson.isNull("expired_at") ? null : statusJson.getString("expired_at"));
            resultStr = verifyJson.toString();
            logger.info("kakao verify resultStr End : " + resultStr);
        }
        return resultStr;
    }

    private String callKakaoAuths(String urlStr, KakaoDto kakaoDto, JSONObject JsonObj) throws NoSuchAlgorithmException, KeyManagementException, IOException {
        String targetUrl = this.propertiesService.getString("kakao.url");
        String enCodeKey = this.propertiesService.getString("kakao.sectKey");
        String enCodeIv = this.propertiesService.getString("kakao.iv");
        String apiKey = this.propertiesService.getString("kakao.apiKey");

        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(X509Certificate[] certs,String authType) {}
            public void checkServerTrusted(X509Certificate[] certs,String authType) {}
        }};

        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        URL url = new URL(urlStr);
//        URL url = new URL("https://test-apigates-pub.dozn.co.kr/sign/v2/iv/prepare/K1110");
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "KakaoAK " + apiKey);
        conn.setRequestProperty("Content-type", "application/json; charset=utf-8");
        conn.setDoOutput(true);

        DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());
        String Data = JsonObj.toString();
        logger.info("kakao ready jsonData :" + Data);
        logger.info("kakao ready outputStream :" + outputStream);
        outputStream.write(Data.getBytes("UTF-8"));
        outputStream.writeBytes(Data);
        outputStream.flush();
        outputStream.close();

        logger.info("kakao confirmWhether : " + conn.getResponseCode());
        logger.info("kakao confirmWhether2 : " + conn);

        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine = "";
        while ((inputLine = bufferedReader.readLine()) != null) {
            stringBuffer.append(inputLine);
        }

        String result = stringBuffer.toString();
        conn.disconnect();
        logger.info("kakao ready ==:> " + result);
        bufferedReader.close();

        return result;
    }

    public String callKakaoAuthsGet(String urlStr, KakaoDto kakaoDto, JSONObject JsonObj) throws NoSuchAlgorithmException, KeyManagementException, IOException {

        String apiKey = this.propertiesService.getString("kakao.apiKey");

        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(X509Certificate[] certs,String authType) {}
            public void checkServerTrusted(X509Certificate[] certs,String authType) {}
        }};

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());

        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        URL url = new URL(urlStr);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "KakaoAK " + apiKey);

        logger.info("kakao confirmWhether : " + conn.getResponseCode());
        logger.info("kakao confirmWhether2 : " + conn);

        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine = "";
        while ((inputLine = bufferedReader.readLine()) != null)  {
            stringBuffer.append(inputLine);
        }
        bufferedReader.close();
        String result = stringBuffer.toString();
        conn.disconnect();

        return result;
    }
}
