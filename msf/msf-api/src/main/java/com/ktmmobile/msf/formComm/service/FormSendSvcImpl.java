package com.ktmmobile.msf.formComm.service;

import com.ktmmobile.msf.formComm.dto.FormSendReqDto;
import com.ktmmobile.msf.formComm.dto.FormSendResVO;
import com.ktmmobile.msf.formComm.mapper.FormSendMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * 서식지 SCAN 전송 서비스 구현.
 * ASIS: CustRequestScanServiceImpl.prodSendScan() 포팅.
 *
 * 처리 흐름:
 *  1. reqType별 DB 조회 (NC: 명의변경 / CC: 해지)
 *  2. 주민번호 분석 (7번째 자리로 성별 코드 설정)
 *  3. 납부방법/고객유형 데이터 정리
 *  4. XML 파일 생성 (서식지 좌표 DB 조회 후 INPUT_DATA 구성)
 *  5. SCAN 서버 multipart 전송 → SCAN_ID 반환
 *  6. DB update (SCAN_ID 저장)
 *
 * LOCAL(scan.url 미설정) 환경: Mock SCAN_ID 반환.
 */
@Service
public class FormSendSvcImpl implements FormSendSvc {

    private static final Logger log = LoggerFactory.getLogger(FormSendSvcImpl.class);

    private final FormSendMapper formSendMapper;

    /** SCAN 서버 URL. 미설정 시 LOCAL Mock 동작. */
    @Value("${scan.url:}")
    private String scanUrl;

    /** XML 임시 파일 저장 경로 */
    @Value("${scan.app.path:/tmp/msf-scan/}")
    private String scanPath;

    public FormSendSvcImpl(FormSendMapper formSendMapper) {
        this.formSendMapper = formSendMapper;
    }

    @Override
    @Transactional
    public FormSendResVO sendScan(FormSendReqDto req) {
        if (req == null || req.getCustReqSeq() == null) {
            return FormSendResVO.fail("고객요청 시퀀스가 없습니다.");
        }
        String reqType = req.getReqType();
        if (reqType == null || reqType.isEmpty()) {
            return FormSendResVO.fail("요청 유형(reqType)이 없습니다.");
        }

        // ── 1. 서식지 데이터 조회 ──
        Map<String, Object> data;
        try {
            if ("NC".equals(reqType)) {
                data = formSendMapper.getReqNmChgData(req.getCustReqSeq());
            } else if ("CC".equals(reqType)) {
                data = formSendMapper.getCancelConsultData(req.getCustReqSeq());
            } else {
                return FormSendResVO.fail("지원하지 않는 요청 유형입니다: " + reqType);
            }
        } catch (Exception e) {
            log.error("[FormSend] DB 조회 실패: custReqSeq={}, reqType={}", req.getCustReqSeq(), reqType, e);
            return FormSendResVO.fail("서식지 정보 조회에 실패했습니다.");
        }

        if (data == null || data.isEmpty()) {
            return FormSendResVO.fail("서식지 정보를 찾을 수 없습니다. custReqSeq=" + req.getCustReqSeq());
        }

        // ── 2. 주민번호 분석 (성별 코드 설정) ──
        settingSsnData(data);

        // ── 3. 기타 설정 (납부방법/고객유형 정리) ──
        settingData(data);

        // ── 4. LOCAL 환경 Mock 처리 ──
        if (scanUrl == null || scanUrl.isEmpty()) {
            String mockScanId = "MOCK_" + reqType + "_" + req.getCustReqSeq();
            log.info("[FormSend] LOCAL Mock 모드 — scanId={}", mockScanId);
            updateScanId(reqType, mockScanId, req.getCustReqSeq());
            return FormSendResVO.ok(mockScanId);
        }

        // ── 5. XML 생성 ──
        String groupCode = getGroupCode(data);
        List<Map<String, Object>> pointList;
        try {
            pointList = formSendMapper.getAppFormPointList(groupCode);
        } catch (Exception e) {
            log.error("[FormSend] 서식지 좌표 조회 실패: groupCode={}", groupCode, e);
            return FormSendResVO.fail("서식지 좌표 조회에 실패했습니다.");
        }

        String xmlFileName = "cust_request_" + req.getCustReqSeq() + ".xml";
        try {
            String xml = buildXml(req.getCustReqSeq(), nvl(req.getUserId(), "MFORM"), data, pointList);
            saveXml(xml, xmlFileName);
        } catch (Exception e) {
            log.error("[FormSend] XML 생성 실패: custReqSeq={}", req.getCustReqSeq(), e);
            return FormSendResVO.fail("서식지 XML 생성에 실패했습니다.");
        }

        // ── 6. SCAN 서버 전송 ──
        String scanId;
        try {
            scanId = sendXmlToScan(xmlFileName);
        } catch (Exception e) {
            log.error("[FormSend] SCAN 전송 실패: custReqSeq={}", req.getCustReqSeq(), e);
            return FormSendResVO.fail("SCAN 서버 전송에 실패했습니다.");
        } finally {
            deleteFile(xmlFileName);
        }

        if (scanId == null || scanId.isEmpty()) {
            return FormSendResVO.fail("SCAN 서버에서 SCAN_ID를 받지 못했습니다.");
        }

        // ── 7. DB update ──
        updateScanId(reqType, scanId, req.getCustReqSeq());

        log.info("[FormSend] 완료: reqType={}, custReqSeq={}, scanId={}", reqType, req.getCustReqSeq(), scanId);
        return FormSendResVO.ok(scanId);
    }

    // ─────────────────────────────────────────────────────────────
    // 주민번호 분석: 7번째 자리로 성별 코드(M/W) 설정.
    // ASIS: CustRequestScanServiceImpl.settingSsnData()
    // ─────────────────────────────────────────────────────────────
    private void settingSsnData(Map<String, Object> data) {
        parseSsn(data, "CSTMR_NATIVE_RRN", "CSTMR_NATIVE_RRN_M", "CSTMR_NATIVE_RRN_W", data);
        parseSsn(data, "GR_CSTMR_NATIVE_RRN", "GR_CSTMR_NATIVE_RRN_M", "GR_CSTMR_NATIVE_RRN_W", data);
        parseSsn(data, "MINOR_AGENT_RRN", "MINOR_AGENT_M", "MINOR_AGENT_W", data);

        // 생년월일 추출 (신청인 주민번호 기준)
        String custSsn = str(data.get("CSTMR_NATIVE_RRN_ORG"));
        if (custSsn.length() >= 7) {
            int genderDigit = Character.getNumericValue(custSsn.charAt(6));
            int birYear = Integer.parseInt(custSsn.substring(0, 2));
            if (genderDigit == 1 || genderDigit == 2 || genderDigit == 5 || genderDigit == 6) birYear += 1900;
            else if (genderDigit == 3 || genderDigit == 4 || genderDigit == 7 || genderDigit == 8) birYear += 2000;
            else birYear += 1900;
            data.put("CUST_BIRTH_YEAR",  String.valueOf(birYear));
            data.put("CUST_BIRTH_MONTH", custSsn.substring(2, 4));
            data.put("CUST_BIRTH_DAY",   custSsn.substring(4, 6));
        }
    }

    private void parseSsn(Map<String, Object> data, String rrnKey, String mKey, String wKey,
                          Map<String, Object> target) {
        String rrn = str(data.get(rrnKey));
        if (rrn.length() > 7) {
            String gd = String.valueOf(rrn.charAt(6));
            target.put(rrnKey, rrn.substring(0, 6)); // 앞 6자리만
            if ("1".equals(gd) || "3".equals(gd) || "5".equals(gd) || "7".equals(gd)) {
                target.put(mKey, "V");
            } else if ("2".equals(gd) || "4".equals(gd) || "6".equals(gd) || "8".equals(gd)) {
                target.put(wKey, "V");
            }
        }
    }

    // ─────────────────────────────────────────────────────────────
    // 납부방법/고객유형/동의 데이터 정리.
    // ASIS: CustRequestScanServiceImpl.settingData()
    // ─────────────────────────────────────────────────────────────
    private void settingData(Map<String, Object> data) {
        String payType = str(data.get("REQ_PAY_TYPE"));
        if (!payType.isEmpty()) {
            if ("D".equals(payType) || "AA".equals(payType)) {
                data.remove("REQ_CARD_COMPANY");
                data.remove("REQ_CARD_NO");
                data.remove("REQ_CARD_YY");
                data.remove("REQ_CARD_MM");
            } else if ("C".equals(payType)) {
                data.remove("REQ_BANK");
                data.remove("REQ_ACCOUNT_NUMBER");
            }
        }

        String cstmrType = str(data.get("CSTMR_TYPE"));
        if (!cstmrType.isEmpty() && !"FN".equals(cstmrType)) {
            data.remove("CSTMR_FOREIGNER_RRN");
            data.remove("CSTMR_FOREIGNER_NATION");
        }

        // 둘 다 동의하면 전체동의로 통합
        data.put("OTHERS_TRNS_ALL_AGREE", "N");
        if ("Y".equals(str(data.get("OTHERS_TRNS_AGREE"))) && "Y".equals(str(data.get("OTHERS_TRNS_KT_AGREE")))) {
            data.put("OTHERS_TRNS_AGREE", "N");
            data.put("OTHERS_TRNS_KT_AGREE", "N");
            data.put("OTHERS_TRNS_ALL_AGREE", "Y");
        }
    }

    // ─────────────────────────────────────────────────────────────
    // XML 생성.
    // ASIS: CustRequestScanServiceImpl.createXml() + InitializaionXml()
    // ─────────────────────────────────────────────────────────────
    private String buildXml(Long custReqSeq, String userId,
                            Map<String, Object> data, List<Map<String, Object>> pointList) {
        int totalPage = 0;
        StringBuilder inputXml = new StringBuilder();
        String prevPageCode = "";

        for (Map<String, Object> point : pointList) {
            String pageCode   = str(point.get("PAGE_CODE"));
            String columnName = str(point.get("COLUMN_NAME"));
            String metaRow    = str(point.get("METAROW"));
            String metaLine   = str(point.get("METALINE"));
            String codeDataYn = str(point.get("CODEDATA_YN"));
            String codeData   = str(point.get("CODEDATA"));
            String delColYn   = str(point.get("DELETECOLUMNYN"));
            String insertVal  = str(data.get(columnName));

            if (inputXml.length() == 0) {
                inputXml.append("<INPUT_DATA pagecode='").append(esc(pageCode)).append("'>");
                prevPageCode = pageCode;
                totalPage++;
            }
            if (!pageCode.equals(prevPageCode)) {
                inputXml.append("</INPUT_DATA>");
                inputXml.append("<INPUT_DATA pagecode='").append(esc(pageCode)).append("'>");
                prevPageCode = pageCode;
                totalPage++;
            }

            // 삭제 조건 체크 (조건 불충족 시 skip)
            if (!delColYn.isEmpty() && !shouldInclude(delColYn, data)) {
                continue;
            }

            // 코드 메타 데이터 변환
            boolean include = true;
            if ("Y".equals(codeDataYn)) {
                if (insertVal.equals(codeData)) {
                    insertVal = "V";
                } else {
                    include = false;
                }
            }

            if (include && !insertVal.isEmpty()) {
                inputXml.append("<DATA XPosition='").append(esc(metaLine))
                        .append("' YPosition='").append(esc(metaRow))
                        .append("'><![CDATA[").append(insertVal).append("]]></DATA>");
            }
        }
        if (inputXml.length() > 0) {
            inputXml.append("</INPUT_DATA>");
        }
        inputXml.append("</ONLINE_INFORMATION>");

        // REGISTER_DATA 헤더
        String agencyId   = str(data.get("CNTPNT_SHOP_ID"));
        String cstmrNm    = str(data.get("CSTMR_NAME"));
        String contractNum = str(data.get("CONTRACT_NUM"));
        StringBuilder reg = new StringBuilder();
        reg.append("<?xml version='1.0' encoding='utf-8' ?>")
           .append("<ONLINE_INFORMATION>")
           .append("<REGISTER_DATA>")
           .append("<AGENCY_ID>").append(esc(agencyId)).append("</AGENCY_ID>")
           .append("<COMPANY_ID>").append(esc(agencyId)).append("</COMPANY_ID>")
           .append("<CUST_NM>").append(esc(cstmrNm)).append("</CUST_NM>")
           .append("<RGST_PRSN_ID>").append(esc(userId)).append("</RGST_PRSN_ID>")
           .append("<RES_NO>").append(esc(contractNum)).append("</RES_NO>")
           .append("<TOTAL_PAGE>").append(totalPage).append("</TOTAL_PAGE>")
           .append("</REGISTER_DATA>");

        return reg.toString() + inputXml.toString();
    }

    private boolean shouldInclude(String delColYn, Map<String, Object> data) {
        String val = str(data.get(delColYn));
        // 대부분 약관: N이면 제외
        return "Y".equals(val);
    }

    // ─────────────────────────────────────────────────────────────
    // XML 파일 저장
    // ─────────────────────────────────────────────────────────────
    private void saveXml(String xml, String fileName) throws IOException {
        File dir = new File(scanPath);
        if (!dir.exists()) dir.mkdirs();
        File f = new File(scanPath + fileName);
        try (FileOutputStream fos = new FileOutputStream(f)) {
            fos.write(xml.getBytes(StandardCharsets.UTF_8));
        }
    }

    // ─────────────────────────────────────────────────────────────
    // SCAN 서버로 XML 파일 multipart 전송.
    // ASIS: CustRequestScanServiceImpl.xmlFileSend()
    // ─────────────────────────────────────────────────────────────
    private String sendXmlToScan(String fileName) throws IOException {
        File f = new File(scanPath + fileName);
        String boundary = "----MsfScanBoundary" + System.currentTimeMillis();

        URL url = new URL(scanUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        conn.setConnectTimeout(30000);
        conn.setReadTimeout(30000);

        try (OutputStream os = conn.getOutputStream()) {
            // part header
            String partHeader = "--" + boundary + "\r\n"
                + "Content-Disposition: form-data; name=\"TransferFile\"; filename=\"" + fileName + "\"\r\n"
                + "Content-Type: application/xml\r\n\r\n";
            os.write(partHeader.getBytes(StandardCharsets.UTF_8));

            // file body
            try (FileInputStream fis = new FileInputStream(f)) {
                byte[] buf = new byte[4096];
                int len;
                while ((len = fis.read(buf)) != -1) os.write(buf, 0, len);
            }

            // end boundary
            os.write(("\r\n--" + boundary + "--\r\n").getBytes(StandardCharsets.UTF_8));
        }

        // 응답 파싱 — <SCAN_ID>값</SCAN_ID>
        StringBuilder resp = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) resp.append(line);
        }
        String body = resp.toString();
        int s = body.indexOf("<SCAN_ID>");
        int e = body.indexOf("</SCAN_ID>");
        if (s >= 0 && e > s) {
            return body.substring(s + 9, e).trim();
        }
        return null;
    }

    // ─────────────────────────────────────────────────────────────
    // DB SCAN_ID 업데이트
    // ─────────────────────────────────────────────────────────────
    private void updateScanId(String reqType, String scanId, Long custReqSeq) {
        try {
            if ("NC".equals(reqType)) {
                formSendMapper.updateNameChgScanId(scanId, custReqSeq);
            } else if ("CC".equals(reqType)) {
                formSendMapper.updateCancelScanId(scanId, custReqSeq);
            }
        } catch (Exception e) {
            log.warn("[FormSend] SCAN_ID DB 업데이트 실패 (무시): reqType={}, scanId={}", reqType, scanId, e);
        }
    }

    // ─────────────────────────────────────────────────────────────
    // 서식지 그룹코드 결정.
    // ASIS: CustRequestScanServiceImpl.getGroupCode()
    // ─────────────────────────────────────────────────────────────
    private String getGroupCode(Map<String, Object> data) {
        String reqType = str(data.get("REQ_TYPE"));
        if ("NC".equals(reqType)) return "E001"; // 명의변경
        if ("CC".equals(reqType)) return "G001"; // 해지상담
        if ("IS".equals(reqType)) return "C001"; // 안심보험
        if ("CL".equals(reqType)) return "D001"; // 통화내역열람
        return "";
    }

    private void deleteFile(String fileName) {
        try { new File(scanPath + fileName).delete(); } catch (Exception ignored) {}
    }

    private String str(Object o) {
        if (o == null) return "";
        return o.toString();
    }

    private String nvl(String s, String def) {
        return (s == null || s.isEmpty()) ? def : s;
    }

    /** XML 특수문자 이스케이프 (CDATA 외부 속성값 등에 사용) */
    private String esc(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&apos;");
    }
}
