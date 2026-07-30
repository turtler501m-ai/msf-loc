package com.ktmmobile.msf.domains.koiidcard.support.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;
import com.ktmmobile.msf.domains.koiidcard.application.dto.mobileId.MobileIdCardHttpResponse;
import com.ktmmobile.msf.domains.koiidcard.application.dto.mobileId.MobileIdCardQrResponse;

@Component
@RequiredArgsConstructor
public class MobileIdUtils {

    private final ObjectMapper objectMapper;

    public MobileIdCardQrResponse decode(String base64Data) {
        try {
            // 1. 외부 API의 response.data() (Base64)를 1차 디코딩합니다.
            String firstDecodedJson = new String(
                Base64.getMimeDecoder().decode(base64Data),
                StandardCharsets.UTF_8
            );
            JsonNode root = objectMapper.readTree(firstDecodedJson);

            // 2. 1차 디코딩 결과에서 ifType과 m200Base64를 추출하고, 변수로 따로 보관합니다.
            String idType = root.path("ifType").asText("");
            String m200Base64 = root.path("m200Base64").asText(""); // QR 전용 Base64 데이터 변수 선언

            // 3. 따로 뽑아둔 m200Base64 문자열을 2차 디코딩(복호화)합니다.
            String secondDecodedJson = new String(
                Base64.getMimeDecoder().decode(m200Base64),
                StandardCharsets.UTF_8
            );
            JsonNode detailRoot = objectMapper.readTree(secondDecodedJson);

            // 4. 2차 디코딩한 상세 필드값들과, 따로 담아둔 m200Base64(qrData)를 조합하여 최종 반환합니다.
            return new MobileIdCardQrResponse(
                detailRoot.path("type").asText(""),      // 가이드의 type (실제 값: "bwp")
                detailRoot.path("version").asText(""),   // 가이드의 version (실제 값: "1.1.0")
                detailRoot.path("cmd").asText(""),       // 가이드의 cmd (실제 값: "200")
                detailRoot.path("trxcode").asText(""),   // 가이드의 trxcode
                idType,                                                          // 1차 디코딩에서 꺼내둔 idType (실제 값: "MPM")
                detailRoot.path("mode").asText(""),      // 가이드의 mode (실제 값: "diyrech")
                m200Base64                                                       // 변수로 따로 빼둔 QR 전용 base64 데이터
            );
        } catch (Exception e) {
            throw new SimpleDomainException("QR 데이터 및 m200Base64 복호화에 실패했습니다.", e);
        }
    }

    public MobileIdCardHttpResponse decodeVerificationResult(String base64Data) {
        if (base64Data == null || base64Data.isBlank()) {
            return null;
        }

        try {
            byte[] decodedBytes = Base64.getDecoder().decode(base64Data);
            String decodedJson = new String(decodedBytes, StandardCharsets.UTF_8);

            return objectMapper.readValue(decodedJson, MobileIdCardHttpResponse.class);
        } catch (IllegalArgumentException e) {
            throw new SimpleDomainException("모바일 신분증 결과 데이터의 Base64 형식이 올바르지 않습니다.", e);
        }
    }
}