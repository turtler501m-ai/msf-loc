package com.ktmmobile.mcp.document.receive.controller;

import com.ktmmobile.mcp.document.receive.service.DocumentReceiveService;
import com.rosis.esonic.e2ee.eSonicE2EE;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/document/upload")
public class DocumentUploadController {
    private static final Logger log = LoggerFactory.getLogger(DocumentUploadController.class);
    private final DocumentReceiveService documentReceiveService;

    @Value("${scan.path.urlTemp}")
    private String tempPath;

    @Value("${scan.V25.interface.url}")
    private String scanInterfaceUrl;

    public DocumentUploadController(DocumentReceiveService documentReceiveService) {
        this.documentReceiveService = documentReceiveService;
    }

    @PostMapping(value = "/init.do", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Map<String, Object>> init(@RequestBody Map<String, Object> param, HttpSession session) {
        try{
            String clientPubKey = param.get("clientPubKey").toString(); // 클라이언트 ephemeral 공개키(raw 65B, base64url)
            String clientNonce = param.get("clientNonce").toString(); // 클라이언트 nonce(권장 16B, base64url)
            String clientInfo = param.get("clientInfo").toString(); //추가 인증용 (선택사항)
            if(!clientInfo.equals("ktm")){
                return ResponseEntity.status(400).cacheControl(CacheControl.noStore()).build(); // 상세 실패 원인 응답X (보안취약점)
            }

            Map<String, Object> res = eSonicE2EE.init(clientPubKey, clientNonce, session);

            return ResponseEntity.ok().cacheControl(CacheControl.noStore()).body(res);
        } catch (Exception e) {
            log.error("init failed", e);
            return ResponseEntity.status(400).cacheControl(CacheControl.noStore()).build(); // 상세 실패 원인 응답X (보안취약점)
        }
    }


    @PostMapping(value = "/urlInsert.do", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> urlInsert(@RequestBody Map<String, Object> encParam, HttpSession session, @RequestHeader(value = "Content-Length", required = false) Long contentLength) {
        try {
            log.error("urlInsert start.");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
            String endTime = LocalDateTime.now().format(formatter);
            // 0) 입력 검증
            final String handshakeId = str(encParam.get("handshakeId"));
            final String reqId       = str(encParam.get("reqId"));
            final String iv          = str(encParam.get("iv"));
            final String ct          = str(encParam.get("ct"));

            if (handshakeId.isEmpty() || reqId.isEmpty() || iv.isEmpty() || ct.isEmpty()) {
                return ResponseEntity.badRequest().body(err("40001", "필수 파라미터 누락"));
            }

            // 1) 복호화
            final String plainJson;
            try {
                plainJson = eSonicE2EE.dec(handshakeId, reqId, iv, ct, session);
            } catch (IllegalStateException ex) {
                log.warn("E2EE_SESSION_EXPIRED");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("error", "E2EE_SESSION_EXPIRED"));
            } catch (Exception ex) {
                log.error("E2EE_DEC_ERROR", ex);
                return ResponseEntity.badRequest().body(err("40002", "복호화 실패"));
            }

            // 2) 평문 JSON 파싱
            JSONObject json = new JSONObject(plainJson);
            String method   = json.optString("method", "");
            if (!"urlInsertEX".equalsIgnoreCase(method)) {
                return ResponseEntity.badRequest().body(err("40003", "invalid method: " + method));
            }
            String docRcvId = json.optString("docRcvId", "");
            JSONArray items = json.optJSONArray("items");
            if (items == null || items.length() == 0) {
                return ResponseEntity.badRequest().body(err("40004", "items is empty"));
            }

            String startTime = json.optString("startTime", "");

            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("docRcvId", docRcvId);
            paramMap.put("startTime", startTime);
            paramMap.put("endTime", endTime);
            paramMap.put("contentLength", contentLength);
            documentReceiveService.insertLogRequest(paramMap);

            // 3) 임시 디렉토리
            File tmpDir = new File(tempPath);
            if (!tmpDir.isDirectory() && !tmpDir.mkdirs()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err("50010", "임시 디렉토리 생성 실패"));
            }

            // 4) base64 → 파일 저장 및 파일 메타 수집
            JSONArray filesArray = new JSONArray();
            for (int i = 0; i < items.length(); i++) {
                JSONObject it     = items.getJSONObject(i);
                String fileName   = it.optString("fileName", "");
                String mimeType   = it.optString("mimeType", "application/octet-stream");
                long   sizeIn     = it.optLong("size", 0L);
                String dataBase64 = it.optString("dataBase64", "");

                if (dataBase64 == null || dataBase64.isEmpty()) {
                    log.warn("[urlInsert] item[{}] has no dataBase64. skip", i);
                    continue;
                }

                String ext  = getExtOrGuess(fileName, mimeType);
                String uuid = UUID.randomUUID().toString().replace("-", "");
                String outName = uuid + (ext.isEmpty() ? "" : "." + ext);
                Path outPath = Paths.get(tmpDir.getAbsolutePath(), outName);

                byte[] raw;
                try {
                    raw = Base64.getDecoder().decode(dataBase64.getBytes(StandardCharsets.US_ASCII));
                } catch (IllegalArgumentException e) {
                    log.warn("[urlInsert] item[{}] base64 decode fail", i, e);
                    continue;
                }

                Files.write(outPath, raw, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

                JSONObject f = new JSONObject();
                f.put("uuid", uuid);
                f.put("fileName", outName);
                f.put("absPath", outPath.toAbsolutePath().toString()); // B가 바로 사용할 절대경로
                f.put("ext", ext);
                f.put("size", sizeIn > 0 ? sizeIn : raw.length);
                f.put("mimeType", mimeType);

                // DB 로우용 메타 (권장 항목들은 포함해줌)
                f.put("itemId",   it.optString("docId", ""));
                f.put("itemName", it.optString("docName", ""));
                f.put("origFileName", fileName);

                filesArray.put(f);
            }

            if (filesArray.length() == 0) {
                return ResponseEntity.badRequest().body(err("40005", "저장할 유효 파일이 없습니다."));
            }

            // 5) B로 JSON(경로 기반) 전송
            JSONObject payload = new JSONObject();
            payload.put("method", "urlInsertPlain");
            payload.put("docRcvId", docRcvId);
            payload.put("tempDir", tmpDir.getAbsolutePath()); // 보조 정보
            payload.put("files", filesArray);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> req = new HttpEntity<>(payload.toString(), headers);

            String url = scanInterfaceUrl + "?method=urlInsert3";
            RestTemplate restTemplate = createRestTemplate();
            ResponseEntity<String> bResp = restTemplate.postForEntity(url, req, String.class);
            HttpStatus sc = bResp.getStatusCode();
            String body   = (bResp.getBody() == null) ? "{}" : bResp.getBody();
            log.error("status : {}, body : {}", sc, body);

            // 응답 그대로 릴레이
            return ResponseEntity
                    .status(sc)
                    .header(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8")
                    .body(body);


        } catch (Exception e) {
            log.error("urlInsert forward error", e);
            return ResponseEntity.ok(err("50000", "이미지 처리중 오류 "));
        } finally {
            log.error("urlInsert end.");
        }
        // 주의: 여기서는 임시 파일을 즉시 삭제하지 않음. 이미지 서버가 관리
    }

    @PostMapping("/getRequestInfo.do")
    @ResponseBody
    public Map<String, String> getRequestInfo() {
        Map<String, String> map = new HashMap<>();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
            String startTime = LocalDateTime.now().format(formatter);
            map.put("startTime", startTime);
        } catch (Exception e) {
            log.error("getRequestInfo error : {}", e.getMessage());
        }
        return map;
    }

    // ===== 유틸 =====

    private static String str(Object o) { return o == null ? "" : String.valueOf(o); }
    private static Map<String, Object> err(String code, String msg) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("code", code);
        m.put("message", msg);
        return m;
    }

    private static JSONObject safeJson(String s) {
        try { return new JSONObject(s); }
        catch (Exception ignore) {
            log.error("safeJson error : {}", ignore.getMessage());
            return new JSONObject();
        }
    }

    private static String getExtOrGuess(String fileName, String mimeType) {
        String ext = getExt(fileName);
        if (!ext.isEmpty()) return ext;
        return guessFromMime(mimeType);
    }

    private static String getExt(String fileName) {
        if (fileName == null) return "";
        int i = fileName.lastIndexOf('.');
        return (i >= 0 && i < fileName.length() - 1) ? fileName.substring(i + 1) : "";
    }

    private static String guessFromMime(String mime) {
        if (mime == null) return "";
        String m = mime.toLowerCase(Locale.ROOT).trim();
        if (m.equals("image/jpeg")) return "jpg";
        if (m.equals("image/png")) return "png";
        if (m.equals("image/tiff") || m.equals("image/tif")) return "tif";
        if (m.equals("application/pdf")) return "pdf";
        return "";
    }

    private static RestTemplate createRestTemplate() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .setSocketTimeout(60000)
                .build();
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setMaxConnTotal(200)
                .setMaxConnPerRoute(100)
                .build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(factory);
    }
}