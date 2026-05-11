package com.ktmmobile.msf.domains.eformsign.adapter.client;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.HexFormat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.client.support.properties.InternalServiceProperties;
import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;
import com.ktmmobile.msf.domains.eformsign.adapter.client.httpclient.FormHttpClient;
import com.ktmmobile.msf.domains.eformsign.application.dto.EformApiTokenRequest;
import com.ktmmobile.msf.domains.eformsign.application.dto.EformApiTokenResponse;
import com.ktmmobile.msf.domains.eformsign.application.port.out.EformClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class FormClientImpl implements EformClient {

    private final FormHttpClient formHttpClient;
    private final InternalServiceProperties properties;


    @Override
    public EformApiTokenResponse getEformApiToken() {

        try {
            // ===============================
            // 1. execution_time 생성
            // ===============================
            long executionTime = Instant.now().toEpochMilli();
            String executionTimeStr = String.valueOf(executionTime);


            // ===============================
            // 2. PrivateKey 생성 (EC 방식)
            // ===============================
            String privateKeyHex = properties.service("eformsign").property("privateKey");

            byte[] keyBytes = HexFormat.of().parseHex(privateKeyHex);

            PKCS8EncodedKeySpec psks8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFact = KeyFactory.getInstance("EC");
            PrivateKey privateKey = keyFact.generatePrivate(psks8KeySpec);

            // ===============================
            // 3. eformsign_signature 생성
            // ===============================
            Signature signature = Signature.getInstance("SHA256withECDSA");

            signature.initSign(privateKey);
            signature.update(
                executionTimeStr.getBytes(StandardCharsets.UTF_8)
            );

            byte[] signedBytes = signature.sign();

            String eformsignSignature =
                new BigInteger(1, signedBytes).toString(16);

            // ===============================
            // 4. Authorization 생성
            // ===============================
            String apiKey = properties.service("eformsign").property("apiKey");

            String encodedApiKey = Base64.getEncoder()
                .encodeToString(
                    apiKey.getBytes(StandardCharsets.UTF_8)
                );

            String authorization = "Bearer " + encodedApiKey;

            // ===============================
            // 5. Client 요청 DTO 생성
            // ===============================
            String memberId = properties.service("eformsign").property("memberId");

            EformApiTokenRequest clientRequest =
                new EformApiTokenRequest(
                    executionTime,
                    memberId
                );

            // ===============================
            // 6. 호출
            // ===============================
            return formHttpClient.getEformApiToken(eformsignSignature, authorization, clientRequest);
        } catch (Exception e) {
            throw new SimpleDomainException("eformsign token 발급 실패", e);
        }
    }
}
