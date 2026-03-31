package com.ktmmobile.msf.formComm.service;

import com.ktmmobile.msf.common.mplatform.MplatFormSvc;
import com.ktmmobile.msf.common.mplatform.vo.MpCrdtCardAthnVO;
import com.ktmmobile.msf.formComm.dto.AgencyShopResVO;
import com.ktmmobile.msf.formComm.dto.CardCheckReqDto;
import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;
import com.ktmmobile.msf.formComm.mapper.SvcAppFormMapper;
import com.ktmmobile.msf.formComm.mapper.ContractInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 업무공통 서비스 구현 — 인터페이스 연계 없는 공통 기능.
 * 인터페이스 연계 서비스(Y04/X01/X85/NICE 등)는 SvcChgInfoSvcImpl 참조.
 */
@Service
public class FormCommSvcImpl implements FormCommSvc {

    private static final Logger logger = LoggerFactory.getLogger(FormCommSvcImpl.class);

    @Autowired
    private SvcAppFormMapper appFormMapper;

    @Autowired
    private ContractInfoMapper contractInfoMapper;

    @Autowired
    private MplatFormSvc mplatFormSvc;

    @Value("${SERVER_NAME:}")
    private String serverLocation;

    @Override
    public List<AgencyShopResVO> getAgencyList() {
        if ("LOCAL".equals(serverLocation)) {
            return Arrays.asList(
                new AgencyShopResVO("AG001", "강남대리점"),
                new AgencyShopResVO("AG002", "서초대리점"),
                new AgencyShopResVO("AG003", "송파대리점"),
                new AgencyShopResVO("AG004", "마포대리점"),
                new AgencyShopResVO("AG005", "종로대리점")
            );
        }
        return appFormMapper.selectAgencyList();
    }

    /**
     * IF_0007 카드번호 유효성 체크.
     * 검증 순서:
     * 1. 카드번호 16자리 숫자 여부
     * 2. Luhn Algorithm (ASIS myNameChg.js checkCardNumber() 동일 로직)
     * 3. 유효기간 (cardYy, cardMm) — 현재 월 이후인지 확인
     * 4. birthDate + custNm 모두 있으면 X91 M플랫폼 실인증 수행
     *    (ASIS AppformController.crdtCardAthnInfo() 동일 흐름)
     */
    @Override
    public Map<String, Object> checkCard(CardCheckReqDto req) {
        Map<String, Object> result = new HashMap<>();

        if (req == null || isBlank(req.getCardNo())) {
            result.put("success", false);
            result.put("message", "카드번호를 입력해 주세요.");
            return result;
        }

        String digits = req.getCardNo().replaceAll("[^0-9]", "");

        // 1. 16자리 검증
        if (digits.length() != 16) {
            result.put("success", false);
            result.put("message", "카드번호는 16자리 숫자여야 합니다.");
            return result;
        }

        // 2. Luhn Algorithm (ASIS checkCardNumber() 동일 로직)
        int sum = 0;
        boolean alt = false;
        for (int i = digits.length() - 1; i >= 0; i--) {
            int n = digits.charAt(i) - '0';
            if (alt) {
                n *= 2;
                if (n > 9) n -= 9;
            }
            sum += n;
            alt = !alt;
        }
        if (sum % 10 != 0) {
            result.put("success", false);
            result.put("message", "유효하지 않은 카드번호입니다.");
            return result;
        }

        // 3. 유효기간 검증 (cardYy, cardMm)
        if (!isBlank(req.getCardYy()) && !isBlank(req.getCardMm())) {
            try {
                int yy = Integer.parseInt(req.getCardYy());
                int mm = Integer.parseInt(req.getCardMm());
                java.time.LocalDate now = java.time.LocalDate.now();
                int curYy = now.getYear() % 100;
                int curMm = now.getMonthValue();
                if (yy < curYy || (yy == curYy && mm < curMm)) {
                    result.put("success", false);
                    result.put("message", "카드 유효기간이 만료되었습니다.");
                    return result;
                }
            } catch (NumberFormatException e) {
                result.put("success", false);
                result.put("message", "유효기간 형식이 올바르지 않습니다.");
                return result;
            }
        }

        // 4. X91 M플랫폼 카드 실인증 (birthDate + custNm 있을 때만)
        if (!isBlank(req.getBirthDate()) && !isBlank(req.getCustNm())) {
            logger.debug("[X91] 카드 실인증 Start: custNm={}", req.getCustNm());
            try {
                String termDay = (isBlank(req.getCardYy()) ? "" : req.getCardYy())
                               + (isBlank(req.getCardMm()) ? "" : req.getCardMm()); // YYMM
                MpCrdtCardAthnVO vo = mplatFormSvc.moscCrdtCardAthnInfo(
                    digits, termDay, req.getBirthDate(), req.getCustNm());

                if (!vo.isSuccess()) {
                    result.put("success", false);
                    result.put("message", "카드 인증 중 오류가 발생했습니다.");
                    return result;
                }
                logger.debug("[X91] 카드 실인증 완료: trtResult={}, globalNo={}", vo.getTrtResult(), vo.getGlobalNo());
                if (!"Y".equals(vo.getTrtResult())) {
                    String trtMsg = vo.getTrtMsg();
                    if (trtMsg != null && trtMsg.contains("주민번호")) {
                        result.put("success", false);
                        result.put("message", "최초 요금 납부등록은 가입자 본인 명의의 카드로만 가능합니다.");
                    } else {
                        result.put("success", false);
                        result.put("message", isBlank(trtMsg)
                            ? "신용카드 유효성 확인에 실패하였습니다. 카드 정보를 확인해 주세요."
                            : trtMsg);
                    }
                    return result;
                }
                result.put("cardKindCd", vo.getCrdtCardKindCd());
                result.put("cardNm",     vo.getCrdtCardNm());
                result.put("globalNo",   vo.getGlobalNo());
            } catch (Exception e) {
                logger.error("X91 카드인증 오류: {}", e.getMessage());
                result.put("success", false);
                result.put("message", "카드 인증 중 오류가 발생했습니다.");
                return result;
            }
        }

        result.put("success", true);
        result.put("message", "");
        return result;
    }

    /**
     * N7003 — 개통일자 조회.
     * MSP_JUO_SUB_INFO.LST_COM_ACTV_DATE 직접 조회 (운영: @DL_MSP).
     * ASIS 흐름: initActivationDate 14자 → 앞 8자리 YYYYMMDD → yyyy.MM.dd 포맷.
     * LOCAL: Mock 반환 (2023.01.05).
     */
    @Override
    public Map<String, Object> getActivationDate(SvcChgInfoDto req) {
        Map<String, Object> result = new HashMap<>();

        if (req == null || isBlank(req.getNcn())) {
            result.put("success", false);
            result.put("message", "계약번호(ncn)가 필요합니다.");
            return result;
        }

        if ("LOCAL".equals(serverLocation)) {
            result.put("success", true);
            result.put("activationDate", "2023.01.05");
            result.put("activationDateRaw", "20230105");
            return result;
        }

        try {
            logger.debug("[N7003] 개통일자 조회 Start: ncn={}", req.getNcn());
            String raw = appFormMapper.selectActivationDate(req.getNcn());
            if (isBlank(raw)) {
                result.put("success", false);
                result.put("message", "개통일자 정보를 찾을 수 없습니다.");
                return result;
            }
            // raw: 14자(YYYYMMDDHH24MISS) 또는 8자(YYYYMMDD) — 앞 8자리 추출
            String digits = raw.replaceAll("[^0-9]", "");
            String ymd = digits.length() >= 8 ? digits.substring(0, 8) : digits;
            String formatted = ymd.length() == 8
                ? ymd.substring(0, 4) + "." + ymd.substring(4, 6) + "." + ymd.substring(6, 8)
                : raw;
            logger.debug("[N7003] 개통일자 조회 완료: ncn={}, activationDate={}", req.getNcn(), formatted);
            result.put("success", true);
            result.put("activationDate", formatted);
            result.put("activationDateRaw", ymd);
        } catch (Exception e) {
            logger.error("[N7003] 개통일자 조회 오류: {}", e.getMessage());
            result.put("success", false);
            result.put("message", "개통일자 조회 중 오류가 발생했습니다.");
        }
        return result;
    }

    /**
     * N7002 — 접점코드(ORGN_ID)로 대리점 코드·정보 조회.
     * ASIS: AppformMapper.xml selectAgentCode (ORG_ORGN_INFO_MST@DL_MSP).
     * LOCAL: Mock 반환 (AG001 / 강남대리점).
     */
    @Override
    public Map<String, Object> getAgentCode(String cntpntShopId) {
        if ("LOCAL".equals(serverLocation)) {
            Map<String, Object> mock = new HashMap<>();
            mock.put("KT_ORG_ID", "AG001");
            mock.put("ORGN_NM", "강남대리점");
            mock.put("ROADN_ADR_ZIPCD", "06234");
            mock.put("ROADN_ADR_BAS_SBST", "서울특별시 강남구 테헤란로 123");
            mock.put("ROADN_ADR_DTL_SBST", "1층");
            return mock;
        }
        logger.debug("[N7002] 접점코드 대리점 조회 Start: cntpntShopId={}", cntpntShopId);
        Map<String, Object> result = appFormMapper.selectAgentCode(cntpntShopId);
        logger.debug("[N7002] 접점코드 대리점 조회 완료: KT_ORG_ID={}", result != null ? result.get("KT_ORG_ID") : "null");
        return result;
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
