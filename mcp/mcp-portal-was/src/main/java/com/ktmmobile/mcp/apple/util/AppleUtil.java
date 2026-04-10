package com.ktmmobile.mcp.apple.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktmmobile.mcp.apple.dto.AppleKey;
import com.ktmmobile.mcp.apple.dto.AppleKeys;
import com.ktmmobile.mcp.apple.dto.ApplePayload;
import com.ktmmobile.mcp.apple.dto.AppleTokenResponse;
import com.ktmmobile.mcp.common.util.CommonHttpClient;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
//import com.nimbusds.jwt.ReadOnlyJWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import net.sf.json.JSONObject;

@Component
public class AppleUtil {

    private static final Logger logger = LoggerFactory.getLogger(AppleUtil.class);

    @Value("${inf.url}")
    private String infUrl;

    @Value("${apple.iss}")
    private String ISS;

    @Value("${apple.client.id}")
    private String AUD;

    @Value("${apple.team.id}")
    private String TEAM_ID;

    @Value("${apple.key.id}")
    private String KEY_ID;

    @Value("${apple.cert.file}")
    private String KEY_PATH;

    @Value("${apple.auth.token.url}")
    private String AUTH_TOKEN_URL;

    @Value("${apple.publickey.url}")
    private String APPLE_PUBLIC_KEYS_URL;

    @Value("${SERVER_NAME}")
    private String serverName;

    @Value("${ext.url}")
    private String extUrl;

//	@Value("${APPLE.WEBSITE.URL}")
//	private String APPLE_WEBSITE_URL;

    /**
     * User가 Sign in with Apple 요청(https://appleid.apple.com/auth/authorize)으로 전달받은 id_token을 이용한 최초 검증
     * Apple Document URL ‣ https://developer.apple.com/documentation/sign_in_with_apple/sign_in_with_apple_rest_api/verifying_a_user
     *
     * @param id_token
     * @return boolean
     */
    public boolean verifyIdentityToken(String id_token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(id_token);
            JWTClaimsSet payload = (JWTClaimsSet) signedJWT.getJWTClaimsSet();

            // EXP
            Date currentTime = new Date(System.currentTimeMillis());
            if (!currentTime.before(payload.getExpirationTime())) {
                return false;
            }

            if (!ISS.equals(payload.getIssuer()) || !AUD.equals(payload.getAudience().get(0))) {
                return false;
            }

            // RSA
            if (verifyPublicKey(signedJWT)) {
                return true;
            }
        } catch (ParseException e) {
            logger.error("[ERR]verifyPublicKey():e:"+e.getMessage());
        }

        return false;
    }

    /**
     * Apple Server에서 공개 키를 받아서 서명 확인
     *
     * @param signedJWT
     * @return
     */
    private boolean verifyPublicKey(SignedJWT signedJWT) {
        try {
            String publicKeys = doGet(APPLE_PUBLIC_KEYS_URL);
            ObjectMapper objectMapper = new ObjectMapper();
            AppleKeys keys = objectMapper.readValue(publicKeys, AppleKeys.class);
            for (AppleKey key : keys.getKeys()) {
                RSAKey rsaKey = (RSAKey) JWK.parse(objectMapper.writeValueAsString(key));
                RSAPublicKey publicKey = rsaKey.toRSAPublicKey();
                JWSVerifier verifier = new RSASSAVerifier(publicKey);

                if (signedJWT.verify(verifier)) {
                    return true;
                }
            }
        } catch(ParseException e) {
            logger.error("[ERR]verifyPublicKey():e:"+e.getMessage());
        } catch (Exception e) {
            logger.error("[ERR]verifyPublicKey():e:"+e.getMessage());
        }

        return false;
    }

    /**
     * client_secret 생성
     * Apple Document URL ‣ https://developer.apple.com/documentation/sign_in_with_apple/generate_and_validate_tokens
     *
     * @return client_secret(jwt)
     */
    public String createClientSecret() {

        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.ES256).keyID(KEY_ID).build();

        JWTClaimsSet claimsSet = new JWTClaimsSet();
        Date now = new Date();

        claimsSet.setIssuer(TEAM_ID);
        claimsSet.setIssueTime(now);
        claimsSet.setExpirationTime(new Date(now.getTime() + 3600000));
        claimsSet.setAudience(ISS);
        claimsSet.setSubject(AUD);

        SignedJWT jwt = new SignedJWT(header, claimsSet);

        try {

            final byte[] keyBytes = java.util.Base64.getDecoder().decode(readPrivateKey());
            final KeyFactory keyFactory = KeyFactory.getInstance("EC");
            final PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(keyBytes));

            //JWSSigner signer = new ECDSASigner((java.security.interfaces.ECPrivateKey) privateKey);
            JWSSigner signer = new ECDSASigner((BigInteger) privateKey);
            jwt.sign(signer);

        } catch (JOSEException e) {
            logger.error("[ERR]createClientSecret():JOSEException:"+e.getMessage());
        } catch (InvalidKeySpecException e) {
            logger.error("[ERR]createClientSecret():InvalidKeySpecException:"+e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            logger.error("[ERR]createClientSecret():NoSuchAlgorithmException:"+e.getMessage());
        }

        return jwt.serialize();
    }

    /**
     * 파일에서 private key 획득
     *    cert/kwcreative_signapple_AuthKey_FV4DPXKRPN.p8
     *    cert/kmembers_AuthKey_3MV5BUX7Y4.p8  [법인 변경으로 애플인증키 변경:2021-10-01]
     *
     * @return Private Key
     */
    @SuppressWarnings("unused")
    private String readPrivateKey() {

        Resource resource = new ClassPathResource(KEY_PATH);
        BufferedReader br = null;
        String privateKeyPEM = "";
        try {
            File file = new File(resource.getURI().getPath());
            br = new BufferedReader(new FileReader(file));
            String st;
            StringBuilder strBuilder = new StringBuilder();

            while ((st = br.readLine()) != null){
                strBuilder.append(st);
            }
            privateKeyPEM = strBuilder.toString();
            privateKeyPEM = privateKeyPEM.replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "").replaceAll("\\s", "");

        } catch (FileNotFoundException e) {
            logger.error("[ERR]readPrivateKey():FileNotFoundException:"+e.getMessage());
        } catch (IOException e) {
            logger.error("[ERR]readPrivateKey():IOException:"+e.getMessage());
        } finally {
            if(br != null) try { br.close(); } catch (Exception fe) {logger.error(fe.getMessage());}
        }

        return privateKeyPEM;

    }

    /**
     * 유효한 code 인지 Apple Server에 확인 요청
     * Apple Document URL ‣ https://developer.apple.com/documentation/sign_in_with_apple/generate_and_validate_tokens
     *
     * @return
     */
    public AppleTokenResponse validateAuthorizationGrantCode(String client_secret, String code, String redirect_uri) {

        Map<String, String> tokenRequest = new HashMap<>();

        tokenRequest.put("client_id", AUD);
        tokenRequest.put("client_secret", client_secret);
        tokenRequest.put("code", code);
        tokenRequest.put("grant_type", "authorization_code");
        tokenRequest.put("redirect_uri", redirect_uri);

        return getTokenResponse(tokenRequest);
    }

    /**
     * 유효한 refresh_token 인지 Apple Server에 확인 요청
     * Apple Document URL ‣ https://developer.apple.com/documentation/sign_in_with_apple/generate_and_validate_tokens
     *
     * @param client_secret
     * @param refresh_token
     * @return
     */
    public AppleTokenResponse validateAnExistingRefreshToken(String client_secret, String refresh_token) {

        Map<String, String> tokenRequest = new HashMap<>();

        tokenRequest.put("client_id", AUD);
        tokenRequest.put("client_secret", client_secret);
        tokenRequest.put("grant_type", "refresh_token");
        tokenRequest.put("refresh_token", refresh_token);

        return getTokenResponse(tokenRequest);
    }

    /**
     * POST https://appleid.apple.com/auth/token
     *
     * @param tokenRequest
     * @return
     */
    private AppleTokenResponse getTokenResponse(Map<String, String> tokenRequest) {

        try {
            String response = doPost(AUTH_TOKEN_URL, tokenRequest);
            ObjectMapper objectMapper = new ObjectMapper();
            AppleTokenResponse tokenResponse = objectMapper.readValue(response, AppleTokenResponse.class);

            if (tokenRequest != null) {
                return tokenResponse;
            }
        } catch (JsonProcessingException e) {
            logger.error("[ERR]getTokenResponse():JsonProcessingException:"+e.getMessage());
        } catch (IOException e) {
            logger.error("[ERR]getTokenResponse():IOException:"+e.getMessage());
        }

        return null;
    }

    /**
     * Apple Meta Value
     *
     * @return
     */
//	public Map<String, String> getMetaInfo() {
//
//		Map<String, String> metaInfo = new HashMap<>();
//
//		metaInfo.put("CLIENT_ID", AUD);
//		metaInfo.put("REDIRECT_URI", APPLE_WEBSITE_URL);
//		metaInfo.put("NONCE", "20B20D-0S8-1K8"); // Test value
//
//		return metaInfo;
//	}

    /**
     * id_token을 decode해서 payload 값 가져오기
     *
     * @param id_token
     * @return
     */
    public ApplePayload decodeFromIdToken(String id_token) {

        try {
            SignedJWT signedJWT = SignedJWT.parse(id_token);
            JWTClaimsSet getPayload = (JWTClaimsSet) signedJWT.getJWTClaimsSet();
            ObjectMapper objectMapper = new ObjectMapper();
            //ReadOnlyJWTClaimsSet payload = signedJWT.getJWTClaimsSet();
            //ApplePayload payload = objectMapper.readValue(getPayload.toJSONObject().toJSONString(), ApplePayload.class);

            Map<String, Object> payloadMap = getPayload.toJSONObject();
            JSONObject payloadJson = new JSONObject();
            for( Map.Entry<String, Object> entry : payloadMap.entrySet() ) {
                String key = entry.getKey();
                Object value = entry.getValue();
                payloadJson.put(key, value);
            }

            ApplePayload payload = objectMapper.readValue(payloadJson.toString(), ApplePayload.class);

            if (payload != null) {
                return payload;
            }
        } catch (IOException e) {
            logger.error("[IOException]decodeFromIdToken():e:"+e.getMessage());
        } catch (Exception e) {
            logger.error("[ERR]decodeFromIdToken():e:"+e.getMessage());
        }

        return null;
    }

    public String doGet(String goUrl) {
        String result = null;

        try {
            CommonHttpClient client = new CommonHttpClient(extUrl+"/getApplePublicKeys.do");
            org.apache.commons.httpclient.NameValuePair[] data = {
                    new org.apache.commons.httpclient.NameValuePair("publicKeysUrl", goUrl)
            };
            result = client.postUtf8(data);

        } catch (SocketTimeoutException e) {
            logger.error("SocketTimeoutException.= {}", e.getMessage());
        } catch(Exception e) {
            logger.error("getAccessToken.Exception = {}", e.getMessage());
        }

        return result;
    }

    public String doPost(String goUrl, Map<String, String> param) {

        String result = null;

        try {
            CommonHttpClient client = new CommonHttpClient(extUrl+"/getAppleAuthToken.do");
            org.apache.commons.httpclient.NameValuePair[] data = {
                    new org.apache.commons.httpclient.NameValuePair("appleAuthTokenUrl", goUrl),
                    new org.apache.commons.httpclient.NameValuePair("clientId", AUD),
                    new org.apache.commons.httpclient.NameValuePair("grantType", "refresh_token"),
                    new org.apache.commons.httpclient.NameValuePair("refreshToken",param.get("refresh_token")),
                    new org.apache.commons.httpclient.NameValuePair("clientSecret", param.get("client_secret"))
            };

            result = client.postUtf8(data);

        } catch (SocketTimeoutException e) {
            logger.error("SocketTimeoutException.= {}", e.getMessage());
        } catch(Exception e) {
            logger.error("getAccessToken.Exception = {}", e.getMessage());
        }

        return result;
    }

}