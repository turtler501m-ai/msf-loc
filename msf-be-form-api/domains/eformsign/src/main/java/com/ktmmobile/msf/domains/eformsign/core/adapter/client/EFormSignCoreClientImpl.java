package com.ktmmobile.msf.domains.eformsign.core.adapter.client;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.HexFormat;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;
import com.ktmmobile.msf.domains.eformsign.core.adapter.client.dto.EFormSignCoreApiTokenHttpResponse;
import com.ktmmobile.msf.domains.eformsign.core.adapter.client.dto.EFormSignCoreDocumentCancelRequest;
import com.ktmmobile.msf.domains.eformsign.core.adapter.client.httpclient.EFormSignCoreHttpClient;
import com.ktmmobile.msf.domains.eformsign.core.application.dto.EFormSignCoreApiTokenRequest;
import com.ktmmobile.msf.domains.eformsign.core.application.dto.EFormSignCoreApiTokenResponse;
import com.ktmmobile.msf.domains.eformsign.core.application.port.out.EFormSignCoreClient;
import com.ktmmobile.msf.domains.externalclient.common.code.ClientConst;
import com.ktmmobile.msf.domains.externalclient.common.property.InternalServiceProperties;
import com.ktmmobile.msf.domains.externalclient.common.property.ServiceProperties;

@Slf4j
@Component
@RequiredArgsConstructor
public class EFormSignCoreClientImpl implements EFormSignCoreClient {

    private final EFormSignCoreHttpClient eFormSignCoreHttpClient;
    private final InternalServiceProperties serviceProperties;

    @Override
    public EFormSignCoreApiTokenResponse issueApiToken() {
        try {
            // 1. execution_time 생성
            long executionTime = Instant.now().toEpochMilli();
            String executionTimeStr = String.valueOf(executionTime);

            // 2. PrivateKey 생성
            ServiceProperties properties = serviceProperties.service(ClientConst.SERVICE_NAME_EFORMSIGN);
            String privateKeyHex = properties.property("privateKey");
            byte[] keyBytes = HexFormat.of().parseHex(privateKeyHex);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

            // 3. eformsign_signature 생성
            Signature ecdsa = Signature.getInstance("SHA256withECDSA");
            ecdsa.initSign(privateKey);
            ecdsa.update(executionTimeStr.getBytes(StandardCharsets.UTF_8));
            String eformsignSignature = HexFormat.of().formatHex(ecdsa.sign());

            // 4. 요청 DTO 생성
            String memberId = properties.property("memberId");
            EFormSignCoreApiTokenRequest request = new EFormSignCoreApiTokenRequest(executionTime, memberId);

            // 5. Bearer 토큰 생성
            String apiKey = properties.property("apiKey");
            String encodedApiKey = Base64.getEncoder().encodeToString(apiKey.getBytes(StandardCharsets.UTF_8));
            String authorization = "Bearer " + encodedApiKey;

            // 6. 호출
            EFormSignCoreApiTokenHttpResponse eformApiToken = eFormSignCoreHttpClient.issueApiToken(eformsignSignature, authorization, request);
            return eformApiToken.toResponse(memberId);
        } catch (Exception e) {
            throw new SimpleDomainException("eFormSign 토큰 발급 실패", e);
        }
    }

    @Override
    public void cancelDocument(List<String> documentIds) {
        EFormSignCoreApiTokenResponse token = issueApiToken();
        eFormSignCoreHttpClient.cancelDocument(
            "Bearer " + token.accessToken(),
            EFormSignCoreDocumentCancelRequest.of(documentIds));
    }
}
