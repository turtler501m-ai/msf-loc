package com.ktmmobile.msf.formSvcChg.service;

import com.ktmmobile.msf.common.mplatform.MplatFormSvc;
import com.ktmmobile.msf.common.mplatform.vo.MpRegSvcChgVO;
import com.ktmmobile.msf.formSvcChg.dto.InsrApplyReqDto;
import com.ktmmobile.msf.formSvcChg.dto.InsrProductDto;
import com.ktmmobile.msf.formSvcChg.mapper.SvcChgInsrMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 단말보험 서비스 구현.
 * ASIS CustRequestController(reqInsr.do) 처리 흐름과 동일.
 * - 보험상태: DB조회(MSP DB링크) → insrViewType 결정
 * - 상품목록: MSP_INTM_INSR_MST@DL_MSP (DB링크)
 * - 신청: X21 직접 (Y24 없음)
 */
@Service
public class SvcChgInsrSvcImpl implements SvcChgInsrSvc {

    private static final Logger logger = LoggerFactory.getLogger(SvcChgInsrSvcImpl.class);

    @Value("${SERVER_NAME:LOCAL}")
    private String serverName;

    /** M포탈(mcp-portal-was) URL — 안심보험 가입신청 저장 시 호출 */
    @Value("${mcp.portal.url:http://localhost:8080}")
    private String mcpPortalUrl;

    @Autowired
    private MplatFormSvc mplatFormSvc;

    @Autowired
    private SvcChgInsrMapper insrMapper;

    /**
     * 보험 가입 상태 조회 및 화면 타입(insrViewType) 결정.
     * ASIS: CustRequestController.reqInsr() → custRequestService.getInsrInfo(contractNum).
     *
     * insrViewType 분기:
     *   A   → 보험가입중 (INSR_STAT_CD = 0 or 1)
     *   ING → 가입 진행중 (ORDER_EXIST not null)
     *   B   → 당일 신청이력 있음 (REQUEST_EXIST not null) — 재신청 불가
     *   C   → 단말 45일 이내 (reqBuyType=MM, 별도 개통일 계산 필요)
     *   D   → 단말 45일 이후 (reqBuyType=MM)
     *   E   → 유심 45일 이내 (reqBuyType=UU)
     *   F   → 유심 45일 이후 (reqBuyType=UU)
     */
    @Override
    public Map<String, Object> getInsrInfo(String ncn) {
        Map<String, Object> result = new HashMap<>();

        if (isBlank(ncn)) {
            result.put("success", false);
            result.put("message", "ncn 파라미터가 누락되었습니다.");
            return result;
        }

        if ("LOCAL".equals(serverName)) {
            result.put("success", true);
            result.put("insrViewType", "D");
            result.put("reqBuyType", "MM");
            result.put("insrStatCd", null);
            result.put("orderExist", null);
            result.put("requestExist", null);
            return result;
        }

        try {
            Map<String, String> info = insrMapper.selectInsrInfo(ncn);

            String insrStatCd   = info != null ? info.get("INSR_STAT_CD")  : null;
            String orderExist   = info != null ? info.get("ORDER_EXIST")    : null;
            String requestExist = info != null ? info.get("REQUEST_EXIST")  : null;
            String reqBuyType   = info != null ? info.get("REQ_BUY_TYPE")   : null;

            String insrViewType = resolveInsrViewType(insrStatCd, orderExist, requestExist, reqBuyType);

            result.put("success", true);
            result.put("insrViewType", insrViewType);
            result.put("reqBuyType",   reqBuyType);
            result.put("insrStatCd",   insrStatCd);
            result.put("orderExist",   orderExist);
            result.put("requestExist", requestExist);
        } catch (Exception e) {
            logger.error("보험 가입 상태 조회 오류: {}", e.getMessage());
            result.put("success", false);
            result.put("message", "보험 가입 상태 조회 중 오류가 발생했습니다.");
        }
        return result;
    }

    /**
     * insrViewType 결정 로직.
     * ASIS: CustRequestController.reqInsr() 분기 로직과 동일.
     */
    private String resolveInsrViewType(String insrStatCd, String orderExist,
                                       String requestExist, String reqBuyType) {
        // 보험 가입중
        if ("0".equals(insrStatCd) || "1".equals(insrStatCd)) {
            return "A";
        }
        // 보험 가입 진행중
        if (orderExist != null) {
            return "ING";
        }
        // 당일 신청이력 있음 (재신청 불가)
        if (requestExist != null) {
            return "B";
        }
        // 구매유형별 분기 (C/D: 단말, E/F: 유심)
        // ※ 45일 이내/이후 분기는 개통일자 기반으로 프론트 또는 상위 로직에서 처리
        if ("UU".equals(reqBuyType) || "AL".equals(reqBuyType)) {
            return "E"; // 유심 (45일 구분은 프론트)
        }
        return "C"; // 단말 (45일 구분은 프론트)
    }

    /**
     * 단말보험 상품 목록 조회.
     * ASIS: AppformController.selectInsrProdListAjax.do → AppformMapper.selectInsrProdList().
     * LOCAL 모드: Mock 데이터 반환.
     */
    @Override
    public Map<String, Object> getInsuranceProducts(String reqBuyType, String rprsPrdtId) {
        Map<String, Object> result = new HashMap<>();

        if ("LOCAL".equals(serverName)) {
            List<InsrProductDto> mockList = new ArrayList<>();
            InsrProductDto mock1 = new InsrProductDto();
            mock1.setInsrProdCd("PL214L310");
            mock1.setInsrProdNm("단말보험 기본형");
            mock1.setCmpnLmtAmt(500000L);
            mock1.setInsrEnggCnt(0);
            mock1.setInsrTypeCd("01");
            mockList.add(mock1);
            InsrProductDto mock2 = new InsrProductDto();
            mock2.setInsrProdCd("PL213M175");
            mock2.setInsrProdNm("USIM 전용 보험");
            mock2.setCmpnLmtAmt(300000L);
            mock2.setInsrEnggCnt(0);
            mock2.setInsrTypeCd("02");
            mockList.add(mock2);
            result.put("success", true);
            result.put("items", mockList);
            return result;
        }

        try {
            Map<String, String> params = new HashMap<>();
            params.put("reqBuyType",  reqBuyType  != null ? reqBuyType  : "UU");
            params.put("rprsPrdtId",  rprsPrdtId  != null ? rprsPrdtId  : "");
            List<InsrProductDto> list = insrMapper.selectInsrProdList(params);
            result.put("success", true);
            result.put("items", list);
        } catch (Exception e) {
            logger.error("단말보험 상품 목록 조회 오류: {}", e.getMessage());
            result.put("success", false);
            result.put("message", "단말보험 상품 목록 조회 중 오류가 발생했습니다.");
        }
        return result;
    }

    /**
     * 단말보험 가입 신청.
     * ASIS: CustRequestController.custRequestAjax(reqType=IS)
     *       → regSvcChg(X21, soc=insrProdCd) → MSF_CUST_REQUEST_INSR 저장.
     * Y24 사전체크 없음 (ASIS에 없음).
     */
    @Override
    public Map<String, Object> applyInsurance(InsrApplyReqDto req) {
        Map<String, Object> result = new HashMap<>();

        if (req == null || isBlank(req.getNcn()) || isBlank(req.getCtn())
                || isBlank(req.getInsrProdCd())) {
            result.put("success", false);
            result.put("message", "필수 파라미터가 누락되었습니다. (ncn, ctn, insrProdCd)");
            return result;
        }

        try {
            // X21 부가서비스 신청 — soc = insrProdCd (보험상품코드)
            // ASIS: regSvcService.regSvcChg(ncn, ctn, custId, insrProdCd, "")
            MpRegSvcChgVO applyVo = mplatFormSvc.regSvcChg(
                req.getNcn(), req.getCtn(), req.getCustId(), req.getInsrProdCd(), "");

            if (!applyVo.isSuccess()) {
                result.put("success", false);
                result.put("resultCode", applyVo.getResultCode());
                result.put("message", applyVo.getSvcMsg());
                return result;
            }

            // M포탈 DB 저장 — mcp-api 호출 (NMCP_CUST_REQUEST_MST + NMCP_CUST_REQUEST_INSR)
            // ASIS: CustRequestController.custRequestAjax(reqType=IS) → insertCustRequestMst/Insr
            saveToCustRequestMcp(req);

            result.put("success", true);
            result.put("resultCode", applyVo.getResultCode());
            result.put("globalNo", applyVo.getGlobalNo());
            result.put("message", "");

        } catch (Exception e) {
            logger.error("단말보험 가입 신청 오류: {}", e.getMessage());
            result.put("success", false);
            result.put("message", "단말보험 가입 처리 중 오류가 발생했습니다.");
        }
        return result;
    }

    /**
     * M포탈 DB에 안심보험 가입신청 저장.
     * mcp-portal-was /mypage/custRequestInsrSaveApi.do 호출
     * (ASIS custRequestAjax reqType=IS 와 동일한 seq채번+MST+INSR 저장 로직, 세션 없이 호출 가능)
     * LOCAL 모드: mcp-portal-was 미연결 시 [TEST ONLY] MSF 자체 테이블에 저장.
     */
    @SuppressWarnings("unchecked")
    private void saveToCustRequestMcp(InsrApplyReqDto req) {
        if ("LOCAL".equals(serverName)) {
            // [TEST ONLY] 로컬 개발 환경: MSF_CUST_REQUEST_INSR에 저장 (mcp-portal-was 미연결)
            try {
                insrMapper.insertCustRequestInsr(req);
            } catch (Exception e) {
                logger.warn("[TEST ONLY] MSF_CUST_REQUEST_INSR 저장 실패: {}", e.getMessage());
            }
            return;
        }

        try {
            // mcp-portal-was POST /mypage/custRequestInsrSaveApi.do 호출
            // ASIS custRequestAjax(reqType=IS) 와 동일 저장 로직 (세션 없음)
            Map<String, Object> param = new HashMap<>();
            param.put("contractNum",      nvl(req.getNcn()));
            param.put("cstmrName",        nvl(req.getName()));
            param.put("mobileNo",         nvl(req.getCtn()));
            param.put("cstmrNativeRrn",   nvl(req.getCstmrNativeRrn()));
            param.put("onlineAuthType",   nvl(req.getOnlineAuthType()));
            param.put("onlineAuthInfo",   nvl(req.getInsrAuthInfo()));
            param.put("custId",           nvl(req.getCustId()));
            param.put("cstmrType",        nvl(req.getCstmrType()));
            param.put("insrProdCd",       nvl(req.getInsrProdCd()));
            param.put("etcMobile",        nvl(req.getEtcMobile()));

            RestTemplate rt = new RestTemplate();
            Map<String, Object> res = rt.postForObject(
                mcpPortalUrl + "/mypage/custRequestInsrSaveApi.do", param, Map.class);

            if (res == null || !"SUCCESS".equals(res.get("RESULT_CODE"))) {
                String code = res != null ? String.valueOf(res.get("RESULT_CODE")) : "NULL";
                logger.warn("M포탈 안심보험 DB 저장 실패 코드={} (X21 신청은 성공)", code);
            } else {
                logger.info("M포탈 안심보험 DB 저장 완료 — CUST_REQ_SEQ={}", res.get("CUST_REQ_SEQ"));
            }

        } catch (Exception e) {
            // DB 저장 실패 시 경고만 (X21 신청은 이미 성공)
            logger.warn("M포탈 안심보험 DB 저장 실패 (X21 신청은 성공): {}", e.getMessage());
        }
    }

    private static String nvl(String s) {
        return s != null ? s : "";
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
